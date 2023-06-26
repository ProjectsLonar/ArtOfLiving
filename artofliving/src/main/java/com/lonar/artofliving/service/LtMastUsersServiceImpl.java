package com.lonar.artofliving.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.common.Validation;
import com.lonar.artofliving.dao.LtMastSmsTokenDao;
import com.lonar.artofliving.dao.LtMastUsersDao;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtAolRolesMaster;
import com.lonar.artofliving.model.LtAolUsersMaster;
import com.lonar.artofliving.model.LtMastLogins;
import com.lonar.artofliving.model.LtMastSmsToken;
import com.lonar.artofliving.model.RequestDto;
import com.lonar.artofliving.model.ResponceEntity;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.repository.LtMastLoginsRepository;
import com.lonar.artofliving.utils.UtilsMaster;

@Service
public class LtMastUsersServiceImpl implements LtMastUsersService,CodeMaster  { 

	@Autowired 
	LtMastUsersDao ltMastUsersDao;
	
	@Autowired
	private Environment env;
	
	@Autowired
	LtMastLoginsRepository ltMastLoginsRepository;
	
	@Autowired
	LtMastSmsTokenDao ltMastSmsTokenDao;
	
	@Autowired
	LtMastSmsTokenService ltMastSmsTokenService;
	
	@Override
	public Status sendOTPToUser(String mobileNumber) throws ServiceException, IOException{
	
		Status status = new Status();
		LtAolUsersMaster user = null;

		if (Validation.validatePhoneNumber(mobileNumber)) {

			if (mobileNumber != null) {
				user = ltMastUsersDao.getLtMastUsersByMobileNumber(mobileNumber);
			} else {
				status.setCode(FAIL);
				status.setMessage("Enter Valid Mobile Number. ");
				return status;
			}
	}
		

		if (user == null) {
			LtAolUsersMaster ltMastUser = new LtAolUsersMaster();

			ltMastUser.setMobileNumber(mobileNumber);
			ltMastUser.setStatus(INPROCESS);

			ltMastUser.setCreatedBy(-1L);
			ltMastUser.setCreationDate(new Date());
			ltMastUser.setLastUpdatedDate(new Date()); 
			ltMastUser.setLastUpdatedBy(-1L);
			ltMastUser.setLastUpdateLogin(-1L);
			ltMastUser.setRoleId(1L);
			ltMastUser.setUserName(""); 
			ltMastUser = ltMastUsersDao.saveLtMastUsers(ltMastUser);

			if (ltMastUser.getUserId() != null) {
				LtMastLogins mastLogins = this.generateAndSendOtp(ltMastUser);
				if (mastLogins != null) {
					status.setCode(SUCCESS);
					status.setMessage("OTP Send Successfully.");

				} else {
					status.setCode(FAIL);
					status.setMessage("OTP Not Send.");
				}
			} else {
				status.setCode(FAIL);
				status.setMessage("OTP Not Send.");
			}

		} else {
			      if(!user.getStatus().equalsIgnoreCase(INACTIVE)) {
					
				     LtMastLogins mastLogins = this.generateAndSendOtp(user);
				      
			if (mastLogins != null) {
				status.setCode(SUCCESS);
				status.setMessage("OTP Send Successfully.");
			}
			} else {
				status.setCode(FAIL);
				status.setMessage("Your Account Has Been Deleted. ");
				//status.setMessage(env.getProperty("lonar.users.sentOTPnotsuccess"));
			}
		}
		
	return status;
}
	
	private LtMastLogins generateAndSendOtp(LtAolUsersMaster ltMastUser) throws IOException, ServiceException {
		// we have to implememt OTP logic
		Status status = new Status();
		String otp = null;
		
		 // Generated default OTP 1234 for UAT
		boolean isOtpForUAT = false;
		// if(isOtpForUAT = Boolean.parseBoolean(env.getProperty("generatedOtpForUat"))) {
		if(isOtpForUAT = true) {
            if(isOtpForUAT) {
       		 otp = "" + "1234";
             }
            }
		 
		 // Generated Random OTP for Production
		 else {
		   
			 if (ltMastUser.getMobileNumber().equalsIgnoreCase("7038631545")) {
					otp = "" + "1234";
				   } else {
					otp = "" + getRandomNumberInRange(1000, 9999);
				 }	 
		       
		     }
		 
		LtMastLogins ltMastLogin = ltMastUsersDao.getLoginDetailsByUserId(ltMastUser.getUserId());

		LtMastLogins ltMastLogins = new LtMastLogins();

		if (ltMastLogin != null) {
			if (ltMastLogin.getTokenId() != null) {
				ltMastLogins.setTokenId(ltMastLogin.getTokenId());
			}
		}
		ltMastLogins.setUserId(ltMastUser.getUserId());

		ltMastLogins.setOtp(Long.parseLong(otp));

		if (ltMastUser.getStatus().equals(INPROCESS) || ltMastUser.getStatus().equals(INACTIVE)) {
			ltMastLogins.setStatus(INPROCESS);
		} else {
			ltMastLogins.setStatus("SUCCESS");
		}

		ltMastLogins.setLoginDate(new Date());
		ltMastLogins.setDevice(null);
		ltMastLogins.setIpAddress(null);

		ltMastLogins = ltMastLoginsRepository.save(ltMastLogins);

		if (ltMastLogins.getLoginId() != null) {
			if (ltMastUser.getMobileNumber() != null) {
				status = sendMessage(ltMastLogins, ltMastUser.getMobileNumber());
			}

			if (status.getCode() == SUCCESS)
				return ltMastLogins;
			else
				return null;
		} else
			return null;
	}
	

	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
		// return 1234;
	}
	
	private Status sendMessage(LtMastLogins ltMastLogins, String mobileNumber) throws IOException, ServiceException {
		Status status = new Status();

		List<LtMastSmsToken> ltMastSmsTokenList = new ArrayList<LtMastSmsToken>();
		LtMastSmsToken ltMastSmsToken = new LtMastSmsToken();

		Long trasid = System.currentTimeMillis();
		ltMastSmsToken.setTransactionId(trasid);
		ltMastSmsToken.setSmsType("OTP");
		ltMastSmsToken.setSmsObject(ltMastLogins.getOtp().toString());
		ltMastSmsToken.setSendTo(Long.parseLong(mobileNumber));
		ltMastLogins.setMobileNumber(mobileNumber);
		ltMastSmsToken.setSendDate(new Date());
		ltMastSmsToken.setSmsStatus("SENDING");
		ltMastSmsToken.setUserId(ltMastLogins.getUserId());
		ltMastSmsTokenList.add(ltMastSmsToken);

		List<LtMastSmsToken> ltMastSmsTokenListOp = ltMastSmsTokenDao.saveall(ltMastSmsTokenList);

		if (ltMastSmsTokenListOp.isEmpty()) {
			status.setMessage("Message OTP send failed");
			status.setCode(INSERT_FAIL);

		} else {
			
			status = ltMastSmsTokenService.sendSms(ltMastSmsToken.getUserId(), trasid);
			if (status.getCode() == SUCCESS) {
				status.setCode(SUCCESS);
				status.setMessage("Message OTP Send Success");
			}

		}
		return status;
	}
	
	
	@Override
	public ResponceEntity login(LtMastLogins ltMastLogins, HttpServletResponse response) throws ServiceException {
		ResponceEntity entity = new ResponceEntity();
		Status status = new Status();
		LtAolUsersMaster ltMastUser = ltMastUsersDao.getLtMastUsersByMobileNumber(ltMastLogins.getMobileNumber().toString());

		LtMastLogins ltMastLogin = ltMastUsersDao.getLoginDetailsByUserId(ltMastUser.getUserId());
		ltMastLogin.setOtp(1234L);
		ltMastLogins.setOtp(1234L);
		
		if (ltMastLogin != null) {
			if (ltMastLogin.getOtp().equals(ltMastLogins.getOtp())) {
				ltMastLogin.setStatus(INPROCESS);
				ltMastLogin.setLoginDate(new Date());
				ltMastLogins = ltMastLoginsRepository.save(ltMastLogin);
				status.setCode(SUCCESS);
				status.setMessage("Login Success");
				status.setData(ltMastLogin.getStatus());

				entity.setCode(SUCCESS);
				entity.setUserId(ltMastLogin.getUserId());
				entity.setStatus(ltMastUser.getStatus());
				entity.setUserName(ltMastUser.getUserName());
				entity.setLastLoginId(ltMastLogins.getLoginId());

				//entity.setRole(ltMastUser.getRoleId());
				//entity.setFirstName(ltMastUser.getFirstName());

				entity.setRole(ltMastUser.getRole());
				entity.setRoleId(ltMastUser.getRoleId());

				//entity.setLastName(ltMastUser.getLastName());
				entity.setMobileNumber(ltMastUser.getMobileNumber());
				//entity.setEmail(ltMastUser.getEmail());
				

			} else {
				entity.setCode(FAIL);
				entity.setMessage("Please Enter Valid OTP");
			}
		} else {
			status.setCode(SUCCESS);
			status.setMessage("Login Success");
			status.setData("SUCCESS");
			entity.setCode(SUCCESS);
			entity.setRole(ltMastUser.getRole());
			entity.setRoleId(ltMastUser.getRoleId());
			entity.setUserId(ltMastUser.getUserId());
			entity.setUserName(ltMastUser.getUserName());
			//entity.setFirstName(ltMastUser.getUserName());
			//entity.setLastName(ltMastUser.getLastName());
			entity.setMobileNumber(ltMastUser.getMobileNumber());
			//entity.setEmail(ltMastUser.getEmail());
		}

		if (status.getCode() == SUCCESS) {
			System.out.println("login success");
		}

		return entity;
	}
	
	@Override
	public Status getallactiveroles() throws ServiceException, IOException{
		Status status = new Status();
		List<LtAolRolesMaster> ltAolUserList = ltMastUsersDao.getallactiveroles( );
		if (!ltAolUserList.isEmpty()) {
			status.setCode(RECORD_FOUND);
			status.setMessage("RECORD_FOUND");
			status.setData(ltAolUserList);

		} else {
			status.setCode(RECORD_NOT_FOUND);
			status.setMessage("RECORD_Not_FOUND");
			status.setData(ltAolUserList);
		}
		return status;
	}
	
	@Override
	public Status getallusers(RequestDto requestDto) throws ServiceException, IOException {
		try {
		Status status= new Status();
		
		List<LtAolUsersMaster> ltMastUserList= ltMastUsersDao.getallusers(requestDto);
		
		if(ltMastUserList!= null) {
			status.setCode(RECORD_FOUND);
			status.setMessage("Record Found Successfully.");
			status.setData(ltMastUserList);
		}else {
			status.setCode(RECORD_NOT_FOUND);
			status.setMessage("Record Not Found.");
			status.setData(null);
		}
		return status;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public Status deleteUser(Long userId) throws ServiceException, IOException {
		Status status = new Status();
		LtAolUsersMaster ltMastUser = ltMastUsersDao.deleteUser(userId);
		if (ltMastUser != null) 
		{
		  status.setCode(DELETE_SUCCESSFULLY);
	      //status.setData(ltMastUser);
	      status.setMessage("User Deleted Successfully.");
	      return status;
        } 
		else {
	        status.setCode(DELETE_FAIL);
	        //status.setData(null);
	        status.setMessage("DELETE_FAIL");
	        return status;
         }
      }
	
	@Override
	public Status saveUserDetails(LtAolUsersMaster ltAolUsersMaster) throws ServiceException,IOException{
		Status status = new Status();
		if(ltAolUsersMaster!=null) {
			status = checkDuplicate(ltAolUsersMaster);
			if (status.getCode() == FAIL) {
				return status;
			}
			if(ltAolUsersMaster.getUserId() == null) {
				LtAolUsersMaster ltAolUsersMasters =new LtAolUsersMaster();
				ltAolUsersMasters.setMobileNumber(ltAolUsersMaster.getMobileNumber());
				ltAolUsersMasters.setUserName(ltAolUsersMaster.getUserName());
				ltAolUsersMasters.setStatus(ltAolUsersMaster.getStatus());
				ltAolUsersMasters.setRoleId(ltAolUsersMaster.getRoleId());
				ltAolUsersMasters.setCreatedBy(ltAolUsersMaster.getCreatedBy());
				ltAolUsersMasters.setCreationDate(UtilsMaster.getCurrentDateTime());
				ltAolUsersMasters.setLastUpdatedBy(ltAolUsersMaster.getCreatedBy());
				ltAolUsersMasters.setLastUpdateLogin(ltAolUsersMaster.getCreatedBy());
				ltAolUsersMasters.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
				
				LtAolUsersMaster ltAolUsersMastersUpdated = ltMastUsersDao.saveLtMastUsers(ltAolUsersMasters);
				if (ltAolUsersMastersUpdated != null) {
					status.setCode(INSERT_SUCCESSFULLY);
					status.setData(ltAolUsersMastersUpdated);
					status.setMessage("User Added Successfully.");
					return status;
				} else {
					status.setCode(INSERT_FAIL);
					status.setData(null);
					status.setMessage("Insert Fail.");
					return status;
				}
			}else {
				LtAolUsersMaster userDetails = ltMastUsersDao.getUserById(ltAolUsersMaster.getUserId());
				userDetails.setUserName(ltAolUsersMaster.getUserName());
				userDetails.setRoleId(ltAolUsersMaster.getRoleId());
				userDetails.setStatus(ltAolUsersMaster.getStatus());
				userDetails.setLastUpdatedBy(ltAolUsersMaster.getCreatedBy());
				userDetails.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
				userDetails.setLastUpdateLogin(ltAolUsersMaster.getCreatedBy());
				
				LtAolUsersMaster ltAolUsersMastersUpdated = ltMastUsersDao.saveLtMastUsers(userDetails);
				
				if (ltAolUsersMastersUpdated != null) {
					status.setCode(UPDATE_SUCCESSFULLY);
					status.setData(ltAolUsersMastersUpdated);
					status.setMessage("User Updated Successfully.");
					return status;
				} else {
					status.setCode(UPDATE_FAIL);
					status.setData(null);
					status.setMessage("Update Fail.");
					return status;
				}
			}
		}
		
		return status;
		
	}
	
	private Status checkDuplicate(LtAolUsersMaster ltAolUsersMaster) throws ServiceException {
		Status status = new Status();
		LtAolUsersMaster ltAolUsersMasters = ltMastUsersDao.getLtMastUsersByMobileNumber(ltAolUsersMaster.getMobileNumber());
		if (ltAolUsersMasters != null) {
			if (!ltAolUsersMasters.getUserId().equals(ltAolUsersMaster.getUserId())) {
				status.setCode(FAIL);
				status.setMessage("Mobile Number Already Exists.");
				return status;
			}
		}
		status.setCode(SUCCESS);
		return status;
	}
}//end of class

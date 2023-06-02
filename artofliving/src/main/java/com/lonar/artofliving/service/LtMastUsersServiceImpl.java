package com.lonar.artofliving.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.common.Validation;
import com.lonar.artofliving.dao.LtMastSmsTokenDao;
import com.lonar.artofliving.dao.LtMastUsersDao;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtAolUsersMaster;
import com.lonar.artofliving.model.LtMastLogins;
import com.lonar.artofliving.model.LtMastSmsToken;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.repository.LtMastLoginsRepository;

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
		//ltMastLogins.setMobile(mobileNumber);
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
	
}//end of class

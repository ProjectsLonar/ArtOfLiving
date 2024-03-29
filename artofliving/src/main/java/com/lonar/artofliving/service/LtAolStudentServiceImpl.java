package com.lonar.artofliving.service;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dao.LtAolCallListMasterDao;
import com.lonar.artofliving.model.CallListMasterResponseDto;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtAolCallListMaster;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.utils.UtilsMaster;

@Service
public class LtAolStudentServiceImpl implements LtAolStudentService,CodeMaster {

	@Autowired
	LtAolCallListMasterDao ltAolCallListMasterDao;
	
	@Override
	public Status addStudent(LtAolCallListMaster ltAolCallListMaster) throws ServiceException, BusinessException, IOException {
		try {
		       Status status =new Status();
		       if(ltAolCallListMaster!=null) {
		    	   		    	   
			   LtAolCallListMaster ltAolCallListMasterUpdate = new LtAolCallListMaster();
			   
			   status = checkDuplicate(ltAolCallListMaster);
			     if (status.getCode() == FAIL) {
			    	 LtAolCallListMaster ltAolCallListMasters = ltAolCallListMasterDao.getAolCallListByMobileNumber(ltAolCallListMaster.getMobileNumber());
			    	  ltAolCallListMasterUpdate.setCallListId(ltAolCallListMasters.getCallListId());
						ltAolCallListMasterUpdate.setMobileNumber(ltAolCallListMasters.getMobileNumber());
						
						if(ltAolCallListMaster.getStudentName() !=null) {
						ltAolCallListMasterUpdate.setStudentName(ltAolCallListMaster.getStudentName());
						}else {ltAolCallListMasterUpdate.setStudentName(ltAolCallListMasters.getStudentName());}
						
						ltAolCallListMasterUpdate.setCallSource("Mobile");
						if(ltAolCallListMaster.getAddress() !=null) {
						ltAolCallListMasterUpdate.setAddress(ltAolCallListMaster.getAddress());
						}else {ltAolCallListMasterUpdate.setAddress(ltAolCallListMasters.getAddress());}
						
						if(ltAolCallListMaster.getCity() !=null) {
						ltAolCallListMasterUpdate.setCity(ltAolCallListMaster.getCity());
						}else {ltAolCallListMasterUpdate.setCity(ltAolCallListMasters.getCity());}
						
						if(ltAolCallListMaster.getPinCode() !=null) {
						ltAolCallListMasterUpdate.setPinCode(ltAolCallListMaster.getPinCode());
						}else {ltAolCallListMasterUpdate.setPinCode(ltAolCallListMasters.getPinCode());}
						
						if(ltAolCallListMaster.getEmail() !=null) {
						ltAolCallListMasterUpdate.setEmail(ltAolCallListMaster.getEmail());}
						else {ltAolCallListMasterUpdate.setEmail(ltAolCallListMasters.getEmail());}
						
						if(ltAolCallListMaster.getGender() !=null) {
						ltAolCallListMasterUpdate.setGender(ltAolCallListMaster.getGender());
						}else {ltAolCallListMasterUpdate.setGender(ltAolCallListMasters.getGender());}
						
						if(ltAolCallListMaster.getDob() !=null) {
						ltAolCallListMasterUpdate.setDob(ltAolCallListMaster.getDob());
						}else {ltAolCallListMasterUpdate.setDob(ltAolCallListMasters.getDob());}
						
						ltAolCallListMasterUpdate.setStatus("New Contact");
						ltAolCallListMasterUpdate.setStatusId(1L);
						
						
						ltAolCallListMasterUpdate.setAssignedTo(null);
						
						if(ltAolCallListMasters.getCreatedBy()!=  null) {
							ltAolCallListMasterUpdate.setCreatedBy(ltAolCallListMasters.getCreatedBy());
						}
						ltAolCallListMasterUpdate.setCreationDate(ltAolCallListMasters.getCreationDate());
						ltAolCallListMasterUpdate.setLastUpdatedBy(ltAolCallListMaster.getUserId());
						ltAolCallListMasterUpdate.setLastUpdateLogin(ltAolCallListMaster.getUserId());
						ltAolCallListMasterUpdate.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
						
						ltAolCallListMasterUpdate = ltAolCallListMasterDao.save(ltAolCallListMasterUpdate);
						 
						CallListMasterResponseDto responseDto = ltAolCallListMasterDao.getCallListResponseById(ltAolCallListMasterUpdate.getCallListId());
						
						if(responseDto !=  null) {
							status.setCode(UPDATE_SUCCESSFULLY);
							status.setMessage("Student Updated Successfully.");
							status.setData(responseDto);
							return status;
						}
			      }
		    
			if(ltAolCallListMaster.getCallListId()!= null) 
			{
			  LtAolCallListMaster ltAolCallListMasters = ltAolCallListMasterDao.getLtAolCallList(ltAolCallListMaster.getCallListId());
			
			  ltAolCallListMasterUpdate.setCallListId(ltAolCallListMasters.getCallListId());
				ltAolCallListMasterUpdate.setMobileNumber(ltAolCallListMasters.getMobileNumber());
				ltAolCallListMasterUpdate.setStudentName(ltAolCallListMaster.getStudentName());
				ltAolCallListMasterUpdate.setCallSource("Mobile");
				ltAolCallListMasterUpdate.setAddress(ltAolCallListMaster.getAddress());
				ltAolCallListMasterUpdate.setCity(ltAolCallListMaster.getCity());
				ltAolCallListMasterUpdate.setPinCode(ltAolCallListMaster.getPinCode());
				ltAolCallListMasterUpdate.setEmail(ltAolCallListMaster.getEmail());
				ltAolCallListMasterUpdate.setGender(ltAolCallListMaster.getGender());
				ltAolCallListMasterUpdate.setDob(ltAolCallListMaster.getDob());
				/*
				 * if(ltAolCallListMasters.getNotesId() !=null) {
				 * ltAolCallListMasterUpdate.setNotesId(ltAolCallListMasters.getNotesId()); }
				 */
				//ltAolCallListMasterUpdate.setCallDate(ltAolCallListMasters.getCallDate());
				ltAolCallListMasterUpdate.setStatus(ltAolCallListMaster.getStatus());
				ltAolCallListMasterUpdate.setStatusId(ltAolCallListMaster.getStatusId());
				ltAolCallListMasterUpdate.setStatusId(ltAolCallListMaster.getStatusId());
				if(ltAolCallListMasters !=null) {
				ltAolCallListMasterUpdate.setAssignedTo(ltAolCallListMasters.getAssignedTo());
				}
				if(ltAolCallListMasters.getCreatedBy()!=  null) {
					ltAolCallListMasterUpdate.setCreatedBy(ltAolCallListMasters.getCreatedBy());
				}
				ltAolCallListMasterUpdate.setCreationDate(ltAolCallListMasters.getCreationDate());
				ltAolCallListMasterUpdate.setLastUpdatedBy(ltAolCallListMaster.getUserId());
				ltAolCallListMasterUpdate.setLastUpdateLogin(ltAolCallListMaster.getUserId());
				ltAolCallListMasterUpdate.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
				
				ltAolCallListMasterUpdate = ltAolCallListMasterDao.save(ltAolCallListMasterUpdate);
				 
				CallListMasterResponseDto responseDto = ltAolCallListMasterDao.getCallListResponseById(ltAolCallListMasterUpdate.getCallListId());
				
				if(responseDto !=  null) {
					status.setCode(UPDATE_SUCCESSFULLY);
					status.setMessage("Student Updated Successfully.");
					status.setData(responseDto);
					return status;
				}
			}else {
				     
				     ltAolCallListMasterUpdate.setMobileNumber(ltAolCallListMaster.getMobileNumber());
				     ltAolCallListMasterUpdate.setStudentName(ltAolCallListMaster.getStudentName());
			         ltAolCallListMasterUpdate.setCallSource("Mobile");
			         ltAolCallListMasterUpdate.setAddress(ltAolCallListMaster.getAddress());
						ltAolCallListMasterUpdate.setCity(ltAolCallListMaster.getCity());
						ltAolCallListMasterUpdate.setPinCode(ltAolCallListMaster.getPinCode());
						ltAolCallListMasterUpdate.setEmail(ltAolCallListMaster.getEmail());
			         ltAolCallListMasterUpdate.setGender(ltAolCallListMaster.getGender());
			         ltAolCallListMasterUpdate.setDob(ltAolCallListMaster.getDob());
			         ltAolCallListMasterUpdate.setStatus("New Contact");
			         ltAolCallListMasterUpdate.setStatusId(1L);
			        // ltAolCallListMasterUpdate.setNotesId(ltAolCallListMaster.getNotesId());
			        // ltAolCallListMasterUpdate.setCallDate(ltAolCallListMaster.getCallDate());
			         //ltAolCallListMasterUpdate.setAssignedTo(ltAolCallListMaster.getAssignedTo());
			         ltAolCallListMasterUpdate.setCreatedBy(ltAolCallListMaster.getUserId());
			         ltAolCallListMasterUpdate.setCreationDate(UtilsMaster.getCurrentDateTime());
			         ltAolCallListMasterUpdate.setLastUpdateLogin(ltAolCallListMaster.getUserId());
			         ltAolCallListMasterUpdate.setLastUpdatedBy(ltAolCallListMaster.getUserId());
			         ltAolCallListMasterUpdate.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
			     }
			       ltAolCallListMasterUpdate = ltAolCallListMasterDao.save(ltAolCallListMasterUpdate);
			 
			       CallListMasterResponseDto responseDto = ltAolCallListMasterDao.getCallListResponseById(ltAolCallListMasterUpdate.getCallListId());
			       
			 if(responseDto != null) {
				 status.setCode(INSERT_SUCCESSFULLY);
				 status.setMessage("Student Added Successfully.");
				 status.setData(responseDto);
				 return status;
			 }else {
				 status.setCode(INSERT_FAIL);
				 status.setMessage("Unable To Add Student.");
				 status.setData(null);
				 return status;
			 }
		}
		return status;}catch(Exception e) {e.printStackTrace();}
		return null;
		
	}

/*	@Override
	public Status deleteStudentFromQueue() throws ServiceException, BusinessException, IOException {
		try {
		Status status = new Status();
		LtAolCallListMaster ltAolCallListMaster = ltAolCallListMasterDao.deleteStudentFromQueue();
		if(ltAolCallListMaster!= null) 
		{
			status.setMessage("Student Deleted Successfully");
			status.setData(ltAolCallListMaster);
			return status;
		}else {
			   status.setMessage("Delete Fail");
			   return status;
		      }
		}catch(Exception e) {e.printStackTrace();}
		return null;
	}*/
	
	private Status checkDuplicate(LtAolCallListMaster ltAolCallListMaster) throws ServiceException, JSONException, IOException {
		Status status = new Status();
		LtAolCallListMaster ltAolCallListMasters = ltAolCallListMasterDao.getAolCallListByMobileNumber(ltAolCallListMaster.getMobileNumber());
		if (ltAolCallListMasters != null) {
			if (!ltAolCallListMasters.getCallListId().equals(ltAolCallListMaster.getCallListId())) {
				status.setCode(FAIL);
				status.setMessage("Mobile Number Already Exists.");
				return status;
			}		
		}
		status.setCode(SUCCESS);
		return status;
	}
}

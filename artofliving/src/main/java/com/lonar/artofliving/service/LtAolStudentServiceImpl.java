package com.lonar.artofliving.service;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dao.LtAolCallListMasterDao;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtAolCallListMaster;
import com.lonar.artofliving.model.LtAolUsersMaster;
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
				//ltAolCallListMasterUpdate.setAssignedTo(ltAolCallListMaster.getAssignedTo());
				if(ltAolCallListMasters.getCreatedBy()!=  null) {
					ltAolCallListMasterUpdate.setCreatedBy(ltAolCallListMasters.getCreatedBy());
				}
				ltAolCallListMasterUpdate.setCreationDate(ltAolCallListMasters.getCreationDate());
				ltAolCallListMasterUpdate.setLastUpdatedBy(ltAolCallListMaster.getUserId());
				ltAolCallListMasterUpdate.setLastUpdateLogin(ltAolCallListMaster.getUserId());
				ltAolCallListMasterUpdate.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
				
				ltAolCallListMasterUpdate = ltAolCallListMasterDao.save(ltAolCallListMasterUpdate);
				
				if(ltAolCallListMasterUpdate !=  null) {
					status.setCode(UPDATE_SUCCESSFULLY);
					status.setMessage("Student Updated Successfully.");
					status.setData(ltAolCallListMasterUpdate);
					return status;
				}
			}else {
				     status = checkDuplicate(ltAolCallListMaster);
				     if (status.getCode() == FAIL) {
					   return status;
				      }
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
			 
			 if(ltAolCallListMasterUpdate != null) {
				 status.setCode(INSERT_SUCCESSFULLY);
				 status.setMessage("Student Added Successfully.");
				 status.setData(ltAolCallListMasterUpdate);
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

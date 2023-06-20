package com.lonar.artofliving.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dao.LtAolCallListMasterDao;
import com.lonar.artofliving.dto.RequestDto;
import com.lonar.artofliving.model.LtAolCallListMaster;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.utils.UtilsMaster;

@Service
public class LtAolStudentServiceImpl implements LtAolStudentService {

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
			
				ltAolCallListMasterUpdate.setMobileNumber(ltAolCallListMaster.getMobileNumber());
				ltAolCallListMasterUpdate.setStudentName(ltAolCallListMaster.getStudentName());
				ltAolCallListMasterUpdate.setCallSource(ltAolCallListMaster.getCallSource());
				ltAolCallListMasterUpdate.setGender(ltAolCallListMaster.getGender());
				ltAolCallListMasterUpdate.setDob(ltAolCallListMaster.getDob());
				ltAolCallListMasterUpdate.setNotesId(ltAolCallListMaster.getNotesId());
				ltAolCallListMasterUpdate.setCallDate(ltAolCallListMaster.getCallDate());
				ltAolCallListMasterUpdate.setAssignedTo(ltAolCallListMaster.getAssignedTo());
				if(ltAolCallListMasters.getCreatedBy()!=  null) {
					ltAolCallListMasterUpdate.setCreatedBy(ltAolCallListMasters.getCreatedBy());
				}
				ltAolCallListMasterUpdate.setCreationDate(ltAolCallListMasters.getCreationDate());
				ltAolCallListMasterUpdate.setLastUpdatedBy(ltAolCallListMaster.getUserId());
				ltAolCallListMasterUpdate.setLastUpdateLogin(ltAolCallListMaster.getUserId());
				ltAolCallListMasterUpdate.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
				
				ltAolCallListMasterUpdate = ltAolCallListMasterDao.save(ltAolCallListMasterUpdate);
				
				if(ltAolCallListMasterUpdate !=  null) {
					status.setMessage("Student Updated Successfully");
					status.setData(ltAolCallListMasterUpdate);
					return status;
				}
			}else {		
				     ltAolCallListMasterUpdate.setMobileNumber(ltAolCallListMaster.getMobileNumber());
				     ltAolCallListMasterUpdate.setStudentName(ltAolCallListMaster.getStudentName());
			         ltAolCallListMasterUpdate.setCallSource(ltAolCallListMaster.getCallSource());
			         ltAolCallListMasterUpdate.setGender(ltAolCallListMaster.getGender());
			         ltAolCallListMasterUpdate.setDob(ltAolCallListMaster.getDob());
			         ltAolCallListMasterUpdate.setNotesId(ltAolCallListMaster.getNotesId());
			         ltAolCallListMasterUpdate.setCallDate(ltAolCallListMaster.getCallDate());
			         //ltAolCallListMasterUpdate.setAssignedTo(ltAolCallListMaster.getAssignedTo());
			         ltAolCallListMasterUpdate.setCreatedBy(ltAolCallListMaster.getUserId());
			         ltAolCallListMasterUpdate.setCreationDate(UtilsMaster.getCurrentDateTime());
			         ltAolCallListMasterUpdate.setLastUpdateLogin(ltAolCallListMaster.getUserId());
			         ltAolCallListMasterUpdate.setLastUpdatedBy(ltAolCallListMaster.getCreatedBy());
			         ltAolCallListMasterUpdate.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
			     }
			       ltAolCallListMasterUpdate = ltAolCallListMasterDao.save(ltAolCallListMasterUpdate);
			 
			 if(ltAolCallListMasterUpdate != null) {
				 status.setMessage("Student Added Successfully");
				 status.setData(ltAolCallListMasterUpdate);
				 return status;
			 }else {
				 status.setMessage("Unable to add Student");
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
}

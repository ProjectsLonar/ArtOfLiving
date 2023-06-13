package com.lonar.artofliving.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dao.LtAolCallListMasterDao;
import com.lonar.artofliving.dto.ResponseDto;
import com.lonar.artofliving.model.AssignedOrderDto;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtAolCallListMaster;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.utils.UtilsMaster;

@Service
public class LtAolCallListServiceImpl implements LtAolCallListService,CodeMaster {

	@Autowired
	private LtAolCallListMasterDao ltAolCallListMasterDao;
	
	@Override
	public Status getAllCallListById(Long callListId) throws ServiceException, IOException {
		
			Status status =new Status();
			List<ResponseDto> responseDto= ltAolCallListMasterDao.getAllCallListById(callListId);
			if(responseDto!= null) {
				status.setMessage("RECORD_FOUND");
				status.setData(responseDto);
				return status;
			}
		status.setMessage("RECORD_NOT_Found");
		status.setData(null);
		return status;
	}
	
	@Override
	public Status saveAssignedTo(AssignedOrderDto assignedOrderDto) throws ServiceException,IOException, JSONException {
		Status status = new Status();
		List<LtAolCallListMaster> ltAolCallList = new ArrayList<LtAolCallListMaster>();
		//assigned 
		if(assignedOrderDto.getAssignedTo() !=null) {	
			for(String mobileNumberList: assignedOrderDto.getMobileNumber()) {
				LtAolCallListMaster  ltAolCallListMaster = ltAolCallListMasterDao.getAolCallListByMobileNumber(mobileNumberList); 
				if(ltAolCallListMaster !=null) {
					if((ltAolCallListMaster.getAssignedTo() == null) || (ltAolCallListMaster.getAssignedTo() == 0) ) {
						ltAolCallListMaster.setAssignedTo(assignedOrderDto.getAssignedTo());
						ltAolCallListMaster.setLastUpdatedBy(assignedOrderDto.getUserId());
						ltAolCallListMaster.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
						ltAolCallListMaster.setLastUpdateLogin(assignedOrderDto.getUserId());
					}
				}
				LtAolCallListMaster ltAolCallListSaved =ltAolCallListMasterDao.save(ltAolCallListMaster);
						ltAolCallList.add(ltAolCallListSaved);
			}
		}//unassigned
		else {
			
			for(String mobileNumberList: assignedOrderDto.getMobileNumber()) {
				LtAolCallListMaster  ltAolCallListMaster = ltAolCallListMasterDao.getAolCallListByMobileNumber(mobileNumberList); 
				if(ltAolCallListMaster !=null) {
					if((ltAolCallListMaster.getAssignedTo() != null) || (ltAolCallListMaster.getAssignedTo() != 0) ) {
						ltAolCallListMaster.setAssignedTo(null);
						ltAolCallListMaster.setLastUpdatedBy(assignedOrderDto.getUserId());
						ltAolCallListMaster.setLastUpdatedDate(UtilsMaster.getCurrentDateTime());
						ltAolCallListMaster.setLastUpdateLogin(assignedOrderDto.getUserId());
					}
				}
				LtAolCallListMaster ltAolCallListSaved =ltAolCallListMasterDao.save(ltAolCallListMaster);
						ltAolCallList.add(ltAolCallListSaved);
			}
		
		}
		
		if(!ltAolCallList.isEmpty()) {
			status.setCode(UPDATE_SUCCESSFULLY); 
			status.setMessage("Update Successfully.");
			status.setData(ltAolCallList);
			return status;
			}else {
			status.setCode(UPDATE_FAIL);
			status.setMessage("Update Fail.");
			status.setData(null);
			return status;
			}
		
	}
	

}

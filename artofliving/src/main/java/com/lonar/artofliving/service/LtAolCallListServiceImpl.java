package com.lonar.artofliving.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dao.LtAolCallListMasterDao;
import com.lonar.artofliving.dto.ResponseDto;
import com.lonar.artofliving.model.LtAolCallListMaster;
import com.lonar.artofliving.model.Status;

@Service
public class LtAolCallListServiceImpl implements LtAolCallListService {

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
}

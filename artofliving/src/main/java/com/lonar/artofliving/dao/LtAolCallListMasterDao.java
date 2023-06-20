package com.lonar.artofliving.dao;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dto.ResponseDto;
import com.lonar.artofliving.model.LtAolCallListMaster;

public interface LtAolCallListMasterDao {

	
	List<ResponseDto> getAllCallListById(Long callListId) throws ServiceException, BusinessException;

	LtAolCallListMaster save(LtAolCallListMaster ltAolCallListMasterUpdate) throws BusinessException, ServiceException, IOException;

	LtAolCallListMaster getLtAolCallList(Long callListId) throws BusinessException, ServiceException, IOException;
	
	LtAolCallListMaster getAolCallListByMobileNumber(String mobileNumberList) throws ServiceException,IOException, JSONException;

	//LtAolCallListMaster deleteStudentFromQueue()throws ServiceException;

}

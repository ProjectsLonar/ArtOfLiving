package com.lonar.artofliving.dao;

import java.util.List;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dto.ResponseDto;
import com.lonar.artofliving.model.LtAolCallListMaster;

public interface LtAolCallListMasterDao {

	
	List<ResponseDto> getAllCallListById(Long callListId) throws ServiceException, BusinessException;

}

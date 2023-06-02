package com.lonar.artofliving.dao;

import java.util.List;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtMastSmsToken;

public interface LtMastSmsTokenDao {

	List<LtMastSmsToken> saveall(List<LtMastSmsToken> ltMastSmsToken) throws ServiceException;
	
	List<LtMastSmsToken> getBySmsId(Long userId, Long transId) throws ServiceException;
	
	int updateStatus(String status, Long userId, Long transId) throws ServiceException;
}

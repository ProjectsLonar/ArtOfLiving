package com.lonar.artofliving.dao;

import java.io.IOException;
import java.util.List;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtMastFaq;
import com.lonar.artofliving.model.RequestDto;

public interface LtMastAolFAQDao {
	
	public List<LtMastFaq> getAllFAQ(String userType) throws ServiceException,IOException;

	public Long getFaqCount(String userType) throws ServiceException, BusinessException ;
}

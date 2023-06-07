package com.lonar.artofliving.dao;

import java.io.IOException;
import java.util.List;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtMastFaq;

public interface LtMastAolFAQDao {
	
	public List<LtMastFaq> getAllFAQ(String userType) throws ServiceException,IOException;

}

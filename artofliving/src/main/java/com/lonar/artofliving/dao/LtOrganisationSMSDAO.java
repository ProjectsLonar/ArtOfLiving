package com.lonar.artofliving.dao;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtOrganisationSMS;

public interface LtOrganisationSMSDAO {
	
	LtOrganisationSMS getsmsTemplateByType(String templateType)throws ServiceException;

}

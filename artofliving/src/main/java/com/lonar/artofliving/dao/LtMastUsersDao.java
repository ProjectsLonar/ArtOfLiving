package com.lonar.artofliving.dao;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtAolUsersMaster;
import com.lonar.artofliving.model.LtMastLogins;

public interface LtMastUsersDao {

	LtAolUsersMaster getLtMastUsersByMobileNumber(String mobileNumber) throws ServiceException;
	
	LtAolUsersMaster saveLtMastUsers (LtAolUsersMaster ltAolUsersMaster) throws ServiceException;
	
	LtMastLogins  getLoginDetailsByUserId(Long userId) throws ServiceException;
	
	LtAolUsersMaster getUserById(Long userId) throws ServiceException;
}

package com.lonar.artofliving.dao;

import java.io.IOException;
import java.util.List;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtAolRolesMaster;
import com.lonar.artofliving.model.LtAolUsersMaster;
import com.lonar.artofliving.model.LtMastLogins;
import com.lonar.artofliving.model.RequestDto;

public interface LtMastUsersDao {

	LtAolUsersMaster getLtMastUsersByMobileNumber(String mobileNumber) throws ServiceException;
	
	LtAolUsersMaster saveLtMastUsers (LtAolUsersMaster ltAolUsersMaster) throws ServiceException;
	
	LtMastLogins  getLoginDetailsByUserId(Long userId) throws ServiceException;
	
	LtAolUsersMaster getUserById(Long userId) throws ServiceException;
	
	List<LtAolRolesMaster> getallactiveroles( )throws ServiceException,IOException;
	
	public List<LtAolUsersMaster> getallusers(RequestDto requestDto) throws ServiceException;
	
	LtAolUsersMaster deleteUser(Long userId)throws ServiceException;
}

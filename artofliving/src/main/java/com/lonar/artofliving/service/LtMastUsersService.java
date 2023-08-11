package com.lonar.artofliving.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtAolUsersMaster;
import com.lonar.artofliving.model.LtMastLogins;
import com.lonar.artofliving.model.MobileSupportedVersionRequestDto;
import com.lonar.artofliving.model.MobileSupportedVersionResponseDto;
import com.lonar.artofliving.model.RequestDto;
import com.lonar.artofliving.model.ResponceEntity;
import com.lonar.artofliving.model.Status;

public interface LtMastUsersService {

	Status sendOTPToUser(String mobileNumber) throws ServiceException, IOException;
	
	ResponceEntity login(LtMastLogins ltMastLogins, HttpServletResponse response)throws ServiceException;
	
	Status getallactiveroles() throws ServiceException, IOException;
	
	Status getallusers(RequestDto requestDto) throws ServiceException, IOException ;
	
	Status deleteUser(Long userId) throws ServiceException, IOException;
	
	Status saveUserDetails(LtAolUsersMaster ltAolUsersMaster) throws ServiceException,IOException;
	
	MobileSupportedVersionResponseDto isMobileSupportedVersion(MobileSupportedVersionRequestDto requestDto)throws ServiceException;
}

package com.lonar.artofliving.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.service.LtMastUsersService;

@RestController
@RequestMapping(value = "/aolusers")
public class LtMastUsersController implements CodeMaster {
	
	@Autowired
	LtMastUsersService ltMastUsersService;

	@RequestMapping(value = "/sendOTP/{mobileNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> sendOTP(@PathVariable("mobileNumber") String mobileNumber)
			throws ServiceException, IOException {
		try {
			return new ResponseEntity<Status>(ltMastUsersService.sendOTPToUser(mobileNumber), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

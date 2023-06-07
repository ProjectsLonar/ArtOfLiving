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

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.service.LtMastAolFAQService;

@RestController
@RequestMapping(value = "/aolfaq")
public class LtMastAolFAQController implements CodeMaster{
	@Autowired
	LtMastAolFAQService ltMastAolFAQService;
	
	@RequestMapping(value = "/getaolfaqs/{userType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> findFAQOrgId(@PathVariable("userType") String userType) throws ServiceException, IOException {
		try {
return new ResponseEntity<Status>(ltMastAolFAQService.getAll(userType), HttpStatus.OK);
		} catch (Exception e) {

			throw new BusinessException(INTERNAL_SERVER_ERROR, "FAQ not found", e);
		}
	}
	
}

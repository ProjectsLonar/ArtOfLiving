package com.lonar.artofliving.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.CodeMaster;

import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.service.LtAolBannersService;


@RestController
@RequestMapping(value = "/aolbanners")
public class LtAolBannersController implements CodeMaster {
	
	@Autowired
	LtAolBannersService ltAolBannersService;
	
	@Autowired
	public Environment env;
	
	@RequestMapping(value = "/getBanners", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getBanners()
			throws ServiceException, IOException {  
		return new ResponseEntity<Status>(ltAolBannersService.getBanners(), HttpStatus.OK);
	}
	
}
		

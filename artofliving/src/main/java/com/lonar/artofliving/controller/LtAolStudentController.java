package com.lonar.artofliving.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dto.RequestDto;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtAolCallListMaster;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.service.LtAolStudentService;

@RestController
@RequestMapping(value="/student")
public class LtAolStudentController implements CodeMaster  {

	@Autowired
	LtAolStudentService ltAolStudentService;
	
		
	@RequestMapping(value= "/addStudent", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> addStudent(@RequestBody LtAolCallListMaster ltAolCallListMaster)throws BusinessException, ServiceException{
		
		    try {
		      return new ResponseEntity<Status>(ltAolStudentService.addStudent(ltAolCallListMaster), HttpStatus.OK);
	        }catch(Exception e) 
		          {
		            throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
	              } 
	}
	
/*	@RequestMapping(value= "/deleteStudentFromQueue", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> deleteStudentFromQueue()throws BusinessException, ServiceException{
		try {
               return new ResponseEntity<Status>(ltAolStudentService.deleteStudentFromQueue(), HttpStatus.OK);			
		}catch(Exception e) {
			       throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		          }
	}*/
	
}

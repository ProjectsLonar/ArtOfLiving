package com.lonar.artofliving.controller;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.AssignedOrderDto;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.RequestDto;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.service.LtAolCallListService;

@RestController
@RequestMapping(value="/aolcallList")
public class LtAolCallListMastController implements CodeMaster {
	
	@Autowired
	private LtAolCallListService ltAolCallListMasterService;
	
	@RequestMapping(value="/getAllCallListById", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<Status> getAllCallListById(@RequestBody RequestDto requestDto) 
			throws ServiceException, BusinessException{
		try {
			return new ResponseEntity<Status>(ltAolCallListMasterService.getAllCallListById(requestDto), HttpStatus.OK);
		}catch(Exception e){
			throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
	}
	
	@RequestMapping(value = "/saveAssignedTo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveAssignedToVersion1(@RequestBody AssignedOrderDto assignedOrderDto) throws ServiceException, IOException, JSONException  {
		return new ResponseEntity<Status>(ltAolCallListMasterService.saveAssignedTo(assignedOrderDto), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getMyQueueList", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<Status> getMyQueueList(@RequestBody RequestDto requestDto) 
			throws ServiceException, BusinessException{
		try {
			return new ResponseEntity<Status>(ltAolCallListMasterService.getMyQueueList(requestDto), HttpStatus.OK);
		}catch(Exception e){
			throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
	}
}
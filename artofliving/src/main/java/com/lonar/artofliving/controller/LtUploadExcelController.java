package com.lonar.artofliving.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.Status;
import com.lonar.artofliving.service.LtAolCallListService;

public class LtUploadExcelController implements CodeMaster {
	
	@Autowired
	private LtAolCallListService ltAolCallListMasterService;
	
	@RequestMapping(value = "/uploadExcelForMasterCallingList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> Post(@RequestParam("userId") Long userId,@RequestParam("file") MultipartFile[] file) throws ServiceException {
		try {
			if (file.length > 0) {
				return new ResponseEntity<Status>(ltAolCallListMasterService.uploadMasterCallingList(userId,file[0]), HttpStatus.OK);
				
			}
			else
			{
				return new ResponseEntity<Status>(ltAolCallListMasterService.uploadMasterCallingList(userId,null), HttpStatus.OK);
					
			}
				} catch (Exception e) {

			throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
	}


}

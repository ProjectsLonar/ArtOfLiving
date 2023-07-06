package com.lonar.artofliving.service;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.AssignedOrderDto;
import com.lonar.artofliving.model.LtAolCallNotes;
import com.lonar.artofliving.model.LtAolUserProducts;
import com.lonar.artofliving.model.RequestDto;
import com.lonar.artofliving.model.Status;

public interface LtAolCallListService {

	
	Status getAllCallListById(RequestDto requestDto) throws ServiceException, IOException;
	
	Status saveAssignedTo(AssignedOrderDto assignedOrderDto) throws ServiceException, IOException, JSONException;
	
	public Status uploadMasterCallingList(Long userId,MultipartFile file) throws ServiceException, IOException;
	
	Status getMyQueueList(RequestDto requestDto) throws ServiceException, IOException;
	
	Status getAllCourses(RequestDto requestDto) throws ServiceException, IOException;
	
	Status saveCourseDetails (LtAolUserProducts ltAolUserProducts)throws ServiceException, IOException;
	
	Status saveNote (LtAolCallNotes ltAolCallNotes)throws ServiceException, IOException;
	
	Status getAllStatus() throws ServiceException, IOException;

}

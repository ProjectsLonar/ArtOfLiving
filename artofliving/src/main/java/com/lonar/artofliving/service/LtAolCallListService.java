package com.lonar.artofliving.service;

import java.io.IOException;

import org.json.JSONException;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.AssignedOrderDto;
import com.lonar.artofliving.model.Status;

public interface LtAolCallListService {

	
	Status getAllCallListById(Long callListId) throws ServiceException, IOException;
	
	Status saveAssignedTo(AssignedOrderDto assignedOrderDto) throws ServiceException, IOException, JSONException;

}

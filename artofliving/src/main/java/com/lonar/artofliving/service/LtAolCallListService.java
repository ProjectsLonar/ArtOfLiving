package com.lonar.artofliving.service;

import java.io.IOException;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.Status;

public interface LtAolCallListService {

	
	Status getAllCallListById(Long callListId) throws ServiceException, IOException;

}

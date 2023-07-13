package com.lonar.artofliving.service;

import java.io.IOException;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;

import com.lonar.artofliving.model.LtAolCallListMaster;
import com.lonar.artofliving.model.Status;

public interface LtAolStudentService {

	Status addStudent(LtAolCallListMaster ltAolCallListMaster)throws ServiceException,BusinessException, IOException;

	//Status deleteStudentFromQueue()throws ServiceException, BusinessException, IOException;

}

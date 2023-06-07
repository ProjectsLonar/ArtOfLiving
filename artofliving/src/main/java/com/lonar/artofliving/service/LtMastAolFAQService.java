package com.lonar.artofliving.service;

import java.io.IOException;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.Status;

public interface LtMastAolFAQService {

	Status getAll(String userType) throws ServiceException, IOException;
}

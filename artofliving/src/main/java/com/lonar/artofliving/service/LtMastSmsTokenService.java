package com.lonar.artofliving.service;

import java.io.IOException;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.Status;

public interface LtMastSmsTokenService {

	Status sendSms(Long userId, Long transId) throws ServiceException,IOException;
}

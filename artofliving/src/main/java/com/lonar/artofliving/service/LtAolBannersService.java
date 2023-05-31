package com.lonar.artofliving.service;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.Status;

public interface LtAolBannersService {

	Status getBanners() throws ServiceException;
}

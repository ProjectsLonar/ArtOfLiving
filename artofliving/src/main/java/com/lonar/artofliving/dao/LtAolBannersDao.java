package com.lonar.artofliving.dao;

import java.util.List;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtAolBanners;

public interface LtAolBannersDao {

	List<LtAolBanners> getBanners() throws ServiceException; 
}

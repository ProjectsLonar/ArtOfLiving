package com.lonar.artofliving.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dao.LtAolBannersDao;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtAolBanners;
import com.lonar.artofliving.model.Status;

@Service
public class LtAolBannersServiceImpl implements LtAolBannersService, CodeMaster {

	@Autowired
	LtAolBannersDao ltAolBannersDao;
	
	@Override
	public Status getBanners() throws ServiceException{
		Status status = new Status();
		List<LtAolBanners> ltAolBannerList = ltAolBannersDao.getBanners( );
		if (!ltAolBannerList.isEmpty()) {
			status.setMessage("RECORD_FOUND");
			status.setData(ltAolBannerList);

		} else {
			status.setMessage("RECORD_Not_FOUND");
			status.setData(ltAolBannerList);
		}
		return status;
	}
}

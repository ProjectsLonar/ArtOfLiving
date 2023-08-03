package com.lonar.artofliving.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dao.LtMastAolFAQDao;
import com.lonar.artofliving.model.AboutUsAndFaqDetailsDto;
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtMastAboutUs;
import com.lonar.artofliving.model.LtMastFaq;
import com.lonar.artofliving.model.Status;

@Service
public class LtMastAolFAQServiceImpl implements LtMastAolFAQService,CodeMaster {
	
	@Autowired
	LtMastAolFAQDao ltMastAolFAQDao;

	@Override
	public Status getAll(String userType) throws ServiceException, IOException {
		Status status = new Status();

		AboutUsAndFaqDetailsDto aboutUsFaqDto = new AboutUsAndFaqDetailsDto();
		
		
		List<LtMastFaq> list = ltMastAolFAQDao.getAllFAQ(userType);
		
		if (!list.isEmpty()) {
			aboutUsFaqDto.setLtMastFAQlist(list);
		}

		List<LtMastAboutUs> aboutUsList = ltMastAolFAQDao.getAllAboutUs();

		if (!aboutUsList.isEmpty()) {
			aboutUsFaqDto.setLtMastAboutUs(aboutUsList.get(0));
		}
		
		Long totalCount = ltMastAolFAQDao.getFaqCount(userType);
		//System.out.println("faqlist"+list);
		if (list.isEmpty()) {
			status.setCode(FAIL);
			status.setMessage("Records Not Found.");
			
			return status;
		}
		status.setCode(SUCCESS);
		status.setMessage("Records Found.");
		status.setData(aboutUsFaqDto);
		status.setRecordCount((long)list.size());
		status.setTotalCount(totalCount);

		return status;
	}
}

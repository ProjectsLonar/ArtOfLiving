package com.lonar.artofliving.dao;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dto.ResponseDto;
import com.lonar.artofliving.model.LtAolCallListMaster;
import com.lonar.artofliving.model.LtAolCallNotes;
import com.lonar.artofliving.model.LtAolProductMaster;
import com.lonar.artofliving.model.LtAolUserProducts;
import com.lonar.artofliving.model.RequestDto;

public interface LtAolCallListMasterDao {

	
	List<ResponseDto> getAllCallListById(RequestDto requestDto) throws ServiceException, BusinessException;

	LtAolCallListMaster save(LtAolCallListMaster ltAolCallListMasterUpdate) throws BusinessException, ServiceException, IOException;

	LtAolCallListMaster getLtAolCallList(Long callListId) throws BusinessException, ServiceException, IOException;
	
	LtAolCallListMaster getAolCallListByMobileNumber(String mobileNumberList) throws ServiceException,IOException, JSONException;


	List<LtAolCallListMaster> saveAll (List<LtAolCallListMaster> ltAolCallListMaster) throws ServiceException, IOException;
	
	List<ResponseDto> getMyQueueList(RequestDto requestDto) throws ServiceException, BusinessException;
	
	List<LtAolProductMaster> getAllCourses(RequestDto requestDto) throws ServiceException, BusinessException;
	
	LtAolUserProducts saveCourseDetails (LtAolUserProducts ltAolUserProducts)throws ServiceException, IOException;
	
	LtAolUserProducts getCourseListAgainstId (Long userCourseId)throws ServiceException, IOException;
	
	LtAolCallNotes saveNote (LtAolCallNotes ltAolCallNotes)throws ServiceException, IOException;
	
	LtAolCallNotes getNoteAgainstId (Long callNoteId)throws ServiceException, IOException;
	
	List<LtAolUserProducts> getAllCoursesAgainstListId(Long callListId) throws ServiceException, BusinessException;
	
	Long getCallListCount(RequestDto requestDto) throws ServiceException, BusinessException;
	
	Long getMyQueueListCount(RequestDto requestDto) throws ServiceException, BusinessException;
}

package com.lonar.artofliving.dao;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.dto.ResponseDto;
import com.lonar.artofliving.model.LtAolCallListMaster;
import com.lonar.artofliving.model.LtAolProductMaster;
import com.lonar.artofliving.model.RequestDto;
import com.lonar.artofliving.repository.LtAolCallListMasterRepository;

@Repository
@PropertySource(value= "classpath:queries/callListMasterQueries.properties", ignoreResourceNotFound = true)
public class LtAolCallListMasterDaoImpl implements LtAolCallListMasterDao{

	@Autowired
	private LtAolCallListMasterRepository ltAolCallListMasterRepository;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Override
	public List<ResponseDto> getAllCallListById(RequestDto requestDto) throws ServiceException, BusinessException {
		
		if (requestDto.getLimit() == 0) {
			requestDto.setLimit(Integer.parseInt(env.getProperty("limit")));
		}

		String searchField = null;
		if (requestDto.getSearchfield() != null) {
			searchField = "%" + requestDto.getSearchfield().toUpperCase() + "%";
		}
		
		
		String query= env.getProperty("getAllCallListById");
		
		List<ResponseDto> list= jdbcTemplate.query(query, new Object[] {requestDto.getCallListId(),searchField, requestDto.getLimit(), requestDto.getOffset() },
				new BeanPropertyRowMapper<ResponseDto>(ResponseDto.class));
		
		if(!list.isEmpty()) {
			return list;
		}
		return list;
	}

	@Override
	public LtAolCallListMaster save(LtAolCallListMaster ltAolCallListMasterUpdate)throws BusinessException, ServiceException, IOException {

		return ltAolCallListMasterRepository.save(ltAolCallListMasterUpdate);
	}

	@Override
	public LtAolCallListMaster getLtAolCallList(Long callListId)
			throws BusinessException, ServiceException, IOException {
		
		String query= env.getProperty("getLtAolCallList");
		List<LtAolCallListMaster> list =jdbcTemplate.query(query, new Object[] {callListId}, 
				new BeanPropertyRowMapper<LtAolCallListMaster>(LtAolCallListMaster.class));
		if(!list.isEmpty()) {
			return list.get(0);
		}
			return null;
	}
	
	
	@Override
	public LtAolCallListMaster getAolCallListByMobileNumber(String mobileNumberList) throws ServiceException,IOException, JSONException{
		
		String query= env.getProperty("getAolCallListByMobileNumber");
		List<LtAolCallListMaster> list =jdbcTemplate.query(query, new Object[] {mobileNumberList}, 
				new BeanPropertyRowMapper<LtAolCallListMaster>(LtAolCallListMaster.class));
		if(!list.isEmpty()) {
			return list.get(0);
		}
			return null;
	}

	/*@Override
	public LtAolCallListMaster deleteStudentFromQueue() throws ServiceException {
		
		return ltAolCallListMasterRepository.deleteById();
	}*/
	
	@Override
	public List<LtAolCallListMaster> saveAll (List<LtAolCallListMaster> ltAolCallListMaster) throws ServiceException, IOException {
		// TODO Auto-generated method stub
		List<LtAolCallListMaster> list = (List<LtAolCallListMaster>) ltAolCallListMasterRepository.saveAll(ltAolCallListMaster);
		return list;
	}

	@Override
	public List<ResponseDto> getMyQueueList(RequestDto requestDto) throws ServiceException, BusinessException{
		if (requestDto.getLimit() == 0) {
			requestDto.setLimit(Integer.parseInt(env.getProperty("limit")));
		}

		String searchField = null;
		if (requestDto.getSearchfield() != null) {
			searchField = "%" + requestDto.getSearchfield().toUpperCase() + "%";
		}

		String query = env.getProperty("getMyQueueList");
		List<ResponseDto> ltMastMyQueueList = jdbcTemplate.query(query,
				new Object[] {requestDto.getCallListId(),requestDto.getUserId(),searchField, requestDto.getLimit(), requestDto.getOffset() },
				new BeanPropertyRowMapper<ResponseDto>(ResponseDto.class));
		if (!ltMastMyQueueList.isEmpty()) {
			return ltMastMyQueueList;
		}
		return null;
	}
	
	
	@Override
	public List<LtAolProductMaster> getAllCourses(RequestDto requestDto) throws ServiceException, BusinessException{
		if (requestDto.getLimit() == 0) {
			requestDto.setLimit(Integer.parseInt(env.getProperty("limit")));
		}

		String searchField = null;
		if (requestDto.getSearchfield() != null) {
			searchField = "%" + requestDto.getSearchfield().toUpperCase() + "%";
		}

		String query = env.getProperty("getAllCourses");
		List<LtAolProductMaster> allCoursesList = jdbcTemplate.query(query,
				new Object[] {searchField, requestDto.getLimit(), requestDto.getOffset() },
				new BeanPropertyRowMapper<LtAolProductMaster>(LtAolProductMaster.class));
		if (!allCoursesList.isEmpty()) {
			return allCoursesList;
		}
		return null;
	}
}
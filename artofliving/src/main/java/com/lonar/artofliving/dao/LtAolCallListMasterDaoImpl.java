package com.lonar.artofliving.dao;

import java.util.List;

import javax.sql.DataSource;

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
	public List<ResponseDto> getAllCallListById(Long callListId) throws ServiceException, BusinessException {
		
		String query= env.getProperty("getAllCallListById");
		//List<LtAolCallListMaster> list= jdbcTemplate.query(query, new Object[] {callListId},
			//	new BeanPropertyRowMapper<LtAolCallListMaster>(LtAolCallListMaster.class));
		List<ResponseDto> list= jdbcTemplate.query(query, new Object[] {callListId},
				new BeanPropertyRowMapper<ResponseDto>(ResponseDto.class));
		
		if(!list.isEmpty()) {
			return list;
		}
		return list;
	}
	
}
package com.lonar.artofliving.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lonar.artofliving.common.BusinessException;
import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtAolRolesMaster;
import com.lonar.artofliving.model.LtAolUsersMaster;
import com.lonar.artofliving.model.LtMastLogins;
import com.lonar.artofliving.model.RequestDto;
import com.lonar.artofliving.repository.LtAolMastRepository;

@Repository
@PropertySource(value = "classpath:queries/aolUsersQueries.properties", ignoreResourceNotFound = true)
public class LtMastUsersDaoImpl implements LtMastUsersDao {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Environment env;
	
	@Autowired 
	LtAolMastRepository  ltAolMastRepository;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	
	@Override
	public LtAolUsersMaster getLtMastUsersByMobileNumber(String mobileNumber) throws ServiceException{
		String query = env.getProperty("getLtMastUsersByMobileNumber");
		List<LtAolUsersMaster> list = jdbcTemplate.query(query, new Object[] { mobileNumber.trim() },
				new BeanPropertyRowMapper<LtAolUsersMaster>(LtAolUsersMaster.class));
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}
	
	@Override
	public LtAolUsersMaster saveLtMastUsers (LtAolUsersMaster ltAolUsersMaster) throws ServiceException{
		return ltAolMastRepository.save(ltAolUsersMaster);
	}
	
	@Override
	public LtMastLogins getLoginDetailsByUserId(Long userId) throws ServiceException {
		String query = env.getProperty("getLoginDetailsByUserId");
		List<LtMastLogins> list = jdbcTemplate.query(query, new Object[] { userId },
				new BeanPropertyRowMapper<LtMastLogins>(LtMastLogins.class));
		if (!list.isEmpty())
			return list.get(0);
		else
			return null;
	}
	
	@Override
	public LtAolUsersMaster getUserById(Long userId) throws ServiceException {
		Optional<LtAolUsersMaster> ltMastUsers = ltAolMastRepository.findById(userId);
		if (ltMastUsers.isPresent()) {
			return ltMastUsers.get();
		}
		return null;
	}
	
	@Override
	public List<LtAolRolesMaster> getallactiveroles( )throws ServiceException,IOException{
		String query = env.getProperty("getallactiveroles");
		List<LtAolRolesMaster> list = jdbcTemplate.query(query, new Object[] {},
				new BeanPropertyRowMapper<LtAolRolesMaster>(LtAolRolesMaster.class));
		if (!list.isEmpty())
			return list;
		else
			return null;
	}
	
	@Override
	public List<LtAolUsersMaster> getallusers(RequestDto requestDto) throws ServiceException {
		
		if (requestDto.getLimit() == 0) {
			requestDto.setLimit(Integer.parseInt(env.getProperty("limit")));
		}

		String searchField = null;
		if (requestDto.getSearchfield() != null) {
			searchField = "%" + requestDto.getSearchfield().toUpperCase() + "%";
		}
		
		String status = null;
		if (requestDto.getStatus() != null) {
			status = "%" + requestDto.getStatus().toUpperCase() + "%";
		}

		String query = env.getProperty("getallusers");
		List<LtAolUsersMaster> ltMastUserList = jdbcTemplate.query(query,
				new Object[] {requestDto.getUserId(),searchField,status, requestDto.getLimit(), requestDto.getOffset() },
				new BeanPropertyRowMapper<LtAolUsersMaster>(LtAolUsersMaster.class));
		if (!ltMastUserList.isEmpty()) {
			return ltMastUserList;
		}
		return null;
	}
	
	@Override
	public LtAolUsersMaster deleteUser(Long userId) throws ServiceException {
	   
		String query = env.getProperty("deleteUser");
		int record =jdbcTemplate.update(query, userId, userId, userId);
		if(record>0) 
		 { 
		   return getUserById(userId);
		 }
		return null;
	}
	
	@Override
	public Long getAllActiveRolesCount( ) throws ServiceException, BusinessException{
		Long totalCount;
		String sql = env.getProperty("getAllActiveRolesCount");
		totalCount = jdbcTemplate.queryForObject(sql, new Object[] { }, Long.class);
		return totalCount;
	}

	
	@Override
	public Long getAllUsersCount( RequestDto requestDto) throws ServiceException, BusinessException{
		Long totalCount;
		
		String searchField = null;
		if (requestDto.getSearchfield() != null) {
			searchField = "%" + requestDto.getSearchfield().toUpperCase() + "%";
		}
		
		String status = null;
		if (requestDto.getStatus() != null) {
			status = "%" + requestDto.getStatus().toUpperCase() + "%";
		}

		String sql = env.getProperty("getAllUsersCount");
		totalCount = jdbcTemplate.queryForObject(sql, new Object[] {requestDto.getUserId(),searchField,status }, Long.class);
		return totalCount;
	}
}

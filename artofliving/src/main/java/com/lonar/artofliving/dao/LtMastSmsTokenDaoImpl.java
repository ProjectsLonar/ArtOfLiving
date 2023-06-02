package com.lonar.artofliving.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtMastSmsToken;
import com.lonar.artofliving.repository.LtMastSmsTokenRepository;

@Repository
public class LtMastSmsTokenDaoImpl implements LtMastSmsTokenDao {
	
	@Autowired
	LtMastSmsTokenRepository ltMastSmsTokenRepository;
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Environment env;
	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	
	@Override
	public List<LtMastSmsToken> saveall(List<LtMastSmsToken> ltMastSmsTokenList) throws ServiceException {
		return (List<LtMastSmsToken>) ltMastSmsTokenRepository.saveAll(ltMastSmsTokenList);
	}

	
	@Override
	public List<LtMastSmsToken> getBySmsId(Long userId, Long transId) throws ServiceException {
		String query = "SELECT * FROM LT_MAST_SMS_TOKENS WHERE ( SMS_STATUS = 'SENDING' OR SMS_STATUS = 'FAIL TO SEND' ) "
				+ " AND COALESCE(USER_ID,-1) =  COALESCE(?,COALESCE(USER_ID,-1))  AND Transaction_id = ? ";

		List<LtMastSmsToken> ltMastSmsTokenList = jdbcTemplate.query(query, new Object[] { userId, transId },
				new BeanPropertyRowMapper<LtMastSmsToken>(LtMastSmsToken.class));
		return ltMastSmsTokenList;
	}
	
	@Override
	public int updateStatus(String status, Long userId, Long transId) throws ServiceException {
		int result = 0;
		result = jdbcTemplate.update(" UPDATE LT_MAST_SMS_TOKENS SET SMS_STATUS = ?  " + "  WHERE   Transaction_id = ? ",
				status.toUpperCase(), transId);
		return result;

	}
}

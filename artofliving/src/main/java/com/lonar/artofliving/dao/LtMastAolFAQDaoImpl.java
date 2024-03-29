package com.lonar.artofliving.dao;

import java.io.IOException;
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
import com.lonar.artofliving.model.CodeMaster;
import com.lonar.artofliving.model.LtMastAboutUs;
import com.lonar.artofliving.model.LtMastFaq;

@Repository
@PropertySource(value = "classpath:queries/ltAolFaqQueries.properties", ignoreResourceNotFound = true)
public class LtMastAolFAQDaoImpl implements LtMastAolFAQDao,CodeMaster {

	@Autowired
	private Environment env;
    @Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		// TODO Auto-generated method stub
		return jdbcTemplate;
	}

	@Override
	public List<LtMastFaq> getAllFAQ(String userType) throws ServiceException,IOException {
		String query = env.getProperty("findFAQbyUsertype");
		System.out.println("in dao");
		List<LtMastFaq> ltMastFAQlist =jdbcTemplate.query(query, new Object[] {"%"+userType.toUpperCase()+"%" },
				new BeanPropertyRowMapper<LtMastFaq>(LtMastFaq.class));
		System.out.println("after dao query"+ltMastFAQlist);
		if(!ltMastFAQlist.isEmpty()) {
			return ltMastFAQlist;
		}
		return null;
		
	}
	
	@Override
	public Long getFaqCount(String userType) throws ServiceException, BusinessException{
		Long totalCount;
		String sql = env.getProperty("getFaqCount");
		totalCount = jdbcTemplate.queryForObject(sql, new Object[] {"%"+userType.toUpperCase()+"%"}, Long.class);
		return totalCount;
	}
	
	@Override
	public List<LtMastAboutUs> getAllAboutUs() throws ServiceException {

		String query = env.getProperty("getAllAboutUs");
		System.out.println("in dao");
		List<LtMastAboutUs> ltMastlist =jdbcTemplate.query(query, new Object[] { },
				new BeanPropertyRowMapper<LtMastAboutUs>(LtMastAboutUs.class));
		System.out.println("after dao query"+ltMastlist);
		if(!ltMastlist.isEmpty()) {
			return ltMastlist;
		}
		return null;

	}
	
}

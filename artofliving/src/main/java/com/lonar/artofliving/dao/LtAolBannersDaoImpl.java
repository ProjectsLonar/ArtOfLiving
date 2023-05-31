package com.lonar.artofliving.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtAolBanners;

@Repository
@PropertySource(value = "classpath:queries/aolBannerQueries.properties", ignoreResourceNotFound = true)
public class LtAolBannersDaoImpl implements LtAolBannersDao {

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
	public List<LtAolBanners> getBanners() throws ServiceException{
		
		String query = env.getProperty("getBanners");
		List<LtAolBanners> list = jdbcTemplate.query(query, new Object[] {},
				new BeanPropertyRowMapper<LtAolBanners>(LtAolBanners.class));
		if (!list.isEmpty())
			return list;
		else
			return null;
	}
}

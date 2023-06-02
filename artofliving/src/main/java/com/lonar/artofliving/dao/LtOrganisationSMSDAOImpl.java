package com.lonar.artofliving.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lonar.artofliving.common.ServiceException;
import com.lonar.artofliving.model.LtOrganisationSMS;

@Repository
public class LtOrganisationSMSDAOImpl implements LtOrganisationSMSDAO {
	
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
	public LtOrganisationSMS getsmsTemplateByType(String templateType) throws ServiceException {
		String query = "SELECT orgsms.* FROM lt_organisation_sms orgsms where orgsms.template_type = ?";
		List<LtOrganisationSMS> ltOrganisationSMSList = jdbcTemplate.query(query, new Object[] {templateType},
				new BeanPropertyRowMapper<LtOrganisationSMS>(LtOrganisationSMS.class));
		if(!ltOrganisationSMSList.isEmpty()) {
			return ltOrganisationSMSList.get(0);
		}
		return null;
	}
}

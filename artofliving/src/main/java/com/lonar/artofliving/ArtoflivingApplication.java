package com.lonar.artofliving;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
@ComponentScan(basePackages = { "com.lonar.*" })
@PropertySource({ "classpath:persistence.properties" })
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
@Configuration
//@EnableSwagger2
//@EnableWebMvc
public class ArtoflivingApplication extends SpringBootServletInitializer{
	
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(ArtoflivingApplication.class, args);
	}

	
	
	@Bean("datasource")
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("db.driver"));
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		return dataSource;
	}
	
	
	 @Bean("resttemplate")
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }
	 
	 /*@Bean
	public Docket aolApi() {
		 return new Docket(DocumentationType.SWAGGER_2).select()
				 .apis(RequestHandlerSelectors.basePackage("com.lonar.artofliving.controller"))
				 .build();
	 }*/
}

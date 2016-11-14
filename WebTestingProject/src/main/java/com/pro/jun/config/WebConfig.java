package com.pro.jun.config;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
// @EnableWebMvc
@ComponentScan(basePackages = "com.pro.jun.*")
// @PropertySource("/WEB-INF/config/properties-config.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		log.info("[ RESOURCES MAPPING ]");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		configurer.enable();
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
		c.setLocation(new ClassPathResource("/com/pro/jun/properties-config.properties"));
		return c;
	}

	@Bean
	public DbProperties properties() {
		return new DbProperties();
	}

	@Bean /* (destroyMethod = "close") */
	public OracleDataSource dataSource(DbProperties properties) throws SQLException {
		log.info("[ DB URL ] = " + properties.getUrl());
		/* DriverManagerDataSource dataSource = null; dataSource = new DriverManagerDataSource();
		 * dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		 * dataSource.setDriverClassName("oracle.jdbc.OracleDriver"); dataSource.setUsername("SCOTT");
		 * dataSource.setPassword("1234"); */

		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setURL(properties.getUrl());
		dataSource.setUser(properties.getUserName());
		dataSource.setPassword(properties.getPw());
		dataSource.setDataSourceName("ds");
		dataSource.setDriverType(properties.getDriverName());

		log.info("[ ORACLE DATABASE CONNECT ]");

		return dataSource;
	}
}

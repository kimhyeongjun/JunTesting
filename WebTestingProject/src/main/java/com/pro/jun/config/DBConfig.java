package com.pro.jun.config;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
public class DBConfig {

	@Autowired
	ApplicationContext applicationContext;

	private Log log = LogFactory.getLog(this.getClass());

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(OracleDataSource dataSource, ApplicationContext applicationContext) {

		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

		try {
			factoryBean.setDataSource(dataSource);
			// factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/configuration.xml"));
			factoryBean.setMapperLocations(applicationContext.getResources("classpath:/com/pro/jun/mybatis/*.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("sqlSessionFactory ERROR", e);
			return null;
		}

		return factoryBean;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}

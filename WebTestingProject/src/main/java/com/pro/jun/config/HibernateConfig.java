package com.pro.jun.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import oracle.jdbc.pool.OracleDataSource;

// @Configuration
public class HibernateConfig {

/*	@Autowired
	OracleDataSource dataSource;

	private Log log = LogFactory.getLog(this.getClass());

	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTanslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws Throwable {
		HibernateTransactionManager txMgr = new HibernateTransactionManager();
		txMgr.setSessionFactory(sessionFactory().getObject());
		return txMgr;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() throws IOException {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(dataSource);
		localSessionFactoryBean.setMappingResources("/com/pro/jun/hibernate/hibernate.hbm.xml");
		java.util.Properties prop = new java.util.Properties();
		prop.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		localSessionFactoryBean.setHibernateProperties(prop);
		return localSessionFactoryBean;
	}*/
}

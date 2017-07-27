package com.pro.jun.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import oracle.jdbc.pool.OracleDataSource;

/*@Configuration
@EnableTransactionManagement*/
public class JPAConfig {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Bean(destroyMethod = "close")
	public OracleDataSource dataSource(DbProperties properties) throws SQLException {
		LOGGER.info("[ DB URL ] = " + properties.getUrl());

		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setURL(properties.getUrl());
		dataSource.setUser(properties.getUserName());
		dataSource.setPassword(properties.getPw());
		dataSource.setDataSourceName("ds");
		dataSource.setDriverType(properties.getDriverName());

		LOGGER.info("[ ORACLE DATABASE CONNECT ]");

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(OracleDataSource dataSource) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan("com.pro.jun.vo");
		
		// persistence 설정
		Properties prop = new Properties();
		prop.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		prop.setProperty("hibernate.show_sql", "true");
		
		// 각 구현제의 프로퍼티 확장 및 설정
		JpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(jpaAdapter);
		em.setJpaProperties(prop);
		return em;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager jpaTransManager = new JpaTransactionManager();
		jpaTransManager.setEntityManagerFactory(emf);
		return jpaTransManager;
	}

}

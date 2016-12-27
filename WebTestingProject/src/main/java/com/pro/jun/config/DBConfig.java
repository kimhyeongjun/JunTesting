package com.pro.jun.config;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import com.pro.jun.dao.BoardDao;
import com.pro.jun.utill.Board;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
@EnableTransactionManagement
public class DBConfig {

	@Autowired
	ApplicationContext applicationContext;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Bean(destroyMethod = "close")
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

	/* @Bean public PersistenceExceptionTranslationPostProcessor postProcessor() { return new
	 * PersistenceExceptionTranslationPostProcessor(); }
	 * 
	 * @Bean public PlatformTransactionManager transactionManager(OracleDataSource dataSource) {
	 * DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
	 * transactionManager.setDataSource(dataSource); return transactionManager; } */

	/* @Bean public TransactionTemplate transactionTemplate(DataSourceTransactionManager manager) { TransactionTemplate
	 * p = new TransactionTemplate(); p.setTransactionManager(manager); return p; } */

	@Bean
	public DataSourceTransactionManager manager(OracleDataSource dataSource) {
		DataSourceTransactionManager manager = new DataSourceTransactionManager();
		manager.setDataSource(dataSource);
		return manager;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(OracleDataSource dataSource, ApplicationContext applicationContext) {

		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

		try {
			factoryBean.setDataSource(dataSource);
			// factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/configuration.xml"));
			factoryBean.setMapperLocations(applicationContext.getResources("classpath:/com/pro/jun/mybatis/*.xml"));
			// factoryBean.setTransactionFactory(managedTransaction);
			factoryBean.setTypeAliasesPackage("com.pro.jun.vo");
			factoryBean.setTypeAliases(new Class<?>[] { Board.class });
		} catch (IOException e) {
			log.error("sqlSessionFactory ERROR", e);
			return null;
		}

		return factoryBean;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public BoardDao boardDAO(SqlSessionTemplate template) {
		return template.getMapper(BoardDao.class);
	}

}

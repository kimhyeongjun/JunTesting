package com.pro.jun.config;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
public class HibernateConfig {

	@Autowired
	ApplicationContext applicationContext;

	private Log log = LogFactory.getLog(this.getClass());
	private @Value("${db.url}") String url;
	private @Value("${db.username}") String userName;
	private @Value("${db.pw}") String pw;
	private @Value("${db.drivername}") String driverName;

	@Bean(destroyMethod = "close")
	public OracleDataSource dataSource() {
		OracleDataSource dataSource = null;
		try {
			dataSource = new OracleDataSource();
			dataSource.setURL(url);
			dataSource.setUser(userName);
			dataSource.setPassword(pw);
			dataSource.setDataSourceName("ds");

			log.info("[ ORACLE DATABASE CONNECT ]");
		} catch (SQLException e) {
			log.error("DATABASE CONNECT FEILED" + e.getMessage());
			e.printStackTrace();
		}

		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(OracleDataSource dataSource, ApplicationContext applicationContext) throws IOException {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(dataSource);
		localSessionFactoryBean.setMappingResources("/com/pro/jun/hibernate/hibernate.hbm.xml");
		java.util.Properties prop = new java.util.Properties();
		prop.setProperty("hibernate", "org.hibernate.dialect.OracleDialect");
		localSessionFactoryBean.setHibernateProperties(prop);
		return localSessionFactoryBean;
	}
}

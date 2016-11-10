package com.pro.jun.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:/com/acme/properties-config.xml")
public class Properties {

	private @Value("${db.url}") String url;
	private @Value("${db.username}") String userName;
	private @Value("${db.pw}") String pw;
	private @Value("${db.drivername}") String driverName;
}

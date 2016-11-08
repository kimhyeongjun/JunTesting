package com.pro.jun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pro.jun.logger.LoggerInterceptor;

@Configuration
public class LoggerInterCeptorConfig {
	
	@Bean
	public LoggerInterceptor loggerSetting() {
		return null;
	}
}

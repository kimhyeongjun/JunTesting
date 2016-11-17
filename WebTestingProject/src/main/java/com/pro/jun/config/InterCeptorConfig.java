package com.pro.jun.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.pro.jun.logger.LoggerInterceptor;

@Configuration
public class InterCeptorConfig extends WebMvcConfigurerAdapter {

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		log.info("========== INTERCEPTOR CONNECT ==========");
		registry.addInterceptor(new LocaleChangeInterceptor());
		registry.addInterceptor(loggerInterceptor()).addPathPatterns("/**");
	}

	@Bean
	public LoggerInterceptor loggerInterceptor() {
		return new LoggerInterceptor();
	}
}

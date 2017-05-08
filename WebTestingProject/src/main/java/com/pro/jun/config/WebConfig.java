package com.pro.jun.config;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pro.jun.logger.LoggerInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.pro.jun.*")
// @PropertySource("/WEB-INF/config/properties-config.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

	// private Log log = LogFactory.getLog(this.getClass()); log4j 설정
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		LOGGER.info("[ RESOURCES MAPPING ]");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		configurer.enable();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LOGGER.info("[ INTERCEPTOR SETTING ]");
		registry.addInterceptor(new LoggerInterceptor()).addPathPatterns("/**");
	}

	// 프로퍼티파일 로드
	@Bean
	public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
		c.setLocation(new ClassPathResource("properties-config.properties"));
		return c;
	}

	// DbProperties 빈객체 생성
	@Bean
	public DbProperties properties() {
		return new DbProperties();
	}

	// 파일 업로드하기 위한 설정
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		return resolver;
	}

	// 메소드 레벨로 실행자(Executor) 오버라이드
	@Bean
	public Executor threadPoolTaskExecutor() {
		return new ThreadPoolTaskExecutor();
	}
}

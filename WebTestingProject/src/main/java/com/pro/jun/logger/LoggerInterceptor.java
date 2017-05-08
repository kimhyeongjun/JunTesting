package com.pro.jun.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

	protected Logger LOGGER = LoggerFactory.getLogger(LoggerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("==================         START           ===================");
		LOGGER.info("SESSION URI : {} ", request.getSession().getId());
		String sNo = request.getParameter("no");
		if(sNo != null) {
			int no = Integer.parseInt(sNo);
			if(no == 43) return false;
			else return true;
		}
		else return true;
	}

}

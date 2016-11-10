package com.pro.jun.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pro.jun.service.BoardSerivceImpl;

@Controller
@RequestMapping("board")
public class BoardController {

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private BoardSerivceImpl boardSVC;

	@RequestMapping("empList")
	public ModelAndView getList(ModelAndView model) {
		log.info("=============== getEmpList() START ===============");
		model.addObject("empList", boardSVC.getEmpList());
		model.setViewName("empList");
		return model;
	}
}

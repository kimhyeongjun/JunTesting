package com.pro.jun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pro.jun.service.BoardSerivceImpl;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	private BoardSerivceImpl boardSVC;
	
	@RequestMapping("list")
	public ModelAndView getList(ModelAndView model) {
		model.setViewName("thymeleafTest");
		return model;
	}
}

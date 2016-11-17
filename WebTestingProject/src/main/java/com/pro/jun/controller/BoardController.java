package com.pro.jun.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pro.jun.service.BoardServiceImpl;
import com.pro.jun.vo.JsonTest;

@Controller
@RequestMapping("board")
public class BoardController {

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private BoardServiceImpl boardSVC;

	@RequestMapping("empList")
	public ModelAndView getList(ModelAndView model) {
		log.info("=============== getEmpList() START ===============");
		model.addObject("empList", boardSVC.getEmpList());
		model.setViewName("empList");
		return model;
	}
	
	@RequestMapping("ajax")
	@ResponseBody
	public String jsonMapper(JsonTest json) throws JsonGenerationException, JsonMappingException, IOException {
		log.info(json.getPrgCd());
		ObjectMapper objMapper = new ObjectMapper();
		String path = "d://log/jsonTest.json";
		json.setFileName("jsonTest");
		json.setFilePath(path);
		
		objMapper.writeValue(new File(path), json);
		
		return objMapper.writeValueAsString(json);
	}
}

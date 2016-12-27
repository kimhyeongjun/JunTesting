package com.pro.jun.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pro.jun.service.BoardService;
import com.pro.jun.utill.Board;
import com.pro.jun.vo.JsonTest;

@Controller
@RequestMapping("board")
public class BoardController {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardService boardSVC;

	/*@RequestMapping("empList")
	public ModelAndView getList(ModelAndView model) {
		log.info("=============== getEmpList() START ===============");
		model.addObject("empList", boardSVC.getEmpList());
		model.setViewName("empList");
		return model;
	}*/
	
	@RequestMapping("eform")
	public void startEform(HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		pw.append("EFROM START");
		pw.flush();
		pw.close();
	}

	@RequestMapping("writeView")
	public String writeView() {
		return "boardWrite";
	}

	@RequestMapping(value = "write", method = RequestMethod.POST)
	@ResponseBody
	public String write(Board board) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();
		HashMap<String, Object> resultMap = boardSVC.write(board);
		return objMapper.writeValueAsString(resultMap);
	}
	
	@RequestMapping(value="selectOne", method = RequestMethod.GET)
	public ModelAndView selectOne(@RequestParam("no") int no) {
		ModelAndView model = new ModelAndView();
		model.addObject("board", boardSVC.selectOne(no));
		model.setViewName("boardSelectOne");
		return model;
	}

	@RequestMapping("ajax")
	@ResponseBody
	public String jsonMapper(JsonTest json) throws JsonGenerationException, JsonMappingException, IOException {
		LOGGER.info(json.getPrgCd());
		ObjectMapper objMapper = new ObjectMapper();
		String path = "d://log/jsonTest.json";
		json.setFileName("jsonTest");
		json.setFilePath(path);
		objMapper.writeValue(new File(path), json);
		return objMapper.writeValueAsString(json);
	}
}

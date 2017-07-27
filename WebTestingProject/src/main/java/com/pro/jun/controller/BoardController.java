package com.pro.jun.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

	/* @RequestMapping("empList") public ModelAndView getList(ModelAndView model) {
	 * log.info("=============== getEmpList() START ==============="); model.addObject("empList",
	 * boardSVC.getEmpList()); model.setViewName("empList"); return model; } */

	@RequestMapping("list")
	public ModelAndView getList(@RequestParam HashMap<String,String>map) {
		LOGGER.info("=============== getEmpList() START ===============");
		LOGGER.info("PARAM => {}", map.get("test"));
		ModelAndView model = new ModelAndView();
		model.addObject("list", boardSVC.getList());
		model.setViewName("boardList");
		return model;
	}

	@RequestMapping("eform")
	public void startEform(HttpServletResponse response, HttpServletRequest request, @RequestParam("file2") MultipartFile file, MultipartHttpServletRequest multi) throws IOException {
		LOGGER.info("ID => {}", request.getParameter("id"));
		LOGGER.info("FILE => {}", file.getOriginalFilename());
		LOGGER.info("FILE_PATH => {}", request.getParameter("path"));
		LOGGER.info("FILE => {}", multi.getFileNames());
		
		/*File downFile = new File("d:/data/TEST_COPY.pdf");
		FileOutputStream fos = new FileOutputStream(downFile);*/
		// File desFile = new File(desFolderPath + command + "_description.xml");
		Document document = null;
		try {
			document = new SAXBuilder().build(file.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Element> list = document.getRootElement().getChildren();
		for (int i = 0; i < list.size(); i++) {
			String nodeName = list.get(i).getName();
			LOGGER.info(nodeName);
		}
		
		/*fos.write(file.getBytes());
		fos.flush();
		fos.close();*/
		PrintWriter pw = response.getWriter();
		ObjectMapper objMapper = new ObjectMapper();
		HashMap<String, Object> map = new HashMap<>();
		map.put("msg", "EFORM START");
		map.put("id", "김형준#$@");
		map.put("pw", 1235);
		// pw.append("EFROM START");
		pw.append(objMapper.writeValueAsString(map));
		pw.flush();
		pw.close();
	}

	@RequestMapping("writeView")
	public String writeView(@RequestParam LinkedHashMap<String, String> map) {
	// public ModelAndView writeView(@RequestParam LinkedHashMap<String, String> map) {
		ModelAndView model = new ModelAndView("redirect:list");
		// RedirectView rv = new RedirectView("redi");
		LOGGER.info("MAP => {}", map);
		HashMap<String, String> dmap = new HashMap<>();
		model.addObject("test", dmap);
		return "boardWrite";
		// return model;
	}

	/**
	 * @param map
	 *            key => userId, title, contents
	 * <pre>
	 * 1. userId : 사용자아이디
	 * 2. title : 글 제목
	 * 3. contents : 글 내용
	 * </pre>
	 * @param file 업로드 파일
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "write", method = RequestMethod.POST)
	@ResponseBody
	public String write( Board board  /*@RequestParam HashMap<String, Object> map, @RequestParam("file") MultipartFile file*/) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();
		HashMap<String, Object> resultMap = boardSVC.write(board);
		// HashMap<String, Object> resultMap = null;
		return objMapper.writeValueAsString(resultMap);
	}

	@RequestMapping(value = "selectOne", method = RequestMethod.GET)
	public ModelAndView selectOne(@RequestParam("no") int no, @RequestParam("jsonStr") String jsonStr) {
		ModelAndView model = new ModelAndView();
		// System.setProperty("user.dir", "d://JCONE/");
		// LOGGER.info("USER.DIR {}", System.getProperty("user.dir"));
		// model.addObject("board", boardSVC.selectOne(no));
		model.setViewName("boardSelectOne");
		LOGGER.info("JSON_STR => {}", jsonStr);
		LOGGER.info("selectOne END");
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
	
	@RequestMapping("ajax2")
	public String ajaxTest() {
		return null;
	}
}

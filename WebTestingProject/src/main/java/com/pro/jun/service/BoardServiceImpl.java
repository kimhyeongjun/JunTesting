package com.pro.jun.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pro.jun.dao.BoardDao;
import com.pro.jun.scheduler.FileManager;
import com.pro.jun.utill.Board;
import com.pro.jun.utill.Emp;
import com.pro.jun.vo.Search;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDAO = null;

	// @Autowired TransactionTemplate transactionTemplate;
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final String NULL = "";
	
	@Override
	// @Scheduled(cron="*/30 * * * * *") 
	@Async("threadPoolTaskExecutor")
	public void asyncProcess() {
		/*LOGGER.info("Execute method with configured executor - {}", Thread.currentThread());
		LOGGER.info("DATE => {}", new Date().toString());
		String path = "D://hieForm_dev/";
		String eformAll = "D://hieForm_dev/eformAll/";
		String eformTEMP = "D://hieForm_dev/eformTEMP/";
		String eformXMLTEMP = "D://hieForm_dev/eformXML/TEMP/";
		String docId = "DC-0D0F238F49F348A4946D930908A341EC";
		if(!NULL.equals(FileManager.deleteDirectory(eformAll + docId))) {
			LOGGER.info("FILE DELETE SUCCESS {}", eformAll + docId);
			if(!NULL.equals(FileManager.deleteDirectory(eformTEMP + docId))) {
				LOGGER.info("FILE DELETE SUCCESS {}", eformTEMP + docId);
				if(!NULL.equals(FileManager.deleteDirectory(eformXMLTEMP + docId))) {
					LOGGER.info("FILE DELETE SUCCESS {}", eformXMLTEMP + docId);
				} else {
					LOGGER.error("FILE DELETE FAIL => {}", eformXMLTEMP + docId);
				}
			} else {
				LOGGER.error("FILE DELETE FAIL => {}", eformTEMP + docId);
			}
		} else {
			LOGGER.error("FILE DELETE FAIL => {}", eformAll + docId);
		}*/
		try {
			// Thread.sleep(10000);
			LOGGER.info("END");
		} catch (Exception e) {
			LOGGER.error("FILE DELTE ERROR", e);
		}
	}

	public List<Emp> getEmpList() {
		List<Emp> list = boardDAO.getEmpList();
		if (list == null) {
			throw new NullPointerException();
		}
		return list;
	}

	@Override
	public List<Board> getList() {
		List<Board> list = boardDAO.getList();
		if(list == null) {
			throw new NullPointerException();
		}
		return list;
	}

	@Override
	public Board selectOne(int no) {
		return boardDAO.getSelectOne(no);
	}

	@Override
	public boolean modify(Board board) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String delete(int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String search(Search search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public HashMap<String, Object> write(Board board) {
		MultipartFile file = board.getFile();
		String fileName = null;
		String savedFileName = null;
		String path = "c://UPLOAD_FILE/";
		boolean result = false;
		DateTime date = new DateTime();
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			/* String originalStr = file.getOriginalFilename(); // 테스트 String[] charSet = { "utf-8", "euc-kr",
			 * "ksc5601", "iso-8859-1", "x-windows-949" }; for (int i = 0; i < charSet.length; i++) { for (int j = 0; j
			 * < charSet.length; j++) { try { System.out.println("[" + charSet[i] + "," + charSet[j] + "] = " + new
			 * String(originalStr.getBytes(charSet[i]), charSet[j])); } catch (UnsupportedEncodingException e) {
			 * e.printStackTrace(); } } } */
			fileName = new String(file.getOriginalFilename());
			savedFileName = board.getUserId() + "_" + date.getMillis() + "_" + fileName;
			File directory = new File(path);
			if (!directory.isDirectory()) {
				directory.mkdirs();
			}
			path += savedFileName;
			board.setFileName(fileName);
			board.setSavedFileName(savedFileName);
			int resultCnt = 0;
			resultCnt = boardDAO.write(board);
			int boardNo = boardDAO.getBoardNo(board.getUserId());
			board.setNo(boardNo);
			resultCnt = boardDAO.uploadFile(board);
			file.transferTo(new File(path));

			if (resultCnt == 1) {
				result = true;
				resultMap.put("result", result);
				resultMap.put("no", boardNo);
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("파일명 인코딩 에러", e);
		} catch (IllegalStateException e) {
			LOGGER.error("파일 저장 에러", e);
		} catch (IOException e) {
			LOGGER.error("파일 저장 에러", e);
		}
		return resultMap;
	}
}

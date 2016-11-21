package com.pro.jun.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.pro.jun.dao.BoardDao;
import com.pro.jun.utill.Board;
import com.pro.jun.utill.Emp;
import com.pro.jun.vo.Search;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDAO = null;
	
	// @Autowired TransactionTemplate transactionTemplate;
	private Log log = LogFactory.getLog(this.getClass());

	public List<Emp> getEmpList() {
		List<Emp> list = boardDAO.getEmpList();
		if (list == null) {
			throw new NullPointerException();
		}
		return list;
	}

	@Override
	public String getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Board selectOne(int num) {
		// TODO Auto-generated method stub
		return null;
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
	public boolean write(Board board) {
		MultipartFile file = board.getFile();
		String fileName = null;
		String savedFileName = null;
		String path = "c://UPLOAD_FILE/";
		boolean result = false;
		DateTime date = new DateTime();
		try {
			fileName = new String(file.getOriginalFilename().getBytes("iso-8859-1"), "utf-8");
			savedFileName = board.getUserId() + "_" + date.getMillis() + "_" + fileName;
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
			}
		} catch (UnsupportedEncodingException e) {
			log.error("파일명 인코딩 에러", e);
		} catch (IllegalStateException e) {
			log.error("파일 저장 에러", e);
		} catch (IOException e) {
			log.error("파일 저장 에러", e);
		}
		return result;
	}
}
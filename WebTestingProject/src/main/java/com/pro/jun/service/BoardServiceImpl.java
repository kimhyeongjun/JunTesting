package com.pro.jun.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.jun.dao.BoardDao;
import com.pro.jun.dao.HibernateDao;
import com.pro.jun.utill.Board;
import com.pro.jun.utill.Emp;
import com.pro.jun.vo.Search;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private SqlSessionTemplate sql_template;
	private BoardDao boardDAO = null;

	/* @Autowired private HibernateDao hibernateDAO; */

	public List<Emp> getEmpList() {
		boardDAO = sql_template.getMapper(BoardDao.class);

		List<Emp> list = boardDAO.getEmpList();
		// List<Emp> list = hibernateDAO.getHibernateEmpList();
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
	public boolean insert(Board board) {
		// TODO Auto-generated method stub
		return false;
	}
}

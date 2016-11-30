package com.pro.jun.service;

import java.util.HashMap;

import com.pro.jun.utill.Board;
import com.pro.jun.vo.Search;

public interface BoardService {
	public String getList();
	public Board selectOne(int num);
	public boolean modify(Board board);
	public String delete(int num);
	public String search(Search search);
	public HashMap<String, Object> write(Board board);
}

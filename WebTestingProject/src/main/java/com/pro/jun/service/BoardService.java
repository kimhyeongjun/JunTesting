package com.pro.jun.service;

import com.pro.jun.utill.Board;
import com.pro.jun.vo.Search;

public interface BoardService {
	public String getList();
	public String selectOne(int num);
	public String modify(Board board);
	public String delete(int num);
	public String search(Search search);
}

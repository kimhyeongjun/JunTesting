package com.pro.jun.service;

import java.util.HashMap;
import java.util.List;

import com.pro.jun.utill.Board;
import com.pro.jun.vo.Search;

public interface BoardService {
	public List<Board> getList();
	public Board selectOne(int no);
	public boolean modify(Board board);
	public String delete(int num);
	public String search(Search search);
	public HashMap<String, Object> write(Board board);
	public void asyncProcess();
}

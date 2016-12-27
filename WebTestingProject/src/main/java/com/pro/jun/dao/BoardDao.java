package com.pro.jun.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pro.jun.utill.Board;
import com.pro.jun.utill.Emp;

@Repository
public interface BoardDao {
	public List<Emp> getEmpList();
	public int write(Board board);
	public int uploadFile(Board board);
	public int getBoardNo(String userId);
	public Board getSelectOne(int no);
}

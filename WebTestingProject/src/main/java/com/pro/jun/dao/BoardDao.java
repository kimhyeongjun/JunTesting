package com.pro.jun.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pro.jun.utill.Emp;

@Repository
public interface BoardDao {
	public List<Emp> getEmpList();
}

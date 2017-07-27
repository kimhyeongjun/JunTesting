package com.pro.jun.vo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "emp")
public class JPAVo {

	@Id
	@Column(name = "EMPNO")
	private int empno;
	@Column(name = "ENAME")
	private String ename;
	@Column(name = "JOB")
	private String job;
	@Column(name = "HIREDATE")
	private Date hiredate;
}

package com.pro.jun.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pro.jun.utill.Emp;

@Repository
public class HibernateDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<Emp> getHibernateEmpList() {
		List<Emp> list = sessionFactory.getCurrentSession().createQuery("FROM emp").list();
		return list;
	}
}

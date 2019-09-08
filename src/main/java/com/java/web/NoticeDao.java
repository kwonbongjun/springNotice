package com.java.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDao {
	@Autowired
	SqlSession s;

	
	public List<Login> loginselect(Login login) {
		List<Login> list=new ArrayList<Login>(); 
		Map<String,Login> map=new HashMap<String, Login>();
		System.out.println("1");
//		System.out.println(s.selectList("test.login").get(0));
	  	return s.selectList("test.login", login); 
	}
	 
	public List<Bean> contentselect() {

		return s.selectList("test.content");
	}
	
	public void insertContent(String val ) {
		s.insert("test.insert",val);
	}
	public void updateContent(Bean bean ) {
		s.update("test.update",bean);
	}
	public void deleteContent(Bean bean ) {
		s.update("test.delete",bean);
	}
}

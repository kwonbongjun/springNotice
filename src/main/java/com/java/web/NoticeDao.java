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

	
	  public List<Login> loginselect(Login login) { List<Login> list=new
	  ArrayList<Login>(); Map<String,Login> map=new HashMap<String, Login>();
	  	return s.selectList("test.login", map); 
	  }
	 
	public List<Bean> contentselect() {
		return s.selectList("test.content");
	}
}

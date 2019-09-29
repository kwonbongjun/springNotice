package com.java.web.service;

import java.util.List;

import com.java.web.bean.Bean;
import com.java.web.bean.Login;
import com.java.web.bean.Movie;
import com.java.web.bean.UserMovie;

public interface NoticeServiceInterface {
	public Login loginRead(String id,String pw);
	public Bean detailRead(int no);
	
	public void insertLogin(Login login);
	public Login checkLogin(String id);
	
	public int titleidmapping(String title);
	public int isSetScore(UserMovie um );
	public void setstar(UserMovie um);
	public void insertUserMovie(UserMovie um);
	public List<String> recommend(String id);
}

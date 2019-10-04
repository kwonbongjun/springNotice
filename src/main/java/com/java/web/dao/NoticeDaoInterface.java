package com.java.web.dao;

import java.util.List;

import com.java.web.bean.Login;
import com.java.web.bean.Movie;
import com.java.web.bean.UserMovie;
import com.java.web.util.CrawlingBean;

public interface NoticeDaoInterface {
	public void insertLogin(Login login);
	public Login selectLogin(String id);
	
	public int titleidmapping(String title);
	public int isSetScore(UserMovie um);
	public void setstar(UserMovie um);
	public void inserUserMovie(UserMovie um);
	public List<String> recommend(String id);
	public void setWatchMovie(UserMovie um);
	public void setrate(UserMovie um);
	public List<Movie> movieRank(int page);
	public int movieRankAll();
	public int existMovie(Movie movie);
	public void insertMovie(Movie m);
	public int selectTotalRank();
	
	public int getMaleRate(int no);
	public int getFemaleRate(int no);
	public int getRate10(int no);
	public int getRate20(int no);
	public int getRate30(int no);
	public int getRate40(int no);
	public int getRate50(int no);
	
	public void crawling(CrawlingBean c);
	
	public int tdidmapping(Movie movie);
	
	
	
}

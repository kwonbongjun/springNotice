package com.java.web.dao;

import java.util.List;
import java.util.Map;

import com.java.web.bean.Bean;
import com.java.web.bean.Login;
import com.java.web.bean.Movie;
import com.java.web.bean.RateBean;
import com.java.web.bean.UserMovie;
import com.java.web.util.CrawlingBean;

public interface NoticeDaoInterface {
	public void insertLogin(Login login);
	public Login selectLogin(String id);
	
	public int titleidmapping(String title);
	public int isSetScore(UserMovie um);
	public void setstar(UserMovie um);
	public void inserUserMovie(UserMovie um);
	public List<CrawlingBean> recommend(String id);
	public void setWatchMovie(UserMovie um);
	public void setrate(UserMovie um);
	public List<Movie> movieRank(int page);
	public int movieRankAll();
	public int existMovie(Movie movie);
	public void insertMovie(Movie m);
	public int selectTotalRank();
	
	public RateBean getMaleRate(int no);
	public RateBean getFemaleRate(int no);
	public RateBean getRate10(int no);
	public RateBean getRate20(int no);
	public RateBean getRate30(int no);
	public RateBean getRate40(int no);
	public RateBean getRate50(int no);
	
	public void crawling(CrawlingBean c);
	
	public Integer tdidmapping(Movie movie);
	public int isWatch(UserMovie um);
	public void insertWatchMovie(UserMovie um);
	public Login checkNickname(String nickname);
	
	public int contentReadSearchAll2(String title);
	public List<Bean> contentReadSearch2(Map<String, Object> map);
	public int watchYn(UserMovie um);
	
	
	
}

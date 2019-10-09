package com.java.web.service;

import java.util.List;

import com.java.web.bean.Bean;
import com.java.web.bean.Login;
import com.java.web.bean.Movie;
import com.java.web.bean.RateBean;
import com.java.web.bean.UserMovie;
import com.java.web.util.CrawlingBean;

public interface NoticeServiceInterface {
	public Login loginRead(Login login);
	public Bean detailRead(int no);
	
	public void insertLogin(Login login);
	public Login checkLogin(String id);
	
	public int titleidmapping(String title);
	public int isSetScore(UserMovie um );
	public void setstar(UserMovie um);
	public void insertUserMovie(UserMovie um);
	public List<CrawlingBean> recommend(String id);
	public void SetWatchMovie(UserMovie um);
	public void setrate(UserMovie um);
	public List<Movie> movieRank(int page);
	public int movieRankAll();
	public int existMovie(Movie movie);
	public void insertMovie(Movie m2);
	
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

	
}

package com.java.web.dao;

import java.util.List;

import com.java.web.bean.Login;
import com.java.web.bean.Movie;
import com.java.web.bean.UserMovie;

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
}

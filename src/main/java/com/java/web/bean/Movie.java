package com.java.web.bean;
import java.lang.*;

public class Movie {
	private String no;
	private String image;
	private String title;
	private String director;
	private String actor;
	private double userRating;
	private int userRatingCnt;
	private String release;
	
	public Movie(String no,String image,String title, String director, String actor, double userRating, int userRatingCnt) {
		this.no=no;
		this.image=image;
		this.title=title;
		this.director=director;
		this.actor=actor;
		this.userRating= Math.round(userRating*100)/100.0;
		this.userRatingCnt=userRatingCnt;
	}
	public Movie(String image,String title, String director, String actor, double userRating, int userRatingCnt,String release) {
		this.image=image;
		this.title=title;
		this.director=director;
		this.actor=actor;
		this.userRating=userRating;
		this.userRating= Math.round(userRating*100)/100.0;
		this.release=release;
	}
	public Movie(String no,String title, String director, double rate, int rateCnt) {
		this.no=no;
		this.title=title;
		this.director=director;
		this.userRating= Math.round(rate*100)/100.0;
		this.userRatingCnt=rateCnt;
	}
	public Movie(String title, String director,String release) {
		this.title=title;
		this.director=director;
		this.release=release;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public double getUserRating() {
		return userRating;
	}
	public void setUserRating(double userRating) {
		this.userRating= Math.round(userRating*100)/100.0;
	}
	public int getUserRatingCnt() {
		return userRatingCnt;
	}
	public void setUserRatingCnt(int userRatingCnt) {
		this.userRatingCnt = userRatingCnt;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	
}

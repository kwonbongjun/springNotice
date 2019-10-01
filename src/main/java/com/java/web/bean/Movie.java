package com.java.web.bean;

public class Movie {
	private String image;
	private String title;
	private String director;
	private String actor;
	private int userRating;
	private int userRatingCnt;
	public Movie(String image,String title, String director, String actor, int userRating, int userRatingCnt) {
		this.image=image;
		this.title=title;
		this.director=director;
		this.actor=actor;
		this.userRating=userRating;
		this.userRatingCnt=userRatingCnt;
	}
	public Movie(String title, String director, int rate, int rateCnt) {
		this.title=title;
		this.director=director;
		this.userRating=rate;
		this.userRatingCnt=rateCnt;
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
	public int getUserRating() {
		return userRating;
	}
	public void setUserRating(int userRating) {
		this.userRating = userRating;
	}
	public int getUserRatingCnt() {
		return userRatingCnt;
	}
	public void setUserRatingCnt(int userRatingCnt) {
		this.userRatingCnt = userRatingCnt;
	}
	
}

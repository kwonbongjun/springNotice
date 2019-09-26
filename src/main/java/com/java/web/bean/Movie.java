package com.java.web.bean;

public class Movie {
	private String image;
	private String title;
	private String director;
	private String actor;
	private String userRating;
	public Movie(String image,String title, String director, String actor, String userRating) {
		this.image=image;
		this.title=title;
		this.director=director;
		this.actor=actor;
		this.userRating=userRating;
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
	public String getUserRating() {
		return userRating;
	}
	public void setUserRating(String userRating) {
		this.userRating = userRating;
	}
	
}

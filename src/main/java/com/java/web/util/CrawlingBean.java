package com.java.web.util;

import java.util.HashMap;

public class CrawlingBean {
	String title;
	String genre1;
	String genre2;
	String nation;
	String time;
	String release;
	//HashMap<String, Object> columns;
	String director;
	public CrawlingBean(String title,String genre1, String genre2,String nation,
			String time, String release,String director) {
		this.title=title;
		this.genre1=genre1;
		this.genre2=genre2;
		this.nation=nation;
		this.time=time;
		this.release=release;
		this.director=director;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre1() {
		return genre1;
	}
	public void setGenre1(String genre1) {
		this.genre1 = genre1;
	}
	public String getGenre2() {
		return genre2;
	}
	public void setGenre2(String genre2) {
		this.genre2 = genre2;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}

}

package com.java.web.bean;

public class UserMovie {
	private String user_id;
	private int m_no;
	private int m_rate;
	private int searchCnt;
	private char watchYn;
	private String review;
	public UserMovie(String user_id, int m_no, int m_rate, int searchCnt, char watchYn, String review) {
		this.user_id=user_id;
		this.m_no=m_no;
		this.m_rate=m_rate;
		this.searchCnt=searchCnt;
		this.watchYn=watchYn;
		this.review=review;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}
	public int getM_rate() {
		return m_rate;
	}
	public void setM_rate(int m_rate) {
		this.m_rate = m_rate;
	}
	public int getSearchCnt() {
		return searchCnt;
	}
	public void setSearchCnt(int searchCnt) {
		this.searchCnt = searchCnt;
	}
	public char getWatchYn() {
		return watchYn;
	}
	public void setWatchYn(char watchYn) {
		this.watchYn = watchYn;
	}
	
}

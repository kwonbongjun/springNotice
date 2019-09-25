package com.java.web.bean;

public class Login {
	private String id;
	private String pw;
	private String nickname;
	public Login(String id,String nickname) {
		this.id=id;
		this.nickname=nickname;
	}
	public Login(String id,String pw,String nickname) {
		this.id=id;
		this.pw=pw;
		this.nickname=nickname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public void setId(String id) {
		this.id=id;
	}
	
	public void setPw(String pw) {
		this.pw=pw;
	}
	
}

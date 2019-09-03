package com.java.web;

public class Bean {
	private int no;
	private String val;
	public Bean(int no,String val) {
		this.no=no;
		this.val=val;
	}
	public int getNo() {
		return no;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val=val;
	}
	
}

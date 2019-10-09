package com.java.web.bean;

public class RateBean {
	private int cnt;
	private float avg;
	public RateBean(int cnt, float avg) {
		this.cnt=cnt;
		this.avg=avg;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}
	
	
}

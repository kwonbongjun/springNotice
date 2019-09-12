package com.java.web;

public class Bean {
	private int no;
	private String title;
	private String val;
	private String writer;
	private String fileName;
	public void update(int no,String title, String val,String writer) {
		this.no=no;
		this.val=val;
		this.title=title;
		this.writer=writer;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	private String fileurl;
	private String ext;
	public Bean(int no,String title, String val) {
		this.no=no;
		this.val=val;
		this.title=title;
	}
	
	public Bean(String title, String val,String writer, 
			String fileName, String originalFileName, String ext) {
		this.val=val;
		this.title=title;
		this.writer=writer;
		this.fileName=fileName;
		this.fileurl=originalFileName;
		this.ext=ext;
	}

	public Bean() {
		// TODO Auto-generated constructor stub
	}
	public Bean(int no, String title, String val, String originalFileName, String fileName, String ext) {
		this.no=no;
		this.val=val;
		this.title=title;
		this.fileurl=fileName;
		this.fileName=originalFileName;
		this.ext=ext;
	}
	public Bean(String title, String val, String writer) {
		this.val=val;
		this.title=title;
		this.writer=writer;
	}
	public void setNo(int no) {
		this.no=no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
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

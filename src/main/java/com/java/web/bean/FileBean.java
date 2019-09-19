package com.java.web.bean;

public class FileBean {
	private int no;
	private String filename;
	private String fileurl;
	private String ext;
	public FileBean(int no, String originalFileName, String fileName, String ext) {
		this.no=no;
		this.filename=originalFileName;
		this.fileurl=fileName;
		this.ext=ext;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
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
}

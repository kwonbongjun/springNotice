package com.java.web.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java.web.bean.Bean;
import com.java.web.bean.FileBean;
import com.java.web.bean.Login;
import com.java.web.bean.Movie;
import com.java.web.bean.UserMovie;
import com.java.web.dao.NoticeDao;
import com.java.web.dao.NoticeDaoInterface;

@Service
public class NoticeService implements NoticeServiceInterface {
 @Autowired
 NoticeDao nd;
 @Autowired
 NoticeDaoInterface ndi;
	
	public Login loginRead(String id, String pw){
		Login login=new Login(id, pw);
		return nd.loginselect(login); 
	}
	public void insertLogin(Login login) {
		ndi.insertLogin(login);
	}
	public Login checkLogin(String id) {
		return ndi.selectLogin(id);
	}
	
 	public int contentReadAll(){
 		return nd.contentselectAll();
 	}
 	public List<Bean> contentRead(int pageNum){
 		return nd.contentselect(pageNum);
 	}
 	public void createContent(Bean bean) {
 		nd.insertContent(bean);
 	}

	public void updateContent(Bean bean) {
 		nd.updateContent(bean);
 	}
	public void deleteContent(Bean bean) {
 		nd.deleteContent(bean);
 	}
	
	public Bean detailRead(int no){
		return nd.detailselect(no);
	}
	
	public void updateDetail(Bean bean){
		nd.updateDetail(bean);
	}
	public void deleteDetail(int no){
		nd.deleteDetail(no);
	}

	public void createFile(FileBean fb) {
		nd.insertFile(fb);
	}

	public List<FileBean> readFile(int no) {
		return nd.selectFile(no);
	}
	public FileBean readFile(FileBean fb) {
		return nd.selectFile(fb);
	}
	
	public int readfinalno() {
		return nd.selectfinalno();
	}
	public void updateFile(FileBean fb) {
		nd.updateFile(fb);
	}

	public void deleteFile(int no) {
		nd.deleteFile(no);	
	}

	public int contentReadSearchAll(String title) {
		return nd.selectSearchAll(title);
	}
	public List<Bean> contentReadSearch(int pageNum, String title){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("pageNum", pageNum);
		map.put("title", title);
		return nd.selectContentSearch(map);
	}
	public int readSearchFinalNo(String title) {
		return nd.selectSearchFinalNo(title);
	}
	
	public void uploadFile(MultipartFile[] files, int no) {
		if(!"".equals(files[0].getOriginalFilename())) {
			for(int i=0;i<files.length;i++) {
				MultipartFile file = files[i];
				String originalFileName=file.getOriginalFilename();
				String ext=originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
				String fileName=UUID.randomUUID().toString();
				try {
					byte[] data=file.getBytes();
					String path="C:\\Resources\\";//D:\\workspace\\resources\\";
					File f = new File(path);
					if(!f.isDirectory()) {
						f.mkdirs();
					}
					OutputStream os = new FileOutputStream(new File(path+fileName+ext));
					os.write(data);
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				FileBean fb=new FileBean(no,originalFileName,fileName,ext);
				createFile(fb);
			}
		}
	}
// 	public void createContent(String val) {
// 		nd.insertContent(val);
// 	}


	public int titleidmapping(String title) {
		return ndi.titleidmapping(title);
	}
	public int isSetScore(UserMovie um ) {
		return ndi.isSetScore(um);
	}
	public void setstar(UserMovie um) {
		ndi.setstar(um);
	}
	public void insertUserMovie(UserMovie um) {
		ndi.inserUserMovie(um);
	}
	public List<String> recommend(String id) {
		return ndi.recommend(id);
	}

}

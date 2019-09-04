package com.java.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
 @Autowired
 NoticeDao nd;
	
	public List<Login> loginRead(Login login){
		return nd.loginselect(login); 
	}
	 
 	
 	public List<Bean> contentRead(){
 		return nd.contentselect();
 	}
 	
 	public void createContent(String val) {
 		nd.insertContent(val);
 	}
	public void updateContent(Bean bean) {
 		nd.updateContent(bean);
 	}
	public void deleteContent(Bean bean) {
 		nd.deleteContent(bean);
 	}
}

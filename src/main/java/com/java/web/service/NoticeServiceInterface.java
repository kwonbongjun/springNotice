package com.java.web.service;

import com.java.web.bean.Bean;
import com.java.web.bean.Login;

public interface NoticeServiceInterface {
	public Login loginRead(String id,String pw);

	public Bean detailRead(int no);
}

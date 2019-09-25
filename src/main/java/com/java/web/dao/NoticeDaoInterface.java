package com.java.web.dao;

import com.java.web.bean.Login;

public interface NoticeDaoInterface {
	public void insertLogin(Login login);
	public Login selectLogin(String id);
}

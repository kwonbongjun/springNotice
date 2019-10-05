package com.java.web.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.java.web.bean.Bean;
import com.java.web.bean.FileBean;
import com.java.web.bean.Login;
import com.java.web.bean.Movie;
import com.java.web.bean.UserMovie;
import com.java.web.util.CrawlingBean;

@Repository
public class NoticeDao implements NoticeDaoInterface{
	@Autowired
	SqlSession s;

	
	public Login loginselect(Login login) {
		List<Login> list=new ArrayList<Login>(); 
		Map<String,Login> map=new HashMap<String, Login>();
		System.out.println("1");

	  	return s.selectOne("test.login", login); 
	}
	public void insertLogin(Login login) {
		s.insert("test.insertLogin",login);
	}
	public Login selectLogin(String id) {
		return s.selectOne("test.checkLogin",id);
	}
	
	public int contentselectAll() {
		return s.selectOne("test.contentall");
	}
	public List<Bean> contentselect(int pageNum) {
		return s.selectList("test.content",pageNum);
	}
	public void insertContent(Bean bean ) {
		System.out.println("2");
		s.insert("test.insert",bean);
		System.out.println("2");
	}

	public void updateContent(Bean bean ) {
		s.update("test.update",bean);
	}
	public void deleteContent(Bean bean ) {
		s.update("test.delete",bean);
	}
	
	public Bean detailselect(int no){
		return s.selectOne("test.detail",no);
	}
	public void updateDetail(Bean bean) {
		s.update("test.updateDetail",bean);	
	}
	public void deleteDetail(int no) {
		s.update("test.deleteDetail",no);
	}
	public void insertFile(FileBean fb) {
		s.insert("test.insertFile", fb);
	}
	public List<FileBean> selectFile(int no) {
		return s.selectList("test.selectFile",no);
	}
	public FileBean selectFile(FileBean fb) {
		return s.selectOne("test.selectFileOne",fb);
	}
	
	public int  selectfinalno() {
		return s.selectOne("test.finalno");
	}
	public void updateFile(FileBean fb) {
		s.insert("test.insertFile",fb);
	}
	public void deleteFile(int no) {
		s.delete("test.deleteFile",no);
	}
	public int selectSearchAll(String title) {
		return s.selectOne("test.searchContentAll",title);
	}
	public List<Bean> selectContentSearch(Map<String, Object> map ){
		return s.selectList("test.selectContentSearch", map);
	}
	public int selectSearchFinalNo(String title) {
		return s.selectOne("test.searchFinalNo",title);
	}
//	public void insertContent(String val ) {
//		s.insert("test.insert",val);
//	}


	public int titleidmapping(String title) {
		return s.selectOne("test.titleidmapping",title);
	}
	public int isSetScore(UserMovie um) {
		System.out.println(s.selectOne("test.isSetScore",um).toString());
		return s.selectOne("test.isSetScore",um);
	}
	public void setstar(UserMovie um) {
		s.update("test.setStar", um);
	}
	public void inserUserMovie(UserMovie um) {
		s.insert("test.insertUM", um);
	}
	public List<CrawlingBean> recommend(String id) {
		return s.selectList("test.recommend", id);
	}
	public void setWatchMovie(UserMovie um) {
		s.update("test.setWatchMovie",um);
	}
	public void setrate(UserMovie m_no) {
		s.update("test.setrate", m_no);
	}
	public List<Movie> movieRank(int page){
		return s.selectList("test.movierank",page);
	}
	public int movieRankAll() {
		return s.selectOne("test.movieRankAll");
	}
	public int existMovie(Movie movie) {
		return s.selectOne("test.existMovie",movie);
	}
	public void insertMovie(Movie m) {
		s.insert("insertMovie",m);
	}
	public int selectTotalRank() {
		return s.selectOne("test.selectTotalRank");
	}
	
	@Override
	public int getMaleRate(int no) {
		return s.selectOne("test.getMaleRate",no);
	}
	@Override
	public int getFemaleRate(int no) {
		return s.selectOne("test.getFemaleRate",no);
	}
	@Override
	public int getRate10(int no) {
		return s.selectOne("test.getRate10",no);
	}
	@Override
	public int getRate20(int no) {
		return s.selectOne("test.getRate20",no);
	}
	@Override
	public int getRate30(int no) {
		return s.selectOne("test.getRate30",no);
	}
	@Override
	public int getRate40(int no) {
		return s.selectOne("test.getRate40",no);
	}
	@Override
	public int getRate50(int no) {
		return s.selectOne("test.getRate50",no);
	}
	
	@Override
	public void crawling(CrawlingBean c) {
		s.insert("test.crawling",c);
	}
	@Override
	public Integer tdidmapping(Movie movie) {
		System.out.println(movie.getDirector()+","+movie.getTitle());
		return s.selectOne("test.tdidmapping",movie);
	}
	@Override
	public int isWatch(UserMovie um) {
		return s.selectOne("test.isWatch",um);
	}
	@Override
	public void insertWatchMovie(UserMovie um) {
		s.insert("test.insertWatchMovie",um);
	}
	
	
}

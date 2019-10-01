package com.java.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.web.bean.Movie;
import com.java.web.dao.NoticeDaoInterface;
import com.java.web.service.NoticeService;
import com.java.web.service.NoticeServiceInterface;

@Controller
public class RateController {
	@Autowired
	NoticeServiceInterface nsi;
	
	@Autowired
	NoticeService ns;
	
	@RequestMapping(value="/rate")
	public String rate(HttpServletRequest req, HttpServletResponse res) {
		String tpage=req.getParameter("pageNum");
		int page=1;
		if(tpage==null) {
			page=1;
		}else {
			page=Integer.parseInt(tpage);
		}
		
		System.out.println(page);
		List<Movie> list = nsi.movieRank(page);
		System.out.println(list.get(0).getTitle());
		req.setAttribute("list", list);
		
		int total;
		//검색여부 확인
		if(req.getParameter("search")!=null) {
			String title=req.getParameter("search");
			total=ns.contentReadSearchAll(title);
//			list=ns.contentReadSearch(page, title);
		}else {
			total=nsi.movieRankAll();
//			list=ns.contentRead(page);
		}
		req.setAttribute("total", page);
		return "rate";
	}
}

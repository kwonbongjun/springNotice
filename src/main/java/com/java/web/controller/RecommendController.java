package com.java.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java.web.bean.Movie;
import com.java.web.service.NoticeServiceInterface;
import com.java.web.util.NaverAPI;

@Controller
public class RecommendController {
	@Autowired
	NoticeServiceInterface nsi;
	
	@RequestMapping(value="/recommend" /*method=RequestMethod.POST*/)
	public String recommend(HttpServletRequest req, HttpServletResponse res) {
		String id=req.getParameter("user");
		System.out.println(id);
		List<String> title=nsi.recommend(id);
		NaverAPI na = new NaverAPI();
		Movie[] m = na.naverMovie(title.get(0),null,null,null);
		req.setAttribute("mlist", m[0]);
		return "recommend";
	}
}

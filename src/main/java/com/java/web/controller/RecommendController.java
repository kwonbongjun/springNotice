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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class RecommendController {
	@Autowired
	NoticeServiceInterface nsi;
	
	@RequestMapping(value="/recommend" /*method=RequestMethod.POST*/)
	public String recommend(HttpServletRequest req, HttpServletResponse res) {
		String str=req.getParameter("update");
		JSONArray ja = JSONArray.fromObject(str);
		Movie[] update = (Movie[]) ja.toArray();
		if(update!=null) {
			for(int i=0;i<update.length;i++) {
				nsi.SetWatchMovie();
			}
		}
		String id=req.getParameter("user");
		System.out.println(id);
		List<String> title=nsi.recommend(id);
		NaverAPI na = new NaverAPI();
		int len=0;
		if(title!=null) {
			len=title.size();
			if(title.size()>3) {
				len=3;
			}
		}
		Movie[] m = new Movie[3];
		for(int i=0;i<len;i++) {
			m[i]=na.naverMovie(title.get(i),null,null,null)[0];
		}

		req.setAttribute("mlist", m);
		return "recommend";
	}
}

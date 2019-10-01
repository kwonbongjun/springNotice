package com.java.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java.web.bean.Movie;
import com.java.web.bean.UserMovie;
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
		String user_id=req.getParameter("user");
		String str1=req.getParameter("update1");
		String str2=req.getParameter("update2");
		String str3=req.getParameter("update3");
		
//		if(str!=null) {
			//JSONArray ja = JSONArray.fromObject(str);
//			System.out.println("2222"+ja);
			String update = null;
			UserMovie um = null;
			if(str1!=null) {
				//for(int i=0;i<ja.size();i++) {
					//update= (String) ja.get(i);
					int m_no=nsi.titleidmapping(str1);
					System.out.println("11111"+user_id+m_no);
					um=new UserMovie(user_id, m_no,0,0,'0');
					nsi.SetWatchMovie(um);
				//}
			}
			if(str2!=null) {
					int m_no=nsi.titleidmapping(str2);
					um=new UserMovie(user_id, m_no,0,0,'0');
					nsi.SetWatchMovie(um);
			}
			if(str3!=null) {
				int m_no=nsi.titleidmapping(str3);
				um=new UserMovie(user_id, m_no,0,0,'0');
				nsi.SetWatchMovie(um);
		}
			
		String id=req.getParameter("user");
		System.out.println(id);
		List<String> title=nsi.recommend(id);
//		if(title==null) {
//			title=nsi.recommend(user_id);
//		}
		NaverAPI na = new NaverAPI();
		int len=0;
		if(title!=null) {
			len=title.size();
			if(title.size()>3) {
				len=3;
			}
		}
		Movie[] m = new Movie[len];
		for(int i=0;i<len;i++) {
			m[i]=na.naverMovie(title.get(i),null,null,null)[0];
		}
		
		req.setAttribute("mlist", m);
		
		return "recommend";
	}
}

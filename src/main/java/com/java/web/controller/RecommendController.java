package com.java.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java.web.bean.Login;
import com.java.web.bean.Movie;
import com.java.web.bean.UserMovie;
import com.java.web.service.NoticeServiceInterface;
import com.java.web.util.CrawlingBean;
import com.java.web.util.CrawlingController;
import com.java.web.util.NaverAPI;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class RecommendController {
	@Autowired
	NoticeServiceInterface nsi;
	
	@RequestMapping(value="/recommend" /*method=RequestMethod.POST*/)
	public String recommend(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("11"+req.getParameter("user"));
		if(req.getParameter("user").equals("null")) {
			System.out.println("111");
			req.setAttribute("recommendAccess", "true");
			return "home";
		}
		String user_id=req.getParameter("user");
		String str1=req.getParameter("update1");
		String str2=req.getParameter("update2");
		String str3=req.getParameter("update3");
		String d1=req.getParameter("director1");
		String d2=req.getParameter("director2");
		String d3=req.getParameter("director3");
		String r1=req.getParameter("release1");
		String r2=req.getParameter("release2");
		String r3=req.getParameter("release3");
//		if(str!=null) {
			//JSONArray ja = JSONArray.fromObject(str);
//			System.out.println("2222"+ja);
			String update = null;
			UserMovie um = null;
			if(str1!=null) {
				System.out.println("22222");
				//for(int i=0;i<ja.size();i++) {
					//update= (String) ja.get(i);
					//int m_no=nsi.titleidmapping(str1);
				System.out.println(str1+d1+r1);
					int m_no=nsi.tdidmapping(new Movie(str1,d1,r1));
					System.out.println("11111"+user_id+m_no);
					System.out.println("3");
					um=new UserMovie(user_id, m_no,0,0,'N',null);
					System.out.println("4");
					if(nsi.isWatch(um)==0) {
						nsi.insertWatchMovie(um);
						System.out.println("5");
						nsi.SetWatchMovie(um);
						System.out.println("6");
					}else {
						System.out.println("7");
					nsi.SetWatchMovie(um);
					System.out.println("8");
					}
				//}
			}

			if(str2!=null) {
					//int m_no=nsi.titleidmapping(str2);
					int m_no=nsi.tdidmapping(new Movie(str2,d2,r2));
					um=new UserMovie(user_id, m_no,0,0,'0',null);
					if(nsi.isWatch(um)==0) {
						nsi.insertWatchMovie(um);
						nsi.SetWatchMovie(um);
					}else {
					nsi.SetWatchMovie(um);
					}
			}
			if(str3!=null) {
				//int m_no=nsi.titleidmapping(str3);
				int m_no=nsi.tdidmapping(new Movie(str3,d3,r3));
				um=new UserMovie(user_id, m_no,0,0,'0',null);
				if(nsi.isWatch(um)==0) {
					nsi.insertWatchMovie(um);
					nsi.SetWatchMovie(um);
				}else {
				nsi.SetWatchMovie(um);
				}
		}
			
		String id=req.getParameter("user");
		System.out.println(id);
		List<CrawlingBean> title=nsi.recommend(id);
//		if(title==null) {
//			title=nsi.recommend(user_id);
//		}
		NaverAPI na = new NaverAPI();
		int len=0;
		Movie temp=null;
		if(title!=null) {
			len=title.size();
			if(title.size()>3) {
				len=3;
			}
		}

		String[] nation=new String[len];
		for(int i=0;i<len;i++) {
		
		System.out.println("11111"+title.get(i).getTitle()+title.get(i).getRelease()+title.get(i).getNation()+title.get(i).getDirector());
		if(title.get(i).getNation().equals("영국")) {
			nation[i]="GB";
		}else if(title.get(i).getNation().equals("오스트레일리아")){
				nation[i]="ETC";
			}
		}
		//Movie[] m = new Movie[len];
		List<Movie> m = new ArrayList<Movie>();
		for(int i=0;i<len;i++) {
			if("".equals(title.get(i).getTitle())){
				continue;
			}
			
			System.out.println(title.get(i).getTitle()+title.get(i).getRelease()+title.get(i).getNation());
//			Movie m2 = na.naverMovie(title.get(i).getTitle(),title.get(i).getRelease(),title.get(i).getRelease(),null);
			if(na.naverMovie(title.get(i).getTitle(),title.get(i).getRelease(),title.get(i).getRelease(),null)==null) {
//				System.out.println("222222");
//				m[i]=null;
//				title.remove(i);
//				i--;
//				len=len-1;
				continue;
			}
//			if(nsi.tdidmapping(new Movie(na.naverMovie(title.get(i).getTitle(),title.get(i).getRelease(),title.get(i).getRelease(),null)[0].getTitle(),
//					na.naverMovie(title.get(i).getTitle(),title.get(i).getRelease(),title.get(i).getRelease(),null)[0].getDirector()))==0) {
//				continue;
//			}
			Movie[] m2 = na.naverMovie(title.get(i).getTitle(),null,null,null);
			if(m2!=null) {
				CrawlingController cc = new CrawlingController();
				List<CrawlingBean> c = cc.getHtmlData2(m2[0].getTitle());//title.get(i).getTitle()
				for(int j=0;j<c.size();j++) {
					Integer m3 = nsi.existMovie(new Movie(c.get(j).getTitle(),c.get(j).getDirector(),c.get(j).getRelease()));
					System.out.println(m3);
					if(m3==0) {
						nsi.crawling(c.get(j));
					}
				}
				if(m2[0].getTitle()!=null && m2[0].getDirector()!=null && m2[0].getRelease()!=null) {
					System.out.println("1123121441"+ m2[0].getTitle()+m2[0].getDirector()+m2[0].getRelease());
				int m_no=nsi.tdidmapping(new Movie(m2[0].getTitle(),m2[0].getDirector(),m2[0].getRelease()));
				um=new UserMovie(user_id, m_no,0,0,'0',null);
				if(nsi.watchYn(um)>0) {
					continue;
				}
				}
				m.add(m2[0]);
			}
			
		}
		
		req.setAttribute("mlist", m);
		
		return "recommend";
	}
}

package com.java.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.web.bean.Movie;
import com.java.web.bean.RateBean;
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
		String code=req.getParameter("code");
		if(code!=null) {
			Map<String,RateBean> map=new HashMap<String, RateBean>();
			int no=Integer.parseInt(code);
			RateBean mrate=nsi.getMaleRate(no);
			RateBean frate=nsi.getFemaleRate(no);
			RateBean rate10 = nsi.getRate10(no);
			RateBean rate20 = nsi.getRate20(no);
			RateBean rate30 = nsi.getRate30(no);
			RateBean rate40 = nsi.getRate40(no);
			RateBean rate50 = nsi.getRate50(no);
			map.put("mrate", mrate);
			map.put("frate", frate);
			map.put("rate10", rate10);
			map.put("rate20", rate20);
			map.put("rate30", rate30);
			map.put("rate40", rate40);
			map.put("rate50", rate50);
			req.setAttribute("map", map);
			return "rateAnalysis";
		}
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
		//토탈페이지 구하기
		total = nsi.selectTotalRank();
		
		//검색여부 확인
		if(req.getParameter("search")!=null) {
			String title=req.getParameter("search");
			total=ns.contentReadSearchAll(title);
//			list=ns.contentReadSearch(page, title);
		}else {
			total=nsi.movieRankAll();
			System.out.println("total"+total);
//			list=ns.contentRead(page);
		}
		req.setAttribute("total", total);
		return "rate";
	}
}

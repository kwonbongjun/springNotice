package com.java.web.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.web.bean.Movie;

import net.sf.json.JSONObject;

@Controller
public class CrawlingController {

	@Autowired
	CrawlingServiceInterface csi;

	@Autowired
	SqlSession s;
	@RequestMapping("/getHtmlData")
	public void getHtmlData(HttpServletRequest req, HttpServletResponse response) {
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("page", req.getParameter("page"));
		try {
			JSONObject jsonObject = JSONObject.fromObject(csi.getHtmlData(paramsMap));
//			response.setContentType("application/json; charset=utf-8");
//			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getHtmlData2")
	public List<CrawlingBean> getHtmlData2(String t) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Document doc;
		List<CrawlingBean> c=new ArrayList<CrawlingBean>();
		try {			
	
			//for(int i=161501; i<162001;i++) {
			String title2=URLEncoder.encode(t, "EUC-KR");
			doc = Jsoup.connect("https://movie.naver.com/movie/search/result.nhn?section=movie&query="+title2).get();
			Elements pages = doc.select(".search_list_1 li");
			String title="";
			String genre1="";
			String genre2="";
			String nation="";
			String runningtime="";
			String release="";
			String director="";
			String actor="";
			String actor2="";

			if("".equals(pages.toString())) {
				//System.out.println(1);	
			}else {
				for(int i=0;i<pages.size();i++) {
				Elements pages2 = doc.select(".search_list_1 li").eq(i).select("dt").eq(0);

				for(Element headline : pages2) {
					title=headline.getElementsByTag("a").eq(0).text();

					break;
				}
				pages2 = doc.select(".search_list_1 li").eq(i).select(".etc").eq(0);
					String temp=pages2.text();
		
					//String genre=headline.getElementsByTag("span").eq(0).text();
					String[] temps=temp.split("\\|");
					if(temps.length<=3) continue;
					String genre=temps[0];

					String[] genres=genre.split(",");
					if(genres.length>1) {
						genre1=genres[0];
						genre2=genres[1];
					}else {
						genre1=genres[0];
						genre2="";
					}
					nation=temps[1];//headline.getElementsByTag("span").eq(1).text();
					runningtime=temps[2];//headline.getElementsByTag("span").eq(2).text();
//					if(runningtime.length()<2) {
//						//continue;
//					}
					runningtime=runningtime.substring(0, runningtime.length()-1);
					release=temps[3];//headline.getElementsByTag("span").eq(3).text();
//					if(release.equals("") || release.length()<3) {
//						//continue;
//					}else {
//					release=release.substring(0, release.length()-2);
//					}
////				}
					pages2 = doc.select(".search_list_1 li").eq(i).select(".etc").eq(1);
				for(Element headline : pages2) {
					director=headline.getElementsByTag("a").eq(0).text();//headline.getElementsByTag("dd").eq(1).text();
					break;
				}
				pages2 = doc.select(".search_list_1 li").eq(i).select(".etc").eq(1);
				System.out.println(pages2);
				for(Element headline : pages2) {
					actor=headline.getElementsByTag("a").eq(1).text();//headline.getElementsByTag("dd").eq(1).text();
					actor2=headline.getElementsByTag("a").eq(2).text();
					break;
				}
				System.out.println(title+""+genre1+genre2+""+nation+runningtime+release+director+actor+actor2);
				c.add(new CrawlingBean(title, genre1, genre2, nation, runningtime, release,director));

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	
}

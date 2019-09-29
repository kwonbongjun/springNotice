package com.java.web.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CrawlingService implements CrawlingServiceInterface {
	@Autowired
	CommonDaoInterface cdi;
	
	@Autowired
	SqlSession s;

	@Override
	public HashMap<String, Object> getHtmlData(HashMap<String, Object> paramsMap) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Document doc;
		try {
			for(int i=161501; i<163001;i++) {
			doc = Jsoup.connect("https://movie.naver.com/movie/bi/mi/basic.nhn?code="+i).get();
			Elements pages = doc.select(".mv_info .step4 em");
			String title="";
			String genre1="";
			String genre2="";
			String nation="";
			String runningtime="";
			String release="";
			String director="";
			if("".equals(pages.toString())) {
				//System.out.println(1);	
			}else {
				Elements pages2 = doc.select(".mv_info .h_movie");
				for(Element headline : pages2) {
					title=headline.getElementsByTag("a").eq(0).text();
					break;
				}
				pages2 = doc.select(".mv_info dd p span");
//				for(Element headline : pages2) {
					String genre=pages2.eq(0).text();
					//String genre=headline.getElementsByTag("span").eq(0).text();
					String[] genres=genre.split(",");
					if(genres.length>1) {
						genre1=genres[0];
						genre2=genres[1].trim();
					}else {
						genre1=genres[0];
						genre2="";
					}
					nation=pages2.eq(1).text();//headline.getElementsByTag("span").eq(1).text();
					runningtime=pages2.eq(2).text();//headline.getElementsByTag("span").eq(2).text();
					if(runningtime.length()<2) {
						continue;
					}
					runningtime=runningtime.substring(0, runningtime.length()-1);
					release=pages2.eq(3).text();//headline.getElementsByTag("span").eq(3).text();
					if(release.equals("") || release.length()<3) {
						continue;
					}else {
					release=release.substring(0, release.length()-2);
					}
//				}
				pages2 = doc.select(".mv_info dd");
//				for(Element headline : pages2) {
					director=pages2.eq(0).text();//headline.getElementsByTag("dd").eq(1).text();
//					break;
//				}
				System.out.println(title+""+genre1+genre2+""+nation+runningtime+release+director);
				CrawlingBean c = new CrawlingBean(title, genre1, genre2, nation, runningtime, release,director);
				s.insert("test.crawling",c);
//				pages2 = doc.select(".mv_info dd");
//				System.out.println(pages2);
//				for(Element headline : pages2) {
//					row=new HashMap<String, Object>();
//					for ( String key : cb.getColumns().keySet() ) {
//						String tit = headline.select(cb.getColumns().get(key).toString()).text();
//						row.put(key, tit);
//					}
//					paramList.add(row);
//					System.out.println(paramList.get(i++));
//				}
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		List<HashMap<String, CrawlingBean>> crawlingList=getList(paramsMap);
//		//System.out.println(crawlingList.size());
//		for(int r=0;r<crawlingList.size();r++) {
//			HashMap<String, CrawlingBean> map = crawlingList.get(r);
//			HashMap<String, Object> stateMap = new HashMap<String, Object>();
//			int step=2;
//			
//			//if(map.containsKey("step"+step)) {
//				List<HashMap<String, Object>> paramList=(getHTMLParser(map.get("step1")));
//			//}
//			int tot = 0;
//			int del = 0;
//			HashMap<String, Object> paramMap1 = null;
//			HashMap<String, Object> paramMap2 = null;
//			
//			for(int i = 0; i < paramList.size(); i++) {
//				paramMap1 = new HashMap<String, Object>();
//				paramMap2 = new HashMap<String, Object>();
//				
//				paramMap1.put("queryType", map.get("step0").getQueryType());
//				paramMap1.put("queryTarget", map.get("step0").getQueryTarget());
//				paramMap1.put("params", paramList.get(i));
//				
//				paramMap2.put("queryType", map.get("step1").getQueryType());
//				paramMap2.put("queryTarget", map.get("step1").getQueryTarget());
//				paramMap2.put("params", paramList.get(i));
//				del += (int) cdi.commonDB(paramMap1).get("result");
//				tot += (int) cdi.commonDB(paramMap2).get("result");
//				
//				while(map.containsKey("step"+step)) {
//					paramMap2.put("queryType", map.get("step"+step).getQueryType());
//					paramMap2.put("queryTarget", map.get("step"+step).getQueryTarget());
//					paramMap2.put("params", paramList.get(i));
//					step++;
//					del += (int) cdi.commonDB(paramMap1).get("result");
//					tot += (int) cdi.commonDB(paramMap2).get("result");
//				}
//
//			}
//			stateMap.put("TotalCount", paramList.size());
//			stateMap.put("InsertCount", tot);
//			stateMap.put("DeleteCount", del);
//			resultMap.put("crawling" + (r + 1),stateMap);
//		}
//		
//		//System.out.println(resultMap.toString());
		return resultMap;
	}

//	public List<HashMap<String, CrawlingBean>> getList(HashMap<String, Object> paramsMap) {
//		List<HashMap<String, CrawlingBean>> crawlingList = new ArrayList<HashMap<String, CrawlingBean>>();
//		HashMap<String, CrawlingBean> crawlingMap = null;
//		HashMap<String,Object> columns=null;
//		List<String> list=new ArrayList<String>();
//		crawlingMap = new HashMap<String, CrawlingBean>();
//		columns = new HashMap<String,Object>();
//		columns.put("tit", ".tit");
//		columns.put("date", ".date");
//		columns.put("clearfix", ".clearfix");
//		String page="1";
//		try {
//		crawlingMap.put("step1", new CrawlingBean("select", "test2.insert", "http://gdu.co.kr/process/process_010100.html?page=1", ".process-list .first_li", columns, ".paging-area.mt_30 li"));
//			
//		Document doc=Jsoup.connect(crawlingMap.get("step1").getUrl()).get();
//		Elements pages = doc.select(".paging-area.mt_30 li");
//		System.out.println(pages);
//		for(Element page2 : pages) {
//			list.add(page2.text());
//			//System.out.println(page2.text().toString());
//			//row.put(key, tit);
//		}
//		for(int i=2; i< list.size()-1;i++) {
//			//System.out.println(list.get(i));
//			crawlingMap.put("step"+list.get(i), new CrawlingBean("insert", "test2.select", "http://gdu.co.kr/process/process_010100.html?page="+list.get(i), ".process-list .first_li", columns, ".paging-area.mt_30 li"));
//		}
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//
//			
//			//System.out.println(page.select("li:eq(1), li:eq(2)").text().toString());
//			
//			
//			
//		crawlingMap.put("step0", new CrawlingBean("delete", "test2.delete", "", "", columns, ""));
//		
//
//		crawlingList.add(crawlingMap);
//		
//		System.out.println("크롤링 리스트 : " + crawlingList.size());	
//		return crawlingList;
//	}
//	
//	
//	public List<HashMap<String, Object>> getHTMLParser(CrawlingBean cb) {
//		List<HashMap<String, Object>> paramList=new ArrayList<HashMap<String,Object>>();
//		HashMap<String, Object> row;
//		try {
//			int i=0;
//			Document doc=Jsoup.connect(cb.getUrl()).get();
//			Elements newHeadlines=doc.select(cb.getTarget());
//			for(Element headline : newHeadlines) {
//				row=new HashMap<String, Object>();
//				for ( String key : cb.getColumns().keySet() ) {
//					String tit = headline.select(cb.getColumns().get(key).toString()).text();
//					row.put(key, tit);
//				}
//				paramList.add(row);
//				System.out.println(paramList.get(i++));
//			}
//
//		}
//		catch(Exception e){
//			
//		}
//		return paramList;
//	}
//	
//	@Autowired
//	TestDaoInterface tdi;
//	public void setData() {
//		List<HashMap<String, Object>> paramList=getData();
//		int tot=0;
//		for(int i=0;i<paramList.size();i++) {
//			tot+=tdi.setData(paramList.get(i));
//		}
//
//		System.out.println(tot);
//	}
	
}

package com.java.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.web.bean.Movie;
import com.java.web.dao.NoticeDaoInterface;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class NaverAPI {
	@Autowired
	SqlSession s;
	@Autowired
	NoticeDaoInterface ndi;
	public Movie[] naverMovie (String search,String sdate, String edate, String  nation) {
		String urlAddress;
		Movie[] movie=null;
		try {
			System.out.println(search);
			if(sdate==null) {
				sdate="2000";
			}
			if(edate==null) {
				edate="2019";
			}
			System.out.println(sdate+edate+nation);
			urlAddress = "https://openapi.naver.com/v1/search/movie.json"
					+ "?query="+URLEncoder.encode(search,"UTF-8")+"&yearfrom="+sdate+"&yearto="+edate;
			//+"\"" +"\""
			if(nation!=null) {
				urlAddress+="&country="+nation;
			}

			URL url = new URL(urlAddress);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			 
			conn.setRequestMethod("GET");
			//conn.setRequestProperty("query", URLEncoder.encode(search,"UTF-8"));
			//System.out.println("at"+at);
			//conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("X-Naver-Client-Id", "GToteGS4spO1kZbI1BT9");
			conn.setRequestProperty("X-Naver-Client-Secret", "_uWb8xA2uB");
			//System.out.println("responsecode"+conn+conn.getInputStream());
			if(conn.getResponseCode()!=200) return null;
			InputStream input = conn.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(inputReader);
			
			String line="";
			String result="";
			while((line=br.readLine())!=null) {
				result+=line;
				System.out.println(line);
			}
			
			JSONObject jtoken = JSONObject.fromObject(result);
			JSONArray ma=JSONArray.fromObject(jtoken.get("items"));
			System.out.println("items size"+ma.size());
			if(ma.size()==0) return null;
			movie = new Movie[ma.size()];
			for(int i = 0;i<ma.size();i++) {

			JSONObject mo=JSONObject.fromObject(ma.get(i));
//			System.out.println(accesstoken);
//			JSONArray documents=JSONArray.fromObject(jtoken.get("documents"));
			
//			InputStream imageInput = new FileInputStream(path+fileName+ext);
//			OutputStream output = response.getOutputStream();
//			IOUtils.copy(input, output);
//			response.setHeader("content-Disposition", "attachment;filename=\""+(originalFilename)+"\"");
			String title=(String)mo.get("title");
			System.out.println(title);
			title=title.replace("<b>", "");
			title=title.replace("</b>", "");
			
			
			String director=(String)mo.get("director");
			director=director.replace("|", "");
			String pubdate=(String)mo.get("pubDate");
			movie[i] = new Movie((String) mo.get("image"), title,
					director,(String) mo.get("actor"),0/*(int) mo.get("userRating")*/,0,pubdate);
			
	
			}
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

		return movie;
	}
}

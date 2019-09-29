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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.web.bean.Movie;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class NaverAPI {
	public Movie naverMovie (String search) {
		String urlAddress;
		Movie movie=null;
		try {
			System.out.println(search);
			urlAddress = "https://openapi.naver.com/v1/search/movie.json"
					+ "?query="+URLEncoder.encode(search,"UTF-8")+"&yearfrom=2000&yearto=2019";
			//+"\"" +"\""
			
			URL url = new URL(urlAddress);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			 
			conn.setRequestMethod("GET");
			//conn.setRequestProperty("query", URLEncoder.encode(search,"UTF-8"));
			//System.out.println("at"+at);
			//conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("X-Naver-Client-Id", "GToteGS4spO1kZbI1BT9");
			conn.setRequestProperty("X-Naver-Client-Secret", "_uWb8xA2uB");
			
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
			JSONObject mo=JSONObject.fromObject(ma.get(0));
//			System.out.println(accesstoken);
//			JSONArray documents=JSONArray.fromObject(jtoken.get("documents"));
			
//			InputStream imageInput = new FileInputStream(path+fileName+ext);
//			OutputStream output = response.getOutputStream();
//			IOUtils.copy(input, output);
//			response.setHeader("content-Disposition", "attachment;filename=\""+(originalFilename)+"\"");
			String title=(String)mo.get("title");

			title=title.replace("<b>", "");
			title=title.replace("</b>", "");
			movie = new Movie((String) mo.get("image"), title,
					(String) mo.get("director"),(String) mo.get("actor"),(String) mo.get("userRating"));
			
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

		return movie;
	}
}

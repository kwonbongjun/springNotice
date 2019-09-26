package com.java.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class DaumAPI {
	public void getsearchAPI (String search) {
		String urlAddress;
		String path="D:\\workspace\\data"; //C:\\Resources\\"; D:\\workspace\\data"
		File f = new File(path);
		File newfile=new File(path+"\\"+search+".txt");
		FileOutputStream os=null;
		if(!f.isDirectory()) {
			f.mkdirs();
		}
		if(f.exists()) {
			f.delete();
		}
		try {
			os = new FileOutputStream(newfile,true);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}


		for(int j=1;j<=10;j++) {
		try {
			urlAddress = "https://dapi.kakao.com/v2/search/blog" //web blog
					+ "?query="+URLEncoder.encode(search,"UTF-8")
					+"&page="+j;

		URL url = new URL(urlAddress);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		 
		conn.setRequestMethod("GET");
		//conn.setRequestProperty("query", URLEncoder.encode(search,"UTF-8"));
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Authorization", "KakaoAK ed94698d2dd2bbca37dbb1ad2cd5ae87");

		System.out.println(conn);
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
		JSONObject accesstoken=JSONObject.fromObject(jtoken.get("meta"));
		System.out.println(accesstoken);
		JSONArray documents=JSONArray.fromObject(jtoken.get("documents"));
		
		for(int i=0;i<documents.size();i++) {
			//System.out.println(documents.get(i));
			JSONObject jo=JSONObject.fromObject(documents.get(i));
			System.out.println(jo.get("title"));
			System.out.println(jo.get("contents"));
			
			os.write(jo.get("title").toString().getBytes());
			os.write(jo.get("contents").toString().getBytes());
		}

		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		}
		try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.java.web;

import java.io.BufferedReader;
import java.io.File;
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class DataController {
	@RequestMapping("/collect")
	public void Collect (HttpServletRequest request, HttpServletResponse response) {
		String urlAddress;
		try {
			String search="나쁜 녀석들";
			urlAddress = "https://dapi.kakao.com/v2/search/web"
					+ "?query="+URLEncoder.encode(search,"UTF-8");
			//+"\"" +"\""
			
			URL url = new URL(urlAddress);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			 
			conn.setRequestMethod("GET");
			//conn.setRequestProperty("query", URLEncoder.encode(search,"UTF-8"));
			//System.out.println("at"+at);
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
			}
			
			JSONObject jtoken = JSONObject.fromObject(result);
			JSONObject accesstoken=JSONObject.fromObject(jtoken.get("meta"));
			System.out.println(accesstoken);
			JSONArray documents=JSONArray.fromObject(jtoken.get("documents"));
			
			String path="C:\\Resources\\";//D:\\workspace\\resources\\";
			File f = new File(path);
			OutputStream os = new FileOutputStream(new File(path+search+".txt"));
			if(!f.isDirectory()) {
				f.mkdirs();
			}
			for(int i=0;i<documents.size();i++) {
				//System.out.println(documents.get(i));
				JSONObject jo=JSONObject.fromObject(documents.get(i));
				System.out.println(jo.get("title"));
				System.out.println(jo.get("contents"));
				os.write(jo.get("title").toString().getBytes());
				os.write(jo.get("contents").toString().getBytes());
			}
			os.close();
//			byte[] data=file.getBytes();
//			String path="C:\\Resources\\";//D:\\workspace\\resources\\";
//			File f = new File(path);
//			if(!f.isDirectory()) {
//				f.mkdirs();
//			}
//			OutputStream os = new FileOutputStream(new File(path+fileName+ext));
//			os.write(data);
//			os.close();
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}
}

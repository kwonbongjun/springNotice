package com.java.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.language.DaitchMokotoffSoundex;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.web.bean.Movie;
import com.java.web.bean.UserMovie;
import com.java.web.hadoop.JobMap;
import com.java.web.hadoop.JobReducer;
import com.java.web.service.NoticeServiceInterface;
import com.java.web.util.CrawlingBean;
import com.java.web.util.CrawlingController;
import com.java.web.util.DaumAPI;
import com.java.web.util.NaverAPI;
import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider.Text;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class DataController {
	@RequestMapping("/search")
	public String search (HttpServletRequest request, HttpServletResponse response) {
		return "Analysis";
	}
	@Autowired
	NoticeServiceInterface nsi;
	
	@RequestMapping("/collect")
	public String Collect (HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String search=request.getParameter("search");
			System.out.println(search);
			String sdate = request.getParameter("sdate");
			String edate = request.getParameter("edate");
			String nation = request.getParameter("nation");
			String director = request.getParameter("director");
			System.out.println(sdate+" "+ edate+" "+ nation+" " + director +"1111");
			NaverAPI n = new NaverAPI(); 
			Movie[] m = n.naverMovie(search, sdate, edate, nation);
			if(director!=null) {
				search=director+" "+search;
			}
//			

				//크롤링	
					CrawlingController cc = new CrawlingController();
					List<CrawlingBean> c = cc.getHtmlData2(search);
					for(int j=0;j<c.size();j++) {
						Integer m3 = nsi.existMovie(new Movie(c.get(j).getTitle(),c.get(j).getDirector()));
						System.out.println(m3);
						if(m3==0) {
							nsi.crawling(c.get(j));
						}
					}

			DaumAPI da = new DaumAPI();
			da.getsearchAPI(search);

			String localStr= "/home/kbj"; //"C:\\Resources\\" "D:\\workspace\\data"
			String hadoopStr="/input/data/";
			Configuration conf = new Configuration();
			Configuration hadoopConf = new Configuration();
			hadoopConf.set("fs.defaultFS", "hdfs://Name:9000");  //"hdfs://192.168.3.34:9000"
			Path localPath = new Path(localStr);
			Path hadoopPath = new Path(hadoopStr);

			FileSystem localSystem=FileSystem.getLocal(conf);
			FileSystem hadoopSystem=FileSystem.get(hadoopConf);
			FSDataInputStream fsis = localSystem.open(new Path(localPath+"/"+search+".txt")); //\\
			FSDataOutputStream fsos = hadoopSystem.create(new Path(hadoopPath+"/a.txt"));
			int byteRead=0;
			while((byteRead=fsis.read())>0) {
				fsos.write(byteRead);
			}
			fsis.close();
			fsos.close();
			
			Analysis a = new Analysis();
			String[] str = a.mapReducer();
			request.setAttribute("movie", m);
			request.setAttribute("data", str);
			
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		

		return "Analysis";
	}
	
	@RequestMapping("/setstar")
	public void setstar (HttpServletRequest request, HttpServletResponse response) {
		if(request.getParameter("user_id")!=null && request.getParameter("m_no")!=null) {
			String user_id = request.getParameter("user_id");
			String title = request.getParameter("m_no");
			String review = request.getParameter("review");
			String director=request.getParameter("director");
			System.out.println(director);
			//int m_no = nsi.titleidmapping(title);
			int m_no=nsi.tdidmapping(new Movie(title,director));
			System.out.println("1111"+user_id+m_no);
			UserMovie um = new UserMovie(user_id, m_no, 0, 0, 'N',review);

			System.out.println(nsi.isSetScore(um));
			if(nsi.isSetScore(um)!=0 && m_no!=0) {
				request.setAttribute("isSetScore",true);
			}else {
				nsi.insertUserMovie(um);
				System.out.println("2");
			}
		}
		
		String str=request.getParameter("star");
		String user_id=request.getParameter("user_id");
		String title = request.getParameter("m_no");
		String director=request.getParameter("director");
		//int m_no = nsi.titleidmapping(title);
		int m_no=nsi.tdidmapping(new Movie(title,director));
		int star=Integer.parseInt(str);
		UserMovie um = new UserMovie(user_id, m_no, star, 0, 'N',null);
		nsi.setstar(um);
		nsi.setrate(um);
		System.out.println(1);
	}
	
}

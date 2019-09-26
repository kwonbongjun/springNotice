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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.web.bean.Movie;
import com.java.web.hadoop.JobMap;
import com.java.web.hadoop.JobReducer;
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
	@RequestMapping("/collect")
	public String Collect (HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String search=request.getParameter("search");
			System.out.println(search);
			
			NaverAPI n = new NaverAPI(); 
			Movie m = n.naverMovie(search);
			
			DaumAPI da = new DaumAPI();
			da.getsearchAPI(search);

			String localStr= "C:\\Resources\\"; //"C:\\Resources\\" "D:\\workspace\\data"
			String hadoopStr="/input/data/";
			Configuration conf = new Configuration();
			Configuration hadoopConf = new Configuration();
			hadoopConf.set("fs.defaultFS", "hdfs://Name:9000");  //"hdfs://192.168.3.34:9000"
			Path localPath = new Path(localStr);
			Path hadoopPath = new Path(hadoopStr);

			FileSystem localSystem=FileSystem.getLocal(conf);
			FileSystem hadoopSystem=FileSystem.get(hadoopConf);
			FSDataInputStream fsis = localSystem.open(new Path(localPath+"\\"+search+".txt"));
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

}

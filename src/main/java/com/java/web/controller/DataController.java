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

import com.java.web.hadoop.JobMap;
import com.java.web.hadoop.JobReducer;
import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider.Text;

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
			
			String path="D:\\workspace\\data"; //C:\\Resources\\";
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
			Configuration conf = new Configuration();
			Configuration hadoopConf = new Configuration();
			hadoopConf.set("fs.defaultFS", "hdfs://192.168.3.34:9000");
			String localStr="D:\\workspace\\data";
			String hadoopStr="/input/data";
			Path localPath = new Path(localStr);
			Path hadoopPath = new Path(hadoopStr);
			FileSystem localSystem=FileSystem.getLocal(conf);
			FileSystem hadoopSystem=FileSystem.get(hadoopConf);
			if(hadoopSystem.exists(new Path("/output/data"))) {
				hadoopSystem.delete(new Path("/output/data"),true);
			}
			FSDataInputStream fsis = localSystem.open(new Path(localPath+"\\나쁜녀석들.txt"));
			FSDataOutputStream fsos = hadoopSystem.create(new Path(hadoopPath+"/나쁜녀석들.txt"));
			int byteRead=0;
			while((byteRead=fsis.read())>0) {
				fsos.write(byteRead);
			}
			
			Job job = Job.getInstance(conf, "word");
			job.setJarByClass(DataController.class);
			job.setMapperClass(JobMap.class);
			job.setCombinerClass(JobReducer.class);
			job.setReducerClass(JobReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputKeyClass(IntWritable.class);
			FileInputFormat.addInputPath(job, new Path(hadoopPath+"/나쁜녀석들.txt"));
			FileOutputFormat.setOutputPath(job, new Path("/output/data"));
			job.waitForCompletion(true);

			
			PrintWriter pw = response.getWriter();
			String targetPath="/output/data/part-r-00000";
			if(hadoopSystem.exists(new Path(targetPath))) {
				fsis = hadoopSystem.open(new Path(targetPath));
				byteRead=0;
				while((byteRead=fsis.read())>0) {
					pw.write(byteRead);
				}
			}
			fsis.close();
			fsos.close();
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

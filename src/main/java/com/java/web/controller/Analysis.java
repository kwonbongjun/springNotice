package com.java.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonObject;
import com.java.web.hadoop.JobMap;
import com.java.web.hadoop.JobReducer;

import net.sf.json.JSONObject;


@Controller
public class Analysis {
	@RequestMapping("/analysis")
	public String mapReducer(HttpServletRequest req) throws IOException {
	try {
	Configuration conf = new Configuration();
	Configuration hadoopConf = new Configuration();
	hadoopConf.set("fs.defaultFS", "hdfs://Name:9000");  //"hdfs://192.168.3.34:9000"
	String localStr= "C:\\Resources\\"; //"C:\\Resources\\" "D:\\workspace\\data"
	String hadoopStr="/input/data/a.txt";
	Path localPath = new Path(localStr);
	Path hadoopPath = new Path(hadoopStr);
	FileSystem localSystem=FileSystem.getLocal(conf);
	FileSystem hadoopSystem=FileSystem.get(hadoopConf);

//	if(hadoopSystem.exists(localPath)) {
//		hadoopSystem.delete(localPath, true);
//	}
	if(hadoopSystem.exists(new Path("/output/data"))) {
		hadoopSystem.delete(new Path("/output/data"),true);
	}

	
	Job job = Job.getInstance(hadoopConf, "word");
	job.setJarByClass(DataController.class);
	job.setMapperClass(JobMap.class);
	job.setCombinerClass(JobReducer.class);
	job.setReducerClass(JobReducer.class);
	job.setMapOutputKeyClass(Text.class);
	job.setMapOutputValueClass(IntWritable.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(IntWritable.class);
	job.setNumReduceTasks(1);
	FileInputFormat.addInputPath(job, hadoopPath);
	FileOutputFormat.setOutputPath(job, new Path("/output/data"));
	job.waitForCompletion(true);

//	System.out.println("1");
//	PrintWriter pw = response.getWriter();
//	String targetPath="/output/data/part-r-00000";
//	if(hadoopSystem.exists(new Path(targetPath))) {
//		fsis = hadoopSystem.open(new Path(targetPath));
//		byteRead=0;
//		while((byteRead=fsis.read())>0) {
//			pw.write(byteRead);
//		}
//	}
	Path targetPath = new Path("/output/data/part-r-00000");
	// 결과 문자열에 담기 위한 변수
	StringBuilder sb = new StringBuilder();
	// 정제 결과 경로에 존재 여부 확인
	if(hadoopSystem.exists(targetPath)){
		// 정제 결과 대상 파일 읽어 오기
		FSDataInputStream fsis = hadoopSystem.open(targetPath);
		String strRead="";
		InputStreamReader isr = new InputStreamReader(fsis);
		BufferedReader br = new BufferedReader(isr);
//		String path="D:\\workspace\\data\\";//"C:\\Resources\\r.csv";
//		OutputStream os = new FileOutputStream(new File(path));
		
		//List<HashMap<String, Object>> resultList= new ArrayList<HashMap<String,Object>>();
		List<JSONObject> resultList= new ArrayList<JSONObject>();
		JSONObject jo = new JSONObject();
		HashMap<String, Object> map= new HashMap<String, Object>();
		String [] array;
		int max=0;
		List<HashMap<String, Object>> resultArray= new ArrayList<HashMap<String,Object>>();
		while((strRead = br.readLine())!= null) { 
			// 정제 결과를 문자열 변수에 담기
			sb.append(strRead);
			array = strRead.split("\t");
			System.out.println(strRead);
//			jo.put(", value)
//			os.write(strRead.getBytes());
			map.put(array[0], Integer.parseInt(array[1]));
			jo.put(array[0], array[1]);
			resultList.add(jo);
			
			resultArray.add(map);
		}
		Collections.sort(resultArray, new Comparator<HashMap<String,Object>>(){
			  public int compare(HashMap<String,Object> map1, HashMap<String,Object> map2){
			    int cnt1 = (int) map1.get("value");
			    int cnt2 = (int) map2.get("value");

			    // int 내림차순
			    return cnt1 > cnt2 ? -1 : cnt1< cnt2 ? 1 : 0;
			    // int 오름차순
			    //return cnt1 < cnt2 ? -1 : cnt1> cnt2 ? 1 : 0;
			  }
			});
		System.out.println(resultArray.get(0));
		
		fsis.close();
//		os.close();

//		System.out.println(jo.toString());
//		req.setAttribute("data", resultArray);
		
	}
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}	
	return "Analysis";
	}

//	@Resource(name="sqlSession2")
//	SqlSession session2;
	
//	@RequestMapping("/analysis2")
//	public String mapReducer2(HttpServletRequest req,Model m) throws IOException {
//		List<HashMap<String, Object>> resultList= session2.selectList("test.get");
//		for(int i=0;i<resultList.size();i++) {
//			System.out.println(resultList.get(i));
//		}
//		m.addAttribute("data",resultList);
//	return "Analysis";
//	}
}

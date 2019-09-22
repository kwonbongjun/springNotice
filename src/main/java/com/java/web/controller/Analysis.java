package com.java.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.web.hadoop.JobMap;
import com.java.web.hadoop.JobReducer;


@Controller
public class Analysis {
	@RequestMapping("/analysis")
	public String mapReducer(HttpServletRequest req) throws IOException {
	try {
	Configuration conf = new Configuration();
	Configuration hadoopConf = new Configuration();
	hadoopConf.set("fs.defaultFS", "hdfs://Name:9000");  //"hdfs://192.168.3.34:9000"
	String localStr= "D:\\workspace\\data\\"; //"C:\\Resources\\" "D:\\workspace\\data"
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
		String path="C:\\Resources\\r.csv";
		OutputStream os = new FileOutputStream(new File(path));
		
		while((strRead = br.readLine())!= null) { 
			// 정제 결과를 문자열 변수에 담기
			sb.append(strRead);
			os.write(strRead.getBytes());
		}
		fsis.close();
		os.close();
		req.setAttribute("data", sb.toString());
	}
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}	
	return "Analysis";
	}

}

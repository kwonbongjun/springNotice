package com.java.web.hadoop;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class JobMap extends Mapper<Object, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		StringTokenizer st = new StringTokenizer(value.toString());
//		Pattern p = Pattern.compile("(^[0-9]*$)");
		String []cols=value.toString().split(" ");
        for(int i=0;i<(cols.length) - 1 ;i++)
        {
        	String temp=cols[i]+cols[i+1];
        	 if(temp.contains("<b>") || temp.contains(".") || temp.contains(",") || temp.contains("?")
 	        		|| temp.contains("&") || temp.contains("</b>") || temp.contains(":​​") || temp.contains("영화")
 	        		|| temp.contains("무비") || temp.contains("-") || temp.contains("\u003a") || temp.contains("/")
 	        		|| temp.contains("·") || temp.contains("감독") || temp.contains("출연") || temp.contains("줄거리")
 	        		|| temp.contains("개봉") || temp.contains("후기") || temp.contains("주연")){
//                 System.out.println("문자열 있음!");
                 continue;
 	        }else if(temp.contains("\u200b") || temp.matches(".다$") || temp.matches(".고$") || temp.matches(".는$")
 	        		|| temp.matches(".러$") || temp.matches(".더$") || temp.matches(".") || temp.matches(".이$")
 	        		|| temp.matches(".은$") || temp.matches(".의$") || temp.matches("..이$") || temp.matches("..은$")
 	        		) {
 	        	System.out.println("유니코드있음!");
 	        	continue;
 	        }else if(temp.contains("바로") || temp.contains("너무") || temp.contains("다시") || temp.contains("그리고")
 	        		||temp.contains("기자")) {
 	        	continue;
 	        }
            word.set(cols[i]+" "+cols[i+1]);
            context.write(word, one);
        }
		while(st.hasMoreElements()) {
			String temp=st.nextToken();
			/*
			 * if(temp.matches(".*[0-9].*")) { System.out.println("숫자 있음!"); continue; }else
			 */ if(temp.contains("<b>") || temp.contains(".") || temp.contains(",") || temp.contains("?")
	        		|| temp.contains("&") || temp.contains("</b>") || temp.contains(":​​") || temp.contains("영화")
	        		|| temp.contains("무비") || temp.contains("-") || temp.contains("\u003a") || temp.contains("/")
	        		|| temp.contains("·") || temp.contains("감독") || temp.contains("출연") || temp.contains("줄거리")
	        		|| temp.contains("개봉") || temp.contains("후기") || temp.contains("주연")){
//                System.out.println("문자열 있음!");
                continue;
	        }else if(temp.contains("\u200b") || temp.matches(".다$") || temp.matches(".고$") || temp.matches(".는$")
	        		|| temp.matches(".러$") || temp.matches(".더$") || temp.matches(".") || temp.matches(".이$")
	        		|| temp.matches(".은$") || temp.matches(".의$") || temp.matches("..이$") || temp.matches("..은$")
	        		) {
//	        	System.out.println("유니코드있음!");
	        	continue;
	        }else if(temp.contains("바로") || temp.contains("너무") || temp.contains("다시") || temp.contains("그리고")
	        	|| temp.contains("기자") || temp.contains("박스오피스")) {
	        	continue;
	        }
			word.set(temp);
			context.write(word, one);
		}
	}
}

package com.java.web.hadoop;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import net.sf.json.JSONObject;

public class JobReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	private IntWritable result = new IntWritable();
	public void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
		int sum = 0;
//		JSONObject jsn = new JSONObject();
//		StringTokenizer st = new StringTokenizer(key.toString());
//		Pattern p = Pattern.compile("(^[0-9]*$)");
//		String []cols=value.toString().split(" ");
//		String temp1=key.toString();
//		array_word[i]=(word.charAt(i));
		for(IntWritable val : values) {
			sum+=val.get();
		}
		result.set(sum);
		context.write(key, result);
	}
}

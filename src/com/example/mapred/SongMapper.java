package com.example.mapred;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import com.opencsv.CSVParser;

public class SongMapper extends Mapper<LongWritable, Text, Text,
Text> {
		 @Override
		 protected void map(LongWritable key, Text value, Context context) throws IOException,
		  InterruptedException {
				

				
		  if (key.get() > 0) {
//		   String[] lines = new CSVParser().parseLine(value.toString());
		   String[] lines = new CSVParser(';').parseLine(value.toString());
           String regex = ".*(?i)"+NameCheck.java+".*";
           Pattern pattern = Pattern.compile(regex);
           Matcher matcher = pattern.matcher(lines[11]);
           //Matcher matcher = pattern.matcher(lines[0]);           
           boolean rs = matcher.find();
           
           Matcher matcher1 = pattern.matcher(lines[50]);
           //Matcher matcher1 = pattern.matcher(lines[1]);
           boolean rs1 = matcher1.find();
           
        
             
             if(rs){
          	  // context.write(new Text(lines[11] + ": " + lines[50]));
          	   context.write(new Text(lines[11]), new Text(lines[50]));
             }
             
          	 if(rs1){
          	   // context.write(new Text(lines[11] + ": " + lines[50]));  
          		context.write(new Text(lines[11]), new Text(lines[50]));  
          	 } 
                
       
		   
		  }
		 }
		}

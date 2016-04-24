package com.example.mapred;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.example.guestbook.Tools;


public class SongsRecommend {
	public static String inputrecord = "";
	public static List<Double> inputFeature = new ArrayList<Double>(); 
	public static List<String>  similar_artists = new ArrayList<String>();
	public static Map<Double, String> recommend = new HashMap<Double, String>();
	
	public static class ReccomendMapper extends Mapper<Text, Text, Text, Text> {
		public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
			Text feature = new Text();
			String line = value.toString();
			String[] terms = line.split(",");
			String filtered = terms[46] + "," + terms[17] + "," + terms[20] + "," + terms[28]; // need to be modified
			
			feature.set(filtered);
			key.set(terms[7]); // artist name as the key
			if (similar_artists.contains(terms[7]))
				if(Double.parseDouble(terms[2]) > 0.2 && Double.parseDouble(terms[3]) > 0.2)
					context.write(key, feature);
		}
	}
	
	public static class RecommendReducer extends Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
	
			Map<String, List<Double>> titleAndFeatures = new HashMap<String, List<Double>>();
			
			for (Text value : values) {
				String[] tmp = value.toString().split(",");
				List<Double> ds = new ArrayList<Double>();
				String title = tmp[0];
				for (int i = 1; i < tmp.length; i ++) {
					ds.add(Double.parseDouble(tmp[i]));
				}
				titleAndFeatures.put(title, ds);
			}
			Tools tool = new Tools();
			
			Set<String> keys = titleAndFeatures.keySet();
			Iterator<String> ite = keys.iterator();
			while(ite.hasNext()) {
				String title = ite.next();
				double val = tool.correlation(titleAndFeatures.get(title), inputFeature);
				if(recommend.size() < 10)
					recommend.put(val, title);
			}
			
				//context.write(key, new Text(result));
			
		}
	}

	// main method of MapReduce program
	public void exe() throws Exception {
		System.out.println("it is starting...");
		Configuration conf = new Configuration();
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", ",");
		Job job = new Job(conf, "Songs Recommendation");
		job.setJarByClass(SongsRecommend.class);
		job.setJobName("TF-IDF");
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setMapperClass(ReccomendMapper.class);
		job.setCombinerClass(RecommendReducer.class);
		job.setReducerClass(RecommendReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		// set the path of input and output file
		FileInputFormat.setInputPaths(job, new Path("/home/kongsiyi/workspace/Example/source/songsclean.csv"));
		FileOutputFormat.setOutputPath(job, new Path("/home/kongsiyi/workspace/Example/source/output.txt"));

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
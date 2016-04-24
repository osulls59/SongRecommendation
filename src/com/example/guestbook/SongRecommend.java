package com.example.guestbook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class SongRecommend {
	public List<String> recommend(String title, String filePath) {
		List<String> similarRecords = new ArrayList<String>();
		String record = findRecord(title.trim(), filePath);
		if (record == "")
			return similarRecords;
		
		similarRecords = this.getSimilarRecords(record, filePath);
		Iterator<String> ite = similarRecords.iterator();
		while(ite.hasNext()) {
			System.out.println(ite.next());
		}
		return similarRecords;
	}
	
	private List<String> getSimilarRecords(String record, String filePath) {
		// TODO Auto-generated method stub
		String line = "";
		BufferedReader br;
		Tools tool = new Tools();
		Map<Double, String> songs = new TreeMap<Double, String>();
		String[] eles = record.split(";");
		List<String> similarRecords = new ArrayList<String>();
		
		String[] stone = (eles[19].substring(1, eles[19].length()-1)).split(",");
		List<Double> f = strList2Double(stone);
		
		try {
			br = new BufferedReader(new FileReader(filePath));
			while( (line = br.readLine()) != null) {
				String[] tmps = line.split(";");
				String title = tmps[50];
				List<Double> tmpf = this.strList2Double((tmps[19].substring(1, tmps[19].length()-1)).split(","));
				if (tmpf.size() == 100 )
					songs.put(tool.correlation(f, tmpf), title);
			}
			System.out.println(songs.size());
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		similarRecords = Map2List(songs);
		return similarRecords;
	}

	private List<String> Map2List(Map<Double, String> songs) {
		// TODO Auto-generated method stub
		List<String> similarRecords = new ArrayList<String>();
		Set<Double> keys = songs.keySet();
		int count = 10;
		if (keys.size() < 10)
			count = keys.size();
		
		Iterator<Double> ite = keys.iterator();
		while(ite.hasNext()) {
			similarRecords.add(songs.get(ite.next()));
			count --;
			if (count == 0)
				break;
		}
		return similarRecords;
	}

	private List<Double> strList2Double(String[] stone) {
		// TODO Auto-generated method stub
		List<Double> d = new ArrayList<Double>();
		if (stone.length < 100) {
			return d;
		}
		for(int i = 0; i < 100; i ++) {
			if (stone[i] != null && stone[i].length() > 0) {
				try {
					d.add(Double.parseDouble(stone[i].trim()));
				} catch (Exception e) {
					d.add(0.0);// or some value to mark this field is wrong. or
								// make a function validates field first ...
				}
			}
		}
		return d;
	}

	private String findRecord(String title, String filePath) {
		String record = "";
		String line = "";
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {
				String[] eles = line.split(";");
				if(title.equalsIgnoreCase(eles[50].trim())) {
					record = line;
					break;
				}
			}
			br.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
		
	}
}

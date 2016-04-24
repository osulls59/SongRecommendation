package com.example.guestbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Tools {
	public static int NUMOFRECORDS = 200; 
	
	//C:\Users\mingyao\Downloads\songsclean.csv
	public void getTestData(String input, String output) {
		BufferedReader br;
		BufferedWriter bw;
		int count = 0;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(input));
			bw = new BufferedWriter(new FileWriter(output));
			while ((line = br.readLine()) != null) {
				bw.write(line);
				bw.newLine();
				count ++;
				if ( count >= NUMOFRECORDS) {
					break;
				} 
			}
			br.close();
			bw.flush();bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public double correlation(List<Double> x, List<Double> y) {
		double coefficient = 0.0;
		
		// calculte the mean of each list
		coefficient = this.cov(x, y) / (this.std(x) * this.std(y));
		return coefficient;
	}
	
	/**
	 * Calculate the mean value of list
	 * @param x List<Double>
	 * @return mean double
	 */
	private double mean(List<Double> x) {
		Iterator<Double> ite = x.iterator();
		double total = 0.0;
		while(ite.hasNext()) {
			double i = ite.next();
			total += i;
		}
		return (total / x.size());
	}
	
	/**
	 * 
	 * @param x
	 * @return
	 */
	private double std(List<Double> x) {
		double mx = this.mean(x);
		double std = 0.0;
		Iterator<Double> ite = x.iterator();
		while(ite.hasNext()) {
			std += Math.pow((ite.next() - mx),2);
		}
		
		return Math.sqrt(std/x.size());
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private double cov(List<Double> x, List<Double> y) {
		double mx = this.mean(x);
		double my = this.mean(y);
		double cov = 0.0;
		
		if (x.size() != y.size()) {
			System.out.println("Error: Mismatched Parameters");
		}
		
		for(int i = 0; i < x.size(); i ++) {
			cov += ((x.get(i) - mx) * (y.get(0) - my));
		}
		
		return (cov / x.size());
	}
}
package com.example.mapred;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class inputString {
	String io = null;
	public void runInput(){
	BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
	String str = null;
//	System.out.println("enter your value");
	try {
		str = rd.readLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	this.io = str;
	}
	

}

package com.example.guestbook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NameCheck {
	public List<String> check(String word, String filePath) {
		List<String> lists = new ArrayList<String>();
		BufferedReader br;
		String line = "";
		
		try {
			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {
				String title = line.split(";")[50].trim();
				int index = title.indexOf(word.trim());
				if (index != -1) {
					lists.add(title);
				}
				if (lists.size() > 5)
					break;
			}
			System.out.println(lists.size());
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists;
	}
}

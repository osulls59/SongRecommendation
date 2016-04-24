package com.example.guestbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;


public class GuestbookServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tools tool = new Tools();
	private SongRecommend sr = new SongRecommend();
	private NameCheck checker = new NameCheck();
	private List<String> similarSongs = new ArrayList<String>();
	private List<String> matchTitle = new ArrayList<String>();
	//SongsRecommend songs=new SongsRecommend();
	//NameCheck name=new NameCheck();
	
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
	  
	  	// Initialization
	  	resp.setContentType("text/html;charset=utf-8");
	  	PrintWriter pw = resp.getWriter();
	  	String title = req.getParameter("title");
	  	String word = req.getParameter("word");
	  	
	  	// Query Similar Result.
	  	if( (title != null) && (title != "")) {
		  	similarSongs = sr.recommend(title.trim(), "C:/Users/mingyao/Downloads/songstest.csv");
		  	System.out.println(similarSongs.size());
		  	// Return query result.
		  	JSONObject json=new JSONObject();
		  	json.put("title", similarSongs);
		  	pw.write(json.toString());
		  	pw.flush();
		    pw.close();
	  	} else if ((word != "") && (word != null)) {
	  		matchTitle = checker.check(word, "C:/Users/mingyao/Downloads/songstest.csv");
	  		JSONObject json=new JSONObject();
		  	json.put("title", matchTitle);
		  	pw.write(json.toString());
		  	pw.flush();
		    pw.close();
	  	}
	    
  }
  
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) 
		  throws IOException{
	  doGet(req, resp);
  }
}

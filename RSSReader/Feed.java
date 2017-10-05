package project;

import java.util.ArrayList;

/**
 * @author Seva Meyer
 * the class represents the feed added by the user
 */
public class Feed{
	private String title;
	private String path;
	private String pubDate;
	private ArrayList<Article> entries = new ArrayList<Article>();
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getPath(){
		return path;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	
	
	public ArrayList<Article> getEntries(){
		return entries;
	}
	
	public void setEntries(ArrayList<Article> entries){
		this.entries = entries;
	}
	public String toString(){
		return "Feed Title: " + title + " Feed Path: " + path + " Feed date: " + pubDate;
	}

}

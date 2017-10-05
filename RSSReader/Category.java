package project;

import java.util.ArrayList;

/**
 * 
 * @author Seva Meyer 
 * the class represents the categories defined by the user
 * 
 */
public class Category {
	private String title;
	private ArrayList<Feed> feeds = new ArrayList<Feed>();

	

	public Category(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
	public ArrayList<Feed> getFeeds() {
		return feeds;
	}
}

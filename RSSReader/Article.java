package project;

/**
 * 
 * @author Rasha Mahdi
 * @modified by Yibing Xue
 * the class represents the entrie of the feed
 */
public class Article {
	
		private String title; //the title of the entrie
		private String description; //the description of the entrie
		private String pubDate; // the publishing date of the entrie
		
		
		public String getTitle(){
			return title;
		}
		
		public void setTitle(String title){
			this.title = title;
		}
		
		public String getDescription(){
			return description;
		}
		
		public void setDescription(String description){
			this.description = description;
		}
	
		public String getPubDate() {
			return pubDate;
		}

		public void setPubDate(String pubDate) {
			this.pubDate = pubDate;
		}
		
		public String toString(){
			return "TITLE: " + title + "\nDESCRIPTION: " + description + "\nPUBLISHING DATE: " + pubDate + "\n\n";	

	}

		

}

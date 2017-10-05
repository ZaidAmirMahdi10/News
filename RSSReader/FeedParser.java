package project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * 
 * @author Seva Meyer
 *
 */
public class FeedParser {
	File file;
	InputStream stream ;
	/**
	 * main constructor taking file path
	 * @param path	the path of the file to parse
	 * @throws Exception
	 */
	public  FeedParser(String path) throws Exception{
		stream = FeedParser.class.getResourceAsStream("/" + path);
		//file = new File("resource/" +path);
	}
	
	/**
	 * the method reads xml file and retrieves data from it
	 * @return <code>article</code> the list of entries
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws FeedException
	 */
	public ArrayList<Article> newArticle() throws IOException, IllegalArgumentException, FeedException{
		XmlReader reader = null;
		Article art = null;
		ArrayList<Article> article = new ArrayList<Article>();
		try {
			 reader = new XmlReader(stream);
			 SyndFeedInput input = new SyndFeedInput();
			 SyndFeed syndF =input.build(reader);
			 
			 for(Iterator i = syndF.getEntries().iterator(); i.hasNext();){
				 art = new Article();
				 SyndEntry entry = (SyndEntry)i.next();
				 art.setDescription(entry.getDescription().getValue().toString());
				 try{
					 art.setPubDate(entry.getPublishedDate().toString());
				 }catch(Exception e){
					 art.setPubDate("Unknown");
				 }
				 art.setTitle(entry.getTitle());
				 article.add(art);
				
			 }
		}finally{
			if(reader!=null)
		    	reader.close();
		    }
		return article;
		}
}

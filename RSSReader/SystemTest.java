package project;

import junit.framework.TestCase;

public class SystemTest extends TestCase{
	
	public SystemTest(String name){
		super(name);
	}
	
	/**
	 * testing the ability of the main
	 * gui frame to run
	 */
	public void testGUI() {
		new TCategoriesAndFeeds();
	}
	
	/**
	 * testing the width of the window
	 */
	public void testFrame(){
		TCategoriesAndFeeds tc = new TCategoriesAndFeeds();
		tc.addCatWindow.setVisible(true);
		assertEquals(900, tc.getWidth());
		assertEquals(650, tc.getHeight());
	}
	
	/**
	 * testing the size of the feeds array list which 
	 * must contain 9 feeds from the database
	 */
	public void testFeeds(){
		TCategoriesAndFeeds tc = new TCategoriesAndFeeds();
		assertEquals(9, tc.feeds.size());
	}
	
	
}

package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.mysql.jdbc.Statement;

/**
 * 
 * @authors Seva Meyer, Yibing Xue 
 * the class created to connect to the database
 * and retrieve data from it using mysql jdbc connector
 * 
 */
public class DatabaseFeed {
	Connection c = null;
	Random r = new Random();
	Category cat;
	private static int userID;

	/**
	 * main constructor providing the connection to the local database
	 */

	public int getUserID() {
		return userID;
	}

	public void setUserID(int id) {
		this.userID = id;
	}

	/**
	 * main constructor to connect to 
	 * the database server
	 */
	public DatabaseFeed() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				c = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/rssDB", "root", "ISrss1313");
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		} catch (ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		}

	}

	/**
	 * 
	 * @param username 	username entered by th user
	 * @param password	password eneterd by the user
	 * @return true 	if the user exists
	 * @return false 	if there is no such user
	 */
	public boolean userExists(String username, String password) {

		try {
			com.mysql.jdbc.Statement s = (Statement) c.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM user");
			while (r.next()) {
				if (r.getString(2).equals(username)
						&& r.getString(3).equals(password)) {
					setUserID(r.getInt(1));
					return true;
				}
			}

		} catch (SQLException e) {
			System.out.println("UserExists: " + e.getMessage());
		}
		return false;
	}

	/**
	 * @param title
	 * @param owner
	 *            the method is intended to add a new category to Category table
	 *            and user ID <code>owner</code> and category ID to
	 *            user_defines_category_ table
	 */
	public void addDBCategory(String title, int owner) {
		try {
			com.mysql.jdbc.Statement s = (Statement) c.createStatement();
			int catID = r.nextInt(1000); //generate a new id for the category
			
			s.executeUpdate("INSERT INTO category VALUES (" + catID + ", '"
					+ title + "')");

			s.executeUpdate("INSERT INTO user_defines_category VALUES ("
					+ r.nextInt(1000) + ", " + owner + ", " + catID + " )");
		} catch (SQLException e) {

			System.out.println("addDBCategory: " + e.getMessage());
		}
	}

	/**
	 * the method to get all user defined categories
	 * 
	 * @param id
	 * @return <code>uc.getCategories()</code> arraylist
	 */
	public ArrayList<Category> getCategoryID(int id) {

		ArrayList<Integer> array = new ArrayList<Integer>(); //an array to store all categories' ids
		User user = new User();

		try {
			com.mysql.jdbc.Statement s = (Statement) c.createStatement();

			ResultSet res = s
					.executeQuery("SELECT category_id FROM user_defines_category WHERE (user_id = "
							+ id + ")");
			while (res.next()) {
				int catID = res.getInt(1);
				array.add(catID);
			}

			Iterator<Integer> it = array.iterator(); //iterator for categories id

			while (it.hasNext()) {
				ArrayList<Integer> fi = new ArrayList<Integer>(); //an array to store all feed ids
				ArrayList<String> ft = new ArrayList<String>();   // an array to store all feed titles
				int itd = it.next();  //next category id
				com.mysql.jdbc.Statement s1 = (Statement) c.createStatement();

				ResultSet res2 = s1
						.executeQuery("SELECT feed_id FROM user_adds_feed_to_category WHERE (category_id = "
								+ itd + " AND user_id = " + id + ")");

				while (res2.next()) {
					fi.add(res2.getInt(1));

				}
				
				Iterator<Integer> it1 = fi.iterator();
				while (it1.hasNext()) {
					ResultSet res3 = s1
							.executeQuery("SELECT title FROM feed WHERE (id = "
									+ it1.next() + ")");
					while (res3.next()) {
						ft.add(res3.getString(1));

					}
				}

				ResultSet res1 = s1
						.executeQuery("SELECT name FROM category WHERE (id = "
								+ itd + ") ");

				while (res1.next()) {

					cat = new Category(res1.getString(1));

					Iterator<String> it4 = ft.iterator();
					while (it4.hasNext()) {
						String ourF = it4.next();
						Iterator<Feed> it3 = getFeeds().iterator();
						while (it3.hasNext()) {
							Feed generalF = it3.next();

							if (ourF.equals(generalF.getTitle())) {
								cat.getFeeds().add(generalF);
							}
						}
					}

					user.getCategories().add(cat); //add a category to user's categories array list

				}
			}
		} catch (SQLException e) {
			System.out.println("GetCategoriesID() " + e.getMessage());
		}
		return user.getCategories();
	}

	/**
	 * 
	 * @return <code>feedsData</code> arraylist containing all feeds stored in
	 *         the database
	 * @throws Exception
	 */
	public ArrayList<Feed> getFeeds() {
		ArrayList<Feed> feedsData = new ArrayList<Feed>();
		Feed feed;
		try {
			com.mysql.jdbc.Statement s = (Statement) c.createStatement();
			ResultSet res = s.executeQuery("SELECT title, path FROM feed");
			while (res.next()) {
				feed = new Feed();
				feed.setTitle(res.getString(1));
				feed.setPath(res.getString(2));
				try {
					FeedParser fp = new FeedParser(res.getString(2));
					for (Iterator<Article> it = fp.newArticle().iterator(); it
							.hasNext();) {
						feed.getEntries().add(it.next());
					}
					feedsData.add(feed);
				} catch (Exception e) {
					System.out.println("File error: " + e.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println("getFeeds(): " + ex.getMessage());
		}
		return feedsData;

	}

	/**
	 * 
	 * @param title		the title of the feed selected
	 * @return <code>grade</code>	the rating of the feed
	 */
	public int getRating(String title) {
		int grade = 0;
		try {
			com.mysql.jdbc.Statement s = (Statement) c.createStatement();
			ResultSet r = s
					.executeQuery("SELECT grade FROM feed WHERE (title = '"
							+ title + "')");
			while (r.next()) {
				grade = r.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("getRating(): " + e.getMessage());
		}

		return grade;

	}

	/**
	 * the method updates the information about
	 * feed's rating
	 * @param <code>title</code> 	the title of the feed
	 */
	public void setRating(String title) {
		int grade = getRating(title) + 1;
		try {
			com.mysql.jdbc.Statement s = (Statement) c.createStatement();
			int u = s.executeUpdate("UPDATE feed SET grade = '" + grade + "' "
					+ " WHERE title = '" + title + "'");
		} catch (SQLException e) {
			System.out.println("setRating(): " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param userid	logged in user's id
	 * @param fTitle	title of the selected feed
	 * @param cTitle	name of the selected category
	 * 
	 * the method adds the information about which feed will be added
	 * to particular category to the database
	 * 
	 */
	public void addFeedToCat(int userid, String fTitle, String cTitle) {
		int fID = 0;	//feed id
		int cID = 0;	//category id
		try {
			com.mysql.jdbc.Statement s = (Statement) c.createStatement();

			ResultSet res = s
					.executeQuery("SELECT id FROM feed WHERE (title = '"
							+ fTitle + "')");
			while (res.next()) {
				fID = res.getInt(1);
			}

			res = s.executeQuery("SELECT id FROM category WHERE (name = '"
					+ cTitle + "')");
			while (res.next())
				cID = res.getInt(1);

			s = (Statement) c.createStatement();
			s.executeUpdate("INSERT INTO user_adds_feed_to_category VALUES ("
					+ r.nextInt(1000) + ", " + userid + ", " + fID + ", " + cID
					+ ")");
		} catch (SQLException ex) {
			System.out.println("addFeedToCat(): " + ex.getMessage());
		}
	}

	/**
	 * the method removes category from the category table in the database
	 * @param userID	user's id
	 * @param category	the name of the category
	 */
	public void removeCategory(int userID, String category) {
		int catID = 0;
		try {
			com.mysql.jdbc.Statement s = (Statement) c.createStatement();
			ResultSet res = s
					.executeQuery("SELECT id FROM category WHERE (name = '"
							+ category + "')");
			while (res.next()) {
				catID = res.getInt(1);
			}
			s.executeUpdate("DELETE FROM category WHERE (name = '" + category
					+ "')");

			s.executeUpdate("DELETE FROM user_defines_category WHERE (user_id = "
					+ userID + " AND category_id = " + catID + ")");

			s.executeUpdate("DELETE FROM user_adds_feed_to_category WHERE (user_id = "
					+ userID + " AND category_id = " + catID + ")");
		} catch (SQLException ex) {
			System.out.println("removeCategpry: " + ex.getMessage());
		}
	}

	/**
	 * the method removes the feed form user's feeds list
	 * @param userID	user's id
	 * @param title		feed's title
	 */
	public void removeFeed(int userID, String title) {
		int id = 0;
		try {
			com.mysql.jdbc.Statement s = (Statement) c.createStatement();
			ResultSet res = s
					.executeQuery("SELECT id FROM feed WHERE (title = '"
							+ title + "')");
			while (res.next()) {
				id = res.getInt(1);
			}
			s.executeUpdate("DELETE FROM user_adds_feed_to_category WHERE (user_id = "
					+ userID + " AND feed_id = " + id + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
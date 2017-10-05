package project;

import java.util.ArrayList;


/**
 * 
 * @author Rasha Mahdi
 * @modified by Yibing Xue
 *
 */
public class User {
	
	private int id;
	private String name;
	private String password;
	
	final ArrayList<Category> categories = new ArrayList<Category>();
	
	public ArrayList<Category> getCategories(){
		return categories;
	}
		
	
	
	/**
	 * Method for getting the id of the user
	 * @return id of the user. 
	 */

	public int getId() {
		return id;
	}
	/**
	 * Method for setting the id for the user
	 * @param	inputId The ID for the user. 
	 */
	public void setId(int inputId) {
		this.id = inputId;
	}
	
	
	/**
	 * Method for getting the users name
	 * @return name of the user. 
	 */

	public String getName() {
		return name;
	}
	/**
	 * Method for setting the name for the user
	 * @param	inputName The name for the user. 
	 */
	public void setName(String inputName) {
		this.name = inputName;
	}
	
	
	
	/**
	 * Method for getting the users password
	 * @return password of the user. 
	 */

	public String getPassword() {
		return password;
	}
	/**
	 * Method for setting the password for the user
	 * @param	inputPassword The password for the user. 
	 */
	public void setPassword(String inputPassword) {
		this.password = inputPassword;
	}
	
	
	
}

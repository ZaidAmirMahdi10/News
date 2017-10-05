package project;

import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import javax.swing.*;

import project.DatabaseFeed;
import project.TCategoriesAndFeeds;
import project.TLogin;

/**
 * 
 * @author Zaid Al-Ogaili
 * main class running the whole application
 * shows the logging in frame
 *
 */
public abstract class TLoginMain extends JFrame {
	
	DatabaseFeed df = new DatabaseFeed();
	public static final String MAIN_PANEL = "main";
	public static final String LOGIN_PANEL = "login";
	private JPanel cards;
	private JPanel contentPane;
	private TLogin loginForm; 
	static TLoginMain f;
	
	public void setGui() {
		try {
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocation(400, 300);
			setResizable(false);
			cards = new JPanel(new CardLayout());
			setContentPane(cards);
			ImageIcon icon = new ImageIcon(TLoginMain.class.getResource("/Our Logo.png"));
			Image image = icon.getImage();
			setIconImage(image);
			loginForm = new TLogin(new Authenticator());
			contentPane = new JPanel();
			cards.add(loginForm, LOGIN_PANEL);
			cards.add(contentPane, MAIN_PANEL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * main method running the frame
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
						f = new TLoginMain() {

						public boolean allow(String username, String pw) throws UnsupportedEncodingException {

							return df.userExists(username, pw);
							
						}
					};
					f.setGui();
					f.pack();
					f.setVisible(true);
					
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public abstract boolean allow(String userName, String password) throws UnsupportedEncodingException;

	/**
	 * 
	 * @author Zaid Al-Ogaili
	 * action listener to check if the input data is correct
	 *
	 */
	private class Authenticator extends AbstractAction {

		public void actionPerformed(ActionEvent e) {
			/*show main application frame*/
			try {
				if (allow(loginForm.getUserName(), loginForm.getPassword())) {
					CardLayout cl = (CardLayout) (cards.getLayout());
					cl.show(cards, MAIN_PANEL);
					TCategoriesAndFeeds me = new TCategoriesAndFeeds();
					me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					me.setVisible(true);
					f.setVisible(false);
					/*show warning message if input is incorrect */
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect username or password!\n" +
							"Please, try again");
				}
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
	}
}
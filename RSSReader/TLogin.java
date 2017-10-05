package project;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * 
 * @author Zaid Al-Ogaili
 * @modified by Rasha Mahdi
 * this class extends JPanel for the logging
 * in frame. The panel contains all GUI components
 * required for logging in
 *
 */
public class TLogin extends JPanel {
	
	private static final String LOGIN_COMMAND = "Log in";
	private JLabel labUserName;
	private JTextField tfUserName;
	private JLabel labPassword;
	private JPasswordField tfPassword;
	private JButton bnLogin;

	ImageIcon imageicon = new ImageIcon(TLogin.class.getResource("/Light blue.jpg"));
	Image image = imageicon.getImage();
	Font font  = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20);
	 
	/*paint image on the panel*/
	public void paintComponent(Graphics f) {
		super.paintComponent(f);
		f.drawImage(image, 0, 0, this);
		
	}
	 
	 /*main constructor */
	public TLogin(AbstractAction loginAction) {
		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		labUserName = new JLabel("User name");
		labUserName.setFont(font);
		labUserName.setForeground(Color.white);
		tfUserName = new JTextField(15);

		labPassword = new JLabel("Password");
		labPassword.setFont(font);
		labPassword.setForeground(Color.white);
		tfPassword = new JPasswordField(15);

		bnLogin = new JButton(loginAction);
		bnLogin.setDefaultCapable(true);
		bnLogin.setContentAreaFilled(false);
		bnLogin.setFont(font);
		bnLogin.setForeground(Color.white);

		String command = (String) loginAction.getValue(Action.ACTION_COMMAND_KEY);

		if ((command == null) || (command = command.trim()).equals("")) {
			command = LOGIN_COMMAND;
		}

		bnLogin.setText(command);

		add(labUserName);
		add(tfUserName);
		add(labPassword);
		add(tfPassword);
		add(bnLogin);

		layout.putConstraint(SpringLayout.WEST, labUserName, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, labUserName, 25,
				SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, tfUserName, 25,
				SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, tfUserName, 20,
				SpringLayout.EAST, labUserName);

		layout.putConstraint(SpringLayout.WEST, labPassword, 20,
				SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, labPassword, 50,
				SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, tfPassword, 50,
				SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, tfPassword, 20,
				SpringLayout.EAST, labUserName);

		layout.putConstraint(SpringLayout.NORTH, bnLogin, 90,
				SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, bnLogin, 130, SpringLayout.WEST,
				labUserName);
		// Set constraints for Container itself
		layout.putConstraint(SpringLayout.SOUTH, this, 100, SpringLayout.SOUTH,
				bnLogin);
				layout.putConstraint(SpringLayout.EAST, this, 100, SpringLayout.EAST,
						tfPassword);
	}


	public String getUserName() {
		return tfUserName.getText();
	}


	public String getPassword() {
		return tfPassword.getText();
	}
}
package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * @author Zaid Al-Ogaili
 * @modified by Rasha Mahdi
 * the class is extending JPanel
 * which is used by TCategoriesAndFeeds class
 */
public class AppPanel extends JPanel {


	/* three panels including categories, feeds and entries */
	private JPanel FirstPanel = new RssImage();
	private JPanel SecondPanel = new BackgroundImage();
	private JPanel ThirdPanel = new JPanel();

	/* text area and scroll pane for entries panel */
	private JTextArea ta = new JTextArea();
	private JScrollPane sp;

	/**
	 * constructor setting features to GUI components
	 */
	public AppPanel() {

		this.setBackground(Color.black);
		this.setPreferredSize(new Dimension(680, 460));
		this.setLayout(new GridLayout(1, 3));

		FirstPanel.setBackground(new Color(0, 112, 100));
		FirstPanel.setLayout(new BoxLayout(FirstPanel, BoxLayout.Y_AXIS));

		SecondPanel.setBackground(new Color(1, 130, 100));
		SecondPanel.setLayout(new BoxLayout(SecondPanel, BoxLayout.Y_AXIS));

		ThirdPanel.setBackground(Color.black);
		ThirdPanel.setLayout(new GridLayout(1, 1));

		getTa().setBackground(new Color(135, 206, 235));
		getTa().setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		getTa().setLineWrap(true);
		getTa().setWrapStyleWord(true);
		getTa().setEditable(false);

		sp = new JScrollPane(getTa());
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		ThirdPanel.add(sp);

		this.add(FirstPanel);
		this.add(SecondPanel);
		this.add(ThirdPanel);

	}

	/**
	 * getters and setters for GUI components
	 */
	public JPanel getFirstPanel() {
		return FirstPanel;
	}

	public JPanel getSecondPanel() {
		return SecondPanel;
	}

	public JPanel getThirdPanel() {
		return ThirdPanel;
	}

	public JTextArea getTa() {
		return ta;
	}

	public void setTa(JTextArea ta) {
		this.ta = ta;
	}

	public JScrollPane getSp() {
		return sp;
	}

	public void setSp(JScrollPane sp) {
		this.sp = sp;
	}

}

/**
 * 
 * @author Rasha Mahdi creating a new JPanel with drawn image
 */
class RssImage extends JPanel {
	Image image;

	public RssImage() {
		ImageIcon imageicon = new ImageIcon(AppPanel.class.getResource("/Light blue.jpg"));
		image = imageicon.getImage();

	}

	public void paintComponent(Graphics f) {
		super.paintComponent(f);
		f.drawImage(image, 0, 0, this);
	}

}

/**
 * 
 * @author Rasha Mahdi creating a new JPanel with drawn image
 */
class BackgroundImage extends JPanel {
	private static final long serialVersionUID = 1L;
	Image image;

	public BackgroundImage() {
		ImageIcon imageicon = new ImageIcon(AppPanel.class.getResource("/Dark blue2.jpg"));
		image = imageicon.getImage();

	}

	public void paintComponent(Graphics f) {
		super.paintComponent(f);
		f.drawImage(image, 0, 0, this);
	}

}

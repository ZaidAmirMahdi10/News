package project;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import javax.swing.*;

/**
 * 
 * @author Zaid Al-Ogaili, Rasha Mahdi
 * modified by Seva Meyer
 * 
 */
public class TCategoriesAndFeeds extends JFrame {

	/**
	 * @author Rahsa Mahdi
	 */
	private DigitPane hour;
	private DigitPane min;
	private DigitPane second;
	private JLabel[] seperator;
	private int tick = 0;

	DatabaseFeed df = new DatabaseFeed();
	static AppPanel appPanel = new AppPanel();

	JFrame addCatWindow = new JFrame(" Adding categories ");
	JFrame addFeedWindow = new JFrame(" Adding Feeds ");
	JFrame removeCatWindow = new JFrame(" Removing categories ");
	JFrame removeFeedWindow = new JFrame("Removing feeds");

	JPanel CatPanel = new RssImage();
	JPanel feedPanel = new BackgroundImage();
	JPanel RemoveCatPanel = new RssImage();
	JPanel removeFeedPanel = new BackgroundImage();

	TextField newCatField = new TextField(20);
	final TextField tf4 = new TextField("              ");

	JButton addCatBut = new JButton(" Add ");
	JButton addFeedBut = new JButton("Add");
	JButton removeCatBut = new JButton("Remove");
	JButton removeFeedBut = new JButton("Remove");
	JButton category; // button represents categories
	JButton feed; // button representing feeds

	JComboBox feedBox = new JComboBox();
	JComboBox catBox = new JComboBox();
	JComboBox usersfeed = new JComboBox();

	static String str;
	static String selectedFeed;
	static String str4;

	static ArrayList<Category> categories; // store all user's category
	ArrayList<Feed> feeds = df.getFeeds(); // store all available feeds
	ArrayList<JButton> Catbuttons = new ArrayList<JButton>();// store all
																// buttons
																// representing
																// categories
	ArrayList<JButton> feedbuttons = new ArrayList<JButton>();// store all
																// buttons
																// representing
																// feeds

	Font font = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20);
	
	ImageIcon icon = new ImageIcon(TLoginMain.class.getResource("/Our Logo.png"));
	Image image = icon.getImage();

	public TCategoriesAndFeeds() {

		
		hour = new DigitPane();
		min = new DigitPane();
		second = new DigitPane();
		seperator = new JLabel[] { new JLabel(":"), new JLabel(":") };
		categories = df.getCategoryID(df.getUserID());
		
		setTitle("Menu");
		setLocation(130, 50);
		setSize(900, 650);
		setResizable(false);
		setIconImage(image);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu OptionsMenu = new JMenu("Options");

		JMenuItem addCatItem = new JMenuItem("Add categories");
		JMenuItem addFeedItem = new JMenuItem("Add feeds");
		JMenuItem removeCatItem = new JMenuItem("Remove categories");
		JMenuItem removeFeedItem = new JMenuItem("Remove feeds");

		/**
		 * @author Rasha Mahdi 
		 * setting digital clock to menu bar
		 */
		menuBar.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;

		menuBar.add(OptionsMenu, c);
		menuBar.add(hour);
		menuBar.add(seperator[0]);
		menuBar.add(min);
		menuBar.add(seperator[1]);
		menuBar.add(second);

		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar cal = Calendar.getInstance();
				hour.setValue(cal.get(Calendar.HOUR_OF_DAY));
				min.setValue(cal.get(Calendar.MINUTE));
				second.setValue(cal.get(Calendar.SECOND));

				if (tick % 2 == 1) {
					seperator[0].setText(" ");
					seperator[1].setText(" ");
				} else {
					seperator[0].setText(":");
					seperator[1].setText(":");
				}
				tick++;
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.start();
		

		OptionsMenu.add(addCatItem);
		OptionsMenu.add(addFeedItem);
		OptionsMenu.addSeparator();
		OptionsMenu.add(removeCatItem);
		OptionsMenu.add(removeFeedItem);
		
		final ActionListener actionListener = new catHandler();
		add(appPanel); // add three main panels to the frame

		/* loading all categories belonging to particular user */
		for (int i = 0; i < categories.size(); i++) {

			category = new JButton(categories.get(i).getTitle());
			category.setFont(font);
			category.setForeground(Color.white);
			category.setContentAreaFilled(false);
			Catbuttons.add(category);
			category.add(Box.createVerticalStrut(18));
			category.addActionListener(actionListener);
			appPanel.getFirstPanel().add(category);
			catBox.addItem(categories.get(i).getTitle());

		}

		/*add all feeds to feed combo box*/
		for (int i = 0; i < feeds.size(); i++) {
			feedBox.addItem(feeds.get(i).getTitle());
		}

		/*the button adding categories*/
		addCatBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				str = newCatField.getText();
				df.addDBCategory(str, df.getUserID()); //update database table
				categories = df.getCategoryID(df.getUserID()); //update categories list
				catBox.addItem(str);
				
				category = new JButton(str);
				category.setContentAreaFilled(false);
				category.setFont(font);
				category.setForeground(Color.white);
				category.add(Box.createVerticalStrut(18));
				category.addActionListener(actionListener);
				
				Catbuttons.add(category);
				addCatWindow.dispose();
				
				appPanel.getFirstPanel().add(category);
				appPanel.getFirstPanel().updateUI();

				newCatField.setText(null);
			}
		});

		/*action listener for adding categories menu item*/
		addCatItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				addCatBut.setFont(font);
				addCatBut.setForeground(Color.white);
				addCatBut.setContentAreaFilled(false);
				
				CatPanel.setPreferredSize(new Dimension(200, 100));
				CatPanel.add(newCatField);
				CatPanel.add(addCatBut);
				
				addCatWindow.setLocation(550, 300);
				addCatWindow.setSize(200, 150);
				addCatWindow.getContentPane().add(CatPanel);
				addCatWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				addCatWindow.pack();
				addCatWindow.setVisible(true);

			}
		});

		/*the action listener for the button adding feeds*/
		addFeedBut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String selectedCat = catBox.getSelectedItem().toString();
				selectedFeed = feedBox.getSelectedItem().toString();
				df.addFeedToCat(df.getUserID(), selectedFeed, selectedCat); // update data in the database
				categories = df.getCategoryID(df.getUserID()); //update categories list
				addFeedWindow.dispose();
			}
		});

		/*action listener for menu item adding feeds*/
		addFeedItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				addFeedBut.setFont(font);
				addFeedBut.setForeground(Color.white);
				addFeedBut.setContentAreaFilled(false);
				
				feedPanel.add(feedBox);
				feedPanel.add(catBox);
				feedPanel.add(addFeedBut);
				
				addFeedWindow.setLocation(550, 300);
				addFeedWindow.setPreferredSize(new Dimension(200, 150));
				addFeedWindow.getContentPane().add(feedPanel);
				addFeedWindow.pack();
				addFeedWindow.setVisible(true);
			}
		});

		/*action listener for removing categories*/
		removeCatItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				
				
				removeCatBut.addActionListener(new ActionListener() {
					Iterator<JButton> itBut = Catbuttons.iterator();
					
					public void actionPerformed(ActionEvent e) {
						str4 = catBox.getSelectedItem().toString();
						df.removeCategory(df.getUserID(), str4);
						catBox.removeItem(str4);

						while (itBut.hasNext()) {
							JButton button = itBut.next();
							if (button.getText().equals(str4)) {
								appPanel.getFirstPanel().remove(button);
							}
						}
						
						appPanel.getFirstPanel().updateUI();
						categories = df.getCategoryID(df.getUserID());
						removeCatWindow.dispose();
					}
				});

				removeCatBut.setFont(font);
				removeCatBut.setForeground(Color.white);
				removeCatBut.setContentAreaFilled(false);
				
				RemoveCatPanel.add(catBox);
				RemoveCatPanel.add(removeCatBut);

				removeCatWindow.setLocation(550, 300);
				removeCatWindow.setPreferredSize(new Dimension(200, 150));
				removeCatWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				removeCatWindow.getContentPane().add(RemoveCatPanel);
				removeCatWindow.pack();
				removeCatWindow.setVisible(true);
			}

		});

		/*action listener for removing feeds menu item*/
		removeFeedItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				
				Iterator<Category> it = categories.iterator();
				while (it.hasNext()) {
					Iterator<Feed> it2 = it.next().getFeeds().iterator();
					while (it2.hasNext()) {
						usersfeed.addItem(it2.next().getTitle());
					}
				}
				removeFeedBut.addActionListener(new ActionListener() {
					Iterator<JButton> itBut = feedbuttons.iterator();
					String selectedF;

					public void actionPerformed(ActionEvent e) {
						selectedF = usersfeed.getSelectedItem().toString();
						df.removeFeed(df.getUserID(), selectedF);
						while (itBut.hasNext()) {
							try {
								JButton b = itBut.next();
								if (b.getText().toString().equals(selectedF)) {
									appPanel.getSecondPanel().remove(b);
									usersfeed.removeItem(selectedF);
								}
							} catch (Exception e1) {
							}
						}
						categories = df.getCategoryID(df.getUserID());
						appPanel.getSecondPanel().updateUI();
						removeFeedWindow.dispose();

					}

				});

				removeFeedBut.setFont(font);
				removeFeedBut.setForeground(Color.white);
				removeFeedBut.setContentAreaFilled(false);
				
				removeFeedPanel.add(usersfeed);
				removeFeedPanel.add(removeFeedBut);

				removeFeedWindow.setPreferredSize(new Dimension(200, 150));
				removeFeedWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				removeFeedWindow.getContentPane().add(removeFeedPanel);
				removeFeedWindow.pack();
				removeFeedWindow.setLocation(550, 300);
				removeFeedWindow.setVisible(true);

			}

		});

	}

	/**
	 * 
	 * action listener class for category button
	 *
	 */
	public class catHandler implements ActionListener {

		public void actionPerformed(ActionEvent ev) {
			appPanel.getSecondPanel().removeAll();
			appPanel.getSecondPanel().updateUI();
			String butName = ev.getActionCommand();

			Iterator<Category> catIt = categories.iterator();
			while (catIt.hasNext()) {
				Category cat = catIt.next();
				if (cat.getTitle().equals(butName)) {
					Iterator<Feed> feedIt = cat.getFeeds().iterator();
					while (feedIt.hasNext()) {

						feed = new JButton(feedIt.next().getTitle());
						feedbuttons.add(feed);
						feed.add(Box.createVerticalStrut(18));
						int grade = df.getRating(feed.getText());
						String text = Integer.toString(grade);
						
						feed.setContentAreaFilled(false);
						feed.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
						feed.setForeground(Color.white);
						feed.setToolTipText("The Rating: " + text); //set the text showing the rating
						
						feed.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Iterator<Feed> fIt = feeds.iterator();
								while (fIt.hasNext()) {
									Feed f = fIt.next();
									if (e.getActionCommand().equals(f.getTitle())) {
										Iterator<Article> aIt = f.getEntries().iterator();
										StringBuilder sb = new StringBuilder();
										while (aIt.hasNext()) {
											sb.append(aIt.next().toString());
										}
										//show entries from the particular feed
										appPanel.getTa().removeAll();
										appPanel.getTa().setText(sb.toString());
									}
								}

							}

						});
						
						/*mouse listener for the feed buttons to show their rating*/
						feed.addMouseListener(new MouseAdapter() {
							String txt = feed.getText();

							public void mouseClicked(MouseEvent ev) {
								if (ev.getClickCount() == 2) {
									df.setRating(txt);
								}

							}
						});
						appPanel.getSecondPanel().add(feed);
						appPanel.getSecondPanel().updateUI();
					}
				}
			}
		}

	}
}
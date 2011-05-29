/**
 * RBWindow.java:<p>
 * A class which encapsulates the functionality of a RecycleBuddy
 * window.
 * 
 * University of Washington, Bothell
 * CSS 360
 * Spring 2011
 * Professor: Valentin Razmov
 * Recycle Buddy Group
 *
 * @author Mark Zachacz
 * @since 5/24/11
 * @latest 5/24/11
 * @version 0.5.01
 * 5/20/11 0.1.01 Added commenting including the change set.
 * 5/20/11 0.3.02 changed constants for screen size from 600x400 to 800x600 - Niko
 * 5/20/11 0.3.03 Made text wrap properly in the text pane.
 * 5/22/11 0.3.04 Made the text on side buttons bigger.
 * 5/23/11 5.0.00 Beta Release - unchanged from build 0.3.04
 * 5/24/11 5.0.01 Made text pane font bigger. Changed the windows colors to two
 * 					different greens.
 * 5/24/11 5.0.03 Added some options for color.
 * 5/29/11 5.0.04 Made the window bigger this should be the max size since if
 * 					it gets any bigger it won't fit on my screen.
 */

package recycleBuddy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class RBWindow extends JFrame {
	
	// Constants for the window size and number of options.
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 700;
	public static final int NUM_OPTIONS = 6;
	
	// References to the window's buttons.
	private RBButton[] options;
	private JButton[] sideOptions;
	private JButton back;
	private JButton home;
	
	// References to the window's main panels and their pane.
	private JPanel mainPan;
	private JPanel bPan;
	private JPanel tPan;
	
	// References to tPan's text and image.
	private JTextArea tPanText;
	private JLabel tPanImage;
	
	// Strings for the panels.
	private static final String BUTTON_PANEL = "button panel";
	private static final String TEXT_PANEL = "text panel";
	
	// Reference to RecycleBuddy's model.
	private RBModel model;
	
	// enum for different button types options.
	enum ButtonTypes {
		HOME,
		BACK,
		OPTION,
		SIDE_OPTION
	}
	
	// consts for dark green
	public static final int DARK_GREEN_R = 0;
	public static final int DARK_GREEN_G = 190;
	public static final int DARK_GREEN_B = 40;
	
	// consts for light green
	public static final int LIGHT_GREEN_R = 0;
	public static final int LIGHT_GREEN_G = 250;
	public static final int LIGHT_GREEN_B = 40;
	
	/**
	 * Constructor
	 * Builds RecycleBuddy's main window.
	 */
	RBWindow() {
		// Set the window's default size and behavior.
		super();
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon temp = new ImageIcon("RecycleBuddy.png");
		this.setIconImage(temp.getImage());
		this.setBackground(new Color(LIGHT_GREEN_R, LIGHT_GREEN_G, LIGHT_GREEN_B));
		this.setTitle("Recycle Buddy");
		
		// Create RecycleBuddy's model.
		model = new RBModel(NUM_OPTIONS, this);
		
		// Set the layout for the window.
		setLayout(new BorderLayout());
		
		// Create panel for the side pane buttons.
		JPanel pPan = new JPanel();
		pPan.setBackground(new Color(DARK_GREEN_R, DARK_GREEN_G, DARK_GREEN_B));
		pPan.setLayout(new GridBagLayout());
		add(pPan, BorderLayout.WEST);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 0;
		
		// Create label for the side pane.
		JLabel pLab = new JLabel();
		pLab.setText("Quick Links");
		pLab.setHorizontalAlignment(JLabel.CENTER);
		pLab.setBackground(new Color(DARK_GREEN_R, DARK_GREEN_G, DARK_GREEN_B));
		pPan.add(pLab, c);
		
		c.weighty = 0.5;
		
		// Add buttons to the side pane.
		sideOptions = new JButton[NUM_OPTIONS];
		for(int i = 0; i < sideOptions.length; i++) {
			sideOptions[i] = new JButton("test");
			sideOptions[i].setFont(new Font("Serif", Font.BOLD, 20));
			sideOptions[i].setBackground(new Color(DARK_GREEN_R, DARK_GREEN_G, DARK_GREEN_B));
			sideOptions[i].addActionListener(new SideOptionListener(model, i));
			c.gridy = i + 1;
			pPan.add(sideOptions[i], c);
		}
		
		// Make the main panel and set it to CardLayout.
		mainPan = new JPanel();
		mainPan.setLayout(new CardLayout());
		add(mainPan, BorderLayout.CENTER);
		
		// Create panel for the option buttons and add it to the main panel.
		bPan = new JPanel();
		bPan.setBackground(new Color(LIGHT_GREEN_R, LIGHT_GREEN_G, LIGHT_GREEN_B));
		bPan.setLayout(new GridLayout(2, 3));
		mainPan.add(bPan, BUTTON_PANEL);
		
		// Add buttons to the options panel.
		options = new RBButton[NUM_OPTIONS];
		for(int i = 0; i < options.length; i++) {
			options[i] = new RBButton("test", "test.png");
			options[i].setBackground(new Color(LIGHT_GREEN_R, LIGHT_GREEN_G, LIGHT_GREEN_B));
			options[i].addActionListener(new OptionListener(model, i));
			bPan.add(options[i], c);
		}
		
		// Create the text panel and add it to the main panel.
		tPan = new JPanel();
		tPan.setBackground(new Color(LIGHT_GREEN_R, LIGHT_GREEN_G, LIGHT_GREEN_B));
		tPan.setLayout(new BorderLayout());
		mainPan.add(tPan, TEXT_PANEL);
		
		// Add text to the text panel.
		tPanText = new JTextArea("This is a test.");
		tPanText.setEditable(false);
		tPanText.setLineWrap(true);
		tPanText.setWrapStyleWord(true);
		tPanText.setFont(new Font("Serif", Font.PLAIN, 20));
		tPan.add(tPanText, BorderLayout.CENTER);
		
		// Add an image to the text panel.
		tPanImage = new JLabel();
		tPanImage.setIcon(new ImageIcon("test.png"));
		tPan.add(tPanImage, BorderLayout.NORTH);
		
		// Create panel for the home and back buttons.
		JPanel pan = new JPanel();
		pan.setBackground(new Color(LIGHT_GREEN_R, LIGHT_GREEN_G, LIGHT_GREEN_B));
		pan.setLayout(new FlowLayout());
		add(pan, BorderLayout.SOUTH);
		
		// Add the home and back buttons.
		back = new JButton("Back");
		back.setBackground(new Color(DARK_GREEN_R, DARK_GREEN_G, DARK_GREEN_B));
		back.addActionListener(new BackListener(model));
		pan.add(back);	
		home = new JButton("Home");
		home.setBackground(new Color(DARK_GREEN_R, DARK_GREEN_G, DARK_GREEN_B));
		home.addActionListener(new HomeListener(model));
		pan.add(home);
		
		// Set the initial state of the window.
		model.setInitialState();
	}
	
	/**
	 * changeView
	 * Depreciated test function for changing views.
	 * 
	 * @param option
	 * @param buttonNum
	 */
	private void changeView(ButtonTypes option, int buttonNum) {
		CardLayout cl = (CardLayout)(mainPan.getLayout());
		if(option == ButtonTypes.HOME) {
			cl.show(mainPan, BUTTON_PANEL);
		}
		else if(option == ButtonTypes.BACK) {
			cl.show(mainPan, TEXT_PANEL);
		}
	}
	
	/**
	 * changeView
	 * Depreciated test function for changing views.
	 * 
	 * @param option
	 */
	private void changeView(ButtonTypes option) {
		changeView(option, 0);
	}
	
	/**
	 * refreshOption
	 * Function for refreshing the contents of one of the windows main
	 * buttons.
	 * 
	 * @param button - The index of the button to be refreshed should be
	 * a value between 0 and 5.
	 * @param text - The text to be displayed on the button.
	 * @param image - File name and path of the image to be displayed 
	 * on the button. If the path is invalid nothing is displayed.
	 * @param active - Should the button be active true yes false no.
	 */
	public void refreshOption(int button, String text, String image, 
			boolean active) {
		options[button].setText(text);
		options[button].setImage(image);
		options[button].setEnabled(active);
	}
	
	/**
	 * refreshSideOption
	 * Function for refreshing the contents of one of the windows side
	 * pane buttons.
	 * 
	 * @param button - The index of the button to be refreshed should be
	 * a value between 0 and 5.
	 * @param active - Should the button be active true yes false no.
	 */
	public void refreshSideOption(int button, String text, boolean active) {
		sideOptions[button].setText(text);
		sideOptions[button].setEnabled(active);
	}
	
	/**
	 * refreshTestPane
	 * Function for refreshing the contents of the windows text pane.
	 * 
	 * @param text - The text to be displayed in the text pane.
	 * @param image - File name and path of the image to be displayed 
	 * in the text pane. If the path is invalid nothing is displayed.
	 */
	public void refreshTextPane(String text, String image) {
		tPanText.setText(text);
		tPanImage.setIcon(new ImageIcon(image));
	}
	
	/**
	 * showButtonPane
	 * Function which switches the windows main view from the text pane
	 * to the button pane.
	 */
	public void showButtonPane() {
		CardLayout cl = (CardLayout)(mainPan.getLayout());
		cl.show(mainPan, BUTTON_PANEL);
	}
	
	/**
	 * showTextPane
	 * Function which switches the windows main view from the button pane
	 * to the text pane.
	 */
	public void showTextPane() {
		CardLayout cl = (CardLayout)(mainPan.getLayout());
		cl.show(mainPan, TEXT_PANEL);
	}
	
}

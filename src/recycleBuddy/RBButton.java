/**
 * RBWindow.java:<p>
 * A class which encapsulates the functionality of a RecycleBuddy
 * main button.
 * 
 * University of Washington, Bothell
 * CSS 360
 * Spring 2011
 * Professor: Valentin Razmov
 * Recycle Buddy Group
 *
 * @author Mark Zachacz
 * @since 5/20/11
 * @latest 5/20/11
 * @version 0.1.01
 * 5/20/11 0.1.01 Added commenting including the change set.
 */

package recycleBuddy;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.GridLayout;


public class RBButton extends JButton {
	
	// Reference to the button's image.
	private JLabel image;
	// Reference to the button's text.
	private JLabel text;
	
	/**
	 * Constructor
	 * Function which creates a new RBButton.
	 * 
	 * @param text - The text to be displayed in the button.
	 * @param imageName - File name and path of the image to be displayed 
	 * on the button. If the path is invalid nothing is displayed.
	 */
	RBButton(String text, String imageName) {
		// Create the button and set its layout. The layout
		// is a grid which is 2 high and one wide.
		super();
		setLayout(new GridLayout(2, 1));
		
		// Add an image to the button.
		image = new JLabel();
		image.setIcon(new ImageIcon(imageName));
		add(image);
		
		// Add text to the button.
		this.text = new JLabel(text);
		this.text.setFont(new Font("Serif", Font.BOLD, 18));
		add(this.text);
	}
	
	/**
	 * setText
	 * Function which changes the text on the button.
	 * 
	 * @param text - The text to be displayed on the button.
	 */
	public void setText(String text) {
		this.text.setText(text);
	}
	
	/**
	 * setImage
	 * Function which changes the image on the button.
	 * 
	 * @param imageName - File name and path of the image to be displayed 
	 * on the button. If the path is invalid nothing is displayed.
	 */
	public void setImage(String imageName) {
		image.setIcon(new ImageIcon(imageName));
	}
	
}

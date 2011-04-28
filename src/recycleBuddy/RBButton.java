package recycleBuddy;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;


public class RBButton extends JButton {
	
	private JLabel image;
	private JLabel text;
	
	RBButton(String text, String imageName) {
		// Create the button and set its layout.
		super();
		setLayout(new GridLayout(2, 1));
		
		// Add an image to the button.
		image = new JLabel();
		image.setIcon(new ImageIcon(imageName));
		add(image);
		
		// Add text to the button.
		this.text = new JLabel(text);
		add(this.text);
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
	public void setImage(String imageName) {
		image.setIcon(new ImageIcon(imageName));
	}
	
}

package recycleBuddy;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;


public class RBButton extends JButton {
	
	RBButton(String image) {
		super();
		setLayout(new GridLayout(2, 1));
		
		JLabel icon = new JLabel();
		icon.setIcon(new ImageIcon(image));
		add(icon);
		
		add(new JLabel("Test"));
	}
	
}

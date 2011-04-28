package recycleBuddy;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class RBWindow extends JFrame {
	
	// Constants for the window size and number of options.
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	public static final int NUM_OPTIONS = 6;
	
	// References to the window's buttons.
	private RBButton[] options;
	private JButton back;
	private JButton home;

	RBWindow() {
		// Set the window's default size and behavior.
		super();
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set the layout for the window.
		setLayout(new BorderLayout());
		
		// Create panel for the option buttons.
		JPanel bPan = new JPanel();
		bPan.setLayout(new GridLayout(2, 3));
		add(bPan, BorderLayout.CENTER);
		
		// Add buttons to the options panel.
		options = new RBButton[NUM_OPTIONS];
		for(int i = 0; i < options.length; i++) {
			options[i] = new RBButton("test", "test.png");
			bPan.add(options[i]);
		}
		
		// Create panel for the home and back buttons.
		JPanel pan = new JPanel();
		pan.setLayout(new FlowLayout());
		add(pan, BorderLayout.SOUTH);
		
		// Add the home and back buttons.
		back = new JButton("Back");
		pan.add(back);	
		home = new JButton("Home");
		pan.add(home);
	}
	
}

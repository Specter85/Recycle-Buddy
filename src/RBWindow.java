package recycleBuddy;

import javax.swing.JFrame;
import javax.swing.JButton;
//import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class RBWindow extends JFrame {
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;

	RBWindow() {
		super();
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		JPanel bPan = new JPanel();
		bPan.setLayout(new GridLayout(2, 3));
		add(bPan, BorderLayout.CENTER);
		bPan.add(new RBButton("test.png"));
		bPan.add(new RBButton("test.png"));
		bPan.add(new RBButton("test.png"));
		bPan.add(new RBButton("test.png"));
		bPan.add(new RBButton("test.png"));
		bPan.add(new RBButton("test.png"));
		
		JPanel pan = new JPanel();
		pan.setLayout(new FlowLayout());
		add(pan, BorderLayout.SOUTH);
		
		JButton back = new JButton("Back");
		pan.add(back);
		
		JButton home = new JButton("Home");
		pan.add(home);
	}
	
}

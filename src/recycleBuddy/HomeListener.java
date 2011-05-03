package recycleBuddy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import recycleBuddy.RBWindow.ButtonTypes;

public class HomeListener implements ActionListener {

	private RBWindow parent;
	
	HomeListener(RBWindow parent) {
		this.parent = parent;
	}
	
	public void actionPerformed(ActionEvent e) {
		parent.changeView(ButtonTypes.HOME);
	}
	
}

package recycleBuddy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import recycleBuddy.RBWindow.DisplayOption;

public class BackListener implements ActionListener {

	private RBWindow parent;
	
	BackListener(RBWindow parent) {
		this.parent = parent;
	}
	
	public void actionPerformed(ActionEvent e) {
		parent.changeView(DisplayOption.SHOW_PARENT);
	}
	
}

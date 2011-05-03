package recycleBuddy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import recycleBuddy.RBWindow.ButtonTypes;

public class SideOptionListener implements ActionListener {
	
	private RBWindow parent;
	private int buttonNum;
	
	SideOptionListener(RBWindow parent, int buttonNum) {
		this.parent = parent;
		this.buttonNum = buttonNum;
	}

	public void actionPerformed(ActionEvent e) {
		parent.changeView(ButtonTypes.SIDE_OPTION, buttonNum);
	}
	
}

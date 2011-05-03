package recycleBuddy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import recycleBuddy.RBWindow.ButtonTypes;

public class OptionListener implements ActionListener {
	
	private RBWindow parent;
	private int buttonNum;
	
	OptionListener(RBWindow parent, int buttonNum) {
		this.parent = parent;
		this.buttonNum = buttonNum;
	}

	public void actionPerformed(ActionEvent e) {
		parent.changeView(ButtonTypes.OPTION, buttonNum);
	}
	
}

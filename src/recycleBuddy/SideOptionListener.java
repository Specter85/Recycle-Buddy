package recycleBuddy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//import recycleBuddy.RBWindow.ButtonTypes;

public class SideOptionListener implements ActionListener {
	
	RBModel model;
	private int buttonNum;
	
	SideOptionListener(RBModel model, int buttonNum) {
		this.model = model;
		this.buttonNum = buttonNum;
	}

	public void actionPerformed(ActionEvent e) {
		model.click(buttonNum + 2);
	}
	
}

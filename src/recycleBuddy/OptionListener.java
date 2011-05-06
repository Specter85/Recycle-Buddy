package recycleBuddy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//import recycleBuddy.RBWindow.ButtonTypes;

public class OptionListener implements ActionListener {
	
	private RBModel model;
	private int buttonNum;
	
	OptionListener(RBModel model, int buttonNum) {
		this.model = model;
		this.buttonNum = buttonNum;
	}

	public void actionPerformed(ActionEvent e) {
		model.click(buttonNum + 2);
	}
	
}

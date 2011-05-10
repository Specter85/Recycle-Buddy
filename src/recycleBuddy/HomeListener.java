package recycleBuddy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import recycleBuddy.RBWindow.ButtonTypes;

public class HomeListener implements ActionListener {

	private RBModel model;
	
	HomeListener(RBModel model) {
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent e) {
		model.click(ButtonTypes.HOME, 0);
	}
	
}

package recycleBuddy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import recycleBuddy.RBWindow.ButtonTypes;

public class BackListener implements ActionListener {

	private RBModel model;
	
	BackListener(RBModel model) {
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent e) {
		model.click(ButtonTypes.BACK, 0);
	}
	
}

/**
 * OptionListener.java:<p>
 * Listener class for RecycleBuddy main buttons.
 * 
 * University of Washington, Bothell
 * CSS 360
 * Spring 2011
 * Professor: Valentin Razmov
 * Recycle Buddy Group
 *
 * @author Mark Zachacz
 * @since 5/20/11
 * @latest 5/23/11
 * @version 0.5.00
 * 5/20/11 0.1.01 Added commenting including the change set.
 * 5/23/11 0.5.00 Beta Release - unchanged from build 0.1.01
 */

package recycleBuddy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// enum for button types.
import recycleBuddy.RBWindow.ButtonTypes;

public class OptionListener implements ActionListener {
	
	// Reference to RecycleBuddy's model.
	private RBModel model;
	// The index of the button the listener is for.
	private int buttonNum;
	
	/**
	 * Constructor
	 * Function which builds a new OptionListener.
	 * 
	 * @param model - Reference to RecycleBuddy's model must
	 * not be null.
	 * @param buttonNum - The index of the button the listener is for.
	 */
	OptionListener(RBModel model, int buttonNum) {
		this.model = model;
		this.buttonNum = buttonNum;
	}

	/**
	 * actionPerformed
	 * Function which handles button clicks.
	 */
	public void actionPerformed(ActionEvent e) {
		model.click(ButtonTypes.OPTION, buttonNum);
	}
	
}

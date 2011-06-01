/**
 * OptionListener.java:<p>
 * Listener class for RecycleBuddy back button.
 * 
 * University of Washington, Bothell
 * CSS 360
 * Spring 2011
 * Professor: Valentin Razmov
 * Recycle Buddy Group
 *
 * @author Mark Zachacz
 * @since 5/20/11
 * @latest 6/1/11
 * @version 1.0.00
 * 5/20/11 0.1.01 Added commenting including the change set.
 * 5/20/11 0.3.02 changed function call from HOME to BACK - Niko
 * 5/23/11 0.5.00 Beta Release - unchanged from build 0.3.02
 * 6/1/11 1.0.00 Final Release - unchanged from build 0.3.02
 */

package recycleBuddy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import recycleBuddy.RBWindow.ButtonTypes;

public class BackListener implements ActionListener {

	// Reference to RecycleBuddy's model.
	private RBModel model;
	
	/**
	 * Constructor
	 * Function which builds a new BackListener.
	 * 
	 * @param model - Reference to RecycleBuddy's model must not
	 * be null.
	 */
	BackListener(RBModel model) {
		this.model = model;
	}
	
	/**
	 * actionPerformed
	 * Function which handles button clicks.
	 */
	public void actionPerformed(ActionEvent e) {
		model.click(ButtonTypes.BACK, 0);
	}
	
}

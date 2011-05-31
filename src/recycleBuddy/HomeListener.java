/**
 * OptionListener.java:<p>
 * Listener class for RecycleBuddy home button.
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

import recycleBuddy.RBWindow.ButtonTypes;

public class HomeListener implements ActionListener {

	// Reference to RecycleBuddy's model.
	private RBModel model;
	
	/**
	 * Constructor
	 * Function which builds a new HomeListener.
	 * 
	 * @param model - Reference to RecycleBuddy's model must not
	 * be null.
	 */
	HomeListener(RBModel model) {
		this.model = model;
	}
	
	/**
	 * actionPerformed
	 * Function which handles button clicks.
	 */
	public void actionPerformed(ActionEvent e) {
		model.click(ButtonTypes.HOME, 0);
	}
	
}

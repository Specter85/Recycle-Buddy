// 5/30/11 - An as-yet unused class to assist in refactoring

package recycleBuddy;

import recycleBuddy.RBWindow.ButtonTypes;

public class RBFormHandler {
	
	
	private RBModel model;
	
	
	// Constructor
	public RBFormHandler(int numDisplays, int numControls, RBWindow refViews) {
		displayAmt = numDisplays;
		controlAmt = numControls;
		view = refViews;
		
		model = new RBModel(controlAmt, view);
		
		model.setInitialState();
		
	}
	
	// VIEWER-CONTROLLER (UI) COMPONENT INTERACTIVE SECTION	
	/**
	 * setInitialState
	 * 
	 * Sends the first display information to be shown.
	 * 
	 * @preconditions 1) Number of display areas is known
	 *   2) Correct command to call displays in Viewer module is known.
	 *   3) Data tree is built.
	 *   
	 * @postcondition Each display of the Viewer module will be set to its
	 *   correct, initialized position.
	 */
	public void setInitialState() {
		// for each display area
		for (int i = 0; i < displayAmt; ++i) {
			// display on the main button and display on a side button			
			if (i < model.getNumSelections()) {
				String[] info = model.getEntry(i);
				
				// unpack the node
				String name, text, img;
				
				name = info[2];
				text = info[1];
				img = info[0];
				
				// call each display in turn and pass it the contents of
				// each RBTreeNode in each of the children of the tree traverser
				view.refreshOption(i, text, img, true);
				
				// ..and set the sidebar displays
				view.refreshSideOption(i, name, true);
			}
			else {
				// display a "disabled" button
				view.refreshOption(i, "", "family.jpg", false);
				
				// ...and set the sidebar displays
				view.refreshSideOption(i, "placeholder: no info", false);
			}
		}
	}
	
	/**
	* click
	*
	* Accepts controller data and sends display information.
	*   
	* @preconditions 1) Number of display areas is known.
	*   2) Correct command to call displays in Viewer module is known.
	*   3) It is understood that:
	*     3a) Control 0 corresponds to HOME
	*     3b) Control 1 corresponds to BACK
	*     3c) Controls 2+ correspond to category selections.
	*   4) Number of displays is equal to number of controls - number
	*     of special controls (aka SPECIALCONTROLOFFSET)
	*   5) Data tree is built
	* 
	* @param whichControl called the display
	* 
	* @postcondition Each display will be set to the correct information.
	*/
	public void click(ButtonTypes selectionType, int whichDisplay) {
		try {
			// VARIABLE DECLARATIONS
			// which (if non-negative) viewer-controller pair sent input
			//int whichDisplay = whichOption - SPECIALCONTROLOFFSET;
			
			// First, go to the correct portion of the tree.
			
			// We want to display the initial data
			if (ButtonTypes.HOME == selectionType) {
				// Go back to the root.
				model.home();
				
				// Reinitialize child offset.
				childOffset = 0;
				
			} // end if HOME
			// We want to display the previous data
			else if (ButtonTypes.BACK == selectionType) {
				// DECLARE VARIABLES												
				// child order of this tree (not available)				
				//int whichChild = treeTraverser.getChildNum();
												
				// probable off-by-one error
				// recalculate offset
				/*
				while ((whichChild - childOffset) > displayAmt) {
					childOffset += displayAmt;
				}*/
									
				// Go back to the parent tree, if it exists
				model.back();
				
			} // end if BACK
			/*
			// We want to display more siblings
			else if (SEEMORE == whichControl) {
				// probable off-by-one error
				childOffset += displayAmt;

			} // end if SEEMORE
			*/
			
			// the side bar displays the root's children
			else if (ButtonTypes.SIDE_OPTION == selectionType){
				// now we are strictly selecting children of the root
				
				model.chooseFromHome(whichDisplay);
				childOffset = 0;
				
				
				// Reset child offset																
			} // end else if side option selection
			
			// We want to display the selected data.
			else if (ButtonTypes.OPTION == selectionType) {
				model.choose(whichDisplay + childOffset);
				childOffset = 0;
				
			} // end else if normal selection
			
			
			// Then, display the information for the number of displays.
			
			// This is where logic to control the difference between
			// the number of displays and number of children should lie.	
			if (model.getNumSelections() > 1) {
				for (int i = childOffset; i < (displayAmt + childOffset); ++i) {
					// get this numbered child of the current node
					if (i < model.getNumSelections()) {
						String[] info = model.getEntry(i);
						
						// unpack the node
						String name, text, img;
						
						name = info[2];
						text = info[1];
						img = info[0];
						
						// make sure we're showing the button pane
						view.showButtonPane();
						
						// call each display in turn and pass it the contents of
						// each RBTreeNode in each of the children of the tree traverser
						view.refreshOption(i, text, img, true);
					}
					else 
					{
						// display a "disabled" button
						view.refreshOption(i, "", "family.jpg", false);
						//view.showTextPane();
					}
				} // next button
			} // end if children
			// else there are no children so display the single view pane
			
			// what we really want:
			/* 
			 * else if (treeTraverser.getChildNum() == 1) {
			 *   ...}
			 */
			else if (model.getNumSelections() == 1){
				// unpack the current node
				String[] info = model.getEntry(0);
				
				// unpack the node
				String name, text, img;
				
				name = info[2];
				text = info[1];
				img = info[0];
				
				// display the single pane
				view.showTextPane();
				
				view.refreshTextPane(text, img);
				
			}
			
			
			// else return to parent
			else {
				model.back();
			}
				
				// Three cases:
				// 1) There are as many children as displays.
				// Solution: perform normally.
				// 2) There are fewer children than there are displays.
				// Solution: disable extra displays.	
				// 3) There are more children than there are displays.
				// Solution: display "see more" information 
	
		}
		catch (Exception e) {
			// add better exception handling
			// focus on out of range exceptions
			// ...and null exceptions
			e.printStackTrace();
		}
	} // end click
	
	
	// PRIVATE MEMBERS
	private int controlAmt; // number of inputs from Controller
	private int displayAmt; // number of displays in Viewer
	private int childOffset; // offset for displaying siblings
	private RBWindow view;
}

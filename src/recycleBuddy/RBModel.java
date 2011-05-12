/**
 * RBModel.java:<p>
 * A managerial class based on MVC architecture that mediates between
 * user input and stored data to pass correct information to the display.
 *
 * University of Washington, Bothell
 * CSS 360
 * Spring 2011
 * Professor: Valentin Razmov
 * Recycle Buddy Group
 *
 * @author Niko Simonson
 * @since 5/3/11
 * @latest 5/11/11
 * @version 0.1.00
 * 5/3/11 0.0.01 - coded constructor and click()
 * 5/5/11 0.0.02 - added childOffset support for more children than displays,
 * 	added full commenting
 * 5/7/11 0.0.03 - changed click() to select(), 
 * 	added RBWindow.ButtonTypes enum to select() params
 *  changed version numbering scheme
 *  started version change log
 * 5/8/11 0.0.04 - added RBWindow reference to constructor
 *  select() now calls refreshOption() of RBWindow
 *  select() can handle fewer children in the data tree than displays
 *  set INITIALFILEPATH to StartZFR.txt
 * 5/9/11 0.0.05 - added "Side Option" selection to select() function
 * 5/11/11 0.0.06 - added null handling for BACK selection of click()
 *  added precondition 5 to click()
 *  added primitive exception handling (needs to be improved) to class
 *  added setInitialState()
 *  added hard stop to BACK option in click()
 * 5/11/11 0.0.07 - remove all null passing
 * 5/11/11 0.1.00 - ZFR candidate passes data to UI
 */

package recycleBuddy;

// enum for select() function
import recycleBuddy.RBWindow.ButtonTypes;
//import java.io.*; // streaming data


public class RBModel {
	// CONSTANT DECLARATIONS
	// Initial path to data. (TO BE DEFINED)
	static String INITIALFILEPATH = "StartZFR.txt";
	// Note - find a better way to do this enumeration:
	/*
	static int HOME = 0; // control requiring root display
	static int BACK = 1; // control requiring parent display
	static int SEEMORE = 2;
	static int SPECIALCONTROLOFFSET = 3; // number of special controls
	*/
	
	// DATA COMPONENT INTERACTIVE SECTION
	// INCLUDES CONSTRUCTOR
	/**
	* RBModel Constructor
	*
	* Creates and builds the root data tree.
	* Points the treeTraverser tree to the root tree.
	* 
	* @preconditions Correct path to a properly-formed Start.txt file is available.
	* 
	* @param The quantity of potential different control inputs.
	*/
	public RBModel(int howManyControls, RBWindow refViews) {
		try {
			// Create a new Recycle Buddy data tree.
			// As the root, its parent is null.
			rootTree = new RBTree(null, 0);
			
			// Build the tree with the file path to the data.
			rootTree.build(INITIALFILEPATH);
			
		
			// Point the tree traverser to the root.
			treeTraverser = rootTree;			
			
			// Set the number of controls.
			//controlAmt = howManyControls;
			
			// Set the number of displays.
			//displayAmt = controlAmt - SPECIALCONTROLOFFSET;
			
			displayAmt = howManyControls;
			
			
			// Initialize child offset.
			childOffset = 0;
			
			// create reference to view
			view = refViews;
		}
		catch (Exception e) {
			// add better exception handling
			// focus on io exceptions
			
			e.printStackTrace();
		}
	} // end RBModel
	
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
			if (i < treeTraverser.getChildNum()) {
				RBTree treeDisplayInfo = treeTraverser.getChild(i);
				RBTreeNode nodeDisplayInfo = treeDisplayInfo.getThisNode();
				
				// unpack the node
				String name, text, img;
				
				name = nodeDisplayInfo.getTitle();
				text = nodeDisplayInfo.getText();
				img = nodeDisplayInfo.getImagePath();
				
				// call each display in turn and pass it the contents of
				// each RBTreeNode in each of the children of the tree traverser
				view.refreshOption(i, text, img, true);
				
				// ..and set the sidebar displays
				view.refreshSideOption(i, text, true);
			}
			else {
				// display a "disabled" button
				view.refreshOption(i, "placeholder: no info", "test.png", false);
				
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
				treeTraverser = rootTree;
				
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
				if (null != treeTraverser.getParent()) {
					treeTraverser = treeTraverser.getParent();
				}																							
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
				
				treeTraverser = rootTree.getChild(whichDisplay);
				childOffset = 0;
				
				
				// Reset child offset																
			} // end else if side option selection
			
			// We want to display the selected data.
			else if (ButtonTypes.OPTION == selectionType){
				if (treeTraverser.getChildNum() != 0) {
					// Go to the selected child tree.
					treeTraverser = 
						treeTraverser.getChild(whichDisplay + childOffset);
					
					// Reset child offset
					childOffset = 0;
				}
			} // end else if normal selection
			
			
			// Then, display the information for the number of displays.
			
			// This is where logic to control the difference between
			// the number of displays and number of children should lie.	
			if (treeTraverser.getChildNum() > 0) {
				for (int i = childOffset; i < (displayAmt + childOffset); ++i) {
					// get this numbered child of the current node
					if (i < treeTraverser.getChildNum())
					{
						RBTree treeDisplayInfo = treeTraverser.getChild(i);
						RBTreeNode nodeDisplayInfo = treeDisplayInfo.getThisNode();
						
						// unpack the node
						String name, text, img;
						
						name = nodeDisplayInfo.getTitle();
						text = nodeDisplayInfo.getText();
						img = nodeDisplayInfo.getImagePath();
						
						// call each display in turn and pass it the contents of
						// each RBTreeNode in each of the children of the tree traverser
						view.refreshOption(i, text, img, true);
					}
					else 
					{
						// display a "disabled" button
						view.refreshOption(i, "placeholder: no info", "test.png", false);
					}
				} // end next
			}
			// else do nothing
				
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
	private RBTree rootTree; // a tree containing recycling data
	private RBTree treeTraverser; // pointer to various parts of the data tree
	private RBWindow view;
} // end class

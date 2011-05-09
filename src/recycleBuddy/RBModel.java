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
 * @latest 5/8/11
 * @version 0.0.04
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
	static int HOME = 0; // control requiring root display
	static int BACK = 1; // control requiring parent display
	static int SEEMORE = 2;
	static int SPECIALCONTROLOFFSET = 3; // number of special controls
	
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
			// add exception handling
			// focus on io exceptions
		}
	} // end RBModel
	
	// VIEWER-CONTROLLER (UI) COMPONENT INTERACTIVE SECTION	
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
				// child order of this tree
				int whichChild = treeTraverser.getChildNum();
				
				// probable off-by-one error
				// recalculate offset
				while ((whichChild - childOffset) > displayAmt) {
					childOffset += displayAmt;
				}
									
				// Go back to the parent tree.
				treeTraverser = treeTraverser.getParent();							
			} // end if BACK
			/*
			// We want to display more siblings
			else if (SEEMORE == whichControl) {
				// probable off-by-one error
				childOffset += displayAmt;

			} // end if SEEMORE
			*/
			
			// We want to display the selected data.
			else {
				// Go to the selected child tree.
				treeTraverser = 
					treeTraverser.getChild(whichDisplay + childOffset);
				
				// Reset child offset
				childOffset = 0;
			} // end else normal selection
			
			// Then, display the information for the number of displays.
			
			// This is where logic to control the difference between
			// the number of displays and number of children should lie.
			
			// Three cases:
			
			for (int i = childOffset; i < (displayAmt + childOffset); ++i) {
				// get this numbered child of the current node
				RBTree treeDisplayInfo = treeTraverser.getChild(i);
				
				// get the node
				
				// 1) There are as many children as displays.
				// Solution: perform normally.
				if (treeDisplayInfo != null) {
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
				// 2) There are fewer children than there are displays.
				// Solution: disable extra displays.
				else // there aren't enough children to be displayed
				{
					// display a "disabled" button
					view.refreshOption(i, "placeholder: no info", null, false);
				}
								
				// 3) There are more children than there are displays.
				// Solution: display "see more" information 
				// as the first (last?) display call
				
				// Currently, this algorithm may produce
				// results that are off by one.				
			}			
		}
		catch (Exception e) {
			// add exception handling
			// focus on out of range exceptions
			// ...and null exceptions
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

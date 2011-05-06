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
 * @version 0.02
 */

package recycleBuddy;

//import java.io.*; // streaming data


public class RBModel {
	// CONSTANT DECLARATIONS
	// Initial path to data. (TO BE DEFINED)
	static String INITIALFILEPATH = "UNDEFINED";
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
	public RBModel(int howManyControls) {
		try {
			// Create a new Recycle Buddy data tree.
			// As the root, its parent is null.
			rootTree = new RBTree(null, 0);
			
			// Build the tree with the file path to the data.
			rootTree.build(INITIALFILEPATH);
			
			// Point the tree traverser to the root.
			treeTraverser = rootTree;
			
			// Set the number of controls.
			controlAmt = howManyControls;
			
			// Set the number of displays.
			displayAmt = controlAmt - SPECIALCONTROLOFFSET;
			
			// Initialize child offset.
			childOffset = 0;
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
	public void click(int whichControl) {
		try {
			// VARIABLE DECLARATIONS
			// which (if non-negative) viewer-controller pair sent input
			int whichDisplay = whichControl - SPECIALCONTROLOFFSET;
			
			// First, go to the correct portion of the tree.
			
			// We want to display the initial data
			if (HOME == whichControl) {
				// Go back to the root.
				treeTraverser = rootTree;
				
				// Reinitialize child offset.
				childOffset = 0;
				
			} // end if HOME
			// We want to display the previous data
			else if (BACK == whichControl) {
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
			// We want to display more siblings
			else if (SEEMORE == whichControl) {
				// probable off-by-one error
				childOffset += displayAmt;

			} // end if SEEMORE
			// We want to display the selected data.
			else {
				// Go to the selected child tree.
				treeTraverser = 
					treeTraverser.getChild(whichDisplay + childOffset);
				
				// Reset child offset
				childOffset = 0;
			} // end else normal selection
			
			// Then, display the information for the number of displays.

			// NOTE
			// To properly implement the following section in MVC, there
			// should be a clock function in RBModel 
			// that forces a refresh of all the displays every few milliseconds
			// if the tree traverser changed.
			for (int i = childOffset; i < (displayAmt + childOffset); ++i) {
				// call each display in turn and pass it the contents of
				// each RBTreeNode in each of the children of the tree traverser
				
				// This is where logic to control the difference between
				// the number of displays and number of children should lie.
				
				// Three cases:
				// 1) There are fewer children than there are displays.
				// Solution: disable extra displays.
				
				// 2) There are as many children as displays.
				// Solution: perform normally.
				
				// 3) There are more children than there are displays.
				// Solution: display "see more" information 
				// as the first (last?) display call
				
				// Currently, this algorithm will probably produce
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
} // end class

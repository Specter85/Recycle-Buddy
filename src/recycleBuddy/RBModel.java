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
 * @latest 6/1/11
 * @version 1.0.00
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
 * 5/20/11 0.3.01 - Alpha 1, display single text panel
 * 5/22/11 0.3.02 - Alpha 2, passing title to side bars rather than text
 * 5/23/11 0.5.00 Beta Release - unchanged from build 0.3.02
 * 5/30/11 0.9.01 - FRC 1, developing RBFormHandler class
 * 5/30/11 0.9.02 - FRC 2, added paths for image folders
 * 5/30/11 0.9.03 - FRC 3, added further exception handling
 * 6/1/11 0.9.04 - FRC 4, expanded comments,
 *   removed child offset (defer to version 2)
 * 6/1/11 1.0.00 Final Release - moved tree build to setInitialState function
 *   added more constant image paths
 *   should handle malformed start.txt data without crashing now
 */

package recycleBuddy;

// enum for button types
import recycleBuddy.RBWindow.ButtonTypes;


public class RBModel {
	// CONSTANT DECLARATIONS
	// Location and name of data and text file.
	static String INITIALFILEPATH = "Start.txt";
	// Location and name of common image folder
	static String COMMONLIBRARYFOLDERPATH = "Common Image Library/";
	// Location and name of city image folder
	static String CITYFOLDERPATH = "Bothell/";
	// Location of error image
	static String ERRORIMAGE = "Common Image Library/error.jpg";
	// Location of disabled image
	static String DISABLEDIMAGE = "CommonImageLibrary/family.jpg";
		
	// DATA COMPONENT INTERACTIVE SECTION
	// INCLUDES CONSTRUCTOR
	/**
	* RBModel Constructor
	* 
	* @preconditions Correct path to a properly-formed Start.txt file is available.
	* 
	* @param The quantity of potential different control inputs, a reference to
	*  the calling window
	*/
	public RBModel(int howManyControls, RBWindow refViews) {
		try {
			// create reference to view
			view = refViews;
			
			// Create a new Recycle Buddy data tree.
			// As the root, its parent is null.
			rootTree = new RBTree(null, 0);
									
			// Set the number of displays.
			displayAmt = howManyControls;			
		}
		catch (Exception e) {
			// print out exception
			e.printStackTrace();
		}
	} // end RBModel
	
	// VIEWER-CONTROLLER (UI) COMPONENT INTERACTIVE SECTION	
	/**
	 * setInitialState
	 * 
	 * Creates and builds the root data tree.
	 * Points the treeTraverser tree to the root tree.
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
		try {
			// VARIABLE DECLARATIONS
			// name, text, and image path in tree node
			String name, text, img;
			
			// Build the tree with the file path to the data.
			rootTree.build(INITIALFILEPATH, COMMONLIBRARYFOLDERPATH, CITYFOLDERPATH);
					
			// Point the tree traverser to the root.
			treeTraverser = rootTree;	
			
			// for each display area
			for (int i = 0; i < displayAmt; ++i) {
				// display on the main button and display on a side button			
				if (i < treeTraverser.getChildNum()) {
					RBTree treeDisplayInfo = treeTraverser.getChild(i);
					RBTreeNode nodeDisplayInfo = treeDisplayInfo.getThisNode();
					
					// unpack the node					
					name = nodeDisplayInfo.getTitle();
					text = nodeDisplayInfo.getText();
					img = nodeDisplayInfo.getImagePath();
					
					// call each display in turn and pass it the contents of
					// each RBTreeNode in each of the children of the tree traverser
					view.refreshOption(i, text, img, true);
					
					// ..and set the sidebar displays
					view.refreshSideOption(i, name, true);
				}
				else {
					// display a "disabled" button
					view.refreshOption(i, "", DISABLEDIMAGE, false);
					
					// ...and set the sidebar displays
					view.refreshSideOption(i, "", false);
				}
			}
		}
		catch (Exception e) {
			// display exception on panel
			view.showTextPane();
			view.refreshTextPane(e.getMessage(), ERRORIMAGE);
			e.printStackTrace();
		}
	}
	
	/**
	* click
	*
	* Accepts controller data and sends display information.
	*   
	* @preconditions 1) Number of display areas is known.
	*   2) Correct command to call displays in Viewer module is known.
	*   3) Number of displays is equal to number of controls - number
	*     of special controls (aka SPECIALCONTROLOFFSET)
	*   4) Data tree is built
	*   5) There are not more children than displays.
	*   6) Enabled viewer-controller pairs exactly correspond to the 
	*      number of children at the current tree.
	* 
	* @param what type of control was clicked, which display is to be selected
	* 
	* @postcondition Each display will be set to the correct information.
	*/
	public void click(ButtonTypes selectionType, int whichDisplay) {
		try {		
			// VARIABLE DECLARATIONS
			// name, text, and image path in tree node
			String name, text, img;
			// tree that holds desired data
			RBTree treeDisplayInfo;
			// node that holds desired data
			RBTreeNode nodeDisplayInfo;
		
			// First, go to the correct portion of the tree.
			
			// We want to display the initial data
			if (ButtonTypes.HOME == selectionType) {
				// Go back to the root.
				treeTraverser = rootTree;				
			} // end if HOME
			
			// We want to display the previous data
			else if (ButtonTypes.BACK == selectionType) {									
				// Go back to the parent tree, if it exists
				if (null != treeTraverser.getParent()) {
					treeTraverser = treeTraverser.getParent();
				}	
				// Otherwise, do nothing.
			} // end if BACK
			
			// the side bar displays the root's children
			else if (ButtonTypes.SIDE_OPTION == selectionType){
				// now we are strictly selecting children of the root
				
				// if the tree has children, get the selected child
				if (rootTree.getChildNum() != 0)
					treeTraverser = rootTree.getChild(whichDisplay);																
			} // end else if side option selection
			
			// We want to display the selected data.
			else if (ButtonTypes.OPTION == selectionType) {
				// if this tree has children
				if (treeTraverser.getChildNum() != 0) {
					// Go to the selected child tree.
					treeTraverser = 						
						treeTraverser.getChild(whichDisplay);
				}
			} // end else if normal selection
			
			// Then, display the information for the number of displays.
			
			// If there is more than one child, we want multiple displays. 
			if (treeTraverser.getChildNum() > 1) {
				// for each display amount
				for (int i = 0; i < (displayAmt); ++i) {
					// get this numbered child of the current node
					if (i < treeTraverser.getChildNum())
					{
						// find the correct branch of the tree
						treeDisplayInfo = treeTraverser.getChild(i);
						
						// retrieve the data node
						nodeDisplayInfo = treeDisplayInfo.getThisNode();
						
						// unpack the node						
						name = nodeDisplayInfo.getTitle();
						text = nodeDisplayInfo.getText();
						img = nodeDisplayInfo.getImagePath();
						
						// make sure we're showing the button pane
						view.showButtonPane();
						
						// call selected display and pass it the unpacked data
						view.refreshOption(i, text, img, true);
					}
					else 
					{
						// display a "disabled" button
						view.refreshOption(i, "", DISABLEDIMAGE, false);
					}
				} // next button
			} // end if children
			
			// if there's a single child, we want to display a single pane view
			else if (treeTraverser.getChildNum() == 1){
				// find the correct branch of the tree
				treeDisplayInfo = treeTraverser.getChild(0);
				
				// retrieve the data node
				nodeDisplayInfo = treeDisplayInfo.getThisNode();
				
				// unpack the current node
				name = nodeDisplayInfo.getTitle();
				text = nodeDisplayInfo.getText();
				img = nodeDisplayInfo.getImagePath();
				
				// display the single pane view
				view.showTextPane();
				
				// send information to that view
				view.refreshTextPane(text, img);
			}
			
			// otherwise, there are no children, so return to the parent
			else {
				treeTraverser = treeTraverser.getParent();
			}	
		}
		catch (Exception e) {
			// display exception on panel
			view.showTextPane();
			view.refreshTextPane(e.getMessage(), ERRORIMAGE);
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	} // end click
	
	// PRIVATE MEMBERS
	private int displayAmt; // number of displays in Viewer
	private RBTree rootTree; // a tree containing recycling data
	private RBTree treeTraverser; // pointer to various parts of the data tree
	private RBWindow view;
} // end class

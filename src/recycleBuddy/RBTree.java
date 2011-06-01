/**
 * RBTree.java:<p>
 * A simple tree to hold recycling data.
 *   Stores children in an array for O(1) lookup.
 *   Includes link to parent for reverse traversal.
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
 * @version 0.9.03
 * 5/3/11 0.0.01 - Created tree structure.
 * 5/5/11 0.0.02 - added build(), full comments, and childNum
 * 5/8/11 0.0.03 - changed version scheme, added some build() functionality
 * 5/11/11 0.0.04 - build tree with dummy data
 * 5/11/11 0.0.05 - read and output data file
 * 5/11/11 0.1.00 - ZFR Candidate, reads data file, passes it to UI
 * 5/23/11 0.5.00 Beta Release - unchanged from build 0.1.00
 * 5/29/11 0.9.01 - FRC 1, added exceptions for malformed and missing files
 * 5/30/11 0.9.02 - FRC 2, added paths for image folders 
 * 6/1/11 0.9.03 - FRC 3, completed comments
 */

package recycleBuddy;

import java.io.*; // streaming data
import java.util.*; // scanner


public class RBTree {

	/**
	* RBTree Constructor
	*
	* Creates an empty tree with a parent and an empty node.
	* 
	* @params reference to parent (can be null, but must be explicitly passed)
	*   number of children
	*/
	public RBTree(RBTree caller, int childNumber) {
		
		// Set parent tree.
		parent = caller;
		
		// Create an empty node.
		thisNode = new RBTreeNode();
		
		// Assign child number
		childNum = childNumber;
	}
	
	/**
	* build
	*
	* Builds a full data tree based on external data.
	* 
	* @precondition a properly-formed file
	* 
	* @param path for file stream, folder locations for images
	* 
	* @postcondition A full tree of recycling data is constructed.
	*/
	public void build(String dataPath, String commonImgs, String cityImgs) throws FileNotFoundException, 
	NumberFormatException, Exception {		
		try {
			// VARIABLE DECLARATIONS
			// An iterative text scanner
			Scanner RBScanner = new Scanner(new File(dataPath));
			
			// call private helper function
			buildHelper(RBScanner, commonImgs, cityImgs);			
		}
		catch (Exception e) {
			// throw exception to the model
			throw e;
		}
		
	} // end build
	
	/**
	* buildHelper 
	*
	* Private helper class.
	* Builds a full data tree based on external data.
	* 
	* @precondition a properly formed file
	* 
	* @param path for file stream, folder path for images
	* 
	* @postcondition A full tree of recycling data is constructed.
	*/
	private void buildHelper(Scanner recycleData, String commonImgs, String cityImgs) 
	throws FileNotFoundException, NumberFormatException, Exception {	
		try {
			// read data for this part of the tree
			// place data into tree node
			
			// read the title
			if (recycleData.hasNext()) 
				thisNode.setTitle(recycleData.nextLine());
			else throw new Exception("The file ended before " +
					"a title could be read.");
			
			// read the text
			if (recycleData.hasNext()) 
				thisNode.setText(recycleData.nextLine());
			else throw new Exception("The file ended before " +
					"a text line could be read.");
			
			// read image file name
			// add folder path in front of file name
			if (recycleData.hasNext()) 
				thisNode.setImagePath(commonImgs + recycleData.nextLine());
			else throw new Exception("The file ended before " +
					"an image path could be read.");
						
			// read number of children
			if (recycleData.hasNext()) {						
				childNum = Integer.parseInt(recycleData.nextLine());
			}
										
			// Recursive and cyclic tree building section
			
			// set current child count
			int currentChild = 0;
			
			// if this tree has children
			if (childNum > 0) {
				// add branches equal to the number of children
				children = new RBTree[childNum];
				
				// create a child tree in each branch
				while (currentChild < childNum) {
					// create specified child tree
					children[currentChild] = new RBTree(this, 0);
					children[currentChild].buildHelper(recycleData, commonImgs, cityImgs);
										
					// move to the next child
					++currentChild;
				} // end while
			} // end if
		} // end try
		catch (NumberFormatException nfe) {
			childNum = 0;
			throw nfe;
		}
		catch (FileNotFoundException fnfe) {
			throw fnfe;
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
	} // end buildHelper
	
	// ACCESSORS
	/**
	* getThisNode
	*
	* @return RBTreeNode containing recycling entry
	*/
	public RBTreeNode getThisNode() {
		return thisNode;
	}
	
	/**
	* getParent
	*
	* @return tree with root at parent
	*/
	public RBTree getParent() {
		return parent;
	}
	
	/**
	* getChild
	*
	* @return tree with root at specified child
	*/
	public RBTree getChild(int whichChild) {
		// if child exists at index
		if (null != children[whichChild]) {
			return children[whichChild];
		}
		else {
			return null;
		}
	}
	
	/**
	* getChildNum
	*
	* @return number of child nodes
	*/
	public int getChildNum() {
		return childNum;
	}
	
	// PRIVATE MEMBERS		
	private RBTreeNode thisNode; // data held by this tree
	private RBTree[] children; // child trees
	private RBTree parent; // parent tree
	private int childNum; // which child is this tree
} // end class

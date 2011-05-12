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
 * @latest 5/11/11
 * @version 0.1.00
 * 5/3/11 0.0.01 - Created tree structure.
 * 5/5/11 0.0.02 - added build(), full comments, and childNum
 * 5/8/11 0.0.03 - changed version scheme, added some build() functionality
 * 5/11/11 0.0.04 - build tree with dummy data
 * 5/11/11 0.0.05 - read and output data file
 * 5/11/11 0.1.00 - ZFR Candidate, reads data file, passes it to UI
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
	*   number of children (not currently needed)
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
	* @param path for file stream
	* 
	* @postcondition A full tree of recycling data is constructed.
	*/
	public void build(String dataPath) throws FileNotFoundException {		
		try {
			
			Scanner RBScanner = new Scanner(new File(dataPath));
			
			buildHelper(RBScanner);			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	} // end build
	
	
	
	/**
	* buildHelper 
	*
	* Private helper class.
	* Builds a full data tree based on external data.
	* 
	* @precondition a properly formed filestream (no error checking yet)
	* 
	* @param path for file stream
	* 
	* @postcondition A full tree of recycling data is constructed.
	*/
	private void buildHelper(Scanner recycleData) throws FileNotFoundException {		
		try {
			// read data for this part of the tree
			thisNode.setTitle(recycleData.nextLine());
			thisNode.setText(recycleData.nextLine());
			thisNode.setImagePath(recycleData.nextLine());
						
			// find number of children
			childNum = Integer.parseInt(recycleData.nextLine());
			
			// set current child count
			int currentChild = 0;
			
			if (childNum > 0) {
				children = new RBTree[childNum];
				// create children
				while (currentChild < childNum) {
									
					children[currentChild] = new RBTree(this, 0);
					children[currentChild].buildHelper(recycleData);
										
					// decrement currentChild
					++currentChild;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	} // end overloaded build
	
	
	
	// ACCESSORS
	// Straightforward code; comment later.
	public RBTreeNode getThisNode() {
		return thisNode;
	}
	
	public RBTree getParent() {
		return parent;
	}
	
	public RBTree getChild(int whichChild) {
		// if child exists at index
		if (null != children[whichChild]) {
			return children[whichChild];
		}
		else {
			return null;
		}
	}
	
	public int getChildNum() {
		return childNum;
	}
	
	// PRIVATE MEMBERS		
	private RBTreeNode thisNode; // data held by this tree
	private RBTree[] children; // child trees
	private RBTree parent; // parent tree
	private int childNum; // which child is this tree
} // end class

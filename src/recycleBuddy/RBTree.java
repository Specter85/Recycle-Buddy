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
 * @version 0.02
 */

package recycleBuddy;

//import java.io.*; // streaming data


public class RBTree {

	/**
	* RBTree Constructor
	*
	* Creates an empty tree with a parent and an empty node.
	* 
	* @param reference to parent (can be null, but must be explicitly passed)
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
	public void build(String dataPath) {
		// open the initial file
		
		// read data
		
		// close the initial file
		
		// for each file to be read
		//   open the file
		//   read the data to:
		//     fill this tree's node
		//     create children trees
		//     return to parent if there are no more children to create
		//       and parent is not null
		//     stop when there are no more children to create and parent is null
		//   close the file
	} // end build
	
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

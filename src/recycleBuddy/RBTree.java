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
 * @latest 5/30/11
 * @version 0.9.02
 * 5/3/11 0.0.01 - Created tree structure.
 * 5/5/11 0.0.02 - added build(), full comments, and childNum
 * 5/8/11 0.0.03 - changed version scheme, added some build() functionality
 * 5/11/11 0.0.04 - build tree with dummy data
 * 5/11/11 0.0.05 - read and output data file
 * 5/11/11 0.1.00 - ZFR Candidate, reads data file, passes it to UI
 * 5/23/11 0.5.00 Beta Release - unchanged from build 0.1.00
 * 5/29/11 0.9.01 - FRC 1, added exceptions for malformed and missing files
 * 5/30/11 0.9.02 - FRC 2, added paths for image folders 
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
	public void build(String dataPath, String commonImgs, String cityImgs) throws FileNotFoundException, 
	NumberFormatException, Exception {		
		try {						
			Scanner RBScanner = new Scanner(new File(dataPath));
			
			buildHelper(RBScanner, commonImgs, cityImgs);			
		}
		catch (Exception e) {
			throw e;
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
	private void buildHelper(Scanner recycleData, String commonImgs, String cityImgs) throws FileNotFoundException, 
	NumberFormatException, Exception {	
		try {
			// read data for this part of the tree
			if (recycleData.hasNext()) 
				thisNode.setTitle(recycleData.nextLine());
			else throw new Exception("The file ended before " +
					"a title could be read.");
			
			if (recycleData.hasNext()) 
				thisNode.setText(recycleData.nextLine());
			else throw new Exception("The file ended before " +
					"a text line could be read.");
			
			if (recycleData.hasNext()) 
				//thisNode.setImagePath(recycleData.nextLine());
				thisNode.setImagePath(commonImgs + recycleData.nextLine());
			else throw new Exception("The file ended before " +
					"an image path could be read.");
						
			System.out.println("Image path: " + thisNode.getImagePath());
			
			// find number of children
			if (recycleData.hasNext()) {						
				childNum = Integer.parseInt(recycleData.nextLine());
			}
										
			System.out.println("in tree: " + thisNode.getTitle());
			if (parent != null ) {
				System.out.println("with parent: " + parent.getThisNode().getTitle());
			}
			else {
				System.out.println("with null parent");
			}
			
			
			// set current child count
			int currentChild = 0;
			
			if (childNum > 0) {
				children = new RBTree[childNum];
				// create children
				while (currentChild < childNum) {
									
					children[currentChild] = new RBTree(this, 0);
					children[currentChild].buildHelper(recycleData, commonImgs, cityImgs);
										
					// decrement currentChild
					++currentChild;
				}
			}
		}
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

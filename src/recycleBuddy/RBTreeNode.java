/**
 * RBTreeNode.java:<p>
 * A data node for a tree that holds recycling data.
 *   Holds name of data, body of text, and a path to an image.
 * 
 * University of Washington, Bothell
 * CSS 360
 * Spring 2011
 * Professor: Valentin Razmov
 * Recycle Buddy Group
 *
 * @author Niko Simonson
 * @since 5/3/11
 * @latest 5/23/11
 * @version 0.5.00
 * 5/3/11 0.0.01 - coded private data members, get and set functions
 * 5/5/11 0.0.02 - added full comments and fill() function
 * 5/8/11 0.0.03 - changed version scheme, considering removing fill()
 * 5/23/11 5.0.00 Beta Release - unchanged from build 0.0.03
 */

package recycleBuddy;

public class RBTreeNode {

	// ACCESSORS
	// Straightforward code; comment later.
	public String getTitle() {
		return title;
	}
	
	public String getText() {
		return text;
	}
	
	public String getImagePath() {
		return imgPath;
	}
	
	
	// MUTATORS
	// Straightforward code; comment later.
	public void setImagePath(String inImgPath) {
		imgPath = inImgPath;
	}
	
	public void setTitle(String inTitle) {
		title = inTitle;
	}
	
	public void setText(String inText) {
		text = inText;
	}
	
	/**
	* fill
	*
	* Fills data in node.
	* 
	* @param path for file stream
	* 
	* @postcondition The node is filled with data.
	*/
	public void fill(String filePath) {
		// open file
		// read data
		// call each mutator
		// close file
	}
	
	// PRIVATE MEMBERS
	private String title; // title or name of data
	private String text; // body of data
	private String imgPath; // path to image content
} // end class

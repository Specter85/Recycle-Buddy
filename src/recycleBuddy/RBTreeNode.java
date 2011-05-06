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
 * @version 0.02
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

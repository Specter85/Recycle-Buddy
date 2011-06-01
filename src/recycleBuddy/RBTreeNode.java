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
 * @latest 6/1/11
 * @version 1.0.00
 * 5/3/11 0.0.01 - coded private data members, get and set functions
 * 5/5/11 0.0.02 - added full comments and fill() function
 * 5/8/11 0.0.03 - changed version scheme, considering removing fill()
 * 5/23/11 0.5.00 Beta Release - unchanged from build 0.0.03
 * 6/1/11 1.0.00 Final Release - finishing comments, removing unneeded fill() method 
 */

package recycleBuddy;

public class RBTreeNode {

	// ACCESSORS
	/**
	* getTitle
	*
	* @return title string
	*/
	public String getTitle() {
		return title;
	}
	
	/**
	* getText
	*
	* @return text string
	*/
	public String getText() {
		return text;
	}
	
	/**
	* getImagePath
	*
	* @return string containing name or path of image file
	*/
	public String getImagePath() {
		return imgPath;
	}
	
	
	// MUTATORS
	/**
	* setImagePath
	*
	* Fills data in node.
	* 
	* @param name or path of image file
	* 
	* @postcondition The data is stored in the node.
	*/
	public void setImagePath(String inImgPath) {
		imgPath = inImgPath;
	}
	
	/**
	* setTitle
	*
	* Fills data in node.
	* 
	* @param name or title of recycling entry
	* 
	* @postcondition The data is stored in the node.
	*/
	public void setTitle(String inTitle) {
		title = inTitle;
	}
	
	/**
	* setText
	*
	* Fills data in node.
	* 
	* @param content of recycling entry
	* 
	* @postcondition The data is stored in the node.
	*/
	public void setText(String inText) {
		text = inText;
	}
		
	// PRIVATE MEMBERS
	private String title; // title or name of data
	private String text; // body of data
	private String imgPath; // path to image content
} // end class

package rcword;


import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.border.*;

/**
 * 	Used to set the fore ground color for the selected text.
 *
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
 class ForeGroundColorAction extends AbstractAction
 {
 		 /**
		  * a reference to the main <code> Word </code> object
		  */
 	      Word parentword;

 	      /**
	  	   *  selected color to set the fore ground.
	 	   */
 	      Color colortoset;


		  /**
		  	* Defines an object of <code> ForeGroundColorDialog </code>
		  	* with a reference to <code> Word </code>
		  	*
		  	* @param parent a reference to the main <code> Word </code>
	 	 	*/
	  	  ForeGroundColorAction(Word parent)
	  	  {
	  	  	   super("TextColour",ImagesLocator.getImage("rcword/Images/ttextcolour.gif"));
	  	  	   parentword = parent;
	  	  }

		   /**
		   	* Defines an object of <code> ForeGroundColorDialog </code>
		   	* with a reference to the main <code> Word </code> object
		   	* and an object of <code> Icon </code> to display on the button
		   	*
		   	* @param parent a reference to the main <code> Word </code> object
		   	* 	@param	icon	an object of <code> Icon </code> to display on the button
	 		*/
		   ForeGroundColorAction(Word parent,Icon icon)
		   {
				super("ð¤ù¢¹ø õí¢íñ¢",icon);
				parentword = parent;
	        }


			/**
			* Action to change the fore ground color.
			*
			*@param event an object of ActionEvent which causes the action.
			*/
	  	  public void actionPerformed(ActionEvent event)
	  	  {
				colortoset = JColorChooser.showDialog(((Component)event.getSource()).getParent(), "Colors", Color.blue);
				if(colortoset != null)
				{

					StyledDocument doc = parentword.workspace.getStyledDocument();
					SimpleAttributeSet a = new SimpleAttributeSet();
					StyleConstants.setForeground(a,colortoset);
					int tempstart = parentword.workspace.getSelectionStart();
					int tempend = parentword.workspace.getSelectionEnd();
					doc.setCharacterAttributes(tempstart, tempend - tempstart,a,false);
					parentword.workspace.repaint();

				}
				parentword.repaint();
				parentword.workspace.repaint();
	  	  }
}// end of TextColorDialog
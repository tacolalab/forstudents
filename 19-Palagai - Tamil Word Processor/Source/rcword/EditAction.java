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
 * Used to do the new file action to open a new file.
 *
 * @version	5.12.2002.
 * @author	RCILTS-Tamil,MIT.
 */
final class NewAction extends AbstractAction
{
	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;


    /**
     *	Defines an object of <code> NewAction </code> with
     * 	a reference to the main <code> Word </code> object.
     *
     * 	@param	parent	a reference to the main <code> Word </code> object
     */
	NewAction(Word parent)
	{
		super("New");
		parentword = parent;
	}

	/**
	 *	Defines an object of <code> NewAction </code> with
	 * 	a reference to the main <code> Word </code> object
	 *	and an object of <code> Icon </code> to display
	 * 	on the button.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
	 * 	@param	icon	an object of <code> Icon </code> to display on the button
     */
	NewAction(Word parent, Icon icon)
	{
			super("New",icon);
			parentword = parent;
	}

	/**
	* Action to create a new file
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{
		//parentword.workspace.setText(" ");
	   	parentword.currentfile.newFile(true);
	   	parentword.repaint();
	}
}

/**
 * Used to do the open file action to open an existing file.
 *
 * @version	5.12.2002.
 * @author	RCILTS-Tamil,MIT.
 */
final class OpenAction extends AbstractAction
{
	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
	 *	Defines an object of <code> OpenAction </code> with
	 * 	a reference to the main <code> Word </code> object.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
     */
	OpenAction(Word parent)
	{
		super("New");
		parentword = parent;
	}

	/**
	 *	Defines an object of <code> OpenAction </code> with
	 * 	a reference to the main <code> Word </code> object
	 *	and an object of <code> Icon </code> to display
	 * 	on the button.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
	 * 	@param	icon	an object of <code> Icon </code> to display on the button
     */
	OpenAction(Word parent, Icon icon)
	{
			super("New",icon);
			parentword = parent;
	}

	/**
	* Action to open a file
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{
		parentword.currentfile.openfile();
		parentword.repaint();
	}
}

/**
 * Used to do the save file action to save the current file.
 *
 * @version	5.12.2002.
 * @author	RCILTS-Tamil,MIT.
 */
final class SaveAction extends AbstractAction
{
	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
	 *	Defines an object of <code> SaveAction </code> with
	 * 	a reference to the main <code> Word </code> object.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
	 */
	SaveAction(Word parent)
	{
		super("New");
		parentword = parent;
	}

	/**
	 *	Defines an object of <code> SaveAction </code> with
	 * 	a reference to the main <code> Word </code> object
	 *	and an object of <code> Icon </code> to display
	 * 	on the button.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
	 * 	@param	icon	an object of <code> Icon </code> to display on the button
     */
	SaveAction(Word parent, Icon icon)
	{
			super("New",icon);
			parentword = parent;
	}

	/**
	* Action to save the current file.
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{
		parentword.currentfile.savefile(true);
	}
}

/**
 * Used to do the cut action to the selected document portion.
 *
 * @version	5.12.2002.
 * @author	RCILTS-Tamil,MIT.
 */
final class CutAction extends AbstractAction
{
	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
	 *	Defines an object of <code> CutAction </code> with
	 * 	a reference to the main <code> Word </code> obzject.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
     */
	CutAction(Word parent)
	{
		super("ªõì¢´");
		parentword = parent;
	}

	/**
	 *	Defines an object of <code> CutAction </code> with
	 * 	a reference to the main <code> Word </code> class
	 *	and an object of <code> Icon </code> to display
	 * 	on the button.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
	 * 	@param	icon	an object of <code> Icon </code> to display on the button
     */
	CutAction(Word parent, Icon icon )
	{
			super("ªõì¢´",icon);
			parentword = parent;
	}

	/**
	* Action to cut the select text
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{
		//parentword.workspace.cut();

		try
		{
			StyledText st = new StyledText(parentword.workspace);
			StyledTextSelection contents = new StyledTextSelection(st);
			parentword.getClipboard().setContents(contents,parentword);
			parentword.workspace.replaceSelection("");
		}
		catch(Exception eCopy)
		{
			System.out.println(eCopy+"\n----> ex at cut ");
		}
	}
}

/**
 * Used to do the copy action to copy the selected portion of the document.
 *
 * @version	5.12.2002.
 * @author	RCILTS-Tamil,MIT.
 */
final class CopyAction extends AbstractAction
{
	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;


	/**
     *	Defines an object of <code> CopyAction </code> with
     * 	a reference to the main <code> Word </code> object.
     *
     * 	@param	parent	a reference to the main <code> Word </code> object
     */
	CopyAction(Word parent)
	{
		super("ïèô¢");
		parentword = parent;
	}

	/**
	 *	Defines an object of <code> CopyAction </code> with
	 * 	a reference to the main <code> Word </code> object
	 *	and an object of <code> Icon </code> to display
	 * 	on the button.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
	 * 	@param	icon	an object of <code> Icon </code> to display on the button
     */
	CopyAction(Word parent, Icon icon)
	{
			super("ïèô¢",icon);
			parentword = parent;
	}

	/**
	* Action to copy the selected text
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			StyledText st = new StyledText(parentword.workspace);
			StyledTextSelection contents = new StyledTextSelection(st);
			parentword.getClipboard().setContents(contents,parentword);
		}
		catch(Exception eCopy)
		{
			System.out.println(eCopy+"\n----> ex at copy ");
		}
	}
}

/**
 * Used to do the paste action to paste the text from the clipboard.
 *
 * @version	5.12.2002.
 * @author	RCILTS-Tamil,MIT.
 */
final class PasteAction extends AbstractAction
{
	/*** StyledText flavor */
	public DataFlavor df = new DataFlavor(StyledText.class, "StyledText");

	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
     *	Defines an object of <code> PasteAction </code> with
     * 	a reference to the main <code> Word </code> object.
     *
     * 	@param	parent	a reference to the main <code> Word </code> object
     */
	PasteAction(Word parent)
	{
		super("åì¢´");
		parentword = parent;
	}

	/**
	 *	Defines an object of <code> PasteAction </code> with
	 * 	a reference to the main <code> Word </code> object
	 *	and an object of <code> Icon </code> to display
	 * 	on the button.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
	 * 	@param	icon	an object of <code> Icon </code> to display on the button
     */
	PasteAction(Word parent,Icon icon)
	{
			super("åì¢´",icon);
			parentword = parent;
	}

	/**
	* Action to paste the previously selected text.
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{
		//parentword.workspace.paste();
		try
		{
			//get content from clipboard
			Transferable content = parentword.getClipboard().getContents(this);

			//StyledText flavor
			if(content.isDataFlavorSupported(df))
			{
				StyledText st = (StyledText) content.getTransferData(df);
				Utils.replaceSelection(parentword,st);
				return;
			}
			//String flavor
			if(content.isDataFlavorSupported(DataFlavor.stringFlavor))
			{
				String text = (String)content.getTransferData(DataFlavor.stringFlavor);
				parentword.workspace.replaceSelection(text);
				return;
			}
		}
		catch(Exception ePast)
		{
			System.out.println(ePast+"\n-----> ex at past.");
		}
	}
}


/**
 * Used to do the clear action to the selected document portion.
 *
 * @version	5.12.2002.
 * @author	RCILTS-Tamil,MIT.
 */
final class ClearAction extends AbstractAction
{
	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
	 *	Defines a <code> ClearAction </code> object with
	 * 	a reference to the main <code> Word </code> object.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
     */
	ClearAction(Word parent)
	{
		super("Üö¤");
		parentword = parent;
	}

	/**
	 *	Defines a <code> ClearAction </code> object with
	 * 	a reference to the main <code> Word </code> object
	 *	and an object of <code> Icon </code> to display
	 * 	on the button.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
	 * 	@param	icon	an object of <code> Icon </code> to display on the button
     */
	ClearAction(Word parent, Icon icon )
	{
			super("Üö¤",icon);
			parentword = parent;
	}

	/**
	* Action to clear selected text
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{
		int cursor_position = parentword.workspace.getCaretPosition();
		System.out.println("before :"+parentword.workspace.getCaretPosition());
		parentword.workspace.replaceSelection("");
		parentword.workspace.setCaretPosition(cursor_position);
		parentword.workspace.repaint();
		System.out.println(" after :"+parentword.workspace.getCaretPosition());
	}
}


/**
 * Used to do the select all action to select the whole text in the workspace.
 *
 * @version	5.12.2002.
 * @author	RCILTS-Tamil,MIT.
 */
final class SelectallAction extends AbstractAction
{
	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
     *	Defines a <code> SelectallAction </code> object with
     * 	a reference to the main <code> Word </code> object.
     *
     * 	@param	parent	a reference to the main <code> Word </code> object
     */
	SelectallAction(Word parent)
	{
		super("Ü¬íî¢¬î»ñ¢ «îó¢ï¢ªî´");
		parentword = parent;
	}

	/**
	 *	Defines a <code> SelectallAction </code> object with
	 * 	a reference to the main <code> Word </code> object
	 *	and an object of <code> Icon </code> to display
	 * 	on the button.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
	 * 	@param	icon	an object of <code> Icon </code> to display on the button
     */
	SelectallAction(Word parent, Icon icon)
	{
			super("åì¢´",icon);
			parentword = parent;
	}

	/**
	* Action to select the whole text in the workspace
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{
		System.out.println(" select all");
		parentword.workspace.selectAll();
		System.out.println(" selected text :"+parentword.workspace.getSelectedText());
	}
}

/**
 * Used to do the find action to find the given text in the workspace.
 *
 * @version	5.12.2002.
 * @author	RCILTS-Tamil,MIT.
 */
final class FindAction extends AbstractAction
{
	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
     *	Defines an object of <code> FindAction </code> object with
     * 	a reference to the main <code> Word </code> object.
     *
     * 	@param	parent	a reference to the main <code> Word </code> object
     */
	FindAction(Word parent)
	{
		super("èí¢´ð¤®");
		parentword = parent;
	}

	/**
	 *	Defines an object of <code> FindAction </code> object with
	 * 	a reference to the main <code> Word </code> object
	 *	and an object of <code> Icon </code> to display
	 * 	on the button.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
	 * 	@param	icon	an object of <code> Icon </code> to display on the button
     */
	FindAction(Word parent,Icon icon)
	{
			super("èí¢´ð¤®",icon);
			parentword = parent;
	}

	/**
	* Action to find the given text
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{   // opens the find dialog
		parentword.finddialog = new FindDialog(parentword,0);
		parentword.finddialog.setLocale();
		parentword.finddialog.repaint();
		parentword.finddialog.show();
	}
}

/**
 * Used to do the replace action to replace the existing text with the given text.
 *
 * @version	5.12.2002.
 * @author	RCILTS-Tamil,MIT.
 */
final class ReplaceAction extends AbstractAction
{
	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
	 *	Defines an object of <code> ReplaceAction </code> with
	 * 	a reference to the main <code> Word </code> object.
	 *
	 * 	@param	parent	a reference to the main <code> Word </code> object
     */
	ReplaceAction(Word parent)
	{
		super("ñ£ø¢Á");
		parentword = parent;
	}

	/**
	* Action to replace the text.
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{	// opens the finddialog to perform replace action
		parentword.finddialog = new FindDialog(parentword,1);
		parentword.finddialog.setLocale();
		parentword.finddialog.show();
	}
}

/**
  *  Used to set the background color for the selected text portion.
  *
  *  @version	5.12.2002
  *  @author	RCILTS-Tamil,MIT
  */
final class BackGroundColorAction extends AbstractAction
{
	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
	 * selected color to set the back ground.
	 */
	Color colortoset;


	/**
	 * Defines a <code> BackGroundColorAction </code> object
	 * with a reference to <code> Word </code>
	 *
	 * @param parent a reference to the main <code> Word </code>
	 */
	BackGroundColorAction(Word parent)
	{
		super("ð¤ù¢¹ø õí¢íñ¢");
		parentword = parent;
	}

	/**
	 * Defines a <code> BackGroundColorAction </code> object
	 * with a reference to the main <code> Word </code> object
	 * and an object of <code> Icon </code> to display on the button
	 *
	 * @ param 	parent 	a reference to the main <code> Word </code> object
	 * @ param	icon	an object of <code> Icon </code> to display on the button
	 */
	BackGroundColorAction(Word parent, Icon icon)
	{
			super("ð¤ù¢¹ø õí¢íñ¢",icon);
			parentword = parent;
	}

	/**
	* Action to set the background color for the selected text portion.
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/

	public void actionPerformed(ActionEvent event)
	{	//	opens a color chooser dialog to get the user option and sets the color
		//  as back ground color for the selected text.
		colortoset = JColorChooser.showDialog(((Component)event.getSource()).getParent(), "Colors", Color.blue);
		if(colortoset != null)
		{
			StyledDocument doc = parentword.workspace.getStyledDocument();
			SimpleAttributeSet a = new SimpleAttributeSet();
			StyleConstants.setBackground(a,colortoset);
			int tempstart = parentword.workspace.getSelectionStart();
			int tempend = parentword.workspace.getSelectionEnd();
			doc.setCharacterAttributes(tempstart, tempend - tempstart,a,false);
			parentword.workspace.repaint();
		}
		parentword.repaint();
		parentword.workspace.repaint();
	}
}


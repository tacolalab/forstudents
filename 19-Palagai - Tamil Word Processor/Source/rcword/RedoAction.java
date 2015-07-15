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
 * Used to do the redo action.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */

class RedoAction extends AbstractAction
{
	/** a reference to the main <code> Word </code> object */
	Word parentword;

	/**
	* Constructs an object of <code> RedoAction </code> with
	* a reference to the main <code> Word </code> object
	* @param parent a reference to the main <code> Word </code> object
	*/
	public RedoAction(Word parent)
	{
			super("ñÁð® ªêò¢",ImagesLocator.getImage("rcword/Images/redo.gif"));
			parentword = parent;
			setEnabled(false);
	}

	/**
	* Action to redo.
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{
		try
	 	{
				parentword.undo.redo();
		}
		catch (CannotRedoException ex)
		{
				System.out.println(ex+"\n ---->Unable to redo: ");
				//ex.printStackTrace();
		}
		update();
		parentword.undoAction.update();
	}

	/** Used to set the state of the redo state. */
	protected void update()
	{
		if(parentword.undo.canRedo())
		{
			setEnabled(true);
			//putValue(Action.NAME, undo.getRedoPresentationName());
		}
		else
		{
			//setEnabled(false);
			//putValue(Action.NAME, "Redo");
		}
	}
}
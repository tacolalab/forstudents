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
 * Used to do the undo action.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class UndoAction extends AbstractAction
{
	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
	* Constructs an object of <code> UndoAction </code> with
	* a reference to the main <code> Word </code> object
	* @param parent a reference to the main <code> Word </code> object
	*/
	public UndoAction(Word parent)//ring in,Icon icon)
	{
		super("îõ¤ó¢",ImagesLocator.getImage("rcword/Images/undo.gif"));
		parentword = parent;
		setEnabled(false);
	}

	/**
	* Action to undo
	*
	* @param	e an object of <code> ActionEvent </code> which causes the action
	*/
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			parentword.undo.undo();
		}
		catch (CannotUndoException ex)
		{
			System.out.println(ex+"\n----> Unable to undo: Undo");
			//ex.printStackTrace();
		}
		update();
		parentword.redoAction.update();
	}

    /**
    * Used to set the state of the undo state.
    */
	protected void update()
	{
		if(parentword.undo.canUndo())
		{
			setEnabled(true);
			//putValue(Action.NAME, undo.getUndoPresentationName());
		}
		else
		{
			//setEnabled(false);
			//putValue(Action.NAME, "Undo");
		}
	}
} // end of UndoAction
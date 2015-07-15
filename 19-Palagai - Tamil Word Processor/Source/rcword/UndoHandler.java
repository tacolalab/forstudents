package rcword;
//import rcword.WordCount;

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
 * Used to perform the undo and redo action.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class UndoHandler implements UndoableEditListener
{
		/**
		* a reference to the main <code> Word </code> object
		*/
		Word parentword;

		/**
		* Constructs an object of <code> UndoHandler </code> with
		* a reference to the main <code> Word </code> object.
		*
		* @param parent a reference to the main <code> Word </code> object
		*/
		UndoHandler(Word parent)
		{
			  parentword = parent;
		}

		/** Used to implement the UndoableEditListener. */
		public void undoableEditHappened(UndoableEditEvent event)
		{
				parentword.undo.addEdit(event.getEdit());
				parentword.undoAction.update();
				parentword.redoAction.update();
       	}
}
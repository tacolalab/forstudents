import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import java.awt.event.*;

/**
 * TTUndoRedo contains two TTActions to provide undo and
 * redo operations on JTextComponents. Each TTAction contains a
 * JButton and JMenuItem.
 *<p>
 * When the user selects one of the buttons or menu items an ActionEvent is sent
 * to all listeners. The <b>command</b> field will contain the string
 * "UNDO" or "REDO", and the <b>modifiers</b> field will be incidentally set to
 * 1. (Note: It will not usually be necessary
 * to listen to these events, the operation is automatically provided on the
 * current JTextComponent.)
 */
public class TTUndoRedo extends TextTool
{
	private Undo undo=new Undo();
	private Redo redo=new Redo();
	private Vector edits=new Vector();
	private int editPos=-1;
	private UndoListener undoListener=new UndoListener();

	/**
	 * Constructs a TTUndoRedo object
	 */
	public TTUndoRedo()
	{
		undo.getButton().setEnabled(false);
		redo.getButton().setEnabled(false);
	}

	/**
	 * Constructs a TTUndoRedo object to operate on the specified text component.
	 */
	public TTUndoRedo(JTextComponent t)
	{
		this();
		setTextComponent(t);
	}

	private class Undo extends TTAction
	{
		public Undo()
		{
			super("Undo");
		}

		protected void performAction(boolean keyed)
		{
			if (text!=null)
			{
				if (editPos==-1)
				{
					undo.getButton().setEnabled(false);
					undo.getMenuItem().setEnabled(false);
					return;
				}

				UndoableEdit e=(UndoableEdit)edits.elementAt(editPos);
				if (e.canUndo())
				{
					e.undo();
					editPos--;
				}

				if(e.canRedo())
				{
					redo.getButton().setEnabled(true);
					redo.getMenuItem().setEnabled(true);
				}
				else
				{
					redo.getButton().setEnabled(false);
					redo.getMenuItem().setEnabled(false);
				}
			}
			else
			{
				undo.getButton().setEnabled(false);
				undo.getMenuItem().setEnabled(false);
			}


			TTUndoRedo.this.postAction(
				new ActionEvent(TTUndoRedo.this,ActionEvent.ACTION_PERFORMED,
				"UNDO",1));

		}
	}

	private class Redo extends TTAction
	{
		public Redo()
		{
			super("Redo");
		}

		protected void performAction(boolean keyed)
		{
			if (text!=null)
			{
				if (editPos+1==edits.size())
				{
					redo.getButton().setEnabled(false);
					redo.getMenuItem().setEnabled(false);
					return;
				}
				UndoableEdit e=(UndoableEdit)edits.elementAt(editPos+1);
				if (e.canRedo())
				{
					e.redo();
					redo.getButton().setEnabled(true);
					redo.getMenuItem().setEnabled(true);
					editPos++;
				}
				else
				{
					redo.getButton().setEnabled(false);
					redo.getMenuItem().setEnabled(false);
				}
			}
			else
			{
				redo.getButton().setEnabled(false);
				redo.getMenuItem().setEnabled(false);
			}

			TTUndoRedo.this.postAction(
				new ActionEvent(TTUndoRedo.this,ActionEvent.ACTION_PERFORMED,
				"REDO",1));
		}
	}

	/**
	 * Gets the undo TTAction, from which a JButton and/or
	 * JMenuItem may be obtained.
	 */
	public TTAction getUndo()
	{
		return undo;
	}

	/**
	 * Gets the redo TTAction, from which a JButton and/or
	 * JMenuItem may be obtained.
	 */
	public TTAction getRedo()
	{
		return redo;
	}

	public void setTextComponent(JTextComponent tp)
	{
		if (text!=null)
			text.getDocument().removeUndoableEditListener(undoListener);
		super.setTextComponent(tp);
		if (tp!=null)
		{
			tp.getDocument().addUndoableEditListener(undoListener);
			undo.setTextComponent(tp);
			redo.setTextComponent(tp);
		}
	}

	private class UndoListener implements UndoableEditListener
	{
		public void undoableEditHappened(UndoableEditEvent e)
		{
			//up til now we have avoided permanently destroying edits if the user
			//has been undoing, in case they want to redo, but with a new edit
			//we can get rid of them
			while (editPos+1<edits.size())
				edits.removeElement(edits.lastElement());

			edits.add(e.getEdit());
			editPos=edits.size()-1;

			UndoableEdit u = (UndoableEdit)e.getEdit();

			if (u.canUndo())
			{
				undo.getButton().setEnabled(true);
				undo.getMenuItem().setEnabled(true);
			}
			else
			{
				undo.getButton().setEnabled(false);
				undo.getMenuItem().setEnabled(false);
			}

			if (u.canRedo())
			{
				redo.getButton().setEnabled(true);
				redo.getMenuItem().setEnabled(true);
			}
			else
			{
				redo.getButton().setEnabled(false);
				redo.getMenuItem().setEnabled(false);
			}
		}
	}
}

//small bug: disable undo,redo button after 1 step only.

import java.beans.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Defines all the items in the edit menu.
 */
public class EditMenu extends javax.swing.JMenu
{
	/*** Reference to ConnectActions interface */
	private transient ConnectActions actionsConnection;

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** Menu Item for "undo" action */
	private javax.swing.JMenuItem UndoItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("UndoIconImage")));

	/*** Menu Item for "redo" action */
	private javax.swing.JMenuItem RedoItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("RedoIconImage")));

	/*** Menu Item for "cut" action */
	private javax.swing.JMenuItem CutItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("cut")));

	/*** Menu Item for "copy" action */
	private javax.swing.JMenuItem CopyItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("copy")));

	/*** Menu Item for "paste" action */
	private javax.swing.JMenuItem PasteItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("paste")));

	/*** Menu Item for "delete" action */
	private javax.swing.JMenuItem DeleteItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("delete")));

	/*** Menu Item for "delete Slide" action */
	private javax.swing.JMenuItem DeleteSlideItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("empty")));

	/*** Menu Item for "find / replace" action */
	private javax.swing.JMenuItem FindReplaceItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("findReplace")));

	/*** Menu Item for "bring to front" action */
	private javax.swing.JMenuItem BringToFrontItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("bringToFront")));

	/*** Menu Item for "sent to back" action */
	private javax.swing.JMenuItem SentToBackItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("sentToBack")));

	/**
	 * Create all the menu items for edit menu and register listeners
	 */
	public EditMenu()
	{
		//set bilingual font
		bilingualFont = Arangam.bilingualFont;
		setFont(bilingualFont);

		//set font, mnemonic, accelerator for menu item and
		//add it to edit menu

		TTUndoRedo undo = AText.undo;

		UndoItem = undo.getUndo().getMenuItem();
		UndoItem.setFont(bilingualFont);
		UndoItem.setMnemonic((int)'U');
		add(UndoItem);

		RedoItem = undo.getRedo().getMenuItem();
		RedoItem.setFont(bilingualFont);
		RedoItem.setMnemonic((int)'R');
		add(RedoItem);

		CutItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke
			(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK));
		CutItem.setFont(bilingualFont);
		CutItem.setMnemonic((int)'T');
		add(CutItem);

		CopyItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke
			(java.awt.event.KeyEvent.VK_C, java.awt.Event.CTRL_MASK));
		CopyItem.setFont(bilingualFont);
		CopyItem.setMnemonic((int)'C');
		add(CopyItem);

		PasteItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke
			(java.awt.event.KeyEvent.VK_V, java.awt.Event.CTRL_MASK));
		PasteItem.setFont(bilingualFont);
		PasteItem.setMnemonic((int)'P');
		add(PasteItem);

		add(new javax.swing.JSeparator());

		DeleteItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke
			(java.awt.event.KeyEvent.VK_DELETE, 0));
		DeleteItem.setMnemonic((int)'L');
		DeleteItem.setFont(bilingualFont);
		add(DeleteItem);

		DeleteSlideItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_D, java.awt.Event.CTRL_MASK));
		DeleteSlideItem.setMnemonic((int)'D');
		DeleteSlideItem.setFont(bilingualFont);
		add(DeleteSlideItem);

		add(new javax.swing.JSeparator());

		FindReplaceItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_F, java.awt.Event.CTRL_MASK));
		FindReplaceItem.setMnemonic((int)'F');
		FindReplaceItem.setFont(bilingualFont);
		add(FindReplaceItem);

		add(new javax.swing.JSeparator());

		BringToFrontItem.setMnemonic((int)'F');
		BringToFrontItem.setFont(bilingualFont);
		add(BringToFrontItem);

		SentToBackItem.setMnemonic((int)'B');
		SentToBackItem.setFont(bilingualFont);
		add(SentToBackItem);

		//set layout and size
		setLayout(null);
		Insets ins = getInsets();
		setSize(ins.left + ins.right + 430,ins.top + ins.bottom + 270);

		//register listeners
		SymAction lSymAction = new SymAction();
		this.addActionListener(lSymAction);
		CutItem.addActionListener(lSymAction);
		CopyItem.addActionListener(lSymAction);
		PasteItem.addActionListener(lSymAction);
		DeleteItem.addActionListener(lSymAction);
		DeleteSlideItem.addActionListener(lSymAction);
		FindReplaceItem.addActionListener(lSymAction);
		BringToFrontItem.addActionListener(lSymAction);
		SentToBackItem.addActionListener(lSymAction);
	}

	/**
	 * Connects the class to the <code>ConnectActions</code> interface.
	 * @param actionsConnection interface to access <code>ActionsImpl</code> methods
	 */
	public void getInterface(ConnectActions actionsConnection)
	{
		this.actionsConnection = actionsConnection;
	}

	/**
	 * Sets the locale for MenuItem depends on the current Locale.
	 */
	public void setLocale()
	{
		wordBundle = Arangam.wordBundle;
		setText(wordBundle.getString("edit"));
		UndoItem.setText(wordBundle.getString("undo"));
		RedoItem.setText(wordBundle.getString("redo"));
		CutItem.setText(wordBundle.getString("cut"));
		CopyItem.setText(wordBundle.getString("copy"));
		PasteItem.setText(wordBundle.getString("paste"));
		DeleteItem.setText(wordBundle.getString("delete"));
		DeleteSlideItem.setText(wordBundle.getString("deleteSlide"));
		FindReplaceItem.setText(wordBundle.getString("findReplace"));
		BringToFrontItem.setText(wordBundle.getString("bringToFront"));
		SentToBackItem.setText(wordBundle.getString("sentToBack"));
	}

	/**
	 * Activate the MenuItems
	 * @param activate If <code>true</code>z, this MenuItems are enabled;
	 * otherwise disabled.
	 */
	public void activate(boolean activate)
	{
		DeleteSlideItem.setEnabled(activate);
		FindReplaceItem.setEnabled(activate);
		BringToFrontItem.setEnabled(activate);
		SentToBackItem.setEnabled(activate);
	}

	public void setUndoEnabled(boolean enable)
	{
		UndoItem.setEnabled(enable);
		RedoItem.setEnabled(enable);
	}

	/**
	 * Enables the Cut, Copy, Paste button depends on the clipboard content.
	 * @see ConnectActions#isPasteAvailable
	 */
	public void setCCPEnabled()
	{
		ConnectComponent focused = actionsConnection.getFocusedComponent();
		//if any component has focus, then enable cut and copy buttons
		if(focused != null)
		{
			CutItem.setEnabled(true);
			CopyItem.setEnabled(true);
			DeleteItem.setEnabled(true);
			BringToFrontItem.setEnabled(true);
			SentToBackItem.setEnabled(true);
		}
		//else disable
		else
		{
			CutItem.setEnabled(false);
			CopyItem.setEnabled(false);
			DeleteItem.setEnabled(false);
			BringToFrontItem.setEnabled(false);
			SentToBackItem.setEnabled(false);
		}
		//pasting depends on clipboard content
		PasteItem.setEnabled(actionsConnection.isPasteAvailable());
	}

	/**
	 * Listen for action events in menu components. For every
	 * action the corresponding function in ConnectActions is invoked
	 */
	private class SymAction implements java.awt.event.ActionListener
	{
		/**
		 * Invoked when an action occurs - when a menu item in menu
		 * is clicked/ selected.
		 */
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == CutItem)
				CutItem_actionPerformed(event);
			else if (object == CopyItem)
				CopyItem_actionPerformed(event);
			else if (object == PasteItem)
				PasteItem_actionPerformed(event);
			else if (object == DeleteItem)
				deleteItem_actionPerformed(event);
			else if (object == DeleteSlideItem)
				DeleteSlideItem_actionPerformed(event);
			else if (object == FindReplaceItem)
				FindReplace_actionPerformed(event);
			else if (object == BringToFrontItem)
				BringToFrontItem_actionPerformed(event);
			else if (object == SentToBackItem)
				SentToBackItem_actionPerformed(event);
		}
	}

	/**
	 * Action for "cut" menu item
	 * @see ConnectActions#CutEdit
	 */
	private void CutItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.CutEdit();
	}

	/**
	 * Action for "copy" menu item
	 * @see ConnectActions#CopyEdit
	 */
	private void CopyItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.CopyEdit();
	}

	/**
	 * Action for "paste" menu item
	 * @see ConnectActions#PasteEdit
	 */
	private void PasteItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.PasteEdit();
	}

	/**
	 * Action for "delete" menu item
	 * @see ConnectActions#DeleteSlideEdit
	 */
	private void deleteItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.DeleteEdit();
	}

	/**
	 * Action for "delete Slide" menu item
	 * @see ConnectActions#SlideShowView
	 */
	private void DeleteSlideItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.DeleteSlideEdit();
	}

	/**
	 * Action for "fin d/replace" menu item
	 * @see ConnectActions#FindReplaceDialog
	 */
	private	void FindReplace_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.FindReplaceDialog();
	}

	/**
	 * Action for "bring to front" menu item
	 * @see ConnectActions#BringToFrontEdit
	 */
	private	void BringToFrontItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.BringToFrontEdit();
	}

	/**
	 * Action for "sent to back" menu item
	 * @see ConnectActions#SendToBackEdit
	 */
	private	void SentToBackItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.SendToBackEdit();
	}
}

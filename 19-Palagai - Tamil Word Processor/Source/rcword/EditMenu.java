package rcword;

import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 *  Used to contain and organize the edit menu items.
 *
 *  @version	5.12.2002.
 *
 *  @author		RCILTS-Tamil,MIT.
 */
public class EditMenu extends javax.swing.JMenu
{
	/**
	  *	Appropriate font for the current local for entire GUI.
	  */
	Font bilingualFont;

	/**
	 *	a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
	 * 	Contains the word bundle for the current local
	 */
	ResourceBundle wordBundle;

	// file edit menu items
	/** menu item for undo operation */
	public static javax.swing.JMenuItem UndoItem = new javax.swing.JMenuItem();
	/** menu item for redp operation */
	public static javax.swing.JMenuItem RedoItem = new javax.swing.JMenuItem();
	/** menu item for cut operation */
	public static javax.swing.JMenuItem CutItem = new javax.swing.JMenuItem();
	/** menu item for copy operation */
	public static javax.swing.JMenuItem CopyItem = new javax.swing.JMenuItem();
	/** menu item for paste operation */
	public static javax.swing.JMenuItem PasteItem = new javax.swing.JMenuItem();
	/** menu item for clear operation */
	public static javax.swing.JMenuItem ClearItem = new javax.swing.JMenuItem();
	/** menu item for selectall operation */
	public static javax.swing.JMenuItem SelectAllItem = new javax.swing.JMenuItem();
	/** menu item for find operation */
	public static javax.swing.JMenuItem FindItem = new javax.swing.JMenuItem();
	/** menu item for findreplace operation */
	public static javax.swing.JMenuItem FindReplaceItem = new javax.swing.JMenuItem();

	// icons for edit menu items
	/** icon for the cut operation */
	ImageIcon cutIcon = ImagesLocator.getImage("rcword/Images/cut.gif");
	/** icon for the copy operation */
	ImageIcon copyIcon = ImagesLocator.getImage("rcword/Images/copy.gif");
	/** icon for the paste operation */
	ImageIcon pasteIcon = ImagesLocator.getImage("rcword/Images/paste.gif");

	//ImageIcon emptyIcon = ImagesLocator.getImage("rcword/Images/empty"));

    /**
	 * Creates an object of <code> EditMenu </code> with all edit menu items
	 * @ param word a reference to the main <code> Word </code> object
 	 */
	public EditMenu(Word word)
	{
		parentword = word;
		bilingualFont = parentword.defaultfont;
		setFont(bilingualFont);

        // creates an UndoAction object and assign it to the undo menu item
		UndoAction undo_action = new UndoAction(parentword);
		undo_action.setEnabled(true);
		add(undo_action);
		UndoItem = getItem(0);
		UndoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke
						(java.awt.event.KeyEvent.VK_Z, java.awt.Event.CTRL_MASK));
		UndoItem.setFont(bilingualFont);
		UndoItem.setMnemonic((int)'Z');

		// creates an RedoAction object and assign it to the redo menu item
		RedoAction redo_action = new RedoAction(parentword);
		redo_action.setEnabled(true);
		add(redo_action);
		RedoItem = getItem(1);
		RedoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke
				(java.awt.event.KeyEvent.VK_Y, java.awt.Event.CTRL_MASK));
		RedoItem.setFont(bilingualFont);
		RedoItem.setMnemonic((int)'Y');

		add(new javax.swing.JSeparator());

		// creates an CutAction object and assign it to the cut menu item
		CutAction cutaction = new CutAction(parentword);
		add(cutaction);
		CutItem = getItem(3);
		CutItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke
					(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK));
		CutItem.setFont(bilingualFont);
		CutItem.setMnemonic((int)'T');

		// creates an CopyAction object and assign it to the copy menu item
		CopyAction copy_action = new CopyAction(parentword);
		//add(copy_action);
		//CopyItem = getItem(4);
		CopyItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke
					(java.awt.event.KeyEvent.VK_C, java.awt.Event.CTRL_MASK));
		CopyItem.setFont(bilingualFont);
		CopyItem.setMnemonic((int)'C');
		add(CopyItem);
		CopyItem.addActionListener(copy_action);


		// creates an PasteAction object and assign it to the paste menu item
		PasteAction paste_action = new PasteAction(parentword);
		add(paste_action);
		PasteItem = getItem(5);
		PasteItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke
					(java.awt.event.KeyEvent.VK_V, java.awt.Event.CTRL_MASK));
		PasteItem.setFont(bilingualFont);
		PasteItem.setMnemonic((int)'P');

		add(new javax.swing.JSeparator());

		ClearAction clear_action = new ClearAction(parentword);
		add(clear_action);
		ClearItem = getItem(7);
		//ClearItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke
		//				(java.awt.event.KeyEvent.VK_A, java.awt.Event.CTRL_MASK));
		ClearItem.setFont(bilingualFont);
		//ClearItem.setMnemonic((int)'A');


		// creates an SelectallAction object and assign it to the select all menu item
		SelectallAction selectall_action = new SelectallAction(parentword);
		add(selectall_action);
		SelectAllItem = getItem(8);
		SelectAllItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke
						(java.awt.event.KeyEvent.VK_L, java.awt.Event.CTRL_MASK));
		SelectAllItem.setFont(bilingualFont);
		SelectAllItem.setMnemonic((int)'A');

		add(new javax.swing.JSeparator());

		// creates an FindAction object and assign it to the find menu item
		FindAction find_action = new FindAction(parentword);
		add(find_action);
		FindItem = getItem(10);
		FindItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
						java.awt.event.KeyEvent.VK_F, java.awt.Event.CTRL_MASK));
		FindItem.setMnemonic((int)'F');
		FindItem.setFont(bilingualFont);

		// creates an ReplaceAction object and assign it to the replace menu item
		ReplaceAction replace_action = new ReplaceAction(parentword);
		add(replace_action);
		FindReplaceItem = getItem(11);
		FindReplaceItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
						java.awt.event.KeyEvent.VK_K, java.awt.Event.CTRL_MASK));
		FindReplaceItem.setMnemonic((int)'H');
		FindReplaceItem.setFont(bilingualFont);

		CutItem.setIcon(cutIcon);
		CopyItem.setIcon(copyIcon);
		PasteItem.setIcon(pasteIcon);

		setLayout(null);
		Insets ins = getInsets();
		setSize(ins.left + ins.right + 430,ins.top + ins.bottom + 270);

	}

	/**
	 * Sets the current locale to the user interface.
	 */
	public void setLocale()
	{
		wordBundle = parentword.wordBundle;
		setText(wordBundle.getString("Edit"));
		UndoItem.setText(wordBundle.getString("Undo"));
		RedoItem.setText(wordBundle.getString("Redo"));
		CutItem.setText(wordBundle.getString("Cut"));
		CopyItem.setText(wordBundle.getString("Copy"));
		PasteItem.setText(wordBundle.getString("Paste"));
		SelectAllItem.setText(wordBundle.getString("SellectAll"));
		FindItem.setText(wordBundle.getString("Find"));
		FindReplaceItem.setText(wordBundle.getString("Replace"));
	}

    /**
    * Used to activate and deactivate the menu items.
    */
	public void activate(boolean activate)
	{
		FindReplaceItem.setEnabled(activate);
	}

}

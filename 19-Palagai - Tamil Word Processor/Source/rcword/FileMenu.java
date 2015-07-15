package rcword;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.IOException;

/**
 *	Used to contain and organize the file menu items
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
public class FileMenu extends javax.swing.JMenu
{
	/**
     * Appropriate font for the current local for entire GUI.
	 */
	Font bilingualFont;

	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	/**
	 * 	Contains the word bundle for the current local
	 */
	ResourceBundle wordBundle;

	//creates filemenu items
	/** menu item for the new file operation */
	javax.swing.JMenuItem NewItem = new javax.swing.JMenuItem();
	/** menu item for the open file operation */
	javax.swing.JMenuItem OpenItem = new javax.swing.JMenuItem();
	//javax.swing.JMenuItem CloseItem = new javax.swing.JMenuItem();
	/** separator for the menu items */
	javax.swing.JSeparator JSeparator1 = new javax.swing.JSeparator();
	/** menu item for the save operation */
	javax.swing.JMenuItem SaveItem = new javax.swing.JMenuItem();
	/** menu item for the save as operation */
	javax.swing.JMenuItem SaveAsItem = new javax.swing.JMenuItem();
	/** separator for the menu items */
	javax.swing.JSeparator JSeparator2 = new javax.swing.JSeparator();
	/** menu item for the page setup operation */
	javax.swing.JMenuItem PageSetupItem = new javax.swing.JMenuItem();
	/** menu item for the print operation */
	javax.swing.JMenuItem PrintItem = new javax.swing.JMenuItem();
	/** separator for the menu items */
	javax.swing.JSeparator JSeparator3 = new javax.swing.JSeparator();
	/** menu item for the email operation */
	javax.swing.JMenuItem EmailItem = new javax.swing.JMenuItem();
	/** separator for the menu items */
	javax.swing.JSeparator JSeparator4 = new javax.swing.JSeparator();
	/** separator for the menu items */
	javax.swing.JSeparator JSeparator5 = new javax.swing.JSeparator();
	/** menu item for the exit operation */
	static javax.swing.JMenuItem ExitItem = new javax.swing.JMenuItem();

	// icons for file menu items
	/** icon for the new file menu items */
	ImageIcon newIcon = ImagesLocator.getImage("rcword/Images/new.gif");
	/** icon for the open file menu items */
	ImageIcon openIcon = ImagesLocator.getImage("rcword/Images/open.gif");
	//ImageIcon closeIcon = ImagesLocator.getImage(Arangam.imageBundle.getString("close"));
	/** icon for the save file menu items */
	ImageIcon saveIcon = ImagesLocator.getImage("rcword/Images/save.gif");
	/** icon for the page setup menu items */
	ImageIcon pagesetupIcon = ImagesLocator.getImage("rcword/Images/pagesetup.gif");
	/** icon for the print menu items */
	ImageIcon printIcon = ImagesLocator.getImage("rcword/Images/print.gif");
	//ImageIcon emptyIcon = ImagesLocator.getImage("rcword/Images/print.gif");

    /**
	 * Creates an object of <code> FileMenu </code> with all file menu items.
	 * @ param word a reference to the main <code> Word </code> object
 	 */
	public FileMenu(Word word)
	{
		parentword = word;
		bilingualFont = Word.defaultfont;
		setFont(bilingualFont);

		NewItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
									java.awt.event.KeyEvent.VK_N,
									java.awt.Event.CTRL_MASK));
		NewItem.setFont(bilingualFont);
		NewItem.setMnemonic((int)'N');
		add(NewItem);

		OpenItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
		OpenItem.setFont(bilingualFont);
		OpenItem.setMnemonic((int)'O');
		add(OpenItem);


		add(JSeparator1);

		SaveItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
		java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
		SaveItem.setMnemonic((int)'S');
		SaveItem.setFont(bilingualFont);
		add(SaveItem);

		//SaveAsItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
		//		java.awt.event.KeyEvent.VK_A, java.awt.Event.CTRL_MASK));
		//SaveAsItem.setMnemonic((int)'A');
		SaveAsItem.setFont(bilingualFont);
		add(SaveAsItem);

		add(JSeparator2);

		PageSetupItem.setMnemonic((int)'U');
		PageSetupItem.setFont(bilingualFont);
		add(PageSetupItem);

		PrintItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_P, java.awt.Event.CTRL_MASK));
		PrintItem.setMnemonic((int)'P');
		PrintItem.setFont(bilingualFont);
		add(PrintItem);

		add(JSeparator3);

		EmailItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
					java.awt.event.KeyEvent.VK_M, java.awt.Event.CTRL_MASK));
		EmailItem.setMnemonic((int)'M');
		EmailItem.setFont(bilingualFont);
		add(EmailItem);

		add(JSeparator4);
		add(JSeparator5);

		ExitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_X, java.awt.Event.ALT_MASK));
		ExitItem.setMnemonic((int)'X');
		ExitItem.setFont(bilingualFont);
		add(ExitItem);

		NewItem.setIcon(newIcon);
		OpenItem.setIcon(openIcon);
		//CloseItem.setIcon(closeIcon);
		SaveItem.setIcon(saveIcon);
		//SaveAsItem.setIcon(emptyIcon);
		PageSetupItem.setIcon(pagesetupIcon);
		PrintItem.setIcon(printIcon);
		//ExitItem.setIcon(emptyIcon);

		setLayout(null);
		Insets ins = getInsets();
		setSize(ins.left + ins.right + 230,ins.top + ins.bottom + 270);

		//register listeners
		SymAction lSymAction = new SymAction();
		NewItem.addActionListener(lSymAction);
		OpenItem.addActionListener(lSymAction);
		//CloseItem.addActionListener(lSymAction);
		SaveItem.addActionListener(lSymAction);
		SaveAsItem.addActionListener(lSymAction);
		PageSetupItem.addActionListener(lSymAction);
		PrintItem.addActionListener(lSymAction);
		EmailItem.addActionListener(lSymAction);
		ExitItem.addActionListener(lSymAction);
	}


	/**
	 *	Used to do the file menu actions.
	 *  @ version 5.12.2002
	 * 	@ author RCILTS-Tamil,MIT.
 	 */
	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == NewItem)
			{   //parentword.workspace.setText(" ");
				parentword.currentfile.newFile(true);
			}
			else if (object == OpenItem)
				parentword.currentfile.openfile();
			//else if (object == CloseItem)
			//	CloseItem_actionPerformed(event);
			else if (object == SaveItem)
				parentword.currentfile.savefile(true);
			else if (object == SaveAsItem)
				parentword.currentfile.saveAsfile();
			else if (object == PageSetupItem)
				parentword.pageSetup();
			else if (object == PrintItem)
			{
				Thread runner = new Thread()
					{
						public void run()
						{
							parentword.printData();
						}
					};
				runner.start();

			}
			else if(object == EmailItem)
			{
				if(parentword.email == null)
				          parentword.email = new EMail(parentword);
				parentword.email.setLocale();
				parentword.email.show();
			}
			if (object == ExitItem)
			{
				try
				{
					if(!parentword.currentfile.confirmClose())
					{
						return;
					}
					// before closing the wordprocesser write recently used files in 'recentfile'.
					parentword.currentfile.addinRecentFile();
				}
				catch(IOException erf)
				{
				 	System.out.println( erf+"-----> exception at closing ");
				}
				System.exit(0);         // exit
			}
		}
	}

	/**
	 * Sets the current locale to the user interface.
	 */
	public void setLocale()
	{
		wordBundle = parentword.wordBundle;
		setText(wordBundle.getString("File"));
		NewItem.setText(wordBundle.getString("New"));
		OpenItem.setText(wordBundle.getString("Open"));
		//CloseItem.setText(wordBundle.getString("close"));
		SaveItem.setText(wordBundle.getString("Save"));
		SaveAsItem.setText(wordBundle.getString("SaveAs"));
		PageSetupItem.setText(wordBundle.getString("PageSetup"));
		EmailItem.setText(wordBundle.getString("E-mail"));
		PrintItem.setText(wordBundle.getString("Print"));
		ExitItem.setText(wordBundle.getString("Exit"));
	}

    /** Activates the file menu items. */
	public void activate(boolean activate)
	{
		//CloseItem.setEnabled(activate);
		SaveItem.setEnabled(activate);
		SaveAsItem.setEnabled(activate);
		PageSetupItem.setEnabled(activate);
		PrintItem.setEnabled(activate);
		ExitItem.setEnabled(activate);
	}
}

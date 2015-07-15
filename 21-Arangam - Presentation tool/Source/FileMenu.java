import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Defines all the items in the file menu.
 */
public class FileMenu extends javax.swing.JMenu
{

	/*** ActionsImpl class reference */
	private transient ConnectActions actionsConnection;

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** Menu Item for "new file" action */
	private javax.swing.JMenuItem NewItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("new")));

	/*** Menu Item for "open file" action */
	private javax.swing.JMenuItem OpenItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("open")));

	/*** Menu Item for "close file" action */
	private javax.swing.JMenuItem CloseItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("close")));

	/*** Menu Item for "save file" action */
	private javax.swing.JMenuItem SaveItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("save")));

	/*** Menu Item for "save as file" action */
	private javax.swing.JMenuItem SaveAsItem = new javax.swing.JMenuItem(
		);

	/*** Menu Item for "page setup" action */
	private javax.swing.JMenuItem PageSetupItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("pageSetup")));

	/*** Menu Item for "print file" action */
	private javax.swing.JMenuItem PrintItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("print")));

	/*** Menu Item for "exit" action */
	private javax.swing.JMenuItem ExitItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("empty")));

	/**
	 * Constructor of the File Menu column create all the items and
	 * all the icon.
	 */
	public FileMenu()
	{
		//set bilingual font
		bilingualFont = Arangam.bilingualFont;
		setFont(bilingualFont);

		//set font, mnemonic, accelerator for menu item and
		//add it to file menu
		NewItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
		java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
		NewItem.setFont(bilingualFont);
		NewItem.setMnemonic((int)'N');
		add(NewItem);

		OpenItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
		OpenItem.setFont(bilingualFont);
		OpenItem.setMnemonic((int)'O');
		add(OpenItem);

		CloseItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_F3, java.awt.Event.ALT_MASK));
		CloseItem.setMnemonic((int)'C');
		CloseItem.setFont(bilingualFont);
		add(CloseItem);

		add(new javax.swing.JSeparator());

		SaveItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
		java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
		SaveItem.setMnemonic((int)'S');
		SaveItem.setFont(bilingualFont);
		add(SaveItem);

		SaveAsItem.setMnemonic((int)'A');
		SaveAsItem.setFont(bilingualFont);
		add(SaveAsItem);

		add(new javax.swing.JSeparator());

		PageSetupItem.setMnemonic((int)'U');
		PageSetupItem.setFont(bilingualFont);
		add(PageSetupItem);

		PrintItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_P, java.awt.Event.CTRL_MASK));
		PrintItem.setMnemonic((int)'P');
		PrintItem.setFont(bilingualFont);
		add(PrintItem);

		add(new javax.swing.JSeparator());

		ExitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_X, java.awt.Event.ALT_MASK));
		ExitItem.setMnemonic((int)'X');
		ExitItem.setFont(bilingualFont);
		add(ExitItem);

		//set layout and size
		setLayout(null);
		Insets ins = getInsets();
		setSize(ins.left + ins.right + 430,ins.top + ins.bottom + 270);

		//register listeners
		SymAction lSymAction = new SymAction();
		NewItem.addActionListener(lSymAction);
		OpenItem.addActionListener(lSymAction);
		CloseItem.addActionListener(lSymAction);
		SaveItem.addActionListener(lSymAction);
		SaveAsItem.addActionListener(lSymAction);
		PageSetupItem.addActionListener(lSymAction);
		PrintItem.addActionListener(lSymAction);
		ExitItem.addActionListener(lSymAction);
	}

	/**
	 * Connect the class to the <code>ConnectActions</code> interface.
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
		setText(wordBundle.getString("file"));
		NewItem.setText(wordBundle.getString("new"));
		OpenItem.setText(wordBundle.getString("open"));
		CloseItem.setText(wordBundle.getString("close"));
		SaveItem.setText(wordBundle.getString("save"));
		SaveAsItem.setText(wordBundle.getString("saveAs"));
		PageSetupItem.setText(wordBundle.getString("pageSetup"));
		PrintItem.setText(wordBundle.getString("print"));
		ExitItem.setText(wordBundle.getString("exit"));
	}

	/**
	 * Activate the MenuItems
	 * @param activate If <code>true</code>, this MenuItems are enabled;
	 * otherwise disabled.
	 */
	public void activate(boolean activate)
	{
		CloseItem.setEnabled(activate);
		SaveItem.setEnabled(activate);
		SaveAsItem.setEnabled(activate);
		PageSetupItem.setEnabled(activate);
		PrintItem.setEnabled(activate);
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
			if (object == NewItem)
				NewItem_actionPerformed(event);
			else if (object == OpenItem)
				OpenItem_actionPerformed(event);
			else if (object == CloseItem)
				CloseItem_actionPerformed(event);
			else if (object == SaveItem)
				SaveItem_actionPerformed(event);
			else if (object == SaveAsItem)
				SaveAsItem_actionPerformed(event);
			else if (object == PageSetupItem)
				PageSetupItem_actionPerformed(event);
			else if (object == PrintItem)
				PrintItem_actionPerformed(event);
			if (object == ExitItem)
				ExitItem_actionPerformed(event);
		}
	}

	/**
	 * Action for "new file" menu item
	 * @see ConnectActions#NewFile
	 */
	private void NewItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.NewFile();
	}

	/**
	 * Action for "open file" menu item
	 * @see ConnectActions#OpenFile
	 */
	private void OpenItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.OpenFile();
	}

	/**
	 * Action for "close file" menu item
	 * @see ConnectActions#CloseFile
	 */
	private	void CloseItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.CloseFile();
	}

	/**
	 * Action for "save file" menu item
	 * @see ConnectActions#SaveFile
	 */
	private	void SaveItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.SaveFile();
	}

	/**
	 * Action for "save as" menu item
	 * @see ConnectActions#SaveAsFile
	 */
	private	void SaveAsItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.SaveAsFile();
	}

	/**
	 * Action for "page setup" menu item
	 * @see ConnectActions#PageSetupFile
	 */
	private	void PageSetupItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.PageSetupFile();
	}

	/**
	 * Action for "print file" menu item
	 * @see ConnectActions#PrintFile
	 */
	private	void PrintItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.PrintFile();
	}

	/**
	 * Action for "exit" menu item
	 * @see ConnectActions#ExitFile
	 */
	private	void ExitItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ExitFile();
	}
}

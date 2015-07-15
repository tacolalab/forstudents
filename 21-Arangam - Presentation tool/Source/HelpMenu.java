import java.beans.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class HelpMenu extends javax.swing.JMenu
{
	/*** ActionsImpl class reference */
	private transient ConnectActions actionsConnection;

	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private Font bilingualFont;

	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** Menu Item for "about" action */
	private javax.swing.JMenuItem AboutItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("about")));
	/*** Menu Item for "arangam help" action */
	private javax.swing.JMenuItem ArangamHelpItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("help")));

	/**
	 * Create all the menu items for Help menu.
	 */
	public HelpMenu()
	{
		//set bilingual font
		bilingualFont = Arangam.bilingualFont;
		setFont(bilingualFont);

		//set font, mnemonic, accelerator for menu item and
		//add it to file menu
		ArangamHelpItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_F1, 0));
		ArangamHelpItem.setMnemonic((int)'H');
		ArangamHelpItem.setFont(bilingualFont);
		add(ArangamHelpItem);

		AboutItem.setMnemonic((int)'A');
		AboutItem.setFont(bilingualFont);
		add(AboutItem);

		//set layout and size
		setLayout(null);
		Insets ins = getInsets();
		setSize(ins.left + ins.right + 430,ins.top + ins.bottom + 270);

		//register listeners
		SymAction lSymAction = new SymAction();
		AboutItem.addActionListener(lSymAction);
		ArangamHelpItem.addActionListener(lSymAction);
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
		setText(wordBundle.getString("help"));
		AboutItem.setText(wordBundle.getString("about"));
		ArangamHelpItem.setText(wordBundle.getString("arangamHelp"));
	}

	/**
	 * Activate the MenuItems
	 * @param activate If <code>true</code>, this MenuItems are enabled;
	 * otherwise disabled.
	 */
	public void activate(boolean activate)
	{
		AboutItem.setEnabled(activate);
		ArangamHelpItem.setEnabled(activate);
	}

	/**
	 * Listen for action events in menu components. For every
	 * action the corresponding function in ConnectActions is invoked
	 */
	private class SymAction implements java.awt.event.ActionListener
	{
		/**
		 * Invoked when an action occurs - when a menu item in menu
		 * is clicked/selected. The corresponding function in ActionsImpl
		 * is called
		 */
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == AboutItem)
				AboutItem_actionPerformed(event);
			else if (object == ArangamHelpItem)
				ArangamHelpItem_actionPerformed(event);

		}

	}

	/**
	 * Action for "about" menu item
	 * @see ConnectActions#AboutHelp
	 */
	private void AboutItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.AboutHelp();
	}

	/**
	 * Action for "arangam help" menu item
	 * @see ConnectActions#ArangamHelp
	 */
	private void ArangamHelpItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ArangamHelp();
	}
}

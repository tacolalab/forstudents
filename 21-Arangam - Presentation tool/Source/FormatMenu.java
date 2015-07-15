import java.beans.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class FormatMenu extends javax.swing.JMenu
{
	/*** ActionsImpl class reference */
	private transient ConnectActions actionsConnection;

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** Menu Item for "font format" action */
	private javax.swing.JMenuItem FontItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("font")));

	/*** Menu Item for "shape format" action */
	private javax.swing.JMenuItem ShapeItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("format")));

	/*** Separates  menu items */
	private javax.swing.JSeparator JSeparator1 = new javax.swing.JSeparator();

	/*** Menu Item for "background format" action */
	private javax.swing.JMenuItem BackgroundItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("background")));

	/**
	 * Create all the menu items for format menu
	 */
	public FormatMenu()
	{
		//set bilingual font
		bilingualFont = Arangam.bilingualFont;
		setFont(bilingualFont);
		FontItem.setFont(bilingualFont);

		//set font, mnemonic, accelerator for menu item and
		//add it to format menu
		FontItem.setMnemonic((int)'F');
		add(FontItem);

		ShapeItem.setFont(bilingualFont);
		ShapeItem.setMnemonic((int)'S');
		add(ShapeItem);

		add(JSeparator1);

		BackgroundItem.setFont(bilingualFont);
		BackgroundItem.setMnemonic((int)'B');
		add(BackgroundItem);

		//set layout and size
		setLayout(null);
		Insets ins = getInsets();
		setSize(getPreferredSize());

		//register listeners
		SymAction lSymAction = new SymAction();
		FontItem.addActionListener(lSymAction);
		ShapeItem.addActionListener(lSymAction);
		BackgroundItem.addActionListener(lSymAction);

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
		setText(wordBundle.getString("format"));
		FontItem.setText(wordBundle.getString("font"));
		ShapeItem.setText(wordBundle.getString("shape"));
		BackgroundItem.setText(wordBundle.getString("background"));
	}

	/**
	 * Activate the menu item depends on the component that has focus
	 * @param activate If <code>true</code>, this MenuItems are enabled;
	 * otherwise disabled.
	 */
	public void activate()
	{
		//enable the menu items - depends on the currently focused component
		ConnectComponent focused = actionsConnection.getFocusedComponent();
		if(focused != null)
		{
			String focusedName = focused.getClass().getName();
			if(focusedName.equals("AText"))
			{
				FontItem.setEnabled(true);
				ShapeItem.setEnabled(false);
			}
			if(focusedName.equals("AShape"))
			{
				FontItem.setEnabled(true);
				ShapeItem.setEnabled(true);
			}
		}else
		{
			ShapeItem.setEnabled(false);
			FontItem.setEnabled(false);
		}
		//background format is always enabled for Slide
	}

	/**
	 * Listen for action events in menu components. For every
	 * action the corresponding function in ConnectActions is invoked
	 */
	private class SymAction implements java.awt.event.ActionListener
	{
		/**
		 * Invoked when an action occurs - when a menu item in menu
		 * is clicked/selected.
		 */
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == FontItem)
				FontItem_actionPerformed(event);
			else if (object == ShapeItem)
				ShapeItem_actionPerformed(event);
			else if (object == BackgroundItem)
				BackgroundItem_actionPerformed(event);
		}
	}

	/**
	 * Action for "font format" menu item
	 * @see ConnectActions#FontFormat
	 */
	private void FontItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.FontFormat();
	}

	/**
	 * Action for "shape format" menu item
	 * @see ConnectActions#ShapeFormat
	 */
	private void ShapeItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ShapeFormat();
	}

	/**
	 * Action for "background format" menu item
	 * @see ConnectActions#BackgroundFormat
	 */
	private void BackgroundItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.BackgroundFormat();
	}
}

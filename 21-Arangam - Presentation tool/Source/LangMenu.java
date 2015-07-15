import java.beans.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Defines all the menu items in the language menu
 */
public class LangMenu extends javax.swing.JMenu
{

	/*** ActionsImpl class reference */
 	private transient ConnectActions actionsConnection;

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** Menu Item for "Tamil" action */
	private javax.swing.JMenuItem tamilLang =
		new javax.swing.JRadioButtonMenuItem("",true);
	/*** Menu Item for "English" action */
	private javax.swing.JMenuItem englishLang =
		new javax.swing.JRadioButtonMenuItem();
	/*** Menu Item for "Tamil English" action */
	private javax.swing.JMenuItem tamEnglishLang =
		new javax.swing.JRadioButtonMenuItem();

	private ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Constructor of the Language Menu column create all the items and
	 * all the icon.
	 */
	public LangMenu()
	{
		//set bilingual font
		bilingualFont = Arangam.bilingualFont;
		setFont(bilingualFont);

		//add menu items to the button group
		//only one language should be selected at a time
		buttonGroup.add(tamilLang);
		buttonGroup.add(englishLang);
		buttonGroup.add(tamEnglishLang);

		//set font, mnemonic, accelerator for menu item and
		//add it to format menu
		tamilLang.setAccelerator(javax.swing.KeyStroke.getKeyStroke("F9"));
		tamilLang.setMnemonic((int)'T');
		tamilLang.setFont(bilingualFont);
		add(tamilLang);

		englishLang.setAccelerator(javax.swing.KeyStroke.getKeyStroke("F8"));
		englishLang.setMnemonic((int)'E');
		englishLang.setFont(bilingualFont);
		add(englishLang);

		tamEnglishLang.setAccelerator(javax.swing.KeyStroke.getKeyStroke("F7"));
		tamEnglishLang.setMnemonic((int)'G');
		tamEnglishLang.setFont(bilingualFont);
		add(tamEnglishLang);

		//set layout and size
		setLayout(null);
		Insets ins = getInsets();
		setSize(ins.left + ins.right + 430,ins.top + ins.bottom + 270);

		//register listeners
		SymAction lSymAction = new SymAction();
		tamilLang.addActionListener(lSymAction);
		englishLang.addActionListener(lSymAction);
		tamEnglishLang.addActionListener(lSymAction);
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
		setText(wordBundle.getString("language"));
		tamilLang.setText(wordBundle.getString("tamil"));
		englishLang.setText(wordBundle.getString("english"));
		tamEnglishLang.setText(wordBundle.getString("tamEnglish"));
	}

	/**
	 * Activate the MenuItems
	 * @param activate If <code>true</code>, this MenuItems are enabled;
	 * otherwise disabled.
	 */
	public void activate(boolean activate)
	{
		tamilLang.setEnabled(activate);
		englishLang.setEnabled(activate);
		tamEnglishLang.setEnabled(activate);
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
			if (object == tamilLang)
				tamilLang_actionPerformed(event);
			else if (object == englishLang)
				englishLang_actionPerformed(event);
			else if (object == tamEnglishLang)
				tamEnglishLang_actionPerformed(event);
		}
	}

	/**
	 * Action for "Tamil" menu item
	 * @see ConnectActions#tamilLang
	 */
	private void tamilLang_actionPerformed(java.awt.event.ActionEvent event)
	{
		tamilLang.setSelected(true);
		actionsConnection.tamilLang();
	}

	/**
	 * Action for "English" menu item
	 * @see ConnectActions#englishLang
	 */
	private void englishLang_actionPerformed(java.awt.event.ActionEvent event)
	{
		englishLang.setSelected(true);
		actionsConnection.englishLang();
	}

	/**
	 * Action for "Tamil English" menu item
	 * @see ConnectActions#tamEnglishLang
	 */
	private void tamEnglishLang_actionPerformed(java.awt.event.ActionEvent event)
	{
		tamEnglishLang.setSelected(true);
		actionsConnection.tamEnglishLang();
	}
}

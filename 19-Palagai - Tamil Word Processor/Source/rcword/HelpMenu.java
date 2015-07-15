package rcword;

import java.beans.*;
import java.awt.*;
import java.util.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.HyperlinkListener;
import java.awt.event.*;
import java.net.URL;

/**
 * Contains the help menu objects.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
public class HelpMenu extends javax.swing.JMenu
{
	/**
	  * Appropriate font for the current local for entire GUI.
	  */
	Font bilingualFont;

	/**
	 * 	Contains the word bundle for the current local
	 */
	ResourceBundle wordBundle;

	/**
	 * a reference to the main <code> Word </code> object
	 */
	Word parentword;

	//declare controls
	/** menu item to open the about dialog.*/
	javax.swing.JMenuItem AboutItem = new javax.swing.JMenuItem();
	/** menu item to open the help dialog.*/
	javax.swing.JMenuItem HelpItem = new javax.swing.JMenuItem();
	/** icon for the about menu item.*/
	ImageIcon aboutIcon = ImagesLocator.getImage("rcword/Images/about.gif");
	/** icon for the help menu item.*/
	ImageIcon helpIcon = ImagesLocator.getImage("rcword/Images/about.gif");

	/**
	 * Constructor of the Help Menu column create all the items and all the icon.
	 */
	public HelpMenu(Word word)
	{
		bilingualFont = Word.defaultfont;
		setFont(bilingualFont);
		parentword = word;

		HelpItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_F1, 0));
		HelpItem.setMnemonic((int)'H');
		HelpItem.setFont(bilingualFont);
		HelpItem.setIcon(helpIcon);
		add(HelpItem);

		AboutItem.setMnemonic((int)'A');
		AboutItem.setFont(bilingualFont);
		AboutItem.setIcon(aboutIcon);
		add(AboutItem);

		setLayout(null);
		Insets ins = getInsets();
		setSize(ins.left + ins.right + 430,ins.top + ins.bottom + 270);

		//register listeners

		ActionListener aboutaction = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{	// about dialog has to be opened here.
					System.out.println(" about ");
				}
			};
		AboutItem.addActionListener(aboutaction);
		ActionListener helpaction = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					wordHelp();
				}
			};
		HelpItem.addActionListener(helpaction);
	}


	/**
	 * Sets the current language to the user interface.
	 */
	public void setLocale()
	{
		wordBundle = Word.wordBundle;
		setText(wordBundle.getString("Help"));
		AboutItem.setText(wordBundle.getString("About"));
		HelpItem.setText(wordBundle.getString("WordHelp"));
	}

	/**
	 * Activates the MenuItems.
	 * @param activate If true, this MenuItems are enabled;
	 * otherwise disabled.
	 */
	public void activate(boolean activate)
	{
		AboutItem.setEnabled(activate);
		HelpItem.setEnabled(activate);
	}

	/**
	* Opens the help frame.
	*/
	public void wordHelp()
	{
		JFrame helpFrame = new JFrame("Word Help");

		helpFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				parentword.setVisible(true);
			}
		});

		JEditorPane helpPane = new JEditorPane();
		helpPane.setEditable(false);

		try
		{	// get the help file and displays in the help frame
			wordBundle = parentword.wordBundle;
			URL helpURL = getClass().getResource(
				wordBundle.getString("HelpFile"));
			helpPane.setPage(helpURL);
			//helpPane.setContentType("text/html");
			helpPane.setEditable(false);

			//helpPane.addHyperlinkListener(new HelpHyperlinkListener());
		}catch(IOException e)
		{
			System.out.println(e + "-Could not load help file");
		}

		JScrollPane scrollPane = new JScrollPane(helpPane);
		helpFrame.getContentPane().add(scrollPane);
		helpFrame.setSize(550,450);
		helpFrame.setLocation(Utils.
				getMiddle(helpFrame.getSize()));
		helpFrame.setVisible(true);
	}

}

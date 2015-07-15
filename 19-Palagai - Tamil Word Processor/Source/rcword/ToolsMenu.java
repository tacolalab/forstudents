package rcword;

import java.beans.*;
import java.awt.*;
import java.util.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.HyperlinkListener;
import java.awt.event.*;
import java.net.URL;
//import SpellChecker.SpellChecker;

/**
 * Contains the tools menu objects.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
public class ToolsMenu extends javax.swing.JMenu
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

	/** menu item to open the word count dialog.*/
	javax.swing.JMenuItem jm_WordCount = new javax.swing.JMenuItem();
	/** menu item to do the sorting operation.*/
	javax.swing.JMenuItem jm_Sort = new javax.swing.JMenuItem();
	/** menu item to change the current language.*/
	javax.swing.JMenuItem jm_Language = new javax.swing.JMenuItem();
	/** menu item to do the spell check operation.*/
	javax.swing.JMenuItem jm_SpellCheck = new javax.swing.JMenuItem();

	/**
	 * Constructor of the Tools Menu column create all the items and all the icon.
	 * @ param word a reference to the main <code> Word </code> object
	 */
	public ToolsMenu(Word word)
	{
		bilingualFont = Word.defaultfont;
		setFont(bilingualFont);
		parentword = word;

		jm_WordCount.setMnemonic((int)'W');
		jm_WordCount.setFont(bilingualFont);
		add(jm_WordCount);
		ActionListener WordCountaction = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{	// call to open the wordcount dialog has to be opened here.
					doWordCount();
				}
			};
		jm_WordCount.addActionListener(WordCountaction);

		jm_Sort.setMnemonic((int)'S');
		jm_Sort.setFont(bilingualFont);
		add(jm_Sort);
		ActionListener SortAction = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					doSort();
				}
			};
		jm_Sort.addActionListener(SortAction);

		jm_Language.setFont(bilingualFont);
		jm_Language.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
											java.awt.event.KeyEvent.VK_F12,0));
		add(jm_Language);
		ActionListener LanaguageAction = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					doLanguage(ev.getActionCommand());
				}
			};
		jm_Language.addActionListener(LanaguageAction);

		jm_SpellCheck.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
									java.awt.event.KeyEvent.VK_F7, 0));
		jm_SpellCheck.setMnemonic((int)'K');
		jm_SpellCheck.setFont(bilingualFont);
		add(jm_SpellCheck);
		ActionListener SpellCheckAction = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					doSpellCheck();
				}
			};
		jm_SpellCheck.addActionListener(SpellCheckAction);

		setLayout(null);
		Insets ins = getInsets();
		setSize(ins.left + ins.right + 430,ins.top + ins.bottom + 270);


	}


	/**
	 * Sets the current language to the user interface.
	 */
	public void setLocale()
	{
		wordBundle = Word.wordBundle;
		setText(wordBundle.getString("Tools"));
		jm_WordCount.setText(wordBundle.getString("WordCount"));
		jm_Sort.setText(wordBundle.getString("Sort"));
		jm_Language.setText(wordBundle.getString("English"));
		jm_SpellCheck.setText(wordBundle.getString("SpellCheck"));
	}

	/**s
	 * Activates the MenuItems.
	 * @param activate If true, this MenuItems are enabled;
	 * otherwise disabled.
	 */
	public void activate(boolean activate)
	{
		//AboutItem.setEnabled(activate);
		//HelpItem.setEnabled(activate);
	}

	/**
	*	Used to call the wordcount dialog.
	*/
	public void doWordCount()
	{
		boolean textPresent = true;
		try
	    {
		   	 parentword.workspace.selectAll();
			 String text = parentword.workspace.getSelectedText();
			 if(text==null)
			 {
			 	ResourceBundle wordbundle = parentword.wordBundle;
				Object[] options = {wordbundle.getString("Ok")};
				Utils.showDialog(parentword,"EmptyFile",JOptionPane.OK_OPTION,
									JOptionPane.INFORMATION_MESSAGE, null , options, 0);
				return;
		 	 }
		 	 else
		 	 {
				parentword.wdcount= new WordCount(parentword);
				parentword.wdcount.show();
			 }
		}
	    catch(Exception e)
	    {
	   	    System.out.println(e+"\n-------> ex at : ToolsMenu doWordCount");
			return;
		}
	}

	/**
	*	Used to call the sorter process.
	*/
	public void doSort()
	{
		parentword.sort = new Sorter(parentword);
	}

	/**
	*	Used to change the language of the UI.
	*/
	public void doLanguage(String arg)
	{
		if(arg.equals("English") || arg.equals("îñ¤ö¢"))
		{
				if(parentword.current_language)
				{  /* if the language is Tamil,then conversion is Tamil --> English
					conversion is first set the current language as English, set the
					resource bundle as current language's resource bundle then update
					the menu and tool bars with the current language
					*/
					parentword.current_language = false;
					parentword.current_locale = new Locale("en","US");
					wordBundle = ResourceBundle.getBundle("WordBundle",parentword.current_locale);
					parentword.wordBundle = wordBundle;
					parentword.language.updateMenubar();
					parentword.language.updateToolbars();
				}
				else
				{   /* if the language is English, then conversion is English --> Tamil
					conversion is first set the current language as Tamil, set the
					resource bundle as current language's resource bundle then update
					the menu and tool bars with the current language
					*/
					parentword.current_language = true;
					parentword.current_locale = new Locale("ta","IN");
					wordBundle = ResourceBundle.getBundle("WordBundle",parentword.current_locale);
					parentword.wordBundle = wordBundle;
					//language.updateLanguageNew();
					parentword.language.updateMenubar();
					parentword.language.updateToolbars();
				}

				//System.out.println("Current lang:"+parentword.current_language+"  "+wordBundle.getString("Find"));


		}
	}

	/**
	* Used to call the spell checker process.
	*/
	public void doSpellCheck()
	{
		System.out.println(" spellcheck");

		//added by anu on 27/12/02 while integrating with spell checker
		DefaultHighlighter.DefaultHighlightPainter dhp = new
				DefaultHighlighter.DefaultHighlightPainter
					(new Color(255,255,200));//light yellow

		//new SpellChecker(parentword.workspace,dhp);

	}

}

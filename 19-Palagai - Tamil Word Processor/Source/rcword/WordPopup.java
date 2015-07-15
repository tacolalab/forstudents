package rcword;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.awt.datatransfer.*;
import java.util.Hashtable;
import javax.swing.text.*;

/**
 *	Used to show the popup menu in the workspace.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
public class WordPopup extends JPopupMenu implements ActionListener
{
	/**	Contains the word bundle for the current local */
	ResourceBundle wordBundle;

	/** Appropriate font for the current local for entire GUI.	*/
	Font defaultfont;
	/** menuitem for cut action.*/
	JMenuItem cut;
	/** menuitem for copy action.*/
	JMenuItem copy;
	/** menuitem for paste action.*/
	JMenuItem paste;
	/** menuitem for paragraph format action.*/
	JMenuItem fontFormat,paraFormat;
	/** menuitem for page format action.*/
	JMenuItem pageFormat;

	/** a reference to the main <code> Word </code> object */
	Word parentword;

    /**
    * Creates an object of <code> WordPopup </code> with
    * a reference to the main <code> Word </code> object.
    * @param word a reference to the main <code> Word </code> object
    */
	WordPopup(Word word)
	{
		try
		{
			//this.arangamText=(AText)arangamText;
			parentword = word;
			defaultfont = parentword.defaultfont;

			cut =new JMenuItem(ImagesLocator.getImage("rcword/Images/cut.gif"));
			copy =new JMenuItem(ImagesLocator.getImage("rcword/Images/copy.gif"));
			paste =new JMenuItem(ImagesLocator.getImage("rcword/Images/paste.gif"));
			//fontFormat =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("font")));
			paraFormat = new JMenuItem();

			setLocale();

			cut.setFont(defaultfont);
			copy.setFont(defaultfont);
			paste.setFont(defaultfont);
			//fontFormat.setFont(defaultfont);
			paraFormat.setFont(defaultfont);

			add(cut);
			add(copy);
			add(paste);
			add(new javax.swing.JSeparator());
			//add(fontFormat);
			add(paraFormat);
			add(new javax.swing.JSeparator());

			cut.addActionListener(this);
			copy.addActionListener(this);
			paste.addActionListener(this);
			//fontFormat.addActionListener(this);
			paraFormat.addActionListener(this);

			boolean cutCopyEnabled = (parentword.workspace.getSelectedText()!=null);
			copy.setEnabled(cutCopyEnabled);
			cut.setEnabled(cutCopyEnabled);

		}catch(Exception e)
		{
			System.out.println(e + "--WordPopup Cons");
			e.printStackTrace();
		}
	}

    /**
    * Action to perform the popup menuitem's action.
    * @param ae An object of ActionEvent which causes the action.
    */
	public void actionPerformed(ActionEvent ae)
	{
		Object object = ae.getSource();
		if(object==cut)
		{
			parentword.workspace.cut();
		}
		else if(object==copy)
		{
			parentword.workspace.copy();
		}
		else if(object==paste)
		{
			parentword.workspace.paste();
		}
		/*
		else if(object==fontFormat)
		{
			StyledDocument doc = arangamText.getStyledDocument();
			FontDialog fontDialog =
				new FontDialog(arangam,arangamText);
			AttributeSet a = doc.getCharacterElement(arangamText.
				getCaretPosition()-1).getAttributes();
			fontDialog.setAttributes(a);
			fontDialog.show();
		}
		*/
		else if(object == paraFormat)
		{   // gets the cursor position's attributes
			AttributeSet a = parentword.dsd_stydoc.getCharacterElement(
								parentword.workspace.getCaretPosition()).getAttributes();
			parentword.m_paragraphDialog.setAttributes(a);  // sets that attributes to the paragraph dialog
			parentword.m_paragraphDialog.setLocale();// sets the current language to the paragraph dialog
			parentword.m_paragraphDialog.show();
			if (parentword.m_paragraphDialog.getOption()==JOptionPane.OK_OPTION)
			{	// sets the selected attibutes to the selected document porsion and the corresponding UI
			  	parentword.setAttributeSet(parentword.m_paragraphDialog.getAttributes(), true);
			  	parentword.showAttributes(parentword.workspace.getCaretPosition());
			}
		}

	}

	/**
	 * sets the current locale to the user interface.
	 */
	public void setLocale()
	{
		wordBundle = parentword.wordBundle;
		cut.setText(wordBundle.getString("Cut"));
		copy.setText(wordBundle.getString("Copy"));
		paste.setText(wordBundle.getString("Paste"));
		//fontFormat.setText(wordBundle.getString("FontFormat"));
		paraFormat.setText(wordBundle.getString("Paragraph"));
	}

}

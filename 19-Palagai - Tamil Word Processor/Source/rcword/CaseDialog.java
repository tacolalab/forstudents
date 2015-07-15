package rcword;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.border.*;

/**
 *	Used to get the user option and change the case of the selected text according
 *  to that.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */

class CaseDialog extends JDialog  implements ActionListener
{
	 /**
	  * A reference to the main <code> Word </code> object
	  */
     Word parentword;

     /**
	  *	Used to set the text as sentence case.
	  */
     JRadioButton jrb_sentcase;

     /**
	  *	Used to set the text as lower case.
	  */
     JRadioButton jrb_lowercase;

     /**
	  *	Used to set the text as upper case.
	  */
     JRadioButton jrb_uppercase;

     /**
	  * Used to set the text as title case.
	  */
     JRadioButton jrb_titlecase;

     /**
	  * Used to toggle the text case
	  */
     JRadioButton jrb_togglecase;

     /**
	  * Used to group the radio buttons.
	  */
     final ButtonGroup bg_group;

     /**
	  * Used to give ok command.
	  */
     JButton jb_ok;

     /**
	  * Used to give cancel command.
	  */
	 JButton jb_cancel;

	 /**
	  * Used to place the case option buttons.
	  */
     JPanel jp_top;

	 /**
	  * Used to place the command buttons.
	  */
	 JPanel jp_end;


	 /**
 	  * Creates an object of <code> CaseDialog </code> with
 	  * a reference to the main <code> Word </code> class.
 	  *
 	  * @param parent a reference to the main <code> Word </code>
	  */
     public CaseDialog(Word parent)
	   {
	   	     super(parent," Change Case ",true);
	   	     parentword = parent;
	   	     getContentPane().setLayout(new BorderLayout());

	   	     jrb_sentcase = new JRadioButton("Sentence Case");
	   	     jrb_lowercase = new JRadioButton("lower case");
	   	     jrb_uppercase = new JRadioButton("UPPER CASE");
	   	     jrb_titlecase = new JRadioButton("Title Case");
	   	     jrb_togglecase = new JRadioButton("toGGLE cASE");

	   	     bg_group = new ButtonGroup();

	   	     bg_group.add(jrb_sentcase);
	   	     bg_group.add(jrb_lowercase);
	   	     bg_group.add(jrb_uppercase);
	   	     bg_group.add(jrb_titlecase);
	   	     bg_group.add(jrb_togglecase);

	   	     jp_top = new JPanel();
	   	     jp_top.setLayout(new GridLayout(5,1));
	   	     jp_top.add(jrb_sentcase);
	   	     jp_top.add(jrb_lowercase);
	   	     jp_top.add(jrb_uppercase);
	   	     jp_top.add(jrb_titlecase);
	   	     jp_top.add(jrb_togglecase);

	   	     getContentPane().add(jp_top,BorderLayout.NORTH);

	   	     jb_ok = new JButton("Ok");
	   	     jb_ok.addActionListener(this);
	   	     jb_cancel = new JButton("Cancel");
	   	     jb_cancel.addActionListener(this);

           jp_end = new JPanel();
           jp_end.add(jb_ok);
           jp_end.add(jb_cancel);

           getContentPane().add(jp_end,BorderLayout.SOUTH);

	   	   setSize(275,185);
           setResizable(false);
		   setLocation(Utils.getMiddle(getSize()));  // sets this dialog box in the middle of the window
		   pack();

		   // listener to listen the escape key and close this dialog
           addKeyListener(new KeyAdapter()
				{
					public void keyPressed(KeyEvent ke)
					{
						if(ke.getKeyCode() == 27)//Esc key
						{
							setVisible(false);
						}
					}
				});

	   }


	   /** Performs the case change operation.
	   * @param	evt an object of <code> ActionEvent </code> which causes the action
	   */
	   public void actionPerformed(ActionEvent evt)
	   {

	   	    String tempstr= parentword.workspace.getSelectedText();

            try
            {
				//if(parentword.workspace.getSelectedText().length()==0)
				//return;

				if(evt.getSource() == jb_cancel )
				{
					setVisible(false);
				}
				else if(evt.getSource() == jb_ok)
				{
					if(jrb_sentcase.isSelected())
					{
						  // For tamil this is not needed.
					}
					else if(jrb_lowercase.isSelected())
					{	// change the text to lower case
						 tempstr=tempstr.toLowerCase();
						 parentword.workspace.replaceSelection(tempstr);
					}
					else if(jrb_uppercase.isSelected())
					{	// change the text to upper case
						 tempstr=tempstr.toUpperCase();
						 parentword.workspace.replaceSelection(tempstr);
					}
					else if(jrb_titlecase.isSelected())
					{
						  // For tamil this is not needed.
					}
					else if(jrb_togglecase.isSelected())
					{
						  // For tamil this is not needed.
					}
				  	setVisible(false);
				}
				parentword.workspace.repaint();
			}
			catch(Exception e)
			{
				System.out.println(e+"---> ex at here': CaseDialog");
				e.printStackTrace();
		    }

	   }// end of actionperformed

	   /**
	   	 * Sets the current locale to the user interface.
	 	 */
	   public void setLocale()
	   {
		   /*
		   JRadioButton jrb_sentcase,jrb_lowercase,jrb_uppercase,
		   jrb_titlecase,jrb_togglecase;

		   ResourceBundle wordbundle = parentword.wordbundle;

		   jrb_sentcase.setText(wordBundle.getString(""));
		   jrb_lowercase.setText(wordBundle.getString("Cut"));
		   jrb_uppercase.setText(wordBundle.getString("Cut"));
		   jrb_titilecase.setText(wordBundle.getString("Cut"));
		   jrb_togglecase.setText(wordBundle.getString("Cut"));
		   */
		}
}







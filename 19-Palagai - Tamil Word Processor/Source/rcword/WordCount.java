package rcword;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.util.ResourceBundle;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.border.*;

/**
 * Used to count and display the word count information.
 *
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
public class WordCount extends JDialog implements ActionListener
  {
	  	  /** a reference to the main <code> Word </code> object */
	  	 Word parentword;
	  	 /** whole text in the workspace.*/
 	     StringTokenizer wholetext;
 	     /** a reference to the main workspace.*/
 	     JTextPane tempworkspace;
 	     /** label to display the heading*/
 	     JLabel heading;
 	     /** label to display the text "word" and the number of words occurred*/
 	     JLabel jlbword;
 	     /** label to display the text "character" and the number of characters occurred */
 	     JLabel jlbchar;
 	     /** label to display the text "line" and the number of line occurred*/
 	     JLabel jlbline;

 	     /** Appropriate font for the current local for entire GUI. */
 	     Font defaultfont ;
 	     /** button to give the close command*/
 	     JButton jbnclose = new JButton();
 	     /** contains the number of word in the document*/
 	     int noofwords;
 	     /** contains the number of characters in the document.*/
 	     int noofchars=0;
 	     /** contains the number of lines in the document.*/
 	     int nooflines=0;

		 /**
	 	 * 	Contains the word bundle for the current local
		 */
 	     ResourceBundle wordbundle;


		/**
		 * Creates an object of <code> WordCount </code> with
		 * a reference to the main <code> Word </code> object
		 *
		 * @ param word a reference to the main <code> Word </code> object
		 */
 	     public WordCount(Word word)
 	     {
 	     	   super(word,"Word Count",true);
 	     	   parentword = word;
 	     	   tempworkspace = parentword.workspace;
 	     	   getContentPane().setLayout(new BorderLayout());

 	     	   defaultfont = parentword.defaultfont;
 	     	   wordbundle = parentword.wordBundle;

 	     	   heading = new JLabel();
 	     	   // sets the heading.
 	     	   heading.setText(wordbundle.getString("Statistics")+" :-----------------");
 	     	   heading.setHorizontalAlignment(SwingConstants.RIGHT);
 	     	   heading.setFont(defaultfont);
 	     	   getContentPane().add(heading,BorderLayout.NORTH);

			   tempworkspace.selectAll();
			   wholetext = new StringTokenizer(tempworkspace.getSelectedText());

 	     	   noofwords = wholetext.countTokens();  // total number of words in the text.
 	     	   while(wholetext.hasMoreTokens())
 	     	   {	/* counts the number of characters in each word and increments the
 	     	   		total number of characters with this */
 	     	        String tempstring = wholetext.nextToken();
 	     	        noofchars = noofchars + tempstring.length();
 	     	   }

 	     		 /*int newline_index=0;  // index of the new line character i the text.
 	     		 String tempstr =tempworkspace.getSelectedText();
 	     		 while(newline_index != -1)
 	     	   {
 	     	   	      newline_index = tempstr.indexOf("\n",newline_index);
 	     	   	      if(newline_index == -1)
 	     	   	      	    newline_index = -1;
 	     	   	      if(newline_index != -1)
 	     	   	      {   // increments the no of line count
 	     	   	      	  nooflines = nooflines + 1;
 	     	   	          newline_index ++;
 	     	   	      }
 	     	   } */
				nooflines = getWrappedLines();

				JPanel leftpanel = new JPanel();
				JPanel rightpanel = new JPanel();
				JPanel bottompanel =new JPanel();

				String str11 = " "+noofwords;

				JPanel jpcenter = new JPanel();
				jpcenter.setLayout(new GridLayout(5,0));

				jlbword = new JLabel();
				jlbword.setText(wordbundle.getString("Words")+" :  "+noofwords+"   " );
				jlbword.setHorizontalAlignment(SwingConstants.RIGHT);
				jlbword.setFont(defaultfont);
				jpcenter.add(new JLabel(" "));
				jpcenter.add(jlbword);


				jlbchar = new JLabel();
				jlbchar.setText(wordbundle.getString("Characters")+" :  "+noofchars+"   ");
				jlbchar.setHorizontalAlignment(SwingConstants.RIGHT);
				jlbchar.setFont(defaultfont);
				jpcenter.add(jlbchar);

				jlbline = new JLabel();
				jlbline.setText(wordbundle.getString("Lines")+" :  "+nooflines+"   ");
				jlbline.setHorizontalAlignment(SwingConstants.RIGHT);
				jlbline.setFont(defaultfont);
				jpcenter.add(jlbline);
				getContentPane().add(jpcenter,BorderLayout.CENTER);

				jbnclose.setText(wordbundle.getString("Cancel"));
				jbnclose.setFont(defaultfont);
				bottompanel.add(jbnclose);

				jbnclose.addActionListener(this);
				getContentPane().add(bottompanel,BorderLayout.SOUTH);

				setSize(250,280);
				setResizable(false);
				setLocation(Utils.getMiddle(getSize()));
				pack();
				// listener to close the dialog when escape key is entered.
				addKeyListener(new KeyAdapter()
						{
							public void keyPressed(KeyEvent ke)
							{
								if(ke.getKeyCode() == 27)//Esc
								{
									setVisible(false);
								}
							}
						});

       }

		/**
		*	Action to close the word count dialog.
		* @param event An object of ActionEvent which causes the action.
		*/
 	     public void actionPerformed(ActionEvent event)
  	   {
  	   	    if(event.getSource() == jbnclose)
  	   	          setVisible(false);
  	   }


  	   public int getWrappedLines()
	   {
	   		int lines = 0;
	   		View view = parentword.workspace.getUI().getRootView(parentword.workspace).getView(0);
	   		int paragraphs = view.getViewCount();
	   		for (int i = 0; i < paragraphs; i++)
	   		{
	   			lines += view.getView(i).getViewCount();
	   		}
	   		return lines;
		}

 }
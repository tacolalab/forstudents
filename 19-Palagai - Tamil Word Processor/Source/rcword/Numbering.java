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
 *	Used to do the numbering operation.
 *	Increments a counter from zero onwards and finds the position where the numbers
 *	has to be displayed and displays it there and listens the position where the numbering
 *	has to be stopped.
 *	<p>    After placing the numbers in the selected portion of the text, while typing
 *   it listens the character entered in the text portion where numbers are activated
 *   to perform the numbering operation. It increments and places the counter after a new
 *	line character and stops the listening when a new line character deleted.</p>

 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class Numbering  implements KeyListener
{
	  /** a reference to the main <code> Word </code> object. */
	  Word parentword;
	/** label to be displayed for numbering.*/
	  JLabel jl_bullet = new JLabel("     n     ");
	  //new ImageIcon("bull2.bmp"));
	  //JLabel jl_bullet = new JLabel(new ImageIcon("bold.gif"));
	/** position where the numbers has to be displayed.*/
	  int bulletplace;
	/** contains the number of moves other than new line character.*/
	  int noofmoves=0;
	/** contains the number of backspaces entered to stop the numbering process.*/
	int noofbackspace=0;
	/** states the state of numbering operation as on or off.*/
	  boolean bulletstate = false;
	/** number to be displayed.*/
	  int numtd= 1;  // no. to display

	/**
	* Constructs an object of <code> Numbering </code> with a reference to the main <code> 	* Word </code> object.
	*@param parent a reference to the main <code> Word </code> object.
	*/
	  public Numbering(Word parent)
	  {   /* the whole process is same as bullets but before display the bullets it
		   increments a count and displays it instead of bullets.
		*/
	  	    super();
	  	    parentword = parent;
	  	    parentword.workspace.addKeyListener(this);
	  	    parentword.workspace.requestFocus();

	  	    jl_bullet.setForeground(Color.black);

	  	    String selectedtext = parentword.workspace.getSelectedText();
	  	    String selectedtextcopy = selectedtext;

	  	    // bulletplace = selectedtext.lastIndexOf("\n");
	  	    int selectionstart = parentword.workspace.getSelectionStart();
	  	   try
	  	   {

					String previoustext = parentword.workspace.getText(0,selectionstart-1);
					int temppt = previoustext.lastIndexOf("\n");
					if (temppt != -1)    numtd++;
					while( (bulletplace = selectedtextcopy.lastIndexOf("\n") ) != -1)
					{
					   selectedtextcopy = selectedtextcopy.substring(0,bulletplace);
					   numtd++;
					}

					//numtd ;
					int numtdcopy = numtd-1;
					//System.out.println("   "+numtd);

					while( (bulletplace = selectedtext.lastIndexOf("\n")) != -1)
					{
						parentword.workspace.setSelectionStart(bulletplace+1+selectionstart);
						parentword.workspace.setSelectionEnd(bulletplace+1+selectionstart);
						JLabel jl_temp = new JLabel("     "+String.valueOf(numtdcopy--)+".    ");
                        jl_temp.setForeground(Color.black);
						parentword.workspace.insertComponent(jl_temp);//new JLabel("     "+String.valueOf(numtdcopy--)+".    ")); //jl_bullet);
								////System.out.println("       : "+bulletplace);
					    selectedtext = selectedtext.substring(0,bulletplace);
					}

					if( temppt != -1)
					{
						 //System.out.println("before");
						 parentword.workspace.setSelectionStart(temppt+1);
						 parentword.workspace.setSelectionEnd(temppt+1);
						 JLabel jl_temp = new JLabel("     "+String.valueOf(numtdcopy--)+".    ");
                         jl_temp.setForeground(Color.black);
						 parentword.workspace.insertComponent(jl_temp);//new JLabel("     "+String.valueOf(numtdcopy)+".    "));
					}

	  	  }
	  	  catch(Exception e)
	  	 {
	  		   System.out.println(e+"---->  exxxx : numbering");
	  	 }


	  	try
	  	{
	  	  parentword.repaint();
	  	}
	  	catch(Exception e)
	  	{
	  		System.out.println(e+"----->  : numbering");
	  	}

	  } //end

	/**
	* 	Listens the character entered in the text portion where numbers are activated
	* 	to perform the numbering operation. Increments and places the counter after a new
	*  	line character. Stops the listening when a newline character deleted.
	*/
	  public void keyPressed(KeyEvent key)
	  {
	  		   int whichkey = key.getKeyCode();
	  		   ////System.out.println("  "+whichkey);

	  	     if(whichkey == KeyEvent.VK_ENTER)
	  	     {
	  	     	   //System.out.println("enter");
	  	     	   bulletstate = true;
	  	     	   noofmoves =2;
	  	     	   parentword.workspace.replaceSelection("\n");
	  	     	   JLabel jl_temp = new JLabel("     "+String.valueOf(numtd++)+".    ");
                   jl_temp.setForeground(Color.black);
	  	   	       parentword.workspace.insertComponent(jl_temp);//new JLabel("     "+String.valueOf(numtd++)+".   "));

	  	   	     //noofmoves ++;
	  	   	     ////System.out.println("enter "+noofmoves);
	  	     }

	  	     else if(whichkey == KeyEvent.VK_BACK_SPACE)
	  	     {
	  	     	   if(bulletstate == true)
	  	     	        noofmoves --;
	  	     	   //System.out.println(" back space :"+noofmoves);
	  	     }
	  	     else if(whichkey>= 44 && whichkey <=111)
	  	     {
	  	     	    if(bulletstate== true)
	  	     	    	 noofmoves++;
	  	     }

	  	     if(noofmoves == 0 && bulletstate )
	  	     {
	  	     	   bulletstate = false;

	  	         parentword.workspace.removeKeyListener(this);
	  	     }

	  		   /*for(int i=120; i<150;i++)
	  	     {
	  	     	  //System.out.println("  "+i+"   "+key.getKeyText(i));
	  	     }*/

	  	     parentword.workspace.repaint();

	  }

	 /**
	 * Just to implement the key listener.
	 */
	  public void keyReleased(KeyEvent key)
	  {

	  }

	/**
	 * Just to implement the key listener.
	 */
	 public void keyTyped(KeyEvent key)
	  {

	  }


}


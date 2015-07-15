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
 *   Displays the bullets and listens the position where the bullet has to be stopped.
 *   <p>    First it finds the '\n' (enter or new line) in backwards direction and
 *   places the bullet as a label in the selected text. If nothing has been selected,
 *   then the whole text will be selected. </p>
 *   <p>    After placing the bullets in the selected portion of the text, while typing
 *   it listens the character entered in the text portion where bullets are activated
 *   to perform the bullets operation. It places a bullet after a new line character
 *   and stops the listening when a new line character deleted.</p>
 *
 * @version 5.12.2002
 * @author RCILTS-Tamil,MIT
 */
class Bullets  implements KeyListener
{
	/**
	* a reference to the main <code> Word </code> object
	*/
	Word parentword;

	/**
	 * contains the string/image to display the bullet.
	 */
	JLabel bullet = new JLabel("     ¤     ");//new ImageIcon("bull2.bmp"));
	//JLabel bullet = new JLabel(new ImageIcon("bold.gif"));

	/**
	 * place where the bullet has to be displayed
	 */
	int bulletplace;

	/**
	 * used to find the bullet's stop position.
	 */
	int noofmoves=0;

	/**
	 * defines the state of the bullet as true or false.
	 */
	boolean bulletstate = false;


	/**
	 * Defines a <code> Bullets </code> object with a reference to <code> Word </code>
	 * @param parent a reference to the main <code> Word </code>
	 */
	public Bullets(Word parent)
	{
	  super();
	  parentword = parent;
	}

 	/**
	 * Displays the bullets and listens the
	 * position where the bullet has to be stopped
	 */
	public void activateBullets( )
	{
		parentword.workspace.addKeyListener(this);
		parentword.workspace.requestFocus();
		bullet.setForeground(Color.black);

		String selectedtext = parentword.workspace.getSelectedText();
		int selectionstart = parentword.workspace.getSelectionStart();
		try
		{
			/* 	This while loop finds the '\n' ( enter or newline ) in backwards direction and
			   	places the bullet as a label in the selected text.
			*/
			while( (bulletplace = selectedtext.lastIndexOf("\n")) != -1)
			{
				parentword.workspace.setSelectionStart(bulletplace+1+selectionstart);
				parentword.workspace.setSelectionEnd(bulletplace+1+selectionstart);
				parentword.workspace.insertComponent(new JLabel("     ¤     "));//bullet);
				selectedtext = selectedtext.substring(0,bulletplace);
			}
			if(selectionstart != 0 )
			{    // this is to place the bullet in the first line of the selection
				 String previoustext = parentword.workspace.getText(0,selectionstart-1);
				 int temppt = previoustext.lastIndexOf("\n");
				 if( temppt != -1)
				  {
					 parentword.workspace.setSelectionStart(temppt+1);
					 parentword.workspace.setSelectionEnd(temppt+1);
					 parentword.workspace.replaceSelection("\n");
					 parentword.workspace.insertComponent(bullet);
				  }
			}
			parentword.repaint();
		}
		catch(Exception e)
		{
			System.out.println(e+ "---->   e at activate bullets : bullet");
			// e.printStackTrace();
		}
	} //end of constructor

	/**
	 * Stops the bullets and stops further listening
	 * to stop bullet without starting.
	 */
	public void inactivateBullets()
	{
		  parentword.workspace.removeKeyListener(this);
	}

	/**
	* 	Listens the character entered in the text portion where bullets are activated
	* 	to perform the bullets operation. Places a bullet after a new line character.
	*   stops the listening when a newline character deleted.
	*/
	public void keyPressed(KeyEvent key)
	{
		   // stores the integer value of the character entered.
		   int whichkey = key.getKeyCode();
		   JLabel tempbullet = new JLabel("     b     ");
		   //tempbullet.setForeground(Color.black);
		   try
		   {
				 if(whichkey == KeyEvent.VK_ENTER)
				 {
					   bulletstate = true;
					   noofmoves =2;
					   parentword.workspace.replaceSelection("\n");
					   parentword.workspace.insertComponent(new JLabel("     b     "));
				 }
				 else if(whichkey == KeyEvent.VK_BACK_SPACE)
				 {
					   if(bulletstate == true)
							noofmoves --;
				 }
				 else if( (whichkey>= 44)  && (whichkey <=111) && (whichkey != 58) )
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
				 {  }*/
				 parentword.workspace.repaint();
	   }
	   catch(Exception ee)
	   {
			System.out.println(ee+"---->   excep  at keyevent : bullet");
		}

	}

	// Just to implement the key listener
	public void keyReleased(KeyEvent key)
	{ }

	// Just to implement the key listener
	public void keyTyped(KeyEvent key)
	{ }
}


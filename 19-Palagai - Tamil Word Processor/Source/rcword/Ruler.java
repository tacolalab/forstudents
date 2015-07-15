package rcword;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Used to show the rulers.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
public class Ruler extends javax.swing.JPanel
{


  /** a reference to the main <code> Word </code> object */
  transient Word parentword;

  //HorizontalRuler horizRuler;  // the horizontal ruler
	/** vertical ruler for this application*/
  VerticalRuler   vertRuler;   // the vertical ruler

	/**
	* Constructs an object of <code> Ruler </code> with
	* a reference to the main <code> Word </code> object
	* @param word a reference to the main <code> Word </code> object
	*/
  public Ruler(Word word)
  {

    this.parentword = word;

    //horizRuler = new HorizontalRuler(myApp);
    vertRuler  = new VerticalRuler(word);
	}

	/**
	* Used to add the ruler in the application.
	*/
  	public void addToContentPane()
  	{
				//myApp.getContentPane().add(horizRuler);
    		parentword.getContentPane().add(vertRuler);
    }

	/**
	* Used to set the position of this ruler.
	* @param x position in the x-axis
	* @param y position in the y-axis
	*/
 	public void setXY(int x, int y)
 	{
	//  horizRuler.setX(x);
	  vertRuler.setY(y);
	}

	/**
	* Used to set the ruler in visible or invisible mode.
	*/
   public void setRulerVisible(boolean visible)
  {

	//  horizRuler.setVisible(visible);
	    vertRuler.setVisible(visible);
	}


}
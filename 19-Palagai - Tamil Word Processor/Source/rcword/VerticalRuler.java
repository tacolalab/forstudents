package rcword;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Used to construct and display the vertical ruler.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
public class VerticalRuler extends javax.swing.JPanel
{
	/** width of the vertical ruler*/
	static final int RULER_WIDTH = 18;
	/** height of the vertical ruler*/
	final int  WORD_HEIGHT = 415;
	/** x-coordinate value of the left top edge of vertical ruler.*/
	int x1 = 0;
	/** y-coordinate value of the left top edge of vertical ruler.*/
	int y1 = 80;
	/** x-coordinate value of the right bottom edge of vertical ruler.*/
	int x2 = 18;
	/** y-coordinate value of the right bottom edge of vertical ruler.*/
	int y2 = 415;

	/** a reference to the main <code> Word </code> object */
	transient Word parentword;

	/** y axis position to mark the marker in vertical ruler */
	int currentY = 30;  // marker y position

	/**
	* Constructs an object of <code> VerticalRuler </code> with
	* a reference to the main <code> Word </code> object
	*
	* @ param word a reference to the main <code> Word </code> object
	*/
	public VerticalRuler(Word word)
	{
		this.parentword = word;
		setLayout(null);
		setBounds(x1,y1,x2,y2)  ;
		setBackground(Color.white);
	}


	/**
	* Draws the ruler - notches, numbers & marker.
	*
	* @param g  the Graphics context in which the painting occurs
	*/
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		//if (!myApp.rulerVisible)
		// return;
		Graphics2D g2 = (Graphics2D)g;
		// draw 3D-ruler effect
		g2.setColor(Color.darkGray);
		g2.drawLine(x1, y1, x1,y2);
		g2.drawLine(x1,y1, x2,y1);
		g2.setColor(Color.lightGray);
		g2.drawLine(x2, y1, x2,y2);
		g2.drawLine(x1,y2,x2,y2);

		// draw notches & numbers
		g2.setColor(Color.black);
		Font currentFont = g2.getFont();
		g2.setFont(new Font(
		currentFont.getName(), currentFont.getStyle(), 10));
		int jump = 20; // vertical gap to leave between to line markers.
		Integer curY;
		for (int i=0; i<WORD_HEIGHT; i+=jump)
		{   // draw the line markers in the ruler whose length is less than
			// the rulers width.
			g2.drawLine(RULER_WIDTH-13, i, RULER_WIDTH-4, i);
			if (((i % (jump*2)) != 0) && (i < 340))
			{   // shows the number in the ruler
				curY = new Integer(i/10);
				g2.drawString(curY.toString(), RULER_WIDTH-15, i+15);
			}
		}

		// draw marker
		g2.setXORMode(Color.black);
		g2.setColor(Color.white);
		g2.drawLine(1, currentY, RULER_WIDTH-1, currentY);

	}


	/**
	* Sets the current y-axis value to draw the line in ruler.
	*
	* @ param y-axis value to be fixed
	*/
	public void setY(int y)
	{
	currentY = y;
	repaint();
	}
}

/**
* @(#)ChartPainter.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
/**Abstract class which is extended from ComponenUI for
drawing the pie chart. It gets the selected row and column value
and used for drawing the chart .It sets the text font, color and
labels for the chart.*/
public abstract class ChartPainter extends ComponentUI
{
	/**Sets the text font for pie chart*/
	protected Font textFont=new Font("Serif",0,12);
	/**Sets the color for the pie chart*/
	protected	Color textColor=Color.black;
	/**Defines the color used for pie chart
	it has array of colors for pie chart.*/
	protected Color colors[]=new Color[] {
		Color.red,Color.blue,Color.yellow,Color.black,Color.green
		,Color.white,Color.gray,Color.cyan,Color.magenta,Color.darkGray
		};
	/**Stores the value for which the chart to be drawn.*/
	protected double values[]=new double[0];
	/**Stores the value used for labels in the pie chart*/
	protected String labels[]=new String[0];
	/**Stores the titles used for the pie chart */
	protected String titles[]=new String[0];
	/**Stores the x axis value used to set the range of the chart*/
	protected String xvalues[]=new String[0];
	/**Sets the textfont for the pie chart
	@param f the Font object */
	public void setTextFont(Font f)
	{
		textFont=f;
	}
	/**Gets the textfont for the pie chart
	@return font the retured font type */
	public Font getTextFont()
	{
		return textFont;
	}
	/**Sets the color for the pie chart
	@param clist array of colors*/
	public void setColor(Color[] clist)
	{
		colors=clist;
	}
	/**Gets the color for the pie chart
	@return color[] array of colors.*/
	public Color[] getColor()
	{
		return colors;
	}
	/**Sets the color for the given index
	@param index of type integer
	@param c of type color.*/
	public void setColor(int index,Color c)
	{
		colors[index]=c;
	}
	/**Gets the color for the given index
	@param index of type integer.*/
	public Color getColor(int index)
	{
		return colors[index];
	}
	/**Sets the text color for the piechart
	@param c of type color*/
	public void setTextColor(Color c)
	{
		textColor=c;
	}
	/**Gets the text color for the piechart*/
	public Color getTextColor()
	{
		return textColor;
	}
	/**Sets the label for the pie chart
	@param l set of lables in string array*/
	public void setLabels(String[] l)
	{
		labels=l;
	}
	/**Sets the value for the pie chart.
	@param v of type double array.*/
	public void setValues(double[] v)
	{
		values=v;
	}
	/**Sets the title forthe pie chart
	@param t of type String array.*/
	public void setTitles(String[] t)
	{
		titles=t;
	}
	/**Set the x axis value for the pie chart
	@param x of type string array.*/
	public void setXvalues(String[] x)
	{
		xvalues=x;
	}

	/**Finds the index position of the mouse entry.*/
	public abstract int indexOfEntryAt(MouseEvent me);
	/**Paint method to paint the components
	@param g the Graphics object
	@param c the JComponent object*/
	public abstract void paint(Graphics g,JComponent c);


}
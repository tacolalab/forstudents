/**
* @(#)PieChartPainter.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;

/**class used for pie chart painting from the selected
row coloum values. it extends the user defined class
chart painter. It does the mathematical calculation for
drawing the pie chart.*/
public class PieChartPainter extends ChartPainter
{
	/**Object of the user defined class*/
	protected static PieChartPainter chartUI=new PieChartPainter();
	/**stores the x coordinate for drawing the pie chart.*/
	protected int originX;
	/**stores the x coordinate for drawing the pie chart.*/
	protected int originY;
	/**stores the radius of circle for drawing the pie chart.*/
	protected int radius;

	/**Calculates the pi by 2 value for pie chart.*/
	private static double piby2=Math.PI/2.0;
	/**Calculates the 2 pi value for pie chart.*/
	private static double twopi=Math.PI*2.0;
	/**Calculates the diameter value for pie chart.*/
	private static double d2r=Math.PI/180.0;

	/**Method to find out the coordinates where the mouse is
	moved in the worksheet.
	@param me the MouseEvent.
	@return int of type integer to get the coordinates.*/
	public int indexOfEntryAt(MouseEvent me)
	{
		int x=me.getX()-originX;
		int y=originY-me.getY();

		if(Math.sqrt(x*x+y*y)>radius) { return -1; }

		double percent=Math.atan2(Math.abs(y),Math.abs(x));
		if(x>=0)
		{
			if(y<=0)
			{
				percent=(piby2-percent)+3*piby2;

			}
		}
		else
		{
			if(y>=0)
			{
				percent=Math.PI-percent;
			}
			else
			{
				percent=Math.PI+percent;
			}
		}
		percent/=twopi;
		double t=0.0;
		if(values!=null)
		{
			for(int i=0;i<values.length;i++)
			{
				if(t+values[i]>percent)
				{
					return i;
				}
				t+=values[i];
			}

		}
		return -1;
	}

	/**Method to paint the chart from the value.
	@param g the graphics object
	@param c the JComponent object.*/
	public void paint(Graphics g,JComponent c)
	{
		Dimension size=c.getSize();
		originX=size.width/2;
		originY=size.height/2;
		g.setColor(Color.white);
		g.fillRect(0,0,size.width,size.height);
		g.setColor(Color.black);

		if(titles[0].length()!=0)//chart title
		{
			int startX=size.width/2;
			startX-=((int)(titles[0].length()/2))*15;//14-font size
			if(startX<100)
				startX=100;

			Font f=g.getFont();
			g.setFont(new Font("TABKural",Font.BOLD,12));
			g.drawString(titles[0],startX,15);
			g.setFont(f);
		}


		int diameter=(originX<originY?size.width-100
					:size.height-100);
		radius=(diameter/2)+1;
		int cornerX=(originX-(diameter/2));
		int cornerY=(originY-(diameter/2));

		int startAngle=0;
		int arcAngle=0;
		int j=0;

		for(int i=0;i<values.length;i++)
		{
			if(i%2==0)
				j++;
			arcAngle=(int)(i<values.length-1?
					Math.round(values[i]*360):
					360-startAngle);
			g.setColor(colors[i%colors.length]);
			g.fillArc(cornerX,cornerY,diameter,diameter,
					startAngle,arcAngle);
			//g.fillRect(15,(i+1)*40,20,20);
			g.fillRect((i%2==0?15:size.width-90),j*40,20,20);
			drawLabel(g,labels[i],startAngle+(arcAngle/2));
			startAngle+=arcAngle;
			g.setColor(Color.black);
			g.drawRect((i%2==0?15:size.width-90),j*40,20,20);
			Font f=g.getFont();
			g.setFont(new Font("TABKural",Font.BOLD,12));
			if(i+1<xvalues.length && xvalues[0].equals("YESVAL"))
				g.drawString(xvalues[i+1],(i%2==0?50:size.width-65),j*40+13);
			else
				g.drawString(String.valueOf(i+1),(i%2==0?50:size.width-65),j*40+13);
			g.setFont(f);
			g.drawOval(cornerX,cornerY,diameter,diameter);
		}
	}

	/**Method to display the label for the chart details.
	@param g object of the Graphics class
	@param text object of the String to set the chart heading.
	@param angle for pie sector values of type double.*/
	public void drawLabel(Graphics g,String text,double angle)
	{
		g.setFont(textFont);
		g.setColor(textColor);
		double radians=angle*d2r;
		int x=(int)((radius+5)*Math.cos(radians));
		int y=(int)((radius+5)*Math.sin(radians));
		if(x<0)
		{
			x-=SwingUtilities.computeStringWidth(
					g.getFontMetrics(),text);
		}
		if(y<0)
		{
			y-=g.getFontMetrics().getHeight();
		}
		g.drawString(text,x+originX,originY-y);
	}

	/**Method to draw the component from the values
	for pie chart.
	@param c the object of JComponent
	@return ComponentUI .*/
	public static ComponentUI createUI(JComponent c)
	{
		return chartUI;
	}


}


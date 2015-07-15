/**
* @(#)PieTableChart.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

//This class uses ChartPainter and PieChartPainter
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
/**Class to get the details from the selected value and from the
table model of the worksheet. It implements the TableModelListener
and extends JComponent. */
public class PieTableChart extends JComponent implements TableModelListener
{
	/**Object of the class TableModel*/
	protected TableModel model;
	/**Object of the class ChartPainter*/
	protected ChartPainter cp;
	/**Stores the percentage value for the pie chart from the calculation.*/
	protected double[] percentages;
	/**Stores the lables for the chart as headings.*/
	protected String[] labels;
	/**Stores the tooltip text as array object.*/
	protected String[] tips;
	/**Sets the number format for calculations.*/
	protected java.text.NumberFormat formatter=
		java.text.NumberFormat.getPercentInstance();
	/**Sets the chart titles as string array object*/
	protected String[] titles;
	/**Sets the x axis value in the string array object.*/
	protected String[] xvalues;

	/**Constructor to invoke the chart details for drawing the
	chart.
	@param tm object of TableModel
	@param title to store the chart titles in the string array.
	@param xval of type string array object.*/
	public PieTableChart(TableModel tm,String [] title,String[] xval)
	{
		titles=title;
		xvalues=xval;
		//System.out.println("Class - PieTablechart");
		setUI(cp=new PieChartPainter());
		setModel(tm);
	}

	/**Sets the text font used in the pie chart
	@Param f of type Font.*/
	public void setTextFont(Font f)
	{
		cp.setTextFont(f);
	}
	/**Gets the font from the text.
	@return Font.*/
	public Font getTextFont()
	{
		return cp.getTextFont();
	}
	/**Sets the text color
	@param c object of Color.*/
	public void setTextColor(Color c)
	{
		cp.setTextColor(c);
	}
	/**Gets the text color from the chart.
	@return Color*/
	public Color getTextColor()
	{
		return cp.getTextColor();
	}
	/**Sets the color from the color list.
	@param clist color list of type color array.*/
	public void setColor(Color[] clist)
	{
		cp.setColor(clist);
	}
	/**Gets the color list
	@return color array object.*/
	public Color[] getColor()
	{
		return cp.getColor();
	}
	/**Sets the color for the given index and the color.
	@param index of type integer to set the position
	@Param c of type Color*/
	public void setColor(int index,Color c)
	{
		cp.setColor(index,c);
	}
	/**Gets the color for the given index.
	@param index of type int the position for setting the color.
	@return Color */
	public Color getColor(int index)
	{
		return cp.getColor(index);
	}

	/**Gets the tooltip text for the chart window.
	@param me the mouse event
	@return String the returned tool tip text.*/
	public String getToolTipText(MouseEvent me)
	{
		if(tips!=null)
		{
			int whichTip=cp.indexOfEntryAt(me);
			if(whichTip!=-1)
			{
				return tips[whichTip];
			}
		}
		return null;
	}

	/**Fires when the table gets changed.
	@param tme object of TableModelEvent.*/
	public void tableChanged(TableModelEvent tme)
	{
		updateLocalValues(tme.getType()!=TableModelEvent.UPDATE);
	}

	/**Sets the model for the table as worksheet.
	@param tm of type TableModel.*/
	public void setModel(TableModel tm)
	{
		if(tm!=model)
		{
			if(model!=null)
			{
				model.removeTableModelListener(this);
			}
			model=tm;
			model.addTableModelListener(this);
			updateLocalValues(true);

		}
	}
	/**Gets the model from the table for drawing pie chart.
	@return TableModel.*/
	public TableModel getModel()
	{
		return model;
	}

	/**Method to get the calculated percentages from the
	chart values to draw the pie chart.*/
	protected void calculatePercentages()
	{
		double runningTotal=0.0;
		if(model.getRowCount()>model.getColumnCount())
		{
			for(int i=model.getRowCount()-1;i>=0;i--)
			{
				percentages[i]=0.0;
				for(int j=model.getColumnCount()-1;j>=0;j--)
				{
					try
					{
						percentages[i]+=((Number)model.getValueAt(i,j)).doubleValue();
					}
					catch(ClassCastException cce)
					{
						try
						{
							percentages[i]+=Double.valueOf(model.getValueAt(i,j).toString()).doubleValue();
						}
						catch(Exception e)
						{}
					}

				}
				runningTotal+=percentages[i];
			}
			for(int i=model.getRowCount()-1;i>=0;i--)
			{
				percentages[i]/=runningTotal;
			}
		}
		else
		{
			for(int i=model.getColumnCount()-1;i>=0;i--)
			{
				percentages[i]=0.0;
				for(int j=model.getRowCount()-1;j>=0;j--)
				{
					try
					{
						percentages[i]+=((Number)model.getValueAt(j,i)).doubleValue();
					}
					catch(ClassCastException cce)
					{
						try
						{
							percentages[i]+=Double.valueOf(model.getValueAt(j,i).toString()).doubleValue();
						}
						catch(Exception e)
						{}
					}

				}
				runningTotal+=percentages[i];
			}
			for(int i=model.getColumnCount()-1;i>=0;i--)
			{
				percentages[i]/=runningTotal;
				//System.out.println(percentages[i]);
			}
		}
	}
	/**Sets the labels for the pie chart and the tool tip text
	for the components in the chart window.*/
	protected void createLabelsAndTips()
	{
		int i;
		if(model.getRowCount()>model.getColumnCount())
		{
			for(i=model.getRowCount()-1;i>=0;i--)
			{
				labels[i]=(String)model.getValueAt(i,0);
				tips[i]=formatter.format(percentages[i]);
			}
		}
		else
		{
			for(i=model.getColumnCount()-1;i>=0;i--)
			{
				labels[i]=(String)model.getValueAt(0,i);
				tips[i]=formatter.format(percentages[i]);
			}
		}
	}
	/**Updates the local values for drawing the chart.
	@param freshStrat of type boolean to check the chart*/
	protected void updateLocalValues(boolean freshStart)
	{
		if(freshStart)
		{
			int count;
			if(model.getRowCount()>model.getColumnCount())
				count=model.getRowCount();
			else
				count=model.getColumnCount();
			if((tips==null)||(count!=tips.length))
			{
				percentages=new double[count];
				labels=new String[count];
				tips=new String[count];
			}
		}
		calculatePercentages();
		createLabelsAndTips();

		cp.setValues(percentages);
		cp.setLabels(labels);
		cp.setTitles(titles);
		cp.setXvalues(xvalues);

		repaint();
	}



}




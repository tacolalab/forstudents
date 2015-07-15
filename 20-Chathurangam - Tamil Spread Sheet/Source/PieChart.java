/**
* @(#)PieChart.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

//This class uses the PieTableChartPopup class
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import java.util.*;

/**To design the pie chart from the selected value from
the worksheet. it extends JFrame class.*/
public class PieChart extends JFrame
{

	/**Stores the data from the table.(selected row/col values)*/
	String [][] data;
	/**Sets the row index*/
	int row;
	/**Sets the col index.*/
	int col;
	/**Stores the headers for table columns*/
	String[] headers;

	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam main;
	ResourceBundle wordBundle;

	/**constructor for designing pie chart from the
	selected row and column values.
	@param d the values from table
	@param r the row index
	@param c the col index
	@param w the worksheet object
	@param title the sheet titles stored in the array.
	@param xval the x axix value for drawing the chart
	@parma main the main class object.*/
	public PieChart(String[][] d,int r,int c,WorkSheet w,String [] title,String [] xval,Chathurangam main)
	{
		this.main=main;
		this.data=d;
		this.row=r;
		this.col=c;
		if(row>col)
		{
			headers=new String[col];
			for(int i=0;i<col;i++)
			{
				headers[i]=data[i][0];
			}
		}
		else
		{
			headers=new String[row];
			for(int i=0;i<row;i++)
			{
				headers[i]=data[0][i];
				//System.out.println("headers value "+headers[i]);
			}
		}

/**
* the abstract class used to set the model for the table.
* it is extended from the class AbstractTableModel.
* from the table model class there are three more classes are derived.
* the first one is editingtablemodel.second is scrollingtablemodel and
* final one is cachingtablemodel. All these classes used the getValueAt()
* and getRowCount() methods.the resultset is the parameter for all three
* methods.the first method is used to make the table editable after data
* is retrieved from the database. the second model is used to make the table
* scrollable if it has more records.the third model is used for caching the data
* from the table.that is table is designed after getting the resultset from the database.
* but in scrolling model without designing the table the data is retrieved.
*
*/
TableModel tm=new AbstractTableModel()
		{
			public int getColumnCount()
			{ return col; }
			public int getRowCount()
			{ return row; }
			public String getColumnName(int c)
			{ return headers[c]; }
			public Class getColumnClass(int c)
			{
				return (c==0)?String.class:Number.class;
			}

			public boolean isCellEditable(int r,int c)
			{ return true; }
			public Object getValueAt(int r, int c)
			{
				if(data[r][c].toString().equals(""))
					return data[r][c];
				try
				{
					double d=Double.parseDouble(data[r][c].toString());
					if(d<0)
						return (new Double(-1*d)).toString();
					else
						return data[r][c];
				}
				catch(Exception e)
				{
					//System.out.println("Not a valid number");
				}
				return (new String(""));
			}
			public void setValueAt(Object value,int r,int c)
			{
				//System.out.println("xyz "+value);
				data[r][c]=(String)value;
				fireTableRowsUpdated(r,r);
			}
		};
		final PieTableChartPopup tcp=new PieTableChartPopup(tm,w,title,xval,main);
		tcp.setVisible(true);

	}
}

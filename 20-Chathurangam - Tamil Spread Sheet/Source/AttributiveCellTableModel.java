/**
* @(#)AttributiveCellTableModel.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

/**
Sets the table model for the current worksheet for font format.
It extends DefaultTableModel. There are 7 constructors used
depending upon the parameters.The no. of rows and no. of
columns should be calculted for the table before setting this
model to the table. Tamil Heading also set for the table column
when initiating the worksheet.
 */
 public class AttributiveCellTableModel extends DefaultTableModel
{

    /**Object of cellattribute interface to add column and insert row
    for the worksheet.*/
    public CellAttribute cellAtt;

 	/**Stores the table cell values as twoD array object for initiating
 	the cells.*/
 	Object[][] data;

	/**To set the total no. of rows in the table cell as the worksheet.*/
	int total_Rows=50;

	/**To store the maximum rows of the table.*/
	int maxRow;

	/**To store the maximum columns of the table.*/
	int maxCol;

	/**To store the column headings in Tamil for the table as sheet.*/
	public String[] headers={"","è(0)","é(1)","ê(2)","ë(3)","ì(4)","í(5)",
							"î(6)","ï(7)","ð(8)","ñ(9)","ò(10)","ó(11)",
							"ô(12)","õ(13)","ö(14)","÷(15)","ø(16)","ù(17)"};

/**To display the current cell position in the input field.*/
public String[] headerOnly={"","è","é","ê","ë","ì","í",
							"î","ï","ð","ñ","ò","ó",
							"ô","õ","ö","÷","ø","ù"};

/**The key words used for expressions stored in the string array.*/
String[] fnKeyWords={"Ãì¢´",
					"âí¢í¤è¢¬è",
					"Üî¤èð¢ðì¢êñ¢",
				"°¬øï¢îð¢ðì¢êñ¢" };


	/**Constructor without any parameter.*/
  public AttributiveCellTableModel()
  {
    this((Vector)null, 0);
  }
  /**Constructor with number of rows and number of columns
  are the parameter for setting the table model.
  @param numRows of type integer to store the number of Rows.
  @param numColumns of type integer to store the number of Columns.
  */
  public AttributiveCellTableModel(int numRows, int numColumns)
  {
    Vector names = new Vector(numColumns);
    names.setSize(numColumns);
    setColumnIdentifiers(names);
    dataVector = new Vector();
    setNumRows(numRows);
  }
  /**Constructor with column headings as vector and number of rows for
  setting the table model.
  @param columnNames of type vector
  @param numRowsof type integer to store the number of Rows.
  */
  public AttributiveCellTableModel(Vector columnNames, int numRows)
  {
    setColumnIdentifiers(columnNames);
    dataVector = new Vector();
    setNumRows(numRows);
  }

  /**Constructor with columnnames as object array and number of
  Rows are the parameters to set the table model.
  @param columnNames of type object array to store the column headings.
  @param numRows of type int to store the no. of  Rows.
  */
  public AttributiveCellTableModel(Object[] columnNames, int numRows)
  {
    this(convertToVector(columnNames), numRows);
  }
  /**Constructor with data as vector and columnNames as vector
  to set the table model
  @param data of type vector to store the table data.
  @param columnNames of type vector to store the column headings.*/
  public AttributiveCellTableModel(Vector data, Vector columnNames)
  {
    setDataVector(data, columnNames);
  }
  /**Construtor with data as object array and columnheadings as
  object array.
  @param data to store the data of table in 2D object array.
  @param columnNames to store the col heading in oneD object array.*/
  public AttributiveCellTableModel(Object[][] data, Object[] columnNames)
  {
    setDataVector(data, columnNames);
  }

	/**Constructor with headers in Tamil in string array, data in 2D
	array object, total no. of rows and the main class object as parameters.
	@param headers of type string array to store the Tamil headings.
	@param data of type object 2D array to store the table data.
	@param main object of the main class Chathurangam.
	@param total_Rows of type integer to store the no. of rows.*/
	public AttributiveCellTableModel(String[] headers,Object[][] data,Chathurangam main,int total_Rows)
	{
		this.headers=headers;
		this.data=data;
		this.total_Rows=total_Rows;
		Vector names = new Vector(headers.length);
		names.setSize(headers.length);
		setColumnIdentifiers(names);
		dataVector = new Vector();
		setNumRows(total_Rows);
    	cellAtt = new DefaultCellAttribute(main,total_Rows,headers.length);
	}

	/**Sets the max value of the dimension of the table to display.
	To find maximum row and maximum column.
	@param d the Dimension object to find width and height.*/
	public void setMax(Dimension d)
	{
		maxRow=d.width;
		maxCol=d.height;
	}
	/**To get the maximum size of the sheet to find width and height.
	@return Dimension to store the width and height in Dimension object.
	*/
	public Dimension getMax()
	{
		return new Dimension(maxRow,maxCol);
	}
	/**To calcute the maximum value for the given row and column
	@param row of type int to store the row index
	@param col of type int to store the column index.
	*/
	public void calMax(int row,int col)
	{
		if(row>maxRow)
			maxRow=row;
		if(col>maxCol)
			maxCol=col;
		if(data[row][col].toString().equals(""))
		{
			int i,j=0;
			loops:	for(i=total_Rows-1;i>=0;i--)
					{
						for(j=headers.length-1;j>=1;j--)
						{
							if(data[i][j].toString().equals(""))
								continue;
							break loops;
						}
					}
			maxRow=i;
			maxCol=j;
		}
	}

	/**Gets the column count from the table or sheet
	@return int to store the no. of columns.*/
	public int getColumnCount()
	{
		return headers.length;
	}

	/**Gets the row count from the table or sheet
	@return int to store the no. of rows.*/
	public int getRowCount()
	{
		return total_Rows;
	}
	/**Gets the column name for the given column index.
	@param col of type int to which the column name to be found out.
	@return String the column name value in string object.*/
	public String getColumnName(int col)
	{
		return headers[col];
	}
	/**Gets the value from the sheet for a given row and column.
	@param row of type int to get the row index
	@param col of  type int to get the col index.
	@return Object the value retained from the table cell.
	*/
	public Object getValueAt(int row,int col)
	{
		if(row == -1 || col == -1)
			return null;
		if(headers[col].equals(""))
		{
			return headers[col]+row;
		}
		else
			return data[row][col];

	}
	/**Sets the value for the sheet for a given row and column.
		@param value the value to be set in the cell of type Object.
		@param row of type int to get the row index
		@param col of  type int to get the col index.

	*/
	public void setValueAt(Object value,int row,int col)
	{
		data[row][col]=value;
		calMax(row,col);
		getMax();

	}
	/**To check the the particular cell is editable.
	@param row of type int the row index
	@param col of type int the col index.
	@return boolean it returns true if it is editable.*/
	public boolean isCellEditable(int row,int col)
	{
		return true;
	}

	/**The method implemented for ListSelectionListener to
	find out column moved in the table.
	@param lse the ListeSelectionEvent*/
	public void columnMoved(ListSelectionEvent lse)
	{

	}
	/**To fire the table cell is getting updated for a given row and col.
	@param r the row index of type integer
	@param c the col index of type integer.
	*/
	public void fireTableCellUpdated(int r,int c)
	{
		System.out.println("row"+r +"col"+c);
	}
	/**Adds a row to the table with empty data. */
	public void addingRow()
	{
	/**String to store the empty data to add more row.*/
	String[] st= {"new","","","","","","","","","","","","","","","","",""};
	//System.out.println("st "+st);

		addRow(st);
	}
  /**To set the data in vector type in the table with
  column names in vector.
  @param newData of type vector.
  @param columnNames of type vector.*/
  public void setDataVector(Vector newData, Vector columnNames)
  {
    if (newData == null)
      throw new IllegalArgumentException("setDataVector() - Null parameter");
    dataVector = new Vector(0);
    //setColumnIdentifiers(columnNames);
    dataVector = newData;
    newRowsAdded(new TableModelEvent(this, 0, getRowCount()-1,
		 TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
  }

  /**Adds column to the table with column name and column data.
  @param columnName of type object
  @param columnData of type vector.*/
  public void addColumn(Object columnName, Vector columnData)
  {
    if (columnName == null)
      throw new IllegalArgumentException("addColumn() - null parameter");
    columnIdentifiers.addElement(columnName);
    int index = 0;
    Enumeration enumeration = dataVector.elements();
    while (enumeration.hasMoreElements())
    {
      Object value;
      if ((columnData != null) && (index < columnData.size()))
	  value = columnData.elementAt(index);
      else
	value = null;
      ((Vector)enumeration.nextElement()).addElement(value);
      index++;
    }

    //
    cellAtt.addColumn();

    fireTableStructureChanged();
  }

  /**Adds row to the table with row data as the parameter
  It adds the row in the end of the table.
  @param rowData of type vector.*/
  public void addRow(Vector rowData) {
    Vector newData = null;
    if (rowData == null) {
      newData = new Vector(getColumnCount());
    }
    else {
      rowData.setSize(getColumnCount());
    }
    dataVector.addElement(newData);

    //
    cellAtt.addRow();

    newRowsAdded(new TableModelEvent(this, getRowCount()-1, getRowCount()-1,
       TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
  }

 /**To insert row in between the data with the specified row index.
 @param row of type int where the row to be inserted.
 @param rowData of type vector to data to be inserted.
 */
  public void insertRow(int row, Vector rowData)
  {
    if (rowData == null) {
      rowData = new Vector(getColumnCount());
    }
    else
    {
      rowData.setSize(getColumnCount());
    }

    dataVector.insertElementAt(rowData, row);

    //
    cellAtt.insertRow(row);

    newRowsAdded(new TableModelEvent(this, row, row,
       TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
  }

  /**Gets the cellattribute interface for the current worksheet.
  @return CellAttribute the interface type is returned from this method.*/
  public CellAttribute getCellAttribute()
  {
    return cellAtt;
  }

  /**Sets the cellattribute for the row and column.
  @param newCellAtt of type CellAttribute to be set for the sheet.*/
  public void setCellAttribute(CellAttribute newCellAtt)
  {
    int numColumns = getColumnCount();
    int numRows    = getRowCount();
    if ((newCellAtt.getSize().width  != numColumns) ||
        (newCellAtt.getSize().height != numRows))
    {
      newCellAtt.setSize(new Dimension(numRows, numColumns));
    }
    cellAtt = newCellAtt;
    fireTableDataChanged();
  }
}
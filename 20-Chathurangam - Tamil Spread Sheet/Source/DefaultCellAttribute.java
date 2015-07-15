/**
* @(#)DefaultCellAttribute.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import java.awt.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import javax.swing.border.*;
import java.lang.*;
import java.awt.datatransfer.*;
import java.awt.print.*;
import javax.swing.DefaultCellEditor;


/**
 * @version 1.0 11/22/98
 */
/**This class sets the model for the table to display the
mulitiple fonts. It implements CellAttribute and CellFont interfaces.
Gets the table data and sets the font for the data,fore color
and back color for the data.*/
public class DefaultCellAttribute  implements CellAttribute,CellFont
  {

  // these values must be synchronized to Table data
  /**Store the row size as integer from the table.*/
  protected int rowSize;
  /**Store the column size as integer from the table.*/
  protected int columnSize;
  /**Stores the span value to span the cells.*/
  protected int[][][] span;                   // CellSpan
  /**Sets the fore color for the data in each cell.*/
  protected Color[][] foreground;
  /**Sets the back color for the data in each cell*/
  protected Color[][] background;
  /**Sets the font for each cell in the table.*/
  public static Font[][]  font;
  /**Obejct of the main class <code>Chathurangam</code>*/
  Chathurangam mapp;

  /**Constructor for the class uses the main class object,
  number of rows and number of columns as parameters.
  @param main object of the main class
  @param numRows of type int to set the number of Rows.
  @param numColumns of type int to set the number of Columns.
  */
  public DefaultCellAttribute(Chathurangam main,int numRows, int numColumns)
  {
    mapp = main;
    //font = mapp.mainFont;
    background=mapp.backclr;
    foreground=mapp.foreclr;
    setSize(new Dimension(numColumns, numRows));
  }

 /**Initiates the value for the data to span the cells*/
  protected void initValue()
  {
    for(int i=0; i<span.length;i++)
    {
      for(int j=0; j<span[i].length; j++)
      {
		span[i][j][CellSpan.COLUMN] = 1;
		span[i][j][CellSpan.ROW]    = 1;
      }
    }
  }

  /**Get the span between the cells for given row and column.
  @param row of type int  for row index
  @param column of type int for col index.*/
  public int[] getSpan(int row, int column)
  {
    if (isOutOfBounds(row, column))
    {
      int[] ret_code = {1,1};
      return ret_code;
    }
    return span[row][column];
  }

  /**Sets the span between the cells for given row and column.
  @param span of type integer array the span area.
  @param row of type int for row index
  @param column of type int for col index.*/
  public void setSpan(int[] span, int row, int column)
  {
    if (isOutOfBounds(row, column)) return;
    this.span[row][column] = span;
  }

  /**Checks the given row and column is visible
  @param row of type int for row index
  @param column of type int for col index.
  @return boolean to check the visibility.*/
  public boolean isVisible(int row, int column)
  {
    if (isOutOfBounds(row, column)) return false;
    if ((span[row][column][CellSpan.COLUMN] < 1)
      ||(span[row][column][CellSpan.ROW]    < 1)) return false;
    return true;
  }

 /**Combines the splitted cells  from the table .
 @param rows of type int[] the selected rows.
 @param columns of type int[] the selected cols.
 */
  public void combine(int[] rows, int[] columns)
  {
    if (isOutOfBounds(rows, columns)) return;
    int    rowSpan  = rows.length;
    int columnSpan  = columns.length;
    int startRow    = rows[0];
    int startColumn = columns[0];
    for (int i=0;i<rowSpan;i++)
    {
      for (int j=0;j<columnSpan;j++)
      {
		if ((span[startRow +i][startColumn +j][CellSpan.COLUMN] != 1) ||(span[startRow +i][startColumn +j][CellSpan.ROW]    != 1))
		{
	  		System.out.println("can't combine DefaultCell Attribute");
	  		return ;
	  	}
      }//end of for
    }
    for (int i=0,ii=0;i<rowSpan;i++,ii--)
    {
      for (int j=0,jj=0;j<columnSpan;j++,jj--)
      {
		span[startRow +i][startColumn +j][CellSpan.COLUMN] = jj;
		span[startRow +i][startColumn +j][CellSpan.ROW]    = ii;
      }
    }
    span[startRow][startColumn][CellSpan.COLUMN] = columnSpan;
    span[startRow][startColumn][CellSpan.ROW]    =    rowSpan;

  }

  /**Splits the cell for the given row and column value
  @param row of type int for row index
  @param column of type int for col index.*/
   public void split(int row, int column)
   {
    if (isOutOfBounds(row, column)) return;
    int columnSpan = span[row][column][CellSpan.COLUMN];
    int    rowSpan = span[row][column][CellSpan.ROW];
    for (int i=0;i<rowSpan;i++) {
      for (int j=0;j<columnSpan;j++) {
	span[row +i][column +j][CellSpan.COLUMN] = 1;
	span[row +i][column +j][CellSpan.ROW]    = 1;
      }
    }
  }

/**Gets the fore ground color for the given row and column
  @param row of type int for row index
  @param column of type int for col index.
  @return Color of type color object.
 */
  public Color getForeground(int row, int column)
  {
    if (isOutOfBounds(row, column)) return null;
    return foreground[row][column];
  }

  /**Sets the fore ground color for the given row and column
   @param color the color to be set .
   @param row of type int for row index
    @param column of type int for col index.
   */
  public void setForeground(Color color, int row, int column)
  {
  //System.out.println("color "+color+"row "+row+"col "+column);
    if (isOutOfBounds(row, column)) return;
    this.foreground[row][column] = color;

  }

    /**Sets the fore ground color for the given rows and columns
    @param color the color to be set .
    @param rows of type int[] for selected rows.
     @param columns of type int[] for selected columns.
    */
    public void setForeground(Color color, int[] rows, int[] columns)
    {
     if (isOutOfBounds(rows, columns)) return;
	  for (int i=0;i<rows.length;i++)
	  {
		int row = rows[i];
		for (int j=0;j<columns.length;j++)
		{
		int column = columns[j];
		mapp.worksheet[mapp.curPane].foreclr[row][column]=color;
		}
	 }
    setValues(foreground, color, rows, columns);
  }

   /**Gets the background color for the given row and column
   @param row of type int for row index
   @param column of type int for col index.
   @return Color the color object.
    */
  public Color getBackground(int row, int column)
  {

    if (isOutOfBounds(row, column)) return null;
    return background[row][column];
  }

    /**Sets the background color for the given row and column
    @param color the color to be set.
    @param row of type int for row index
    @param column of type int for col index.*/
  public void setBackground(Color color, int row, int column)
  {

    if (isOutOfBounds(row, column)) return;
    this.background[row][column] = color;
  }

   /**Sets the background color for the given row and column
   @param color the color to be set.
   @param rows of type int[] for row index
   @param columns of type int[] for col index.*/
  public void setBackground(Color color, int[] rows, int[] columns)
  {

    if (isOutOfBounds(rows, columns)) return;
	for (int i=0;i<rows.length;i++) {
	int row = rows[i];
	for (int j=0;j<columns.length;j++) {
	int column = columns[j];
	mapp.worksheet[mapp.curPane].backclr[row][column]=color;
	}}
    setValues(background, color, rows, columns);
  }

  // CellFont

    /**Gets the font for the given row and column
    @param row of type int for row index
    @param column of type int for col index.
    @return Font  the font object.*/
  public Font getFont(int row, int column)
  {
    if (isOutOfBounds(row, column)) return null;
    return font[row][column];
  }

    /**Sets the font for the given row and column
   @param Font of type font.
   @param row of type int for row index
    @param column of type int for col index.
  */
  public void setFont(Font fnt, int row, int column)
  {

    if (isOutOfBounds(row, column)) return;
    font[row][column] = fnt;
    mapp.getWorkSheet().fonts[row][column]=fnt;
  }

   /**Sets the font for the given row and column
  @param Font of type font.
  @param rows of type int[] for row index
   @param columns of type int[] for col index.
   */
  public void setFont(Font fnt, int[] rows, int[] columns)
  {

    if (isOutOfBounds(rows, columns))
    {
		return;
	}
		for (int i=0;i<rows.length;i++) {
	  	int row = rows[i];
	  	for (int j=0;j<columns.length;j++) {
		int column = columns[j];
		mapp.worksheet[mapp.curPane].fonts[row][column]=fnt;
		//System.out.println("DCAcurPane "+mapp.curPane);
		}}

    	setValues(font, fnt, rows, columns);
    //System.out.println("after ");
  }
  //


  //
  // CellAttribute
  /**Add column to the currently using table*/
  public void addColumn()
  {
    int[][][] oldSpan = span;
    int numRows    = oldSpan.length;
    int numColumns = oldSpan[0].length;
    span = new int[numRows][numColumns + 1][2];
    System.arraycopy(oldSpan,0,span,0,numRows);
    for (int i=0;i<numRows;i++) {
      span[i][numColumns][CellSpan.COLUMN] = 1;
      span[i][numColumns][CellSpan.ROW]    = 1;
    }
  }

  /**Adds the row to the table.
  It adds the row at the end of the table.*/
  public void addRow()
  {
    int[][][] oldSpan = span;
    int numRows    = oldSpan.length;
    int numColumns = oldSpan[0].length;
    span = new int[numRows + 1][numColumns][2];
    System.arraycopy(oldSpan,0,span,0,numRows);
    for (int i=0;i<numColumns;i++) {
      span[numRows][i][CellSpan.COLUMN] = 1;
      span[numRows][i][CellSpan.ROW]    = 1;
    }
  }

 /**Inserts the row for the given row index.
 @param row of type int where the row to be inserted.*/
  public void insertRow(int row)
  {
    int[][][] oldSpan = span;
    int numRows    = oldSpan.length;
    int numColumns = oldSpan[0].length;
    span = new int[numRows + 1][numColumns][2];
    if (0 < row) {
      System.arraycopy(oldSpan,0,span,0,row-1);
    }
    System.arraycopy(oldSpan,0,span,row,numRows - row);
    for (int i=0;i<numColumns;i++) {
      span[row][i][CellSpan.COLUMN] = 1;
      span[row][i][CellSpan.ROW]    = 1;
    }
  }

  /**Gets the Size of the row and columns
  @return Dimension to get width and height.*/
  public Dimension getSize()
  {
    return new Dimension(rowSize, columnSize);
  }

  /**Sets the size for the given dimension.
  @param size of type Dimension.*/
  public void setSize(Dimension size)
  {
    columnSize = size.width;
    rowSize    = size.height;
    span = new int[rowSize][columnSize][2];   // 2: COLUMN,ROW
    foreground = new Color[rowSize][columnSize];
    background = new Color[rowSize][columnSize];
    font = new Font[rowSize][columnSize];
	Font curFont=new Font("TABKural",0,12);
    for(int i=0;i<rowSize;i++)
    for(int j=0;j<columnSize;j++)
    font[i][j]=curFont;
    initValue();
  }

  /**Checks for out of bound values in the given row and column
  @param row of type int for row index
  @param column of type int for column index.
  @return boolean the value is true if out of bounds.*/
  protected boolean isOutOfBounds(int row, int column)
  {
    if ((row    < 0)||(rowSize    <= row)
      ||(column < 0)||(columnSize <= column)) {
      return true;
    }
    return false;
  }

    /**Checks for out of bound values in the given row and column
    @param rows of type int[] for row index
    @param columns of type int[] for column index.
    @return boolean the value is true if out of bounds.*/
  protected boolean isOutOfBounds(int[] rows, int[] columns)
  {
    for (int i=0;i<rows.length;i++)
    {
      if ((rows[i] < 0)||(rowSize <= rows[i])) return true;
    }
    for (int i=0;i<columns.length;i++)
    {
      if ((columns[i] < 0)||(columnSize <= columns[i])) return true;
    }
    return false;
  }

  /**Sets the value after font, fore color and back color are
  changed.
  @param target of type object[] to set the target value.
  @param value of type object to set the value.
  @param rows of type int[] for row index
  @param columns of type int[] for column index.
   */
  protected void setValues(Object[][] target, Object value,
                           int[] rows, int[] columns)
   {
    for (int i=0;i<rows.length;i++)
    {
      int row = rows[i];
      for (int j=0;j<columns.length;j++)
      {
		int column = columns[j];
		target[row][column] = value;
      }
    }
  }
}
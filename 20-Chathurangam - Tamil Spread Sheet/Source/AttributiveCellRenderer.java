/**
* @(#)AttributiveCellRenderer.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;

/**
To display the multiples fonts in the sheets.
It is extended from JLabel and implements the TablecellRenderer interface.
It sets back and fore color along withfonts.
 * @version 1.0 11/22/98
 */
public class AttributiveCellRenderer extends JLabel implements TableCellRenderer
{
	/**Object of the main class <code>Chathurangam</code>*/
	Chathurangam main;

	/**Sets the border for the component.*/
   protected static Border noFocusBorder;

  /**Constructor for the class with the main class object
  as parameter.
  @param main object of the main class Chathurangam.*/
  public AttributiveCellRenderer(Chathurangam main)
  {
    this.main=main;
    noFocusBorder = new EmptyBorder(1, 2, 1, 2);
    setOpaque(true);
    setBorder(noFocusBorder);
  }

 /**Gets the cell renderer component for display fonts and colors.
 @param table to which the font and color to be set
 @param value the value for which font and color to be set.
 @paaram isSelected returns true if selected.
 @param hasFocus returns true if got focus.
 @param row of type int the selected row
 @param coloumn of type int the selected column.*/
  public Component getTableCellRendererComponent(JTable table, Object value,
                 boolean isSelected, boolean hasFocus, int row, int column)
  {
   /**Color object for setting fore color.*/
   Color foreground = null;

    /**Color object for setting back color.*/
    Color background = null;

    /**Font object for setting font.*/
    Font font = null;

    /**The model to be set for the table.*/
    TableModel model = table.getModel();
	super.setHorizontalAlignment( JLabel.CENTER );
//	table.setRowSelectionInterval(0,0);
   /*Checks the model with the AttributiveCellTableModel*/
   if (model instanceof AttributiveCellTableModel)
    {
    	/*Checks the font with main class font.*/
      if (main.cellAtt[main.curPane] instanceof CellFont)
      {
			foreground = ((CellFont)main.cellAtt[main.curPane]).getForeground(row,column);
			background = ((CellFont)main.cellAtt[main.curPane]).getBackground(row,column);
			font = ((CellFont)main.cellAtt[main.curPane]).getFont(row,column);
      }
    }
    if (isSelected)
    {
      setForeground((foreground != null) ? foreground
                          : table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    }
    else {
				  setForeground((foreground != null) ? foreground
						  : table.getForeground());
				  setBackground((background != null) ? background
						  : table.getBackground());
   		 }
    setFont((font != null) ? font : table.getFont());

    if (hasFocus)
    {
      setBorder( UIManager.getBorder("Table.focusCellHighlightBorder") );
     if (table.isCellEditable(row, column))
      {
		setForeground((foreground != null) ? foreground
	              : UIManager.getColor("Table.focusCellForeground") );
		setBackground( UIManager.getColor("Table.focusCellBackground") );
      }
    } else
     {
    	  setBorder(noFocusBorder);
     }
    setValue(value);
    return this;
  }

 /**Sets the value after setting font and color.
 @param value of type object , the value to be set.*/
  protected void setValue(Object value)
  {
    setText((value == null) ? "" : value.toString());
  }
}
/**
* @(#)CellSpan.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

/**The interface for receiving events.
The class that is interested in processing an event
implements this interface, and the object created
with that class is registered with a component.
When the event occurs, that object's methods are invoked.
There are five methods implemented in this interface.*/
public interface CellSpan
{
  /**sets the row index as integer type.*/
  public final int ROW    = 0;
  /**sets the column index as integer type.*/
  public final int COLUMN = 1;

  public int[] getSpan(int row, int column);
  public void setSpan(int[] span, int row, int column);

  public boolean isVisible(int row, int column);

  public void combine(int[] rows, int[] columns);
  public void split(int row, int column);

}

/**
* @(#)CellAttribute.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import java.awt.*;

/**The interface for receiving events.
The class that is interested in processing an event
implements this interface, and the object created
with that class is registered with a component.
When the event occurs, that object's methods are invoked.
There are five methods implemented in this interface.*/
public interface CellAttribute {

  /**Add column method for this interface.*/
  public void addColumn();


	/**Add row method for this interface.*/
	public void addRow();

  /**Insert row method for this interface.*/
  public void insertRow(int row);

  /**Gets the size in Dimension for this interface.*/
  public Dimension getSize();

  /**Sets the size for the given Dimension for this interface.*/
  public void setSize(Dimension size);

}


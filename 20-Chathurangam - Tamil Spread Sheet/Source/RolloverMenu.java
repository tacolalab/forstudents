/**
* @(#)RolloverMenu.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import java.awt.Color;

/**Provides the rollover effect for the menu items.
when mouse over on the menu itmes it displays the
border effect for the menus. It extends JMenu.
*/
public class RolloverMenu extends JMenu
{
	/**Mouselistener for the button components.*/
	transient MenuMouseListener mouseListener = new MenuMouseListener();
	/**Border to be set for the buttons to provide rollover effect.*/
	Border b= new BevelBorder(BevelBorder.LOWERED,Color.gray, Color.white);
	/*RolloverMenu(Icon icon)
	{
		super(icon);
		addMouseListener(mouseListener);
		setBorder(b);
		setBorderPainted(false);
	}*/

	/**constructor for the rollover menu with the string as
	the parameter.
	@param str object of the String.*/
	RolloverMenu(String str)
	{
		super(str);
		addMouseListener(mouseListener);
		setBorder(b);
		setBorderPainted(false);
	}

	/**Inner class to provide the mouse listener interface for the
	menus. It extends MouseAdapter class.*/
	class MenuMouseListener extends MouseAdapter
	{
			/**Method used for action event mouse entered.
			@param me the MouseEvent object.*/
		public void mouseEntered(MouseEvent me)
		{
			if(isEnabled())
			{
				setBorderPainted(true);
				repaint();
			}
		}
		/**Method used for action event mouse exited.
		@param me the MouseEvent object.*/
		public void mouseExited(MouseEvent me)
		{
			setBorderPainted(false);
			repaint();
		}
	}
}
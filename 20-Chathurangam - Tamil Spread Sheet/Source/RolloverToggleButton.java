/**
* @(#)RolloverToggleButton.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import javax.swing.JToggleButton;
import javax.swing.Icon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import javax.swing.ButtonModel;
import java.awt.Color;
import java.awt.Graphics;

/**Provides the rollover effect for the toggle button.
when mouse over on the button it displays the
border effect for the buttons. It extends JToggleButton.
*/
public class RolloverToggleButton extends JToggleButton
{
	/**Mouselistener for the button components.*/
	transient ButtonMouseListener mouseListener = new ButtonMouseListener();
	/**Border to be set for the buttons to provide rollover effect.*/
	Border b= new BevelBorder(BevelBorder.LOWERED,Color.gray, Color.white);
	/**constructor to set the rollover effect for the button
	@param icon object of Icon.*/
	RolloverToggleButton(Icon icon)
	{
		super(icon);
		addMouseListener(mouseListener);
		setBorder(b);
		setBorderPainted(false);
	}

	/**constructor for the rollover button with the string as
	the parameter.
	@param str object of the String.*/
	RolloverToggleButton(String str)
	{
		super(str);
		addMouseListener(mouseListener);
		setBorder(b);
		setBorderPainted(false);
	}

	/**Method to select the compoenent for the rollover effect.
	@param select of type boolean to check the selected components.*/
	public void setSelected(boolean select)
	{
		super.setSelected(select);
		if(select)
			setBorderPainted(true);
		else
			setBorderPainted(false);
	}

	/**Inner class to provide the mouse listener interface for the
	buttons. It extends MouseAdapter class.*/
	class ButtonMouseListener extends MouseAdapter
	{
		/**Method used for action event mouse entered.
		@param me the MouseEvent object.*/
		public void mouseEntered(MouseEvent me)
		{
			if(isEnabled())
			{
				setBorderPainted(true);
			}
		}
		/**Method used for action event mouse exited.
		@param me the MouseEvent object.*/
		public void mouseExited(MouseEvent me)
		{
			if(!isSelected())
				setBorderPainted(false);
		}
	}
}

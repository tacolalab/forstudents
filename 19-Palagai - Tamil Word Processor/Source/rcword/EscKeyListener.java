package rcword;

import java.awt.event.*;
import javax.swing.JDialog;

/**
 * Listen to escape key and dispose or make the component invisible
 */
public class EscKeyListener extends KeyAdapter
{
	/*** component for which listener is to set */
	private java.awt.Component component;
	/*** dispose the component on dispose? */
	private boolean canDispose;

	/**
	 * Listen to escape key and dispose or make the component invisible
	 * @param component the component for which listener is to set
	 * @param canDispose can dispose the component if escape key is pressed
	 */
	public EscKeyListener(java.awt.Component component,boolean canDispose)
	{
		this.component = component;
		this.canDispose = canDispose;
	}

	/**
	 * Invoked when a key has been pressed.
	 */
	public void keyPressed(KeyEvent ke)
	{
		//dispose or make invisible when escape key is pressed
		if(ke.getKeyCode() == ke.VK_ESCAPE)
		{
			if(canDispose && component instanceof JDialog)
				((JDialog)component).dispose();
			else
				component.setVisible(false);
		}
	}
}

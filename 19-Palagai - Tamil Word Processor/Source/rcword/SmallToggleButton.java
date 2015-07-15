package rcword;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.rtf.*;
import javax.swing.undo.*;

/**
 * Used to create an interactive button.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class SmallToggleButton extends JToggleButton implements ItemListener
{
	/** button while off state*/
  protected Border m_raised;
	/** button while on state*/
  protected Border m_lowered;

	/**
	* Constructs an object of <code>  SmallToggleButton </code> with the following
	* parameters.
	* @param selected to specify the button's state on or off.
	* @param imgUnselected icon to be fixed for this button while off state
	* @param imgSelected icon to be fixed for this button while on state
	* @param tip tooltip to be fixed for this button.
	*/
  public SmallToggleButton(boolean selected,
   ImageIcon imgUnselected, ImageIcon imgSelected, String tip) {
    super(imgUnselected, selected);
    setHorizontalAlignment(CENTER);
    setBorderPainted(true);
    m_raised = new BevelBorder(BevelBorder.RAISED);
    m_lowered = new BevelBorder(BevelBorder.LOWERED);
    setBorder(selected ? m_lowered : m_raised);
    setMargin(new Insets(1,1,1,1));
    setToolTipText(tip);
    setRequestFocusEnabled(false);
    setSelectedIcon(imgSelected);
    addItemListener(this);
  }

  /**
  * Returns the alignment in the Y-axis.
  */
  public float getAlignmentY() { return 0.5f; }

  /**
  * Used to implement the <code> ItemListener </code>.
  */
  public void itemStateChanged(ItemEvent e)
  {
    setBorder(isSelected() ? m_lowered : m_raised);
  }
}

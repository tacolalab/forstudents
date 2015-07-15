import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.util.MissingResourceException;
import java.util.*;
import java.beans.*;

/**
 * The TTAction class provides a base for TextTools that consist simply of
 * buttons and menu items. The class is incidentally implemented as an Action,
 * but provides its own JButton and JMenuItem objects which work together
 * to reflect each other's state.
 *
 * @author Software Component Trove
 * @version 1.0
 * @since JDK1.2
 */
public class TTAction extends TextTool implements Action
{
	/**
	 * The button object, created as needed.
	 */
	protected AbstractButton button;
	/**
	 * The menu item object, created as needed.
	 */
	protected JMenuItem menuItem;
	private boolean isToggle;
	private MouseOverheadListener mouseOverheadListener;
	private boolean isFlush=true;
	private String label;
	private Icon image;
	private String toolTip;
	private String longDesc;
	private boolean showKeyAction=true;
	private KeyStroke keyStroke;
	private Vector listeners=new Vector();
	private boolean enabled=true;
	protected boolean wasNulled=false;
	private KeyAdapter keyAdapter;
	static private boolean fixBug=true;

	/**
	 * Constructs a TTAction object.
	 *
	 * @param function is a arbitrary string that reflects the TTAction's
	 * function. This string will be used to obtain resource settings from
	 * trove/resources/TextTools.properties
	 * @param reflectStyle indicates whether this control reflects the state of the
	 * attribute at the current cursor position. For example the Bold
	 * button will be shown as selected if the cursor moves to an area of bold
	 * text.
	 * @param toggle if true specifies that the button will be created as a
	 * JToggleButton and the menu item as a JCheckBoxMenuItem, otherwise as
	 * a JButton and JMenuItem respectively.
	 * @param attr not null for TextTools that operate on text attributes,
	 * such as TTBold. This
	 * is a StyleConstants object, and is specified when TTAction
	 * can handle much of the functionality.
	 * @param value if set indicates when the state of this
	 * TextTool is active, eg for an attribute of
	 * StyleConstants.UNDERLINE it will be Boolean.TRUE, for
	 * StyleConstants.Alignment it might be (Integer)StyleConstants.ALIGN_RIGHT.
	 */
	public TTAction(String function,boolean reflectStyle,boolean toggle,
		Object attr,Object val)
	{
		super(attr,val,reflectStyle);
		String label;

		try
		{
			String imageFile=resources.getString(function+"IconImage");
			image=new ImageIcon(Toolkit.getDefaultToolkit().
			createImage(getClass().getClassLoader().
			getResource(imageFile)));
		}
		catch(MissingResourceException ex)
		{
			ex.printStackTrace();
			return;
		}

		try
		{
			label=resources.getString(function+"Label");
		}
		catch (MissingResourceException ex)
		{
			label="";
		}
		this.label=label;
		try
		{
			String accel=resources.getString(function+"Accelerator");
			int mask=0;

			if (accel.indexOf("Ctrl-")!=-1)
				mask=Event.CTRL_MASK;
			if (accel.indexOf("Shift-")!=-1)
				mask|=Event.SHIFT_MASK;
			if (accel.indexOf("Alt-")!=-1)
				mask|=Event.ALT_MASK;
			if (accel.indexOf("Meta-")!=-1)
				mask|=Event.META_MASK;
			keyStroke=KeyStroke.getKeyStroke(accel.charAt(accel.length()-1),mask);
		}
		catch (MissingResourceException ex)
		{
			ex.printStackTrace();
			return;
		}
		toolTip=label;
		isToggle=toggle;
	}

	/**
	 * Constructor for TTAction objects that do their own attribute handling.
	 *
	 * @param function is a arbitrary string that reflects the TTAction's
	 * function. This string will be used for obtain resource settings from
	 * trove/resources/TextTools.properties
	 */
	public TTAction(String function)
	{
		this(function,false,false,null,null);
	}

	/**
	 * Buttons are usually flush to their container and show the border only
	 * when the mouse pointer is overhead.
	 *
	 * @param flush turn this behaviour off
	 */
	public void setFlush(boolean flush)
	{
		isFlush=flush;
		if (button==null) return;
		if (!flush)
		{
			button.removeMouseListener(mouseOverheadListener);
			button.setBorderPainted(true);
		}
		else
		{
			button.addMouseListener(mouseOverheadListener);
			button.setBorderPainted(false);
		}
	}

	/**
	 * Get the internal button associated to this TTAction.
	 *
	 * @return either a JToggleButton or JButton depending on the toggle parameter
	 * of the constructor.
	 */
	public AbstractButton getButton()
	{
		if (button==null)
		{
			if (isToggle)
				button=new JToggleButton(image);
			else
				button=new JButton(image);
			button.setRequestFocusEnabled(false);
			mouseOverheadListener=new MouseOverheadListener();
			button.setFocusPainted(false);
			button.setMargin(new Insets(2,2,2,2));
			button.addActionListener(this);
			button.addItemListener(new ItemSet());
			setKeyboardAction(keyStroke);
			//do this to get the buttons to look the same
			if (!isToggle)
				button.setBorder(new JToggleButton().getBorder());
			setFlush(isFlush);
			setToolTip(toolTip);
		}
		return button;
	}

	/**
	 * Get the internal menu item associated to this TTAction.
	 *
	 * @return either a JCheckBoxMenuItem or JMenuItem depending on the
	 * toggle parameter of the constructor.
	 */
	public JMenuItem getMenuItem()
	{
		if (menuItem==null)
		{
			if (isToggle)
				menuItem=new JCheckBoxMenuItem(label,image);
			else
				menuItem=new JMenuItem(label,image);
			menuItem.addActionListener(this);
			menuItem.setAccelerator(keyStroke);
		}
		return menuItem;
	}

	/**
	 * Sets the tooltip text for the button. The default is the value of the
	 * "Function"Label resource in trove/resources/TextTools.properties
	 *
	 * @param tip the text for the tooltip.
	 */
	public void setToolTip(String tip)
	{
		toolTip=tip;
		String tt=tip;
		if (button==null) return;
		//Java's Metal interface already does this*/
		if (keyStroke!=null&&showKeyAction&&
			UIManager.getLookAndFeel().getName().indexOf("Metal")==-1)
		{
			tt+=" ";
			if ((keyStroke.getModifiers()&java.awt.Event.CTRL_MASK)>0)
				tt+="Ctrl-";
			if ((keyStroke.getModifiers()&java.awt.Event.ALT_MASK)>0)
				tt+="Alt-";
			if ((keyStroke.getModifiers()&java.awt.Event.SHIFT_MASK)>0)
				tt+="Shift-";
			tt+=(char)keyStroke.getKeyCode();
		}
		button.setToolTipText(tt);
	}

	/**
	 * Sets the keyboard action for the button and menu item
	 *
	 * @param ks the keystroke to associate
	 */
	public void setKeyboardAction(KeyStroke ks)
	{
		keyStroke=ks;
		if (button!=null)
		{
			if (ks==null)
			{
				button.unregisterKeyboardAction(
				button.getRegisteredKeyStrokes()[0]);
			}
			else
			{
				button.registerKeyboardAction(
				this,
				"KEYED",
				ks,
				JComponent.WHEN_IN_FOCUSED_WINDOW);
			}
		}
		if (menuItem!=null&&!(menuItem instanceof JMenu))
			menuItem.setAccelerator(ks);
		setToolTip(toolTip);
	}

	private class MouseOverheadListener extends MouseAdapter
	{
		public void mouseEntered(MouseEvent e)
		{
			button.setBorderPainted(true);
		}
		public void mouseExited(MouseEvent e)
		{
			if (!button.isSelected())
				button.setBorderPainted(false);
		}
	}

	/**
	 * Called when action is performed.
	 *
	 * @param keyed indicates if the action was generated via a keyboard action.
	 */
	protected void performAction(boolean keyed)
	{
		//stub
	}

	public void actionPerformed(ActionEvent e)
	{
		if (text!=null)
		{
			if (isToggle)
			{
				if (e.getActionCommand().equals("KEYED"))
				{
					if (button!=null) button.setSelected(!button.isSelected());
					if (menuItem!=null) menuItem.setSelected(button.isSelected());
				}
				else if (e.getSource()==menuItem)
				{
				if (button!=null)
					button.setSelected(menuItem.isSelected());
				}
				else
				if (menuItem!=null) menuItem.setSelected(button.isSelected());
			}
		}
		performAction(e.getActionCommand().equals("KEYED"));
	}

	//partial bug fix, see bug id 4433937 at Sun's java site
	public void setTextComponent(JTextComponent t)
	{
		if (keyAdapter==null&&fixBug)
		{
			keyAdapter=new KeyAdapter()
			{
				public void keyPressed(KeyEvent ke)
				{
					if (text instanceof JTextPane)
					{
						if (wasNulled)
						{
							wasNulled=false;
							((JTextPane)text).setCharacterAttributes(attrs,false);
						}
					}
				}
			};
		}
		if (text!=null && attribute!=null)
		{
			text.removeKeyListener(keyAdapter);
		}
		super.setTextComponent(t);
		if (text!=null && attribute!=null)
		{
			text.addKeyListener(keyAdapter);
		}
	}

	protected void checkTextPaneValueAtCaret(Object val)
	{
		char c=0;
		try{c=text.getDocument().getText(text.getCaretPosition()-1,1).charAt(0);}
		catch (Exception ex) {}

		if (text.getText().length()==0||c=='\n')
		{
			wasNulled=true;
		}
		else if (isToggle)
		{
			boolean set;
			set=val==null?false:val.equals(value);
			if (button!=null) button.setSelected(set);
			if (menuItem!=null) menuItem.setSelected(set);
		}
	}

	/**
	 * Called to turn on or off the text appended to the tooltip to
	 * show the active keyboard action/menu accelerator.
	 *
	 * @param show true to show the keyboard action.
	 */
	public void showKeyboardActionOnToolTip(boolean show)
	{
		showKeyAction=show;
		setToolTip(toolTip);
	}

	private class ItemSet implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			if (isFlush)
				button.setBorderPainted(e.getStateChange()!=ItemEvent.DESELECTED);
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener list)
	{
		if (!listeners.contains(list))
			listeners.add(list);
	}

	public void removePropertyChangeListener(PropertyChangeListener list)
	{
		listeners.remove(list);
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean e)
	{
		if (button!=null) button.setEnabled(e);
		if (menuItem!=null) menuItem.setEnabled(e);
			PropertyChangeEvent event=
				new PropertyChangeEvent(
				this,"Enabled",new Boolean(enabled),new Boolean(e));
		if (enabled!=e)
		{
			Enumeration enum=listeners.elements();
			while (enum.hasMoreElements())
			{
				((PropertyChangeListener)enum.nextElement()).propertyChange(event);
			}
		}
		enabled=e;
	}

	public void putValue(String key, Object val)
	{
		if (key.equals(Action.NAME))
		{
			label=(String)val;
			if (button!=null) button.setText(label);
			if (menuItem!=null) menuItem.setText(label);
		}
		else if (key.equals(Action.SMALL_ICON))
		{
			image=(Icon)val;
			if (button!=null) button.setIcon(image);
			if (menuItem!=null) menuItem.setIcon(image);
		}
		else if (key.equals(Action.SHORT_DESCRIPTION))
		{
			toolTip=(String)val;
			setToolTip(toolTip);
		}
		else if (key.equals(Action.LONG_DESCRIPTION))
		{
			longDesc=(String)val;
		}
	}

	public Object getValue(String key)
	{
		if (key.equals(Action.NAME))
		{
			return label;
		}
		else if (key.equals(Action.SMALL_ICON))
		{
			return image;
		}
		else if (key.equals(Action.SHORT_DESCRIPTION))
		{
			return toolTip;
		}
		else if (key.equals(Action.LONG_DESCRIPTION))
		{
			return longDesc;
		}
		else
			return null;
	}

	/**
	 * Fixes a known bug with JTextPanes, which as at 1.3.1 was still
	 * around. The bug causes the character attributes to be reset whenever
	 * all the text is deleted, when the return key is hit or when the
	 * caret is backspaced to the start of a line. (See bug id 4433937
	 * at the Java site)
	 * @param fix is defaulted to true
	 */
	public void fixJTextPaneBug(boolean fix)
	{
		fixBug=fix;
	}
}

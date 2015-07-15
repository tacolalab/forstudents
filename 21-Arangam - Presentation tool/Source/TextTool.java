import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;

/**
 * The TextTool class is the ancestor class for all TextTools, providing common
 * routines.
 *<p>
 * TextTools operate on JTextComponents, but the operation will be dependent
 * on the abilities of the particular component. For example all TextTools
 * are operational for JTextPanes, but TTUnderline will not be operational on
 * JTextAreas or JTextFields.
 *<p>
 * Apart from automatically setting the appropriate text attribute when selected
 * by the user, TextTools also post ActionEvents to any registered
 * ActionListeners, although these would not usually be required. The
 * <b>command</b> and <b>modifiers</b> fields of the ActionEvent are
 * hijacked to contain information about the attribute changed. See the
 * appropriate descendants' documentation for more information.
 *
 * @author Software Component Trove
 * @version 1.0
 * @since JDK1.2
 */

public class TextTool
{
	/**
	 * Contains the resource information found in
	 * trove/resources/TextTools.properties.
	 */
	protected static ResourceBundle resources;

	static
	{
		resources = Arangam.imageBundle;
	}

	/**
	 * The current JTextComponent that this TextTool control operates on.
	 */
	protected JTextComponent text;
	private Vector actionListeners;
	private TextSelectionListener textSelectionListener;
	/**
	  * Used for JTextPanes
	  */
	protected SimpleAttributeSet attrs=new SimpleAttributeSet();
	/**
	 * The AttributeSet key that this control represents, eg StyleConstants.BOLD.
	 */
	protected Object attribute;
	/**
	 * The corresponding value for the attribute key. It further specifies an
	 * attribute, normally it will be Boolean.TRUE, but may be for example
	 * StyleConstants.ALIGN_LEFT
	 */
	protected Object value;
	private boolean reflectCaret;

	/**
	 *  Constructs a basic TextTool.
	 *<p>
	 * @param attribute the AttributeSet key that this control represents,
	 * eg StyleConstants.BOLD.
	 * @param value if set indicates when the state of this
	 * TextTool is active, eg for an attribute of
	 * StyleConstants.UNDERLINE it will be Boolean.TRUE, for
	 * StyleConstants.Alignment it might be (Integer)StyleConstants.ALIGN_RIGHT.
	 * @param reflectStyle indicates whether this control reflects the state of the
	 * attribute at the current cursor position. For example the Bold
	 * button will be shown as selected if the cursor moves to an area of bold
	 * text.
	 */
	public TextTool(Object attribute,Object value,boolean reflectStyle)
	{
		reflectCaret=reflectStyle;
		this.attribute=attribute;
		this.value=value;
		if (reflectStyle)
		textSelectionListener=new TextSelectionListener();
	}
	/**
	 *  Constructs a basic TextTool which has no specific attribute and does
	 *  not reflect the caret position.
	 */
	public TextTool()
	{
		this(null,null,false);
	}

	/**
	 * Sets the text component for the control to operate on.
	 * @param tp the text component.
	 */
	public void setTextComponent(JTextComponent tp)
	{
		if (text!=null&&reflectCaret)
		text.removeCaretListener(textSelectionListener);
		text=tp;
		if (text!=null&&reflectCaret)
		text.addCaretListener(textSelectionListener);
	}

	/**
	 * Calls all registered ActionListeners, if any.
	 */
	protected void postAction(ActionEvent e)
	{
		if (actionListeners!=null)
		{
			Enumeration enum=actionListeners.elements();
			while (enum.hasMoreElements())
			{
				((ActionListener)enum.nextElement()).actionPerformed(e);
			}
		}
	}

	/**
	 * Adds an ActionListener. These would only be required if the application
	 * needs to perform other operations apart from on the text item itself.
	 */
	public void addActionListener(ActionListener l)
	{
		if (actionListeners==null)
		actionListeners=new Vector();
		if (!actionListeners.contains(l))
		{
			actionListeners.add(l);
		}
	}

	/**
	 * Removes a registered ActionListener.
	 */
	public void removeActionListener(ActionListener l)
	{
		if (actionListeners!=null)
		actionListeners.remove(l);
	}

	private class TextSelectionListener implements CaretListener
	{
		public void caretUpdate(CaretEvent e)
		{
			if (text!=null)
				if (text instanceof JTextPane && attribute!=null)
					checkTextPaneValueAtCaret(evalAttributes(attribute));
				else
					checkTextComponentValueAtCaret();
		}
	}

	/**
	 * Given a StyleConstants attribute, returns the value for that attribute
	 * at the current caret location of a JTextPane
	 * @param attribute the StyleConstants attribute to evaluate
	 */
	protected Object evalAttributes(Object attribute)
	{
		JTextPane textPane=(JTextPane)text;
		Object val=null;

		AttributeSet as=null;
		if (textPane.getSelectionStart()==textPane.getSelectionEnd())
		{
			if (textPane.getSelectionStart()>0)
			{
				Element el=textPane.getStyledDocument().
				getCharacterElement(textPane.getSelectionStart()-1);
				as=el.getAttributes();
			}
			else
			{
				as=textPane.getCharacterAttributes();
			}
		}
		else
		{
			Element el=textPane.getStyledDocument().
			getCharacterElement(textPane.getSelectionStart());
			as=el.getAttributes();
		}
		if (as!=null)
		{
			val=as.getAttribute(attribute);
		}
		if (text.getText().length()!=0)
		{
			attrs.removeAttribute(attribute);
			if (val!=null)
				attrs.addAttribute(attribute,val);
		}
		return val;
	}

	/**
	 * Called when a caret update occurs on a JTextPane. The implementer
	 * should use this to set the state of the TextTool if it reflects
	 * the current text's attributes.
	 * @param val the value for this TextTool's attribute at the current caret
	 * position.
	 */
	protected void checkTextPaneValueAtCaret(Object val){}

	/**
	 * Called when a non-JTextPane JTextComponent is selected. The implementer
	 * should use this to set the state of the TextTool if it reflects
	 * the current text's attributes.
	 */
	protected void checkTextComponentValueAtCaret(){}
}


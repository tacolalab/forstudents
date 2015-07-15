import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;

public interface ConnectText extends ConnectComponent
{

	/**
	 * Returns the text contained in this TextComponent in terms of the
	 * content type of this editor. If an exception is thrown while
	 * attempting to retrieve the text, null will be returned.
	 * @return the text
	 */
	public String getText();

	/**
	 * Applies the given attributes to character content. If there is a
	 * selection, the attributes are applied to the selection range.
	 * If there is no selection, the attributes are applied to the input
	 * attribute set which defines the attributes for any new text that gets
	 * inserted.
	 * @param attr the attributes
	 * @param replace if <code>true</code>, then replace the existing attributes first
	 */
	public void setCharacterAttributes(AttributeSet attr,boolean replace);

	/**
	 * Fetches the character attributes in effect at the current location
	 * of the caret, or null
	 * @return the attributes, or null
	 */
	public AttributeSet getCharacterAttributes();

	/**
	 * Gets the input attributes for the pane
	 * @return the attributes
	 */
	public MutableAttributeSet getInputAttributes();

	/**
	 * Returns the selected text contained in this TextComponent
	 * If the selection is null or the document empty, returns null
	 * @return the text
	 */
	public String getSelectedText();

	/**
	 * Fetches the caret that allows text-oriented navigation over the view
	 * @return the attributes, or null
	 * @return the caret
	 */
	public Caret getCaret();

	/**
	 * Fetches the model associated with the editor
	 * @return the model
	 */
	public StyledDocument getStyledDocument();

	/**
	 * Sets the current color used to render the selected text.
	 * Setting the color to null is the same as Color.black
	 * @param selectedTextColor the color
	 */
	public void setSelectedTextColor(Color selectedTextColor);

	/**
	 * Sets the current color used to render the selection. Setting the
	 * color to null is the same as setting Color.white. Setting the
	 * color results in a PropertyChange event ("selectionColor")
	 * @param selectionTextColor the color
	 */
	public void setSelectionColor(Color selectionTextColor);

	/**
	 * Sets the current color used to render the caret. Setting to null
	 * effectively restores the default color. Setting the color results
	 * in a PropertyChange event ("caretColor") being fired
	 * of the caret, or null
	 * @param caretColor the color
	 */
	public void setCaretColor(Color caretColor);

	/**
	 * Sets the background color of this component
	 * @param bgColor the desired background Color
	 */
	public void setBackground(Color bgColor);

	/**
	 * Returns the position of the text insertion caret for the text component
	 * @return the position of the text insertion caret for the text component >= 0
	 */
	public int getCaretPosition();

	/**
	 * Sets the selection start to the specified position.
	 * The new starting point is constrained to be before or at the current
	 * selection end of the caret, or null
	 * @param selectionStart the start position of the text >= 0
	 */
	public void setSelectionStart(int selectionStart);

	/**
	 * Sets the selection end to the specified position.
	 * The new end point is constrained to be at or after the current
	 * selection start of the caret, or null
	 * @param selectionEnd - the end position of the text >= 0
	 */
	public void setSelectionEnd(int selectionEnd);

	/**
	 * Applies the given attributes to character content. If there is a
	 * selection, the attributes are applied to the selection range.
	 * If there is no selection, the attributes are applied to the input
	 * attribute set which defines the attributes for any new text that gets
	 * inserted.
	 * @param attr the attributes
	 * @param ApplyToPara if <code>true</code>, apply to paragraph
	 */
	public void setAttributeSet(AttributeSet attr, boolean ApplyToPara);

	/**
	 * Selects all the text in the TextComponent. Does nothing on a null or
	 * empty document
	 */
	public void selectAll();

	/**
	 * Replaces the currently selected content with new content represented
	 * by the given string. If there is no selection this amounts to an
	 * insert of the given text. If there is no replacement text
	 * this amounts to a removal of the current selection. The replacement
	 * text will have the attributes currently defined for input at
	 * the point of insertion. If the document is not editable, beep and return
	 * @param content the content to replace the selection with
	 */
	public void replaceSelection(String replace)
		throws BadLocationException;

	/**
	 * Replaces the currently selected content with new content represented
	 * by the given StyledText. If there is no selection this amounts to an
	 * insert of the given text. If there is no replacement text
	 * this amounts to a removal of the current selection. The replacement
	 * text will have the attributes currently defined for input at
	 * the point of insertion. If the document is not editable, beep and return
	 * @param content the content to replace the selection with
	 */
	public void replaceSelection(StyledText content)
		throws BadLocationException;

	/**
	 * Returns the size of this component in the form of a Dimension object.
	 * The height field of the Dimension object contains this component's
	 * height, and the width field of the Dimension object contains
	 * this component's width
	 * @return a Dimension object that indicates the size of this component
	 */
	public Dimension getSize();

	/**
	 * Returns the border of this component or null if no border is
	 * currently set
	 * @return the border object for this component
	 */
	public Border getBorder();

	/**
	 * Gets the background color of this component
	 * @return this component's background color; if this component
	 * does not have a background color, the background color of its
	 * parent is returned
	 */
	public Color getBackground();

	/**
	 * Selects the text between the specified start and end positions
	 * of the caret, or null
	 * @param selectionStart the start position of the text
	 * @param selectionEnd the end position of the text
	 */
	public void select(int selectionStart,int selectionEnd);

	/**
	 * Apply or remove focus from this component
	 * @param focus <code>true</code> if this component should be focused, <code>false</code> otherwise
	 */
	public void setFocus(boolean focus);
}

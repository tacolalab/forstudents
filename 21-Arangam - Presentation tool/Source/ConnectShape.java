import java.awt.*;

/**
 * Interface for Arangam Slide component, AShape
 */
public interface ConnectShape extends ConnectComponent
{
	/**
	 * Set background color for this component
	 * @param bgColor color to set as background
	 */
	public void setBackground(Color bgColor);

	/**
	 * Set border color for this component
	 * @param outlineColor color to set as outline
	 */
	public void setOutlineColor(Color outlineColor);

	/**
	 * Set border for the inner component - "text editor"
	 * @param border border to set
	 */
	public void setEditorBorder(javax.swing.border.Border border);

	/**
	 * Set font/ text attributes for "text editor"
	 * @param a the attributeset to set
	 */
	public void setFontAttributes(javax.swing.text.AttributeSet a);

	/**
	 * Applies the given attributes to character content. If there is
	 * a selection, the attributes are applied to the selection range.
	 * If there is no selection, the attributes are applied to the input
	 * attribute set which defines the attributes for any new text that
	 * @param a the attributes
	 * @param replace if <code>true</code>, then replace the existing attributes first
	 */
	public void setCharacterAttributes(javax.swing.text.AttributeSet a,
		boolean replace);

	/**
	 * Get text/ font attributes of this component
	 * @return the attributes
	 */
	public javax.swing.text.AttributeSet getFontAttributes();
}

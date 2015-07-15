import java.util.ArrayList;

/**
 * ArrayList to store all the text components in a Slide
 * Used for find/replace
 * @see FindReplaceDialog#find
 */
public class TextArray extends ArrayList
{
	/**
	 * Initialise the arraylist
	 */
	TextArray()
	{
		super();
	}

	/**
	 * Appends the specified AText element to the end of this list
	 * @param o element to be appended to this list.
	 * @return <code>true</code> (as per the general contract of Collection.add).
	 */
	public boolean add(Object o)
	{
		super.add((AText)o);
		return true;
	}

	/**
	 * Gets the AText element at the specified position
	 */
	public AText getArangamText(int i)
	{
		AText p = (AText)(super.get(i));
		return p;
	}
}

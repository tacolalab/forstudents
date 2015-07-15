import java.awt.datatransfer.*;

/**
 * A class which implements the capability required to transfer a
 * component (<code>AText, AImage, AShape</code>). This capability
 * is used in the cut, copy and paste actions.
 * @see Slide#copyComponent()
 * @see Slide#pasteComponent()
 * @see ConnectComponent
 */
public class ComponentSelection implements Transferable,ClipboardOwner
{
	/*** AText data format that can appear on a clipboard */
	public static DataFlavor textFlavor;

	/*** AImage data format that can appear on a clipboard	*/
	public static DataFlavor imageFlavor;

	/*** AShape data format that can appear on a clipboard.	*/
	public static DataFlavor shapeFlavor;

	/*** Flavors that are supported */
	private DataFlavor [] supportedFlavors =
		{textFlavor, imageFlavor, shapeFlavor};

	/*** text component's reference */
	private AText theText;
	/*** image component's reference */
	private AImage theImage;
	/*** shape component's reference */
	private AShape theShape;

	/**
	 * Creates a transferable object capable of transferring
	 * the specified <code>ConnectComponent</code>
	 * @param theArangamObject the component to be transferred
	 */

	public ComponentSelection(Object theArangamObject)
	{
		//identify the component- text or image or shape
		//and make a reference to it
		String className = theArangamObject.getClass().toString();
		if (className.equals("class AText"))
		{
			try
			{
				theText = (AText)theArangamObject;
				textFlavor = new DataFlavor(Class.forName("AText"), "AText");
				imageFlavor = null;
				shapeFlavor = null;
			}
			catch(ClassNotFoundException cnfe)
			{
				return;
			}
		}
		else if (className.equals("class AImage"))
		{
			try
			{
				theImage = (AImage)theArangamObject;
				imageFlavor = new DataFlavor(Class.forName("java.awt.Image"), "AImage");
				textFlavor = null;
				shapeFlavor = null;
			}
			catch(ClassNotFoundException cnfe)
			{
				return;
			}
		}
		else if (className.equals("class AShape"))
		{
			try
			{
				theShape= (AShape)theArangamObject;
				shapeFlavor = new DataFlavor(Class.forName("AShape"), "AShape");
				textFlavor = null;
				imageFlavor = null;
			}
			catch(ClassNotFoundException cnfe)
			{
				return;
			}
		}
	}

	/**
	 * Returns an array of <code>DataFlavor</code> objects indicating the flavors the
	 * data can be provided in <code>AText, AImage, AShape</code>
	 */
	public synchronized DataFlavor[] getTransferDataFlavors()
	{
		return (supportedFlavors);
	}

	/**
	 * Returns whether or not the specified data flavor is supported for
	 * this object.
	 */
	public boolean isDataFlavorSupported(DataFlavor arangamComponentFlavor)
	{
		if(arangamComponentFlavor == null)
			return false;
		if((this.textFlavor != null) &&
			(arangamComponentFlavor.equals(this.textFlavor)))
			return true;
		if((this.imageFlavor != null) &&
			(arangamComponentFlavor.equals(this.imageFlavor)))
			return true;
		if((this.shapeFlavor != null) &&
			(arangamComponentFlavor.equals(this.shapeFlavor)))
			return true;
		return false;
	}

	/**
	 * If the data requested is <code>AText, AImage or AShape</code>
	 * flavor, returns the object representing the data to be transferred. The
	 * class of the object returned is defined by the representation class of
	 * the flavor.
	 *
	 * @param arangamComponentFlavor  the requested flavor for the data
	 * @exception UnsupportedFlavorException
	 *   if the requested data flavor is not supported
	 */
	public synchronized Object getTransferData(DataFlavor arangamComponentFlavor)
		throws UnsupportedFlavorException
	{
		if(arangamComponentFlavor.equals(this.textFlavor))
			return theText;
		else if(arangamComponentFlavor.equals(this.imageFlavor))
			return theImage;
		else if(arangamComponentFlavor.equals(this.shapeFlavor))
			return theShape;
		else
			throw new UnsupportedFlavorException (arangamComponentFlavor);
	}

	/**
	 * Notifies this object that it is no longer the owner of the contents
	 * of the clipboard. Does nothing here.
	 *
	 * @param parClipboard    the clipboard that is no longer owned
	 * @param parTranserable  the contents which this owner had placed on the
	 * clipboard
	 */
	public void lostOwnership(Clipboard parClipboard,Transferable parTransferable)
	{

	}
}

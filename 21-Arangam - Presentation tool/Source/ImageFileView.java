import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * An implementation of FileView interface to provide the file chooser with
 * UI information for image files.
 * <p>
 * This implementation will provide an icon for the following file types:
 * gif, jpg
 */
public class ImageFileView extends FileView
{
	ImageIcon jpgIcon = ImagesLocator.getImage(Arangam.imageBundle.getString("jpgIcon"));
	ImageIcon gifIcon = ImagesLocator.getImage(Arangam.imageBundle.getString("gifIcon"));

	/**
	 * The name of the file. Do nothing here and let the system file view
	 * handle this.
	 *
	 * @see FileView#getName
	 */
	public String getName(File bilingualFont)
	{
		return null;   //let the L&F FileView figure this out
	}

	/**
	 * A human readable description of the file. Do nothing here and let
	 * the system file view handle this.
	 *
	 * @see FileView#getDescription
	 */
	public String getDescription(File bilingualFont)
	{
		return null;   //let the L&F FileView figure this out
	}

	/**
	 * Whether the directory is traversable or not. Do nothing here and let
	 * the system file view handle this.
	 *
	 * @see FileView#isTraversable
	 */
	public Boolean isTraversable(File bilingualFont)
	{
		return null;   //let the L&F FileView figure this out
	}

	/**
	 * A human readable description of the type of the file.
	 *
	 * @see #getExtension
	 * @see FileView#getTypeDescription
	 */
	public String getTypeDescription(File bilingualFont)
	{
		String extension = getExtension(bilingualFont);
		String type = null;
		if(extension != null)
		{
			if(extension.equals("jpeg") || extension.equals("jpg"))
				type = "JPEG Image";
			else if(extension.equals("gif"))
				type = "GIF Image";
		}
		return type;
	}

	/**
	 * Icon that represents this file. Returns JPG, or GIF icons
	 * according to file type.
	 *
	 * @see FileView#getIcon
	 */
	public Icon getIcon(File bilingualFont)
	{
		String extension = getExtension(bilingualFont);
		Icon icon = null;
		if(extension != null)
		{
			if(extension.equals("jpeg") || extension.equals("jpg"))
				icon = jpgIcon;
			else if(extension.equals("gif"))
				icon = gifIcon;
		}
		return icon;
	}

	/**
	 * Returns the "dot" extension for the given file.
	 *
	 * @see #getTypeDescription
	 */
	public static String getExtension(File bilingualFont)
	{
		String ext = null;
		String s = bilingualFont.getName();
		int i = s.lastIndexOf('.');
		if (i > 0 &&  i < s.length() - 1)
		ext = s.substring(i+1).toLowerCase();
		return ext;
	}
}

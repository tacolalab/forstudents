import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.filechooser.FileView;

/**
 * An implementation of FileView interface to provide the file chooser with
 * UI information for "Arangam" files.
 * <p>
 * This implementation will provide icon for files with *.tpt file name
 * extension.
 */
public class ArangamFileView extends FileView
{
	ImageIcon arangamFileIcon = ImagesLocator.getImage(Arangam.imageBundle.getString("arangamFileIcon"));

	/**
	 * The name of the file. Do nothing here and let the system file view
	 * handle this.

	 * @see FileView#getName
	 */
	public String getName(File file)
	{
		return null;   //let the L&F FileView figure this out
	}

	/**
	 * A human readable description of the file. Do nothing here and let
	 * the system file view handle this.
	 *
	 * @see FileView#getDescription
	 */
	public String getDescription(File f)
	{
		return null;   //let the L&F FileView figure this out
	}

	/**
	 * Whether the directory is traversable or not. Do nothing here and let
	 * the system file view handle this.
	 *
	 * @see FileView#isTraversable
	 */
	public Boolean isTraversable(File f)
	{
		return null;   //let the L&F FileView figure this out
	}

	/**
	 * A human readable description of the type of the file.
	 *
	 * @see #getExtension
	 * @see FileView#getTypeDescription
	 * @param file the file
	 * @return the description of type of file
	 */
	public String getTypeDescription(File file)
	{
		String extension = getExtension(file);
		String type = null;
		if (extension != null)
			if (extension.equals(Arangam.fileExt))
				type = "Arangam";
		return type;
	}

	/**
	 * Gets the icon that represents this file.
	 * @param file the file
	 * @return the "Arangam" icon.
	 * @see FileView#getIcon
	 */
	public Icon getIcon(File file)
	{
		String extension = getExtension(file);
		Icon icon = null;
		if (extension != null)
			if (extension.equals(Arangam.fileExt))
				icon = arangamFileIcon;
		return icon;
	}

	/**
	 * Returns the "dot" extension for the given file.
	 * @param file the file
	 * @return the extension of the given file
	 * @see #getTypeDescription
	 */
	public static String getExtension(File f)
	{
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		if (i > 0 &&  i < s.length() - 1)
			ext = s.substring(i+1).toLowerCase();
		return ext;
	}
}

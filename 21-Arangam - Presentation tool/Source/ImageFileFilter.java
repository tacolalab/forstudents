import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * An implementation of FileFilter interface to let a JFileChooser keep
 * non-image files from appearing in the directory listing.
 * <p>
 * This implementation will filter all files without having the
 * file extensions: gif, jpg
 */
public class ImageFileFilter extends FileFilter
{
	/**
	 * Accepts all directories and all gif, jpg and jpeg files.
	 * @return <code>true</code> if this file should be shown in the directory pane,
	 * false if it shouldn't.
	 * @see #getExtension
	 * @see FileFilter#accept
	 */
	public boolean accept(File file)
	{
		//accept directories
		if(file.isDirectory())
			return true;

		//accept image files .jpg and .gif
		String extension = getExtension(file);
		if(extension != null)
		{
			if (extension.equals("gif") ||
				extension.equals("jpeg") ||
				extension.equals("jpg"))
				return true;
			else
				return false;
		}
		return false;
	}

	/**
	 * Returns the human readable description of this filter. The description
	 * returned is:
	 * <p>
	 * <ul>"All Pictures (*.gif; *.jpg; *.jpeg;)"</ul>
	 * @return the description of this filter
	 * @see FileFilter#getDescription
	 */
	public String getDescription()
	{
		ResourceBundle wordBundle = Arangam.wordBundle;

		return wordBundle.getString("PictureFiles");
	}

	/**
	 * Returns the extension portion of the file's name.
	 * @param file the File
	 * @return the file extension.
	 * @see #accept
	 */
	public static String getExtension(File file)
	{
		String ext = null;
		String s = file.getName();
		int i = s.lastIndexOf('.');

		//get the portion after last dot
		if (i > 0 &&  i < s.length() - 1)
			ext = s.substring(i+1).toLowerCase();
		return ext;
	}
}

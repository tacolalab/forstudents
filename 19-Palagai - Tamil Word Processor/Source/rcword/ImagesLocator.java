package rcword;

import java.net.URL;
import java.awt.*;
import javax.swing.*;

/**
 * Used to load the images from correct place (directory or JAR file) into GUI.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */

public class ImagesLocator
{
	/**
	* Loads an image from correct place (directory or JAR file).
	*
	* @param imageName  name of image to be loaded (with no path)
	* @return loaded image
	*/
	public static ImageIcon getImage(String imageName)
	{
		ClassLoader cl = ImagesLocator.class.getClassLoader();
		ImageIcon i = new ImageIcon(cl.getResource(imageName));
		return i;
	}

}

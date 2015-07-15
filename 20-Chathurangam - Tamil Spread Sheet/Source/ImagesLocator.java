/**
* @(#)ImagesLocator.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/
import java.net.URL;
import java.awt.*;
import javax.swing.*;

/**
* Load images from correct place (directory or JAR
file) into GUI.
*
*/
public class ImagesLocator
{
	/**
	* Load image from correct place (directory or JAR
file).
	*
	* @param imageName  name of image to be loaded (with
no path)
	* @return loaded image
	*/
	public static ImageIcon getImage(String imageName)
	{
		ClassLoader cl=ImagesLocator.class.getClassLoader();
		return new ImageIcon(cl.getResource(imageName));
	}
	ImageIcon cutIcon =ImagesLocator.getImage("sps1.gif");
}

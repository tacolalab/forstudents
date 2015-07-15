import javax.swing.*;
import java.beans.*;
import java.awt.*;
import java.io.File;

/**
 * Preview for image files (jpg and gif). Utility used with
 * file chooser while inserting image
 */
public class ImagePreview extends JComponent implements PropertyChangeListener
{
	/*** Icon to show the image file */
	private ImageIcon thumbnail = null;
	/*** The image file */
	private File file = null;

	/**
	 * Associates the file chooser fc with this
	 * @param fc the file chooser
	 */
	public ImagePreview(JFileChooser fc)
	{
		setPreferredSize(new Dimension(100, 50));
		fc.addPropertyChangeListener(this);
	}

	/**
	 * Update the icon when the selected file in file chooser changes
	 */
	public void loadImage()
	{
		//return if no file is selected
		if (file == null)
			return;

		//get icon
		ImageIcon tmpIcon = new ImageIcon(file.getPath());

		//get it scaled if it is too large
		if (tmpIcon.getIconWidth() > 90)
		{
			thumbnail = new ImageIcon(tmpIcon.getImage().getScaledInstance(
								90, -1, Image.SCALE_DEFAULT));
		}
		else
			thumbnail = tmpIcon;
	}

	/**
	 * Invoked when a bound property is changed
	 */
	public void propertyChange(PropertyChangeEvent e)
	{
		//load image when the selected file is changes
		String prop = e.getPropertyName();
		if(prop.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY))
		{
			file = (File) e.getNewValue();
			if(isShowing())
			{
				loadImage();
				repaint();
			}
		}
	}

	/**
	 * Paints the icon
	 * @param graphics the <code>Graphics</code> context in which to paint.
	 */
	public void paintComponent(Graphics graphics)
	{
		if (thumbnail == null)
			loadImage();
		if (thumbnail != null)
		{
			int x = getWidth()/2 - thumbnail.getIconWidth()/2;
			int y = getHeight()/2 - thumbnail.getIconHeight()/2;
			if (y < 0)
				y = 0;
			if (x < 5)
				x = 5;
			thumbnail.paintIcon(this, graphics, x, y);
		}
	}
}

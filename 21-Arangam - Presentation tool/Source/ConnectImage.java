import javax.swing.*;
import java.awt.*;

/**
 * Interface for Arangam Slide component. AImage
 */
public interface ConnectImage extends ConnectComponent
{
	/**
	 * Get the original image - not scaled
	 * @return the ImageIcon
	 */
	public ImageIcon getOriginalImageIcon();

	/**
	 * Get the icon of the label(image component)
	 * @return the Icon
	 */
	public Icon getIcon();

	/**
	 * Set Icon to label(image component)
	 * @param icon icon to set
	 */
	public void setIcon(Icon icon);

	/**
	 * Moves and resizes this component to conform to the new bounding
	 * rectangle r. This component's new position is specified by r.x
	 * and r.y, and its new size is specified by r.width and r.height
	 * @param r the new bounding rectangle for this component
	 */
	public void setBounds(Rectangle r);

	/**
	 * Resizes this component so that it has width w and height h
	 * @param w the new width of this component in pixels
	 * @param h the new height of this component in pixels
	 */
	public void setSize(int w,int h);

	/**
	 * Moves this component to a new location. The top-left corner of
	 * the new location is specified by the x and y parameters in
	 * the coordinate space of this component's parent
	 * @param x the x-coordinate of the new location's
	 * top-left corner in the parent's coordinate space
	 * @param  the y-coordinate of the new
	 * location's top-left corner in the parent's coordinate space
	 */
	public void setLocation(int x,int y);

	/**
	 * Set bounds, Invoked when scaling
	 * @param diff the difference between mouse pressed and released point
	 * @param eventLocation mouse pressed point
	 * @param draggingLocation mouse dragged point
	 * @param draggingEvent drag event direction
	 */
	public void setBounds(Point diff, Point eventLocation,
		Point draggingLocation,int draggingEvent);
}

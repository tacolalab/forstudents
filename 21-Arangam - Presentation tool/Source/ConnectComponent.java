import java.awt.*;
import javax.swing.border.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * Interface for Arangam Slide components (AText, AImage, AShape)
 */
public interface ConnectComponent
{
    /**
     * Dragging direction constants
     */
	public static final int MOVE       = 0,
							EDIT       = 1,
							NORTH      = 2,
							SOUTH      = 3,
							EAST       = 4,
							WEST       = 5,
							NORTH_EAST = 6,
							NORTH_WEST = 7,
							SOUTH_EAST = 8,
							SOUTH_WEST = 9;

	//increment in paste position
	int xInc = 20;
	int yInc = 20;

	/**
	 * Register listeners of the component.
	 */
	public void registerListeners();

	/**
	 * Connects component with the main application. This enables the
	 * component to receive general information about the global state of
	 * the application.
	 */
	public void getInterface(Arangam arangam);

	/**
	 * Focus or un focus the component
	 * @param focus - true to focus and false not to focus the component
	 */
	public void setFocus(boolean focus);

	/**
	 * Sets the specified boolean to indicate whether or not this component
	 * should be editable. For example, should be called with false when
	 * in SlideSorter mode.
	 * @param editable - indicates whether this component is editable
	 */
	public void setArangamEditable(boolean editable);

	/**
	 * Removes the component from it's parent Slide
	 */
	public void removeFromSlide();

	/**
	 * Sets the opaque property of the component.
	 * @param isOpaque - true if this component should be opaque
	 */
	public void setOpaque(boolean isOpaque);

	/**
	 * Returns the bounding rectangle of the component.
	 * @return a rectangle indicating this component's bounds.
	 */
	public Rectangle getBounds();

	/**
	 * Sets the border of this component
	 * @param border the border to be rendered for this component
	 */
	public void setBorder(Border border);

	/**
	 * Checks the opaque property of the component.
	 * @return <code>true</code> if this component is opaque
	 */
	public boolean isOpaque();

	/**
	 * Gets the location of this component in the form of a point.
	 * @return an instance of Point representing the top-left
	 * corner of the component's bounds.
	 */
	public Point getLocation();

	/**
	 * Sets the bound for the component.
	 * @param x the new x-coordinate of this component
	 * @param y the new y-coordinate of this component
	 * @param width the new width of this component
	 * @param height the new height of this component
	 */
	public void setBounds(int x,int y,int width,int heigth);

	/**
	 * Shows or hides this component depending on the value of parameter b.
	 * @param b if <code>true</code>, shows this component; otherwise, hides this component
	 */
	public void setVisible(boolean b);

	/**
	 * Set the "position" variable to the relative position of the
	 * component within its layer.
	 * @param position the components position to set
	 */
	public void setP(int position);

	/**
	 * Set position for pasting components from clipboard
	 */
	public void setPastePosition();
}

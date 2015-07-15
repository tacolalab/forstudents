import java.awt.*;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

/**
 * Bounding component for all components
 */
public class ABoundLabel extends JLabel implements ConnectComponent
{
	/*** Main class reference */
	private transient Arangam arangam;

	/*** Slideshow in which this is added */
	private Slide parentSlide;

	/*** Can be edited (including dragging) */
	private boolean isArangamEditable = true;

	/*** Is the mouse dragged */
	private boolean isDragged = false;

	/*** Mouse Drag in which direction */
	private int draggingEvent;

	/*** Relative location for dragging */
	private Point draggingLocation = new Point();

	/*** True if mouse moves in border of the component */
	private boolean isBorder = false;

	/*** Mouse pressed position */
	private Point pressed = new Point();

	/*** Mouse Released position */
	private Point released = new Point();

	/*** Difference between mouse pressed and released point */
	private Point diff = new Point();

	/*** Bounds of this component */
	private Rectangle myBounds;

	/*** Bounds of parent component */
	private Rectangle slideBounds;

	/*** Component that is focused */
	private ConnectComponent focused;

	/*** Mouse sensitivity place for scaling and moving */
	private final int BOUNDS = 6;

	/**
	 * Remembers the position of this component and
	 * place this component in position "one" temporary while editing
	 */
	private int position = -2;

	/**
	 * Constructs a new bounding component and connects it to a <code>Slide</code>
	 * and to the application(<code>Arangam</code>)
	 * @param aSlide contains the bounding component
	 * @param arangam main class reference
	 * @param x the new x-coordinate of this component
	 * @param y the new y-coordinate of this component
	 * @param w the new width of this component
	 * @param h the new height of this component
	 * @see Slide
	 * @see Arangam
	 */
	public ABoundLabel(Slide aSlide, Arangam arangam,
				int x, int y,int w,int h)
	{
		super("");
		this.arangam = arangam;
		this.parentSlide = aSlide;

		//set bounds
		setLocation(x,y);
		setSize(w,h);

		//add listeners
		registerListeners();
	}

	/**
	 * Register mouse and mouse motion listeners
	 * @see ABoundLabel.SymMouse
	 * @see ABoundLabel.SymMouseMotion
	 */
	public void registerListeners()
	{
		//mouse motion
		SymMouseMotion aSymMouseMotion = new SymMouseMotion();
		addMouseMotionListener(aSymMouseMotion);

		//mouse
 		SymMouse aSymMouse = new SymMouse();
		addMouseListener(aSymMouse);
	}

	/**
	 * MouseMotionAdapter for the component.
	 * Determines the dragging event and check whether the
	 * mouse is on component's border and also
	 * to resize the component
	 */
	private class SymMouseMotion extends java.awt.event.MouseMotionAdapter
	{
		/**
		 * Invoked when mouse moved
		 * Determines the dragging event and check whether the
		 * mouse is on component's border
		 * @see #Label_mouseMoved
		 */
		public void mouseMoved(java.awt.event.MouseEvent event)
		{
			Label_mouseMoved(event);
		}

		/**
		 * Invoked when mouse dragged
		 * Resizes the component
		 * @see #Label_mouseDragged
		 */
		public void mouseDragged(java.awt.event.MouseEvent event)
		{
			Label_mouseDragged(event);
		}
	}

	/**
	 * MouseAdapter for the component.
	 * Shows popup menu on right click and to focus the component on
	 * mouse press
	 */
	private class SymMouse extends java.awt.event.MouseAdapter
	{
		/**
		 * Invoked when mouse pressed
		 * Remembers the mouse pressed position
		 * @see #Label_mousePressed
		 */
		public void mousePressed(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == ABoundLabel.this && isArangamEditable)
				Label_mousePressed(event);
		}

		/**
		 * Invoked when mouse released
		 * Remembers the mouse released position
		 * @see #Label_mouseReleased
		 */
		public void mouseReleased(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == ABoundLabel.this && isArangamEditable)
				Label_mouseReleased(event);
		}
	}

	/**
	 * Remembers the mouse pressed position
	 */
	private void Label_mousePressed(java.awt.event.MouseEvent event)
	{
		//start drag
		isDragged = true;
		setFocus(true);
		draggingLocation.setLocation(event.getX(), event.getY());

		//remember the mouse pressed position if the bounded component is AImage
		focused=arangam.slideshow1.getCurrentSlide().focused;
		if(focused!=null &&
			focused.getClass().getName().equals("AImage"))
		{
			switch(draggingEvent)
			{
				case ConnectComponent.NORTH_WEST:
					pressed.x = -getX();
					pressed.y = -getY();
					break;
				case ConnectComponent.NORTH_EAST:
					pressed.x = event.getX();
					pressed.y = -getY();
					break;
				case ConnectComponent.SOUTH_WEST:
					pressed.x = -getX();
					pressed.y = event.getY();
					break;
				case ConnectComponent.SOUTH_EAST:
					pressed.x = event.getX();
					pressed.y = event.getY();
					break;
				case ConnectComponent.NORTH:
					pressed.x = event.getX();
					pressed.y = -getY();
					break;
				case ConnectComponent.SOUTH:
					pressed.x = event.getX();
					pressed.y = event.getY();
					break;
				case ConnectComponent.EAST:
					pressed.x = event.getX();
					pressed.y = event.getY();
					break;
				case ConnectComponent.WEST:
					pressed.x = -getX();
					pressed.y = -getY();
					break;
			}//switch
		}//if
	}

	/**
	 * Remembers the mouse released position
	 * @see #setImageBounds
	 */
	private void Label_mouseReleased(java.awt.event.MouseEvent event)
	{
		//end drag
		isDragged = false;
		setFocus(false);

		//bounded component is other than AImage
		if(focused!=null && !focused.getClass().getName().equals("AImage"))
		{
			//set bounds accordingly
			focused.setBounds(getBounds().x+3,getBounds().y+3,getBounds().width-6,getBounds().height-6);
		}

		//bounded component is AImage
		if(focused!=null && focused.getClass().getName().equals("AImage"))
		{
			//remember the mouse released position
			switch(draggingEvent)
			{
				case ConnectComponent.NORTH_WEST:
					released.x = -getX();
					released.y = -getY();
					break;
				case ConnectComponent.NORTH_EAST:
					released.x = event.getX();
					released.y = -getY();
					break;
				case ConnectComponent.SOUTH_WEST:
					released.x = -getX();
					released.y = event.getY();
					break;
				case ConnectComponent.SOUTH_EAST:
					released.x = event.getX();
					released.y = event.getY();
					break;
				case ConnectComponent.NORTH:
					released.x = event.getX();
					released.y = -getY();
					break;
				case ConnectComponent.SOUTH:
					released.x = event.getX();
					released.y = event.getY();
					break;
				case ConnectComponent.EAST:
					released.x = event.getX();
					released.y = event.getY();
					break;
				case ConnectComponent.WEST:
					released.x = -getX();
					released.y = -getY();
					break;
			}

			//calculate the difference between pressed and released points
			diff.x = released.x-pressed.x;
			diff.y = released.y-pressed.y;

			//scales and sets the image component's bound
			Point eventLocation = new Point(event.getX(), event.getY());
			setImageBounds(diff,eventLocation);
		}
	}

	/**
	 * Determines the dragging event and check whether the
	 * mouse is on component's border
	 */
	private void Label_mouseMoved(java.awt.event.MouseEvent event)
	{
		//get the focused component
		focused = arangam.slideshow1.getCurrentSlide().focused;

		Rectangle myBounds = getBounds();

		//initialise mouse moved direction
		boolean isNorth = false,
		isSouth = false,
		isWest = false,
		isEast = false,
		isEdgeMiddle = false;
		isBorder = false;

		//update ruler coordinates
		arangam.actionsImpl1.setRulerXY(getX()+event.getX(), getY()+event.getY());

		//initialise dragging event
		draggingEvent = ConnectComponent.EDIT;

		Cursor cur =new Cursor(Cursor.TEXT_CURSOR);

		//set text cursor
		setCursor(cur);

		//update mouse direction
		if( Math.abs(event.getPoint().getY()-myBounds.height) < BOUNDS ||
			Math.abs(event.getPoint().getY()) < BOUNDS)
		{
			isBorder = true;
			if (Math.abs(event.getPoint().getY()) < BOUNDS)
			{
				isNorth = true;
			}
			else
			{
				isSouth = true;
			}
		}

		//update mouse direction
		if( Math.abs(event.getPoint().getX()-myBounds.width) < BOUNDS ||
			Math.abs(event.getPoint().getX()) < BOUNDS)
		{
			isBorder = true;
			if (Math.abs(event.getPoint().getX()) < BOUNDS)
			{
				isWest = true;
			}
			else
			{
				isEast = true;
			}
		}

		//update mouse direction
		//if focused, can also be scaled
		if ((isNorth || isSouth) &&
				(Math.abs(event.getPoint().getX() - getWidth()/2) < BOUNDS))
		{
			isEdgeMiddle = true;
		}
		else if ((isEast || isWest) &&
				(Math.abs(event.getPoint().getY() - getHeight()/2) < BOUNDS))
		{
			isEdgeMiddle = true;
		}


		//update drag event from mouse direction
		if (isNorth && isEast)
		{
			cur = new Cursor(Cursor.NE_RESIZE_CURSOR);
			draggingEvent = ConnectComponent.NORTH_EAST;
		}
		else if (isNorth && isWest)
		{
			cur = new Cursor(Cursor.NW_RESIZE_CURSOR);
			draggingEvent = ConnectComponent.NORTH_WEST;
		}
		else if (isSouth && isEast)
		{
			cur = new Cursor(Cursor.SE_RESIZE_CURSOR);
			draggingEvent = ConnectComponent.SOUTH_EAST;
		}
		else if (isSouth && isWest)
		{
			cur = new Cursor(Cursor.SW_RESIZE_CURSOR);
			draggingEvent = ConnectComponent.SOUTH_WEST;
		}
		else if (isNorth && isEdgeMiddle)
		{
			cur = new Cursor(Cursor.N_RESIZE_CURSOR);
			draggingEvent = ConnectComponent.NORTH;
		}
		else if (isSouth && isEdgeMiddle)
		{
			cur = new Cursor(Cursor.S_RESIZE_CURSOR);
			draggingEvent = ConnectComponent.SOUTH;
		}
		else if (isEast && isEdgeMiddle)
		{
			cur = new Cursor(Cursor.E_RESIZE_CURSOR);
			draggingEvent = ConnectComponent.EAST;
		}
		else if (isWest && isEdgeMiddle)
		{
			cur = new Cursor(Cursor.W_RESIZE_CURSOR);
			draggingEvent = ConnectComponent.WEST;
		}
		else if (isBorder)
		{
			draggingEvent = ConnectComponent.MOVE;
			cur = new Cursor(Cursor.MOVE_CURSOR);
		}
		setCursor(cur);
	}//Label_mouseMoved

	/**
	 * Resize the component
	 */
	private void Label_mouseDragged(java.awt.event.MouseEvent event)
	{
		isDragged = true;
		Point eventLocation = new Point(event.getX(), event.getY());

		//get bounds
		Rectangle myBounds = getBounds();
		Rectangle slideBounds = getParent().getBounds();

		//update bounds according to drag event
		switch (draggingEvent)
		{
			case ConnectComponent.NORTH_WEST:
				myBounds.setLocation(myBounds.x+eventLocation.x,
					myBounds.y+eventLocation.y);
				myBounds.setSize(myBounds.width-eventLocation.x,
					myBounds.height-eventLocation.y);
				break;
			case ConnectComponent.NORTH_EAST:
				myBounds.setLocation(myBounds.x, myBounds.y+eventLocation.y);
				myBounds.setSize(eventLocation.x, myBounds.height-eventLocation.y);
				break;
			case ConnectComponent.SOUTH_WEST:
				myBounds.setLocation(myBounds.x+eventLocation.x, myBounds.y);
				myBounds.setSize(myBounds.width-eventLocation.x, eventLocation.y);
				break;
			case ConnectComponent.SOUTH_EAST:
				myBounds.setSize(eventLocation.x, eventLocation.y);
				break;
			case ConnectComponent.NORTH:
				myBounds.setLocation(myBounds.x, myBounds.y+eventLocation.y);
				myBounds.setSize(myBounds.width, myBounds.height-eventLocation.y);
				break;
			case ConnectComponent.SOUTH:
				myBounds.setSize(myBounds.width, eventLocation.y);
				break;
			case ConnectComponent.EAST:
				myBounds.setSize(eventLocation.x, myBounds.height);
				break;
			case ConnectComponent.WEST:
				myBounds.setLocation(myBounds.x+eventLocation.x, myBounds.y);
				myBounds.setSize(myBounds.width-eventLocation.x, myBounds.height);
				break;
			case ConnectComponent.MOVE:
				myBounds.setLocation(
				myBounds.x + eventLocation.x - draggingLocation.x,
				myBounds.y + eventLocation.y - draggingLocation.y);
				break;
		}//switch

		//can't be in size zero
		if ((myBounds.height <= 15) || (myBounds.width <= 15))
			return;
		//accept event if only at least one corner is inside the screen
		if ((slideBounds.contains(myBounds.x, myBounds.y)) ||
				(slideBounds.contains(myBounds.x+myBounds.width, myBounds.y)) ||
				(slideBounds.contains(myBounds.x, myBounds.y+myBounds.height)) ||
				(slideBounds.contains(
			myBounds.x+myBounds.width, myBounds.y+myBounds.height)))
		{
			setBounds(myBounds);
		}

		//update ruler coordinates
		arangam.actionsImpl1.setRulerXY(getX(), getY());
	}

	/**
	 * Draws the notches and bounding rectangle on border for scaling
	 * and moving
	 * @param graphics the <code>Graphics</code> context in which to paint.
	 */
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D)g;

		//the array representing the dashing pattern
		float dash1[] = {5.0f};

		//create a stroke style with the above attributes
		BasicStroke basicStroke = new BasicStroke(
			0.1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
			5.0f, dash1, 0.0f);

		//set it to graphics
		g2D.setStroke(basicStroke);
		g2D.setColor(Color.darkGray);

		//get focused component
		ConnectComponent focusedComp =
			arangam.slideshow1.getCurrentSlide().focused;

		//if some component is focused
		if(focusedComp != null)

		//drag event
		if(isDragged)
		{
			g2D.drawRect(3, 3, getSize().width-7, getSize().height-7);
		}

		//normal
		else
		{
			Rectangle myBounds = getBounds();
			int 	width = myBounds.width,
			height = myBounds.height,
			widthWise = (int)width/2,
			heightWise = (int)height/2;

			//4 Corners
			g.fillRect(0,0,4,4);
			g.fillRect(width-4,height-4,4,4);
			g.fillRect(width-4,0,4,4);
			g.fillRect(0,height-4,4,4);

			//widthWise
			g.fillRect(widthWise-2,0,4,4);
			g.fillRect(widthWise-2,height-4,4,4);

			//heightWise
			g.fillRect(0,heightWise-2,4,4);
			g.fillRect(width-4,heightWise-2,4,4);
			g2D.drawRect(0, 0, getSize().width-1, getSize().height-1);
		}
	}

	/**
	 * Connect the class to the main class.
	 * @param arangam main class reference.
	 */
	public void getInterface(Arangam arangam)
	{
		this.arangam = arangam;
	}

	/**
	 * Add or remove focus from this component.
	 * @param focus <code>true</code> if the component has to be focused.
	 */
	public void setFocus(boolean focus)
	{
		if(focus)
		{
			setVisible(true);
		}
		else
		{
			setVisible(false);
		}
	}

	/**
	 * Returns the bounding rectangle of the component.
	 * @return a rectangle indicating this component's bounds.
	 */
	public Rectangle getBounds()
	{
		return super.getBounds();
	}

	/**
	 * Checks the opaque property of the component.
	 * @return <code>true</code> if this component is opaque
	 */
	public boolean isOpaque()
	{
		return false;
	}

	/**
	 * Sets the bound for the component.
	 * @param x the new x-coordinate of this component
	 * @param y the new y-coordinate of this component
	 * @param width the new width of this component
	 * @param height the new height of this component
	 */
	public void setBounds(int x,int y,int w,int h)
	{
		super.setBounds(x,y,w,h);
	}

	/**
	 * Shows or hides this component depending on the value of parameter b.
	 * @param b if <code>true</code>, shows this component; otherwise, hides this component
	 */
	public void setVisible(boolean b)
	{
		super.setVisible(b);
	}

	/**
	 * Scales and sets the image component's bound
	 * @param diff the difference between the mouse pressed and released
	 * position
	 * @param eventLocation mouse released location
	 */
	public void setImageBounds(Point diff,Point eventLocation)
	{
		Rectangle originalBounds = null;

		//get focused component
		focused = arangam.slideshow1.getCurrentSlide().focused;

		//if an image is focused
		if(focused != null)
		{
			//get bounds
			myBounds = focused.getBounds();
			originalBounds = focused.getBounds();
			slideBounds = parentSlide.getBounds();

			//initialise images and icon
			Image tempImage = ((ConnectImage)focused).getOriginalImageIcon().getImage();
			Image scaledImage = ((ConnectImage)focused).getOriginalImageIcon().getImage();
			ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

			//to get size of current image
			int w1 = ((ImageIcon)	((ConnectImage)focused)
				.getIcon()).getIconWidth();
			int h1 = ((ImageIcon)	((ConnectImage)focused)
				.getIcon()).getIconHeight();

			//drag event is scale
			if(draggingEvent != MOVE)
			{
				//get the scaled instance
				scaledImage = tempImage.getScaledInstance
					(w1+diff.x,h1+diff.y,Image.SCALE_AREA_AVERAGING);

				//create an icon with it
				scaledImageIcon = new ImageIcon(scaledImage);

				//new size
				w1 = w1+diff.x;
				h1 = h1+diff.y;

				//set the new icon
				((ConnectImage)focused).setIcon(scaledImageIcon);
			}

			//drag event is move
			//update bounds
			switch (draggingEvent)
			{
				case ConnectComponent.NORTH_WEST:
					myBounds.setLocation(myBounds.x+eventLocation.x,
					myBounds.y+eventLocation.y);
					break;
				case ConnectComponent.NORTH_EAST:
					myBounds.setLocation(myBounds.x, myBounds.y+eventLocation.y);
					break;
				case ConnectComponent.SOUTH_WEST:
					myBounds.setLocation(myBounds.x+eventLocation.x, myBounds.y);
					break;
				case ConnectComponent.SOUTH_EAST:
					break;
				case ConnectComponent.NORTH:
					myBounds.setLocation(myBounds.x, myBounds.y+eventLocation.y);
					break;
				case ConnectComponent.SOUTH:
					break;
				case ConnectComponent.EAST:
					break;
				case ConnectComponent.WEST:
					myBounds.setLocation(myBounds.x+eventLocation.x, myBounds.y);
					break;
				case ConnectComponent.MOVE:
					myBounds.setLocation(
					myBounds.x + eventLocation.x - draggingLocation.x,
					myBounds.y + eventLocation.y - draggingLocation.y);
					((ConnectImage)focused).setLocation(myBounds.x,myBounds.y);
					break;
			}

			//can't be in size zero
			if ((myBounds.height <= 15) || (myBounds.width <= 15))
				return;

			//accept event if only at least one corner is inside the screen
			if ((slideBounds.contains(myBounds.x, myBounds.y)) ||
					(slideBounds.contains(myBounds.x+myBounds.width, myBounds.y)) ||
					(slideBounds.contains(myBounds.x, myBounds.y+myBounds.height)) ||
					(slideBounds.contains(
					myBounds.x+myBounds.width, myBounds.y+myBounds.height)))
			{
				if(draggingEvent != ConnectComponent.MOVE)
				{
					setSize(w1,h1);
				}
				else
				{
					setSize(originalBounds.width,originalBounds.height);
				}
				((ConnectImage)focused).setBounds(getBounds().x+3,getBounds().y+3,
					getBounds().width-6,getBounds().height-6);
			}

			//update ruler coordinates
			arangam.actionsImpl1.setRulerXY(getX(), getY());
			repaint();
		}
	}

	//other implementations of ConnectComponent interface. Not used because
	//this is a bounding component

	/**
	 * Set the "position" variable to the relative position of the
	 * component within its layer.
	 * @param position the components position to set
	 */
	public void setP(int position)
	{
		//do nothing
	}

	/**
	 * Set position for pasting components from clipboard
	 */
	public void setPastePosition()
	{
		//do nothing
	}

	/**
	 * Sets the border of this component
	 * @param border the border to be rendered for this component
	 */
	public void setBorder(Border border)
	{
		//do nothing
	}

	/**
	 * Enables or disables editing components in <code>Arangam</code>
	 * @param editable <code>true</code> if this application should be editable, <code>false</code> otherwise
	 */
	public void setArangamEditable(boolean editable)
	{
		//do nothing
	}

	/**
	 * Remove this from the <code>Slide</code>
	 */
	public void removeFromSlide()
	{
		//do nothing
	}

	/**
	 * If true the component paints every pixel within its bounds.
	 * Otherwise, the component may not paint some or all of its pixels,
	 * allowing the underlying pixels to show through.
	 * @param isOpaque <code>true</code> if this component should be opaque
	 */
	public void setOpaque(boolean isOpaque)
	{
		//do nothing
	}

	/**
	 * Gets the location of this component in the form of a point.
	 * @return an instance of <code>Point</code> representing the top-left
	 * corner of the component's bounds.
	 */
	public Point getLocation()
	{
		return null;
	}
}

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 * Basically a label, enables resizing and moving of images.
 * Supports jpeg and gif format of pictures
 */
public class AImage extends javax.swing.JLabel implements ConnectImage
{
	/*** Brder line style */
	private int lineStyle;

	/*** Border line width */
	private int lineWidth;

	/*** Main class reference */
	private transient Arangam arangam;

	/*** <code>Slideshow</code> in which this is added */
	private Slide parentSlide;

	/*** Can be edited (including dragging) */
	private boolean isArangamEditable = true;

	/*** Focus is on this component */
	private boolean isFocused = true;

	/*** Width of image */
	private int	imageWidth;

	/*** Height of image */
	private int	imageHeight;

	/**
	 * Remembers the position of this component and
	 * place this component in position "one" temporary while editing
	 */
	private int position = -2;

	/**
	 * Original image icon.Not compressed or expanded.
	 * Used for scaling
	 */
	public ImageIcon originalImageIcon;

	/**
	 * Constructs a new <code>AImage</code> and connects it to a <code>Slide</code>
	 * and to the application(<code>Arangam</code>)
	 * @param Slide the <code>Slide</code> that contains this <code>AImage</code>
	 * @param arangam main class reference
	 * @param img the image to be set for this component
	 * @see Slide
	 * @see Arangam
	 */
	public AImage(Slide aSlide, Arangam arangam,Image img)
	{
		this.arangam = arangam;
		this.parentSlide = aSlide;

		//scaling depends on parent component
		double pX=(float)(Arangam.A_WIDTH-10)/Arangam.SLIDESHOW_WIDTH;
		double pY=(float)(Arangam.A_HEIGHT-30)/Arangam.SLIDESHOW_HEIGHT;

		//get the image and set it to label
		ImageIcon imgIcon=new ImageIcon(img);
		originalImageIcon=new ImageIcon(img);
		imageWidth=imgIcon.getIconWidth();
		imageHeight=imgIcon.getIconHeight();
		setIcon(imgIcon);
		lineWidth = 1;

		//set bounds
		setLocation(Arangam.pasteCompLocation);
		setSize(new Dimension(imageWidth,imageHeight));

		//scale the image
		setBounds1((int)((double)getBounds().x/pX),
				(int)((double)getBounds().y/pY),
				(int)((double)getBounds().width/pX),
				(int)((double)getBounds().height/pY));

		//set bounds for the temporary component(ABoundLabel)
		//this is the bounding component for AImage
		Rectangle bounds = getBounds();
		parentSlide.aBoundLabel.setBounds(bounds.x-3,bounds.y-3,
			bounds.width+6,bounds.height+6);
		parentSlide.aBoundLabel.setVisible(true);

		//add to parent (Slide)
		parentSlide.add(this);
		//focus this component
		parentSlide.focusComponent(this);
		//register listeners
		registerListeners();
	}

	/**
	 * Used when pasting image from <code>clipboard</code>
	 * Constructs a new </code>AImage and connects it to a <code>Slide</code>
	 * and to the application(<code>Arangam</code>).
	 * @param Slide the <code>Slide</code> that contains this <code>Image</code>
	 * @param arangam main class reference
	 * @param img the image to be set for this component
	 * @param border the border of the <code>Image</code> in clipboard
	 * @see Slide
	 * @see Arangam
	 * @see Arangam#clipboard
	 */
	public AImage(Slide aSlide, Arangam arangam, Image img, Border border)
	{
		this.arangam = arangam;
		this.parentSlide = aSlide;
		setBorder(border);

		//scaling depends on parent component
		double pX=(float)(Arangam.A_WIDTH-10)/Arangam.SLIDESHOW_WIDTH;
		double pY=(float)(Arangam.A_HEIGHT-30)/Arangam.SLIDESHOW_HEIGHT;

		//get the image and set it to label
		ImageIcon imgIcon=new ImageIcon(img);
		originalImageIcon=new ImageIcon(img);
		imageWidth=imgIcon.getIconWidth();
		imageHeight=imgIcon.getIconHeight();
		setIcon(imgIcon);

		//set bounds
		setLocation(Arangam.pasteCompLocation);
		setSize(new Dimension(imageWidth,imageHeight));

		//set bounds for the temporary component(ABoundLabel)
		//this is the bounding component for AImage
		Rectangle bounds = getBounds();
		parentSlide.aBoundLabel.setBounds(bounds.x-3,bounds.y-3,
			bounds.width+6,bounds.height+6);
		parentSlide.aBoundLabel.setVisible(true);

		//add to parent (Slide)
		parentSlide.add(this);
		//focus this component
		parentSlide.focusComponent(this);
		//register listeners
		registerListeners();
	}

	/**
	 * Register mouse listeners
	 */
	public void registerListeners()
	{
		SymMouseMotion aSymMouseMotion = new SymMouseMotion();
		addMouseMotionListener(aSymMouseMotion);
		SymMouse aSymMouse = new SymMouse();
		addMouseListener(aSymMouse);
	}

	/**
	 * MouseMotionAdapter for the component.
	 * Just to update the ruler on mouse move and drag
	 * @see Rule#setX
	 * @see Rule#setY
	 */
	private class SymMouseMotion extends java.awt.event.MouseMotionAdapter
	{
		/**
		 * Update the ruler on mouse move
		 */
		public void mouseMoved(java.awt.event.MouseEvent event)
		{
			if (isArangamEditable)
			{
				arangam.actionsImpl1.setRulerXY(getX()+event.getX(), getY()+event.getY());
			}
		}

		/**
		 * Update the ruler on mouse drag
		 */
		public void mouseDragged(java.awt.event.MouseEvent event)
		{
			if (isArangamEditable)
			{
				arangam.actionsImpl1.setRulerXY(getX()+event.getX(), getY()+event.getY());
			}
		}
	}

	/**
	 * MouseAdapter for the component.
	 * Focus the component when the mouse is pressed or
	 * popup the components menu
	 */
	private class SymMouse extends java.awt.event.MouseAdapter
	{
		/**
		 * Invoked when mouse pressed
		 */
		public void mousePressed(java.awt.event.MouseEvent event)
		{
			//if popup action is triggered
			if (SwingUtilities.isRightMouseButton(event))
			{
				//popup the components menu if the Slide is in "editing" mode
				if(isArangamEditable)
				{
					ImagePopup imagePopup = new ImagePopup(AImage.this,arangam,parentSlide);
					imagePopup.show(event.getComponent(),event.getX(),event.getY());
				}
				//popup the show menu
				else
				{
					SlideshowPopup slideshowPopup = new SlideshowPopup(parentSlide,arangam);
					slideshowPopup.show(event.getComponent(),event.getX(),event.getY());
				}
			}
			//focus the component if the Slide is in "editing" mode
			Object object = event.getSource();
			if ((object == AImage.this) && (isArangamEditable))
				parentSlide.focusComponent(AImage.this);
		}
	}

	/**
	 * Draws border of the component
	 * @param graphics the <code>Graphics</code> context in which to paint.
	 */
	public void paint(Graphics graphics)
	{
		super.paint(graphics);

		Graphics2D graphics2D = (Graphics2D)graphics;

		//the array representing the dashing pattern
		float dash[] = null;

		//initialise the dash array for different shape
		switch (lineStyle)
		{
			case 1 :
				dash = new float[2];
				dash[0] = 2.0f;
				dash[1] = 5.0f;
				break;
			case 2 :
				dash = new float[1];
				dash[0] = 5.0f;
				break;
			case 3 :
				dash = new float[2];
				dash[0] = 12.0f;
				dash[1] = 5.0f;
				break;
			case 4 :
				dash = new float[4];
				dash[0] = 12.0f;
				dash[1] = 6.0f;
				dash[2] = 5.0f;
				dash[3] = 6.0f;
				break;
			case 5 :
				dash = new float[2];
				dash[0] = 20.0f;
				dash[1] = 6.0f;
				break;
			case 6 :
				dash = new float[4];
				dash[0] = 20.0f;
				dash[1] = 6.0f;
				dash[2] = 5.0f;
				dash[3] = 6.0f;
				break;
			case 7 :
				dash = new float[6];
				dash[0] = 20.0f;
				dash[1] = 6.0f;
				dash[2] = 5.0f;
				dash[3] = 6.0f;
				dash[4] = 5.0f;
				dash[5] = 6.0f;
				break;
		}

		//create a stroke style with the above attributes
		BasicStroke stroke;
		if (lineStyle == 0)
			stroke = new BasicStroke(lineWidth);
		else
			stroke = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

		//set it to graphics
		graphics2D.setStroke(stroke);

		//no need to draw boxes on "sorter" and "show" view
		if(arangam.viewMode != arangam.SLIDE_VIEW)
			return;

		//draw the dashed line if the component has focus
		if(isFocused)
		{
			float dash1[] = {5.0f};
			BasicStroke basicStroke = new BasicStroke(
				0.1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
				5.0f, dash1, 0.0f);
			graphics2D.setStroke(basicStroke);
			graphics2D.setColor(Color.darkGray);
			graphics2D.drawRect(0, 0, getSize().width-1, getSize().height-1);
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
	 * Enables or disables editing components in <code>Arangam</code>
	 * @param editable <code>true</code> if this application should be editable, <code>false</code> otherwise
	 */
	public void setArangamEditable(boolean editable)
	{
		isArangamEditable = editable;
		setEnabled(editable);
		if (!editable)
		{
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			setFocus(false);
		}
	}

	/**
	 * Add or remove focus from this component.
	 * @param focus <code>true</code> if the component has to be focused.
	 */
	public void setFocus(boolean focus)
	{
		isFocused = focus;
		setEnabled(true);

		if (focus)
		{
			//if this has to be focused, bring it to front temporarily
			position=parentSlide.getPosition(AImage.this);
			parentSlide.setPosition(AImage.this,0);
			parentSlide.aBoundLabel.setBounds(
				getLocation().x-3,
				getLocation().y-3,
				getBounds().width+6,
				getBounds().height+6);
			//bounding component
			parentSlide.aBoundLabel.setVisible(true);
			//focus this
			requestFocus();
			setOpaque(true);

			//to paste component in this position
			setPastePosition();
		}
		else
		{
			//else bring it back to original position
			parentSlide.setPosition(this,position);
			parentSlide.aBoundLabel.setVisible(false);
			setOpaque(false);
		}
		parentSlide.repaint();

		//mark Slideshow as modified
		arangam.slideModified = true;
	}

	/**
	 * Remove this from the <code>Slide</code>
	 */
	public void removeFromSlide()
	{
		parentSlide.remove(this);
		parentSlide.focusComponent(null);
	}

	/**
	 * If true the component paints every pixel within its bounds.
	 * Otherwise, the component may not paint some or all of its pixels,
	 * allowing the underlying pixels to show through.
	 * @param isOpaque <code>true</code> if this component should be opaque
	 */
	public void setOpaque(boolean isOpaque)
	{
		super.setOpaque(isOpaque);
	}

	/**
	 * Sets the border of this component
	 * @param border the border to be rendered for this component
	 */
	public void setBorder(Border border)
	{
		super.setBorder(border);
	}

	/**
	 * Returns the graphic image (glyph, icon) that the label displays
	 * @return an Icon
	 */
	public Icon getIcon()
	{
		return super.getIcon();
	}

	/**
	 * Defines the icon this component will display. If the value of icon
	 * is null, nothing is displayed
	 * @param icon the icon to set
	 */
	public void setIcon(Icon icon)
	{
		super.setIcon(icon);
	}

	/**
	 * Moves and resizes
	 * this component to confirm to the new bounding rectangle <code>r</code>
	 * @param r the new bounding rectangle for this component
	 */
	public void setBounds(Rectangle r)
	{
		super.setBounds(r);
	}

	/**
	 * Makes the component visible or invisible
	 * @param flag <code>true</code> to make the component visible;
	 * false to make it invisible
	 */
	public void setVisible(boolean flag)
	{
		super.setVisible(flag);
	}

	/**
	 * Resizes this component so that it has width <code>w</code> and height <code>h</code>
	 * @param w width
	 * @param h height
	 */
	public void setSize(int w,int h)
	{
		super.setSize(w,h);
	}

	/**
	 * Moves this component to a new location. The top-left corner of the
	 * new location is specified by the <code>x</code> and <code>y</code> parameters in the
	 * coordinate space of this component's parent
	 * @param x the x-coordinate of the new location's
	 * top-left corner in the parent's coordinate space
	 * @param y the y-coordinate of the new
	 * location's top-left corner in the parent's coordinate space
	 */
	public void setLocation(int x,int y)
	{
		super.setLocation(x,y);
	}

	/**
	 * Set bounds. Invoked when scaling
	 * @param diff the difference between mouse pressed and released point
	 * @param eventLocation mouse pressed point
	 * @param draggingLocation mouse dragged point
	 * @param draggingEvent drag event direction
	 */
	public void setBounds(Point diff, Point eventLocation, Point draggingLocation,
							int draggingEvent)
	{
		//bounding rectangle
		Rectangle myBounds  = getBounds();

		//bounding rectangle of parent (Slide)
		Rectangle slideBounds  = getParent().getBounds();

		//get icon
		Image tempImage=((ImageIcon)getIcon()).getImage();
		Image scaledImage=((ImageIcon)getIcon()).getImage();
		ImageIcon scaledImageIcon=new ImageIcon(scaledImage);

		//get size
		imageWidth=scaledImageIcon.getIconWidth();
		imageHeight=scaledImageIcon.getIconHeight();

		//scaling event
		if(draggingEvent!=ConnectComponent.MOVE)
		{
			//scale the image and set
			scaledImage =tempImage.getScaledInstance(imageWidth+diff.x,imageHeight+diff.y,Image.SCALE_AREA_AVERAGING);
			scaledImageIcon=new ImageIcon(scaledImage);
			imageWidth=imageWidth+diff.x;
			imageHeight=imageHeight+diff.y;
			setIcon(scaledImageIcon);
		}

		//update mybounds according to dragging event
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
				setBounds(myBounds);
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
			setSize(imageWidth,imageHeight);
		}

		//update ruler coordinates
		arangam.actionsImpl1.setRulerXY(getX(), getY());
		repaint();
	}

	/**
	 * Moves and resizes this component. The new location of the
	 * top-left corner is specified by <code>x</code> and <code>y</code>, and the new size is
	 * specified by width and height
	 * @param x the new x-coordinate of this component
	 * @param y the new y-coordinate of this component
	 * @param w the new width of this component
	 * @param h the new height of this component
	 */
	public void setBounds(int x,int y,int w,int h)
	{
		super.setBounds(x,y,w,h);
	}

	/**
	 * Moves and resizes this component. The new location of the
	 * top-left corner is specified by <code>x</code> and <code>y</code>, and the new size is
	 * specified by width and height. Called when scaling
	 * @param x the new x-coordinate of this component
	 * @param y the new y-coordinate of this component
	 * @param w the new width of this component
	 * @param h the new height of this component
	 */
	public void setBounds1(int x,int y,int w,int h)
	{
		if(w!=0 && h!=0) //can't scale with 0 as parameters
		{
			Image tempImage=((ImageIcon)getIcon()).getImage();
			Image scaledImage=((ImageIcon)getIcon()).getImage();
			ImageIcon scaledImageIcon=new ImageIcon(scaledImage);
			scaledImage =tempImage.getScaledInstance(w,h,Image.SCALE_AREA_AVERAGING);
			scaledImageIcon=new ImageIcon(scaledImage);
			setIcon(scaledImageIcon);
			setBounds(x,y,scaledImageIcon.getIconWidth(),
				scaledImageIcon.getIconHeight());
			repaint();
		}
	}

	/**
	 * Original image icon. Not compressed or expanded.
	 * Used for scaling
	 */
	public ImageIcon getOriginalImageIcon()
	{
		return originalImageIcon;
	}

	/**
	 * Set the "position" variable to the relative position of the
	 * component within its layer.
	 * @param position the components position to set
	 */
	public void setP(int position)
	{
		this.position=position;
	}

	/**
	 * Set the line width to <code>width</code>
	 * @param width the width of the line
	 */
	public void setLineWidth(int width)
	{
		lineWidth = width;
		repaint();
	}

	/**
	 * Set the line style to <code>style</code>
	 * @param style the style of the line
	 */
	public void setLineStyle(int style)
	{
		lineStyle = style;
		repaint();
	}

	/**
	 * Gets the line width
	 * @return the width of the line
	 */
	public int getLineWidth()
	{
		return lineWidth;
	}

	/**
	 * Gets the line style
	 * @return the style of the line
	 */
	public int getLineStyle()
	{
		return lineStyle;
	}

	/**
	 * Set position for pasting components from <code>clipboard</code>
	 */
	public void setPastePosition()
	{
		//increment by 10 in both direction
		int x = getX()+xInc;
		int y = getY()+yInc;

		int w = getWidth();
		int h = getHeight();
		Rectangle slideBounds = getParent().getBounds();

		//if it is ok, set it
		if ((slideBounds.contains(x, y)) ||
			(slideBounds.contains(x+w, y)) ||
			(slideBounds.contains(x, y+h)) ||
			(slideBounds.contains(x+w, y+h)))
		{
			Arangam.pasteCompLocation.x = x;
			Arangam.pasteCompLocation.y = y;
		}
		//else undo it
		else
		{
			Arangam.pasteCompLocation.x = x-xInc;
			Arangam.pasteCompLocation.y = y-yInc;
		}
	}

}

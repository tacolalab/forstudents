import java.awt.*;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.text.Caret;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.border.Border;

/**
 * Shape component enables editing of text inside a shape
 */
public class  AShape extends javax.swing.JLayeredPane implements ConnectShape
{
	/*** Shape type constants */
	final static int DIAMOND			= 0,
					OVAL				= 1,
					RECTANGLE			= 2,
					ROUND_RECTANGLE		= 3,
					SQUARE				= 4,
					RIGHT_TRIANGLE		= 6,
					ISOCELES_TRIANGLE	= 7;

	/*** Main class reference */
	private transient Arangam arangam;

	/*** <code>Slideshow</code> in which this is added */
	private Slide parentSlide;

	/*** Can be edited (including dragging) */
	boolean isArangamEditable = true;

	/*** Focus is on this component */
	boolean isFocused = true;

	/*** Text editing component */
	TextEditor textPane;

	/*** Label component */
	ShapeLabel label;

	/*** Shape type */
	int shapeType;

	/*** Outline style */
	int lineStyle;

	/*** Outline width */
	int lineWidth;

	/**
	 * Remembers the position of this component and
	 * place this component in position one temporary while editing
	 */
	int position=-2;

	/**
	 * Constructs a new shape component and connects it to a <code>Slide</code>
	 * and to the application(<code>Arangam</code>).
	 * @param aSlide the <code>Slide</code> that contains this <code>AShape</code> component
	 * @param arangam main class reference
	 * @param shapeType the shape type
	 * @see Slide
	 * @see Arangam
	 * @see TextEditor
	 */
	public AShape(Slide aSlide, Arangam arangam,int shapeType)
	{
		this.arangam = arangam;
		this.parentSlide = aSlide;
		this.shapeType = shapeType;

		//default line style and width
		lineWidth = 1;
		lineStyle = 0;

		//set bounds
		setLocation(Arangam.pasteCompLocation);
		setSize(100,100);

		//construct a label and text editing component
		int w=getSize().width;
		int h=getSize().height;
		label= new ShapeLabel(w,h);
		textPane = new TextEditor(w,h);

		//add them
		add(textPane);
		add(label);
		textPane.setBackground(label.bgColor);

		//set bounds for the temporary component(ABoundLabel)
		//this is the bounding component for AImage
		Rectangle r=getBounds();
		Point p=getLocation();
		parentSlide.aBoundLabel.setBounds(p.x-3,p.y-3,r.width+6,r.height+6);

		//add to parent (Slide)
		parentSlide.add(this);
		//focus this component
		parentSlide.focusComponent(this);
		//register listeners
		registerListeners();
	}

	/**
	 * Used when pasting shape from <code>clipboard</code>
	 * Constructs a new shape component and connects it to a <code>Slide</code>
	 * and to the application(<code>Arangam</code>).
	 * @param aSlide the <code>Slide</code> that contains this <code>AShape</code> component
	 * @param arangam main class reference
	 * @param shapeType the shape type
	 * @param linewidth the outline width
	 * @param size the dimension of this component
	 * @param st StyledText, text with different styles
	 * @param bgColor the background color
	 * @param outlineColor the border color
	 * @see Slide
	 * @see Arangam
	 * @see TextEditor
	 */
	public AShape(Slide aSlide, Arangam arangam,int shapeType,
						int lineWidth,int lineStyle,Dimension size,
						StyledText styledText,Color bgColor,Color outlineColor)
	{
		this.arangam = arangam;
		this.parentSlide = aSlide;
		this.shapeType = shapeType;
		this.lineWidth = lineWidth;
		this.lineStyle = lineStyle;

		//set bounds
		setLocation(Arangam.pasteCompLocation);
		setSize(size);

		//construct a label and text editing component
		int w=getSize().width;
		int h=getSize().height;
		label= new ShapeLabel(w,h);
		textPane = new TextEditor(w,h,styledText);

		//add them
		add(textPane);
		add(label);

		setBackground(bgColor);
		setOutlineColor(outlineColor);

		//set bounds for the temporary component(ABoundLabel)
		//this is the bounding component for AImage
		Rectangle r=getBounds();
		Point p=getLocation();
		parentSlide.aBoundLabel.setBounds(p.x-3,p.y-3,r.width+6,r.height+6);

		//add to parent (Slide)
		parentSlide.add(this);
		//focus this component
		parentSlide.focusComponent(this);
		//register listeners
		registerListeners();
	}

	/**
	 * Register mouse and other listeners
	 * @see AShape.SymMouse
	 * @see TextEditor#registerListeners
     */
	public void registerListeners()
	{
		SymMouse aSymMouse = new SymMouse();
		addMouseListener(aSymMouse);
		textPane.registerListeners();
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
		 * Focus the component on mouse pressed
		 * @see Slide#focusComponent
		 */
		public void mousePressed(java.awt.event.MouseEvent event)
		{
			if (!isFocused && isArangamEditable)
			{
				super.mousePressed(event);
				parentSlide.focusComponent(AShape.this);
			}
		}

		/**
		 * Invoked when mouse released
		 * Shows popup menu on right click
		 * @see ShapePopup
		 * @see SlideshowPopup
		 */
		public void mouseReleased(java.awt.event.MouseEvent event)
		{
			//right click
			if(SwingUtilities.isRightMouseButton(event))
			{
				//"editing" mode - popup menu for text
				if(isArangamEditable)
				{
					ShapePopup shapePopup = new ShapePopup(AShape.this,arangam,parentSlide);
					shapePopup.show(event.getComponent(),event.getX(),event.getY());
				}
				//else - popup menu for Slide
				else
				{
					SlideshowPopup slideshowPopupMenu = new SlideshowPopup(parentSlide,arangam);
					slideshowPopupMenu.show(event.getComponent(),event.getX(),event.getY());
				}
			}
		}
	}

	/**
	 * Draws bounding rectangle when focused.
	 * @param graphics the <code>Graphics</code> context in which to paint.
	 */
	public void paint(Graphics g)
	{
		super.paint(g);

		//no need to draw boxes on "sorter" and "show" view
		if (arangam.viewMode != arangam.SLIDE_VIEW)
			return;

		//draw rectangular boxes
		if (isFocused)
		{
			Graphics2D g2D = (Graphics2D)g;
			float dash1[] = {5.0f};
			BasicStroke basicStroke = new BasicStroke(
			0.1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
			10.0f, dash1, 0.0f);
			g2D.setStroke(basicStroke);
			g2D.setColor(Color.darkGray);
			g2D.drawRect(0, 0, getSize().width-1, getSize().height-1);
			label.setSize(getSize());
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
	 * Enables or disables editing components in Arangam
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
	 * Apply or remove focus from this component
	 * @param focus <code>true</code> if this component should be focused, <code>false</code> otherwise
	 */
	public void setFocus(boolean focus)
	{
		isFocused = focus;
		setEnabled(focus);
		textPane.textEditorInShape.setEnabled(focus);
		if (focus)
		{
			//if this has to be focused, bring it to front temporarily
			position=parentSlide.getPosition(this);
			parentSlide.setPosition(this,0);

			//bounding component
			parentSlide.aBoundLabel.setBounds(
				getLocation().x-3,
				getLocation().y-3,
				getBounds().width+6,
				getBounds().height+6);
			parentSlide.aBoundLabel.setVisible(true);

			//focus this
			requestFocus();

		}
		else
		{
			//else bring it back to original position
			transferFocus();
			parentSlide.setPosition(this,position);
			textPane.textEditorInShape.setCaretPosition(0);

			//don't paint fully
			setOpaque(false);
		}
		parentSlide.aBoundLabel.setVisible(false);
		repaint();
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
		label.setOpaque(isOpaque);
		repaint();
	}

	/**
	 * Returns true if this component is completely opaque
	 * @return <code>true</code> if this component is completely opaque
	 */
	public boolean isOpaque()
	{
		return super.isOpaque();
	}

	/**
	 * Stores the bounds of this component into "return value" <code>rv</code> and
	 * returns <code>rv</code>. If <code>rv</code> is null a new Rectangle is allocated
	 * @return rv the return value, modified to the component's bounds
	 */
	public Rectangle getBounds()
	{
		return super.getBounds();
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
	 * Sets the background color of this component
	 * @param bgColor the desired background Color
	 */
	public void setBackground(Color bgColor)
	{
		label.setBackground(bgColor);
		textPane.setBackground(bgColor);
		repaint();
	}

	/**
	 * Sets the border color of this component
	 * @param outlineColor the desired border Color
	 */
	public void setOutlineColor(Color outlineColor)
	{
		label.setOutlineColor(outlineColor);
	}

	/**
	 * Gets the border color of this component
	 * @param the border Color
	 */
	public Color getOutlineColor()
	{
		return label.getOutlineColor();
	}

	/**
	 * Gets the Shape type
	 * @param the shape type
	 */
	public int getShapeType()
	{
		return shapeType;
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
		//update bounds for label and textPane
		if(label!=null)
		{
			label.setSize(w,h);
			label.repaint();
			textPane.setSize(w-10,h-20);
		}
	}

	/**
	 * Makes the component visible or invisible
	 * @param flag <code>true</code> to make the component visible;
	 * false to make it invisible
	 */
	public void setVisible(boolean b)
	{
		super.setVisible(b);
	}

	/**
	 * Label component of the shape. Different shapes are drawn here
	 */
	public class ShapeLabel extends JLabel
	{
		/*** Labels background color */
		public Color bgColor=Color.white;

		/*** Labels border color */
		public Color outlineColor=Color.black;

		/**
		 * Constructs a label component of the shape.
		 * @param x the x-coordinate of this component
		 * @param y the y-coordinate of this component
		 */
		public ShapeLabel(int x, int y)
		{
			super("");
			setSize(x,y);
			setLocation(0,0);
			setText("");
			repaint();
			setBackground(Color.green);
			setOutlineColor(Color.black);
		}

		/**
		 * Draw shape with line styles
		 * @param x the x-coordinate of this component
		 */
		public void paint(Graphics g)
		{
			g.setPaintMode();
			Graphics2D g2D = (Graphics2D)g;
			g2D.setPaintMode();
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

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
			g2D.setStroke(stroke);

			int x = getX();
			int y = getY();
			int width = getWidth();
			int height = getHeight();
			int size = Math.min(width,height);
			int start = lineWidth/2;

			//draw the dashed line according to different shape
			switch(shapeType)
			{
				case RECTANGLE:
					if(bgColor != null && !bgColor.equals(Color.white))
					{
						g2D.setColor(bgColor);
						g2D.fillRect(lineWidth/2, lineWidth/2,
							width-1 - lineWidth, height-1 - lineWidth);
					}
					g2D.setColor(outlineColor);
					g2D.drawRect(lineWidth/2, lineWidth/2,
						width-1 - lineWidth, height-1 - lineWidth);
					break;

				case SQUARE:
					if(bgColor != null && !bgColor.equals(Color.white))
					{
						g2D.setColor(bgColor);
						g2D.fillRect(
							(width-size)/2 + start,
							(height-size)/2 + start,
							size - lineWidth,
							size - lineWidth);
					}
					g2D.setColor(outlineColor);
					g2D.drawRect(
						(width-size)/2 + start,
						(height-size)/2 + start,
						size - lineWidth,
						size - lineWidth);
					break;

				case ROUND_RECTANGLE:
					if(bgColor != null && !bgColor.equals(Color.white))
					{
						g2D.setColor(bgColor);
						g2D.fillRoundRect(lineWidth/2, lineWidth/2,
							width-1 - lineWidth, height-1 - lineWidth,
						width/7, height/7);
					}
					g2D.setColor(outlineColor);
					g2D.drawRoundRect(lineWidth/2, lineWidth/2,
						width-1 - lineWidth, height-1 - lineWidth,
					width/7, height/7);
					break;

				case OVAL://p
					if(bgColor != null && !bgColor.equals(Color.white))
					{
						g2D.setColor(bgColor);
						g2D.fillOval(start,start,width-start*2,height-start*2);
					}
					g2D.setColor(outlineColor);
					g2D.drawOval(start,start,width-start*2,height-start*2);
					break;

				case DIAMOND://p
					int xx1[]={width/2,width-start,width/2,start};
					int yy1[]={start,height/2,height-start,height/2};
					if(bgColor != null && !bgColor.equals(Color.white))
					{
						g2D.setColor(bgColor);
						g2D.fillPolygon(xx1,yy1,4);
					}
					g2D.setColor(outlineColor);
					g2D.drawPolygon(xx1,yy1,4);
					break;

				case ISOCELES_TRIANGLE://p
					int ix1[]={start,(width-start)/2,width-start*2};
					int iy1[]={height-start,start,height-start};
					if(bgColor != null && !bgColor.equals(Color.white))
					{
						g2D.setColor(bgColor);
						g2D.fillPolygon(ix1,iy1,3);
					}
					g2D.setColor(outlineColor);
					g2D.drawPolygon(ix1,iy1,3);
					break;

			};

		}

		/**
		 * Sets the background color of this component
		 * @param bgColor the desired background Color
		 */
		public void setBackground(Color bgColor)
		{
			this.bgColor=bgColor;
		}

		/**
		 * Sets the border color of this component
		 * @param outlineColor the desired border Color
		 */
		public void setOutlineColor(Color outlineColor)
		{
			this.outlineColor=outlineColor;
		}

		/**
		 * Gets the border color of this component
		 * @param the border color
		 */
		public Color getOutlineColor()
		{
			return outlineColor;
		}
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
	 * Scrollpane containing text component for <code>AShape</code>
	 */
	public class TextEditor extends JScrollPane
	{
		/*** Text editing component */
		public JTextPane textEditorInShape;

		/**
		 * Constructs a scrollpane with text component in its viewport
		 * @param w the width of the component
		 * @param h the height of the component
		 * @param text the text to be set for text component
		 */
		public TextEditor(int w,int h)
		{
			super(JScrollPane.VERTICAL_SCROLLBAR_NEVER,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
			//new text component
			textEditorInShape = new JTextPane();

			//set attributes
			MutableAttributeSet m = arangam.actionsToolbar1.getAttributes();
			textEditorInShape.setCharacterAttributes(m,false);

			//set bounds
            setSize(w-10,h-20);
			setLocation(Utils.getMiddle(new Dimension(w,h),getSize()));

			textEditorInShape.setOpaque(false);

			//initial attributes
			textEditorInShape.setCharacterAttributes((AttributeSet)Arangam.newMattr, false);

			this.getViewport().setBackground(Color.white);
			this.setBorder(null);
			this.getViewport().setOpaque(false);
			this.setOpaque(false);

			//add it
			getViewport().add(textEditorInShape);
			//register listeners
			registerListeners();
		}

		/**
		 * Constructs a scrollpane with text component in its viewport
		 * and with StyledText
		 * @param w the width of the component
		 * @param h the height of the component
		 * @param styledText the styled text to be set for text component
		 */
		public TextEditor(int w,int h,StyledText styledText)
		{
			super(JScrollPane.VERTICAL_SCROLLBAR_NEVER,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
			//new text component
			textEditorInShape = new JTextPane();

			//set StyledText
			if(styledText != null)
				replaceSelection(styledText);

			//set bounds
			setSize(w-10,h-20);
			setLocation(Utils.getMiddle(new Dimension(w,h),getSize()));

			textEditorInShape.setOpaque(false);

			this.getViewport().setBackground(Color.white);
			this.setBorder(null);
			this.getViewport().setOpaque(false);
			this.setOpaque(false);

			//add it
			getViewport().add(textEditorInShape);
			//register listeners
			registerListeners();
		}

		/**
		 * Replaces the currently selected content with new content represented
		 * by the given <code>StyledText</code>. If there is no selection this amounts to an
		 * insert of the given text. If there is no replacement text
		 * this amounts to a removal of the current selection. The replacement
		 * text will have the attributes currently defined for input at
		 * the point of insertion. If the document is not editable, beep
		 * and return
		 * @param content the content to replace the selection with
		 * @see StyledText#insert
		 */
		public void replaceSelection(StyledText content)
		{
			JTextPane t = textEditorInShape;
			Document doc = t.getDocument();
			String text;
			Caret caret = t.getCaret();
			int insertPos = 0;
			int i;
			int contentSize;
			if(doc != null)
			{
				try
				{
					int p0 = Math.min(caret.getDot(), caret.getMark());
					int p1 = Math.max(caret.getDot(), caret.getMark());
					//if there is any selection
					if(p0 != p1)
					{
						doc.remove(p0, p1 - p0);
					}
					//insert the content
					if(content != null)
					{
						content.insert(doc, p0);
					}
				}
				catch(javax.swing.text.BadLocationException ble)
				{
					//beep and return
					UIManager.getLookAndFeel().provideErrorFeedback(this);
					return;
				}
			}
		}


		/**
		 * Returns the text contained in this TextComponent in terms of the
		 * content type of this editor. If an exception is thrown while
		 * attempting to retrieve the text, null will be returned.
		 * @return the text
		 */
		public String getText()
		{
			return textEditorInShape.getText();
		}

		/**
		 * Register mouse and caret listeners
		 * @see AShape.SymMouse
		 * @see TextEditor.ShapeCaretListener
		 */
		public void registerListeners()
		{
			//mouse
			SymMouse aSymMouse = new SymMouse();
			textEditorInShape.addMouseListener(aSymMouse);

			//caret
			ShapeCaretListener shapeCaretListener =new ShapeCaretListener();
			textEditorInShape.addCaretListener(shapeCaretListener);

			//update the ruler on mouse move
			textEditorInShape.addMouseMotionListener(new MouseMotionAdapter()
			{
				public void mouseMoved(java.awt.event.MouseEvent event)
				{
					if (isArangamEditable)
					{
						arangam.actionsImpl1.setRulerXY(getX()+event.getX(), getY()+event.getY());
					}
				}
				public void mouseDragged(java.awt.event.MouseEvent event)
				{
					if (isArangamEditable)
					{
						arangam.actionsImpl1.setRulerXY(getX()+event.getX(), getY()+event.getY());
					}
				}
			});

		}

		/**
		 * Listens for caret update and make the corresponding changes in toolbar
		 */
		private class ShapeCaretListener implements CaretListener
		{
			/**
			 * Called when the caret position is updated.
			 * Make the corresponding changes in toolbar.
			 */
			public void caretUpdate(CaretEvent e)
			{
				//can't update FontToolBar for Selected text.
				if(textEditorInShape.getSelectedText() != null)
				{
					return;
				}

				int pos = e.getDot();
				//update toolbar with the previous element's attributes
				if(pos != 0)
				{
					pos--;
				}

				StyledDocument m_doc = textEditorInShape.getStyledDocument();
				AttributeSet a = m_doc.getCharacterElement(pos).getAttributes();
				if(a != null)
				{
					arangam.actionsToolbar1.setFontToolBar(a);
				}

			}
		}

	}

	/**
	 * Gain focus on text component for shape
	 */
	public void requestFocus()
	{
		super.requestFocus();
		textPane.textEditorInShape.requestFocus();
	}

	/**
	 * Sets character attributes for text component
	 * @param a the attributes
	 */
	public void setFontAttributes(AttributeSet a)
	{
		textPane.textEditorInShape.setCharacterAttributes(a,false);
	}

	/**
	 * Gets character attributes from text component
	 * @return the attributes
	 */
	public AttributeSet getFontAttributes()
	{
		JTextPane t = textPane.textEditorInShape;
		StyledDocument doc = t.getStyledDocument();
		AttributeSet a = doc.getCharacterElement(t.getCaret().getDot()-1).getAttributes();
		return a;
	}

	/**
	 * Gets the labels background color
	 * @return the color
	 */
	public Color getBackground()
	{
		return label.bgColor;
	}

	/**
	 * Sets outline width
	 * @param width the outline width
	 */
	public void setLineWidth(int width)
	{
		lineWidth = width;
		repaint();
	}

	/**
	 * Sets outline style
	 * @param style the outline style
	 */
	public void setLineStyle(int style)
	{
		lineStyle = style;
		repaint();
	}

	/**
	 * Gets outline width
	 * @return the outline width
	 */
	public int getLineWidth()
	{
		return lineWidth;
	}

	/**
	 * Gets outline style
	 * @return the outline style
	 */
	public int getLineStyle()
	{
		return lineStyle;
	}

	/**
	 * Sets border for the textPane
	 * @param border the border to be rendered
	 */
	public void setEditorBorder(Border border)
	{
		textPane.setBorder(border);
		textPane.getViewport().setOpaque(false);
		textPane.setOpaque(false);
		textPane.textEditorInShape.setOpaque(false);
	}

	/**
	 * Applies the given attributes to character content. If there is a
	 * selection, the attributes are applied to the selection range.
	 * If there is no selection, the attributes are applied to the input
	 * attribute set which defines the attributes for any new text that gets
	 * inserted.
	 * @param attr the attributes
	 * @param replace if <code>true</code>, then replace the existing attributes first
	 */
	public void setCharacterAttributes(AttributeSet a,
		boolean setParagraphAttributes)
	{
		JTextPane t = textPane.textEditorInShape;
		t.setCharacterAttributes(a, false);
		if(setParagraphAttributes)
		{
			t.setParagraphAttributes(a, false);
		}
	}

	/**
	 * Set position for pasting components from <code>clipboard</code>
	 */
	public void setPastePosition()
	{
		int x = getX()+xInc;
		int y = getY()+yInc;
		int w = getWidth();
		int h = getHeight();
		Rectangle slideBounds = getParent().getBounds();
		if ((slideBounds.contains(x, y)) ||
			(slideBounds.contains(x+w, y)) ||
			(slideBounds.contains(x, y+h)) ||
			(slideBounds.contains(x+w, y+h)))
		{
			Arangam.pasteCompLocation.x = x;
			Arangam.pasteCompLocation.y = y;
		}
		else
		{
			Arangam.pasteCompLocation.x = x-xInc;
			Arangam.pasteCompLocation.y = y-yInc;
		}
	}

}

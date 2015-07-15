import java.awt.*;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

/**
 * Text component. Enables editing of text
 */
public class AText extends JScrollPane implements ConnectText
{

	/*** Main class reference */
	private transient Arangam arangam;

	/*** <code>Slideshow</code> in which this is added */
	private Slide parentSlide;

	/*** Can be edited (including dragging) */
	boolean isArangamEditable = true;

	/*** Focus is on this component */
	boolean isFocused = true;

	/*** Document model of this component */
	protected StyledDocument m_doc;

	/*** Undo listener */
	public static TTUndoRedo undo = new TTUndoRedo();

	/**
	 * Remembers the position of this component and
	 * place this component in position one temporary while editing
	 */
	private int position = -2;

	JTextPane tPane = new JTextPane();

	/**
	 * Constructs a new text component and connects it to a Slide
	 * and to the application(Arangam)
	 * @param aSlide Class Slide that contains the text component
	 * @param arangam main class reference
	 * @see Slide
	 * @see Arangam
	 */
	public AText(Slide aSlide, Arangam arangam)
	{
		super(JScrollPane.VERTICAL_SCROLLBAR_NEVER,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		getViewport().add(tPane);

		this.arangam = arangam;
		parentSlide = aSlide;

		//get document model of this component
		m_doc = tPane.getStyledDocument();

		//set attributes
		MutableAttributeSet m = arangam.actionsToolbar1.getAttributes();
		tPane.setCharacterAttributes(m,false);

		//set bounds
		int fontSize=StyleConstants.getFontSize
			((AttributeSet)Arangam.newMattr);
		setLocation(Arangam.pasteCompLocation);
		setSize(200,(int)(fontSize*1.5));

		//set bounds for the temporary component(ABoundLabel)
		//this is the bounding component for AImage
		parentSlide.aBoundLabel.setBounds(getBounds().x-3,getBounds().y-3,
			getBounds().width+6,getBounds().height+6);
		parentSlide.aBoundLabel.setVisible(true);

		//default border
		setBorder(new EmptyBorder(6,6,6,6));
		setBackground(Color.white);

		//set colors
		Color c=Utils.invertColor(getBackground());
		tPane.setSelectedTextColor(getBackground());
		tPane.setSelectionColor(c);
		tPane.setCaretColor(c);

		//add undo listener
		undo.setTextComponent(tPane);

		//add to parent (Slide)
		parentSlide.add(this);
		//focus this component
		parentSlide.focusComponent(this);
		//register listeners
		registerListeners();
	}

	/**
	 * Used when pasting StyledText from clipboard
	 * Constructs a new text component and connects it to a Slide
	 * and to the application(Arangam).
	 * @param aSlide Class Slide that contains the text component
	 * @param arangam main class reference
	 * @param st StyledText, text with different styles
	 * @param size the dimension of this component
	 * @param bgColor the background color
	 * @param border the border of the text in clipboard
	 * @see Slide
	 * @see Arangam
	 * @see #setStyledText
	 */
	public AText(Slide aSlide, Arangam arangam,StyledText st,
			Dimension size, Color bgColor, Border border)
	{
		super(JScrollPane.VERTICAL_SCROLLBAR_NEVER,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.arangam = arangam;
		this.parentSlide = aSlide;

		getViewport().add(tPane);

		//insert styled text
		setStyledText(st);

		//set background color, border
		setBackground(bgColor);
		setBorder(border);

		//get Document model of this component
		m_doc = tPane.getStyledDocument();

		//set colors
		Color c=Utils.invertColor(getBackground());
		tPane.setSelectedTextColor(getBackground());
		tPane.setSelectionColor(c);
		tPane.setCaretColor(c);

		//set bounds
		setLocation(Arangam.pasteCompLocation);
		setSize(getPreferredSize());

		//set bounds for the temporary component(ABoundLabel)
		//this is the bounding component for AImage
		parentSlide.aBoundLabel.setBounds(getBounds().x-3,getBounds().y-3,
			getBounds().width+6,getBounds().height+6);
		parentSlide.aBoundLabel.setVisible(true);

		//add to parent (Slide)
		parentSlide.add(this);
		//focus this component
		parentSlide.focusComponent(this);
		//register listeners
		registerListeners();
	}

	/**
	 * Used when pasting String from clipboard
	 * Constructs a new text component and connects it to a Slide
	 * and to the application(Arangam).
	 * @param aSlide Class Slide that contains the text component
	 * @param arangam main class reference
	 * @param str the string to set
	 * @param size the dimension of this component
	 * @param bgColor the background color
	 * @param border the border of the text in clipboard
	 * @see Slide
	 * @see Arangam
	 */
	public AText(Slide aSlide, Arangam arangam,String str,
			Dimension size, Color bgColor, Border border)
	{
		super(JScrollPane.VERTICAL_SCROLLBAR_NEVER,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.arangam = arangam;
		this.parentSlide = aSlide;

		getViewport().add(tPane);

		//set text, background and border
		tPane.setText(str);
		setBackground(bgColor);
		setBorder(border);

		//get Document model of this component
		m_doc = tPane.getStyledDocument();

		//set colors
		Color c=Utils.invertColor(getBackground());
		tPane.setSelectedTextColor(getBackground());
		tPane.setSelectionColor(c);
		tPane.setCaretColor(c);

		//set bounds
		setLocation(Arangam.pasteCompLocation);
		setSize(getPreferredSize());

		//set bounds for the temporary component(ABoundLabel)
		//this is the bounding component for AImage
		parentSlide.aBoundLabel.setBounds(getBounds().x-3,getBounds().y-3,
			getBounds().width+6,getBounds().height+6);
		parentSlide.aBoundLabel.setVisible(true);

		//add to parent (Slide)
		parentSlide.add(this);
		//focus this component
		parentSlide.focusComponent(this);
		//register listeners
		registerListeners();
	}

	/**
	 * Register mouse and caret listeners
	 * @see AText.SymMouse
	 * @see AText.TextCaretListener
	 */
	public void registerListeners()
	{
		SymMouse aSymMouse = new SymMouse();
		tPane.addMouseListener(aSymMouse);

		//add caret listener
		TextCaretListener textCaretListener =new TextCaretListener();
		tPane.addCaretListener(textCaretListener);

		//update the ruler on mouse move
		tPane.addMouseMotionListener(new MouseMotionAdapter()
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
	 * Listens for Caret update and make the corresponding changes in toolbar
	 */
	private class TextCaretListener implements CaretListener
	{
		/**
		 * Called when the caret position is updated.
		 * Make the corresponding changes in toolbar.
		 */
		public void caretUpdate(CaretEvent e)
		{
			//can't update FontToolBar for Selected text.
			if(getSelectedText() != null)
			{
				return;
			}

			int pos = e.getDot();

			//update toolbar with the previous element's attributes
			if(pos != 0)
			{
				pos--;
			}
			AttributeSet a = m_doc.getCharacterElement(pos).getAttributes();
			if(a != null)
			{
				arangam.actionsToolbar1.setFontToolBar(a);
			}
		}

	}

	/**
	 * MouseAdapter for the component.
	 * Shows popup menu on right click and to focus the component on
	 * mouse press
	 */
	public class SymMouse extends java.awt.event.MouseAdapter
	{
		/**
		 * Invoked when mouse released
		 * Shows popup menu on right click
		 * @see TextPopup
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
					TextPopup textPopup = new TextPopup(AText.this,arangam,parentSlide);
					textPopup.show(event.getComponent(),event.getX(),event.getY());
				}
				//else - popup menu for Slide
				else
				{
					SlideshowPopup slideshowPopupMenu = new SlideshowPopup(parentSlide,arangam);
					slideshowPopupMenu.show(event.getComponent(),event.getX(),event.getY());
				}
			}
		}

		/**
		 * Invoked when mouse pressed
		 * Focus the component on mouse pressed
		 * @see Slide#focusComponent
		 */
		public void mousePressed(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if(isArangamEditable && !isFocused)
			{
				parentSlide.focusComponent(AText.this);
			}
		}
	}

	/**
	 * Draws the bounding rectangle when focused.
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
			5.0f, dash1, 0.0f);
			g2D.setStroke(basicStroke);
			g2D.setColor(Color.darkGray);
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
		tPane.setEnabled(focus);
		tPane.setEditable(focus);
		if (focus)
		{
			//if this has to be focused, bring it to front temporarily
			position=parentSlide.getPosition(AText.this);
			parentSlide.setPosition(AText.this,0);
			parentSlide.aBoundLabel.setBounds(
				getLocation().x-3,
				getLocation().y-3,
				getBounds().width+6,
				getBounds().height+6);
			//bounding component
			parentSlide.aBoundLabel.setVisible(true);

			//focus this
			requestFocus();
			tPane.requestFocus();

			//for find
			tPane.getCaret().setVisible(true);
			tPane.getCaret().setSelectionVisible(true);

			//paint fully
			tPane.setOpaque(true);
			setOpaque(true);
		}
		else
		{
			tPane.transferFocus();

			//for find
			tPane.getCaret().setVisible(false);
			tPane.getCaret().setSelectionVisible(false);

			//else bring it back to original position
			parentSlide.setPosition(this,position);
			parentSlide.aBoundLabel.setVisible(false);

			//to set to opaque or not
			Color c = getBackground();
			setBackground(c);
		}

		repaint();
		parentSlide.repaint();

		//mark Slideshow as modified
		arangam.slideModified = true;
	}

	/**
	 * Apply or remove focus from this component
	 * Used for selecting found text in Find/Replace
	 * @param focus <code>true</code> if this component should be focused, <code>false</code> otherwise
	 * @param caretPosition position of caret while focusing
	 */
	public void setFocus(boolean focus,int caretPosition)
	{
		isFocused = focus;
		setEnabled(focus);
		tPane.setEnabled(focus);
		tPane.setEditable(focus);
		if(focus)
		{
			//if this has to be focused, bring it to front temporarily
			position = parentSlide.getPosition(AText.this);
			parentSlide.setPosition(AText.this,0);

			//bounding component
			parentSlide.aBoundLabel.setBounds(
				getLocation().x-3,
				getLocation().y-3,
				getBounds().width+6,
				getBounds().height+6);
			parentSlide.aBoundLabel.setVisible(true);

			//focus this
			tPane.requestFocus();

			//for find
			tPane.getCaret().setVisible(true);
			tPane.setCaretPosition(caretPosition);
			tPane.getCaret().setSelectionVisible(true);

		}
		else
		{
			transferFocus();

			//for find
			tPane.getCaret().setVisible(false);
			tPane.getCaret().setSelectionVisible(false);

			//else bring it back to original position
			parentSlide.setPosition(this,position);
			parentSlide.aBoundLabel.setVisible(false);

		}
		parentSlide.repaint();

		//mark Slideshow as modified
		arangam.slideModified = true;
	}

	/**
	 * Remove this from the Slide
	 */
	public void removeFromSlide()
	{
		parentSlide.remove(this);
	}

	/**
	 * Returns the text contained in this TextComponent in terms of the
	 * content type of this editor. If an exception is thrown while
	 * attempting to retrieve the text, null will be returned.
	 * @return the text
	 */
	public String getText()
	{
		return tPane.getText();
	}

	/**
	 * Returns the selected text contained in this TextComponent
	 * If the selection is null or the document empty, returns null
	 * @return the text
	 */
	public String getSelectedText()
	{
		return tPane.getSelectedText();
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
	public void setCharacterAttributes(AttributeSet attr,boolean replace)
	{
		tPane.setCharacterAttributes(attr,replace);
	}

	/**
	 * Fetches the character attributes in effect at the current location
	 * of the caret, or null
	 * @return the attributes, or null
	 */
	public AttributeSet getCharacterAttributes()
	{
		return tPane.getCharacterAttributes();
	}

	/**
	 * Fetches the caret that allows text-oriented navigation over the view
	 * @return the attributes, or null
	 * @return the caret
	 */
	public Caret getCaret()
	{
		return tPane.getCaret();
	}

	public MutableAttributeSet getInputAttributes()
	{
		return tPane.getInputAttributes();
	}

	/**
	 * Fetches the model associated with the editor
	 * @return the model
	 */
	public StyledDocument getStyledDocument()
	{
		return tPane.getStyledDocument();
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
	 * Returns true if this component is completely opaque
	 * @return <code>true</code> if this component is completely opaque
	 */
	public boolean isOpaque()
	{
		return super.isOpaque();
	}

	/**
	 * Stores the bounds of this component into "return value" rv and
	 * returns rv. If rv is null a new Rectangle is allocated
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
	 * Sets the current color used to render the selection. Setting the
	 * color to null is the same as setting Color.white. Setting the
	 * color results in a PropertyChange event ("selectionColor")
	 * @param selectionTextColor the color
	 */
	public void setSelectionColor(Color selectionColor)
	{
		tPane.setSelectionColor(selectionColor);
	}

	/**
	 * Sets the current color used to render the selected text.
	 * Setting the color to null is the same as Color.black
	 * @param selectedTextColor the color
	 */
	public void setSelectedTextColor(Color selectedTextColor)
	{
		tPane.setSelectedTextColor(selectedTextColor);
	}

	/**
	 * Sets the current color used to render the caret. Setting to null
	 * effectively restores the default color. Setting the color results
	 * in a PropertyChange event ("caretColor") being fired
	 * of the caret, or null
	 * @param caretColor the color
	 */
	public void setCaretColor(Color caretColor)
	{
		tPane.setCaretColor(caretColor);
	}

	/**
	 * Sets the background color of this component
	 * @param bgColor the desired background Color
	 */
	public void setBackground(Color bgColor)
	{
		//paint fully if Background Color is set. else not

		super.setBackground(bgColor);

		if(bgColor != null && !bgColor.equals(Color.white))
		{
			setOpaque(true);
			getViewport().setOpaque(true);
			if(tPane != null)
			{
				tPane.setOpaque(true);
			}

		}
		else
		{
			setOpaque(false);
			getViewport().setOpaque(false);
			if(tPane != null)
			{
				tPane.setOpaque(false);
			}
		}

		if(tPane != null)
		{
			tPane.setSelectedTextColor(getBackground());
			tPane.setBackground(getBackground());
		}
	}

	/**
	 * Returns the position of the text insertion caret for the text component
	 * @return the position of the text insertion caret for the text component >= 0
	 */
	public int getCaretPosition()
	{
		return tPane.getCaretPosition();
	}

	/**
	 * Replaces the currently selected content with new content represented
	 * by the given string. If there is no selection this amounts to an
	 * insert of the given text. If there is no replacement text
	 * this amounts to a removal of the current selection. The replacement
	 * text will have the attributes currently defined for input at
	 * the point of insertion. If the document is not editable, beep and return
	 * @param content the content to replace the selection with
	 */
	public void replaceSelection(String replace)
	{
		tPane.replaceSelection(replace);
	}

	/**
	 * Replaces the currently selected content with new content represented
	 * by the given StyledText. If there is no selection this amounts to an
	 * insert of the given text. If there is no replacement text
	 * this amounts to a removal of the current selection. The replacement
	 * text will have the attributes currently defined for input at
	 * the point of insertion. If the document is not editable, beep and return
	 * @param content the content to replace the selection with
	 * @see StyledText#insert
	 */
	public void replaceSelection(StyledText content)
	{
		Document doc = tPane.getDocument();
		String text;
		Caret caret = getCaret();
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
			catch(BadLocationException ble)
			{
				javax.swing.UIManager.getLookAndFeel().provideErrorFeedback(this);
				return;
			}
		}
	}

	/**
	 * Initialise textbox with the "styled" text
	 * @param content the styled text
	 */
	public void setStyledText(StyledText content)
	{
		Document doc = tPane.getDocument();
		if(content != null)
		{
			try
			{
				content.insert(doc, 0);
			}
			catch(BadLocationException ble)
			{
				javax.swing.UIManager.getLookAndFeel().provideErrorFeedback(this);
				return;
			}
		}
	}

	/**
	 * Initialise textbox with the text
	 * @param content the text
	 */
	public void setText(String content)
	{
		tPane.setText(content);
	}

	/**
	 * Sets the selection start to the specified position.
	 * The new starting point is constrained to be before or at the current
	 * selection end of the caret, or null
	 * @param selectionStart the start position of the text >= 0
	 */
	public void setSelectionStart(int selStart)
	{
		tPane.setSelectionStart(selStart);
	}

	/**
	 * Sets the selection end to the specified position.
	 * The new end point is constrained to be at or after the current
	 * selection start of the caret, or null
	 * @param selectionEnd the end position of the text >= 0
	 */
	public void setSelectionEnd(int selEnd)
	{
		tPane.setSelectionEnd(selEnd);
	}

	/**
	 * Selects all the text in the TextComponent. Does nothing on a null or
	 * empty document
	 */
	public void selectAll()
	{
		tPane.selectAll();
	}

	/**
	 * Gets the location of this component in the form of a point.
	 * @return an instance of Point representing the top-left
	 * corner of the component's bounds.
	 */
	public Point getLocation()
	{
		return super.getLocation();
	}

	/**
	 * Returns the size of this component in the form of a Dimension object.
	 * The height field of the Dimension object contains this component's
	 * height, and the width field of the Dimension object contains
	 * this component's width
	 * @return a Dimension object that indicates the size of this component
	 */
	public Dimension getSize()
	{
		return super.getSize();
	}

	/**
	 * Returns the border of this component or null if no border is
	 * currently set
	 * @return the border object for this component
	 */
	public Border getBorder()
	{
		return super.getBorder();
	}

	/**
	 * Gets the background color of this component
	 * @return this component's background color; if this component
	 * does not have a background color, the background color of its
	 * parent is returned
	 */
	public Color getBackground()
	{
		return super.getBackground();
	}

	/**
	 * Moves and resizes this component. The new location of the
	 * top-left corner is specified by x and y, and the new size is
	 * specified by width and height
	 * @param x the new x-coordinate of this component
	 * @param y the new y-coordinate of this component
	 * @param w the new width of this component
	 * @param h the new height of this component
	 */
	public void setBounds(int x,int y,int w,int h)
	{
		super.setBounds(x,y,w,h);
		if(isFocused)
		{
			parentSlide.aBoundLabel.setBounds(getBounds().x-3, getBounds().y-3,
			getBounds().width+6, getBounds().height+6);
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
	 * Applies the given attributes to character content. If there is a
	 * selection, the attributes are applied to the selection range.
	 * If there is no selection, the attributes are applied to the input
	 * attribute set which defines the attributes for any new text that gets
	 * inserted.
	 * @param attr the attributes
	 * @param ApplyToPara if <code>true</code>, apply to paragraph
	 */
	public void setAttributeSet(AttributeSet attr,boolean setParagraphAttributes)
	{
		int xStart = tPane.getSelectionStart();
		int xFinish = tPane.getSelectionEnd();

		if(setParagraphAttributes)
		{
			m_doc.setParagraphAttributes(xStart,xFinish - xStart, attr, false);
		}

		//if there is any selection
		if(xStart != xFinish)
		{
			m_doc.setCharacterAttributes(xStart, xFinish - xStart, attr, false);
		}

		//else add it to input attributes
		else
		{
			MutableAttributeSet inputAttributes = getInputAttributes();
			inputAttributes.addAttributes(attr);
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
	 * Selects the text between the specified start and end positions
	 * of the caret, or null
	 * @param selectionStart the start position of the text
	 * @param selectionEnd the end position of the text
	 */
	public void select(int from,int to)
	{
		tPane.setCaretPosition(from);
		tPane.moveCaretPosition(to);
	}

	/**
	 * Find the given string in the text component.
	 * @param searchString the string to be searched
	 * @param caseSensitive <code>true</code> for case sensitive search
	 * @param downDir <code>true</code> for searching downwards
	 * @param wholeword <code>true</code> for searching whole words
	 * @return <code>true</code> when fished searching in this text component
	 */
	public boolean find(String searchString,boolean caseSensitive,
		boolean downDir,boolean wholeWord)
	{
		String text = tPane.getText();
		int caretPosition = tPane.getCaretPosition();
		int searchStrLength = searchString.length();
		while(true)
		{
			int index = -1;
			//change to lower case for case insensitive search
			if(!caseSensitive)
			{
				text = text.toLowerCase();
				searchString = searchString.toLowerCase();
			}

			//initialise start index according to search direction
			if(downDir)
				index = text.indexOf(searchString,caretPosition);
			else
				index = text.lastIndexOf(searchString,caretPosition-1);

			//till not found
			if(index != -1 )
			{
				//for whole word search
				if(wholeWord)
				{
					//initialise character after and before word
					char charBefore = 't';
					char charAfter = 't';

					//assume char before for first word = ' '
					if(index == 0)
					{
						charBefore = ' ';
						charAfter = text.charAt(index+searchStrLength);
					}
					//assume char after for last word = ' '
					else if(index == getText().length())
					{
						charBefore = text.charAt(index-1);
						charAfter = ' ';
					}
					//normal case
					else
					{
						if(downDir)
						{
							charBefore = text.charAt(index-1);
							charAfter = text.charAt(index+searchStrLength);
						}
						else
						{
							charAfter = text.charAt(index);
							charBefore = text.charAt(index-searchStrLength-1);
						}
					}

					//if both the chars are word separators
					if(Utils.isSeparator(charBefore) && Utils.isSeparator(charAfter))
					{
						//select the searched string
						if(downDir)
							select(index,index+searchStrLength);
						else
							select(index+searchStrLength,index);
						return false;
					}
					//else update caret position and continue
					else
					{
						caretPosition = (downDir?index+searchStrLength:index);
						continue;
					}
				}
				//for partial word search
				else
				{
					if(downDir)
						tPane.select(index,index+searchStrLength);
					else
						tPane.select(index+searchStrLength,index);
					return false;
				}
			}
			//no return and come to this point
			//break and return true
			break;
		}
		return true;
	}

	/**
	 * Returns the current x coordinate of the component's origin
	 * @return the current x coordinate of the component's origin
	 */
	public int getX()
	{
		return super.getX();
	}

	/**
	 * Returns the current y coordinate of the component's origin
	 * @return the current y coordinate of the component's origin
	 */
	public int getY()
	{
		return super.getY();
	}

	/**
	 * Set position for pasting components from clipboard
	 */
	public void setPastePosition()
	{
		int x = getX()+20;
		int y = getY()+20;
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
			Arangam.pasteCompLocation.x = x-20;
			Arangam.pasteCompLocation.y = y-20;
		}
	}

	/**
	 * Gain focus on text component for shape
	 */
	public void requestFocus()
	{
		super.requestFocus();
		tPane.requestFocus();
	}

}

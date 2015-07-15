import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.applet.*;
import java.net.*;
import javax.swing.border.*;

/**
 * Container for all text, image, shape components
 */
public class Slide extends javax.swing.JLayeredPane
{
	//Constant for background fill effect
	final static int FILL_SOLID = 0,
					FILL_HORIZONTAL = 1,
					FILL_VERTICAL = 2,
					FILL_DIAGONAL_UP = 3,
					FILL_DIAGONAL_DOWN = 4;

	/*** Main class reference */
	private transient Arangam arangam;

	/*** Reference to focused component */
	public ConnectComponent focused;

	/*** StyledText flavor */
	public DataFlavor df = new DataFlavor(StyledText.class, "StyledText");

	/*** Reference to a shape component */
	private AShape newShape;

	/*** Reference to a ABoundLabel component */
	public ABoundLabel aBoundLabel;

	/*** Don't transform for first time */
	private boolean firstTime = true;

	/*** Transformation for Slide */
	private AffineTransform at;

	/*** can be edited (including dragging) */
	boolean isArangamEditable = true;

	/*** Background fill effect */
	public int fillEffect = Slide.FILL_SOLID;	//

	/*** Initial color for background effect */
	public Color fromColor = Color.white;

	/*** Final color for background effect */
	public Color toColor = Color.white;

	/*** Paste text box as full or not */
	boolean pasteAsFullTextComp = false;

	/*** Reference for StyledText for pasting shape */
	StyledText styledTextForShape;

	/**
	 * Create an empty Slide
	 * @see Slideshow
	 */
	public Slide(Arangam arangam)
	{
		this.arangam = arangam;
		//setLayout(null);

		focused = null;
		aBoundLabel= new ABoundLabel(this,this.arangam,0,0,0,0);
		add(aBoundLabel);
		aBoundLabel.setVisible(false);

		//construct transformation
		at = new AffineTransform();

		//register listeners
		registerListeners();

	}

	/**
	 * MouseMotionAdapter for the component.
	 * Just to update the ruler on mouse move/drag
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
			arangam.actionsImpl1.setRulerXY(event.getX(), event.getY());
		}

		/**
		 * Update the ruler on mouse drag
		 */
		public void mouseDragged(java.awt.event.MouseEvent event)
		{
			arangam.actionsImpl1.setRulerXY(event.getX(), event.getY());
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
		 * Invoked when mouse released
		 */
		public void mouseReleased(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if(arangam.viewMode == Arangam.SHOW_VIEW &&
				!SwingUtilities.isRightMouseButton(event))
			{
				arangam.actionsImpl1.NextSlideView();
			}
			else if (object == Slide.this && isArangamEditable)
			{
				Slide_mousePressed(event);
			}

			if (SwingUtilities.isRightMouseButton(event))
			{
				if(arangam.viewMode == Arangam.SLIDE_VIEW)
				{
					SlidePopup slidePopup = new SlidePopup(Slide.this,arangam);
					slidePopup.show(event.getComponent(),event.getX(),event.getY());
				}
				else if(arangam.viewMode == Arangam.SHOW_VIEW)
				{
					SlideshowPopup slideshowPopup = new SlideshowPopup(Slide.this,arangam);
					slideshowPopup.show(event.getComponent(),event.getX(),event.getY());
				}

			}
		}
	}

	/**
	 * Draws background effect
	 * @param graphics the <code>Graphics</code> context in which to paint.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		//bound focused component with ABoundLabel
		if(focused!=null )
		{
			aBoundLabel.setVisible(true);
		}
		else
		{
			aBoundLabel.setVisible(false);
		}

		Graphics2D g2D = (Graphics2D)g;
		GradientPaint grad;

		//paint background of Slide
		switch(fillEffect)
		{
			case FILL_SOLID :
				grad = new GradientPaint(0, 0, fromColor, getWidth(), 0, fromColor);
				g2D.setPaint(grad);
				g2D.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
				break;
			case FILL_HORIZONTAL :
				grad = new GradientPaint(0, 0, fromColor, getWidth(), 0, toColor);
				g2D.setPaint(grad);
				g2D.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
				break;
			case FILL_VERTICAL :
				grad = new GradientPaint(0, 0, fromColor, 0, getHeight(), toColor);
				g2D.setPaint(grad);
				g2D.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
				break;
			case FILL_DIAGONAL_UP :
				grad = new GradientPaint(0, 0, fromColor, getWidth(),
				getHeight(), toColor);
				g2D.setPaint(grad);
				g2D.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
				break;
			case FILL_DIAGONAL_DOWN :
				grad = new GradientPaint(getWidth(), 0, fromColor, 0,
				getHeight(), toColor);
				g2D.setPaint(grad);
				g2D.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
				break;
		}//switch

		//very light gray
		g2D.setColor(new Color(220, 220, 220));

		//no transformation at first
		if(firstTime)
		{
			at.setToIdentity();
			firstTime = false;
		}

		//transform at show view
		if(arangam.viewMode == Arangam.SHOW_VIEW)
		{
			Dimension slide_Dim = new Dimension(Arangam.SLIDESHOW_WIDTH,
													Arangam.SLIDESHOW_HEIGHT);
			Dimension screen_Dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
			double pY=(float)(screen_Dim.height)/slide_Dim.height;
			double pX=(float)(screen_Dim.width)/slide_Dim.width;
			at.setToIdentity();
			at.scale(pX,pY);
			g2D.transform(at);
		}
		//transform at sorter view
		else if(arangam.viewMode == Arangam.SORTER_VIEW)
		{
			at.setToIdentity();
			at.scale(.25,.25);
			g2D.transform(at);
		}

	}//paint

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
	 * Invoked when mouse pressed
	 */
	private void Slide_mousePressed(java.awt.event.MouseEvent event)
	{
		if (focused != null)
		{
			String focusedName = focused.getClass().getName();

			//remove text component with no text
			if(focusedName.equals("AText") &&
				((ConnectText)focused).getText().equals(""))
			{
				focused.removeFromSlide();
				aBoundLabel.setVisible(false);
			}

			//remove focus
			focused.setFocus(false);

			//set default properties
			aBoundLabel.setVisible(false);
			focused = null;
			arangam.actionsToolbar1.setDefaultFontToolBar();
			arangam.setCCPEnabled();

			arangam.formatMenuObj1.activate();
		}
	}

	/**
	 * Connect the class to the main class
	 * @param arangam main class reference
	 */
	public void getInterface(Arangam arangam)
	{
		this.arangam = arangam;
	}

	/**
	 * Set the application to edit or non edit mode
	 * @param editable if <code>true</code>, can be editable; otherwise can't
	 */
	public void setArangamEditable(boolean editable)
	{
		int num = getComponentCount();

		for(int i=0; i<num ; i++)
		{
			((ConnectComponent)(getComponent(i))).setArangamEditable(editable);
		}
		isArangamEditable = editable;
	}

	/**
	 * Focus the given component
	 * @param component the component to be focused
	 */
	public void focusComponent(ConnectComponent component)
	{
		if(focused != null)
		{
			//remove text component with no text
			if(focused.getClass().getName().equals("AText") &&
				((ConnectText)focused).getText().equals("") &&
				focused!=component)
			{
				focused.removeFromSlide();
				aBoundLabel.setVisible(false);
			}
			//remove focus from it
			focused.setFocus(false);
		}

		//refer to new component
		focused = component;

		if(component != null)
		{
			//focus it
			focused.setFocus(true);
			focused.setPastePosition();
		}

		//update toolbar and menubar
		arangam.setCCPEnabled();
		arangam.formatMenuObj1.activate();
	}

	/**
	 * Focus the given text component at the indicated caret position.
	 * Used for find/replace
	 * @param component the component to be focused
	 * @param caretPosition the caret position of text component
	 */
	public void focusComponent(ConnectComponent component,int caretPosition)
	{
		if(focused != null)
		{
			if(focused.getClass().getName().equals("AText") &&
			((ConnectText)focused).getText().equals("") && focused!=component)
			{
				focused.removeFromSlide();
				aBoundLabel.setVisible(false);
			}
			((AText)focused).setFocus(false,caretPosition);
		}
		focused = component;
		if(component != null)
		{
			((AText)focused).setFocus(true,caretPosition);
		}
		arangam.setCCPEnabled();
		arangam.formatMenuObj1.activate();
	}

	/**
	 * Copy the component that has focus
	 * @see ComponentSelection
	 * @see StyledText
	 */
	public void copyComponent()
	{
		//return if nothing is focused
		if (focused == null)
		{
			return;
		}

		String focusedName = focused.getClass().getName();

		//set pasting position
		focused.setPastePosition();

		//for text
		if(focusedName.equals("AText"))
		{
			try
			{
				AText focusedText = (AText) focused;
				JTextPane tp = focusedText.tPane;

				//no selection. copy the textbox fully
				if(focusedText.getSelectedText() == null)
				{
					arangam.textSize = focusedText.getSize();
					arangam.textbgColor = focusedText.getBackground();
					arangam.textBorder = focusedText.getBorder();
					focusedText.selectAll();
					StyledText st = new StyledText(tp);
					StyledTextSelection contents = new StyledTextSelection(st);
					String s = focusedText.getSelectedText();
					focusedText.setSelectionStart(0);
					focusedText.setSelectionEnd(0);
					arangam.clipboard.setContents(contents,arangam);
					pasteAsFullTextComp = true;
					return;
				}
				//copy only the selected text and text attributes
				if(focusedText.getSelectedText() != null)
				{
					StyledText st = new StyledText(tp);
					arangam.textSize = focusedText.getSize();
					arangam.textbgColor = Color.white;
					arangam.textBorder = new EmptyBorder(6,6,6,6);
					StyledTextSelection contents = new StyledTextSelection(st);
					arangam.clipboard.setContents(contents,arangam);
					pasteAsFullTextComp = false;
					return;
				}
			}catch(BadLocationException e)
			{
				UIManager.getLookAndFeel().provideErrorFeedback(this);
				return;
			}
		}

		//for shape
		if(focusedName.equals("AShape"))
		{
			try
			{
				AShape aShape = (AShape) focused;
				JTextPane shapeTextPane = aShape.textPane.textEditorInShape;

				//copy only the selected text and text attributes
				if(shapeTextPane.getSelectedText() != null)
				{
					StyledText st = new StyledText(shapeTextPane);
					StyledTextSelection contents = new StyledTextSelection(st);
					arangam.clipboard.setContents(contents,arangam);
					pasteAsFullTextComp = false;
					return;
				}
				//no selection. copy the textbox fully
				if(shapeTextPane.getSelectedText() == null)
				{
					shapeTextPane.selectAll();
					styledTextForShape = new StyledText(shapeTextPane);
					shapeTextPane.setSelectionStart(0);
					shapeTextPane.setSelectionEnd(0);
					pasteAsFullTextComp = true;
					//no return purposely.
				}
			}catch(BadLocationException e)
			{
				UIManager.getLookAndFeel().provideErrorFeedback(this);
				return;
			}
		}

		//create a ComponentSelection object with focused component
		ComponentSelection contents = null;
		contents = new ComponentSelection((Object)focused);

		//save it in clipboard
		arangam.clipboard.setContents(contents,arangam);

		//update toolbar
		arangam.setCCPEnabled();
		repaint();
	}

	/**
	 * Paste from clipboard
	 * @see ComponentSelection
	 * @see StyledText
	 */
	public void pasteComponent()
	{
		try
		{
			//get content from clipboard
			Transferable content = arangam.clipboard.getContents(this);

			//return if no content
			if(content == null)
				return;

			String focusedName = "";
			//for text
			if(focused != null)
			{
				focusedName = focused.getClass().getName();
				//if the clipboard has only the selected text
				if(!pasteAsFullTextComp && focusedName.equals("AText"))
				{
					//StyledText flavor
					if(content.isDataFlavorSupported(df))
					{
						StyledText st = (StyledText) content.getTransferData(df);
						((ConnectText)focused).replaceSelection(st);
						return;
					}
					//String flavor
					if(content.isDataFlavorSupported(DataFlavor.stringFlavor))
					{
						String text = (String)content.getTransferData(DataFlavor.stringFlavor);
						((ConnectText)focused).replaceSelection(text);
						return;
					}
					//Image flavor
					if(content.isDataFlavorSupported(ComponentSelection.imageFlavor))
					{
						AImage clipboardArangamImage = (AImage)
							content.getTransferData(ComponentSelection.imageFlavor);
						ImageIcon imgIcon = (ImageIcon)clipboardArangamImage.getIcon();
						Border b = clipboardArangamImage.getBorder();
						Image img = imgIcon.getImage();
						AImage newArangamImage = new AImage(this,arangam,img,b);
						return;
					}
					//Shape flavor
					if(content.isDataFlavorSupported(ComponentSelection.shapeFlavor))
					{
						AShape clipboardArangamShape = (AShape)
							content.getTransferData(ComponentSelection.shapeFlavor);
						int shapeType = clipboardArangamShape.getShapeType();
						Dimension size = clipboardArangamShape.getSize();
						//String text = clipboardArangamShape.textPane.getText();
						Color bgColor = clipboardArangamShape.getBackground();
						Color outlineColor = clipboardArangamShape.getOutlineColor();
						int lineWidth = clipboardArangamShape.getLineWidth();
						int lineStyle = clipboardArangamShape.getLineStyle();
						AShape newArangamShape = new AShape(this,arangam,shapeType,
							lineWidth,lineStyle,size,styledTextForShape,bgColor,outlineColor);
						return;
					}
				}//AText

				//for shape
				if (!pasteAsFullTextComp && focusedName.equals("AShape"))
				{
					//StyledText flavor
					if(content.isDataFlavorSupported(df))
					{
						StyledText st = (StyledText) content.getTransferData(df);
						AShape aShape = (AShape)focused;
						aShape.textPane.replaceSelection(st);
						return;
					}
					//String flavor
					if(content.isDataFlavorSupported(DataFlavor.stringFlavor))
					{
						String text = (String)content.getTransferData(DataFlavor.stringFlavor);
						AShape aShape = (AShape)focused;
						aShape.textPane.textEditorInShape.replaceSelection(text);
						return;
					}
					//Image flavor
					if(content.isDataFlavorSupported(
						ComponentSelection.imageFlavor))
					{
						AImage clipboardArangamImage = (AImage)
							content.getTransferData(ComponentSelection.imageFlavor);
						ImageIcon imgIcon = (ImageIcon)clipboardArangamImage.getIcon();
						Border b = clipboardArangamImage.getBorder();
						Image img = imgIcon.getImage();
						AImage newArangamImage = new AImage(this,arangam,img,b);
						return;
					}
					//Shape flavor
					if(content.isDataFlavorSupported(
						ComponentSelection.shapeFlavor))
					{
						AShape clipboardArangamShape = (AShape)
							content.getTransferData(ComponentSelection.shapeFlavor);
						int shapeType = clipboardArangamShape.getShapeType();
						Dimension size = clipboardArangamShape.getSize();
						Color bgColor = clipboardArangamShape.getBackground();
						Color outlineColor = clipboardArangamShape.getOutlineColor();
						int lineWidth = clipboardArangamShape.getLineWidth();
						int lineStyle = clipboardArangamShape.getLineStyle();
						AShape newArangamShape = new AShape(this,arangam,shapeType,
							lineWidth,lineStyle,size,styledTextForShape,bgColor,outlineColor);
						return;
					}
				}//AShape
			}//focused != null

			//nothing is focused
			//StyledText flavor
			if(content.isDataFlavorSupported(df))
			{
				StyledText st = (StyledText) content.getTransferData(df);
				AText newArangamText = new AText(this,arangam,st,
					arangam.textSize,arangam.textbgColor,arangam.textBorder);
				return;
			}
			//String flavor
			if(content.isDataFlavorSupported(DataFlavor.stringFlavor))
			{
				String str = (String) content.getTransferData(DataFlavor.stringFlavor);
				arangam.textSize.height = 18;
				arangam.textSize.width = str.length()*10;
				AText newArangamText = new AText(this,arangam,str,
					arangam.textSize,arangam.textbgColor,arangam.textBorder);
				return;
			}
			//Image flavor
			if(content.isDataFlavorSupported(
				ComponentSelection.imageFlavor))
			{
				AImage clipboardArangamImage = (AImage)
					content.getTransferData(ComponentSelection.imageFlavor);
				ImageIcon imgIcon = (ImageIcon)clipboardArangamImage.getIcon();
				Border b = clipboardArangamImage.getBorder();
				Image img = imgIcon.getImage();
				AImage newArangamImage = new AImage(this,arangam,img,b);
				return;
			}
			//Shape flavor
			if(content.isDataFlavorSupported(
				ComponentSelection.shapeFlavor))
			{
				AShape clipboardArangamShape = (AShape)
					content.getTransferData(ComponentSelection.shapeFlavor);
				int shapeType = clipboardArangamShape.getShapeType();
				Dimension size = clipboardArangamShape.getSize();
				Color bgColor = clipboardArangamShape.getBackground();
				Color outlineColor = clipboardArangamShape.getOutlineColor();
				int lineWidth = clipboardArangamShape.getLineWidth();
				int lineStyle = clipboardArangamShape.getLineStyle();
				AShape newArangamShape = new AShape(this,arangam,shapeType,
					lineWidth,lineStyle,size,styledTextForShape,bgColor,outlineColor);
				return;
			}
			else
				return;
		}
		catch(BadLocationException e)
		{
			UIManager.getLookAndFeel().provideErrorFeedback(this);
			return;
		}
		catch(UnsupportedFlavorException e)
		{
			UIManager.getLookAndFeel().provideErrorFeedback(this);
			return;
		}
		catch(java.io.IOException e)
		{
			UIManager.getLookAndFeel().provideErrorFeedback(this);
			return;
		}
		finally
		{
			repaint();
		}
	}

	/**
	 * Delete the component that has focus
	 */
	public void clearComponent()
	{
		if(focused != null)
		{
			if(focused instanceof AText)
			{
				AText aText = (AText)focused;
				if(aText.getSelectedText() != null)
				{
					aText.replaceSelection("");
					return;
				}
			}
			else if(focused instanceof AShape)
			{
				AShape aShape = (AShape)focused;
				JTextPane shapeTextPane = aShape.textPane.textEditorInShape;

				if(shapeTextPane.getSelectedText() != null)
				{
					shapeTextPane.replaceSelection("");
					return;
				}
			}
			focused.setFocus(false);
			focused.removeFromSlide();
			focused = null;
		}
		repaint();
	}

	/**
	 * Bring component to front by inserting it first into the Slide
	 * @see #sendComponentToBack
	 */
	public void bringComponentToFront()
	{
		if(focused == null)
			return;
		//store components
		Vector tmpVector = new Vector();
		int compCount = getComponentCount();

		//bring component to bringToFront by inserting it first into the Slide
		tmpVector.add((Component)focused);
		for(int i=0; i < compCount; i++)
		{
			Component tmpComp = getComponent(i);
			if (!tmpComp.equals(focused))
				tmpVector.add(tmpComp);
		}
		removeAll();
		for(int i=0; i < compCount; i++)
			add((Component)(tmpVector.elementAt(i)));
		focused.setP(getPosition((Component)focused));

		repaint();
	}

	/**
	 * Sent component to back by inserting it last into the Slide
	 * @see #bringComponentToFront
	 */
	public void sendComponentToBack()
	{
		if(focused == null)
			return;
		Vector tmpVector = new Vector();
		int compCount = getComponentCount();
		//bring component to bringToFront by inserting it last into the Slide
		for(int i=0; i < compCount; i++)
		{
			Component tmpComp = getComponent(i);
			if (!tmpComp.equals(focused))
			tmpVector.add(tmpComp);
		}
		tmpVector.add((Component)focused);
		removeAll();
		for(int i=0; i < compCount; i++)
			add((Component)(tmpVector.elementAt(i)));
		focused.setP(getPosition((Component)focused));
		focused.setFocus(true);
		repaint();
	}

	/**
	 * Add a new text component to Slide
	 * @see AText
	 */
	public void addArangamText()
	{
		AText newText = new AText(Slide.this, arangam);
	}

	/**
	 * Add a new shape component to Slide
	 * @see AShape
	 */
	public void addArangamShape(int shapeType)
	{
		newShape = new AShape(this, arangam,shapeType);
	}

	/**
	 * Add a new image component to Slide
	 * @see AImage
	 */
	public void addArangamImage(java.net.URL file)
	{
		Image img = Toolkit.getDefaultToolkit().getImage(file);
		AImage newImage = new AImage(this, arangam, img);
	}

	/**
	 * Set fill effect for Slide background
	 * @see #paint
	 */
	public void setFillEffect(int effect)
	{
		fillEffect = effect;
		repaint();
	}

	/**
	 * Set "from color" for Slide background
	 * @see #paint
	 */
	public void setFromColor(Color color)
	{
		fromColor = color;
		repaint();
	}

	/**
	 * Set "to color" for Slide background
	 * @see #paint
	 */
	public void setToColor(Color color)
	{
		toColor = color;
		repaint();
	}

	/**
	 * Set Slide size to normal. Called after show
	 * @see Slideshow#setSlideshowSizeBack
	 */
	public void setSizeBack()
	{
		setBounds(0,0,Arangam.SLIDESHOW_WIDTH,Arangam.SLIDESHOW_HEIGHT);
		repaint();
	}
}

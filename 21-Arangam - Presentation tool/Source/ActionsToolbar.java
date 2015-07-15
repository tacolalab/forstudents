import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * Defines the <code>ActionsToolbar</code>. Composed by different button,
 * combo boxes that are shortcuts to common operation
 */
public class ActionsToolbar extends JPanel
{
	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** ActionsImpl class reference */
	private transient ConnectActions actionsConnection;

	/*** Main class reference */
	private transient Arangam arangam;

	/*** Initialise Slide number */
	private int slideNum=1;

	/*** Bilingual(Tamil and English) font used. */
	private Font bilingualFont = Arangam.bilingualFont;

	/*** Initialise font size */
	private int fontSizeb4 = 24;

	/** Button shortcut to create a new file */
	RolloverButton newButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("new")));

	/** Button shortcut to open a file */
	RolloverButton openButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("open")));

	/** Button shortcut to save the file */
	RolloverButton saveButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("save")));

	/** Button shortcut to print the file */
	RolloverButton printButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("print")));

	/** Button shortcut to perform "Cut" operation */
	RolloverButton cutButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("cut")));

	/** Button shortcut to perform "Copy" operation */
	RolloverButton copyButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("copy")));

	/** Button shortcut to perform "Paste" operation */
	RolloverButton pasteButton = new RolloverButton( ImagesLocator.getImage(Arangam.imageBundle.getString("paste")));

	/** Button shortcut to undo text action */
	AbstractButton undoButton;

	/** Button shortcut to redo text action */
	AbstractButton redoButton;

	/** Button shortcut to create a new Slide */
	RolloverButton newslideButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("slide")));

	/** Button shortcut to create a new picture */
	RolloverButton pictureButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("picture")));

	/** Button shortcut to create a new text */
	RolloverButton textboxButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("textBox")));

	/** Button shortcut to create a new shape */
	RolloverButton shapeButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("shape")));

	/** Button shortcut to go to next Slide */
	RolloverButton prevslideButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("prevSlide")));

	/** Button shortcut to go to previous Slide */
	RolloverButton nextslideButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("nextSlide")));

	/** Text field shortcut to go to specified Slide */
	javax.swing.JTextField slideNumText = new javax.swing.JTextField();

	/** Combo box indicate the sorter dimension*/
	javax.swing.JComboBox sorterDimComboBox = new javax.swing.JComboBox();

	//2nd row
	/** Get fonts from system */
	String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().
		getAvailableFontFamilyNames();

	/** Shortcut for font name selection */
	javax.swing.JComboBox fontComboBox = new JComboBox(fontNames);

	/** Shortcut for font size selection */
	javax.swing.JComboBox fontSizeComboBox = new javax.swing.JComboBox();

	/** Shortcut for bold action */
	RolloverToggleButton boldButton = new RolloverToggleButton(ImagesLocator.getImage(Arangam.imageBundle.getString("bold")));

	/** Shortcut for italic action */
	RolloverToggleButton italicButton = new RolloverToggleButton(ImagesLocator.getImage(Arangam.imageBundle.getString("italic")));

	/** Shortcut for underline action */
	RolloverToggleButton underlineButton = new RolloverToggleButton(ImagesLocator.getImage(Arangam.imageBundle.getString("underline")));

	/** Shortcut for subscript action */
	RolloverToggleButton subscriptButton = new RolloverToggleButton(ImagesLocator.getImage(Arangam.imageBundle.getString("subscript")));

	/** Shortcut for superscript action */
	RolloverToggleButton superscriptButton = new RolloverToggleButton(ImagesLocator.getImage(Arangam.imageBundle.getString("superscript")));

	/** Shortcut for "align left" action */
	RolloverToggleButton leftAlignButton = new RolloverToggleButton(ImagesLocator.getImage(Arangam.imageBundle.getString("alignLeft")));

	/** Shortcut for "align center" action */
	RolloverToggleButton centerAlignButton = new RolloverToggleButton(ImagesLocator.getImage(Arangam.imageBundle.getString("alignCenter")));

	/** Shortcut for "align right" action */
	RolloverToggleButton rightAlignButton = new RolloverToggleButton(ImagesLocator.getImage(Arangam.imageBundle.getString("alignRight")));

	/** Shortcut for "foreground color" action */
	RolloverButton fgColorButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("textColor")));

	/** Shortcut for "background color" action */
	RolloverButton bgColorButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("fillColor")));

	/** Shortcut for "outline color" action */
	RolloverButton outlineColorButton = new RolloverButton(ImagesLocator.getImage(Arangam.imageBundle.getString("outlineColor")));

	/**
	 * Create all the buttons, combo boxes for <code>ActionsToolbar</code>
	 */
	public ActionsToolbar(Arangam arangam)
	{
		this.arangam=arangam;

		//setting bounds of buttons
		int startX1=2,//start location x for first row components
			startX2=2,//start location x for second row components
			width = 25,//width of component
			height = 25,//height of component
			xInc = 25,//Increment in x direction after adding a component
			gapBefore = 2,//gap between components
			gapAfter = 4;//gap between components

		//setLayout(null);
		setLayout(new BorderLayout());

		//set bounds and border for toolbar
		Insets ins = getInsets();
		//setBounds(0,0,Arangam.A_WIDTH-5,61);
		//setBorder(BorderFactory.createRaisedBevelBorder());

		JToolBar fp = new JToolBar("Actions Toolbar");
		fp.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
		fp.setBorder(BorderFactory.createLineBorder(Color.gray));

		//set bounds for components and add it to toolbar
		//then calculate corresponding startX,startY and XInc
		newButton.setDefaultCapable(false);
		fp.add(newButton);

		openButton.setDefaultCapable(false);
		fp.add(openButton);

		saveButton.setDefaultCapable(false);
		fp.add(saveButton);

		fp.add(new ASeparator());

		printButton.setDefaultCapable(false);
		fp.add(printButton);

		fp.add(new ASeparator());
		startX1 += gapAfter;

		cutButton.setDefaultCapable(false);
		fp.add(cutButton);
		cutButton.setEnabled(false);

		copyButton.setDefaultCapable(false);
		fp.add(copyButton);
		copyButton.setEnabled(false);

		pasteButton.setDefaultCapable(false);
		fp.add(pasteButton);
		pasteButton.setEnabled(false);

		fp.add(new ASeparator());
		startX1 += gapAfter;

		TTUndoRedo undo = AText.undo;

		undoButton = undo.getUndo().getButton();
		fp.add(undoButton);

		redoButton = undo.getRedo().getButton();
		fp.add(redoButton);

		fp.add(new ASeparator());

		newslideButton.setDefaultCapable(false);
		fp.add(newslideButton);

		pictureButton.setDefaultCapable(false);
		fp.add(pictureButton);

		textboxButton.setDefaultCapable(false);
		fp.add(textboxButton);

		shapeButton.setDefaultCapable(false);
		fp.add(shapeButton);

		shapeButton.addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent me)
			{
				ShapePanelPopup shapePanelPopup = new ShapePanelPopup();
				shapePanelPopup.show(me.getComponent(),me.getX(),me.getY());
			}
		});

		fp.add(new ASeparator());

		prevslideButton.setDefaultCapable(false);
		fp.add(prevslideButton);

		nextslideButton.setDefaultCapable(false);
		fp.add(nextslideButton);

		//set values in Slide number textbox
		slideNumText.setText("");
		fp.add(slideNumText);

		sorterDimComboBox.setFont(Arangam.englishFont);
		sorterDimComboBox.addItem("2x2");
		sorterDimComboBox.addItem("3x3");
		sorterDimComboBox.addItem("4x4");
		sorterDimComboBox.setSelectedItem("2x2");
		fp.add(sorterDimComboBox);

		//-----------------second row starts here------------------------

		JToolBar sp = new JToolBar("Format Toolbar");
		sp.setLayout(new FlowLayout(FlowLayout.LEFT,2,2));
		sp.setBorder(BorderFactory.createLineBorder(Color.gray));

		fontComboBox.setBackground(Color.white);
		fontComboBox.setFont(bilingualFont);
		sp.add(fontComboBox);

		sp.add(fontSizeComboBox);
		fontSizeComboBox.setFont(bilingualFont);

		//set values in font size ComboBox
		fontSizeComboBox.addItem("8");
		fontSizeComboBox.addItem("9");
		fontSizeComboBox.addItem("10");
		fontSizeComboBox.addItem("12");
		fontSizeComboBox.addItem("14");
		fontSizeComboBox.addItem("16");
		fontSizeComboBox.addItem("18");
		fontSizeComboBox.addItem("20");
		fontSizeComboBox.addItem("24");
		fontSizeComboBox.addItem("28");
		fontSizeComboBox.addItem("32");
		fontSizeComboBox.addItem("36");
		fontSizeComboBox.addItem("40");
		fontSizeComboBox.addItem("44");
		fontSizeComboBox.addItem("48");
		fontSizeComboBox.addItem("54");
		fontSizeComboBox.addItem("60");
		fontSizeComboBox.addItem("66");
		fontSizeComboBox.addItem("72");
		fontSizeComboBox.addItem("80");
		fontSizeComboBox.addItem("88");
		fontSizeComboBox.addItem("96");
		fontSizeComboBox.setSelectedItem("24");
		fontSizeComboBox.setEditable(true);

		sp.add(new ASeparator());

		sp.add(boldButton);
		sp.add(italicButton);
		sp.add(underlineButton);
		sp.add(subscriptButton);
		sp.add(superscriptButton);

		sp.add(new ASeparator());

		sp.add(leftAlignButton);
		sp.add(centerAlignButton);
		sp.add(rightAlignButton);

		sp.add(new ASeparator());

		fgColorButton.setBackground(Color.black);
		sp.add(fgColorButton);
		sp.add(bgColorButton);
		sp.add(outlineColorButton);

		add(fp,BorderLayout.NORTH);
		add(sp,BorderLayout.SOUTH);

		//register listeners
		SymAction lSymAction = new SymAction();
		newButton.addActionListener(lSymAction);
		openButton.addActionListener(lSymAction);
		saveButton.addActionListener(lSymAction);
		printButton.addActionListener(lSymAction);
		cutButton.addActionListener(lSymAction);
		copyButton.addActionListener(lSymAction);
		pasteButton.addActionListener(lSymAction);
		newslideButton.addActionListener(lSymAction);
		pictureButton.addActionListener(lSymAction);
		textboxButton.addActionListener(lSymAction);
		prevslideButton.addActionListener(lSymAction);
		nextslideButton.addActionListener(lSymAction);
		slideNumText.addActionListener(lSymAction);
		sorterDimComboBox.addActionListener(lSymAction);

		fontComboBox.addActionListener(lSymAction);
		fontSizeComboBox.addActionListener(lSymAction);
		boldButton.addActionListener(lSymAction);
		italicButton.addActionListener(lSymAction);
		underlineButton.addActionListener(lSymAction);
		subscriptButton.addActionListener(lSymAction);
		superscriptButton.addActionListener(lSymAction);
		leftAlignButton.addActionListener(lSymAction);
		centerAlignButton.addActionListener(lSymAction);
		rightAlignButton.addActionListener(lSymAction);
		fgColorButton.addActionListener(lSymAction);
		bgColorButton.addActionListener(lSymAction);
		outlineColorButton.addActionListener(lSymAction);

	}

	/**
	 * Connects the class to the <code>ConnectActions</code> interface.
	 * @param actionsConnection interface to access <code>ActionsImpl</code> methods
	 */
	public void getInterface(ConnectActions actionsConnection)
	{
		this.actionsConnection = actionsConnection;
	}

	/**
	 * Sets the locale for components depends on the current Locale.
	 */
	public void setLocale()
	{
		wordBundle = Arangam.wordBundle;
		newButton.setToolTipText(wordBundle.getString("new"));
		openButton.setToolTipText(wordBundle.getString("open"));
		saveButton.setToolTipText(wordBundle.getString("save"));
		printButton.setToolTipText(wordBundle.getString("print"));
		cutButton.setToolTipText(wordBundle.getString("cut"));
		copyButton.setToolTipText(wordBundle.getString("copy"));
		pasteButton.setToolTipText(wordBundle.getString("paste"));
		undoButton.setToolTipText(wordBundle.getString("undo"));
		redoButton.setToolTipText(wordBundle.getString("redo"));
		newslideButton.setToolTipText(wordBundle.getString("newSlide"));
		pictureButton.setToolTipText(wordBundle.getString("picture"));
		textboxButton.setToolTipText(wordBundle.getString("textBox"));
		shapeButton.setToolTipText(wordBundle.getString("shape"));
		prevslideButton.setToolTipText(wordBundle.getString("prevSlide"));
		nextslideButton.setToolTipText(wordBundle.getString("nextSlide"));
		slideNumText.setToolTipText(wordBundle.getString("slideNum"));

		fontComboBox.setToolTipText(wordBundle.getString("font"));
		fontSizeComboBox.setToolTipText(wordBundle.getString("fontSize"));

		italicButton.setToolTipText(wordBundle.getString("italic"));
		boldButton.setToolTipText(wordBundle.getString("bold"));
		italicButton.setToolTipText(wordBundle.getString("italic"));
		underlineButton.setToolTipText(wordBundle.getString("underline"));
		subscriptButton.setToolTipText(wordBundle.getString("subscript"));
		superscriptButton.setToolTipText(wordBundle.getString("superscript"));
		leftAlignButton.setToolTipText(wordBundle.getString("alignLeft"));
		centerAlignButton.setToolTipText(wordBundle.getString("alignCenter"));
		rightAlignButton.setToolTipText(wordBundle.getString("alignRight"));
		fgColorButton.setToolTipText(wordBundle.getString("textColor"));
		bgColorButton.setToolTipText(wordBundle.getString("fillColor"));
		outlineColorButton.setToolTipText(wordBundle.getString("outlineColor"));

	}

	/**
	 * Activate the menu items.
	 * @param activate If <code>true</code>, the menu items are enabled;
	 * otherwise disabled.
	 */
	public void activate(boolean activate)
	{
		saveButton.setEnabled(activate);
		printButton.setEnabled(activate);

		newslideButton.setEnabled(activate);
		textboxButton.setEnabled(activate);
		pictureButton.setEnabled(activate);
		shapeButton.setEnabled(activate);
		prevslideButton.setEnabled(activate);
		nextslideButton.setEnabled(activate);
		slideNumText.setEnabled(activate);

		fontComboBox.setEnabled(activate);
		fontSizeComboBox.setEnabled(activate);
		boldButton.setEnabled(activate);
		italicButton.setEnabled(activate);
		underlineButton.setEnabled(activate);
		subscriptButton.setEnabled(activate);
		superscriptButton.setEnabled(activate);
		leftAlignButton.setEnabled(activate);
		centerAlignButton.setEnabled(activate);
		rightAlignButton.setEnabled(activate);
		fgColorButton.setEnabled(activate);
		bgColorButton.setEnabled(activate);
		outlineColorButton.setEnabled(activate);
	}

	public void setUndoEnabled(boolean enable)
	{
		undoButton.setEnabled(enable);
		redoButton.setEnabled(enable);
	}

	/**
	 * Enables the Cut, Copy, Paste button depends on the <code>Clipboard</code> content.
	 * @see ConnectActions#isPasteAvailable
	 */
	public void setCCPEnabled()
	{
		ConnectComponent focused = actionsConnection.getFocusedComponent();
		//if any component has focus, then enable cut and copy buttons
		if(focused != null)
		{
			cutButton.setEnabled(true);
			copyButton.setEnabled(true);
		}
		//else disable
		else
		{
			cutButton.setEnabled(false);
			copyButton.setEnabled(false);
		}
		//pasting depends on clipboard content
		pasteButton.setEnabled(actionsConnection.isPasteAvailable());

	}

	/**
	 * Enables the Navigation Button(<code>prevSlide</code> and <code>nextSlide</code>).
	 * @param prev if <code>true</code> enables the <code>prevSlide</code> Button
	 * @param next if <code>true</code> enables the <code>nextSlide</code> Button
	 */
	public void setEnablePrevNext(boolean prev, boolean next)
	{
		prevslideButton.setEnabled(prev);
		nextslideButton.setEnabled(next);
	}

	/**
	 * Sets the current <code>Slide</code> number.
	 * @param myNum current <code>Slide</code> number to be set.
	 */
	public void setSlideNumInToolbar(int myNum)
	{
		String tmpString = String.valueOf(myNum);
		//show nothing if no Slide
		if (tmpString.equals("0"))
			slideNumText.setText("");
		else
			slideNumText.setText(tmpString);
	}

	/**
	 * Gets the current font.
	 * @return current font.
	 */
	public String getFontInToolbar()
	{
		return this.fontComboBox.getSelectedItem().toString();
	}

	/**
	 * Returns the attributes to be applied to text component.
	 * @return the attributes to be applied to component.
	 */
	public MutableAttributeSet getAttributes()
	{
		MutableAttributeSet attributeSet = new SimpleAttributeSet();

		StyleConstants.setFontFamily(attributeSet,fontComboBox.getSelectedItem().toString());

		Integer fontSize = new Integer(fontSizeComboBox.getSelectedItem().toString());
		StyleConstants.setFontSize(attributeSet,fontSize.intValue());

		StyleConstants.setBold(attributeSet,boldButton.isSelected());
		StyleConstants.setItalic(attributeSet,boldButton.isSelected());
		StyleConstants.setUnderline(attributeSet,italicButton.isSelected());
		StyleConstants.setSubscript(attributeSet,subscriptButton.isSelected());
		StyleConstants.setSuperscript(attributeSet,superscriptButton.isSelected());
		StyleConstants.setForeground(attributeSet,fgColorButton.getBackground());

		if(leftAlignButton.isEnabled())
		{
			StyleConstants.setAlignment(attributeSet,StyleConstants.ALIGN_LEFT);
		}
		else if(centerAlignButton.isEnabled())
		{
			StyleConstants.setAlignment(attributeSet,StyleConstants.ALIGN_CENTER);
		}
		else if(rightAlignButton.isEnabled())
		{
			StyleConstants.setAlignment(attributeSet,StyleConstants.ALIGN_RIGHT);
		}

		return attributeSet;
	}

	/**
	 * Sets the font toolbar with the given attributes.
	 * @param a the Attribute to be set.
	 */
	public void setFontToolBar(AttributeSet a)
	{
		//get the attributes from the AttibuteSet a
		//and set it to corresponding components
		fontSizeComboBox.setSelectedItem(new Integer(StyleConstants.
			getFontSize(a)));
		fontComboBox.setSelectedItem(StyleConstants.getFontFamily(a));

		boldButton.setSelected(StyleConstants.isBold(a));
		italicButton.setSelected(StyleConstants.isItalic(a));
		underlineButton.setSelected(StyleConstants.isUnderline(a));
		subscriptButton.setSelected(StyleConstants.isSubscript(a));
		superscriptButton.setSelected(StyleConstants.isSuperscript(a));

		int alignment = StyleConstants.getAlignment(a);
		switch(alignment)
		{
			case StyleConstants.ALIGN_LEFT:
				leftAlignButton.setSelected(true);
				centerAlignButton.setSelected(false);
				rightAlignButton.setSelected(false);
				break;
			case StyleConstants.ALIGN_CENTER:
				leftAlignButton.setSelected(false);
				centerAlignButton.setSelected(true);
				rightAlignButton.setSelected(false);
				break;
			case StyleConstants.ALIGN_RIGHT:
				leftAlignButton.setSelected(false);
				centerAlignButton.setSelected(false);
				rightAlignButton.setSelected(true);
				break;
			case StyleConstants.ALIGN_JUSTIFIED:
				leftAlignButton.setSelected(false);
				centerAlignButton.setSelected(false);
				rightAlignButton.setSelected(false);
		};
	}

	/**
	 * Sets the default setting for font toolbar.
	 */
	public void setDefaultFontToolBar()
	{
		/*Arangam.newMattr=new SimpleAttributeSet();

		fontComboBox.setSelectedIndex(0);		//select first font
		leftAlignButton.setSelected(true);

		boldButton.setSelected(false);
		italicButton .setSelected(false);
		underlineButton.setSelected(false);
		subscriptButton.setSelected(false);
		superscriptButton.setSelected(false);
		centerAlignButton.setSelected(false);
		rightAlignButton.setSelected(false);*/
		//do nothing
	}

	/**
	 * Pops up a menu to select a shape
	 */
	private class ShapePanelPopup extends JPopupMenu implements ActionListener
	{

		/*** Menu items for different shapes */
		private JMenuItem diamond,oval,rectangle,roundRectangle,square;

		/**
		 * Construct shape menu items and add them
		 */
		private ShapePanelPopup()
		{
			super();
			//construct them
			diamond = new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("diamond")));
			oval = new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("oval")));
			rectangle = new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("rectangle")));
			roundRectangle = new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("roundRectangle")));
			square = new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("square")));

			//add them to popup menu
			add(diamond);
			add(oval);
			add(rectangle);
			add(roundRectangle);
			add(square);

			//add action listeners to menu items
			diamond.addActionListener(this);
			oval.addActionListener(this);
			rectangle.addActionListener(this);
			roundRectangle.addActionListener(this);
			square.addActionListener(this);
		}

		/**
		 * Invoked when an action occurs.When a shape menu item is clicked.
		 * @see ConnectActions#ShapeInsert
		 */
		public void actionPerformed(ActionEvent ae)
		{
			//the corresponding function is called when any
			//shape menu item is clicked
			Object object = ae.getSource();
			if(object==diamond)
			{
				actionsConnection.ShapeInsert(AShape.DIAMOND);
			}
			else if(object==oval)
			{
				actionsConnection.ShapeInsert(AShape.OVAL);
			}
			else if(object==rectangle)
			{
				actionsConnection.ShapeInsert(AShape.RECTANGLE);
			}
			else if(object==roundRectangle)
			{
				actionsConnection.ShapeInsert(AShape.ROUND_RECTANGLE);
			}
			else if(object==square)
			{
				actionsConnection.ShapeInsert(AShape.SQUARE);
			}
		}

	}

	/**
	 * ActionListener for toolbar components. For every
	 * action the corresponding function in <code>ConnectActions</code> is invoked
	 */
	private class SymAction implements java.awt.event.ActionListener
	{

		/**
		 * Invoked when an action occurs that is when a component in toolbar
		 * is clicked or selected.
		 * @see ConnectActions#ShapeInsert
		 */
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			//the corresponding function is called when the
			//component is clicked or selected
			Object object = event.getSource();
			if (object == newButton)
				newButton_actionPerformed(event);
			else if (object == openButton)
				openButton_actionPerformed(event);
			else if (object == saveButton)
				saveButton_actionPerformed(event);
			else if (object == printButton)
				printButton_actionPerformed(event);
			else if (object == cutButton)
				cutButton_actionPerformed(event);
			else if (object == copyButton)
				copyButton_actionPerformed(event);
			else if (object == pasteButton)
				pasteButton_actionPerformed(event);
			else if (object == newslideButton)
				newslideButton_actionPerformed(event);
			else if (object == pictureButton)
				pictureButton_actionPerformed(event);
			else if (object == textboxButton)
				textboxButton_actionPerformed(event);
			else if (object == prevslideButton)
				prevslideButton_actionPerformed(event);
			else if (object == nextslideButton)
				nextslideButton_actionPerformed(event);
			else if (object == slideNumText)
				slideNumText_actionPerformed(event);
			else if (object == sorterDimComboBox)
				sorterDimComboBox_actionPerformed(event);

			else if (object == fontComboBox)
				fontComboBox_actionPerformed(event);
			else if (object == fontSizeComboBox)
				fontSizeComboBox_actionPerformed(event);
			else if (object == boldButton)
				boldButton_actionPerformed(event);
			else if (object == italicButton)
				italicButton_actionPerformed(event);
			else if (object == underlineButton)
				underlineButton_actionPerformed(event);
			else if (object == subscriptButton)
				subscriptButton_actionPerformed(event);
			else if (object == superscriptButton)
				superscriptButton_actionPerformed(event);
			else if (object == leftAlignButton)
				leftAlignButton_actionPerformed(event);
			else if (object == centerAlignButton)
				centerAlignButton_actionPerformed(event);
			else if (object == rightAlignButton)
				rightAlignButton_actionPerformed(event);
			else if (object == fgColorButton)
				fgColorButton_actionPerformed(event);
			else if (object == bgColorButton)
				bgColorButton_actionPerformed(event);
			else if (object == outlineColorButton)
				outlineColorButton_actionPerformed(event);
		}
	}

	/**
	 * Action for "new" button
	 * @see ConnectActions#NewFile
	 */
	private void newButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.NewFile();
	}

	/**
	 * Action for "open" button
	 * @see ConnectActions#OpenFile
	 */
	private void openButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.OpenFile();
	}

	/**
	 * Action for "save" button
	 * @see ConnectActions#SaveFile
	 */
	private void saveButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.SaveFile();
	}

	/**
	 * Action for "print" button
	 * @see ConnectActions#PrintFile
	 */
	private void printButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.PrintFile();
	}

	/**
	 * Action for "cut" button
	 * @see ConnectActions#CutEdit
	 */
	private void cutButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.CutEdit();
	}

	/**
	 * Action for "copy" button
	 * @see ConnectActions#CopyEdit
	 */
	private void copyButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.CopyEdit();
	}

	/**
	 * Action for "paste" button
	 * @see ConnectActions#PasteEdit
	 */
	private void pasteButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.PasteEdit();
	}

	/**
	 * Action for "new Slide" button
	 * @see ConnectActions#NewSlideInsert
	 */
	private void newslideButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.NewSlideInsert();
	}

	/**
	 * Action for "picture" button
	 * @see ConnectActions#PictureInsert
	 */
	private void pictureButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.PictureInsert();
	}

	/**
	 * Action for "text box" button
	 * @see ConnectActions#TextBoxInsert
	 */
	private void textboxButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.TextBoxInsert();
	}

	/**
	 * Action for "previous Slide" button
	 * @see ConnectActions#PrevSlideView
	 */
	private void prevslideButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.PrevSlideView();
	}

	/**
	 * Action for "next Slide" button
	 * @see ConnectActions#NextSlideView
	 */
	private void nextslideButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.NextSlideView();
	}

	/**
	 * Action for "Slide number" text field
	 * @see ConnectActions#setCurrentSlideNum
	 */
	private void slideNumText_actionPerformed(java.awt.event.ActionEvent event)
	{
		JTextField mySlide = (JTextField) event.getSource();
		String slideStr = new String(mySlide.getText());
		actionsConnection.setCurrentSlideNum(slideStr);
	}

	/**
	 * Action for "sorter" combo box
	 * @see ConnectActions#setCurrentSorterDim
	 */
	private void sorterDimComboBox_actionPerformed(java.awt.event.ActionEvent event)
	{
		JComboBox cb = (JComboBox)event.getSource();
		int dimension = cb.getSelectedIndex() + 2;
		actionsConnection.setCurrentSorterDim(dimension);
	}

	/**
	 * Action for "font" combo box
	 * @see ConnectActions#updateTextAttr
	 */
	private void fontComboBox_actionPerformed(java.awt.event.ActionEvent event)
	{
		JComboBox cb = (JComboBox) event.getSource();
		String fontFamily = (String)cb.getSelectedItem();
		MutableAttributeSet mattr=new SimpleAttributeSet();
		StyleConstants.setFontFamily(mattr,fontFamily);
		actionsConnection.updateTextAttr(mattr,false);
	}

	/**
	 * Action for "font size" combo box
	 * @see ConnectActions#updateTextAttr
	 */
	private void fontSizeComboBox_actionPerformed(java.awt.event.ActionEvent event)
	{
		JComboBox cb = (JComboBox) event.getSource();
		int fontSize = -1;
		try
		{
			fontSize = Integer.parseInt(cb.getSelectedItem().toString());
		}
		catch(NumberFormatException e)
		{
			//select the previously selected font size
			fontSize = fontSizeb4;
			cb.setSelectedItem(new Integer(fontSize).toString());
		}
		//remember the font size
		fontSizeb4 = fontSize;
		MutableAttributeSet mattr = new SimpleAttributeSet();

		StyleConstants.setFontSize(mattr,fontSize);
		actionsConnection.updateTextAttr(mattr,false);
	}

	/**
	 * Action for "bold" button
	 * @see ConnectActions#updateTextAttr
	 */
	private void boldButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		MutableAttributeSet mattr=new SimpleAttributeSet();
		StyleConstants.setBold(mattr,boldButton.isSelected());
		actionsConnection.updateTextAttr(mattr,false);
	}

	/**
	 * Action for "italic" button
	 * @see ConnectActions#updateTextAttr
	 */
	private void italicButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		MutableAttributeSet mattr=new SimpleAttributeSet();
		StyleConstants.setItalic(mattr,italicButton.isSelected());
		actionsConnection.updateTextAttr(mattr,false);
	}

	/**
	 * Action for "underline" button
	 * @see ConnectActions#updateTextAttr
	 */
	private void underlineButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		MutableAttributeSet mattr=new SimpleAttributeSet();
		StyleConstants.setUnderline(mattr,underlineButton.isSelected());
		actionsConnection.updateTextAttr(mattr,false);
	}

	/**
	 * Action for "subscript" button
	 * @see ConnectActions#updateTextAttr
	 */
	private void subscriptButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		MutableAttributeSet mattr=new SimpleAttributeSet();
		StyleConstants.setSubscript(mattr,subscriptButton.isSelected());
		actionsConnection.updateTextAttr(mattr,false);
	}

	/**
	 * Action for "superscript" button
	 * @see ConnectActions#updateTextAttr
	 */
	private void superscriptButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		MutableAttributeSet mattr=new SimpleAttributeSet();
		StyleConstants.setSuperscript(mattr,superscriptButton.isSelected());
		actionsConnection.updateTextAttr(mattr,false);
	}

	/**
	 * Action for "align left" button
	 * @see ConnectActions#updateTextAttr
	 */
	private void leftAlignButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		centerAlignButton.setSelected(false);
		rightAlignButton.setSelected(false);
		MutableAttributeSet mattr=new SimpleAttributeSet();
		StyleConstants.setAlignment(mattr,StyleConstants.ALIGN_LEFT);
		actionsConnection.updateTextAttr(mattr,true);
	}

	/**
	 * Action for "align center" button
	 * @see ConnectActions#updateTextAttr
	 */
	private void centerAlignButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		leftAlignButton.setSelected(false);
		rightAlignButton.setSelected(false);
		MutableAttributeSet mattr=new SimpleAttributeSet();
		StyleConstants.setAlignment(mattr,StyleConstants.ALIGN_CENTER);
		actionsConnection.updateTextAttr(mattr,true);
	}

	/**
	 * Action for "align right" button
	 * @see ConnectActions#updateTextAttr
	 */
	private void rightAlignButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		centerAlignButton.setSelected(false);
		leftAlignButton.setSelected(false);
		MutableAttributeSet mattr=new SimpleAttributeSet();
		StyleConstants.setAlignment(mattr,StyleConstants.ALIGN_RIGHT);
		actionsConnection.updateTextAttr(mattr,true);
	}

	/**
	 * Action for "foreground" button
	 * @see ConnectActions#updateTextAttr
	 */
	private void fgColorButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		JColorChooser foreGndColorChooser = new JColorChooser
			(fgColorButton.getBackground());

		Color foreGndColor = foreGndColorChooser.showDialog(
			this,"Choose Color",Color.black);
		if (foreGndColor != null)
		{
			MutableAttributeSet mattr=new SimpleAttributeSet();
			StyleConstants.setForeground(mattr,foreGndColor);
			actionsConnection.updateTextAttr(mattr,false);
			fgColorButton.setBackground(foreGndColor);
		}
	}

	/**
	 * Action for "background" button
	 * @see ConnectActions#updateTextAttr
	 */
	private void bgColorButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		if(arangam.slideshow1.getCurrentSlide().focused != null)
		{
			Color backGndColor = JColorChooser.showDialog(
			this,"Choose Color",Color.white);
			if (backGndColor != null)
			{
				MutableAttributeSet mattr=new SimpleAttributeSet();
				actionsConnection.setBgColor(backGndColor);
				bgColorButton.setBackground(backGndColor);
			}
		}
	}

	/**
	 * Action for "outline" button
	 * @see ConnectActions#updateTextAttr
	 */
	private void outlineColorButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		if(arangam.slideshow1.getCurrentSlide().focused != null)
		{
			Color outlineColor = JColorChooser.showDialog(
			this,"Choose Color",Color.white);
			if (outlineColor != null)
			{
				actionsConnection.setOutlineColor(outlineColor);
				outlineColorButton.setBackground(outlineColor);
			}
		}
	}
}

import java.beans.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class InsertMenu extends javax.swing.JMenu
{

	/*** ActionsImpl class reference */
 	private transient ConnectActions actionsConnection;

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** Menu Item for "New Slide" action */
	private javax.swing.JMenuItem NewSlideItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("newSlide")));

	/*** Separates  menu items */
	private javax.swing.JSeparator JSeparator1 = new javax.swing.JSeparator();

	/*** Menu Item for "Picture" action */
	private javax.swing.JMenuItem PictureItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("picture")));

	/*** Menu Item for "Textbox" action */
	private javax.swing.JMenuItem TextBoxItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("textBox")));

	/*** Menu Item for "Shape" action */
	private javax.swing.JMenu ShapeItem = new javax.swing.JMenu();

	/*** Menu Item for "Diamond" action */
	private	javax.swing.JMenuItem diamond = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("diamond")));

	/*** Menu Item for "Oval" action */
	private	javax.swing.JMenuItem oval = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("oval")));

	/*** Menu Item for "Rectangle" action */
	private	javax.swing.JMenuItem rectangle = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("rectangle")));

	/*** Menu Item for "Round Rectangle" action */
	private	javax.swing.JMenuItem roundRectangle = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("roundRectangle")));

	/*** Menu Item for "Square" action */
	private	javax.swing.JMenuItem square = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("square")));

	/*** Menu Item for "Right Triangle" action */
	private	javax.swing.JMenuItem rightTriangle = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("rightTriangle")));

	/*** Menu Item for "Isosceles Triangle" action */
	private	javax.swing.JMenuItem isoscelesTriangle = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("isoscelesTriangle")));

	/**
	 * Create all the menu items for Insert menu
	 */
	public InsertMenu()
	{
		//set bilingual font
		bilingualFont = Arangam.bilingualFont;
		setFont(bilingualFont);

		//set font, mnemonic, accelerator for menu item and
		//add it to format menu
		NewSlideItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_M, java.awt.Event.CTRL_MASK));
		NewSlideItem.setFont(bilingualFont);
		NewSlideItem.setMnemonic((int)'N');
		add(NewSlideItem);

		add(JSeparator1);

		PictureItem.setFont(bilingualFont);
		PictureItem.setMnemonic((int)'P');
		add(PictureItem);

		TextBoxItem.setFont(bilingualFont);
		TextBoxItem.setMnemonic((int)'T');
		TextBoxItem.setFont(bilingualFont);
		add(TextBoxItem);

		ShapeItem.setFont(bilingualFont);
		ShapeItem.setMnemonic((int)'S');
		ShapeItem.setFont(bilingualFont);

		diamond.setFont(bilingualFont);
		diamond.setMnemonic((int)'D');

		oval.setFont(bilingualFont);
		oval.setMnemonic((int)'O');

		rectangle.setFont(bilingualFont);
		rectangle.setMnemonic((int)'R');

		roundRectangle.setFont(bilingualFont);
		roundRectangle.setMnemonic((int)'U');

		square.setFont(bilingualFont);
		square.setMnemonic((int)'S');

		rightTriangle.setFont(bilingualFont);
		isoscelesTriangle.setFont(bilingualFont);

		ShapeItem.add(diamond);
		ShapeItem.add(oval);
		ShapeItem.add(rectangle);
		ShapeItem.add(roundRectangle);
		ShapeItem.add(square);
		add(ShapeItem);

		//set layout and size
		setLayout(null);
		Insets ins = getInsets();
		setSize(ins.left + ins.right + 430,ins.top + ins.bottom + 270);

		//register listeners
		SymAction lSymAction = new SymAction();
		NewSlideItem.addActionListener(lSymAction);
		PictureItem.addActionListener(lSymAction);
		TextBoxItem.addActionListener(lSymAction);
		diamond.addActionListener(lSymAction);
		oval.addActionListener(lSymAction);
		rectangle.addActionListener(lSymAction);
		roundRectangle.addActionListener(lSymAction);
		square.addActionListener(lSymAction);
		rightTriangle.addActionListener(lSymAction);
		isoscelesTriangle.addActionListener(lSymAction);
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
	 * Sets the locale for MenuItem depends on the current Locale.
	 */
	public void setLocale()
	{
		wordBundle = Arangam.wordBundle;
		setText(wordBundle.getString("insert"));
		NewSlideItem.setText(wordBundle.getString("newSlide"));
		PictureItem.setText(wordBundle.getString("picture"));
		TextBoxItem.setText(wordBundle.getString("textBox"));
		ShapeItem.setText(wordBundle.getString("shape"));
		diamond.setText(wordBundle.getString("diamond"));
		oval.setText(wordBundle.getString("oval"));
		rectangle.setText(wordBundle.getString("rectangle"));
		roundRectangle.setText(wordBundle.getString("roundRectangle"));
		square.setText(wordBundle.getString("square"));
		rightTriangle.setText(wordBundle.getString("rightTriangle"));
		isoscelesTriangle.setText(wordBundle.getString("isoscelesTriangle"));
	}

	/**
	 * Activate the MenuItems
	 * @param activate If <code>true</code>, this MenuItems are enabled;
	 * otherwise disabled.
	 */
	public void activate(boolean activate)
	{
		NewSlideItem.setEnabled(activate);
		PictureItem.setEnabled(activate);
		TextBoxItem.setEnabled(activate);
		ShapeItem.setEnabled(activate);
	}

	/**
	 * Listen for action events in menu components. For every
	 * action the corresponding function in ConnectActions is invoked
	 */
	private class SymAction implements java.awt.event.ActionListener
	{
		/**
		 * Invoked when an action occurs - when a menu item in menu
		 * is clicked/selected.
		 */
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == NewSlideItem)
				NewSlideItem_actionPerformed(event);
			else if(object == PictureItem)
				PictureItem_actionPerformed(event);
			else if (object == TextBoxItem)
				TextBoxItem_actionPerformed(event);

			else if (object == diamond)
				diamond_actionPerformed(event);
			else if (object == oval)
				oval_actionPerformed(event);
			else if (object == rectangle)
				rectangle_actionPerformed(event);
			else if (object == roundRectangle)
				roundRectangle_actionPerformed(event);
			else if (object == square)
				square_actionPerformed(event);
			else if (object == rightTriangle)
				rightTriangle_actionPerformed(event);
			else if (object == isoscelesTriangle)
				isoscelesTriangle_actionPerformed(event);
		}
	}

	/**
	 * Action for "New Slide" menu item
	 * @see ConnectActions#NewSlideInsert
	 */
	private void NewSlideItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.NewSlideInsert();
	}

	/**
	 * Action for "Picture" menu item
	 * @see ConnectActions#PictureInsert
	 */
	private void PictureItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.PictureInsert();
	}

	/**
	 * Action for "Textbox" menu item
	 * @see ConnectActions#TextBoxInsert
	 */
	private void TextBoxItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.TextBoxInsert();
	}

	/**
	 * Action for "Diamond" menu item
	 * @see ConnectActions#ShapeInsert
	 */
	private void diamond_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ShapeInsert(AShape.DIAMOND);
	}

	/**
	 * Action for "Oval" menu item
	 * @see ConnectActions#ShapeInsert
	 */
	private void oval_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ShapeInsert(AShape.OVAL);
	}

	/**
	 * Action for "Rectangle" menu item
	 * @see ConnectActions#FontFormat
	 */
	private void rectangle_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ShapeInsert(AShape.RECTANGLE);
	}

	/**
	 * Action for "Round Rectangle" menu item
	 * @see ConnectActions#FontFormat
	 */
	private void roundRectangle_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ShapeInsert(AShape.ROUND_RECTANGLE);
	}

	/**
	 * Action for "Square" menu item
	 * @see ConnectActions#FontFormat
	 */
	private void square_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ShapeInsert(AShape.SQUARE);
	}

	/**
	 * Action for "Right Angle Ttriangle" menu item
	 * @see ConnectActions#FontFormat
	 */
	private void rightTriangle_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ShapeInsert(AShape.RIGHT_TRIANGLE);
	}

	/**
	 * Action for "Isosceles Triangle" menu item
	 * @see ConnectActions#FontFormat
	 */
	private void isoscelesTriangle_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ShapeInsert(AShape.ISOCELES_TRIANGLE);
	}
}

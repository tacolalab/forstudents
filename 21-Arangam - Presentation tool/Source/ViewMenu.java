import java.beans.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Class that Defines all the items in the view menu column.
 */
public class ViewMenu extends javax.swing.JMenu
{
	/*** Reference to ConnectActions interface */
	private transient ConnectActions actionsConnection;

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/*** Gets the words in current language - Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** Menu Item for view mode "Slide" */
	private javax.swing.JMenuItem SlideItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("slide")));

	/*** Menu Item for view mode "Sorter" */
	private javax.swing.JMenuItem SlideSorterItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("slideSorter")));

	/*** Menu Item for view mode "Show" */
	private javax.swing.JMenuItem SlideShowItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("slideShow")));

	/*** Separator for menu item */
	private javax.swing.JSeparator JSeparator1 = new javax.swing.JSeparator();

	/*** Menu Item for actions toolbar visibility */
	private javax.swing.JCheckBoxMenuItem ActionsToolbarItem = new javax.swing.JCheckBoxMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("empty")));

	/*** Menu Item for view mode toolbar visibility */
	private javax.swing.JCheckBoxMenuItem ViewModeToolbarItem = new javax.swing.JCheckBoxMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("empty")));

	/*** Menu Item for ruler visibility */
	private javax.swing.JCheckBoxMenuItem RulerItem =  new javax.swing.JCheckBoxMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("empty")));

	/*** Separator for menu item */
	private javax.swing.JSeparator JSeparator2 = new javax.swing.JSeparator();

	/*** Menu Item to "Go to Slide" */
	private javax.swing.JMenuItem GotoSlideItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("empty")));

	/*** Menu Item for navigating to next Slide */
	private javax.swing.JMenuItem NextSlideItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("nextSlide")));

	/*** Menu Item for navigating to previous Slide */
	private javax.swing.JMenuItem PrevSlideItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("prevSlide")));

	/*** Menu Item for closing the show*/
	private javax.swing.JMenuItem EndShowItem = new javax.swing.JMenuItem(
		ImagesLocator.getImage(Arangam.imageBundle.getString("empty")));

	/**
	 * Create all the menu items for view menu and register listeners
	 */
	public ViewMenu()
	{
		//set bilingual font
		bilingualFont = Arangam.bilingualFont;
		setFont(bilingualFont);

		//set font, mnemonic, accelerator for menu item and
		//add it to view menu
		SlideItem.setMnemonic((int)'S');
		SlideItem.setFont(bilingualFont);
		add(SlideItem);

		SlideSorterItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_F4, 0));
		SlideSorterItem.setMnemonic((int)'D');
		SlideSorterItem.setFont(bilingualFont);
		//add(SlideSorterItem);

		SlideShowItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_F5, 0));
		SlideShowItem.setMnemonic((int)'W');
		SlideShowItem.setFont(bilingualFont);
		add(SlideShowItem);

		add(JSeparator1);

		ActionsToolbarItem.setMnemonic((int)'A');
		ActionsToolbarItem.setFont(bilingualFont);
		add(ActionsToolbarItem);

		ViewModeToolbarItem.setMnemonic((int)'V');
		ViewModeToolbarItem.setFont(bilingualFont);
		add(ViewModeToolbarItem);

		RulerItem.setMnemonic((int)'R');
		RulerItem.setFont(bilingualFont);
		add(RulerItem);

		add(JSeparator2);

		GotoSlideItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
		java.awt.event.KeyEvent.VK_G, java.awt.Event.CTRL_MASK));
		GotoSlideItem.setMnemonic((int)'G');
		GotoSlideItem.setFont(bilingualFont);
		add(GotoSlideItem);

		NextSlideItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_PAGE_DOWN, 0));
		NextSlideItem.setMnemonic((int)'N');
		NextSlideItem.setFont(bilingualFont);
		add(NextSlideItem);

		PrevSlideItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_PAGE_UP, 0));
		PrevSlideItem.setMnemonic((int)'P');
		PrevSlideItem.setFont(bilingualFont);
		add(PrevSlideItem);

		EndShowItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
			java.awt.event.KeyEvent.VK_ESCAPE, 0));
		add(EndShowItem);
		//make it not visible
		//used only in Show.java
		EndShowItem.setVisible(false);

		//set layout and size
		setLayout(null);
		Insets ins = getInsets();
		setSize(ins.left + ins.right + 430,ins.top + ins.bottom + 270);

		//default selection
		ActionsToolbarItem.setSelected(true);
		ViewModeToolbarItem.setSelected(true);
		RulerItem.setSelected(true);

		//register listeners
		SymAction lSymAction = new SymAction();
		SlideItem.addActionListener(lSymAction);
		SlideSorterItem.addActionListener(lSymAction);
		SlideShowItem.addActionListener(lSymAction);
		GotoSlideItem.addActionListener(lSymAction);
		ActionsToolbarItem.addActionListener(lSymAction);
		ViewModeToolbarItem.addActionListener(lSymAction);
		RulerItem.addActionListener(lSymAction);
		NextSlideItem.addActionListener(lSymAction);
		PrevSlideItem.addActionListener(lSymAction);
		EndShowItem.addActionListener(lSymAction);

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
		setText(wordBundle.getString("view"));
		SlideItem.setText(wordBundle.getString("slide"));
		SlideSorterItem.setText(wordBundle.getString("slideSorter"));
		SlideShowItem.setText(wordBundle.getString("slideShow"));
		ActionsToolbarItem.setText(wordBundle.getString("actionsToolbar"));
		ViewModeToolbarItem.setText(wordBundle.getString("viewModeToolbar"));
		RulerItem.setText(wordBundle.getString("ruler"));
		GotoSlideItem.setText(wordBundle.getString("gotoSlide"));
		NextSlideItem.setText(wordBundle.getString("nextSlide"));
		PrevSlideItem.setText(wordBundle.getString("prevSlide"));
	}

	/**
	 * Activate the MenuItems
	 * @param activate If <code>true</code>, this MenuItems are enabled;
	 * otherwise disabled.
	 */
	public void activate(boolean activate)
	{
		SlideItem.setEnabled(activate);
		SlideSorterItem.setEnabled(activate);
		SlideShowItem.setEnabled(activate);
		ActionsToolbarItem.setEnabled(activate);
		ViewModeToolbarItem.setEnabled(activate);
		RulerItem.setEnabled(activate);
		GotoSlideItem.setEnabled(activate);
		NextSlideItem.setEnabled(activate);
		PrevSlideItem.setEnabled(activate);
	}

	/**
	 * Enables the Navigation MenuItems(Previous Slide, Next Slide).
	 */
	public void setEnablePrevNext(boolean enable1, boolean enable2)
	{
		PrevSlideItem.setEnabled(enable1);
		NextSlideItem.setEnabled(enable2);
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
			//the corresponding function is called when the
			//component is clicked/selected
			Object object = event.getSource();
			if (object == SlideItem)
				SlideItem_actionPerformed(event);
			else if (object == SlideSorterItem)
				SlideSorterItem_actionPerformed(event);
			else if (object == SlideShowItem)
				SlideShowItem_actionPerformed(event);
			else if (object == PrevSlideItem)
				PrevSlideItem_actionPerformed(event);
			else if (object == NextSlideItem)
				NextSlideItem_actionPerformed(event);
			if (object == GotoSlideItem)
				GotoSlideItem_actionPerformed(event);
			else if (object == ActionsToolbarItem)
				ActionsToolbarItem_actionPerformed(event);
			else if (object == ViewModeToolbarItem)
				ViewModeToolbarItem_actionPerformed(event);
			else if (object == RulerItem)
				RulerItem_actionPerformed(event);
			else if (object == EndShowItem)
				EndShowItem_actionPerformed(event);
		}
	}

	/**
	 * Action for "Slide" menu item
	 * @see ConnectActions#SlideView
	 */
	private void SlideItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.SlideView();
	}

	/**
	 * Action for "sorter" menu item
	 * @see ConnectActions#SlideSorterView
	 */
	private void SlideSorterItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.SlideSorterView();
	}

	/**
	 * Action for "show" menu item
	 * @see ConnectActions#SlideShowView
	 */
	private void SlideShowItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.SlideShowView();
	}

	/**
	 * Action for "previous Slide" menu item
	 * @see ConnectActions#PrevSlideView
	 */
	private void PrevSlideItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.PrevSlideView();
	}

	/**
	 * Action for "next Slide" menu item
	 * @see ConnectActions#NextSlideView
	 */
	private void NextSlideItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.NextSlideView();
	}

	/**
	 * Action for "go to Slide" menu item
	 * @see ConnectActions#GotoSlideView
	 */
	private void GotoSlideItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.GotoSlideView();
	}

	/**
	 * Action for "actions toolbar" menu item
	 * @see ConnectActions#ActionsToolbarView
	 */
	private void ActionsToolbarItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ActionsToolbarView();
	}

	/**
	 * Action for "view mode toolbar" menu item
	 * @see ConnectActions#ViewModeToolbarView
	 */
	private void ViewModeToolbarItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.ViewModeToolbarView();
	}

	/**
	 * Action for "ruler" menu item
	 * @see ConnectActions#RulerView
	 */
	private void RulerItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.RulerView();
	}

	/**
	 * Action for "end show" menu item
	 * @see ConnectActions#EndShow
	 */
	private void EndShowItem_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.EndShow();
	}
}

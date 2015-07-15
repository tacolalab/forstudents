import java.beans.*;
import java.awt.*;
import java.util.ResourceBundle;
import javax.swing.*;

public class ViewModeToolbar extends javax.swing.JToolBar
{
	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** ActionsImpl class reference */
 	private transient ConnectActions actionsConnection;


	/** Shortcut for "Slide view" action */
	private RolloverButton slideButton = new RolloverButton
		(ImagesLocator.getImage(Arangam.imageBundle.getString("slide")));
	/** Shortcut for "Sorter view" action */
	private RolloverButton slidesorterButton = new RolloverButton(
		ImagesLocator.getImage(Arangam.imageBundle.getString("slideSorter")));
	/** Shortcut for "Show view" action */
	private RolloverButton slideshowButton = new RolloverButton(
		ImagesLocator.getImage(Arangam.imageBundle.getString("slideShow")));

	/**
	 * Create all the buttons, comboboxes for view mode toolbar
	 */
	public ViewModeToolbar()
	{
		//setFloatable(false);
		//setLayout(null);

		//set bounds and border for toolbar
		//setBounds(0,Arangam.A_HEIGHT-75,Arangam.A_WIDTH-5,30);
		setBorder(BorderFactory.createLoweredBevelBorder());

		//set bounds of buttons and add to toolbar
		add(slideButton);
		//slideButton.setBounds(2,2,25,25);

		//add(slidesorterButton);
		//slidesorterButton.setBounds(27,2,25,25);

		add(slideshowButton);
		//slideshowButton.setBounds(52,2,25,25);

		//register listeners
		SymAction lSymAction = new SymAction();
		slideButton.addActionListener(lSymAction);
		slidesorterButton.addActionListener(lSymAction);
		slideshowButton.addActionListener(lSymAction);
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
		slideButton.setToolTipText(wordBundle.getString("slide"));
		slideshowButton.setToolTipText(wordBundle.getString("slideSorter"));
	}

	/**
	 * Activate the MenuItems
	 * @param activate If <code>true</code>, this MenuItems are enabled;
	 * otherwise disabled.
	 */
	public void activate(boolean activate)
	{
		slideButton.setEnabled(activate);
		slideshowButton.setEnabled(activate);
	}

	/**
	 * ActionListener for toolbar components. For every
	 * action the corresponding function in ConnectAction is invoked
	 */
	private class SymAction implements java.awt.event.ActionListener
	{
		/**
		 * Invoked when an action occurs - when a component in toolbar
		 * is clicked/selected.
		 */
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			//the corresponding function is called when the
			//component is clicked/selected
			Object object = event.getSource();
			if (object == slideButton)
				slideButton_actionPerformed(event);
			else if (object == slidesorterButton)
				slidesorterButton_actionPerformed(event);
			else if (object == slideshowButton)
				slideshowButton_actionPerformed(event);
		}
	}

	/**
	 * Action for "Slide view" button
	 * @see ConnectActions#SlideView
	 */
	private void slideButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.SlideView();
	}

	/**
	 * Action for "sorter view" button
	 * @see ConnectActions#SlideSorterView
	 */
	private void slidesorterButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.SlideSorterView();
	}

	/**
	 * Action for "show view" button
	 * @see ConnectActions#SlideShowView
	 */
	private void slideshowButton_actionPerformed(java.awt.event.ActionEvent event)
	{
		actionsConnection.SlideShowView();
	}
}

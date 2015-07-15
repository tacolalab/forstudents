import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.awt.datatransfer.*;
import java.util.Hashtable;
import javax.swing.text.*;

/**
 * Custom popup menu that let the user to do paste, background
 * operation for Slide
 */
public class SlideshowPopup extends JPopupMenu implements ActionListener
{
	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** Popup menu item for show */
	JMenuItem endShow;

	/*** Main class reference */
	private transient Arangam arangam;

	/*** Reference to current Slide */
	private Slide parentSlide;

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/**
	 * Creates popup menu that let the user to end show
	 * @param Slide current Slide reference
	 * @param arangam main class reference
	 */
	public SlideshowPopup(Slide aSlide,Arangam arangam)
	{
		this.arangam = arangam;
		this.parentSlide = aSlide;

		//create menu item
		endShow = new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("endShow")));

		//set bilingual font
		bilingualFont = arangam.getBilingualFont();
		endShow.setFont(bilingualFont);

		//set current locale
		setLocale();

		//add it to popup menu
		add(endShow);

		//register listeners
		endShow.addActionListener(this);
	}

	/**
	 * Invoked when an action occurs. When a menu item is clicked.
	 * calls corresponding Slide functions
	 * @see Show#stop
	 */
	public void actionPerformed(ActionEvent ae)
	{
		Object object = ae.getSource();
		if(object==endShow)
		{
			arangam.show.stop();
			//display normal "Slide view" after "show view"
			arangam.actionsImpl1.SlideView();
		}
	}

	/**
	 * Sets the locale for Components depends on the current Locale.
	 */
	public void setLocale()
	{
		wordBundle = Arangam.wordBundle;
		endShow.setText(wordBundle.getString("endShow"));
	}
}

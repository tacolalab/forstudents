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
public class SlidePopup extends JPopupMenu implements ActionListener
{
	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** ActionsImpl class reference */
 	private transient ConnectActions actionsConnection;

	/*** StyledText flavor */
	public DataFlavor df = new DataFlavor(StyledText.class, "StyledText");

	/*** Popup menu items for Slide */
	private JMenuItem paste,background;

	/*** Main class reference */
	private transient Arangam arangam;

	/*** Reference to current Slide */
	private Slide parentSlide;

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/**
	 * Creates popup menu that let the user to do paste, background
	 * operation for current Slide
	 * @param Slide current Slide reference
	 * @param arangam main class reference
	 */
	public SlidePopup(Slide aSlide,Arangam arangam)
	{
		this.arangam = arangam;
		this.parentSlide = aSlide;

		//create menu items
		paste =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("paste")));
		background =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("background")));

		//set bilingual font
		bilingualFont = arangam.getBilingualFont();
		paste.setFont(bilingualFont);
		background.setFont(bilingualFont);

		//set current locale
		setLocale();

		//add them to popup menu
		add(paste);
		add(new javax.swing.JSeparator());
		add(background);

		//register listeners
		paste.addActionListener(this);
		background.addActionListener(this);

		//enable or disable paste menu item
		Transferable content=arangam.clipboard.getContents(null);
		boolean pasteAvailable = ( (content!=null) &&
				( (content.isDataFlavorSupported(DataFlavor.stringFlavor)) ||
					(content.isDataFlavorSupported(ComponentSelection.imageFlavor)) ||
					(content.isDataFlavorSupported(ComponentSelection.shapeFlavor)) ||
					(content.isDataFlavorSupported(ComponentSelection.textFlavor)) ||
					(content.isDataFlavorSupported(df)) ) );
		paste.setEnabled(pasteAvailable);
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
	 * Invoked when an action occurs. When a menu item is clicked.
	 * calls corresponding Slide functions
	 * @see Slide#pasteComponent
	 * @see ConnectActions#BackgroundFormat
	 */
	public void actionPerformed(ActionEvent ae)
	{
		Object object = ae.getSource();
		if(object==paste)
		{
			parentSlide.pasteComponent();
		}
		else if(object==background)
		{
			arangam.actionsImpl1.BackgroundFormat();
		}
	}

	/**
	 * Sets the locale for Components depends on the current Locale.
	 */
	public void setLocale()
	{
		wordBundle = Arangam.wordBundle;
		paste.setText(wordBundle.getString("paste"));
		background.setText(wordBundle.getString("background"));
	}
}

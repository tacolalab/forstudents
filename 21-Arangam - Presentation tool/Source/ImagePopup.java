import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.datatransfer.*;

/**
 * Custom popup menu that let the user to do cut, copy, paste
 * operation for image component AImage
 */
public class ImagePopup extends JPopupMenu implements ActionListener
{
	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** Main class reference */
	private transient Arangam arangam;

	/*** ActionsImpl class reference */
 	private transient ConnectActions actionsConnection;

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/*** StyledText flavor */
	public DataFlavor df = new DataFlavor(StyledText.class, "StyledText");

	/*** Popup menu items for image component */
	JMenuItem cut,copy,paste,bringToFront,sentToBack;

	/*** Reference to the image component */
	ConnectImage arangamImage;

	/*** Reference to current Slide */
	public Slide parentSlide;

	/**
	 * Creates popup menu that let the user to do cut, copy, paste
	 * operation for image component AImage
	 * @param arangamImage reference to image component
	 * @param main class reference
	 * @param Slide current Slide reference
	 */
	public ImagePopup(ConnectImage arangamImage,Arangam arangam,Slide aSlide)
	{
		this.arangamImage = arangamImage;
		this.arangam = arangam;
		this.parentSlide = aSlide;

		bilingualFont=arangam.getBilingualFont();

		//create menu items
		cut = new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("cut")));
		copy = new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("copy")));
		paste = new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("paste")));
		bringToFront = new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("bringToFront")));
		sentToBack =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("sentToBack")));

		//set font for them
		cut.setFont(bilingualFont);
		copy.setFont(bilingualFont);
		paste.setFont(bilingualFont);
		bringToFront.setFont(bilingualFont);
		sentToBack.setFont(bilingualFont);

		//add them to popup menu
		add(cut);
		add(copy);
		add(paste);
		add(new javax.swing.JSeparator());
		add(bringToFront);
		add(sentToBack);

		//register listeners
		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		bringToFront.addActionListener(this);
		sentToBack.addActionListener(this);

		//enable or disable paste menu item
		Transferable content=arangam.clipboard.getContents(null);
		boolean pasteAvailable = ( (content!=null) &&
		( (content.isDataFlavorSupported(DataFlavor.stringFlavor)) ||
			(content.isDataFlavorSupported(ComponentSelection.imageFlavor)) ||
			(content.isDataFlavorSupported(ComponentSelection.shapeFlavor)) ||
			(content.isDataFlavorSupported(df)) ) );
		paste.setEnabled(pasteAvailable);

	 	//set current locale
	 	setLocale();
	}

	/**
	 * Invoked when an action occurs. When a menu item is clicked.
	 * calls corresponding Slide functions
	 * @see Slide#copyComponent
	 * @see Slide#pasteComponent
	 * @see Slide#bringComponentToFront
	 * @see Slide#sendComponentToBack
	 */
	public void actionPerformed(ActionEvent ae)
	{
		Object object = ae.getSource();
		//cut - copy and delete
		if(object==cut)
		{
			parentSlide.copyComponent();
			((ConnectComponent)arangamImage).removeFromSlide();
			parentSlide.repaint();
		}
		else if(object == copy)
		{
			parentSlide.copyComponent();
		}
		else if(object == paste)
		{
			parentSlide.pasteComponent();
		}
		else if(object == bringToFront)
		{
			parentSlide.bringComponentToFront();
		}
		else if(object == sentToBack)
		{
			parentSlide.sendComponentToBack();
		}
	}

	/**
	 * Sets the locale for Components depends on the current Locale.
	 */
	public void setLocale()
	{
		wordBundle = Arangam.wordBundle;
		cut.setText(wordBundle.getString("cut"));
		copy.setText(wordBundle.getString("copy"));
		paste.setText(wordBundle.getString("paste"));
		bringToFront.setText(wordBundle.getString("bringToFront"));
		sentToBack.setText(wordBundle.getString("sentToBack"));
	}

}

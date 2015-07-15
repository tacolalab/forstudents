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
 * operation for text component AText
 */
public class TextPopup extends JPopupMenu implements ActionListener
{
	/*** Gets the words in current language - Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** StyledText flavor */
	public DataFlavor df = new DataFlavor(StyledText.class, "StyledText");

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/*** Popup menu items for shape component */
	private JMenuItem cut,copy,paste,fontFormat,bringToFront,sentToBack;

	/*** Reference to the text component */
	private AText arangamText;

	/*** Main class reference */
	private transient Arangam arangam;

	/*** Reference to current Slide */
	private Slide parentSlide;

	/**
	 * Creates popup menu that let the user to do cut, copy, paste
	 * operation for text component AText
	 * @param arangamText reference to text component
	 * @param arangam main class reference
	 * @param aSlide current Slide reference
	 */
	public TextPopup(ConnectText arangamText,Arangam arangam,Slide aSlide)
	{
		this.arangamText=(AText)arangamText;
		this.arangam=arangam;
		this.parentSlide=aSlide;
		bilingualFont = arangam.getBilingualFont();

		//create menu items
		cut =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("cut")));
		copy =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("copy")));
		paste =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("paste")));
		fontFormat =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("font")));
		bringToFront =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("bringToFront")));
		sentToBack =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("sentToBack")));

		cut.setAccelerator(javax.swing.KeyStroke.getKeyStroke
			(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK));

		copy.setAccelerator(javax.swing.KeyStroke.getKeyStroke
			(java.awt.event.KeyEvent.VK_C, java.awt.Event.CTRL_MASK));

		paste.setAccelerator(javax.swing.KeyStroke.getKeyStroke
			(java.awt.event.KeyEvent.VK_V, java.awt.Event.CTRL_MASK));

		//set current locale
		setLocale();

		//set bilingual font
		cut.setFont(bilingualFont);
		copy.setFont(bilingualFont);
		paste.setFont(bilingualFont);
		fontFormat.setFont(bilingualFont);
		bringToFront.setFont(bilingualFont);
		sentToBack.setFont(bilingualFont);

		//add them to popup menu
		add(cut);
		add(copy);
		add(paste);
		add(new javax.swing.JSeparator());
		add(fontFormat);
		add(new javax.swing.JSeparator());
		add(bringToFront);
		add(sentToBack);

		//register listeners
		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		fontFormat.addActionListener(this);
		bringToFront.addActionListener(this);
		sentToBack.addActionListener(this);

		//enable or disable paste menu item
		Transferable content=arangam.clipboard.getContents(null);
		boolean pasteAvailable = ( (content!=null) &&
			( (content.isDataFlavorSupported(DataFlavor.stringFlavor)) ||
			(content.isDataFlavorSupported(df)) ||
			(content.isDataFlavorSupported(ComponentSelection.imageFlavor)) ||
			(content.isDataFlavorSupported(ComponentSelection.shapeFlavor)) )	);
		paste.setEnabled(pasteAvailable);

	}

	/**
	 * Invoked when an action occurs. When a menu item is clicked.
	 * calls corresponding Slide functions
	 * @see Slide#copyComponent
	 * @see Slide#pasteComponent
	 * @see FontDialog
	 * @see Slide#bringComponentToFront
	 * @see Slide#sendComponentToBack
	 */
	public void actionPerformed(ActionEvent ae)
	{
		Object object = ae.getSource();
		//cut = copy and delete
		if(object==cut)
		{
			parentSlide.copyComponent();
			parentSlide.clearComponent();
			parentSlide.repaint();
		}
		else if(object==copy)
		{
			parentSlide.copyComponent();
		}
		else if(object==paste)
		{
			parentSlide.pasteComponent();
		}
		else if(object==fontFormat)
		{
			StyledDocument doc = arangamText.getStyledDocument();
			FontDialog fontDialog =
				new FontDialog(arangam,arangamText);
			AttributeSet a = doc.getCharacterElement(arangamText.
				getCaretPosition()-1).getAttributes();
			fontDialog.setAttributes(a);
			fontDialog.show();
		}
		else if(object==bringToFront)
		{
			parentSlide.bringComponentToFront();
		}
		else if(object==sentToBack)
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
		fontFormat.setText(wordBundle.getString("fontFormat"));
		bringToFront.setText(wordBundle.getString("bringToFront"));
		sentToBack.setText(wordBundle.getString("sentToBack"));
	}

}

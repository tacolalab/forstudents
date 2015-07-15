import java.awt.Font;
import java.util.ResourceBundle ;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.text.AttributeSet;

/**
 * Custom popup menu that let the user to do cut, copy, paste
 * operation for shape component AShape
 */
public class ShapePopup extends JPopupMenu implements ActionListener
{
	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** StyledText flavor */
	public DataFlavor df = new DataFlavor(StyledText.class, "StyledText");

	/*** Popup menu items for shape component */
	public JMenuItem cut,copy,paste,font,format,bringToFront,sentToBack;

	/*** Reference to the image component */
	public AShape arangamShape;

	/*** Main class reference */
	private transient Arangam arangam;

	/*** <code>ActionsImpl</code> class reference */
 	private transient ConnectActions actionsConnection;

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/*** Reference to current <code>Slide</code> */
	public Slide parentSlide;

	/**
	 * Creates popup menu that let the user to do cut, copy, paste
	 * operation for shape component <code>AShape</code>
	 * @param arangamShape reference to <code>AShape</code> component
	 * @param arangam main class reference
	 * @param Slide current Slide reference
	 */
	public ShapePopup(AShape arangamShape,Arangam arangam,Slide aSlide)
	{
		this.arangamShape = arangamShape;
		this.arangam = arangam;
		this.parentSlide = aSlide;

		//create menu items
		cut =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("cut")));
		copy =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("copy")));
		paste =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("paste")));
		font =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("font")));
		format =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("format")));
		bringToFront =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("bringToFront")));
		sentToBack =new JMenuItem(ImagesLocator.getImage(Arangam.imageBundle.getString("sentToBack")));

		//set current locale
		setLocale();

		//set bilingual font
		bilingualFont = arangam.getBilingualFont();
		cut.setFont(bilingualFont);
		copy.setFont(bilingualFont);
		paste.setFont(bilingualFont);
		font.setFont(bilingualFont);
		format.setFont(bilingualFont);
		bringToFront.setFont(bilingualFont);
		sentToBack.setFont(bilingualFont);

		//add them to popup menu
		add(cut);
		add(copy);
		add(paste);
		add(new javax.swing.JSeparator());
		add(font);
		add(format);
		add(new javax.swing.JSeparator());
		add(bringToFront);
		add(sentToBack);

		//register listeners
		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		font.addActionListener(this);
		format.addActionListener(this);
		bringToFront.addActionListener(this);
		sentToBack.addActionListener(this);

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
	 * Invoked when an action occurs. When a menu item is clicked.
	 * calls corresponding <code>Slide</code> functions
	 * @see Slide#copyComponent
	 * @see Slide#pasteComponent
	 * @see FontDialog
	 * @see ShapeFormatDialog
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
		}
		else if(object==copy)
		{
			parentSlide.copyComponent();
		}
		else if(object==paste)
		{
			parentSlide.pasteComponent();
		}
		else if(object==font)
		{
			ConnectComponent focused = arangamShape;
			FontDialog fontDialog =	new FontDialog(arangam,focused);
			ConnectShape focusedShape =
				(ConnectShape)focused;
			AttributeSet attribute = focusedShape.getFontAttributes();
           	fontDialog.setAttributes(attribute);
			fontDialog.show();
		}
		else if(object==format)
		{
			ShapeFormatDialog shapeFormatDialog = new ShapeFormatDialog(arangam,arangamShape);
			shapeFormatDialog.setAttributes();
			shapeFormatDialog.show();
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
	 * Sets the locale for components depends on the current Locale.
	 */
	public void setLocale()
	{
		wordBundle = Arangam.wordBundle;
		cut.setText(wordBundle.getString("cut"));
		copy.setText(wordBundle.getString("copy"));
		paste.setText(wordBundle.getString("paste"));
		font.setText(wordBundle.getString("font"));
		format.setText(wordBundle.getString("format"));
		bringToFront.setText(wordBundle.getString("bringToFront"));
		sentToBack.setText(wordBundle.getString("sentToBack"));
	}

}

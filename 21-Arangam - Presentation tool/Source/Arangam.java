import java.io.InputStream;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Main Application frame
 */
 public class Arangam extends JFrame implements ClipboardOwner,
	FileHistory.IFileHistory
{
	static JPanel presentaionPane;
    Rule rowView;
    Rule columnView;
	JScrollPane scrollPane;

	/**
	 * The applet instance to run on browser.
	 */
	public JApplet applet;

	/**
	 * Application frame dimension constants
	 */
	public static int 	A_WIDTH,
						A_HEIGHT;

	/**
	 * Slideshow dimensions constants
	 */
	public static int	SLIDESHOW_X,
						SLIDESHOW_Y,
						SLIDESHOW_WIDTH,
						SLIDESHOW_HEIGHT;

	/**
	 * View mode constants
	 */
	public static int	SLIDE_VIEW = 0,
						SORTER_VIEW = 1,
						SHOW_VIEW = 2,
						PRINT_VIEW = 3;

	/**
	 * Position for new component
	 */
	public static Point pasteCompLocation = new Point(10,10);

	/**
	 * General program variables
	 */
	public int viewMode = SLIDE_VIEW;

	/**
	 * Stores the save state of presentation.
	 */
	public boolean slideModified = false;

	/**
	 * Current Filename.
	 */
	public File filename = null;

	/**
	 * Clipboard used in the application.
	 */
	public Clipboard clipboard;

	/**
	 * Toolbar and ruler variables
	 */
	public boolean actionsToolbarVisible = true,
					viewModeToolbarVisible = true,
					rulerVisible = true;

	/**
	 * Is the Background need applied to all slides?
	 */
	public boolean isBGApplyToAll = false;

	/**
	 * File extension of presentation.
	 */
	public static final String fileExt = "tpt";

	/**
	 * AttributeSet for new AText.
	 */
	public static MutableAttributeSet newMattr;

	/**
	 * The class used to implements the actions made by the user through the GUI.
	 */
	transient public ActionsImpl actionsImpl1;

	/**
	 * Class used to maintain File History.
	 */
	public FileHistory fileHistory;

	/**
	 * File separator string of system.
	 */
	private static final String FILE_SEPARATOR_STR = System.getProperty("file.separator");

	/**
	 * Bilingual(Tamil and English) font used.
	 */
	public static Font bilingualFont;

	/**
	 * Monolingual(English) font used.
	 */
	public static Font englishFont;

	/**
	 * Current locale of application.
	 */
	public static Locale currentLocale;

	/**
	 * Resource bundle used to store current locale "texts".
	 */
	public static ResourceBundle wordBundle;

	/**
	 * ImageBundle used to store image filenames.
	 */
	public static ResourceBundle imageBundle = ResourceBundle.getBundle("ImageBundle");

	/**
	 * Icon for Information messages.
	 */
	public static ImageIcon infoIcon = ImagesLocator.getImage(
			Arangam.imageBundle.getString("infoIcon"));
	/**
	 * Icon for Warning messages.
	 */
	public static ImageIcon warIcon = ImagesLocator.getImage(
			Arangam.imageBundle.getString("warIcon"));
	/**
	 * Icon for Error messages.
	 */
	public static ImageIcon errIcon = ImagesLocator.getImage(
			Arangam.imageBundle.getString("errIcon"));

	/*** Tamil language */
	public static String language = "ta";
	/*** Country : India */
	public static String country = "IN";

	/*** Show class reference */
	public Show show;

	/*** Slideshow, the editing area */
	public Slideshow slideshow1;

	/*** Actions toolbar, shortcut for common actions */
	public ActionsToolbar actionsToolbar1;

	/*** For changing view mode */
	public ViewModeToolbar viewModeToolbar1;

	/*** Menu bar for application frame */
	public javax.swing.JMenuBar menuBar;

	/*** File menu in menu bar */
	public FileMenu fileMenuObj1;

	/*** Edit menu in menu bar */
	public EditMenu editMenuObj1;

	/*** View menu in menu bar */
	public ViewMenu viewMenuObj1;

	/*** Insert menu in menu bar */
	public InsertMenu insertMenuObj1;

	/*** Format menu in menu bar */
	public FormatMenu formatMenuObj1;

	/*** Help menu in menu bar */
	public HelpMenu helpMenuObj1;

	/*** Language menu in menu bar */
	public LangMenu langMenuObj1;

	/*** Text size. Used while pasting text with String Flavor */
	public Dimension textSize;

	/*** Text background color. Used while pasting text */
	public Color textbgColor=Color.white;

	/*** Text border. Used while pasting text */
	public Border textBorder;

	/*** Dimension for sorter view */
	public int slideSorterDimensions = 2;

	/**
	 * Called when run as an application
	 */
	public Arangam()
	{
		this(null);
	}

	/**
	 * The application starts from this function. The function builds all the
	 * toolbars and menu items for the Slide Show Starts with an empty Slide Show.
	 */
	public Arangam(JApplet applet)
	{
		super("RCILTS ARANGAM");
		setName("Arangam in Java");
		this.applet = applet;

		//don't do anything on close
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		setIconImage(ImagesLocator.getImage(Arangam.imageBundle.getString("arangamLogo"))
				.getImage());

		//set bounds
		Utils.sizeScreen(this);

		//setBounds(0,0,600,600);

		//create font from font files
		try
		{
			InputStream inputStream1 = getClass().getResourceAsStream("TAB_Tamil.TTF");
			Font tempFont = Font.createFont(Font.TRUETYPE_FONT, inputStream1);
			bilingualFont = tempFont.deriveFont(Font.PLAIN, 14);

			InputStream inputStream2 = getClass().getResourceAsStream("English.ttf");
			tempFont = Font.createFont(Font.TRUETYPE_FONT, inputStream2);
			englishFont = tempFont.deriveFont(Font.PLAIN, 12);
		}
		catch(FontFormatException e)
		{
			//alert for exception and return
			Object[] options = {wordBundle.getString("ok")};
			Utils.showDialog(this,"fontCantLoadErr",JOptionPane.OK_OPTION,
				JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);
			return;
		}
		catch(java.io.IOException e)
		{
			//alert for exception and return
			Object[] options = {wordBundle.getString("ok")};
			Utils.showDialog(this,"fontCantLoadErr",JOptionPane.OK_OPTION,
				JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);
			return;
		}

		//constructs a Clipboard
		clipboard = new Clipboard("Arangam Clipboard");

		//constructs all components
		actionsImpl1 = new ActionsImpl();
		slideshow1 = new Slideshow(this);
		actionsToolbar1 = new ActionsToolbar(this);
		viewModeToolbar1 = new ViewModeToolbar();
		menuBar = new javax.swing.JMenuBar();
		fileMenuObj1 = new FileMenu();
		editMenuObj1 = new EditMenu();
		viewMenuObj1 = new ViewMenu();
		insertMenuObj1 = new InsertMenu();
		formatMenuObj1 = new FormatMenu();
		langMenuObj1 = new LangMenu();
		helpMenuObj1 = new HelpMenu();

		//set current locale
		currentLocale = new Locale(language,country);
		setLocale(language, country);

		//set frame for show window
		Show.setFrame();

		//construct show
		show = new Show();
		show.getInterface(this);

		getRootPane().putClientProperty("defeatSystemEventQueueCheck",Boolean.TRUE);

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
		        actionsImpl1.ExitFile();
			}
		});

		//add the above said components

		//layout
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(new Color(180,180,180));    //some lightGray

		JPanel northPane = new JPanel(new BorderLayout());

		northPane.add(menuBar,BorderLayout.NORTH);
		northPane.add(actionsToolbar1,BorderLayout.CENTER);


		//-----start-----

		//set menu bar
		contentPane.add(northPane,BorderLayout.NORTH);

		presentaionPane = new JPanel(new BorderLayout(10,10));

		scrollPane = new JScrollPane(presentaionPane);
		presentaionPane.add(slideshow1,BorderLayout.CENTER);

		contentPane.add(scrollPane,BorderLayout.CENTER);
		contentPane.add(viewModeToolbar1,BorderLayout.SOUTH);

		rowView = new Rule(Rule.VERTICAL, true);
		Dimension d = scrollPane.getSize();
		rowView.setPreferredHeight(d.height);

		columnView = new Rule(Rule.HORIZONTAL, true);
		columnView.setPreferredWidth(d.width);

		scrollPane.setRowHeaderView(rowView);
		scrollPane.setColumnHeaderView(columnView);
		//-----end-----

		menuBar.setAlignmentY(1.5F);

		//set mnemonics to menus
		fileMenuObj1.setMnemonic((int)'F');
		menuBar.add(fileMenuObj1);
		editMenuObj1.setMnemonic((int)'E');
		menuBar.add(editMenuObj1);
		viewMenuObj1.setMnemonic((int)'V');
		menuBar.add(viewMenuObj1);
		insertMenuObj1.setMnemonic((int)'I');
		menuBar.add(insertMenuObj1);
		formatMenuObj1.setMnemonic((int)'O');
		menuBar.add(formatMenuObj1);
		langMenuObj1.setMnemonic((int)'L');
		menuBar.add(langMenuObj1);
		helpMenuObj1.setMnemonic((int)'H');
		menuBar.add(helpMenuObj1);

		actionsImpl1.getInterface(this);

		//Connect all objects to the ConnectAction interface
		fileMenuObj1.getInterface(actionsImpl1);
		editMenuObj1.getInterface(actionsImpl1);
		viewMenuObj1.getInterface(actionsImpl1);
		insertMenuObj1.getInterface(actionsImpl1);
		formatMenuObj1.getInterface(actionsImpl1);
		langMenuObj1.getInterface(actionsImpl1);
		helpMenuObj1.getInterface(actionsImpl1);
		actionsToolbar1.getInterface(actionsImpl1);
		viewModeToolbar1.getInterface(actionsImpl1);
		Utils.getInterface(this);

		//start with a new empty Slideshow
		actionsImpl1.NewFile();
		slideModified = false;

		//create and initialise FileHistory
		fileHistory = new FileHistory(this);
		fileHistory.initFileMenuHistory();

		//set properties

		setCCPEnabled();
		if(System.getProperty("java.version").startsWith("1.4"))
			setExtendedState(Frame.MAXIMIZED_BOTH);
		setUIManager();
		actionsToolbar1.setUndoEnabled(false);
		editMenuObj1.setUndoEnabled(false);

		//layout
		//setResizable(false);

		//default text properties
		textSize=new Dimension(100,18);
		textBorder=new EmptyBorder(6,6,6,6);
		newMattr = new SimpleAttributeSet();
		StyleConstants.setFontSize(newMattr,24);
		MutableAttributeSet mattr=new SimpleAttributeSet();
		String fontFamily = actionsToolbar1.getFontInToolbar();
		StyleConstants.setFontFamily(mattr,fontFamily);
		actionsImpl1.updateTextAttr(mattr,false);

		//if application, make it visible
		//else set applet
		if(applet == null)
			setVisible(true);
		else
			applet.setJMenuBar(menuBar);


	}

	/**
	 * Update the application frame's title with filename.
	 * @param filename the filename to set
	 */
	public void setFileTitle(File filename)
	{
		String title = getTitle();
		title = title.substring(0,14);
		if(filename != null)
		{
			String fName = filename.getName();
			title += " - "+fName;
		}
		setTitle(title);
	}

	/**
	 * Gets the bilingual font of the application.
	 * @return the bilingual font
	 */
	public Font getBilingualFont()
	{
		return bilingualFont;
	}

	/**
	 * Gets the language of the current locale.
	 * @return the language as a String
	 */
	public String getLanguage()
	{
		return language;
	}

	/**s
	 * Get the country of the current locale.
	 * @return the country as a String
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * Sets the locale for the application.
	 * @param language language for the locale
	 * @param country country for the locale
	 */
	public void setLocale(String language, String country)
	{
		this.language = language;
		this.country = country;
		currentLocale = new Locale(language,country);
		wordBundle = ResourceBundle.getBundle("WordBundle",currentLocale);

		//set it to all component
		fileMenuObj1.setLocale();
		editMenuObj1.setLocale();
		viewMenuObj1.setLocale();
		insertMenuObj1.setLocale();
		formatMenuObj1.setLocale();
		helpMenuObj1.setLocale();
		langMenuObj1.setLocale();
		actionsToolbar1.setLocale();
		viewModeToolbar1.setLocale();
		menuBar.setFont(bilingualFont);
	}

	/**
	 * Gets the current locale.
	 * @return the current locale
	 */
	public Locale getLocale()
	{
		return currentLocale;
	}

	/**
	 * Gets the current WordBundle.
	 * @return the WordBundle
	 */
	public ResourceBundle getWordBundle()
	{
		return wordBundle;
	}

	/**
	 * Sets UIManager for the components.
	 */
	public void setUIManager()
	{
		//set bilingual font for the components used
		UIManager.put("Button.font",bilingualFont);
		UIManager.put("ComboBox.font",bilingualFont);
		UIManager.put("Label.font",bilingualFont);
		UIManager.put("CheckBox.font",bilingualFont);
		UIManager.put("RadioButton.font",bilingualFont);
		UIManager.put("TitledBorder.font",bilingualFont);
		UIManager.put("ToolTip.font",bilingualFont);
		UIManager.put("FileChooser.font",bilingualFont);
		UIManager.getDefaults().put("TextField.selectionBackground",Color.black);
		UIManager.getDefaults().put("TextField.selectionForeground",Color.white);
	}

	/**
	 * Overridden to paint the applet if any.
	 */
	public void repaint()
	{
		super.repaint();
		if(applet != null)
		{
			applet.repaint();
		}
	}

	/**
	 * Activate the toolbars and menus
	 * @param activate if <code>true</code>, enables the toolbar components and menu items,
	 * otherwise disables
	 */
	public void activateToolbar(boolean activate)
	{
		actionsToolbar1.activate(activate);
		viewModeToolbar1.activate(activate);
		fileMenuObj1.activate(activate);
		editMenuObj1.activate(activate);
		viewMenuObj1.activate(activate);
		insertMenuObj1.activate(activate);
		if(activate)
			formatMenuObj1.activate();
		helpMenuObj1.activate(activate);
		langMenuObj1.activate(activate);

		//false after file - new, open, close
		actionsToolbar1.setUndoEnabled(false);
		editMenuObj1.setUndoEnabled(false);
	}


	/**
	 * Activates the Cut, Copy, Paste Operation depends on the Clipboard content.
	 */
	public void setCCPEnabled()
	{
		editMenuObj1.setCCPEnabled();
		actionsToolbar1.setCCPEnabled();
	}

	/**
	 * Activates the Previous Slide, Next Slide Operation depends on
	 * the current Slide number.
	 */
	public void setEnablePrevNext()
	{
		boolean enable1,enable2;
		//enable the previous Slide navigation if there is any Slide before
		if(slideshow1.getCurrentSlideNum() > 1)
		{
			enable1 = true;
		}
		else
		{
			enable1 = false;
		}

		//enable the next Slide navigation if there is any Slide after
		if(slideshow1.getCurrentSlideNum() < slideshow1.getSlidesCount())
		{
			enable2 = true;
		}
		else
		{
			enable2 = false;
		}

		viewMenuObj1.setEnablePrevNext(enable1,enable2);
		actionsToolbar1.setEnablePrevNext(enable1,enable2);
	}

	public static void main(String args[])
	{
		new Arangam();
	}

	// --- Implementation of FileHistory.IFileHistory interface ----------------

	/**
	* Get the application name to identify the configuration file in the
	* the USER_HOME directory. This name should be unique in this directory.
	*
	* @return the application name
	*/
	public String getApplicationName()
	{
		return "Arangam";
	}

	/**
	* Get a handle to the frame's file menu.
	*
	* @return the frame's file menu
	*/
	public JMenu getFileMenu()
	{
		return menuBar.getMenu(0);
	}

	/**
	* Return the size of the main application frame.
	* It is used to center the file history maintenance window.
	*
	* @return the frame's size
	*/
	public Dimension getSize()
	{
		return super.getSize();
	}

	/**
	* Return the main application frame.
	* It is used to center the file history maintenance window.
	*
	* @return the main GUI frame
	*/
	public JFrame getFrame()
	{
		return this;
	}

	/**
	* Simulate a load file activity.
	*
	* @param path   the path name of the loaded file
	*/
	public void loadFile(String path)
	{
		actionsImpl1.OpenFile(path);
	}

	// -------------------------------------------------------------------------

	// --- Implementation of Clipboard interface ----------------
	/**
	 * Notifies this object that it is no longer the owner of the contents of the
	 * clipboard.
	 */
	public void lostOwnership(Clipboard clipboard,Transferable contents)
	{
	}
	// -------------------------------------------------------------------------

}

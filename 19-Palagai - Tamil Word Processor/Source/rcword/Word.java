package rcword;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;

import java.util.*;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.event.*;

import javax.swing.undo.*;
import javax.swing.table.*;
import javax.swing.border.*;

import java.awt.print.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.text.html.*;

import javax.swing.plaf.basic.*;

/**
 *  Main class which organizes and uses all other
 *  classes in this package.
 *
 *  @version	5.12.2002
 *
 *  @author		RCILTS-Tamil,MIT
 */
public class Word extends JFrame implements ActionListener, Printable, ClipboardOwner
{

	 /** Appropriate font for the current local for entire GUI. */
     static Font defaultfont;

     /** Text component where the text can be entered and edited 	*/
     static JTextPane workspace;

     /** Scroll pane which contains the text component      */
     JScrollPane workspacescrollpane;

     /** Tool bar of the word processor      */
     JToolBar toolbar;

     /** Format bar of the word processor      */
     JToolBar formatbar;//,statusbar;

     /** Combo box for font names      */
     JComboBox fontnamecombobox;

     /** Combo box for font size      */
     JComboBox fontsizecombobox;

     /** Combo box for font styles      */
     JComboBox fontstylecombobox;

     /** Hash table which hashes all action      */
     Hashtable actionhash = new Hashtable();

     /** Check box for Tool bar      */
     JCheckBoxMenuItem toolbarmenuitem;

     /** Check box for Format bar      */
     JCheckBoxMenuItem formatbarmenuitem;

     /** Check box for Status  bar      */
     JCheckBoxMenuItem statusbarmenuitem;

     /** Check box for ruler */
     JCheckBoxMenuItem rulerMenuItem ;

     /** JPanel for status bar      */
     JPanel statusbar ;

	 /** Button for new file	  */
     JButton newbutton;

     /** WordCount object to compute word count of the file      */
     WordCount wdcount;

     /** UndoAction object for undo operations      */
     UndoAction undoAction ;

     /** RedoAction object for redo operations      */
     RedoAction redoAction ;

     /** UndoableEditListener object for undo and redo operations      */
     protected UndoableEditListener undoHandler;

     /** UndoManager object for undo and redo operation     */
     protected UndoManager undo ;

     /** Table object for table operation      */
     Table table;

	 /** Used in creating the default styled document	  */
     StyleContext stycont;

 	 /** Used to fix the document in the text component 	  */
     DefaultStyledDocument  dsd_stydoc ;

     /** Kit used to fix in the text component to show the html/htm files      */
     HTMLEditorKit htmlkit;

     /** Kit used to fix in the text component to show the rich text formatted files      */
     RTFEditorKit  rtfkit;

     /** Used to denote the document as text document       */
     static int TEXT = 1;

     /** Used to denote the document as rich text formatted document      */
     static int RTF = 2;

     /** Used to denote the document as html/htm document      */
     static int HTML = 3;

     /** Used to denote the current document's type      */
     int editorkit_type = 0;

	 /** Used to denote the current font name	  */
     String currentfontname=" ";

     /** Used to denote the current font size      */
     int currentfontsize;

     /** Used to denote the cursor position  or starting position of the selection      */
     int caretstart = -1;

     /** Used to denote the end position of the selection      */
     int caretend = -1;

	 /** Used to denote the current document has updated or not	  */
     protected boolean workspace_update;

     /** Used to set the text as bold      */
     JToggleButton jtb_bold,jtb_italic;

     /** Used to set the text as underlined      */
     JToggleButton jtb_underline;

     /** Used to set the text as left aligned      */
     JToggleButton jtb_leftalign;

     /** Used to set the text as center aligned     */
     JToggleButton jtb_centeralign;

     /** Used to set the text as sub script      */
     JToggleButton jtb_subscript;

     /** Used to set the text as super script      */
     JToggleButton jtb_superscript;

     /** Used to set the text as stroked      */
     JToggleButton jtb_strikeThrough;

	 /** Menu bar of the word processor	  */
     JMenuBar menubar;

     /** progress bar to show the progress of file opening */
     JProgressBar progress;

	 /** Used to denote the current file name	  */
	 String current_file_name = null;

	 /** Used to do the file operations	  */
     FileOperation currentfile;

     /** Dialog box to do the find and replace operations      */
     FindDialog finddialog ;

     //File fileproperty ;
     //FileProperty property;

     /** Used to draw the horizontal ruler      */
     Ruler ruler = new Ruler(this);

     private Rule columnView;
     private Rule rowView;

     /** Button to select ruler in cm or inchec */
     private JToggleButton isMetric;

     /** Used to do the bullets operations      */
     Bullets bults ;

     /** Used to send a mail      */
     EMail email;

     /** Dialog box to set the tab stop attributes      */
     Tab tab;

	 /** Contains the list of tables drawn	  */
     ArrayList al_tablelist = new ArrayList();

     /** Contains the focus status of tables drawn      */
     ArrayList al_tableStatus = new ArrayList();

     /** Denotes the currently focused row of the currently focused table      */
     int current_row= -1;

     /** Denotes the currently focused column of the currently focused table      */
     int current_column = -1;
     //JTable jt_currenttable;

	 /** Denotes the status true/false	  */
     Boolean ON = new Boolean(true);

	 /** Dialog to set the paragraph attributes	  */
     protected ParagraphDialog m_paragraphDialog;

     /** Used to do the sorting operation      */
     protected Sorter sort;

	 /** Dialog to set the page format	  */
     protected PageFormat pageFormat;

     /** Used in printing process      */
     protected PrintView m_printView;

	 /**  Used in changing the UI language	  */
     protected Language language;

     /** Denotes the current language. true for Tamil  , false for English      */
     boolean current_language =  true;

     /** Denotes the current local      */
     protected Locale current_locale ;

     /** Contains the word bundle for the current local      */
     public static ResourceBundle wordBundle;

     /** Used to set the font attribute */
     protected FontDialog fd_fontdialog;

	 /** Used to show the current horizontal position of the cursor	  */
     JLabel jl_row = new JLabel();

     /** Used to show the current vertical position of the cursor      */
     JLabel jl_collum = new JLabel();

	 /** Used to access the file menu      */
     FileMenu filemenu;

     ToolsMenu toolsmenu;

	 /** Used to access the edit menu	  */
     EditMenu editmenu;

     /** Used to access the help menu	  */
     HelpMenu helpmenu;

     /** used to identify whether the current document has changed or not */
     boolean textChanged = false;

     protected DocumentListener docHandler;

     /** Used to identify the current OS */
     int currentOS ;  // At present 1:Windows, 2:Linux

	 /**
	  * Constructs a new object of <code> Word </code>
	  * which does all initial arrangements to open the word processor.
	  */
     public Word()
     {
			try
			{
				/* This try portion gets the font file form the source file */
				InputStream fontStream = getClass().getResourceAsStream("TABKural.TTF");
				Font tempFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
				defaultfont = tempFont.deriveFont(Font.PLAIN, 14);

				String OSname = System.getProperty("os.name");
				System.out.println("os.name : "+OSname);
				OSname = OSname.toLowerCase();

				if(OSname.indexOf("windows") != -1)
				{
					currentOS = 1;// windows.
					System.out.println(" The current OS is Windows ");
				}
				else
				{
					currentOS = 2; // not windows
					System.out.println(" The current OS is not Windows");
				}

			}
			catch(Exception e)
			{
				System.out.println(e+"\n-->exception at font creation");
			}

			setTitle(" RCILTS - WORD ");
			setSize(800,580);

			/* Sets Tamil is the initial language*/
			current_locale = new Locale("ta","IN");
			/* Sets the current language's word bundles in wordBundle */
			wordBundle = ResourceBundle.getBundle("WordBundle",current_locale);

			//setIconImage(new ImageIcon("tha.gif"));
			currentfile = new FileOperation(this);
			undoAction = new UndoAction(this);
			redoAction = new RedoAction(this);
			undoHandler = new UndoHandler(this);
			docHandler = new DocHandler();
			bults = new Bullets(this); 	// to do the bullets operation
			undo = new UndoManager();
			language = new Language(this); 	// to set the UI's language

			menubar = createMenuBar();	// creates the menubar
			currentfile.updatemenubar(); // to add the recent files in the file menu
			setJMenuBar(menubar);
			getContentPane().setLayout(null);

			progress = new JProgressBar();
			toolbar = createToolBar();
			toolbar.setBounds(0,0,getWidth()-10,36);
			getContentPane().add(toolbar);

			stycont = new StyleContext();
			dsd_stydoc = new DefaultStyledDocument(stycont);

			htmlkit = new HTMLEditorKit();
			rtfkit= new RTFEditorKit();

			workspace = new JTextPane();
			workspace.setFont(defaultfont);	// sets the default font for the workspace

			SymMouse aSymMouse = new SymMouse(); //		to listen the mouse
			workspace.addMouseListener(aSymMouse);

			hashDefaultActions();
			makeActionsPretty();

            /* listens the cursor's position and updates the UI according to this.   */
			CaretListener curentplace = new CaretListener()
				{
					public void caretUpdate(CaretEvent e)
					{
						showAttributes(e.getDot());  // call to set UI with the cursor's position
						int pos = workspace.getCaretPosition();
						Element map = workspace.getDocument().getDefaultRootElement();
						int row = map.getElementIndex(pos);
						Element lineElem = map.getElement(row);
						int col = pos - lineElem.getStartOffset();
						jl_row.setText(""+row);
						jl_collum.setText(""+col);

					}
				};
			workspace.addCaretListener(curentplace);

			/* to listen whether the workspace focus gained or not */
			FocusListener workspace_listener = new FocusListener()
				 {
					 public void focusGained(FocusEvent e)
						{
							 if (caretstart>=0 && caretend>=0)
								if (workspace.getCaretPosition()==caretstart)
								{
									workspace.setCaretPosition(caretend);
									workspace.moveCaretPosition(caretstart);
								}
								else
								{
									try
									{
										//System.out.println(" start :"+caretstart+"   end: "+caretend);
										workspace.select(caretstart, caretend);
									}
									catch(Exception efl)
									{
										System.out.println(efl+"\n-----> e at focus listener");
										efl.printStackTrace();
									}
								 }
						}
						public void focusLost(FocusEvent e)
						{
							caretstart = workspace.getSelectionStart();
							caretend = workspace.getSelectionEnd();
						}
				};

			workspace.addFocusListener(workspace_listener);

			formatbar = createFormatBar();	// creates the format bar
			formatbar.setBounds(0,37,getWidth()-10,36);
			getContentPane().add(formatbar);

			 // Create the row and column headers.
			columnView = new Rule(Rule.HORIZONTAL, true);
				columnView.setPreferredWidth(workspace.getWidth());
			columnView.getAccessibleContext().setAccessibleName("Column Header");
			columnView.getAccessibleContext().
					setAccessibleDescription("Displays horizontal ruler for " +
											 "measuring scroll pane client.");
			rowView = new Rule(Rule.VERTICAL, true);
				rowView.setPreferredHeight(workspace.getHeight());
			rowView.getAccessibleContext().setAccessibleName("Row Header");
			rowView.getAccessibleContext().
					setAccessibleDescription("Displays vertical ruler for " +
											 "measuring scroll pane client.");

			JPanel buttonCorner = new JPanel();
			isMetric = new JToggleButton("cm", true);
			isMetric.setFont(new Font("SansSerif", Font.PLAIN, 11));
			isMetric.setMargin(new Insets(2,2,2,2));
			isMetric.setToolTipText("Toggles rulers' unit of measure " +
									"between inches and centimeters.");
			buttonCorner.add(isMetric); //Use the default FlowLayout
			buttonCorner.getAccessibleContext().setAccessibleName("Upper Left Corner");

			ItemListener ruleScaleListener = new ItemListener()
				{
					public void itemStateChanged(ItemEvent e)
					{
						if (e.getStateChange() == ItemEvent.SELECTED)
						{	// Turn it to metric.
							rowView.setIsMetric(true);
							columnView.setIsMetric(true);
						} else
						{ // Turn it to inches.
							rowView.setIsMetric(false);
							columnView.setIsMetric(false);
						}
					}
				};
			isMetric.addItemListener(ruleScaleListener);

			// scroll pane to put the workspace
			workspacescrollpane = new JScrollPane(workspace, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
										ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			//workspacescrollpane.getViewport().add(workspace);
			workspacescrollpane.setBounds(0,75,getWidth()-10,420);    //0-20, 190- 170

			workspacescrollpane.setColumnHeaderView(columnView);
        	workspacescrollpane.setRowHeaderView(rowView);
			workspacescrollpane.setCorner(JScrollPane.UPPER_LEFT_CORNER,buttonCorner);

			getContentPane().add(workspacescrollpane);
			//ruler.addToContentPane();  // adds the horizontal ruler to the main frame

			workspace.getDocument().addUndoableEditListener(undoHandler);  // adds the undo redo Handler to the workspace document
			workspace.getDocument().addDocumentListener(docHandler);

			CutAction cutAction = new CutAction(this);
			workspace.getInputMap().put(
						KeyStroke.getKeyStroke( KeyEvent.VK_X, InputEvent.CTRL_MASK ), "Cut" );
			workspace.getActionMap().put( "Cut", cutAction );

			CopyAction copyAction = new CopyAction(this);
			workspace.getInputMap().put(
						KeyStroke.getKeyStroke( KeyEvent.VK_C, InputEvent.CTRL_MASK ), "Copy" );
			workspace.getActionMap().put( "Copy", copyAction );

			PasteAction pasteAction = new PasteAction(this);
			workspace.getInputMap().put(
						KeyStroke.getKeyStroke( KeyEvent.VK_V, InputEvent.CTRL_MASK ), "Paste" );
			workspace.getActionMap().put( "Paste", pasteAction );

			ReplaceAction replaceAction = new ReplaceAction(this);

			InputMap im = workspace.getInputMap();
			// remove old binding
			KeyStroke typed010 = KeyStroke.getKeyStroke("typed \010");
			InputMap parent = im;
			while (parent != null)
			{
				parent.remove(typed010);
				parent = parent.getParent();
			}    // rebind backspace
			KeyStroke ctrlH = KeyStroke.getKeyStroke("ctrl H");

			// add new binding
			KeyStroke bksp = KeyStroke.getKeyStroke("BACK_SPACE");
			im.put(bksp, DefaultEditorKit.deletePrevCharAction);
			im.put(ctrlH, replaceAction);

			statusbar = createStatusBar(); 	// creates status bar
			statusbar.setBounds(0,496,790,420);
			getContentPane().add(statusbar);

			currentfile.newFile(false);	// sets the first file as a new file

			language.updateToolbars(); // updates the toolbars with the current language
			setLocale();	// updates the menus with the current language
			setUIManager();	// sets the interface manager

			ToolTipManager jttm = ToolTipManager.sharedInstance();
			jttm.setInitialDelay(2);
			addWindowListener(new WindowAdapter()	// listener to the window

			 {
				   public void windowClosing(WindowEvent e)
				   {
					   try
					   {
						   if(!currentfile.confirmClose())
						   		return;
						   currentfile.addinRecentFile();

						} // before closing the window, to write the recent files into a text file
						catch(Exception ewc) { System.out.println(ewc+"----->  exception at windows closing"); }
						System.exit(0);
				   }
			  } );

			showAttributes(0);	// initially set the UI according to the zeroth position of the workspace
			setDefaultFont();	// sets the default font for the document

    }

	/**
	  * Hashes all the actions in <code> Hashtable </code>
	  * @see #actionhash
	  */
    void hashDefaultActions()
    {
	      Action[] workspaceactions = workspace.getActions();
	      // gets the workspace actions and puts that in the hashtable
	      for( int i =0; i < workspaceactions.length;i++)
	      {
	          String actionname = (String)workspaceactions[i].getValue(Action.NAME);
	          actionhash.put(actionname,workspaceactions[i]);
	      }
    }

	/**
	  * returns an object of <code> Action </code> corresponding to an object of <code> String </code>.
	  * @param actionname Action name for which the <code> Action </code> object needed
	  * @return Action Object of <code> Action </code>
	  */
    Action getHashedAction(String actionname)
    {
	       return (Action)actionhash.get(actionname);
    }

	/**
	 * Gets the alignment actions form the hash table and binds with the key
	 */
    void makeActionsPretty()
    {
			Action actiontodo;
			actiontodo = getHashedAction("left-justify");
			actiontodo.putValue(Action.SMALL_ICON,ImagesLocator.getImage("rcword/Images/alignleft.gif"));
			actiontodo = getHashedAction("center-justify");
			actiontodo.putValue(Action.SMALL_ICON,ImagesLocator.getImage("rcword/Images/aligncenter.gif"));
			actiontodo.putValue(Action.NAME, "StyleConstants.ALIGN_CENTER");
			actiontodo = getHashedAction("right-justify");
			actiontodo.putValue(Action.SMALL_ICON,ImagesLocator.getImage("rcword/Images/alignright.gif"));
			actiontodo.putValue(Action.NAME, "StyleConstants.ALIGN_RIGHT");
	}

	/**
	* Creates the menu bar.
	*/
	protected JMenuBar createMenuBar()
	{
			JMenuBar tempmenubar = new JMenuBar();
			//tempmenubar.setForeground(Color.blue);//new Color(Color.blue));

			filemenu = new FileMenu(this);
			filemenu.setMnemonic((int)'F');	// sets mnemonic to file menu
			tempmenubar.add(filemenu);

			editmenu = new EditMenu(this);
			editmenu.setMnemonic((int)'E');	// sets mnemonic to edit menu
			tempmenubar.add(editmenu);

			JMenu viewmenu = new JMenu("ð£ó¢¬õ");
			viewmenu.setMnemonic((int)'V');	// sets mnemonic to view menu

			toolbarmenuitem = new JCheckBoxMenuItem("è¼õ¤ð¢˜ðì¢¬ì");
			formatbarmenuitem= new JCheckBoxMenuItem("Ü¬ñð¢¹ð¢šðì¢¬ì");
			statusbarmenuitem = new JCheckBoxMenuItem("ï¤¬ôð¢™ðì¢¬ì");
			rulerMenuItem = new JCheckBoxMenuItem("Ü÷¾«è£ô¢");

			toolbarmenuitem.setState(true);
			formatbarmenuitem.setState(true);
			statusbarmenuitem.setState(true);
			rulerMenuItem.setState(true);

			toolbarmenuitem.setFont(defaultfont);
			formatbarmenuitem.setFont(defaultfont);
			statusbarmenuitem.setFont(defaultfont);
			rulerMenuItem.setFont(defaultfont);

			toolbarmenuitem.addActionListener(this);
			formatbarmenuitem.addActionListener(this);
			statusbarmenuitem.addActionListener(this);
			rulerMenuItem.addActionListener(this);

			viewmenu.add(toolbarmenuitem);
			viewmenu.add(formatbarmenuitem);
			viewmenu.add(statusbarmenuitem);
			viewmenu.add(rulerMenuItem);

			viewmenu.setFont(defaultfont);
			tempmenubar.add(viewmenu);

				/*tempmenubar.add(makeMenu( "¸¬ö",
										new Object[]
							 {  "ðìñ¢" },
						this ) );

			JMenu temp_insert_menu = (JMenu)tempmenubar.getComponentAtIndex(3) ;
			temp_insert_menu.setMnemonic((int)'I');
			temp_insert_menu.getItem(0).setMnemonic((int)'P');
			*/

			tempmenubar.add(makeMenu( "Ü¬ñð¢¹",
										new Object[]
							 {  "ï¤¬ôšñ£ø¢Á",/*"°ñ¤ö¢è÷¢","âí¢è÷¢",*/"ï¤Áî¢î ï¤¬ô"
							 },
						this ) );

			JMenu tempmenu =(JMenu) tempmenubar.getComponentAtIndex(3);
			tempmenu.setMnemonic((int)'O');	// sets mnemonic to format menu
			tempmenu.getItem(0).setMnemonic((int)'C');		// sets mnemonic to the change case dialog
			//tempmenu.getItem(1).setMnemonic((int)'B');	// sets mnemonic to the bullets
			//tempmenu.getItem(2).setMnemonic((int)'N');	// sets mnemonic to the numbering
			tempmenu.getItem(1).setMnemonic((int)'T');		// sets mnemonic to tab stop dialog
			m_paragraphDialog = new ParagraphDialog(this);
			JMenuItem jmi_tempitem = new JMenuItem("ðî¢î¤...");
			jmi_tempitem.setFont(defaultfont);
			jmi_tempitem.setMnemonic('p');		// sets mnemonic to paragraph dialog
			ActionListener lst = new ActionListener()	// Listener to open the paragraph dialog
				 {
				   public void actionPerformed(ActionEvent e)
				   {
						 AttributeSet a = dsd_stydoc.getCharacterElement(
						   workspace.getCaretPosition()).getAttributes();	// gets the cursor position's attributes
						 m_paragraphDialog.setAttributes(a);	// sets that attributes to the paragraph dialog
						 m_paragraphDialog.setLocale();	// sets the current language to the paragraph dialog
						 m_paragraphDialog.show();
						 if (m_paragraphDialog.getOption()==JOptionPane.OK_OPTION)
						 { 	  // sets the selected attributes to the selected document portion and the corresponding UI
							  setAttributeSet(m_paragraphDialog.getAttributes(), true);
							  showAttributes(workspace.getCaretPosition());
						 }
				   }
				 };
			 jmi_tempitem.addActionListener(lst);
			 tempmenu.add(jmi_tempitem);

			 fd_fontdialog = new FontDialog(this);

			 JMenuItem jmi_fontitem = new JMenuItem("â¿î¢¶¼");    //
			 jmi_fontitem.setFont(defaultfont);
			 jmi_fontitem.setMnemonic('f');
			 ActionListener font_listener = new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						//WordProcessor.this.repaint();
						AttributeSet a = dsd_stydoc.getCharacterElement(
							workspace.getCaretPosition()).getAttributes();
						fd_fontdialog.setAttributes(a);
						fd_fontdialog.setLocale();
						fd_fontdialog.show();
						if (fd_fontdialog.getOption()==JOptionPane.OK_OPTION)
						{
							System.out.println(" 		Error status at Word button:"+fd_fontdialog.m_lstFontSize.errorOccured);

							setAttributeSet(fd_fontdialog.getAttributes(), false);
							showAttributes(workspace.getCaretPosition());
						}
					}
				};
			 jmi_fontitem.addActionListener(font_listener);
			 tempmenu.add(jmi_fontitem);
/*
			tempmenubar.add(makeMenu( "è¼õ¤è÷¢",
									new Object[]
						{  "ªê£ô¢¢ âí¢í¤è¢¬è","õó¤¬êð¢ð´î¢¶","English"
						},
					this ) );
			JMenu temp_toolsmenu =(JMenu) tempmenubar.getComponentAtIndex(4);
			temp_toolsmenu.setMnemonic((int)'T');		// sets mnemonic to the tools menu
			temp_toolsmenu.getItem(0).setMnemonic((int)'W');		// sets mnemonic to word count
			temp_toolsmenu.getItem(1).setMnemonic((int)'S');		// sets mnemonic to sort
			JMenuItem jmi_language = temp_toolsmenu.getItem(2);
			// sets shortcut key to toggle the UI's language with Tamil and English
			jmi_language.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_F12,0));
	*/
			/* tempmenubar.add(makeMenu( "Üì¢ìõ¬í",     // table
									new Object[]
						 {  "õ¬ó",
							makeMenu("¸¬ö",new Object[]{"ï¤ó½è¢° Þìð¢¹øñ¢","ï¤ó½è¢° õôð¢¹øñ¢",null,"ï¤¬óè¢° «ñô¢ðè¢èñ¢¢","ï¤¬óè¢° è¦ö¢ðè¢èñ¢"}, this),
							makeMenu(" Üö¤",new Object[]{"Üì¢ìõ¬í","ï¤¬ó","ï¤óô¢"}, this),
						 },
					this ) );

			tempmenubar.add(makeMenu( "ê£÷óñ¢",
									new Object[]
						 {  " "
						 },
					this ) );


			tempmenubar.add(makeMenu( "àîõ¤",
									new Object[]
								 {  "ªê£ô¢ô£÷ó¢ àîõ¤","ªê£ô¢ô£÷ó¢ ðø¢ø¤"

								 },
								this ) );
			*/

			toolsmenu = new ToolsMenu(this);
			toolsmenu.setMnemonic((int)'T');		// sets mnemonic to the help menu
			tempmenubar.add(toolsmenu);

			helpmenu = new HelpMenu(this);
			helpmenu.setMnemonic((int)'H');		// sets mnemonic to the help menu
			tempmenubar.add(helpmenu);

			return tempmenubar;
	}

	/**
	 * Creates the status bar
     */
    protected JPanel createStatusBar()
    {
    	 JPanel panel = new JPanel();
	     panel.setLayout(new FlowLayout(FlowLayout.LEFT));

	     JLabel lb = new JLabel("           ");//àîõ¤ F1 ");
	     lb.setFont(defaultfont);
	     panel.add(lb);
	     panel.add(jl_row);		// label to display the cursor's row position
	     panel.add(jl_collum);		// label to display the cursor's column position
	     return panel;

    }

	/**
	 * Creates the tool bar
	 */
    public JToolBar createToolBar()
    {
			JToolBar temptoolbar = new JToolBar();
			temptoolbar.setBorder(new EtchedBorder());
			temptoolbar.setFont(defaultfont);

            // action to add the new button
			NewAction new_action = new NewAction(this, ImagesLocator.getImage("rcword/Images/new.gif"));
			temptoolbar.add(new_action);
			// action to add the open button
			OpenAction open_action = new OpenAction(this, ImagesLocator.getImage("rcword/Images/open.gif"));
			temptoolbar.add(open_action);
			// action to add the save button
			SaveAction save_action = new SaveAction(this, ImagesLocator.getImage("rcword/Images/save.gif"));
			temptoolbar.add(save_action);

			temptoolbar.addSeparator();

			// action to add the cut button
			CutAction cutaction = new CutAction(this, ImagesLocator.getImage("rcword/Images/cut.gif"));
			temptoolbar.add(cutaction);
			// action to add the copy button
			CopyAction copy_action = new CopyAction(this,ImagesLocator.getImage("rcword/Images/copy.gif"));
			temptoolbar.add(copy_action);
			// action to add the paste button
			PasteAction paste_action = new PasteAction(this,ImagesLocator.getImage("rcword/Images/paste.gif"));
			temptoolbar.add(paste_action);

			temptoolbar.addSeparator();

			// action to add the undo button
			UndoAction undo_action = new UndoAction(this);
			undo_action.setEnabled(true);
			temptoolbar.add(undo_action);
			// action to add the redo button
			RedoAction redo_action = new RedoAction(this);
			redo_action.setEnabled(true);
			temptoolbar.add(redo_action);

		    temptoolbar.addSeparator();

			// action to add the fore ground button
			ForeGroundColorAction fg_action = new ForeGroundColorAction(this,ImagesLocator.getImage("rcword/Images/ttextcolour.gif"));
			temptoolbar.add(fg_action);
			// action to add the back ground button
            BackGroundColorAction bg_action = new BackGroundColorAction(this,ImagesLocator.getImage("rcword/Images/fillcolor.gif"));
			//temptoolbar.add(bg_action); // this is temporarily commented

			return temptoolbar;
	  }

	  /**
	   * Creates the format bar.
	   */
	  public JToolBar createFormatBar()
	  {
			JToolBar tempformatbar = 	 new JToolBar();
			tempformatbar.setBorder(new EtchedBorder());
			tempformatbar.setLayout(new FlowLayout(FlowLayout.LEFT));

			fontnamecombobox = new JComboBox();
			// gets the fonts available in the system and fix it in the font name combo box
			String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
			for (int i = 0; i < fonts.length; i++)
			{
				  fontnamecombobox.addItem(fonts[i]);
			}

			fontnamecombobox.addActionListener(this);
			//fontnamecombobox.setBounds(10,50,50,50);
			//fontnamecombobox.setPreferredSize(new Dimension(50,20));
			fontnamecombobox.setFont(defaultfont);
            fontnamecombobox.setToolTipText("Font Name");
			tempformatbar.add(fontnamecombobox);

			String sizeitems[]= {"8","10","12","14","16","18","20"};
			fontsizecombobox = new JComboBox(sizeitems);
			fontsizecombobox.setMaximumSize(fontsizecombobox.getPreferredSize());
			fontsizecombobox.setEditable(true);
			//fontsizecombobox.setBounds(100,37,50,35);
			fontsizecombobox.setFont(defaultfont);
			// 	fontsizecombobox.setSelectedIndex(1);
			fontsizecombobox.addActionListener(this);
			fontsizecombobox.setToolTipText("Font size");
			//fontsizecombobox.addItemListener(itlisten);

			tempformatbar.add(fontsizecombobox);

			fontstylecombobox = new JComboBox();
			fontstylecombobox.setMaximumSize(fontstylecombobox.getPreferredSize());
			fontstylecombobox.setEditable(true);

			ActionListener stylelistener = new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
							if (workspace_update || fontstylecombobox.getItemCount()==0)
								return;
							String name = (String)fontstylecombobox.getSelectedItem();
							int index = fontstylecombobox.getSelectedIndex();
							int pt = workspace.getCaretPosition();

							// New style name entered
							if (index == -1)
							{
								fontstylecombobox.addItem(name);
								Style style = dsd_stydoc.addStyle(name, null);
								AttributeSet a = dsd_stydoc.getCharacterElement(pt).getAttributes();
								style.addAttributes(a);
								return;
							}

							// Apply the selected style
							Style currStyle = dsd_stydoc.getLogicalStyle(pt);
							if (!currStyle.getName().equals(name))
							{
								Style style = dsd_stydoc.getStyle(name);
								setAttributeSet(style);
							}
					}
				};
			fontstylecombobox.addActionListener(stylelistener);
			//tempformatbar.add(fontstylecombobox);

			jtb_bold = new JToggleButton("",ImagesLocator.getImage("rcword/Images/tbold.gif"));
			jtb_bold.setBorder(new BevelBorder(BevelBorder.LOWERED));
			ActionListener boldlistener = new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						MutableAttributeSet attr = new SimpleAttributeSet();
						StyleConstants.setBold(attr, jtb_bold.isSelected());
						setAttributeSet(attr);
						workspace.grabFocus();
					}
				};
			jtb_bold.addActionListener(boldlistener);
			jtb_bold.setToolTipText("Bold");
			tempformatbar.add(jtb_bold);

			jtb_italic = new JToggleButton("",ImagesLocator.getImage("rcword/Images/titalic.gif"));
			jtb_italic.setBorder(new BevelBorder(BevelBorder.LOWERED));
			ActionListener italiclistener = new ActionListener()
			  {
					public void actionPerformed(ActionEvent e)
					{
						MutableAttributeSet attr = new SimpleAttributeSet();
						StyleConstants.setItalic(attr, jtb_italic.isSelected());
						setAttributeSet(attr);
						workspace.grabFocus();
					}
				};
			jtb_italic.addActionListener(italiclistener);
			jtb_italic.setToolTipText("Italic");
			tempformatbar.add(jtb_italic);

			jtb_underline = new JToggleButton("",ImagesLocator.getImage("rcword/Images/tunderline.gif"));
			jtb_underline.setBorder(new BevelBorder(BevelBorder.LOWERED));
			ActionListener underlinelistener = new ActionListener()
	    		{
					public void actionPerformed(ActionEvent e)
					{
						MutableAttributeSet attr = new SimpleAttributeSet();
						StyleConstants.setUnderline(attr, jtb_underline.isSelected());
						setAttributeSet(attr);
						workspace.grabFocus();
					}
				};
			jtb_underline.addActionListener(underlinelistener);
			jtb_underline.setToolTipText("Underline");
			tempformatbar.add(jtb_underline);

			jtb_leftalign = new JToggleButton("",ImagesLocator.getImage("rcword/Images/alignleft.gif"));
			jtb_leftalign.setBorder(new BevelBorder(BevelBorder.LOWERED));
			ActionListener leftalignlistener = new ActionListener()
				  {
					  public void actionPerformed(ActionEvent e)
					  {
						    //System.out.println("align left");
							MutableAttributeSet attr = new SimpleAttributeSet();
							StyleConstants.setAlignment(attr,StyleConstants.ALIGN_LEFT);
							setAttributeSet(attr);
							workspace.grabFocus();
					  }
				  };
			jtb_leftalign.addActionListener(leftalignlistener);
			jtb_leftalign.setToolTipText("Left Align");
			//tempformatbar.add(jtb_leftalign);

			 jtb_centeralign = new JToggleButton("",ImagesLocator.getImage("rcword/Images/aligncenter.gif"));
			jtb_centeralign.setBorder(new BevelBorder(BevelBorder.LOWERED));
			ActionListener centeralignlistener = new ActionListener()
				  {
					  public void actionPerformed(ActionEvent e)
					  {
							MutableAttributeSet attr = new SimpleAttributeSet();
							StyleConstants.setAlignment(attr,StyleConstants.ALIGN_CENTER);
							setAttributeSet(attr);
							workspace.grabFocus();
					  }
				  };
			jtb_centeralign.addActionListener(centeralignlistener);
			jtb_centeralign.setToolTipText("Centeralign");
			//tempformatbar.add(jtb_centeralign);

             // gets the align actions from the hash table and fix it in the format bar
			 tempformatbar.add(getHashedAction("left-justify")).setText("");
			 tempformatbar.add(getHashedAction("center-justify")).setText("");
			 tempformatbar.add(getHashedAction("right-justify")).setText("");

			 jtb_subscript = new JToggleButton("",ImagesLocator.getImage("rcword/Images/tsubscript.gif"));
			 jtb_subscript.setBorder(new BevelBorder(BevelBorder.LOWERED));
			 ActionListener subscriptlistener = new ActionListener()
				  {
					  public void actionPerformed(ActionEvent e)
					  {
							MutableAttributeSet attr = new SimpleAttributeSet();
							StyleConstants.setSubscript(attr,jtb_subscript.isSelected());
							setAttributeSet(attr);
							workspace.grabFocus();
					  }
				  };
			jtb_subscript.addActionListener(subscriptlistener);
			jtb_subscript.setToolTipText("Subscript");
			tempformatbar.add(jtb_subscript);

			jtb_superscript = new JToggleButton("",ImagesLocator.getImage("rcword/Images/tsuperscript.gif"));
			jtb_superscript.setBorder(new BevelBorder(BevelBorder.LOWERED));
			ActionListener superscriptlistener = new ActionListener()
				  {
					  public  void actionPerformed(ActionEvent e)
					  {
							MutableAttributeSet attr = new SimpleAttributeSet();
							StyleConstants.setSuperscript(attr,jtb_superscript.isSelected());
							setAttributeSet(attr);
							workspace.grabFocus();
						}
				  };
			jtb_superscript.addActionListener(superscriptlistener);
			jtb_superscript.setToolTipText("Superscript");
			tempformatbar.add(jtb_superscript);

			jtb_strikeThrough = new JToggleButton("",ImagesLocator.getImage("rcword/Images/tstrikethrough.gif"));
			jtb_strikeThrough.setBorder(new BevelBorder(BevelBorder.LOWERED));
			ActionListener strikeThroughlistener = new ActionListener()
				  {
					  public void actionPerformed(ActionEvent e)
					  {
							MutableAttributeSet attr = new SimpleAttributeSet();
							StyleConstants.setStrikeThrough(attr,jtb_strikeThrough.isSelected());
							setAttributeSet(attr);
							workspace.grabFocus();
						}
				  };
			jtb_strikeThrough.addActionListener(strikeThroughlistener);
			jtb_strikeThrough.setToolTipText("StrikeThrough");
			tempformatbar.add(jtb_strikeThrough);

			tempformatbar.setFont(defaultfont);
			return tempformatbar;
	   }

	   /**
	    * Creates and returns a menu with the given parent, array of menu items and listener .
	    * @param parent String or menu
	    * @param items  array of menu items to be added in the menu
	    * @param target Listener to listen this menu
	    * @return JMenu a newly created <code> JMenu </code> object
        */
	   public static JMenu makeMenu(Object parent,Object[] items,Object target)
	   {
	   	      JMenu tempmenu = null;

	   	        if(parent instanceof JMenu)
	   	      	     tempmenu = (JMenu)parent;
	   	      	else if(parent instanceof String)
	   	      		    tempmenu = new JMenu( (String)parent);
	   	      	else    return null;

	   	      	for( int i=0; i<items.length; i++)
	   	        {
					  if(items[i] == null)
	   	       	      	      tempmenu.addSeparator();
	   	       	      else if( ( items[i].toString()).substring(0,1).equals("+"))
	   	       	               tempmenu.add(makeCheckButtonMenuItem( ((String)items[i]).substring(1),target));
	   	       	     	else
	   	       	      		     tempmenu.add(makeMenuItem( items[i],target));
				}

	   	      tempmenu.setFont(defaultfont);
	   	      return tempmenu;
	   }

	   /**
	   * Creates a menu item with the given object and listener.
	   * @param item the content of this menu item
	   * @param target Listener to listen this menu item
	   * @return JMenuItem a newly created <code> JMenuItem </code> object
	   */
	   public static JMenuItem makeMenuItem(Object item,Object target)
	   {
	   	       JMenuItem tempmenuitem = null;

	   	       if(item instanceof String)
	   	       {
	   	       	    tempmenuitem = new JMenuItem( (String)item);
	   	       }
	   	       else if(item instanceof JMenuItem)
	   	       	    tempmenuitem = (JMenuItem) item;
	   	       else  return null;

	   	       if( target instanceof ActionListener)
	   	       	    tempmenuitem.addActionListener( (ActionListener)target);

	   	       tempmenuitem.setFont(defaultfont);
	   	       return tempmenuitem;
	   }


	  /**
	   * Creates a checkbox menu item with the given object and listener.
	   * @param item the content of this checkbox menu item
	   * @param target Listener to listen this checkbox menu item
	   * @return JMenuItem a newly created <code> JCheckBoxMenuItem </code> object
	   */
	  public static JCheckBoxMenuItem makeCheckButtonMenuItem(Object item,Object target)
	  {
	           JCheckBoxMenuItem tempcbmenuitem = null;

	   	       if(item instanceof String)
	   	       	     	 tempcbmenuitem =  new JCheckBoxMenuItem((String)item,true);
	   	       else if(item instanceof JMenuItem)
	   	       	    tempcbmenuitem = (JCheckBoxMenuItem) item;
	   	       else  return null;

	   	       if( target instanceof ActionListener)
	   	       	    tempcbmenuitem.addActionListener( (ActionListener)target);

	   	       tempcbmenuitem.setFont(defaultfont);
	   	       return tempcbmenuitem;
	  }

	 /**
	  * Invoked when an action occurs.
	  * @param event an object of ActionEvent which causes the action.
	  */
	 public void actionPerformed(ActionEvent event)
	 {
		 	// gets the source of the event and checks its property like JMenuItem or JCheckBoxMenuItem

		 	if(event.getSource() instanceof JCheckBoxMenuItem)
		 	{
				JMenuItem temp_menuitem  = (JMenuItem)event.getSource();
				String arg = event.getActionCommand();

				if(arg.equals("Ü÷¾«è£ô¢") || arg.equals("Ruler") )
				{
					if(rulerMenuItem.getState())
					{
						workspacescrollpane.setColumnHeaderView(columnView);
						workspacescrollpane.setRowHeaderView(rowView);
					}
					else
					{
						workspacescrollpane.setColumnHeaderView(null);
						workspacescrollpane.setRowHeaderView(null);
					}
					workspace.repaint();
				}
				else if(arg.equals("è¼õ¤ð¢˜ðì¢¬ì") || arg.equals("Ü¬ñð¢¹ð¢šðì¢¬ì") || arg.equals("ï¤¬ôð¢™ðì¢¬ì")  || arg.equals("Toolbar") || arg.equals("Formatbar") || arg.equals("Statusbar"))
				{    // update the frame with the selected bars
					updateFrame();
					workspacescrollpane.setColumnHeaderView(columnView);
					workspacescrollpane.setRowHeaderView(rowView);
					workspace.repaint();
				}

			}
			else if(event.getSource() instanceof JMenuItem)
			{
				JMenuItem temp_menuitem  = (JMenuItem)event.getSource();
				String arg = event.getActionCommand();

				if(arg.equals("ªê£ô¢¢ âí¢í¤è¢¬è") || arg.equals("Word Count")) // wordcount action
				{
					wdcount= new WordCount(this);
					wdcount.show();
				}
				else if(arg.equals("õó¤¬êð¢ð´î¢¶") || arg.equals("Sort"))  // sorting action
				{
					sort = new Sorter(this);
				}
				else if(arg.equals("ï¤¬ôšñ£ø¢Á") || arg.equals("Change Case"))   // change case  action
				{
					CaseDialog cdcase = new CaseDialog(this);
					cdcase.show();
				}
				else if(arg.equals("°ñ¤ö¢è÷¢") || arg.equals("Bullets"))   // bullets action
				{
					try
					{
						bults.activateBullets();
					}
					catch(Exception e )
					{
						  System.out.println(e+"\n -----> e at bullets activating ");
						  //e.printStackTrace();
					}
				}
				else if(arg.equals("âí¢è÷¢")  || arg.equals("Numbering")) 	// numbering action
				{
					Numbering number = new Numbering(this);
				}
				else if(arg.equals("õ¬ó") || arg.equals("Draw"))  // Draw table
				{
					table = new Table(this);
					table.show();
				}
				else if(arg.equals("ðí¢¹è÷¢") || arg.equals("Properties"))
				{
					try
					{
						/*property = new FileProperty(this,workspace);
						property.show();
						//System.out.println("file " + fileproperty.toString());
						*/
						wdcount= new WordCount(this);
						wdcount.show();
					}
					catch(Exception e)
					{	System.out.println(e+"\n -----> e at file property activating ");
					}
				}
				else if(arg.equals("ï¤Áî¢î ï¤¬ô") || arg.equals("Tab Stop"))  // tabstop
				{
					if(tab == null)
					tab = new Tab(this);
					tab.showList();
					tab.setLocale();
					tab.show();
				}
				else if(arg.equals("ï¤¬óè¢° «ñô¢ðè¢èñ¢¢")  || arg.equals("Row"))  // insert row above
				{
					String[] st= { "","","",""};
					// gets the current focused table object
					JTable jt_currenttable =(JTable) al_tablelist.get( al_tableStatus.indexOf(ON) );
					DefaultTableModel dtm_model = (DefaultTableModel)jt_currenttable.getModel();
					dtm_model.insertRow(table.CURENT_ROW,st);
					jt_currenttable.repaint();
				}
				else if(arg.equals("ï¤¬óè¢° è¦ö¢ðè¢èñ¢"))  // insert row bellow
				{
					// gets the current focused table object
					JTable jt_currenttable =(JTable) al_tablelist.get( al_tableStatus.indexOf(ON) );
					int col_no = jt_currenttable.getColumnCount();
					Object rr[] = new Object[col_no];
					for(int i=0;i<col_no;i++)
						rr[i] = "";
					DefaultTableModel dtm_model = (DefaultTableModel)jt_currenttable.getModel();
					dtm_model.insertRow(table.CURENT_ROW+1,rr);
					jt_currenttable.repaint();
				}
				else if(arg.equals("ï¤ó½è¢° Þìð¢¹øñ¢"))  // column left
				{
					// gets the current focused table object
					JTable jt_currenttable =(JTable) al_tablelist.get( al_tableStatus.indexOf(ON) );
					DefaultTableModel dtm_model = (DefaultTableModel)jt_currenttable.getModel();
					// adds a new column at the end and moves the last column to the required place
					dtm_model.addColumn((Object)"");
					jt_currenttable.moveColumn(jt_currenttable.getColumnCount()-1,table.CURENT_COLUMN);
					jt_currenttable.repaint();
				}
				else if(arg.equals("ï¤ó½è¢° õôð¢¹øñ¢"))  // collum left
				{
					// gets the current focused table object
					JTable jt_currenttable =(JTable) al_tablelist.get( al_tableStatus.indexOf(ON) );
					DefaultTableModel dtm_model = (DefaultTableModel)jt_currenttable.getModel();
					dtm_model.addColumn((Object)"");
					jt_currenttable.moveColumn(jt_currenttable.getColumnCount()-1,table.CURENT_COLUMN+1);
					jt_currenttable.repaint();
				}
				else if(arg.equals("Üì¢ìõ¬í"))    // remove table
				{
					// gets the current focused table object
					JTable jt_currenttable =(JTable) al_tablelist.get( al_tableStatus.indexOf(ON) );
					workspace.remove(jt_currenttable);
					workspace.removeAll();
					workspace.repaint();
				}
				else if(arg.equals("ï¤óô¢")) 	// remove column
				{
					// gets the current focused table object
					JTable jt_currenttable =(JTable) al_tablelist.get( al_tableStatus.indexOf(ON) );
					int col_no = table.CURENT_COLUMN;      // get the current focused column number
					TableColumn col_toremove = jt_currenttable.getColumnModel().getColumn(col_no);  // get the current focused column object
					jt_currenttable.getColumnModel().removeColumn(col_toremove);
				}
				else if(arg.equals("ï¤¬ó")) // row
				{
					// gets the current focused table object
					JTable jt_currenttable =(JTable) al_tablelist.get( al_tableStatus.indexOf(ON) );
					int row_no = table.CURENT_ROW;
					DefaultTableModel dtm_model = (DefaultTableModel)jt_currenttable.getModel();
					dtm_model.removeRow(row_no);
				}
				else if(arg.equals("English") || arg.equals("îñ¤ö¢"))
				{
					if(current_language)
					{  /* if the language is Tamil,then conversion is Tamil --> English
						Conversion is first set the current language as English, set the
						resource bundle as current language's resource bundle then update
						the menu and tool bars with the current language
						*/
						current_language = false;
						current_locale = new Locale("en","US");
						wordBundle = ResourceBundle.getBundle("WordBundle",current_locale);
						language.updateMenubar();
						language.updateToolbars();
					}
					else
					{   /* if the language is English, then conversion is English --> Tamil
						Conversion is first set the current language as Tamil, set the
						resource bundle as current language's resource bundle then update
						the menu and tool bars with the current language
						*/
						current_language = true;
						current_locale = new Locale("ta","IN");
						wordBundle = ResourceBundle.getBundle("WordBundle",current_locale);
						//language.updateLanguageNew();
						language.updateMenubar();
						language.updateToolbars();
					}

				}

				else if(arg.equals("ðìñ¢") || arg.equals("Picture"))  // picture
				{
					try
					{
						insertPicture();
					}
					catch(Exception ep)
					{
						System.out.println(ep+"\n---> e at :picture insertion Word");
					}
			    }

			}
			else  // non menuitem
			{
				if(event.getSource() ==  fontnamecombobox)
				{   // updates the font name attribute
					String m_fontName = fontnamecombobox.getSelectedItem().toString();
					MutableAttributeSet attr = new SimpleAttributeSet();
					StyleConstants.setFontFamily(attr, m_fontName);
					setAttributeSet(attr);
					workspace.grabFocus();
				}
				else if(event.getSource() == fontsizecombobox)
				{
					// updates the font size attribute
					int 	fontSize = 0;
					try
					{
						fontSize = Integer.parseInt(fontsizecombobox.getSelectedItem().toString());
					}
					catch (NumberFormatException ex)
					{
						Utils.warning(this,"InvalidNumber");
						fontsizecombobox.setSelectedItem(Integer.toString(currentfontsize));
						return;
					}

					if(fontSize > 1200)
						fontSize = 1200;
					MutableAttributeSet attr = new SimpleAttributeSet();
					StyleConstants.setFontSize(attr, fontSize);
					setAttributeSet(attr);
					workspace.grabFocus();
				}
			}
			menubar.repaint();
	 }

	 /**
	  * Gets the attributes of cursor's current position and fix it in user interface.
	  * @param p cursor's current position
	  */
     protected void showAttributes(int p)
  	 {
		    if(p!=0)
		    	p=p-1;
    		workspace_update = true;	// to indicate that the document has updated
        	try
  	 	    {       /* gets the cursor's current position's attributes and
  	 	    		   sets the UI according to this
  	 	    		*/
    				AttributeSet a = dsd_stydoc.getCharacterElement(p).getAttributes();
    				String fname = StyleConstants.getFontFamily(a);
    				if (!currentfontname.equals(fname))
    				{
				    	  currentfontname = fname;
      					fontnamecombobox.setSelectedItem(fname);
    				}
    				int size = StyleConstants.getFontSize(a);
    				if (currentfontsize != size)
    				{
				    	  currentfontsize = size;
      					fontsizecombobox.setSelectedItem(Integer.toString(currentfontsize));
    				}

    				boolean bold = StyleConstants.isBold(a);
    				if (bold != jtb_bold.isSelected())
      					jtb_bold.setSelected(bold);
     				boolean italic = StyleConstants.isItalic(a);
    				if (italic != jtb_italic.isSelected())
      					jtb_italic.setSelected(italic);
					boolean underline = StyleConstants.isUnderline(a);
					if (underline != jtb_underline.isSelected())
						  jtb_underline.setSelected(underline);
				    boolean subscript = StyleConstants.isSubscript(a);
				    if( subscript != jtb_subscript.isSelected())
				          jtb_subscript.setSelected(subscript);
				    boolean superscript = StyleConstants.isSuperscript(a);
				    if( superscript != jtb_superscript.isSelected())
				          jtb_superscript.setSelected(superscript);
				    boolean strikethrough = StyleConstants.isStrikeThrough(a);
				    if( strikethrough != jtb_strikeThrough.isSelected())
				          jtb_strikeThrough.setSelected(strikethrough);

					//System.out.println(" Alignment is :"+StyleConstants.getAlignment(a));
					//System.out.println(" left :"+StyleConstants.ALIGN_LEFT);
					//System.out.println(" right:"+StyleConstants.ALIGN_RIGHT);
					//System.out.println("center:"+StyleConstants.ALIGN_CENTER);
					switch(StyleConstants.getAlignment(a))
					  {
						   case StyleConstants.ALIGN_CENTER :
						   {
								  jtb_centeralign.setSelected(true);
								  jtb_leftalign.setSelected(false);
								  break;
						   }

						   case StyleConstants.ALIGN_LEFT:
						   {
								  jtb_centeralign.setSelected(false);
								  jtb_leftalign.setSelected(true);
								  break;
						   }
					  }
  	 	 }
  	 	 catch(Exception e)
  	 	 {
  	 	 	  System.out.println(e+"------>  ssssssnnnno:");
  	 	 	  e.printStackTrace();
  	 	 }

		workspace_update = false;
		repaint();
     }

    /**
     * Sets the given attributes to the selected paragraph/text with the decision parameter.
     * @param attr attributes to be set to the selected text
     * @param setParagraphAttributes if true, sets paragraph attributes
     *								 if false, sets character attributes
     */
    protected void setAttributeSet(AttributeSet attr, boolean setParagraphAttributes)
	  {
			int xStart = workspace.getSelectionStart();
			int xFinish = workspace.getSelectionEnd();
			//if (!m_monitor.hasFocus()) {
			//  xStart = m_xStart;
			//  xFinish = m_xFinish;
			//}
			if(setParagraphAttributes)
			  dsd_stydoc.setParagraphAttributes(xStart,xFinish - xStart, attr, false);
			else if (xStart != xFinish)
			{
			  //dsd_stydoc.setCharacterAttributes(xStart,xFinish - xStart, attr, false);
				workspace.setCharacterAttributes(attr, false);
			}
			else
			{
			  if(editorkit_type == RTF)
			  {
				  MutableAttributeSet inputAttributes =
				  rtfkit.getInputAttributes();
				 inputAttributes.addAttributes(attr);
			  }
			  else
			  {
				  //System.out.println("Something has to do for default editor kit");
			  }
			}
	  }


     /**
      * Sets the given attributes to the selected text
      * @param att attributes to be set to the selected text
      */
	 protected void setAttributeSet(AttributeSet att)
	 {
			if (workspace_update)
				  return;
			int tempstart = workspace.getSelectionStart();
			int tempend = workspace.getSelectionEnd();
			if (!workspace.hasFocus())
			{
				  tempstart = caretstart;
				  tempend = caretend;
			}
			if (tempstart != tempend)
			{
					//dsd_stydoc.setCharacterAttributes(tempstart, tempend - tempstart, att, false);
					workspace.setCharacterAttributes(att, false);
			}
			else
			{
				   // here "some thing has to do done "
				   //dsd_stydoc.setCharacterAttributes(tempstart,0, att, false);
				   workspace.setCharacterAttributes(att, false);
			}
			workspace.repaint();
			repaint();
     }

	 /**
	  * Shows the styles in the style combo box.
	  */
     protected void showStyles()
     {
  			  workspace_update = true;
	    		if (fontstylecombobox.getItemCount() > 0)
      		       fontstylecombobox.removeAllItems();
		    	Enumeration en = dsd_stydoc.getStyleNames();
    			while (en.hasMoreElements())
    			{
				      String styname = en.nextElement().toString();
      				fontstylecombobox.addItem(styname);
    			}
    			workspace_update = false;
  	 }

	 /**
	  * Updates the frame. Checks the selected toolbars and updates the frame with the
	  * selected toolbars only and shows it accordingly.
	  */
	 public void updateFrame()
     {
		if(toolbarmenuitem.getState() && formatbarmenuitem.getState() && statusbarmenuitem.getState())
		{  // all are selected
			toolbar.setBounds(0,0,790,36);
			formatbar.setBounds(0,37,790,36);
			workspacescrollpane.setBounds(0,75,790,420);
			statusbar.setBounds(0,496,790,36);
		}
		else if(toolbarmenuitem.getState() && formatbarmenuitem.getState() && !statusbarmenuitem.getState() )
		{	// except status bar
			toolbar.setBounds(0,0,790,36);
			formatbar.setBounds(0,37,790,36);
			workspacescrollpane.setBounds(0,75,790,420+36);
		}

		else if(toolbarmenuitem.getState() && !formatbarmenuitem.getState() && statusbarmenuitem.getState())
		{ 	// except format bar
			toolbar.setBounds(0,0,790,36);
			workspacescrollpane.setBounds(0,37,790,420+36);
			statusbar.setBounds(0,496,790,36);
		}

		else if(toolbarmenuitem.getState() && !formatbarmenuitem.getState()  && !statusbarmenuitem.getState())
		{	// toolbar only selected
			toolbar.setBounds(0,0,790,36);
			workspacescrollpane.setBounds(0,37,790,420+36+36);
		}

		else if( !toolbarmenuitem.getState() && formatbarmenuitem.getState() && statusbarmenuitem.getState())
		{	// except tool bar all are selected
			formatbar.setBounds(0,0,790,36);
			workspacescrollpane.setBounds(0,37,790,420+36);
			statusbar.setBounds(0,496,790,36);
		}
		else if( !toolbarmenuitem.getState() && formatbarmenuitem.getState() && !statusbarmenuitem.getState())
		{	//format bar only selected
			formatbar.setBounds(0,0,790,36);
			workspacescrollpane.setBounds(0,37,790,420+36+36);
		}

		else if( !toolbarmenuitem.getState() && !formatbarmenuitem.getState() && statusbarmenuitem.getState())
		{	// status bar only selected
			workspacescrollpane.setBounds(0,0,790,420+36+36);
			statusbar.setBounds(0,496,790,36);
		}
		else if( !toolbarmenuitem.getState() && !formatbarmenuitem.getState() && !statusbarmenuitem.getState())
		{	// none of the tool bars selected
			workspacescrollpane.setBounds(0,0,790,420+36+36+36);
		}

		if(toolbarmenuitem.getState())
			toolbar.show(true);
		else
			toolbar.show(false);
		if(formatbarmenuitem.getState())
			formatbar.show(true);
		else
			formatbar.show(false);
		if(statusbarmenuitem.getState())
			statusbar.show(true);
		else
			statusbar.show(false);
		//ruler.addToContentPane();
		repaint();
    }

    /**
     * Sets the default font for the document.
     */
    public void setDefaultFont()
    {
		StyledDocument doc = workspace.getStyledDocument();
		MutableAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setFontFamily(attr, "TAB-Anna");
		doc.setParagraphAttributes(0,10,attr,true);
		textChanged = false;
		workspace.repaint();
	}

	/**
	 * Resets the undo manager.
	 */
    protected void resetUndoManager()
    {
		undo.discardAllEdits();
		undoAction.update();
		redoAction.update();
    }

	/**
	 * Returns a reference of this class.
	 */
	protected Word getparent() { return this; }

	/**
	 * Calls the system's default printer property to set page attributes.
	 * @see #printData
	 * @see #print
	 * @see java.awt.print.PrinterJob#pageDialog
	 */
	public void pageSetup()
	{
		PrinterJob printJob = PrinterJob.getPrinterJob();
		// if the user has already changed the page format, show the last
		// selected options
		if (pageFormat == null)
			pageFormat = printJob.pageDialog(printJob.defaultPage());
		else
			pageFormat = printJob.pageDialog(printJob.defaultPage(pageFormat));
	}


   /**
    * Prints the current document
    * @see java.awt.print.PrinterJob
    */
   public void printData()
   {
	   getJMenuBar().repaint();
	   try
	   {
		  PrinterJob prnJob = PrinterJob.getPrinterJob();
		  prnJob.setPrintable(this);
		  if (!prnJob.printDialog())
			return;
		  setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		  prnJob.print();
		  setCursor( Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		  Object[] options = {wordBundle.getString("Ok")};
		  Utils.showDialog(this,"PrintingCompleted",JOptionPane.OK_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null , options, 0);
	   }
	   catch (PrinterException e)
	   {
			 System.err.println(e+"\n----> e at printing");
	   }
    }

	/**
	 * Retrieves the portion of the user interface to print
	 */
    public int print(Graphics pg, PageFormat pageFormat,int pageIndex)
	                                    throws PrinterException
	{
			pg.translate((int)pageFormat.getImageableX(),
			  							(int)pageFormat.getImageableY());
			int wPage = (int)pageFormat.getImageableWidth();
			int hPage = (int)pageFormat.getImageableHeight();
			pg.setClip(0, 0, wPage, hPage);

			// Only do this once per print
			if (m_printView == null)
			{
				  BasicTextUI btui = (BasicTextUI)workspace.getUI();
				  View root = btui.getRootView(workspace);
				  m_printView = new PrintView(dsd_stydoc.getDefaultRootElement(),
														root, wPage, hPage,this);
			}

			boolean bContinue = m_printView.paintPage(pg,hPage, pageIndex);
			System.gc();

			if (bContinue)
			  return PAGE_EXISTS;
			else
			{
				m_printView = null;
				return NO_SUCH_PAGE;
			}
      }// end of method print

	  /**
	   * Inserts the selected picture in the cursor's current position.
	   */
      public void insertPicture()
      {

			SimpleFilter m_imageFilter = new SimpleFilter("jpg", "ImageFiles");

  		    final JFileChooser  m_chooser = new JFileChooser();
            m_chooser.setCurrentDirectory(new File("."));
            m_chooser.addChoosableFileFilter(m_imageFilter);
            m_chooser.setFileFilter(m_imageFilter);
            m_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			m_chooser.setAcceptAllFileFilterUsed(false);
            currentfile.setLanguage(m_chooser);		// sets the current language for the File chooser
			if (m_chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			  return;

			File fChoosen = m_chooser.getSelectedFile();
			ImageIcon icon = new ImageIcon(fChoosen.getPath());
			int w = icon.getIconWidth();
			int h = icon.getIconHeight();
			if (w<=0 || h<=0)
			{	// error occurred due to the image file
			  JOptionPane.showMessageDialog(this,"Error reading image file\n"+
												fChoosen.getPath(), "Warning",
												JOptionPane.WARNING_MESSAGE);
				return;
			}
			MutableAttributeSet attr = new SimpleAttributeSet();
			StyleConstants.setIcon(attr, icon);
			int p = workspace.getCaretPosition();
			try
			{
			  dsd_stydoc.insertString(p, " ", attr);
			}
			catch (BadLocationException ex)
			{
				System.out.println(ex+"\n-------> e at inserting the picture");
			}

	}

    /**
     * Sets the user interface manager.
     */
	public void setUIManager()
	{
	  		UIManager.put("Button.font",defaultfont);
	  		UIManager.put("ComboBox.font",defaultfont);
	  		UIManager.put("Label.font",defaultfont);
	  		UIManager.put("CheckBox.font",defaultfont);
	  		UIManager.put("RadioButton.font",defaultfont);
	  		UIManager.put("TitledBorder.font",defaultfont);
	  		UIManager.put("ToolTip.font",defaultfont);
	  		UIManager.put("FileChooser.font",defaultfont);
	}

 	/**
	 * Used to listen the mouse action to open popup menu.
	 * @version 5.12.2002
	 * @author RCILTS-Tamil,MIT
     */
	class SymMouse extends java.awt.event.MouseAdapter
	{
		/**
		 * Invoked when a mouse button has been released on a component.
		 * @see java.awt.event.MouseAdapter#mouseReleased
		 */
		public void mouseReleased(java.awt.event.MouseEvent event)
		{
			super.mouseReleased(event);
			if(SwingUtilities.isRightMouseButton(event))
			{
					WordPopup wordPopup = new WordPopup(Word.this);
					wordPopup.show(event.getComponent(),event.getX(),event.getY());
			}
		}

		/**
		 * Invoked when a mouse button has been pressed on a component.
		 * @see java.awt.event.MouseAdapter#mousePressed
		 */
		public void mousePressed(java.awt.event.MouseEvent event)
		{ }
	}

	/**
	 * Sets the current locale to the user interface.
	 */
	public void setLocale()
	{
		filemenu.setLocale();
		toolsmenu.setLocale();
		editmenu.setLocale();
		helpmenu.setLocale();
	}

	class DocHandler implements DocumentListener
	{
		public void changedUpdate(DocumentEvent e)
		{
			textChanged = true;

		}

		public void insertUpdate(DocumentEvent e)
		{
			textChanged = true;
		}

		public void removeUpdate(DocumentEvent e)
		{
			textChanged = true;
		}
	}

	public Clipboard getClipboard()
	{
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		return c;
	}

	// --- Implementation of Clipboard interface ----------------
	/**
	* Notifies this object that it is no longer the owner of the contents of the
	* clipboard.
	*/
	public void lostOwnership(Clipboard clipboard,Transferable contents)
	{
	}
		// -------------------------------------------------------------------------


} // end of rcwundo0


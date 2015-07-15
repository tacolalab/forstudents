     /**		CODE OPTIMIZED

* @(#)Chathurangam.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*
* This software is the confidential and proprietary information of
* RCILTS-Tamil. ("Confidential Information"). You shall not
* disclose such Confidential Information and shall use it only in
* accordance with the terms of the license agreement you entered into
* with RCILTS-Tamil.00
*/

/*Run this file. It uses the Worksheet, SSMenu files*/
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import javax.swing.border.*;
import java.lang.*;
import java.awt.datatransfer.*;
import java.awt.print.*;
import javax.swing.DefaultCellEditor;
import javax.swing.undo.*;

/**
 This is the main class of the application.(Chathurangam.java)
 It contains two constructors one invokes when the application
 starts and the other one invokes when the saved file opens.
 It uses mainly WorkSheet class and SSMenu class.
 Worksheet class is used for data and formula.
 SSMenu class is used to set the menubar for the application window.
 The ExcelAdapter class is used to enable Copy-Paste functionalities
 between the spreadsheet application and MS-Excel.
 Font is bind with the application package
 if the system does not have the Tamil font.(TABKural.ttf).

 * @version 1.02 09 Dec 2002
 * @author RCILTS_Tamil
 */

public class Chathurangam extends JFrame implements ActionListener,Serializable,Printable,KeyListener
{
	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();
	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");

	/**The tabbed pane object is used to set the tables as sheets.*/
	JTabbedPane jtp;

	/**The array of tables is used to set for default worksheets.*/
	public JTable[] table;

	/**The table object is used to store the current table among the
	set of 15 tables.*/
	//JTable temptable;

	/**The table object is used to store the column heading for the table.*/
	JTable headerColumn;

	/**The table object is used to store the column heading for temp purposes..*/
	JTable cur_tableHeader;
	JTable cur_table;

	/**The scrollpane array is used to store the table for default sheets. */
	JScrollPane[]  jsp;

	/**The text field is used to display the currently selected cell and
	to display the results after calculations.*/
	JTextField inputField;
	JTextField addrField;

	/**The popupmenu is used to display the menu when
	right clicking the mouse*/
	JPopupMenu jp_popup;

	/**The menu item is used for cut-item in popup menu*/
	JMenuItem jm_item;

	/**The interface object is used to set font in the table cell.*/
	CellFont[] cellAtt=new CellFont[15];

	/**The object of the class AttributiveCellTableModel which is
	extended from DefaultTableModel.*/
	AttributiveCellTableModel[] actml=new AttributiveCellTableModel[15];

	/**The worksheet array is used to store the no. of worksheets to be
	placed for spreadsheet.*/
	WorkSheet[] worksheet;

	/**The two dimensional color array is used to store the forecolor of each cell*/
	Color[][] foreclr=new Color[50][19];

	/**The two dimensional color array is used to store the back color of each cell*/
	Color[][] backclr=new Color[50][19];

	/**The string array is used to set the title for the worksheet*/
	public String[] sheet_title={  "õ¤ó¤ð¢¹1", "õ¤ó¤ð¢¹2", "õ¤ó¤ð¢¹3","õ¤ó¤ð¢¹4", "õ¤ó¤ð¢¹5","õ¤ó¤ð¢¹6",	"õ¤ó¤ð¢¹7",
							"õ¤ó¤ð¢¹8",	"õ¤ó¤ð¢¹9",		"õ¤ó¤ð¢¹10",	"õ¤ó¤ð¢¹11","õ¤ó¤ð¢¹12",	"õ¤ó¤ð¢¹13",	"õ¤ó¤ð¢¹14","õ¤ó¤ð¢¹15"	 };

	//int sheetInd,rowInd,rowHt;
	/**The font to be set to the application which is retrieved
	from the application package itself.*/
	public static Font bilingualFont;

	/**The string object is used to set the language to set the
	local language font.*/
	public static String language = "ta";

	/**The string object is used to set the country for the local settings.*/
	public static String country = "IN";

	/**The locale object is used to set the local language and the country.*/
	public static Locale currentLocale;

	/**The word bundle is used to list the tamil and english equivalent words
	for setting menu items,buttons and labels in Tamil.*/
	public ResourceBundle wordBundle;

	/**The temp font is used to retrive the font from the application package.*/
	Font tempFont;

	/**The linked list is used to store the recent files which are opened recently.*/
	LinkedList ll_recentfiles=new LinkedList();

	/**The integer value for no. of maximum recent files to be displayed in the
	file menu.*/
	final int no_ResentFiles = 5;

	/**The integer value to denote the currently selected pane(sheet) among
	the default sheets.*/
	int curPane=0;

	/**The integer value to set the no. of scrollpanes for each sheet.*/
	int sp=15;

	/**The integer value to store the no. of tabs in the workbook.*/
	int tab_count=4;

	/**The integer value to store the selected row from the current table.*/
	int Row;

	/**The integer value to store the selected column from the current table.*/
	int Col;

	/**The integer value to store the option pane value for exiting from the application.*/
	//int select;

	/**The boolean value to check the file is already saved or not.*/
	public boolean is_saved;

	/**The integer value for row index is used for cut-copy-paste functionality.*/
	static int rowIndex;

	/**The integer value for column index is used for cut-copy-paste functionality.*/
	static int colIndex;

	/**The array object is used to store the value to the clipboard for
	cut-copy-paste functions.*/
	static Object [][] clipboard;

	/**The string object is used to store the cell value for pasting.*/
	String cellVal;

	/**The component object is used for indicating selected component.*/
	Component selectedComponent;

	/**The combo box object is used to list the available fonts in the system.*/
	JComboBox fontName;

	/**The combo box object  is used to list the font size to be used for the
	text style format ranging 8 to 72*/
	JComboBox fontSize;

	/**The array list object is used to store the list of tables in the workbook.*/
	ArrayList al_tables=new ArrayList();
	ArrayList al_tableHeaders=new ArrayList();

	/**The panel is used  to place the icon buttons in the iconbar. */
	JPanel jp_icons=new JPanel(new GridLayout(1,5));
	JPanel jp_iconsFile=new JPanel();//new GridLayout(1,2));
	JPanel jp_iconsEdit=new JPanel();
	JPanel jp_iconsChart=new JPanel();
	JPanel jp_iconsStyle=new JPanel();

	/**The panel is used to place the inputfield,fontname combobox
	and font size combobox.*/
	JPanel jp_fonts=new JPanel(new FlowLayout(FlowLayout.LEFT));

	/**The button object to make it rollover when mouse over on that. */
	JButton jbNew;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbOpen;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbClose;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbSave;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbCut;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbCopy;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbPaste;

	JButton jbPrint;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbSum;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbChartP;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbChartH;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbChartV;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbForeClr;

	/**The button object to make it rollover when mouse over on that. */
	JButton jbBackClr;;

	/**The toggle button object to make it rollover when mouse over on that. */
	JToggleButton jbBold;

	/**The toggle button object to make it rollover when mouse over on that. */
	JToggleButton jbItalic;

	/**The toggle button object to make it rollover when mouse over on that. */
	//RolloverToggleButton jbUnderline;

	public String inputText;
	/**The double value is used to store the sum value after
	adding selected values.*/
	double sum=0;

	/**The string object is used to store the saved file name.*/
	String saved_file;

	/**The boolean value to check the file is first time saved or not.*/
	public boolean isFirstSaved;

	/**the boolean array is used to check the worksheet is present or not.*/
	boolean[] isSheet;

	/**The integer value to store the total no. of sheets in the workbook.*/
	int sheetCount;

	/**The integer value to give the sheet no. when inserting a new sheet.*/
	int sheetNo=1;

	/**The date object is used to store the file create date.*/
	public Date createDate;//for the properties menu

	/**The integer value to store the no. of rows to be set for the table(sheet).*/
	int total_Rows=50;  // no of rows

	/**The integer value to store the starting row position.*/
	int startRow;

	/**The integer value to store the selected row index position.*/
	int sel_row;

	/**The integer value to store the selected column index position.*/
	int sel_col;

	String cpy_value=new String();

	/**The string array object is used to store the column heading for the table.*/
	public String[] headers={"","è(0)","é(1)","ê(2)","ë(3)","ì(4)","í(5)",
								"î(6)","ï(7)","ð(8)","ñ(9)","ò(10)","ó(11)",
							"ô(12)","õ(13)","ö(14)","÷(15)","ø(16)","ù(17)"};

	public String headRefs=new String("èéêëìíîïðñòóôõö÷øù");

	public String[] headerNums={"è","é","ê","ë","ì","í",
							"î","ï","ð","ñ","ò","ó",
							"ô","õ","ö","÷","ø","ù"};
	int i;
   String inpText=new String("");
   /**Stores the cell address for all the cells in the table*/
   ArrayList al_cellRefs=new ArrayList();
   /**Stores the sheets for rowheight save*/
	JTable table_forSave,tableClrSave;
   Hashtable ht_sheet=new Hashtable();//to store sheet number and rowht hashtable--save & saveAs
   Hashtable htSheet=new Hashtable();

   Hashtable ht_colwid=new Hashtable();//to store sheet number and column width- saeve & saveAs
   Hashtable htColwid=new Hashtable();
   Hashtable clr_save=new Hashtable();
   Hashtable clrSave=new Hashtable();

   Font[][] saveFnt=new Font[50][19];

   Object[][] backColor=new Object[][]{};
   Object[][] foreColor=new Object[][]{};
   Object[][] saveFont=new Object[][]{};

	ObjectOutputStream out;

	Hashtable tempCl = new Hashtable();
	Hashtable ht_bkclrSave=new Hashtable();
	Hashtable ht_fntSave=new Hashtable();
	Hashtable ht_foreclrSave=new Hashtable();

	Hashtable htBkclrSave=new Hashtable();
	Hashtable htFntSave=new Hashtable();
	Hashtable htForeclrSave=new Hashtable();
	boolean zoom= false;
    protected UndoManager m_undoManager = new UndoManager();
    int RowHgt;
    String[] zmValue;
    String[] zoom_value;
	int rowCnt,colCnt;
	int mouseClkCnt;
	String formula_range=new String();
	String operator=new String();
	/**copy cell attributes--------CP- cell properties> */
	int rowselCP,colselCP;
	String dataCP=new String();
	String fntCP=new String();
	Font fntCA;//=new Font("TABKural",1,14);
	int fsizeCP;
	int fstyleCP;
	Color bkcolorCP=new Color(255,0,0);
	Color forecolorCP=new Color(0,0,255);
	int clrbkCP,clrforeCP;
	static int sheetFile=1;
	static int mainSheet=1;
	/**copy cell attributes<--------*/

	/*private Clipboard system;
	private StringSelection stsel;
	private String rowstring,value;*/

	/**The main constructor will invoke the application. It displays default of four worksheets with four tables as a worksheet. WorkSheet and
	SSMenu are the classes used for the main class.WorkSheet class is 	used for constructing the table and datas. SSMenu will set the menu
	bar and menu items along with the corresponding actions for the 	menu items. The main class will set the icon bars along with icons
	with rollover effect in the applicaion window.Font to be set for the components is bound with the application package.The main
	class will also display the popup-menu,inputField,fontname list and 	font size list.The ExcelAdapter class is also enabled along with
	this class to provide cut-copy-paste functionality between 	the application and MS-Excel.*/

	public Chathurangam()		//MAIN CONSTRUCTOR FOR SPREADSHEET
		{
				try
				{
					InputStream fontInputStream =
						getClass().getResourceAsStream("TABKural.TTF");
						tempFont = Font.createFont(Font.TRUETYPE_FONT,
							fontInputStream);
					bilingualFont = tempFont.deriveFont(Font.PLAIN, 12);
				}
				catch(Exception e2)
				{
					System.out.println("line 136"+e2);
					e2.printStackTrace();
				}
			setUIManager();

			currentLocale = new Locale(language,country);
			setLocale(language, country);

			/*my addition */
			is_saved=true;
			addKeyListener(this);
			saved_file=new String("Untitled ");
			setTitle("Tamil Virithaal- "+saved_file+sheetFile);
			isFirstSaved=false;
			isSheet=new boolean[15];
			for(i=1;i<5;i++)
				isSheet[i]=true;
			for(;i<15;i++)
				isSheet[i]=false;

			/*my addition ends */

			setGUI();
			table=new JTable[15];
			worksheet=new WorkSheet[15];

			insertSheet(true); //insertsheet from this constructor is called with true param

			jsp= new JScrollPane[sp];

			ExcelAdapter ea=new ExcelAdapter((JTable)al_tables.get(curPane));

			/*cur_table=(JTable)al_tables.get(curPane);

			KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
			      // Identifying the copy KeyStroke user can modify this
			      // to copy on some other Key combination.
			KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK,false);
			      // Identifying the Paste KeyStroke user can modify this
			      //to copy on some other Key combination.
			cur_table.registerKeyboardAction(this,"Copy",copy,JComponent.WHEN_FOCUSED);

			cur_table.registerKeyboardAction(this,"Paste",paste,JComponent.WHEN_FOCUSED);
      		system = Toolkit.getDefaultToolkit().getSystemClipboard();*/

			repaint();


	} // END OF MAIN CONSTRUCTOR HERE..
	/**The main constructor will invoke the application. It displays saved
	worksheets. WorkSheet and SSMenu are the classes used
	for the main class.WorkSheet class is used for constructing
	the table and datas. SSMenu will set the menubar and menu items along
	with the corresponding actions for the menu items.
	The main class will set the icon bars along with icons
	with rollover effect in the applicaion window.Font to be set for the
	components is bound with the application package.The main
	class will also display the popup-menu,inputField,fontname list and
	font size list.The ExcelAdapter class is also enabled along with
	this class to provide cut-copy-paste functionality between
	the application and MS-Excel. This construcor is invoked when the
	saved file is opened.The saved object is retrived and displayed
	along with the data.
	@param sheet of type worksheet array to be opened.
	@param saved-fname of type string to store the saved filename.
	@param sheets[] array of sheets of type boolean to check the sheet is
	available or not?
	@param sheet_titles of type string array to store the sheet titles.
	*/
	public Chathurangam(WorkSheet[] sheet,String saved_fname,boolean []sheets,String[] sheet_titles, Hashtable htSheet,Hashtable htColwid,Hashtable htBkclrSave,Hashtable htForeclrSave,Hashtable htFntSave,String[] zmValue)
	{
		try{
				InputStream fontInputStream =	getClass().getResourceAsStream("TABKural.TTF");
				tempFont = Font.createFont(Font.TRUETYPE_FONT,fontInputStream);
				bilingualFont = tempFont.deriveFont(Font.PLAIN, 12);
			}
			catch(Exception e2)
			{
				System.out.println("line 136"+e2);
				e2.printStackTrace();
			}

		setUIManager();
		currentLocale = new Locale(language,country);
		setLocale(language, country);


		//my addition
		int i;
		is_saved=true;
		addKeyListener(this);
		saved_file=new String(saved_fname);
		setTitle("Tamil Virithaal- "+saved_file);
		isFirstSaved=true;
  		isSheet=sheets;
  		worksheet=sheet;
  		sheet_title=sheet_titles;
		htSheet=htSheet;
		htColwid=htColwid;

		/*backColor=backColor;
		foreColor=foreColor;
		saveFont=saveFont;*/

		htBkclrSave=htBkclrSave;
		htForeclrSave=htForeclrSave;
		htFntSave=htFntSave;
		zmValue=zmValue;

		setGUI();

		table=new JTable[15];
		//worksheet=new WorkSheet[15];

		insertSheet(false);//insert sheet from this constructor is with fasle param

		jsp= new JScrollPane[sp];

		//**ROW HEIGHT RETIRVEL----***/
		Integer sh=new Integer(0);
		Integer row=new Integer(0);
		Integer ht=new Integer(0);
		JTable openTableRow;
		JTable	openTableHeaderRow;

		Enumeration enum=htSheet.keys();
		for(; enum.hasMoreElements();)
		{
			sh=(Integer)enum.nextElement();
			//Integer value=(Integer)htSheet.get(key);
			openTableRow=(JTable)al_tables.get(sh.intValue());
			openTableHeaderRow=(JTable)al_tableHeaders.get(sh.intValue());
			Hashtable temphash=(Hashtable)htSheet.get(sh);
			Enumeration e1=temphash.keys();
			for(;e1.hasMoreElements();)
			{
				row=(Integer)e1.nextElement();
				ht=(Integer)temphash.get(row);
				//System.out.println("row  & ht *** "+row+" ,"+ht);
				openTableRow.setRowHeight(row.intValue(),ht.intValue());
				openTableHeaderRow.setRowHeight(row.intValue(),ht.intValue());
			}
			//System.out.println("row & heit main class "+row+" ,"+ht);
		}
		//**COLUMN WIDTH RETRIVEL---**/
		Integer shC=new Integer(0);
		Integer col=new Integer(0);
		Integer wid=new Integer(0);
		JTable openTableCol;

		Enumeration enu=htColwid.keys();
		for(; enu.hasMoreElements();)
		{
			shC=(Integer)enu.nextElement();
			//Integer value=(Integer)htSheet.get(key);
			//System.out.println("size sheets "+htColwid.size());
			openTableCol=(JTable)al_tables.get(shC.intValue());
			Hashtable temphash=(Hashtable)htColwid.get(shC);
			//System.out.println("size cols  "+temphash.size());
			Enumeration e2=temphash.keys();
			for(;e2.hasMoreElements();)
			{
				col=(Integer)e2.nextElement();
				wid=(Integer)temphash.get(col);
				//System.out.println("col & wid *** "+col+" ,"+wid);
				TableColumnModel tcmOpen = openTableCol.getColumnModel();
				TableColumn colOp = tcmOpen.getColumn(col.intValue());
				colOp.setPreferredWidth(wid.intValue());
			}

		}
		//---------BACK COLOR RETRIEVAL----------------
		Integer si=new Integer(0);
		Color bcolor=new Color(0,0,0);
		Color fcolor=new Color(0,0,0);
		Font sfont;
		Font fnt=null;
		String fname=new String();
		int fstyle=0;
		int fsize=0;
		JTable clrTable;
		Enumeration enuBk=htBkclrSave.keys();
		for(;enuBk.hasMoreElements();)
		{
			si=(Integer)enuBk.nextElement();
			clrTable=(JTable)al_tables.get(si.intValue());
			backColor=(Color[][])htBkclrSave.get(si);
			for(int m=0;m< clrTable.getRowCount();m++)
						{
							for(int n=0;n< clrTable.getColumnCount();n++)
							{
								//if(backclr[m][n]!=bcolor)
								//{
									bcolor=(Color)backColor[m][n];
									//System.out.println("BackClr OPEN meth "+backColor[m][n]+","+m+","+n);
								/*	if(bcolor!=null)
										System.out.println("BackClr OPEN meth "+bcolor+","+m+","+n);*/

									cellAtt[si.intValue()+1].setBackground(bcolor,m,n);
								//}

							}
				}
		}
		//System.out.println("VVVVVClr OPEN meth "+backColor[0][0]);
		Enumeration enufore=htForeclrSave.keys();
		for(;enufore.hasMoreElements();)
		{
			si=(Integer)enufore.nextElement();
			clrTable=(JTable)al_tables.get(si.intValue());
			foreColor=(Color[][])htForeclrSave.get(si);
			for(int m=0;m< clrTable.getRowCount();m++)
						{
							for(int n=0;n< clrTable.getColumnCount();n++)
							{
								//if(foreclr[m][n]!=null)
								//{
									fcolor=(Color)foreColor[m][n];
									//System.out.println("foreClr OPEN meth "+foreColor[m][n]+","+m+","+n);
									cellAtt[si.intValue()+1].setForeground(fcolor,m,n);
								//}
						}
				}
		}

				Enumeration enufnt=htFntSave.keys();
				for(;enufnt.hasMoreElements();)
				{
					si=(Integer)enufnt.nextElement();
					clrTable=(JTable)al_tables.get(si.intValue());
					saveFont=(Font[][])htFntSave.get(si);
					for(int m=0;m< clrTable.getRowCount();m++)
								{
									for(int n=0;n< clrTable.getColumnCount();n++)
									{
										if(saveFont[m][n]!=null)
										{

											fnt=(Font)saveFont[m][n];
											fname=fnt.getFamily();
											fstyle=fnt.getStyle();
											fsize=fnt.getSize();
											sfont=new Font(fname,fstyle,fsize);
											cellAtt[si.intValue()+1].setFont(sfont,m,n);
											clrTable.repaint();
										}
									}
						}
		}

		/*System.out.println("zm value leg "+zmValue.length);
		System.out.println("zm value 1- "+zmValue[0]);
		System.out.println("zm value 2- "+zmValue[1]);
		System.out.println("zm value 3-"+zmValue[2]);
		System.out.println("zm value 4- "+zmValue[3]);*/


		ExcelAdapter ea=new ExcelAdapter((JTable)al_tables.get(curPane));
		repaint();


	} //END OF SECOND CONSTRUCTOR HERE..
	public void setGUI()//setting components for the 2 constructors
	{
		setSize(750,550);
		setLocation(110,90);
		setIconImage(img);

		ll_recentfiles.clear();
		try
		{
			if(new File("rfile").exists())
				filestollist(); // for recent files
			else
				new File("rfile").createNewFile();
		}
		catch(Exception e)
		{
			System.out.println("file creation-"+e);
		}
		/**new addition of codes for formula*/

		for(int m=0;m<headRefs.length();m++)
			al_cellRefs.add(m,headerNums[m]);

		/**new addition of codes for formula ...........ends here*/

		jp_popup=new JPopupMenu("Edit");//popup menu for work sheet
		Container contents=getContentPane();
		GridBagLayout gridbag=new GridBagLayout();
		contents.setLayout(gridbag);
		GridBagConstraints constraint=new GridBagConstraints();

		jbNew=new JButton();//ImagesLocator.getImage("new.gif"));
		jbNew.setToolTipText(wordBundle.getString("new")+"/New");
		jbNew.addActionListener(this);

		jbOpen=new JButton(ImagesLocator.getImage("open.gif"));
		jbOpen.setToolTipText(wordBundle.getString("open")+"/Open");
		jbOpen.addActionListener(this);

		jbSave=new JButton(ImagesLocator.getImage("save.gif"));
		jbSave.setToolTipText(wordBundle.getString("save")+"/Save");
		jbSave.addActionListener(this);

		jbCut=new JButton(ImagesLocator.getImage("cut.gif"));
		jbCut.setToolTipText(wordBundle.getString("cut")+"/Cut");
		jbCut.addActionListener(this);

		jbCopy=new JButton(ImagesLocator.getImage("copy.gif"));
		jbCopy.setToolTipText(wordBundle.getString("copy")+"/Copy");
		jbCopy.addActionListener(this);

		jbPaste=new JButton(ImagesLocator.getImage("paste.gif"));
		jbPaste.setToolTipText(wordBundle.getString("paste")+"/Paste");
		jbPaste.addActionListener(this);

		jbPrint=new JButton(ImagesLocator.getImage("print.gif"));
		jbPrint.setToolTipText(wordBundle.getString("print")+"/Print");
		jbPrint.addActionListener(this);

		jbSum=new JButton(ImagesLocator.getImage("sum.GIF"));
		jbSum.setToolTipText(wordBundle.getString("sum")+"/Sum");
		jbSum.addActionListener(this);

		jbChartV=new JButton(ImagesLocator.getImage("chart.gif"));
		jbChartV.setToolTipText(wordBundle.getString("chartV")+"/BarChartVertical");
		jbChartV.addActionListener(this);

		jbChartH=new JButton(ImagesLocator.getImage("hcIcon.gif"));
		jbChartH.setToolTipText(wordBundle.getString("chartH")+"/BarChartHorizontal");
		jbChartH.addActionListener(this);

		jbChartP=new JButton(ImagesLocator.getImage("pieIcon.gif"));
		jbChartP.setToolTipText(wordBundle.getString("chartP")+"/PieChart");
		jbChartP.addActionListener(this);

		jbBackClr=new JButton(ImagesLocator.getImage("backclr.gif"));
		jbBackClr.setToolTipText(wordBundle.getString("background")+"/Background");
		jbBackClr.addActionListener(this);

		jbForeClr=new JButton(ImagesLocator.getImage("foreclr.gif"));
		jbForeClr.setToolTipText(wordBundle.getString("textColor")+"/TextColor");
		jbForeClr.addActionListener(this);

		fontName=new JComboBox();
		fontName.setBackground(Color.white);
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for ( i = 0; i < fonts.length; i++)
			fontName.addItem(fonts[i]);
		fontName.addActionListener(this);
		fontName.setFont(new Font("TABKural",0,12));
		fontName.setSelectedItem("TABKural");
		fontName.setToolTipText(wordBundle.getString("fontName")+"/FontName");

		ActionListener font_name_action = new ActionListener()
		   {
		   /**The action performed method is used for action event
		   when the font name combobox gets the action listener.*/
			public void actionPerformed(ActionEvent ev)
			   {
			  JTable cur_table=(JTable)al_tables.get(jtp.getSelectedIndex());
			  int[] columns = cur_table.getSelectedColumns();
			  int[] rows    = cur_table.getSelectedRows();
			  if(columns.length==0 && rows.length==0)
			  {

			  }
			  if ((rows == null) || (columns == null)) return;
			  if ((rows.length<1)||(columns.length<1)) return;
				int fntstyle=Font.PLAIN;
				if(jbBold.isSelected())
				fntstyle+=Font.BOLD;
				if(jbItalic.isSelected())
				fntstyle+=Font.ITALIC;
			    Font font = new Font((String)fontName.getSelectedItem(), fntstyle,
							  Integer.parseInt((String)fontSize.getSelectedItem()));

			  cellAtt[jtp.getSelectedIndex()+1].setFont(font, rows, columns);
			 // temptable.clearSelection();
			  cur_table.revalidate();
			  cur_table.repaint();
				}
			};
		fontName.addActionListener(font_name_action);

		fontSize=new JComboBox();
		fontSize.setBackground(Color.white);
		int[] fontSizes={8,9,10,11,12,14,16,18,20,22,24,26,28,36,48,72};
		for ( i = 0; i < fontSizes.length; i++)
			fontSize.addItem(new Integer(fontSizes[i]).toString());
		fontSize.setFont(new Font("TABKural",0,12));
		fontSize.setSelectedIndex(4);
		fontSize.setToolTipText(wordBundle.getString("fontSize")+"/FontSize");
		ActionListener font_size_action = new ActionListener()
		   {
		   /**The action performed method is used for action event
		   when the font size combobox gets the action listener.*/
			public void actionPerformed(ActionEvent ev)
			   {
			  int[] columns = cur_table.getSelectedColumns();
			  int[] rows    = cur_table.getSelectedRows();
			  if(columns.length==0 && rows.length==0)
			   {

				}
			  if ((rows == null) || (columns == null)) return;
			  if ((rows.length<1)||(columns.length<1)) return;
				int fntstyle=Font.PLAIN;
				if(jbBold.isSelected())
				fntstyle+=Font.BOLD;
				if(jbItalic.isSelected())
				fntstyle+=Font.ITALIC;
			  	Font font = new Font((String)fontName.getSelectedItem(),	  fntstyle,
							  Integer.parseInt((String)fontSize.getSelectedItem()));

			  cellAtt[jtp.getSelectedIndex()+1].setFont(font, rows, columns);
			  String cellValue=cur_table.getValueAt(cur_table.getSelectedRow(),cur_table.getSelectedColumn()).toString().trim();
			  int valLen=cellValue.length();
			  int fntSize=Integer.parseInt((String)fontSize.getSelectedItem());
			  /*if(fntSize>20)
			  {
				  cur_table.setRowHeight(cur_table.getSelectedRow(),65);
				  cur_tableHeader.setRowHeight(cur_table.getSelectedRow(),65);

				  TableColumnModel tcm = cur_table.getColumnModel();
				  TableColumn col = tcm.getColumn(cur_table.getSelectedColumn());
				  if(fntSize>20 && fntSize <30)
				  col.setPreferredWidth(valLen*20);
				  if(fntSize>30 && fntSize <50)
				  col.setPreferredWidth(valLen*30);
				  if(fntSize>50 && fntSize <73)
				  col.setPreferredWidth(valLen*45);
			  }*/
			 // temptable.clearSelection();
			  cur_table.revalidate();
			  cur_table.repaint();
				}
			};
		fontSize.addActionListener(font_size_action);

		jbBold=new JToggleButton(ImagesLocator.getImage("bold.gif"));
		jbBold.setToolTipText(wordBundle.getString("bold")+"/Bold");
		jbBold.addActionListener(this);

		jbItalic=new JToggleButton(ImagesLocator.getImage("italic.gif"));
		jbItalic.setToolTipText(wordBundle.getString("italic")+"/Italic");
		jbItalic.addActionListener(this);

		jtp=new JTabbedPane(JTabbedPane.BOTTOM);
		jtp.addMouseListener(mListener);

		ImageIcon imgCut=new ImageIcon("cut.gif");
		ImageIcon imgCopy=new ImageIcon("copy.gif");
		ImageIcon imgPaste=new ImageIcon("paste.gif");

		jm_item = new JMenuItem("ªõì¢´/Cut");//cut option in popup menu
		jm_item.addActionListener(this);
		jm_item.setMnemonic(KeyEvent.VK_X);
		jm_item.setFont(bilingualFont);
		jm_item.setAccelerator(javax.swing.KeyStroke.getKeyStroke
								(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK));

		jm_item.setIcon(ImagesLocator.getImage("cut.gif"));
		jp_popup.add(jm_item);

		jm_item = new JMenuItem("ïèô¢/Copy");//copy option in popup menu
		jm_item.setFont(bilingualFont);
		jm_item.setMnemonic(KeyEvent.VK_C);
		jm_item.setIcon(ImagesLocator.getImage("copy.gif"));
		jm_item.addActionListener(this);
		jm_item.setAccelerator(javax.swing.KeyStroke.getKeyStroke
							(java.awt.event.KeyEvent.VK_C, java.awt.Event.CTRL_MASK));

		jp_popup.add(jm_item);

		jm_item = new JMenuItem("åì¢´/Paste");//paste option in popup menu
		jm_item.setFont(bilingualFont);
		jm_item.setMnemonic(KeyEvent.VK_P);
		jm_item.setIcon(ImagesLocator.getImage("paste.gif"));
		jm_item.addActionListener(this);
		jm_item.setAccelerator(javax.swing.KeyStroke.getKeyStroke
						(java.awt.event.KeyEvent.VK_V, java.awt.Event.CTRL_MASK));

		jp_popup.add(jm_item);

		jp_popup.addSeparator();

		jm_item=new JMenuItem("ïèô¢-õ¤î¤º¬ø/CopyFormula");
		jm_item.setFont(bilingualFont);
		jm_item.addActionListener(this);
		jp_popup.add(jm_item);

		jm_item=new JMenuItem("åì¢´-õ¤î¤º¬ø/PasteFormula");
		jm_item.setFont(bilingualFont);
		jm_item.addActionListener(this);
		jp_popup.add(jm_item);

		jp_popup.addSeparator();

		jm_item=new JMenuItem("åù¢ø¤¬í/Merge Cells");
		jm_item.setFont(bilingualFont);
		jm_item.addActionListener(this);
		//jp_popup.add(jm_item);

		jm_item=new JMenuItem("ð¤ó¤/Split Cells");
		jm_item.setFont(bilingualFont);
		jm_item.addActionListener(this);
		//jp_popup.add(jm_item);

		//jp_popup.addSeparator();

		jm_item=new JMenuItem("àì¢îó¬õ ï¦è¢°è/ClearContent");
		jm_item.setFont(bilingualFont);
		jm_item.addActionListener(this);
		jp_popup.add(jm_item);

		jm_item=new JMenuItem("Ü¬ùî¢¬î»ñ¢ «îó¢ï¢ªî´/SelectAll");
		jm_item.setFont(bilingualFont);
		jm_item.addActionListener(this);
		jp_popup.add(jm_item);

		jm_item=new JMenuItem("«îó¢ï¢ªî´î¢îõø¢¬ø ï¦è¢°/DeSelectAll");
		jm_item.setFont(bilingualFont);
		jm_item.addActionListener(this);
		jp_popup.add(jm_item);

		jp_popup.addSeparator();

		jm_item = new JMenuItem("ªê¼°-ï¤óô¢/Insert-Row");//insert-Row in popup menu
		jm_item.setFont(bilingualFont);
		jm_item.addActionListener(this);
		jp_popup.add(jm_item);

		jm_item = new JMenuItem("ªê¼°-ï¤¬ó/Insert-Column");//insert-Column in popup menu
		jm_item.setFont(bilingualFont);
		jm_item.addActionListener(this);
		jp_popup.add(jm_item);

		jp_popup.addSeparator();

		jm_item = new JMenuItem("ï¦è¢° - ï¤óô¢/Delete-Row");//delete-Row in popup menu
		jm_item.setFont(bilingualFont);
		jm_item.addActionListener(this);
		jp_popup.add(jm_item);


		jm_item = new JMenuItem("ï¦è¢° - ï¤¬ó/Delete-Column");//delete-Column in popup menu
		jm_item.setFont(bilingualFont);
		jm_item.addActionListener(this);
		jp_popup.add(jm_item);

		jp_popup.repaint();

		enableEvents(AWTEvent.MOUSE_EVENT_MASK);

		inputField=new JTextField("");
		inputField.setFont(bilingualFont);
		inputField.addActionListener(this);

		addrField=new JTextField("");
		addrField.setFont(bilingualFont);

		/*NEW ADDITION FOR TOOLBAR  start */
		JToolBar FileToolbar = new JToolBar();
		JToolBar EditToolbar = new JToolBar();
		JToolBar ChartToolbar = new JToolBar();

		jp_iconsFile.add( FileToolbar, BorderLayout.NORTH );

		jp_icons.add(jp_iconsFile);
		jp_iconsStyle.add(jbBold);
		jp_iconsStyle.add(jbItalic);
		jp_icons.add(jp_iconsStyle);

		jbNew = addToolbarButton( FileToolbar, true, null,
								"new", wordBundle.getString("new") );
		jbOpen = addToolbarButton( FileToolbar, true, null,
								"open", wordBundle.getString("open") );
		jbSave = addToolbarButton( FileToolbar, true, null,
								"save", wordBundle.getString("save") );

		FileToolbar.addSeparator();

		jbPrint = addToolbarButton( FileToolbar, true, null,
								"print", wordBundle.getString("print") );

		FileToolbar.addSeparator();

		jbCopy = addToolbarButton( FileToolbar, true, null,
								"copy", wordBundle.getString("copy") );
		jbCut = addToolbarButton( FileToolbar, true, null,
								"cut", wordBundle.getString("cut") );
		jbPaste = addToolbarButton( FileToolbar, true, null,
								"paste", wordBundle.getString("paste") );

		FileToolbar.addSeparator();


		jbSum = addToolbarButton( FileToolbar, true, null,
								"sum", wordBundle.getString("sum") );

		FileToolbar.addSeparator();

		jbChartH = addToolbarButton( FileToolbar, true, null,
								"hcIcon", wordBundle.getString("chartH") );
		jbChartV = addToolbarButton( FileToolbar, true, null,
								"chart", wordBundle.getString("chartV"));
		jbChartP = addToolbarButton( FileToolbar, true, null,
								"pieIcon", wordBundle.getString("chartP") );

		FileToolbar.addSeparator();

		jbBackClr = addToolbarButton( FileToolbar, true, null,
								"backclr", wordBundle.getString("background") );

		jbForeClr = addToolbarButton( FileToolbar, true, null,
								"foreclr", wordBundle.getString("textColor") );

		/*NEW ADDITION FOR TOOLBAR  end*/
		constraint.fill=GridBagConstraints.BOTH;
		constraint.weightx=0.01;
		constraint.gridwidth=GridBagConstraints.REMAINDER;
		gridbag.setConstraints(jp_icons,constraint);
		contents.add(jp_icons);

		inputField.setPreferredSize(new Dimension(250,25));
		addrField.setPreferredSize(new Dimension(50,25));

		jp_fonts.add(addrField);
		jp_fonts.add(inputField);

		fontName.setPreferredSize(new Dimension(100,25));
		jp_fonts.add(fontName);
		fontSize.setPreferredSize(new Dimension(45,25));
		jp_fonts.add(fontSize);

		gridbag.setConstraints(jp_fonts,constraint);
		contents.add(jp_fonts);

		constraint.fill=GridBagConstraints.BOTH;
		constraint.insets=new Insets(0,5,0,0);
		constraint.weightx=1;constraint.weighty=1;
		constraint.gridwidth=GridBagConstraints.REMAINDER;
		gridbag.setConstraints(jtp,constraint);
		contents.add(jtp);

		addWindowListener(new WindowAdapter(){

			/**The anonymous inner class to provide
			alert message when the application window is
			closed with out saving it.*/
			public void windowClosing(WindowEvent we)
			{
				try
				{
					/*my addition */
					if(is_saved)
					{
						addinResentFile();
						//System.exit(0);
						mainSheet--;
						if(mainSheet==0)
						System.exit(0);
						else
						setVisible(false);
					}
					if (! confirmClose())
					return;

					/*my addition ends*/
				}
				catch(Exception ec)
				{
					System.out.println(ec+"Chathurangam.java");
				}
			}
		  });

	/**The class to provide cut-copy-paste functionality between
	spreadsheet and the Ms-Excel.*/
	is_saved=true;
	initPane();

	}

	/**SAVEAS FILE METHO******/
		/**The method is used to save the file in different file format
		or another name as copy with same file type. It first checks
		whether sheet is present.Using object out put stream
		it writes the crete date,sheet present,sheet title
		along with the data in the table.The saved file name is added with
		the recent files list to display the file in the file menu.*/
		public boolean saveAsFile()
		{
				if(!sheetPresent())
			    {
				//pop up menu for No sheet to save
				wordBundle = this.getWordBundle();
				Object[] options = {wordBundle.getString("yes")};
				String NoSheetFound = wordBundle.getString("NoSheetFound");
				String messageTitle = wordBundle.getString("messageTitle");
				int select = showDialog(this,"NoSheetFound",JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

				return true;
			}
			String file =this.getFile();//the name of a
			String loc=new String();
			File fi=new File(file);

			/**The file chooser object is used to display the files in
			the system directory.*/
			JFileChooser chooser=new JFileChooser(".");
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setCurrentDirectory(fi);
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			setLanguage(chooser);
			chooser.addChoosableFileFilter(new ExampleFileFilter("tss","ê¶óé¢èñ¢ «è£ð¢¹è÷¢"));
			/*my addition*/
			int sel_row,sel_col,i;

			int option=chooser.showSaveDialog(this);
			try
			{
				 if(option==JFileChooser.APPROVE_OPTION)
				 {
					String prev_file=new String(saved_file);
					saved_file=new String(chooser.getSelectedFile().getAbsolutePath());

					if(!(saved_file.endsWith(".tss")))
						saved_file=new String(chooser.getSelectedFile().getAbsolutePath()+".tss");

					if(new File(saved_file).exists() && new File(saved_file).isDirectory())
					{
						saved_file=new String(prev_file);
						wordBundle = this.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String notSavedWar = wordBundle.getString("fileOverwriteCon");
						String messageTitle = wordBundle.getString("messageTitle");
						return false;
					}

					if(new File(saved_file).exists() && new File(saved_file).isFile())
					{

						wordBundle = this.getWordBundle();
						Object[] options = {wordBundle.getString("ok"),wordBundle.getString("cancel")};
						String notSavedWar = wordBundle.getString("fileOverwriteCon");
						String messageTitle = wordBundle.getString("messageTitle");
						int select = showDialog(chooser,"fileOverwriteCon",
												JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
												options, 1);

						if(select==JOptionPane.YES_OPTION)
						{
							if(!isFirstSaved)
								saveFile();
							else
								saveFile();
						}
						if(select==JOptionPane.NO_OPTION)
						{
							saved_file=new String(prev_file);
							return false;
						}
					}
					setTitle("Tamil Virithaal- "+saved_file);
					isFirstSaved=true;
					is_saved=true;
					SSMenu ssm=(SSMenu)getJMenuBar();
					ssm.setProps(true);
					setJMenuBar(ssm);
					repaint();
					try{
					out=new ObjectOutputStream(new FileOutputStream(saved_file));
					//for properties menu
					out.writeObject(new Date());//data 1
					out.writeObject(isSheet);//data 2
					out.writeObject(sheet_title);//data 3
					/**ROW HEIGHT SAVING CODES----start*/

					int tabcnt=jtp.getTabCount();
					String[] zoom_value=new String[tabcnt];
					for(int s=0;s<tabcnt;s++)//for number of sheets
					{
						table_forSave=(JTable)al_tables.get(s);
						if(zoom)
						{
								int rowHght=table_forSave.getRowHeight();
								zoom_value[s]=Integer.toString(rowHght);
						}
						Hashtable tempRh = new Hashtable();
						for(int r=0;r<table_forSave.getRowCount();r++)//for number of rows
						{
							if(table_forSave.getRowHeight(r)!=20)
							 {
								try
									{
										tempRh.put(new Integer(r),new Integer(table_forSave.getRowHeight(r)));
									}
								catch(Exception ee)			{ 	ee.printStackTrace(); }
							  }// if ending

							}//inner for ending
							//ht_sheet.put(new Integer(s),ht_rowht[s]);
							ht_sheet.put(new Integer(s), tempRh);

					}//outer for
						out.writeObject(ht_sheet);//for saving row height

						/**ROW HEIGHT SAVING CODES----end*/


						/**COLUMN WIDTH SAVE PROCESS---------START*/
						for(int c=0;c<cur_table.getColumnCount();c++)
						{
								TableColumnModel tcm = cur_table.getColumnModel();
								TableColumn col = tcm.getColumn(c);
								int cc=col.getPreferredWidth();
								if(cc!=75)
								{
									tempCl.put(new Integer(c),new Integer(cc));
									ht_colwid.put(new Integer(c), tempCl);
								}

						}
						out.writeObject(ht_colwid);//for saving column width
						/**COLUMN WIDTH SAVE PROCESS---------ends*/
						/**COLOR SAVING METHOD FOLLOWS----Starts*/
						Color saveBkClr=new Color(255,0,0);
						Color saveFrClr=new Color(0,0,255);
						Font deFont=new Font("TABKural",0,12);
						String fntNa=deFont.getFamily();
						String ceFnt;
						for(int s=0;s<tabcnt;s++)//for number of sheets  // for 1
						{
							tableClrSave=(JTable)al_tables.get(s);
							Color bkclr[][]= new Color[tableClrSave.getRowCount()][tableClrSave.getColumnCount()];
							Color frclr[][]= new Color[tableClrSave.getRowCount()][tableClrSave.getColumnCount()];
							Font[][] savFnt=new Font[tableClrSave.getRowCount()][tableClrSave.getColumnCount()];
							for(int m=0;m<tableClrSave.getRowCount();m++)//for 2
							{
								for(int n=0;n<tableClrSave.getColumnCount();n++)//for 3
								{
									saveBkClr=cellAtt[curPane].getBackground(m,n);
									saveFrClr=cellAtt[curPane].getForeground(m,n);
									backclr[m][n]=null;
									foreclr[m][n]=null;
									ceFnt=new String(cellAtt[curPane].getFont(m,n).getFamily());

									if(saveBkClr!=null)
									{
										//backclr[m][n]=saveBkClr;
										bkclr[m][n]=saveBkClr;

									}
									if(saveFrClr!=null)
									{
										//foreclr[m][n]=saveFrClr;
										frclr[m][n]=saveBkClr;
									}
									if(!ceFnt.equals(fntNa))
									{
										//saveFnt[m][n]=cellAtt[curPane].getFont(m,n);
										savFnt[m][n]=cellAtt[curPane].getFont(m,n);
									}
								}//end for 3
							}//end for 2
							ht_bkclrSave.put(new Integer(s),bkclr);
							ht_foreclrSave.put(new Integer(s),frclr);
							ht_fntSave.put(new Integer(s),savFnt);
							/*ht_bkclrSave.put(new Integer(jtp.getSelectedIndex()),backclr);
							ht_foreclrSave.put(new Integer(jtp.getSelectedIndex()),foreclr);
							ht_fntSave.put(new Integer(jtp.getSelectedIndex()),saveFnt);*/
							}//end for 1
							out.writeObject(ht_bkclrSave);
							out.writeObject(ht_foreclrSave);
							out.writeObject(ht_fntSave);
						}	//end of try
						catch(NullPointerException npe)
						{
							System.out.println("npe  " + npe);
						}
						catch(Exception ee)
						{
							ee.printStackTrace();
						}
			/**----------------------------------Font saving method....ends*/
						out.writeObject(zoom_value);//for saving zoom value

			for(int k=1;k<15;k++)
					{
						/*my addition*/
						if(!isSheet[k])
							continue;
						JTextField jtf=(JTextField)table[k].getEditorComponent();
						if(jtf!=null)
						{
							sel_row=table[k].getEditingRow();
							sel_col=table[k].getEditingColumn()+1;
							worksheet[k].data[sel_row][sel_col]=jtf.getText();
							worksheet[k].etm.setValueAt(jtf.getText(),sel_row,sel_col);
						}
						/*my addition ends*/
						Dimension max=worksheet[k].etm.getMax();
						out.writeObject(max);

						for(i=0;i<max.width+1;i++)
						{
							for(int j=1;j<max.height+1;j++)
							{
								if(!(worksheet[k].data[i][j].toString().equals("")))
								{
									out.writeObject(worksheet[k].data[i][j]);
								}
								else
									out.writeObject(null);
							}
						}
					}

					/* recent file changing*/

					for(i=0;i<ll_recentfiles.size();i++)
					{
						if(ll_recentfiles.get(i).toString().equals(saved_file))
						{
							rearrFiles(i);
							break;
						}
					}

					if(i==ll_recentfiles.size())
					{
						ll_recentfiles.addFirst(saved_file);
						if(ll_recentfiles.size()>5)
							ll_recentfiles.removeLast();
					}
	                setJMenuBar(new SSMenu(this));
					show();
					pack();
					repaint();

					/* end recent file changing*/
					out.close();
					return true;
				  }
				  else if(option==JFileChooser.CANCEL_OPTION)
				  	return false;

			}
			catch(Exception e)
			{
				//System.out.println(e+"1581");
				e.printStackTrace();
			}
			return true;

	}//end of saveAsFile

	/**SAVE FILE METHOD*/

	/**The method is used to save the file as .tss(Tamil SpreadSheet)
	file type. It first checks the sheet is present. Using object
	out put stream it writes the crete date,sheet present,sheet title
	along with the data in the table.It write the dimension of the sheet
	to avoid writing unnecesssary cells(cells without data), then it
	adds the file name in the recent files list to display the file name
	in the file menu.*/
	public void saveFile()
	{
		String file =this.getFile();//the name of a

		String loc=new String();
		File fi=new File(file);
		int sel_row,sel_col,i;
		//Hashtable tempCl = new Hashtable();

		if(!sheetPresent())
		{

			wordBundle = this.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};

			String NoSheetFound = wordBundle.getString("NoSheetFound");
			String messageTitle = wordBundle.getString("messageTitle");
			int select = showDialog(this,"NoSheetFound",
								JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);

			return;
		}

		try
		{
			setTitle("Tamil Virithaal- "+saved_file);
			isFirstSaved=true;
			is_saved=true;
			SSMenu ssm=(SSMenu)getJMenuBar();
			ssm.setProps(true);
			setJMenuBar(ssm);
			repaint();
			try
			{
				out=new ObjectOutputStream(new FileOutputStream(saved_file));
				//for properties menu
				out.writeObject(new Date());//data 1
				out.writeObject(isSheet);//data 2
				out.writeObject(sheet_title);//data 3

				/**ROW HEIGHT SAVING CODES----start*/

				int tabcnt=jtp.getTabCount();
				zoom_value=new String[tabcnt];
				for(int s=0;s<tabcnt;s++)//for number of sheets
				{
					table_forSave=(JTable)al_tables.get(s);
					if(zoom)
					{
							int rowHght=table_forSave.getRowHeight();
							zoom_value[s]=Integer.toString(rowHght);
					}
					Hashtable tempRh = new Hashtable();
					for(int r=0;r<table_forSave.getRowCount();r++)//for number of rows
					{
						if(table_forSave.getRowHeight(r)!=20)
						{
							try
							{
								tempRh.put(new Integer(r),new Integer(table_forSave.getRowHeight(r)));
							}
							catch(Exception ee) { 	ee.printStackTrace(); }
					  	}// if ending

					}//inner for ending
					//ht_sheet.put(new Integer(s),ht_rowht[s]);
					ht_sheet.put(new Integer(s), tempRh);

				}//outer for
				out.writeObject(ht_sheet);//for saving row height // data 4

				/**ROW HEIGHT SAVING CODES----end*/

				/**COLUMN WIDTH SAVE PROCESS---------START*/
				for(int c=0;c<cur_table.getColumnCount();c++)
				{
					TableColumnModel tcm = cur_table.getColumnModel();
					TableColumn col = tcm.getColumn(c);
					int cc=col.getPreferredWidth();
					if(cc!=75)
					{
						tempCl.put(new Integer(c),new Integer(cc));
						ht_colwid.put(new Integer(jtp.getSelectedIndex()), tempCl);
					}

				}
				out.writeObject(ht_colwid);//for saving column width //data 5
				/**COLUMN WIDTH SAVE PROCESS---------ends*/
				/**COLOR SAVING METHOD FOLLOWS----Starts*/
				Color saveBkClr=new Color(255,0,0);
				Color saveFrClr=new Color(0,0,255);
				Font deFont=new Font("TABKural",0,12);
				String fntNa=deFont.getFamily();
				String ceFnt;
				//String clrstr=new String();
				for(int s=0;s<tabcnt;s++)//for number of sheets  // for 1
				{
					tableClrSave=(JTable)al_tables.get(s);

					Color bkclr[][]= new Color[tableClrSave.getRowCount()][tableClrSave.getColumnCount()];
					Color frclr[][]= new Color[tableClrSave.getRowCount()][tableClrSave.getColumnCount()];
					Font[][] savFnt=new Font[tableClrSave.getRowCount()][tableClrSave.getColumnCount()];

					for(int m=0;m<tableClrSave.getRowCount();m++)//for 2
					{
						for(int n=0;n<tableClrSave.getColumnCount();n++)//for 3
						{
							saveBkClr=cellAtt[s+1].getBackground(m,n);
							saveFrClr=cellAtt[s+1].getForeground(m,n);
							backclr[m][n]=null;
							foreclr[m][n]=null;
							ceFnt=new String(cellAtt[s+1].getFont(m,n).getFamily());

							if(saveBkClr!=null)
							{
								//backclr[m][n]=saveBkClr;

								bkclr[m][n]=saveBkClr;

							}
							if(saveFrClr!=null)
							{
								frclr[m][n]=saveFrClr;
							}
							if(!ceFnt.equals(fntNa))
							{
								savFnt[m][n]=cellAtt[s+1].getFont(m,n);
							}

						}//end for 3
					}//end for 2

					ht_bkclrSave.put(new Integer(s),bkclr);
					ht_foreclrSave.put(new Integer(s),frclr);
					ht_fntSave.put(new Integer(s),savFnt);

				}//end for 1

				/*for(int j=0; j<4; j++)
				{
					Color[][] cc = (Color[][])ht_bkclrSave.get(new Integer(j));

					for(int h=0; h<cc.length; h++)
					{
						for(int m=0; m<cc[0].length; m++)
						{
							Color c = cc[h][m];

							if(c!=null)
								System.out.println("Col  " + c + "   row " + h + "   col  " + m);

						}
					}
				}*/

				out.writeObject(ht_bkclrSave); // data 6
				out.writeObject(ht_foreclrSave); //data 7
				out.writeObject(ht_fntSave);//data 8

			}	//end of try
			catch(NullPointerException npe)
			{
				System.out.println("npe  "+ npe);
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
			/**----------------------------------Font saving method....ends*/
				out.writeObject(zoom_value);//for saving zoom value. // data 9
				for(int k=1;k<15;k++)
				{
					/*my addition*/
					if(!isSheet[k])
						continue;
					/*my addition ends*/
					Dimension max=new Dimension(total_Rows,headers.length);//worksheet[k].etm.getMax();
					out.writeObject(max);//data 10
					for(i=0;i<max.width;i++)
					{
						for(int j=1;j<max.height;j++)
						{
							if(worksheet[k].data[i][j] != null)
							{
								out.writeObject(worksheet[k].data[i][j]); // data 11
							}
							else
							{
								out.writeObject(null);
							}
						}
					}
				}

				/* recent file changing*/

				for(i=0;i<ll_recentfiles.size();i++)
				{
					if(ll_recentfiles.get(i).toString().equals(saved_file))
					{
						rearrFiles(i);
						break;
					}
				}

				if(i==ll_recentfiles.size())
				{
					ll_recentfiles.addFirst(saved_file);
					if(ll_recentfiles.size()>5)
						ll_recentfiles.removeLast();
				}
                setJMenuBar(new SSMenu(this));
				show();
				pack();
				repaint();

				/* end recent file changing*/
				out.close();


		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e+"1684");
		}


	}//end of saveFile

	/**OPEN FILE METHOD ****/
		/**The method is used to open the saved file. Before
		opening the file the alert message will be given to save
		the currently working sheet.Using Object input stream
		it reads create date,sheet present, sheet title along
		with data of the sheet with dimension.If font is written
		with data then it retrieves the font along with data.
		It invokes the second constructor to open the file.*/
		public void openFile()
		{
					String file =this.getFile();//the name of a
					String loc=new String();
					File fi=new File(file);
					int shInd=0;
					int rowInd=0;
					int rowht=0;

				if(!is_saved)
				{
					wordBundle = this.getWordBundle();
					Object[] options = {
													wordBundle.getString("yes"),
													wordBundle.getString("no"),
													wordBundle.getString("cancel")	            };

					String notSavedWar = wordBundle.getString("notSavedWar");
					String messageTitle = wordBundle.getString("messageTitle");
					int select = showDialog(this,"notSavedWar",
											JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,	options, 0);

					if(select==JOptionPane.YES_OPTION)
					{
						if(!isFirstSaved)
							saveAsFile();
						else
							saveFile();
					}
					if(select==JOptionPane.NO_OPTION)
					{
						try{
								if(!isFirstSaved)
									addinResentFile();
							}
							catch(Exception eC)
							{
								System.out.println("ABCD"+eC);
							}
					}
					if(select==JOptionPane.CANCEL_OPTION)
					{
						return;
					 }
				}

				/**The file chooser object is used to display the files in
				the system directory.*/
				JFileChooser chooser=new JFileChooser(".");
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.addChoosableFileFilter(new ExampleFileFilter("tss","ê¶óé¢èñ¢ «è£ð¢¹è÷¢"));
				chooser.setCurrentDirectory(fi);
				setLanguage(chooser);

				int option=chooser.showOpenDialog(this);

				try
				{
					if(option==JFileChooser.APPROVE_OPTION)
					{
						String selFile=chooser.getSelectedFile().getAbsolutePath();
						if(!(selFile.endsWith(".tss")))
							selFile=new String(chooser.getSelectedFile().getAbsolutePath()+".tss");
						if(!new File(selFile).exists())
						{
							wordBundle = this.getWordBundle();
							Object[] options = {wordBundle.getString("ok")};
							String fileNotFound = wordBundle.getString("fileNotFound");
							String messageTitle = wordBundle.getString("messageTitle");
							int select = showDialog(this,"fileNotFound",
													JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, 0);
							return;
						}

						ObjectInputStream in=new ObjectInputStream(new FileInputStream(selFile));
						createDate=(Date)in.readObject();  //data 1
						isSheet=(boolean []) in.readObject();//data 2
						sheet_title =(String[])in.readObject(); //data 3
						htSheet=(Hashtable)in.readObject();
						htColwid=(Hashtable)in.readObject();

						htBkclrSave=(Hashtable)in.readObject();
						htForeclrSave=(Hashtable)in.readObject();
						htFntSave=(Hashtable)in.readObject();
						zmValue=(String[])in.readObject();

						WorkSheet[] worksheet=new WorkSheet[15];

						for(int k=1;k<15;k++)
						{
							if(!isSheet[k])
								continue;
							Dimension max=(Dimension)in.readObject();//data 7
							worksheet[k]=new WorkSheet(this);
							worksheet[k].etm.setMax(max);
							for(int i=0;i<max.width;i++)
							{
								for(int j=1;j<max.height;j++)
								{
									 Object d=(Object)in.readObject();//data 8

									 if(d!=null)
									 {
										worksheet[k].data[i][j]=d;

									}
									else	{	}
									 if(!(worksheet[k].data[i][j].toString().equals("")))
									 {

									 }

								 }
							 }
						 }

						 /* recent file changing*/

						int i;

						for(i=0;i<ll_recentfiles.size();i++)
						{
							if(ll_recentfiles.get(i).toString().equals(selFile))
							{
								rearrFiles(i);
								break;
							}
						}

						if(i==ll_recentfiles.size())
						{
							ll_recentfiles.addFirst(selFile);
							if(ll_recentfiles.size()>5)
								ll_recentfiles.removeLast();
						}

						try
						{
							addinResentFile();
						}
						catch(Exception e)
						{
							System.out.println(e+" 1809");
						}

						/* end recent file changing*/


						Chathurangam newSs=new Chathurangam(worksheet,selFile,isSheet,sheet_title,htSheet,htColwid,htBkclrSave,htForeclrSave,htFntSave,zmValue);
						newSs.createDate=createDate;
						setVisible(false);
						newSs.setJMenuBar(new SSMenu(newSs));

						 Font tFont=new Font("TABKural",0,12);
						 for(int k=1;k<15;k++)
						 {
							if(!isSheet[k])
								continue;
							 for( i=0;i<50;i++)
							 for(int j=1;j<19;j++)
							 {

							 }
						 }
						 newSs.show();
						 newSs.pack();
						 newSs.repaint();

						 //updateFileMenu(fileName);


					}
				}
				catch(NullPointerException npe)
				{
					System.out.println("1759"+npe);
					npe.printStackTrace();
				}
				catch(Exception e)
				{
					System.out.println(e);
					e.printStackTrace();
				}

				//ll_recentfiles.add(fileName);

		}//end of openFile

		/**RECENT FILE OPEN MEHTOD****/
		/**The method to open the file from the
		recent files list. It is similar to open file method.
		Using object input stream it reads the create date,
		sheet present,sheet title and data with font. It checks
		with the recent files list and rearrange it.
		@param fName of type string the file name to be opened.
		*/
		public void FileOpenRFiles(String fName)
		{
			try
			{
				if(!is_saved)
				{

					wordBundle = this.getWordBundle();
					Object[] options = {
									wordBundle.getString("yes"),
									wordBundle.getString("no"),
									wordBundle.getString("cancel")
								   };

				String notSavedWar = wordBundle.getString("notSavedWar");
				String messageTitle = wordBundle.getString("messageTitle");
				int select = showDialog(this,"notSavedWar",
										JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
										options, 0);

				if(select==JOptionPane.YES_OPTION)
				{
					if(!isFirstSaved)
						saveAsFile();
					else
						saveFile();
				}
				if(select==JOptionPane.NO_OPTION)
				{
					try{

					if(!isFirstSaved)
					addinResentFile();
						}
						catch(Exception eC)
						{
							System.out.println("ABCD"+eC);
						}


				}
				if(select==JOptionPane.CANCEL_OPTION)
				{
					return;
				 }
				}

				ObjectInputStream in=new ObjectInputStream(new FileInputStream(fName));
				createDate=(Date)in.readObject();//for properties//data 1
				isSheet=(boolean []) in.readObject();//data 2
				sheet_title=(String[])in.readObject();//data 3
				htSheet=(Hashtable)in.readObject();
				htColwid=(Hashtable)in.readObject();

				htBkclrSave=(Hashtable)in.readObject();
				htForeclrSave=(Hashtable)in.readObject();
				htFntSave=(Hashtable)in.readObject();
				zmValue=(String[])in.readObject();
				WorkSheet[] worksheet=new WorkSheet[15];

				for(int k=1;k<15;k++)
				{
					if(!isSheet[k])
						continue;

					Dimension max=(Dimension)in.readObject();//data 7
					worksheet[k]=new WorkSheet(this);
					worksheet[k].etm.setMax(max);

					for(int i=0;i<max.width;i++)
					{
						for(int j=1;j<max.height;j++)
						{
							 Object d=(Object)in.readObject();//data 8
							 if(d!=null)
							 {
								worksheet[k].data[i][j]=d;
							}
							else
							{
							}

						 }
					}
				 }

				 /* recent file changing*/

				int i;

				for(i=0;i<ll_recentfiles.size();i++)
				{
					if(ll_recentfiles.get(i).toString().equals(fName))
					{
						rearrFiles(i);
						break;
					}
				}

				if(i==ll_recentfiles.size())
				{
					ll_recentfiles.addFirst(fName);
					if(ll_recentfiles.size()>5)
						ll_recentfiles.removeLast();
				}

				try
				{
					addinResentFile();
				}
				catch(FileNotFoundException fnfe)
				{
					System.out.println("fnfe");
				}
				catch(Exception e)
				{
					System.out.println(e+"1935");
				}

				/* end recent file changing*/

				 Chathurangam newSs=new Chathurangam(worksheet,fName,isSheet,sheet_title,htSheet,htColwid,htBkclrSave,htForeclrSave,htFntSave,zmValue);
				 newSs.createDate=createDate;
				 setVisible(false);
				 newSs.setJMenuBar(new SSMenu(newSs));
				 Font tFont=new Font("TABKural",0,12);
				 for(int k=1;k<15;k++)
				 {
					if(!isSheet[k])
						continue;
					 for( i=0;i<50;i++)
					 for(int j=1;j<19;j++)
					 {
					 }
				 }
				 newSs.show();
				 newSs.pack();
				 newSs.repaint();

				 //updateFileMenu(fileName);
			}
			catch(FileNotFoundException fnfe)
			{
				wordBundle = this.getWordBundle();
				Object[] options = {wordBundle.getString("ok")};
				String FileNotFound = wordBundle.getString("FileNotFound");
				String messageTitle = wordBundle.getString("messageTitle");
				int reply = showDialog(this,"FileNotFound",JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, 0);
				return;

			}
			catch(NullPointerException npe)
			{
				System.out.println("1883 "+npe);
			}
			catch(Exception e)
			{
				System.out.println(e+"1963");
				e.printStackTrace();
			}
		}
	//end of FileOpenRFiles

	// Helper method to create new toolbar buttons
	public JButton addToolbarButton( JToolBar toolBar, boolean bUseImage, String sButtonText,
									String sButton, String sToolHelp )
	{
		JButton b;

		// Create a new button
		if( bUseImage )
			b = new JButton( new ImageIcon( sButton + ".gif" ) );
		else
			b = (JButton)toolBar.add( new JButton() );

		// Add the button to the toolbar
		toolBar.add( b );

		// Add optional button text
		if( sButtonText != null )
			b.setText( sButtonText );
		else
		{
			// Only a graphic, so make the button smaller
			b.setMargin( new Insets( 0, 0, 0, 0 ) );
		}


		// Add optional tooltip help
		if( sToolHelp != null )
			b.setToolTipText( sToolHelp );

		// Make sure this button sends a message when the user clicks it
		b.setActionCommand( "Toolbar:" + sButton );
		b.addActionListener( this );

		return b;
	}



	/**The method is used to provide the alert message when
	closing the window if it is not saved already.
	@return boolean it returns true if confirmed to close the window.*/
	public boolean confirmClose()
	{
		try{
				wordBundle = this.getWordBundle();
				Object[] options = {wordBundle.getString("yes"),
										wordBundle.getString("no"),
										wordBundle.getString("cancel")};
				String notSavedWar = wordBundle.getString("notSavedWar");
				String messageTitle = wordBundle.getString("messageTitle");

			int reply = showDialog(this,"notSavedWar",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, options, 0);

			if(reply==JOptionPane.YES_OPTION)
				{

					boolean isSave=true;
					if(!isFirstSaved)
						isSave=saveAsFile();
					else
						saveFile();
					if(isSave)
					{
						addinResentFile();
						//System.exit(0);
						setVisible(false);
					}
				}
				if(reply==JOptionPane.NO_OPTION)
				{
					addinResentFile();
					//System.exit(0);
					setVisible(false);
					return true;
				}
				if(reply==JOptionPane.CANCEL_OPTION)
				{
					setVisible(true);
					return false;
				}
		}//end of try
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		return true;
	}

	/**The method is used to add the files into the linkedlist
	for displaying recent files.It displays maximum of five files.*/
	public void filestollist()
		{
			/**The string object is used to store the file name.*/
			String filename=new String();

			/**The temp object is used to read the file name object.*/
			Object temp;

			/**The object inputstream to put the file list into the text file/*/
			ObjectInputStream ip;

			try
				{
					ip = new ObjectInputStream(new FileInputStream("rfile"));
				}
				catch(Exception e)
				{
					System.out.println("file inputStream-"+e);
					return;
				}

				for(int i=0; i<no_ResentFiles; i++ )
				{
					try
					{
						temp=ip.readObject();
						filename=temp.toString();
					}
					catch(Exception ee)
					{
						//System.out.println("no more files-"+ee);
						return;
					}
					ll_recentfiles.add(filename);
				}
				//System.out.println("the value of i"+i);

		}

		/**The method is used to display the recent files list. The file
		list is read from the text file using object output stream.
		It throws IO exception if the file is not read correctly from the
		text file.*/
		public void addinResentFile() throws IOException
		{
			/**The menu string object is used to store the file name.*/
			String menu=new String();
			try
			{
				ObjectOutputStream p = new ObjectOutputStream(new FileOutputStream("rfile"));//ostream);
				for(int i=0; i<ll_recentfiles.size() ; i++)
		        {
					p.writeObject((menu=ll_recentfiles.get(i).toString()));
				}
				p.flush();

			}
			catch(FileNotFoundException e)
			{
				System.out.println("   yes"+e);
			}

	} // end of method ---addinResentFile

	/**The method to insert sheet as default sheets when
	the application starts.
	@param constructorType of type boolean to check whether it is
	called from main constructor or file open constructor.*/
	public void insertSheet(boolean constructorType)//called when constructor invokes
	{
			jsp=new JScrollPane[sp];
			sheetCount=0;
			for(int i=1;i<15;i++)
			{
				if(isSheet[i])
				{
					initTable(i,constructorType);//initialises the tables
					jtp.addTab(sheet_title[i-1],jsp[i]);
					//System.out.println(isSheet[i]+"==="+i);
					sheetCount++;
					sheetNo=i;
				}
			}
			jtp.setFont(bilingualFont);

	}

	/**The method is used to insert when the insert sheet menu items is
	invoked. This will add more sheets along with default four worksheets.
	@param sheet of type integer to set for the sheet title order number.*/
	public void insertSheet(int sheet)//called when insert sheet menu clicked
	{
			int sel_ind=jtp.getSelectedIndex();
			tab_count++;
			initTable(sheet,true);//initialises the tables
			//jtp.addTab("õ¤ó¤ð¢¹"+sheet,jsp[sheet]);
			jtp.insertTab("õ¤ó¤ð¢¹"+sheet,null,jsp[sheet],null,sel_ind);
			//jtp.setSelectedIndex(sheet);
			jtp.setFont(bilingualFont);
			is_saved=false;
			SSMenu ssm=(SSMenu)getJMenuBar();
			ssm.setProps(false);
			setJMenuBar(ssm);
			repaint();

	}
  public void insertSheet(int sheet, int sheetID)//called when insert sheet menu clicked
	{
			int sel_ind=jtp.getSelectedIndex();
			tab_count++;
			initTable(sheet,true);//initialises the tables
			jtp.insertTab("õ¤ó¤ð¢¹"+sheetID,null,jsp[sheet],null,sel_ind);
			jtp.setFont(bilingualFont);
			is_saved=false;
			SSMenu ssm=(SSMenu)getJMenuBar();
			ssm.setProps(false);
			setJMenuBar(ssm);
			repaint();
 	}


	/**The method is used to get the cell value for copy function.
	The selected row and selected column will be found out with
	row & column index then the value(s) of the cell(s) get copied
	and stored in the clipboard.*/
	public void cellValue()
	{

			/*my addition */
			int i,j,startRow,startCol,limitRow,limitCol;
			startRow=cur_table.getSelectedRow();
			startCol=cur_table.getSelectedColumn();
			limitRow=cur_table.getSelectedRowCount();
			limitCol=cur_table.getSelectedColumnCount();
			if(startRow==-1 || startCol==-1 || limitRow==0 || limitCol==0)
			{

				wordBundle = this.getWordBundle();
				Object[] options = {wordBundle.getString("yes")	};
				String InvalidSelection = wordBundle.getString("InvalidSelection");
				String messageTitle = wordBundle.getString("messageTitle");

				int reply = showDialog(this,"InvalidSelection",
					JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
					 options, 0);
				return;

			}
			rowIndex=0;colIndex=0;
			clipboard = new Object[cur_table.getRowCount()][cur_table.getColumnCount()];
			for(i=startRow;i<(startRow+limitRow);i++,rowIndex++)
				for(j=startCol,colIndex=0;j<(startCol+limitCol);j++,colIndex++)
					clipboard[rowIndex][colIndex]=cur_table.getValueAt(i,j);
			//CellCopyProps ccp=new CellCopyProps(startRow,startCol,cellAtt[curPane].getFont(startRow,startCol).getFamily(),cellAtt[curPane].getBackground(startRow,startCol),cellAtt[curPane].getForeground(startRow,startCol));
			cellPropsGet();
	}

	/**The method is used to get the cell value for cut function.
	The selected row and selected column will be found out with
	row & column index then the value(s) of the cell(s) get copied
	and stored in the clipboard. The source that is from where it is
	copied is removed from the cell for cut function.*/
	public void cellValue_cut()
	{

			/*my addition */
			int i,j,startRow,startCol,limitRow,limitCol;
			startRow=cur_table.getSelectedRow();
			startCol=cur_table.getSelectedColumn();
			limitRow=cur_table.getSelectedRowCount();
			limitCol=cur_table.getSelectedColumnCount();
			//System.out.println("srow-"+startRow+"-scol-"+startCol+"-lrow-"+limitRow+"-lcol-"+limitCol);
			if(startRow==-1 || startCol==-1 || limitRow==0 || limitCol==0)
			{
				wordBundle = this.getWordBundle();
				Object[] options = {wordBundle.getString("ok")};
				String InvalidSelection = wordBundle.getString("InvalidSelection");
				String messageTitle = wordBundle.getString("messageTitle");
				int reply = showDialog(this,"InvalidSelection",	JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null,options, 0);
				return;
			}
			rowIndex=0;colIndex=0;
			clipboard = new Object[cur_table.getRowCount()][cur_table.getColumnCount()];
			for(i=startRow;i<(startRow+limitRow);i++,rowIndex++)
				for(j=startCol,colIndex=0;j<(startCol+limitCol);j++,colIndex++)
				{
					clipboard[rowIndex][colIndex]=cur_table.getValueAt(i,j);
					cur_table.setValueAt("",i,j);
				}
			is_saved=false;
			SSMenu ssm=(SSMenu)getJMenuBar();
			ssm.setProps(false);
			setJMenuBar(ssm);
			repaint();
			cellPropsGet();
			cur_table.repaint();

	}

	public void cellPropsGet()
	{
		rowselCP=cur_table.getSelectedRow();
		colselCP=cur_table.getSelectedColumn();
		fntCP=cellAtt[curPane].getFont(rowselCP,colselCP).getFamily();
		fsizeCP=cellAtt[curPane].getFont(rowselCP,colselCP).getSize();
		fstyleCP=cellAtt[curPane].getFont(rowselCP,colselCP).getStyle();
		bkcolorCP=cellAtt[curPane].getBackground(rowselCP,colselCP);
		forecolorCP=cellAtt[curPane].getForeground(rowselCP,colselCP);
		//clrbkCP=bkcolorCP.getRGB();
		//clrforeCP=forecolorCP.getRGB();
		fntCA=new Font(fntCP,fstyleCP,fsizeCP);
	}

	/**The method is used to get the value to be pasted for cut/copy function.
	The selected row and selected column will be found out with
	row & column index then the value(s) of the cell(s) to be copied
	and retrieved from the clipboard. */
	public void cellValuePaste()
	{
				int startRow,startCol,i,j;
				startRow=cur_table.getSelectedRow();
				startCol=cur_table.getSelectedColumn();

				int rowDiff=cur_table.getRowCount()-startRow;
				int colDiff=cur_table.getColumnCount()-startCol;

				if(rowDiff<rowCnt || colDiff<colCnt)
				{
					wordBundle = this.getWordBundle();
					Object[] options = {wordBundle.getString("ok")};
					String InvalidDest = wordBundle.getString("InvalidDest");
					String messageTitle = wordBundle.getString("messageTitle");
					int reply = showDialog(this,"InvalidDest",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
				}
				else
				{
					if(startRow==-1 || startCol==-1)
					{
						wordBundle = this.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String InvalidSelection = wordBundle.getString("InvalidSelection");
						String messageTitle = wordBundle.getString("messageTitle");
						int reply = showDialog(this,"InvalidSelection",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null,options, 0);
						return;
					}
					for(i=0;i<rowIndex && (startRow+i)<cur_table.getRowCount();i++)
					{
						for(j=0;j<colIndex && (startCol+j)<cur_table.getColumnCount();j++)
						{
							cur_table.setValueAt(clipboard[i][j],startRow+i,startCol+j);
							cellAtt[curPane].setFont(fntCA,startRow+i,startCol+j);
							cellAtt[curPane].setBackground(bkcolorCP,startRow+i,startCol+j);
							cellAtt[curPane].setForeground(forecolorCP,startRow+i,startCol+j);
						}
					}
					is_saved=false;
					SSMenu ssm=(SSMenu)getJMenuBar();
					ssm.setProps(false);
					setJMenuBar(ssm);
					repaint();
					cur_table.repaint();
				}

	}

	/**The method is used to insert row between the data in the sheet.
	This will find the selected row and selected column from the table
	and insert the row where needed. If no row(s) or Column(s) is selected
	it displays alert message.*/
	public void handleInsertRow()
	{
		WorkSheet tempSheet;
		startRow=cur_table.getSelectedRow();
		if(startRow==-1)
		{
			wordBundle = this.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			String InvalidSelection = wordBundle.getString("InvalidSelection");
			String messageTitle = wordBundle.getString("messageTitle");
			int reply = showDialog(this,"InvalidSelection",	JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, 0);
			return;
		}

		else if(startRow==getWorkSheet().getMaxRow()-1)
		{
				wordBundle = this.getWordBundle();
				Object[] options = {wordBundle.getString("ok")};
				String LimitExceeded = wordBundle.getString("Limit Exceeded");
				String messageTitle = wordBundle.getString("messageTitle");
				int reply = showDialog(this,"LimitExceeded",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, 0);
				return;
		}
		is_saved=false;
		SSMenu ssm=(SSMenu)getJMenuBar();
		ssm.setProps(false);
		setJMenuBar(ssm);
		repaint();
		tempSheet=getWorkSheet();
		JTextField jtf=(JTextField)cur_table.getEditorComponent();

		if(jtf!=null)
		{
			sel_row=cur_table.getEditingRow();
			sel_col=cur_table.getEditingColumn()+1;
			tempSheet.data[sel_row][sel_col]=jtf.getText();
		}
		//check for any data loss
		for(int i=tempSheet.getMaxRow()-1;i>=startRow && i>0;i--)
		{
			for(int j=1;j<tempSheet.getMaxCol()-1;j++)
			{
				tempSheet.data[i][j]=tempSheet.data[i-1][j];
			}
		}
		for(int j=1;j<tempSheet.getMaxCol()-1;j++)
		{
			tempSheet.data[startRow][j]=new String("");
		}
		//System.out.println("==="+startRow);
		repaint();
	}

	/**The method is used to insert column between the data in the sheet.
		This will find the selected row and selected column from the table
		and insert the column where needed. If no row(s) or Column(s) is selected
	it displays alert message.*/
	public void handleInsertCol()
	{
		int startCol,sel_row,sel_col;
		startCol=cur_table.getSelectedColumn();
		if(startCol==-1)
		{
			wordBundle = this.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			String InvalidSelection = wordBundle.getString("InvalidSelection");
			String messageTitle = wordBundle.getString("messageTitle");

			int reply = showDialog(this,"InvalidSelection",
										JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				 options, 0);

			return;
		}
		else if(startCol==getWorkSheet().getMaxCol()-2)
		{
			wordBundle = this.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			String LimitExceeded = wordBundle.getString("Limit Exceeded");
			String messageTitle = wordBundle.getString("messageTitle");

			int reply = showDialog(this,"LimitExceeded",
						JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
 							options, 0);
			return;
		}
		is_saved=false;
		SSMenu ssm=(SSMenu)getJMenuBar();
		ssm.setProps(false);
		setJMenuBar(ssm);
		repaint();
		WorkSheet tempSheet=getWorkSheet();
		JTextField jtf=(JTextField)cur_table.getEditorComponent();
		if(jtf!=null)
		{
			sel_row=cur_table.getEditingRow();
			sel_col=cur_table.getEditingColumn()+1;
			tempSheet.data[sel_row][sel_col]=jtf.getText();
		}
		//check for any data loss

		for(int j=tempSheet.getMaxCol()-1;j>startCol;j--)
		{
			for(int i=0;i<tempSheet.getMaxRow();i++)
			{
				tempSheet.data[i][j]=tempSheet.data[i][j-1];
			}
		}
		//System.out.println("hai=="+startCol);
		for(int i=0;i<tempSheet.getMaxRow();i++)
		{

			tempSheet.data[i][startCol+1]=new String("");
		}
		repaint();
	}

	/**The method is used to delete row between the data in the sheet.
			This will find the selected row and selected column from the table
			and delete the row where not needed. If no row(s) or Column(s) is selected
	it displays alert message.*/
	public void handleDeleteRow()
	{
		int startRow,sel_row,sel_col,i;
		WorkSheet tempSheet;
		startRow=cur_table.getSelectedRow();
		if(startRow==-1)
		{
			wordBundle = this.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			String InvalidSelection = wordBundle.getString("InvalidSelection");
			String messageTitle = wordBundle.getString("messageTitle");

			int reply = showDialog(this,"InvalidSelection",
										JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				 options, 0);

			return;
		}
		is_saved=false;
		SSMenu ssm=(SSMenu)getJMenuBar();
		ssm.setProps(false);
		setJMenuBar(ssm);
		repaint();
		tempSheet=getWorkSheet();
		JTextField jtf=(JTextField)cur_table.getEditorComponent();
		if(jtf!=null)
		{
			sel_row=cur_table.getEditingRow();
			sel_col=cur_table.getEditingColumn()+1;
			tempSheet.data[sel_row][sel_col]=jtf.getText();
		}

		if(startRow!=tempSheet.getMaxRow()-1)
		{
			for(i=startRow;i<tempSheet.getMaxRow()-1;i++)
			{
				for(int j=1;j<tempSheet.getMaxCol()-1;j++)
				{
					tempSheet.data[i][j]=tempSheet.data[i+1][j];
				}
			}
		}
		else
			i=startRow;
		for(int j=1;j<tempSheet.getMaxCol()-1;j++)
		{
			tempSheet.data[i][j]=new String("");
		}
		repaint();
	}

	/**The method is used to delete column between the data in the sheet.
		This will find the selected row and selected column from the table
		and delete the column where not needed. If no row(s) or Column(s) is selected
	it displays alert message.*/
	public void handleDeleteCol()
	{
		int startCol,sel_row,sel_col,j;
		startCol=cur_table.getSelectedColumn();
		if(startCol==-1)
		{
			wordBundle = this.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			String InvalidSelection = wordBundle.getString("InvalidSelection");
			String messageTitle = wordBundle.getString("messageTitle");

			int reply = showDialog(this,"InvalidSelection",
							JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				 					options, 0);

			return;
		}
		is_saved=false;
		SSMenu ssm=(SSMenu)getJMenuBar();
		ssm.setProps(false);
		setJMenuBar(ssm);
		repaint();
		WorkSheet tempSheet=getWorkSheet();
		JTextField jtf=(JTextField)cur_table.getEditorComponent();
		if(jtf!=null)
		{
			sel_row=cur_table.getEditingRow();
			sel_col=cur_table.getEditingColumn()+1;
			tempSheet.data[sel_row][sel_col]=jtf.getText();
		}
		//check for any data loss
		startCol++;
		if(startCol!=tempSheet.getMaxCol()-1)
		{
			for(j=startCol;j<tempSheet.getMaxCol()-1;j++)
			{
				for(int i=0;i<tempSheet.getMaxRow();i++)
				{
					tempSheet.data[i][j]=tempSheet.data[i][j+1];
				}
			}
		}
		else
			j=startCol;

		for(int i=0;i<tempSheet.getMaxRow();i++)
		{

			tempSheet.data[i][j]=new String("");
		}
		repaint();
	}

	/**The method to check the sheets availablity before opening the
	saved file. It returns true or false depending upon the sheet is present
	or not.
	@return boolean it returns true if sheet is present.*/
	private boolean sheetPresent()
	{
		for(int i=1;i<15;i++)
			if(isSheet[i])
				return true;
		return false;
	}

	/**The method is used to get the components from
	the file chooser object to set the language(Tamil)
	in the filechooser button components.
	@param fc the file chooser object to display
	the files in the system directory.*/
	public void setLanguage(JFileChooser fc)
	{
			wordBundle = this.getWordBundle();

			fc.setFont(this.getBilingualFont());
			for(int i=0; i < fc.getComponentCount();i++)
				if (fc.getComponent(i) instanceof JPanel)
					setJFCButtonText((JPanel)fc.getComponent(i),wordBundle);
	}

	/**The method to set the button  text in Tamil in the
	JComponents.The equivalent Tamil words are retrieved
	from the word bundle as property file.
	@param jp the JPanel object.
	@param r the ResourceBundle object to store the list
	of Tamil and English words.*/
	private void setJFCButtonText(JPanel jp, ResourceBundle r)
		{
			for(int j=0; j < jp.getComponentCount();j++)
			{
				Component bo = jp.getComponent(j);
				if (bo instanceof JPanel)
					setJFCButtonText((JPanel)bo,r);
				if (bo instanceof JButton)
				{
					JButton b = (JButton)bo;
					if(b.getText() != null)
					{
						if(b.getText().equals("Open"))
							b.setText(r.getString("open"));
						if(b.getText().equals("Save"))
							b.setText(r.getString("save"));
						if(b.getText().equals("Cancel"))
							b.setText(r.getString("cancel"));
					}
				}
				else if (bo instanceof JLabel)
				{
					JLabel l = ((JLabel)bo);
					if(l.getDisplayedMnemonic() == 73)
						l.setText(r.getString("select"));
					else if(l.getDisplayedMnemonic() == 78)
						l.setText(r.getString("fileName"));
					else if(l.getDisplayedMnemonic() == 84)
						l.setText(r.getString("fileType"));
				}
			}
	}
	//fc ends here

	/** optionpane show dialog
	Option pane for alert messages, the button text will be displayed in Tamil.
	@return int options
	@param parent component to which the optionpane to be displayed
	@param msg the message to be displayed
	@param option the option to be choosen
	@param type the type of optionpane
	@param icon the icon to be displayed
	@param options the available options to be displayed like yes,no etc..
	@param selectIndex the integer value for the selected value
	*/	public int showDialog(Component parent,String msg,int option,int type,Icon icon,
		Object[] options,int selectIndex)
	   {

		wordBundle = this.getWordBundle();
		String messageTitle = wordBundle.getString("messageTitle");

		String message = wordBundle.getString(msg);

		JOptionPane p = new JOptionPane((Object)message,
			option, type, null,	options, options[0]);
		JDialog d = p.createDialog(parent,messageTitle);

		d.setResizable( false );
		d.show();
		Object selectedValue = p.getValue();

		if(selectedValue.equals(new Integer(-1)))
		{
			d.dispose();
			return JOptionPane.CANCEL_OPTION;
		}

		if(selectedValue == null)
		{
			d.dispose();
			return JOptionPane.CLOSED_OPTION;
		}

		//If there is not an array of option buttons:
		if(options == null)
		{
			if(selectedValue instanceof Integer)
			{
				d.dispose();
				return ((Integer)selectedValue).intValue();
			}
			d.dispose();
			return JOptionPane.CLOSED_OPTION;
		}

		//If there is an array of option buttons:
		for(int counter = 0, maxCounter = options.length;
			counter < maxCounter; counter++)
		{
			if(options[counter].equals(selectedValue))
			{
				d.dispose();
				return counter;
			}
		}
		d.dispose();
		return 0;
	}

	/**The method is used to rearrange the recent files list
	if it exceeds the limit of five.The recent file list
	will be at maximum of five in the file menu.
	@param objIndex of type integer to set the position of the file.*/
	private void rearrFiles(int objIndex)
	{
		Object temp;
		temp=ll_recentfiles.remove(objIndex);
		ll_recentfiles.addFirst(temp);
	}

	/**The method to set the sheet in the proper place
	depending upon the sheet index and the availablity.
	@param index of type integer the sheet position.
	@param val of type boolean to check the sheet position.*/
	public void setSheet(int index,boolean val)
	{
		//if(index>=15)
		//index=1;
		if(val)
		{
			isSheet[index]=val;
			return;
		}

		for(int i=1;i<15;i++)
		{
			if(isSheet[i])
			{
				index--;
				if(index==0)
				{
					isSheet[i]=val;
					break;
				}
			}
		}

	}

	/**The method is used to set the sheet position
	in the correct place.
	@return int the sheet to be set at the position.*/
	public int setIsheet()
	{
		int i,isheetVal=0;
		for(i=1;i<15;i++)
			if(isSheet[i])
				isheetVal=i;
		return isheetVal;
	}

	/**The method is used to set the popup menu
	when right clicking the mouse on the sheet.It finds the
	position(co-ordinates) of the mouse pointer and displays the
	popup menu.
	@param e the MouseEvent*/
	public void processMouseEvent(MouseEvent e)//right click popup menu
		{
		    if (e.isPopupTrigger())
		    {
				selectedComponent = e.getComponent();
				int height=e.getY();
				int width=e.getX();
				if(getWidth()-width<300)
					width-=jp_popup.getWidth();
				if(getHeight()-height<325)
					height-=jp_popup.getHeight();

	          jp_popup.show(e.getComponent(), width, height);
		    }
		    super.processMouseEvent(e);
        }

	/**The anonymous inner class to fire actions when
	mouse clicking.It has three methods. All the methods
	are implemented.They are mousePressed,
	mouseClicked and mouseReleased also
	checkpopup method is implemented.This is used
	for popup menu.*/
	MouseListener mouseListener = new MouseAdapter()
	   {
		   public void mousePressed(MouseEvent e) { checkPopup(e); }
		   public void mouseClicked(MouseEvent e)
		    {
      		//	System.out.println(" Click Cound OLD :"+e.getClickCount());
      		mouseClkCnt=e.getClickCount();
			  if(e.getClickCount()==1)
			  {
				//temptable = (JTable) e.getComponent();
				//tempheader = (JTable) e.getComponent();
				setInputField();
				//setInputFont();
				cur_tableHeader=(JTable)al_tableHeaders.get(jtp.getSelectedIndex());
				cur_table=(JTable)al_tables.get(jtp.getSelectedIndex());
				//temptable=cur_table;
			  }
			  if(e.getClickCount()>=2)
			  {
				  inpText=inputField.getText().trim();
				  if(inpText.length()!=0)
				  {
				  cur_table.setValueAt(inpText,cur_table.getSelectedRow(),cur_table.getSelectedColumn());
			  	 }
			  	 else
			  	 {
					 System.out.println("NULL STRING");
				 }
			  }
			  // checkPopup(e);
			}
		   public void mouseReleased(MouseEvent e)
		   {
			   checkPopup(e);
		   }
    	  private void checkPopup(MouseEvent e)
	      {
	        if (e.isPopupTrigger())
	        {
				 /*inpText=inputField.getText().trim();
				  if(inpText.length()!=0)
				  {
				  		cur_table.setValueAt(inpText,cur_table.getSelectedRow(),cur_table.getSelectedColumn());
			  	  }*/
	        	//cur_table.setRowSelectionInterval(0,1);
				selectedComponent = e.getComponent();
				int height=e.getY();
				int width=e.getX();
				if(getWidth()-width<300)
					width-=jp_popup.getWidth();
				if(getHeight()-height<325)
					height-=jp_popup.getHeight();

	          jp_popup.show(e.getComponent(), width, height);
	        }
          }
	    };

		/**The anonymous inner class to fire actions when
		mouse clicking.It has three methods. All the methods
		are implemented.They are mousePressed,
		mouseClicked and mouseReleased.It is used
		to rename the sheet title when double clicking the sheet title.*/
       MouseListener mListener = new MouseAdapter()
	  	   {

	  		   public void mousePressed(MouseEvent e) { checkPopup(e); }
	  		   public void mouseClicked(MouseEvent e)
	  		    {
	  			  if(e.getClickCount()>=2)
	  			  {
						openWindow();
	  			  }
	  			   checkPopup(e);
	  			}
	  		   public void mouseReleased(MouseEvent e)  { checkPopup(e); }
	      	   private void checkPopup(MouseEvent e)
	  	       {
	           }
	    	};
      //ends here
      KeyListener keyListener=new KeyAdapter()
      {
		  public void keyPressed(KeyEvent ke)
		  {
			  //System.out.println("Ky PressedTyped");
		  }
		  public void keyReleased(KeyEvent ke)
		  {
		  		// System.out.println("Ky ReelesTyped");
		  }
		  public void keyTyped(KeyEvent ke)
		  {
		  		/* int row=cur_table.getSelectedRow();
		  		 int col=cur_table.getSelectedColumn();

		  		if(mouseClkCnt==1)
		  		{
					mouseClkCnt=0;
					cur_table.setValueAt("",row,col);
		  			cur_table.repaint();
				}*/

		  }
	  };
	public void openWindow()
	{
		JFrame name= new NameFrame(this,jtp);
		 name.show();
	}



	/**The method is used to get the file when save the file
	or using saveAs file.
	@return String the name of the saved file.*/
	public String getFile()
	{
		return saved_file;
	}

	/**The method to initiate the tabbed pane for setting the
	default worksheets with pane change listeners to find out
	the currently selected pane.*/
	void initPane()
	{
		   jtp.getModel().addChangeListener(
		   new ChangeListener()
		   {
		   	/**The inner method is used to fire the action event
		   	when the state of the pane is changed.*/
		      public void stateChanged(ChangeEvent e)
				{

					SingleSelectionModel model = (SingleSelectionModel) e.getSource();

					if(model.getSelectedIndex() == jtp.getTabCount()-1)
					  {
					//	System.out.println("curpane value "+curPane);
					  }
					  curPane=model.getSelectedIndex()+1;

		       }
		   }
		);
	}

	/**The method to initiate the table as a worksheet.It sets the
	font of the table,and the models to be used for back color
	and fore color. Also setting the table heading with Table header in
	Tamil. The editor is fixed to enable the Tamil font when editing the
	table.The array of tables are stored in the array list.
	@param i the integer value to set the no. of sheets to be initialised.
	@param constructorType of type boolean to check whether
	the method is called from the constructor. */
	void initTable(int i,boolean constructorType)
	{
		if(constructorType)
		worksheet[i]=new WorkSheet(this);
		backclr=worksheet[i].backclr;
		foreclr=worksheet[i].foreclr;
		actml[i] = new AttributiveCellTableModel(headers,worksheet[i].data,this,total_Rows);
		cellAtt[i] =(CellFont)actml[i].getCellAttribute();
		table[i]=new JTable(actml[i]);
		table[i].setDefaultEditor(Object.class, new MyEditor(this));
		table[i].setDefaultRenderer(Object.class ,new AttributiveCellRenderer(this));
		table[i].setColumnModel(worksheet[i].cm);
		table[i].setCellSelectionEnabled(true);
		table[i].addMouseListener(mouseListener);
		table[i].addKeyListener(keyListener);
		table[i].setRowHeight(20);
		table[i].setRowSelectionAllowed(true);
		table[i].setGridColor(Color.black);
		table[i].setSelectionBackground(Color.lightGray);

		al_tables.add(table[i]);
		jtp.setPreferredSize(new Dimension(775,450));

		JTableHeader th=table[i].getTableHeader();
		th.setFont(worksheet[i].curFont);
		th.setReorderingAllowed(false);
		th.setToolTipText("Column heading");

		table[i].setTableHeader(th);

		headerColumn=new JTable(worksheet[i].tm, worksheet[i].rowHeaderModel);
		table[i].createDefaultColumnsFromModel();
		headerColumn.createDefaultColumnsFromModel();
		table[i].setSelectionModel(headerColumn.getSelectionModel());
		headerColumn.setBackground(Color.black);
		headerColumn.setSelectionBackground(Color.lightGray);
		headerColumn.setColumnSelectionAllowed(false);
		headerColumn.setCellSelectionEnabled(false);
  		headerColumn.setDefaultRenderer(Object.class, new ButtonRenderer());
		headerColumn.setDefaultEditor(Object.class, new ButtonEditor(this,new JCheckBox()));
		headerColumn.setRowHeight(20);
		al_tableHeaders.add(headerColumn);

		JViewport jv=new JViewport();
		jv.setView(headerColumn);
		jv.setPreferredSize(headerColumn.getMaximumSize());

		table[i].setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		headerColumn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		jsp[i]=new JScrollPane(table[i]);
		jsp[i].setRowHeader(jv);

		worksheet[i].colSelModel=table[i].getColumnModel().getSelectionModel();
		worksheet[i].colSelModel.addListSelectionListener(new SelectionDebugger("col",worksheet[i],worksheet[i].colSelModel));

		worksheet[i].rowSelModel=table[i].getSelectionModel();
		worksheet[i].rowSelModel.addListSelectionListener(new SelectionDebugger("row",worksheet[i],worksheet[i].rowSelModel));

		table[i].changeSelection(0,0,false,false);
		repaint();
	}


	/**The method is used to print the data through the printer.
	@param g of type Graphics
	@param pageFormat of type PageFormat
	@param pageIndex of type integer the no. of page to be printed.*/
	public int print(Graphics g, PageFormat pageFormat,int pageIndex) throws PrinterException
		{

			if(pageIndex < 0)
				return Printable.NO_SUCH_PAGE;
			//translate screen coordinate system to printing coordinate system
			Graphics2D g2 = (Graphics2D)g;
			g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			double pX = pageFormat.getImageableWidth()/this.getWidth();
			double pY = pageFormat.getImageableHeight()/this.getHeight();
			double pPrint = Math.min(pX, pY);
			g2.scale(pPrint,pPrint);
			g2.clip(new Rectangle(0, 0,	cur_table.getWidth(), cur_table.getHeight()));
			cur_table.print(g2);
			return Printable.PAGE_EXISTS;


	}//end of print method

/**
 The internal class which is used to set the Tamil font
 for the table when the table is getting edited.
 the JTextField is used as cell editor component for the JTable.
 It enables the tamil font when table cell is in editable mode.
 The user defined class is derived from the DefaultCellEditor
*/
class MyEditor extends DefaultCellEditor //implements ActionListener//to display tamil font when typing
	  {
		/**The constructor used for the class to set the
		text field as the editor component.*/
	    JTextField editor;
	    UndoManager undomanager;
	    Chathurangam chathu;
	    SSMenu ss;
	    public MyEditor(Chathurangam chathu)
	    {
			super(new JTextField());
			this.chathu=chathu;
		}

	    public Component getTableCellEditorComponent(JTable table, Object value,
	                                                  boolean isSelected,  int row, int column)
	    {
	     	editor = (JTextField)super.getTableCellEditorComponent(table, value, isSelected, row, column);
	        editor.setHorizontalAlignment(SwingConstants.LEFT);

	        String fname=fontName.getSelectedItem().toString();
	        Font fn=cellAtt[curPane].getFont(table.getSelectedRow(),table.getSelectedColumn());
	        if(fn == null)
				editor.setFont(new Font("TABKural", 0,12));
			else
	        	editor.setFont(new Font(fn.getName(), fn.getStyle(),fn.getSize()));

		    ActionListener cellListener = new ActionListener()
		    {
			 public void actionPerformed(ActionEvent ev)
			   {
				editor.setText(chathu.inputText);
				}
			 };
				editor.addActionListener(cellListener);
				//editor.addKeyListener(new Translitration(editor));
	    	    return editor;
	    }

  }


	/**The method is used to calculate the sum value.
	The given values in the table cell should be selected
	and click the "kootu/Sum menu item" in the calculation menu.
	@param sumval of type double to store the sum value.
	@param length of type integer to get the selected row/column
	numbers.*/
	public void cellValueSum(double sumval,int length)
  	{
  		Row=cur_table.getSelectedRow();
  		Col=cur_table.getSelectedColumn();

  		cur_table.setValueAt(Double.toString(sumval),Row+length,Col);
  		cur_table.repaint();
  		sumval=0;
	}
/*my addition*/

/**The method is used to fire the action event when
the cell is pressed.It checks for arrow key and tab keys.
The keyevent will be fired when the arrow key and tab
keys are used. It calls setInputField() and setInputFont()s
methods to display the selected cell coordinate and the
selected cell font.
@param ke the KeyEvent.*/
public void keyPressed(KeyEvent ke)
{
	int key=ke.getKeyCode();
	if((key>36 && key<41) || key==0 || key==KeyEvent.VK_ENTER) /*check for arrow keys and tab*/
	{
		setInputField();
		setInputFont();
	}
	else
	{
		is_saved=false;
		SSMenu ssm=(SSMenu)getJMenuBar();
		ssm.setProps(false);
		setJMenuBar(ssm);
		repaint();
	}
}

/**The method is used to fire the action event when
the cell is typed.It checks for arrow key and tab keys.
The keyevent will be fired when the arrow key and tab
keys are used. It calls setInputField() and setInputFont()
methods to display the selected cell coordinate and the
selected cell font.
@param ke the KeyEvent.*/
public void keyTyped(KeyEvent ke)
{
	int key=ke.getKeyCode();
	if((key>36 && key<41) || key==0 || key==KeyEvent.VK_ENTER) /*check for arrow keys and tab*/
	{
		setInputField();
		setInputFont();
	}
	else
	{
		is_saved=false;
		SSMenu ssm=(SSMenu)getJMenuBar();
		ssm.setProps(false);
		setJMenuBar(ssm);
		repaint();
	}
}

/**The method is used to fire the action event when
the cell is released.It checks for arrow key and tab keys.
The keyevent will be fired when the arrow key and tab
keys are used. It calls setInputField() and setInputFont()
methods to display the selected cell coordinate and the
selected cell font.
@param ke the KeyEvent.*/
public void keyReleased(KeyEvent ke)
{
	int key=ke.getKeyCode();
	if((key>36 && key<41) || key==0 || key==KeyEvent.VK_ENTER) /*check for arrow keys and tab*/
	{
		setInputField();
		setInputFont();
	}
	else
	{
		is_saved=false;
		SSMenu ssm=(SSMenu)getJMenuBar();
		ssm.setProps(false);
		setJMenuBar(ssm);
		repaint();
	}
}

/*public void setInputField()
{
	int rowVal,colVal;
	if((rowVal=temptable.getSelectedRow())!=-1 && (colVal=temptable.getSelectedColumn())!=-1)
	{
		addrField.setText(rowVal+worksheet[1].headerOnly[colVal+1]+":"+temptable.getValueAt(rowVal,colVal).toString());
	}
}*/
/**The method is used to set the currently selected cell's coordinate
value in the input text field.The row heading value and the column
heading value is combined and displayed.*/
public void setInputField()
{
	try{
	int rowVal=0,colVal=0;
		if(jtp.getTabCount()!=0)
		  {
		  	rowVal=cur_table.getSelectedRow();
		  	colVal=cur_table.getSelectedColumn();

		  	if(rowVal!=-1 && colVal!=-1)
			{
				addrField.setText(rowVal+":"+worksheet[1].headerOnly[colVal+1]);
				Font ff=cellAtt[curPane].getFont(cur_table.getSelectedRow(),cur_table.getSelectedColumn());
				fontName.setSelectedItem(ff.getName());
				fontSize.setSelectedItem(Integer.toString(ff.getSize()));

				jbBold.setSelected(false);
				jbItalic.setSelected(false);
				if(ff.toString().indexOf("bolditalic") != -1)
				{
					jbBold.setSelected(true);
					jbItalic.setSelected(true);
				}
				else if(ff.toString().indexOf("bold") != -1)
					jbBold.setSelected(true);
				else if(ff.toString().indexOf("italic") != -1)
					jbItalic.setSelected(true);

				//jbUnderline.setSelected(false);
			}
		  }
		}
		catch(Exception e1)
			{
				//System.out.println("SETINPUT"+e1);
				//e1.printStackTrace();
			}
			jtp.repaint();
}
	/*my addition ends*/

	/**The method is used to set the input font for the
	currently selected cell of the table.This method will set the
	font of the selected cell.The font will be get selected in the
	font combobox list.*/
    void setInputFont()
    {
			int[] columns = cur_table.getSelectedColumns();
			int[] rows    = cur_table.getSelectedRows();
			if(columns.length==0 && rows.length==0)
			{
			}
			if ((rows == null) || (columns == null)) return;
			if ((rows.length<1)||(columns.length<1)) return;
			int fntstyle=Font.PLAIN;
			if(jbBold.isSelected())
			fntstyle+=Font.BOLD;
			if(jbItalic.isSelected())
			fntstyle+=Font.ITALIC;
			Font font = new Font((String)fontName.getSelectedItem(),	  fntstyle,
							  Integer.parseInt((String)fontSize.getSelectedItem()));

			cellAtt[curPane].setFont(font, rows, columns);
    }

	/**
	* The method to get the tamil font which is bound along with the package.
	* @return Font the tamil font bound with the application
	*/
	public Font getBilingualFont()
	{
		return bilingualFont;
	}

	/**
	* The method to get the Language
	@return String to get the Language
	*/
	public String getLanguage()
	{
		return language;
	}

	/**
	 The method to get the country region
	@return String to get the country
	*/
	public String getCountry()
	{
		return country;
	}

	/**
	* The method to set the language and country.
	@param language to set the language
	@param country to set the country
	*/
	public void setLocale(String language, String country)
	{
		this.language = language;
		this.country = country;
		currentLocale = new Locale(language,country);
		wordBundle = ResourceBundle.getBundle("WordBundle",currentLocale);
	}

	/**The method to set the current locale for the application.
	@return Locale the type of the curent locale settings.*/
	public Locale getLocale()
	{
		return currentLocale;
	}

	/**
	* To get the Tamil equivalent words from the word bundle.
	It is the property file which contains the list of Tamil and
	equivalent English words.
	@return ResourceBundle
	*/
	public ResourceBundle getWordBundle()
	{
		return wordBundle;
	}

	/** The method setUIManager will set the Tamil text
	in the components available in the application.*/
	public void setUIManager()
	{
		UIManager.put("Button.font",bilingualFont);
		UIManager.put("ComboBox.font",bilingualFont);
		UIManager.put("Label.font",bilingualFont);
		UIManager.put("CheckBox.font",bilingualFont);
		UIManager.put("RadioButton.font",bilingualFont);
		UIManager.put("TitledBorder.font",bilingualFont);
		UIManager.put("ToolTip.font",bilingualFont);
		UIManager.put("FileChooser.font",bilingualFont);
	}
//resor ends..

/**The method is used to change the foreground and
back ground color of the selected cell data.It displays the
color chooser dialog box to select the required color.
@param isForeground of type boolean to check the fore ground
or back ground color.*/
private final void changeColor(boolean isForeground)
  {
	JTable colortable=(JTable)al_tables.get(jtp.getSelectedIndex());
    int[] columns = colortable.getSelectedColumns();
    int[] rows    = colortable.getSelectedRows();
    if ((rows == null) || (columns == null)) return;
    if ((rows.length<1)||(columns.length<1)) return;
    Color target    = cellAtt[curPane].getForeground(rows[0], columns[0]);
    Color reference = cellAtt[curPane].getBackground(rows[0], columns[0]);

    for (int i=0;i<rows.length;i++)
    {
      int row = rows[i];
      for (int j=0;j<columns.length;j++)
      {
			int column = columns[j];
			target    = (target    != cellAtt[curPane].getForeground(row, column)) ?
			  null : target;
			reference = (reference != cellAtt[curPane].getBackground(row, column)) ?
			  null : reference;
      }
    }
    String title;
    if (isForeground)
    {
      target    = (target   !=null) ? target    : colortable.getForeground();
      reference = (reference!=null) ? reference : colortable.getBackground();
      title = "Text Color";
    } else
       {
    	  target    = (reference!=null) ? reference : colortable.getBackground();
    	  reference = (target   !=null) ? target    : colortable.getForeground();
    	  title = "Fill Color";
       }
    TextColorChooser chooser =   new TextColorChooser(target, reference, isForeground);
    Color color = chooser.showDialog(this,title);

    if (color != null)
    {
      if (isForeground)
      {
		cellAtt[curPane].setForeground(color, rows, columns);
      }
      else
      {
			cellAtt[curPane].setBackground(color, rows, columns);
       }
      //temptable.clearSelection();
      colortable.revalidate();
      colortable.repaint();
    }
  }

	/**The method is used to set the font style like Bold and Italic
	 of the text.The two buttons are provided to change
	the font style.It set the enable/disable depending upon the
	selected cell's font style.
	@param style of type integer to set for Bold(1), Italic(2) .The default
	of 0 is assigned for plain style of the text.*/
	public void FontStyle(int style)
	{
		int[] columns = cur_table.getSelectedColumns();
		int[] rows    = cur_table.getSelectedRows();
		if(columns.length==0 && rows.length==0)
		{

		}
		if ((rows == null) || (columns == null)) return;
		if ((rows.length<1)||(columns.length<1)) return;
		Font font = null;
		int fntstyle=Font.PLAIN;
		if(jbBold.isSelected())
		fntstyle+=Font.BOLD;
		if(jbItalic.isSelected())
		fntstyle+=Font.ITALIC;

		switch(style)
		{
			case 1:
			{
				font = new Font((String)fontName.getSelectedItem(),fntstyle,
						  Integer.parseInt((String)fontSize.getSelectedItem()));
			}
			break;

			case 2:
			{
				font = new Font((String)fontName.getSelectedItem(),fntstyle,
						  Integer.parseInt((String)fontSize.getSelectedItem()));
			}
			break;

			case 3:
			{
			}
			break;

			default :
			{
				font = new Font((String)fontName.getSelectedItem(),fntstyle,
						  Integer.parseInt((String)fontSize.getSelectedItem()));
			}
		}

		cellAtt[curPane].setFont(font, rows, columns);
		// temptable.clearSelection();
		cur_table.revalidate();
		cur_table.repaint();
	}
	public void newFileCreation()
	{
		Chathurangam newSs=new Chathurangam();
		SSMenu ssm=new SSMenu(this);
		ssm.setProps(false);
		newSs.setJMenuBar(new SSMenu(newSs));
		newSs.show();
		newSs.pack();
		newSs.repaint();
	}
	/**The action performed method is used to fire
	the actions when the components receives the action listener
	@param evt of type ActionEvent*/
	public void actionPerformed(ActionEvent evt)
	{
		 String celSor=new String();
		 String celDest=new String();
		 char celSrc='c';
		 char celDst='c';

		 String command = evt.getActionCommand();
		/*the new button icon is to create the new application window.*/
		if(evt.getSource()==jbNew) // Icon new.gif
		{
			newFileCreation();
			++sheetFile;
			mainSheet++;
		}
		/*The open button icon is used to open the file*/
		if(evt.getSource()==jbOpen)//Icon open.gif
		{
			openFile();

		}//&& end

		/*The save button icon is used to save the file */
		if(evt.getSource()==jbSave)//Icon save.gif
		{
		  if(!isFirstSaved)
			saveAsFile();
		  else
			saveFile();
		} //end of save if

		/*The print button icon is used to print the data through printer.*/
		if(evt.getSource()==jbPrint)//Icon print.gif
		{
				PrinterJob printJob = PrinterJob.getPrinterJob();
				printJob.setPrintable(this);

				if(printJob.printDialog())
				try  {
						printJob.print();
					 }
				catch (PrinterException error)
					{
						error.printStackTrace();
					}
		}

		/*The cut button icon is used to cut the value from the cell.*/
		if(evt.getSource()==jbCut) // Icon cut.gif
		{
			cellValue_cut();
		}

		/*The copy button icon is used to copy the value from the cell.*/
		if(evt.getSource()==jbCopy)//Icon copy.gif
		{
			cellValue();
		}
		/*The paste button icon is used to paste the value from
		cut method and copy method.*/
		if(evt.getSource()==jbPaste)//Icon paste.gif
		{
			String valuePaste=cellVal;
			cellValuePaste();
		}
		/*The sum button icon is used to find the sum
		value for the selected cell values.*/
		if(evt.getSource()==jbSum) // Icon sum.gif
		{
			WorkSheet temp;
			temp  = getWorkSheet();
			temp.buildChart();
			int len=temp.rowSelArray.length;
			sum =temp.sumValue;
			//inputField.setText("Ãì¢´ = "+Double.toString(sum));
			try
			{
				int selRow=cur_table.getSelectedRow();
				int selcol=cur_table.getSelectedColumn();
				int srlen=cur_table.getSelectedRows().length;
				if(cur_table.getSelectedRows().length<=1&&cur_table.getSelectedColumns().length>=1)
				{
					cur_table.setValueAt(Double.toString(sum),selRow, (cur_table.getSelectedColumns().length)+selcol);
					cur_table.setRowSelectionInterval(selRow,(cur_table.getSelectedColumns().length)+selcol);
					//inputField.setText();
				}
				else
				cur_table.setValueAt(Double.toString(sum),selRow+srlen,selcol);
				cur_table.setRowSelectionInterval(selRow,selRow+cur_table.getSelectedRows().length);
				setFormula();
				cur_table.repaint();
				temp.sumValue=0;
			}
			catch(NullPointerException npe)
			{
				//System.out.println("nep");
				wordBundle = this.getWordBundle();
				Object[] options = {wordBundle.getString("ok")};
				String SelectData = wordBundle.getString("SelectData");
				String messageTitle = wordBundle.getString("messageTitle");
				int select = showDialog(this,"SelectData",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}
		/*The chartV button icon is used to draw the vertical bar chart*/
		if(evt.getSource()==jbChartV)//Icon chart.gif
		{
			drawVbar();
		}
		/*The chartH button icon is used to draw the horizontal bar chart*/
		if(evt.getSource()==jbChartH)
		{
			drawHbar();
		}
		/*The chartP button icon is used to draw the pie chart*/
		if(evt.getSource()==jbChartP)
		{
			drawPie();
		}

		/*The action event for the cut item from popup menu.*/
		if (command.equals("ªõì¢´/Cut")) //cut popup menu
		{
			cellValue_cut();
		}
		/*The action event for the copy item from popup menu.*/
		if (command.equals("ïèô¢/Copy")) //copy popup menu
		{
			cellValue();
		}
		/*The action event for the paste item from popup menu.*/
		if (command.equals("åì¢´/Paste")) //paste popup menu
		{
			 String valuePaste=cellVal;
			 cellValuePaste();
		}

		/**Copy formula*/
		if(command.equals("ïèô¢-õ¤î¤º¬ø/CopyFormula"))
		{
				String form_equa=Formul_Equation();

				/*if(cpy_value.startsWith("="))
				{
					int colSor=cur_table.getSelectedColumn();
					celSor=headerNums[colSor];
					celSrc=celSor.charAt(0);
					System.out.println("ccccce lsor  "+celSrc);
					System.out.println("eqeree ee "+form_equa);
				}*/
		}

		/**Paste formula*/
		if(command.equals("åì¢´-õ¤î¤º¬ø/PasteFormula"))
		{
			//cpy_value=Formul_Equation();
			int row=cur_table.getSelectedRow();
			int col=cur_table.getSelectedColumn();
			int colDest=cur_table.getSelectedColumn();
			celDest=headerNums[colDest];
			celDst=celDest.charAt(0);
			int srStr=cpy_value.indexOf("(");
			celSrc=cpy_value.charAt(srStr+1);
			cpy_value=cpy_value.replace(celSrc,celDst);
			cur_table.setValueAt(cpy_value,row,col);
		}

		if(command.equals("åù¢ø¤¬í/Merge Cells"))
		{
				  int[] columns = cur_table.getSelectedColumns();
				  int[] rows    = cur_table.getSelectedRows();
				  cellAtt[curPane].combine(rows,columns);
				  cur_table.clearSelection();
				  cur_table.revalidate();
				  cur_table.repaint();

		}
		if(command.equals("ð¤ó¤/Split Cells"))
		{
				int column = cur_table.getSelectedColumn();
				int row    = cur_table.getSelectedRow();
				cellAtt[curPane].split(row,column);
				cur_table.clearSelection();
				cur_table.revalidate();
				cur_table.repaint();

		}
		/**Clears the content of the selected cell(s)*/
		if(command.equals("àì¢îó¬õ ï¦è¢°è/ClearContent"))
		{
			int[] columns = cur_table.getSelectedColumns();
			int[] rows    = cur_table.getSelectedRows();
			if(columns.length==0 && rows.length==0)
			{
				System.out.println("no row./col selected ..");
			}
			for(int m=0;m<rows.length;m++)
			{
				for(int n=0;n<columns.length;n++)
				{
						cur_table.setValueAt(" ",rows[m],columns[n]);
				}
			}
			cur_table.revalidate();
			cur_table.repaint();
		}
		if(command.equals("Ü¬ùî¢¬î»ñ¢ «îó¢ï¢ªî´/SelectAll"))
		{
			cur_table.setRowSelectionAllowed(true);
			cur_table.setRowSelectionInterval(0,0);
			cur_table.selectAll();
			cur_table.revalidate();
			cur_table.repaint();
		}
		if(command.equals("«îó¢ï¢ªî´î¢îõø¢¬ø ï¦è¢°/DeSelectAll"))
		{
			cur_table.clearSelection();
			cur_table.revalidate();
			cur_table.repaint();
		}

		/*The action event for the insert Row item from popup menu.*/
		if(command.equals("ªê¼°-ï¤óô¢/Insert-Row"))
		{
			int rowlen=cur_table.getSelectedRows().length;
			for(int k=0;k<rowlen;k++)
			handleInsertRow();

		}
		/*The action event for the insert column item from popup menu.*/
		if(command.equals("ªê¼°-ï¤¬ó/Insert-Column"))
		{
			if(cur_table.getSelectedRows().length<=1&&cur_table.getSelectedColumns().length>=1)
			{
				int selCols=cur_table.getSelectedColumns().length;
				for(int k=0;k<selCols;k++)
				handleInsertCol();
			}
		}
		/*The action event for the delete Row item from popup menu.*/
		if(command.equals("ï¦è¢° - ï¤óô¢/Delete-Row"))
		{
			int rowlen=cur_table.getSelectedRows().length;
			for(int k=0;k<rowlen;k++)
			handleDeleteRow();
		}
		/*The action event for the delete  column item from popup menu.*/
		if(command.equals("ï¦è¢° - ï¤¬ó/Delete-Column"))
		{
			if(cur_table.getSelectedRows().length<=1&&cur_table.getSelectedColumns().length>=1)
			{
				int selCols=cur_table.getSelectedColumns().length;
				for(int k=0;k<selCols;k++)
				handleDeleteCol();
			}

		}
		/*The action event for the background color button icon
		to set the back color of the text .*/
		if(evt.getSource()==jbBackClr)
		{
			changeColor(false);
		}
		/*The action event for the foreground color button icon
		to set the forecolor of the text .*/
		if(evt.getSource()==jbForeClr)
		{
			changeColor(true);
		}
		/*The action event for the font style's Bold button icon*/
		if(evt.getSource()==jbBold)
		{
			if(jbBold.isSelected())
			FontStyle(1);
			else
			FontStyle(0);
		}
		/*The action event for the font style's Italic button icon*/
		if(evt.getSource()==jbItalic)
		{
			if(jbItalic.isSelected())
			FontStyle(2);
			else
			FontStyle(0);
		}
		/*The action event for the Underline button icon*/
		/*if(evt.getSource()==jbUnderline)
		{
			if(jbUnderline.isSelected())
			FontStyle(3);
			else
			FontStyle(0);
		}*/


		/*This is used to evaluate formula or expn in the inputfield*/
		if(evt.getSource()==inputField)
		{
			int formValue=0;
			String inpTxt=new String();
			inputText=new String(inputField.getText());
			inputText.trim();
			int eqpos=inputText.indexOf("=");
			int openpos=inputText.indexOf("(");
			if(eqpos==-1 || openpos==-1)
			{
				//System.out.println("invalid bracess ....");
				wordBundle = this.getWordBundle();
				Object[] options = {wordBundle.getString("ok")};
				String InvalidBrace = wordBundle.getString("InvalidBrace");
				String messageTitle = wordBundle.getString("messageTitle");
				int select = showDialog(this,"InvalidBrace",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

			}
			else
			{
			inpTxt=inputText.substring(inputText.indexOf("="),inputText.indexOf("("));

			if(inputText.startsWith("=")==true)
			{
				startWithEqSymb();
			}
			else
			{
			String cell_refer=new String(inputText);
			String colLeter=new String();
			char[] cellRefs;
			int colRefs;

			//FormulaHandling(inputText);

			if(inputText.equals(""))
			{
				inputField.setText(worksheet[curPane].formula[worksheet[curPane].rowSelArray[0]]	[worksheet[curPane].colSelArray[0]]);
			}
			else
			{
				try{
						worksheet[curPane].evaluateFormula(inputText.trim());
						cell_refer=cell_refer.substring(0,2);
						cellRefs=cell_refer.toCharArray();
						int col_pos=headRefs.indexOf(cellRefs[0]);
						String rowval=new String(cellRefs);
						int row_pos=Integer.parseInt(rowval.substring(1,2));
								//cur_table.setValueAt(inputText,row_pos,col_pos);
						jtp.repaint();
					}
					catch(NumberFormatException nfe)
					{
						System.out.println("NumberformatXXEEPP-chathu2");
						wordBundle = this.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String InsuffData = wordBundle.getString("InsuffData");
						String messageTitle = wordBundle.getString("messageTitle");
						int select = showDialog(this,"InsuffData",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

					}
					catch(ArrayIndexOutOfBoundsException aiobe)
					{
						System.out.println("AIOOBEXXXp");
						//aiobe.printStackTrace();
						wordBundle = this.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String OutofRange = wordBundle.getString("OutofRange");
						String messageTitle = wordBundle.getString("messageTitle");
						int select = showDialog(this,"OutofRange",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
					}
					catch(EmptyStackException ese)
					{
						System.out.println("ees ");
					}
					catch(StringIndexOutOfBoundsException sioobe)
					{
						System.out.println("StringIndexOutOfBoundsException-chathu3");
						sioobe.printStackTrace();
						//ee.printStackTrace();
						/*wordBundle = this.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String NoNegativeVals = wordBundle.getString("NoNegativeVals");
						String messageTitle = wordBundle.getString("messageTitle");
						int select = showDialog(this,"NoNegativeVals",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);*/

					}
					catch(Exception ee)
					{
						System.out.println("otherException-chathu3");
					}
			}
			}
		repaint();

		}



		/**SHORT CUT KEYS FOR CUT-COPY-PASTE ACTION		*/
		/* if (evt.getActionCommand().compareTo("Copy")==0)
		      {
		         StringBuffer sbf=new StringBuffer();
		         // Check to ensure we have selected only a contiguous block of
		         // cells
		         int numcols=cur_table.getSelectedColumnCount();
		         int numrows=cur_table.getSelectedRowCount();
		         int[] rowsselected=cur_table.getSelectedRows();
		         int[] colsselected=cur_table.getSelectedColumns();
		         if (!((numrows-1==rowsselected[rowsselected.length-1]-rowsselected[0] &&
		                numrows==rowsselected.length) &&
		(numcols-1==colsselected[colsselected.length-1]-colsselected[0] &&
		                numcols==colsselected.length)))
		         {
		            JOptionPane.showMessageDialog(null, "Invalid Copy Selection",
		                                          "Invalid Copy Selection",
		                                          JOptionPane.ERROR_MESSAGE);
		            return;
		         }
		         for (int i=0;i<numrows;i++)
		         {
		            for (int j=0;j<numcols;j++)
		            {
		sbf.append(cur_table.getValueAt(rowsselected[i],colsselected[j]));
		               if (j<numcols-1) sbf.append("\t");
		            }
		            sbf.append("\n");
		         }
		         stsel  = new StringSelection(sbf.toString());
		         system = Toolkit.getDefaultToolkit().getSystemClipboard();
		         system.setContents(stsel,stsel);
		      }
		      if (evt.getActionCommand().compareTo("Paste")==0)
		      {
		          //System.out.println("Trying to Paste");
		          int startRow=(cur_table.getSelectedRows())[0];
		          int startCol=(cur_table.getSelectedColumns())[0];
		          try
		          {
		             String trstring= (String)(system.getContents(this).getTransferData(DataFlavor.stringFlavor));
		             //System.out.println("String is:"+trstring);
		             StringTokenizer st1=new StringTokenizer(trstring,"\n");
		             for(int i=0;st1.hasMoreTokens();i++)
		             {
		                rowstring=st1.nextToken();
		                StringTokenizer st2=new StringTokenizer(rowstring,"\t");
		                for(int j=0;st2.hasMoreTokens();j++)
		                {
		                   value=(String)st2.nextToken();
		                   if (startRow+i< cur_table.getRowCount()  &&
		                       startCol+j< cur_table.getColumnCount())
		                       cur_table.setValueAt(value,startRow+i,startCol+j);
		                   System.out.println("Putting "+ value+"atrow="+startRow+i+"column="+startCol+j);
		               }
		            }
		         }
		         catch(Exception ex){ex.printStackTrace();}
      }*/
		/**ENDS HERE*/
	}
	}//end of action performed


	public void startWithEqSymb()
	{
		String opr=new String();
		int colon_pos=inputText.indexOf(":");
		if(colon_pos!=-1)// if - 1
			{
				int opBrkt_pos=inputText.indexOf("(");
				int clBrkt_pos=inputText.indexOf(")");
				operator=inputText.substring(0,opBrkt_pos);
			 	opr=operator.substring(1,opBrkt_pos);
				if(opr.equals("Ãì¢´"))
				{
						formula_range=inputText.substring(opBrkt_pos+1,clBrkt_pos);
						int mid_pos=formula_range.indexOf(":");
						int startIndex=Integer.parseInt(formula_range.substring(1,mid_pos));
						int endIndex=Integer.parseInt(formula_range.substring(mid_pos+2));
						String rangeFormula=new String();
						for(int k=startIndex;k<=endIndex;k++)
						{
							rangeFormula=rangeFormula+formula_range.substring(0,1)+Integer.toString(k)+"+";
						}
						rangeFormula=rangeFormula.substring(0,rangeFormula.length()-1);
						rangeFormula=operator+"("+rangeFormula+")";
						String ResCell=FindResultCell();
						ResCell=ResCell+rangeFormula;
						worksheet[curPane].evaluateFormula(ResCell);
				}
			else
			{
					if(opr.equals("êó£êó¤"))
					{
						String aveStr=new String();
						int st_pos=inputText.indexOf("(");
						int end_pos=inputText.indexOf(")");
						aveStr=inputText.substring(st_pos+1,end_pos);
						FindAverage(aveStr);
					}
					else
					{
								if(opr.equals("°¬øï¢îð¢ðì¢êñ¢"))
								{
										String minStr=new String();
										int st_pos=inputText.indexOf("(");
										int end_pos=inputText.indexOf(")");
										minStr=inputText.substring(st_pos+1,end_pos);
										FindMinimum(minStr);
								}
								else
								{

									if(opr.equals("Üî¤èð¢ðì¢êñ¢"))
									{
											String maxStr=new String();
											int st_pos=inputText.indexOf("(");
											int end_pos=inputText.indexOf(")");
											maxStr=inputText.substring(st_pos+1,end_pos);
											FindMaximum(maxStr);

									}
									else
									{
										String ResCell=FindResultCell();
										ResCell=ResCell+inputText;
										try
										{
												worksheet[curPane].evaluateFormula(ResCell);
										}
										catch(NumberFormatException nfe)
										{
											System.out.println("NumbFormat XXcep-chathu1");
											nfe.printStackTrace();
										}
										catch(Exception ee)
										{
											System.out.println("GGen Xception");
										}
									}
							}
					}
			}
					}//end of if -1
	}
	public String Formul_Equation()
	{
		int row=cur_table.getSelectedRow();
		int col=cur_table.getSelectedColumn();
		cpy_value =cur_table.getValueAt(row,col).toString();
		return cpy_value;
	}
	/**Finding the Result Cell where the result of sum to be displayed*/
	public String FindResultCell()
	{
		 int selRow=cur_table.getSelectedRow();
		 int selCol=cur_table.getSelectedColumn();
		 String cellAddr=al_cellRefs.get(selCol)+Integer.toString(selRow);
		 return cellAddr;
	}
	/**Finding the average value for the given expression..*/
	public void FindAverage(String aveStr)
	{
		int mid_pos=aveStr.indexOf(":");
		int col_pos1=al_cellRefs.indexOf(aveStr.substring(0,1));
		int row_pos1=Integer.parseInt(aveStr.substring(1,mid_pos));
		//int col_pos2=al_cellRefs.indexOf(aveStr.substring(0,1));
		int row_pos2=Integer.parseInt(aveStr.substring(mid_pos+2));
		double totalValue=0;
		int m;
		for(m=row_pos1;m<=row_pos2;m++)
		totalValue=totalValue+Double.parseDouble(cur_table.getValueAt(m,col_pos1).toString());
		int count=(row_pos2-row_pos1)+1;
		double aveRage=totalValue/count;
		cur_table.setValueAt(Double.toString(aveRage),cur_table.getSelectedRow(),cur_table.getSelectedColumn());
	}

	public void FindMaximum(String maxStr)
	{
		int mid_pos=maxStr.indexOf(":");
		int col_pos1=al_cellRefs.indexOf(maxStr.substring(0,1));
		int row_pos1=Integer.parseInt(maxStr.substring(1,mid_pos));
		int row_pos2=Integer.parseInt(maxStr.substring(mid_pos+2));
		int cntSels=(row_pos2-row_pos1)+1;
		double maxValue=0;
		int m;
		String valuesMax=new String();
		for(m=row_pos1;m<=row_pos2;m++)
		{
			valuesMax=valuesMax+cur_table.getValueAt(m,col_pos1).toString()+"@";
			//cellValues[c]=Double.parseDouble(cur_table.getValueAt(m,col_pos1).toString());
		}
		StringTokenizer cellVals=new StringTokenizer(valuesMax,"@");

		double[] cellValues=new double[cntSels];
		while(cellVals.hasMoreTokens())
		{
			for(int k=0;k<cellValues.length;k++)
			cellValues[k]=Double.parseDouble(cellVals.nextToken());

		}
		maxValue=cellValues[0];
		for(int b=1;b<cellValues.length;b++)
		{
			if(maxValue<cellValues[b])
			{

				maxValue=cellValues[b];

			}
		}
		cur_table.setValueAt(Double.toString(maxValue),cur_table.getSelectedRow(),cur_table.getSelectedColumn());


	}
	/**Finding the Minimum value amonth given from the expression..*/
	public  void FindMinimum(String minStr)
	{
				int mid_pos=minStr.indexOf(":");
				int col_pos1=al_cellRefs.indexOf(minStr.substring(0,1));
				int row_pos1=Integer.parseInt(minStr.substring(1,mid_pos));
				int row_pos2=Integer.parseInt(minStr.substring(mid_pos+2));
				int cntSels=(row_pos2-row_pos1)+1;
				double minValue=0;
				int m;
				String valuesMin=new String();
				for(m=row_pos1;m<=row_pos2;m++)
				{
					valuesMin=valuesMin+cur_table.getValueAt(m,col_pos1).toString()+"@";
					//cellValues[c]=Double.parseDouble(cur_table.getValueAt(m,col_pos1).toString());
				}
				StringTokenizer cellVals=new StringTokenizer(valuesMin,"@");

				double[] cellValues=new double[cntSels];
				while(cellVals.hasMoreTokens())
				{
					for(int k=0;k<cellValues.length;k++)
					cellValues[k]=Double.parseDouble(cellVals.nextToken());

				}
				minValue=cellValues[0];
				for(int b=1;b<cellValues.length;b++)
				{
					if(minValue>cellValues[b])
					{

						minValue=cellValues[b];

					}
				}
				cur_table.setValueAt(Double.toString(minValue),cur_table.getSelectedRow(),cur_table.getSelectedColumn());

	}
	public void setFormula()
	{
		int selRowLen=cur_table.getSelectedRows().length;
		int selColLen=cur_table.getSelectedColumns().length;
		Object cell1=al_cellRefs.get(cur_table.getSelectedColumn());
		cell1.toString();
		//cell1+cur_table.getSelectedRow();
		int sRow=cur_table.getSelectedRow();
		for(int n=0;n<cur_table.getSelectedRows().length;n++)
		{
			//System.out.println("cell refs "+cell1+(sRow+n));
		}

	}

		/**The method is used to draw the pie chart. It gets the
		current worksheet and displays the custom chart details
		like chart title and axis to draw it.*/
		public void drawPie()
		{
			WorkSheet ws;
			ws=this.getWorkSheet();
			if(ws.isBarOk())
				new ChartCustom(0,this,ws);
			else
			{
				wordBundle = this.getWordBundle();
				Object[] options = {wordBundle.getString("yes")};
				String InvalidSelection = wordBundle.getString("InvalidSelection");
				String messageTitle = wordBundle.getString("messageTitle");
				int select = showDialog(this,"InvalidSelection",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
			}
		}

	/**The method is used to draw the horizontal bar chart. It gets the
	current worksheet and displays the custom chart details
	like chart title and axis to draw it.*/
	public void drawHbar()
	{
		WorkSheet ws;
		ws=this.getWorkSheet();
		if(ws.isBarOk())
		{

			new ChartCustom(2,this,ws);

		}
		else
		{
			wordBundle = this.getWordBundle();
			Object[] options = {wordBundle.getString("yes")};
			String InvalidSelection = wordBundle.getString("InvalidSelection");
			String messageTitle = wordBundle.getString("messageTitle");
			int select = showDialog(this,"InvalidSelection",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
		}
	}

	/**The method is used to draw the vertical bar chart. It gets the
	current worksheet and displays the custom chart details
	like chart title and axis to draw it.*/
	public void drawVbar()
	{
		WorkSheet ws;
		ws=this.getWorkSheet();
		if(ws.isBarOk())
		{
			new ChartCustom(1,this,ws);

		}
		else
		{		wordBundle = this.getWordBundle();
				Object[] options = {wordBundle.getString("ok")};
				String InvalidSelection = wordBundle.getString("InvalidSelection");
				String messageTitle = wordBundle.getString("messageTitle");
				int select = showDialog(this,"InvalidSelection",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

		}
	}
	/**The method is used to check the worksheet is available among
	the 15 sheets to be displayed for file properties.
	@param i of type integer the no. of sheet.
	@return boolean to check the sheet's availablity.*/
	public boolean checkSheet(int i)
	{
		return isSheet[i];
	}

	/**The main method to start the application. It invokes
	the Chathurangam constructor and sets the menubar for that.
	@param a of type string array as an argument.
	*/
	public static void main(String a[])
	{
		Chathurangam ss=new Chathurangam();
		SSMenu ssm=new SSMenu(ss);
		ssm.setProps(false);
		ss.setJMenuBar(ssm);
		ss.show();
		ss.pack();
		ss.repaint();
	}

	/**The method is  used to get the current worksheet.
	This will return the current worksheet among the 15 sheets.
	@return WorkSheet it is the returned current worksheet.*/
	 public WorkSheet getWorkSheet()
	 {
		return worksheet[curPane];
	 }
}

/**This inner class is used to get the selected area from the table.
This class implements the ListSelectionListener interface.
The valueChanged method is implemented in this class.
This will calculate the selected row and columns from the
table using worksheet class.*/
class SelectionDebugger implements ListSelectionListener
	{
		ListSelectionModel model;
		int[] selection;
		int[] result;
		WorkSheet worksheet;
		String type;
		/**The constructor is used to get the
		selected area from the table. It uses worksheet class for that.
		@param type of type String object to check the row column identification.
		@param worksheet is the object of WorkSheet class
		@param lsm the list selection model to get the max selection index.*/
		public SelectionDebugger(String type,WorkSheet worksheet,ListSelectionModel lsm)
		{

			model=lsm;
			this.result=result;
			this.worksheet=worksheet;
			this.type=new String(type);
		}

	 /**This fn gets the starting row and ending row,starting col and ending
	col of the selected area in the form of arrays
	@param lse the ListSelectionEvent*/
	    public void valueChanged(ListSelectionEvent lse)
		{
			if(!lse.getValueIsAdjusting())
			{
				StringBuffer buf=new StringBuffer();
				selection=getSelectedIndices(model.getMinSelectionIndex(),
							model.getMaxSelectionIndex());


				if(type.equals("row"))
				{
					worksheet.rowSelArray=new int[selection.length];
					for(int i=0;i<selection.length;i++)
						worksheet.rowSelArray[i]=selection[i];
				}
				if(type.equals("col"))
				{
					worksheet.colSelArray=new int[selection.length];
					for(int i=0;i<selection.length;i++)
						worksheet.colSelArray[i]=selection[i];
				}

			}
		}

		/**The method to get the get selected indices from the table.
		It returns the array of type integer to store the index values.
		@param start of type integer to store the staring index.
		@param stop of type integer to store the ending index.
		@return int[] to store the returned array of indices.
		*/
		protected int[] getSelectedIndices(int start,int stop)
		{
			if((start==-1)||(stop==-1))
			{
				return new int[0];
			}

			int guesses[]=new int[stop-start+1];
			int index=0;
			for(int i=start;i<=stop;i++)
			{
				if(model.isSelectedIndex(i))
				{
					guesses[index++]=i;
				}
			}
			int realthing[]=new int[index];
			System.arraycopy(guesses,0,realthing,0,index);
			return realthing;
		}
	}
/**
* @(#)SSMenu.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

//This class is the menu that is used in the Chathurangam.It provides the
//functionalities of the menu : new,open,close,save

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.event.ListSelectionEvent.*;
import java.lang.*;
import java.awt.datatransfer.*;
import java.awt.print.*;
import java.awt.geom.*;
import javax.swing.undo.*;
import java.awt.Dimension;
import javax.swing.text.html.*;
import java.net.*;
import java.math.*;
import java.text.*;

/** To set the menubar for the application. It combined with
the main class Chathurangam.It has total  6 menus like file menu,
Tools menu, calculation menu,Insert menu,Chart menu and
help menu along with corresponding action events.It extends the
JMenuBar and implements the action listener.*/
public class SSMenu extends JMenuBar implements ActionListener //,Printable
{
	/**Store the file menu items in the string array.*/
	String[] fileItems=new String[] {"  ¹î¤ò/New","  î¤ø/Open"," Í´/Close","  «êñ¤/Save","        SaveAs","  Üê¢ê¤´/Print","õ¤ó¤î£÷¢ ðí¢¹è÷¢/Properties","   ªõ÷¤«òÁ/Exit"}; //new,open,save,print,exit
	/**Store the formulae items used for calculation menu.*/
	String[] formulae=new String[] {"Ãì¢´/Sum","âí¢í¤è¢¬è/Count","Üî¤èð¢ðì¢êñ¢/Maximum","°¬øï¢îðì¢êñ¢/Minimum","êó£êó¤/Average"};
														//menu items for calculations--- sum,count,maximum,minimum,average,if
	/**Store the chart items used for pie chart.*/
	String[] charts=new String[]{"õì¢ìñ¢/Pie"};//,"ªêõ¢õèñ¢"};pie(circle),bar(rectangle)
	/**Store the chart items as sub menu for bar charts*/
	String[] subcharts=new String[] {"è¤¬ìñì¢ìñ¢/Horizontal","àòó¢ñì¢ìñ¢/Vertical"};//horizontal,vertical
	/**Store the insert menu items in the insert menu.*/
	String[] insertItems=new String[]{"õ¤ó¤ð¢¹/Sheet","ªê¼°-ï¤óô¢/Insert-Row","ªê¼°-ï¤¬ó/Insert-Column","ï¦è¢° - ï¤óô¢/Delete-Row","ï¦è¢° - ï¤¬ó/Delete-Column"};//,"ï¤óô¢/Row","ï¤¬ó/Column"};//worksheet,row,column
	/**Store the edit items used for Tools menu.*/
	String[] editItems=new String[]{"ªõì¢´/Cut","ïèô¢/Copy","åì¢´/Paste","Ü´î¢¶ «î´ / FindNext","õ¤ó¤ð¢¹-ï¦è¢°/Delete-sheet","ï¤óô¢ àòóñ¢/RowHeight","ï¤¬ó Üèôñ¢/ColumnWidth","Ü÷õ£è¢èñ¢/Zoom"};//copy,cut,paste
	/**Store the help menu items used for help menu.*/
	String[] helps=new String[]{"ê¶óé¢èñ¢ ðø¢ø¤../AboutChathurangam","ê¶óé¢èñ¢ àîõ¤/Chathurangam-Help"}; //help menu(about spreadsheet,spreadsheet help)
	/**To save the array values to draw charts*/
	ArrayList vals=new ArrayList();

	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam ss;

	/**Obejct of the class <code>barChartH</code>*/
	barChartH bchartH;

	/**Obejct of the class <code>PieTableChartPopup</code>*/
	PieTableChartPopup pcpopup;
	/**Array of tables used for worksheets.*/
	JTable[] table;
	/**Temp table object used for sheet insert with default sheets.*/
	JTable table_insert;

	/**Sets the menu item for the insert menu */
	JMenuItem item0;
	/**Sets the menu item for the insert menu */
	JMenuItem item1;
	/**Sets the menu item for the insert menu */
	JMenuItem item2;
	/**Sets the menu item for the insert menu */
	JMenuItem item3;
	/**Sets the menu item for the insert menu */
	JMenuItem item4;
	/**Sets the menu item for the calculation menu */
	JMenuItem cal_item0;
	/**Sets the menu item for the calculation menu */
	JMenuItem cal_item1;
	/**Sets the menu item for the calculation menu */
	JMenuItem cal_item2;
	/**Sets the menu item for the calculation menu */
	JMenuItem cal_item3;
	/**Sets the menu item for the calculation menu */
	JMenuItem cal_item4;
	/**Sets the menu item for the Tools menu --undo*/
	JMenuItem eMenu0,eMenu01;
	/**Sets the menu item for the Tools menu */
	JMenuItem eMenu1;
	/**Sets the menu item for the Tools menu */
	JMenuItem eMenu2;
	/**Sets the menu item for the Tools menu */
	JMenuItem eMenu3;
	/**Sets the menu item for the Tools menu */
	JMenuItem eMenu4;
	/**Sets the menu item for the Tools menu */
	JMenuItem eMenu5;
	/**Sets the menu item for the Tools menu */
	JMenuItem eMenu6;
	/**Sets the menu item for the Tools menu */
	JMenuItem eMenu7;
	/**Sets the menu item for the Tools menu */
	JMenuItem eMenu8;
	/**Sets the menu item close for the file menu */
	JMenuItem itemClose;

	/**Sets the button for print in the file menu of the application.*/
	JMenuItem itemPrint;
	/**Sets the button for save in the  file menu of the application.*/
	JMenuItem itemSave;
	/**Sets the button for saveAs in the  file menu of the application.*/
	JMenuItem itemSaveAs;
	/**Sets the button for charts in the file menuof the application.*/
	JMenuItem chartsItem;
	/**Sets the button for horizontal bar  in the  file menu of the application.*/
	JMenuItem subchartItem0;
	/**Sets the button for vertical bar  in the  file menu of the application.*/
	JMenuItem subchartItem1;

	/**Sets the font bound with the application package. */
	Font tempFont;

	/**Array of worksheets to be used in the application.*/
	WorkSheet[] worksheet;

	/**Store the selected row in the integer array. */
	int[] rowSelArray;
	/**Store the selected column in the integer array. */
	int[] colSelArray;
	/**Store the sheet order number along with sheet title.*/
	int iSheet=4;

	/**Store the row index as integer value.*/
	int Row;
	/**Store the column index as integer value.*/
	int Col;
	/**To handle recent files list in the linked list.*/
	int ll_size;
	/**To store the file name as string object.*/
	String[] filename;

	/**The word bundle is used to list the tamil and english equivalent words
	for setting menu items,buttons and labels in Tamil.*/
	ResourceBundle wordBundle;
	/**Sets the font object bound with the application package.*/
	public static Font currFont;
	/**Store th language string for setting local language.*/
	public static String language = "ta";
	/**Store th country string for setting local country.*/
	public static String country = "IN";
	/**Sets the locale parameter language and country for the application */
	public static Locale currentLocale;
	/**Temp menu item is used for recent files list to display in the
	file menu.*/
	JMenuItem[] tempmenuitem;
	/**Sets the property menu item in the file menu.*/
	JMenuItem itemProp;
	/**Sets the help menu item in the help menu to display aboutSPS.*/
	JMenuItem helpItem0;
	/**Sets the help menu item in the help menu to display the help document.*/
	JMenuItem helpItem1;
	JTable cur_table;
	//ArrayList al_delTable=new ArrayList();
	int selIndex;
	String delSheetTitle=new String();
	int sheetNum=0;
	static int sheetId=14;
	/**Constructor for the menu bar setting with the application.
	It sets the menus and corresponding action events. There
	are 6 main menus and one sub menu set with main class.
	If main class constructor invokes the menu bar also to be
	set for that. Recent files display in the file menu also handled
	in this constructor.
	@param ss object of the main class Chathurangam.*/
	public SSMenu(Chathurangam ss)
	{
		this.ss=ss;
		iSheet=ss.setIsheet();

		try{
			InputStream fontInputStream =
				getClass().getResourceAsStream("TABKural.TTF");
				tempFont = Font.createFont(Font.TRUETYPE_FONT,
					fontInputStream);
			currFont = tempFont.deriveFont(0, 12);
			}
			catch(Exception e2)
			{
				System.out.println("line 136"+e2);
				e2.printStackTrace();
			}
		ss.setUIManager();

		currentLocale = new Locale(language,country);
		ss.setLocale(language, country);

		RolloverMenu fileMenu=new RolloverMenu("«è£ð¢¹/File"); // file
		JMenuItem itemNew=new JMenuItem(fileItems[0],null);//new
		itemNew.setIcon(ImagesLocator.getImage("new.gif"));
		itemNew.addActionListener(this);
		itemNew.setFont(currFont);
		itemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke
							(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));

		itemNew.setMnemonic(KeyEvent.VK_N);
		fileMenu.add(itemNew);

		JMenuItem itemOpen=new JMenuItem(fileItems[1],null);//open
		itemOpen.setIcon(ImagesLocator.getImage("open.gif"));
		itemOpen.addActionListener(this);
		itemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke
							(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));

		itemOpen.setMnemonic(KeyEvent.VK_O);
		itemOpen.setFont(currFont);
		fileMenu.add(itemOpen);

		itemClose=new JMenuItem(fileItems[2],null);//close
		//ImageIcon imgOpen=new ImageIcon("open.gif");
		itemClose.setIcon(ImagesLocator.getImage("Close.gif"));
		itemClose.addActionListener(this);
		itemClose.setFont(currFont);
		fileMenu.add(itemClose);

		itemSave=new JMenuItem(fileItems[3],null);//save
		//ImageIcon imgSave=new ImageIcon("save.gif");
		itemSave.setIcon(ImagesLocator.getImage("save.gif"));
		itemSave.addActionListener(this);
		itemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke
							(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));

		itemSave.setMnemonic(KeyEvent.VK_S);
		itemSave.setFont(currFont);
		fileMenu.add(itemSave);

		itemSaveAs=new JMenuItem(fileItems[4],null);
		itemSaveAs.addActionListener(this);
		itemSaveAs.setFont(currFont);
		fileMenu.add(itemSaveAs);

		itemPrint=new JMenuItem(fileItems[5],null);
		//ImageIcon imgPrint=new ImageIcon("print.gif");
		itemPrint.setIcon(ImagesLocator.getImage("print.gif"));
		itemPrint.addActionListener(this);
		itemPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke
							(java.awt.event.KeyEvent.VK_P, java.awt.Event.CTRL_MASK));

		itemPrint.setMnemonic(KeyEvent.VK_P);
		itemPrint.setFont(currFont);
		fileMenu.add(itemPrint);

		fileMenu.addSeparator();

		itemProp=new JMenuItem(fileItems[6],null);
		itemProp.addActionListener(this);
		itemProp.setFont(currFont);
		fileMenu.add(itemProp);

		fileMenu.addSeparator();

		ll_size=ss.ll_recentfiles.size();
		tempmenuitem=new JMenuItem[ll_size];
		filename=new String[ll_size];
		for(int i=0;i<ll_size;i++)
		{
			filename[i]=ss.ll_recentfiles.get(i).toString();
			tempmenuitem[i] = new JMenuItem(filename[i]);
			fileMenu.add(tempmenuitem[i]);
		//	System.out.println(filename[i]+"---filename array ");
			tempmenuitem[i].setActionCommand(filename[i]);
			tempmenuitem[i].addActionListener(this);

		}// end of for
		fileMenu.addSeparator();

		JMenuItem itemExit=new JMenuItem(fileItems[7],null);
		itemExit.addActionListener(this);
		itemExit.setFont(currFont);
		fileMenu.add(itemExit);
		fileMenu.setFont(currFont);
		fileMenu.setMnemonic(KeyEvent.VK_F);

		add(fileMenu);
		fileMenu.repaint();

		RolloverMenu	editMenu=new RolloverMenu("ªî£°ð¢¹/Tools"); // Group

		eMenu1=new JMenuItem(editItems[0]);//cut
		eMenu1.addActionListener(this);
		eMenu1.setFont(currFont);
		eMenu1.setIcon(ImagesLocator.getImage("cut.gif"));

		//eMenu1.setAccelerator(KeyEvent.);
		eMenu1.setAccelerator(javax.swing.KeyStroke.getKeyStroke
					(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK));

		eMenu1.setMnemonic(KeyEvent.VK_X);
		editMenu.add(eMenu1);

		eMenu2=new JMenuItem(editItems[1]);//copy
		eMenu2.addActionListener(this);
		eMenu2.setFont(currFont);
		eMenu2.setIcon(ImagesLocator.getImage("copy.gif"));

		eMenu2.setAccelerator(javax.swing.KeyStroke.getKeyStroke
					(java.awt.event.KeyEvent.VK_C, java.awt.Event.CTRL_MASK));

		eMenu2.setMnemonic(KeyEvent.VK_C);
		editMenu.add(eMenu2);

		eMenu3=new JMenuItem(editItems[2]);//paste
		eMenu3.addActionListener(this);
		eMenu3.setFont(currFont);
		eMenu3.setIcon(ImagesLocator.getImage("paste.gif"));

		eMenu3.setAccelerator(javax.swing.KeyStroke.getKeyStroke
					(java.awt.event.KeyEvent.VK_V, java.awt.Event.CTRL_MASK));

		eMenu3.setMnemonic(KeyEvent.VK_V);
		editMenu.add(eMenu3);

		editMenu.addSeparator();

		eMenu4=new JMenuItem(editItems[3]);//find/replace
		eMenu4.addActionListener(this);
		eMenu4.setFont(currFont);
		//eMenu1.setAccelerator(KeyEvent.);
		eMenu4.setAccelerator(javax.swing.KeyStroke.getKeyStroke
					(java.awt.event.KeyEvent.VK_F, java.awt.Event.CTRL_MASK));

		eMenu4.setMnemonic(KeyEvent.VK_F);
		editMenu.add(eMenu4);

		eMenu5=new JMenuItem(editItems[4]);//delete sheet
		eMenu5.addActionListener(this);
		eMenu5.setFont(currFont);
		//eMenu1.setAccelerator(KeyEvent.);
		eMenu5.setAccelerator(javax.swing.KeyStroke.getKeyStroke
					(java.awt.event.KeyEvent.VK_D, java.awt.Event.CTRL_MASK));

		eMenu5.setMnemonic(KeyEvent.VK_D);
		editMenu.add(eMenu5);

		editMenu.addSeparator();

		eMenu0=new JMenuItem("ê¤ø¢ø¬ø âô¢¬ô / CellBorder");//cell border
		eMenu0.addActionListener(this);
		eMenu0.setFont(currFont);
		//editMenu.add(eMenu0);

		eMenu6=new JMenuItem(editItems[5],null);//row height
		eMenu6.addActionListener(this);
		eMenu6.setFont(currFont);
		editMenu.add(eMenu6);

		eMenu7=new JMenuItem(editItems[6],null);//column width
		eMenu7.addActionListener(this);
		eMenu7.setFont(currFont);
		editMenu.add(eMenu7);

		eMenu8=new JMenuItem(editItems[7],null);//zooming
		eMenu8.addActionListener(this);
		eMenu8.setFont(currFont);
		editMenu.add(eMenu8);

		editMenu.setFont(currFont);
		editMenu.setMnemonic(KeyEvent.VK_T);
		add(editMenu);
		editMenu.repaint();

		RolloverMenu calMenu=new RolloverMenu("èíè¢è¤´/Calculation"); //calculate

		cal_item0=new JMenuItem(formulae[0],null);
		cal_item0.addActionListener(this);
		cal_item0.setFont(currFont);
		calMenu.add(cal_item0);

		cal_item1=new JMenuItem(formulae[1],null);
		cal_item1.addActionListener(this);
		cal_item1.setFont(currFont);
		calMenu.add(cal_item1);

		cal_item2=new JMenuItem(formulae[2],null);
		cal_item2.addActionListener(this);
		cal_item2.setFont(currFont);
		calMenu.add(cal_item2);

		cal_item3=new JMenuItem(formulae[3],null);
		cal_item3.addActionListener(this);
		cal_item3.setFont(currFont);
		calMenu.add(cal_item3);

		cal_item4=new JMenuItem(formulae[4],null);
		cal_item4.addActionListener(this);
		cal_item4.setFont(currFont);
		calMenu.add(cal_item4);

		calMenu.setFont(currFont);
		calMenu.setMnemonic(KeyEvent.VK_C);
		add(calMenu);
		calMenu.repaint();

		RolloverMenu insertMenu=new RolloverMenu("ªê¼°/Insert"); // Insert

		item0=new JMenuItem(insertItems[0],null);
		item0.addActionListener(this);
		item0.setFont(currFont);
		//item.setActionCommand(i.toString());
		insertMenu.add(item0);

		item1=new JMenuItem(insertItems[1],null);
		item1.addActionListener(this);
		item1.setFont(currFont);
		insertMenu.add(item1);

		item2=new JMenuItem(insertItems[2],null);
		item2.addActionListener(this);
		item2.setFont(currFont);
		insertMenu.add(item2);

		item3=new JMenuItem(insertItems[3],null);
		item3.addActionListener(this);
		item3.setFont(currFont);
		insertMenu.add(item3);

		item4=new JMenuItem(insertItems[4],null);
		item4.addActionListener(this);
		item4.setFont(currFont);
		insertMenu.add(item4);

		insertMenu.setFont(currFont);
		insertMenu.setMnemonic(KeyEvent.VK_I);
		add(insertMenu);
		insertMenu.repaint();

		RolloverMenu chartMenu=new RolloverMenu("õ¬óðìñ¢/Chart");//chart
		chartsItem=new JMenuItem(charts[0],null);
		chartsItem.addActionListener(this);
		chartsItem.setFont(currFont);
		chartMenu.add(chartsItem);
		//chartMenu.add(charts[0]);

		chartMenu.setFont(currFont);
		chartMenu.setMnemonic(KeyEvent.VK_R);
		add(chartMenu);
		chartMenu.repaint();
		RolloverMenu subMenu =new RolloverMenu("ªêõ¢õèñ¢/Bar");//bar-rectangle

		subchartItem0=new JMenuItem(subcharts[0],null);
		subchartItem0.addActionListener(this);
		subchartItem0.setFont(currFont);
		subMenu.add(subchartItem0);

		subchartItem1=new JMenuItem(subcharts[1],null);
		subchartItem1.addActionListener(this);
		subchartItem1.setFont(currFont);
		subMenu.add(subchartItem1);

		subMenu.setFont(currFont);
		chartMenu.add(subMenu);

		subMenu.repaint();

		RolloverMenu helpMenu=new RolloverMenu("àîõ¤/Help");

		helpItem0=new JMenuItem(helps[0],null);
		helpItem0.addActionListener(this);
		helpItem0.setIcon(ImagesLocator.getImage("about.gif"));
		helpItem0.setFont(currFont);
		helpMenu.add(helpItem0);

		helpItem1=new JMenuItem(helps[1],null);
		helpItem1.addActionListener(this);
		helpItem1.setIcon(ImagesLocator.getImage("about.gif"));
		helpItem1.setFont(currFont);
		helpItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke
							(java.awt.event.KeyEvent.VK_F1, 0));

		helpItem1.setMnemonic(KeyEvent.VK_F1);
		helpMenu.add(helpItem1);

		helpMenu.setFont(currFont);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		add(helpMenu);
		helpMenu.repaint();
		repaint();
	}
	/**Sets the property menu items visiblity mode.
	if file not saved the property item is disabled.
	@param b of type boolean to check the visible mode.*/
	public void setProps(boolean b)
	{
		itemProp.setEnabled(b);
	}

	/**The method will fire when the menu item
	gets the action listeners
	@param evt object of ActionEvent*/
	public void actionPerformed(ActionEvent evt)
	{

		//File menu
		// --- new

			if(evt.getActionCommand()=="  ¹î¤ò/New" )//new
			{
				/*Chathurangam newSs=new Chathurangam();
				SSMenu ssm=new SSMenu(newSs);
				ssm.setProps(false);
				newSs.setJMenuBar(new SSMenu(newSs));
				newSs.show();
				newSs.pack();
				newSs.repaint();*/
				ss.newFileCreation();
				++ss.sheetFile;
				ss.mainSheet++;
			}

		  //---open
		    if(evt.getActionCommand()=="  î¤ø/Open")//open file
			{
				ss.openFile();
			}//&& end of open

		  //----close
		  	if(evt.getSource()==itemClose)
		 	 {
     			  ss.saveFile();
				  enableInterFace();
				  ss.jtp.removeAll();
		  	 }
		  //---save

		    if(evt.getActionCommand()=="  «êñ¤/Save")//save file
		  		{
		  	 		if(!ss.isFirstSaved)
		  	 			ss.saveAsFile();
		  	 		else
		  	 			ss.saveFile();
				} //end of save if


			//---Save As


			//if(evt.getActionCommand()=="       SaveAs")//save as file
			if(evt.getSource()==itemSaveAs)
			{
				 ss.saveAsFile();
			} //end of save as


		  //---print
		    if(evt.getActionCommand()=="  Üê¢ê¤´/Print")//print
		  	   {

					PrinterJob printJob = PrinterJob.getPrinterJob();
					printJob.setPrintable(ss);

					if(printJob.printDialog())
						try
						{
							 printJob.print();
						}
						catch (PrinterException error)
						{
							error.printStackTrace();
						}
					/*table_insert=(JTable)ss.al_tables.get(ss.curPane);
					PrinterJob pj=PrinterJob.getPrinterJob();
					pj.setPrintable(ss);
					pj.printDialog();
				//java.awt.print.PrinterJob.getPrinterJob().printDialog();
					try
					{
						pj.print();
					}
						catch (Exception PrintException)
						{
							PrintException.printStackTrace();
						}*/
				}

		  //---properties


		    if(evt.getActionCommand()=="õ¤ó¤î£÷¢ ðí¢¹è÷¢/Properties")//properties
		  		{
		  			JFrame propFrame=new spProperties(ss);
		  			propFrame.show();
				}

		 //----recent files

		    if(evt.getActionCommand()=="   ªõ÷¤«òÁ/Exit")//exit
		  		{

		  			/*my addition */
		  			try
					{
						if(ss.is_saved)
						{
							ss.addinResentFile();
							//System.exit(0);
							ss.mainSheet--;
							if(ss.mainSheet==0)
								System.exit(0);
							else
							{
								setVisible(false);
								ss.setVisible(false);
							}

						}
						/*String s="<html><body font face=TABKural color=black>õ¤ó¤î£¬÷  «êñ¤è¢è «õí¢´ñ£ ?</body></html>";
						ss.select=JOptionPane.showConfirmDialog(ss.fr,	s,"*** Sheet Save ***",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);*/

						wordBundle = ss.getWordBundle();
						Object[] options = {wordBundle.getString("yes"),wordBundle.getString("no"),wordBundle.getString("cancel")};

						String notSavedWar = wordBundle.getString("notSavedWar");
						String messageTitle = wordBundle.getString("messageTitle");
						int select = ss.showDialog(ss,"notSavedWar",
												JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
												options, 0);

						if(select==JOptionPane.YES_OPTION)
						{

							if(!ss.isFirstSaved)
								ss.saveAsFile();
							else
								ss.saveFile();

							//System.exit(0);
							setVisible(false);
							ss.setVisible(false);
						}
						if(select==JOptionPane.NO_OPTION)
						{
							if(!ss.isFirstSaved)
							ss.addinResentFile();

							//System.exit(0);
							setVisible(false);
							ss.setVisible(false);
						}
						if(select==JOptionPane.CANCEL_OPTION)
						{
							//System.out.println("CANCEL3333"+ss.select);
							setVisible(true);
							ss.setVisible(true);

						}

					}
					catch(Exception ec)
					{
						System.out.println("482"+ec);
						ec.printStackTrace();
					}
					/*my addition ends*/
				}

		//Edit menu
		  // --- copy
		    if(evt.getActionCommand()=="ïèô¢/Copy")//copy
		  	 {
				ss.cellValue();
			 }

		  //--- cut
		    if(evt.getActionCommand()== "ªõì¢´/Cut")//cut
		  	 {
				ss.cellValue_cut();
		     }

		  //---paste
	        if(evt.getActionCommand()== "åì¢´/Paste")//paste
		  	 {
				String valuePaste=ss.cellVal;
		  	 	//System.out.println("paste "+valuePaste);
		  	 	ss.cellValuePaste();
			 }
			// find
			if(evt.getSource()==eMenu4)
			 {
				JFrame find=new FindFrame(ss);
				find.show();
			 }

		  //---delete sheet
		    if(evt.getActionCommand()=="õ¤ó¤ð¢¹-ï¦è¢°/Delete-sheet")//delete sheet
		  		{
					//JTable delTable;
					wordBundle = ss.getWordBundle();
					Object[] optionsmain = {wordBundle.getString("yes"),wordBundle.getString("no")};
					String DeleteSheet = wordBundle.getString("DeleteSheet");
					String messageTitleMain = wordBundle.getString("messageTitle");
					int selectmain = ss.showDialog(ss,"DeleteSheet",	JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,	optionsmain, 0);
					if(selectmain==JOptionPane.YES_OPTION)
					{
							selIndex=ss.jtp.getSelectedIndex();
							int tabcnt=ss.jtp.getTabCount();

							if(tabcnt<=1)
							{
								wordBundle = ss.getWordBundle();
								Object[] options = {wordBundle.getString("ok")};
								String DelSheet = wordBundle.getString("DelSheet");
								String messageTitle = wordBundle.getString("messageTitle");
								int select = ss.showDialog(ss,"DelSheet",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
								return;
							}
							if(selIndex<0)
							{
								eMenu5.setEnabled(false);
								wordBundle = ss.getWordBundle();
								Object[] options = {wordBundle.getString("ok")};
								String NoSheetFound = wordBundle.getString("NoSheetFound");
								String messageTitle = wordBundle.getString("messageTitle");
								int select = ss.showDialog(ss,"NoSheetFound",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
								return;
							}
							delSheetTitle=ss.jtp.getTitleAt(selIndex);
							//al_delTable.add((JTable)ss.al_tables.remove(selIndex));
							ss.jtp.removeTabAt(selIndex);
							ss.tab_count--;
							iSheet--;
							ss.setSheet(selIndex+1,false);
							iSheet=ss.setIsheet();
							ss.is_saved=false;
							ss.isSheet[selIndex]=false;
					}
					if(selectmain==JOptionPane.NO_OPTION)
					{
						return;
					}
				}

		 //calculations
		 //---sum

			if(evt.getActionCommand()=="Ãì¢´/Sum") //sum
				{
					WorkSheet temp;
					temp  = ss.getWorkSheet();
					temp.buildChart();
					double sum =temp.sumValue;
					//ss.inputField.setText("Ãì¢´ = "+Double.toString(sum));
					//cellValueSum(sum);
					try
					{
						int selRow=ss.cur_table.getSelectedRow();
						int selcol=ss.cur_table.getSelectedColumn();
						int srlen=ss.cur_table.getSelectedRows().length;
						if(ss.cur_table.getSelectedRows().length<=1&&ss.cur_table.getSelectedColumns().length>=1)
							ss.cur_table.setValueAt(Double.toString(sum),selRow, (ss.cur_table.getSelectedColumns().length)+selcol);
						else
						ss.cur_table.setValueAt(Double.toString(sum),selRow+srlen,selcol);
						ss.cur_table.setRowSelectionInterval(selRow,selRow+ss.cur_table.getSelectedRows().length);
						ss.cur_table.repaint();
						sum=0;
					}
					catch(NullPointerException npe)
					{
						wordBundle = ss.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String SelectData = wordBundle.getString("SelectData");
						String messageTitle = wordBundle.getString("messageTitle");
						int select =ss.showDialog(ss,"SelectData",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
					}
					catch(Exception ee)
					{
						ee.printStackTrace();
					}

				}
		 //---count
			if(evt.getActionCommand()=="âí¢í¤è¢¬è/Count")//count
				{
					WorkSheet countSheet;
					countSheet=ss.getWorkSheet();
					//countSheet.buildChart();
					int count=countSheet.rowSelArray.length*countSheet.colSelArray.length;//countSheet.count;
					ss.inputField.setText("âí¢í¤è¢¬è = "+Integer.toString(count));
				}

		 //---maximum
			if(evt.getActionCommand()=="Üî¤èð¢ðì¢êñ¢/Maximum")//max
				{
					WorkSheet calSheet;
					calSheet=ss.getWorkSheet();
					ss.inputField.setText("");
					calSheet.minMax();
					double MAX=calSheet.MAX;
					ss.inputField.setText("Üî¤èðì¢êñ¢ = "+Double.toString(MAX));
					MAX=0;
				}
		 //---minimum
			if(evt.getActionCommand()=="°¬øï¢îðì¢êñ¢/Minimum")//min
				{
					WorkSheet calSheet;
					calSheet=ss.getWorkSheet();
					ss.inputField.setText("");
					calSheet.minMax();
					double MIN=calSheet.MIN;
					ss.inputField.setText("°¬øï¢îðì¢êñ¢ = "+Double.toString(MIN));
					MIN=0;
				}
		 //---average
			if(evt.getActionCommand()=="êó£êó¤/Average")//averagae
				{
					WorkSheet calSheet;
					calSheet=ss.getWorkSheet();
					ss.inputField.setText("");
					calSheet.buildChart();
					double ave = calSheet.average;
					String dble_num=NumberFormat.getInstance().format(ave);
					//ss.inputField.setText("êó£êó¤ = "+Double.toString(ave));
					ss.inputField.setText("êó£êó¤ = "+dble_num);
				}

		//Insert Menu
		   //---sheet


			if(evt.getSource()==item0)//insert worksheet
			{
				int shtCnt=ss.jtp.getTabCount();
				if(iSheet>=14)//shtCnt>15)
				{
					if(shtCnt>=14)
					{
							wordBundle = ss.getWordBundle();
							Object[] options = {wordBundle.getString("ok")};
							String noSheetsInserted = wordBundle.getString("noSheetsInserted");
							String messageTitle = wordBundle.getString("messageTitle");
							int select = ss.showDialog(ss,"noSheetsInserted",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
							return;
					}
					else
					{
						sheetId++;
						sheetNum=Integer.parseInt(delSheetTitle.substring(7));
						ss.setSheet(sheetNum,true);
						ss.insertSheet(sheetNum,sheetId);
					}

				}
				else
				{
					iSheet++;
					ss.setSheet(iSheet,true);
					ss.insertSheet(iSheet);

				}


			}
			//---rowins

			if(evt.getSource()==item1)//insert row
				{
					//ss.handleInsertRow();
					try
					{
					int rowlen=ss.cur_table.getSelectedRows().length;
						for(int k=0;k<rowlen;k++)
								ss.handleInsertRow();
					}
					catch(NullPointerException npe)
					{
						wordBundle = ss.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String SelectRow = wordBundle.getString("SelectRow");
						String messageTitle = wordBundle.getString("messageTitle");
						int select = ss.showDialog(ss,"SelectRow",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

					}
					catch(Exception ee)
					{

					}

				}
			//---columnins

			if(evt.getSource()==item2)//insert column
				{
					try{

					if(ss.cur_table.getSelectedRows().length<=1&&ss.cur_table.getSelectedColumns().length>=1)
						{
								int selCols=ss.cur_table.getSelectedColumns().length;
								for(int k=0;k<selCols;k++)
									ss.handleInsertCol();
						}
					}
					catch(NullPointerException npe)
					{
						wordBundle = ss.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String SelectColumn = wordBundle.getString("SelectColumn");
						String messageTitle = wordBundle.getString("messageTitle");
						int select = ss.showDialog(ss,"SelectColumn",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

					}
					catch(Exception ee)
					{

					}
					//ss.handleInsertCol();
				}
			//--rowdel

			if(evt.getSource()==item3)//del row
				{
					try
					{
						int rowlen=ss.cur_table.getSelectedRows().length;
							for(int k=0;k<rowlen;k++)
								ss.handleDeleteRow();
					}
					catch(NullPointerException npe)
					{
						wordBundle = ss.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String SelectRow = wordBundle.getString("SelectRow");
						String messageTitle = wordBundle.getString("messageTitle");
						int select = ss.showDialog(ss,"SelectRow",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
					}
					catch(Exception ee)		{	}
					//ss.handleDeleteRow();
				}
			//---coldel
			if(evt.getSource()==item4)// delete column
				{
					try
					{
						if(ss.cur_table.getSelectedRows().length<=1&&ss.cur_table.getSelectedColumns().length>=1)
						{
							int selCols=ss.cur_table.getSelectedColumns().length;
								for(int k=0;k<selCols;k++)
										ss.handleDeleteCol();
						}
						//ss.handleDeleteCol();
					}
					catch(NullPointerException npe)
					{
						wordBundle = ss.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String SelectColumn = wordBundle.getString("SelectColumn");
						String messageTitle = wordBundle.getString("messageTitle");
						int select = ss.showDialog(ss,"SelectColumn",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
					}
					catch(Exception ee)	{		}
				}
			/*if(evt.getSource()==eMenu0)
				{
					System.out.println("cell border");
					//ss.setCellBorder(true);

				}*/
			if(evt.getSource()==eMenu6)//row height
			 {
				String as=evt.getActionCommand();
				cur_table=(JTable)ss.al_tables.get(ss.jtp.getSelectedIndex());
				int[] rows=cur_table.getSelectedRows();
				 if(rows.length==0)
				  {
				  }
				if (rows == null) return;
			  	if (rows.length<1) return;

				JFrame sf=new SizeFrame(rows,ss,cur_table,ss.cur_tableHeader);
				sf.show();
			 }

			if(evt.getSource()==eMenu7)//column width
			 {
			 	try{
						int selcol=ss.cur_table.getSelectedColumn();
						JFrame colwid=new ColumnWidth(ss,selcol);
						colwid.show();
					}
					catch(NullPointerException npe)
					{
						wordBundle = ss.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String SelectCell = wordBundle.getString("SelectCell");
						String messageTitle = wordBundle.getString("messageTitle");
						int select =ss.showDialog(ss,"SelectCell",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

					}
					catch(Exception eee)
					{
						System.out.println("");//ExceptionSSMenu "+eee);
					}
			 }

			if(evt.getSource()==eMenu8)//zooming
			 {
					JFrame zmFrame=new ZoomFrame(ss);
					zmFrame.show();
			 }

		 //Chart
		 	//---pie
			if(evt.getActionCommand()=="õì¢ìñ¢/Pie")//pie chart
				{
					ss.drawPie();
				}

		 	//---bar
		 		//***horizontal
			if(evt.getActionCommand()=="è¤¬ìñì¢ìñ¢/Horizontal")//bar chart horizontal
					{
						ss.drawHbar();
					}
		 		//***vertical
			if(evt.getActionCommand()=="àòó¢ñì¢ìñ¢/Vertical")//bar chart vertical
					{
						ss.drawVbar();
					}

		 //help
		 	//---About sps
		 	//if(evt.getActionCommand()=="õ¤ó¤ î£÷¢ ðø¢ø¤../AboutSps")
					if(evt.getSource()==helpItem0)
					{
						JWindow help=new AboutSps();
						help.show();
					}

		 	//---sps help
		 	//if(evt.getActionCommand()=="õ¤ó¤ î£÷¢ àîõ¤/Sps-Help")
					if(evt.getSource()==helpItem1)
					{
						JFrame helpDetails=new Helps();
						helpDetails.show();
					}
				 out: for(int i=0;i<filename.length;i++)
					 {

						if(evt.getActionCommand().equals(filename[i]))
						 {
							ss.FileOpenRFiles(filename[i]);
							break out;
						 }
					 }// end of for


	}//&& end of action performed

	public void enableInterFace()
	{
			  eMenu1.setEnabled(false);
			  eMenu2.setEnabled(false);
			  eMenu3.setEnabled(false);
			  eMenu4.setEnabled(false);
			  eMenu5.setEnabled(false);
			  eMenu6.setEnabled(false);
			  eMenu7.setEnabled(false);
			  eMenu8.setEnabled(false);

			  item0.setEnabled(false);
			  item1.setEnabled(false);
			  item2.setEnabled(false);
			  item3.setEnabled(false);
			  item4.setEnabled(false);

			  cal_item0.setEnabled(false);
			  cal_item1.setEnabled(false);
			  cal_item2.setEnabled(false);
			  cal_item3.setEnabled(false);
			  cal_item4.setEnabled(false);

			  chartsItem.setEnabled(false);
			  subchartItem0.setEnabled(false);
			  subchartItem1.setEnabled(false);
			  itemSave.setEnabled(false);
			  itemSaveAs.setEnabled(false);
			  itemPrint.setEnabled(false);
			  itemProp.setEnabled(false);
			  itemClose.setEnabled(false);
			  this.setVisible(false);

			  ss.jbSave.setEnabled(false);
			  ss.jbCut.setEnabled(false);
			  ss.jbCopy.setEnabled(false);
			  ss.jbPaste.setEnabled(false);
			  ss.jbPrint.setEnabled(false);
			  ss.jbChartP.setEnabled(false);
			  ss.jbChartH.setEnabled(false);
			  ss.jbChartV.setEnabled(false);
			  ss.jbBold.setEnabled(false);
			  ss.jbItalic.setEnabled(false);
			  ss.jbSum.setEnabled(false);

			  ss.jbBackClr.setEnabled(false);
			  ss.jbForeClr.setEnabled(false);
			  ss.fontName.setEnabled(false);
			  ss.fontSize.setEnabled(false);
			  //setJMenuBar(ss);
			  ss.setJMenuBar(new SSMenu(ss));
	}


/**To calculate the sum value from the
selected row and column values.
@param sumval of type integer the result value after summation.
*/
public void cellValueSum(int sumval)
	{

		Row=ss.cur_table.getSelectedRow();
		Col=ss.cur_table.getSelectedColumn();

		ss.cur_table.setValueAt(Integer.toString(sumval),Row,Col);
		ss.cur_table.repaint();
		sumval=0;
	}

}// && end of class SS Menu
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
/**Inner class is used to increase the row height of
the worksheet. The selectd row will be identified and
the row height can be increased as we require.
The text filed is displayed to enter the row height value.*/
class SizeFrame extends JFrame implements ActionListener
{
	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam main;

	/**To set the label and text field in the window.*/
	JPanel panel=new JPanel(new GridLayout());
	/**To set the buttons(ok and cancel) in the window.*/
	JPanel panel1=new JPanel();
	/**Sets the heading  for row height entry.*/
	JLabel jl_head;
	/**Used for entering row height for the table.*/
	JTextField jt_rowht=new JTextField(3);
	/**Used for ok button to increase the row height.*/
	JButton jb_ok;
	/**Used for cancel button to increase the row height.*/
	JButton jb_cancel;
	/**The table to which row height to be increased.*/
	JTable table;
	/**The table header used in the row height increasing table.*/
	JTable headtable;
	/**Store the row height value from the text field.*/
	int h;
	/**Store the selected row index from the table.*/
	int[] selRow;
	/**Store the row height value after formatting the height value.*/
	int rowHt;
	ResourceBundle wordBundle;

	/**Constructor is invoked when the row height is to be increased.
	The text field willl be displayed to enter the row height.
	@param r of type integer to set the row index.
	@param main object of main class Chathurangam.
	@param table object of JTable
	@param tablehd object of Table header of type Table.*/
	public SizeFrame(int[] rows,Chathurangam main,JTable table,JTable tablehd)
	{
		this.main=main;
		this.table=table;
		this.headtable=tablehd;
		this.selRow=rows;

		setSize(200,120);
		setLocation(210,140);
		setTitle("Row Height");
		setResizable(false);
		jl_head=new JLabel(main.getWordBundle().getString("RowHeight"));
		jb_ok=new JButton(main.getWordBundle().getString("ok"));
		jb_cancel=new JButton(main.getWordBundle().getString("cancel"));
		jb_ok.addActionListener(this);
		jb_cancel.addActionListener(this);

		h=table.getRowHeight();
		jt_rowht.setText(Integer.toString(h));

		panel.add(jl_head);
		panel.add(jt_rowht);
		panel1.add(jb_ok);
		panel1.add(jb_cancel);

		getContentPane().add(panel,"North");
		getContentPane().add(panel1,"South");

		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == 27)//Esc
				{
					setVisible(false);
				}
				//if(ke.getKeyCode() == 10 && findButton.isEnabled())//Enter
				//{
				//	find();
				//}
			}
		});
	}
	/**Fire action events when the ok and cancel button
	gets the action listeners. Ok button will change the
	row height and the cancel button will cancel the
	row height change.
	@param ae the ActionEvent.*/
	public void actionPerformed(ActionEvent ae)
	{
		Hashtable temp = new Hashtable();
		if(ae.getSource()==jb_ok)
		{
			rowHt=Integer.parseInt(jt_rowht.getText().trim());
			if(rowHt>=0 && rowHt<=400)
			{
					for(int r=0;r<selRow.length;r++)
					{
						main.cur_table.setRowHeight(selRow[r],rowHt);
						main.cur_tableHeader.setRowHeight(selRow[r],rowHt);

					/**for Saving the Row Height----->*/
					temp.put(new Integer(selRow[r]),new Integer(rowHt));
					main.ht_sheet.put(new Integer(main.jtp.getSelectedIndex()), temp);
					}
					/**---------------------->*/
					main.cur_table.repaint();
					main.cur_table.revalidate();
					setVisible(false);
			 }
			 else
			 {
				 wordBundle = main.getWordBundle();
				 Object[] options = {wordBundle.getString("ok")};
				 String InvalidRowHgt = wordBundle.getString("InvalidRowHgt");
				 String messageTitle = wordBundle.getString("messageTitle");
				 int select = main.showDialog(main,"InvalidRowHgt",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

				 show();
			 }
		}
		if(ae.getSource()==jb_cancel)
		{
			setVisible(false);
		}
	}
}


/**Inner class to set the total no. of rows for the table as
worksheet. double type is used for total no. of rows.*/
class arrValue
{
	double[] selValues=new double[50];//total no_of rows
}


/**Displays the helpdocument to handle the tool.
Both in Tamil and English the document will be displayed.
HyperlinkListener is provided to handle hyperlinks.*/
class Helps extends JFrame implements HyperlinkListener
{
	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();
	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");
	/**Sets the html document with help details for spreadsheet*/
	JEditorPane pane;
	/**To provide the hyperlink in the document.*/
	HTMLEditorKit hekFile = new HTMLEditorKit();
	/**Sets the font for the component.*/
	//Font f=new Font("TABKural",0,12);
	/**To store the file  used for html document.*/
	String str;
	/**Used to get the file path of html document.*/
	StringBuffer sb;
	/**Constructor is used to invoke the help document
	from the help menu.Stringbuffer is used to get the
	file path of the help document.*/
	public Helps()
	{
		setSize(750,500);
		setLocation(100,140);
		//setSize(250,550);
		//setLocation(540,20);
		setIconImage(img);
		setTitle("Chathuranam - Help");


//to resolve the path
		sb=new StringBuffer("file:").append(new File("helpindex.htm").getAbsolutePath());
		str=sb.toString();

		pane = new JEditorPane();
		pane.setFont(new Font("TABKural",0,12));
//		hekFile.setFont(new Font("TABKural",0,12));
		/**To set the editor pane with html document for help menu.*/
		JScrollPane jsp=new JScrollPane(pane);
		pane.addHyperlinkListener(this);
		getContentPane().add(jsp,BorderLayout.CENTER);
		pane.setContentType("text/htm");
		pane.setEditable(false);
		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == 27)//Esc
				{
					setVisible(false);
				}
				//if(ke.getKeyCode() == 10 && findButton.isEnabled())//Enter
				//{
				//	find();
				//}
			}
		});

		try
		{	/**URL object is used to set the html document
			in the editor pane.*/
			URL url =new URL(str);
			pane.setEditorKit( hekFile );
			pane.setPage(url);
		}
		 catch (Throwable t)
		 {
		 	System.out.println("line 762");
		 }

	}


	/**Provide hyperlink in the help document.
	@he object of HyperlinkEvent.*/
	public void hyperlinkUpdate(HyperlinkEvent he)
	{
		try
		{
			if (he.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
			{
				 pane.setPage(he.getURL());

			}
		}
		catch(Exception e)
		{
			System.out.println(" e at hereee"+e);
		}
	}
}
/**
* @(#)spProperties.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.Color.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import java.net.*;
import java.io.*;

/**Displays the file properties for the  spreadsheet.
It contains file path(location), size of the file and type.
The file creation date, last modified date and file access date
also displayed. File summary and the contents are listed in the
file properties.
*/
public class spProperties extends JFrame
{
	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();
	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");

	/**Sets the font for the component*/
	Font f=new Font("TABKural",0,12);

	StringTokenizer st_mDate,st_aDate;
	String strDatabaseName=new String();

	String strHtmFile = new String("file:\\database\\" +"dbhelp.htm");

	JTabbedPane jtb_prop_pane=new JTabbedPane();

	JEditorPane jed_pane;
	JTextArea jt_conts;
	JScrollPane js_pane;

	//JLabel jl_home=new JLabel(ImagesLocator.getImage("dba.gif"));
	JLabel jl_modDate;

	JFrame fr=new JFrame();

	JPanel jp_paneGen=new JPanel(new GridLayout(10,1));
	JPanel jp_paneSum=new JPanel(new GridLayout(10,2));
	JPanel jp_paneStat=new JPanel(new GridLayout(8,1));
	JPanel jp_paneCont=new JPanel();

	int sTab=0,select,fileSize=0;
	int kbytes=0;


	String temp;

	String cDate,mDate,aDate; // createDate , modified Date, accessed Date
	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam mapp;


public spProperties(Chathurangam app)
{
	mapp=app;
	//System.out.println("hello");
	setSize(500,450);
	setLocation(150,100);
	setIconImage(img);
	setTitle("Spreadsheet - Properties");

	jtb_prop_pane.add("ªð£¶",jp_paneGen);
	jtb_prop_pane.add("¹÷¢÷¤ò¤òô¢",jp_paneSum);
	jtb_prop_pane.add("ªî£°ð¢¹",jp_paneStat);
	jtb_prop_pane.add("à÷¢÷ìè¢èñ¢",jp_paneCont);
	jtb_prop_pane.setFont(f);
	jtb_prop_pane.setPreferredSize(new Dimension(200,450));

	String file =app.getFile();//the name of a
	String loc=new String();
	File fi=new File(file);
	try
	{
	loc=fi.getCanonicalPath();
	//System.out.println("location "+loc);
	}
	catch(Exception et)
	{
		//et.printStackTrace();
	}
	try
	{
		FileInputStream in = new FileInputStream(file);
		fileSize=in.available();
		kbytes=fileSize/1024;
	//	System.out.println("kilobytes "+kbytes);
	//	System.out.println("Size of " + file + " (in bytes) is " + in.available());
		}
	catch(Exception e)
	{
		//e.printStackTrace();
	}
	// Date & Month Modification - english to tamil &&&&&&&&&&&&&&


	cDate=created();
	//cDate=cDate.substring(0,20)+cDate.substring(30,34);
	String crDate=dayMonthConvert(cDate);

	mDate=modified();
	//mDate=mDate.substring(0,20)+mDate.substring(30,34);
	String moDate=dayMonthConvert(mDate);

	aDate=accessed();
	//aDate=aDate.substring(0,20)+aDate.substring(30,34);
	String acDate=dayMonthConvert(aDate);

	JLabel jl_dbname=new JLabel();//ImagesLocator.getImage("dblogo.gif"));
	JLabel jl_type=new JLabel("õ¬è            îñ¤ö¢ õ¤ó¤î£÷¢");
	jl_type.setFont(f);
	jl_type.setForeground(Color.black);

	JLabel jl_loc=new JLabel("Þìñ¢            "+loc);
	jl_loc.setFont(f);
	jl_loc.setForeground(Color.black);

	JLabel jl_size=new JLabel("Ü÷¾          "+fileSize+"(in bytes)  "+kbytes+" KB");
	//jl_dbname.setFont(f);
	jl_size.setForeground(Color.black);
	jl_size.setFont(f);
	jp_paneGen.add(jl_dbname);

	JLabel jl_created=new JLabel("à¼õ£è¢èñ¢            "+crDate);
	jl_created.setForeground(Color.black);
	jl_created.setFont(f);

	JLabel jl_modified=new JLabel("î¤¼î¢îñ¢                "+moDate);
	jl_modified.setForeground(Color.black);
	jl_modified.setFont(f);

	JLabel jl_accessed=new JLabel("î¤øð¢¹                   "+acDate);
	jl_accessed.setForeground(Color.black);
	jl_accessed.setFont(f);

	jp_paneGen.add(new JSeparator());
	jp_paneGen.add(jl_type);
	jp_paneGen.add(jl_loc);
	jp_paneGen.add(jl_size);

	jp_paneGen.add(new JSeparator());

	jp_paneGen.add(jl_created);
	jp_paneGen.add(jl_modified);
	jp_paneGen.add(jl_accessed);

	jp_paneGen.add(new JSeparator());

//&&&&&&&&&&&---------------Summary pane--------Start

	JLabel jl_title=new JLabel("          î¬ôð¢¹");
	jl_title.setFont(f);

	JLabel jl_author=new JLabel("         Ýê¤ó¤òó¢");
	jl_author.setFont(f);

	JLabel jl_comp=new JLabel("          ï¤Áõùñ¢");
	jl_comp.setFont(f);

	JLabel jl_subj=new JLabel("          ªð£¼÷¢");
	jl_subj.setFont(f);

	JLabel jl_manager=new JLabel("         «ñô£÷ó¢");
	jl_manager.setFont(f);
	Font tFont=new Font("TABKural",0,12);

	JTextField jt_title=new JTextField(app.getFile());
	jt_title.setEditable(false);
	JTextField jt_author=new JTextField("ªî£ö¤ô¢¸ì¢ðî¢ î¦ó¢¾ õ÷ ¬ñòñ¢ - îñ¤ö¢");
	jt_author.setFont(tFont);
	jt_author.setEditable(false);

	JTextField jt_comp=new JTextField("Üí¢í£ ðô¢è¬ôè¢ èöèñ¢");
	jt_comp.setFont(tFont);
jt_comp.setEditable(false);

	JTextField jt_subj=new JTextField("îñ¤ö¢ õ¤ó¤î£÷¢");
	jt_subj.setFont(f);
	jt_subj.setEditable(false);

	JTextField jt_manager=new JTextField("å¼é¢è¤¬íð¢ð£÷ó¢");
	jt_manager.setFont(tFont);
	jt_manager.setEditable(false);

	jp_paneSum.add(jl_title);
	jp_paneSum.add(jt_title);
	jp_paneSum.add(jl_author);
	jp_paneSum.add(jt_author);
	jp_paneSum.add(jl_comp);
	jp_paneSum.add(jt_comp);
	jp_paneSum.add(jl_subj);
	jp_paneSum.add(jt_subj);
	jp_paneSum.add(jl_manager);
	jp_paneSum.add(jt_manager);
	jp_paneSum.add(new JSeparator());

//&&&&&&&&&&&---------------Summary pane--------End
//&&&&&&&&&&&---------------Statstics pane--------Start
	JLabel jl_crDat=new JLabel("à¼õ£è¢èñ¢                  " + crDate);
	jl_crDat.setFont(f);
	jl_crDat.setForeground(Color.black);

	JLabel jl_modDat=new JLabel("î¤¼î¢îñ¢                    "+moDate);
	jl_modDat.setFont(f);
	jl_modDat.setForeground(Color.black);
	JLabel jl_accDat=new JLabel("î¤øð¢¹                          "+acDate);
	jl_accDat.setFont(f);
	jl_accDat.setForeground(Color.black);

	jp_paneStat.add(jl_crDat);
	jp_paneStat.add(jl_modDat);
	jp_paneStat.add(jl_accDat);
	jp_paneStat.add(new JSeparator());


//&&&&&&&&&&&---------------Statstics pane--------End

//&&&&&&&&&&&---------------Content tab--------Start

	jt_conts=new JTextArea(15,15);
	jt_conts.setFont(f);
	jp_paneCont.add(jt_conts,BorderLayout.CENTER);


	//System.out.println("data source name - "+strDatabaseName);

	jed_pane = new JEditorPane();
	JScrollPane jsp=new JScrollPane(jed_pane);
	jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	jsp.setPreferredSize(new Dimension(250,400));
	//pane.addHyperlinkListener(this);
	getContentPane().add(jsp,BorderLayout.CENTER);
	jed_pane.setContentType("text/htm");
	jed_pane.setEditable(false);
	int tc=app.jtp.getTabCount();
	jt_conts.append("îñ¤ö¢ õ¤ó¤î£÷¢ \n\n");
	jt_conts.setEditable(false);
	for(int i=1;i<15;i++)
	{
		if(mapp.checkSheet(i))
			jt_conts.append("\t õ¤ó¤ð¢¹  "+i+"\n");
	}
	//System.out.println(" TAb counts "+tc);
	try
	{
		URL url =new URL(strHtmFile);
		//jed_pane.setEditorKit(hekFile );
		jed_pane.setPage(url);
	}
	 catch (Throwable t)
	 {
	 //t.printStackTrace();
	 }

	getContentPane().add(jtb_prop_pane);

	} // end of constructor SubWindow
	public String created()
	{
		File filecrea=new File("tha.gif");
		long lmodcr=filecrea.lastModified();
		Date crDte=new Date(lmodcr);
		String created_Date=crDte.toString();
		return created_Date;

			//return mapp.createDate.toString();
	}
		public String accessed()
		{
			Date cur=new Date();
			String curDate=cur.toString();
			return curDate;
		}
		public String modified()
		{
			Date nowM = new Date();
			String date=nowM.toString();
			File fileMod=new File(mapp.getFile());
			long lmod=fileMod.lastModified();
			Date modDate=new Date(lmod);
			String modified_Date=modDate.toString();
			return modified_Date;
	    }
	// day month convertion to tamil font method...
	public String dayMonthConvert(String tempDate)
	{
		String temMonth;
		String tempDay;
		String dayMonth=tempDate.substring(8);

		tempDay=tempDate.substring(0,3);
		temMonth=tempDate.substring(4,7);

			if(tempDay.startsWith("Mon"))
			{
					tempDay="î¤é¢è÷¢";
			}
			if(tempDay.startsWith("Tue"))
			{
					tempDay="ªêõ¢õ£ò¢";
			}
			if(tempDay.startsWith("Wed"))
			{
					tempDay="¹îù¢";
			}
			if(tempDay.startsWith("Thu"))
			{
					tempDay="õ¤ò£öù¢";
			}
			if(tempDay.startsWith("Fri"))
			{
					tempDay="ªõ÷¢÷¤";
			}
			if(tempDay.startsWith("Sat"))
			{
					tempDay="êù¤";
			}
			if(tempDay.startsWith("Sun"))
			{
					tempDay="ë£ò¤Á";
			}
		// for month convertion follows ...

		   if(temMonth.startsWith("Jan"))
			{
				temMonth="üùõó¤¢";
			}
			if(temMonth.startsWith("Feb"))
			{
				temMonth="ð¤ð¢óõó¤¢";
			}
			if(temMonth.startsWith("Mar"))
			{
				temMonth="ñ£ó¢ê¢";
			}
			if(temMonth.startsWith("Apr"))
			{
				temMonth="ãð¢óô¢¢";
			}
			if(temMonth.startsWith("May"))
			{
				temMonth="«ñ";
			}
			if(temMonth.startsWith("Jun"))
			{
				temMonth="ü¨ù¢";
			}
			if(temMonth.startsWith("Jul"))
			{
				temMonth="ü¨¬ô";
			}
			if(temMonth.startsWith("Aug"))
			{
				temMonth="Ýèú¢´";
			}
			if(temMonth.startsWith("Sep"))
			{
				temMonth="ªêð¢ìñ¢ðó¢¢";
			}
			if(temMonth.startsWith("Oct"))
			{
				temMonth="Üè¢«ì£ðó¢";
			}
			if(temMonth.startsWith("Nov"))
			{
				temMonth="ïõñ¢ðó¢¢";
			}
			if(temMonth.startsWith("Dec"))
			{
				temMonth="®êñ¢ðó¢";
			}
			dayMonth=tempDay+"  "+temMonth+"   "+dayMonth;

		return dayMonth;
	} // dayMonthConvert method ends here.

}

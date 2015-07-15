/**
* @(#)PropFrame.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.lang.*;
import java.awt.event.ComponentListener;
import java.awt.image.*;
import java.awt.Image;

/**To display the property of the file. It extends the JFrame.
It displays the details about the file path, file size and the file
type. The file creation date, last modified date and access date.
It also displays the file summary and contents of the file.
*/
public class PropFrame extends JFrame
{
	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();
	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");

	/**Sets the font for the components used for worksheet.*/
	Font tabFont=new Font("TABKural",0,12);

	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam mapp;

	/**Sets the worksheets as the tabs for the work book.*/
	JTabbedPane jtp_tab;
	/**Panel to set the general tab pane for the file properties */
	JPanel jp_pan1=new JPanel();
	/**Panel to set the summary tab pane for the file properties */
	JPanel jp_pan2=new JPanel();
	/**Panel to set the statistics tab pane for the file properties */
	JPanel jp_pan3=new JPanel();
	/**Panel to set the contents tab pane for the file properties */
	JPanel jp_pan4=new JPanel();
	/**Sub panes used for file property display.*/
	JPanel jp_pan2In=new JPanel(new GridLayout(5,2));
	/**Sub panes used for file property display.*/
	JPanel jp_pan3In=new JPanel(new GridLayout(5,1));
	/**Sub panes used for file property display.*/
	JPanel jp_pan4In=new JPanel(new FlowLayout());
	/**Label heading to set for the summary property*/
	JLabel jl_sum1=new JLabel("  î¬ôð¢¹");
	/**Label heading to set for the summary property*/
	JLabel jl_sum2=new JLabel("sum1 ");
	/**Label heading to set for the summary property*/
	JLabel jl_sum3=new JLabel("sum1 ");
	/**Label heading to set for the summary property*/
	JLabel jl_sum4=new JLabel("sum1 ");
	/**Label heading to set for the summary property*/
	JLabel jl_sum5=new JLabel("sum1 ");

	/**Sets the summary property for display*/
	JTextField jt_sum1=new JTextField(20);
	/**Sets the summary property for display*/
	JTextField jt_sum2=new JTextField();
	/**Sets the summary property for display*/
	JTextField jt_sum3=new JTextField();
	/**Sets the summary property for display*/
	JTextField jt_sum4=new JTextField();
	/**Sets the summary property for display*/
	JTextField jt_sum5=new JTextField();

	/**Sets the contents pane's label heading.*/
	JLabel jl_cont=new JLabel("àì¢ªð£¼÷¢/Content");
	/**Text area to display the contents in the workbook.*/
	JTextArea jt_cont=new JTextArea(15,15);

	/**Label heading for the file property.*/
	JLabel jl_top;
	/**Label heading for the file property.*/
	JLabel jl_mid=new JLabel();
	/**Label heading for the file property.*/
	JLabel jl_bot=new JLabel();
	/**Label heading for the file property.*/
	JLabel jl_mod;
	/**sets the separator for the property details.*/
	JSeparator jsep=new JSeparator();

	/**Constructor to display the spreadsheet properties. It includes
	file size, location and path along with file creation date, last modified date
	and file access date .It also displays the file summary and contents.
	@param ss object of the main class.*/
	public PropFrame(Chathurangam ss)
	{
		mapp=ss;
		setTitle("SpreadSheet - Properties");
		setSize(450,450);
		setLocation(175,100);
		setIconImage(img);

		Date now = new Date();
		//System.out.println(now);
		String date=now.toString();
		File fileMod=new File("d:\\demosps\\Chathurangam.java");
		long lmod=fileMod.lastModified();
		//System.out.println("last modified --"+lmod);
		Date modDate=new Date(lmod);
		String modified_Date=modDate.toString();
		//System.out.println("las mod Date  "+modified_Date);

		jtp_tab=new JTabbedPane();
		jtp_tab.setFont(tabFont);
		jtp_tab.addTab(" ªð£¶ ",jp_pan1); // General Tab
		jtp_tab.addTab(" ªî£°ð¢¹ ",jp_pan2); // Summary tab
		jtp_tab.addTab(" ¹÷¢÷¤ò¤òô¢ ",jp_pan3);//statistics tab
		jtp_tab.addTab(" àì¢ªð£¼÷¢ ",jp_pan4);//content tab

		int tcount=mapp.tab_count;
		for(int i=1;i<=tcount;i++)
		{
		jt_cont.append("õ¤ó¤î£÷¢ "+i+"\n");
		}
		jp_pan2In.add(jl_sum1);
		jp_pan2In.add(jt_sum1);

		jp_pan2In.add(jl_sum2);
		jp_pan2In.add(jt_sum2);

		jp_pan2In.add(jl_sum3);
		jp_pan2In.add(jt_sum3);

		jp_pan2In.add(jl_sum4);
		jp_pan2In.add(jt_sum4);

		jp_pan2In.add(jl_sum5);
		jp_pan2In.add(jt_sum5);

		jp_pan2.add(jp_pan2In);

		jl_top=new JLabel("î¤¼î¢îñ¢                       "+date);
		jl_top.setFont(new Font("TABKural",0,12));
		jl_top.setForeground(Color.black);

		jl_mod=new JLabel("è¬ìê¤ò£è î¤¼î¢îð¢ðì¢ì ï£÷¢ "+modified_Date);
		jl_mod.setFont(new Font("TABKural",0,12));
		jl_mod.setForeground(Color.black);

		//jp_pan3In.add(jl_top);
		jp_pan3In.add(jl_mod);
		jp_pan3In.add(new JSeparator());
		jp_pan3In.add(new JLabel(ImagesLocator.getImage("empty.gif")));
		jp_pan3In.add(jsep);
		jp_pan3In.add(jl_bot);

		jp_pan3.add(jp_pan3In);
		jp_pan1.add(new JLabel(""));
		jp_pan1.add(new JLabel("---------------------------------------------"));

		jl_sum1.setForeground(Color.black);
		jl_sum1.setFont(tabFont);
		jl_cont.setFont(tabFont);
		jt_cont.setFont(tabFont);

		jp_pan4In.add(jl_cont);
		jp_pan4In.add(jt_cont);

		jp_pan4.add(jp_pan4In);

		getContentPane().add(jtp_tab);
	}
}
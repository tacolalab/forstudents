/**
* @(#)FindFrame.java 1.02 09/12/2002
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

/**Class to enable the Find and Replace function for the
worksheet. The given value can be searched throughout the
sheet and if necessary the value can be replace with another
value.This class extends from JFrame and implements
the action listener interface for action events.*/
public class FindFrame extends JFrame implements ActionListener
{
	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();
	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");

	/**To place labels and text fields in the window.*/
	JPanel jp_left=new JPanel(new GridLayout(6,1));
	/**To place the buttons next,replace and close in the window.*/
	JPanel jp_right=new JPanel(new GridLayout(3,1));

	/**To enter the find string from the worksheet.*/
	JTextField jt_find=new JTextField(25);
	/**To enter the replace string from the worksheet.*/
	JTextField jt_replace=new JTextField(25);

	/**To move the search for next occurence.*/
	JButton jb_find=new JButton("Ü´î¢¶ «î´ / FindNext");
	/**To close the window after find and replace function.*/
	JButton jb_close=new JButton("Í´/Close");
	/**To replace the string with found out string.*/
	JButton jb_replace=new JButton("ñ£ø¢Á/Replace");
	/**Sets the font for the component.*/
	Font font=new Font("TABKural",0,12);
	/**Sets the label heading for the find text field.*/
	JLabel jl_find=new JLabel("Þ¬î «î´/Find What");
	/**Sets the replace label heading for the replace filed entry.*/
	JLabel jl_replace=new JLabel("ñ£ø¢Á/Replace");
	ResourceBundle wordBundle;
	EscWinEvent escEvnt;

	/**Sets the match case for string to be found out*/
	JCheckBox jch_matchcase;
	boolean jchChecked=false;
	JRadioButton jrb_up=new JRadioButton("Up",false);
	JRadioButton jrb_down=new JRadioButton("Down",true);
	JPanel jp_find=new JPanel(new GridLayout(1,3));
	ButtonGroup bg_upDown=new ButtonGroup();

	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam main;
	/**Stores the selected row index*/
	int selRow=0;//table.getSelectedRow();
	/**Stores the selected column index*/

	int selCol=0;//table.getSelectedColumn();
	JTable table;
	String tableText=new String();
	boolean found = false;
	String findText;

	int findStartRow = 0;
	int findStartColumn = 0;
	boolean newRow = true;

	/**Constructor to invoke the find & replace functions.It displays
	the window for the string to find out to be entered. Replace string
	can also be given to replace the found out word.
	@param main object of MainClass */
	public FindFrame(Chathurangam main)
	{
		this.main=main;
		setSize(450,180);
		setTitle("Find");
		setIconImage(img);
		setLocation(100,120);
		setResizable(false);
		jt_find.setFont(font);
		jt_replace.setFont(font);
		jb_find.setFont(font);
		jb_close.setFont(font);
		jb_replace.setFont(font);
		jl_find.setFont(font);
		jl_replace.setFont(font);
		jch_matchcase=new JCheckBox(main.wordBundle.getString("case")+" /Match Case");

		jb_find.addActionListener(this);
		jb_close.addActionListener(this);
		jb_replace.addActionListener(this);
		jch_matchcase.addActionListener(this);

		jt_replace.setVisible(false);
		bg_upDown.add(jrb_up);
		bg_upDown.add(jrb_down);

		//jp_find.add(jch_matchcase);
		//jp_find.add(jrb_up);
		//jp_find.add(jrb_down);

		jp_left.add(jl_find);
		jp_left.add(jt_find);
		jp_left.add(jl_replace);
		jp_left.add(jt_replace);
		jp_left.add(jch_matchcase);
		jp_left.add(jp_find);
		//jch_matchcase.setText(wordBundle.getString("case"));

		jp_right.add(jb_find);
		jp_right.add(jb_replace);
		jp_right.add(jb_close);

		getContentPane().add(jp_left,"West");
		getContentPane().add(jp_right,"East");

		addKeyListener(new EscWinEvent((Component)this));

	}

	/**Fires action event when the button components will receive the
	action listeners
	@param ae object of ActionEvent.*/
	public void actionPerformed(ActionEvent ae)
	{
		table=main.cur_table;

		if(ae.getSource()==jb_close)
		{
			setVisible(false);
		}
		if(ae.getSource()==jch_matchcase)
		{
			if(jch_matchcase.isSelected())
			jchChecked=true;
			else
			jchChecked=false;

		}
		if(ae.getSource()==jb_find)// find button
		{
			boolean found = FindMethod();
			if(!found)
				found = FindMethod();
		}
		if(ae.getSource()==jb_replace)
		{
			jt_replace.setVisible(true);
			String replace=jt_replace.getText().trim();
			table.setValueAt(replace,table.getSelectedRow(),table.getSelectedColumn());
			table.repaint();
		}
	}


	public boolean FindMethod()
	{
		findText=jt_find.getText().trim();
		int rowCnt= table.getRowCount();
		int colCnt= table.getColumnCount();
		int currentRow = findStartRow;
		int currentColumn = findStartColumn;
		int txtLen;

		for(int i=currentRow;i<rowCnt;i++)
		{
			int j =0;
			if(!newRow)
				j = currentColumn;

			for( ;j<colCnt;j++)
			{
				tableText=table.getValueAt(i,j).toString().trim();
				txtLen=tableText.length();
				if(txtLen>=findText.length())
				{
					if(jchChecked==false)
					{
						tableText=tableText.toUpperCase();
						findText=findText.toUpperCase();
					}

					if(findText.equals(tableText))
					{
						table.setRowSelectionInterval(i,i);
						table.setColumnSelectionInterval(j,j);
						findStartRow = i;
						if(j<colCnt)
						{
							findStartColumn = j+1;
							newRow = false;
						}
						else
							newRow = true;

						return true;
					}
				}

				if(j>=17)
				{
					newRow = true;
				}


				/*if(jchChecked==true && !table.getValueAt(i,j).equals(null))
				{
					tableText=table.getValueAt(i,j).toString().trim();
					if(findText.equals(tableText))
					{
						table.setRowSelectionInterval(i,i);
						table.setColumnSelectionInterval(j,j);
					}
				}
				if(jchChecked==false && !table.getValueAt(i,j).equals(null) && !table.getValueAt(i,j).equals(""))
				{
					tableText=table.getValueAt(i,j).toString().trim();
					tableText=tableText.toUpperCase();
					findText=findText.toUpperCase();
					if(findText.equals(tableText))
					{
						table.setRowSelectionInterval(i,i);
						table.setColumnSelectionInterval(j,j);
					}
				}
				else
				{
					//System.out.println("No matching String Found !");
				}*/

			}//inner for loop -- j value

		}//outer for loop -- i value
		//System.out.println(" match not fond :"+findStartRow+"  "+findStartColumn);
		findStartRow = 0;
		findStartColumn = 0;
		return false;
	}
}
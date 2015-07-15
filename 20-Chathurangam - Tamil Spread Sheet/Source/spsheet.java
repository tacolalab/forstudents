/**
* @(#)spsheet.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
* @author V.Venkateswaran.
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import javax.swing.border.*;
import java.awt.Color.*;

/**To display the splash window when the application opens.
It contains the details about the product and company in the image.
it extends JWindow and implements action listener.*/
public class spsheet extends JWindow implements ActionListener
{
		/**The toolkit object is used to get the default toolkit
		to set the icon in the title bar of the window.*/
		Toolkit tk= Toolkit.getDefaultToolkit();
		/**The image object is used to set the image in the titlebar of the window.*/
		Image img=tk.getImage("tha.gif");
		/**Sets the border empty for the component.*/
		EmptyBorder empty=new EmptyBorder(5,5,5,5);
		/**Sets the font for the component.*/
		Font font=new Font("TABKural",0,14);
		/**Sets the color for the component.*/
		Color c=new Color(223,179,127);
		/**Button to enter into the application.*/
		JButton jb_enter=new JButton("  ¸¬ö¾");
		/**Sets the image with tool details. */
		JLabel jl_text;

	/**Constructor is invoked when splash window starts.
	It stays back in the screen till clicking the enter button.*/
	public spsheet()
	{
			setSize(620,390);
			setLocation(120,60);

			jl_text=new JLabel(ImagesLocator.getImage("sppage.jpg"));

			jb_enter.setFont(font);
			jb_enter.addActionListener(this);
			jb_enter.setForeground(Color.white);
			jb_enter.setBackground(Color.black);
			jb_enter.setBorder(empty);

			getContentPane().add(jl_text);
			getContentPane().add(jb_enter,BorderLayout.SOUTH);
			getContentPane().setBackground(c);
	}
	/**Fires action event when the enter button
	gets the action listener.It starts the application window.*/
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==jb_enter)
		{
			setVisible(false);
			Chathurangam ap=new Chathurangam();
			ap.setJMenuBar(new SSMenu(ap));
			ap.show();
			ap.pack();
			ap.repaint();
		}
	}
	/**Main method to start the splash window.
	@param ar[] of type string array.It calls the
	main class constructor.*/
	public static void main(String ar[])
	{
		spsheet base=new spsheet();
		base.show();
	}
}
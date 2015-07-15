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
import java.awt.Dimension;
import javax.swing.text.html.*;
import java.net.*;

public class ZoomFrame extends JFrame implements ActionListener
{
	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();

	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");

  	/**Obejct of the main class <code>Chathurangam</code>*/
  	Chathurangam main;

  	JPanel pantop=new JPanel();
  	JPanel panmid=new JPanel();
  	JPanel panbot=new JPanel();
  	JPanel panbut=new JPanel(new GridLayout(1,2));
  	JPanel pansize=new JPanel(new GridLayout(7,1));
  	JLabel jl_head=new JLabel("Ü÷õ£è¢èñ¢ / Magnification");

  	JButton jb_ok=new JButton("êó¤/ok");
  	JButton jb_cancel=new JButton("ï¦è¢°/Cancel");

  	JRadioButton jrb_size1=new JRadioButton("25%");
  	JRadioButton jrb_size2=new JRadioButton("50%");
  	JRadioButton jrb_size3=new JRadioButton("75%");
  	JRadioButton jrb_size4=new JRadioButton("100%",true);
  	JRadioButton jrb_size5=new JRadioButton("200%");
  	JRadioButton jrb_size6=new JRadioButton("õ¤¼ð¢ðî¢«îó¢¾/ Custom %");

	ButtonGroup bgrp=new ButtonGroup();
	JTextField jt_cust=new JTextField(2);
	int zmsize=0;

public ZoomFrame(Chathurangam main)
{
	this.main=main;
	setSize(220,270);
	setLocation(120,130);
	setIconImage(img);
	setTitle("Magnification(Zooming)");
	setResizable(false);
	jt_cust.setPreferredSize(new Dimension(5,10));
	jb_ok.addActionListener(this);
	jb_cancel.addActionListener(this);

	pantop.add(jl_head);

	panbut.add(jb_ok);
	panbut.add(jb_cancel);

	bgrp.add(jrb_size1);
	bgrp.add(jrb_size2);
	bgrp.add(jrb_size3);
	bgrp.add(jrb_size4);
	bgrp.add(jrb_size5);
	bgrp.add(jrb_size6);

	pansize.add(jrb_size1);
	pansize.add(jrb_size2);
	pansize.add(jrb_size3);
	pansize.add(jrb_size4);
	pansize.add(jrb_size5);
	pansize.add(jrb_size6);
	pansize.add(jt_cust);

	panmid.add(pansize);
	panbot.add(panbut);

	getContentPane().add(pantop,"North");
	getContentPane().add(panmid,"Center");
	getContentPane().add(panbot,"South");

	addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == 27)//Esc
				{
					setVisible(false);
				}
			}
		});

}
public void actionPerformed(ActionEvent ae)
{
	int rowHeight;

	if(ae.getSource()==jb_ok)
	{
		if(jrb_size1.isSelected())
		{
			for(int i=0;i<main.cur_table.getRowCount();i++)
			{
				rowHeight=main.cur_table.getRowHeight(i);
				if(rowHeight==20)
				{
					main.cur_table.setRowHeight(i,20*1/4);
					main.cur_tableHeader.setRowHeight(i,20*1/4);
					setVisible(false);
					main.zoom=true;
				}
				else
				{
					main.cur_table.setRowHeight(i,rowHeight*1/4);
					main.cur_tableHeader.setRowHeight(i,rowHeight*1/4);
					setVisible(false);
					main.zoom=true;

				}
			}
		}
		if(jrb_size2.isSelected())
		{
		  for(int i=0;i<main.cur_table.getRowCount();i++)
			{
				rowHeight=main.cur_table.getRowHeight(i);
				if(rowHeight==20)
				{
					main.cur_table.setRowHeight(i,20*1/2);
					main.cur_tableHeader.setRowHeight(i,20*1/2);
					setVisible(false);
					main.zoom=true;
				}
				else
				{
					main.cur_table.setRowHeight(i,rowHeight*1/2);
					main.cur_tableHeader.setRowHeight(i,rowHeight*1/2);
					setVisible(false);
					main.zoom=true;
				}
			}
		}
		if(jrb_size3.isSelected())
		{
			for(int i=0;i<main.cur_table.getRowCount();i++)
			{
				rowHeight=main.cur_table.getRowHeight(i);
				if(rowHeight==20)
				{
					main.cur_table.setRowHeight(i,20*3/4);
					main.cur_tableHeader.setRowHeight(i,20*3/4);
					setVisible(false);
					main.zoom=true;
				}
				else
				{
					main.cur_table.setRowHeight(i,rowHeight*3/4);
					main.cur_tableHeader.setRowHeight(i,rowHeight*3/4);
					setVisible(false);
					main.zoom=true;
				}
			}
		}
		if(jrb_size4.isSelected())
		{
			for(int i=0;i<main.cur_table.getRowCount();i++)
			{
				rowHeight=main.cur_table.getRowHeight(i);
			if(rowHeight==20)
			{
				main.cur_table.setRowHeight(i,20*1);
				main.cur_tableHeader.setRowHeight(i,20*1);
				setVisible(false);
				main.zoom=true;
			}
			else
			{
				main.cur_table.setRowHeight(i,rowHeight*1);
				main.cur_tableHeader.setRowHeight(i,rowHeight*1);
				setVisible(false);
				main.zoom=true;
			}
			}

		}
		if(jrb_size5.isSelected())
		{
			for(int i=0;i<main.cur_table.getRowCount();i++)
			{
			rowHeight=main.cur_table.getRowHeight(i);
			if(rowHeight==20)
			{
				main.cur_table.setRowHeight(i,20*2);
				main.cur_tableHeader.setRowHeight(i,20*2);
				setVisible(false);
				main.zoom=true;
			}
			else
			{
				main.cur_table.setRowHeight(i,rowHeight*2);
				main.cur_tableHeader.setRowHeight(i,rowHeight*2);
				setVisible(false);
				main.zoom=true;
			}
			}
		}
		if(jrb_size6.isSelected())
		{
			for(int i=0;i<main.cur_table.getRowCount();i++)
			{
			rowHeight=main.cur_table.getRowHeight(i);
			if(rowHeight==20)
			{
				zmsize=Integer.parseInt(jt_cust.getText().trim());
				main.cur_table.setRowHeight(i,20*zmsize/100);
				main.cur_tableHeader.setRowHeight(i,20*zmsize/100);
				setVisible(false);
				main.zoom=true;
			}
			else
			{
				main.cur_table.setRowHeight(i,rowHeight*zmsize/100);
				main.cur_tableHeader.setRowHeight(i,rowHeight*zmsize/100);
				setVisible(false);
				main.zoom=true;
			}
			}
		}
	}
	if(ae.getSource()==jb_cancel)
	{
		setVisible(false);
	}
}
}
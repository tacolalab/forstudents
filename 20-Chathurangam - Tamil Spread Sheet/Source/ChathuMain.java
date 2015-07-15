import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import javax.swing.border.*;
import java.awt.Color.*;

public class ChathuMain extends JFrame implements ActionListener
{
	/**
	* The toolkit class is used to set the icon in the titlebar for the window.
	*/
	Toolkit tk= Toolkit.getDefaultToolkit();
	Image img=tk.getImage("tha.gif");

	JLabel jl_head=new JLabel(ImagesLocator.getImage("dbhomsp.jpg"));
	JLabel jl_subhead=new JLabel("«î¬õò£ù îó¾î÷è¢ è¼õ¤¬òî¢ «îó¢ï¢ªî´è¢è¾ñ¢ / Choose the BackEnd");
	JPanel jp_top=new JPanel();
	JPanel jp_bot=new JPanel();
	JPanel jp_mid=new JPanel(new GridLayout(4,1));

	JButton jb_open=new JButton("î¤ø/Open");
	JButton jb_close=new JButton("ïù¢ø¤ / Exit");

	ButtonGroup bgrp=new ButtonGroup();

	public ChathuMain()
	{
		setSize(750,550);
		setLocation(100,90);
		setTitle("Spread Sheet - Tamil");
		setIconImage(img);//icon at title bar

		jb_open.setFont(new Font("TABKural",1,12));
		jb_open.addActionListener(this);
		jb_close.setFont(new Font("TABKural",1,12));
		jl_subhead.setFont(new Font("TABKural",1,14));
		jl_subhead.setForeground(Color.white);
		jb_close.addActionListener(this);

		jp_top.add(jl_head);
		jp_bot.add(jb_close);
		jp_bot.add(jb_open);

		jp_mid.add(jl_subhead);

		jp_top.setBackground(Color.black);
		jp_bot.setBackground(Color.black);
		jp_mid.setBackground(Color.black);

		jb_open.setBackground(Color.black);
		jb_open.setForeground(Color.white);

		jb_close.setBackground(Color.black);
		jb_close.setForeground(Color.white);

		getContentPane().setBackground(Color.black);
		getContentPane().add(jp_top,"North");
		getContentPane().add(jp_bot,"South");
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==jb_close)
		{
				System.exit(0);
		}
		if(ae.getSource()==jb_open)
		{
				//System.out.println("MS-Access");
				Chathurangam chathu=new Chathurangam();//filename);
				chathu.setJMenuBar(new SSMenu(chathu));
				chathu.show();
				setVisible(false);
		}

	}
	public static void main(String ar[])
	{
		ChathuMain db=new ChathuMain();
		db.show();
	}
}
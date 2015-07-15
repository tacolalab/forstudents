/**
  *  This class is to create the user interface for Morphological
  *  Analyser
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
/** <applet code = "Analyser" width = 400 height = 400>
	</applet>
	*/

public class poonguzhazhi extends JFrame implements ActionListener
{
	Toolkit tk = Toolkit.getDefaultToolkit();

	Dimension dim = tk.getScreenSize();
	double d2 = dim.getHeight();
	double d1 = dim.getWidth();

	Font f=new Font("TAB-Anna",5,18);

	JLabel l1 = new JLabel("â¬îð¢ ðø¢ø¤ð¢ «ðêô£ñ¢?");
	JComboBox jcb= new JComboBox();

	JButton but1=new JButton("Exit");
	JPanel p1=new JPanel();
	JPanel p2= new JPanel();
	JPanel p3= new JPanel();

	BufferedReader f1;
	BufferedReader f2;
	String temp="";
	String temp1="";
	JFrame frame = null;


	JMenuBar menuBar;
	JMenuItem menuItem;

	JMenu menu;

	final int T99	= 1;
	final int TRANS	= 2;
	final int SYSTEM= 3;
	int keyboard = 1;

	public poonguzhazhi() {

		super("Poonguzhali Chatterbox version 1.1");
		setSize(408,301);
		//ImageIcon i = new ImageIcon((FileLocator.getFile("GIF.GIF")));
		//ImagesLocator.getImage
		//setIconImage(i.getImage());
		setLocation(( (int)d1-408)/2,((int)d2-278)/2);

		try
		{
			InputStream inputStream = getClass().getResourceAsStream("TABANNA.TTF");
			Font f1 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			f = f1.deriveFont(Font.PLAIN, 14);
		}
		catch(Exception e)
		{
		}

		try{
					f1=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("domainlist.txt")));
					while((temp=f1.readLine())!=null)
					{
					//System.out.println(temp);
					jcb.addItem(temp);
					//System.out.println("tokens from domainlist"+temp);
					}
				}
					catch(Exception e)
					{
						System.out.println(e);
			}


		p1.add(l1);
		p2.add(jcb);
		p3.add(but1);
		l1.setFont(f);
		jcb.setFont(f);
		jcb.addActionListener(this);
		/*p1.setBackground(Color.black);
		p2.setBackground(Color.black);
		p3.setBackground(Color.black);*/




		menuBar = new JMenuBar();


		setJMenuBar(menuBar);

		menu = new JMenu("File");
		menuBar.add(menu);

		/*menuItem = new JMenuItem("To Add New Domain");
		menu.add(menuItem);
		menuItem.addActionListener(this);*/
			//new ActionListener()
		//{public void actionPerformed(ActionEvent ae){domain()}});

		menuItem = new JMenuItem("Exit");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){System.exit(0);}});



		menu = new JMenu("Help ");
		menuBar.add(menu);

		menuItem = new JMenuItem("About");
		menu.add(menuItem);




		but1.addActionListener(this);

		getContentPane().add(p1,"North");
		getContentPane().add(p2,"Center");
		getContentPane().add(p3,"South");

		setVisible(true);
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(1);}});

	}



	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==but1)
			System.exit(0);

		if(ae.getSource()==jcb)
			{

				//System.out.println("entered");
				try{
				 FileInputStream fin = new FileInputStream("filedomains.txt");
				 FileInputStream fin1 = new FileInputStream("Infofile.txt");
				 Properties prop = new Properties();
				 Properties prop1 = new Properties();
				 prop.load(fin);
				 prop1.load(fin1);

					//System.out.println(jcb.getSelectedItem());
					try{
						f1=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("domainlist.txt")));

						while((temp1=f1.readLine())!=null)
						{
							if(jcb.getSelectedItem().equals(temp1))
							{
								//System.out.println("entered combo loop  "+temp1);
								String filename1 = prop.getProperty(temp1);
								String inter = prop1.getProperty(temp1);
								String itemsel1=temp1;
								//System.out.println("I have "+filename1);
								//System.out.println("I don't have "+inter);
								frame =  new pooMain1(filename1,inter,itemsel1);
								break;
							}
						}
					}
					catch(Exception e){System.out.println(e);}
			}catch(Exception e){System.out.println(e);}
	}
}
	public static void main(String args[])
	{
		new poonguzhazhi();
	}


}
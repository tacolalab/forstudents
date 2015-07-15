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

public class domainaddition extends JFrame implements ActionListener,KeyListener
{
	Toolkit tk = Toolkit.getDefaultToolkit();

	Dimension dim = tk.getScreenSize();
	double d2 = dim.getHeight();
	double d1 = dim.getWidth();

	Font f=new Font("TAB-Anna",5,18);

	//JTextField tf1 = new JTextField(30);
	//JTextArea tf2 = new JTextArea(10,20);

	JLabel dom=new JLabel("To Add New Domain");
	JLabel engdom=new JLabel("Enter the domain name in english");
	JLabel info=new JLabel("Information about Domain ");
	JTextField jtf1dom = new JTextField(" ",10);
	JTextField jtfdom = new JTextField(" ",10);
	//Translit_Text jtfdom=new Translit_Text(10);
	JTextArea jtf2 = new JTextArea(5,20);
	JScrollPane scrollpane=new JScrollPane(jtf2);

	JButton save =new JButton("SAVE");
	JButton clear =new JButton("CLEAR");
	JButton exit=new JButton("EXIT");
	JPanel p1=new JPanel();
	JPanel p2= new JPanel();
	JPanel p3= new JPanel();
	//JScrollPane scrollpane=new JScrollPane(tf2);
	BufferedReader f1;
	BufferedReader f2;
	String temp="";
	String temp1="";
	String domaintex="";

	JPopupMenu popup;
	JMenuBar menuBar;
	JMenuItem menuItem;
	JRadioButtonMenuItem radioMenu;
	JMenu menu;
	ButtonGroup group = new ButtonGroup();
	ButtonGroup fontgroup = new ButtonGroup();

	final int T99	= 1;
	final int TRANS	= 2;
	final int SYSTEM= 3;
	int keyboard = 1;

	public domainaddition() {

		super("Poonguzhali Chatterbox version 1.1");
		setSize(408,301);
		//setSize(500,600);



		setLocation(( (int)d1-408)/2,((int)d2-278)/2);

		/*try
		{
			InputStream inputStream = getClass().getResourceAsStream("TABANNA.TTF");
			Font f1 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			f = f1.deriveFont(Font.PLAIN, 14);
		}
		catch(Exception e)
		{
		}*/



		p1.add(dom);
		p1.add(jtfdom);
		p2.add(engdom);
		p2.add(jtf1dom);
		p2.add(info);
		p2.add(scrollpane);
		//p3.add(but);
		p3.add(clear);
		p3.add(save);
		p3.add(exit);

		jtfdom.setFont(f);
		jtf2.setFont(f);


		popup = new JPopupMenu();

		menuBar = new JMenuBar();


		setJMenuBar(menuBar);

		menu = new JMenu("File");
		menuBar.add(menu);

		menuItem = new JMenuItem("Knowledge Base Updation");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem("Exit");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem("hai");
		menu.add(menuItem);

		menu = new JMenu("KeyBoard");
		menuBar.add(menu);

		radioMenu = new JRadioButtonMenuItem("TamilNet99");
		menu.add(radioMenu);
		group.add(radioMenu);
		radioMenu.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){keyboard = T99;}});
		radioMenu.setSelected(true);

		radioMenu = new JRadioButtonMenuItem("Transliteration");
		menu.add(radioMenu);
		radioMenu.setEnabled(false);
		group.add(radioMenu);

		radioMenu = new JRadioButtonMenuItem("System KeyBoard");
		radioMenu.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){keyboard = SYSTEM;}});
		radioMenu.add(menuItem);
		menu.add(radioMenu);
		group.add(radioMenu);


		menu = new JMenu("Encoding");
		menuBar.add(menu);

		radioMenu = new JRadioButtonMenuItem("Tab");
		menu.add(radioMenu);
		fontgroup.add(radioMenu);
		radioMenu.setSelected(true);

		radioMenu = new JRadioButtonMenuItem("Tam");
		menu.add(radioMenu);
		radioMenu.setEnabled(false);
		fontgroup.add(radioMenu);


		menuItem = new JMenuItem("cut");
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("copy");
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("paste");
		menuItem.addActionListener(this);
		popup.add(menuItem);
		popup.addSeparator();

		menuItem = new JMenuItem("delete");
		menuItem.addActionListener(this);
		popup.add(menuItem);

		MouseListener popupListener = new PopupListener();
		jtf2.addKeyListener(this);
		jtf2.addMouseListener(popupListener);


		clear.addActionListener(this);
		save.addActionListener(this);
		exit.addActionListener(this);
		getContentPane().add(p1,"North");
		getContentPane().add(p2,"Center");
		getContentPane().add(p3,"South");

		setVisible(true);


	}



	public void keyPressed(KeyEvent ke)
	{
		//if(keyboard == 1)
		//System.out.println("The current keyboard is TamilNet99 /n which is not active now");
			//jtf2.setText(st99.tamilNet99(ke,jtf2.getText()));
		if(keyboard == 2)
			System.out.println("The current keyboard is transliteration /n which is not active now");
		else ;//System.out.println("The current keyboard is system keyboard");
	}
	public void keyTyped(KeyEvent ke)
	{
		if((keyboard == 3)||(keyboard == 1));
		else
			ke.consume();
	}
	public void keyReleased(KeyEvent ke)
	{
	}


	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==exit)
			System.exit(0);

		if(ae.getSource()==clear)
		{
			jtfdom.setText("");
			jtf1dom.setText("");
			jtf2.setText("");
			}
		if(ae.getSource()==save)
			{
				try{
				 FileInputStream fin = new FileInputStream("filedomains.txt");
				 FileInputStream fin1 = new FileInputStream("Infofile.txt");
				 Properties prop = new Properties();
				 Properties prop1 = new Properties();
				 prop.load(fin);
				 prop1.load(fin1);

				domaintex= jtfdom.getText().trim();
				String domain1=jtf1dom.getText().trim();
				String info=jtf2.getText().trim();
				System.out.println(domaintex);
				FileWriter kbdom; FileWriter techdom; FileWriter startdom;
				//jcb.addItem(domaintex);
				try
				  {
				  FileWriter f=new FileWriter("domainlist.txt",true);
				  kbdom =new FileWriter("KB"+domain1+".txt");
				  techdom= new FileWriter(domain1+"_techterms.txt");
				  startdom= new FileWriter(domain1+"_starters.txt");
				  BufferedWriter bf=new BufferedWriter(f);
				  System.out.println("Writing to file");
				  bf.newLine();
				  //bf.write("$");
				  bf.write(domaintex);
				  //bf.write("$");
				  bf.close();
				  f.close();
				  }
				catch(Exception e){System.out.println(e);}

				try
				 {

					 FileWriter f1=new FileWriter("filedomains.txt",true);
					 BufferedWriter bf1=new BufferedWriter(f1);
					 System.out.println(domaintex);
					 System.out.println("Writing to file");
					 bf1.newLine();
					 bf1.write(domaintex);
					 bf1.write("=");
					 bf1.write("/");
					 bf1.write("KB"+domain1+".txt");
					 bf1.write(",");bf1.write(domain1+"_techterms.txt");bf1.write(",");
					 bf1.write(domain1+"_starters.txt");bf1.write("/");
					 bf1.close();
					 f1.close();
				 }
				 catch(Exception e){System.out.println(e);}

				 try
				 {

					 FileWriter f2=new FileWriter("InfoFile.txt",true);
					 BufferedWriter bf2=new BufferedWriter(f2);
					 System.out.println(domaintex);
					 System.out.println("Writing to file");
					 bf2.newLine();
					 bf2.write(domaintex);
					 bf2.write("=");
					 bf2.write(info);
					 bf2.close();
					 f2.close();
				 }
				 catch(Exception e){System.out.println(e);}

	 		 }
	 		 catch(Exception e){System.out.println(e);}

			}
		if(ae.getActionCommand().equals("Exit"))
			{
				System.exit(0);
			}
		if(ae.getActionCommand().equals("Knowledge Base Updation"))
			{
				KBInterfaceMenu kn=new KBInterfaceMenu();
			}

		if(ae.getActionCommand().equals("cut"))
			jtf2.cut();
		if(ae.getActionCommand().equals("copy"))
			jtf2.copy();
		if(ae.getActionCommand().equals("paste"))
			jtf2.paste();
		if(ae.getActionCommand().equals("delete"))
			jtf2.replaceSelection("");
	}

class PopupListener extends MouseAdapter
{
	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
		showPopup(e);
	}

	private void showPopup(MouseEvent e)
	{
		if (e.isPopupTrigger())
		{
			popup.show(e.getComponent(),
			e.getX(), e.getY());
		}
	}
}
public static void main(String args[])
	{
		new domainaddition();
	}

}
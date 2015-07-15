/*
OWNER   : RCILTS

PURPOSE : This program gives the menu for the interfaces to the Knowledge Base

*/

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
public class KBInterfaceMenu extends JFrame implements ActionListener
{
   public static int code = 1;
   //JFrame frame=null;
   JMenuBar menuBar;
   JMenu menu, submenu;
   JMenuItem menuItem;
   JComboBox jbdomain=new JComboBox();
   JButton jbexit=new JButton("EXIT");


   Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension dim = tk.getScreenSize();
    double d1 = dim.getHeight();
    double d2 = dim.getWidth();
	String fileselected="";
	//String domainsel="";
	String temp="";
	String temp1="";
	String token[]= new String[25];
	BufferedReader f1;

   public KBInterfaceMenu()
   {
    	super("Poongkuzhali");
    	setSize(600,440);

        Font f = new Font("TAB-Anna",4,16);
        Font t = new Font("Times New Roman",3,45);
	  	jbexit.addActionListener(this);
	  	jbdomain.setFont(f);
	  	try{
			f1=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("domainlist.txt")));
			while((temp=f1.readLine())!=null)
			{
				jbdomain.addItem(temp);
			}
		    }
		 catch(Exception ex){System.out.println(ex);}

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menu = new JMenu("Files");
		menu.setFont(f);
		menuBar.add(menu);
		menuBar.add(jbdomain);
		menuBar.add(jbexit);
		jbexit.addActionListener(this);

		menuItem = new JMenuItem("Knowledge Base",KeyEvent.VK_K);
		menuItem.setFont(f);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Words",KeyEvent.VK_W);
		menuItem.setFont(f);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Technical Terms",KeyEvent.VK_T);
		menuItem.setFont(f);
		menuItem.addActionListener(this);
		menu.add(menuItem);

  		menuItem = new JMenuItem("Exit",KeyEvent.VK_E);
		menuItem.setFont(f);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		show();

   }

     public void Stringtokenize(String s1)
      	 	{
   		System.out.println(s1);
   		int wordNo=0;
   		int wordCount=0;


   		 StringTokenizer st=new StringTokenizer(s1,",,/,");
   		 while(st.hasMoreTokens())
   			{
   			token[wordNo]=st.nextToken();
   			wordNo++;
   			}
   		wordCount=wordNo;
   		wordNo--;
   			for(int i=0;i<wordCount;i++)
   			 {

   			 System.out.println("Word "+i+" = "+token[i] +"\n");
   			  }

	}

   public void actionPerformed(ActionEvent ae)
   {
	   try{
	   FileInputStream fin = new FileInputStream("filedomains.txt");
	   Properties prop = new Properties();
	   prop.load(fin);

			f1=new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("domainlist.txt")));
			while((temp1=f1.readLine())!=null)
			{
				if(jbdomain.getSelectedItem().equals(temp1)){
					fileselected = prop.getProperty(temp1);
					Stringtokenize(fileselected);
					System.out.println(fileselected);
					}
				}
			}
			catch(Exception e){System.out.println(e);}



      if(ae.getActionCommand().equals("Knowledge Base"))
       {

		  KBIntface k =  new KBIntface(token[0]);
		  System.out.println("enteredintface"+token[0]);}

       if(ae.getActionCommand().equals("Words"))
       {

       	  KBIntfWords w=new KBIntfWords();

      }
       if(ae.getActionCommand().equals("Technical Terms"))
       {
		   KBIntfTechterms t= new KBIntfTechterms(token[1]);
		        }
        if(ae.getActionCommand().equals("Exit"))
                   this.dispose();
        if(ae.getActionCommand().equals("EXIT"))
                   this.dispose();

    }
/*	public void SelectionOfFile()
		{
			if(jbdomain.getSelectedItem()="Internet"||jbdomain.getSelectedItem()="Electricity"||
			jbdomain.getSelectedItem()="Indian Language Computing")
			{
				JOptionPane.showMessageDialog(getContentPane(),"Select an domain from the comboBox");
			}
			if(jbdomain.getSelectedItem()="Internet")
			 {
				fileselected="KBInternet.txt" }
			if(jbdomain.getSelectedItem()="Electricity")
			 {
				fileselected="KBElectricity.txt" }
			if(jbdomain.getSelectedItem()="Indian Language Computing")
			 {
				fileselected="KBIndiya.txt" }
		}*/

     public static void main(String a[])
    {
	   new KBInterfaceMenu();
    }
}
/*
OWNER   : RCILTS

PURPOSE : This program gives the interface to the file of technical terms

*/

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
public class KBIntfTechterms extends JFrame implements ActionListener
{
   JLabel jlInputKey=new JLabel("Input Key : ");
   JTextField jtfInputKey = new JTextField(20);
   JButton jbEnter = new JButton("ENTER");
   JButton jbExit = new JButton("EXIT");
   JPanel p1 = new JPanel();
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension dim = tk.getScreenSize();
    double d1 = dim.getHeight();
    double d2 = dim.getWidth();
    String filepassed="";


   public KBIntfTechterms(String filepassed)
   {
	this.filepassed=filepassed;
//    	super("Poongkuzhali");
    	setSize(500,100);

        Font f = new Font("TAB-Anna",4,16);

		p1.add(jlInputKey);
		p1.add(jtfInputKey);
		p1.add(jbEnter);
//		j1InputKey.setFont(f);
		jtfInputKey.setFont(f);
    	jtfInputKey.setBackground(Color.white);
    	jtfInputKey.setForeground(Color.black);
    	p1.add(jbExit);
 	 	jbEnter.addActionListener(this);
    	jbExit.addActionListener(this);
		jbEnter.setForeground(Color.red);
		jbExit.setForeground(Color.red);
		getContentPane().add(p1,"North");

		setVisible(true);
//			addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){this.dispose();}});
   }

   public void actionPerformed(ActionEvent ae)
   {
	System.out.println("Actionevent="+ae.getActionCommand());
      if(ae.getActionCommand().equals("ENTER"))
       {
		   String s="$"+jtfInputKey.getText()+"$";
		   try
		   {
			FileInputStream f1=new FileInputStream(filepassed);
			int found=fnd(f1,s);
			System.out.println("Found:"+found);
			if(found==0)
			{
		  try
		  {
			  FileWriter f=new FileWriter(filepassed,true);
			  BufferedWriter bf=new BufferedWriter(f);
			  System.out.println("Writing to file");
			bf.newLine();
			bf.write("$");
				bf.write(jtfInputKey.getText().trim());
			bf.write("$");
				bf.close();
				f.close();
			}catch(Exception e){System.out.println(e);}

			}
			f1.close();
			}catch(Exception e){System.out.println(e);}
		}

       if(ae.getActionCommand().equals("EXIT"))
       {
                   this.dispose();
		}
	}
private int fnd(FileInputStream f1, String srchstr)
{
	StringBuffer sb=new StringBuffer("");
	int flag=0,found=0;
	char ch=' ';

		System.out.print("This is search string : "+srchstr);
		while(found==0 && ch!=(char)-1)
		{
			try{
			ch=(char)f1.read();
			} catch(Exception e){System.out.println(e);}
			if(ch=='$')
			{
				sb.append(ch);
				if(flag==0)
					flag=1;
				else
				{
					if(srchstr.equals(sb.substring(0)))
					{
						found=1;
						flag=0;
					}
					else
					{
						sb.delete(0,sb.length());
						flag=0;
					}
				}
			}
			if(flag==1 && ch!='$')
				sb.append(ch);
			}
			System.out.println(found);
			return found;
	}

   /*  public static void main(String a[])
    {
	   new KBIntfTechterms();
    }*/
}
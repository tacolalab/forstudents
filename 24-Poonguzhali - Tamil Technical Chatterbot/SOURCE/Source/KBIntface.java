/*OWNER   : RCILTS

PURPOSE : This program gives the interface to the Knowledge Base

*/

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
public class KBIntface extends JFrame implements ActionListener
{
   JLabel jlInputKey=new JLabel("Input Key : ");
   JTextField jtfInputKey = new JTextField(20);
   JButton jbEnter = new JButton("ENTER");
   JButton jbExit = new JButton("EXIT");
   JComboBox jbdomain=new JComboBox();
   JPanel p1 = new JPanel();
   JPanel p2 = new JPanel();
   JPanel p3 = new JPanel();
   JPanel p4 = new JPanel();
   JPanel p5 = new JPanel();
   JPanel p6 = new JPanel();
   JPanel p7 = new JPanel();
	JLabel jlResponse=new JLabel("Response : ");
	JLabel jlAlternateResponse=new JLabel("Alternate Response : ");
	JTextField jtfResponse=new JTextField(10);
	JTextField jtfAlternateResponse=new JTextField(10);

	JButton jbDone=new JButton("DONE");
	JButton jbSave=new JButton("SAVE");

	Vector responses=new Vector();
	int NoOfResponses=0;
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension dim = tk.getScreenSize();
    double d1 = dim.getHeight();
    double d2 = dim.getWidth();
    String filepassed="";


   public KBIntface(String filepassed)
   {
	this.filepassed=filepassed;
    	//super("Poongkuzhali");
	System.out.println(filepassed);
    	//setSize(500,100);
		setSize(700,500);
		Font f = new Font("TAB-Anna",4,16);
		/*jbdomain.addItem("Doamin List");
		jbdomain.addItem("Internet");
		jbdomain.addItem("Electricity");
		jbdomain.addItem("Indian Language Computing");*/

		p1.add(jlInputKey);
		p1.add(jtfInputKey);
		//p1.add(jbdomain);//p1
		p1.add(jbEnter);//p1
		jtfInputKey.setFont(f);
		jtfResponse.setFont(f);
		jtfAlternateResponse.setFont(f);
    	p1.add(jbExit);//p1
 	 	jbEnter.addActionListener(this);
    	jbExit.addActionListener(this);
		jbSave.addActionListener(this);
		jbDone.addActionListener(this);
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
			FileInputStream f=new FileInputStream(filepassed);
			int found=fnd(f,s);
			System.out.println("Found:"+found);
			if(found==0)
			{
				setSize(500,200);
				p3.add(jlResponse);
				p3.add(jtfResponse);
				p4.add(jlAlternateResponse);
				p4.add(jtfAlternateResponse);
				p7.add(jbSave);
				p7.add(jbExit);
				p6.setLayout(new BorderLayout());
				p6.add(p3,"North");
				p6.add(p4,"South");
				p5.setLayout(new BorderLayout());
				p5.add(p6,"Center");
				p5.add(p7,"South");
				getContentPane().add(p5,"Center");
				setVisible(true);
			}
			f.close();
			}catch(Exception e){System.out.println(e);}
		}

	  if(ae.getActionCommand().equals("SAVE"))
	  {
		System.out.println("Inside save");
		  try
		  {
			  FileWriter f=new FileWriter(filepassed,true);
			  BufferedWriter bf=new BufferedWriter(f);
			  System.out.println("Writing to file");
			bf.newLine();
			bf.write("$");
				bf.write(jtfInputKey.getText().trim());
			bf.write("$");
			bf.write("n");
			bf.write("1");
			bf.write("~");
			bf.write(jtfResponse.getText().trim());
			bf.write("#");
			bf.write(jtfAlternateResponse.getText().trim());
			bf.write("%");
			bf.close();
			f.close();
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

     /*public static void main(String a[])
    {
	   new KBIntface();
    }*/
}
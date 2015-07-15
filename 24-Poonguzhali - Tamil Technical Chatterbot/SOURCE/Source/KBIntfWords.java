/*
OWNER   : RCILTS

PURPOSE : This program gives the interface to the file of technical terms

*/

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
public class KBIntfWords extends JFrame implements ActionListener,FocusListener
{
   JLabel jlWord=new JLabel("Word : ");
   JTextField jtfWord = new JTextField(20);
   JButton jbEnter = new JButton("ENTER");
   JButton jbExit = new JButton("EXIT");
   JPanel p1 = new JPanel(new FlowLayout());
   JPanel p2 = new JPanel(new FlowLayout());

	JLabel jlWordNo=new JLabel("Word No. : ");
	JLabel jlPriority=new JLabel("Priority : ");
	JLabel jlLink=new JLabel("Links");
	JTextField jtfWordNo=new JTextField(" ",5);
	JTextField jtfPriority=new JTextField(" ",5);
	JTextField jtfLink=new JTextField(" ",10);
	JButton jbSave=new JButton("SAVE");
	Vector synonyms=new Vector();
	Vector links=new Vector();
	int NoOfSynonyms=0;
	int NoOfLinks=0;
   Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension dim = tk.getScreenSize();
    double d1 = dim.getHeight();
    double d2 = dim.getWidth();


   public KBIntfWords()
   {
    	super("Poongkuzhali");
    	setSize(100,200);

        Font f = new Font("TAB-Anna",4,16);

		p1.add(jlWord);
		p1.add(jtfWord);
		p1.add(jbEnter);

		jtfWord.setFont(f);
    	jtfWord.setBackground(Color.white);
    	jtfWord.setForeground(Color.black);
    	p1.add(jbExit);
 	 	jbEnter.addActionListener(this);
    	jbExit.addActionListener(this);
		jbSave.addActionListener(this);
		jbEnter.setForeground(Color.red);
		 jbExit.setForeground(Color.red);
		 getContentPane().add(p1,"Center");

		pack();
		setVisible(true);
			addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
   }

   public void actionPerformed(ActionEvent ae)
   {
	System.out.println("Actionevent="+ae.getActionCommand());
      if(ae.getActionCommand().equals("ENTER"))
       {
		   String s="$"+jtfWord.getText()+"$";
		   try
		   {
			FileInputStream f=new FileInputStream("words.txt");
			int found=fnd(f,s);
			System.out.println("Found:"+found);
			if(found==0)
			{
				System.out.println("Inside Found");
				p2.add(jlWordNo);
				p2.add(jtfWordNo);
				p2.add(jlPriority);
				p2.add(jtfPriority);
				p2.add(jlLink);
				p2.add(jtfLink);
				p2.add(jbSave);
				getContentPane().add(p2,"South");
				pack();
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
			FileWriter f=new FileWriter("words.txt",true);
			BufferedWriter bf=new BufferedWriter(f);
			bf.newLine();
				bf.write(jtfWordNo.getText().trim());
			bf.write(".");
			bf.write("$");
				bf.write(jtfWord.getText().trim());
			bf.write("$");
			bf.write("n");
			for(int i=0;i<jtfWordNo.getText().trim().length();i++)
				bf.write(jtfWordNo.getText().trim());
			bf.write("w");
				bf.write(jtfWord.getText().trim());
			bf.write("p");
				bf.write(jtfPriority.getText().trim());
			bf.write("p");
				bf.write(jtfLink.getText().trim());
			bf.write("#");
			bf.close();
			f.close();
			}catch(Exception e){System.out.println(e);}
		}

       if(ae.getActionCommand().equals("EXIT"))
       {
                    this.dispose();
		}
	}
	public void focusGained(FocusEvent fe)
	{
	}
	public void focusLost(FocusEvent fe)
	{
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

     public static void main(String a[])
    {
	   new KBIntfWords();
    }
}
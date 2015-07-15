package SpellChecker;

/*               Analyzer for Noun                           */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.System;
import java.util.Stack;
import java.io.*;
import java.sql.*;
import analyser.*;

public class noun5_temp extends JFrame implements ActionListener
         {
//          static JTextField tf1 = new JTextField("marathil",20);
            static JComboBox tf1 = new JComboBox();
		    static JTextArea tf2 = new JTextArea(10,20);

     	  Toolkit tk = Toolkit.getDefaultToolkit();
    	  Image img = tk.getImage(".\\gif.GIF");
  		  Dimension dim = tk.getScreenSize();
  		  double d1 = dim.getHeight();
		  double d2 = dim.getWidth();

          Font f=new Font("TAB-Anna",4,18);
          static JButton but = new JButton("Ok");
          static JButton but1=new JButton("Exit");
          static JPanel p1=new JPanel();
          static JPanel p2= new JPanel();
          static JPanel p3= new JPanel();
          static JScrollPane scrollpane=new JScrollPane(tf2);

	      static ByteMeth me=new ByteMeth();
          static Stack s=new Stack();
          static byte[] thth = {20,20};

		  static boolean splitCompletedInPlural = false,splitCompletedInAi=false;

    	//byte itam[]={3,8,1,23};byte[] iv={27};byte y[]={24};byte in[]={3,31};byte tt []=tabconvert2.convert("��");

		  			  static byte[] im=tabconvert2.convert("�");
		  			  static byte[] ink=tabconvert2.convert("�");
		  			  static byte[] odu=tabconvert2.convert("�");
		  			  static byte[] odu1 = {11,18,5};
		  			  static byte tt []=tabconvert2.convert("��");
		  			  static byte ai[]={9};static byte u[]={5};static byte aa[]={2};
		  			  static byte th[]={20};static byte kk[]={14};
		  			  static byte il[]={3,26};static byte aal[]={2,26};
		  			  static byte itam[]={3,18,1,23};
		  			  static byte ukku[]={5,14,14,5};static byte irku[]={3,30,14,5};
		  			  static byte kku[]={14,14,5};
		  			  static byte akku[]={1,14,14,5};static byte akk[]={1,14,14};
		  			  static byte ukk[]={5,14,14};static byte in[]={3,31};static byte um[]={5,23};
		  			  static byte kal[]={14,1,29};static byte ikkal[]={14,14,1,29};
		  	    	  static byte ul[]={5,29};static byte o[]={11};static byte ae[]={8};
		  			  static byte iv[]={27};static byte y[]={24};
		  			  static byte an[]={2,31};
		        	  static byte irunthu[]=tabconvert2.convert("޼");
		        	  static byte ilirunthu[]=tabconvert2.convert("����");
		        	  static byte itamirunthu[]=tabconvert2.convert("���");
		  			  static byte utan[]=tabconvert2.convert("����");
		  			  static byte akka[]={2,14,1};static byte athu[]={1,20,5};
		  			  static byte udaya[]={5,18,9,24,1};static byte thaan[]={20,2,31};
		  			  static byte ukkaaga[]=tabconvert2.convert("����");


  public noun5_temp()
        {
              super("MAnalyser - Noun");
	     	  tf1.setFont(f);
              tf2.setFont(f);
              p1.add(tf1);
              p3.add(but);
              p3.add(but1);
              setSize(600,400);
              setIconImage(img);
              setLocation(((int)d1-400)/2,((int)d2-600)/2);


		   	  /*tf1.addItem("");
              tf1.addItem("�����");
              tf1.addItem("ܬ������");
              tf1.addItem("����������");
              tf1.addItem("���������");
              tf1.addItem("������");
              tf1.addItem("�������");
              tf1.addItem("����������");
              tf1.addItem("���������");
              tf1.addItem("������������");
              tf1.addItem(" ������������    ");*/
              but.addActionListener(this);
              but1.addActionListener(this);
              getContentPane().add(p1,"North");
              getContentPane().add(scrollpane,"Center");
              getContentPane().add(p3,"South");
              show();
              addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(1);}});

           }


public static void sete(String str)
{
	System.out.println("str in settext : "+str);
	tf1.addItem(str);

}

public void actionPerformed(ActionEvent ae)
{
		if(ae.getSource()==but1)
			{
				this.dispose();
				/*HSpellchecker spell = new HSpellchecker();
				spell.show();*/
			}
			//setVisible(false);
		if(ae.getSource()==but)
		{
				String str1=((String)tf1.getSelectedItem()).trim();
				checkall(str1);
				String temp1="";
				while (!s.empty())
					 temp1=temp1+"\n\t"+s.pop().toString();
				int len4=temp1.length();
				String temp2="	"+temp1.substring(0,len4);
				tf2.setText(temp2);
		}
}


public static Stack split(String strin)
{
	checkall(strin);
	return s;
} // end of function split


public static Stack checkall(String str1)
{
		ByteMeth me=new ByteMeth();
		byte h[]=tabconvert2.convert(str1);byte tt []=tabconvert2.convert("��");
		byte g[]=new byte[h.length];byte ai[]={9};
		byte tmp1[]=new byte[h.length];byte th[]={20};byte kk[]={14};
		byte il[]={3,26};byte aal[]={2,26};byte itam[]={3,18,1,23};
		byte ukku[]={5,14,14,5};byte kku[]={14,14,5};byte ukk[]={5,14,14};byte in[]={3,31};byte kal[]={14,1,29};
	    byte ul[]={5,29};byte[] iv={27};byte y[]={24};byte an[]={2,31};
      	byte irunthu[]=tabconvert2.convert("޼");
		byte utan[]=tabconvert2.convert("����");
		byte akka[]={2,14,1};byte athu[]={1,20,5};
		byte udaya[]={5,18,9,24,1};
		//System.out.println("byte equivalent :" + " \n");
		String spchk;
		/*for(int w=0;w<h.length;w++)
			System.out.println(h[w]);*/

		if(check_aa_o_ae(h))
			{
				System.out.println("i am inside check_o_ae"+ str1);
				return s;
			}
		if(checkil(h))
			{
				System.out.println("i am inside checkil"+ str1);
				return s;
			}
		if(checkin(h))
			{
				System.out.println("i am inside checkin"+ str1);
				return s;
			}
		else if(plural1(h))
			{
				System.out.println("i am inside plural1"+ str1);
				return s;
			}
		else if(plural(h))
			{
				System.out.println("i am inside plural"+ str1);
				return s;
			}
		else if(checkitam(h))
			{
				System.out.println("i am inside checkitam"+ str1);
				return s;
			}
		else if(check_utan(h))
			{
				System.out.println("i am inside checkitam"+ str1);
				return s;
			}
        else if(check_athu(h))
			{
				System.out.println("i am inside checkathu"+ str1);
				return s;
			}
		else if(checkThaan(h))
			{
				System.out.println("i am inside check_thaan"+ str1);
				return s;
			}
	     else if(check_udaya(h))
			{
				System.out.println("i am inside checkudaya"+ str1);
				return s;
			}
	     else if(check_ai(h))
			{
				System.out.println("i am inside checkai"+ str1);
				return s;
			}
		else if(checkUm(h))
			{
				System.out.println("i am inside checkum"+ str1);
				return s;
			}
	     else if(check_aal(h))
			{
				System.out.println("i am inside checkall twise"+ str1);
				return s;
			}
		else if(check_akku(h))
			{
				System.out.println("i am inside checkakku"+ str1);
				return s;
			}
		else if(check_ukku(h))
			{
				System.out.println("i am inside checkukku"+ str1);
				return s;
			}
		else if(check_irku(h))
			{
				System.out.println("i am inside checkirku"+ str1);
				return s;
			}
		else if(check_kku(h))
			{
				System.out.println("i am inside checkkku"+ str1);
				return s;
			}
		else if(check_ukkaaga(h))
			{
				System.out.println("i am inside check_ukkaaga"+ str1);
				return s;
			}
		else if(check_akka(h))
			return s;
		else if(check_odu1(h))
			return s;

		else if(check_ilirunthu(h))
			{
				System.out.println("i am inside checkirnuthu"+ str1);
				return s;
			}
		else if(check_itamirunthu(h))
			{
				System.out.println("i am inside checkirnuthu"+ str1);
				return s;
			}
		else
		{
			try
			{
				System.out.println("i am in call to Spellchecker");
				spchk = SpchkNoun.checker(tabconvert2.revert(h));
				System.out.println("sugg in checkall :" + spchk);
				s = checkagain(spchk);
			}
			catch(Exception e)
			{
				System.out.println("exception");
				e.printStackTrace();
			}
			return s;
		}

} // end of checkall


public static Stack checkagain(String strin)
{

	ByteMeth me=new ByteMeth();
	byte h[]=tabconvert2.convert(strin);
	if(check_aa_o_ae(h))
		return s;
	if(checkil(h))
		return s;
	if(checkin(h))
		return s;
	if(checkitam(h))
		return s;
	if(check_utan(h))
		return s;
	if(check_athu(h))
		return s;
	if(checkThaan(h))
		return s;
	if(plural(h))
		return s;
	if(check_udaya(h))
		return s;
	if(check_ai(h))
		return s;
	if(checkUm(h))
		return s;
	if(check_aal(h))
		return s;
	if(check_akku(h))
		return s;
	if(check_ukku(h))
		return s;
	if(check_irku(h))
		return s;
	if(check_kku(h))
		return s;
	if(check_ukkaaga(h))
		return s;
	if(check_akka(h))
		return s;
	if(check_odu1(h))
		return s;
	if(plural1(h))
		return s;
	if(plural(h))
		return s;
	if(check_ilirunthu(h))
		return s;
	if(check_itamirunthu(h))
		return s;
	else
		{
			Stack st=checkall(strin);
			return st;
		}


} // end of function checkagain


public static boolean check_aa_o_ae(byte h1[])
{

	byte m[]={23},str[],str1[],str2[],str3[],y[]={24};
	byte u[]={5},th[]={20},a[]={1},zh[]={28},ll[]={26,26},l[]={26};
	byte ll1[]={29,29},l1[]={29},rr1[]={30,30},mm[]={23};
	byte nn[]={31,31},nn1[]={19,19};
	if(h1.length < ae.length)
		return false;
	if(me.endswith(h1,aa)||me.endswith(h1,ae)||me.endswith(h1,o))
	{
		System.out.println("aa_o_ae_ok");
		byte rem[]=me.subarray(h1,h1.length-1,h1.length);
		str = me.subarray(h1,0,(h1.length-1));
		s.push("\n"+tabconvert2.revert(rem)+"<clitic>");
		if(check_ai(str))
			return true;
		if(check_aal(str))
			return true;
		if(checkil(str))
			return true;
		if(checkUm(str))
			return true;
		if(checkThaan(str))
			return true;
		if(me.endswith(str,tabconvert2.convert("޼��")))
		{
			str1 = me.addarray(str,u);
			if(check_ilirunthu(str1))
				return true;
			if(check_itamirunthu(str1))
				return true;
		}
		if(me.endswith(str,iv))
		{
			str1 = me.subarray(str,0,(str.length-1));
			if(check_ukkaaga(str1))
				return true;
			else if(check_akka(str1))
				return true;
			else if(me.endswith(str1,a)||me.endswith(str1,aa)||me.endswith(str1,zh))
			{
				str2 = me.addarray(str,u);
				s.push("\n"+tabconvert2.revert(str2)+"(noun)");
				//WordChecker.SplitOk = true;
				return true;
			}
			else
			{
				s.push("\n"+tabconvert2.revert(str1)+"(noun)");
				//WordChecker.SplitOk = true;
				return true;
			}
		}
		if(me.endswith(str,y))
		{
			str1 = me.subarray(str,0,(str.length-1));
			if(check_ai(str1))
				return true;
			else
			{
				s.push("\n"+tabconvert2.revert(str1)+"(noun)");
				//WordChecker.SplitOk = true;
				return true;
			}
		}
		if(!plural(str))
		{
			if(me.endswith(str,y))
			{
				str1 = me.subarray(str,0,(str.length-1));
				s.push("\n"+tabconvert2.revert(str1)+"(noun)");
				//WordChecker.SplitOk = true;
				return true;
			}
			else if(me.endswith(str,ll)||me.endswith(str,ll1)||me.endswith(str,mm)||me.endswith(str,nn)||me.endswith(str,nn1))
			{
				byte b[] = me.subarray(str,0,str.length-1);
				s.push("\n"+tabconvert2.revert(b)+"(noun)");
				//WordChecker.SplitOk = true;
				System.out.println("noun value :" + tabconvert2.revert(b));
				return true;
			}
			else if(me.endswith(str,m)||me.endswith(str,l))
			{
				s.push("\n"+tabconvert2.revert(str)+"(noun)");
				//WordChecker.SplitOk = true;
				return true;
			}
			else if(me.endswith(str,th))
			{
				str1 = me.addarray(str,u);
				s.push("\n"+tabconvert2.revert(str1)+"(noun)");
				//WordChecker.SplitOk = true;
				return true;
			}
			else if(str.length > 4 && me.endswith(str,tabconvert2.convert("����")))
			{
				str1 = me.addarray(str,u);
				s.push("\n"+tabconvert2.revert(str1)+"(noun)");
				//WordChecker.SplitOk = true;
				return true;
			}
			else
			{
				s.push("\n"+tabconvert2.revert(str)+"(noun)");
				//WordChecker.SplitOk = true;
				return true;
			}

		}
		else
			{
				s.push("\n"+tabconvert2.revert(str)+"(noun)");
				//WordChecker.SplitOk = true;
				return(true);
			}
	}

	else
		return(false);
} // end of function check_aa_o_ae



public static boolean checkThaan(byte h1[])
{
	int strLen = thaan.length;
	byte str1[];
	if(h1.length < strLen)
		return false;
	else if(me.endswith(h1,thaan))
	{
		System.out.println("thaan_ok");
		byte rem[]=me.subarray(h1,h1.length-strLen,h1.length);
		byte str[] = me.subarray(h1,0,(h1.length-strLen));
		s.push("\n"+tabconvert2.revert(rem)+"<clitic> ");
		if(me.endswith(str,th))
			str1 = me.subarray(str,0,str.length-1);
		else
		{
			str1 = str;
			/*for(int i=0;i<str.length;i++)
				str1[i] = str[i];*/
		}
		if(check_ai(str1))
			return true;
		else if(checkil(str1))
			return true;
		else if(check_aal(str1))
			return true;
		else if(plural(str1))
			return true;
		else if(plural1(str1))
			return true;
		else if(check_ukku(str1))
			return true;
		else if(check_ukkaaga(str1))
			return true;
		else if(check_akka(str1))
			return true;
		else if(checkUm(str1))
			return true;
		else if(check_akku(str1))
			return true;
		else if(check_odu1(str1))
			return true;
		else if(check_ilirunthu(str1))
			return true;
		else if(check_itamirunthu(str1))
			return true;
		else if(check_aa_o_ae(str1))
			return true;
		else
		{
			s.push("\n"+tabconvert2.revert(str1)+"(noun)");
			//WordChecker.SplitOk = true;
			return true;
		}
	}
	else
		return false;
}	// end of function checkThaan

/*public static boolean check_irunthu(byte h1[])
{
	//System.out.println("i am inside function check_irunthu");
	if(me.endswith(h1,irunthu))
		{
			try
			{
				System.out.println("irunthu_ok");
				byte r[]=me.subarray(h1,h1.length-6,h1.length);
				byte a[]=me.subarray(h1,0,(h1.length-irunthu.length));
				s.push("\n"+"       "+tabconvert2.revert(r)+"<case>");
				checkil(a);
				checkitam(a);
				System.out.println("str a:"+ tabconvert2.revert(a));
				String spchk = SpchkNoun.checker(tabconvert2.revert(a));
				System.out.println("spchk:" + spchk);
				checkil(tabconvert2.convert(spchk));
				checkitam(tabconvert2.convert(spchk));
			}
			catch(Exception e)
			{
				System.out.println("exception");
				e.printStackTrace();
			}
			return true;
		}  // end of if

	else
		return false;

} */// ens of function check_irunthu

public static boolean check_ilirunthu(byte h1[])
{
	//System.out.println("i am inside function check_irunthu");
	if(h1.length < ilirunthu.length)
		return false;
	if(me.endswith(h1,ilirunthu))
		{
			byte r[]=me.subarray(h1,h1.length-ilirunthu.length,h1.length);
			byte a[]=me.subarray(h1,0,(h1.length-ilirunthu.length));
			s.push("\n"+"       "+tabconvert2.revert(r)+"<case>");
			if(plural(a))
				return true;
			checkdata(a,ilirunthu);
			return true;
			/*try
			{
				System.out.println("irunthu_ok");
				byte r[]=me.subarray(h1,h1.length-6,h1.length);
				byte a[]=me.subarray(h1,0,(h1.length-irunthu.length));
				s.push("\n"+"       "+tabconvert2.revert(r)+"<case>");
				System.out.println("str a:"+ tabconvert2.revert(a));
				String spchk = SpchkNoun.checker(tabconvert2.revert(a));
				System.out.println("spchk:" + spchk);
				checkil(tabconvert2.convert(spchk));
				checkitam(tabconvert2.convert(spchk));
			}
			catch(Exception e)
			{
				System.out.println("exception");
				e.printStackTrace();
			}
			return true;*/
		}  // end of if

	else
		return false;

} // ens of function check_ilirunthu

public static boolean check_itamirunthu(byte h1[])
{
	if(h1.length < itamirunthu.length)
		return false;
	if(me.endswith(h1,itamirunthu))
		{
			byte r[]=me.subarray(h1,h1.length-itamirunthu.length,h1.length);
			byte a[]=me.subarray(h1,0,(h1.length-itamirunthu.length));
			s.push("\n"+tabconvert2.revert(r)+"<case>");
			if(plural(a))
				return true;
			checkdata(a,itamirunthu);
			return true;
			/*try
			{
				System.out.println("irunthu_ok");
				byte r[]=me.subarray(h1,h1.length-6,h1.length);
				byte a[]=me.subarray(h1,0,(h1.length-irunthu.length));
				s.push("\n"+"       "+tabconvert2.revert(r)+"<case>");
				System.out.println("str a:"+ tabconvert2.revert(a));
				String spchk = SpchkNoun.checker(tabconvert2.revert(a));
				System.out.println("spchk:" + spchk);
				checkil(tabconvert2.convert(spchk));
				checkitam(tabconvert2.convert(spchk));
			}
			catch(Exception e)
			{
				System.out.println("exception");
				e.printStackTrace();
			}
			return true;*/
		}  // end of if

	else
		return false;

} // ens of function check_itamirunthu


public static boolean check_athu(byte h1[])
{
	boolean flag1,flag2;
	String spchk;
	if(h1.length < athu.length)
		return false;
	if(me.endswith(h1,athu))
			{
				byte r[]=me.subarray(h1,h1.length-3,h1.length);
				byte a[]=me.subarray(h1,0,h1.length-athu.length);
				s.push("\n"+tabconvert2.revert(r)+"<case>");
				//String spchk = SpchkNoun.checker(tabconvert2.revert(a));
				flag1=plural(a);
				flag2=checkdata(a,athu);
				try
				{
					if(!flag1 && !flag2)
						{
							System.out.println("i am inside if");
							spchk = SpchkNoun.checker(tabconvert2.revert(a));
							checkagain(spchk);
						}
				}
				catch(Exception e)
				{
					System.out.println("exception");
					e.printStackTrace();
				}
				return(true);
		 	}
		else
			return(false);

} // end of function check_athu

public static boolean check_udaya(byte h1[])
{
	String spchk;
	boolean issplit1 = false,issplit2 = false;
	if(h1.length < udaya.length)
		return false;
	 if(me.endswith(h1,udaya))
	 {
		System.out.println("udaya");
		byte r[]=me.subarray(h1,h1.length-5,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-udaya.length));
		s.push("\n"+"     "+tabconvert2.revert(r)+"<case>");
		issplit1 = plural(a);
		if(issplit1 == false)
			issplit2 = checkdata(a,udaya);
		if(issplit1 || issplit2)
			return true;
		else
		{
			try
			{
				/*if(!plural(a))
				{*/
					spchk = SpchkNoun.checker(tabconvert2.revert(a));
					checkagain(spchk);
				//}
			}
			catch(Exception e)
			{
				System.out.println("exception");
				e.printStackTrace();
			}
			checkdata(a,udaya);
			return(true);
		}
	}
		else
			return(false);

} // end of function check_udaya

public static boolean check_ai(byte h1[])
{
	String spchk;
	if(h1.length < ai.length)
		return false;
	if(me.endswith(h1,ai))
	{
		System.out.println("ai_ok");
		byte r[]=me.subarray(h1,h1.length-1,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-ai.length));
		s.push("\n"+tabconvert2.revert(r)+"<case>");
		System.out.println("r value :" + tabconvert2.revert(r));
		plural(a);
		if(!splitCompletedInPlural)
		{
			if(checkdata(a,ai))
			{
				splitCompletedInAi = true;
				System.out.println("splitCompletedInAi value :" + splitCompletedInAi);
			}
			else
			{
				splitCompletedInAi = false;
				System.out.println("splitCompletedInAi value :" + splitCompletedInAi);
			}
		}
	 	return(true);
	}
	else
		return(false);

} // end of function check_ai

public static boolean check_aal(byte h1[])
{

	boolean flag1,flag2;
	String spchk;
	if(h1.length < aal.length)
		return false;
	if(me.endswith(h1,aal))
	{
		System.out.println("aal_ok");
		byte r[]=me.subarray(h1,h1.length-2,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-aal.length));
		s.push("\n"+tabconvert2.revert(r)+"<case>");
		if(!plural(a))
		{
			if(!checkdata(a,il))
			{
				try
				{
					spchk = SpchkNoun.checker(tabconvert2.revert(a));
					//System.out.println(" string in check_aaga after call tp spchk : " + spchk);
					checkagain(spchk);

				}
				catch(Exception e)
				{
					System.out.println("exception");
					e.printStackTrace();
				}
			}
		}
		return(true);
	}

	else
		return(false);
} // end of function check_aal

public static boolean check_ukku(byte h1[])
{

	boolean flag1,flag2,splitCompleted1=false,splitCompleted2=false;
	byte ukku1[]={5,14,14,6};
	String spchk;
	if(h1.length < ukku.length)
		return false;
	if(me.endswith(h1,ukku)||me.endswith(h1,ukku1))
			{
				System.out.println("ukku1_ok");
			    byte r[]=me.subarray(h1,h1.length-4,h1.length);
		        byte a[]=me.subarray(h1,0,(h1.length-ukku.length));
		        s.push("\n"+tabconvert2.revert(ukku)+"<case>");
				splitCompleted1 = plural1(a);
				System.out.println("splitCompleted value" + splitCompleted1);
				if(splitCompleted1)
					{
						System.out.println("plural1 success");
						return true;
					}
				else
					{
						System.out.println("plural success");
						splitCompleted2 = plural(a);
					}
				if(!splitCompleted2)
						checkdata(a,ukku);
				/*try
				{spchk = SpchkNoun.checker(tabconvert2.revert(a));
				plural(tabconvert2.convert(spchk));
				checkdata(tabconvert2.convert(spchk));}
				catch(Exception e)
				{
					System.out.println("exception");
					e.printStackTrace();
				}*/

				return(true);
			}
		else
			return(false);

} // end of function check_ukku


public static boolean check_irku(byte h1[])
{

	boolean flag1,flag2;
	String spchk;
	if(h1.length < irku.length)
		return false;
	if(me.endswith(h1,irku))
			{
				System.out.println("irku_ok");
			    byte r[]=me.subarray(h1,h1.length-4,h1.length);
		        byte a[]=me.subarray(h1,0,(h1.length-irku.length));
		        s.push("\n"+tabconvert2.revert(r)+"<case>");
				plural(a);
				checkdata(a,irku);
				/*try
				{spchk = SpchkNoun.checker(tabconvert2.revert(a));
				plural(tabconvert2.convert(spchk));
				checkdata(tabconvert2.convert(spchk));}
				catch(Exception e)
				{
					System.out.println("exception");
					e.printStackTrace();
				}*/

				return(true);
			}
		else
			return(false);

} // end of function check_irku

public static boolean check_kku(byte h1[])
{
		String spchk;
	if(h1.length < kku.length)
		return false;
	if(me.endswith(h1,kku))
			{
				System.out.println("kku_ok");
			    byte r[]=me.subarray(h1,h1.length-3,h1.length);
		        byte a[]=me.subarray(h1,0,(h1.length-kku.length));
				s.push("\n"+tabconvert2.revert(r)+"<case>");
				plural(a);
				/*try
				{
					if(!plural(a))
						{
							spchk = SpchkNoun.checker(tabconvert2.revert(a));
							checkagain(spchk);
						}
				}
				catch(Exception e)
				{
					System.out.println("exception");
					e.printStackTrace();
				}*/
				checkdata(a,kku);
			    /*if(me.endswith(a,thth)||me.endswith(a,tt)||me.endswith(a,in)||me.endswith(a,iv)||me.endswith(a,y))
					System.out.println("sorry");
				else
					s.push("\n"+"      "+tabconvert2.revert(a)+" <noun>");*/
					return(true);
		}
		else
			return(false);

} // end of function check_kku


public static boolean check_akku(byte h1[])
{
	String spchk;
	if(h1.length < akku.length)
		return false;
	if(me.endswith(h1,akku))
		{
			System.out.println("akku_ok");
			byte r[]=me.subarray(h1,h1.length-akku.length,h1.length);
			byte a[]=me.subarray(h1,0,(h1.length-akku.length));
			s.push("\n"+tabconvert2.revert(r)+"<case>");
			s.push("\n"+tabconvert2.revert(a)+"(noun)");
			return(true);
		}
		else
			return(false);

} // end of function check_akku

public static boolean check_akka(byte h1[])
{
	String spchk;
	if(h1.length < akka.length)
		return false;
	if(me.endswith(h1,akka))
			{
				System.out.println("akka_ok");
			    byte r[]=me.subarray(h1,h1.length-3,h1.length);
		        byte a[]=me.subarray(h1,0,(h1.length-akka.length));
		        s.push("\n"+tabconvert2.revert(r)+"<case>");
		        //check_ukku(a);
		        if(me.endswith(a,akk))
				{
					byte suf[]=me.addarray(a,u);
					if(check_akku(suf))
						return true;
				}
		        if(!plural(a))
		        	checkdata(a,akka);


		        try
				{
					spchk = SpchkNoun.checker(tabconvert2.revert(a));
					System.out.println(" string in check_aaga after call tp spchk : " + spchk);
					checkagain(spchk);

				}
				catch(Exception e)
				{
					System.out.println("exception");
					e.printStackTrace();
				}
				return(true);
			}
		else
			return(false);

} // end of function check_akka

public static boolean check_ukkaaga(byte h1[])
{
	String spchk;
	if(h1.length < ukkaaga.length)
		return false;
	boolean isSplit = false;
	if(me.endswith(h1,ukkaaga))
			{
				System.out.println("ukkaaga_ok");
			    byte r[]=me.subarray(h1,h1.length-ukkaaga.length,h1.length);
		        byte a[]=me.subarray(h1,0,(h1.length-ukkaaga.length));
		        s.push("\n"+tabconvert2.revert(r)+"<case>");
		        //isSplit = checkdata(a,ukkaaga);
		        if(!plural(a))
		        	isSplit = checkdata(a,ukkaaga);
				if(!isSplit)
				{
					try
					{
						spchk = SpchkNoun.checker(tabconvert2.revert(a));
						System.out.println(" string in check_aaga after call tp spchk : " + spchk);
						checkagain(spchk);

					}
					catch(Exception e)
					{
						System.out.println("exception");
						e.printStackTrace();
					}
				}
				return(true);
			}
		else
			return(false);

} // end of function check_ukkaaga


public static boolean check_odu1(byte h1[])
{
	String spchk;
	if(h1.length < odu1.length)
		return false;
	if(me.endswith(h1,odu1))
			{
				System.out.println("odu1_ok");
			    byte r[]=me.subarray(h1,h1.length-3,h1.length);
		        byte a[]=me.subarray(h1,0,(h1.length-odu1.length));
		        s.push("\n"+tabconvert2.revert(r)+"<case>");
				plural(a);
				if(!splitCompletedInPlural)
			    	checkdata(a,odu1);

				/*if(me.endswith(a,thth)||me.endswith(a,tt)||me.endswith(a,in)||me.endswith(a,iv)||me.endswith(a,y))
					System.out.println("sorry");
				else
						s.push("\n"+"       "+tabconvert2.revert(a)+" <noun>");*/
				return(true);
		}
		else
			return(false);

} // end of function check_odu1

public static boolean check_utan(byte h1[])
{
	String spchk;
	if(h1.length < utan.length)
		return false;
	if(me.endswith(h1,utan))
			{
				System.out.println("utan_ok");
			    byte r[]=me.subarray(h1,h1.length-4,h1.length);
		        byte a[]=me.subarray(h1,0,(h1.length-utan.length));
		        s.push("\n"+"       "+tabconvert2.revert(r)+"<case>");
		       try
			   {
				   if(!plural(a))
					{
						spchk = SpchkNoun.checker(tabconvert2.revert(a));
						checkagain(spchk);
					}
				}
				catch(Exception e)
				{
					System.out.println("exception");
					e.printStackTrace();
				}
			    checkdata(a,utan);
				/*if(me.endswith(a,thth)||me.endswith(a,tt)||me.endswith(a,in)||me.endswith(a,iv)||me.endswith(a,y))
					System.out.println("sorry");
				else
					s.push("\n"+"     "+tabconvert2.revert(a)+"  <noun>");*/
				return(true);
		}
		else
			return(false);

} // end of function check_utan

public static boolean checkitam(byte str1[])
{
	String spchk;
	if(str1.length < itam.length)
		return false;
	if(me.endswith(str1,itam))
		{
			System.out.println("itam_ok");
			byte r[]=me.subarray(str1,str1.length-4,str1.length);
			byte a[]=me.subarray(str1,0,(str1.length-itam.length));
		   	s.push("\n"+tabconvert2.revert(r)+"<case>");
		   	if(plural(a))
		   		return true;
		   	checkdata(a,itam);
		   	return true;

		   	/*try
			{
				if(!plural(a))
				{
					spchk = SpchkNoun.checker(tabconvert2.revert(a));
					checkagain(spchk);
				}
			}
			catch(Exception e)
			{
				System.out.println("exception");
				e.printStackTrace();
			}*/

		/*	if(me.endswith(a,thth)||me.endswith(a,tt)||me.endswith(a,in)||me.endswith(a,iv)||me.endswith(a,y))
				System.out.println("sorry");
			else
				s.push("\n"+"    "+tabconvert2.revert(a)+"(noun)");*/
					}
	else
		return false;
} // end of function checkitam

public static boolean checkil(byte str2[])
{
		String spchk;
		if(str2.length < il.length)
			return false;
		if(me.endswith(str2,il))
			{
				System.out.println("il_ok");
	            byte r[]=me.subarray(str2,str2.length-2,str2.length);
                byte a[]=me.subarray(str2,0,(str2.length-il.length));
		   	    s.push("\n"+tabconvert2.revert(r)+"<case>");
		   	    if(!plural(a))
		   	    {
					if(!checkdata(a,il))
					{
						try
						{
							spchk = SpchkNoun.checker(tabconvert2.revert(a));
							//System.out.println(" string in check_aaga after call tp spchk : " + spchk);
							checkagain(spchk);

						}
						catch(Exception e)
						{
							System.out.println("exception");
							e.printStackTrace();
						}
					}
				}
			    return(true);
		}
		else
		{
			return(false);

		}

	}

public static boolean checkin(byte str2[])
{
		String spchk;
		if(str2.length < in.length)
			return false;
		if(me.endswith(str2,in))
			{
				System.out.println("in_ok");
	            byte r[]=me.subarray(str2,str2.length-2,str2.length);
                byte a[]=me.subarray(str2,0,(str2.length-il.length));
		   	    s.push("\n"+tabconvert2.revert(r)+"<case>");
		   	    if(!plural(a))
				{
					if(!checkdata(a,il))
					{
						try
						{
							spchk = SpchkNoun.checker(tabconvert2.revert(a));
							//System.out.println(" string in check_aaga after call tp spchk : " + spchk);
							checkagain(spchk);

						}
						catch(Exception e)
						{
							System.out.println("exception");
							e.printStackTrace();
						}
					}
				}
	            return(true);
		}
		else
		{
			return(false);

		}

}	// end of function checkin

public static boolean checkarru(byte str[])
{
	byte b[],b1[],arru[] = {1,30,30,5},iv[]={27},m[]={23},ivv[]={3,27},avv[]={1,27},evv[]={7,27},y[]={24};
	if(str.length < arru.length)
		return false;
	if(me.endswith(str,arru))
	{
		byte suf[]=me.subarray(str,str.length-arru.length,str.length);
		byte rem[]=me.subarray(str,0,(str.length-arru.length));
		s.push("\n"+tabconvert2.revert(suf)+"<oblique suffix>");
		if(me.endswith(rem,ivv)||me.endswith(rem,evv)||me.isequal(rem,avv))
		{
			b = me.addarray(rem,ai);
			s.push("\n"+tabconvert2.revert(b)+"(noun)");
			System.out.println("noun in checkarru 1:" + tabconvert2.revert(b));
			return true;
		}
		if(me.isequal(rem,tabconvert2.convert("�������")))
		{
			b=me.subarray(rem,0,rem.length-1);
			b1 = me.addarray(b,m);
			s.push("\n"+tabconvert2.revert(b1)+"(noun)");
			System.out.println("noun in checkarru 2:" + tabconvert2.revert(b1));
			return true;
		}
		if(me.endswith(rem,iv)||me.endswith(rem,y))
		{
			byte a1[]=me.subarray(str,0,(rem.length-1));
			System.out.println("noun in checkarru 3:" + tabconvert2.revert(a1));
			s.push("\n"+tabconvert2.revert(a1)+"(noun)");
		}
		return true;
	}
	else
		return false;


}	// end of function checkarru

public static boolean checkUm(byte str2[])
{
		String spchk;
		byte r1[]={30},u[]={5},b[],akk[]={1,14,14};
		if(str2.length < um.length)
			return false;
		boolean splitCompleted1=false,splitCompleted2=false,status=false;//,splitCompletedInAi=false;
		if(me.endswith(str2,um))
			{
				System.out.println("Um_ok");
	            byte r[]=me.subarray(str2,str2.length-um.length,str2.length);
                byte a[]=me.subarray(str2,0,(str2.length-um.length));
		   	    s.push("\n"+tabconvert2.revert(r)+"<clitic>");
		   	    if(check_aal(a))
		   	    	return true;
		   	    if(checkil(a))
		   	    	return true;
		   	    if(check_akku(a))
		   	    	return true;
		   	    if(plural(a))
		   	    	return true;
		   	    if(plural1(a))
		   	    	return true;
		   	    if(me.endswith(a,akk))
		   	    {
					b = me.addarray(a,u);
					if(check_akku(b))
						return true;
				}
		   	    if(me.endswith(a,r1))
		   	    {
					b= me.addarray(a,u);
					System.out.println("noun in checkUm 0:" + tabconvert2.revert(b));
					s.push("\n"+tabconvert2.revert(b)+"(noun)");
					return true;
				}
		   	    if(me.endswith(a,iv))
		   	    {
					byte a1[]=me.subarray(a,0,(a.length-1));
					System.out.println("noun in checkUm 1:" + tabconvert2.revert(a1));
		   	    	s.push("\n"+tabconvert2.revert(a1)+"(noun)");
		   	    	return true;
				}
				else if(me.endswith(a,y))
				{
					byte a2[]=me.subarray(a,0,(a.length-1));
				    status = check_ai(a2);
					if(splitCompletedInPlural|| splitCompletedInAi) // if string is completely split in checkai
					{
						System.out.println("\t\tboth true");
						return true;
					}
					else
					{
						System.out.println("i am in else1 of checkUm");
						s.push("\n"+tabconvert2.revert(a2)+"(noun)");
						return true;
					}
				}
				else
				{
					System.out.println("i am in else2 of checkUm");
					s.push("\n"+tabconvert2.revert(a)+"(noun)");
					return true;
				}
			}
			else
				return false;


}	// end of function checkUm


public static boolean checkdata(byte[] str,byte[] casemarker)
{
	    ByteMeth me=new ByteMeth();
        byte th[]={20},n[]={31},p[]={22};byte k[]={14};byte ku[]={14,5};byte du []=tabconvert2.convert("�");
		byte tt[]={18,18};
	    byte in[]={3,31};byte kal[]={14,1,29};   byte[] vu=tabconvert2.convert("�");
		byte it[]={18};   byte ul[]={5,29};byte[] iv={27};byte y[]={24};byte an[]={2,31};
		byte[] thth = tabconvert2.convert("��"); byte[] im=tabconvert2.convert("�");
		byte ir[]={30};byte irir[]={30,30},arr[]={1,30,30};byte u[]={5};
		byte nn[]={31,31},nn1[]={19,19},e[]={3},ee[]={4},l[]={26},ll[]={26,26},ll1[]={29,29},l1[]={29},rr1[]={30,30},mm[]={23};
		System.out.println(" I ama inside checkdata" + tabconvert2.revert(str));

		for(int i=0;i<str.length;i++)
			System.out.println("byte[" + i + "]"+ str[i]);

	  	if(me.isequal(casemarker,kku))
	  	{
			System.out.println("................12");
			if(me.endswith(str,ai)||me.endswith(str,e)||me.isequal(str,ee)||me.endswith(str,ee))
			{
				s.push("\n"+tabconvert2.revert(str)+"(noun)");
				return true;
			}
			else
				return true;
		}
		else if(me.isequal(casemarker,itamirunthu))
		{
			System.out.println("................13");
			if(me.endswith(str,thth))
			{
				byte b[]=me.subarray(str,0,(str.length-2));
			    byte e1[]=me.addarray(b,im);
			    s.push("\n"+tabconvert2.revert(thth)+"(Oblique)");
			 	s.push("\n"+tabconvert2.revert(e1)+"(noun)");
				return true;
			}
			else if(me.endswith(str,iv)||me.endswith(str,y))
			{
				byte b[]=me.subarray(str,0,(str.length-1));
				s.push("\n"+tabconvert2.revert(b)+"(noun)");
				return true;
			}
			else
			{
				s.push("\n"+"    "+tabconvert2.revert(str)+"(noun)");
				return true;
			}
		}	// end in case of itamirunthu
		else if(me.endswith(str,ll)||me.endswith(str,ll1)||me.endswith(str,mm)||me.endswith(str,nn)||me.endswith(str,nn1))
		{
			byte b[] = me.subarray(str,0,str.length-1);
			s.push("\n"+tabconvert2.revert(b)+"(noun)");
			System.out.println("noun value :" + tabconvert2.revert(b));
			return true;
		}
	  	else if(me.endswith(str,e)||me.endswith(str,ee)||me.endswith(str,l1)/*||me.endswith(str,y)*/||me.endswith(str,ai))
		{
			s.push("\n"+tabconvert2.revert(str)+"(noun)");
			return true;
		}
	  	else if(me.endswith(str,in))
		{
			 System.out.println("................0");
		     byte b[]=me.subarray(str,0,(str.length-2));
			 if(me.endswith(b,y))
			 {
				 byte b2[]=me.subarray(b,0,(b.length-1));
				 s.push("\n"+"    "+tabconvert2.revert(in)+"(Euphonic)");
				 s.push("\n"+"    "+tabconvert2.revert(b2)+"(noun)");
				 return true;
		     }

			else if(me.endswith(b,irir)||me.endswith(b,it))
			{
				byte b3[]=me.subarray(b,0,(b.length-1));
				byte e1[]=me.addarray(b3,u);
				s.push("\n"+"    "+tabconvert2.revert(in)+"(Euphonic)");
				s.push("\n"+"    "+tabconvert2.revert(e1)+"(noun)");
				return true;
			}
			else if(me.endswith(b,ir))
			{
				byte e1[]=me.addarray(b,u);
				s.push("\n"+"    "+tabconvert2.revert(in)+"(Euphonic)");
			  	s.push("\n"+"    "+tabconvert2.revert(e1)+"(noun)");
			  	return true;
			}
			else if(me.endswith(b,thth))
			{

				 byte b1[]=me.subarray(b,0,(b.length-2));
				 byte e1[]=me.addarray(b1,im);
						   s.push("\n"+tabconvert2.revert(in)+"(Euphonic)");
					 s.push("\n"+tabconvert2.revert(thth)+"(Oblique)");
				 s.push("\n"+tabconvert2.revert(e1)+"(noun)");
				 return true;
			}
			else if(me.endswith(b,iv))
			{
				byte b1[]=me.subarray(b,0,(b.length-1));
				s.push("\n"+"    "+tabconvert2.revert(in)+"(Euphonic)");
			  	s.push("\n"+"    "+tabconvert2.revert(b1)+"(noun)");
			  return true;
			}
			else
			 	{

					return false;
				}

		} // end in case of in

		else if(me.endswith(str,k))
		{
			 System.out.println("................1");
			 byte b[]=me.subarray(str,0,(str.length-1));
			 byte e1[]=me.addarray(b,ku);
			 s.push("\n"+tabconvert2.revert(e1)+"(noun)");
			 return true;
		 }

	    else if(me.endswith(str,thth))
		 {
			 System.out.println("i am inside checkdata thth");
			 byte b[]=me.subarray(str,0,(str.length-2));
			 byte e1[]=me.addarray(b,im);
			 s.push("\n"+tabconvert2.revert(thth)+"(Oblique)");
			 s.push("\n"+tabconvert2.revert(e1)+"(noun)");
			 return true;
		 }

	     else if(me.endswith(str,tt))
		 {
			 System.out.println("................2");
			 byte b[]=me.subarray(str,0,(str.length-2));
			 byte e1[]=me.addarray(b,du);
			 s.push("\n"+tabconvert2.revert(e1)+"(noun)");
			 return true;
		}
		else if(me.endswith(str,iv))
		{
			  System.out.println("................3");
			  byte b[]=me.subarray(str,0,(str.length-1));
              s.push("\n"+tabconvert2.revert(b)+"(noun)");
              return true;
         }
         else if(me.endswith(str,l))
		 {
			byte rem[]=me.subarray(str,0,str.length-1);
			if(me.endswith(rem,tabconvert2.convert("��")))
				return false;
			System.out.println("................4");
			s.push("\n"+tabconvert2.revert(str)+"(noun)");
			return true;
         }
         /*else if(me.endswith(str,l))
		 {
		 	byte b[]=me.subarray(str,0,(str.length-1));
		 	byte e1[]=me.addarray(b,vu);
		    s.push("\n"+tabconvert2.revert(b)+"(noun)");
		    return true;
         }*/
		else if(me.endswith(str,y))
		 {
			System.out.println("................5");
			System.out.println("entered string :" + tabconvert2.revert(str));
			byte b[]=me.subarray(str,0,(str.length-1));
			s.push("\n"+tabconvert2.revert(b)+"(noun)");
			return true;
		}
		else if(me.endswith(str,n))
		{
			System.out.println("................6");
			s.push("\n"+tabconvert2.revert(str)+"(noun)");
			return true;
		}
		else if(me.endswith(str,arr))
		{
			System.out.println("................7");
			byte b[]= me.addarray(str,u);
			checkarru(b);
			return true;
		}
        else if(me.endswith(str,rr1)||me.endswith(str,p))
        {
			System.out.println("................8");
			byte b[]= me.addarray(str,u);
			s.push("\n"+tabconvert2.revert(b)+"(noun)");
			System.out.println("hai hai hai");
			return true;
		}
		else if(!me.endswith(str,y)&& !me.endswith(str,iv)&& !me.endswith(str,tt)&& !me.endswith(str,thth)&& !me.endswith(str,k))
		{
			if(me.isequal(str,tabconvert2.convert("���")))
				s.push("\n"+"����"+"(noun)");
			if(me.isequal(str,tabconvert2.convert("���")))
				s.push("\n"+"����"+"(noun)");
			System.out.println("................9");
			byte b[]= me.addarray(str,u);
			s.push("\n"+tabconvert2.revert(b)+"(noun)");
//			System.out.println("splitcompleted value in checkdata :"+ splitcompleted);
			return true;
		}

		else
		{
			if(!me.isequal(casemarker,il)||!me.isequal(casemarker,in)||!me.isequal(casemarker,aal))
				s.push("\n"+tabconvert2.revert(str)+"(noun)");
			return false;
		}
	/*}
	else
	 	return false;*/

}  // end of function checkdata

public static boolean checkCvcPattern(byte[] str1)
{
	byte r[]={25},ch[]={16},n[]={31},n1[]={19};
	byte lastLetter[]=me.subarray(str1,str1.length-1,str1.length);
	byte rem[]=me.subarray(str1,0,(str1.length-1));
	if(me.endswith(rem,r)||me.endswith(rem,ch)||me.endswith(rem,n)||me.endswith(rem,n1))
		return true;
	else
		return false;

}	// end of function checkCvcPattern

public static boolean plural1(byte[]  str1)
{

	ByteMeth me=new ByteMeth();
	byte kal[]={14,1,29};byte kk[]={14},r1[]={30},l[]={26},aa[]={2},ee[]={4},u[]={5},uu[]={6},A[]={8},oo[]={11};
	if(str1.length < ikkal.length)
		return false;
	if(me.endswith(str1,ikkal))
		{
			System.out.println("ikkal ok");
			byte r[]=me.subarray(str1,str1.length-ikkal.length,str1.length);
			byte a[]=me.subarray(str1,0,(str1.length-ikkal.length));
			if(me.endswith(a,aa)||me.endswith(a,ee)||me.endswith(a,uu)||me.endswith(a,aa)||me.endswith(a,oo)||me.endswith(a,A))
			{
				s.push("\n"+tabconvert2.revert(r)+"(plural)");
				s.push("\n"+tabconvert2.revert(a)+"(noun)");
				return true;
			}
			else if(me.endswith(a,u))
			{
				if(checkCvcPattern(a))
				{
					s.push("\n"+tabconvert2.revert(r)+"(plural)");
					s.push("\n"+tabconvert2.revert(a)+"(noun)");
					return true;
				}
				else
					return false;
			}
			else return false;
		}
	else
		return false;
}	// end of function plural1


public static boolean plural(byte[]  str1)
{
	ByteMeth me=new ByteMeth();
	byte kal[]={14,1,29};byte kk[]={14},r1[]={30},l[]={26};
	if(str1.length < kal.length)
		return false;
	if(me.endswith(str1,kal))
		{
			  byte r[]=me.subarray(str1,str1.length-3,str1.length);
			  byte a[]=me.subarray(str1,0,(str1.length-3));
			  s.push("\n"+tabconvert2.revert(r)+"(plural)");
			  if(me.endswith(a,ink))
				{
					  byte b[]=me.subarray(a,0,(a.length-1));
					  byte e[]=me.addarray(b,im);
					  s.push("\n"+tabconvert2.revert(e)+"(noun)");
					  splitCompletedInPlural= true;
					  return true;
				 }
			 else if(me.endswith(a,kk))
				{
					  byte b[]=me.subarray(a,0,(a.length-1));
					  s.push("\n"+tabconvert2.revert(b)+"(noun)");
					  splitCompletedInPlural= true;
					  return true;
				}
			 else if(me.endswith(a,r1))
			 {
				byte b[]=me.subarray(a,0,(a.length-1));
				byte e[]=me.addarray(b,l);
				s.push("\n"+tabconvert2.revert(e)+"(noun)");
				System.out.println("noun value in plural :" + tabconvert2.revert(e));
				splitCompletedInPlural= true;
				return true;
			 }
			 else
				{
					s.push("\n"+tabconvert2.revert(a)+"(noun)");
					splitCompletedInPlural= true;
					return true;

				}

		}
	else
		return false;

}		// end of function plural


 public static void main(String args[])
     {
            noun5_temp mor = new noun5_temp();
      }
  }



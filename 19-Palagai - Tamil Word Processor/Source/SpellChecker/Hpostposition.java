package SpellChecker;

//    .............  Spellchecker with User Interface  ..............
//    .............		   postpositionchecking	 	   ..............

import java.util.*;
import java.lang.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import analyser.*;

public class Hpostposition extends JFrame implements ActionListener
{
	static JButton check = new JButton("Check");       // Check & Ok are two buttons
	static JButton close = new JButton("Close");

	Font f=new Font("TAB-Anna",4,16);

	static JTextField text1 = new JTextField("ñóî¢î¤ô¢",16);  //text field to get the user input
	static JTextArea text2 = new JTextArea(20,30);     //text field to print the output
	static JTextField text3 = new JTextField("ñóî¢î¤ô¢",20);

	static JComboBox jc = new JComboBox();

	static JPanel p1 = new JPanel();
	static JScrollPane p2 = new JScrollPane(text2);
	static JPanel p3 = new JPanel();

	// byte codes for accusative postpositions
	static byte vida[]={27,3,18,1};
	static byte pola[]={22,11,26,1};
	static byte kondu[]={14,10,19,18,5};
	static byte nokki[]={21,11,14,14,3};
	static byte pattri[]={22,1,30,30,3};
	static byte kuriththu[]={14,5,30,3,20,20,5};
	static byte suttri[]={16,5,30,30,3};
	static byte vittu[]={27,3,18,18,5};
	static byte thavira[]={20,1,27,3,25,1};
	static byte munnittu[]={23,5,31,31,3,18,18,5};
	static byte vendi[]={27,8,19,18,3};
	static byte otti[]={10,18,18,3};
	static byte poruththu[]={22,10,30,5,20,20,5};
	static byte poruththavarai[]={22,10,30,5,20,20,1,27,1,25,9};
	// byte codes for genitive postpositions
	static byte meedhu[]={23,4,20,5};
	static byte vazhiyaaga[]={27,1,28,3,24,2,14,1};
	static byte moolamaaga[]={23,6,26,1,23,2,14,1};
	static byte vaayilaaga[]={27,2,24,3,26,2,14,1};
	static byte poruttu[]={22,10,25,5,18,18,5};
	static byte meethu[]={23,4,20,5};
	static byte meethil[]={23,4,20,3,26};
	// byte codes for locative postposition
	static byte mael[]={23,8,26};
	static byte keezh[]={14,4,28};
	// byte codes for dative postposition
	static byte aaga[]={2,14,1};
	static byte mun[]={23,5,31};
	static byte pin[]={22,3,31};
	static byte endru[]={7,31,30,5};
	static byte ul1[]={5,29};
	static byte ulla[]={5,29,29,1};
	static byte uriya[]={5,25,3,24,1};
	static byte namakkidaiye[]={21,1,23,1,14,14,3,18,9,24,8};
	static byte naduve[]={21,1,18,5,27,8};
	static byte veliye[]={27,7,29,3,24,8};
	static byte maththiyil[]={23,1,20,20,3,24,3,26};
	static byte ethiril[]={7,20,3,25,3,26};
	static byte pakkaththil[]={22,1,14,14,1,20,20,3,26};
	static byte arukil[]={1,25,5,14,3,26};
	static byte pathil[]={22,1,20,3,26};
	static byte thaguntha[]={20,1,14,5,21,20,1};
	static byte maaraaga[]={23,2,30,2,14,1};
	static byte naeraaga[]={21,8,25,2,14,1};


	static String suggest="";
	public Hpostposition()
	{
		setTitle("SpellChecker-Postposition");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int ht = d.height;
		int wid = d.width;
         setSize(800,240);

		Image img = tk.getImage(".\\Hlpglobe.GIF");
		setIconImage(img);

		p1.add(text1);
		p1.add(text3);

		p3.add(jc);
		p3.add(check);
		p3.add(close);


		text1.setFont(f);
		text2.setFont(f);
		text3.setFont(f);
		jc.setFont(f);

		jc.addItem("Suggestion");

		check.addActionListener(this);
		close.addActionListener(this);
		text1.addActionListener(this);
		jc.addActionListener(this);

		getContentPane().add(p1,"North");
		getContentPane().add(p2,"Center");
		getContentPane().add(p3,"South");

		addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			});

		show();
	  }       // end of constructor

	public static String changearr(byte[] strin,byte[] split)
	{
		int len1,len2;
		len1 = split.length;
		len2 = strin.length;

		for(int j=len1;j>0;j--)
			{
				strin[len2-j] = split[len1-j];
			}
		return(tabconvert2.revert(strin));
	}

	/*public static String spellcheck(String strin)
		{
			byte start[],end[];int i=0,j=0;
			String spchk="",temp1="",temp2="";
			try{
				spchk=checker(strin);
				}
			catch(Exception e)
				{
					System.out.println("exception");
					e.printStackTrace();
				}
			Stack wordstack = verb.checkverb(spchk);
			while(!wordstack.empty())
			{
				temp1=temp1+wordstack.pop().toString();
			}
			int len4=temp1.length();
		     //jc.addItem(temp1);
			temp2="     "+temp1.substring(0,len4-1);
			jc.addItem(temp2);
			return(temp2);

		} // end of function spellcheck*/


	public static String spellcheck(String strin)
	{
		ByteMeth me=new ByteMeth();
		String spchk="",temp1="",temp2="";
		//Case cs = new Case();
		byte start[],end[],mergedcase[],mergedstr[];int i=0,j=0;
		String case1="",case2="",case3="",casemarker="";

		try{
			spchk=checker(strin);
			}
		catch(Exception e)
			{
				System.out.println("exception");
				e.printStackTrace();
			}
		Stack wordstack = Analyser.getStack(spchk);
		while(!wordstack.empty())
		{
			temp1=temp1+wordstack.pop().toString();
		}
		int len4=temp1.length();
		 //jc.addItem(temp1);
		temp2="     "+temp1.substring(0,len4);
		jc.addItem(temp2);
		return(temp2);
		//return spchk;

		/*Stack wordstack = noun5_temp.split(strin);
		String temp1="";
		while (!wordstack.empty())
			 temp1=temp1+wordstack.pop().toString();
		int len4=temp1.length();
		String temp2="     "+temp1.substring(0,len4-1);
		String temp1="",temp3="",noun="";
		boolean number=true;
		int nounindex=0,caseindex=0,pluralindex=0,Euphonicindex=0;
		boolean caseindex1=false,caseindex2=false,caseindex3=false;
		boolean searchresult=true;

		while (!wordstack.empty())
			{
				temp3=wordstack.pop().toString();
				nounindex=temp3.indexOf("(noun)");

				if(nounindex!= -1)
					{
						noun=temp3.substring(1,nounindex);
						System.out.println("noun str is:"+noun);
						try{
								searchresult = dicsearch(noun);
							}
						catch(Exception e){e.printStackTrace();}
						if(searchresult==true)
						{
							System.out.println("search succes");
							temp1=temp1+temp3;
							//jc.addItem("string is correct-found after dic search");
						}
						else
						{
							byte nounbyte[] = tabconvert2.convert(noun);
							int nounlength=nounbyte.length;
							int halflen=(int)nounlength/2;
							System.out.println("nounlen:"+nounlength);
							System.out.println("halflen:"+halflen);

							System.out.println("search failed");
							// noun length is odd
							if((nounlength%2)!=0)
							{
								int numchars1 = halflen,numchars2=nounlength-halflen-1;
								start = new byte[numchars1];
								end = new byte[numchars2];
								for(i=0;i<halflen;i++)
									start[i] = nounbyte[i];
								for(j=halflen+1,i=0;j<nounlength;j++,i++)
									end[i] = nounbyte[j];
								System.out.println("noun length odd");
								System.out.println("start:" + tabconvert2.revert(start));
								System.out.println("end:" + tabconvert2.revert(end));
								//jc.removeAllItems();


							}
							// noun length is even
							else
							{
								int numchars1 = halflen,numchars2=nounlength-halflen;
								start = new byte[numchars1];
								end = new byte[numchars2];
								for(i=0;i<halflen;i++)
									start[i] = nounbyte[i];
								for(j=halflen,i=0;j<nounlength;j++,i++)
									end[i] = nounbyte[j];
								System.out.println("noun length even");
								System.out.println("start:" + tabconvert2.revert(start));
								System.out.println("end:" + tabconvert2.revert(end));
								//jc.removeAllItems();
							}
							System.out.println("i am in call to sugg_from_dic");
							sugg_from_dictionary(start,end);

						}// end of inner else

					} // end of outer else
					else
					{
						caseindex = temp3.indexOf("<case");
						Euphonicindex = temp3.indexOf("(Euphonic");
						if(caseindex!= -1)
						{
							if(caseindex1==false)
								{
									caseindex1=true;
									case1=temp3.substring(1,caseindex).trim();
								}
							else if(caseindex2==false)
								{
									caseindex2=true;
									case2=temp3.substring(1,caseindex).trim();
								}
							else
								{
									caseindex3=true;
									case3=temp3.substring(1,caseindex-1).trim();
								}
						}
						if(Euphonicindex != -1)
						{
							if(caseindex1==false)
								{
									caseindex1=true;
									case1=temp3.substring(1,Euphonicindex).trim();
								}
							else if(caseindex2==false)
								{
									caseindex2=true;
									case2=temp3.substring(1,Euphonicindex).trim();
								}
							else
								{
									caseindex3=true;
									case3=temp3.substring(1,Euphonicindex-1).trim();
								}
						}
						pluralindex = temp3.indexOf("(plural");
						if(pluralindex == -1)
							number=true;
						else
							number=false;
						temp1=temp1+temp3;
					} // end of else


				System.out.println("word:" + temp1);
			} // end of while construct
		System.out.println("noun=:" + noun);
		System.out.println("case1=:" + case1);
		System.out.println("case2=:" + case2);
		System.out.println("case3=:" + case3);
		System.out.println("number=:" + number);

		mergedstr =	cs.addCase(noun,case1,case2,number);
		text3.setText(tabconvert2.revert(mergedstr));
		jc.addItem("mstr from case:"+tabconvert2.revert(mergedstr));
		int len4=temp1.length();
		String temp2="     "+temp1.substring(0,len4-1);
		return(temp2);*/


	}	// end of function spellcheck


public void sugg_from_dictionary(byte[] start,byte[] end)
{

	ByteMeth me=new ByteMeth();
	byte dicword[]=null,tempbyte[]=null;
	String temp="";
	BufferedReader dic = null;
	try{

	//modified by anu on 26/12/02 while integrating
	try
	{
		dic = new BufferedReader(new InputStreamReader(getClass().
			getResourceAsStream("dictionary.txt")));
	}catch(Exception e)
	{
		e.printStackTrace();
	}

	StreamTokenizer input = new StreamTokenizer(dic);
	int tokentype = 0;
	while((tokentype = input.nextToken()) != StreamTokenizer.TT_EOF)
	{
		if(tokentype == StreamTokenizer.TT_WORD)
		{
			temp=input.sval;
			tempbyte = tabconvert2.convert(temp);
			if(me.startswith(tempbyte,start)&&me.endswith(tempbyte,end))
				{
					jc.addItem(temp);
					System.out.println("i am inside if of sug_dic");
				}

		}

	}}catch(Exception e){e.printStackTrace();}


} // end of function sugg_from_dictionary


public static String checker(String strin) throws Exception//it checks for special cases,
{							  	     //searches the dictionary & returns
	ByteMeth me=new ByteMeth();
	byte bstr[] = tabconvert2.convert(strin);
	int blen = bstr.length;
	String temp="";
	Stack s1;
	boolean searchresult=true;
	System.out.println("str in checker :"+ strin);
	suggest="";
	boolean status = check_accusative(bstr,blen);
	if(status)
		{
			temp = "accusative-ok";
			jc.addItem(temp);
			return suggest;
		}
	else
		{
			status = check_genitive(bstr,blen);
			if(status)
				{
					temp = "genitive-ok-ok";
					jc.addItem(temp);
					return suggest;
				}
			else
				{
					status = check_lacative(bstr,blen);
					if(status)
						{
							temp = "lacative-ok";
							jc.addItem(temp);
							return suggest;
						}
					else
						{
							status = check_dative(bstr,blen);
							if(status)
								{
									temp = "dative-ok";
									jc.addItem(temp);
									return suggest;
								}
							else
								{
									temp = "no-match";
									jc.addItem(temp);
									return temp;
									/*status = check_aaga(bstr,blen);
									if(status)
										{
											temp = "aaga-ok";
											return suggest;
										}
									else
										{
											status = check_kal(bstr,blen);
											if(status)
												{
													temp = "kal-ok";
													//jc.addItem(temp);
													return suggest;
												}
											else
												{
													if(me.endswith(bstr,ai))
														{
															temp = "ai-ok";
															//jc.addItem(temp);
															return suggest;
														}
													else
														{

															try{
																 searchresult = dicsearch(strin);}catch(Exception e){e.printStackTrace();}
																System.out.println("the string is correct-match found in dictionary");
																if(searchresult == true)
																{
																	temp = "the string is correct";
																	text2.setText(temp);
																	//jc.addItem("the string is correct");
																	return(temp );
																}
																else
																{
																	temp = "no-match";
																	text2.setText(temp);
																	return temp;
																}



														}
												}
										}*/
								}
						}
				}
		}

}	// end of function checker



//modified by anu on 26/12/02 while integrating
public boolean dicsearch(String root)// throws IOException
 {
	  BufferedReader dic = null;
	try{

	//modified by anu on 26/12/02 while integrating
	try
	{
		dic = new BufferedReader(new InputStreamReader(getClass().
			getResourceAsStream("dictionary.txt")));
	}catch(Exception e)
	{
		e.printStackTrace();
	}

	StreamTokenizer input = new StreamTokenizer(dic);
	int tokentype = 0;
	while((tokentype = input.nextToken()) != StreamTokenizer.TT_EOF)
	{
		if(tokentype == StreamTokenizer.TT_WORD)
		{
			if(root.equals(input.sval))
				return true;

		}

	}}catch(Exception e){e.printStackTrace();}
	return false;

} // ens of function dicsearch


public static boolean check_accusative(byte[] bstr,int blen)
{
	if(check_u(bstr,blen))  //check munnittu,poruththu,vittu,kondu,kuriththu
		return true;
	else if(check_e(bstr,blen)) // check suttri,pattri,vendi,otti,nokki
		return true;
	else
		return false;

} // end of function check_accusative postposition


public static boolean check_dative(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	if(blen <= pakkaththil.length)
		return false;
	if(me.endswith(bstr,ul1)||me.endswith(bstr,uriya)||me.endswith(bstr,ulla)||me.endswith(bstr,mun)||me.endswith(bstr,pin)||me.endswith(bstr,aaga)||me.endswith(bstr,pathil)||me.endswith(bstr,naeraaga)||me.endswith(bstr,maaraaga)||me.endswith(bstr,maththiyil)||me.endswith(bstr,pakkaththil)||me.endswith(bstr,arukil)||me.endswith(bstr,ethiril)||me.endswith(bstr,thaguntha)||me.endswith(bstr,namakkidaiye)||me.endswith(bstr,veliye)||me.endswith(bstr,naduve)||me.endswith(bstr,endru))
		{
			jc.addItem("string is correct");
			return true;
		}
	if(check_naeraaga_maaraaga(bstr,blen))
		return true;
	else if(check_il(bstr,blen))
		return true;
	else if(check_thaguntha(bstr,blen))  // check aaga
		return true;
	else if(check_uriya(bstr,blen))  // check aaga
		return true;
	else if(check_ul1_ulla(bstr,blen))
		return true;
	else if(check_ae(bstr,blen))
		return true;
	else if(check_endru(bstr,blen))
		return true;
	else if(check_mun_pin(bstr,blen))  // check mun & pin
		return true;
	else if(check_aaga(bstr,blen))  // check aaga
		return true;
	else
		return false;

} // end of function check_dative postposition


public static boolean check_genitive(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	if(blen <= poruttu.length)
		return false;
	if(me.endswith(bstr,poruttu)||me.endswith(bstr,meethu)||me.endswith(bstr,meethil))
		return true;
	else if(check_ga(bstr,blen))  // check moolamaga & vaayilaga
		return true;
	else if(check_poruttu(bstr,blen))
		return true;
	else if(check_meethu_meethil(bstr,blen))
		return true;
	else
		return false;

} // end of function check_genitive postposition

public static boolean check_lacative(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	if(blen <= keezh.length)
		return false;
	if(me.endswith(bstr,keezh)||me.endswith(bstr,mael))
		return true;
	else if(check_keezh(bstr,blen))  // check mael & keezh
		return true;
	else if(check_mael(bstr,blen))
		return true;
	else
		return false;

} // end of function check_lacative postposition


public static boolean check_u(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	if(blen <= munnittu.length)
		return false;
	if(me.endswith(bstr,kondu)||me.endswith(bstr,kuriththu)||me.endswith(bstr,vittu)||me.endswith(bstr,munnittu)||me.endswith(bstr,poruththu))
	{
		text2.setText("string is correct");
		return(true);
	}
	else if(check_pattern_munnittu(bstr,munnittu))
	{
		get_sugg(bstr,munnittu);
		return(true);
	}
	else if(check_pattern_kondu(bstr,kondu))
	{
		set_sugg(bstr,kondu);
		return(true);
	}
	else if(check_pattern_poruththu(bstr,poruththu))
	{
			get_sugg(bstr,poruththu);
			set_sugg(bstr,poruttu);
			return(true);
	}
	else if(check_pattern_kuriththu(bstr,kuriththu))
	{
		get_sugg(bstr,kuriththu);
		return(true);
	}

	else if(check_pattern_vittu(bstr,vittu))
	{
		get_sugg(bstr,vittu);
		System.out.println("suggest in check_pattern_irunthu :" + suggest);
		return(true);
	}


	else
		return(false);

} // end of function check_u


public static boolean check_e(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	if(blen <= nokki.length)
		return false;
	if(me.endswith(bstr,nokki)||me.endswith(bstr,pattri)||me.endswith(bstr,suttri)||me.endswith(bstr,vendi)||me.endswith(bstr,otti))
	{
		text2.setText("string is correct");
		return(true);
	}
	else if(check_pattern_nokki(bstr,nokki))
	{
		get_sugg(bstr,nokki);
		set_sugg(bstr,pattri);
		set_sugg(bstr,suttri);
		return(true);
	}
	else if(check_pattern_vendi(bstr,vendi))
	{
		get_sugg(bstr,vendi);
		return(true);
	}
	else if(check_pattern_pattri_suttri(bstr,suttri))
	{
		return(true);
	}
	else if(check_pattern_otti(bstr,otti))
	{
		get_sugg(bstr,otti);
		return(true);
	}
	else
		return(false);

} // end of function check_e


public static boolean check_ga(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	if(blen <= moolamaaga.length)
		return false;
	if(me.endswith(bstr,moolamaaga)||me.endswith(bstr,vaayilaaga)||me.endswith(bstr,vazhiyaaga))
	{
		text2.setText("string is correct");
		return(true);
	}
	else if(check_pattern_moolamaaga_vaayilaaga_vazhiyaaga(bstr,vaayilaaga))
		return(true);
	else
		return(false);

} // end of function check_ga



public static void set_sugg(byte[] bstr,byte[] marker)
{
	ByteMeth me=new ByteMeth();
	int len = marker.length,blen = bstr.length;
	byte rem[] = me.subarray(bstr,0,blen-len);
	String sugg = tabconvert2.revert(me.addarray(rem,marker));
	jc.addItem(sugg);
	HSugg.model.addElement(sugg);
} // end of function set_sugg


public static void get_sugg(byte[] bstr,byte[] marker)
{
	System.out.println("str in set_sugg :" + tabconvert2.revert(bstr));
	ByteMeth me=new ByteMeth();
	int len = marker.length,blen = bstr.length;
	byte rem[] = me.subarray(bstr,0,blen-len);
	suggest= tabconvert2.revert(me.addarray(rem,marker));
	jc.addItem(suggest);
	text3.setText(suggest);
	HSugg.model.addElement(suggest);
} // end of function get_sugg



public static boolean check_pattern_kondu(byte[] bstr,byte[] marker)
{
	ByteMeth me=new ByteMeth();
	int blen = bstr.length;
	int len = marker.length;
	if(blen <= len)
		return false;
	byte kon[]={14,10,19},ndu[]={19,18,5},ond[]={10,19,18};
	if(blen>5)
	{
		byte end[] = me.subarray(bstr,blen-len,blen);
		byte end234[] = {end[1],end[2],end[3]};
		if(me.startswith(end,kon)||me.endswith(bstr,ndu)|| me.isequal(end234,ond))
			return(true);
		else
			return(false);
	}
	else
		return(false);
} // end of function check_pattern_kondu


public static boolean check_pattern_poruththu(byte[] bstr,byte[] marker)
{
	ByteMeth me=new ByteMeth();
	int blen = bstr.length;
	int len = marker.length;
	if(blen <= len)
		return false;
	byte poru[]={22,10,30,5};
	byte por[]={22,10,30};
	byte oruthth[]={10,30,5,20,20};
	byte ruththu[]={30,5,20,20,5};
	byte uththu[]={5,20,20,5};
	byte ththu[]={20,20,5},pp[]={22};
	if(blen>7)
		{
			byte end[] = me.subarray(bstr,blen-len,blen);
			byte end23456[] = {end[1],end[2],end[3],end[4],end[5]};
			if(me.startswith(end,por)||me.startswith(bstr,poru)||me.endswith(bstr,ruththu)||me.endswith(bstr,uththu)||(me.endswith(bstr,ththu)&&me.startswith(bstr,pp))|| me.isequal(end23456,oruthth))
				return(true);
			else
				return(false);
		}
	else
		return false;
} // end of function check_pattern_poruththu


public static boolean check_pattern_munnittu(byte[] bstr,byte[] marker)
{
	ByteMeth me=new ByteMeth();
	int blen = bstr.length;
	int len = marker.length;
	if(blen <= len)
		return false;
	byte munn[]={23,5,31},ittu[]={3,18,18,5},unni[]={5,31,31,3},itt[]={3,18,18};
	if(blen>8)
	{
		byte end[] = me.subarray(bstr,blen-len,blen),od[]={11,18},o[]={11},du[]={18,5};
		byte end2345[] = {end[1],end[2],end[3],end[4]};
		byte end567[]={end[4],end[5],end[6]};
		if(me.startswith(end,munn)||me.endswith(bstr,ittu)|| me.isequal(end2345,unni)|| me.isequal(end567,itt))
			return(true);
		else
			return(false);
	}
	else
		return(false);
} // end of function check_pattern_munnittu

public static boolean check_pattern_kuriththu(byte[] bstr,byte[] marker)
{
	ByteMeth me=new ByteMeth();
	int blen = bstr.length;
	int len = marker.length;
	if(blen <= len)
		return false;
	byte kuri[]={14,5,30},ththu[]={20,20,5},urith[]={5,30,3,20},ithth[]={3,20,20},ku[]={14,5},kk[]={14};
	if(blen>7)
	{
		byte end[] = me.subarray(bstr,blen-len,blen);
		byte end2345[] = {end[1],end[2],end[3],end[4]};
		byte end456[] = {end[3],end[4],end[5]};
		if(me.startswith(end,kuri)||me.endswith(bstr,ththu)|| me.isequal(end2345,urith)|| (me.isequal(end456,ithth) && me.startswith(bstr,kk)))
			return(true);
		else
			return(false);
	}
	else
		return(false);
} // end of function check_pattern_kuriththu


public static boolean check_pattern_vittu(byte[] bstr,byte[] marker)
{
	ByteMeth me=new ByteMeth();
	int blen = bstr.length;
	int len = marker.length;
	if(blen <= len)
		return false;
	if(blen>5)
	{
		byte end[] = me.subarray(bstr,blen-len,blen);
		byte vit[]={27,3,18},itt[]={3,18,18},ttu[]={18,18,5},iv[]={27};
		byte end234[] = {end[1],end[2],end[3]};
		if(me.startswith(end,vit)||(me.startswith(end,iv)&&me.endswith(bstr,ttu))|| me.isequal(end234,itt))
			return(true);
		else
			return(false);
	}
	else
		return(false);
} // end of function check_pattern_vittu


public static boolean check_pattern_nokki(byte[] bstr,byte[] marker)
{
	ByteMeth me=new ByteMeth();
	int blen = bstr.length;
	int len = marker.length;
	if(blen <= len)
		return false;
	byte nok[]={21,11,14},okk[]={11,14,14},kki[]={14,14,3},nth[]={21},kk[]={14,14};
	if(blen>=5)
	{
		byte end[] = me.subarray(bstr,blen-len,blen);
		byte end234[] = {end[1],end[2],end[3]},end34[]={end[2],end[3]};
		if(me.startswith(end,nok)||me.endswith(bstr,kki)|| me.isequal(end234,okk)||(me.startswith(end,nth) && me.isequal(end34,kk)))
			return(true);
		else
			return(false);
	}
	else
		return(false);
} // end of function check_pattern_nokki


public static boolean check_pattern_pattri_suttri(byte[] bstr,byte[] marker)
{
	ByteMeth me=new ByteMeth();
	int blen = bstr.length;
	int len = marker.length;
	if(blen <= len)
		return false;
	if(me.endswith(bstr,suttri)||me.endswith(bstr,pattri))
	{
		jc.addItem("string is correct");
		return true;
	}
	byte attri[]={1,30,30,3},pat[]={22,1,30},ttri[]={30,30,3},uttr[]={1,30,30},attr[]={1,30,30},ich[]={16},ip[]={22},irir[]={30,30};
	byte end[] = me.subarray(bstr,blen-len,blen);
	byte end234[] = {end[1],end[2],end[3]},end34[]={end[2],end[3]};
	if(me.endswith(bstr,attri))
	{
			set_sugg(bstr,suttri);
			set_sugg(bstr,pattri);
			return true;
	}
	else if(me.endswith(bstr,ttri)|| me.isequal(end234,attr)||(me.isequal(end234,uttr))||(me.startswith(bstr,ip)&&me.isequal(end34,irir))||(me.startswith(end,ich)&&me.isequal(end34,irir)))
	{
		if(me.startswith(end,ip))
		{
			get_sugg(bstr,pattri);
			set_sugg(bstr,suttri);
		}
		else
		{
			get_sugg(bstr,suttri);
			set_sugg(bstr,pattri);
		}
		return(true);
	}
	else
		return false;

} // end of function check_pattern_pattri_suttri


public static boolean check_pattern_vendi(byte[] bstr,byte[] marker)
{
	ByteMeth me=new ByteMeth();
	int blen = bstr.length;
	int len = marker.length;
	if(blen <= len)
		return false;
	if(me.endswith(bstr,vendi))
	{
		jc.addItem("string is correct");
		return true;
	}
	byte ve[]={27,8},di[]={18,3},ven[]={27,8,19},ndi[]={19,18,3},yend[]={8,19,18},iv[]={27},init[]={19,18};
	if(blen>5)
	{
		byte end[] = me.subarray(bstr,blen-len,blen);
		byte end234[] = {end[1],end[2],end[3]},end34[]={end[2],end[3]};
		if(me.startswith(end,ven)||me.endswith(bstr,ndi)|| me.isequal(end234,yend)||(me.startswith(end,iv)&&me.isequal(end34,init))||(me.startswith(end,ve)&&me.endswith(bstr,di)))
			return(true);
		else
			return(false);
	}
	else
		return(false);
} // end of function check_pattern_vendi


public static boolean check_pattern_otti(byte[] bstr,byte[] marker)
{
	ByteMeth me=new ByteMeth();
	int blen = bstr.length;
	int len = marker.length;
	if(blen <= len)
		return false;
	if(me.endswith(bstr,otti))
	{
		jc.addItem("string is correct");
		return true;
	}
	byte ot[]={10,18},tti[]={18,18,3},o1[]={11},itit[]={18,18};
	if(blen>=5)
	{
		byte end[] = me.subarray(bstr,blen-len,blen);
		byte end23[] = {end[1],end[2]};
		if(me.startswith(end,ot)||me.endswith(bstr,tti)||(me.isequal(end23,itit)&&me.startswith(end,o1)))
			return(true);
		else
			return(false);
	}
	else
		return(false);
} // end of function check_pattern_otti


public static boolean check_pattern_moolamaaga_vaayilaaga_vazhiyaaga(byte[] bstr,byte[] marker)
{
	ByteMeth me=new ByteMeth();
	int blen = bstr.length;
	int len = marker.length;
	if(blen <= len)
		return false;
	if(me.endswith(bstr,moolamaaga)||me.endswith(bstr,vaayilaaga)||me.endswith(bstr,vazhiyaaga))
	{
		jc.addItem("string is correct");
		return true;
	}
	byte im[]={23},la[]={26,1},am[]={1,23},ka[]={14,1},moo[]={23,6};
	byte pat[]={22,1,30},ttri[]={30,30,3},uttr[]={1,30,30},attr[]={1,30,30};
	byte ich[]={16},ip[]={22},irir[]={30,30},laaga[]={26,2,14,1};
	byte vaai[]={27,2,24},iv[]={27},yil[]={24,3,26},zhiy[]={28,3,24};
	byte vazhi[]={27,1,28,3},maaga[]={23,2,14,1},lamaa[]={26,1,23,2},yilaa[]={24,3,26,2};
	byte end[] = me.subarray(bstr,blen-len,blen),yaaga[]={24,2,14,1},zhiyaa[]={28,3,24,2};
	byte end3456[]={end[2],end[3],end[4],end[5]};
	byte end34[] = {end[2],end[3]},end45[]={end[3],end[4]},end345[] = {end[2],end[3],end[4]},end78[]={end[6],end[7]};
	if((me.endswith(bstr,maaga)&&me.startswith(end,im))||me.isequal(end3456,lamaa)|| (me.isequal(end45,am)&&me.isequal(end78,ka))||(me.startswith(end,im)&&me.isequal(end78,ka)&&me.isequal(end34,la)))
	{
		get_sugg(bstr,moolamaaga);
		set_sugg(bstr,vaayilaaga);
		set_sugg(bstr,vazhiyaaga);
		return(true);
	}
	else if(me.endswith(bstr,laaga)||me.startswith(end,vaai)|| me.isequal(end3456,yilaa)||(me.startswith(end,iv)&&me.isequal(end345,yil)))
	{
		get_sugg(bstr,vaayilaaga);
		set_sugg(bstr,vazhiyaaga);
		set_sugg(bstr,moolamaaga);
		return(true);
	}
	else if((me.startswith(end,iv)&&me.endswith(bstr,yaaga))||me.isequal(end3456,zhiyaa)||me.startswith(end,vazhi)|| (me.startswith(end,iv)&&me.isequal(end345,zhiy)))
	{
		get_sugg(bstr,vazhiyaaga);
		set_sugg(bstr,vaayilaaga);
		set_sugg(bstr,moolamaaga);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_moolamaaga_vaayilaaga



public static boolean check_poruttu(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = poruttu.length;
	if(blen <= len)
		return false;
	if(me.endswith(bstr,poruttu))
	{
		jc.addItem("string is correct");
		return true;
	}
	byte ttu[]={18,18,5},ip[]={22},poru[]={22,10,25,5},rutt[]={25,5,18,18};
	byte end[] = me.subarray(bstr,blen-len,blen);
	byte end3456[]={end[2],end[3],end[4],end[5]};
	if(blen>=7)
	{
		if((me.endswith(bstr,ttu)&&me.startswith(end,ip))||me.startswith(end,poru)|| me.isequal(end3456,rutt))
		{
			get_sugg(bstr,poruttu);
			set_sugg(bstr,poruththu);
			return(true);
		}
		else
			return false;

	}
	else
		return(false);
} // end of function check_poruttu

public static boolean check_meethu_meethil(byte[] bstr,int blen)
{
		if(check_pattern_meethil(bstr,blen))  // check meethu & meetyhil
			return true;
		else if(check_pattern_meethu(bstr,blen))
			return true;
		else
			return false;
} // end of function check_meethu_meethil



public static boolean check_pattern_meethil(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = meethil.length;
	if(blen <= len)
		return false;
	if(me.endswith(bstr,meethil))
	{
		jc.addItem("string is correct");
		return true;
	}
	byte im[]={23},meeth[]={23,4,20},thil[]={20,3,26},mee[]={23,4},il[]={26},il1[]={3,26};
	byte end[] = me.subarray(bstr,blen-len,blen);
	if(me.endswith(bstr,thil)||(me.endswith(bstr,il1)&&me.startswith(end,im))||(me.endswith(bstr,il)&&me.startswith(end,mee)))
	{
		get_sugg(bstr,meethil);
		set_sugg(bstr,meethu);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_meethil

public static boolean check_pattern_meethu(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = meethu.length;
	if(blen <= len)
		return false;
	if(me.endswith(bstr,meedhu))
	{
		jc.addItem("string is correct");
		return true;
	}
	byte im[]={23},meeth[]={23,4,20},thu[]={20,5},mee[]={23,4};
	byte end[] = me.subarray(bstr,blen-len,blen);
	if(me.startswith(end,meeth)||(me.endswith(bstr,thu)&&me.startswith(end,im)))
	{
		get_sugg(bstr,meethu);
		//set_sugg(bstr,meethil);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_meethu


public static boolean check_keezh(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = keezh.length;
	if(blen <= len)
		return false;
	if(me.endswith(bstr,keezh))
	{
		jc.addItem("string is correct");
		return true;
	}
	byte ik[]={14},eezh[]={4,28},kee[]={14,4},zh[]={28};
	byte end[] = me.subarray(bstr,blen-len,blen);
	if(blen>=3)
	{
		if(me.startswith(end,kee)||(me.endswith(bstr,zh)&&me.startswith(end,ik))||me.endswith(bstr,eezh))
		{
			get_sugg(bstr,keezh);
			return(true);
		}
		else
			return false;

	}
	else
		return(false);
} // end of function check_pattern_keezh


public static boolean check_mael(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = mael.length;
	if(blen <= len)
		return false;
	if(me.endswith(bstr,mael))
	{
		jc.addItem("string is correct");
		return true;
	}
	byte il[]={26},mae[]={23,8},ael[]={8,26},im[]={23};
	byte end[] = me.subarray(bstr,blen-len,blen);
	//byte end3456[]={end[2],end[3],end[4],end[5]};
	if(blen>=3)
	{
		if(me.startswith(end,mae)||(me.endswith(bstr,il)&&me.startswith(end,im))||me.endswith(bstr,ael))
		{
			get_sugg(bstr,mael);
			jc.addItem(tabconvert2.revert(end));
			return(true);
		}
		else
			return false;

	}
	else
		return(false);
} // end of function check_pattern_mael


public static boolean check_mun_pin(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = mun.length;
	if(blen <= len)
		return false;
	byte mu[]={23,5},pi[]={22,3},un[]={5,31},in[]={3,31},im[]={23},ip[]={22},nn[]={31};
	byte end[] = me.subarray(bstr,blen-len,blen);
	if(blen>=3)
	{
		if(me.startswith(end,mu)||me.endswith(bstr,un)||(me.startswith(end,im)&&me.endswith(end,nn)))
		{
			get_sugg(bstr,mun);
			set_sugg(bstr,pin);
			return(true);
		}
		if(me.startswith(end,pi)||me.endswith(bstr,in)||(me.startswith(end,ip)&&me.endswith(end,nn)))
		{
			get_sugg(bstr,pin);
			set_sugg(bstr,mun);
			return(true);
		}
		else
			return false;
	}
	else
		return(false);
} // end of function check_pattern_mun_pin


public static boolean check_naeraaga_maaraaga(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = naeraaga.length;
	if(blen <= len)
		return false;
	byte naer[]={21,8,25},raaga[]={25,2,14,1},nth[]={21},maar1[]={23,2,30},raaga1[]={30,2,14,1},im[]={23};
	byte end[] = me.subarray(bstr,blen-len,blen),ga[]={14,1};
	byte end3456[]={end[2],end[3],end[4],end[5]};
	if((me.endswith(bstr,aaga)&&me.startswith(end,nth))||me.startswith(end,naer)|| me.isequal(end3456,raaga)||(me.endswith(end,ga)&&me.startswith(end,nth)))
	{
		if(me.startswith(end,nth))
		{
			get_sugg(bstr,naeraaga);
			set_sugg(bstr,maaraaga);
		}
		else
		{
			get_sugg(bstr,maaraaga);
			set_sugg(bstr,naeraaga);
		}
		return(true);
	}
	else if((me.endswith(bstr,aaga)&&me.startswith(end,im))||me.startswith(end,maar1)|| me.isequal(end3456,raaga1)||(me.endswith(end,ga)&&me.startswith(end,im)))
	{
		get_sugg(bstr,maaraaga);
		set_sugg(bstr,naeraaga);
		return(true);
	}
	else
		return false;
} // end of function check_naeraaga_maaraaga

public static boolean check_il(byte[] bstr,int blen)
{
	if(check_pattern_maththiyil(bstr,blen))
		return true;
	else if(check_pattern_pakkaththil(bstr,blen))
		return true;
	else if(check_pattern_ethiril(bstr,blen))
		return true;
	else if(check_pattern_arukil(bstr,blen))
		return true;
	else if(check_pattern_pathil(bstr,blen))
		return true;
	else
		return false;

} // end of function check_il

public static boolean check_ae(byte[] bstr,int blen)
{
	if(check_pattern_namakkidaiye(bstr,blen))
		return true;
	else if(check_pattern_veliye(bstr,blen))
		return true;
	else if(check_pattern_naduve(bstr,blen))
		return true;
	else
		return false;

} // end of function check_ae

public static boolean check_thaguntha(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = thaguntha.length;
	if(blen <= len)
		return false;
	byte thagu[]={20,1,14,5},ntha[]={21,20,1},th[]={20},unth[]={5,21,20};
	byte end[] = me.subarray(bstr,blen-len,blen);
	byte end456[]={end[3],end[4],end[5]};
	if((me.endswith(bstr,ntha)&&me.startswith(end,th))||me.startswith(end,thagu)||me.isequal(end456,unth))
	{
		get_sugg(bstr,thaguntha);
		return(true);
	}
	else
		return false;

} // end of function check_thaguntha

public static boolean check_uriya(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = uriya.length;
	if(blen <= len)
		return false;
	byte uri[]={5,25,3},iya[]={3,24,1},iy[]={24},riy[]={25,3,24},ya[]={24,1},u[]={5};
	byte end[] = me.subarray(bstr,blen-len,blen);
	byte end234[]={end[1],end[2],end[3]};
	if((me.endswith(bstr,ya)&&me.startswith(end,u))||me.startswith(end,uri)||me.isequal(end234,riy)||me.endswith(end,iya))
	{
		get_sugg(bstr,uriya);
		return(true);
	}
	else
		return false;

} // end of function check_uriya

public static boolean check_endru(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = endru.length;
	if(blen <= len)
		return false;
	if(me.endswith(bstr,endru))
	{
		jc.addItem("string is correct");
		return true;
	}
	byte en[]={7,31},ndru[]={31,30,5},ae[]={7},u[]={5};
	byte end[] = me.subarray(bstr,blen-len,blen);
	if((me.endswith(bstr,u)&&me.startswith(end,ae))||me.startswith(end,en)||me.endswith(end,ndru))
	{
		get_sugg(bstr,endru);
		return(true);
	}
	else
		return false;

} // end of function check_endru

public static boolean check_aaga(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = aaga.length;
	if(blen <= len)
		return false;
	if(me.endswith(bstr,aaga))
	{
		jc.addItem("string is correct");
		return true;
	}
	byte aak[]={2,14},ka[]={14,1},aa[]={2},a[]={1};
	byte end[] = me.subarray(bstr,blen-len,blen);
	if(me.endswith(bstr,ka)||(me.startswith(end,aa)&&me.endswith(end,a))||me.startswith(end,aak))
	{
		get_sugg(bstr,aaga);
		return(true);
	}
	else
		return false;

} // end of function check_aaga

public static boolean check_pattern_namakkidaiye(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = namakkidaiye.length;
	if(blen <= len)
		return false;
	byte na[]={21,1},kidaiye[]={14,3,18,9,24,8},dai[]={18,9,24};
	byte daiye[]={18,9,24,8},namak[]={21,1,23,1,14},ye[]={24,8};
	byte end[] = me.subarray(bstr,blen-len,blen);
	byte end8910[]={end[7],end[8],end[9]};
	if(me.endswith(bstr,kidaiye)||me.startswith(end,namak)||(me.startswith(end,na)&&(me.endswith(end,daiye)||me.isequal(end8910,dai))))
	{
		get_sugg(bstr,namakkidaiye);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_namakkidaiye

public static boolean check_pattern_veliye(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = veliye.length;
	if(blen <= len)
		return false;
	byte veli[]={27,7,29,3},iye[]={3,9,24,8},dai[]={18,9,24},iv[]={27};
	byte liy[]={29,3,24},ve[]={27,7},iy[]={24},eli[]={7,29,3};
	byte end[] = me.subarray(bstr,blen-len,blen);
	byte end345[]={end[2],end[3],end[4]},end5[]={end[4]},end234[]={end[1],end[2],end[3]};
	System.out.println("postposition :");
	for(int i=0;i<len;i++)
		System.out.println("byte["+i+"] :" + end[i]);
	System.out.println("string :");
		for(int i=0;i<blen;i++)
		System.out.println("byte["+i+"] :" + bstr[i]);
	if(me.endswith(bstr,iye)||me.startswith(end,veli)||me.isequal(end234,eli)||(me.startswith(end,iv)&&me.isequal(end345,liy))||(me.startswith(end,ve)&&me.isequal(end5,iy)))
	{
		get_sugg(bstr,veliye);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_veliye

public static boolean check_pattern_naduve(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = naduve.length;
	if(blen <= len)
		return false;
	byte nadu[]={21,1,18,5},uve[]={5,27,8},na[]={21,1};
	byte iv[]={27},ve[]={27,8},nad[]={21,1,18},nth[]={21};
	byte end[] = me.subarray(bstr,blen-len,blen);
	byte end5[]={end[4]};
	if(me.endswith(bstr,uve)||me.startswith(end,nadu)||(me.startswith(end,nth)&&me.endswith(end,ve))||(me.startswith(end,nad)&&me.isequal(end5,iv)))
	{
		get_sugg(bstr,naduve);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_naduve


public static boolean check_ul1_ulla(byte[] bstr,int blen)
{
	if(check_pattern_ul1(bstr,blen))
		return true;
	else if(check_pattern_ulla(bstr,blen))
		return true;
	else
		return false;

} // end of function check_ul1_ulla

public static boolean check_pattern_ul1(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = ul1.length;
	if(blen <= len)
		return false;
	byte u[]={5},l1[]={29},l[]={25};
	byte end[] = me.subarray(bstr,blen-len,blen);
	if((me.endswith(bstr,l)&&me.startswith(end,u))||me.endswith(bstr,l1)||me.startswith(end,u))
	{
		get_sugg(bstr,ul1);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_ul1

public static boolean check_pattern_ulla(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = ulla.length;
	if(blen <= len)
		return false;
	byte u[]={5},la[]={29,1},a[]={1};
	byte end[] = me.subarray(bstr,blen-len,blen);
	if(me.endswith(bstr,la)||me.startswith(end,ul1)||(me.startswith(end,u)&&me.endswith(end,a)))
	{
		get_sugg(bstr,ulla);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_ulla


public static boolean check_pattern_maththiyil(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = maththiyil.length;
	if(blen <= len)
		return false;
	byte aththi[]={1,20,20,3},math[]={23,1,20},il[]={26},im[]={23},yil[]={24,3,26},maththi[]={23,1,20,20,3};
	byte end[] = me.subarray(bstr,blen-len,blen);
	byte end2345[]={end[1],end[2],end[3],end[4]};
	if((me.endswith(bstr,yil)&&me.startswith(end,im))||(me.endswith(bstr,il)&&me.startswith(end,math))||me.startswith(end,maththi)|| me.isequal(end2345,aththi))
	{
		get_sugg(bstr,maththiyil);
		set_sugg(bstr,pakkaththil);
		set_sugg(bstr,arukil);
		set_sugg(bstr,ethiril);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_maththiyil

public static boolean check_pattern_pakkaththil(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = pakkaththil.length;
	if(blen <= len)
		return false;
	byte pakka[]={22,1,14,14,1},aththil[]={1,20,20,3,26},il[]={26},ith[]={20},akkath[]={1,14,14,1,20},ih[]={20},pakkath[]={22,1,14,14,1,20},ip[]={22};
	byte end[] = me.subarray(bstr,blen-len,blen);
	byte end23456[]={end[1],end[2],end[3],end[4],end[5]},end5[]={end[4]};
	if(me.startswith(end,pakkath)||me.endswith(bstr,aththil)||(me.startswith(end,pakka)&&(me.endswith(end,il)||me.isequal(end5,ith)))|| me.isequal(end23456,akkath))
	{
		get_sugg(bstr,pakkaththil);
		set_sugg(bstr,ethiril);
		set_sugg(bstr,arukil);
		set_sugg(bstr,maththiyil);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_pakkaththil

public static boolean check_pattern_ethiril(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = ethiril.length;
	if(blen <= len)
		return false;
	byte ethir[]={7,20,3,25},thiril[]={20,3,25,3,26},eth[]={7,20},ir[]={25},ethi[]={7,20,3},il[]={26};
	byte end[] = me.subarray(bstr,blen-len,blen),end4[]={end[3]};
	if(me.startswith(end,ethir)||(me.endswith(bstr,il)&&me.startswith(end,ethi))||(me.startswith(end,eth)&& me.isequal(end4,ir))||me.endswith(end,thiril))
	{
		get_sugg(bstr,ethiril);
		set_sugg(bstr,arukil);
		set_sugg(bstr,maththiyil);
		set_sugg(bstr,pakkaththil);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_ethiril

public static boolean check_pattern_arukil(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = arukil.length;
	if(blen <= len)
		return false;
	byte a[]={1},il[]={26},kil[]={14,3,26},aruk[]={1,25,5,14},ruk[]={25,5,14},uk[]={5,14},rukil[]={25,5,14,3,26};
	byte end[] = me.subarray(bstr,blen-len,blen),end4[]={end[3]},end34[]={end[2],end[3]};
	byte end234[]={end[1],end[2],end[3]};
	if(me.startswith(end,aruk)||me.endswith(bstr,rukil)||(me.startswith(end,a)&&(me.endswith(end,kil)||me.isequal(end34,uk)))|| (me.isequal(end234,ruk)&&me.endswith(end,il)))
	{
		get_sugg(bstr,arukil);
		set_sugg(bstr,pakkaththil);
		set_sugg(bstr,ethiril);
		set_sugg(bstr,maththiyil);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_arukil

public static boolean check_pattern_pathil(byte[] bstr,int blen)
{
	ByteMeth me=new ByteMeth();
	int len = pathil.length;
	if(blen <= len)
		return false;
	byte ip[]={22},thil[]={20,3,26},pathi[]={22,1,20,3},il[]={26},path[]={22,1,20},ith[]={20};
	byte end[] = me.subarray(bstr,blen-len,blen),end4[]={end[3]};
	if((me.endswith(bstr,il)&&me.startswith(end,path))||(me.endswith(bstr,thil)&&me.startswith(end,ip))||me.startswith(end,pathi))
	{
		get_sugg(bstr,pathil);
		set_sugg(bstr,arukil);
		set_sugg(bstr,ethiril);
		set_sugg(bstr,maththiyil);
		set_sugg(bstr,pakkaththil);
		return(true);
	}
	else
		return false;

} // end of function check_pattern_pathil

public void actionPerformed(ActionEvent ae)
{
	String str = ae.getActionCommand();
	String str1,result="";
	if(str.equals("Check"))
	   {
			str1=text1.getText().trim();
			jc.removeAllItems();
			try
				{
					result=spellcheck(str1);
				}
			catch(Exception e)
				{
					System.out.println("exception");
					e.printStackTrace();
				}
			if(result.equals("stack_empty"))
				{
					System.out.println("error");
					text2.setText(result);
				}
			else
				{
					text2.setText(result);
				}
	   }
	else if(str.equals("Close"))
	   System.exit(0);

}	// end of function actionPerformed

public static void main(String args[])
	{
		Hpostposition postposition = new Hpostposition();

	}
}


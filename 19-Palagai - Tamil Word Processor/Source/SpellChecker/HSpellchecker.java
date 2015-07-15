package SpellChecker;
/***********************************************

   Interface for main Window Spell Checker

************************************************/

//   RCILTS - Tamil,
//   Anna University,
//   Chennai.


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JList;
import javax.swing.*;
import java.lang.*;
import java.sql.*;
import analyser.*;


public class HSpellchecker extends JFrame implements ActionListener
	{
		Font f=new Font("TAB-Anna",4,14);
		static JTextArea givenText = new JTextArea();

		static JScrollPane scrollpane1 = new JScrollPane(givenText);
		static DefaultListModel model = new DefaultListModel();
		static JList inCorrectWords = new JList(model);

		//static JScrollPane  scrollpane2 = new JScrollPane(inCorrectWords);
		static JButton split,spellcheck,cancel,suggest,exit,browse;
		static JPanel p1 = new JPanel();

		String text="";
		String string="";
		FileInputStream fileinputstream;
		String[] words=null;
		String correctedword="";
		static String misspelled="";
		String tempstr;
		JFileChooser jfc_filename = new JFileChooser();

		//DefaultHighlighter.DefaultHighlightPainter(Color(23));

		JPopupMenu popmenu;
		Component selectedComponent;

		static byte sandhi[]={14,20,22};


		public HSpellchecker()
		{
			setTitle("SPELL CHECKER");
			givenText.setFont(f);

			DefaultHighlighter dh = new DefaultHighlighter();

			givenText.setHighlighter(dh);

//			final Container content = getContentPane(  );
//			content.setLayout(new FlowLayout(  ));

			givenText.addMouseListener(new MyMouseListener());
//			content.add(givenText);

			popmenu = new JPopupMenu("pop");

/*			String correctedstr="";
			misspelled=inCorrectWords.getSelectedValue().toString();

			try{
				correctedstr = SpchkNoun.checker(misspelled);
			}
			catch(Exception e){e.printStackTrace();}
			if(!correctedstr.equals(misspelled))
			{
				System.out.println("noun");
			}
			else
			{
				System.out.println("verb1");
				correctedstr = Spchkverb.checker(misspelled);
				if(!correctedstr.equals(misspelled))
				{
					System.out.println("verb");
				}
				else
				{
					System.out.println("postpos");
					try{
					correctedstr = Hpostposition.checker(misspelled);}
					catch(Exception e){e.printStackTrace();}
				}
			}*/

//			setVisible(true);

			//modified
			/*givenText.setText("0123456789 aaaaaaa aa");
			DefaultHighlighter dh = new DefaultHighlighter();
			DefaultHighlighter.DefaultHighlightPainter dhp = new
			DefaultHighlighter.DefaultHighlightPainter(Color.green);
			givenText.setHighlighter(dh);
			try
			{
				dh.addHighlight(1,10, dhp);
			}
			catch (javax.swing.text.BadLocationException jst) { }*/

			// ended

			inCorrectWords.setFont(f);
			setSize(700,400);
			browse = addButton(p1,"«è£ğ¢¹");
			//split = addButton(p1,"ğ¤ó¤");
			spellcheck = addButton(p1,"êó¤ğ£ó¢");
			cancel = addButton(p1,"ï¦è¢°");
			suggest = addButton(p1,"İ«ô£ê¬ù");
			exit = addButton(p1,"Í´");
			getContentPane().add(scrollpane1,"Center");
//			getContentPane().add(scrollpane2,"East");
			getContentPane().add(p1,"South");
			addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
			show();

		}	// end of the constructor

		private JMenuItem makeMenuItem(String label)
		{
		   JMenuItem item = new JMenuItem(label);
		   item.addActionListener( this );
		   return item;
		}

		public JButton addButton(JPanel pa,String name)
		  {
			JButton but = new JButton(name);
			but.addActionListener(this);
			but.setFont(f);
			pa.add(but);
			return but;
		  }	// end of function addButton

	  	public void actionPerformed(ActionEvent ae)
	   	{
		   if(ae.getSource()==exit)
				System.exit(0);
		   if(ae.getSource()==suggest)
		   {
				HSugg.model.removeAllElements();
				HSugg.text2.setText("");
				String correctedstr="";
				misspelled=inCorrectWords.getSelectedValue().toString();

				HSugg sug=new HSugg(givenText,misspelled);

				try{
				correctedstr = SpchkNoun.checker(misspelled);}
				catch(Exception e){e.printStackTrace();}
				if(!correctedstr.equals(misspelled))
				{
					System.out.println("noun");
				}
				else
				{
					System.out.println("verb1");
					correctedstr = Spchkverb.checker(misspelled);
					if(!correctedstr.equals(misspelled))
					{
						System.out.println("verb");
					}
					else
					{
						System.out.println("postpos");
						try{
						correctedstr = Hpostposition.checker(misspelled);}
						catch(Exception e){e.printStackTrace();}
					}
				}

			}

			if(ae.getSource()==browse)
			{
				givenText.setText("");
				int option = jfc_filename.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION)
				{
				  try
				  {
					//tempstr=jfc_filename.getSelectedFile().toString();
					fileinputstream=new FileInputStream(jfc_filename.getSelectedFile());

					//added by anu on 30/12/02 while integrating

					string = "";
					try
					{
						while(fileinputstream.available()!=0)
						{
							int finputstream=fileinputstream.read();
							string=string+(char)finputstream;
						}
					}
					catch(Exception e)
					{
					}
					givenText.setText(string);
				}
				catch(Exception e)
				{
				}
				}
			}

		   if(ae.getSource()==cancel)
			{
				givenText.setText("");
				model.removeAllElements();
			}
		   if(ae.getSource()==spellcheck)
		   {
				DefaultHighlighter.DefaultHighlightPainter dhp = new
					DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
				text = givenText.getText().trim();
				spellCheck(text,givenText.getHighlighter(),dhp);
		   }
		   if(ae.getSource()==split)
		   {
			   //modified by anu on 26/12/02 while integrating
			 /*
			 	noun5_temp.tf1.removeAllItems();
			   noun5_temp mor = new noun5_temp();
			   Analyser mor = new Analyser();
			   mr.show();
			   Analyser.tf1.setText(givenText.getSelectedText().toString());
			   Analyser mor = new Ana
			   */
		   }


	   	}	// end of function actionPerformed

		//modified by anu on 27/12/02 while integrating
		public static void spellCheck(String doc, Highlighter dh,
			DefaultHighlighter.DefaultHighlightPainter dhp)
		{
		try
		{
			int s=0,e=0,tempIndex = 0;
			//WordType wt = new WordType();
			String tamilWord="",type = "";
			boolean highlight=false;
			model.removeAllElements();
			HSugg.model.removeAllElements();
			StringTokenizer strTok = new StringTokenizer(doc,"'\n' : ; ,  .'\t' ");

			while(strTok.hasMoreTokens())
			{
				tamilWord = strTok.nextToken();

				// System.out.println(" Tamil Words " + tamilWord);

				// Word type identification using Morphological analyser

				String output = Analyser.analyze(tamilWord);
				TabMethods tm = new TabMethods();

				StringTokenizer strtk = new StringTokenizer(output, "><\n ");
				while(strtk.hasMoreTokens())
				{
					String rootword = strtk.nextToken();
					if(rootword.equals(tamilWord))
					{
						if(!strtk.hasMoreTokens())
						highlight=true;
						else
						continue;
					}
					//System.out.println(rootword);
				}

				//type = wt.whatIs(tamilWord);
				s=doc.indexOf(tamilWord,tempIndex);
				e=doc.indexOf(tamilWord,tempIndex)+tamilWord.length();
				tempIndex += tamilWord.length();
				//s=javax.swing.text.Utilities.getWordStart(givenText,(e+2));
				//e=javax.swing.text.Utilities.getWordEnd(givenText,e);
				byte bstr[] = tabconvert2.convert(tamilWord);
				ByteMeth me=new ByteMeth();


				if(highlight)
				{
					//System.out.println("Mistake word :" +tamilWord);
					//if(me.endswith(bstr,sandhi))
					if(tamilWord.endsWith("ğ¢") || tamilWord.endsWith("è¢"))
					{
						System.out.println("Ending with Sandhi");
					}
					else
					{
					//model.addElement(tamilWord);
					}
					//modi
					try
					{
						System.out.println("s value"+s);

						System.out.println("e value"+e);


					}
				catch(Exception e2){}


					try
					{
						if(tamilWord.endsWith("ğ¢") || tamilWord.endsWith("è¢") || tamilWord.endsWith("ê¢"))
						System.out.println("");
						else
						{
									dh.addHighlight(s,e, dhp);
									highlight=false;
								}

					}
					catch (javax.swing.text.BadLocationException jst)
					{ }
				}//if


			}
			//givenText.setText("0123456789 aaaaaaa aa");
		}catch(Exception e3){System.out.println("e3 : "+e3);}

					//System.out.println(" Checking Completed");

		}	// end of function spellCheck

		public static void main(String args[])
		{
			new HSpellchecker();
		}

		/** This function converts the given byte array to String */

		public static String byteToString(byte by[])
		{
			String byString = new String();
			for(int j=0; j<by.length; j++)
			{
				if((by[j]>=0) && (by[j] <=9))
					byString = byString + "00" +(by[j]);
				else if((by[j]>=10) && (by[j] <=99))
					byString = byString + "0" +(by[j]);
				else
					byString = byString + (by[j]);
			}
			return byString;
		}

		/** This function converts the given String into the byte array */

		public static byte[] stringToByte(String st)
		{
			int slen = st.length();
			byte by[] = new byte[slen/3];
			int j = 0, t=0;

			for(int i=0; i<slen ; i+=3)
			{
				char c=st.charAt(i);
				char c1=st.charAt(i+1);
				char c2 = st.charAt(i+2);
				String s = "";
				String s1 = String.valueOf(c);
				String s2 = String.valueOf(c1);
				String s3 = String.valueOf(c2);

				if( (s1.equalsIgnoreCase("0")) && (s2.equalsIgnoreCase("0")) )
				{
					by[j]=Byte.parseByte(s3);
					j++;
				}
				else if(s1.equalsIgnoreCase("0"))
				{
					s = s2 + s3;
					by[j]=Byte.parseByte(s);
					j++;
				}
				else
				{
					s = s1 + s2 + s3;
					by[j]=Byte.parseByte(s);
					j++;
				}
			}
			return by;
	}


	//modified by anu on 30/12/02 while integrating

	class MyMouseListener extends MouseAdapter
	{
		  //not necessary - remove it
		  /*
		  public void mousePressed(MouseEvent e) { checkPopup(e); }
		  public void mouseClicked(MouseEvent e) { checkPopup(e); }
		  */

		  public void mouseReleased(MouseEvent e) { checkPopup(e); }
	}

	  private void checkPopup(MouseEvent e)
	  {
		if (e.isPopupTrigger(  ))
		{
			int pos = givenText.getCaretPosition();
			selectedComponent = e.getComponent();
			popmenu.show(e.getComponent(), e.getX(), e.getY());
			String text = givenText.getText().trim();
			StringTokenizer strTok = new StringTokenizer(text,"'\n' : ; ,  .'\t' ");
			int carpos1 = givenText.getSelectionStart();
			int carpos2 = givenText.getSelectionEnd();

			int start = pos;
			int end = pos;
			while(!Character.isWhitespace(text.charAt(start))) start--;
			while(!Character.isWhitespace(text.charAt(end))) end++;

			char matchfir = text.charAt(start+1);
			char matchfir2 = text.charAt(start+2);
			char matchfir3 = text.charAt(start+3);
			char matchlas = text.charAt(end-1);

			// Selecting the Possible combination words from Dictionary

			String correctedstr="";

			//misspelled=text.substring(carpos1, carpos2);
			misspelled=text.substring(start+1, end);
			HSugg sugg = new HSugg(givenText,misspelled);

			try{
			correctedstr = SpchkNoun.checker(misspelled);}
			catch(Exception ex){ex.printStackTrace();}
			if(!correctedstr.equals(misspelled))
			{
				System.out.println("noun");
			}
			else
			{
				System.out.println("verb1");
				correctedstr = Spchkverb.checker(misspelled);
				if(!correctedstr.equals(misspelled))
				{
					System.out.println("verb");
				}
				else
				{
					System.out.println("postpos");
					try{
					correctedstr = Hpostposition.checker(misspelled);}
					catch(Exception ex){ex.printStackTrace();}
				}
			}

			System.out.println("carpos1 "+carpos1+"carpos2 "+carpos2+" "+text.substring(carpos1,carpos2)+" "+e.getX()+" "+e.getY()+"\n"+correctedstr);

			//popmenu.add(makeMenuItem("Suggestion 1"));
			//popmenu.add(makeMenuItem("Suggestion 2"));
			//popmenu.remove(0);
		}
	  }
}

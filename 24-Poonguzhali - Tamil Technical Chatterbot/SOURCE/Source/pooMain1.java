
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
public class pooMain1 extends JFrame implements ActionListener
{
   JTextField ttQuestion=new JTextField(30);	//	The variable ttQuestion is the text field for entering the input in transliterated mode
   JTextArea jtaDiscussionArea= new JTextArea(20,10);	//	This text area displays the inputs and the generated outputs cumulatively
   JButton jbEnter = new JButton("ÃÁè");	//	The ENTER Button
   JButton jbExit = new JButton("ïù¢ø¤");	//	The EXIT Button
   JTextArea jtaInfoArea = new JTextArea(3,1);	//	The introductory details are provided in this text area
   JPanel panel = new JPanel(new FlowLayout());	//	The panel to lay the input text field and the buttons
   JScrollPane jspDiscussionArea = new JScrollPane(jtaDiscussionArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);	//	The scroll panel for the discussion area
   JScrollPane jspInfoArea = new JScrollPane(jtaInfoArea);	//	The scroll panel for the introductory details
   Font f = new Font("TAB-Anna",4,16);

   String q[]=new String[100];	//	The question array
   String a[]=new String[100];	//	The answer array
   LinkedList hist_tech_terms=new LinkedList();	//	The history of technical terms that are encountered during the course of the chat are stored as a linked list
   StringBuffer initiateQuestion=new StringBuffer("");	//	The system-initiated question when no input is given
   StringBuffer initiateAnswer=new StringBuffer("");	//	The corresponding answer for the system-initiated question
   StringBuffer last_search_string=new StringBuffer("");	//	The decomposed string for which te previous answer has been provided eg., "Þ¬íòñ¢ + ðòù¢"
   int initiation=0;	//	whether dialogue-initiation by the system has taken place or not
   int cur=0;	//	The current question-answer number
   String file3="";
   String file4="";
   String itemsel="";
   String token1[]=new String[20];
   //FrameMain mf;

   //public pooInternet(FrameMain mf)	//	constructor
   public pooMain1(String file3,String file4,String itemsel)
   {

    	super("Poongkuzhali - Internet");
    	this.file3=file3;
		this.file4=file4;
	   	this.itemsel=itemsel;
//    	this.mf=mf;
    	//mf.hide();
	 	setSize(700,600);
	    Toolkit tk = Toolkit.getDefaultToolkit();
	   // Image img = tk.getImage("gif.GIF");
    	//setIconImage(img);

    	panel.add(ttQuestion);	//	adding the input field to the panel
    	ttQuestion.setFont(f);	//	setting its font
    	panel.add(jbEnter);	//	adding the ENTER button to the panel
    	jbEnter.setFont(f);	//	setting its font
    	panel.add(jbExit);	//	adding the EXIT button to the panel
    	jbExit.setFont(f);	//	setting its font
		jtaDiscussionArea.setLineWrap(true);	//	setting line wrap for the discussion area
		jtaDiscussionArea.setWrapStyleWord(true);	//	setting word wrap for the discussion area
    	jtaDiscussionArea.setText("");	//	clearing the discussion area
    	jtaDiscussionArea.setFont(f);		//	setting its font
    	jtaDiscussionArea.setEditable(false);	// the discussion area is made non-editable
		jtaInfoArea.setText("");	//	clearing the introductory information area
    	jtaInfoArea.setFont(f);	//	setting its font
    	jtaInfoArea.setEditable(false);	// the introductory information area is made non-editable
    	jbEnter.addActionListener(this);	// the action listener is added to the ENTER button
    	jbExit.addActionListener(this);	// the action listener is added to the EXIT button

		getContentPane().add(jspInfoArea,"North");	// the introductory information area is added to the north of the content panel
    	jtaInfoArea.setForeground(Color.blue);	//	The text in the introductory information area appears in blue color
		jbEnter.setForeground(Color.red);	//	The text in the introductory information area appears in red color
		jbExit.setForeground(Color.red);	//	The text in the introductory information area appears in red color
		jtaDiscussionArea.setForeground(Color.black); //	The text in the introductory information area appears in black color
		getContentPane().add(panel,"Center");	//	The textfield/buttons panel is added to the center of the content panel
    	getContentPane().add(jspDiscussionArea,"South");	//	The discussion panel is added to the south of the content panel
		//jtaInfoArea.append("·	Þ¬íòñ¢ âù¢ð¶ àôè¤ù¢ âï¢îð¢ ð°î¤ò¤½ñ¢ à÷¢÷ õ¤ðóé¢è¬÷ õ¦ì¢®ô¤¼ï¢îð®«ò\n                               ªðø¢Áè¢ªè£÷¢÷ àî¾ñ¢ ê£îùñ£°ñ¢.\n·	Þ¶ Ýò¤óè¢èíè¢è£ù õ¬ôð¢ð¤ù¢ùô¢è÷¤ù¢ å¼é¢è¤¬íð¢¹.\n·	Þîù¢ ªêòô¢ð£´è¬÷ ï¤£¢íò¤ð¢ð¶ ¬õòè õ¤£¤¾ õ¬ô âù¢Âñ¢ ï¤Áõùñ¢.\n·	Þ¬íòñ¢ âù¢ð¶ èí¤ð¢ªð£ø¤è÷¤ù¢ ñ¤èð¢ªð£¤ò, ñ¤è õ¤£¤ï¢î, àôè÷£õ¤ò õ¬ô«ð£ù¢ø Þ¬íð¢ð£°ñ¢.\n·	àôè¤ù¢ Üî¢î¬ù Ëôèé¢è÷»ñ¢ îèõô¢ î÷é¢è¬÷»ñ¢ Üµè¤î¢ îèõô¢, ªêò¢î¤,\n                              Üø¤¾ ºîô¤òõø¬¢ø ªï£®ð¢ªð£¿î¤ô¢ Þôõêñ£èð¢ ªðÁõîø¢° Þ¬íòñ¢ ðòù¢ð´è¤ø¶.\n·	Þ¬íòî¢î¤ù¢ ñ¤èð¢ð¤óðô ðòù¢ð£´ ñ¤ù¢ Üë¢êô¢ - ÞîùÍôñ¢ Þ¬íòî¢îð¢¢ \n                               ðòù¢ð´î¢¶ñ¢ Ü¬ùõ¼è¢°ñ¢ è®îñ¢ â¿î¤ ðî¤ô¢ ªðøô£ñ¢.");
		jtaInfoArea.append(file4);
			/*	The introductory information is added to the text area		*/
		show();	//	the content panel is displayed
   		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(1);}});
   			/*	The window listener, which performs clean-up functions during exit	*/
	}

	public void actionPerformed(ActionEvent ae)	//	tracing the button presses
	{
	   if(ae.getSource()==jbEnter)	//	if the ENTER button is pressed
	   {
			 Stringtokenize(file3);
			enter();	//	function enter() is invoked
	   }
	   if(ae.getSource()==jbExit)	//	if the EXIT button is pressed
	   {
		    summary();	//	function summary() is invoked
			this.dispose();	//	close & clear this frame
			//mf.show();	//	show the Main frame

	   }
   }
public void Stringtokenize(String s1)
   	 	{
		//System.out.println(s1);
		int wordNo=0;
		int wordCount=0;
	//	String s1="";

		 StringTokenizer st=new StringTokenizer(s1,",,/,");
		 while(st.hasMoreTokens())
			{
			token1[wordNo]=st.nextToken();
			wordNo++;
			}
		wordCount=wordNo;
		wordNo--;
			for(int i=0;i<wordCount;i++)
			 {

			 //System.out.println("Word "+i+" = "+token1[i] +"\n");
			  }

	}



   public void enter()	//	This function gives the actions to be performed when the ENTER button is pressed
   {
   		q[cur]=ttQuestion.getText().trim();	//	getting the input question into the question array
   		ttQuestion.setText(" ");	//	clearing the input text field
//   		ttQuestion.clear();	//	calling function clear() of the Translit_Text class (to clear its StringBuffer object)
   		Answer ans=new Answer();	//	creating an Answer object
   		a[cur]=ans.answer(q[cur]);	//	passing the input question, generating the answer and storing it into the answer array
   		if(cur>0)	//	if this is not the first input
   		for(int i=0;i<cur;i++)
   		{
   			if(a[cur].equals(a[i]) && !a[cur].equals("Þô¢¬ô.") && !q[cur].equals(""))	//	if the current answer is not empty, not "Þô¢¬ô." and equals a previously generated answer,
   		   	if(a[cur].length()>24)	//	if the length of the current answer is greater than 24 characters
   		   	{
   		   		if(!a[cur].substring(0,24).equals("ï£ù¢ ºù¢«ð Ãø¤ò¶ «ð£ô¢, "))	//	if the first 24 characters of the currently generated answer is the string "ï£ù¢ ºù¢«ð Ãø¤ò¶ «ð£ô¢, "
   		   			a[cur]="ï£ù¢ ºù¢«ð Ãø¤ò¶ «ð£ô¢, "+a[cur];	//	append "ï£ù¢ ºù¢«ð Ãø¤ò¶ «ð£ô¢, " to the start of the currently generated answer
   			}
   			else
   				a[cur]="ï£ù¢ ºù¢«ð Ãø¤ò¶ «ð£ô¢, "+a[cur];	//	/	append "ï£ù¢ ºù¢«ð Ãø¤ò¶ «ð£ô¢, " to the start of the currently generated answer
   		}
   	  	jtaDiscussionArea.append("ï¦é¢è÷¢ : ");	//	display "ï¦é¢è÷¢ : " in the discussion area
   		jtaDiscussionArea.append(q[cur]+" \n");	//	display the user's question in the discussion area
   		jtaDiscussionArea.append("Ìé¢°öô¤ : ");	//	display "Ìé¢°öô¤ : " in the discussion area
   		jtaDiscussionArea.append(a[cur]+"\n \n");	//	display the generated answer in the discussion area
		cur++;	//	increment the value of the 'current' question-answer pair
	}

   public void summary()	//	This function stores the dialogues in the sequence of the chat
   {
   		try
   		{
   		FileOutputStream f3=new FileOutputStream("chatInternet.doc");	//	creating the file "chatInternet.doc"
   		char ch;	//	a temporary character variable
   		String s1="à¬óò£ìô¢ ²¼è¢èñ¢";	//	setting the title string
   		String s3=" ï¦é¢è÷¢ : ";
   		String s4=" Ìé¢°öô¤ : ";
   		for(int i=0;i<s1.length();i++)
   			f3.write(s1.charAt(i));	//	writing to the string s1 to file character by character
   		f3.write('\n');	//	adding a newline to the file
   		for(int i=0;i<22;i++)
   			f3.write('*');	//	underlining the title
   		f3.write('\n');	//	adding a newline to the file
   		f3.write('\n');	//	adding a newline to the file
   		for(int i=0;i<cur;i++)
   		{
   			Integer n=new Integer(i+1);	//	creating an Integer object with the value i+1 - to add serial numbers from 1 to cur
   			String s2=n.toString();	//	converting the serial number variable to a string
   			for(int j=0;j<s2.length();j++)
   				f3.write(s2.charAt(j));	//	writing the serial number to the file
   			f3.write('.');	//	adding a space
   			f3.write(' ');	//	adding a space
   			for(int j=0;j<s3.length();j++)
   				f3.write(s3.charAt(j));		//	writing " ï¦é¢è÷¢ : " to the file
   			for(int j=0;j<q[i].length();j++)
   				f3.write(q[i].charAt(j));	//	writing the user's input to the file
   			f3.write('\n');	//	adding a newline to the file
   			for(int j=0;j<s4.length();j++)
   				f3.write(s4.charAt(j));		//	writing " Ìé¢°öô¤ : " to the file
   			for(int j=0;j<a[i].length();j++)
   				f3.write(a[i].charAt(j));	//	writing the generated answer to the file
   			f3.write('\n');	//	adding a newline to the file
   			f3.write('\n');	//	adding a newline to the file
   		}
   		} catch(Exception e){System.out.println(e);}
	}

	   private class InputWordList	//	this class are required in order to parse the input
	   {
		   Vector prioVector=new Vector();	//	the priorities of the words in the input string are stored in this vector
		   LinkedList linkList=new LinkedList();	//	a linked list of the node structure (consisting of word number, word and priority) is created with the words in the input string
		   LinkedList doneList=new LinkedList();	//	the processed words are stored in this linked list
	   }

   private class Answer
   {
	Analyser manalyzer=new Analyser();	//	The Analyzer class is instantiated
	InputWordList iList=new InputWordList();	//	An object of the InputWordList class is created
	String answerString="";	//	The generated answer is stored in this temporary string
	String token[]=new String[100];	//	each word in the input string is stored separately in the token array
	int wordNo=0;	//	the number of words input
	int wordCount=0;	//	the input word count
	String tech_term[]=new String[100];	//	the technical terms in the input are stored in this array
	String split_token[]=new String[100];	//	the roots of the words in the input string, obtained after processing by the Analyzer, are stored in this array
	int techTermCount=0;	//	the count of the technical terms in the input
	int definition=0;	//	is it a definition-type question?
	int pronoun=0;	//	is a pronoun used instead of a technical term?
	int describe=0;	//	is a description requested?
	int yesno=0; // is it a yes/no type of question?
	int negation=0;	//	is there a negation in the input? i.e., is it a negative question?
	String searchString="";	//	a temporary variable to store the search string
	int numans=0;	//	the number of answers available for the input question
	int passive=0;	//	is the input in passive voice?
	int passiveIndex=-1; // the number of the word in which passive voice ("ð´ < auxillary verb >") occurs

	private String answer(String q)
   {
	   tokenize(q);	//	tokenize the input into words
		if(wordCount>0)	// if input is non-empty
		{
			setQuestionType();	//	identify the type of the input question
			getTechTerms();	//	store the technical terms found in the input in an array
			for(int i=0;i<techTermCount;i++)
				//System.out.println("techterm"+i+tech_term[i]);	//	display the technical terms in the input

			setHistory();	//	set the history data from the current input question
			for(int i=0;i<hist_tech_terms.size();i++)
				//System.out.println("linkedlist_tech"+hist_tech_terms.get(i).toString());	//	display the history of technical terms

			setDefnDesc();	//	identify if the input is a request for a definition or a description
			//System.out.println("Definition"+definition+" , describe"+describe+"wordCount="+wordCount);	//	display the values of the flags

			if(describe==0 && definition==0)	//	if not a request for a definition or a description
			{
				createList();	//	create the linked list of words in the input

				for(int k=0;k<iList.linkList.size();k++)
				{
					node n1=new node();
					n1=(node)iList.linkList.get(k);
					n1.print();	//	display the word number, root word and its priority for the input words
				}

				/*Identifying the highest priority word*/

				while(!iList.prioVector.isEmpty() && answerString.equals("")) //fail gracefully
				{
					int max=9999,maxIndex=0;	//max - the largest element in the list; maxIndex - its index
					if(iList.prioVector.size()>0)
						max=Integer.parseInt(iList.prioVector.firstElement().toString());	//set the first element as max
					for(int i=0;i<iList.prioVector.size();i++)	//identifying the largest element (max)
					{
						int temp=Integer.parseInt(iList.prioVector.get(i).toString());
						//System.out.println("iList.prioVector:"+temp);
						if(max>temp)
						{
							max=temp;
							maxIndex=i;
						}
					}
					String highPriorityWord=getHighPriorityWord(max);	//	identifying the highest priority word

					/*Getting all the lists of links for the highest priority word into a string*/

					String pattern="";	//	the pattern to be searched in the KB
					if(!highPriorityWord.equals("")) /*if highPriorityWord is present */
					{
						String srch_hpword="$"+highPriorityWord+"$";	// a temporary string used as an argument
						String links=getLinks(srch_hpword);	//	the links of the highest priority word

						//System.out.println("links:"+links);
						links=links+"#";	//	adding a terminating symbol
						/*Got all the lists of links for the highest priority word into a string*/

						pattern=pattern+getLexeme(links);	//	getting the actual search pattern from the links
						if(pattern.equals(""))
							pattern=highPriorityWord;	//	if no links, the highest priority word is taken as it is
						else
							pattern=highPriorityWord+" + "+pattern;	// else, the identified pattern is appended to it
						//System.out.println("pattern:"+pattern);
					}   /*if highPriorityWord is present */

						if(pattern.equals(""))
						{
							if(techTermCount>0)
						   searchString="$"+tech_term[techTermCount-1]+"$";	//	if a pattern is still not found, the technical term is used as such
						}
						else
						{
							if(techTermCount>0)
								searchString="$"+tech_term[techTermCount-1]+" + "+pattern+"$";	//	the tech. term + the pattern is set as the search string
							else
								searchString="$"+pattern+"$";	//	if no tech term is present, the pattern alone is set as the search string
						}
						answerString=getFromFile(searchString);	//	the search string is used as argument to get the answer string

						if(answerString.equals(""))
							iList.prioVector.removeElementAt(maxIndex);	//	if no match is found, remove the highest priority element from the list and process the next highest priority element
					  }//fail gracefully
}//if not endral enna

	if((answerString.equals("") && techTermCount>0) || definition==1 || describe==1)	// if an answer has not yet been found or the definition/describe flag has been set
	{
		if(definition==1)	//	if it is a definition
		{
			searchString="$";
			for(int k=0;k<=wordNo-3;k++)
				searchString=searchString+token[k]+" ";
			searchString=searchString+token[wordNo-2]+"$";	//	set the search string as all words from the question except the last two
		}
		else if(techTermCount>0 && describe==0)
			searchString="$"+tech_term[techTermCount-1]+"$";	//	if the question is incomprehensible but for the tech term, set it as search string
		answerString=getFileData(searchString);	//	set answer string from file
	}
		if(answerString.equals("") && yesno==1)	//	if it is an incomprehensible yes/no question, set "Þô¢¬ô." as the answer string
			answerString="Þô¢¬ô.";
		if(answerString.equals("") && initiation==1 && negation==0)	//	if it is a system-initiated question
		{
			answerString=initiateAnswer.substring(0);	//	get the system-initiated answer
			initiation=0;
		}
		if(answerString.equals("") && initiation==1 && negation==1)	//	if the user does not want an answer for the system-initiated question
		{
			answerString=init();	//	initiate a dialogue afresh
		}
		if(answerString.equals(""))	//	if the question is totally incomprehensible
			answerString="ï£ñ¢ "+itemsel+"¢ ðø¢ø¤ð¢ «ð²è¤«ø£ñ¢.";

}// if input present
	else	//	if there is no user input, initiate a dialogue
	{
		//System.out.println("NO I/P");
		if(initiation==0)
			answerString=init();
		else
		{
			answerString=initiateAnswer.substring(0);
			initiation=0;
		}
	}
	return answerString;
}

private String getLexeme(String links)	//	given the links, get the lexeme
{
		String str="";
		char ch;
		int matched=0,nomatch;
		int i=0;
		while(i<links.length() && matched==0)	//	if there are more links and a match has not been found
		{
			ch=links.charAt(i);	//	scanning the links
			i++;
			if(ch=='~')	//	if the start of a link has been found,
			{
				Vector v=new Vector();	//	create a new vector
				ch=links.charAt(i);
				i++;
				while(ch!='~' && ch!='#')	//	while a new link or end of links is not encountered
				{
					String s5="";	// a temp. str s5
					while(ch!=',' && ch!='~' && ch!='#')	//	getting the current element of the current link
					{
						s5=s5+ch;
						ch=links.charAt(i);
						i++;
					}
					v.addElement(s5);	//	adding this element to the vector
					if(i<links.length() && ch==',')	//	skip commas
					{
						ch=links.charAt(i);
						i++;
					}
				}
				String str1="";
				nomatch=0;

				/*To check if the elements in the link are available in the input*/
				for(int m=0;m<v.size() && nomatch==0;m++)
				{
					String s=v.get(m).toString();
					int v_elmt=Integer.parseInt(s);
					for(int k=0;k<iList.linkList.size() && nomatch==0;k++)
					{
						node n2=(node)iList.linkList.get(k);
						int n=n2.getwno();
						if(v_elmt==n)
						{
							if(str1.equals(""))
							   str1=str1+n2.getword();
							else
							   str1=str1+" + "+n2.getword();
						}
					}
						if(str1.equals(""))
							nomatch=1;
				}
				/*Checked if the elements in the link are available in the input*/

				if(nomatch==0)
					matched=1;
				if(matched==1)
					str=str+str1;
			}
		}
		return str;
}
private String getFileData(String searchString)	//	get the answer from the KB using the search String
{
		String s5="",str="";
		int flag=0,found=0;
		int cnt=-1;
		char ch=' ';
		try
		{
			 BufferedReader f1 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(token1[0])));
			//FileInputStream f1=new FileInputStream("KBInternet.txt");
			if(describe==1)	//	if a description is asked for, get the previous search term
				searchString=last_search_string.substring(0,last_search_string.length());
			found=fnd(f1,searchString);
			if(found==1 && describe==0)	//	if the pattern has been found and a description of the previous question is not asked for
			{
				last_search_string.replace(0,last_search_string.length(),searchString);
				ch=(char)f1.read();
				while(ch!='n')
					ch=(char)f1.read();
				ch=(char)f1.read();
				s5=s5+ch;
				numans=Integer.parseInt(s5);
				String ans[]=new String[numans];
				int chosenans;
				if(numans>1)	//	if more than 1 answer is available,
				{
				Random r=new Random();
				chosenans=r.nextInt(numans);	//	choose one at random
				if(chosenans==numans)
					chosenans--;
				}
				else
					chosenans=0;
				while((ch=(char)f1.read())!='#')
				{
					if(ch=='~')
					{
						cnt++;
						ans[cnt]="";
					}
					else
					{
						ans[cnt]=ans[cnt]+ch;
					}
				}
					str=ans[chosenans];

				if(passive==1)	//	if the question is in passive voice, append the passive form
					str=str+" Þõ¢õ£Á "+tech_term[techTermCount-1]+" "+token[passiveIndex]+".";

			  }
			else if(describe==1)	//	if a description is asked for, get the alternate response
			{
				String stemp="";
				ch=(char)f1.read();
				while(ch!='#')
					ch=(char)f1.read();
				ch=(char)f1.read();
				stemp=stemp+ch;
				while((ch=(char)f1.read())!='%')
				{
					stemp=stemp+ch;
				}
				str=stemp;
			}
		} catch(Exception e){System.out.println(e);}
	return str;
}
private String getFromFile(String searchString)	//	get the answer from the KB using the search String
{
		String s5="";
		String str="";
		int flag=0,found=0;
		int cnt=-1;
		char ch=' ';
		try
		{
			//FileInputStream f1=new FileInputStream("KBInternet.txt");
			 BufferedReader f1 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(token1[0])));
			if(describe==1)	//	if a description is asked for, get the previous search term
				searchString=last_search_string.substring(0,last_search_string.length());
			found=fnd(f1,searchString);
			if(found==1 && describe==0)	//	if the pattern has been found and a description of the previous question is not asked for
			{
				last_search_string.replace(0,last_search_string.length(),searchString);
				ch=(char)f1.read();
				while(ch!='n')
					ch=(char)f1.read();
				ch=(char)f1.read();
				s5=s5+ch;
				numans=Integer.parseInt(s5);
				String ans[]=new String[numans];
				int chosenans;
				if(numans>1)	//	if more than 1 answer is available,
				{
				Random r=new Random();
				chosenans=r.nextInt(numans);	//	choose one at random
				if(chosenans==numans)
					chosenans--;
				}
				else
					chosenans=0;
				while((ch=(char)f1.read())!='#')
				{
					if(ch=='~')
					{
						cnt++;
						ans[cnt]="";
					}
					else
					{
						ans[cnt]=ans[cnt]+ch;
					}
				}
				if(yesno==1)	//	if yes/no type of question
					str="Ýñ¢, "+ans[chosenans];
				else
					str=ans[chosenans];

				if(passive==1)	// if question in passive voice
					str=str+" Þõ¢õ£Á "+tech_term[techTermCount-1]+" "+token[passiveIndex]+".";
			}
			else if(describe==1)	// if a description is sought
			{
				String stemp="";
				ch=(char)f1.read();
				while(ch!='#')
					ch=(char)f1.read();
				ch=(char)f1.read();
				stemp=stemp+ch;
				while((ch=(char)f1.read())!='%')
				{
						stemp=stemp+ch;
				}
					str=stemp;
			}
		} catch(Exception e){System.out.println(e);}
		return str;
}
private String getLinks(String srch_hpword)	//	getting the links of the highest priority word
{
	String links="";
	try
	{
		BufferedReader f1 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("words.txt")));
	//FileInputStream f1=new FileInputStream("words.txt");
	int j;
	if((j=fnd(f1,srch_hpword))==1)
	{
		char ch=(char)f1.read();
		while(ch!='~' && ch!='#')
			ch=(char)f1.read();
		while(ch!='#')
		{
			links=links+ch;
			ch=(char)f1.read();
		}
	}
	}catch(Exception e){System.out.println(e);}
	return links;
}
private String getHighPriorityWord(int max)	//	getting the highest priority word, using the priorty value
{
	String highPriorityWord="";
	node n1=new node();
	for(int i=0;i<iList.linkList.size() && highPriorityWord.equals("");i++)	//	scanning the linked list created from the input
	{
		node n2=new node();
		n2=(node)iList.linkList.get(i);
		if(n2.getprio()==max)	//	if the currently scanned input word has the highest priority
		{
			n1=(node)iList.linkList.get(i);
			int flag=0;
			for(int k=0;k<iList.doneList.size();k++)
			{
				if(iList.doneList.get(k).equals(n1.getword()))
					flag=1;
			}
			if(flag==0)
				highPriorityWord=highPriorityWord+n1.getword();	//	get the corresponding word
			iList.doneList.add(n1.getword());
		}
	}
	return highPriorityWord;
}

private void createList()	//	Creating a linked list of the root words from the input
{
	char ch;
	int j;
	try
	{
		BufferedReader f1 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("words.txt")));
//		FileInputStream f1=new FileInputStream("words.txt");
		StringBuffer sb=new StringBuffer("");
		int flag=0,found=0;
		ch=' ';
		while(ch!=(char)-1)
		{
			ch=(char)f1.read();
			if(ch=='$')
			{
				sb.append(ch);
				if(flag==0)
					flag=1;
				else
				{
					for(int i=0;i<=wordNo;i++)
					{
						if(sb.substring(0).equals("$"+split_token[i]+"$"))
						{
							found=1;
							String s1="";
							String s2="";
							String s3="";
							ch=(char)f1.read();
							while(ch!='n')
								ch=(char)f1.read();
							ch=(char)f1.read();
							while(ch!='w')
							{
								s1=s1+ch;
								ch=(char)f1.read();
							}
							ch=(char)f1.read();
							while(ch!='p')
							{
								s2=s2+ch;
								ch=(char)f1.read();
							}
							ch=(char)f1.read();
							while(ch!='p')
							{
								s3=s3+ch;
								ch=(char)f1.read();
							}
							int wno=0;
							int prio=0;
							wno=Integer.parseInt(s1);
							prio=Integer.parseInt(s3);
							iList.prioVector.addElement(s3);
							node n=new node();
							n.add(wno,s2,prio);
							iList.linkList.add(n);
						}
					}
					sb.delete(0,sb.length());
					flag=0;
				}
			}
			if(flag==1 && ch!='$')
				sb.append(ch);
		}
	}catch(Exception e){System.out.println(e);}
}

private String init()	//	To initiate a dialogue
{
		initiation=1;
		String initQuestion="";
		String initAnswer="";
		int found=0;
		try
		{
			BufferedReader f5 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(token1[1])));
//			FileInputStream f5=new FileInputStream("Internet_starters.txt");
			char ch;
			if(hist_tech_terms.size()>0)
			{
				String searchString="$"+hist_tech_terms.getLast().toString()+"$";	//	use the last technical term to inititate a dialogue
				if((found=fnd(f5,searchString))==1)
				{
					hist_tech_terms.removeLast();	//	remove it, once it is used - to avoid repetitions
					ch=(char)f5.read();
				}
			}
			if(found==0)
			{
				if(hist_tech_terms.size()>0)	//	if no technical term remains to be used
					f5.close();
				//BufferedReader filetodo = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(token1[1])));
				File filetodo = new File("Internet_starters.txt");
				//System.out.println("file length :"+filetodo.length());
				int fileLength=(int)filetodo.length();
				Random r=new Random();
				int randNum=r.nextInt(fileLength-200);	//	get a question from the Internet_starters file at random
				f5 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(token1[1])));
//				f5=new FileInputStream("Internet_starters.txt");
				f5.skip(randNum);
				ch=(char)f5.read();
				while(ch!='~')
					ch=(char)f5.read();

			}
			ch=(char)f5.read();
			while(ch!='?')
			{
				initQuestion=initQuestion+ch;
				ch=(char)f5.read();
			}
			initQuestion=initQuestion+ch;
			ch=(char)f5.read();
			while(ch!='#')
			{
				initAnswer=initAnswer+ch;
				ch=(char)f5.read();
			}
			if(initiateQuestion.length()>0)	//	delete the initiation questions and answers once they are used
				initiateQuestion.delete(0,initiateQuestion.length());
			initiateQuestion.append(initQuestion);
			if(initiateAnswer.length()>0)
				initiateAnswer.delete(0,initiateAnswer.length());
			initiateAnswer.append(initAnswer);
		}catch(Exception e){System.out.println(e);}
		return initQuestion;
}
private void setDefnDesc()	//	set the definition and describe flags if necessary
{
if(wordCount>1)
{
	 if((token[wordNo-1].equals("âù¢ø£ô¢") || token[wordNo-1].equals("âù¢ð¶") || token[wordNo-1].equals("ðø¢ø¤è¢") || token[wordNo-1].equals("ðø¢ø¤è¢")) && (token[wordNo].equals("âù¢ù") || token[wordNo].equals("ò£¶") || token[wordNo].equals("ÃÁ") || token[wordNo].equals("ÃÁè") || token[wordNo].equals("ÃÁé¢è÷¢")))
	{
		definition=1;
	}
}
else if(wordCount==1 && techTermCount>0)
{
	definition=1;
}
if(wordCount<=2)
{

	if(wordCount==1)
		if(token[0].equals("¹ó¤òõ¤ô¢¬ô") || token[0].equals("Üð¢ð®ªòù¢ø£ô¢") || token[0].equals("õ¤õó¤")
			|| token[0].equals("õ¤õó¤è¢è") || token[0].equals("õ¤õó¤»é¢è÷¢"))
			describe=1;
	if(wordCount>1)
	{
		if((token[0].equals("Üð¢ð®") && token[1].equals("âù¢ø£ô¢"))
			|| split_token[0].equals("õ¤õóñ¢") || token[1].equals("ÃÁè") || token[1].equals("ÃÁ")
			|| token[0].equals("ÃÁé¢è÷¢"))
				describe=1;
	}
}
}
private void setHistory()	//	set history data
{
	if(hist_tech_terms.size()==0 && techTermCount>0)
		hist_tech_terms.add(tech_term[techTermCount-1]);
	else
	{
		int inlist=0;
		for(int i=0;i<hist_tech_terms.size();i++)
		{
			if(techTermCount>0)
			{
				if(tech_term[techTermCount-1].equals(hist_tech_terms.get(i).toString()))
					hist_tech_terms.remove(i);
			}
		}
		if(techTermCount>0)
			hist_tech_terms.add(tech_term[techTermCount-1]);
	}
}
private void setQuestionType()	//	set question type
{
	for(int i=0;i<=wordNo;i++)
	{
			//System.out.println("Wordno:"+i);
			String temp="",temp0="",temp01="",temp02="",temp1="";
			int j=1;
			split_token[i]="";
			temp0=temp0+manalyzer.analyze(token[i]);
			//System.out.println("org.temp="+temp0);
			temp01=temp01+temp0.substring(1,temp0.length());		// remove '\n' at the beginning of the String

			if(temp01.length()>0)
			{
			if(temp01.charAt(0)=='\n')
				temp02=temp02+temp01.substring(1,temp01.length());
			else
				temp02=temp02+temp01;
			}
			else
							temp02=temp02+temp01;

			if(i==wordNo)
			{
				int lastindex=temp02.lastIndexOf("<");
				if(lastindex>0)
				{
				String lasttemp=temp02.substring(0,lastindex-1).trim();
				if(lasttemp.charAt(lasttemp.length()-1)=='Ý')	//	if the last word ends with Ý, set yesno flag
					yesno=1;
				}
			}
			//System.out.println("yesno="+yesno);
			int tempindex=temp02.indexOf("\n");
			if(tempindex>0)
				temp=temp+temp02.substring(0,tempindex);
			else
				temp=temp+temp02;

			tempindex=temp.indexOf("<");

			//System.out.println("temp="+temp+" , tempindex="+tempindex);
			if(tempindex==-1)
				temp1=temp.trim();
			else
				temp1=temp.substring(0,tempindex-1).trim();
			split_token[i]=split_token[i]+temp1;
			int negindex=temp0.indexOf("neg");	//	set negation flag
			if(negindex>-1)
			{
				if(negation==0)
					negation=1;
				if(negation==1)
					negation=0;
			}

			int pronounindex=temp.indexOf("pronoun");	//	set pronoun flag
			if(pronounindex>-1)
				pronoun=1;

			int voiceindex=temp0.indexOf("ð´ < auxillary verb >");	//	set passive voice flag
			if(voiceindex>-1)
			{
				passive=1;
				passiveIndex=i;
			}
	}
}
private void getTechTerms()	//	identify technical terms, including compounds of upto 3 words
{
	String srch_techterm;
	int found3=0;
	int found2=0;
	int found1=0;

	try
	{
	//System.out.println("Inside try block");
	for(int i=1;i<wordNo;i++)
	{
		srch_techterm="$"+split_token[i-1]+" "+split_token[i]+" "+split_token[i+1]+"$";
		BufferedReader f2 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(token1[2])));
		//FileInputStream f2=new FileInputStream("Internet_techterms.txt");
		if((found3=fnd(f2,srch_techterm))==1)
		{
			tech_term[techTermCount]=""+split_token[i-1]+" "+split_token[i]+" "+split_token[i+1];
			techTermCount++;
			i=i+2;
		}
		if(found3==0)
		{
			String srch_techterm1="$"+split_token[i-1]+" "+split_token[i]+"$";
			BufferedReader f6 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(token1[2])));
			//FileInputStream f6=new FileInputStream("Internet_techterms.txt");
			if((found2=fnd(f6,srch_techterm1))==1)
			{
				tech_term[techTermCount]=""+split_token[i-1]+" "+split_token[i];
				techTermCount++;
				i=i+1;
			}
		}
		if(found2==0 && found3==0)
		{
			String srch_techterm1="$"+split_token[i-1]+"$";
			BufferedReader f6 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(token1[2])));
			//FileInputStream f6=new FileInputStream("Internet_techterms.txt");
			if((found1=fnd(f6,srch_techterm1))==1)
			{
				tech_term[techTermCount]=""+split_token[i-1];
				techTermCount++;
				i=i+1;
			}
		}
	}
	if(found3==0 && found2==0)
	{
			String srch_techterm2="$"+split_token[wordNo-1]+" "+split_token[wordNo]+"$";
			BufferedReader f7 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(token1[2])));
			//FileInputStream f7=new FileInputStream("Internet_techterms.txt");
			if((found2=fnd(f7,srch_techterm2))==1)
			{
				tech_term[techTermCount]=""+split_token[wordNo-1]+" "+split_token[wordNo];
				techTermCount++;
			}
	}
		if(found2==0 && found3==0)
		{
			String srch_techterm1="$"+split_token[wordNo-1]+"$";
			BufferedReader f7 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(token1[2])));
			//FileInputStream f7=new FileInputStream("Internet_techterms.txt");
			if((found1=fnd(f7,srch_techterm1))==1)
			{
				tech_term[techTermCount]=""+split_token[wordNo-1];
				techTermCount++;
			}
		}

		if(found2==0 && found3==0)
		{
			String srch_techterm1="$"+split_token[wordNo]+"$";
			BufferedReader f6 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(token1[2])));
			//FileInputStream f6=new FileInputStream("Internet_techterms.txt");
			if((found1=fnd(f6,srch_techterm1))==1)
			{
				tech_term[techTermCount]=""+split_token[wordNo];
				techTermCount++;
			}
		}
	}catch(Exception e){System.out.println(e);}
	if(techTermCount==0 && pronoun==1 && hist_tech_terms.size()>0)
	{
		tech_term[0]=hist_tech_terms.getLast().toString();
		techTermCount++;
	}
}

private void tokenize(String q)	//	split the input into tokens
{
	StringTokenizer st=new StringTokenizer(q," !;,.?");
	while(st.hasMoreTokens())
	{
		token[wordNo]=st.nextToken();
		wordNo++;
	}
	wordCount=wordNo;
	wordNo--;
	//for(int i=0;i<wordCount;i++)
		//System.out.println("Word "+i+" = "+token[i]);
}

private int fnd(BufferedReader f1, String searchString)	//	find a given string in a given file
{
	StringBuffer sb=new StringBuffer("");
	int flag=0,found=0;
	char ch=' ';

		//System.out.print("This is search string : "+searchString);
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
					if(searchString.equals(sb.substring(0)))
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
			//System.out.println(found);
			return found;
	}
}
}
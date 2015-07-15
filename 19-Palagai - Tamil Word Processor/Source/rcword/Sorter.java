package rcword;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.util.*;
import java.util.StringTokenizer;
import javax.swing.*;

/**
 * Used to do the paragraph sorting.
 * First gets the text in the workspace and separates that into paragraphs by
 * listening the new line character (‘\n’). Converts them in to internal code. Checks
 * the first character of the paragraphs. If this is Tamil then this paragraph is kept
 * in Tamil paragraph list. Otherwise this paragraph is kept in the NonTamil paragraph
 * list. Sorts these two lists separately using java build in method. Displays the
 * sorted Tamil paragraphs first and then Non-Tamil paragraphs list.
 * <p> The sorting process will be done with the assumption that a paragraph is either fully
 * Tamil of Non-Tamil. Otherwise the results may be incorrect. </p>
 *
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class Sorter
{
	/** a reference to the main <code> Word </code> object */
	Word parentword;
	/** position where the sorting process has to be considered in the workspace.*/
    int startat = -1;
	/** arraylist which contains the list of Tamil paragraphs.*/
    ArrayList al_Tamil_Paras 		= new ArrayList();    //n
	/** arraylist which contains the list of Tamil paragraphs internal code.*/
    ArrayList al_Tamil_Paras_Code 	= new ArrayList();    //n
	/** arraylist which contains the list of English paragraphs.*/
    ArrayList al_Eng_Paras 			= new ArrayList();   //n
	/** arraylist which contains the list of all paragraphs.*/
    ArrayList al_ParaList_string 	= new ArrayList();    //n
	/** contains the whole text to be sorted.*/
    String str_whole_text = "";

	/**
	* Constructs an object of <code>  Sorter </code> with
	* a reference to the main <code> Word </code> object
	* @param word a reference to the main <code> Word </code> object
	*/
    Sorter(Word word)
	{
		parentword = word;
 		try
        {
			if(parentword.workspace.getSelectedText()==null)
			{ // if there is no selection, then select the whole text
				parentword.workspace.selectAll();
				startat = 0;
			}
			str_whole_text = parentword.workspace.getSelectedText();

			if(str_whole_text == null )
			{
				System.out.println(" text not available ");
				return;
			}

			if(startat!=0)
				startat = parentword.workspace.getSelectionStart();
			// tokenizer to get the paragraphs
			StringTokenizer sttkn_text = new StringTokenizer(str_whole_text);

			while(sttkn_text.hasMoreTokens())
			{ // gets the each para and trims it and put in the list of paragraphs to be sorted.
				    String str_temp_para = sttkn_text.nextToken("\n");
				    String str_temp_para_copy = str_temp_para.trim();
                    al_ParaList_string.add(str_temp_para_copy);
			}

            for(int i = 0; i < al_ParaList_string.size(); i++)
             {  /*checks each paragraphs whether it is Tamil or English.
 			If this is tamil then puts this para in Tamil list otherwise
			puts this in the English list of paragraphs to sort separately.
			*/
				 String str = (String) al_ParaList_string.get(i);
				 try
				 {
					 boolean lang = tabconvert2.checkLanguage(str.substring(0,1));
					 if(lang)
					 	al_Tamil_Paras.add(al_ParaList_string.get(i));
					 else
					 	al_Eng_Paras.add(al_ParaList_string.get(i) );
				 }
				 catch(Exception e)
				 {
					 System.out.println(e+"\n---->  exceee : sorter");
				 }
			 }

			 Object[] Tamil_object = new Object[al_Tamil_Paras.size()];
			 Object[] Eng_object = new Object[al_Eng_Paras.size()];
			 String t_text = "";//\n\n";

			 if(al_Tamil_Paras != null)
			 {
					Tamil_object = al_Tamil_Paras.toArray();
					for(int i = 0; i<Tamil_object.length;i++)
					{
						byte[] t_code = tabconvert2.convert( (String) Tamil_object[i] );
						show(t_code);
						al_Tamil_Paras_Code.add( new String(t_code) );
					}
					Object[] Tamil_object_code = al_Tamil_Paras_Code.toArray();
					Arrays.sort(Tamil_object_code);
                    for(int i=0;i<Tamil_object_code.length;i++)
					{
						 show(( (String)Tamil_object_code[i] ).getBytes() );
						 String tr = tabconvert2.revert(  ( (String)Tamil_object_code[i] ).getBytes()  );
						 t_text = t_text+"\n"+tr;
					}
		     }


			 if(al_Eng_Paras != null)
			 {
			    Eng_object = al_Eng_Paras.toArray();
			 	Arrays.sort(Eng_object);
			 	for(int i = 0; i < Eng_object.length; i++)
				{
					t_text = t_text+"\n"+Eng_object[i];
				}
			 }

			if(parentword.workspace.getSelectedText() != null)
			{
					//t_text = parentword.workspace.getSelectedText() + t_text ;
					//parentword.workspace.setText(t_text);
					parentword.workspace.replaceSelection(t_text);
			 }
			 else
					parentword.workspace.replaceSelection(t_text);

    	}
    	catch(Exception e)
    	{
			System.out.println(e+"\n ----> e at heree: sorter");
			//e.printStackTrace();

		}

   }// end of method

	/**
	* Used to show the code for testing purpose.
	*/
   public void show(byte[] word)
   {
	   //System.out.println("");
	   //for(int i = 0 ; i< word.length;i++)
	   //		System.out.print(" "+word[i]);
	   //System.out.println("");
	}



}// end of class


//notes 1:   can set "96 for a" instead of "40 0 for a"


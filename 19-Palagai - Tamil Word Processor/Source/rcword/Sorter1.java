// not updated
package rcword;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.util.*;
import java.util.StringTokenizer;
import javax.swing.*;

/**
 *
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class Sorter1
{
	/**
			 * a reference to the main <code> Word </code> object
		 */
	Word parentword;
	String str_wholetext = ""; 				//the text to be sorted

	int startat = -1;        				// starting point of the text to be sorted
	int paraend = -2;        				// end point of a particular para
    int para_count = 0;

    ArrayList al_ParaList = new ArrayList();// list of SortPara's
    //SortParaList spl_ParaList = new SortParaList();

    ArrayList al_ParaList2 = new ArrayList();
    ArrayList al_ParaOrder = new ArrayList();

	Sorter1(Word word)
	{
		parentword = word;
 		try
        {

			if(parentword.workspace.getSelectedText()==null)
			{
				//System.out.println("  1   ");
				parentword.workspace.selectAll();
				startat = 0;
			}
			str_wholetext = parentword.workspace.getSelectedText();
			if(startat!=0)
			{
				startat = parentword.workspace.getSelectionStart();
				parentword.workspace.selectAll();
				String str_temp_1 = parentword.workspace.getSelectedText();
				//startat = str_temp.indexOf(str_wholetext);
                String str_temp_2 = str_temp_1.substring(0,startat-1);
                startat = str_temp_2.lastIndexOf("\n")+1;
                str_wholetext = str_temp_1.substring(startat);
			}

			System.out.println(str_wholetext);
			paraend = str_wholetext.indexOf("\n");

			if(paraend == -1)
			{   // this is for the text which has only one paragraph.
				 //System.out.println("nothing to sort");
				 return;
			}
			paraend--;

			////System.out.println("startat: "+startat);
			////System.out.println("paraend :"+paraend);
			int para_start = 0, para_end = paraend+1;
			//System.out.println("	"+"SN"+"     "+"St pt"+"     "+"endpt"+"   "+"first word");
			//System.out.println("      ----------------------------------------");
            do
            {
                String str_para = str_wholetext.substring(para_start,para_end);
				StringTokenizer sttkn_para = new StringTokenizer(str_para);
				String str_first_word ;
				byte code[] = null;
				if(sttkn_para.hasMoreElements())
				{
					 str_first_word = sttkn_para.nextToken();
					 code = tabconvert2.convert(str_first_word);
				}
				else str_first_word = null;

				//SortPara sp_temp = new SortPara(para_count++, startat+para_start,startat+para_end,str_first_word);
				//sp_temp.show1();
				//SortPara sp_temp = new SortPara(para_count++, startat+para_start,startat+para_end,code);
				//sp_temp.show_code();
				//spl_ParaList.add(sp_temp);

				para_start = para_end+1;
				para_end = str_wholetext.indexOf("\n",para_start);

		    }
		    while(para_end != -1);

		    String str_para = str_wholetext.substring(para_start);
		    StringTokenizer sttkn_para = new StringTokenizer(str_para);
			byte code[] = null;
			String str_first_word ;
			if(sttkn_para.hasMoreElements())
			{
				 str_first_word = sttkn_para.nextToken();
				 code = tabconvert2.convert(str_first_word);
			}
			else str_first_word = null;
			//SortPara sp_temp = new SortPara(para_count++,para_start,str_wholetext.length()-1,str_first_word);
			//sp_temp.show1();
			//3-12-2002   SortPara sp_temp = new SortPara(para_count++,para_start,str_wholetext.length()-1,code);
			//3-12-2002  sp_temp.show_code();
			//spl_ParaList.add(sp_temp);
			//spl_ParaList.show();

	    }
		catch(Exception e)
		{
			System.out.println(e+"  \n---> e at here  : sorter1");
			//e.printStackTrace();
		}

	}

}

/*String str_para = str_wholetext.substring(0,paraend);
	        //System.out.println("para :");
	        System.out.println(str_para);
	        StringTokenizer sttkn_para = new StringTokenizer(str_para);
	        String str_first_word = sttkn_para.nextToken();
            //System.out.println("first word :"+str_first_word);
            SortPara sp_temp = new SortPara(para_count++,startat,startat+paraend,str_first_word);
            sp_temp.show(); */


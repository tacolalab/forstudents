//added by anu on 27/12/02 while integrating
//SpellChecker without interface

package SpellChecker;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.text.*;
import java.util.*;
import analyser.*;

public class SpellChecker
{
	private JTextComponent textComponent;

	private Set set;

	private void spellCheck(String doc, Highlighter dh,
		DefaultHighlighter.DefaultHighlightPainter dhp)
	{
		int s=0,e=0,tempIndex = 0;
		String tamilWord="",type = "";
		boolean highlight=false;

		//get all word separators

		StringTokenizer strTok = new StringTokenizer(doc,"'\n' : ; ,  .'\t' ");
		dh.removeAllHighlights();

		while(strTok.hasMoreTokens())
		{
			tamilWord = strTok.nextToken();
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
			}

			s=doc.indexOf(tamilWord,tempIndex);
			e=doc.indexOf(tamilWord,tempIndex)+tamilWord.length();
			tempIndex += tamilWord.length();
			byte bstr[] = tabconvert2.convert(tamilWord);
			ByteMeth me=new ByteMeth();

			if(highlight)
			{
				try
				{
					if(tamilWord.endsWith("ð¢") || tamilWord.endsWith("è¢") || tamilWord.endsWith("ê¢"))
					{
						//System.out.println("Sandhi");
					}
					else
					{
						dh.addHighlight(s,e, dhp);
						highlight=false;
					}

				}
				catch (javax.swing.text.BadLocationException ble)
				{
					ble.printStackTrace();
				}
			}
		}
		set = findHighlights();
	}

	public SpellChecker(JTextComponent textComponent,
		DefaultHighlighter.DefaultHighlightPainter dhp)
	{
		this.textComponent = textComponent;

		String text ="";
		try
		{
			Document doc = textComponent.getDocument();
			text = doc.getText(0,doc.getLength());
		}catch(BadLocationException ble)
		{
			ble.printStackTrace();
		}

		spellCheck(text,textComponent.getHighlighter(),dhp);

		MyMouseListener mouseListener = new MyMouseListener();
		textComponent.addMouseListener(mouseListener);
	}

	private class MyMouseListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e)
		{
			int pos = textComponent.getCaretPosition();

			if(e.getClickCount() >=2 && isHighlightedPosition(pos))
			{
				checkPopup(e);
			}
		}
	}

	private void checkPopup(MouseEvent e)
	{
		int pos = textComponent.getCaretPosition();

		HSugg.model.removeAllElements();
		Document doc = textComponent.getDocument();
		String text = "";
		try
		{
			text = doc.getText(0,doc.getLength());
		}catch(BadLocationException ble)
		{
			ble.printStackTrace();
		}

		StringTokenizer strTok = new StringTokenizer(text,"'\n' : ; ,  .'\t' ");

		int start = pos;
		int end = pos;
		while(!Character.isWhitespace(text.charAt(start)))
			start--;
		while(!Character.isWhitespace(text.charAt(end)))
			end++;

		// Selecting the Possible combination words from Dictionary
		String correctedstr="";

		String missSpeltWord = text.substring(start+1, end);
		HSugg sugg = new HSugg(textComponent,missSpeltWord);

		try
		{
			correctedstr = SpchkNoun.checker(missSpeltWord);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}

		if(!correctedstr.equals(missSpeltWord))
		{
			//System.out.println("noun");
		}
		else
		{
			//System.out.println("verb1");
			correctedstr = Spchkverb.checker(missSpeltWord);
			if(!correctedstr.equals(missSpeltWord))
			{
				//System.out.println("verb");
			}
			else
			{
				//System.out.println("postpos");
				try
				{
					correctedstr = Hpostposition.checker(missSpeltWord);
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

	private boolean isHighlightedPosition(int position)
	{
		if(set.contains(new Integer(position)))
			return true;
		return false;
	}

	private Set findHighlights()
	{
		set = new HashSet();

		Highlighter hlr = textComponent.getHighlighter();
		Highlighter.Highlight[] hl = hlr.getHighlights();

		for(int i=0; i < hl.length; i++)
		{
			int start = hl[i].getStartOffset();
			int end = hl[i].getEndOffset();

			for(int j=start; j <= end; j++)
			{
				set.add(new Integer(start++));
			}
		}
		return set;
	}

}

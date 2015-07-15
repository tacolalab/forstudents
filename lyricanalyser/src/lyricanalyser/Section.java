package lyricanalyser;

/**
 * <p>Title: Tamil Lyric Section</p>
 *
 * <p>Description: Represents a Tamil Lyrical Section. pallavi or saranams</p>
 *
 * <p>Copyright: Copyright (c) 2009 Mellinam Education</p>
 *
 * <p>Company: Mellinam Education</p>
 *
 * @author Madhan Karky
 * @version 1.0
 */

import java.util.*;
import java.io.IOException;

public class Section {

    Vector lines;
    String tSection,eSection;
    Characters chars;
    String sectionType; //pallavi, saranam...

    public Section(String section, Characters chars)throws IOException {
        tSection = section;
        eSection = new String("");
        this.chars = chars;
        lines = new Vector();
        splitSection();
    }

    /**
     * This section splits a given section into corresponding lines
     *
     */
    public void splitSection() throws IOException{
       StringTokenizer st = new StringTokenizer(tSection,"\n");
       String tempLine;
       while(st.hasMoreTokens()){
           tempLine = st.nextToken();

           if(tempLine.charAt(0)=='<')
               continue;

           lines.addElement(new Line(tempLine.trim(),chars));
       }

   }

   /**
    * computes musical score for a  section based on score of lines
    * @return double musical score for a section
    */
   public double computeMScore()throws IOException{

       double mScore =0, edScore = 0.50, mnScore = 0.25, ibScore = 0.15, mtScore = 0.10;
        int edhugaiCount,moanaiCount,iyaibuCount,meterCount;
        edhugaiCount = 0; moanaiCount = 0; iyaibuCount = 0; meterCount = 0;

       Line line1,line2;
       Word wrd1, wrd2;
       int minWords,totalPairs=0;
       if(lines != null){
           for (int i = 0; i < lines.size(); i++) {
               line1 = (Line) lines.elementAt(i);

               for (int j = i + 1; j < lines.size(); j++) {
                   line2 = (Line) lines.elementAt(j);

                   if (line1 != null && line2 != null) {
                       if (line1.words.size() <= line2.words.size())
                           minWords = line1.words.size();
                       else
                           minWords = line2.words.size();

                       for (int k = 0; k < minWords; k++) {

                           wrd1 = (Word) line1.words.elementAt(k);
                           wrd2 = (Word) line2.words.elementAt(k);

                           if (wrd1 != null && wrd2 != null) {
                               if (wrd1.isEdhugai(wrd2))
                                   edhugaiCount++;
                               if (wrd1.isMoanai(wrd2))
                                   moanaiCount++;
                               if (wrd1.isIyaibu(wrd2))
                                   iyaibuCount++;
                               if (wrd1.isMeterMatch(wrd2))
                                   meterCount++;
                           }

                       }



                       totalPairs += minWords; //total combinations checked

                   }

                   if (j == i + 2) //to check for only two lines in a section
                       break;
               }
               mScore += line1.computeMScore();
           }
       }
       else
           return 0;

       mScore = mScore/(double)lines.size();

       edScore = edScore * edhugaiCount / totalPairs;
       mnScore = mnScore * moanaiCount / totalPairs;
       ibScore = ibScore * iyaibuCount / totalPairs;
       mtScore = mtScore * meterCount / totalPairs;

       double rhymeScore = edScore + mnScore + ibScore + mtScore;


       mScore = (mScore * 0.50) + (rhymeScore * 0.50);




       return mScore;
   }



   /**
    * Transliterates the section
    * @return String Transliteration of the section
    */
   public String toEnglish(){
       for(int i = 0; i<lines.size();i++)
           if(lines.elementAt(i)!= null)
               eSection += ((Line)lines.elementAt(i)).toEnglish()+"\n";

       if(eSection != null)
           return eSection;
       else
           return null;
   }


}

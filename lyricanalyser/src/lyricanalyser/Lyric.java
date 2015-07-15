package lyricanalyser;

/**
 * <p>Title: Tamil Lyric Analyser</p>
 *
 * <p>Description: Analyses Tamil Lyrics on Various Aspects</p>
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

public class Lyric {
    Vector sections;
    Characters chars;
    String tLyric, eLyric;
    /**
     * Constructs a Lyric
     * @param lyric String The complete Lyric in Tamil UTF-16
     * @param chars Characters the character set
     * @throws IOException for reading lyric & charset files
     */
    public Lyric(String lyric,Characters chars) throws IOException{
        tLyric = lyric;
        eLyric = new String("");
        this.chars = chars;
        sections = new Vector();
        splitLyric();
    }

    /**
     * Splits a lyric into its sections
     * @throws IOException
     */
    public void splitLyric() throws IOException{
      StringTokenizer st = new StringTokenizer(tLyric,"#");
      String tempSection;
      while(st.hasMoreTokens()){
          tempSection = st.nextToken();

          if(tempSection.charAt(0)=='<')
              continue;

          sections.addElement(new Section(tempSection.trim(),chars));
      }


   }

   /**
    * computes musical score for a lyric based on score of its sections
    * @return double musical score for a lyric
    */
   public double computeMScore()throws IOException{

      double mScore=0;
      Section ts;
      if(sections != null){
          for (int i = 0; i < sections.size(); i++) {
              ts = (Section) sections.elementAt(i);
              if (ts != null)
                  mScore += ts.computeMScore();
          }
      }
      else
          return 0;

      return (mScore/(double)sections.size());
  }


  /**
   * Transliterates a lyric
   * @return String transliteration of a lyric
   */
  public String toEnglish(){
       for(int i = 0; i<sections.size();i++)
           if(sections.elementAt(i)!=null)
               eLyric += ((Section)sections.elementAt(i)).toEnglish()+"\n\n";

       return eLyric;
   }
}
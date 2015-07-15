package lyricanalyser;

/**
 * <p>Title: Tamil Lyric Liner</p>
 *
 * <p>Description: Represents a Line of a Lyric</p>
 *
 * <p>Copyright: Copyright (c) 2009 Mellinam Education</p>
 *
 * <p>Company: Mellinam Education</p>
 *
 * @author Madhan Karky
 * @version 1.0
 *
 */

import java.util.*;
import java.io.IOException;

public class Line {

    Vector words;
    Characters chars;
    String tLine, eLine;

    public Line(String line,Characters chars)throws IOException {
        tLine = line;
        eLine = new String("");
        this.chars = chars;
        words = new Vector();
        splitLine();
    }

    /**
     * This method splits a line into an object of words
     * @throws IOException
     */
    public void splitLine() throws IOException{
        StringTokenizer st = new StringTokenizer(tLine.trim());
        String tempWord;
        while(st.hasMoreTokens()){
            tempWord = st.nextToken();

            if(tempWord.charAt(0)=='<')
                continue;

            words.addElement(new Word(tempWord.trim(),chars));
        }

    }

    /**
     * Compute musical score for a line based on score of words
     * @return double the musical score of the line
     */
    public double computeMScore()throws IOException{

        double mScore =0, edScore = 0.50, mnScore = 0.25, ibScore = 0.15, mtScore = 0.10;
        int edhugaiCount,moanaiCount,iyaibuCount,meterCount;
        edhugaiCount = 0; moanaiCount = 0; iyaibuCount = 0; meterCount = 0;
        int combinations;

        Word tw,pw=null;
        if(words != null){
            for (int i = 0; i < words.size(); i++) {
                tw = (Word) words.elementAt(i);
                pw=null;
                if (tw != null) {
                    for (int j = i + 1; j < words.size(); j++) {
                        if(j<words.size())
                            pw = (Word) words.elementAt(j);

                        if (pw != null && !pw.isEqual(tw)) {
                            if (pw.isEdhugai(tw))
                                edhugaiCount++;
                            if (pw.isMoanai(tw))
                                moanaiCount++;
                            if (pw.isMeterMatch(tw))
                                meterCount++;
                            if (pw.isIyaibu(tw))
                                iyaibuCount++;
                        }

                    }
                }

                mScore += tw.computeMScore();

            }

                mScore = mScore / (double) words.size();

                if (words.size() == 1)
                    combinations = 0;
                else
                    combinations = factorial(words.size()) /
                                   (2 * (factorial(words.size() - 2)));

                if (combinations > 0) {
                    edScore = edScore * edhugaiCount / combinations;
                    mnScore = mnScore * moanaiCount / combinations;
                    ibScore = ibScore * iyaibuCount / combinations;
                    mtScore = mtScore * meterCount / combinations;
                    double rhymeScore = edScore + mnScore + ibScore + mtScore;
                    mScore = (mScore * 0.75) + (rhymeScore * 0.25);
                }


        }
        else
            return 0;

        return mScore;
    }

    /**
     * Method to compute factorial of a given number
     * @param n int the input number
     * @return int factorial of the input number
     */
    private int factorial(int n){
        if( n <= 1 )
            return 1;
        else
            return n * factorial( n - 1 );
    }


    /**
     * converts the line to english transliteration
     * @return String transliteration of the line
     */
    public String toEnglish(){
       for(int i = 0; i<words.size();i++)
           if(words.elementAt(i)!= null)
           eLine += ((Word)words.elementAt(i)).toEnglish()+" ";

       return eLine;
   }
}

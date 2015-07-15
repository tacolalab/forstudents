package lyricanalyser;
import java.util.*;

/**
 * <p>Title: Letter</p>
 *
 * <p>Description: Represents a Tamil Letter</p>
 *
 * <p>Copyright: Madhan Karky Copyright (c) 2009</p>
 *
 * <p>Company: Mellinum Education</p>
 *
 * @author Madhan Karky
 * @version 1.0
 */
public class Letter {
    String tLetter; //tamil letter
    String eLetter; //english transliteration
    String type;   // vowel v consonant c cv combination sp special
    double tablet; //meter length of the letter
    double mScore; //musical score for the letter


    /**
     * Initialises Letter Object
     *
     * @param tL String tamil letter as Unicode UTF16
     * @param eL String english transliteration of tamil letter
     * @param lT String type of the letter consonant c, vowel v, combination cv
     * @param tab double tablet length of the letter
     */
    public Letter(String tL, String eL, String lT,double tab, double mS) {
        tLetter = new String(tL);
        eLetter = new String(eL);
        type = new String(lT);
        tablet = tab;
        mScore = mS;
    }

    /**
     * Initialises Letter Object
     *
     * @param compString String complete string of letter object seperated by commas
     */
    public Letter(String compString){
        StringTokenizer st = new StringTokenizer(compString,",");
        tLetter = st.nextToken();
        eLetter = st.nextToken();
        type = st.nextToken();
        if(st.hasMoreTokens())
            tablet = Double.parseDouble(st.nextToken());
        else
            System.out.println("Error Letter :"+compString);

        if(st.hasMoreTokens())
            mScore = Double.parseDouble(st.nextToken());
        else
            System.out.println("Error Letter :"+compString);
     }


     public Letter(Letter tempLetter){
         this.tLetter = tempLetter.tLetter;
         this.eLetter = tempLetter.eLetter;
         this.type = tempLetter.type;
         this.tablet = tempLetter.tablet;
         this.mScore = tempLetter.mScore;

     }


     /**
      * returns the english transliteration of the object
      * @return String English transliteration of the letter
      */
    @Override
     public String toString(){
        return eLetter;
    }

    public boolean isEquals(Letter l){
        return l.tLetter.equals(this.tLetter);
    }
}

package lyricanalyser;
/**
 * <p>Title: Word</p>
 *
 * <p>Description: Represents Tamil Words and operations on words</p>
 *
 * <p>Copyright: Copyright (c) 2009 Madhan Karky Vairamuthu</p>
 *
 * <p>Company: Mellinam Education</p>
 *
 * @author Madhan Karky
 * @version 1.0
 */

import java.util.*;
import java.io.IOException;

public class Word {

    //Vector letters;
    Characters chars;
    String tWord,eWord;


    public Word() {
       tWord = new String("");
       eWord = new String("");
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Word(String tWord, Characters chars) throws IOException {
        this.tWord = tWord;
        eWord = new String("");
        this.chars = chars;
        //letters = new Vector();
        //wordSplit();

    }
    /**
     * This method splits the string tamil word into individual tamil letters
     * @throws IOException file read exception
     */
    public Vector wordSplit() throws IOException {
        Vector letters = new Vector();
        Letter tempLetter;
        String tString,tStringNext;

        for (int i = 0; i < tWord.length(); i++) {
            tString = tWord.substring(i, i+1);
            if(i<tWord.length()-1)
                tStringNext = tWord.substring(i+1, i+2);
            else
                tStringNext="";

            if(tStringNext.equals("\u0BBE") ||  tStringNext.equals("\u0BCD") ||  tStringNext.equals("\u0BBF") ||  tStringNext.equals("\u0BC0") ||  tStringNext.equals("\u0BC1") ||  tStringNext.equals("\u0BC2") ||  tStringNext.equals("\u0BC6") ||  tStringNext.equals("\u0BC7") ||  tStringNext.equals("\u0BC8") ||  tStringNext.equals("\u0BCA") ||
tStringNext.equals("\u0BCB") ||  tStringNext.equals("\u0BCC") || tStringNext.equals("\u0BD7") || tStringNext.equals("\u0BD7") ){


                tString = tString + tStringNext;
                i++;
            }
            tempLetter = chars.findLetter(new Letter(tString, "-","-",0,0));
            letters.add(tempLetter);
        }
        return letters;
    }

    /**
     * This method is used to determine if two words match for edhugai
     * @param wrd Word The word to be compared
     * @return boolean true if second letter matches
     */
    public boolean isEdhugai(Word wrd) throws IOException{
        Vector letters = this.wordSplit();
        Vector wrdLetters = wrd.wordSplit();

        if(wrd==null || wrdLetters.size() < 2 || letters.size()<2)
            return false;
        else
            if(this.getLetterAt(2) != null && wrd.getLetterAt(2) != null)
                return (this.getLetterAt(2).isEquals(wrd.getLetterAt(2)) ||
                        this.getLetterAt(2).eLetter.equalsIgnoreCase(wrd.getLetterAt(2).eLetter));
            else
                return false;
    }

    /**
     * This method is used to determine if two words match for moanai
     * @param wrd Word The word to be compared
     * @return boolean true is first letter matches
     */
    public boolean isMoanai(Word wrd)throws IOException{
        Vector letters = this.wordSplit();
        Vector wrdLetters = wrd.wordSplit();

        if(wrd == null || wrdLetters.size() < 1 || letters.size()<1)
         return false;
     else
     if(this.getLetterAt(1) != null && wrd.getLetterAt(1) != null)
            return (this.getLetterAt(1).isEquals(wrd.getLetterAt(1)) ||
                    this.getLetterAt(1).eLetter.equalsIgnoreCase(wrd.getLetterAt(1).eLetter));
        else
            return false;

    }

    /**
     * This method is used to determine if two words match for Iyaibu
     * @param wrd Word The word to be compared
     * @return boolean true if last letters match
     */
    public boolean isIyaibu(Word wrd)throws IOException{
        Vector letters = this.wordSplit();
        Vector wrdLetters = wrd.wordSplit();
        if(wrd == null || wrdLetters.size() < 1 || letters.size()<1)
         return false;
     else
     if(this.getLetterAt(letters.size()) != null && wrd.getLetterAt(wrdLetters.size()) != null)
            return (this.getLetterAt(letters.size()).isEquals(wrd.getLetterAt(wrdLetters.size()))||
                    this.getLetterAt(letters.size()).eLetter.equalsIgnoreCase(wrd.getLetterAt(wrdLetters.size()).eLetter));
        else
            return false;


    }

    /**
     * This method is used to check if two words match by meter length
     * @param wrd Word the word to be checked
     * @return boolean true if they match by tablet
     */
    public boolean isMeterMatch(Word wrd)throws IOException{
        Vector letters = this.wordSplit();
        Vector wrdLetters = wrd.wordSplit();

        if(wrd == null || wrdLetters.size() != letters.size())
            return false;

        for(int i = 0; i<wrdLetters.size();i++){

            if(this.getLetterAt(i+1) != null && wrd.getLetterAt(i+1) != null){
                if(this.getLetterAt(i+1).tablet != wrd.getLetterAt(i+1).tablet)
                    return false;}
            else
                return false;
        }
        return true;
    }

    /**
     * This method returns the letter at a given position
     * @param i int the position of the letter
     * @return Letter the letter at given position
     */
    public Letter getLetterAt(int i)throws IOException{
        Vector letters = this.wordSplit();

        if(i>letters.size())
            return null;

        Letter let = (Letter)letters.elementAt(i-1);

        return let;
    }


    public boolean isEqual(Word wrd)throws IOException{
        Vector letters = this.wordSplit();
        Vector wrdLetters = wrd.wordSplit();

        if(wrd == null || wrdLetters.size() != letters.size())
            return false;

        for(int i = 0; i<wrdLetters.size();i++){
            if (!(this.getLetterAt(i + 1).isEquals(wrd.getLetterAt(i + 1))))
                return false;
        }

        return true;
    }

    /**
     * computes musical score for a word based on the score of its letters
     * @return double the musical score for the word
     */
    public double computeMScore() throws IOException{
        Vector letters = this.wordSplit();
        double mScore=0,negScore=0; //negScore stores the score to reduce due to doubling
        Letter tl,pl=null;
        if(letters != null)
            for(int i = 0; i<letters.size();i++){
                tl = (Letter)letters.elementAt(i);
                if(tl != null){
                    if(i!=0 && pl != null){
                        if(tl.eLetter.startsWith(pl.eLetter))//checks for doubling
                            negScore += tl.mScore/2.0;
                    }
                    pl = tl;
                    mScore += (tl.mScore-negScore);
                    negScore = 0;
                }
            }
        else
            return 0;

        return (mScore/(double)letters.size());
    }




    /**
     * This method returns the english transliteration of the word
     * @return String the transliterated word
     */
    public String toEnglish(){
        try{
            Vector letters = this.wordSplit();

            eWord = new String("");
            Letter tl;

            for (int i = 0; i < letters.size(); i++) {
                tl = (Letter) letters.elementAt(i);
                if(tl != null && tl.eLetter!= null)
                    eWord += tl.eLetter;
            }

        } catch (Exception e){//Catch exception if any
          System.err.println("Error Word: " + e.getMessage());}
      return eWord;
    }

    private void jbInit() throws Exception {
    }


}

package lyricanalyser;
import java.util.*;
import java.io.*;


/**
 * <p>Title: Dictionary</p>
 *
 * <p>Description: Defines operations over a set of Words</p>
 *
 * <p>Copyright: Madhan Karky Copyright (c) 2009</p>
 *
 * <p>Company: Mellinum Education</p>
 *
 * @author Madhan Karky
 * @version 1.0
 */
public class Dictionary {

    Vector allWords;
    Characters chars;

    /**
     * Initialises the Characters Object
     * @throws IOException Throws IOException if file not readable
     */
    public Dictionary() throws IOException{
       //load from dict.txt into allWords Vector
       //FileInputStream fis = new FileInputStream("test.txt");
       //InputStreamReader in = new InputStreamReader(fis, "UTF-8");
       allWords = new Vector();
       chars = new Characters();

       readFile("nounSet.txt","n");
       readFile("verbSet.txt","v");

    }


    /**
     *
     * finds a letter from the character set that matches with tLetter
     * @param tLetter Letter the letter to be found
     * @return Letter : The corresponding letter from Character set
     */
    public Word findWord(Word wrd) throws IOException
    {
        Word dictWord;
        for(int i=0;i<allWords.size(); i++){
            dictWord = (Word)allWords.elementAt(i);

            if(dictWord.isEqual(wrd))
                return dictWord;
        }
        return null;
    }

    /**
     * This method returns a set of words from dictionary that match tablet lengths
     * @param wrd Word the word to be matched
     * @return Vector the set of matched words
     */
    public Vector findTabletMatch(Word wrd, Vector list)throws IOException{
        Vector matchWords = new Vector();
        Word dictWord;
        for(int i=0;i<list.size(); i++){
            dictWord = (Word)list.elementAt(i);

            if(dictWord.isMeterMatch(wrd))
                matchWords.addElement(dictWord);
        }

        return matchWords;
    }

    /**
    * This method returns a set of words from dictionary that match edhugai
    * @param wrd Word the word to be matched
    * @return Vector the set of matched words
    */
   public Vector findEdhugaiMatch(Word wrd,Vector list)throws IOException{
       Vector matchWords = new Vector();
       Word dictWord;
       for(int i=0;i<list.size(); i++){
           dictWord = (Word)list.elementAt(i);

           if(dictWord.isEdhugai(wrd))
               matchWords.addElement(dictWord);
       }

       return matchWords;
   }

   /**
       * This method returns a set of words from dictionary that match moanai
       * @param wrd Word the word to be matched
       * @return Vector the set of matched words
       */
      public Vector findMoanaiMatch(Word wrd, Vector list)throws IOException{
          Vector matchWords = new Vector();
          Word dictWord;
          for(int i=0;i<list.size(); i++){
              dictWord = (Word)list.elementAt(i);

              if(dictWord.isMoanai(wrd))
                  matchWords.addElement(dictWord);
          }

          return matchWords;
    }


    /**
     * This method returns a set of words from dictionary that match iyaibu
     * @param wrd Word the word to be matched
     * @return Vector the set of matched words
     */
    public Vector findIyaibuMatch(Word wrd,Vector list)throws IOException{
        Vector matchWords = new Vector();
        Word dictWord;
        for(int i=0;i<list.size(); i++){
            dictWord = (Word)list.elementAt(i);

            if(dictWord.isIyaibu(wrd))
                matchWords.addElement(dictWord);
        }

        return matchWords;
    }

    /**
     * This method finds all words in dictionary that matches a given pattern
     * @param pattern String the pattern filter
     * @return Vector The list of words that match  the pattern
     * @throws IOException
     */
    /*public Vector patternMatch(String pattern) throws IOException{
        Vector matchWords = new Vector();
        Word dictWord;
        Letter tl;

        for(int i=0;i<allWords.size(); i++){
            dictWord = (Word)allWords.elementAt(i);
            Vector wrdLetters = dictWord.wordSplit();

        for(int j = 0; j<pattern.length();i++){
            tl = dictWord.getLetterAt(i+1);
            if( tl != null){
                if(pattern.charAt(i)='*')
                    continue;
                else
                    //to be continued

            }

        }


                matchWords.addElement(dictWord);
        }
        return matchWords;
    }
    */


    /**
     * Reads from file and creates a Letter object and adds to allLetters
     * @param fileName String file name containing letter statistics
     */
    private void readFile(String fileName,String type){
      Word wrd;
      try{
         // Open the file that is the first
         // command line parameter
         BufferedReader br = new BufferedReader(new InputStreamReader
                              (new FileInputStream(fileName),"UTF16"));
          String strLine;
          //Read File Line By Line
          while ((strLine = br.readLine()) != null)   {
              wrd = new Word(strLine,chars);
              allWords.addElement(wrd);
          }

      }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
      }
  }

}

package lyricanalyser;
import java.util.*;
import java.io.*;


/**
 * <p>Title: LetterSet</p>
 *
 * <p>Description: Defines operations over a set of letters</p>
 *
 * <p>Copyright: Madhan Karky Copyright (c) 2009</p>
 *
 * <p>Company: Mellinum Education</p>
 *
 * @author Madhan Karky
 * @version 1.0
 */
public class Characters {

    Vector allLetters;

    /**
     * Initialises the Characters Object
     * @throws IOException Throws IOException if file not readable
     */
    public Characters() throws IOException{
       allLetters = new Vector();
       readFile("charSet.txt");
    }


    /**
     *
     * finds a letter from the character set that matches with tLetter
     * @param tLetter Letter the letter to be found
     * @return Letter : The corresponding letter from Character set
     */
    public Letter findLetter(Letter tLetter)
    {
        for(int i=0;i<allLetters.size(); i++){
            Letter l = (Letter)allLetters.elementAt(i);
            if(l.isEquals(tLetter)){
                //System.out.println(l.tLetter + tLetter.tLetter);
                return l;
            }
        }
        return null;
    }


    /**
     * Reads from file and creates a Letter object and adds to allLetters
     * @param fileName String file name containing letter statistics
     */
    private void readFile(String fileName){
      Letter tL;
      try{
         // Open the file that is the first
         // command line parameter
         BufferedReader br = new BufferedReader(new InputStreamReader
                              (new FileInputStream(fileName)));
          String strLine = new String("");
          //Read File Line By Line
          while ((strLine = br.readLine()) != null)   {
              tL = new Letter(strLine);
              if(tL != null)
                  allLetters.addElement(tL);
              else
                  System.out.println(strLine);
          }
      }catch (Exception e){//Catch exception if any
          System.err.println("Error Characters: " + e.getMessage());
      }
  }


  /**
   * converts the list into a single string for display
   * @param v Vector List containing group of letters
   * @return String list of letters separated by a space
   */
  public String toString(Vector v){
        Enumeration enum1;
        Letter tempLetter;
        String listString = new String("");
     for (enum1=v.elements();enum1.hasMoreElements();){
        listString = listString + (Letter)enum1.nextElement() + " ";
     }
     return listString;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lyricanalyser;

import java.io.Serializable;

/**
 *
 * @author ubuntu02
 */
public class LIndex implements Serializable {
    String key,ltitle,lmusic,lauthor,lhead,lsentence;
    public void setlIndex(String word,String lTitle,String lMusic,String lAuthor,String lHead,String lSenetence){
        key=word;
        ltitle=lTitle;
        lmusic=lMusic;
        lauthor=lAuthor;
        lhead=lHead;
        lsentence=lSenetence;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lyricanalyser;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author karthikeyan.S
 * @version 1.0
 * @since AUCEG
 */
public class LTravereseIndex {

    public static Hashtable<String, ArrayList> lIndex;
    public static boolean isLoaded = true;

    public void loadIndex() {
        System.out.println("Welcome to Index Loading");
        ObjectInputStream objectInputStreamTamil = IOHelper.getObjectInputStream("./db/Index/Lyrics.ser");
        lIndex = (Hashtable<String, ArrayList>) IOHelper.readObjectFromInputStream(objectInputStreamTamil);
        IOHelper.closeObjectInputStream(objectInputStreamTamil);
        isLoaded = false;
    }

    public ArrayList readLyricsIndex(String input) {
        if (isLoaded) {
            loadIndex();
        }
        ArrayList getResults;
        input=removeSymbol(input);
        getResults=lIndex.get(input);
        if(getResults!=null){
            getResults=storeObjectToArray(getResults);
        }else{
            getResults=new ArrayList();
        }
        return getResults;
    }

    public void print(){
        Set key=lIndex.keySet();
        for(Object s:key){
            System.out.println(s.toString());
        }
    }

    public ArrayList storeObjectToArray(ArrayList input) {
        ArrayList<String[]> store = new ArrayList<String[]>();
        for (Object s : input) {
            String[] putString = new String[6];
            LIndex index = (LIndex) s;
            putString[0] = index.key.toString();
            putString[1] = index.ltitle.toString();
            putString[2] = index.lmusic.toString();
            putString[3] = index.lauthor.toString();
            putString[4] = index.lhead.toString();
            putString[5] = index.lsentence.toString();
            store.add(putString);
        }
        return store;
    }


    public String removeSymbol(String input) {
        String[] symbols = {"!", "@", "#", "$", "%", "^", "&", "_", "{", "}", "=", "[", "]", "|", "/", "\\", "\"", ">", "<", ";", ":", "'", ",", ".", "~", "`", "?", "+", "-", "*", "(", ")"};
        for (int i = 0; i < symbols.length; i++) {
            if (input.contains(symbols[i])) {
                input = input.replace(symbols[i], "");
            }
        }
        return input;
    }

    public void display(ArrayList input) {
        for (Object s : input) {
            String[] getOutput = (String[]) s;
            System.out.println("Tamilword   :" + getOutput[0]);
            System.out.println("\ttitle----->   :" + getOutput[1]);
            System.out.println("\tmusic------>   :" + getOutput[2]);
            System.out.println("\tauthor----->   :" + getOutput[3]);
            System.out.println("\tlyrics head--->  :" + getOutput[4]);
            System.out.println("\tlSentence------>  :" + getOutput[5]);
        }
    }
public static void main(String[] args){
    LTravereseIndex travereseIndex=new LTravereseIndex();
ArrayList results=travereseIndex.readLyricsIndex("காதல்");
    travereseIndex.display(results);
}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lyricanalyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author ubuntu02
 */
public class LBuildIndex{

  public Hashtable<String, ArrayList> buildIndex;

    public LBuildIndex() {
        ArrayList getFile=getFileFromDirectory("db/");
        for(Object s:getFile){
            fileRead(s.toString());
        }
        fileWrite("Lyrics.ser");
    }

    public ArrayList getFileFromDirectory(String dirName){
        buildIndex = new Hashtable<String, ArrayList>();
        ArrayList getFileList=new ArrayList();
        File file=new File(dirName);
        File[] fileName=file.listFiles();
        for(Object s:fileName){
            getFileList.add(s.toString());
        }
        return getFileList;
    }
    public void fileRead(String fileName){
        String title = "",author="",music="",lhead="",lsentence="";
        BufferedReader bufferedReader = IOHelper.getBufferedReader(fileName);
        String readLine = null;
        while ((readLine = IOHelper.readLineFromBufferedReader(bufferedReader)) != null) {
            if(readLine.contains("<தலைப்பு>")){
                lhead=tagRemove(readLine, "</தலைப்பு>");
            }if(readLine.contains("<படம்>")){
                title=tagRemove(readLine, "</படம்>");
            }if(readLine.contains("<இசை>")){
                music=tagRemove(readLine, "</இசை>");
            }if(readLine.contains("<வரிகள்>")){
                author=tagRemove(readLine, "</வரிகள்>");
            }if(!readLine.contains("<")){
                lsentence=lsentence+readLine+"\n";
            }
        }
        linesplit(lsentence, title, author, music, lhead);
    }

public String tagRemove(String input,String tag){
    int start=input.indexOf(">")+1;
    int end=input.indexOf(tag);
    input=input.substring(start,end);
    return input;
}

    public void linesplit(String readLine,String title,String author,String music,String lhead){
        String[] spt=readLine.split("\n");
        for(int i=0;i<spt.length;i++){
            String line="";
            if(i==0){
                line=spt[i]+"<br/>"+spt[i+1]+"<br/>"+spt[i+2]+"<br/>";
            }else if(i==spt.length-1){
                line=spt[i-2]+"<br/>"+spt[i-1]+"<br/>"+spt[i]+"<br/>";
            }else if(i!=0&&i!=spt.length-1){
                line=spt[i-1]+"<br/>"+spt[i]+"<br/>"+spt[i+1]+"<br/>";
            }
            wordsplit(spt[i], title, author, music, lhead, line);
        }
    }

    public void wordsplit(String line,String title,String author,String music,String lhead,String lline){
        String[] spt=line.split(" ");
        for(String s:spt){
            ArrayList<LIndex> value=new ArrayList<LIndex>();
            LIndex index=new LIndex();
            index.setlIndex(s, title,music, author, lhead, lline);
            value.add(index);
            indexPut(s, value);
        }
    }

    public void indexPut(String key,ArrayList<LIndex> value){
        if(buildIndex.containsKey(key)){
            ArrayList getOldValue=buildIndex.get(key);
            value.addAll(getOldValue);
        }
        buildIndex.put(key, value);
    }

    public void fileWrite(String fileName){
        File file = new File("./db/Index");
        file.mkdir();
        ObjectOutputStream objectOutputStream = IOHelper.getObjectOutputStream("./db/Index/" + fileName);
        IOHelper.writeObjectToOutputStream(objectOutputStream, buildIndex);
        IOHelper.closeObjectOutputStream(objectOutputStream);
        System.out.println("Lyrics Index Finished Successfully");
    }

    public static void main(String[] args){
        new LBuildIndex();
    }
}

package org.apache.nutch.analysis.unl.ta;

import java.io.*;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.*;
import org.apache.nutch.analysis.unl.ta.Tag.*;

public class Analyser {

    static boolean analysePart = true;
    static boolean print;
    //public static ResourceBundle Tags = ResourceBundle.getBundle(Tag.ENGLISH_EXPAND,new Locale("en","US"));
    public static ResourceBundle Tags = ResourceBundle.getBundle("org/apache/nutch/analysis/unl/ta/Tag/" + Tag.ENGLISH_EXPAND, new Locale("en", "US"));

    public static String analyseF(String input, boolean analysePart) {
        //System.out..println("The input is"+input);
        String output = "<" + input + ">" + ":\n";
        //System.out..println("The input is"+output);
        try {
            Stack allStk = new Stack();
            int count = 0;

            Method.analyse(input, allStk, analysePart);
            //	//////System.out..println("after method call");
            int size = allStk.size();
            //System.out..println("size of stack  "+ size);
            if (size == 0) {
                output += "<Error>\n";
                return output;
            }
            //	output +=" Size="+ size;
            for (int i = 0; i < size; i++) {
                Stack outputStack = (Stack) allStk.get(i);
                String part = "<";
                boolean flag = false;
                String stag = "~";
                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());
                    //System.out..println("The entry string is"+s);

                    int tag = entry.getTag();

                    //System.out..println("tag value ="+tag+"\t  i="+i);
                    if (tag != -1) {
                        flag = true;
                        if (part.length() == 1) {
                            part += s;
                        } else {
                            part += "+" + s;
                        }
                        s += " < ";
                        s += Tags.getString(String.valueOf(tag));
                        //s +=" "+"&"+" ";
                        stag = stag + "\t" + String.valueOf(tag);
                        //s += String.valueOf(tag);
                        //System.out..println(String.valueOf(tag));
                        //System.out..println("\t");
                        //System.out..println(s);
                        //System.out..println("\t");
                        s += " > ";
                    } else {
                        count++;
                        s += "<0>";
                        break;
                    }
                    output += s;
                }
                //out.write("\n");
                if (!stag.equals("~")) //out1.write("\n"+stag+"\n");
                {
                    if (allStk.size() != 1 && flag == true) {
                        output += Utils.newLineStr + part + '>' + Utils.newLineStr;
                    }
                }

            }
            //output +="\n";
            if (count == (size - 1) && size == 4) {
                if (output.startsWith("<#>:")) {
                    output = "<<s>\n";
                } else {
                    output += "<unknown>\n";

                }
            }
            //out.write("@\n");
            //out.clo		se();
            //out1.write("@\n");
            //out1.close();
        } catch (Exception e) {
            System.err.println("Analyser analyseF: " + e);
            e.printStackTrace();
            return input;
        }
        //////System.out..println( "output =" + output);
        //output+="\n";

        return output;
    }

    public static String analyseW(String input, boolean analysePart) {
        String output = input + ":";
        //System.out..println("analyseW");
        try {
            Stack allStk = new Stack();

            // for dic ref count
            // output += "- " + Method.analyse(input,allStk);

            Method.analyse(input, allStk, analysePart);
            int size = allStk.size();

            if (size == 0) {
                output += " Unknown";
                return output;
            }
            for (int i = 0; i < size; i++) {
                Stack outputStack = (Stack) allStk.get(i);

                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());

                    output += Utils.newLineStr_OS;
                    output += s;
                    int tag = entry.getTag();

                    if (tag != -1) {
                        s = " < ";
                        s += Tags.getString(String.valueOf(tag));
                        s += " > ";
                    } else {
                        s = "";
                    }
                    output += s;
                }
                if (allStk.size() != 1) {
                    output += Utils.newLineStr_OS + "***";
                }
            }
        } catch (Exception e) {
            System.err.println("Analyser analyseW: " + e);
            e.printStackTrace();
            return input;
        }
        return output;
    }

    public static Vector analyse(String input, boolean analysePart) {
        Vector output = new Vector();

        try {
            Stack allStk = new Stack();

            Method.analyse(input, allStk, analysePart);
            for (int i = 0; i < allStk.size(); i++) {
                Stack outputStack = (Stack) allStk.get(i);
                Vector part = new Vector();
                Vector tag = new Vector();

                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());

                    part.add(s);
                    int tagInt = entry.getTag();

                    tag.add(new Integer(tagInt));
                }
                output.add(new Element(part, tag));
            }
        } catch (Exception e) {
            System.err.println("Analyser analyse: " + e);
            e.printStackTrace();
            return null;
        }
        return output;
    }

    public static Vector getVector(Stack inputStk) {
        Vector output = new Vector();

        try {
            for (int i = 0; i < inputStk.size(); i++) {
                Stack outputStack = (Stack) inputStk.get(i);
                Vector part = new Vector();
                Vector tag = new Vector();

                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());

                    part.add(s);
                    int tagInt = entry.getTag();

                    if (tagInt != -1) {
                        s = String.valueOf(tagInt);
                    } else {
                        s = "";
                    }
                    tag.add(s);
                }
                output.add(new Element(part, tag));
            }
        } catch (Exception e) {
            System.err.println("Analyser analyse: " + e);
            e.printStackTrace();
            return null;
        }
        return output;
    }

    // call this before analysing a large document
    // load btree into memory
    public static void init() {
        analyse(UnicodeConverter.revert(Constant.vel), analysePart);
    }

    public static void main(String args[]) {
        try {
            Vector v = new Vector();
            String str = "";
            BufferedReader inbuff = new BufferedReader(new FileReader("in.txt"));
            BufferedWriter outbuff = new BufferedWriter(new FileWriter("out.txt"));

            while ((str = inbuff.readLine()) != null) {
                if (str.indexOf("#") == -1) {
                    String analysed = Analyser.analyseF(str, true);
                    outbuff.write(analysed);
                }
            }
            inbuff.close();
            outbuff.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // for procesing analyser output------------------
    static Set d = new HashSet();
    static String y = "";

    static {
        d.add(String.valueOf(Tag.Numbers));
        d.add(String.valueOf(Tag.Noun));
        d.add(String.valueOf(Tag.Pronoun));
        d.add(String.valueOf(Tag.InterrogativeNoun));
        d.add(String.valueOf(Tag.PronounOblique));
        d.add(String.valueOf(Tag.Verb));
        d.add(String.valueOf(Tag.FiniteVerb));
        d.add(String.valueOf(Tag.NegativeFiniteVerb));
        d.add(String.valueOf(Tag.Adjective));
        d.add(String.valueOf(Tag.DemonstrativeAdjective));
        d.add(String.valueOf(Tag.InterrogativeAdjective));
        d.add(String.valueOf(Tag.Adverb));
        d.add(String.valueOf(Tag.Interjection));
        d.add(String.valueOf(Tag.Conjunction));
        d.add(String.valueOf(Tag.Particle));
        d.add(String.valueOf(Tag.Intensifier));
        d.add(String.valueOf(Tag.Postposition));
    }

    public static boolean isNoun(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector tag = elmt.getTag();
            int index = elmt.containsTag(new Integer(Tag.Noun));

            if (index != -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean is2320(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector tag = elmt.getTag();
            int index = elmt.containsTag(new Integer(2320));

            if (index != -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoun(String word) {
        //System.out..println("Is noun is true");
        return isNoun(analyse(word, analysePart));
    }

    public static boolean isVerb(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector tag = elmt.getTag();
            int index = elmt.containsTag(new Integer(Tag.Verb));

            if (index != -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean isVerb(String word) {
        return isVerb(analyse(word, analysePart));
    }

    public static String getRoot(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector partV = elmt.getPart();

            // String tag = null;

            if (partV.size() > 0) {
                return (String) partV.get(0);
            }
        }
        return null;
    }

    public static String getRoot(String word) {
        return getRoot(analyse(word, analysePart));
    }

    public static boolean isAnalysed(Vector v) {
        if (v.size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isAnalysed(String input) {
        Vector v = analyse(input, analysePart);

        if (v.size() > 0) {
            return true;
        }
        return false;
    }

    public ArrayList onlineDictAnalyser(String input, String language) {
        input = removeSymbols(input.trim());
        ArrayList arr = new ArrayList();
        input = analyseF(input.trim(), true);
        System.out.println(input);
        if (!input.contains("<Error>")) {
            arr.add(getAanlyserFormat(input));
            input = getRootWord(input);
            if (input.length() > 1) {
                arr.add(input.trim());
                input = getTypes(arr.get(0).toString());
                arr.add(input.trim());
            } else {
                arr = new ArrayList();
                arr.add("Analyser Not Found");
                arr.add("Analyser Not Found");
                arr.add("none");
            }
        } else {
            arr.add("Analyser Not Found");
            arr.add("Analyser Not Found");
            arr.add("none");
        }
        return arr;
    }

    public String getAanlyserFormat(String input) {
        String alsFormat = "";
        String[] spt = input.split("\n");
        for (int i = 0; i < spt.length; i++) {
            if (spt[i].contains(":") && spt.length > 1) {
                i++;
                while (spt[i].contains("<")) {
                    int start = spt[i].indexOf("<");
                    int end = 0;
                    if (spt[i].contains(">")) {
                        end = spt[i].indexOf(">");
                    } else {
                        end = spt[i].length() - 2;
                    }
                    if (end != spt[i].length() - 2) {
                        alsFormat += spt[i].substring(0, start) + "+";
                    } else {
                        alsFormat += checkIkkal(spt[i].substring(0, start));
                    }
                    spt[i] = spt[i].substring(end + 1, spt[i].length());
                }
                alsFormat += "<br/>";
            }
        }
        return alsFormat;
    }

    public String checkIkkal(String input){
        if(input.contains("க்கள்")){
            input=input.replace("க்","க் + ");
        }
        return input;
    }

    public String getRootWord(String input) {
        try {
            if (input.contains("Demonstrative Adjective")) {
                String[] spt = input.split("\n");
                int string = spt.length - 2;
                int end = spt[string].indexOf("<");
                input = spt[string].substring(0, end);
            } else {
                int start = input.indexOf(":");
                input = input.substring(start + 1, input.length() - 1);
                int end = input.indexOf("<");
                input = input.substring(1, end - 1);
            }
            return input;
        } catch (Exception e) {
            return "Word Not Found";
        }
    }

    public String getTypes(String input) {
        try {
            if (input.contains("Demonstrative Adjective")) {
                String[] spt = input.split("\n");
                int string = spt.length - 2;
                int start = spt[string].indexOf("<");
                int end = spt[string].indexOf(">");
                input = spt[string].substring(start + 1, end - 1);
            } else {
                int start = input.indexOf(":");
                input = input.substring(start + 1, input.length() - 1);
                start = input.indexOf("<");
                int end = input.indexOf(">");
                input = input.substring(start + 1, end - 1);
            }
            return input;
        } catch (Exception e) {
            return "none";
        }
    }

    public String removeSymbols(String input) {
        String[] symbols = {"!", "@", "#", "$", "%", "^", "&", "_", "{", "}", "=", "[", "]", "|", "/", "\\", "\"", ">", "<", ";", ":", "'", ",", ".", "~", "`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "?", "+", "-", "*", "(", ")"};
        for (int i = 0; i < symbols.length; i++) {
            if (input.contains(symbols[i])) {
                input = input.replace(symbols[i], "");
            }
        }
        return input;
    }
}

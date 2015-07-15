package clia.unl.Source.Word_Gen.Generator;

import java.io.*;
import java.util.*;

/** This is the main class which is used for reading files
 * to get the auxillary verb,verb category & gender endings */
public class FileRead {

    File inputFile = null;

    /**Constructor*/
    public FileRead() {
    }

    /** main method in which file read object is created*/
    public static void main(String args[]) {
        FileRead fr = new FileRead();
    }

    /** This method gets the auxillary verb from the file
     * when the file name, the root auxillary,
     * and the record no is given*/
    public String getAuxString(String rootaux, String fileName, int no) {
        String auxinf = "";
        String s = "";
        int flg = 0;
        String tok = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader("./resource/unl/" + fileName));
            while ((s = in.readLine()) != null) {
                StringTokenizer strToken = new StringTokenizer(s);
                while (strToken.hasMoreTokens()) {
                    String token = strToken.nextToken().toString();
                    if (!token.equals(rootaux)) {
                        for (int i = 0; i < no; i++) {
                            tok = strToken.nextToken();
                            flg = 1;
                        }
                    }
                }
                if (flg == 1) {
                    break;
                }
                if (flg == 1) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tok;
    }

    /** This method gets the verb category for the given root auxillary*/
    public int getVerbCat(String rootaux, String fileName) throws IOException {
        String s = "";

        int vbcat = 0;
        int flg = 0;
        try {

            BufferedReader in = new BufferedReader(new FileReader("./resource/unl/" + fileName));
            while ((s = in.readLine()) != null) {
                StringTokenizer strToken = new StringTokenizer(s);
                while (strToken.hasMoreElements()) {
                    String token = strToken.nextToken().toString();
                    if (token.equals(rootaux)) {
                        flg = 1;
                        vbcat = Integer.parseInt(strToken.nextElement().toString());
                        break;
                    }
                }
                if (flg == 1) {
                    break;
                }
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vbcat;
    }

    /** This method gets the auxillary category when
     * the root auxillary is given*/
    public int getAuxCat(String rootaux, String fileName) throws IOException {
        int auxcat = 0;
        try {

            StreamTokenizer input = new StreamTokenizer(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("./resource/unl/" + fileName))));
            input.eolIsSignificant(true);

            int tokentype = 0;

            while ((tokentype = input.nextToken()) != StreamTokenizer.TT_EOF) {

                if (!input.sval.equals(rootaux)) {

                    do {
                        tokentype = input.nextToken();

                        if (tokentype == StreamTokenizer.TT_EOF) {
                            return 0;
                        }
                        if (tokentype == StreamTokenizer.TT_EOL) {
                            break;
                        } else;

                    } while (!(tokentype == StreamTokenizer.TT_EOL));
                } else {
                    input.nextToken();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return auxcat;
    }

    /** This method generates the gender endings when the subject
     * and the file is given*/
    public String getGenderEnding(String subject, String file) {
        return getAuxString(subject, file, 1);
    }
}




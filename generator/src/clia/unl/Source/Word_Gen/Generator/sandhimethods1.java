package clia.unl.Source.Word_Gen.Generator;

import java.io.*;

public class sandhimethods1 {

    TabMethods tm = new TabMethods();
    ByteMethods bm = new ByteMethods();

    public byte[] sandhimeth(byte nounbyte[], byte[] nountemp) throws IOException {
        byte[] aRRu = tm.convert("அற்று");
        byte[] vaRRu = tm.convert("வற்று");
        byte[] pala = tm.convert("பல");
        byte[] sila = tm.convert("சில");
        byte[] avai = tm.convert("அவை");
        byte[] ivai = tm.convert("இவை");
        byte[] evai = tm.convert("எவை");
        byte[] ellam = tm.convert("எல்லாம்");
        byte[] a = {1};
        byte[] y = {24};
        byte[] k = {14};
        byte[] in = {3, 31};
        byte[] kaN = {14, 1, 19};
        String tan = "தன் ";
        String en = "என்";
        String un = "உன்";
        String nam = "நம்";
        String em = "எம்";
        String um = "உம்";
        String nounstr = tm.revert(nounbyte);
        byte[] temp = null;
        byte[] output = null;
        int x = nounbyte.length - 1;
        if (nounbyte.length == 1 && nounbyte[0] == 0) {
            temp = nountemp;
        } else if (nountemp.length == 1 && nountemp[0] == 0) {
            temp = nounbyte;
        } else if (nounbyte[nounbyte.length - 1] == 23) {//start
            if ((nounstr.equals("தம்")) || (nounstr.equals("நம்")) || (nounstr.equals("நும்")) || (nounstr.equals("எம்")) || (nounstr.equals("உம்"))) {
                String nounstrdoub = nounstr + nounstr.charAt(nounstr.length() - 1);
                temp = tm.convert(nounstrdoub);
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);

            } else if (bm.isequal(nounbyte, ellam)) {
                temp = bm.addarray1(bm.subarray(nounbyte, 0, 4), vaRRu);
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);
            } else {
                byte[] thth = {20, 20};
                temp = bm.addarray(bm.subarray(nounbyte, 0, nounbyte.length - 1), thth);
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);
            }
        }//end
        /*if ends with u*/ else if ((nounbyte[nounbyte.length - 1]) == 5) {//start

            if ((((nounbyte[nounbyte.length - 3]) == 2) || ((nounbyte[nounbyte.length - 3]) == 4) || ((nounbyte[nounbyte.length - 3]) == 6) || ((nounbyte[nounbyte.length - 3]) == 8) || ((nounbyte[nounbyte.length - 3]) == 11)) && (((nounbyte[nounbyte.length - 2]) == 18) || ((nounbyte[nounbyte.length - 2]) == 30))) {
                nounstr = tm.revert(bm.subarray(nounbyte, 0, 3));
                String nounstrdoub = nounstr + nounstr.charAt(nounstr.length() - 1);
                temp = tm.convert(nounstrdoub);
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);
            } else if ((((nounbyte[nounbyte.length - 3]) == 1) || ((nounbyte[nounbyte.length - 3]) == 3) || ((nounbyte[nounbyte.length - 3]) == 5) || ((nounbyte[nounbyte.length - 3]) == 7) || ((nounbyte[nounbyte.length - 3]) == 9)) && (((nounbyte[nounbyte.length - 2]) == 18) || ((nounbyte[nounbyte.length - 2]) == 20))) {

                byte[] v = {27};
                temp = bm.addarray1(nounbyte, v);
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);
            } else if (((nounbyte[nounbyte.length - 2]) == 25) || ((nounbyte[nounbyte.length - 2]) == 28) || ((nounbyte[nounbyte.length - 2]) == 20) || ((nounbyte[nounbyte.length - 2]) == 29) || ((nounbyte[nounbyte.length - 2]) == 19) || ((nounbyte[nounbyte.length - 2]) == 31)) {

                byte[] v = {27};
                temp = bm.addarray1(nounbyte, v);
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);
            } else if (((nounbyte[nounbyte.length - 2]) == 14) || ((nounbyte[nounbyte.length - 2]) == 16) || ((nounbyte[nounbyte.length - 2]) == 18) || ((nounbyte[nounbyte.length - 2]) == 22) || ((nounbyte[nounbyte.length - 2]) == 27) || ((nounbyte[nounbyte.length - 2]) == 20) || ((nounbyte[nounbyte.length - 2]) == 30)) {

                temp = bm.subarray(nounbyte, 0, nounbyte.length - 1);
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);
            } else {
                temp = nounbyte;
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);
            }
        }//end
        else if ((bm.isequal(nounbyte, avai)) || (bm.isequal(nounbyte, evai)) || (bm.isequal(nounbyte, ivai))) {
            temp = bm.addarray(bm.subarray(nounbyte, 0, 2), aRRu);
            if (bm.isequal(nountemp, kaN)) {
                temp = bm.addarray(temp, in);
            }
            output = bm.addarray(temp, nountemp);

        } else if ((bm.isequal(nounbyte, sila)) || (bm.isequal(nounbyte, pala))) {
            temp = bm.addarray(nounbyte, aRRu);
            if (bm.isequal(nountemp, kaN)) {
                temp = bm.addarray(temp, in);
            }
            output = bm.addarray(temp, nountemp);

        } /*if ends with n,N,y,l.L and the nounbyte length<3*/ else if (((nounbyte[nounbyte.length - 1]) == 19) || ((nounbyte[nounbyte.length - 1]) == 31) || ((nounbyte[nounbyte.length - 1]) == 24) || ((nounbyte[nounbyte.length - 1]) == 26) || ((nounbyte[nounbyte.length - 1]) == 29)) {//start//99999
            int no = nounbyte.length;
            if (((nounstr.equals(en)) || (nounstr.equals(un)) || (nounstr.equals(nam)) || (nounstr.equals(em)) || (nounstr.equals(tan)) || (nounstr.equals(um))) && ((bm.startswith(nountemp, a)))) {//1
                temp = nounbyte;
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);
            }//1
            if (((nounbyte[nounbyte.length - 2]) == 1) || ((nounbyte[nounbyte.length - 2]) == 3) || ((nounbyte[nounbyte.length - 2]) == 5) || ((nounbyte[nounbyte.length - 2]) == 7) || ((nounbyte[nounbyte.length - 2]) == 9) || ((nounbyte[nounbyte.length - 2]) == 10) || ((nounbyte[nounbyte.length - 2]) == 12)) {//start//2
                String nounstrdoub = nounstr + nounstr.charAt(nounstr.length() - 1);
                temp = tm.convert(nounstrdoub);
                if ((((temp[temp.length - 1]) == 24)) && (((temp[temp.length - 2]) == 24)) && (bm.startswith(nountemp, k))) {//3
                    temp = bm.subarray(temp, 0, temp.length - 1);
                    output = bm.addarray(temp, nountemp);
                    if (bm.isequal(nountemp, kaN)) {//4
                        temp = bm.addarray(bm.addarray(temp, y), in);
                        output = bm.addarray(temp, nountemp);
                    }//4

                }//3
                else {//3-1
                    if (bm.isequal(nountemp, kaN)) {
                        temp = bm.addarray(temp, in);
                    }
                    output = bm.addarray(temp, nountemp);
                }//3-1
            }//end//2
            else {//start//2-1
                temp = nounbyte;
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);
            }//end//2-1
        }//end
        else {
            temp = nounbyte;
            if (bm.isequal(nountemp, kaN)) {
                temp = bm.addarray(temp, in);
            }
            output = bm.addarray(temp, nountemp);
        }
        if ((((nounbyte[nounbyte.length - 1]) == 24)) && (((nounbyte[nounbyte.length - 2]) == 24)) && (bm.startswith(nountemp, k))) {
            temp = bm.subarray(nounbyte, 0, nounbyte.length - 1);
            output = bm.addarray(temp, nountemp);
        }//9999999
        return output;
    }
}

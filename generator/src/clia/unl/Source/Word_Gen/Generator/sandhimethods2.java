package clia.unl.Source.Word_Gen.Generator;

import java.io.*;

public class sandhimethods2 {

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
        String tan = "தன்";
        String en = "என்";
        String un = "உன்";
        String nam = "நம்";
        String em = "எம்";
        String um = "உம்";
        String nounstr = tm.revert(nounbyte);
        byte[] temp = null;
        byte[] output = null;
        int x = nounbyte.length - 1;
        int len = nounbyte[nounbyte.length - 1];
        if (nounbyte.length == 1 && nounbyte[0] == 0) {
            temp = nountemp;
        } else if (nountemp.length == 1 && nountemp[0] == 0) {
            temp = nounbyte;
        } else if (nounbyte[nounbyte.length - 1] == 23) {//start
            if ((nounstr.equals("îñ¢")) || (nounstr.equals("ïñ¢")) || (nounstr.equals("¸ñ¢")) || (nounstr.equals("âñ¢")) || (nounstr.equals("àñ¢"))) {
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
            if (((((nounbyte[nounbyte.length - 3]) == 2) || (((nounbyte[nounbyte.length - 3]) == 4) || ((nounbyte[nounbyte.length - 3]) == 6) || ((nounbyte[nounbyte.length - 3]) == 8)) || ((nounbyte[nounbyte.length - 3]) == 11)) && (((nounbyte[nounbyte.length - 2]) == 18)))) {
                byte[] itt = {18};
                temp = bm.addarray(bm.subarray(nounbyte, 0, nounbyte.length - 1), itt);

                //	temp=bm.subarray(nounbyte,0,nounbyte.length-1);
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);
            } else if ((((nounbyte[nounbyte.length - 3]) == 2) || ((nounbyte[nounbyte.length - 3]) == 11)) && ((nounbyte[nounbyte.length - 2]) == 30)) {
                byte[] irr = {30};
                temp = bm.addarray(bm.subarray(nounbyte, 0, nounbyte.length - 1), irr);
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
            if (((nounbyte.length) < 3) || ((nounbyte.length) == 3) || (((nounbyte.length) > 3) && ((nounbyte[nounbyte.length - 4] == 14) || (nounbyte[nounbyte.length - 4] == 15) || (nounbyte[nounbyte.length - 4] == 16)
                    || (nounbyte[nounbyte.length - 4] == 17) || (nounbyte[nounbyte.length - 4] == 18) || (nounbyte[nounbyte.length - 4] == 19) || (nounbyte[nounbyte.length - 4] == 20) || (nounbyte[nounbyte.length - 4] == 21) || (nounbyte[nounbyte.length - 4] == 22) || (nounbyte[nounbyte.length - 4] == 23) || (nounbyte[nounbyte.length - 4] == 24)
                    || (nounbyte[nounbyte.length - 4] == 25) || (nounbyte[nounbyte.length - 4] == 26) || (nounbyte[nounbyte.length - 4] == 27) || (nounbyte[nounbyte.length - 4] == 28) || (nounbyte[nounbyte.length - 4] == 29) || (nounbyte[nounbyte.length - 4] == 31) || (nounbyte[nounbyte.length - 4] == 31))))
                /*||((nounbyte[nounbyte.length-5]==14)||(nounbyte[nounbyte.length-5]==15)||(nounbyte[nounbyte.length-5]==16)
            ||(nounbyte[nounbyte.length-5]==17)||(nounbyte[nounbyte.length-5]==18)||(nounbyte[nounbyte.length-5]==19)||(nounbyte[nounbyte.length-5]==20)||(nounbyte[nounbyte.length-5]==21)||(nounbyte[nounbyte.length-5]==22)||(nounbyte[nounbyte.length-5]==23)||(nounbyte[nounbyte.length-5]==24)
            ||(nounbyte[nounbyte.length-5]==25)||(nounbyte[nounbyte.length-5]==26)||(nounbyte[nounbyte.length-5]==27)||(nounbyte[nounbyte.length-5]==28)||(nounbyte[nounbyte.length-5]==29)||(nounbyte[nounbyte.length-5]==31)||(nounbyte[nounbyte.length-5]==31))))*/ {//start for doubling
                if (((nounstr.equals(en)) || (nounstr.equals(un)) || (nounstr.equals(nam)) || (nounstr.equals(em)) || (nounstr.equals(tan)) || (nounstr.equals(um))) && ((bm.startswith(nountemp, a)))) {//start1
                    temp = nounbyte;
                    if (bm.isequal(nountemp, kaN)) {
                        temp = bm.addarray(temp, in);
                    }
                    output = bm.addarray(temp, nountemp);
                }//end1
                else if (((nounbyte[nounbyte.length - 2]) == 1) || ((nounbyte[nounbyte.length - 2]) == 3) || ((nounbyte[nounbyte.length - 2]) == 5) || ((nounbyte[nounbyte.length - 2]) == 7) || ((nounbyte[nounbyte.length - 2]) == 9) || ((nounbyte[nounbyte.length - 2]) == 10) || ((nounbyte[nounbyte.length - 2]) == 12)) {//start2
                    String nounstrdoub = nounstr + nounstr.charAt(nounstr.length() - 1);
                    temp = tm.convert(nounstrdoub);

                    if (((temp[temp.length - 1] == 14) || (temp[temp.length - 1] == 15) || (temp[temp.length - 1] == 16)
                            || (temp[temp.length - 1] == 17) || (temp[temp.length - 1] == 18) || (temp[temp.length - 1] == 19) || (temp[temp.length - 1] == 20) || (temp[temp.length - 1] == 21) || (temp[temp.length - 1] == 22) || (temp[temp.length - 1] == 23) || (temp[temp.length - 1] == 24)
                            || (temp[temp.length - 1] == 25) || (temp[temp.length - 1] == 26) || (temp[temp.length - 1] == 27) || (temp[temp.length - 1] == 28) || (temp[temp.length - 1] == 29) || (temp[temp.length - 1] == 31) || (temp[temp.length - 1] == 31)) && (bm.startswith(nountemp, k))) {//2a
                        temp = bm.subarray(temp, 0, temp.length - 1);
                        if (bm.isequal(nountemp, kaN)) {//2a1
                            nounstrdoub = nounstr + nounstr.charAt(nounstr.length() - 1);
                            temp = tm.convert(nounstrdoub);
                            temp = bm.addarray(temp, in);
                            output = bm.addarray(temp, nountemp);
                        }//2a1
                    }//2a
                    else {//2b
                        if (bm.isequal(nountemp, kaN)) {
                            temp = bm.addarray(temp, in);
                        }
                        output = bm.addarray(temp, nountemp);
                    }//2b
                }//end2
                else {//start3
                    temp = nounbyte;
                    if (bm.isequal(nountemp, kaN)) {
                        temp = bm.addarray(temp, in);
                    }
                    output = bm.addarray(temp, nountemp);
                }//end3
            }//end for doubling
            else {// start else for doubling
                temp = nounbyte;
                if (bm.isequal(nountemp, kaN)) {
                    temp = bm.addarray(temp, in);
                }
                output = bm.addarray(temp, nountemp);
            }// end else for doubling
        }//end 99999
        else {// start final else
            String n = tm.revert(nounbyte);
            temp = nounbyte;
            if (bm.isequal(nountemp, kaN)) {
                temp = bm.addarray(temp, in);
            }
            output = bm.addarray(temp, nountemp);
        }// end final else
        return output;
    }
}

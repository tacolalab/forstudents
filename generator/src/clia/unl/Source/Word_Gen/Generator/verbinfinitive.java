package clia.unl.Source.Word_Gen.Generator;

public class verbinfinitive {//start pgm

    TabMethods tm = new TabMethods();
    ByteMethods bm = new ByteMethods();
    sandhimethods1 sm = new sandhimethods1();
    FileRead file = new FileRead();

    public String verbgetinfi(String verb) {
        int cat = 0;
        String verbinfinitive = "";
        byte[] a = {1};
        byte[] ka = {14, 1};
        byte[] kka = {14, 14, 1};
        byte[] tka = {18, 14, 1};
        byte[] Rka = {30, 14, 1};
        byte[] ara = {1, 25, 1};
        byte[] verbbyte = tm.convert(verb);
        try {
            cat = file.getVerbCat(verb, "VerbCategory.txt");
            if ((cat == 1) || (cat == 3) || (cat == 6) || (cat == 7) || (cat == 8) || (cat == 9)
                    || (cat == 10) || (cat == 12) || (cat == 14) || (cat == 15) || (cat == 16) || (cat == 17)) {
                if ((cat == 14) || (cat == 15) || (cat == 1)) {
                    String verbstrdoub = verb + verb.charAt(verb.length() - 1);
                    verbbyte = tm.convert(verbstrdoub);
                    verbinfinitive = tm.revert(bm.verbaddarray(verbbyte, a));
                } else if (cat == 17) {
                    verbbyte = bm.subarray(verbbyte, 0, verbbyte.length - 1);
                    verbinfinitive = tm.revert(bm.verbaddarray(verbbyte, ara));
                } else if ((cat == 3) || (cat == 6) || (cat == 7) || (cat == 8) || (cat == 9)
                        || (cat == 10) || (cat == 12) || (cat == 16)) {
                    verbinfinitive = tm.revert(bm.verbaddarray(verbbyte, a));
                } else {
                    verbinfinitive = tm.revert(verbbyte);
                }
            } else if ((cat == 11) || (cat == 13) || (cat == 4) || (cat == 18)) {
                if (cat == 11) {
                    verbbyte = bm.subarray(verbbyte, 0, verbbyte.length - 1);
                    verbinfinitive = tm.revert(bm.verbaddarray(verbbyte, tka));
                } else if (cat == 13) {
                    verbbyte = bm.subarray(verbbyte, 0, verbbyte.length - 1);
                    verbinfinitive = tm.revert(bm.verbaddarray(verbbyte, Rka));
                } else {
                    verbinfinitive = tm.revert(bm.verbaddarray(verbbyte, ka));
                }
            } else if ((cat == 2) || (cat == 5)) {
                verbinfinitive = tm.revert(bm.verbaddarray(verbbyte, kka));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return verbinfinitive;
    }
}

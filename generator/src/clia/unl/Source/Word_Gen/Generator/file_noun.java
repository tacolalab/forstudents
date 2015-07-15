package clia.unl.Source.Word_Gen.Generator;



import java.io.*;
import java.util.*;
import java.io.FileInputStream;

public class file_noun {//start pgm

    TabMethods tm = new TabMethods();
    ByteMethods bm = new ByteMethods();
    AdjectiveMethods1 am = new AdjectiveMethods1();
    clitics cl = new clitics();
    StringBuffer sb = new StringBuffer();
    Vector generatedvec = new Vector(10, 1);
    String[] gensentences1 = null;
    String[] gensentences = null;
    String ruleinput = "";
    String fileinput = "";
    String s = "";
    String[] rule = {"1", "2", "3", "4", "6", "7", "8"};
    String nouninput = "";
    String giv_sub = "எழுவாய்";
    String giv_adj1 = "பெயரடை";
    String giv_noun1 = "பெயர்ச்சொல்";
    String giv_case1 = "வேற்றுமைகள்";
    String giv_clitic1 = "மிதவை ஒட்டு";
    String giv_post1 = "பின்நிலை";
    String giv_adj2 = "பெயரடை";
    String giv_noun2 = "பெயர்ச்சொல்";
    String giv_case2 = "வேற்றுமைகள்";
    String giv_clitic2 = "மிதவை ஒட்டு";
    String giv_post2 = "பின்நிலை";
    String giv_adv = "வினையடை";
    String giv_verb = "வினைச்சொல்";
    String giv_aux1 = "துணை";
    String giv_aux2 = "துணை";
    String giv_aux3 = "துணை";
    String giv_aux4 = "துணை";
    String giv_tense = "காலங்கள்";
    boolean isSingular1 = true;
    boolean isSingular2 = true;
    BufferedReader f1;
    String temp = "";

    public StringBuffer NounCMGen(String filein, String ruleselected) {//start function
        String[] cases = {"இடமிருந்து", "இலிருந்து", "ஐ", "உக்காக", "அக்காக", "க்காக", "கு", "க்கு", "உக்கு", "அக்கு", "அது", "உடைய", "ஆல்", "இடம்", "இல்", "கண்", "ஓடு", "உடன்"};

        String[] posts = {"பின்நிலை", "விட", "விடவும்", "போல", "போல்", "போன்று", "கொண்டு", "நோக்கி", "பற்றி", "குறித்து", "சுற்றி", "சுற்றிலும்", "விட்டு", "தவிர", "முன்னிட்டு", "வேண்டி", "ஒட்டி", "பொறுத்து", "பொறுத்தவரை", "ஆக", "என்று", "முன்", "பின்", "உள்", "இடையே", "நடுவே", "மத்தியில்", "வௌதயே", "மேல்", "கீழ்", "எதிரில்", "பக்கத்தில்", "அருகில்", "பதில்", "மாறாக", "நேராக", "உரிய", "உள்ள", "தகுந்த", "வாயிலாக", "மூலமாக", "வழியாக", "பேரில்", "பொருட்டு", "உடன்", "கூட", "உடைய", "வசம்", "இடம்", "வரை", "தோறும்", "ஆர"};
        String[] str = {"பெயர்ச்சொல் விதிகள்", "பெயர்ச்சொல்+வேற்றுமை உருபு",
            "பெயர்ச்சொல்+பன்மை",
            "பெயர்ச்சொல்+ஒட்டு",
            "பெயர்ச்சொல்+பன்மை+ஒட்டு",
            "பெயர்ச்சொல்+பன்மை+வேற்றுமை உருபு",
            "பெயர்ச்சொல்+வேற்றுமை உருபு+ஒட்டு",
            "பெயர்ச்சொல்+வேற்றுமை உருபு+சொல்லுருபு",
            "பெயர்ச்சொல்+வேற்றுமை உருபு+சொல்லுருபு+ஒட்டு", "பெயர்ச்சொல்+உரிச்சொல் ஈற்றசை"};

        try {//start file try
            f1 = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(filein))));

            while ((temp = f1.readLine().trim()) != null) {//start file while
                nouninput = temp;
                giv_sub = "நான்";
                giv_verb = "நட";
                giv_tense = "நிகழ்காலம்";
                giv_noun1 = nouninput;
                isSingular1 = true;
                for (int c1 = 0; c1 < str.length; c1++) {
                    switch (c1) {
                        case 1://N1+CM
                        {
                            if (ruleselected.equals("பெயர்ச்சொல்+வேற்றுமை உருபு")) {
                                giv_post1 = "பின்நிலை";
                                isSingular1 = true;
                                giv_case1 = "வேற்றுமைகள்";
                                giv_clitic1 = "மிதவை ஒட்டு";
                                for (int l = 0; l < cases.length; l++) {
                                    giv_case1 = cases[l];
                                    gensentences = CorporateDemo1.generateSentences1(giv_sub, giv_adj1, giv_noun1, giv_case1, giv_clitic1, isSingular1, giv_post1, giv_adj2, giv_noun2, giv_case2, giv_clitic2, isSingular2, giv_post2, giv_adv, giv_verb, giv_aux1, giv_aux2, giv_aux3, giv_aux4, giv_tense);
                                    for (int j = 0; j < 1 && gensentences[j] != null; j++) {
                                        try {
                                            sb.append(gensentences[j].trim() + "\n");
                                        } catch (Exception ex) {
                                            System.out.println("N_CM2" + ex);
                                        }
                                    }

                                }
                            }
                            break;
                        }
                        case 2://N1+PL
                        {
                            if (ruleselected.equals("பெயர்ச்சொல்+பன்மை")) {//start if
                                s = "";
                                giv_post1 = "பின்நிலை";
                                isSingular1 = true;
                                giv_case1 = "வேற்றுமைகள்";
                                giv_clitic1 = "மிதவை ஒட்டு";
                                isSingular1 = false;
                                giv_case1 = "எழுவாய் வேற்றுமை";
                                gensentences = CorporateDemo1.generateSentences1(giv_sub, giv_adj1, giv_noun1, giv_case1, giv_clitic1, isSingular1, giv_post1, giv_adj2, giv_noun2, giv_case2, giv_clitic2, isSingular2, giv_post2, giv_adv, giv_verb, giv_aux1, giv_aux2, giv_aux3, giv_aux4, giv_tense);
                                for (int j = 0; j < 1 && gensentences[j] != null; j++) {
                                    try {
                                        sb.append(gensentences[j].trim() + "\n");
                                        //s=s+gensentences[j];
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                }
                                //generatedvec.add(s);
                            }//start close
                            break;
                        }
                        case 3: {
                            if (ruleselected.equals("பெயர்ச்சொல்+ஒட்டு"))//N1+clitic
                            {//start if
                                s = "";
                                giv_post1 = "பின்நிலை";
                                isSingular1 = true;
                                giv_case1 = "எழுவாய் வேற்றுமை";
                                giv_clitic1 = "மிதவை ஒட்டு";
                                for (int i = 0; i < rule.length; i++) {//open for 1
                                    ruleinput = rule[i];
                                    String[] rules = cl.tokenize(ruleinput);
                                    Vector cli = cl.ruleadd(rules);

                                    for (int m = 0; m < cli.size(); m++) {//open for 2
                                        isSingular1 = true;
                                        //giv_case1="ஐ";
                                        giv_clitic1 = String.valueOf(cli.get(m));
                                        gensentences = CorporateDemo1.generateSentences1(giv_sub, giv_adj1, giv_noun1, giv_case1, giv_clitic1, isSingular1, giv_post1, giv_adj2, giv_noun2, giv_case2, giv_clitic2, isSingular2, giv_post2, giv_adv, giv_verb, giv_aux1, giv_aux2, giv_aux3, giv_aux4, giv_tense);

                                        for (int j = 0; j < 1 && gensentences[j] != null; j++) {
                                            try {

                                                sb.append(gensentences[j].trim() + "\n");
                                                //s=s+gensentences[j];
                                            } catch (Exception ex) {
                                                System.out.println(ex);
                                            }
                                        }
                                    }//close for 2
                                    //generatedvec.add(s);
                                }//close for 1
                            }//close if
                            break;
                        }
                        case 4://N1+PL+clitic
                        {
                            if (ruleselected.equals("பெயர்ச்சொல்+பன்மை+ஒட்டு")) {//start if
                                s = "";
                                giv_post1 = "பின்நிலை";
                                isSingular1 = true;
                                giv_case1 = "எழுவாய் வேற்றுமை";
                                giv_clitic1 = "மிதவை ஒட்டு";
                                for (int i = 0; i < rule.length; i++) {//open for 1
                                    ruleinput = rule[i];
                                    String[] rules = cl.tokenize(ruleinput);
                                    Vector cli = cl.ruleadd(rules);

                                    for (int m = 0; m < cli.size(); m++) {//open for 2
                                        isSingular1 = false;
                                        giv_clitic1 = String.valueOf(cli.get(m));
                                        gensentences = CorporateDemo1.generateSentences1(giv_sub, giv_adj1, giv_noun1, giv_case1, giv_clitic1, isSingular1, giv_post1, giv_adj2, giv_noun2, giv_case2, giv_clitic2, isSingular2, giv_post2, giv_adv, giv_verb, giv_aux1, giv_aux2, giv_aux3, giv_aux4, giv_tense);

                                        for (int j = 0; j < 1 && gensentences[j] != null; j++) {
                                            try {

                                                sb.append(gensentences[j].trim() + "\n");
                                            } catch (Exception ex) {
                                                System.out.println(ex);
                                            }
                                        }
                                    }//close for 2
                                }//close for 1
                            }//close if
                            break;
                        }
                        case 5://N1+Pl+CM
                        {
                            s = "";
                            giv_post1 = "பின்நிலை";
                            isSingular1 = true;
                            giv_case1 = "வேற்றுமைகள்";
                            giv_clitic1 = "மிதவை ஒட்டு";
                            if (ruleselected.equals("பெயர்ச்சொல்+பன்மை+வேற்றுமை உருபு")) {//start if
                                isSingular1 = false;
                                for (int l = 0; l < cases.length; l++) {
                                    giv_case1 = cases[l];
                                    gensentences = CorporateDemo1.generateSentences1(giv_sub, giv_adj1, giv_noun1, giv_case1, giv_clitic1, isSingular1, giv_post1, giv_adj2, giv_noun2, giv_case2, giv_clitic2, isSingular2, giv_post2, giv_adv, giv_verb, giv_aux1, giv_aux2, giv_aux3, giv_aux4, giv_tense);
                                    for (int j = 0; j < 1 && gensentences[j] != null; j++) {
                                        try {
                                            sb.append(gensentences[j].trim() + "\n");
                                        } catch (Exception ex) {
                                            System.out.println(ex);
                                        }
                                    }
                                }
                            }//close if
                            break;
                        }
                        case 6://N1+CM+Clitic
                        {
                            if (ruleselected.equals("பெயர்ச்சொல்+வேற்றுமை உருபு+ஒட்டு")) {//start if
                                s = "";
                                giv_post1 = "பின்நிலை";
                                isSingular1 = true;
                                giv_case1 = "வேற்றுமைகள்";
                                giv_clitic1 = "மிதவை ஒட்டு";
                                isSingular1 = true;
                                for (int i = 0; i < rule.length; i++) {//open for 1
                                    ruleinput = rule[i];
                                    String[] rules = cl.tokenize(ruleinput);
                                    Vector cli = cl.ruleadd(rules);
                                    for (int m = 0; m < cli.size(); m++) {//open for 2
                                        giv_clitic1 = String.valueOf(cli.get(m));
                                        for (int l = 0; l < cases.length; l++) {//open cases
                                            giv_case1 = cases[l];
                                            gensentences = CorporateDemo1.generateSentences1(giv_sub, giv_adj1, giv_noun1, giv_case1, giv_clitic1, isSingular1, giv_post1, giv_adj2, giv_noun2, giv_case2, giv_clitic2, isSingular2, giv_post2, giv_adv, giv_verb, giv_aux1, giv_aux2, giv_aux3, giv_aux4, giv_tense);

                                            for (int j = 0; j < 1 && gensentences[j] != null; j++) {
                                                try {
                                                    sb.append(gensentences[j].trim() + "\n");
                                                    //s=s+gensentences[j];
                                                    //ctr=ctr+1;
                                                } catch (Exception ex) {
                                                    System.out.println(ex);
                                                }
                                            }
                                            //generatedvec.add(s);
                                        }//close cases
                                    }//close for 2

                                }//close for 1
                            }//start close
                            break;
                        }
                        case 7://N1+CM+PP
                        {
                            if ((ruleselected.equals("பெயர்ச்சொல்+வேற்றுமை உருபு+சொல்லுருபு"))) {//start if
                                s = "";
                                giv_post1 = "பின்நிலை";
                                isSingular1 = true;
                                giv_case1 = "வேற்றுமைகள்";
                                giv_clitic1 = "மிதவை ஒட்டு";
                                for (int l = 0; l < posts.length; l++) {
                                    giv_post1 = posts[l];
                                    gensentences = CorporateDemo1.generateSentences1(giv_sub, giv_adj1, giv_noun1, giv_case1, giv_clitic1, isSingular1, giv_post1, giv_adj2, giv_noun2, giv_case2, giv_clitic2, isSingular2, giv_post2, giv_adv, giv_verb, giv_aux1, giv_aux2, giv_aux3, giv_aux4, giv_tense);
                                    for (int j = 0; j < 1 && gensentences[j] != null; j++) {
                                        try {
                                            sb.append(gensentences[j].trim() + "\n");
                                            //s=s+gensentences[j];
                                        } catch (Exception ex) {
                                            System.out.println(ex);
                                        }
                                    }
                                    //generatedvec.add(s);
                                }
                            }//start close
                            break;
                        }

                        case 8://N1+CM+PP+Clitic
                        {

                            if (ruleselected.equals("பெயர்ச்சொல்+வேற்றுமை உருபு+சொல்லுருபு+ஒட்டு")) {//start if
                                s = "";
                                giv_post1 = "பின்நிலை";
                                isSingular1 = true;
                                giv_case1 = "வேற்றுமைகள்";
                                giv_clitic1 = "மிதவை ஒட்டு";
                                for (int i = 0; i < rule.length; i++) {//open for 1
                                    ruleinput = rule[i];
                                    String[] rules = cl.tokenize(ruleinput);
                                    Vector cli = cl.ruleadd(rules);

                                    for (int m = 0; m < cli.size(); m++) {//open for 2
                                        giv_clitic1 = String.valueOf(cli.get(m));
                                        for (int l = 0; l < posts.length; l++) {//open cases
                                            giv_post1 = posts[l];
                                            gensentences = CorporateDemo1.generateSentences1(giv_sub, giv_adj1, giv_noun1, giv_case1, giv_clitic1, isSingular1, giv_post1, giv_adj2, giv_noun2, giv_case2, giv_clitic2, isSingular2, giv_post2, giv_adv, giv_verb, giv_aux1, giv_aux2, giv_aux3, giv_aux4, giv_tense);

                                            for (int j = 0; j < 1 && gensentences[j] != null; j++) {
                                                try {
                                                    String gen = gensentences[j].trim();
                                                    String out = tm.revert(bm.addarray(tm.convert(gen), tm.convert(giv_clitic1)));
                                                    sb.append(out.trim() + "\n");
                                                } catch (Exception ex) {
                                                    System.out.println("N_CM2" + ex);
                                                }
                                            }
                                        }//close cases
                                    }//close for 2

                                }//close for 1
                            }//start close
                            break;
                        }



                        case 9://N1+adjectival suffix.
                        {
                            s = "";
                            giv_post1 = "பின்நிலை";
                            isSingular1 = true;
                            giv_case1 = "வேற்றுமைகள்";
                            giv_clitic1 = "மிதவை ஒட்டு";
                            String[] adject1 = {"ஆன", "இய", "உள்ள", "அற்ற"};
                            if (ruleselected.equals("பெயர்ச்சொல்+உரிச்சொல் ஈற்றசை")) {
                                for (int j = 0; j < adject1.length; j++) {
                                    sb.append(am.addAdjective(giv_noun1, adject1[j]).trim() + "\n");
                                }
                            }
                            break;
                        }


                    }
                }
            }//close file while
        }//close file try
        catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }//close function
}//close pgm


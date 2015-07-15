package clia.unl.Source.Word_Gen.Generator;

import java.io.*;

public class CorporateDemo1 {

    BufferedReader f1;
    String temp1;
    String token1[] = new String[20];
    String filename1 = "";
    String filename2 = "";
    static String[] sub_str = {"எழுவாய்", "நான்", "நாம்", "நாங்கள் ", "நீ", "நீங்கள்", "நீவீர்", "அவன்", "அவள்", "அவர்", "அவர்கள்", "அது", "அவை", "அவைகள்"};
    static String[] sub1_str = {"பால்", "ஆண்பால்", "பெண்பால்", "பலர்பால்", "ஒன்றன்பால்", "பலவின்பால¢"};
    static String[] adj_str = {"பெயரடை", "உறுதி", "அழகு", "கருப்பு", "உயரம்", "பயங்கரம்", "கோரம்", "தூய்மை", "ஒரு", "இரு", "மூன்று", "நான்கு", "ஐந்து", "ஆறு", "ஏழு", "எட்டு", "ஒன்பது", "பத்து"};
    static String[] case_str = {"வேற்றுமைகள்", "எழுவாய்", "வேற்றுமை", "ஐ", "ஆல்", "கண்", "உக்கு", "இல்", "இடம்", "ஓடு", "உடைய", "உடன்", "அது", "இருந்து", "இன்", "உக்காக", "ஆக", "இலிருந்து", "இடமிருந்து", "இனது"};
    static String[] pps_str = {"பின்நிலை", "விட", "விடவும்", "போல", "போல்", "போன்று", "கொண்டு", "நோக்கி", "பற்றி", "குறித்து", "சுற்றி", "சுற்றிலும்", "விட்டு", "தவிர", "முன்னிட்டு", "வேண்டி", "ஒட்டி", "பொறுத்து", "பொறுத்தவரை", "ஆக", "என்று", "முன்", "பின்", "உள்", "இடையே", "நடுவே", "மத்தியில்", "வௌதயே", "மேல்", "கீழ்", "எதிரில்", "பக்கத்தில்", "அருகில்", "பதில்", "மாறாக", "நேராக", "உரிய", "உள்ள", "தகுந்த", "வாயிலாக", "மூலமாக", "வழியாக",
        "பேரில்", "பொருட்டு", "உடன்", "கூட", "உடைய", "வசம்", "இடம்", "வரை", "தோறும்", "ஆர"};
    static String[] adv_str = {"வினையடை", "வேகம்", "விரைவு", "அழகு", "அடிக்கடி", "இனிமேல்", "இனியும்", "மறுபடியும்", "மீண்டும்", "மெல்ல", "உள்", "எதிர்", "பின்", "வௌத", "கூச்சல்", "பச்சை", "மஞ்சள்", "போல", "போல்", "போன்று", "கொண்டு", "நோக்கி", "பற்றி", "குறித்து", "சுற்றி", "சுற்றிலும்", "தவிர", "முன்னிட்டு", "வேண்டி", "ஒட்டி", "பொறுத்து", "ஆக", "முன்", "இடையே", "நடுவே", "மத்தியில்", "மேல்", "கீழ்", "எதிரில்", "பக்கத்தில்", "அருகில்", "பதில்", "மாறாக", "நேராக", "வாயிலாக", "மூலமாக", "வழியாக", "பேரில்", "பொருட்டு", "கூட", "உடைய", "வசம்", "இடம்", "வரை"};
    static String[] ver_str = {"வினைச்சொல்", "சாப்பிடு", "செய்", "வெட்டு", "விழு", "போ", "படி", "நட", "உண்", "தூங்கு", "போடு", "பெறு", "செல்", "சொல்", "கல்", "காண்", "வா"};
    static String[] aux_str = {"துணை", "கொள்", "இரு", "விடு", "பார்", "தொலை", "அழு", "கொடு", "கிட", "கிழி", "தள்ளு", "தீர்", "போடு", "காட்டு", "மாட்டு", "படு", "போ", "வா", "செய்", "வை", "முடி", "ஆயிற்று", "வேண்டும்", "வேண்டாம்", "கூடும்", "கூடாது", "இல்லை"};
    static String[] noun_str = {"பெயர்ச்சொல்", "மாம்பழம்", "கிண்ணம்", "கட்டில்", "கட்டை", "பொம்மை", "மரம்", "தாள்", "கத்தி", "பூ", "புத்தகம்", "ஆயுதம்", "ஈ"};
    static String[] tense_str = {"காலங்கள்", "நிகழ்காலம்", "இறந்தகாலம்", "எதிர்காலம்"};
    static String[] clitic_str = {"மிதவை ஒட்டு", "ஆ", "ஏ", "ஓ", "தான்", "மட்டும்", "மாத்திரம்", "என்னும்", "ஆகிலும்", "ஆயினும்", "கூட", "உம்", "ஆவது", "அடா", "அடி", "அம்மா", "அப்பா", "அய்யா"};

    public static String[] generateSentences(String subject, String adjective1, String noun1, String case1, String clitic1, boolean issingular1, String post1, String adjective2, String noun2, String case2, String clitic2, boolean issingular2, String post2, String adverb, String verb, String auxilary1, String auxilary2, String auxilary3, String auxilary4, String tense) throws Exception {
        VerbMethods verbobj = new VerbMethods();
        CaseMethods caseobj = new CaseMethods();
        PostPositionMethods postobj = new PostPositionMethods();
        AdjectiveMethods adjobj = new AdjectiveMethods();
        CliticMethods cliticobj = new CliticMethods();


        String post1result = "", post2result = "", case1result = "", clitic1result = "", case2result = "", clitic2result = "", adj1result = "", adj2result = "";

        String aux[] = {auxilary1, auxilary2, auxilary3, auxilary4};
        String[] adv_verb_aux_tense = null;
        adv_verb_aux_tense = verbobj.callAll(subject, verb, aux, tense, "", adverb);

        if (!(post1.equals(pps_str[0]))) {
            post1result = " " + postobj.addPostPositions(noun1, post1, 1, issingular1);
        } else if (!(clitic1.equals(clitic_str[0]))) {
            clitic1result = "" + cliticobj.addclitic(noun1, case1, "", clitic1, issingular1);
        } else if (!(case1.equals(case_str[0]))) {
            case1result = " " + caseobj.addCase(noun1, case1, "", issingular1);
        }

        if (!(post2.equals(pps_str[0]))) {
            post2result = " " + postobj.addPostPositions(noun2, post2, 1, issingular2);
        } else if (!(clitic2.equals(clitic_str[0]))) {
            clitic2result = "" + cliticobj.addclitic(noun2, case2, "", clitic2, issingular1);
        } else if (!(case2.equals(case_str[0]))) {
            case2result = " " + caseobj.addCase(noun2, case2, "", issingular2);
        }

        if (!(adjective1.equals(adj_str[0]))) {
            adj1result = " " + adjobj.addAdjective(adjective1);
        }
        if (!(adjective2.equals(adj_str[0]))) {
            adj2result = " " + adjobj.addAdjective(adjective2);
        }



        for (int count = 0; count < adv_verb_aux_tense.length && adv_verb_aux_tense[count] != null; count++) {
            adv_verb_aux_tense[count] = adj1result + " " + clitic1result + " " + case1result + post1result + adj2result + case2result + clitic2result + post2result + " " + adv_verb_aux_tense[count];
        }

        return adv_verb_aux_tense;
    }

    public static String[] generateSentences1(String subject, String adjective1, String noun1, String case1, String clitic1, boolean issingular1, String post1, String adjective2, String noun2, String case2, String clitic2, boolean issingular2, String post2, String adverb, String verb, String auxilary1, String auxilary2, String auxilary3, String auxilary4, String tense) throws IOException {
        VerbMethods verbobj = new VerbMethods();
        CaseMethods caseobj = new CaseMethods();
        PostPositionMethods postobj = new PostPositionMethods();
        AdjectiveMethods adjobj = new AdjectiveMethods();
        CliticMethods cliticobj = new CliticMethods();
        String clitic1result = "", clitic2result = "", post1result = "", post2result = "", case1result = "", case2result = "", adj1result = "", adj2result = "";
        String aux[] = {auxilary1, auxilary2, auxilary3, auxilary4};
        String[] adv_verb_aux_tense = null;
        adv_verb_aux_tense = verbobj.callAll(subject, verb, aux, tense, "", adverb);
        if (!(post1.equals(pps_str[0]))) {
            post1result = " " + postobj.addPostPositions(noun1, post1, 1, issingular1);

        } else if (!(clitic1.equals(clitic_str[0]))) {
            clitic1result = "" + cliticobj.addclitic(noun1, case1, "", clitic1, issingular1);
        } else if (!(case1.equals(case_str[0]))) {
            case1result = " " + caseobj.addCase(noun1, case1, "", issingular1);
        }
        if (!(post2.equals(pps_str[0]))) {
            post2result = " " + postobj.addPostPositions(noun2, post2, 1, issingular2);
        } else if (!(clitic2.equals(clitic_str[0]))) {
            clitic2result = "" + cliticobj.addclitic(noun2, case2, "", clitic2, issingular1);
        } else if (!(case2.equals(case_str[0]))) {
            case2result = " " + caseobj.addCase(noun2, case2, "", issingular2);
        }
        if (!(adjective1.equals(adj_str[0]))) {
            adj1result = " " + adjobj.addAdjective(adjective1);
        }
        if (!(adjective2.equals(adj_str[0]))) {
            adj2result = " " + adjobj.addAdjective(adjective2);
        }
        for (int count = 0; count < adv_verb_aux_tense.length && adv_verb_aux_tense[count] != null; count++) {
            adv_verb_aux_tense[count] = adj1result + " " + clitic1result + " " + case1result + post1result + adj2result + case2result + clitic2result + post2result + " ";
        }
        return adv_verb_aux_tense;
    }

    /** This is the main method*/
    public static void main(String ar[]) {
        new CorporateDemo1();
    }
}

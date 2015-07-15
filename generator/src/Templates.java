
import clia.unl.Source.Word_Gen.Generator.*;
import java.util.ArrayList;
/***
 *
 * @author karthikeyans
 * @since AUCEG
 * @version 1.0
 */
public class Templates {



    public ArrayList temp_process(String input) {
        input=removeSymbols(input.trim());
        ArrayList results = new ArrayList();
        ArrayList generator = gen_process(input.trim());
        results.add(generator);
        return results;
    }

    public ArrayList gen_process(String input) {
        input=wordAlias(input.trim());
        for(int i=0;i<input.length();i++){
            int value=input.charAt(i);
            System.out.println(value);
        }
        ArrayList returnwords = new ArrayList();
        String[] cases = {"இடமிருந்து", "இலிருந்து", "ஐ", "உக்காக", "அக்காக", "க்காக", "கு", "க்கு", "உக்கு", "அக்கு", "அது", "உடைய", "ஆல்", "இடம்", "இல்", "கண்", "ஓடு", "உடன்"};
        for (int i = 0; i < cases.length; i++) {
            try {
                word_noun noun = new word_noun();
                StringBuffer genwords = noun.NounCMGen1(input, "பெயர்ச்சொல்+வேற்றுமை உருபு", cases[i]);
                String genwords1 = genwords.toString().trim();
                if (genwords1.length() != 0) {
                    returnwords.add(genwords1);
                    System.out.println(genwords1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnwords;
    }

    public String wordAlias(String input){
        if(input.endsWith("து")){
            input=input.replace("து", "த்");
        }
        return input;
    }

    public String removeSymbols(String input) {
        String[] symbols = {"!", "@", "#", "$", "%", "^", "&", "_", "{", "}", "=", "[", "]", "|", "/", "\\", "\"", ">", "<", ";", ":", "'", ",", ".", "~", "`", "?", "+", "-", "*", "(", ")"};
        for (int i = 0; i < symbols.length; i++) {
            if (input.contains(symbols[i])) {
                input = input.replace(symbols[i], "");
            }
        }
        return input;
    }


    public static void main(String[] args) {
        Templates generator = new Templates();
        ArrayList results = generator.temp_process("அம்மாவை");
    }
}

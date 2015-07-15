
import java.util.*;
import org.apache.nutch.analysis.unl.ta.Analyser;

public class Test {
    public static void main(String args[]) {
        Analyser analyser = new Analyser();
        ArrayList arr = analyser.onlineDictAnalyser("மதுரையிலிருந்து", "Tamil");
        System.out.println(arr.get(0).toString());
        System.out.println(arr.get(1).toString());
        System.out.println(arr.get(2).toString());
        System.out.println(Analyser.isAnalysed("கணினியிடாம்"));
    }
}


//மதுரையிலிருந்து
//மதுரைக்கு
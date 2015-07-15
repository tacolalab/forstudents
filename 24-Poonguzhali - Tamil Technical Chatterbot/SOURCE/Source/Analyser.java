/**
  *  This class is to create the user interface for Morphological
  *  Analyser for noun
  */

import java.util.*;

public class Analyser
{
	public String analyze(String input)
	{
		Methods vme = new Methods();
		VSearch search = new VSearch();

		Stack outputStack = new Stack();
		String output = "\n";

		if(search.dicSearch(input,"adjective.txt"))
			outputStack.push(input+" < adjective > ");

		else if(search.dicSearch(input,"noun.txt"))
			outputStack.push(input+" < noun > ");

		else if(search.dicSearch(input,"pronoun.txt"))
			outputStack.push(input+" < pronoun > ");

		else if(search.dicSearch(input,"adverb.txt"))
			outputStack.push(input+" < adverb > ");

		else
			outputStack = vme.checkverb(input);

		while (!outputStack.empty())
			output += (outputStack.pop().toString())+"\n";

		return output;
	}

}
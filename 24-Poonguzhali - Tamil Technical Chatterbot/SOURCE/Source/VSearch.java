import java.io.*;

/**
  * program to search the dictionary for defective types of verb category
  */
public class VSearch
{
	VSearch(){}

	public boolean dicSearch(String word,String fileName)
	{
		String temp="";
		FileReader dic = null;
		try
		{
			try
			{
				dic = new FileReader(fileName);
			}
			catch(Exception e)
			{
			}
			StreamTokenizer input = new StreamTokenizer(dic);
			int tokentype = 0;
			while((tokentype = input.nextToken()) != StreamTokenizer.TT_EOF)
			{
				if(tokentype == StreamTokenizer.TT_WORD)
				{
					temp=input.sval;
					if(temp.equals(word))
						return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}

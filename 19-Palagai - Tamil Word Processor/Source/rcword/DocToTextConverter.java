package rcword;

/* a temp.txt file is used initially to retrieve set of words.*/
import java.io.*;
class DocToTextConverter
{
	FileInputStream fis;
	FileOutputStream fos_word;
	FileOutputStream fos_link;
	String inputFile;
	boolean isMac;

	public DocToTextConverter(String infile,String wordfile,String linkfile)
	{
		try
		{
			isMac=false;
			inputFile=new String(infile);
			fis = new FileInputStream(infile);
			fos_word = new FileOutputStream(wordfile);
			fos_link = new FileOutputStream(linkfile);
		}
		catch(Exception e)
		{
		}
	}

	public int convert_doc_txt()
	{
        long offset;
		/* 1536-offset at which actual text is present in MS Word doc*/
		int bytes=1,is_link=0;
		boolean is_word=false;
		try
		{
            if(!check_valid_doc())
            	return 1;//ERROR
			fis.close();
			offset=getOffset();
			System.out.println(offset);
			fis.close();
			fis=new FileInputStream(inputFile);
			fis.skip(offset);
			out:while(true)
			{

				while (bytes!=0)
				{

					bytes=fis.read();

					if (bytes==80  && is_link==1)
						break;

					if ((bytes==32 || bytes==72) && is_link==1)
					{
						process_link();
						fos_word.write((int)' ');
						is_link=0;
						continue;
					}

					if (bytes==19)
					{
						is_link=1;
						continue;
					}
					else
						is_link=0;

					if(bytes==13) /*for processing '\n' in Doc */
					{
						fos_word.write((int)'\n');
						bytes-=3;
					}
					if(bytes==9)
					{
						fos_word.write((int)'	');
						System.out.println(" comesssssss here 111");
					}

					if ((bytes>0 && bytes<=32) || bytes==147 || bytes==148)
					{
						if(is_word)
							fos_word.write((int)' ');
						is_word=false;
					}
					else if(bytes!=0)
					{
						fos_word.write(bytes);
						is_word=true;
					}
				}
				fos_word.write(bytes);
				int i=0;
				while ((bytes=fis.read())==0 && i<257)
					i++;

				if(i==257)
					break out;
				else
				{
					if (bytes==80  && is_link==1)
						break;

					if ((bytes==32 || bytes==72) && is_link==1)
					{
						process_link();
						fos_word.write((int)' ');
						is_link=0;
						continue;
					}

					if (bytes==19)
					{
						is_link=1;
						continue;
					}
					else
						is_link=0;

					if(bytes==13) /*for processing '\n' in Doc */
					{
						fos_word.write((int)'\n');
						bytes-=3;
					}

					if(bytes==9)
					{
						fos_word.write((int)'	');
						System.out.println(" comesssssss here 2222222");
					}

					if ((bytes>0 && bytes<=32) || bytes==147 || bytes==148)
					{
						if(is_word)
							fos_word.write((int)' ');
						is_word=false;
					}
					else if(bytes!=0)
					{
						fos_word.write(bytes);
						is_word=true;
					}
				}

			}
			fis.close();
    		fos_word.close();
	    	fos_link.close();
		}
		catch(Exception e)
		{
			System.out.println("File not found");
		}
		return 0;//SUCCESS

	}
	private void process_link()
	{
		int bytes=0;
		byte []link_byte=new byte[4]; /* check whether mail or http link*/
		String link_str;
		try
		{
			bytes=fis.read();

			/*check for Table of Contents codings=92*/
			/*check for page breaks=65*/
			/*check for include picture=73*/
                        /*check for check boxes=70*/
                        /*check for date=68*/
                        if(bytes==84 || bytes==65 || bytes==73)
			{
				while(fis.read()!=20);
					return;
			}
                        if(bytes==70 || bytes==68)
                        {
                                //System.out.print("*");
                                while(fis.read()!=21);
					return;
			}

			do
			{
				if(bytes==20)
					return;
			}
			while((bytes=fis.read())!=34);


			fis.read(link_byte);
			link_str=new String(link_byte);
			System.out.println("The link found is"+link_str);
			if(link_str.equals("mail"))
				while(fis.read()!=34);
			else
			{
				fos_link.write(link_byte);
				while((bytes=fis.read())!=34)
					fos_link.write(bytes);
				fos_link.write((int)'\n');
			}
			/* to skip 3 bytes */
                        while ((bytes=fis.read())!=20)
						{
							if(bytes==21)
								return;
						}

			while((bytes=fis.read())!=21)
				fos_word.write(bytes);
		}
		catch(Exception e)
		{
		}
	}
        private boolean check_valid_doc()
        {
                int byte1=0,byte2=0;
                try
                {
                        byte1=fis.read();
                        byte2=fis.read();
                }
                catch(Exception e)
                {
                }
                if(byte1==208 && byte2==207)
                {
                        //System.out.println(byte1+" "+byte2);
                        return true;
                }
				else if (byte1==254 && byte2==55)
				{
					System.out.println("Mac word file");
					isMac=true;
					return true;
				}
                else
                {
                        System.out.println("not a valid doc file");
                        return false;
                }
        }
		private int getOffset()
		{
			int byte1=0,byte2=0,byte3,byte4,offset=512;
			if(isMac)
				return 256;
			try
			{
				fis = new FileInputStream(inputFile);
				fis.skip(512);
				byte1=fis.read();
				byte2=fis.read();
				fis.skip(22);
				byte3=fis.read();
				byte4=fis.read();
				if (byte1==236 && byte2==165)
				{
					if(byte3==0 && byte4==6)
						return (offset+8*256);
					else
						return (offset+byte4*256);
				}
				else if (byte1==220 && byte2==165)
					return (offset+byte4*256);
				else if(byte1==82 && byte2==0)
					return offset+15*256;
				else if(byte1==253 && byte2==255 && byte4==0)
					return (byte3+6)*256;
				else if(byte1==254 && byte2==255 && byte4==0)
					return (byte3+5)*256;

			}
			catch(Exception e)
			{
			}
			return offset+4*256;
		}

}

/*
public class doc_txt
{
	public static void main(String [] args)
	{
		DocToTextConverter convert=new DocToTextConverter(args[0],args[1],args[2]);
		convert.convert_doc_txt();
	}

}

*/
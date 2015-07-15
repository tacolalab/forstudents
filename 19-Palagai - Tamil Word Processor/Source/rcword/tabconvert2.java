package rcword;

import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

/**
 * Used to convert the TAB text in to the internal code and vise versa.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
public class tabconvert2
{

	/**
	* Does the reverse process of the method convert.
	* @ b sequence of numbers for which the equivalent string is required.
	*/
	public static  String revert( byte b[] )
	{
		short tab =0;
		short temp =0;
		short prefix =0;
		boolean puttemp = false;
		boolean tamil = false;
		boolean zero = false;
		boolean putprefix = false;
		int i=0;
		ArrayList al= new ArrayList();


		if( b[0] == 0)
		{
			zero = true;
			i++;
		}
		else
		zero = false;

		for(  ; i<b.length; i++)
		{
			if(zero)
			{   // Nontamil
				tab = (short)b[i];
			}
			else
			{  // Tamil

				// the below two line for special sorting asked by palani
				if(b[i]==38)
					tab = (short) 32;

				else if(  ( b[i] <= 12) && ( b[i] >=1 )  )
				{
					//System.out.println(" vol");
					tab =(short)( b[i]+219 ) ;
				}
				else if( b[i] == 13)
				{	     // System.out.println("akku ");
					tab =(short)( b[i]+218 ) ;
				}
				else if( 	(b[i] >= 14)  &&  (b[i] <= 36)  )
				{	 //else if
					if( i+1<b.length )
					{  //inner if
						switch( b[i+1] )
						{
							case 0:
								tab = (short)( b[i]+218 ) ;
								temp = (short) 162;
								puttemp=true;
								i++;
								break;
								/*
								case -1:
								tab = (short)( b[i]+218 ) ;
								temp = (short) 162;
								puttemp=true;
								i++;
								break;
								*/
							case 1:
								tab = (short)(b[i]+218 ) ;
								i++;
								break;
							case 2:
								tab = (short)( b[i]+218 ) ;
								temp =(short) 163;
								puttemp = true;
								i++;
								break;

							case 3:
								if( b[i] == 18 )
								{
									tab = (short) 174;
								}
								else
								{
									tab = (short)( b[i]+218 ) ;
									temp =(short) 164;
									puttemp = true;
								}
								i++;
								break;

							case 4:
								if( b[i] == 18 )
								{
									tab = (short) 175;
								}
								else
								{
									tab = (short)( b[i]+218 ) ;
									temp =(short) 166;
									puttemp = true;
								}
								i++;
								break;

							case 5:
								if( (b[i] >= 14)  &&  (b[i] <= 20)  )
								{
									tab = (short)( b[i]+162 ) ;
									i++;
								}
								else if( (b[i] >= 21)  &&  (b[i] <= 31)  )
								{
									tab = (short)( b[i]+163 ) ;
									i++;
								}
								else if(  (b[i] >= 32)  &&  (b[i] <= 36)  )
								{
									tab = (short)( b[i]+218 ) ;
									temp =(short) 167;
									puttemp = true;
									i++;
								}
								break;

							case 6:
								if( (b[i] >= 14)  &&  (b[i] <= 20)  )
								{
									tab = (short)( b[i]+181 ) ;
									i++;
								}
								else if( (b[i] >= 21)  &&  (b[i] <= 25)  )
								{
									tab = (short)( b[i]+182 ) ;
									i++;
								}
								else if( (b[i] >= 26)  &&  (b[i] <= 31)  )
								{
									tab = (short)( b[i]+188 ) ;
									i++;
								}
								else if( (b[i] >= 32)  &&  (b[i] <= 36)  )
								{
									tab = (short)( b[i]+218 ) ;
									temp = (short) 168;
									puttemp = true;
									i++;
								}
								break;

							case 7 :
								prefix = (short) 170;
								tab = (short)( b[i]+218 ) ;
								putprefix = true;
								i++;
								break;

							case 8 :
								prefix = (short) 171; // 34 has to be 171 .This is due to softview problem
								tab = (short)( b[i]+218 ) ;
								putprefix = true;
								i++;
								break;

							case 9 :
								prefix = (short) 172;
								tab = (short)( b[i]+218 ) ;
								putprefix = true;
								i++;
								break;

							case 10 :
								prefix = (short) 170;
								tab = (short)( b[i]+218 ) ;
								temp = (short) 163;
								putprefix = true;
								puttemp= true;
								i++;
								break;

							case 11 :
								prefix = (short) 171; // 34 hat to be 171.This is due to softview 171-34 problem
								tab = (short)( b[i]+218 ) ;
								temp = (short) 163;
								putprefix = true;
								puttemp= true;
								i++;
								break;

							case 12 :
								prefix = (short) 170;
								tab = (short)( b[i]+218 ) ;
								temp = (short) 247;
								putprefix = true;
								puttemp= true;
								i++;
								break;

							default :
								tab = (short)( b[i]+218 ) ;
								temp = (short) 162;
								puttemp=true;
								break;
						}// end of switch

					}  // end inner if
					else
					{
						tab = (short)( b[i]+218 ) ;
						temp = (short) 162;
						puttemp=true;
					}
				} //end else if

				else if(b[i] == 37)
				tab= (short) 255;

			}// end of tamil

			if(putprefix)
			{
				al.add(new Character( (char) prefix) );
				putprefix = false;
			}

			al.add(new Character((char)tab) );

			if(puttemp)
			{
				al.add(new Character( (char) temp ) );
				puttemp = false;
			}

			//al.add(  new Character( (char) ((short)5) )  ) ;


			if( (i+1<b.length) && (b[i+1] == 0) )
			{
				//  System.out.println(" zero ");
				i++;
				if(zero)
				{
					zero = false;
					continue;
				}
				else
				{
					zero = true;
					continue;
				}
			}
		}// end of for

		String ret = new String();

		for(int j=0; j<al.size();j++)
		{
			ret = ret+( (Character)(al.get(j)) ).charValue() ;
		}
		return ret;
	}	 //end of revert



	/**
	* Converts the given text to the internal code.
	* Gets a string and checks each character. According to this it
	* will check the next first and second characters if necessary. With
	* this check condition appropriate one or two integers will be added
	* in the output byte[].
	* If a character is non-Tamil character then a zero (‘0’) will be
	* added stating that non Tamil is started and it puts the non-Tamil
	* character’s equivalent ASCII value.
	* If the non-Tamil characters end by a Tamil character it will put a
	* zero (‘0’) stating that non-Tamil ends and then puts the correct
	* numbers in the output bye[].
	* Ex.     Some words and their converted out puts are given bellow.
	* 		Üñ¢ñ£		-	1 23 23 2
	* 		Üabcñ¢ñ£	-	1 0 97 98 99 0 23 23 2
	* 		Apple		-	0 65 112 112 108 101
	*
	* @param p string to be converted.
	*/
	public static byte[] convert(String p)
	{
		byte mycode = 0;
		byte temp=0;
		boolean puttemp= false;
		boolean tamil = false;
		boolean zero = false;
		ArrayList al = new ArrayList();

		if(p==null)
		{
			byte[] as = null;
			return as;
		}

		for(int i=0 ; i<p.length() ; i++ )
		{
			if( (p.charAt(i) == 32))
			{
				tamil = true;
			}
			else if(  ( p.charAt(i) <=127 )  && ( p.charAt(i) >=0 ) )
				tamil = false;
			else if( p.charAt(i) >= 128)
				tamil = true;
			if( p.charAt(i) == 34)  // This condition is only for Softview due to that 171-34 interchange problem.
				tamil = true;

			if( (!tamil) && (!zero) )
			{
				al.add( new Byte( (byte) 0 ) );
				zero = true;
			}
			else if( (tamil) && (zero) )
			{
				al.add( new Byte( (byte) 0 ) );
				zero = false;
			}


			if(!tamil)
			{
				mycode = ( byte ) p.charAt(i);
				al.add( new Byte( mycode) );
				continue ;
			}


			else if(tamil)
			{	// 123
				if( (p.charAt(i) == 32))
				mycode = 38;

				else if(  ( p.charAt(i)>= 220 )  &&  (  p.charAt(i)<=  231 ) )
				{
					if( (  p.charAt(i) ==   229 ) && ( i+1<p.length() )  &&  (  p.charAt(i+1) ==   247 ) )
					{
						mycode =   12;
						i++;
					}
					else if( p.charAt(i) == 231 )
					{
						mycode =  13;
					}
					else
					{
						mycode =  (byte)(p.charAt(i) - 219);
					}
				}

				else if(  (  p.charAt(i)>=  176 )  &&  (  p.charAt(i)<=  182 ) )
				{
					mycode =  (byte)(p.charAt(i) - 162);
					temp = 5;
					puttemp = true;
				}

				else if(  (  p.charAt(i)>=  184 )  &&  (  p.charAt(i)<=  194 ) )
				{
					mycode =  (byte)(p.charAt(i) - 163);
					temp =  5;
					puttemp = true;
				}

				else if(  (  p.charAt(i)>=  195 )  &&  (  p.charAt(i)<=  201 ) )
				{
					mycode =  (byte)(p.charAt(i) - 181);
					temp =   6;
					puttemp = true;
				}

				else if(  (  p.charAt(i)>=  203 )  &&  (  p.charAt(i)<=  207 ) )
				{
					mycode =  (byte)(p.charAt(i) - 182);
					temp =   6;
					puttemp = true;
				}

				else if(  (  p.charAt(i)>=  214 )  &&  (  p.charAt(i)<=  219 ) )
				{
					mycode =  (byte)(p.charAt(i) - 188);
					temp =   6;
					puttemp = true;
				}

				else if(  (  p.charAt(i)>=  232 )  &&  (  p.charAt(i)<=  254 ) )
				{

					if( i+1<p.length() )
					{
						switch(  p.charAt(i+1) )
						{
							case 162:
								mycode =  (byte)(p.charAt(i) - 218);
								i++;
								temp = 0;
								//temp = -1;
								puttemp = true;
								// the above two statements are only for the purposes of sorting
								break;

							case 163:
								mycode =  (byte)(p.charAt(i) - 218);
								i++;
								temp	 =   2;
								puttemp = true;
								break;

							case 164:
								mycode =  (byte)(p.charAt(i) - 218);
								i++;
								temp =   3;
								puttemp = true;
								break;

							case 166:
								mycode =  (byte)(p.charAt(i) - 218);
								i++;
								temp =   4;
								puttemp = true;
								break;

							case 167:
								mycode =  (byte)(p.charAt(i) - 218);
								i++;
								temp =  5 ;
								puttemp = true;
								break;

							case 168:
								mycode =  (byte)(p.charAt(i) - 218);
								i++;
								temp =  6 ;
								puttemp = true;
								break;

							default :
								mycode =  (byte)(p.charAt(i) - 218);
								temp =   1;
								puttemp = true;
								break;
						} // end of switch
					}
					else
					{
						mycode =  (byte)(p.charAt(i) - 218);
						temp =   1;
						puttemp = true;
					}
				}
				else if(  p.charAt(i) == 172)
				{
					mycode = (byte)( p.charAt(i+1) - 218);
					i++;
					temp =   9 ;
					puttemp = true;
				}
				else if(  p.charAt(i) == 170)
				{ /////
					if( i+2<p.length() )
						switch(  p.charAt(i+2) )
						{
							case 163 :
								mycode =  (byte)(p.charAt(i+1) - 218);
								temp =   10 ;
								puttemp = true;
								i+=2;
								break;

							case 247 :
								if( i+3<p.length() )
								{
									if(!checkLa(p.charAt(i+3)))
									{
										mycode =  (byte)(p.charAt(i+1) - 218 );
										temp =   7 ;
										puttemp = true;
										i++;
										break;
									}
								}
								mycode = (byte)(p.charAt(i+1) - 218 );
								temp =   12 ;
								puttemp = true;
								i+=2;
								break;

							default :
								mycode =  (byte)(p.charAt(i+1) - 218 );
								temp =   7 ;
								puttemp = true;
								i++;
								break;
						} // end of switch
					else
					{
						mycode =  (byte)(p.charAt(i+1) -218);
						temp =   7 ;
						puttemp = true;
						i++;
					}
				}
				else if(  p.charAt(i) == 171 )
				{
					if( (i+2<p.length()) &&  (  p.charAt(i+2) == 163) )
					{
						mycode =  (byte)(p.charAt(i+1) - 218);
						temp =   11 ;
						puttemp = true;
						i+=2;
					}
					else
					{
						mycode =  (byte)(p.charAt(i+1) - 218 );
						temp =   8 ;
						puttemp = true;
						i++;
					}
				}
				else if(  p.charAt(i) == 174 )
				{
					mycode =   18;
					temp =   3 ;
					puttemp = true;
				}
				/*     else if( (  p.charAt(i) == 40 ) && ( i+2<p.length() ) && (  p.charAt(i+2) == 41) )
				{	  // this condition is actully wrong this is due to alcrypt
				mycode =   18;
				temp =   3 ;
				puttemp = true;
				i+=2;
				}  */

				else if(  p.charAt(i) == 175 )
				{
					mycode =   18;
					temp =   4 ;
					puttemp = true;
				}
				else if(  p.charAt(i) == 255 )
					mycode = 37;
			}//123
			//  else
			//     mycode = (byte)p.charAt(i);
			al.add( new Byte( mycode ) );
			if(puttemp)
			{
				al.add( new Byte( temp ));
				puttemp = false;
			}
		}

		byte ret[] = new byte[al.size()];

		//byte by = ((Byte) al.get(2));
		//((Byte) al.get(2)).byteValue();
		for(int l=0; l<al.size() ;l++ )
		{
			ret[l] =((Byte) al.get(l)).byteValue(); //(Byte)al.get(l);
		//System.out.print(ret[l]+"  ");
		}

	return ret;
	}


	/**
	*  Used to check the 'La' character(29) comes as a combination
	*  three letters or separate letter.
	*
	* @ param nexttola next character of 'la' in the text.
	* @ return boolean true if 'La' comes in three letter consonant i.e 'auv'
	*				   false if 'La' comes in two letters consonant
	*/
	public static boolean checkLa(char nexttola)
	{
		switch(nexttola)
		{
			case 162:   return false;
			case 163:   return false;
			case 164:   return false;
			case 166:   return false;
			default :   		return true;
		}
	}


	/**
	* Used to check whether the given word is Tamil word or non-Tamil word.
	* It checks the first character. If this is Tamil then the word is Tamil
	* word. Otherwise it is a non-Tamil word. Mixed words are not considered here.
	* That is they are decided with their first character.
	*
	* @ param word word whose language has to be identified.
	* @ boolean true if the word is tamil, otherwise false.
	*
	*/
	public static boolean checkLanguage(String word)
	{
		if(word.length() == 1)
		{
			if( (word.charAt(0) <=255 )  && ( word.charAt(0) >=170 )  )
				return true;
			else
				return false;
		}
		return false;
	}


}


import java.util.Stack;

public class Methods
{

	ByteMeth me = new ByteMeth();
	TabMethods tm = new TabMethods();
	VVerbalParticiple vbp = new VVerbalParticiple();
	VTenses tense = new VTenses();
	VAuxillary aux = new VAuxillary();
	VGender removegender = new VGender();
	VClitics clitic = new VClitics();
	VOthers removeMiscs = new VOthers();
	VMiddleGender middlegender = new VMiddleGender();
	VCheckInf infinitive = new VCheckInf();
	VNegative negative = new VNegative();
	VSearch search = new VSearch();
	Cases tcase = new Cases();

	public Stack checkverb(String inputString)
	{
		Stack s=new Stack();

		if(isEndswithKCTP(inputString))
			inputString = inputString.substring(0,inputString.length()-2);

		if(isNoun(inputString))
		{
			s.push(inputString+" < Noun > ");
			return s;
		}
		else if(isVerb(inputString))
		{
			s.push(inputString+" < Verb > ");
			return s;
		}

		s.push(inputString);

		byte givenString[] = tm.convert(inputString);;

		if(me.endswith(givenString,VVariables.a) && !me.endswith(givenString,VVariables.kooda) && !me.endswith(givenString,VVariables.aana) && !me.endswith(givenString,VVariables.ana) && !me.endswith(givenString,VVariables.aaga))
		{
			if(me.endswith(givenString,VVariables.kka) || me.endswith(givenString,VVariables.ka))
				s = infinitive.removeInf(s);
			else if(isEndswithRelativeParticipleSuffix(s))
			{
				s.pop();
				givenString = me.subarray(givenString,0,givenString.length-1);
				s.push("\n‹ < Relative Participle Suffix>");
				s.push(tm.revert(givenString));
				s = tense.checktenses(s);
			}
			else
			{
				s = infinitive.removeInf(s);
			}

			s = checkForAux(s);

			if(isVerb(s))
			{
				s.push(s.pop().toString().trim()+" < verb > ");
				return s;
			}
		/*	else
			{
				while(!s.empty())
					s.pop().toString();
				s.push(inputString+"Ò¢"+" < Noun Modifier > ");
				return s;
			}*/
		}

		while(true)
		{
			givenString = tm.convert(s.peek().toString().trim());

			if(me.endswith(givenString,VVariables.um))
			{
				if(me.endswith(givenString,VVariables.vendum)||me.endswith(givenString,VVariables.koodum))
					break;
				else
				 {
						s = clitic.removeUm(s);
						if(isEndswithFutureNeuterSuffix(s))
						{
							Object tempobj = s.pop();
							String tempstr = s.pop().toString();
							s.push(tempstr.substring(0,(tempstr.indexOf(" < "))+3)+"Neuter Gender >");
							s.push(tempobj);
							s = tense.removeFutureNeuterSuffix(s);
							s = checkForAux(s);
							if(isVerb(s))
							{
								s.push(s.pop().toString().trim()+" < Verb > ");
								return s;
							}
						}
				 }
			}
			else if(isEndswithThaan(givenString))
				s = clitic.removeThaan(s);
			else if(me.endswith(givenString,VVariables.adaa)||me.endswith(givenString,VVariables.adi))
				s = clitic.removeAdaaAdi(s);
			else if(me.endswith(givenString,VVariables.ae))
				s = clitic.removeAe(s);
			else if(me.endswith(givenString,VVariables.kooda))
				s = clitic.removeKooda(s);
			else if(me.endswith(givenString,VVariables.aal))
				s = clitic.removeAal(s);
			else if(me.endswith(givenString,VVariables.aa))
				s = clitic.removeAa(s);
			else break;
		}
		if(isNoun(s))
		{
			s.push(s.pop().toString().trim()+" < Noun > ");
			return s;
		}
		if(isVerb(s))
		{
			s.push(s.pop().toString().trim()+" < Verb > ");
			return s;
		}

		givenString = tm.convert(s.peek().toString().trim());

		if(me.endswith(givenString,VVariables.aathey))
		{
			s = negative.removeAathey(s);
			s = infinitive.removeInf(s);
		}
		else if(me.endswith(givenString,VVariables.aatheergal))
		{
			s = negative.removeAatheergal(s);
			s = infinitive.removeInf(s);
		}
		else if(me.endswith(givenString,VVariables.aamal))
		{
			s = negative.removeAamal(s);
			s = infinitive.removeInf(s);
		}
		else if(me.endswith(givenString,VVariables.aathu))
		{
			s = negative.removeAathu(s);
			s = infinitive.removeInf(s);
		}
		else if(me.endswith(givenString,VVariables.aavittal))
		{
			s = negative.removeAavittal(s);
			s = infinitive.removeInf(s);
		}
		else if(me.endswith(givenString,VVariables.ungal))
		{
			s = negative.removeUngal(s);
			s = checkForAux(s);
		}
		if(isVerb(s))
		{
			s.push(s.pop().toString().trim()+" < verb > ");
			return s;
		}
		if(isEndswithVbp(s))
			s = vbp.checkVbp(s,tm.convert(s.pop().toString().trim()));

		if(isEndswithCase(s))
			s = removeCases(s);

		if(isEndswithOblique(s))
			s = removeOblique(s);
		else if(isEndswithPluralMarker(s))
			s = removePluralMarker(s);

		if(isNoun(s))
		{
			s.push(s.pop().toString().trim()+" < Noun > ");
			return s;
		}

		if(isEndswithPronouns(s))
		{
			s = middlegender.checkMiddleGender(s);
			s = tense.checktenses(s);
		}

		if(isEndswithAuxInfEnd(s))
			s = removeAuxInfEnd(s);

		if(isEndswithAuxVbpEnd(s))
		{
			s = removeAuxVbpEnd(s);
			if(isEndswithVbp(s))  //else error;
				s = vbp.checkVbp(s,tm.convert(s.pop().toString().trim()));
		}

		if(isEndswithGenderEndings(s))
		{
			s = removegender.checkaalaan(s,tm.convert(s.pop().toString().trim()));
			s = tense.checktenses(s);
		}

		s = checkForAux(s);

		if(isVerb(s))
			s.push(s.pop().toString().trim()+" < verb > ");
		return s;
	}

	public Stack removeCases(Stack s)
	{
		byte[] topElement = tm.convert(s.pop().toString().trim());

		if(me.endswith(topElement,NVariables.il))
			s = tcase.checkil(s,topElement);
		else if(me.endswith(topElement,NVariables.in))
			s = tcase.checkin(s,topElement);
		else if(me.endswith(topElement,NVariables.ukkaaga))
			s = tcase.check_ukkaaga (s,topElement);
		else if(me.endswith(topElement,NVariables.kkaaga))
			s = tcase.check_kkaaga (s,topElement);
		else if(me.endswith(topElement,NVariables.akka))
			s = tcase.check_akka(s,topElement);
		else if(me.endswith(topElement,NVariables.aana))
			s = tcase.check_aana(s,topElement);
		else if(me.endswith(topElement,NVariables.ukku))
			s = tcase.check_ukku(s,topElement);
		else if(me.endswith(topElement,NVariables.akku))
			s = tcase.check_akku(s,topElement);
		else if(me.endswith(topElement,NVariables.kku))
			s = tcase.check_kku(s,topElement);
		else if(me.endswith(topElement,NVariables.irku))
			s = tcase.check_irku(s,topElement);
		else if(me.endswith(topElement,NVariables.ai))
			s = tcase.check_ai(s,topElement);
		else if(me.endswith(topElement,NVariables.ilirunthu))
			s = tcase.check_ilirunthu(s,topElement);
		else if(me.endswith(topElement,NVariables.itamirunthu))
			s = tcase.check_itamirunthu(s,topElement);
		else if(me.endswith(topElement,NVariables.udaiyathu))
			s.push("This method is not available");
		else if(me.endswith(topElement,NVariables.udaya))
			s = tcase.check_udaya (s,topElement);
		else if(me.endswith(topElement,NVariables.utan))
			s = tcase.check_utan(s,topElement);
		else if(me.endswith(topElement,NVariables.odu))
			s = tcase.check_odu(s,topElement);
		else if(me.endswith(topElement,NVariables.itam))
			s = tcase.checkitam(s,topElement);
		else if(me.endswith(topElement,NVariables.aal))
			s = tcase.remove_aal(s,topElement);
		return s;
	}

	boolean isEndswithCase(Stack s)
	{
		byte tempbyte[] = tm.convert(s.peek().toString().trim());
		if(me.endswith(tempbyte,NVariables.il)||me.endswith(tempbyte,NVariables.ai)
			||me.endswith(tempbyte,NVariables.in)||me.endswith(tempbyte,NVariables.ukkaaga)
			||me.endswith(tempbyte,NVariables.kkaaga)||me.endswith(tempbyte,NVariables.akka)
			||me.endswith(tempbyte,NVariables.ukku)||me.endswith(tempbyte,NVariables.kku)
			||me.endswith(tempbyte,NVariables.irku)||me.endswith(tempbyte,NVariables.ilirunthu)
			||me.endswith(tempbyte,NVariables.itamirunthu)||me.endswith(tempbyte,NVariables.udaya)
			||me.endswith(tempbyte,NVariables.utan)||me.endswith(tempbyte,NVariables.odu)
			||me.endswith(tempbyte,NVariables.itam)||me.endswith(tempbyte,NVariables.aal)
			||me.endswith(tempbyte,NVariables.aana))
			return true;
		else
			return false;
	}

	boolean isEndswithThaan(byte[] givenWord)
	{
		if(me.before_endswith(givenWord,VVariables.avan,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.aval1,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.avar,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.adhu,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.avai,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.avarkal,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.um,VVariables.thaan)
			||me.before_endswith(givenWord,me.addarray(VVariables.kooda,VVariables.th),VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.ae,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.aal,VVariables.thaan))
			return true;
		else
			return false;
	}


	public Stack removeAuxInfEnd(Stack s)
	{
		byte[] topelement= tm.convert(s.pop().toString().trim());

		if(me.endswith(topelement,VVariables.vendum))
			s = aux.checkVendum(s,topelement);
		if(me.endswith(topelement,VVariables.vendaam))
			s = aux.checkVendaam(s,topelement);
		if(me.endswith(topelement,VVariables.koodum))
			s = aux.checkKoodum(s,topelement);
		if(me.endswith(topelement,VVariables.koodathu))
			s = aux.checkKoodathu(s,topelement);
		if(me.endswith(topelement,VVariables.illai))
			s = aux.checkIllai(s,topelement);

		s = infinitive.removeInf(s);

		return s;
	}

	public Stack removeAuxVbpEnd(Stack s)
	{
		byte[][] auxVbpEnd = {VVariables.aayirru,VVariables.poyirru};

		Object obj = new Object();

		obj = s.pop();

		byte bfullstring[] = tm.convert(obj.toString().trim());

		if(me.endswith(bfullstring,auxVbpEnd[0]))
			s = aux.checkAayirru(s,bfullstring);
		else if(me.endswith(bfullstring,auxVbpEnd[1]))
			s = aux.checkPoyirru(s,bfullstring);

		return s;
	}

	public Stack checkAuxCat1(Stack s)
	{
		Object obj = new Object();

		obj = s.pop();

		byte bfullstring[] = tm.convert(obj.toString().trim());

		if(me.endswith(bfullstring,VVariables.vidu))
			s = aux.checkVidu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.koll))
			s = aux.checkKoll(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.kattu))
			s = aux.checkKattu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.kaattu))
			s = aux.checkKaattu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.tholai))
			s = aux.checkTholai(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.azu))
			s = aux.checkAzu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.kodu))
			s = aux.checkKodu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.kida))
			s = aux.checkKida(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.kizi))
			s = aux.checkKizi(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.thallu))
			s = aux.checkThallu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.podu))
			s = aux.checkPodu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.theer))
			s = aux.checkTheer(s,bfullstring);
		else s.push(obj);

		if(s.peek().toString().trim().length()>1)
		{
			s = vbp.checkVbp(s,tm.convert(s.pop().toString().trim()));
			s = checkForAux(s);
		}
		return s;
	}

	public Stack checkAuxCat3(Stack s)
	{
		Object obj = new Object();
		obj = s.pop();
		byte bfullstring[] = tm.convert(obj.toString().trim());
		if(me.endswith(bfullstring,VVariables.iru))
			s = aux.checkIru(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.poo))
			s = aux.checkPoo(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.vaa))
			s = aux.checkVaa(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.vai))
			s = aux.checkVai(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.mudi))
			s = aux.checkMudi(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.paar))
			s = aux.checkPaar(s,bfullstring);
		else
			s.push(obj);
		if(s.peek().toString().trim().length()>1)
		{
			s = vbp.checkVbp(s,tm.convert(s.pop().toString().trim()));
			s = infinitive.removeInf(s);
			s = checkForAux(s);
		}
		return s;
	}

	public Stack checkAuxCat2(Stack s)
	{
		Object obj = new Object();
		obj = s.pop();
		byte bfullstring[] = tm.convert(obj.toString().trim());
		if(me.endswith(bfullstring,VVariables.padu))
			s = aux.checkPadu(s,bfullstring);
		s = infinitive.removeInf(s);
		s = checkForAux(s);
		return s;
	}

	public Stack checkForAux(Stack s)
	{
		Object obj = new Object();

		obj = s.peek();

		byte bfullstring[] = tm.convert(obj.toString().trim());
		if(obj.toString().trim().length()>0)
		{
			if(me.endswith(bfullstring,VVariables.vidu)||me.endswith(bfullstring,VVariables.koll)
				||me.endswith(bfullstring,VVariables.theer)||me.endswith(bfullstring,VVariables.kaattu)||me.endswith(bfullstring,VVariables.tholai)||me.endswith(bfullstring,VVariables.azu)||me.endswith(bfullstring,VVariables.kodu)||me.endswith(bfullstring,VVariables.kida)||me.endswith(bfullstring,VVariables.kizi)||me.endswith(bfullstring,VVariables.thallu))
				s = checkAuxCat1(s);
			else if(me.endswith(bfullstring,VVariables.iru)||me.endswith(bfullstring,VVariables.poo)||me.endswith(bfullstring,VVariables.vaa)||me.endswith(bfullstring,VVariables.vai)||me.endswith(bfullstring,VVariables.mudi)||me.endswith(bfullstring,VVariables.paar))
				s = checkAuxCat3(s);
			else if(me.endswith(bfullstring,VVariables.padu))
				s = checkAuxCat2(s);
		}
		return s;
	}

	boolean isEndswithAuxVbpEnd(Stack s)
	{
		byte[][] auxVbpEnd = {VVariables.aayirru,VVariables.poyirru};
		byte[] givenWord = tm.convert(s.peek().toString().trim());

		for(int i=0; i < auxVbpEnd.length; i++)
			if(me.endswith(givenWord,auxVbpEnd[i]))
				return true;
		return false;
	}

	boolean isEndswithVbp(Stack s)//refer palani
	{
		byte[][] vbpList = {VVariables.thu,VVariables.ththu,VVariables.nththu,VVariables.nrru,VVariables.n1tu,VVariables.rr1u};
		byte[] givenWord = tm.convert(s.peek().toString().trim());

		if(me.endswith(givenWord,VVariables.adhu))
			return false;
		for(int i=0; i < vbpList.length; i++)
			if(me.endswith(givenWord,vbpList[i]))
				return true;
		return false;
	}

	boolean isEndswithAuxInfEnd(Stack s)
	{
		byte[][] auxInfEnd = {VVariables.vendum,VVariables.vendaam,VVariables.koodum,VVariables.koodathu,
							VVariables.illai};
		byte[] givenWord = tm.convert(s.peek().toString().trim());

		for(int i=0; i < auxInfEnd.length; i++)
			if(me.endswith(givenWord,auxInfEnd[i]))
				return true;
		return false;
	}

	boolean isEndswithGenderEndings(Stack s)
	{
		byte[][] genderEnding = { VVariables.aan,VVariables.aal1,VVariables.aar,VVariables.adhu,
											VVariables.ana,VVariables.aarkal,VVariables.eerkal,VVariables.ein,
											VVariables.om,VVariables.aai,VVariables.aai,VVariables.anar};
		byte[] givenWord = tm.convert(s.peek().toString().trim());

		for(int i=0; i < genderEnding.length; i++)
			if(me.endswith(givenWord,genderEnding[i]))
				return true;
		return false;
	}

	boolean isEndswithPronouns(Stack s)
	{
		byte[][] pronouns = { VVariables.avan,VVariables.aval1,VVariables.avar,VVariables.adhu,
									VVariables.avai,VVariables.avarkal};
		byte[] givenWord = tm.convert(s.peek().toString().trim());

		for(int i=0; i < pronouns.length; i++)
			if(me.endswith(givenWord,pronouns[i]))
				return true;
		return false;
	}

	boolean isEndswithOblique(Stack s)
	{
		byte[][] obliques = {VVariables.ththu,VVariables.thth,NVariables.tt,NVariables.arru};
		byte[] givenWord = tm.convert(s.peek().toString().trim());

		for(int i=0;i < obliques.length;i++)
			if(me.endswith(givenWord,obliques[i]))
				return true;
		return false;
	}

	boolean isEndswithPluralMarker(Stack s)
	{
		if(me.endswith(tm.convert(s.peek().toString().trim()),NVariables.kal))
			return true;
		else
			return false;
	}

	public Stack removePluralMarker(Stack s)
	{
		byte[] givenWord = tm.convert(s.pop().toString().trim());

		if(me.endswith(givenWord,NVariables.kkal))
		{
			byte pluralSuffix[] = me.subarray(givenWord,givenWord.length-NVariables.kkal.length,givenWord.length);
			byte remainingString[] = me.subarray(givenWord,0,givenWord.length-NVariables.kkal.length);

			s.push("\n"+tm.revert(pluralSuffix)+" < plural >");
			s.push("\n"+tm.revert(remainingString));
		}

		else if(me.endswith(givenWord,NVariables.kal))
		{
			byte pluralSuffix [] = me.subarray(givenWord,givenWord.length-NVariables.kal.length,givenWord.length);
			byte remainingString[] = me.subarray(givenWord,0,givenWord.length-NVariables.kal.length);
			s.push("\n"+tm.revert(pluralSuffix)+" < plural >");
			if(me.endswith(remainingString,NVariables.ng))
				remainingString = me.addarray(me.subarray(remainingString,0,remainingString.length-1),NVariables.m);
			else if(me.endswith(remainingString,NVariables.t))
				remainingString = me.addarray(me.subarray(remainingString,0,remainingString.length-1),NVariables.l1);
			s.push("\n"+tm.revert(remainingString));
		}

		return s;
	}

	public Stack removeOblique(Stack s)
	{

		byte toremove[] = tm.convert(s.pop().toString().trim());

		if(me.endswith(toremove,VVariables.ththu))
		{
			byte[] removed = me.subarray(toremove,toremove.length-VVariables.ththu.length,toremove.length);
			byte[] remainder = me.addarray(me.subarray(toremove,0,toremove.length-VVariables.ththu.length),NVariables.m);

			s.push("\n"+tm.revert(removed)+"< oblique >");
			s.push(tm.revert(remainder));
		}
		else if(me.endswith(toremove,NVariables.thth))
		{
			byte[] removed = me.subarray(toremove,toremove.length-NVariables.thth.length,toremove.length);
			byte[] remainder = me.addarray(me.subarray(toremove,0,toremove.length-NVariables.thth.length),NVariables.m);

			s.push("\n"+tm.revert(removed)+"< oblique >");
			s.push(tm.revert(remainder));
		}
		else if(me.endswith(toremove,NVariables.tt))
		{
			byte[] removed = me.subarray(toremove,toremove.length-NVariables.t.length,toremove.length);
			byte[] remainder = me.addarray(me.subarray(toremove,0,toremove.length-NVariables.t.length),NVariables.u);

			s.push("\n"+tm.revert(removed)+"< oblique >");
			s.push(tm.revert(remainder));
		}
		else if(me.endswith(toremove,NVariables.arru))
		{
			byte[] removed = me.subarray(toremove,toremove.length-NVariables.arru.length,toremove.length);
			byte[] remainder = me.subarray(toremove,0,toremove.length-NVariables.arru.length);


			s.push("\n"+tm.revert(removed)+"< oblique suffix>");

			if(me.endswith(remainder,NVariables.iv))
				remainder = me.subarray(remainder,0,remainder.length-1);

			s.push(tm.revert(remainder));
		}
		else
			s.push(tm.revert(toremove));
		return s;
	}

	boolean isNoun(Stack s)
	{
		if(search.dicSearch(s.peek().toString().trim(),"noun.txt"))
			return true;
		else
			return false;
	}

	boolean isVerb(Stack s)
	{
		if(search.dicSearch(s.peek().toString().trim(),"verb.txt"))
			return true;
		else
			return false;
	}
	boolean isNoun(String s)
	{
		if(search.dicSearch(s,"noun.txt"))
			return true;
		else
			return false;
	}

	boolean isVerb(String s)
	{
		if(search.dicSearch(s,"verb.txt"))
			return true;
		else
			return false;
	}
	boolean isEndswithRelativeParticipleSuffix(Stack s)
	{
		byte tenseMarkers[][] = {VVariables.kiru,VVariables.kir,VVariables.th,VVariables.nth,VVariables.n,VVariables.thth,VVariables.t,VVariables.in,VVariables.r1,VVariables.rr1,VVariables.nn,VVariables.n1tu,VVariables.tt/*VVariables.arukiru,VVariables.arukir,,VVariables.aruv,VVariables.r1p,VVariables.tpVVariables,pp,VVariables.nr1,VVariables.an1t,,VVariables.nr1*/};
		byte[] givenWord = tm.convert(s.peek().toString().trim());
		for(int i=0;i<tenseMarkers.length;i++)
			if(me.before_endswith(givenWord,tenseMarkers[i],VVariables.a))
				return true;
		return false;
	}
	boolean isEndswithTenseMarker(Stack s)
	{
		byte tenseMarkers[][] = {VVariables.kiru,VVariables.kir,VVariables.th,VVariables.nth,VVariables.n,VVariables.thth,VVariables.t,VVariables.in,VVariables.r1,VVariables.rr1,VVariables.nn,VVariables.n1tu,VVariables.tt/*VVariables.arukiru,VVariables.arukir,,VVariables.aruv,VVariables.r1p,VVariables.tpVVariables,pp,VVariables.nr1,VVariables.an1t,,VVariables.nr1*/};
		byte[] givenWord = tm.convert(s.peek().toString().trim());
		if(me.endswith(givenWord,NVariables.utan)||me.endswith(givenWord,VVariables.aan))
			return false;
		for(int i=0;i<tenseMarkers.length;i++)
			if(me.endswith(givenWord,tenseMarkers[i]))
				return true;
		return false;
	}
	boolean isEndswithKCTP(String inputString)
	{
		if(inputString.endsWith("Ë¢")||inputString.endsWith("Í¢")||inputString.endsWith("Ó¢")||inputString.endsWith("¢"))
			return true;
		return false;
	}
	boolean isEndswithFutureNeuterSuffix(Stack s)
	{
		 byte futureNeuterSuffixes[][] = {VVariables.yy,VVariables.zh,VVariables.kk,VVariables.n1n1,VVariables.t,VVariables.r1,VVariables.ll,VVariables.l1l1,VVariables.tk,VVariables.r1k,VVariables.ar};
		 byte[] givenWord = tm.convert(s.peek().toString().trim());

		 for(int i=0;i<futureNeuterSuffixes.length;i++)
		 	if(me.endswith(givenWord,futureNeuterSuffixes[i]))
		 		return true;
		 return false;

	}
}
import java.util.Stack;
public class VGender
{
	VVerbalParticiple vbp = new VVerbalParticiple();
	VTenses tense = new VTenses();
	VAuxillary aux = new VAuxillary();
	ByteMeth me = new ByteMeth();
	TabMethods tm = new TabMethods();

	public Stack checkaalaan(Stack s,byte [] s1)
	{
		if(me.endswith(s1,VVariables.aan) || me.endswith(s1,VVariables.aal1)
			||me.endswith(s1,VVariables.aar)||me.endswith(s1,VVariables.um)
			||me.endswith(s1,VVariables.om)||me.endswith(s1,VVariables.ein)
			||me.endswith(s1,VVariables.aai)||me.endswith(s1,VVariables.eer))
		{
			byte r[]=me.subarray(s1,s1.length-2,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-2));
			if(me.endswith(s1,VVariables.aai))
				s.push("\n"+tm.revert(r)+" < 2nd per. sing. > ");
			else if(me.endswith(s1,VVariables.aal1))
				s.push("\n"+tm.revert(r)+" < 3rd per. Fem. sing.>");
			else if(me.endswith(s1,VVariables.aan))
				s.push("\n"+tm.revert(r)+" < 3rd per. Mas. sing. > ");
			else if(me.endswith(s1,VVariables.ein))
				s.push("\n"+tm.revert(r)+" < 1st per. Sing. > ");
			else if(me.endswith(s1,VVariables.om))
				s.push("\n"+tm.revert(r)+" < 1st per. Plur. > ");
			else if(me.endswith(s1,VVariables.um))
				s.push("\n"+tm.revert(r)+" < Neuter Gender > ");
			else if(me.endswith(s1,VVariables.aar))
				s.push("\n"+tm.revert(r)+" < 3rd per. sing. Fem./Mas. >");
			else if(me.endswith(s1,VVariables.eer))
				s.push("\n"+tm.revert(r)+" < 2rd per. sing. >");
			s.push(tm.revert(a));
		}
		else if(me.endswith(s1,VVariables.ana)||me.endswith(s1,VVariables.adhu))
		{
			byte r[]=me.subarray(s1,s1.length-3,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-3));
			if(me.endswith(s1,VVariables.ana))
				s.push("\n"+tm.revert(r)+" < Neuter plural > ");
			else if(me.endswith(s1,VVariables.adhu))
				s.push("\n"+tm.revert(r)+" < Neuter singular > ");
			s.push(tm.revert(a));
		}
		else if(me.endswith(s1,VVariables.anar))
		{
			byte r[]=me.subarray(s1,s1.length-4,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-4));
			s.push("\n"+tm.revert(r)+" < 3rd person plural > ");
			s.push(tm.revert(a));
		}
		else if(me.endswith(s1,VVariables.aarkal)||me.endswith(s1,VVariables.eerkal))
		{
			byte r[]=me.subarray(s1,s1.length-5,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-5));
			if(me.endswith(s1,VVariables.aarkal))
				s.push("\n"+tm.revert(r)+" < 3rd person plural > ");
			else if(me.endswith(s1,VVariables.eerkal))
				s.push("\n"+tm.revert(r)+" < 2rd person plural > ");
			s.push(tm.revert(a));
		}
		return s;
	}
}
import java.util.Stack;

public class VOthers
{
	ByteMeth me = new ByteMeth();
	TabMethods tm = new TabMethods();

	public Stack checkKa(Stack s,byte [] s1)
	{
		if(me.endswith(s1,VVariables.ka))
		{
			byte r[]=me.subarray(s1,s1.length-VVariables.ka.length,s1.length);
			byte a1[]=me.subarray(s1,0,(s1.length-VVariables.ka.length));
			if(me.isequal(a1,tm.convert("ï")))
				return s;
			if(me.endswith(a1,VVariables.t))
			{
				a1 = me.subarray(a1,0,a1.length-1);
				a1 = me.addarray(a1,VVariables.l1);
			}
			if(me.endswith(a1,VVariables.k))  // for the case : ï¦è¢è
				return s;
			s.push("\n"+tm.revert(r)+" < õ¤òé¢«è£÷¢ õ¤¬ùºø¢Á õ¤°î¤ > ");
			s.push("\n"+tm.revert(a1));
		}
		return s;
	}	// end of function checkKa

	public Stack checkE(Stack s,byte [] s1)
	{
		if(me.endswith(s1,VVariables.e))
		{
			byte r[]=me.subarray(s1,s1.length-1,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-1));
			if(me.endswith(a,VVariables.ll))
				a = me.subarray(a,0,a.length-1);
			else
				a = me.addarray(a,VVariables.u);
			s.push("\n"+tm.revert(r)+" < õ¤¬ùªòê¢ê õ¤°î¤ > ");
			s.push("\n"+tm.revert(a));
			return s;
		}
		else
		return s;
	}	// end of function checkE

	public Stack checkU(Stack s,byte [] s1)
	{
		if(me.endswith(s1,VVariables.u))
		{
			byte r[]=me.subarray(s1,s1.length-1,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-1));
			s.push("\n"+tm.revert(r)+" < õ¤¬ùªòê¢ê õ¤°î¤ > ");
			s.push("\n"+tm.revert(a));
		}
		return s;
	}	// end of function checkU

	 	/*It will not work because we can't determine  whether it is 'peyarechcha vikuthi' or vinaiyechcha vikuthi
	 	if the tense marker is removed then it is called pv otherwise it will be vv*/

	public Stack checkA(Stack s,byte [] s1)
	{
		if(me.endswith(s1,VVariables.a))
		{
			byte r[]=me.subarray(s1,s1.length-1,s1.length);
			byte a1[]=me.subarray(s1,0,(s1.length-1));
			if(!s.empty())
			{
				Object obj = s.pop();
				if(me.endswith(a1,VVariables.p))
				{
					a1 = me.addarray(a1,VVariables.u);
					s.push("\n"+tm.revert(r)+" < õ¤¬ùªòê¢ê õ¤°î¤ > ");
					s.push("\n"+tm.revert(a1));
					s.push(obj);
					return s;
				}
				else if(s.peek().toString().endsWith(""))
				{
					s.push(obj);
					return s;
				}
				else
				{
					s.push("\n"+tm.revert(r)+" < õ¤¬ùªòê¢ê õ¤°î¤ > ");
					if(me.endswith(a1,VVariables.k))
						a1 = me.addarray(a1,VVariables.u);
					if(me.endswith(a1,VVariables.ll))
						a1 = me.subarray(a1,0,a1.length-1);
					s.push("\n"+tm.revert(a1));
					s.push(obj);
					return s;
				}
			}
			else
				return s;
		}
		else
		{
			return s;
		}
	}

	public Stack checkThal(Stack s,byte [] s1)
	{
		byte r[],a[];
		if(me.endswith(s1,VVariables.ththal))
		{
			r=me.subarray(s1,s1.length-VVariables.ththal.length,s1.length);
			a=me.subarray(s1,0,(s1.length-VVariables.ththal.length));
			s.push("\n"+tm.revert(r)+" ( ªî£ö¤ø¢ªðòó¢ õ¤°î¤ ) ");
			s.push("\n"+tm.revert(a));
			return s;
		}
		else if(me.endswith(s1,VVariables.thal))
		{
			r=me.subarray(s1,s1.length-VVariables.thal.length,s1.length);
			a=me.subarray(s1,0,(s1.length-VVariables.thal.length));
			s.push("\n"+tm.revert(r)+" ( ªî£ö¤ø¢ªðòó¢ õ¤°î¤ ) ");
			s.push("\n"+tm.revert(a));
			return s;
		}
		else
			return s;
	}	// end of function checkThal

	public Stack checkKal(Stack s,byte [] h)
	{
		if((h.length > VVariables.kal.length)&&me.endswith(h,VVariables.kal))
		{
			byte plural[]=me.subarray(h,h.length-3,h.length);
			byte rem[]=me.subarray(h,0,h.length-3);
			s.push("\n"+tm.revert(plural)+" ( ðù¢¬ñ ) ");
			//s = removegender.checkaalaan(s,rem);
			return s;
		}
		else
			return s;
	} // end of function checkKal

}

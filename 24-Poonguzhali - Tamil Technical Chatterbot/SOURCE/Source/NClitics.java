/**
  *  This class defines the methods to remove the clitics
  */

import java.util.Stack;

public class NClitics
{

	ByteMeth me = new ByteMeth();
	TabMethods tm = new TabMethods();
/**
  *  This to remove thaan
  */

	public Stack checkThaan(Stack s,byte [] h)
	{

		if(me.endswith(h,NVariables.thaan))
		{
			byte clitic[]=me.subarray(h,h.length-NVariables.thaan.length,h.length);
			byte rem[]=me.subarray(h,0,h.length-NVariables.thaan.length);
			s.push("\n"+tm.revert(clitic)+" < clitic > ");
			if(me.endswith(rem,NVariables.th)||me.endswith(rem,NVariables.iv))
				rem = me.subarray(rem,0,rem.length-1);
			s.push(tm.revert(rem));
		}
		return s;
	}
/**
  *  This to remove adaa or adi
  */

	public Stack checkAdaaAdi(Stack s,byte [] h)
	{
		if(me.endswith(h,NVariables.adaa)||me.endswith(h,NVariables.adi))
		{
			byte clitic[]=me.subarray(h,h.length-NVariables.adaa.length,h.length);
			byte rem[]=me.subarray(h,0,h.length-NVariables.adaa.length);
			s.push("\n"+tm.revert(clitic)+" < clitic > ");
			if(me.endswith(rem,NVariables.y))
				rem = me.subarray(rem,0,rem.length-1);
			s.push(tm.revert(rem));
		}
		return s;
	}
/**
  *  This to remove Ae
  */

	public Stack checkAe(Stack s,byte [] h)
	{
		if((h.length > NVariables.ae.length)&&me.endswith(h,NVariables.ae))
		{
			byte clitic[]=me.subarray(h,h.length-1,h.length);
			byte rem[]=me.subarray(h,0,h.length-1);
			s.push("\n"+tm.revert(clitic)+" < clitic > ");
			if(me.endswith(rem,NVariables.y)||me.endswith(rem,NVariables.iv))
				rem = me.subarray(rem,0,rem.length-1);
			if(me.endswith(rem,NVariables.r1))
				rem = me.addarray(rem,NVariables.u);
			else if(me.endswith(rem,NVariables.th)||me.endswith(rem,NVariables.k))
				rem = me.addarray(rem,NVariables.u);
			s.push(tm.revert(rem));
		}
		return s;
	}
/**
  *  This to remove kooda
  */

	public Stack checkKooda(Stack s,byte [] h)
	{
		if((h.length > NVariables.kooda.length)&&me.endswith(h,NVariables.kooda))
		{
			byte clitic[]=me.subarray(h,h.length-NVariables.kooda.length,h.length);
			byte rem[]=me.subarray(h,0,h.length-NVariables.kooda.length);
			s.push("\n"+tm.revert(clitic)+" < clitic > ");
			if(me.endswith(rem,NVariables.k))
				rem = me.subarray(rem,0,rem.length-1);
			s.push(tm.revert(rem));
		}
		return s;
	}
/**
  *  This to remove um
  */

	public Stack checkUm(Stack s,byte [] h)
	{
		if(me.endswith(h,NVariables.um))
		{
			byte clitic[]=me.subarray(h,h.length-2,h.length);
			byte rem[]=me.subarray(h,0,h.length-2);
			s.push("\n"+tm.revert(clitic)+" < clitic > ");
			if(me.endswith(rem,NVariables.y)||me.endswith(rem,NVariables.iv))
				rem = me.subarray(rem,0,rem.length-1);
			if(me.endswith(rem,NVariables.t)||me.endswith(rem,NVariables.th)||me.endswith(rem,NVariables.kk))
				rem = me.addarray(rem,NVariables.u);
			s.push(tm.revert(rem));
		}
		return s;
	}

	public Stack checkAa(Stack s,byte [] h)
	{
		if((h.length > NVariables.aa.length)&&me.endswith(h,NVariables.aa))
		{
			byte clitic[]=me.subarray(h,h.length-1,h.length);
			byte rem[]=me.subarray(h,0,h.length-1);
			s.push("\n"+tm.revert(clitic)+" < clitic > ");
			if(me.endswith(rem,NVariables.y)||me.endswith(rem,NVariables.iv))
				rem = me.subarray(rem,0,rem.length-1);
			if(me.endswith(rem,NVariables.r1))
				rem = me.addarray(rem,NVariables.u);
			s.push(tm.revert(rem));
		}
		return s;
	}
}
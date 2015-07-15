import java.util.*;

public class Cases
{
	TabMethods tm = new TabMethods();
	ByteMeth me = new ByteMeth();

	public Stack check_ukkaaga(Stack s,byte h1[])
	{
			byte r[]=me.subarray(h1,h1.length-NVariables.ukkaaga.length,h1.length);
			byte a[]=me.subarray(h1,0,(h1.length-NVariables.ukkaaga.length));
			s.push("\n"+tm.revert(r)+" < case >");
			s.push("\n"+tm.revert(a));
			return s;
	}

	public Stack check_ilirunthu(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-NVariables.ilirunthu.length,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.ilirunthu.length));
		s.push("\n"+tm.revert(r)+" < case >");
		if(me.endswith(a,NVariables.y))
			a = me.subarray(a,0,a.length-1);
		if(me.endswith(a,VVariables.pp))
			a = me.addarray(a,NVariables.u);
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack check_itamirunthu(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-NVariables.itamirunthu.length,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.itamirunthu.length));
		s.push("\n"+tm.revert(r)+" < case >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack checkitam(Stack s,byte str1[])
	{
		byte r[]=me.subarray(str1,str1.length-4,str1.length);
		byte a[]=me.subarray(str1,0,(str1.length-NVariables.itam.length));
		s.push("\n"+tm.revert(r)+" < case >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack checkin(Stack s,byte str2[])
	{
		byte r[]=me.subarray(str2,str2.length-2,str2.length);
		byte a[]=me.subarray(str2,0,(str2.length-NVariables.il.length));
		s.push("\n"+tm.revert(r)+" < case >");
		if(me.endswith(a,NVariables.y))
			a = me.subarray(a,0,a.length-1);
		s.push("\n"+tm.revert(a));
		return s;
	}

	public  Stack check_ai(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-1,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.ai.length));
		s.push("\n"+tm.revert(r)+" < case >");
		if(me.endswith(a,NVariables.nn) || me.endswith(a,NVariables.iv)|| me.endswith(a,NVariables.nn1)|| me.endswith(a,NVariables.y))
			a = me.subarray(a,0,a.length-1);
		if(me.endswith(a,NVariables.ch)||me.endswith(a,NVariables.ch)||me.endswith(a,NVariables.pp)||me.endswith(a,NVariables.rr1)||( me.endswith(a,NVariables.th)&& !me.endswith(a,NVariables.thth)))
			a = me.addarray(a,NVariables.u);
		s.push("\n"+tm.revert(a));
		return s;
	}

	public  Stack checkil(Stack s,byte str2[])
	{
		byte r[]=me.subarray(str2,str2.length-2,str2.length);
		byte a[]=me.subarray(str2,0,(str2.length-NVariables.il.length));
		s.push("\n"+tm.revert(r)+" < case >");
		if(!me.endswith(a,NVariables.thth) && me.endswith(a,NVariables.th)||me.endswith(a,NVariables.k)||me.endswith(a,NVariables.t))
			a = me.addarray(a,NVariables.u);
		else if(me.endswith(a,NVariables.y) || me.endswith(a,NVariables.n1)|| me.endswith(a,NVariables.iv))
			a = me.subarray(a,0,a.length-1);
		else if(me.endswith(a,NVariables.rr1))
		{
			a = me.subarray(a,0,a.length-1);
			a = me.addarray(a,NVariables.u);
		}
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack check_utan(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-4,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.utan.length));
		if(me.endswith(a,NVariables.nn)||me.endswith(a,NVariables.y)||me.endswith(a,NVariables.iv))
			a = me.subarray(a,0,a.length-1);
		if(me.endswith(a,NVariables.p))
			a = me.addarray(a,NVariables.u);
		s.push("\n"+tm.revert(r)+" < case >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack check_athu(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-3,h1.length);
		byte a[]=me.subarray(h1,0,h1.length-NVariables.athu.length);
		if(me.endswith(a,NVariables.iv)||me.endswith(a,NVariables.nn))
			a = me.subarray(a,0,a.length-1);
		s.push("\n"+tm.revert(r)+" < case >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack check_odu(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-3,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.odu.length));
		s.push("\n"+tm.revert(r)+" < case >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack check_kkaaga(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-NVariables.kkaaga.length,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.kkaaga.length));
		s.push("\n"+tm.revert(r)+" < case >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack check_udaya(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-5,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.udaya.length));
		s.push("\n"+tm.revert(r)+" < case >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack check_akku(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-NVariables.akku.length,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.akku.length));
		s.push("\n"+tm.revert(r)+" < case >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack check_akka(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-3,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.akka.length));
		if(me.endswith(a,NVariables.y))
			a = me.subarray(a,0,a.length-1);
		else if(me.endswith(a,NVariables.iv) ||me.endswith(a,NVariables.k)|| me.endswith(a,NVariables.t)|| me.endswith(a,NVariables.th))
			a = me.addarray(a,NVariables.u);
		s.push("\n"+tm.revert(r)+" < adverbial suffix >");
		s.push("\n"+tm.revert(a));
		return s;
	}
	public Stack check_aana(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-NVariables.aana.length,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.aana.length));
		if(me.endswith(a,NVariables.y))
			a = me.subarray(a,0,a.length-1);
		else if(me.endswith(a,NVariables.iv) ||me.endswith(a,NVariables.k)|| me.endswith(a,NVariables.t)|| me.endswith(a,NVariables.th))
			a = me.addarray(a,NVariables.u);
		s.push("\n"+tm.revert(r)+" < adjectival suffix >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack check_kku(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-3,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.kku.length));
		s.push("\n"+tm.revert(r)+" < case >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack check_ukku(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-4,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.ukku.length));
		if(me.endswith(a,NVariables.p)||me.endswith(a,NVariables.ch)||me.endswith(a,NVariables.th))
			a = me.addarray(a,NVariables.u);
		s.push("\n"+tm.revert(NVariables.ukku)+" < case >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack check_irku(Stack s,byte h1[])
	{
		byte r[]=me.subarray(h1,h1.length-4,h1.length);
		byte a[]=me.subarray(h1,0,(h1.length-NVariables.irku.length));
		s.push("\n"+tm.revert(r)+" < case >");
		s.push("\n"+tm.revert(a));
		return s;
	}

	public Stack remove_aal(Stack s,byte[] topByte)
	{
			byte clitic[]=me.subarray(topByte,topByte.length-VVariables.aal.length,topByte.length);
			byte rem[] = me.subarray(topByte,0,topByte.length-VVariables.aal.length);
			s.push("\n"+tm.revert(clitic)+" < case > ");
			s.push(tm.revert(rem));
			return s;
	}
}

import java.util.Stack;
public class VCheckInf
{
	ByteMeth bm = new ByteMeth();
	TabMethods tm = new TabMethods();
	VSearch search = new VSearch();

	public Stack removeInf(Stack s)
	{
		byte[] topElement = tm.convert(s.pop().toString().trim());
		if(search.dicSearch(tm.revert(topElement),"cat7.txt"))
		{
			byte[] removed = bm.subarray(topElement,topElement.length-1,topElement.length);
			byte[] yetToRemove = bm.addarray(bm.subarray(topElement,0,topElement.length-1),VVariables.u);
			s.push("\n"+tm.revert(removed)+" < infinitive > ");
			s.push(tm.revert(yetToRemove));
			return s;
		}

		if(bm.endswith(topElement,VVariables.kka))
		{
			byte[] removed = bm.subarray(topElement,topElement.length-3,topElement.length);
			byte[] yetToRemove = bm.subarray(topElement,0,topElement.length-3);
			s.push("\n"+tm.revert(removed)+" < infinitive > ");
			s.push(tm.revert(yetToRemove));
		}
		else if(bm.endswith(topElement,VVariables.ka))
		{
			byte[] removed = bm.subarray(topElement,topElement.length-2,topElement.length);
			byte[] yetToRemove = bm.subarray(topElement,0,topElement.length-2);
			s.push("\n"+tm.revert(removed)+" < infinitive > ");
			if(bm.endswith(yetToRemove,VVariables.t))// ketka --->  kel
				yetToRemove = bm.addarray(bm.subarray(yetToRemove,0,yetToRemove.length-1),VVariables.l1);
			if(bm.endswith(yetToRemove,VVariables.r1))
				yetToRemove = bm.addarray(bm.subarray(yetToRemove,0,yetToRemove.length-1),VVariables.l);
			s.push(tm.revert(yetToRemove));
		}
		else if(bm.endswith(topElement,VVariables.a))
		{
			byte[] removed = bm.subarray(topElement,topElement.length-1,topElement.length);
			byte[] yetToRemove = bm.subarray(topElement,0,topElement.length-1);

/**
  * If the word is endswith y then remove the y
  *
  */
			if(bm.endswith(yetToRemove,VVariables.y))
				yetToRemove = bm.subarray(yetToRemove,0,yetToRemove.length-1);
/**
  * If the word ends with zh and not in "zh.txt" file OR  \\ vazhu,  makizha
  * If the word ends with t   \\aattu
  * Then add u at  the end \\ peru
  */

			else if((bm.endswith(yetToRemove,VVariables.zh) && !search.dicSearch(tm.revert(yetToRemove),"zh.txt")) || bm.endswith(yetToRemove,VVariables.t) || bm.endswith(yetToRemove,VVariables.r1))
				yetToRemove = bm.addarray(yetToRemove,VVariables.u);
/**
  * If the word is ends with NN  OR If the word ends with ll then remove the N
  */
  			else if(bm.endswith(yetToRemove,VVariables.n1) || bm.endswith(yetToRemove,VVariables.ll))
				yetToRemove = bm.subarray(yetToRemove,0,yetToRemove.length-1);
/**
  *  If the word ends with thar and var take the first byte and add one A
  */		else if(bm.isequal(yetToRemove,VVariables.var) || bm.isequal(yetToRemove,VVariables.thar))
				yetToRemove = bm.addarray(bm.subarray(yetToRemove,0,1),VVariables.aa);

			s.push("\n"+tm.revert(removed)+" < infinitive > ");
			s.push(tm.revert(yetToRemove));
		}
		else
		{
				s.push(tm.revert(topElement));
		}
		return s;
	}
}
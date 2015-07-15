import java.util.Stack;

public class VMiddleGender
{
	public Stack checkMiddleGender(Stack s)
	{
		TabMethods tm = new TabMethods();
		ByteMeth me = new ByteMeth();
		byte[] topElement = tm.convert(s.peek().toString().trim());
	/*if(me.endswith(topElement,VVariables.avan) || (me.endswith(topElement,VVariables.aval)
			|| (me.endswith(topElement,VVariables.adhu)) || (me.endswith(topElement,VVariables.avar)||(me.endswith(topElement,VVariables.avargal))*/
		if(me.endswith(topElement,VVariables.avan))
		{
			s.pop();
			byte[] remove = me.subarray(topElement,topElement.length-VVariables.avan.length,topElement.length);
			byte[] remain = me.subarray(topElement,0,topElement.length-VVariables.avan.length);
			s.push("\n"+tm.revert(remove)+" < avan - verbalnoun > ");
			s.push("\n"+tm.revert(remain));
		}
		else if(me.endswith(topElement,VVariables.aval1))
		{
			s.pop();
			byte[] remove = me.subarray(topElement,topElement.length-VVariables.aval1.length,topElement.length);
			byte[] remain = me.subarray(topElement,0,topElement.length-VVariables.aval1.length);
			s.push("\n"+tm.revert(remove)+" < aval - verbalnoun > ");
			s.push("\n"+tm.revert(remain));
		}
		else if(me.endswith(topElement,VVariables.avarkal))
		{
			s.pop();
			byte[] remove = me.subarray(topElement,topElement.length-VVariables.avarkal.length,topElement.length);
			byte[] remain = me.subarray(topElement,0,topElement.length-VVariables.avarkal.length);
			s.push("\n"+tm.revert(remove)+" < avargal - verbalnoun > ");
			s.push("\n"+tm.revert(remain));
		}
		else if(me.endswith(topElement,VVariables.avar))
		{
			s.pop();
			byte[] remove = me.subarray(topElement,topElement.length-VVariables.avar.length,topElement.length);
			byte[] remain = me.subarray(topElement,0,topElement.length-VVariables.avar.length);
			s.push("\n"+tm.revert(remove)+" < avar - verbalnoun > ");
			s.push("\n"+tm.revert(remain));
		}

		return s;
	}
}
/*
PROJECT : POONGKUZHALI

AUTHOR  : S. SOWMYA

OWNER   : RCILTS

PURPOSE : This program gives the node class and its functions

*/

class node
{
	int wno;
	String word;
	int prio;
	void add(int wnum,String w,int p)
	{
		wno=wnum;
		word=w;
		prio=p;
	}
	int getwno()
	{
		return wno;
	}
	String getword()
	{
		return word;
	}
	int getprio()
	{
		return prio;
	}
	void print()
	{
		System.out.print("Wordno:"+wno+" "+"Word:"+word+" "+"Priority:"+prio);
		System.out.println("");
	}
}

/**
* @(#)WorkSheet.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.event.ListSelectionEvent.*;
import java.lang.*;
import java.lang.String;
import java.text.*;

/**It is the base class for the Main class. It initiates the
data and row for the worksheet as table.It sets the model
for table editing.Methods are implemented to get the
selected row and column values for using expressions
and charts.It implements Serializable interface.*/
public class WorkSheet implements Serializable
{
	/**Model to be set for the table as worksheet.*/
	TableModel tm;

	/**User defined model to be set for the table for
	multi font enabling.*/
	public AttributiveCellTableModel etm;
	/**The object is used to display the font and color for the
	table data.*/
	public DefaultCellAttribute dca;
	/**Model is used to handle columns in the worksheet.*/
	TableColumnModel cm;
	/**Model is used to handle the column heading in the worksheet.*/
	TableColumnModel rowHeaderModel;
	/**Model to get selected row and columns from the table.*/
	ListSelectionModel rowSelModel;
	/**Model to get selected row and columns from the table.*/
	ListSelectionModel colSelModel;
	/**Double value is used to store the value after addion is done.*/
	double sumValue = 0;
	/**To store the average value for the selected values.*/
	double average=0;
	/**Sets the font for the models.*/
	Font curFont;
	/**Store the maximum value from the table.*/
	double MAX=0;
	/**Store the minimum value from the table.*/
	double MIN=0;
	/**Store the data used for sum or addition from the table.*/
	double[] sumData;
	/**Store the data used for sum or addition from the table for temp purpose..*/
	double[] sData;

	/**Store the count value used count the selected row/cols from the table.*/
	public int count=0;
	/**Store the total no. of rows for creating the table.*/
	int total_Rows=50;  // no of rows

	/**To store the column headings in Tamil for the table as sheet.*/
	public String[] headers1={"","и(0)","й(1)","к(2)","л(3)","м(4)","н(5)",
							"о(6)","п(7)","р(8)","с(9)","т(10)","у(11)",
							"ф(12)","х(13)","ц(14)","ч(15)","ш(16)","щ(17)"};

   /**To display the current cell position in the input field.*/
	public String[] headerOnly={"","и","й","к","л","м","н",
							"о","п","р","с","т","у",
							"ф","х","ц","ч","ш","щ"};

	public String[] headers={"","и","й","к","л","м","н",
							"о","п","р","с","т","у",
							"ф","х","ц","ч","ш","щ"};

	/**The key words used for expressions stored in the string array.*/
	String[] fnKeyWords={"√мҐі",
					"внҐн§иҐђи",
					"№о§ирҐрмҐксҐ",
					"∞ђшпҐорҐрмҐксҐ",
					"ку£ку§" };     //SUM,COUNT,MAX,MIN

	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam mapp;
	ResourceBundle wordBundle;
	/**To store the data used for the table cells. */
	Object[][] data=new Object[total_Rows][headers.length];
	/**Sets the font for each cell in the table. */
	Font[][] fonts=new Font[total_Rows][headers.length];
	/**Sets the back color for each cell in the table. */
	Color[][] backclr=new Color[total_Rows][headers.length];
	/**Sets the fore color for each cell in the table. */
	Color[][] foreclr=new Color[total_Rows][headers.length];
	/**Store the formula used for expressions. */
	String[][] formula=new String[total_Rows][headers.length];
	/**Store the selected row in the integer array. */
	int[] rowSelArray;
	/**Store the selected column in the integer array. */
	int[] colSelArray;
	/**Store the headers length as integer. */
	int headerslen=headers.length;// no of columns
	/**Store the data to be used for charts in 2D string array. */
	String[][] chartData;
	/**Store the data used for copy the data from the cell to cell. */
	String[][] copyData;
	boolean chkVal=false;

	/**Constructor used with the main class object to initiate the
	models for worksheet.*/
	public WorkSheet(Chathurangam main)
	{
		mapp=main;
		initModels();
	}

	/**Constructor used to init the models with no. of rows for
	create the worksheet.*/
	public WorkSheet(int rows)
	{
		total_Rows=rows;
		initModels();

	}
	/**Initialises all the model required for the worksheet
	It initialises data and formula used for the worksheet*/
	void initModels()
	{
		curFont=new Font("TABKural",0,12);
		for(int i=0;i<total_Rows;i++)
		{
			for(int j=1;j<headers.length;j++)
			{
				data[i][j]="";
				formula[i][j]="";
			}
		}

		tm=new AttributiveCellTableModel(headers,data,mapp,total_Rows);
		etm=(AttributiveCellTableModel)tm;

		cm=new DefaultTableColumnModel()
		{
			boolean first=true;
			public void addColumn(TableColumn tc)
			{

				if(first)
				{
					first=false;
					return;
				}
				tc.setMinWidth(60);
				super.addColumn(tc);
			}
		};
		rowHeaderModel=new DefaultTableColumnModel()
		{
			boolean first=true;
			public void addColumn(TableColumn tc)
			{
				if(first)
				{
					tc.setMaxWidth(50);
					super.addColumn(tc);
					first=false;
				}
			}
		};

	}

	/**
	* the method to check the header type
	@return int if header is not character type returns -1
	@param c character to check
	*/
	int isHeader(char c)
	{
		char test;
		for(int i=1;i<headers.length;i++)
		{
			test=headers[i].charAt(0);
			if(test==c)
				return i;
		}
		return -1;

	}

	/**
	* the method to check the character is operator
	@return int if the character is not an operator returns -1
	@param c the character to check for an operator
	*/
	//This fn is to check if a char is an operator
	int isOperator(char c)
	{
		char[] operator={'=','+','-','*','/','(',')','^',' '};
		for(int i=0;i<operator.length;i++)
		{

			if(c==operator[i])
			{
				return i;
			}
		}
		return -1;
	}

	/**
	* the method to check the string is operator
	@return int if the string is not an operator returns -1
	@param opr the string to check for an operator
	*/
	//This fn is to check if a string is an operator
	int isOperator(String opr)
	{
		String[] operator={"=","+","-","*","/","(",")","^"," "};
		for(int i=0;i<operator.length;i++)
		{

			if(opr.equals(operator[i]))
			{
				return i;
			}
		}
		return -1;
	}

	//This fn converts the formula into an expn which contains fully of
	//numbers.It uses isHeader,isOperator
	/**
	* the method to convert the data from formula
	@return String the converted data
	@param tformula the formula to be converted of type String
	*/
	String dataConvert(String tformula)
	{
		int col,row;
		char c,curr;

		StringBuffer strrow=new StringBuffer();
		StringBuffer finalstr=new StringBuffer();

		boolean equalflag=false;
		for(int i=0;i<tformula.length();)
		{
			curr=tformula.charAt(i++);
			if((col=isHeader(curr))!=-1)
			{
				strrow=new StringBuffer();
				while((isOperator(c=tformula.charAt(i)))==-1)
				{

					strrow.append(c);
					i++;
					if(i==tformula.length())
						break;
				}
				row=Integer.parseInt(strrow.toString());

				if(equalflag==false)
					finalstr.append(strrow+"t"+Integer.toString(col));
				else
				{
					if(data[row][col]==" ")
						finalstr.append(0);
					else
						finalstr.append(data[row][col]);
				}

			}
			else
			{
				if(curr!=' ')
					finalstr.append(curr);
				if(curr=='=')
				{
					equalflag=true;
				}
			}

		}//end of for loop

		return finalstr.toString();

	}
	public boolean checkOperatorValid(String sttr)
	{
		wordBundle = mapp.getWordBundle();
		Object[] options = {wordBundle.getString("yes")	};
		String InvalidDataValid = wordBundle.getString("InvalidDataValid");
		String messageTitle = wordBundle.getString("messageTitle");

		int reply = mapp.showDialog(mapp,"InvalidDataValid",
		JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
		options, 0);

		return false;
	}

	/**
	* the method to give the precedence of the operators
	@return boolean
	@param s1 the first string for the formula
	@param s2 the second string for the formula
	*/
	//This fn gives the precedence of oprs s1,s2
		boolean prcd(String s1,String s2)
	{
		if(s1.equals(null)||s2.equals(" "))
			return false;
//		if(s1.equals("^"))
//			return true;
		if(s1.equals("/"))
			return true;
		if(s1.equals("*")&&!s2.equals("/"))
			return true;
		if(s1.equals("+")&&s2.equals("-"))
			return true;
		if(!s1.equals("(")&&s2.equals(")"))
			return true;
		if(s1.equals("(")&&s2.equals(")"))
			return false;

		if(s1.equals("0"))
			return false;
		return false;
	}

	/**
	* the method to converts  the formula into a postfix expression
	@return vector
	@param formula the formula to be converted of string type
	*/
	//This fn converts the formula into a postfix expn
	Vector evaluatePostfix(String formula)
	{

		String token=new String();
		StringTokenizer formulaTokenizer = new StringTokenizer(formula,"+-*/()^ ",true);
		Stack opstk=new Stack();
		Vector postfix=new Vector();
		int ptr=0,opnd;
		String topsymb=new String();
		String temp=new String("0");
		while(formulaTokenizer.hasMoreTokens())
		{
			if(formulaTokenizer.hasMoreTokens())
			{
				token=formulaTokenizer.nextToken();

			}
			else
				break;
			if(isOperator(token)==-1)
			{
				if(!token.equals(" "))
					postfix.addElement(token);
			}
			else
			{
				if(!opstk.empty())
					temp=opstk.peek().toString();
				while(!opstk.empty()&&prcd(temp,token))
				{
					topsymb=opstk.pop().toString();
					if(!topsymb.equals(" "))
						postfix.addElement(topsymb);
					if(!opstk.empty())
						temp=opstk.peek().toString();

				}
				if(opstk.empty()||!token.equals(")"))
				{
					if(!token.equals(" "))
						opstk.push(token);

				}
				else
					topsymb=opstk.pop().toString();

			}
		}
		while(!opstk.empty())
		{

			topsymb=opstk.pop().toString();
			if(!topsymb.equals(" "))
				postfix.addElement(topsymb);


		}
		return postfix;

	}

	/**
	* the method to get the value of operand1 and operand2
	@return double
	@param sym the string value
	@param opnd1 the first operand value of type double
	@param opnd2 the second operand value of type double
	*/
	//This fn returns the value opnd1 sym opnd2
   double oper(String sym,double opnd1,double opnd2)
	{
		char c=sym.charAt(0);
		switch(c)
		{
			case '+':return opnd1+opnd2;
			case '-':return opnd1-opnd2;
			case '*':return opnd1*opnd2;
			case '/':return opnd1/opnd2;
			case '^':return Math.pow(opnd1,opnd2);

		}
		return 0;
	}

	/**
	* the method to evalulate the value from the expression
	@param postfix the vector type postfix
	@return double the evaluated value
	*/
	//This fn caluates the postfix expn.It uses isOperator,oper
	double evaluateValue(Vector postfix)
	{
		Stack opndstk=new Stack();
		double opnd2,opnd1,value;

		for(int i=0;i<postfix.size();i++)
		{
			if(isOperator(postfix.elementAt(i).toString())==-1)
				opndstk.push(postfix.elementAt(i));
			else
			{
				opnd2=Double.parseDouble(opndstk.pop().toString());
				opnd1=Double.parseDouble(opndstk.pop().toString());
				value=oper(postfix.elementAt(i).toString(),opnd1,opnd2);
				opndstk.push(Double.toString(value));
			}

		}
		return Double.parseDouble(opndstk.pop().toString());
	}

	//This fn checks whether there are any more fns in the formula from
	//the specified index.
	/**
	*This fn checks whether there are any more fns in the formula from
	*the specified index.
	@return int returns -1 if no more functions are available
	@param index the position  of formula
	@param fnFormula the string value
	*/
	int moreFns(int index,String fnFormula)
	{
		int n;
		for(int i=0;i<fnKeyWords.length;i++)
		{
			if((n=fnFormula.indexOf(fnKeyWords[i],index))!=-1)
			{
				return n;
			}
		}
		return -1;

	}

	/**
	* the method to evaluate the function sum
	@param fnSubFormula the sub formula from the function
	@return String
	*/
	String evalFnSum(String fnSubFormula)
	{
		StringBuffer expdFormula=new StringBuffer();
		StringBuffer strrow=new StringBuffer();

		int startIndex,endIndex,midIndex;
		int colStart,colEnd,rowStart,rowEnd;
		if((midIndex=fnSubFormula.indexOf(':'))==-1)
			return fnSubFormula;
		colStart=isHeader(fnSubFormula.charAt(0));
		colEnd=isHeader(fnSubFormula.charAt(midIndex+1));

		strrow=new StringBuffer();
		for(int i=1;i<midIndex;i++)
			strrow.append(fnSubFormula.charAt(i));
		rowStart=Integer.parseInt(strrow.toString());

		strrow=new StringBuffer();
		for(int i=midIndex+2;i<fnSubFormula.length();i++)
			strrow.append(fnSubFormula.charAt(i));
		rowEnd=Integer.parseInt(strrow.toString());

		for(int i=colStart;i<=colEnd;i++)
		{
			for(int j=rowStart;j<=rowEnd;j++)
			{
				expdFormula.append(headers[i]+Integer.toString(j));
				if(i!=colEnd+1)
					expdFormula.append("+");
			}

		}
		expdFormula.append("0");

		return expdFormula.toString();
	}

	/**
	* the method to evaluate count of values
	@param fnSubFormula the formula to count selected values
	@return String
	*/
	String evalFnCount(String fnSubFormula)
	{
		StringBuffer expdFormula=new StringBuffer();
		StringBuffer strrow=new StringBuffer();

		int startIndex,endIndex,midIndex;
		int colStart,colEnd,rowStart,rowEnd;
		if((midIndex=fnSubFormula.indexOf(':'))==-1)
			return fnSubFormula;
		colStart=isHeader(fnSubFormula.charAt(0));
		colEnd=isHeader(fnSubFormula.charAt(midIndex+1));

		strrow=new StringBuffer();
		for(int i=1;i<midIndex;i++)
			strrow.append(fnSubFormula.charAt(i));
		rowStart=Integer.parseInt(strrow.toString());

		strrow=new StringBuffer();
		for(int i=midIndex+2;i<fnSubFormula.length();i++)
			strrow.append(fnSubFormula.charAt(i));
		rowEnd=Integer.parseInt(strrow.toString());
		int count=0;
		for(int i=colStart;i<=colEnd;i++)
		{
			for(int j=rowStart;j<=rowEnd;j++)
			{
				count++;
			}

		}
		return Integer.toString(count);

	}

	/**
	* the method to evaluate maximum of values
	@param fnSubFormula the formula to maximum selected values
	@return String
	*/
	String evalFnMax(String fnSubFormula)
	{
		StringBuffer expdFormula=new StringBuffer();
		StringBuffer strrow=new StringBuffer();
		int startIndex,endIndex,midIndex;
		int colStart,colEnd,rowStart,rowEnd;
		if((midIndex=fnSubFormula.indexOf(':'))==-1)
			return fnSubFormula;
		colStart=isHeader(fnSubFormula.charAt(0));
		colEnd=isHeader(fnSubFormula.charAt(midIndex+1));

		strrow=new StringBuffer();
		for(int i=1;i<midIndex;i++)
			strrow.append(fnSubFormula.charAt(i));
		rowStart=Integer.parseInt(strrow.toString());

		strrow=new StringBuffer();
		for(int i=midIndex+2;i<fnSubFormula.length();i++)
			strrow.append(fnSubFormula.charAt(i));
		rowEnd = Integer.parseInt(strrow.toString());
		int res = Integer.parseInt(data[rowStart][colStart].toString());
		String strCurr=new String();
		int curr;
		for(int i=rowStart;i<=rowEnd;i++)
		{
			for(int j=colStart;j<=colEnd;j++)
			{
				strCurr=data[i][j].toString();
				System.out.println("BBalal "+strCurr);
				curr=Integer.parseInt(strCurr);
				if(curr>res)
					res=curr;
			}

		}

		return Integer.toString(res);

	}

	/**
	* the method to evaluate minimum  of values
	@param fnSubFormula the formula to minimum selected values
	@return String
	*/
	String evalFnMin(String fnSubFormula)
	{
		/*System.out.println("fnsbform "+fnSubFormula);
		mapp.FindMinimum(fnSubFormula);
		return "";*/
		StringBuffer expdFormula=new StringBuffer();
		StringBuffer strrow=new StringBuffer();
		int startIndex,endIndex,midIndex;
		int colStart,colEnd,rowStart,rowEnd;
		if((midIndex=fnSubFormula.indexOf(':'))==-1)
			return fnSubFormula;
		colStart=isHeader(fnSubFormula.charAt(0));
		colEnd=isHeader(fnSubFormula.charAt(midIndex+1));

		strrow=new StringBuffer();
		for(int i=1;i<midIndex;i++)
			strrow.append(fnSubFormula.charAt(i));
		rowStart=Integer.parseInt(strrow.toString());

		strrow=new StringBuffer();
		for(int i=midIndex+2;i<fnSubFormula.length();i++)
			strrow.append(fnSubFormula.charAt(i));
		rowEnd = Integer.parseInt(strrow.toString());
		int res = Integer.parseInt(data[rowStart][colStart].toString());
		String strCurr=new String();
		int curr;
		for(int i=rowStart;i<=rowEnd;i++)
		{
			for(int j=colStart;j<=colEnd;j++)
			{
				strCurr=data[i][j].toString();
				System.out.println("BBalal mini "+strCurr);
				curr=Integer.parseInt(strCurr);
				if(curr<res)
					res=curr;
			}

		}

		return Integer.toString(res);
	}
/**&&&&&&&&&&&new addition-start*/
	/**
	* the method to evaluate average  of values
	@param fnSubFormula the formula to minimum selected values
	@return String
	*/
	String evalFnAve(String fnSubFormula)
	{
		StringBuffer expdFormula=new StringBuffer();
		StringBuffer strrow=new StringBuffer();
		int startIndex,endIndex,midIndex;
		int colStart,colEnd,rowStart,rowEnd;
		if((midIndex=fnSubFormula.indexOf(':'))==-1)
			return fnSubFormula;
		colStart=isHeader(fnSubFormula.charAt(0));
		colEnd=isHeader(fnSubFormula.charAt(midIndex+1));

		strrow=new StringBuffer();
		for(int i=1;i<midIndex;i++)
			strrow.append(fnSubFormula.charAt(i));
		rowStart=Integer.parseInt(strrow.toString());

		strrow=new StringBuffer();
		for(int i=midIndex+2;i<fnSubFormula.length();i++)
			strrow.append(fnSubFormula.charAt(i));
		rowEnd = Integer.parseInt(strrow.toString());
		int res = Integer.parseInt(data[rowStart][colStart].toString());
		String strCurr=new String();
		double curr=0;
		double aveVal=0;
		double finalAverage=0.0;
		Double netAvg;
		NumberFormat numFor=NumberFormat.getNumberInstance();
		numFor.setMinimumFractionDigits(2);
		int count=0;
		for(int i=rowStart;i<=rowEnd;i++)
		{
			for(int j=colStart;j<=colEnd;j++)
			{
				strCurr=data[i][j].toString();
				curr=Double.parseDouble(strCurr);
				aveVal+=curr;
				//if(curr<res)
				//	res=curr;
			}
		count++;
		}
		finalAverage=aveVal/count;
		netAvg=new Double(numFor.format(finalAverage));
		return Double.toString(netAvg.doubleValue());
	}

/**&&&&&&&new add --end*/


	/**This fn evaluates the formula.It first retrieves the parameters of
	*the function and then sends the parameters acc to the function type
	@return string
	@param fnIndex of type integer
	@param index of type integer
	@param fnFormula of type String
	*/
	String substituteFormula(int fnIndex,int index,String fnFormula)
	{
		String temp=new String();
		StringBuffer finalFormulaBuf=new StringBuffer();
		int startIndex,endIndex;
		startIndex=index+fnKeyWords[fnIndex].length()+1;
		endIndex=fnFormula.indexOf(')',startIndex);
		finalFormulaBuf.append(fnFormula.substring(0,index));
		finalFormulaBuf.append('(');
		temp=fnFormula.substring(startIndex,endIndex);
		switch(fnIndex)
		{
			case 0:
				temp=evalFnSum(temp);
				break;
			case 1:
				temp=evalFnCount(temp);
				break;
			case 2:
				temp=evalFnMax(temp);
				break;
			case 3:
				temp=evalFnMin(temp);
				break;
			case 4:
				temp=evalFnAve(temp);
				break;

		}

		finalFormulaBuf.append(temp);
		finalFormulaBuf.append(')');
		finalFormulaBuf.append(fnFormula.substring(endIndex+1,
		fnFormula.length()));

		return finalFormulaBuf.toString();

	}

	/**This fn returns the id of the function to be evaluated
	@return int
	@param index of type integer
	@param fnFormula of type String
	*/
	//This fn returns the id of the function to be evaluated
	int getFnIndex(int index,String fnFormula)
	{
		String temp=new String();
		StringBuffer sb_form=new StringBuffer(fnFormula);
		if(sb_form.indexOf("ку£ку§")!=-1)
		{
			return 4;
		}
		else
		{
				for(int i=0;i<fnKeyWords.length;i++)
				{
					temp=fnFormula.substring(index,index+fnKeyWords[i].length());
					if(temp.equals(fnKeyWords[i]))
					{
						return i;
					}
				}
			return -1;
		}

	}

	/**This fn evaluates the function in the expn and returns the function
	*without the expn.It uses moreFns,getFnIndex,substituteFormula
	@return String the returned functions without expression
	@param fnFormula of type string
	*/
	//This fn evaluates the function in the expn and returns the expn
	//without the expn.It uses moreFns,getFnIndex,substituteFormula
	String evaluateFunctions(String fnFormula)
	{
		int index,prevIndex=0,fnIndex;
		while((index=moreFns(prevIndex,fnFormula))!=-1)
		{
			fnIndex=getFnIndex(index,fnFormula);
			fnFormula=substituteFormula(fnIndex,index,fnFormula);
			prevIndex=index;
		}
		return fnFormula;
	}

	/**This fn evaluates the expn entered in the input area. It Uses
	*evaluateFunctions,dataConvert,evaluatePostfix,evaluateValue
	@param tamilFormula the string type of the tamil formula
	*/
	//This fn evaluates the expn entered in the input area. It Uses
	//evaluateFunctions,dataConvert,evaluatePostfix,evaluateValue
	public void evaluateFormula(String tamilFormula)
	{
		int row,col,header,headerend;

		try{

				String fnLessFormula=evaluateFunctions(tamilFormula);
				String formula=dataConvert(fnLessFormula);
				header=formula.indexOf('t');
				String strrow=formula.substring(0,header);
				headerend=formula.indexOf('=');
				String strcol=formula.substring(header+1,headerend);
				Vector postfix;
				row=Integer.parseInt(strrow);
				col=Integer.parseInt(strcol);
				this.formula[row][col]=tamilFormula;
				postfix=evaluatePostfix(formula.substring(headerend+1,formula.length()));
				data[row][col]=Double.toString(evaluateValue(postfix));
		}
		catch(StringIndexOutOfBoundsException siobe)
		{
			System.out.println("StringIndsafsdf out of Bound-Worksheet");
			siobe.printStackTrace();
			wordBundle = mapp.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			String InvalidSymbol = wordBundle.getString("InvalidSymbol");
			String messageTitle = wordBundle.getString("messageTitle");
			int select = mapp.showDialog(mapp,"InvalidSymbol",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
		}
		catch(NumberFormatException nfe)
		{
			System.out.println("NUm23094823904ffrormwsd");
			wordBundle = mapp.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			String InvalidDataValid = wordBundle.getString("InvalidDataValid");
			String messageTitle = wordBundle.getString("messageTitle");
			int select = mapp.showDialog(mapp,"InvalidDataValid",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
		}
		catch(EmptyStackException ese)
		{
			System.out.println("EMPTY STACK EXCEPTON --worksheet");
			wordBundle = mapp.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			String InvalidDataValid = wordBundle.getString("InvalidDataValid");
			String messageTitle = wordBundle.getString("messageTitle");
			int select = mapp.showDialog(mapp,"InvalidDataValid",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

			//ese.printStackTrace();
		}
		catch(Exception ee)
		{
			System.out.println("Geddrormwsd");
			ee.printStackTrace();
			wordBundle = mapp.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			String InvalidDataValid = wordBundle.getString("InvalidDataValid");
			String messageTitle = wordBundle.getString("messageTitle");
			int select = mapp.showDialog(mapp,"InvalidDataValid",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

		}
	}


	/**
	* the method is used for cut-copy-paste functions
	@return String[][] to get the data.
	*/
	//The following fn. is used for cut-copy-paste
	public String[][] getData()
	{
		copyData=new String[rowSelArray.length][colSelArray.length];
		int chartDataRow=0,chartDataCol=0;
		int r,c;
		for(int i=0;i<rowSelArray.length;i++,chartDataRow++)
			{
		    	r=rowSelArray[i];
		    	chartDataCol=0;
				for(int j=0;j<colSelArray.length;j++,chartDataCol++)
				{
				c=colSelArray[j];
				copyData[chartDataRow][chartDataCol]=data[r][c+1].toString();
				//System.out.println("copied data"+copyData[i][j]);
				}
			}
		return copyData;
	}


	/**This fn builds the pie chart using the PieChart object
	@param til of type string[]
	@param xval of type string[]
	*/
	//This fn builds the pie chart using the PieChart object
	public void buildPieChart(String [] til,String[] xval,Chathurangam mapp)//String chartType)
	{
		/*my addition*/
		if(rowSelArray.length!=1 && colSelArray.length!=1)
		{
			return;
		}

		try{
		chartData=new String[rowSelArray.length][colSelArray.length];
		int chartDataRow=0,chartDataCol=0;
		int r,c;
		for(int i=0;i<rowSelArray.length;i++,chartDataRow++)
		{
			r=rowSelArray[i];
			chartDataCol=0;
			for(int j=0;j<colSelArray.length;j++,chartDataCol++)
			{
				c=colSelArray[j];
				chartData[chartDataRow][chartDataCol]=data[r][c+1].toString();
			}
		}
		/**new additon for validation --start*/
		double[] chartValue=new double[rowSelArray.length];
		double tempvalue=0.0;
		try
		{
			for(int i=0;i<chartDataRow;i++)
			{
				for(int j=0;j<chartDataCol;j++)
				{
						tempvalue=Double.parseDouble(chartData[i][j]);
				}
				chartValue[i]=tempvalue;
			}
		}
		catch(Exception ee)
		{
				chkVal=false;
		}

		if(checkDataValidity(chartValue))
		/**new additon for validation --end*/
		{
			PieChart piechart=new PieChart(chartData,rowSelArray.length,colSelArray.length,this,til,xval,mapp);
		}
		else
		{
				wordBundle = mapp.getWordBundle();
				Object[] options = {wordBundle.getString("ok")};
				String DataCheck = wordBundle.getString("DataCheck");
				String messageTitle = wordBundle.getString("messageTitle");
				int select = mapp.showDialog(mapp,"DataCheck",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
		}

		}
		catch(ArrayIndexOutOfBoundsException aiobe)
		{
			//System.out.println("Wrong Data 628");
		}
		catch(Exception e)
		{
			//System.out.println("632");
			e.printStackTrace();
		}
	}

	public boolean checkDataValidity(double[] values)
	{
		for(int c=0;c<values.length;c++)
		{
			Double dble=new Double(values[c]);
			if(values[c]==0 || dble.isNaN())
			{
				chkVal=false;
				break;
			}
			else
			{
				chkVal=true;
			}
		}//end of for loop
	return chkVal;
	}

	/**
	* the method to check the bar type
	@return boolean
	*/
	public boolean isBarOk()
	{
		//System.out.println(rowSelArray.length+"===="+colSelArray.length);
		if(rowSelArray.length==0 || colSelArray.length==0)
			return false;
		else if(rowSelArray.length!=1 && colSelArray.length!=1)
			return false;
		return true;
	}

	/**
	* the method to draw bar chart
	@return double[]
	*/
	public double[] buildBarChart()
	{
		double[] sumData=null;
		double tempData;
		int k=0,r,c;

		try
		{
	   		if(rowSelArray.length>colSelArray.length)
	   		{
				sumData=new double[rowSelArray.length];
				c=colSelArray[0];
				for(int i=0;i<rowSelArray.length;i++)
				{
					r=rowSelArray[i];
					if(!(data[r][c+1].toString().equals("")))
					{
						try
						{
							tempData=Double.parseDouble(data[r][c+1].toString());
						}
	   					catch(Exception e)
	   					{
							tempData=0;
							//e.printStackTrace();
						}
	   					sumData[k]=tempData;
					}
					else
						sumData[k]=0;
					k++;
				}
			}
			else
	   		{
				sumData=new double[colSelArray.length];
				r=rowSelArray[0];
				for(int i=0;i<colSelArray.length;i++)
				{
					c=colSelArray[i];

					if(!(data[r][c+1].toString().equals("")))
					{

						try
						{
							tempData=Double.parseDouble(data[r][c+1].toString());
							//System.out.println(tempData);
						}
						catch(Exception e)
						{
							tempData=0;
						}
						sumData[k]=tempData;
					}
					else
						sumData[k]=0;
					k++;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException aiobe)
		{
			System.out.println("Wrong Data714"+aiobe);
		}
		catch(Exception e)
		{
			System.out.println("718");
		}

		return sumData;

	}

	/**Method to build bar chart with double value
	which is selected from the table.
	@return double[] the value returned from the selected cell.*/
	public double[] buildBarChart_doublevalue()
			{
				try{
					chartData=new String[rowSelArray.length][colSelArray.length];
					sData=new double[rowSelArray.length];
					int chartDataRow=0,chartDataCol=0;
					int r,c;
					String strg;
					for(int i=0;i<rowSelArray.length;i++,chartDataRow++)
					{
					r=rowSelArray[i];
					chartDataCol=0;
						for(int j=0;j<colSelArray.length;j++,chartDataCol++)
						{
							c=colSelArray[j];
							chartData[chartDataRow][chartDataCol]=data[r][c+1].toString();
							strg = chartData[chartDataRow][chartDataCol].trim();
							sData[i]=Double.parseDouble(strg);
							//sumValue += sumint;
						}
					}

					}
						catch(ArrayIndexOutOfBoundsException aiobe)
							{
								System.out.println("Wrong Data 790"+aiobe);
							}
						catch(Exception e)
								{
									System.out.println("794 "+e);
						}
						return sData;
	}

	/**
	* To find out minimum and maximum value of selected values
	*/
	public void minMax()
	{
		try{

				chartData=new String[rowSelArray.length][colSelArray.length];
				double[][] sumData=new double[rowSelArray.length][colSelArray.length];
				int chartDataRow=0,chartDataCol=0;
				int r,c;
				boolean first=true;
				r=rowSelArray[0];
				c=colSelArray[0];
				double sumint;
				String strg;
				MAX=MIN=0;
				for(int i=0;i<rowSelArray.length;i++,chartDataRow++)
				{
				r=rowSelArray[i];
				chartDataCol=0;
					for(int j=0;j<colSelArray.length;j++,chartDataCol++)
				 	{
						c=colSelArray[j];
						if(!(data[r][c+1].toString().equals("")))
						{
							chartData[chartDataRow][chartDataCol]=data[r][c+1].toString();
							strg = chartData[chartDataRow][chartDataCol];
							try
							{
								sumint = Double.parseDouble(strg);
								if(first)
								{
									MAX=MIN=sumint;
									first=false;
								}
								else
								{
									if(sumint>MAX)
										MAX=sumint;
									if(sumint<MIN)
										MIN=sumint;
									sumData[i][j]=sumint;
								}
							}
							catch(Exception e)
							{
								sumData[i][j]=0;
							}
						}
						else
							sumData[i][j]=0;
					}
				}

			}
			catch(ArrayIndexOutOfBoundsException aiobe)
			{
				System.out.println("Wrong Data 856 "+aiobe);
			}
			catch(Exception e)
			{
				System.out.println("860 "+e);
				//e.printstacktrace();
			}
	}

	/**
	* the method to count the selected values
	@return int the no. of selected values
	*/
	public int CountSelect()
	{
		chartData=new String[rowSelArray.length][colSelArray.length];
		count=(rowSelArray.length)*(colSelArray.length);
		return count;
	}

	/**
	* the method to the total no. of rows
	@return int the no. of rows
	*/
	public int getMaxRow()
	{
		return total_Rows;
	}

	/**
	* the method to the total no. of columns
	@return int the no. of columns
	*/
	public int getMaxCol()
	{
		return headers.length;
	}

	/**
	* the method to find out the selected values from the current table
	*/
	public void buildChart()
	{
		try
		{
	   		chartData=new String[rowSelArray.length][colSelArray.length];
			double[][] sumData=new double[rowSelArray.length][colSelArray.length];
			int chartDataRow=0,chartDataCol=0;
			int r,c;
			double sumint;
			count=0;
			sumValue=0;
			for(int i=0;i<rowSelArray.length;i++,chartDataRow++)
			{
				r=rowSelArray[i];
				chartDataCol=0;
				for(int j=0;j<colSelArray.length;j++,chartDataCol++)
				{
					c=colSelArray[j];
					if(data[r][c+1].toString().equals(""))
					{
						sumint=0;
					}
					else
					{
						chartData[chartDataRow][chartDataCol]=data[r][c+1].toString();
						try
						{
							count++;
							sumint=Double.parseDouble(chartData[chartDataRow][chartDataCol]);
							//String strg = chartData[chartDataRow][chartDataCol].trim();
						}
						catch(NumberFormatException nfe)
						{
								System.out.println("Numb-Format-Exception&&");
								//e.printStackTrace();
								sumint=0;
						}
						catch(Exception e)
						{
							System.out.println("Other Format Exception&&"+e);
							//e.printStackTrace();
							sumint=0;
						}
	 				}
					sumData[i][j]=sumint;
					sumValue += sumint;
	 			}
			}
			average=(float)(sumValue/count);
			//System.out.println("average = "+average);1
			}
			catch(ArrayIndexOutOfBoundsException aiobe)
			{
				System.out.println("Wrong Data 936");
			}
			catch(Exception e)
			{
				System.out.println("941"+e);
			}

		}
}


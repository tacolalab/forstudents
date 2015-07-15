/**
* @(#)ChartCustom.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**To display the chart customisation window for the
chart creation in the spreadsheet. The values to be selected
from the table and choose the chart type. It provides the
chart options like chart title and axis for drawing the chart.
It extends JFrame and implements action listener interface
for action event.*/
public class ChartCustom extends JFrame implements ActionListener
{
	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();
	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");
	/**To enter the chart title for the chart.*/
	JTextField chartTitle;
	/**The title for the x axis in the chart.*/
	JTextField xTitle;
	/**The title for the y axis in the chart.*/
	JTextField yTitle;
	/**To enter the x axis value for the chart.*/
	JTextField xValue;
	/**Sets the heading as chart label*/
	JLabel chartLabel;
	/**Sets the label heading for x axis.*/
	JLabel xLabel;
	/**Sets the label heading for y axis.*/
	JLabel yLabel;
	/**Sets the label heading for x axis value.*/
	JLabel xValLabel;
	/**Panel used for setting the labels and text fileds.*/
	JPanel jp=new JPanel();
	/**Panel is used to place the buttons in the chart options.*/
	JPanel jp1=new JPanel();
	/**Button to ok the chart with titles and axis.*/
	JButton ok;
	/**Button to cancel the chart with titles and axis.*/
	JButton cancel;
	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam map;
	/**Obejct of the class <code>WorkSheet</code>*/
	WorkSheet ws;
	/**Store the values in double array for which the chart
	to be drawn.*/
	double[] values;
	/**Store the titles for the charts*/
	String titles[];
	/**Stores the x axis value to set the range for the chart.*/
	String xvalues[];
	/**The word bundle is used to list the tamil and english equivalent words
	for setting menu items,buttons and labels in Tamil.*/
	ResourceBundle wordBundle;
	/**Sets the type of the chart to be drawn.
	type 0-pie 1-Vertical bar 2-horizontal bar*/
	int type;
	Font curFont=new Font("TABKural",0,12);
	/**Constructor is invoked to display the chart options.
	It asks for chart title for x axis and y axis along with
	x axis value for the range of the chart.
	@param type of type integer to check pie or
	bar chart(Horizontal /Vetical)
	@param mapp object of the main class Chathurangam
	@param ws object of the WorkSheet class.*/
	public ChartCustom(int type,Chathurangam mapp,WorkSheet ws)
	{
		super("Chart Options");
		map=mapp;
		setIconImage(img);
		this.type=type;
		this.ws=ws;
		titles=new String[3];
		String XAxisHelp=new String(map.getWordBundle().getString("XaxRange"));
		String XAxisEg=new String(map.getWordBundle().getString("Example")+":- 1,1:2,2");
		GridLayout gl,glMain;
		glMain=new GridLayout(2,1);
		if(type!=0)
		{
			gl=new GridLayout(4,2);
			gl.setVgap(5);
		}
		else
		{
			gl=new GridLayout(2,2);
			gl.setVgap(20);
		}
		jp.setLayout(gl);
		Container contents=getContentPane();
		chartLabel=new JLabel("          õ¬óðì î¬ôð¢¹");
		chartLabel.setFont(curFont);
		chartLabel.setForeground(Color.black);
		xLabel=new JLabel("          è¤¬ì Üê¢² î¬ôð¢¹");
		xLabel.setFont(curFont);
		xLabel.setForeground(Color.black);
		yLabel=new JLabel("          àòó¢ Üê¢² î¬ôð¢¹");
		yLabel.setFont(curFont);
		yLabel.setForeground(Color.black);
		xValLabel=new JLabel("          è¤¬ì Üê¢² âô¢¬ô");
		xValLabel.setFont(curFont);
		xValLabel.setForeground(Color.black);
		chartTitle=new JTextField();
		chartTitle.setFont(curFont);
		xTitle=new JTextField();
		xTitle.setFont(curFont);
		yTitle=new JTextField();
		yTitle.setFont(curFont);
		xValue=new JTextField();
		xValue.setFont(curFont);
		jp.add(chartLabel);
		jp.add(chartTitle);
		if(type!=0)
		{
			jp.add(xLabel);
			jp.add(xTitle);
			jp.add(yLabel);
			jp.add(yTitle);
		}
		//jp.add(xValLabel);
		//jp.add(xValue);
		ok=new JButton(map.getWordBundle().getString("ok"));
		ok.setFont(curFont);
		ok.addActionListener(this);
		cancel=new JButton(map.getWordBundle().getString("cancel"));
		cancel.setFont(curFont);
		cancel.addActionListener(this);
		jp1.add(ok);
		jp1.add(cancel);
		JLabel X1=new JLabel(XAxisHelp);
		X1.setForeground(Color.black);
		jp1.add(X1);
		JLabel X2=new JLabel(XAxisEg);
		X2.setForeground(Color.black);
		jp1.add(X2);
		contents.setLayout(glMain);
		contents.add(jp);
		contents.add(jp1);
		if(type!=0)
			setSize(650,250);
		else
			setSize(650,200);
		setVisible(true);

		addKeyListener(new KeyAdapter()
				{
					public void keyPressed(KeyEvent ke)
					{
						if(ke.getKeyCode() == 27)//Esc
						{
							setVisible(false);
						}
					}
				});


	}

	/**Fires when the ok and cancel button gets the
	action listener for action events. Ok button to
	draw the chart and the cancel button to cancel the
	chart drawing.
	@param ae the ActionEvent object.*/
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==ok)//ae.getActionCommand().equals("  OK  ")) // pie chart
		{
			if(!processTitles())
				return;
			if(type==0)
			{
				values=ws.buildBarChart();
				if(values.length>15)
				{
						wordBundle = map.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String checkRange1 = wordBundle.getString("checkRange1");
						String messageTitle = wordBundle.getString("messageTitle");
						int select = map.showDialog(map,"checkRange1",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
				 }
				else
				{
					ws.buildPieChart(titles,xvalues,map);
				}
			}
			else if(type==1) // vertical bar chart
			{
				values=ws.buildBarChart();
				if(checkDataValidity(values))
				{
					if(values.length>10)
					{
						wordBundle = map.getWordBundle();
						Object[] options = {wordBundle.getString("ok")};
						String checkRange = wordBundle.getString("checkRange");
						String messageTitle = wordBundle.getString("messageTitle");
						int select = map.showDialog(map,"checkRange",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
					}
					else
					{
						JFrame bcV =new barChartV(map,values,titles,xvalues);
						bcV.repaint();
						bcV.validate();
						bcV.show();
					}
				}
				else
				{
					//System.out.println("Chart can not be drawn");
					wordBundle = map.getWordBundle();
					Object[] options = {wordBundle.getString("ok")};
					String DataCheck = wordBundle.getString("DataCheck");
					String messageTitle = wordBundle.getString("messageTitle");
					int select = map.showDialog(map,"DataCheck",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
				}
			}
			else if(type==2)//horizontal bar chart
			{
				values=ws.buildBarChart();
				//arrValue av=new arrValue();
				//av.selValues=values;
				if(checkDataValidity(values))
				{
					if(values.length>10)
					{
							wordBundle = map.getWordBundle();
							Object[] options = {wordBundle.getString("ok")};
							String checkRange = wordBundle.getString("checkRange");
							String messageTitle = wordBundle.getString("messageTitle");
							int select = map.showDialog(map,"checkRange",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
					}
					else
					{
						JFrame bcH =new barChartH(map,values,titles,xvalues);
						bcH.repaint();
						bcH.validate();
						bcH.show();
					}
				}
				else
				{
					wordBundle = map.getWordBundle();
					Object[] options = {wordBundle.getString("ok")};
					String DataCheck = wordBundle.getString("DataCheck");
					String messageTitle = wordBundle.getString("messageTitle");
					int select = map.showDialog(map,"DataCheck",JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);
				}

			}
			setVisible(false);
		}
		else if(ae.getSource()==cancel)//getActionCommand().equals("CANCEL"))
			setVisible(false);

	}

	public boolean checkDataValidity(double[] values)
	{
		boolean chkVal=false;
		for(int c=0;c<values.length;c++)
		{
			Double dble=new Double(values[c]);
			if(values[c]==0 || dble.isNaN())
			{
				//System.out.println(c+"value is equal to zero  "+dble.isNaN());
				chkVal=false;
				break;
			}
			else
			{
				//System.out.println(values[c]+"value is equal to zero(else)   "+dble.isNaN());
				chkVal=true;
			}
		}//end of for loop
	return chkVal;
	}

	/**Method to process the chart titles given
	for x axis and y axis.
	@return boolean to check the process of chart title.*/
	private boolean processTitles()
	{
		titles[0]=chartTitle.getText();
		titles[1]=xTitle.getText();
		titles[2]=yTitle.getText();
		if(xValue.getText().equals(""))
		{
			xvalues=new String[1];
			xvalues[0]=new String("NOVAL");
			return true;
		}
		if(!processXRange())
		{
			//String s="<html><body font face=TABKural color=black>X-Range Value Selection is not proper.Would you like to continue?</body></html>";
			//int select=JOptionPane.showConfirmDialog(new JFrame(),s,"X-Values Selection",		JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			wordBundle = map.getWordBundle();
			Object[] options = {wordBundle.getString("yes"),wordBundle.getString("no"),wordBundle.getString("cancel")};
			String XaxValue = wordBundle.getString("XaxValue");
			String messageTitle = wordBundle.getString("messageTitle");
			int select = map.showDialog(map,"XaxValue",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,options, 0);

			if(select==JOptionPane.YES_OPTION)
			{
				xvalues=new String[1];
				xvalues[0]=new String("NOVAL");
				return true;
			}
			else
				return false;

		}
		return true;
	}
	/**Checks the x axis range value for drawing the chart.
	@return boolean it returns true if it is processed the x range. */
	private boolean processXRange()
	{
		int startRow=0,startCol=0,endRow=0,endCol=0;
		/**Used to tokenize the text received from the
		x axis value text field.*/
		StringTokenizer st=new StringTokenizer(xValue.getText(),",:");
		try
		{
			if(!st.hasMoreTokens())
				return false;
			startRow=Integer.parseInt(st.nextToken());

			if(!st.hasMoreTokens())
				return false;
			startCol=Integer.parseInt(st.nextToken());

			if(!st.hasMoreTokens())
				return false;
			endRow=Integer.parseInt(st.nextToken());

			if(!st.hasMoreTokens())
				return false;
			endCol=Integer.parseInt(st.nextToken());

		}
		catch(Exception e)
		{
			return false;
		}
		//System.out.println(startRow+"--"+startCol+"--"+endRow+"--"+endCol);
		if(startRow<0 || startCol<0 || endRow<0 || endCol<0)
			return false;
		if(startRow!=endRow && startCol!=endCol)
			return false;
		if(endRow>49||endCol>17)
			return false;
		if(endRow<startRow)
		{
			int temp;
			temp=endRow;
			endRow=startRow;
			startRow=temp;
		}
		else if(endCol<startCol)
		{
			int temp;
			temp=endCol;
			endCol=startCol;
			startCol=temp;
		}
		if(startRow==endRow)
		{
			xvalues=new String[endCol-startCol+2];
			xvalues[0]=new String("YESVAL");
			for(int i=0;i<(endCol-startCol+1);i++)
				xvalues[i+1]=new String(ws.data[startRow][startCol+i].toString());
		}
		else
		{
			xvalues=new String[endRow-startRow+2];
			xvalues[0]=new String("YESVAL");
			for(int i=0;i<(endRow-startRow+1);i++)
				xvalues[i+1]=new String(ws.data[startRow+i][startCol].toString());
		}
		return true;
	}

}

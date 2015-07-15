/**
* @(#)barChartH.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.lang.*;
import java.awt.event.ComponentListener;
import java.awt.image.*;
import java.awt.Image;
import com.sun.image.codec.jpeg.*;

/**To provide the data visualisation for the table data in the form
of Charts. This type is horizontal bar chart .It can be saved as image file
for future references.Chart titles and axis range can be specified
to draw the chart.*/
public class barChartH extends JFrame implements ComponentListener
{

	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();
	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");

	/**font to be set for the chart window*/
	Font currFont=new Font("TABKural",0,12);

	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam map;

	/**Object of graphics class.*/
	Graphics gps;

	/**To store the width of the screen from dimension*/
	int currentWidth;
	/**To store the height of the screen from dimension*/
	int currentHeight;
	/**Values for which the chart to be drawn.*/
	double[] values;

	/**Temp integer value (array length)to draw the chart.*/
	int arr_length=0;
	/**Temp integer value to draw the chart.*/
	int w=0;
	/**Temp integer value (no. of values)to draw the chart.*/
	int no_of_values;
	/**Temp integer value (chart width)to draw the chart.*/
	int width = 0;
	/**Temp integer value to draw the chart.(x interval value)*/
	int x_interval;
	/**Temp integer value to draw the chart.(gap between charts)*/
	int gap;
	/**Temp integer value to draw the chart.(y interval value)*/
	int y_interval;
	/**Temp integer value to draw the chart.(temp height for chart)*/
	int temp_ht;
	/**Temp integer value to draw the chart.(maximum value)*/
	int max=0;
	/**To store the chart titles in string array object. */
	String [] titles;
	/**To store the x axis value in string array object.*/
	String [] xvalues;

	/**The word bundle is used to list the tamil and english equivalent words
	for setting menu items,buttons and labels in Tamil.*/
	ResourceBundle wordBundle;

	/**Constructor to draw horizontal bar chart for the given
	value, title and x axis range.
	@parm maapp object of the class Chathurangam
	@param val of type double array for which the chart to be drawn.
	@param tit of type string array to store the chart title.
	@param xval of type string array to store x value.
	*/
	public barChartH(Chathurangam maapp,double[] val,String [] tit,String[] xval)
	{								// starting of constructor barChartH
		this.map=maapp;
		values=val;
		titles=tit;
		xvalues=xval;

		setSize(700,550);
		setLocation(10,10);
		setIconImage(img);
		setBackground(Color.white);
		setTitle("Chart Diagram - Bar(Horizontal)");

		addComponentListener(this);
		arr_length=values.length;

		Container container = this.getContentPane();
		no_of_values=values.length;
		/*if(no_of_values>10)
		setSize(800,600);
		if(no_of_values>15)
		{
			System.out.println("out of Range");
			ExitFrame();
		}*/

		addWindowListener(new WindowAdapter()
		{
    		public void windowClosing(WindowEvent we)
    		  {
    		  	ExitFrame(we);
    		 }
		});

	}//end of constructor barChartH..

	/**Exits from the chart window when the window is closed.
	@param we the WindowEvent object.*/
	public void ExitFrame(WindowEvent we)
	{
		wordBundle = map.getWordBundle();
		Object[] options = {
							wordBundle.getString("yes"),
							wordBundle.getString("no"),
							wordBundle.getString("cancel")
						   };

		String notSavedWar = wordBundle.getString("notSavedWar");
		String messageTitle = wordBundle.getString("messageTitle");
		int select = map.showDialog(this,"notSavedWar",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
							options, 0);

		if(select==JOptionPane.OK_OPTION)
		{
			barChartH bth = (barChartH)we.getComponent();
			convertImage(gps,bth);
		}
		if(select==JOptionPane.NO_OPTION)
		{
		}
		if(select==JOptionPane.CANCEL_OPTION)
		{
			WorkSheet ws;
			ws=map.getWorkSheet();
			double [] values=ws.buildBarChart();
			arrValue av=new arrValue();
			av.selValues=values;
			barChartH bcH =new barChartH(map,values,titles,xvalues);
			bcH.show();
			map.repaint();

		}
	}

	/*public void ExitFrame()//2 nd meth for out of range values
		{
			wordBundle = map.getWordBundle();
			Object[] options = {
								wordBundle.getString("ok"),
							   };

			String notSavedWar = wordBundle.getString("notSavedWar");
			String messageTitle = wordBundle.getString("messageTitle");
			int select = map.showDialog(this,"notSavedWar",
									JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,
								options, 0);

			map.repaint();
			return;

	}//exit frame 2*/
	/**It is implemented for the component listener interface
	to resize the component.*/
	public void componentResized(ComponentEvent comp_event)
		{
			currentWidth = getWidth();
		    currentHeight = getHeight();
		    repaint();
		}

	/**It is implemented for the component listener interface
	to move the component.*/
	public void componentMoved(ComponentEvent comp_event)
		{
			//repaint();
		}

	/**It is implemented for the component listener interface
	to show the component.*/
	public void componentShown(ComponentEvent comp_event)
		{
			currentWidth = getWidth();
		    currentHeight = getHeight();
		    //repaint();
		}

	/**It is implemented for the component listener interface
	to hide the component.*/
	public void componentHidden(ComponentEvent comp_event)
		{
			repaint();
		}

	/**To paint the chart for the given values and
	graphics object as the parameter.
	@param g the object of Graphics.*/
	public void paint(Graphics g)
	{
        gps = g;
		double valOld[]=new double[values.length];
		double maxVal,minVal,normalVal=0,factor=1;
		maxVal=minVal=values[0];
		valOld[0]=values[0];
		for(int i=1;i<values.length;i++)
		{
			valOld[i]=values[i];
			if(values[i]>maxVal)
				maxVal=values[i];
			if(values[i]!=0 && values[i]<minVal)
				minVal=values[i];
		}
		//check whether normalisation is neccessary
		if(maxVal!=0 && minVal!=0 & maxVal!=minVal)
		{
			if((maxVal-minVal)<1)
				factor=100;
			else if((maxVal-minVal)<10)
				factor=10;
			else
				factor=1;
			for(int i=0;i<values.length;i++)
			{
				//Normalistaion procedure
				normalVal=(maxVal-minVal)/5;
				if(values[i]!=0)
				{
					values[i]=(values[i]-minVal)+normalVal;
				}
				values[i]=factor*values[i];
			}

		}
		max=(int)values[0];

		for(int i=0;i<values.length;i++)
		 {
		  if(values[i]<0)
		  	values[i]=0;
		  if(values[i]>max)
		    max=(int)values[i];
		 }


		//default interval : 1,2,3...10
		if(max > 0 && max <=10)
			width = 1;
		if(max > 10 && max <=50)
			width = 5;
		if(max > 50 && max <=100)
			width = 10;
		if(max > 100 && max <=500)
			width = 50;
		if(max > 500 && max <=1000)
			width = 100;

		String result=new String();
		String maxStr=Integer.toString(max);
		int length = maxStr.length();
		if(length > 3)
		{
			String first=maxStr.substring(0,1);
			String second=maxStr.substring(1,2);
			int firstValue=Integer.parseInt(first);
			int secondValue=Integer.parseInt(second);
			if(secondValue>=Integer.parseInt(String.valueOf(5)))
			{
				firstValue += 1;
				result += firstValue;
				for(int i = 0;i < length-1; i++)
				{
					result += 0;
				}

			}
			else
			{
				result += firstValue+""+5;
				for(int i = 0;i < length-2; i++)
				{
					result += 0;
				}

			}

			width = Integer.parseInt(result)/10;
		}

		currentWidth = getWidth();
	    currentHeight = getHeight();
	    if(titles[0].length()!=0)//chart title
		{
			int startX=currentWidth/2+100;
			startX-=((int)(titles[0].length()/2))*15;//14-font size
			if(startX<100)
				startX=100;

			Font f=g.getFont();
			g.setFont(new Font("TABKural",Font.BOLD,12));
			g.drawString(titles[0],startX,75);
			g.setFont(f);
		}
		if(titles[1].length()!=0)//XAxis title
		{
			int startX=currentWidth/2;
			startX-=((int)(titles[1].length()/2))*15;//14-font size
			if(startX<100)
				startX=100;

			Font f=g.getFont();
			g.setFont(new Font("TABKural",Font.BOLD,12));
			g.drawString(titles[1],startX,currentHeight-18);
			g.setFont(f);
		}
		if(titles[2].length()!=0)//YAXis
		{
			char [] data=titles[2].toCharArray();
			int startY=currentHeight/2;
			startY-=((int)(titles[2].length()/2))*17;
			if(startY<100)
				startY=100;
			Font f=g.getFont();
			g.setFont(new Font("TABKural",Font.BOLD,12));
			for(int i=0;i<data.length;i++)
				g.drawChars(data,i,1,currentWidth-100,startY+i*17);
			g.setFont(f);

		}
		g.drawRect(50,50,currentWidth-100,currentHeight-100);
		g.drawString(getFormattedString(minVal-normalVal),48,currentHeight-33);
		y_interval=(int)((currentHeight-50)/(arr_length+2));
	    temp_ht=y_interval;
	    if(xvalues[0].equals("NOVAL"))
	    {
			for(int k=0,yval=1,height=1;k<arr_length;k++,yval+=height)//Y Axis partitions
			{
				g.drawLine(48,currentHeight-50-y_interval,50,currentHeight-50-y_interval);
				g.drawString(Integer.toString(yval),10,currentHeight-45-y_interval);
				y_interval+=temp_ht;
			}
		}
		else
		{
			int j=xvalues.length;
			for(int k=0,yval=1,height=1;k<arr_length && j>1;k++,yval+=height)//Y Axis partitions
			{
				g.drawLine(48,currentHeight-50-y_interval,50,currentHeight-50-y_interval);
				g.drawString(xvalues[yval],10,currentHeight-45-y_interval);
				y_interval+=temp_ht;
				j--;
			}

		}

		x_interval=(currentWidth-100)/10;//X Axis Partitions


		for(int j=50+x_interval,xval=width;j<=currentWidth-50;j+=x_interval,xval+=width)
		{
			g.drawLine(j,currentHeight-48,j,currentHeight-50);
			g.drawString(getFormattedString((minVal-normalVal)+xval/factor),j-5,currentHeight-33);
		}

		y_interval=(int)((currentHeight-50)/(arr_length+2));
		gap=(temp_ht-40)/2;
		w=temp_ht-40;

		for(int i=0;i<values.length;i++,y_interval+=temp_ht)
		{
			g.setColor(Color.red);
			g.drawString(Double.toString(valOld[i]),55+(((int)values[i])*x_interval/width),currentHeight-45-y_interval);
			g.setColor(Color.blue);
			g.fillRect(50,currentHeight-50-y_interval-gap,((int)values[i])*x_interval/width,w);

		}
		//repaint();
		validate();

	} // end of paint method

	/**Gets the formatted string of the chart data.
	@param d of type double value for which the chart to be drawn.*/
	private String getFormattedString(double d)
	{
		String s=Double.toString(d);
		int index=s.indexOf((int)'.');
		if(index>-1 && (index+2)<(s.length()-1))
			return s.substring(0,index+2);
		else
			return s;
	}
/**Converts the chart into image format of type .JPG.
@param g of type graphics.
@param bcH  object of the class barChartH.*/
public void convertImage(Graphics g,barChartH bcH)
  {
	/**Displays the file in the directory.*/
	JFileChooser chooser=new JFileChooser();
	chooser.setAcceptAllFileFilterUsed(false);
	chooser.addChoosableFileFilter(new ExampleFileFilter(new String[] {"jpg", "jpeg"},"chart image"));
	map.setLanguage(chooser);
	int option=chooser.showSaveDialog(this);
	try{

	  if(option==JFileChooser.APPROVE_OPTION) // If approve option
		{
		  File file;
		  String filename=chooser.getSelectedFile().getAbsolutePath();
		  OutputStream out=null;
		  wordBundle = map.getWordBundle();
		  Object[] options = {wordBundle.getString("ok")};


		  if(filename.endsWith(".jpg") || filename.endsWith(".jpeg"))
		  {
		    	file=new File(filename);
		  	  	out=new FileOutputStream(filename);
		  }
		  else
		  {
		  	    file=new File(filename+".jpg");
		   		out=new FileOutputStream(filename+".jpg");
		  }
  		 if (file.exists()) {file.delete();}
		 	try
		 		{
		 			file.createNewFile();
		        }
		        catch (IOException eIo) {}

		     try
		     {

				Image image = (Image)bcH.createImage(700,550);
				g = image.getGraphics();
				bcH.paint(g);
				BufferedImage bufImage = (BufferedImage)image;
				JPEGImageEncoder jencoder = JPEGCodec.createJPEGEncoder(out);
				jencoder.encode(bufImage);
				out.flush();
				out.close();
		  	}
		  	catch(Exception e)
		  	{
			  e.printStackTrace();
		  	}

		} // end of if approve option.
		if(option==JFileChooser.CANCEL_OPTION)
		{
			WorkSheet ws;
			ws=map.getWorkSheet();
			double [] values=ws.buildBarChart();
			arrValue av=new arrValue();
			av.selValues=values;
			bcH =new barChartH(map,values,titles,xvalues);
			bcH.repaint();
			bcH.show();
			map.repaint();		}

		}
		catch(IOException ioex)
		{
			System.out.println();//"file save exception");
		}

  }//end of convertimage method.
} // end of class barChartH
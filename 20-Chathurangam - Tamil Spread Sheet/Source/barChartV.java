/**
* @(#)barChartV.java 1.02 09/12/2002
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
import java.awt.image.*;
import java.awt.Image;
import com.sun.image.codec.jpeg.*;

/**To provide the data visualisation for the table data in the form
of Charts. This type is vertical bar chart .It can be saved as image file
for future references.Chart titles and axis range can be specified
to draw the chart.*/
public class barChartV extends JFrame implements ComponentListener
{
	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();
	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");

	/**Values for which the chart to be drawn.*/
	double[] values;

	/**Temp integer value to draw the chart.*/
	int w=20;

	/**Temp integer value (array length)to draw the chart.*/
	int arr_length=0;

	/**Temp integer value (no. of values)to draw the chart.*/
	int no_of_values;

	/**To store the width of the screen from dimension*/
	int currentWidth;

	/**To store the height of the screen from dimension*/
	int currentHeight;

	/**Temp integer value to draw the chart.(maximum value)*/
	int max;

	/**Temp integer value (chart height )to draw the chart.*/
	int height = 0;

	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam map;

	/**Object of graphics class.*/
	Graphics gps;

	/**To store the chart titles in string array object. */
	String [] titles;

	/**To store the x axis value in string array object.*/
	String [] xvalues;

	/**Constructor to draw vertical bar chart for the given
	value, title and x axis range.
	@parm maapp object of the class Chathurangam
	@param val of type double array for which the chart to be drawn.
	@param tit of type string array to store the chart title.
	@param xval of type string array to store x value.
	*/
	public barChartV(Chathurangam maapp,double[] val,String[] tit,String[] xval)
	{
		this.map=maapp;
		titles=tit;
		xvalues=xval;
		values=val;
		setSize(700,550);
		setLocation(10,10);
		setIconImage(img);
		setBackground(Color.white);
		setTitle("Chart Diagram - Bar(Vertical)");
		//setResizable(false);
		addComponentListener(this);
		arr_length=values.length;
		no_of_values=values.length;
		addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent we)
		{
			ExitFrame(we);
		}
  		 });

		//repaint();
		validate();
	}
		/**Exits from the chart window when the window is closed.
		@param we the WindowEvent object.*/
		void ExitFrame(WindowEvent we)
			{
				/**The word bundle is used to list the tamil and english equivalent words
				for setting menu items,buttons and labels in Tamil.*/
				ResourceBundle wordBundle = map.getWordBundle();
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
				if (select==JOptionPane.OK_OPTION)
					{
						barChartV bvth = (barChartV)we.getComponent();
						convertImage(gps,bvth);
					}
					if(select==JOptionPane.NO_OPTION)
					{

					}

					if(select==JOptionPane.CANCEL_OPTION)
					{
						WorkSheet ws;
						ws=map.getWorkSheet();
						double [] values=ws.buildBarChart();
						JFrame bcV =new barChartV(map,values,titles,xvalues);
						bcV.repaint();
						bcV.show();
						map.repaint();
					}
		}

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
				//System.out.println("component Moved");
				repaint();
			}
		/**It is implemented for the component listener interface
		to show the component.*/
		public void componentShown(ComponentEvent comp_event)
			{
				currentWidth = getWidth();
			    currentHeight = getHeight();
			    repaint();
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
			if((int)values[i]>max)
				max=(int)values[i];
		}
		//default interval : 1,2,3...10
		if(max > 0 && max <=10)
			height = 1;
		if(max > 10 && max <=50)
			height = 5;
		if(max > 50 && max <=100)
			height = 10;
		if(max > 100 && max <=500)
			height = 50;
		if(max > 500 && max <=1000)
			height = 100;
		/**To store the result value in the string object.*/
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

		height = Integer.parseInt(result)/10;
		}

		currentWidth = getWidth();
	    currentHeight = getHeight();
	    if(titles[0].length()!=0)//chart title
		{
			int startX=currentWidth/2;
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
				g.drawChars(data,i,1,75,startY+i*17);
			g.setFont(f);

		}
		g.drawRect(50,50,currentWidth-100,currentHeight-100);
		//g.drawLine(50,currentHeight-50,currentWidth-50,currentHeight-50);
		//g.drawLine(50,50,50,currentHeight-50);
		g.drawString(getFormattedString(minVal-normalVal),10,currentHeight-50);

		int y_interval=(currentHeight-100)/10;//Y Axis Partitions
		int temp_ht=y_interval;


	   	for(int k=0,yval=height;k<10;k++,yval+=height)//Y Axis partitions
	   	{
			g.drawLine(48,currentHeight-50-y_interval,50,currentHeight-50-y_interval);
			g.drawString(getFormattedString((minVal-normalVal)+(k+1)*height/factor),10,currentHeight-45-y_interval);
			y_interval+=temp_ht;
		}

		int x_interval=(int)((currentWidth-50)/(arr_length+2));//X Axis Partitions
		if(xvalues[0].equals("NOVAL"))
		{
			for(int j=50+x_interval,xval=1,height=1;(j+x_interval)<currentWidth-50;j+=x_interval,xval+=height)
	   		{
				g.drawLine(j,currentHeight-48,j,currentHeight-50);
				g.drawString(Integer.toString(xval),j-5,currentHeight-33);
			}
		}
		else
		{
			int k=xvalues.length;
			for(int j=50+x_interval,xval=1,height=1;(j+x_interval)<currentWidth-50 && k>1;j+=x_interval,xval+=height)
			{
				g.drawLine(j,currentHeight-48,j,currentHeight-50);
				g.drawString(xvalues[xval],j-5,currentHeight-33);
				k--;
			}
		}
		Random ran = new Random(200);
		y_interval=(currentHeight-100)/10;
		int gap=(x_interval-5)/2;
		w=x_interval-25;
		int wid=0;
		if(arr_length<8)
		wid=28;
		else wid=15;
		for(int j=50+x_interval,xval=1,width=1,i=0;i<values.length && j<currentWidth-50;j+=x_interval,xval+=width,i++)
		{
			Color clr = new Color(ran.nextInt());
			g.setColor(Color.black);
			g.drawString(Double.toString(valOld[i]),j-5,currentHeight-55-(((int)values[i])*y_interval/height));
			g.setColor(Color.blue);
			g.fillRect(j-gap+42,currentHeight-50-(((int)values[i])*y_interval/height),wid,((int)values[i])*y_interval/height);
		}
	    /* First painting occurs at (x,y), where x is at least
	      insets.left, and y is at least insets.height. */
	//repaint();
	}

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
public void convertImage(Graphics g,barChartV bcV)
{
	/**Displays the file in the directory.*/
	JFileChooser chooser=new JFileChooser();
	chooser.setAcceptAllFileFilterUsed(false);
	chooser.addChoosableFileFilter(new ExampleFileFilter(new String[] {"jpg", "jpeg"},"chart image"));
	int option=chooser.showSaveDialog(this);
	map.setLanguage(chooser);

	try{

	  if(option==JFileChooser.APPROVE_OPTION)
		{
	      File file;
			String filename=chooser.getSelectedFile().getAbsolutePath();
			OutputStream out=null;
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

				Image image = (Image)bcV.createImage(700,550);
				g = image.getGraphics();
				bcV.paint(g);
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

		}
		if(option==JFileChooser.CANCEL_OPTION)
				{
					WorkSheet ws;
					ws=map.getWorkSheet();
					double [] values=ws.buildBarChart();
					bcV =new barChartV(map,values,titles,xvalues);
					bcV.show();
					map.repaint();

				}

		}
		catch(IOException ioex)
		{
			System.out.println();//"file save exception");
		}

}
}
/**
* @(#)PieTableChartPopup.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/

//This class uses PieTableChart class
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import javax.swing.event.*;
import java.io.*;
import java.lang.*;
import java.awt.event.ComponentListener;
import java.awt.image.*;
import java.awt.Image;
import com.sun.image.codec.jpeg.*;


/**This class can be used to display the data selected by the user
From the value pie chart can be drawn. It extends JFrame.
*/
public class PieTableChartPopup extends JFrame
{
	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();
	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");

	/**Stores the file menu items in the string array object.*/
	String[] fileItems=new String[] {"¹î¤ò","î¤ø","«êñ¤","ªõ÷¤«òÁ"}; //new,open,save,exit
	/**Stores the selected value from the option pane alert message.*/
	int select;
	/**Object of the class <code>WorkSheet</code>*/
	WorkSheet ws;
	/**Stores the chart titles in the string array object.*/
	String [] titles;
	/**Stores the x axis values  in the string array */
	String [] xvalues;

	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam main;
	/**Constructor invoked to draw the pie chart  from the selected
	value and calculated values for the chart.
	@param tm object of TableModel.
	@param w object of WorkSheet
	@param title to store the chart titles in the string array.
	@param xval to store the x axis value for the chart.
	@param main object of the main class.*/
	public PieTableChartPopup(TableModel tm,WorkSheet w,String [] title,String[] xval,Chathurangam main)
	{
		this.main=main;
		titles=title;
		xvalues=xval;
		ws=w;
		setSize(500,300);
		setLocation(100,100);
		setIconImage(img);
		setTitle("Chart Diagram - PieChart");
		setBackground(Color.white);
		//System.out.println("Class - PieTableChartPopUp ***");
		PieTableChart tc=new PieTableChart(tm,title,xval);
		/**sets the font for the chart components.*/
		Font curFont=new Font("TABKural",0,12);

		addWindowListener(new WindowAdapter()
				{
		    		public void windowClosing(WindowEvent we)
		    		  {
		    		    ExitFrame(we);
					  }
		});
		tc.setTextFont(curFont);
		getContentPane().add(tc,BorderLayout.CENTER);
		tc.setToolTipText("Pie Chart");
	}

	/**Exits from the chart window when the window is closed.
	@param we the WindowEvent object.*/
	public void ExitFrame(WindowEvent we)
	{
		/**The word bundle is used to list the tamil and english equivalent words
		for setting menu items,buttons and labels in Tamil.*/
		ResourceBundle wordBundle = main.getWordBundle();
		Object[] options = {
							wordBundle.getString("yes"),
							wordBundle.getString("no"),
							wordBundle.getString("cancel")
						   };

		String notSavedWar = wordBundle.getString("notSavedWar");
		String messageTitle = wordBundle.getString("messageTitle");
		int select = main.showDialog(main,"notSavedWar",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
							options, 0);

			if(select==JOptionPane.YES_OPTION)
			{
				PieTableChartPopup pie = (PieTableChartPopup)we.getComponent();
				convertImage(pie);
			}
			if(select==JOptionPane.NO_OPTION)
			{
			//System.out.println("NOOO");
			}
			if(select==JOptionPane.CANCEL_OPTION)
			{
			//System.out.println("CANCELO");
				ws.buildPieChart(titles,xvalues,main);
			}

	}

	/**Converts the chart into image format of type .JPG.
	@param pie of type PieTableChartPopup.*/
	public void convertImage(PieTableChartPopup pie)
	  {
		/**Object of Graphics class.*/
		Graphics g;
		/**File chooser to display the file dialog for saving the chart as image.*/
		JFileChooser chooser=new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(new ExampleFileFilter(new String[] {"jpg", "jpeg"},"chart image"));
		int option=chooser.showSaveDialog(this);
		try{

		  if(option==JFileChooser.APPROVE_OPTION) // If approve option
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

					Image image = (Image)pie.createImage(700,550);
					g = image.getGraphics();
					pie.paint(g);
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
			}
			catch(IOException ioex)
			{
				System.out.println("file save exception");
			}

  }//end of convertimage method.

}

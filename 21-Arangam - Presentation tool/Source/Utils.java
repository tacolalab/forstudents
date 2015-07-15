import java.awt.GraphicsConfiguration;
import java.awt.Container;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Component;
import java.util.ResourceBundle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.Clipboard;

import javax.swing.*;

/**
 * General Utilities like function to invert color
 */
public class Utils
{
	/*** Gets the words in current language - Tamil, English or Tamil-English */
	private static java.util.ResourceBundle wordBundle;

	/*** Main class reference */
	private transient static Arangam arangam;

	private static final char[] WORD_SEPARATORS = {' ', '\t', '\n', ';', '?', '_',
		'\r', '\f', '.', ',', ':', '-', '(', ')', '[', ']', '{',
		'}', '<', '>', '/', '|', '\\', '\'', '\"'};

	/**
	 * Connect the class to the ConnectAction interface.
	 * @param actionsConnection interface to ActionImpls method.
	 */
	public static void getInterface(Arangam arangam1)
	{
		arangam = arangam1;
	}

	/**
	 * Finds whether the character is a word separator
	 * @param ch the character to check.
	 * @return <code>true</code> if the character is word separator
	 */
	public static boolean isSeparator(char ch)
	{
		for(int k=0; k<WORD_SEPARATORS.length; k++)
			if(ch == WORD_SEPARATORS[k])
				return true;
		return false;
	}

	/**
	 * Inverts the given color
	 * @param color the character to invert
	 * @return the inverted color
	 */
	public static Color invertColor(Color color)
	{
		int r=color.getRed();
		int g=color.getGreen();
		int b=color.getBlue();
		r=Math.abs(255-r);
		g=Math.abs(255-g);
		b=Math.abs(255-b);
		return new Color(r,g,b);
	}

	/**
	 * Gets the middle point in a dimension
	 * @param dimension the dimension for which middle point is calculated
	 * @return the middle point
	 */
	public static Point getMiddle(Dimension dimension)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)(screenSize.width-dimension.width)/2;
		int y = (int)(screenSize.height-dimension.height)/2;
		return new Point(x,y);
	}

	/**
	 * Gets the middle point for two dimensions
	 * @param dimension the dimension for which middle point is calculated
	 * @return the middle point
	 */
	public static Point getMiddle(Dimension d1,Dimension d2)
	{
		int x = (int)(d1.width-d2.width)/2;
		int y = (int)(d1.height-d2.height)/2;
		return new Point(x,y);
	}

	/**
	 * Show the dialog with specified attributes
	 * @param parent parent window
	 * @param msg the message to display
	 * @param type the type of option to be displayed
	 * @param icon the Icon image to display
	 * @param options the choices the user can select
	 * @param initialValue the choice that is initially selected
	 */
	public static int showDialog(Component parent,String msg,int option,
		int type,javax.swing.Icon icon,Object[] options,int initialValue)
	{
		//get words from bundle
		wordBundle = arangam.getWordBundle();
		String messageTitle = wordBundle.getString("messageTitle");
		String message = wordBundle.getString(msg);


		//create an option pane
		JOptionPane p = new JOptionPane((Object)message,
			option, type, icon,	options, options[0]);

		//create a dialog with it
		JDialog d = p.createDialog(parent,messageTitle);

		//show it
		d.setResizable( false );
		d.show();
		Object selectedValue = p.getValue();

		//if the option is close/null, dispose
		if(selectedValue.equals(new Integer(-1)))
		{
			d.dispose();
			return JOptionPane.CANCEL_OPTION;
		}
		if(selectedValue == null)
		{
			d.dispose();
			return JOptionPane.CLOSED_OPTION;
		}

		//if there is no array of option buttons
		if(options == null)
		{
			if(selectedValue instanceof Integer)
			{
				d.dispose();
				return ((Integer)selectedValue).intValue();
			}
			d.dispose();
			return JOptionPane.CLOSED_OPTION;
		}

		//if there is an array of option buttons:
		for(int counter = 0, maxCounter = options.length;
			counter < maxCounter; counter++)
		{
			if(options[counter].equals(selectedValue))
			{
				d.dispose();
				return counter;
			}
		}
		d.dispose();
		return 0;
	}

	/**
	 * Sets the frames bound accoding to screen size
	 * @param frame the frame which is to be sized
	 */
	public static void sizeScreen(JFrame frame)
	{
		if(System.getProperty("java.version").startsWith("1.4"))
		{
			//get screen size and set it to frame
			Toolkit kit = Toolkit.getDefaultToolkit();
			Dimension screenSize = kit.getScreenSize();

			//to display the frame above taskbar
			GraphicsConfiguration config = frame.getGraphicsConfiguration();
			Insets insets = kit.getScreenInsets(config);
			screenSize.width -= (insets.left + insets.right);
			screenSize.height -= (insets.top + insets.bottom);
			frame.setLocation(insets.left, insets.top);
			frame.setSize(screenSize);

			//update dimensions for several components
			Arangam.A_WIDTH = frame.getWidth();
			Arangam.A_HEIGHT = frame.getHeight();
			Arangam.SLIDESHOW_X = frame.getX() + 70;
			Arangam.SLIDESHOW_Y = frame.getY() + 90;
			Arangam.SLIDESHOW_WIDTH = frame.getWidth() - 130;
			Arangam.SLIDESHOW_HEIGHT = frame.getHeight() - 180;
		}else
		{
			sizeScreen_v3(frame);
		}
	}

	public static void sizeScreen_v3(JFrame f)
	{
		Dimension dimScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f.setSize(dimScreenSize);
		f.setVisible(true);
		java.awt.Rectangle rectVisible = f.getRootPane().getVisibleRect();
		int iWidth = (int)rectVisible.getWidth();
		int iHeight = (int)rectVisible.getHeight();
		int iX = (int)rectVisible.x;
		int iY = (int)rectVisible.y;
		f.setBounds(iX,iY,iWidth,iHeight);
	}

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Slideshow is run in this window
 */
class Show extends JWindow
{
	/*** Main class reference */
	private transient Arangam arangam;

	/*** Frame in which this window is displayed */
	private static JFrame frame;

	/**
	 * Create a window with screen size
	 */
	public Show()
	{
		super(frame);

		//set bounds
		Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		setBounds(0,0,screenSize.width,screenSize.height);

		//make it invisible
		setVisible(false);
	}

	/**
	 * Set frame in which this window is displayed
	 */
	public static void setFrame()
	{
		frame = new JFrame();
		//set icon for frame
		frame.setIconImage(ImagesLocator.getImage
			(Arangam.imageBundle.getString("arangamLogo")).getImage());
	}

	/**
	 * Connect the class to the main class.
	 * @param arangam main class reference.
	 */
	public void getInterface(Arangam arangam)
	{
		this.arangam = arangam;
	}

	/**
	 * Display the Slide one by one
	 */
	public void run()
	{
		arangam.slideshow1.setSlideshowSize(getSize());
		getContentPane().add(arangam.slideshow1);
		setVisible(true);
		requestFocus();
		requestFocusInWindow();
		arangam.setEnabled(false);
	}

	/**
	 * Stop running the Slideshow
	 */
	public void stop()
	{
		getContentPane().removeAll();
		setVisible(false);
		arangam.slideshow1.setSlideshowSizeBack();
		arangam.presentaionPane.add(arangam.slideshow1);
		arangam.setEnabled(true);
		arangam.requestFocus();
	}

}

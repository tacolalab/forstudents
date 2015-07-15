import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Displays the startng splash image and calls the constructor of
 * main application frame(<code>Arangam</code>).
 */
public class ArangamMain extends JWindow
{
	/**
	 * Constructor to show the splash screen.
	 * @param filename the image file to show
	 * @param frame on which this window is showing
	 */
	public ArangamMain(String filename, JFrame frame)
	{
		super(frame);

		//create a label with splash image icon
		JLabel l = new JLabel(ImagesLocator.getImage(filename));
		//add it to window
		getContentPane().add(l, BorderLayout.CENTER);
		pack();

		//set bounds
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = l.getPreferredSize();
		setLocation(screenSize.width/2 - (labelSize.width/2),
				screenSize.height/2 - (labelSize.height/2));

		final Runnable closerRunner = new Runnable()
		{
			public void run()
			{
				//dispose the window
				setVisible(false);
				dispose();
			}
		};

		Runnable waitRunner = new Runnable()
		{
			public void run()
			{
				try
				{
					//create arangam application
					new Arangam(null);

					/*
					Causes run() to be executed synchronously on the
					AWT event dispatching thread. This call will block until
					all pending AWT events have been processed and (then)
					run() returns
					*/
					SwingUtilities.invokeAndWait(closerRunner);
				}
				catch(InterruptedException e)
				{
					//alert for general exception and return
					Object[] options = {Arangam.wordBundle.getString("ok")};
					Utils.showDialog(null,"appCantOpen",JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);
					return;
				}
				catch(java.lang.reflect.InvocationTargetException e)
				{
					//alert for general exception and return
					Object[] options = {Arangam.wordBundle.getString("ok")};
					Utils.showDialog(null,"appCantOpen",JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);
					return;
				}
			}
		};

		setVisible(true);
		//create a thread with waitRunner
		Thread splashThread = new Thread(waitRunner, "SplashThread");
		//start it
		splashThread.start();
		//set priority to normal level - 5
		splashThread.setPriority(Thread.NORM_PRIORITY);
	}

	/**
	 * Create a new Instance of this class
	 */
	public static void main(String args[])
	{
		JFrame frame = new JFrame();
		new ArangamMain(Arangam.imageBundle.getString("splash"), frame);
	}

}

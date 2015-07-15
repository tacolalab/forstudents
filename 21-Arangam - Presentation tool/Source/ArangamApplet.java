import java.awt.*;
import java.applet.*;
import javax.swing.*;

/**
 * Applet is used if the product is run from browser
 */
public class ArangamApplet extends JApplet
{
	/**
	 * Initialize by adding application frames content to applet
	 */
	public void init()
	{
		Arangam p = new Arangam(this);
		setContentPane(p.getContentPane());
	}
}

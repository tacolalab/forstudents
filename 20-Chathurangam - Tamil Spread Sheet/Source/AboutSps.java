import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.event.ListSelectionEvent.*;
import java.lang.*;
import java.awt.datatransfer.*;
import java.awt.print.*;
import java.awt.geom.*;
import java.awt.Dimension;
import javax.swing.text.html.*;
import java.net.*;
import javax.swing.border.*;


/**To display the details about the tool in the image
when the aboutSps menu item is selected from the help menu.
it is extended from JFrame.*/
class AboutSps extends JWindow implements ActionListener
{
	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();
	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");
	/**Sets the image with company logo and address with
	product details for aboutsps.*/
	JLabel jl_image=new JLabel(ImagesLocator.getImage("abtsp.jpg"));
	/**Constructor invoked when the aboutSps menu item is
	clicked from the help menu.*/
	JButton jb_cross=new JButton(ImagesLocator.getImage("cross.gif"));
	EmptyBorder empty=new EmptyBorder(5,5,10,10);
	JPanel buttonpan=new JPanel(new GridLayout(1,6));

	public AboutSps()
	{
		setSize(400,400);
		setLocation(200,100);
		//setIconImage(img);
		//setTitle("About Chathurangam --- Tamil Spreadsheet");
		jb_cross.addActionListener(this);
		jb_cross.setBorder(empty);
		buttonpan.setBackground(new Color(139,155,177));
		jb_cross.setBackground(new Color(139,155,177));
		buttonpan.add(new JLabel(""));
		buttonpan.add(new JLabel(""));
		buttonpan.add(new JLabel(""));
		buttonpan.add(new JLabel(""));
		buttonpan.add(new JLabel(""));
		buttonpan.add(jb_cross);
		getContentPane().add(buttonpan,BorderLayout.SOUTH );
		getContentPane().add(jl_image,BorderLayout.CENTER);
	}
	public void actionPerformed(ActionEvent ae)
	{
	if(ae.getSource()==jb_cross)
	{
		setVisible(false);
	}
	}
}

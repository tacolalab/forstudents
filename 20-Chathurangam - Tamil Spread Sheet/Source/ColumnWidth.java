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

/** The class is used to vary the column width of
the worksheet. The selectd column will be identified and
the column width can be increased as we require.
The text filed is displayed to enter the required column width value.*/

public class ColumnWidth extends JFrame implements ActionListener
{
	/**The toolkit object is used to get the default toolkit
	to set the icon in the title bar of the window.*/
	Toolkit tk= Toolkit.getDefaultToolkit();

	/**The image object is used to set the image in the titlebar of the window.*/
	Image img=tk.getImage("tha.gif");

	/**Obejct of the main class <code>Chathurangam</code>*/
	Chathurangam main;

	/**To set the label and text field in the window.*/
	JPanel panel=new JPanel(new GridLayout());
	/**To set the buttons(ok and cancel) in the window.*/
	JPanel panel1=new JPanel();
	/**Sets the heading  for row height entry.*/
	JLabel jl_head;
	/**Used for entering row height for the table.*/
	JTextField jt_colwid=new JTextField(3);
	/**Used for ok button to increase the row height.*/
	JButton jb_ok;
	/**Used for cancel button to increase the row height.*/
	JButton jb_cancel;
	ResourceBundle wordBundle;
	/**Store the selected column index from the table.*/
	int selcol;

	/**
	* The constructor to set the column width for selected row.
	* @param main object of the main class Chathurangam
	* @param selcol the integer value for the selected column
	*/
	public ColumnWidth(Chathurangam main, int selcol)
	{
		this.main=main;
		this.selcol=selcol;
		setSize(200,120);
		setLocation(210,140);
		setTitle("ColumnWidth");
		setResizable(false);

		jl_head=new JLabel(main.getWordBundle().getString("ColumnWidth"));
		jb_ok=new JButton(main.getWordBundle().getString("ok"));
		jb_cancel=new JButton(main.getWordBundle().getString("cancel"));
		jb_ok.addActionListener(this);
		jb_cancel.addActionListener(this);

		jt_colwid.setText(Integer.toString(75));

		panel.add(jl_head);
		panel.add(jt_colwid);
		panel1.add(jb_ok);
		panel1.add(jb_cancel);

		getContentPane().add(panel,"North");
		getContentPane().add(panel1,"South");

		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == 27)//Esc
				{
					setVisible(false);
				}
				//if(ke.getKeyCode() == 10 && findButton.isEnabled())//Enter
				//{
				//	find();
				//}
			}
		});

	}

	/**Fire action events when the ok and cancel button
		gets the action listeners. Ok button will change the
		row height and the cancel button will cancel the
		row height change.
		@param ae the ActionEvent.*/
	public void actionPerformed(ActionEvent ae)
	{
		Hashtable temp = new Hashtable();

		if(ae.getSource()==jb_ok)
			{
				TableColumnModel tcm = main.cur_table.getColumnModel();
				TableColumn col = tcm.getColumn(selcol);
				int colwid=Integer.parseInt(jt_colwid.getText().trim());
				if(colwid<60 || colwid>300)
				{
					//System.out.println("Colwid Less than min value");
					wordBundle = main.getWordBundle();
					Object[] options = {
													wordBundle.getString("ok"),
												};

					String minLimit = wordBundle.getString("minLimit");
					String messageTitle = wordBundle.getString("messageTitle");
					int select = main.showDialog(this,"minLimit",
											JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,
											options, 0);
					show();

				}
				else
				{
				col.setPreferredWidth(colwid);
				//main.ht_colwid.put(new Integer(selcol),new Integer(colwid));
				temp.put(new Integer(selcol),new Integer(colwid));
				main.ht_colwid.put(new Integer(main.jtp.getSelectedIndex()), temp);
				setVisible(false);
				}
			}
			if(ae.getSource()==jb_cancel)
			{
				setVisible(false);
			}
	}
}
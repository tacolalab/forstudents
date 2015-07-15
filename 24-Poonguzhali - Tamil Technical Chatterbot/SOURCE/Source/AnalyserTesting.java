/**
  *  This class is to create the user interface for Morphological
  *  Analyser for noun
  */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

public class AnalyserTesting extends JFrame implements ActionListener
{

	Toolkit tk = Toolkit.getDefaultToolkit();
	Image img = tk.getImage("gif.GIF");
	Dimension dim = tk.getScreenSize();
	double d1 = dim.getHeight();
	double d2 = dim.getWidth();

	ResultSet resultSet;
	Statement statement;
	Connection connection;

	String[] result = new String[500];
	int index = 0;
	int count = 0;
	int tempindex = 1;
	Font f=new Font("TAB_Elango_Barathi",4,16);

	JTextField tf1 = new JTextField(30);
	JTextArea tf2 = new JTextArea(10,20);
	JTextField tf3 = new JTextField(2);
	JButton ok = new JButton("Ok");
	JButton exit = new JButton("Exit");
	JButton next = new JButton("Next »»");
	JButton previous = new JButton("«« Previous");
	JButton go = new JButton("Go");
	JPanel p1=new JPanel();
	JPanel p2= new JPanel();
	JPanel p3= new JPanel();
	JScrollPane scrollpane=new JScrollPane(tf2);
	JPopupMenu popup;
	JMenuItem menuItem;


	public AnalyserTesting()
	{

		super("Morphological Analyser version 1.1");
		setSize(600,400);
		setIconImage(img);
		setLocation(((int)d1-400)/2,((int)d2-600)/2);

		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			connection = DriverManager.getConnection("jdbc:odbc:analyserData");
			statement =  connection.createStatement();
		 	resultSet = statement.executeQuery("select word from sorted_words ");
		 	int i=0;
		 	while(resultSet.next())
		 		 result[i++] = resultSet.getString(1);
		 	count = i;
		 	connection.close();
		}
		catch(Exception e)
		{
			 System.out.println("SQLException");
		}

		p1.add(tf1);
		System.out.println(index);
		tf1.setText(result[index++]);
		p3.add(next);
		p3.add(ok);
		p3.add(exit);
		p3.add(previous);
		p3.add(tf3);
		p3.add(go);


		tf1.setFont(f);
		tf1.setEditable(true);
		tf2.setFont(f);

		tf1.setBackground(Color.black);
		tf2.setBackground(Color.black);
		tf3.setBackground(Color.black);
		tf1.setForeground(Color.yellow);
		tf2.setForeground(Color.yellow);
		tf3.setForeground(Color.yellow);
		p1.setBackground(Color.darkGray);
		p2.setBackground(Color.darkGray);
		p3.setBackground(Color.darkGray);
		next.setBackground(Color.lightGray);
		previous.setBackground(Color.lightGray);
		exit.setBackground(Color.lightGray);
		ok.setBackground(Color.lightGray);
		go.setBackground(Color.lightGray);

		popup = new JPopupMenu();

		menuItem = new JMenuItem("cut");
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("copy");
		menuItem.addActionListener(this);
		popup.add(menuItem);

		menuItem = new JMenuItem("paste");
		menuItem.addActionListener(this);
		popup.add(menuItem);
		popup.addSeparator();

		menuItem = new JMenuItem("delete");
		menuItem.addActionListener(this);
		popup.add(menuItem);

		MouseListener popupListener = new PopupListener();
		tf1.addMouseListener(popupListener);

		ok.addActionListener(this);
		exit.addActionListener(this);
		next.addActionListener(this);
		previous.addActionListener(this);
		go.addActionListener(this);

		previous.setEnabled(false);

		getContentPane().add(p1,"North");
		getContentPane().add(scrollpane,"Center");
		getContentPane().add(p3,"South");

		pack();
		setResizable(false);
		setVisible(true);//show();
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(1);}});

	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == go)
			{
				 try
				 {
					 int temp =Integer.parseInt(tf3.getText().trim());

				 	 if(temp > 0 && temp < result.length)
				 	 {
						  index = temp;
						  tf1.setText(result[index-1]);
			 			  tf2.setText("");
			 			  previous.setEnabled(true);
					 }
			  	  }
			  	  catch(Exception e)
			  	  {
						System.out.println(e);
				  }


			}
		if(ae.getSource() == exit)
			System.exit(0);

		if(ae.getSource() == next)
		{
			 if(tempindex == 1)
			 	index++;
			 else
			 	index+=2;

			 if(index <= count)
			 {
				System.out.println(index-1);
				tf1.setText(result[index-1]);
				tf2.setText("");
			 	previous.setEnabled(true);
		  	 }
		  	 if(index == count)
		  	 {
				next.setEnabled(false);
			 }
		}
		if(ae.getSource() == previous)
		{
			 if(tempindex == 2)
			 	index--;
			 else
			 	index-=2;
			 if(index >= 0)
			 {

				System.out.println(index);
			 	tf1.setText(result[index]);
			 	tf2.setText("");
			 	next.setEnabled(true);
		  	 }
		  	 if(index == 0)
		  	 	previous.setEnabled(false);

		}

		if(ae.getSource() == next)
			 tempindex = 1;
		else
			 tempindex = 2;

		if(ae.getSource() == ok)
		{
			try
			{
				tf2.setText(analyze(tf1.getText().trim()));
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}

		if(ae.getActionCommand().equals("cut"))
			tf1.cut();
		if(ae.getActionCommand().equals("copy"))
			tf1.copy();
		if(ae.getActionCommand().equals("paste"))
			tf1.paste();
		if(ae.getActionCommand().equals("delete"))
			tf1.replaceSelection("");
	}

	public static void main(String args[])
	{
		new AnalyserTesting();
	}

	public String analyze(String input)
	{
		Methods vme = new Methods();
		VSearch search = new VSearch();

		Stack outputStack = new Stack();
		String output = "\n";

		if(search.dicSearch(input,"adjective.txt"))
			outputStack.push(input+" < adjective > ");

		else if(search.dicSearch(input,"noun.txt"))
			outputStack.push(input+" < noun > ");
		else if(search.dicSearch(input,"adverb.txt"))
			outputStack.push(input+" < adverb > ");

		else
			outputStack = vme.checkverb(input);

		while (!outputStack.empty())
			output += (outputStack.pop().toString())+"\n";

		return output;
	}
class PopupListener extends MouseAdapter
{
	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
		showPopup(e);
	}

	private void showPopup(MouseEvent e)
	{
		if (e.isPopupTrigger())
		{
			popup.show(e.getComponent(),
			e.getX(), e.getY());
		}
	}
}

}
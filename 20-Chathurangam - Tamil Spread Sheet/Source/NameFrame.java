import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;


	/**The inner class is used to set the title for the worksheet
	When double clicking the sheet title it asks for the new name.
	The new name to be given in the text field and click the change
	button.It will rename the sheet title.*/
    class NameFrame extends JFrame implements ActionListener
    {
	  /**The text field to enter the new name for the sheet title.*/
	  JTextField jtName=new JTextField(15);

	  /**The button to change the name of the sheet title.*/
	  JButton  jbChange=new JButton("ñ£ø¢Á/Change");

	  /**The panel is used to set the text field for entering the name.*/
	  JPanel jpTop=new JPanel();

	  /**The panel is used to set the button to change the name.*/
	  JPanel jpBot=new JPanel(new FlowLayout());

	  Chathurangam chathu;
	  ResourceBundle wordBundle;

	  /**The constructor for the NameFrame class is
	  invoked when the sheet is double clicked.
	  The selected tabbedpane will be sent as the parameter
	  for the constructor.*/
	  public NameFrame(Chathurangam chathu,JTabbedPane jtp)
	  {
		this.chathu=chathu;
		setSize(300,100);
		setLocation(230,250);
		setResizable(false);
		setTitle("Changing Sheet Title");

		jtName.setFont(chathu.bilingualFont);
		jbChange.setFont(chathu.bilingualFont);
		jbChange.addActionListener(this);

		jpTop.add(jtName);
		jpBot.add(jbChange);

		getContentPane().add(jpTop,"North");
		getContentPane().add(jpBot,"South");

		addKeyListener(new EscWinEvent((Component)this));
		//addKeyListener(new KeyAdapter()
		/*{
			public void keyPressed(KeyEvent ke)
			{
				int whichkey = ke.getKeyCode();

				if(ke.getKeyCode() == 27)//Esc
				{
					setVisible(false);
				}
				if(whichkey == KeyEvent.VK_ENTER)
				{
					   actionFired();
				}

			}
		});*/

	  }

	  /**The action performed method is used when
	  the change button gets the action listener for action event.*/
	  public void actionPerformed(ActionEvent ae)
	  {
		  if(ae.getSource()==jbChange)
		  {
			actionFired();
		  }

	  }
	  void actionFired()
	  {
		  			String str=chathu.jtp.getTitleAt(chathu.jtp.getSelectedIndex()).trim();
		  			boolean duplicate=false;
		  			String nameValue=jtName.getText().trim();
		  				for(int k=0;k<15;k++)
		  				{
		  					if(chathu.sheet_title[k].equals(nameValue))
		  					duplicate=true;
		  				}

		  			if(duplicate||nameValue.equals("*")||nameValue.equals("/")||nameValue.equals("\"")||nameValue.equals("?"))
		  				{
		  					wordBundle = chathu.getWordBundle();
		  					Object[] options = {wordBundle.getString("ok")	};
		  					String duplicateNotallowed = wordBundle.getString("duplicateNotallowed");
		  					String messageTitle = wordBundle.getString("messageTitle");

		  					int select = chathu.showDialog(this,"duplicateNotallowed",
		  					JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null,	 options, 0);
		  				}

		  			else
		  			{
		  				for(int i=0;i<15;i++)
		  				{
		  					if(chathu.sheet_title[i].equals(str))
		  						{
		  						str=jtName.getText().trim();
		  						if(str.length()!=0)
		  						{
		  							chathu.jtp.setTitleAt(chathu.jtp.getSelectedIndex(),str);
		  							chathu.sheet_title[i]=jtName.getText().trim();

		  							this.setVisible(false);
		  							break;
		  						}
		  					else
		  					{
		  						wordBundle = chathu.getWordBundle();
		  						Object[] options = {wordBundle.getString("ok")	};
		  						String noNullValue = wordBundle.getString("noNullValue");
		  						String messageTitle = wordBundle.getString("messageTitle");

		  						int select = chathu.showDialog(this,"noNullValue",
		  							JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,0);

		  						show();
		  					}
		  				}
		  			}

			}
	  }
    }
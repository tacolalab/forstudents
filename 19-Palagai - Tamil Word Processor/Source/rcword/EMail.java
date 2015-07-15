package rcword;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.net.URL;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *	Used to send the electronic mail. Gets the SMTP server's IP address where this
 * 	system is connected and gets the source, destination mail IDs, subject and
 *	message of the mail and a file to attach (if needed) and sends it to the
 * 	destination	addresses using that SMPT server.
 *  <p> An SMTP server is needed in order to send the mail, otherwise this word
 *	processor can't send the mail.</P>
 *
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class EMail extends JDialog  //implements ActionListener
{
    /**
	 * a reference to the main <code> Word </code> object
	 */
    Word parentword ;

    /**
	 * Appropriate font for the current local for entire GUI.
	 */
    static Font defaultfont;

	/** label to diaplay the text "SMTP server IP :" */
	JLabel hostname = new JLabel("SMTP server IP : ",SwingConstants.RIGHT);
	/** label to diaplay the text "from " */
	JLabel fromname = new JLabel("ppõ¤´ïó¢    : ",SwingConstants.RIGHT);
	/** label to diaplay the text "to " */
	JLabel toname = new JLabel("ªðÁïó¢    : ",SwingConstants.RIGHT);
	/** label to diaplay the text "subject " */
	JLabel subjectname = new JLabel("ªð£¼÷¢    : ",SwingConstants.RIGHT);
	/** label to diaplay the text "BCC " */
	JLabel bcname = new JLabel("ñ¬ø™ïèô¢     :",SwingConstants.RIGHT); //ïèô¢
	/** label to diaplay the text "CC " */
	JLabel ccname = new JLabel("ïèô¢     :",SwingConstants.RIGHT);

	/** textfield to get the SMTP host IP	*/
    JTextField hostfield = new JTextField();
    /** textfield to get the mail ID of the sender	*/
    JTextField fromfield = new JTextField();
    /** textfield to get the mail ID of the receiver.	*/
    JTextField tofield= new JTextField();
    /** textfield to get the subject of the mail.	*/
    JTextField subjectfield = new JTextField();
    /** textfield to get the mail IDs of the blind carbon copy 	*/
    JTextField bcfield = new JTextField();
    /** textfield to get the mail IDs of the carbon copy	*/
    JTextField ccfield = new JTextField();

	/** textfield to get the message of the mail.	*/
    JTextArea message = new JTextArea(6,26);

	/** button to give the attach command */
    JButton attach = new JButton("Þ¬íð¢¹");
    /** button to give the send command */
    JButton send = new JButton("ÜÂð¢¹");
    /** button to give the cancel command */
    JButton cancel = new JButton("ï¦è¢°");

    /** denotes whether any files have been attached to the mail or not */
    boolean attachfile = false;
    /** denotes whether BC mail IDs have given or not */
    boolean bcalso = false;
    /** denotes whether CC mail IDs have given or not */
    boolean ccalso = false;

	/** file to attach with the mail */
    File filetoattach ;
    /** contains CC mail IDs */
    String ccIDs = "";
    /** contains BC mail IDs */
    String bcIDs = "";

   /**
   *	Constructs a new object of <code> EMail </code> that creates the mail
   *	dialog with the current language
   *
   *	@param word a reference to the main <code> Word </code> object
   */

   public EMail(Word word)
   {
	    super(word,"Send e-mail",true);
	    parentword = word;

        defaultfont = parentword.defaultfont;

		/** panel to add the address labels and text boxes to enter the addresses*/
		JPanel addresspanel= new JPanel();
		/** panel to add the message text box */
		JPanel messagepanel = new JPanel();
		/**	buttonpanel to add the send, attach and cancel buttons*/
		JPanel buttonpanel = new JPanel();

		// sets the default font.
        hostname.setFont(defaultfont);
        fromname.setFont(defaultfont);
        toname.setFont(defaultfont);
        subjectname.setFont(defaultfont);
        bcname.setFont(defaultfont);
        ccname.setFont(defaultfont);
        send.setFont(defaultfont);
		cancel.setFont(defaultfont);
		message.setFont(defaultfont);
		attach.setFont(defaultfont);


		// sets the Foreground color
        hostname.setForeground(Color.blue);
        fromname.setForeground(Color.blue);
        toname.setForeground(Color.blue);
        subjectname.setForeground(Color.blue);
        bcname.setForeground(Color.blue);
        ccname.setForeground(Color.blue);
        attach.setForeground(Color.blue);
        send.setForeground(Color.blue);
        cancel.setForeground(Color.blue);

		// add the address labels and text boxes in the Address panel
        addresspanel.setLayout(new GridLayout(8,2));
        addresspanel.add(new JLabel(""));
        addresspanel.add(new JLabel(""));
        addresspanel.add(hostname);
        addresspanel.add(hostfield);
        addresspanel.add(fromname);
        addresspanel.add(fromfield);
        addresspanel.add(toname);
        addresspanel.add(tofield);
        addresspanel.add(subjectname);
        addresspanel.add(subjectfield);
        addresspanel.add(bcname);
        addresspanel.add(bcfield);
        addresspanel.add(ccname);
        addresspanel.add(ccfield);
        addresspanel.add(new JLabel(""));

        hostfield.setText("10.1.1.1");
        getContentPane().add(addresspanel,"North");

        JScrollPane scrollmessage =new JScrollPane(message,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        messagepanel.add(new JLabel(""));
        messagepanel.add(scrollmessage);
        getContentPane().add(messagepanel,"Center");
        buttonpanel.add(attach);
        // action to attach a file.
        ActionListener attachaction = new ActionListener()
        	{
				public void actionPerformed(ActionEvent ev)
				{	// opens the file chooser dialog and gets the file to be attached
					 JFileChooser filechooserdialog = new JFileChooser();
					 int option = filechooserdialog.showOpenDialog(parentword);

					 if(option == JFileChooser.APPROVE_OPTION)
					 {
	   	    	    	 filetoattach = filechooserdialog.getSelectedFile();
	   	    	    	 attachfile = true;
					 }
				 }
			 };
		attach.addActionListener(attachaction);

        buttonpanel.add(send);
        // action to send the mail
        ActionListener sendaction = new ActionListener()
             {
				   public void actionPerformed(ActionEvent ev)
				   {
					   try
					   {
						   String[] mail_arguments = {tofield.getText(), fromfield.getText(),hostfield.getText(),subjectfield.getText(),"false"};
							if(ccfield.getText()!= null)
							{  // get the CC mail IDs
								ccIDs = ccfield.getText();
								ccalso = true;
							}
							if(bcfield.getText()!= null )
							{	// get the BC mail IDs
								bcIDs = bcfield.getText();
								bcalso = true;
							}
							sendAttMail(mail_arguments);
					    }
						catch(Exception ee)
						{	//	mail couldn't be sent
							System.out.println(ee+"--->  ex at while sending mail: email");
							Object[] options = {parentword.wordBundle.getString("Ok")};
							Utils.showDialog(parentword,"EatMailSent",JOptionPane.OK_OPTION,
							JOptionPane.ERROR_MESSAGE, null , options, 0);
						}
					   show(false);	// close the mail dialog
				   }
			    };
		send.addActionListener(sendaction);

        buttonpanel.add(cancel);
        ActionListener cancelaction = new ActionListener()
		        {
						   public void actionPerformed(ActionEvent ev)
						   {   // clear all text boxes before close the mail dialog
							   emptyAllFields();
							   show(false);
						   }
			   };
		cancel.addActionListener(cancelaction);
        getContentPane().add(buttonpanel,"South");

        setSize(400,350);
        setResizable(false);
		setLocation(Utils.getMiddle(getSize())); // sets this dialog in the middle of the window
		setLocale();	// sets the current language to the mail UI
		pack();
        addKeyListener(new KeyAdapter()	// adds the key listener to close by the escape key
			{
				public void keyPressed(KeyEvent ke)
				{
					if(ke.getKeyCode() == 27)//Esc
					{
						setVisible(false);
					}
				}
			});
   }


   /**
	*	Gets the necessary information and sends the mail.
	*
	*	@ param	args contains the destination and source mail IDs, SMTP server IP,
	*				 subject of the mail and a boolean to state debug or not
	*/
	public void sendAttMail(String[] args)
	{
		if (args.length != 5)
		{	// incurrect call to send the mail with lessthan 5 arguments
		    return;
		}

		String to = args[0];	// destination mail IDs
		String from = args[1];	// source mail ID
		String host = args[2];	//	SMTP server IP address
		String subject = args[3];	// subject  of the mail
		boolean debug = Boolean.valueOf(args[4]).booleanValue();
		String msgText1 = message.getText();	// message of the mail
		if(parentword.workspace.getText()!= null)
		{	//	adds the text in the workspace of the word processor after the message of the mail.
			msgText1 = msgText1+" \n\n Text in the Workspace: \n"+parentword.workspace.getText();
		}

		// creates some properties and get the default Session
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);

		try {
		    // create a message
		    MimeMessage msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress(from));
		    InternetAddress[] address = {new InternetAddress(to)};
		    msg.setRecipients(Message.RecipientType.TO, address);
		    msg.setSubject(subject);

		    if(ccalso)
		    {	// adds CC mail IDs if entered
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccfield.getText(),false) );
				ccalso = false;
			}
            if(bcalso)
		    {	// adds BC mail IDs if entered
				msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcfield.getText(),false) );
				bcalso = false;
			}

		    // creates and fill the first message part
		    MimeBodyPart mbp1 = new MimeBodyPart();
		    // attaches the first message part
		    mbp1.setText(msgText1);
		    // creates the Multipart and its parts to it
		    Multipart mp = new MimeMultipart();
		    // adds the message as the body part of the mail
		    mp.addBodyPart(mbp1);

            if(attachfile)	// if file has to be attached with the mail
            {
					// creates the second message part
					MimeBodyPart mbp2 = new MimeBodyPart();

						// attaches the file to the message
					FileDataSource fds = new FileDataSource(filetoattach);
					mbp2.setDataHandler(new DataHandler(fds));
					mbp2.setFileName(fds.getName());

					// attaches the second message part
					mp.addBodyPart(mbp2);
					attachfile = false;
					/*JOptionPane.showMessageDialog(this,"File attached",
											         "Confermation",
						                             JOptionPane.DEFAULT_OPTION);
				     */
		    }

		    // adds the Multipart to the message
		    msg.setContent(mp);

		    // sets the Date: header
		    msg.setSentDate(new Date());

		    // sends the message
		    try
		    {
		    	Transport.send(msg);
			}
			catch(SendFailedException ee)
			{
				 System.out.println(ee+"---> ex at sending : email");
				 ee.printStackTrace();

				 //System.out.println(" Invalid address:");
				 /*Address invalid_add[] = ee.getInvalidAddresses();
				 for(int ii = 0; ii<invalid_add.length;ii++)
				 {
				 	System.out.println(" "+ii+" :"+invalid_add[ii]);
				 } */

				 //System.out.println(" valid but not sent address:");
				 /* Address validnotsent_add[] = ee.getValidUnsentAddresses();
				 for(int ii = 0; ii<validnotsent_add.length;ii++)
				 {
				 	System.out.println(" "+ii+" :"+validnotsent_add[ii]);
				 } */

				 JOptionPane.showMessageDialog(null, "ex while sending the mail.");
			 }
			 emptyAllFields(); // clears all the text boxes

		}
		catch (MessagingException mex)
		{
		    System.out.println(mex+"\n-----> e at EMail");
		    Exception ex = null;
		    if ((ex = mex.getNextException()) != null)
		    {
				System.out.println(ex+"\n---> ex in sentattfile :Email ");
				Object[] options = {parentword.wordBundle.getString("ok")};
				Utils.showDialog(parentword,"E at Mail Sent",JOptionPane.OK_OPTION,
				JOptionPane.ERROR_MESSAGE, null , options, 0);
		    }
		}
    } // end of sendmail

	/** Used to clear all input textfields. */
    public void emptyAllFields()
    {
			hostfield.setText("");
			fromfield.setText("");
			tofield.setText("");
			subjectfield.setText("");
			bcfield.setText("");
			ccfield.setText("");
			message.setText("");
	}

	/**
	 * Sets the current locale to the user interface.
	 */
	public void setLocale()
	{
			String temp = "    : ";
			hostname.setText(parentword.wordBundle.getString("SMTP")+temp);
			fromname.setText(parentword.wordBundle.getString("From")+temp);
			toname.setText(parentword.wordBundle.getString("To")+temp);
			subjectname.setText(parentword.wordBundle.getString("Subject")+temp);
			bcname.setText(parentword.wordBundle.getString("Bcc")+temp);
			ccname.setText(parentword.wordBundle.getString("Cc")+temp);

			attach.setText(parentword.wordBundle.getString("Attach"));
			send.setText(parentword.wordBundle.getString("Send"));
			cancel.setText(parentword.wordBundle.getString("Cancel"));
	}


}







/*
private void trace(String s)
  {
      tracer.append(s + "\n");
      tracer.setCaretPosition(tracer.getDocument().getLength());
  }


  /**  this method was designed using sockets
       and this wouldn't send the body of the mail /
  public boolean sendMail(String host,String from, String to, String subject)
  {
		//System.out.println(" with in amil");
		System.out.println( " host"+host);

		//public boolean sendMail(String host, String from, String to, String subject)
		// {

		//String host = "10.1.1.1";
		//String from = "k2000@yahoo.com";
		//String to = "k_saravan2000@yahoo.com";
		// String subject = "testing from my program suvadi";

		Socket smtpPipe;
		BufferedReader in;
		InetAddress ourselves;
		OutputStreamWriter out;

		try
		{
		ourselves = InetAddress.getLocalHost();
		} catch (UnknownHostException uhe) { return false; }

		tracer.setText("");

		int index = host.indexOf(':');
		int port = 25;
		if (index != -1)
		{
		port = Integer.parseInt(host.substring(index + 1));
		host = host.substring(0,index);
		}

		try
		{
	        smtpPipe = new Socket(host, port);
	        if (smtpPipe == null)
	          return false;

	        in = new BufferedReader(new InputStreamReader(smtpPipe.getInputStream()));
	        out = new OutputStreamWriter(smtpPipe.getOutputStream());
	        if (in == null || out == null)
	          return false;

	        String response, command;
	        trace(response = in.readLine());
	        if (!response.startsWith("220"))
	          return     false  ;    //error("serverdown");

	        command = "HELO " + ourselves.getHostName();
	        out.write(command + "\r\n");
	        out.flush();
	        trace(command);
	        trace(response = in.readLine());
	        if (!response.startsWith("250"))
	          return         false;        //error("badhost");

	        command = "MAIL FROM:<" + from + ">";
	        out.write(command + "\r\n");
	        out.flush();
	        trace(command);
	        trace(response = in.readLine());
	        if (!response.startsWith("250"))
	          return  false;          //error("badsender");

	        command = "RCPT TO:<" + to + ">";
	        out.write(command + "\r\n");
	        out.flush();
	        trace(command);
	        trace(response = in.readLine());
	        if (!response.startsWith("250"))
	          return false;          //error("badrecepient");

	        out.write("DATA\r\n");
	        out.flush();
	        trace("DATA");
	        trace(response = in.readLine());
	        if (!response.startsWith("354"))
	          return false;          //error("badmsg");
	        trace("[Sending mail...]");

	        out.write("To: <" + to + ">");
	        out.write("\r\n");
	        out.write("From: <" + from + ">");
	        out.write("\r\n");
	        out.write("Subject: " + subject);
	        out.write("\r\n");
	       // out.write("X-Mailer: Jext " + Jext.BUILD);
	        out.write("\r\n");
	        out.write("\r\n");

	        // inner comment starts here
	        try
	        {
	          String text;
	          Document doc = textArea.getDocument();
	          Element map = doc.getDefaultRootElement();
	          int total = map.getElementCount();

	          for (int i = 0; i < total; i++)
	          {
	            Element lineElement = map.getElement(i);
	            int start = lineElement.getStartOffset();
	            int end = lineElement.getEndOffset() - 1;
	            end -= start;

	            text = doc.getText(start, end);
	            if (text.equals("."))
	              text = "!";
	            out.write(text);
	            out.write("\r\n");
	            trace("[" + (i + 1) * 100 / total + "%]");
	           }
	        } catch (BadLocationException ble) { }

            // inner comment ends here

            trace("saravanan");

	        out.write(".\r\n");
	        out.flush();
	        trace(".");
	        trace(response = in.readLine());
	        if (!response.startsWith("250"))
	          return false ;   //error("badmsg");

	        out.write("QUIT");
	        trace("QUIT");

	        smtpPipe.close();
	        smtpPipe = null;
	    } catch (IOException ioe) { return false; }

	      return true;

  }


   /** this method will send the mail with body also using
    *  the javamail package
   public void postMail( String recipients[ ], String subject, String message , String from) throws MessagingException
   {
	        //System.out.println(" with in postmail");
	        try
	        {
				boolean debug = false;

				 //Set the host smtp address
				 Properties props = new Properties();
				 props.put("mail.smtp.host","10.1.1.1");// "smtp.jcom.net");

				// create some properties and get the default Session
				Session session = Session.getDefaultInstance(props, null);
				session.setDebug(debug);

				// create a message
				Message msg = null;
				try
				{
					 msg = new MimeMessage(session);
				}
				catch(Exception ee)
				{ System.out.println(ee+"---> ex the messafge : email");
			    }


				// set the from and to address
				InternetAddress addressFrom = new InternetAddress(from);
				msg.setFrom(addressFrom);

				InternetAddress[] addressTo = new InternetAddress[recipients.length];
				for (int i = 0; i < recipients.length; i++)
				{
					addressTo[i] = new InternetAddress(recipients[i]);
				}
				msg.setRecipients(Message.RecipientType.TO, addressTo);


				// Optional : You can also set your custom headers in the Email if you Want
				msg.addHeader("MyHeaderName", "myHeaderValue");

				// Setting the Subject and Content Type
				msg.setSubject(subject);
				msg.setContent(message, "text/plain");
				Transport.send(msg);
		    }
		    catch(Exception e)
		    {
				System.out.println(e+ "----> ex at posting the main: email");
				e.printStackTrace();
			}
			//System.out.println(" end of post mail");
	} // end of postmail

*/
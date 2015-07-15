
package rcword;

import java.io.*;
import java.awt.*;
import java.net.*;
import java.util.*;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.awt.event.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.border.*;
import javax.swing.text.html.*;
import javax.swing.text.rtf.*;
// import java.awt.HeadlessException;

/**
 *	Used to perform all file operations.
 *
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class FileOperation
{
	/** a reference to the main <code> Word </code> object */
	Word parentword;

	/**	a linked list which contains the list of recently used files.*/
	LinkedList recentfilesList= new LinkedList();

	/**	position in the file menu where the recent files have to be added.*/
	final int recentFilePosition = 11;

	/**	maximum number of recent files in the file menu.*/
	final int no_RecentFiles = 5;

	/**	Contains the word bundle for the current local. */
	ResourceBundle wordBundle;

    /**	temporary file name.*/
	String tempfilename;

	int currentOS;

	/*** To set the current directory in file chooser dialog to user home */
	private File currentDirectory = new File(System.getProperty("user.home"));

    /**
     * Constructs an object of <code> FileOperation </code> with a reference
     * to the main <code> Word </code> object.
     *
     * @param word a reference to the main <code> Word </code> object
     */
	public FileOperation(Word word)
	{
	   	     parentword = word;
	   	     currentOS = word.currentOS;
	}


    /**
    * Used to get the main frame of the application.
    */
    protected Frame getFrame()
    {
    	 for (Container parent = parentword.getContentPane().getParent(); parent != null; parent = parent.getParent())
	           if (parent instanceof Frame)
						return (Frame) parent;
    	  return null;
    }

    /**
    *	Does the new file operation.
    */
    public void newFile(boolean confirm)
	{
		if(confirm)
		{
			if(!confirmClose())
				return;
		}

		parentword.workspace.setText(" ");
		if(parentword.workspace.getDocument() != null)
		{
			  parentword.workspace.getDocument().removeUndoableEditListener(parentword.undoHandler);
			  parentword.workspace.getDocument().removeDocumentListener(parentword.docHandler);
		}
//		parentword.workspace.setEditorKit(parentword.rtfkit);
		parentword.dsd_stydoc = new DefaultStyledDocument(parentword.stycont); //create the new document
		parentword.workspace.setDocument(parentword.dsd_stydoc); // adds the document
		parentword.workspace.getDocument().addUndoableEditListener(parentword.undoHandler); // adds the undo listener
		parentword.workspace.getDocument().addDocumentListener(parentword.docHandler);
		parentword.resetUndoManager(); // refreshes the undo manager
		parentword.setDefaultFont();   // sets the default font for the application
		parentword.bults.bulletstate = false;	// sets the bullet state as ON state
		parentword.workspace.setMargin( new Insets(2,2,2,2));
		parentword.setTitle("Untitled");	// sets the file name of the new file as "Untitled"
		parentword.current_file_name = "Untitled";
		parentword.caretstart = -1; 	// sets the cursor's selection starting position as -1
		parentword.caretend = -1;		// sets the cursor's selection end position as -1
		parentword.textChanged = false;
	 }

    /**
     *  Does the open file operation.
     */
	 public  void openfile()
     {
			try
			{
				if(!confirmClose())
				{
					return;
				}
				parentword.bults.bulletstate = false;	// sets the bullet state as ON
				parentword.bults.inactivateBullets();

				//JFileChooser fileChooser = new AFileChooser();
				JFileChooser fileChooser = new JFileChooser();

				fileChooser.setCurrentDirectory(currentDirectory);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				SimpleFilter rtfFilter = new SimpleFilter("rtf", "RichTextFormat");
				SimpleFilter htmlFilter = new SimpleFilter("html" ,"WebFiles");
				SimpleFilter textFilter = new SimpleFilter("txt","TextFiles");

				setLanguage(fileChooser);	// sets the current language for the file chooser

				fileChooser.addChoosableFileFilter(rtfFilter);
				fileChooser.addChoosableFileFilter(htmlFilter);
				fileChooser.addChoosableFileFilter(textFilter);

				if(currentOS == 1)
				{
					SimpleFilter docFilter = new SimpleFilter("doc","DocFiles");
					SimpleFilter pdfFilter = new SimpleFilter("pdf","PDFFiles");
					SimpleFilter psFilter = new SimpleFilter("ps","PSFiles");
					fileChooser.addChoosableFileFilter(docFilter);
					//fileChooser.addChoosableFileFilter(pdfFilter);
					//fileChooser.addChoosableFileFilter(psFilter);
				}
				else //if( currentOS == 2)
					System.out.println(" The current OS is not Windows.");

				fileChooser.setFileFilter(rtfFilter);
				fileChooser.addKeyListener(new KeyAdapter()
					{		// adds the key listener to close the dialog while escape key is entered.
						public void keyPressed(KeyEvent ke)
						{
							if(ke.getKeyCode() == 27)//Esc
							{
								ke.getComponent().setVisible(false);
							}
						}
					});

				int result = fileChooser.showOpenDialog(getFrame());
				if (result == JFileChooser.APPROVE_OPTION)
				{
					File filename = fileChooser.getSelectedFile();
					if (filename == null)
					{
						return;
					}

					File filetoopen = new File(filename.getAbsolutePath());
					parentword.textChanged = false;
					open_file_appropriately(filetoopen,false);	// call method to open the file appropriately.

					//update current directory
					currentDirectory = fileChooser.getCurrentDirectory();
				}
				parentword.textChanged= false;
			}
			catch(Exception e9)
			{
				System.out.println(e9+"\n------>ex at file opening : FileOperation ");
			}
	  } // end of open file


	  /**
	   *	Opens the required file in an appropriate way for ".rtf",".html" and ".txt".
	   * @ param	filetoopen file which has to be opened.
	   * @ param	is_this_from_recentfile shows whether it is called form the
	   *									recentfile or from the filechoolser dialog.
	   */
      public void open_file_appropriately(File filetoopen, boolean is_this_from_recentfile)
      {
		  	if(parentword.textChanged)
		  		if(!confirmClose())
						return;
			if (filetoopen.exists())
			{
				parentword.workspace.setText("");
				getFrame().setTitle(filetoopen.toString());
				parentword.current_file_name = filetoopen.toString();
				parentword.caretstart = -1;
	    		parentword.caretend = -1;

				// check whether this file already exists in the recent file list.
				int removeIndex = checkDuplicateRFile(filetoopen.toString());
				if(  removeIndex !=  -1 )
				{   // if so remove the duplicate.
					parentword.menubar.getMenu(0).remove(recentFilePosition+removeIndex-1);
					recentfilesList.remove(no_RecentFiles - removeIndex);
				}

				// adds this file in the recent files list.
				recentfilesList.add(filetoopen.toString());
				// updates the file menu with this file.
				updateFileMenu(filetoopen.toString());

                // check the recent files count if it exceed the maximum limit remove the front element.
				if(recentfilesList.size()>no_RecentFiles)
				{
					recentfilesList.remove(0);
					parentword.menubar.getMenu(0).remove(recentFilePosition+no_RecentFiles);
				}

				// check the file extension and open appropriately
				if(filetoopen.toString().endsWith("rtf") )
				{
					openRTFFile(filetoopen.toString());
					return;
				}
				if(filetoopen.toString().endsWith("doc"))
				{
					openDocFile(filetoopen.toString());
					return;
				}
				if(filetoopen.toString().endsWith("html") || filetoopen.toString().endsWith("HTML") ||  filetoopen.toString().endsWith("htm")  ||  filetoopen.toString().endsWith("HTM") )
				{
					openHTMLFile("file:"+filetoopen.toString());
					return;
				}
				if(filetoopen.toString().endsWith("pdf"))
				{
					openPDFFile(filetoopen.toString());
					return;
				}
				if(filetoopen.toString().endsWith("ps"))
				{
					openPSFile(filetoopen.toString());

					return;
				}

                // if the file is other than .rtf and .html open as an .txt file
                openTextFile(filetoopen.toString());
				parentword.textChanged = false;
			}
			else
			{   // if the file doesn't exists inform the user.
				ResourceBundle wordbundle = parentword.wordBundle;
				Object[] options = {wordbundle.getString("Ok")};
				Utils.showDialog(parentword,"FileNotFound",JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null , options, 0);
			}

			//System.out.println(" open finished");
	  }

      /**
      * Opens a rich text file.
      *
      * @param filename name of the file to be opened
      */
      public void openRTFFile(String filename)
      {
		   tempfilename = filename;
           // fix the rtf editor kit before opening the file
           //parentword.workspace.setEditorKit(parentword.rtfkit);
           parentword.caretstart = -1;
	       parentword.caretend = -1;

     	   Thread runner = new Thread()
  			   {
  				   public void run()
  			  	  {
  						try
  						{
  							InputStream in = new FileInputStream(tempfilename); // open a stream to read the file
  							parentword.dsd_stydoc = new DefaultStyledDocument(parentword.stycont); //create the new document
  							parentword.rtfkit.read(in, parentword.dsd_stydoc, 0); // read the document
  							parentword.workspace.setDocument(parentword.dsd_stydoc);
  							parentword.workspace.getDocument().addUndoableEditListener(parentword.undoHandler); // adds undo listener
  							parentword.workspace.getDocument().addDocumentListener(parentword.docHandler);
	    	                parentword.resetUndoManager();
  							in.close(); // closes the file stream after reading
  						}
  						catch (Exception ex)
  						{
  							ex.printStackTrace();
  						}
  			        }
  		         };
           runner.start();
       }// end of openRTFFile

	/**
	* Opens a Word Document file as a text file.
	*
	* @param filename name of the file to be opened
	*/
	public void openDocFile(String filename)
	{
		tempfilename = filename;
		String fileName = filename.substring(0,filename.indexOf(".doc"));
		DocToTextConverter docTOtext = new DocToTextConverter(filename, fileName + ".txt", fileName + "links.txt");
		int check = docTOtext.convert_doc_txt();
		openTextFile(fileName+".txt");
	}// end of openDocFile


	/**
	* Opens a PDF file as a text file.
	*
	* @param filename name of the file to be opened
	*/
	public void openPDFFile(String filename)
	{
		try
		{
			tempfilename = filename;
			String fileName = filename.substring(0,filename.indexOf(".pdf"));
			Process pros = null;

			String path = System.getProperty("user.dir");
			path = path+"\\rcword\\pdftotext ";

			pros = Runtime.getRuntime().exec(path+ filename);
			openTextFile(fileName+".txt");
		}
		catch(Exception e)
		{
			System.out.println(e+"\n-----> e at pdf conversion.");
			e.printStackTrace();
		}
	}// end of openPDFFile



	/**
	* Opens a PS file as a text file.
	*
	* @param filename name of the file to be opened
	*/
	public void openPSFile(String filename)
	{
		try
		{
			tempfilename = filename;
			String fileName = filename.substring(0,filename.indexOf(".ps"));
			Process pros = null;

			String path = System.getProperty("user.dir");
			path = path+"\\rcword\\pstotxt3 -output ";

			pros = Runtime.getRuntime().exec( path + fileName + ".txt " + tempfilename);
			openTextFile(fileName+".txt");
		}
		catch(Exception e)
		{
			System.out.println(e+"\n-----> e at ps conversion.");
		}
	}// end of openPDFFile

	/**
	* Opens a text file.
	*
	* @param filename name of the file to be opened
	*/
	public void openTextFile(String filename)
	{
		tempfilename = filename;

		try
		{
			File fileToOpen = new File(filename);
			Document oldDoc =parentword.workspace.getDocument();
			if(oldDoc != null)
			{
				oldDoc.removeUndoableEditListener(parentword.undoHandler);
				oldDoc.removeDocumentListener(parentword.docHandler);
			}

			try
			{

				parentword.workspace.setDocument(parentword.dsd_stydoc);
				parentword.setDefaultFont();
				Thread loader = new FileLoader(parentword,fileToOpen, parentword.workspace.getDocument());
				loader.start();
			}
			catch(Exception e88)
			{
				System.out.println(e88+"\n---->ex at file opening :FileOperation");
				e88.printStackTrace();
			}
		}
		catch(Exception e)
		{
			System.out.println(e+"\n -----> ex at text file opening. \n File may not be available,.");
		}
		//System.out.println(" opending finished >");
	}// end of openTextFile
	/**
	* Opens an html file.
	*
	* @param filename name of the file to be opened
	*/
	public void openHTMLFile(String filename)
	  {
				   ////System.out.println(" openhtml ffile : "+filename);
		           tempfilename = filename;

				try{
		           parentword.workspace.setEditorKit(parentword.htmlkit);
		           //parentword.dsd_stydoc = new DefaultStyledDocument(parentword.stycont);
		           //parentword.workspace.setDocument(parentword.dsd_stydoc);
		          //try{
					 parentword.workspace.setEditable(false); 					// this is to open an html file  and listen for hyperlink
					 parentword.workspace.setPage(filename);
				     //parentword.workspace.read( new FileReader(filename), parentword.htmlkit);
				     parentword.workspace.addHyperlinkListener(new HyperlinkListener() 					  // listener to activate the hyperlinks
				           {
							    public void hyperlinkUpdate( HyperlinkEvent ev)
							    {
							         try
								     {
										  if(ev.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
										  {
										      parentword.workspace.setPage(ev.getURL());
										  }
								     }
								     catch(IOException ex)
								     {
										  System.out.println(ex+"----> ex at hyperlink event: fileoperaion");
									  }
								  }
							});
			       }
			       catch(Exception e)
			       {
					   System.out.println(e+"----> er at htmlfinel: fileoperation");
				    }

		}// end of openhtmlFile

	/**
	* Used to save the current file.
	*/

   	public void savefile(boolean confirm)
    {
			/*if(confirm)
			{
				if(!confirmClose())
				{
					return;
				}
			}
			*/
			parentword.textChanged = false;
            if(! parentword.current_file_name.equals("Untitled"))			// if the file already saved
            	saveWithoutFilechooser(new File(parentword.current_file_name));
			else  // if the file was not yet saved.
			{
				// show the file chooser and allow the user to save the file in desired place
				JFileChooser filechooserdialog = new JFileChooser();

				filechooserdialog.setCurrentDirectory(currentDirectory);
				filechooserdialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
				filechooserdialog.setAcceptAllFileFilterUsed(false);

				SimpleFilter rtfFilter = new SimpleFilter("rtf", "RichTextFormat");
				SimpleFilter textFilter = new SimpleFilter("txt","TextFiles");

				setLanguage(filechooserdialog);

				filechooserdialog.addChoosableFileFilter(rtfFilter);
				filechooserdialog.addChoosableFileFilter(textFilter);

				filechooserdialog.setFileFilter(rtfFilter);
				// add key listener to close the filechooser when escape key was entered.
				filechooserdialog.addKeyListener(new KeyAdapter()
					{
						public void keyPressed(KeyEvent ke)
						{
							if(ke.getKeyCode() == 27)//Esc
							{
								System.out.println("sssssssssss");
								ke.getComponent().setVisible(false);
							}
						}
					});


				 int option = filechooserdialog.showSaveDialog(parentword);

				 if(option == JFileChooser.APPROVE_OPTION)
				 { // of the option is OK
						 File filetosave = filechooserdialog.getSelectedFile();

						 String temp_filename = filetosave.toString().toLowerCase();

						 if(temp_filename.indexOf(".")==-1)
						 { 	// if any extension is present for this file
							 SimpleFilter filefilter = null;
							 String extn =null;
							 try
							 {
								 filefilter = (SimpleFilter)filechooserdialog.getFileFilter();
								 extn = filefilter.getExtension(); // gets the file extension
							 }catch(Exception ee)
							 {
								 System.out.println(ee+"\n----> e at filefilter :fileoperation");
								 return;
							 }

							 if(extn.equals(".rtf"))  // if the file is ritch text file
							 	temp_filename = temp_filename+".rtf";
							 else if (extn.equals(".txt")) // if the file is text file
							 	temp_filename = temp_filename+".txt";
							filetosave = new File(temp_filename);  // creates the file with the correct extension.
						 }



						 if(filetosave.exists() )
						 { // if the file already exits.
							ResourceBundle wordBundle = parentword.wordBundle;
							Object[] options = {wordBundle.getString("Yes"),
												wordBundle.getString("No")};
							// gets the user option to overwrite the existing or not
							int overwrite = Utils.showDialog(filechooserdialog,"FileOverwriteCon",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
								options, 1);
							if (overwrite == JOptionPane.CANCEL_OPTION ||
								overwrite == JOptionPane.NO_OPTION)
							{
								return;
							}
						 }

						 // update the current directory
						 currentDirectory = filechooserdialog.getCurrentDirectory();

						 parentword.current_file_name = filetosave.toString();
						 // adds this file in the recent files list.
						 recentfilesList.add(filetosave.getAbsolutePath().toString());
						 // updates the file menu with this file.
						 updateFileMenu(filetosave.getAbsolutePath().toString());

						 // check the recent files count if it exceed the maximum limit remove the front element.
						 if(recentfilesList.size()>no_RecentFiles)
						 {
							recentfilesList.remove(0);
							parentword.menubar.getMenu(0).remove(recentFilePosition+no_RecentFiles);
						 }


						 if(temp_filename.endsWith(".rtf"))
						 {// calls a method to save the file as ritch text file
							  saveRTFFile(filetosave.toString());
							  getFrame().setTitle(filetosave.toString());
							  return;
						 }
						 else if (temp_filename.endsWith(".txt"))
						 { // calls a method to save the file as text file
							 saveTextFile(filetosave);
							 getFrame().setTitle(filetosave.toString());
							 return;
						 }
						 else
						 {   // calls a method to save default mode
							 saveTextFile(filetosave);
							 getFrame().setTitle(filetosave.toString());
							 return;
						}
				}
				else if(option == JFileChooser.CANCEL_OPTION)
				{
					parentword.textChanged = true;
					return;
				}
			} // end of else
	   } //end of savefile


	/**
	* Used to save a file as rich text file.
	*
	* @param filename name of the file to be saved
	*/
      public void saveRTFFile(String filename)
      {
		    tempfilename = filename;
            Thread runner = new Thread()
		           {
		              public void run()
		              {
		 					try
		 					{   // creates a file stream to save the file
		 						OutputStream out = new FileOutputStream(tempfilename);
		 						// writes the file using the default styled document and rtf editor kit.
		 						parentword.rtfkit.write(out, parentword.dsd_stydoc, 0, parentword.dsd_stydoc.getLength());
		 						out.close(); // close the file stream
		 					}
		 					catch (Exception ex)
		 				    {
		 				    	   System.out.println(ex+"\n----->ex at saving rtf file : FileOperation");
		 				    	   ex.printStackTrace();
		 					}
		               }
		            };
            runner.start();
	  } // end of saveRTFFile


	  /**
	  * Used to save an html file.
	  *
	  * @param filename file name to be saved.
	  */
      public void saveHTMLFile(String filename)
	  {
	  		    tempfilename = filename;
	              Thread runner = new Thread()
	  		           {
	  		              public void run()
	  		              {
	  		 					try
	  		 					{
	  		 						OutputStream out = new FileOutputStream(tempfilename);
	  		 						parentword.htmlkit.write(out, parentword.dsd_stydoc, 0, parentword.dsd_stydoc.getLength());
	  		 						out.close();
	  		 					}
	  		 					catch (Exception ex)
	  		 				    {
	  		 					   ex.printStackTrace();
	  		 					}
	  		               }
	  		           };
	              runner.start();
	  	  } // end of saveHTMLFile





	/**
	* Used to do the save as operation.
	*/
	  public void saveAsfile()
      {   // simply set the current file is untitled and call the save method.
        	 parentword.current_file_name = "Untitled";
			 savefile(true);

	   } //end of saveAsfile


   /**
   * Used to save the current file as html file
   *
   */
   public void saveAsWebPage()
   {
	   try
	   {

			final HTMLEditorKit htmlkit = new HTMLEditorKit();
			//parentword.workspace.setEditorKit(parentword.htmlkit);
			JTextPane tempworkspace = new JTextPane();
			tempworkspace.setEditorKit(htmlkit);
			StyleContext sty_cnt = new StyleContext();
			final DefaultStyledDocument sty_doc = new DefaultStyledDocument(sty_cnt);
			tempworkspace.setDocument(sty_doc);
			tempworkspace.setText(parentword.workspace.getText());
			final JFileChooser filechooser = new JFileChooser();
			filechooser.setCurrentDirectory(new File("."));
			SimpleFilter  htmlFilter = new SimpleFilter("html", "html Documents");
			filechooser.setFileFilter(htmlFilter);
			filechooser.setAcceptAllFileFilterUsed(false);

            Thread runner = new Thread() { public void run() { if (
			filechooser.showSaveDialog(parentword) != JFileChooser.
			APPROVE_OPTION) return; //WordProcessor.this.repaint(); File
			File fChoosen = filechooser.getSelectedFile();

                   // Recall that text component read/write operations are
                   // thread safe. Its ok to do this in a separate thread.
            		try
            		{
              			OutputStream out = new FileOutputStream(fChoosen);
              			htmlkit.write(out, sty_doc, 0, sty_doc.getLength());
              			out.close();
            		}
                    catch (Exception ex)
                    {
                    	System.out.println(ex+"----> exx : fileoperation");
              	   		//ex.printStackTrace();
            		}

            		// Make sure chooser is updated to reflect new file
            		filechooser.rescanCurrentDirectory();
            		//WordProcessor.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          	    }
             };
             runner.start();
	   	  }
	  	  catch(Exception e)
	  	  {
	  	   	   System.out.println(e+"----> yew edddd : fileoperation");
	  	  }

     } // end of save as web page

 	/**
 	* Used to update the file menu. It receives a recently accessed file name and adds this
 	* file name in the file menu's recent files list.
 	*
 	* @param filename name of the file to be added in the file menu's recent files list.
 	*/
     public void updateFileMenu(String filename)
     {			// get the file menu
			JMenu tempmenu = parentword.menubar.getMenu(0);
			JMenuItem tempmenuitem;
			try
			{  	// creates a menu item with this file name
				tempmenuitem = new JMenuItem(filename);
				tempmenuitem.setFont(parentword.defaultfont);
				tempmenu.add(tempmenuitem,recentFilePosition); //adds this menu item with the file menu
         		ActionListener  menulistener = new ActionListener() 				// adds an action listener for this menuitem
						{
							  public void actionPerformed(ActionEvent e)
							  { 	// if this menu button was clicked
							  		//parentword.workspace.setText(" ");
  									String filetoopen  = ( (JMenuItem)e.getSource() ).getText();
									File file = new File(filetoopen);
									/* calls the file open method to open this recently accessed file
									    menu item, the boolean value true is passed to indicate that
									    this call is from file menu item and so do not open the file
									    chooser to open the file.
									    */
									open_file_appropriately(file,true);
							   }
						};
				tempmenuitem.addActionListener(menulistener);
			}
			catch(Exception e3)
     		{
					System.out.println(e3+"---->  no addition : fileoperation");
			}

      }// end of updateFileMenu

	/**
	* Used to check whether a file is already in the recent files list or not.
	* <p> If present, it returns the index of this file in the recent files list.
	*  Otherwise returns -1.
	*
	* @param filename name of the file which has to be checked in the recent files
	*		   list for duplication
	* @return int	index of the file passed in the recent files list.
	*/
      public int checkDuplicateRFile(String filename)
      {
		   if( recentfilesList.size() == 0)
		   {  // if the recent files list is empty
		       return -1 ;
		   }
		   else
		   { // searches the recent files list with the file passed to this method.
			    for(int i=0;i<recentfilesList.size();i++)
			    {
					if(filename.equals(recentfilesList.get(i)))
					{
					    return no_RecentFiles-i;
					   }
				 }
		         return -1; 	// file not present in the recent files list.
			 }
      }

	/**
	* Writes the name of the recently accessed files in the file "_Recent.tmp".
	* This method is called before closing the application in order to
	* save the name of the recently accessed files in a file. So that,
	* while next time opening the recently accessed files can be read
	* from this file.
	*/
      public void addinRecentFile() throws IOException
      {
		  try
		  {	// opens a file stream to write the names of the recently accessed files in the file "_Recent.tmp"
		  			ObjectOutputStream p = new ObjectOutputStream(new FileOutputStream("_Recent.tmp"));//ostream);
				    for( int i=0; i<recentfilesList.size() ; i++)
                    {
						p.writeObject(recentfilesList.get(i));
					}
					p.flush();
			}
			catch(FileNotFoundException e)
			{
					System.out.println(e+"--->   file not found : fileoperaion");
			}

 	  } // addinRecentFile


	/**
	* Reads the name of the recently accessed files from the file "_Recent.tmp" which contains
	* the list of recently accessed file names and updates the file menu with this recently
	* accessed file names as menu items.
	* Reads the names of the recently accessed files from the file "_Recent.tmp" which contains
	* the list of recently accessed file names and updates the file menu with this recently
	* accessed file names as menu items.
	* <p> This method will be called when this application is opened in order to know the recently
	* accessed files before it was closed last time.
	*/
      protected void updatemenubar()
	  { 	// gets the file menu to update it.
			JMenu tempmenu = parentword.menubar.getMenu(0);
			JMenuItem tempmenuitem;
			String filename="";
			try
			{	// creates a file stream to open the file "_Recent.tmp"
				ObjectInputStream ip = new ObjectInputStream(new FileInputStream("_Recent.tmp"));
                for(int i=0; i<no_RecentFiles ; i++ )
                {
					    try
					    {
						     filename = 	(String)ip.readObject();
						 }
						 catch(Exception ee)
						 {
							 System.out.println(ee+" \n----> updatemenubar,FileOperation ");
							 break;
						 }

						tempmenuitem = new JMenuItem(filename);	// creates a menu item with this file name.
						tempmenuitem.setFont(parentword.defaultfont);
						recentfilesList.add(filename);// adds this file name in the recent files list
						tempmenu.add(tempmenuitem,recentFilePosition); // adds this menu item in the file menu

						ActionListener  menulistener = new ActionListener()
								{
									  public void actionPerformed(ActionEvent e)
									  {
											//parentword.workspace.setText("");
											//System.out.println(" in recent file :"+parentword.textChanged);
											String filetoopen  = ( (JMenuItem)e.getSource() ).getText();

											File file = new File(filetoopen);
											open_file_appropriately(file,true);
									   }
								};
						tempmenuitem.addActionListener(menulistener);
				}
			}
			catch(Exception e3)
			{
					System.out.println(e3+"---->  no addition : fileoperation");
			}
     } // end of updatemenubar

	/**
	* Used to set the UI of the file chooser with the current language.
	*/
     public void setLanguage(JFileChooser fc)
	 {	// gets the wordbundle of the current language.
	 		wordBundle = parentword.wordBundle;
	 		fc.setFont(parentword.defaultfont);
	 		for(int i=0; i < fc.getComponentCount();i++)
	 			if (fc.getComponent(i) instanceof JPanel)
	 				setJFCButtonText((JPanel)fc.getComponent(i),wordBundle);
	 }

	 /**
	 * Used to set the UI of the components in a panel with the
	 * current language.
	 *
	 * @param jp panel whose components has to be updated.
	 * @param r words bundle for the current language.
	 */
	 private void setJFCButtonText(JPanel jp, ResourceBundle r)
	 {
		// gets each component in the panel
	 	for(int j=0; j < jp.getComponentCount();j++)
	 	{
	 		Component bo = jp.getComponent(j);
	 		// if this also a panel then call this method again with this panel
	 		if (bo instanceof JPanel)
	 			setJFCButtonText((JPanel)bo,r);
	 		else if (bo instanceof JButton)
	 		{
				JButton b = (JButton)bo;
				if(b.getText() != null)
	 			{
	 				if(b.getText().equals("Open"))
	 				{
						b.setText(r.getString("Open"));
	 					b.setToolTipText(r.getString("OpenSelectedFile"));
					}
	 				else if(b.getText().equals("Save"))
	 				{
						b.setText(r.getString("Save"));
	 					b.setToolTipText(r.getString("SaveSelectedFile"));
					}
	 				else if(b.getText().equals("Cancel"))
	 				{
	 					b.setText(r.getString("Cancel"));
	 					b.setToolTipText(r.getString("AbortFileChooserDialog"));
					}
					else if(b.getToolTipText().equals("Up One Level"))
						b.setToolTipText(r.getString("UpOneLevel"));
					else if(b.getToolTipText().equals("Home"))
						b.setToolTipText(r.getString("Home"));
					else if(b.getToolTipText().equals("Create New Folder"))
						b.setToolTipText(r.getString("CreateNewFolder"));
	 			}
	 		}
	 		else if (bo instanceof JLabel)
	 		{
	 			JLabel l = ((JLabel)bo);
	 			if(l.getDisplayedMnemonic() == 73)
	 				l.setText(r.getString("Select"));
	 			else if(l.getDisplayedMnemonic() == 78)
	 				l.setText(r.getString("FileName"));
	 			else if(l.getDisplayedMnemonic() == 84)
	 				l.setText(r.getString("FileType"));
	 		}
	 		else if(bo instanceof JComboBox)
	 		{
				JComboBox file_format = ( (JComboBox) bo );
				//file_format.removeItem((Object)"All Files (*.*)");
				//file_format.addItem(r.getString("AllFiles(*.*)"));
			}
	 	}
	} // set Language to the filechooser

	/**
	* Used to save a file as a text file.
	*
	* @param filetosave file to be saved.
	*/
	public void saveTextFile(File filetosave)
	{
			tempfilename = filetosave.toString();
			Thread runner = new Thread()
			   {
				  public void run()
				  {
						try
						{	// creates a writer to write a file and writes the text in the workspace
							FileWriter writer = new FileWriter(tempfilename);
							parentword.workspace.write(writer);
							writer.close();
						}
						catch (Exception ex)
						{
							   System.out.println(ex+"\n----->ex at saving rtf file : FileOperation");
							//   ex.printStackTrace();
						}
				   }
				};
			runner.start();

	}


	/**
	* Used to save a file without showing the file chooser.
	* This method will be called while saving an already existing file.
	*
	* @param filetosave file to be saved.
	*/
	public void saveWithoutFilechooser(File filetosave)
	{
		String temp_filename = filetosave.toString().toLowerCase();
		if(temp_filename.endsWith(".rtf"))
		 {
			  saveRTFFile(filetosave.toString());
			  getFrame().setTitle(filetosave.toString());
			  return;
		 }
		 else if (temp_filename.endsWith(".txt"))
		 {
			 saveTextFile(filetosave);
			 getFrame().setTitle(filetosave.toString());
			 return;
 		}
	}


	/**
	 * Makes the file chooser dialog not resizable
	   *** this has a problem with the 1.4 version.
	 */
	/*
	private class AFileChooser extends JFileChooser
	{
		AFileChooser(String path)
		{
			super(path);
		}
		AFileChooser()
		{
			super();
		}
		//overwrite the JFilechoosers createDialog to make the file
		//chooser dialog not resizable
		protected JDialog createDialog(Component parent) throws HeadlessException
		{
			JDialog dialog = super.createDialog(parent);
			dialog.setResizable(false);
			return dialog;
		}
	}
	*/

	/**
	* Checks the current document is saved or not.
	*/
    public boolean confirmClose()
	{
			if (parentword.textChanged)
			{
				 	ResourceBundle wordBundle = parentword.wordBundle;
					Object[] options = {wordBundle.getString("Yes"),
										wordBundle.getString("No"),
										wordBundle.getString("Cancel")};

					// gets the user option to overwrite the existing or not
					int overwrite = Utils.showDialog(parentword,"NotSavedWar",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
						options, 1);

					/*int reply = JOptionPane.showConfirmDialog(null,"Save the changes?",
					"Save?", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE, null);
					*/
				if (overwrite == JOptionPane.YES_OPTION)
				{
					savefile(false);
					if(parentword.textChanged)
						return false;
					else return true;
				}
				else if (overwrite == JOptionPane.CANCEL_OPTION)
					return false;
				else if (overwrite == JOptionPane.NO_OPTION)
					return true;
			}

			parentword.textChanged = false;
			return true;
	}



} // end of fileOPeration class






/*
      public void openRTFFile(String filename)
	        {
	  		   //System.out.println(" openrt ffile");
	             tempfilename = filename;

	             parentword.workspace.setEditorKit(parentword.rtfkit);
	             parentword.dsd_stydoc = new DefaultStyledDocument(parentword.stycont);
	             parentword.workspace.setDocument(parentword.dsd_stydoc);
	             try
	             {
				   System.out.print("  1   ");
	               parentword.workspace.setPage(new URL("file:"+filename));
	               System.out.print("  2   ");
	               parentword.workspace.getDocument().addUndoableEditListener(parentword.undoHandler);
	               System.out.print("  3   ");
	    	       parentword.resetUndoManager();
			     }
			   catch(Exception e)
			   {
				     //System.out.println(" e at rtf");
				     e.printStackTrace();
				}


	      }// end of openRTFFile


       // first rtf opener
       */


       /*
	              public void openHTMLFile(String filename)
	   		  		{
	   		  				   //System.out.println(" openhtml ffile");
	   		  		           tempfilename = filename;

	   		  		           parentword.workspace.setEditorKit(parentword.htmlkit);


	   		  		         // try{
	   		  				//	 parentword.workspace.setPage(filename);
	   		  		         //  }
	   		  			     //  catch(Exception e) { //System.out.println(" er at htmlfinel");}


	   		  		     	   Thread runner = new Thread()
	   		  		  			   {
	   		  		  				   public void run()
	   		  		  			  	  {
	   		  		  						try
	   		  		  						{
	   		  		  							InputStream in = new FileInputStream(tempfilename);
	   		  		  							parentword.dsd_stydoc = new DefaultStyledDocument(parentword.stycont);
	   		  		  							parentword.htmlkit.read(in, parentword.dsd_stydoc, 0);
	   		  		  							parentword.workspace.setDocument(parentword.dsd_stydoc);
	   		  		  							parentword.workspace.getDocument().addUndoableEditListener(parentword.undoHandler);
	   		  			    	                parentword.resetUndoManager();
	   		  		  							in.close();
	   		  		  							//System.out.println("   pp   ");
	   		  		  						}
	   		  		  						catch (Exception ex)
	   		  		  						{
	   		  		  							ex.printStackTrace();
	   		  		  						}

	   		  		  			        }
	   		  		  		         };
	   		  		           runner.start();


	   		       }// end of openRTFFile

	                  */



	            /*
	            public void saveHTMLFile(String filename)
	   				{
	   						   //System.out.println(" openrt ffile");
	   				           tempfilename = filename;

	   				           parentword.workspace.setEditorKit(parentword.htmlkit);
	   				           //parentword.dsd_stydoc = new DefaultStyledDocument(parentword.stycont);
	   				          // parentword.workspace.setDocument(parentword.dsd_stydoc);
	   				          try{
	   				           parentword.workspace.read( new FileReader(filename), parentword.htmlkit);
	   					       }
	   			       catch(Exception e) { System.out.println(e+" er at htmlfinel");}
	   			   }
	             */


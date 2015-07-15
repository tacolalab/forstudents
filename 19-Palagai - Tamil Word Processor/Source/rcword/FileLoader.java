package rcword;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.border.*;

/**
 *	Used to read and display a text file from the system to the workspace.
 *
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class FileLoader extends Thread
{
	/**
	* a reference to the main <code> Word </code> object
	*/
	Word parentword;

	/**
	* an object of <code> Document </code> where the file has to be opened.
	*/
	Document doc;

    /**
	* <code> File </code> object which contains the file to be opened
	*/
    File filetoopen;


    /**
	 *	Constructs an object of <code> FileLoader </code> with the following inputs.
	 *
	 * @ param	word a reference to the main <code> Word </code> object
	 * @ param	filetoopen <code> File </code> object which contains the file to
	 *						be opened.
	 * @ param	dectforfile an object of <code> Document </code> where the file has to
	 						be opened.
	 */
	FileLoader(Word word,File filetoopen, Document doctforfile)
	{
		setPriority(4); // sets the priority of this thread.
		this.filetoopen = filetoopen;
		this.doc = doctforfile;
		parentword = word;
	}

	/** Method to run the thread.
	*/
	public void run()
	{
		try
		{
			int input_from_file;
			// initialize the statusbar
			//parentword.progress = new JProgressBar();
			parentword.progress.setMinimum(0);
			parentword.progress.setMaximum((int) filetoopen.length());
			parentword.statusbar.add(parentword.progress);
			parentword.statusbar.repaint();

			// try to start reading
			Reader in = new FileReader(filetoopen);

			/* creates buffer of required length. This length is the length of the file to
			   be opened. Since this length is long, while converting it to int some
			   fraction may lose. So after converting the long to int just add 1 to the
			   length to don't lose any data
			*/
			char[] buff = new char[ ( (int)filetoopen.length() )+ 1];
			while ((input_from_file = in.read(buff, 0, buff.length)) != -1)
			{
				doc.insertString(doc.getLength(), new String(buff, 0, input_from_file), null);
				for(int i = 0 ; i<= input_from_file; i+=100)
				{
					try
					{
						Thread.sleep(100);
					} catch(Exception e)
					{
						System.out.println(e+"\n---> ex while file loading :FileLoader");
					}
					parentword.progress.setValue(parentword.progress.getValue() + i );
					parentword.progress.repaint();
					parentword.statusbar.repaint();
					parentword.repaint();
				}
				//System.out.println("                 3 ");
				//doc.insertString(doc.getLength(), new String(buff, 0, input_from_file), null);
				//progress.setValue(progress.getValue() + (input_from_file/2));
				//System.out.println("  :"+parentword.progress.getValue()+" out of :"+input_from_file);
				parentword.statusbar.remove(parentword.progress);
			}

			// done... get rid of progressbar
			doc.addUndoableEditListener(parentword.undoHandler);
			doc.addDocumentListener(parentword.docHandler);
			//parentword.statusbar.revalidate();

			parentword.resetUndoManager();
		}
		catch (IOException e)
		{
			System.err.println(e.toString());
		}
		catch (BadLocationException e)
		{
			System.err.println(e.getMessage());
		}

	} // end of run
} // end of fileLoader

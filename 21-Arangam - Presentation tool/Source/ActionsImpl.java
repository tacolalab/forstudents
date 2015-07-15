import java.io.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Clipboard;
import java.util.ResourceBundle;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyledDocument;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;

/**
 * An implementation of <code>ConnectActions</code> interface. All
 * general actions of the program are implemented. Includes all the
 * actions in the menu bar, tool bar, and any other part of the program
 * And methods for setting global
 * program variables and updating the correlated object.
 * <p>
 * The method names are equivalent to the names of
 * their counterpart actions in the menus with a postfix of the menu name.
 * For example: SaveAsFile is the method that should be called to implement
 * the "SaveAs" action is the File menu.
 */
public class ActionsImpl implements ConnectActions
{
	/*** Initialise for Find Dialog */
	private FindReplaceDialog findDialog;

	/*** For initial ruler visibility */
	private boolean	firstTime = true;

	/*** Main class reference */
	private transient Arangam arangam;

	/*** For setting current directory in file chooser dialog to user home */
	private File m_currentDirectory = new File(".");

	/*** Declaration for different file choosers */
	private JFileChooser openFileChooser, saveFileChooser, imageFileChooser;

	/*** Gets the words in current language viz Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	// --- Implementation of ConnectAction starts--------------------------------

	/**
	 * Connect the class to the main class
	 * @param arangam main class reference
	 */
	public void getInterface(Arangam arangam)
	{
		this.arangam = arangam;
	}

	/**
	 * Close the currently opened presentation if any and create a new
	 * blank presentation.
	 * @see #CloseFile
	 * @see #ConfirmClose
	 */
	public void NewFile()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SORTER_VIEW &&
			arangam.viewMode != Arangam.SLIDE_VIEW)
		{
			return;
		}

		//return if the current file is not saved
		if (!ConfirmClose())
			return;

		//new Slideshow is like closing the Slideshow and adding a new Slide
		CloseFile();

		//add a Slide to Slideshow
		arangam.slideshow1.addSlide();

		//show the added Slide
		arangam.slideshow1.showSlide(1);

		//sets the ruler
		setRulerVisible(arangam.rulerVisible);

		//mark the file as not modified
		arangam.slideModified = false;

		//update the ActionsToolbar
		arangam.actionsToolbar1.setSlideNumInToolbar(arangam.slideshow1.getCurrentSlideNum());
		arangam.activateToolbar(true);
		arangam.setEnablePrevNext();
	}

	/**
	 * Close the currently opened presentation if any and open a
	 * <code>JFileChooser</code> dialog to select a file for opening.
	 * @see #ConfirmClose
	 * @see ArangamFileFilter
	 * @see ArangamFileView
	 * @see ActionsImpl.AFileChooser
	 * @see TypeAheadSelector
	 */
	public void OpenFile()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SORTER_VIEW &&
			arangam.viewMode != Arangam.SLIDE_VIEW)
		{
			return;
		}

		//return if the current file is not saved
		if(!ConfirmClose())
			return;

		//change the mode to "editing" if not already
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			SlideView();

		//construct a file chooser to open file
		openFileChooser = new AFileChooser();

		//speed up file searching in file chooser
		new TypeAheadSelector(openFileChooser);

		//set current directory for file chooser
		openFileChooser.setCurrentDirectory(m_currentDirectory);

		//don't accept "All Files" filter
		openFileChooser.setAcceptAllFileFilterUsed(false);

		//set selection mode to files only
		openFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		//add filter for arangam files
		openFileChooser.addChoosableFileFilter(new ArangamFileFilter());

		//show arangam files with icon
		openFileChooser.setFileView(new ArangamFileView());

		//sets current language to file chooser
		setLanguage(openFileChooser);

		//displays the dialog and returns the file that was chosen
		if (openFileChooser.showOpenDialog(arangam) == JFileChooser.APPROVE_OPTION)//if1
		{
			//get the selected file
			File theFile = openFileChooser.getSelectedFile();

			//get the file extension
			String fileExt = ArangamFileFilter.getExtension(theFile);

			//if there is no extension add "Arangam fileExt" to it
			if ((fileExt == null) || (!(fileExt.equals(Arangam.fileExt))))
			{
				String filename1 = new String(theFile.getAbsoluteFile() +
					"." + Arangam.fileExt);
				theFile = new File(filename1);
			}

			//return if the extension is wrong
			if(!fileExt.equals(Arangam.fileExt) || !theFile.isFile())
			{
				wordBundle = arangam.getWordBundle();
				Object[] options = {wordBundle.getString("ok")};
				Utils.showDialog(openFileChooser,"notAProperFileWar",JOptionPane.OK_OPTION,
					JOptionPane.WARNING_MESSAGE, Arangam.warIcon,options, 0);
				return;
			}
			if (theFile != null)
			{
				String path = theFile.getAbsolutePath();
				try
				{
					//set wait cursor to indicate some action is going on
					arangam.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

					//create the input stream for the file path
					FileInputStream fis = new FileInputStream(path);
					GZIPInputStream gz = new GZIPInputStream(fis);
					ObjectInputStream ois = new ObjectInputStream(gz);

					//remove the current file
					arangam.presentaionPane.remove(arangam.slideshow1);

					//load Slideshow from the input stream
					arangam.slideshow1 = (Slideshow)ois.readObject();

					//update current directory
					m_currentDirectory = openFileChooser.getCurrentDirectory();

					//update ruler and toolbar
					setRulerVisible(arangam.rulerVisible);
					arangam.activateToolbar(true);

					gz.close();
					fis.close();
				}
				catch(FileNotFoundException error)
				{
					//set default cursor
					arangam.setCursor(Cursor.getDefaultCursor());
					wordBundle = arangam.getWordBundle();
					Object[] options = {wordBundle.getString("ok")};

					//alert that the file is not found
					Utils.showDialog(arangam,"fileNotFound",JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);

					//remove it from recent file and return
					arangam.fileHistory.removePathname(path);
					arangam.repaint();
					return;
				}
				catch(Exception error)
				{
					//set default cursor
					arangam.setCursor(Cursor.getDefaultCursor());
					wordBundle = arangam.getWordBundle();

					//alert for general exception, close the file and return
					Object[] options = {wordBundle.getString("ok")};
					Utils.showDialog(arangam,"incompatibleFileErr",JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);
					CloseFile();
					error.printStackTrace();
					return;
				}

				//remember filename for future saving
				arangam.filename = theFile;

				//update the application title
				arangam.setFileTitle(arangam.filename);

				//connect Slideshow to ConnectActions interface
				arangam.slideshow1.getInterface(arangam);
				arangam.presentaionPane.add(arangam.slideshow1);

				//update loaded Slideshow's listeners
				Slide tmpSlide = new Slide(arangam);
				ConnectComponent tmpComponent;
				int i = -1;

				//register listeners for all components
				do
				{
					if (i == -1)
						tmpSlide = (Slide)arangam.slideshow1.getComponent(0);
					else
						tmpSlide = arangam.slideshow1.getSlide(i);

					tmpSlide.getInterface(arangam);
					tmpSlide.registerListeners();

					if (tmpSlide.getComponentCount() != 0)
					{
						int j = 0;
						do
						{
							tmpComponent =(ConnectComponent)tmpSlide.getComponent(j);
							tmpComponent.getInterface(arangam);
							tmpComponent.registerListeners();
							j++;
						} while (j < tmpSlide.getComponentCount());
					}
					i++;
				} while (i < (arangam.slideshow1.getSlidesCount()-1));

				//solves bug with opaque textbox when serializing
				solveOpaqueBug();

				//show the first Slide
				arangam.slideshow1.showSlide(1);

				//insert the file path to recent file history
				arangam.fileHistory.insertPathname(path);

			}
		}

		//show the current Slide
		arangam.slideshow1.showSlide();

		//update the actions toolbar
		arangam.actionsToolbar1.setSlideNumInToolbar(
			arangam.slideshow1.getCurrentSlideNum());
		arangam.setEnablePrevNext();

		//set default cursor
		arangam.setCursor(Cursor.getDefaultCursor());
		arangam.repaint();
	}

	/**
	 * Close the currently opened presentation if any and open
	 * a file indicated by path. Used to open a file from recent file history.
	 * @param path the file path
	 */
	public void OpenFile(String path)
    {
		//return if the current file is not saved
		if(!ConfirmClose())
			return;

		//change the mode to "editing" if not already
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			SlideView();

		//create a new file specified by the path
		File theFile = new File(path);

		try
		{
			//set wait cursor to indicate some action is going on
			arangam.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			//create the input stream for the file path
			FileInputStream fis = new FileInputStream(path);
			GZIPInputStream gz = new GZIPInputStream(fis);
			ObjectInputStream ois = new ObjectInputStream(gz);

			//remove the current file
			arangam.presentaionPane.remove(arangam.slideshow1);

			//load Slideshow from the input stream
			arangam.slideshow1 = (Slideshow)ois.readObject();

			//update ruler and toolbar
			setRulerVisible(arangam.rulerVisible);
			arangam.activateToolbar(true);
		}
		catch(FileNotFoundException error)
		{
			//set default cursor
			arangam.setCursor(Cursor.getDefaultCursor());
			wordBundle = arangam.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};

			//alert that the file is not found
			Utils.showDialog(arangam,"fileNotFound",JOptionPane.OK_OPTION,
				JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);

			//remove it from recent file and return
			arangam.fileHistory.removePathname(path);
			arangam.repaint();
			return;
		}
		catch (Exception error)
		{
			//set default cursor
			arangam.setCursor(Cursor.getDefaultCursor());

			//alert for general exception, close the file and return
			wordBundle = arangam.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			Utils.showDialog(arangam,"incompatibleFileErr",JOptionPane.OK_OPTION,
				JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);
			CloseFile();
			error.printStackTrace();
			return;
		}

		//remember filename for future saving
		arangam.filename = theFile;

		//update the application title
		arangam.setFileTitle(arangam.filename);

		//connect Slideshow to ConnectActions interface
		arangam.slideshow1.getInterface(arangam);
		arangam.presentaionPane.add(arangam.slideshow1);

		//update loaded Slideshow's listeners
		Slide tmpSlide = new Slide(arangam);
		ConnectComponent tmpComponent;
		int i = -1;

		//register listeners for all components
		do
		{
			if (i == -1)
				tmpSlide = (Slide)arangam.slideshow1.getComponent(0);
			else
				tmpSlide = arangam.slideshow1.getSlide(i);

			tmpSlide.getInterface(arangam);
			tmpSlide.registerListeners();

			if (tmpSlide.getComponentCount() != 0)
			{
				int j = 0;
				do
				{
					tmpComponent =(ConnectComponent)tmpSlide.getComponent(j);
					tmpComponent.getInterface(arangam);
					tmpComponent.registerListeners();
					j++;
				} while (j < tmpSlide.getComponentCount());
			}
			i++;
		} while (i < (arangam.slideshow1.getSlidesCount()-1));

		//solves bug with opaque textbox when serializing
		solveOpaqueBug();

		//show the first Slide
		arangam.slideshow1.showSlide(1);

		//update the actions toolbar
		arangam.actionsToolbar1.setSlideNumInToolbar(
			arangam.slideshow1.getCurrentSlideNum());
		arangam.setEnablePrevNext();

		//insert the file path to recent file history
		arangam.fileHistory.insertPathname(path);

		arangam.repaint();

		//set default cursor
		arangam.setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * Closes the <code>Slideshow</code> without exiting the program. If it
	 * contains any unsaved changed, the user will be asked if the wants
	 * to save the <code>Slideshow</code> before closing.
	 * @see #NewFile
	 * @see #ConfirmClose
	 */
	public void CloseFile()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SORTER_VIEW &&
			arangam.viewMode != Arangam.SLIDE_VIEW)
		{
			return;
		}

		//return if the current file is not saved
		if (!ConfirmClose())
			 return;

		//change the mode to "editing" if not already
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			SlideView();

		//remove the Slideshow
		arangam.presentaionPane.remove(arangam.slideshow1);

		//create a new Slideshow and add it to arangam
		arangam.slideshow1 = new Slideshow(arangam);
		arangam.presentaionPane.add(arangam.slideshow1);

		//ruler should not be visible after closing the file
		if(!firstTime)
		{
			setRulerVisible(false);
		}
		firstTime = false;

		//update Action toolbar
		arangam.actionsToolbar1.setSlideNumInToolbar(
			arangam.slideshow1.getCurrentSlideNum());
		arangam.activateToolbar(false);

		//update application title
		arangam.setFileTitle(arangam.filename);

		//mark file as not modified
		arangam.slideModified = false;

		//as no file is open assign null to file name
		arangam.filename=null;

		arangam.repaint();
	}

	/**
	 * Saves the <code>Slideshow</code> to disk with its current file name
	 * @see #SaveAsFile()
	 */
	public void SaveFile()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SORTER_VIEW &&
			arangam.viewMode != Arangam.SLIDE_VIEW)
		{
			return;
		}

		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//change the mode to "editing" if not already
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			SlideView();

		//if this is the first saving of Slideshow,then do "Save As"
		if (arangam.filename == null)
		{
			SaveAsFile();
			return;
		}

		//if already had been saved once
		else
		{
			//remember the focused component if any and remove focus from it
			ConnectComponent focusedComp = arangam.slideshow1.getCurrentSlide().focused;
			if (focusedComp != null)
			{
				((Slide)arangam.slideshow1.getCurrentSlide()).focusComponent(null);
			}

			//set wait cursor to indicate some action is going on
			arangam.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			//remember the current Slide
			int currentSlide = arangam.slideshow1.getCurrentSlideNum();
			//show the last Slide
			arangam.slideshow1.showSlide(arangam.slideshow1.getSlidesCount());

			try
			{
				//create an output stream by the specified path
				FileOutputStream fos = new FileOutputStream(
					  arangam.filename.getAbsolutePath());
				GZIPOutputStream gz = new GZIPOutputStream(fos);
				ObjectOutputStream oos = new ObjectOutputStream(gz);

				//write the Slideshow to output stream
				oos.writeObject(arangam.slideshow1);

				//solves bug with opaque textbox when serializing;
				solveOpaqueBug();

				//flush and close the stream.
				oos.flush();
				gz.close();
				fos.close();

				//update application title
				arangam.setFileTitle(arangam.filename);
			}
			catch (Exception saexp)
			{
				//set default cursor
				arangam.setCursor(Cursor.getDefaultCursor());


				//alert for general exception and return
				wordBundle = arangam.getWordBundle();
				Object[] options = {wordBundle.getString("ok")};
				Utils.showDialog(arangam,"fileCantSaveErr",JOptionPane.OK_OPTION,
					JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);
				saexp.printStackTrace();
				return;
			}

			//show the current Slide
			arangam.slideshow1.showSlide(currentSlide);

			//focus the current component if any
			if (focusedComp != null)
			{
				arangam.slideshow1.getCurrentSlide().focusComponent(focusedComp);
			}

			//mark file as not modified
			arangam.slideModified = false;

			//set default cursor
			arangam.setCursor(Cursor.getDefaultCursor());
		}
	}

	/**
	 * Save the presentation which is not saved yet or Saves the <code>Slideshow</code>
	 * to disk with a different name. Open a <code>JFileChooser</code> Dialog to select
	 * the path for saving file.
	 * @see ActionsImpl.AFileChooser
	 * @see TypeAheadSelector
	 */
	public void SaveAsFile()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SORTER_VIEW &&
			arangam.viewMode != Arangam.SLIDE_VIEW)
		{
			return;
		}

		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//change the mode to "editing" if not already
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			SlideView();

		//construct a file chooser to save file
		saveFileChooser = new AFileChooser();

		//speed up file searching in file chooser
		new TypeAheadSelector(saveFileChooser);

		//set current directory for file chooser
		saveFileChooser.setCurrentDirectory(m_currentDirectory);

		//don't accept "All Files" filter
		saveFileChooser.setAcceptAllFileFilterUsed(false);

		//set the dialog to "Save" type
		saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

		//set selection mode to files only
		saveFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		//add filter for arangam files
		saveFileChooser.addChoosableFileFilter(new ArangamFileFilter());  //filter files

		//show arangam file with icon
		saveFileChooser.setFileView(new ArangamFileView());           //show picture icons

		//get the current language and set it to file chooser
		String lang = arangam.getLanguage();
		setLanguage(saveFileChooser);

		//pop up the file chooser and get its the return state
		int save = saveFileChooser.showSaveDialog(arangam);

		//if the return value is "approve", then save the file
		if (save == JFileChooser.APPROVE_OPTION)
		{
			File theFile = saveFileChooser.getSelectedFile();
			if (theFile != null)
			{
				//if no "tpt" extension, add it automatically
				String fileExt = ArangamFileFilter.getExtension(theFile);
				if ((fileExt == null) || (!(fileExt.equals(Arangam.fileExt))))
				{
					String filename = new String(theFile.getAbsoluteFile() +
						"." + Arangam.fileExt);
					theFile = new File(filename);
				}

				//confirm overwriting if needed
				if (theFile.exists())
				{
					wordBundle = arangam.getWordBundle();
					Object[] options = {wordBundle.getString("yes"),
										wordBundle.getString("no")};

					int overwrite = Utils.showDialog(saveFileChooser,"fileOverwriteCon",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, Arangam.warIcon,
						options, 1);
					if (overwrite == JOptionPane.CANCEL_OPTION ||
						overwrite == JOptionPane.NO_OPTION)
					{
						return;
					}
				}

				//set wait cursor to indicate some action is going on
				arangam.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				//remember the focused component if any and remove focus from it
				ConnectComponent focusedComp =
					  arangam.slideshow1.getCurrentSlide().focused;
				if (focusedComp != null)
				{
					((Slide)arangam.slideshow1.getCurrentSlide()).focusComponent(null);
				}

				//remember the current Slide
				int currentSlide = arangam.slideshow1.getCurrentSlideNum();
				//show the last Slide
				arangam.slideshow1.showSlide(arangam.slideshow1.getSlidesCount());

				try
				{
					//create an output stream by the specified path
					FileOutputStream fos = new FileOutputStream(
						  theFile.getAbsolutePath());
					GZIPOutputStream gz = new GZIPOutputStream(fos);
					ObjectOutputStream oos = new ObjectOutputStream(gz);

					//write the Slideshow to output stream
					oos.writeObject(arangam.slideshow1);

					//solves bug with opaque textbox when serializing;
					solveOpaqueBug();

					//flush and close the stream.
					oos.flush();
					gz.close();
					fos.close();

					//mark file as not modified
					arangam.slideModified = false;

					//update current directory
					m_currentDirectory = saveFileChooser.getCurrentDirectory();
				}
				catch (Exception sasexp)
				{
					//set default cursor
					arangam.setCursor(Cursor.getDefaultCursor());

					//alert for general exception and return
					wordBundle = arangam.getWordBundle();
					Object[] options = {wordBundle.getString("ok")};
					Utils.showDialog(arangam,"fileCantSaveErr",JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE, Arangam.errIcon,options, 0);

					return;
				}

				//remember filename for future saving
				arangam.filename = theFile;

				//update application title
				arangam.setFileTitle(arangam.filename);

				//show the current Slide
				arangam.slideshow1.showSlide(currentSlide);

				//set default cursor
				arangam.setCursor(Cursor.getDefaultCursor());

				//focus the current component if any
				if (focusedComp != null)
				{
					arangam.slideshow1.getCurrentSlide().focusComponent(focusedComp);
				}

				//insert the file path to recent file history
				String path = theFile.getAbsolutePath();
				arangam.fileHistory.insertPathname(path);

			}
		}
		arangam.repaint();
	}

	/**
	 * Sets margin, paper source, paper size, page orientation,
	 * and other layout options for printing of the <code>Slideshow</code>.
	 * @see Slideshow#pageSetup
	 */
	public void PageSetupFile()
	{
		//no need to print empty Slideshow
		if (arangam.slideshow1.isEmpty())
			return;
		//call Slideshow's pageSetup()
		arangam.slideshow1.pageSetup();
	}

	/**
	 * Prints the <code>Slideshow</code> or part of it. Also, lets the user select
	 * print options.
	 * @see Slideshow#printSlideshow
	 */
	public void PrintFile()
	{
		//no need to print empty Slideshow
		if (arangam.slideshow1.isEmpty())
			return;

		//change the mode to "editing" if not already
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//remember the current Slide
		int currentSlide = arangam.slideshow1.getCurrentSlideNum();

		//call Slideshow's printSlideshow()
		arangam.slideshow1.printSlideshow();

		//show the current Slide
		arangam.slideshow1.showSlide(currentSlide);
	}

	/**
	 * Closes the application after asking the user if the wants to save any
	 * changes to the <code>Slideshow</code>.
	 */
	public void ExitFile()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SORTER_VIEW &&
			arangam.viewMode != Arangam.SLIDE_VIEW)
		{
			return;
		}

		//return if the current file is not saved
		if (!ConfirmClose())
			return;

        //save entries for next session
        arangam.fileHistory.saveHistoryEntries();

		//dispose the application or applet and exit
		arangam.setVisible(false);
		arangam.dispose();
		if(arangam.applet != null)
		{
			arangam.applet.destroy();
		}
        System.exit(0);
	}

	//EDIT - Menu

	/**
	 * Removes the selected object from the <code>Slide</code> and places it
	 * on the <code>Clipboard</code>.
	 * @see #CopyEdit
	 * @see #DeleteEdit
	 */

	public void CutEdit()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//cutting is done by copying and deleting
		CopyEdit();
		DeleteEdit();
	}

	/**
	 * Copies the selected object to the <code>Clipboard</code>.
	 * @see Slide#copyComponent
	 */
	public void CopyEdit()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//call Slideshow's copyComponent()
		arangam.slideshow1.getCurrentSlide().copyComponent();

		//mark file as modified
		arangam.slideModified = true;
	}

	/**
	 * Inserts the contents of the <code>Clipboard</code> to the current <code>Slide</code>.
	 * This action is available only if the user has cut or copied an object.
	 * @see Slide#pasteComponent
	 */
	public void PasteEdit()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//call Slideshow's pasteComponent()
		arangam.slideshow1.getCurrentSlide().pasteComponent();

		arangam.repaint();

		//mark file as modified
		arangam.slideModified= true;
	}

	/**
	 * Deletes the selected object without putting it on the <code>Clipboard</code>.
	 * @see Slide#clearComponent
	 */
	public void DeleteEdit()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//call Slideshow's clearComponent()
		arangam.slideshow1.getCurrentSlide().clearComponent();

		//mark file as modified
		arangam.slideModified= true;
	}

	/**
	 * Deletes the current <code>Slide</code>
	 * @see Slideshow#removeSlide()
	 */
	public void DeleteSlideEdit()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//close the file if there is only one Slide
		if (arangam.slideshow1.getSlidesCount() == 1)
		{
			CloseFile();
			//mark file as not modified
			arangam.slideModified= false;
		}
		else if (arangam.slideshow1.getSlidesCount() > 1)
		{
			//else simply call Slideshow's removeSlide()
			arangam.slideshow1.removeSlide();

			//update Slide number in toolbar
			arangam.actionsToolbar1.setSlideNumInToolbar(
			  arangam.slideshow1.getCurrentSlideNum());

			//mark file as modified
			arangam.slideModified= true;
		}
	}

	/**
	 * Open up a <code>FindReplaceDialog</code>
	 * @see FindReplaceDialog
	 */
	public void FindReplaceDialog()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		FindReplaceDialog findDialog=new FindReplaceDialog(arangam);
		findDialog.getInterface(this);
		findDialog.setLocale();
		findDialog.show();
	}

	/**
	 * Places the selected object in front of other overlapping objects
	 * @see Slide#bringComponentToFront
	 */
	public void BringToFrontEdit()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//call Slideshow's bringComponentToFront()
		arangam.slideshow1.getCurrentSlide().bringComponentToFront();

		//mark file as modified
		arangam.slideModified= true;
	}

	/**
	 * Places the selected object behind other overlapping objects
	 * @see Slide#sendComponentToBack
	 */
	public void SendToBackEdit()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//call Slideshow's sendComponentToBack()
		arangam.slideshow1.getCurrentSlide().sendComponentToBack();

		//mark file as modified
		arangam.slideModified= true;
	}

	/**
	 * Switches to "Slide" view mode, where one can work on one <code>Slide</code> at a time.
	 */
	public void SlideView()
	{

		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if it is already in "Slide" mode
		if (arangam.viewMode == Arangam.SLIDE_VIEW)
			return;

		//set wait cursor to indicate some action is going on
		arangam.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		//set the visibility of ruler and toolbars
		setRulerVisible(arangam.rulerVisible);

		//set the visibility and toolbars
		arangam.actionsToolbar1.setVisible(arangam.actionsToolbarVisible);
		arangam.viewModeToolbar1.setVisible(arangam.viewModeToolbarVisible);

		//mark Slideshow as editable
		arangam.slideshow1.setArangamEditable(true);

		//set normal size for slides
		arangam.slideshow1.setSlideshowSizeBack();

		//show the current Slide
		arangam.slideshow1.showSlide();

		//update Slide number in toolbar
		arangam.actionsToolbar1.setSlideNumInToolbar(
			arangam.slideshow1.getCurrentSlideNum());

		//update viewMode to "Slide" view
		arangam.viewMode = Arangam.SLIDE_VIEW;

		//set default cursor
		arangam.setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * Displays miniature versions of all <code>Slides</code> in the <code>Slideshow</code>
	 * @see Slideshow#showSlideSorter
	 */
	public void SlideSorterView()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//set wait cursor to indicate some action is going on
		arangam.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		//no ruler in sorter mode
		setRulerVisible(false);

		//set the visibility and toolbars
		arangam.actionsToolbar1.setVisible(arangam.actionsToolbarVisible);
		arangam.viewModeToolbar1.setVisible(arangam.viewModeToolbarVisible);

		//if view mode is "Slide", remember the focused component if any
		//and remove focus from it
		if (arangam.viewMode == Arangam.SLIDE_VIEW)
		{
			ConnectComponent focusedComp =
			  arangam.slideshow1.getCurrentSlide().focused;
			if (focusedComp != null)
				arangam.slideshow1.getCurrentSlide().focusComponent(null);
		}

		//mark Slide as editable
		arangam.slideshow1.setArangamEditable(true);

		//if view mode is "show", set corresponding Slideshow location
		if (arangam.viewMode == Arangam.SHOW_VIEW)
		{
			arangam.slideshow1.setLocation(
			  Arangam.SLIDESHOW_X, Arangam.SLIDESHOW_Y);
			arangam.slideshow1.setSlideshowSizeBack();
		}

		//get zoom factor
		int zoom = arangam.slideSorterDimensions;

		//calculate which segment( page) of Slideshow to show
		int slideSegNum = (arangam.slideshow1.getCurrentSlideNum() - 1)/
			(zoom*zoom) + 1;

		//calls Slideshow's showSlideSorter()
		arangam.slideshow1.showSlideSorter(slideSegNum);

		//calculate Slide number to show
		int slideNum = slideSegNum - 1;
		slideNum *= zoom*zoom;
		slideNum++;

		//set it
		arangam.slideshow1.setCurrentSlideNum(slideNum);

		//update it in toolbar
		arangam.actionsToolbar1.setSlideNumInToolbar(
		  arangam.slideshow1.getCurrentSlideNum());

		//mark Slideshow as not editable
		arangam.slideshow1.setArangamEditable(false);

		//update "view mode" to sorter view
		arangam.viewMode = Arangam.SORTER_VIEW;

		//set default cursor
		arangam.setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * Runs the <code>Slideshow</code>, beginning with the first <code>Slide</code>
	 * @see Show#run
	 */
	public void SlideShowView()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if it is already in "show" mode
		if (arangam.viewMode == Arangam.SHOW_VIEW)
			return;

		//set wait cursor to indicate some action is going on
		arangam.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		//if view mode is "Slide", remember the focused component if any
		//and remove focus from it
		if (arangam.viewMode == Arangam.SLIDE_VIEW)
		{
			ConnectComponent focusedComp =
				  ((Slide)arangam.slideshow1.getComponent(0)).focused;
			if (focusedComp != null)
				arangam.slideshow1.getCurrentSlide().focusComponent(null);
		}

		//if view mode is "sorter", set corresponding Slideshow location
		else if (arangam.viewMode == Arangam.SORTER_VIEW)
			arangam.slideshow1.setSlideshowSizeBack();

		//mark Slideshow as not editable
		arangam.slideshow1.setArangamEditable(false);

		//start the show from first Slide
		arangam.slideshow1.showSlide(1);

		//update "view mode" to "show" view
		arangam.viewMode = Arangam.SHOW_VIEW;

		//set default cursor
		arangam.setCursor(Cursor.getDefaultCursor());

		//start show
		arangam.show.run();
	}

	/**
	 * Displays or hides the <code>ActionsToolbar</code>
	 * @see ActionsToolbar#setVisible
	 */
	public void ActionsToolbarView()
	{
		//toggles the action toolbar's visibility
		arangam.actionsToolbarVisible = !arangam.actionsToolbarVisible;
		if (arangam.viewMode == Arangam.SLIDE_VIEW)
			arangam.actionsToolbar1.setVisible(arangam.actionsToolbarVisible);
	}

	/**
	 * Displays or hides the <code>ViewModeToolbar</code>
	 * @see ViewModeToolbar#setVisible
	 */
	public void ViewModeToolbarView()
	{
		//toggles the "view mode" toolbar's visibility
		arangam.viewModeToolbarVisible = !arangam.viewModeToolbarVisible;
		if (arangam.viewMode == Arangam.SLIDE_VIEW)
			arangam.viewModeToolbar1.setVisible(arangam.viewModeToolbarVisible);
	}

	/**
	 * Displays or hides the <code>Ruler</code>
	 * @see #setRulerVisible
	 */
	public void RulerView()
	{
		//toggles the ruler's visibility
		arangam.rulerVisible = !arangam.rulerVisible;
		if (arangam.viewMode == Arangam.SLIDE_VIEW &&
			arangam.slideshow1.getCurrentSlideNum() != 0)
			setRulerVisible(arangam.rulerVisible);
	}

	/**
	 * Open the <code>GotoSlide</code> Dialog, where the user can enter a number of
	 * the <code>Slide</code> to be displayed.
	 * @see GotoSlideDialog
	 */
	public void GotoSlideView()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//create a new instance for GotoSlideDialog and display it
		GotoSlideDialog gotoSlideDialog = new GotoSlideDialog(arangam);
		gotoSlideDialog.show();
	}

	/**
	 * Sets the current Slide to the <code>Slide</code> indicated by slideNumberStr
	 * @param slideNumberStr the <code>Slide</code> to display
	 * @see #setCurrentSlideNum
	 */
	public void GotoSlide(String slideNumberStr)
	{
		//go to the specified Slide by setting the current Slide number
		if (slideNumberStr != null)
			setCurrentSlideNum(slideNumberStr);
	}

	/**
	 * Displays the previous <code>Slide</code> in the "Slideshow view" and "Slide View" or
	 * the previous set of miniature slides in "Sorter View".
	 * @return <code>true</code> if there is any previous <code>Slide</code>
	 * @see Slideshow#showSlideSorter
	 */
	public boolean PrevSlideView()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return false;

		//if view mode is "sorter"
		if (arangam.viewMode == Arangam.SORTER_VIEW)
		{
			//get zoom factor
			int zoom = arangam.slideSorterDimensions;

			//calculate which segment( page) of Slideshow to show
			int slideSegNum = (arangam.slideshow1.getCurrentSlideNum() - 1)/
				(zoom*zoom) + 1;

			//no previous Slide if the segment( page) number < 1
			if (slideSegNum <= 1)
				return false;//no previous page

        	//set wait cursor - indicate some action is going on
        	arangam.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        	//calculate current Slide number and show it
        	slideSegNum--;
			arangam.slideshow1.showSlideSorter(slideSegNum);

			int slideNum = slideSegNum - 1;
			slideNum *= zoom*zoom;
			slideNum++;

			//set it
			arangam.slideshow1.setCurrentSlideNum(slideNum);

			//update "action" toolbar
			arangam.actionsToolbar1.setSlideNumInToolbar(
				arangam.slideshow1.getCurrentSlideNum());
			arangam.setEnablePrevNext();

			//set default cursor
			arangam.setCursor(Cursor.getDefaultCursor());
			return true;//previous page is there
		}
		//if view mode is "Slide" or "show"
		else if (arangam.slideshow1.getCurrentSlideNum() > 1)
		{
			//show the previous Slide
			arangam.slideshow1.showSlide(arangam.slideshow1.getCurrentSlideNum()-1);

			//update "action" toolbar
			arangam.actionsToolbar1.setSlideNumInToolbar(
				arangam.slideshow1.getCurrentSlideNum());
			arangam.setEnablePrevNext();

			//set default cursor
			arangam.setCursor(Cursor.getDefaultCursor());
			return true;//previous Slide is there
		}
		//update "action" toolbar
		arangam.setEnablePrevNext();
		return false;//no previous Slide
	}

	/**
	 * Displays the next <code>Slide</code> in the "Slideshow view" and "Slide view", or the
	 * next set of miniature slides, in "Sorter view".
	 */
	public boolean NextSlideView()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return false;

		if (arangam.viewMode == Arangam.SORTER_VIEW)
		{
			//get zoom factor
			int zoom = arangam.slideSorterDimensions;

			//calculate which segment( page) of Slideshow to show
			int slideSegNum = (arangam.slideshow1.getCurrentSlideNum() - 1)/
				(zoom*zoom) + 1;

			if (slideSegNum*zoom*zoom >= arangam.slideshow1.getSlidesCount())
				return false;//no next page

			//set wait cursor to indicate some action is going on
			arangam.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			//calculate current Slide number and show it
			slideSegNum++;
			arangam.slideshow1.showSlideSorter(slideSegNum);
			int slideNum = slideSegNum - 1;
			slideNum *= zoom*zoom;
			slideNum++;

			//set it
			arangam.slideshow1.setCurrentSlideNum(slideNum);

			//update "action" toolbar
			arangam.actionsToolbar1.setSlideNumInToolbar(
				arangam.slideshow1.getCurrentSlideNum());
			arangam.setEnablePrevNext();

			//set default cursor
			arangam.setCursor(Cursor.getDefaultCursor());
			return true;//next page is there
		}

		else if (arangam.slideshow1.getCurrentSlideNum() <
			arangam.slideshow1.getSlidesCount())
		{
			//show the next Slide
			arangam.slideshow1.showSlide(arangam.slideshow1.getCurrentSlideNum()+1);

			//update "action" in toolbar
			arangam.actionsToolbar1.setSlideNumInToolbar(
				arangam.slideshow1.getCurrentSlideNum());
			arangam.setEnablePrevNext();

			//set default cursor
			arangam.setCursor(Cursor.getDefaultCursor());

			return true;//next Slide there
		}
		arangam.setEnablePrevNext();
		return false;//no next Slide or page
	}

	/**
	 * Inserts a new <code>Slide</code> after the current Slide and shows it
	 * @see Slideshow#addSlide
	 */
	public void NewSlideInsert()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//don't focus any component
		arangam.slideshow1.getCurrentSlide().focusComponent(null);

		//add a Slide to Slideshow
		arangam.slideshow1.addSlide();

		//show it
		arangam.slideshow1.showSlide(arangam.slideshow1.getCurrentSlideNum() + 1);

		//update "action" toolbar
		arangam.actionsToolbar1.setSlideNumInToolbar(arangam.slideshow1.getCurrentSlideNum());
		arangam.actionsToolbar1.setDefaultFontToolBar();
		arangam.setEnablePrevNext();

		//mark file as modified
		arangam.slideModified= true;
	}

	/**
	 * Inserts an picture in the <code>Slide</code>.
	 * Open a <code>JFileChooser</code> Dialog to select a picture file(jpg or gif)
	 * @see Slide#addArangamImage
	 * @see ImageFileFilter
	 * @see ImageFileView
	 * @see ImagePreview
	 * @see ActionsImpl.AFileChooser
	 * @see TypeAheadSelector
	 */
	public void PictureInsert()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		File theFile = null;
		//construct a file chooser to open image file
		imageFileChooser = new AFileChooser();

		//speed up file searching in file chooser
		new TypeAheadSelector(imageFileChooser);

		//set current directory for file chooser
		imageFileChooser.setCurrentDirectory(m_currentDirectory);

		//set selection mode to files only
		imageFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		//don't accept "All Files" filter
		imageFileChooser.setAcceptAllFileFilterUsed(false);

		//add filter for picture files
		imageFileChooser.addChoosableFileFilter(new ImageFileFilter());

		//show image file with icon
		imageFileChooser.setFileView(new ImageFileView());

		//show a preview image of the selected file
		imageFileChooser.setAccessory(new ImagePreview(imageFileChooser));

		//get the current language and set it to file chooser
		String lang = arangam.getLanguage();
		setLanguage(imageFileChooser);

		//display the dialog and return the selected file
		if (imageFileChooser.showOpenDialog(arangam) == JFileChooser.APPROVE_OPTION)
			theFile = imageFileChooser.getSelectedFile();

		if (theFile != null)
		try
		{
			//call Slideshow's addArangamImage()
			arangam.slideshow1.getCurrentSlide().addArangamImage(theFile.toURL());
			m_currentDirectory = imageFileChooser.getCurrentDirectory();
		}catch (java.net.MalformedURLException error)
		{
			//alert for exception
			wordBundle = arangam.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			Utils.showDialog(arangam,"pictureCantInsertErr",JOptionPane.OK_OPTION,
				JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);
		}

		//mark file as modified
		arangam.slideModified= true;
	}

	/**
	 * Draws a new Textbox(<code>AText</code>)
	 * @see Slide#addArangamText
	 */
	public void TextBoxInsert()
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//call Slideshow's addArangamText()
		arangam.slideshow1.getCurrentSlide().addArangamText();

		//mark file as modified
		arangam.slideModified= true;
	}

	/**
	 * Draws a new Shape(<code>AShape</code>).
	 * @param shapeType an integer indicating the <code>Shape</code>. May be diamond, oval,
	 * rectangle, round rectangle or square
	 * @see Slide#addArangamShape
	 */
	public void ShapeInsert(int shapeType)
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//call Slideshow's addArangamShape()
		arangam.slideshow1.getCurrentSlide().addArangamShape(shapeType);

		//mark file as modified
		arangam.slideModified= true;
	}

	/**
	 * Open a dialog, where one can change the font formats of the
	 * selected text component and of new one to come.
	 * @see FontDialog
	 */
	public void FontFormat()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//get the focused component
		ConnectComponent focused =
			arangam.slideshow1.getCurrentSlide().focused;

		//if any component(AText or AShape) has focus, get its attributes(font).
		//update the attributes to font dialog and show it
		if(focused!=null)
		{
			FontDialog fontDialog =
					new FontDialog(arangam,focused);
			AttributeSet attribute = null;
			String focusedName = focused.getClass().getName();
			if(focusedName.equals("AText"))
			{
				ConnectText focusedText =
					(ConnectText)focused;
				StyledDocument doc = focusedText.getStyledDocument();
				attribute = doc.getCharacterElement(
					focusedText.getCaretPosition()-1).getAttributes();
			}
			if(focusedName.equals("AShape"))
			{
				ConnectShape focusedShape =
					(ConnectShape)focused;
				attribute = focusedShape.getFontAttributes();
			}
			if(attribute != null)
				fontDialog.setAttributes(attribute);
			fontDialog.show();
		}
	}

	/**
	 * Open a <code>ShapeFormatDialog</code>, where one can change the shape formats of the
	 * selected <code>shape</code>
	 */
	public void ShapeFormat()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//get the focused component
		ConnectComponent focused =
			arangam.slideshow1.getCurrentSlide().focused;

		//if any component(AShape) has focus, get its attributes.
		//update the attributes to shape dialog and show it
		if(focused!=null)
		{
			String focusedName = focused.getClass().getName();
			if(focusedName.equals("AShape"))
			{
				AShape focusedShape = (AShape)focused;
				ShapeFormatDialog shapeFormatDialog =
					new ShapeFormatDialog(arangam,focusedShape);
				shapeFormatDialog.setAttributes();
				shapeFormatDialog.show();
			}
		}
	}

	/**
	 * Open a <code>BackgroundFormat</code> dialog, where one can set the background color and
	 * texture for <code>Slide</code>
	 */
	public void BackgroundFormat()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
			return;

		//create and show the background dialog for current Slide
		BackgroundDialog backgroundDialog =
			new BackgroundDialog(arangam, arangam.slideshow1.getCurrentSlide());
		backgroundDialog.show();

		//mark file as modified
		arangam.slideModified= true;
	}

	//language menu
	/**
	 * Sets the locale to Tamil
	 * @see Arangam#setLocale
	 */
	public void tamilLang()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
		{
			return;
		}

		//set language to Tamil if not already
		if(!arangam.getLanguage().equals("taIN"))
		{
			arangam.setLocale("ta", "IN");
		}
	}

	/**
	 * Sets the locale to English
	 * @see Arangam#setLocale
	 */
	public void englishLang()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
		{
			return;
		}
		//set language to English if not already
		if(!arangam.getLanguage().equals("enUS"))
		{
			arangam.setLocale("en", "US");
		}
	}

	/**
	 * Sets the locale to Tamil-English
	 * @see Arangam#setLocale
	 */
	public void tamEnglishLang()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
		{
			return;
		}
		//set language to Tamil-English if not already
		if(!arangam.getLanguage().equals("teIN"))
		{
			arangam.setLocale("te", "IN");
		}
	}

	/**
	 * Displays the version number of this program and copyright notices
	 */
	public void AboutHelp()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
		{
			return;
		}

		//create and show the about dialog
		final JDialog helpDialog = new JDialog(arangam,"About",true);
		JButton okButton = new JButton(Arangam.wordBundle.getString("ok"));
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				helpDialog.dispose();
			}
		});

		Container pane = helpDialog.getContentPane();
		pane.setLayout(new BorderLayout());
		pane.add(new JLabel(ImagesLocator.getImage(
			Arangam.wordBundle.getString("aboutHelp"))),BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		pane.add(buttonPanel,BorderLayout.SOUTH);
		helpDialog.pack();
		helpDialog.setSize(helpDialog.getPreferredSize());
		helpDialog.setLocation(Utils.getMiddle(helpDialog.getSize()));

		helpDialog.setResizable(false);
		helpDialog.show();
		helpDialog.dispose();
	}

	/**
	 * Open up the help file in help window
	 * @see ActionsImpl.HelpHyperlinkListener
	 */
	public void ArangamHelp()
	{
		//return if the application is not in "editing" mode
		if (arangam.viewMode != Arangam.SLIDE_VIEW)
		{
			return;
		}

		//create and show the help frame
		JFrame helpFrame = new JFrame("Arangam Help");
		helpFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				arangam.setVisible(true);
			}
		});

		//create an editor pane to show the help content
		JEditorPane helpPane = new JEditorPane();
		//make it non editable
		helpPane.setEditable(false);

		try
		{
			//get the help file and set it to editor pane
			wordBundle = arangam.getWordBundle();
			java.net.URL helpURL = getClass().getResource(
				wordBundle.getString("helpFile"));
			helpPane.setFont(arangam.getBilingualFont());
			helpPane.setContentType("text/html");

			helpPane.setPage(helpURL);
			helpPane.addHyperlinkListener(new HelpHyperlinkListener());

		}catch(IOException e)
		{
			//alert for exception and return
			Object[] options = {wordBundle.getString("ok")};
			Utils.showDialog(arangam,"helpFileCantLoadErr",JOptionPane.OK_OPTION,
				JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);
			e.printStackTrace();
			return;
		}

		//put editor pane in a scroll pane
		JScrollPane scrollPane = new JScrollPane(helpPane);

		//add scroll pane to help frame
		helpFrame.getContentPane().add(scrollPane);

		//set bounds for help frame and make it visible
		helpFrame.setSize(550,450);
		helpFrame.setLocation(Utils.
				getMiddle(helpFrame.getSize()));
		helpFrame.setVisible(true);
	}

	/**
	 * Updates the text attributes for text component which has current focus.
	 * If no component has focus, the attributes are added and applied
	 * when new one is created.
	 * @param mattr the <code>MutableAttributeSet</code> for applying to text
	 * @param setParagraphAttributes whether the mattr applies
	 * to paragraph or not
	 * @see javax.swing.JTextPane#setCharacterAttributes
	 */
	public void updateTextAttr(MutableAttributeSet mattr,
		boolean setParagraphAttributes)
	{
		//get the component that is currently focused
		ConnectComponent focusedComp =
			arangam.slideshow1.getCurrentSlide().focused;

		if(focusedComp != null)
		{
			//apply the attributes to the corresponding component
			String focusedName = focusedComp.getClass().getName();
			if(focusedName.equals("AText") )
			{
				AText focusedText = (AText)focusedComp;
				focusedText.setAttributeSet((AttributeSet)mattr, setParagraphAttributes);
				focusedText.grabFocus();
			}
			if(focusedName.equals("AShape") )
			{
				AShape focusedShape = (AShape)focusedComp;
				focusedShape.setCharacterAttributes((AttributeSet)mattr, setParagraphAttributes);
				focusedShape.textPane.textEditorInShape.grabFocus();
			}
		}
	}

	/**
	 * Sets background color for focused component.
	 * @param bgColor the background color
	 */
	public void setBgColor(Color bgColor)
	{
		//get RGB values from the color
		int r = bgColor.getRed();
		int g = bgColor.getGreen();
		int b = bgColor.getBlue();

		//invert the RGB values to get inverted color
		r = Math.abs(255-r);
		g = Math.abs(255-g);
		b = Math.abs(255-b);

		//for setting inverted color to text selection and caret
		Color selectionColor = new Color(r,g,b);
		Color caretColor = new Color(r,g,b);

		//get the focused component and apply the above said colors to it
		ConnectComponent focusedComp =
			arangam.slideshow1.getCurrentSlide().focused;
		if (focusedComp != null)
		{
			String focusedName = focusedComp.getClass().getName();
			if(focusedName.equals("AText")  ||
				focusedName.equals("AShape") )
			{
				if(focusedName.equals("AShape") )
				{
					AShape aShape = (AShape)focusedComp;
					aShape.setBackground(bgColor);
					aShape.textPane.textEditorInShape.grabFocus();
					return;
				}
				ConnectText focusedText=(ConnectText)focusedComp;
				focusedText.setBackground(bgColor);
				focusedText.setSelectionColor(selectionColor);
				focusedText.setSelectedTextColor(bgColor);
				focusedText.setCaretColor(caretColor);

				AText aText = (AText)focusedComp;
				aText.grabFocus();
			}
		}
	}

	/**
	 * Sets outline color for focused component.
	 * @param outlineColor the border color
	 * @see AShape#setOutlineColor
	 */
	public void setOutlineColor(Color outlineColor)
	{
		//get the focused component and apply the color to its border
		ConnectComponent focusedComp =
			arangam.slideshow1.getCurrentSlide().focused;
		if (focusedComp != null)
		{
			String focusedName = focusedComp.getClass().getName();
			if(focusedName.equals("AText"))
			{
				//AText must have empty space around. so add Empty border also
				AText aText = (AText)focusedComp;
				aText.setBorder(new CompoundBorder
					(new BorderUIResource.LineBorderUIResource(outlineColor),
					 new EmptyBorder(6,6,6,6)	) );
				aText.grabFocus();
			}
			else if(focusedName.equals("AImage"))
			{
				focusedComp.setBorder(new BorderUIResource.LineBorderUIResource
					(outlineColor));
			}
			else if(focusedName.equals("AShape"))
			{
				//see AShape#setOutlineColor
				AShape aShape = (AShape)focusedComp;
				aShape.setOutlineColor(outlineColor);
				aShape.textPane.textEditorInShape.grabFocus();
			}
		}
	}

	/**
	 * To show the particular <code>Slide</code>
	 * @param slideNumberStr number of <code>Slide</code> to display
	 */
	public void setCurrentSlideNum(String slideNumberStr)
	{
		//return if the Slideshow is empty
		if (arangam.slideshow1.isEmpty())
			return;

		//retrieve the Slide number and show that Slide
		try
		{
			Integer slideNum = new Integer(slideNumberStr);
			int slideNumber = slideNum.intValue();
			if ( (slideNumber<1) ||
				     (slideNumber>arangam.slideshow1.getSlidesCount()))
				throw (new NumberFormatException());
    		arangam.slideshow1.showSlide(slideNumber);

			//update Slide number in toolbar
			arangam.actionsToolbar1.setSlideNumInToolbar(
				  arangam.slideshow1.getCurrentSlideNum());
		}
		catch (NumberFormatException error)
		{
			//alert if the input is not a number
			wordBundle = arangam.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};
			Utils.showDialog(arangam,"incorrectSlideNoErr",JOptionPane.OK_OPTION,
				JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);

			//update Slide number in toolbar
			arangam.actionsToolbar1.setSlideNumInToolbar(
				  arangam.slideshow1.getCurrentSlideNum());
		}
	}

	/**
	 * Sets Slide Sorter dimension to 2x2, 3x3 or 4x4
	 * @param dimension the dimension
	 */
	public void setCurrentSorterDim(int dimension)
	{
		arangam.slideSorterDimensions = dimension;
		if (arangam.viewMode == Arangam.SORTER_VIEW)
			SlideSorterView();
		arangam.slideshow1.grabFocus();
	}

	/**
	 * End the <code>Slideshow</code> if it is in "Show" mode
	 * @see Show#stop
	 */
	public void EndShow()
	{
		//end show means change the view mode from "Show" to Slide"
		arangam.show.stop();
		arangam.actionsImpl1.SlideView();
	}

	/**
	 * Checks the <code>Clipboard</code> for any content
	 */
	public boolean isPasteAvailable()
	{
		//StyledText flavor
		DataFlavor df = new DataFlavor(StyledText.class, "StyledText");

		//get clipboard contents
		Transferable content = arangam.clipboard.getContents(null);

		//return true if the clipboard has any of the required flour
		boolean pasteAvailable = (
			(content!=null) &&
			(
				(content.isDataFlavorSupported(DataFlavor.stringFlavor)) ||
				(content.isDataFlavorSupported(df)) ||
				(content.isDataFlavorSupported(ComponentSelection.imageFlavor)) ||
				(content.isDataFlavorSupported(ComponentSelection.textFlavor)) ||
				(content.isDataFlavorSupported(ComponentSelection.shapeFlavor))
			));
		return pasteAvailable;
	}

	/**
	 * Returns the currently focused component
	 */
	public ConnectComponent getFocusedComponent()
	{
		return arangam.slideshow1.getCurrentSlide().focused;
	}

	// --- Implementation of ConnectAction ends--------------------------------

	/**
	 * Confirm closing of <code>Slideshow</code> when not saved
	 */
	public boolean ConfirmClose()
	{
		if (arangam.slideModified)
		{
			wordBundle = arangam.getWordBundle();
			Object[] options = {wordBundle.getString("yes"),
								wordBundle.getString("no"),
								wordBundle.getString("cancel")};
			String notSavedWar = wordBundle.getString("notSavedWar");
			String messageTitle = wordBundle.getString("messageTitle");

			//alert "not saved" warning
			int reply = Utils.showDialog(arangam,"notSavedWar",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, Arangam.errIcon,
				 options, 0);

 			//if the answer was YES, exit
			if (reply == JOptionPane.YES_OPTION)
			{
				if (arangam.filename == null)
				{
					SaveAsFile();
					return true;
				}
				else
				{
					SaveFile();
					return true;
				}
			}
			//else do nothing
			else if (reply == JOptionPane.CANCEL_OPTION)
			{
				return false;
			}
		}
		//mark file as not modified
		arangam.slideModified = false;
		return true;
	}

	/**
	 * Sets the locale for the components in <code>JFileChooser</code>
	 * @param fc the <code>JFileChooser</code> to set locale
	 * @see #setJFCButtonText
	 */
	public void setLanguage(JFileChooser fc)
	{
		fc.setFont(arangam.getBilingualFont());

		//set current locale for all panels in file chooser
		for(int i=0; i < fc.getComponentCount();i++)
			if (fc.getComponent(i) instanceof JPanel)
				setJFCButtonText((JPanel)fc.getComponent(i));
	}

	/**
	 * Sets the locale for the panel components in <code>JFileChooser</code>
	 * @param jp the <code>JPanel</code> to set locale
	 * @see #setLanguage
	 */
	public void setJFCButtonText(JPanel jp)
	{
		ResourceBundle r = arangam.getWordBundle();

		//set current locale for all components in panel
		for(int j=0; j < jp.getComponentCount();j++)
		{
			Component bo = jp.getComponent(j);
			if (bo instanceof JPanel)
				setJFCButtonText((JPanel)bo);
			if (bo instanceof AbstractButton)
			{
				AbstractButton b = (AbstractButton)bo;

				String tooltip = b.getToolTipText();
				if(tooltip.equals("Up One Level"))
				{
					b.setToolTipText(r.getString("upOneLevel"));
				}
				else if(tooltip.equals("Desktop"))
				{
					b.setToolTipText(r.getString("saveSelectedFile"));
				}
				else if(tooltip.equals("Create New Folder"))
				{
					b.setToolTipText(r.getString("createNewFolder"));
				}
				else if(tooltip.equals("List"))
				{
					b.setToolTipText(r.getString("list"));
				}
				else if(tooltip.equals("Details"))
				{
					b.setToolTipText(r.getString("details"));
				}

				if(b.getText() != null)
				{
					if(b.getText().equals("Open"))
					{
						b.setText(r.getString("open"));
						b.setToolTipText(r.getString("openSelectedFile"));
					}
					if(b.getText().equals("Save"))
					{
						b.setText(r.getString("save"));
						b.setToolTipText(r.getString("saveSelectedFile"));
					}
					if(b.getText().equals("Cancel"))
					{
						b.setText(r.getString("cancel"));
						b.setToolTipText(r.getString("abort"));
					}
				}
			}
			else if (bo instanceof JLabel)
			{
				JLabel l = ((JLabel)bo);
				if(l.getDisplayedMnemonic() == 73)
					l.setText(r.getString("select"));
				else if(l.getDisplayedMnemonic() == 78)
					l.setText(r.getString("fileName"));
				else if(l.getDisplayedMnemonic() == 84)
					l.setText(r.getString("fileType"));
			}
		}
	}

	/**
	 * Solves bug with opaque <code>AText</code> and <code>AShape</code> when serializing
	 */
	void solveOpaqueBug()
	{
		//reference for Slide
		Slide tmpSlide;

		//reference for ConnectComponent
		ConnectComponent tmpComponent;

		//solve the bug for every component in Slide
		int i = 0;
		do
		{
			tmpSlide = arangam.slideshow1.getSlide(i);
			if (tmpSlide.getComponentCount() != 0)
			{
				int j = 0;
				do
				{
					tmpComponent = (ConnectComponent)tmpSlide.getComponent(j);
					String tmpClass = tmpComponent.getClass().getName();
					//solve for AText. background bug
					if (tmpClass.equals("AText"))
					{
						AText tempText = (AText)tmpComponent;
						Color c = tempText.getBackground();
						tempText.setBackground(c);
					}
					//solve for AShape. border bug
					else if(tmpClass.equals("AShape"))
					{
						((AShape)tmpComponent).setEditorBorder(null);
					}
					j++;

				} while (j < tmpSlide.getComponentCount());
			}
			i++;
		} while (i < arangam.slideshow1.getSlidesCount());
	}

	/**
	 * Set the visibility of rulers
	 * @param visible <code>true</code> if the ruler is visible, otherwise <code>false</code>
	 */
	public void setRulerVisible(boolean visible)
	{
		arangam.rowView.setVisible(visible);
		arangam.columnView.setVisible(visible);
		if(visible)
		{
			arangam.scrollPane.setRowHeaderView(arangam.rowView);
			arangam.scrollPane.setColumnHeaderView(arangam.columnView);
		}
		else
		{
			arangam.scrollPane.setRowHeaderView(null);
			arangam.scrollPane.setColumnHeaderView(null);
		}
	}

	public void setRulerXY(int x, int y)
	{
		arangam.rowView.setY(y);
		arangam.columnView.setX(x);
	}

	// --- Helper classes starts------------------------------------------------------

	/**
	 * To listen for hypertext link update in help file.
	 */
	class HelpHyperlinkListener implements HyperlinkListener
	{
		//set the editor pane's page to the URL
		public void hyperlinkUpdate(HyperlinkEvent he)
		{
			if (he.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
			{
				try
				{
					JEditorPane p = (JEditorPane)he.getSource();
					p.setPage(he.getURL());
				}catch(IOException e)
				{
					//alert for exception and return
					Object[] options = {wordBundle.getString("ok")};
					Utils.showDialog(arangam,"helpFileCantLoadErr",JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE, Arangam.errIcon, options, 0);
					return;
				}
			}
		}
	}

	/**
	 * Makes the file chooser dialog not resizable
	 */
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

	// --- Helper classes ends-------------------------------------------------

}

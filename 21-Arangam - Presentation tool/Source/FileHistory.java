import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Provide a file history mechanism for the File menu of a parent frame.
 */
public class FileHistory
{
	/*** maximum length for file path */
	private static final int MAX_ITEM_LEN = 50;

	/*** File separator string of system */
	private static final String FILE_SEPARATOR_STR = System.getProperty("file.separator");

	/*** Configuration file name to store recent file */
	private static String historySerFile;

	/*** Maximum number of entry */
	private static int max_itemnames;

	/*** List to store recent file name */
	private static ArrayList itemnameHistory = new ArrayList(max_itemnames);

	/*** List to store recent file path */
	private static ArrayList pathnameHistory = new ArrayList(max_itemnames);

	/*** Application to which this utility is attached */
	private IFileHistory caller;

	/*** Application's file menu */
	private JMenu fileMenu;

	// --- IFileHistory interface ----------------------------------------------

	/**
	 * Interface that must be implemented by a GUI application frame
	 * that wants to use the FileHistory class.
	 */
	public static interface IFileHistory
	{

		/**
		 * Get the application name to identify the configuration file in the
		 * the USER_HOME directory. This name should be unique in this directory.
		 *
		 * @return the application name
		*/
		public String getApplicationName();

		/**
		 * Get a handle to the frame's file menu.
		 *
		 * @return the frame's file menu
		 */
		public JMenu getFileMenu();

		/**
		 * Return the size of the main application frame.
		 * It is used to center the file history maintenance window.
		 *
		 * @return the main GUI frame's size
		 */
		public Dimension getSize();

		/**
		 * Return the main application frame.
		 * It is used to center the file history maintenance window.
		 *
		 * @return the main GUI frame
		 */
		public JFrame getFrame();

		/**
		 * Perform the load file activity.
		 *
		 * @param path the path name of the loaded file
		 */
		public void loadFile(String pathname);
	}

	// -------------------------------------------------------------------------

	/**
	 * caller - the parent frame that hosts the file menu.
	 */
	public FileHistory(IFileHistory caller)
	{
		this.caller = caller;

		//file name to store recent files
		historySerFile = System.getProperty("user.home")
			+ FILE_SEPARATOR_STR
			+ caller.getApplicationName()
			+ "_FILE_HISTORY.cfg";
		String max_itemnames_str = System.getProperty("itemnames.max", "9");
		try
		{
			max_itemnames = Integer.parseInt(max_itemnames_str);
		}catch (NumberFormatException e)
		{
			System.exit(1);
		}
		if (max_itemnames < 1)
		{
			max_itemnames = 9;
		}

		//get file menu from caller (application)
		fileMenu = caller.getFileMenu();
	}

	/**
	* Initialize item name and path name arraylists from historySerFile.
	* build up the additional entries in the File menu.
	*/
	public final void initFileMenuHistory()
	{
		if (new File(historySerFile).exists())
		{
			try
			{
				FileInputStream fis = new FileInputStream(historySerFile);
				ObjectInputStream ois = new ObjectInputStream(fis);
				int itemnameCount = ois.readInt();

				// if the user has reduced filehistory size in the past: cut last items
				if (itemnameCount > max_itemnames)
				{
					itemnameCount = max_itemnames;
				}
				if (itemnameCount > 0)
				{
					fileMenu.addSeparator();
				}
				//add items by reading from "history file"
				for (int i=0; i<itemnameCount; i++)
				{
					itemnameHistory.add((String)ois.readObject());
					pathnameHistory.add((String)ois.readObject());
					MenuItemWithFixedTooltip item = new MenuItemWithFixedTooltip((i+1)+": "
						+(String)itemnameHistory.get(i));
					item.setToolTipText((String)pathnameHistory.get(i));

					//add corresponding listener
					item.addActionListener(new ItemListener(i));
					fileMenu.add(item);
				}
				ois.close();
				fis.close();
			}catch (Exception e)
			{
				System.err.println(e + "--Trouble reading file history entries");
			}
		}
	}

	/**
	* Save item name and path name arraylists to historySerFile.
	*/
	public void saveHistoryEntries()
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(historySerFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			int itemnameCount = itemnameHistory.size();
			oos.writeInt(itemnameCount);
			//write the file name and path to "history file"
			for (int i=0; i<itemnameCount; i++)
			{
				oos.writeObject((String)itemnameHistory.get(i));
				oos.writeObject((String)pathnameHistory.get(i));
			}
			oos.flush();
			oos.close();
			fos.close();
		}catch (Exception e)
		{
			System.err.println(e + "--Trouble saving file history entries");
		}
	}

	/**
	* Insert the last loaded path name into the File menu if it is not
	* present yet. Only max path names are shown. Every item starts with
	* "<i>: ", where <i> is in the range [1..max].
	* The loaded item name will become item number 1 in the list.
	*/
	public final void insertPathname(String pathname)
	{
		for (int k=0; k<pathnameHistory.size();k++)
		{
			//insert at top if the entry found already
			if (((String)pathnameHistory.get(k)).equals(pathname))
			{
				int index = fileMenu.getItemCount() - itemnameHistory.size() + k;
				fileMenu.remove(index);
				pathnameHistory.remove(k);
				itemnameHistory.remove(k);
				if (itemnameHistory.isEmpty())
				{
					//JSeparator is the last menu item at (index-1)
					fileMenu.remove(index-1);
				}
				//recursive call
				insertPathname(pathname);
				return;
			}
		}
		if (itemnameHistory.isEmpty())
		{
			fileMenu.addSeparator();
		}
		else
		{
			//remove all item name entries to prepare for re-arrangement
			for (int i=fileMenu.getItemCount()-1, j=0; j < itemnameHistory.size(); i--, j++)
			{
				fileMenu.remove(i);
			}
		}
		if (itemnameHistory.size() == max_itemnames)
		{
			// fileList is full: remove last entry to get space for the first item
			itemnameHistory.remove(max_itemnames-1);
			pathnameHistory.remove(max_itemnames-1);
		}

		itemnameHistory.add(0, getItemname(pathname));
		pathnameHistory.add(0, pathname);
		for (int i=0; i<itemnameHistory.size(); i++)
		{
			MenuItemWithFixedTooltip item = new MenuItemWithFixedTooltip((i+1)+": "
			+(String)itemnameHistory.get(i));
			item.setToolTipText((String)pathnameHistory.get(i));
			item.addActionListener(new ItemListener(i));
			fileMenu.add(item);
		}
	}

	/**
	* Remove the path name from the File menu.
	*/
	public void removePathname(String pathname)
	{
		//remove the entry if the file is not found
		for (int k=0; k<pathnameHistory.size();k++)
		{
			if (((String)pathnameHistory.get(k)).equals(pathname))
			{
				int index = fileMenu.getItemCount() - itemnameHistory.size() + k;
				fileMenu.remove(index);
				pathnameHistory.remove(k);
				itemnameHistory.remove(k);
				if (itemnameHistory.isEmpty())
				{
					//JSeparator is the last menu item at (index-1)
					fileMenu.remove(index-1);
				}
				return;
			}
		}
	}

	/**
	* Return the item name (abbreviated item name if necessary)
	* to be shown in the file menu open item list.
	* A maximum of MAX_ITEM_LEN characters is used for the
	* item name because we do not want to make the JMenuItem
	* entry too wide.
	*/
	protected String getItemname(String pathname)
	{
		final char FILE_SEPARATOR = FILE_SEPARATOR_STR.charAt(0);
		final int pathnameLen = pathname.length();
		// if the Pathan is short enough: return whole path name
		if (pathnameLen <= MAX_ITEM_LEN)
		{
			return pathname;
		}
		// if we have only one directory: return whole path name
		// we will not cut to MAX_ITEM_LEN here
		if (pathname.indexOf(FILE_SEPARATOR_STR) == pathname.lastIndexOf(FILE_SEPARATOR_STR))
		{
			return pathname;
		}
		else
		{
			// abbreviate path name: Windows OS like solution
			final int ABBREVIATED_PREFIX_LEN = 6; // e.g.: C:\..\
			final int MAX_PATHNAME_LEN = MAX_ITEM_LEN - ABBREVIATED_PREFIX_LEN;
			int firstFileSeparatorIndex = 0;
			for (int i=pathnameLen-1; i>=(pathnameLen-MAX_PATHNAME_LEN); i--)
			{
				if (pathname.charAt(i) == FILE_SEPARATOR)
				{
					firstFileSeparatorIndex = i;
				}
			}
			if (firstFileSeparatorIndex > 0)
			{
				return pathname.substring(0, 3)
					+ ".."
					+ pathname.substring(firstFileSeparatorIndex, pathnameLen);
			}
			else
			{
				return pathname.substring(0, 3)
					+ ".."
					+ FILE_SEPARATOR_STR
					+ ".."
					+ pathname.substring(pathnameLen-MAX_PATHNAME_LEN, pathnameLen);
			}
		}
	}

	/**
	* Create a JList instance with itemnameHistory as its model.
	*/
	private final JList createItemList()
	{
		ListModel model = new ListModel();
		JList list = new JList(model);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		return list;
	}


	// --- Helper classes ------------------------------------------------------

	/**
	* Create a tooltip location directly over the menu item,
	* ie, left align the tooltip text in "overlay" technique.
	*/
	private final class MenuItemWithFixedTooltip extends JMenuItem
	{

		public MenuItemWithFixedTooltip(String text)
		{
			super(text);
		}

		public Point getToolTipLocation(MouseEvent e)
		{
			//display tooltip the specific location
			Graphics g = getGraphics();
			FontMetrics metrics = g.getFontMetrics(g.getFont());
			String prefix = itemnameHistory.size() <= 9 ? "8: " : "88: ";
			int prefixWidth = metrics.stringWidth(prefix);
			int x = JButton.TRAILING + JButton.LEADING -1 + prefixWidth;
			return new Point(x, 0);
		}
	}

	/**
	 * Listen to menu item selections.
	 */
	private final class ItemListener implements ActionListener
	{
		int itemNbr;

		/**
		 * Listen to menu item specified by itemNbr
		 */
		public ItemListener(int itemNbr)
		{
			this.itemNbr = itemNbr;
		}

		/**
		 * Invoked when menu item is selected. Load the corresponding file
		 */
		public void actionPerformed(ActionEvent e)
		{
			JMenuItem item = (JMenuItem)e.getSource();
			FileHistory.this.insertPathname(item.getToolTipText());
			caller.loadFile(item.getToolTipText());
		}
	}

	/**
	 * The list model for our File History dialog itemList.
	 */
	private final class ListModel extends AbstractListModel
	{

		/**
		 * Gets element at position i
		 * @param i elements position
		 */
		public Object getElementAt(int i)
		{
			return itemnameHistory.get(i);
		}

		/**
		 * Gets size of list
		 * @return the size
		 */
		public int getSize()
		{
			return itemnameHistory.size();
		}

	}

}

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;

/**
 * Listen to key events in the file chooser. Used for speeding up file search
 * in file chooser
 */
public class TypeAheadSelector extends KeyAdapter implements PropertyChangeListener
{
	private JFileChooser chooser;
	private StringBuffer partialName = new StringBuffer();
	private Vector files;
	private boolean resetPartialName = true;

	/**
	 * Associates this class to the file chooser
	 * @param chooser the file chooser
	 */
	public TypeAheadSelector(JFileChooser chooser)
	{
		this.chooser = chooser;
		//find the list component in file chooser
		Component comp = findJList(chooser);
		//add key listener
		comp.addKeyListener(this);
		//add list data listener for updating files in a file vector
		setListDataListener();
		chooser.addPropertyChangeListener(this);
	}

	/**
	 * Add list data listener to the list component in file chooser
	 * Updates the vector containing file names
	 */
	private void setListDataListener()
	{
		final BasicDirectoryModel model = ((BasicFileChooserUI)chooser.getUI()).getModel();
		//if the contents are changed,
		//update the file vector
		model.addListDataListener(new ListDataListener() {
			public void contentsChanged(ListDataEvent lde) {
				Vector buffer = model.getFiles();
				if (buffer.size() > 0) {
					files = buffer;
				}
			}
			public void intervalAdded(ListDataEvent lde) {}
			public void intervalRemoved(ListDataEvent lde) {}
		});
	}

	/**
	 * Find list component present in the given component
	 * @param comp the component in which list is to be found
	 * @return the list component
	 */
	private Component findJList(Component comp)
	{
		//found a list component
		if (comp instanceof JList) return comp;

		//recursively call till the component has no child
		if (comp instanceof Container) {
			Component[] components = ((Container)comp).getComponents();
			for(int i = 0; i < components.length; i++) {
				Component child = findJList(components[i]);
				if (child != null) return child;
			}
		}
		//no list component
		return null;
	}

	/**
	 * Invoked when a key is typed
	 */
	public void keyTyped(KeyEvent ke)
	{
		//approve the file selection if "enter" key is pressed
		if (ke.getKeyChar() == KeyEvent.VK_ENTER)
		{
			if (chooser.getSelectedFile() != null)
				if (chooser.getSelectedFile().isFile())
					chooser.approveSelection();
		}

		//else append that char to partialName
		partialName.append(ke.getKeyChar());

		//change to uppercase - to ignore case
		String upperCasePartialName = partialName.toString().toUpperCase();

		if(files != null)
		{
			//choose the file, if it starts with partialName
			for(int i = 0; i < files.size(); i++)
			{
				File item = (File)files.get(i);
				String name = item.getName().toUpperCase();
				if (name.startsWith(upperCasePartialName))
				{
					resetPartialName = false;
					chooser.setSelectedFile(item);
					return;
				}
			}
		}
		else return;
	}

	/**
	 * Invoked when the file choosers property changed
	 * Resets the partial name if file selection changes
	 */
	public void propertyChange(PropertyChangeEvent e)
	{
		//reset the partial name if file selection changes
		String propertyName = e.getPropertyName();
		if (propertyName.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
			if (resetPartialName) partialName.setLength(0);
			resetPartialName = true;
		}
	}
}

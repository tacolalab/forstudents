import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;

/**
 * Create a panel containing list for font name and size
 */
public class OpenList extends JPanel implements ListSelectionListener, ActionListener
{
	/**
	 * Gets the words in current language. Tamil, English or
	 * Tamil-English
	 */
	private ResourceBundle wordBundle;

	/*** Title (label) for list */
	protected JLabel m_title;

	/*** Text field to show selected item */
	protected JTextField m_text;

	/*** Text field to show all item */
	protected JList m_list;
	protected JScrollPane m_scroll;

	/*** Reference to bilingual font used in application */
	private Font bilingualFont;

	/*** Reference to English font used in application */
	Font englishFont;

	/**
	 * Create list for font name and size
	 * @param data array to initialise list
	 * @param title list title
	 */
	public OpenList(String[] data, String title)
	{
		setLayout(null);

		//construct components and add them to this panel
		m_title = new JLabel(title, JLabel.LEFT);
		add(m_title);

		m_text = new JTextField();
		m_text.addActionListener(this);
		add(m_text);

		m_list = new JList(data);
		m_list.setVisibleRowCount(4);
		m_list.addListSelectionListener(this);
		m_scroll = new JScrollPane(m_list);
		add(m_scroll);

		//set font
		englishFont = Arangam.englishFont;
		bilingualFont = Arangam.bilingualFont;
		m_title.setFont(bilingualFont);
		m_text.setFont(englishFont);
		m_list.setFont(englishFont);
	}

	/**
	 * Changes the selected value for "font name" list
	 * @param sel the value to be selected
	 */
	public void setSelected(String sel)
	{
		m_list.setSelectedValue(sel, true);
		m_text.setText(sel);
	}

	/**
	 * Gets the selected value for "font name" list
	 * @return the selected value
	 */
	public String getSelected()
	{
		return m_text.getText();
	}

	/**
	 * Changes the selected value for "font size" list
	 * @param value the value to be selected
	 */
	public void setSelectedInt(int value)
	{
		setSelected(Integer.toString(value));
	}

	/**
	 * Gets the selected value for "font size" list
	 * @return the selected value
	 */
	public int getSelectedInt()
	{
		try
		{
			return Integer.parseInt(getSelected());
		}catch (NumberFormatException ex) { return -1; }
	}

	/**
	 * Called whenever the value of the selection changes.
	 */
	public void valueChanged(ListSelectionEvent e)
	{
		Object obj = m_list.getSelectedValue();
		if (obj != null)
			m_text.setText(obj.toString());
	}

	/**
	 * Invoked when an action occurs. Enter key is pressed in text field
	 */
	public void actionPerformed(ActionEvent e)
	{
		ListModel model = m_list.getModel();
		String key = m_text.getText().toLowerCase();
		for (int k=0; k<model.getSize(); k++)
		{
			String data = (String)model.getElementAt(k);
			if (data.toLowerCase().startsWith(key))
			{
				m_list.setSelectedValue(data, true);
				break;
			}
		}
	}

	/**
	 * Add list selection listener to list
	 */
	public void addListSelectionListener(ListSelectionListener lst)
	{
		m_list.addListSelectionListener(lst);
	}

	/**
	 * Gets the preferred size for the panel
	 * @return the preferred size
	 */
	public Dimension getPreferredSize()
	{
		//preferred size depend on all the components preferred size
		Insets ins = getInsets();
		Dimension d1 = m_title.getPreferredSize();
		Dimension d2 = m_text.getPreferredSize();
		Dimension d3 = m_scroll.getPreferredSize();
		int w = Math.max(Math.max(d1.width, d2.width), d3.width);
		int h = d1.height + d2.height + d3.height;
		return new Dimension(w+ins.left+ins.right,
		h+ins.top+ins.bottom);
	}

	/**
	 * Gets the maximum size for the panel
	 * @return the maximum size
	 */
	public Dimension getMaximumSize()
	{
		//maximum size depend on all the components maximum size
		Insets ins = getInsets();
		Dimension d1 = m_title.getMaximumSize();
		Dimension d2 = m_text.getMaximumSize();
		Dimension d3 = m_scroll.getMaximumSize();
		int w = Math.max(Math.max(d1.width, d2.width), d3.width);
		int h = d1.height + d2.height + d3.height;
		return new Dimension(w+ins.left+ins.right,
			h+ins.top+ins.bottom);
	}

	/**
	 * Gets the minimum size for the panel
	 * @return the minimum size
	 */
	public Dimension getMinimumSize()
	{
		//minimum size depend on all the components maximum size
		Insets ins = getInsets();
		Dimension d1 = m_title.getMinimumSize();
		Dimension d2 = m_text.getMinimumSize();
		Dimension d3 = m_scroll.getMinimumSize();
		int w = Math.max(Math.max(d1.width, d2.width), d3.width);
		int h = d1.height + d2.height + d3.height;
		return new Dimension(w+ins.left+ins.right,
		h+ins.top+ins.bottom);
	}

	/**
	 * Prompts the layout manager to lay out this component
	 */
	public void doLayout()
	{
		//set bounds depend on all the components preferred size
		Insets ins = getInsets();
		Dimension d = getSize();
		int x = ins.left;
		int y = ins.top;
		int w = d.width-ins.left-ins.right;
		int h = d.height-ins.top-ins.bottom;
		Dimension d1 = m_title.getPreferredSize();
		m_title.setBounds(x, y, w, d1.height);
		y += d1.height;
		Dimension d2 = m_text.getPreferredSize();
		m_text.setBounds(x, y, w, d2.height);
		y += d2.height;
		m_scroll.setBounds(x, y, w, h-y);
	}

	/**
	 * Sets the title text of list in current locale
	 * @param r the resource bundle
	 * @param s the string
	 */
	public void setText(ResourceBundle r, String s)
	{
		m_title.setText(r.getString(s));
	}

}

package rcword;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.rtf.*;
import javax.swing.undo.*;

class FontDialog extends JDialog
{
	  protected int m_option = JOptionPane.CLOSED_OPTION;
	  protected OpenList m_lstFontName;
	  protected OpenList m_lstFontSize;
	  protected MutableAttributeSet m_attributes;
	  protected JCheckBox m_chkBold;
	  protected JCheckBox m_chkItalic;
	  protected JCheckBox m_chkUnderline;

	  protected JCheckBox m_chkStrikethrough;
	  protected JCheckBox m_chkSubscript;
	  protected JCheckBox m_chkSuperscript;

	  /*** Label indicating "Color:" */
	  protected JLabel m_colorLabel;

	  /*** Preview pane */
	  protected JTextPane m_preview;

	  /** Panel showing font styles like bold, italic */
	  JPanel effectsPanel;

	  /** Panel showing font name, size */
	   JPanel fontPanel;

	  /*** Button indicating background color */
	  protected JButton m_btColor;

	  /**	 Appropriate font for the current local for entire GUI.  */
	  static Font defaultfont ;

	  /** Panel for previewing the attributes */
	  JPanel previewPanel;

	  /*** Button for applying attribute to text */
	  JButton btOK = new JButton("OK");

	  /*** Button for closing this dialog */
	  JButton btCancel = new JButton();

	  Word parentword;


	  public FontDialog(Word word)
	  {
			super(word, "Font", true);

			parentword = word;

			defaultfont = parentword.defaultfont;

			getContentPane().setLayout(new BoxLayout(getContentPane(),
			  BoxLayout.Y_AXIS));

			fontPanel = new JPanel(new GridLayout(1, 2, 10, 2));
			fontPanel.setBorder(new TitledBorder(new EtchedBorder(), "â¿î¢¼"));
			fontPanel.setFont(defaultfont);

			//initialise font name and size
			String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().
									getAvailableFontFamilyNames();
			String[] fontSizes= {"8","9","10","12","14","16","18","20","24","28",
								"32","36","40","44","48","54","60","66","72","80",
								"88","96" };

			//construct font name list
			m_lstFontName = new OpenList(fontNames, null, true);    // OpenList for text
			m_lstFontName.m_title.setFont(defaultfont);
			fontPanel.add(m_lstFontName);

			//construct font size list
			m_lstFontSize = new OpenList(fontSizes, null, false);	// OpenList for numbers
			m_lstFontSize.m_title.setFont(defaultfont);
			fontPanel.add(m_lstFontSize);

			getContentPane().add(fontPanel);

			effectsPanel = new JPanel(new GridLayout(2, 3, 2, 2));
			effectsPanel.setBorder(new TitledBorder(new EtchedBorder()));
			effectsPanel.setFont(defaultfont);

			//construct components for other styles and add them
			m_chkBold = new JCheckBox("Bold");
			m_chkBold.setFont(defaultfont);
			effectsPanel.add(m_chkBold);
			m_chkItalic = new JCheckBox("Italic");
			m_chkItalic.setFont(defaultfont);
			effectsPanel.add(m_chkItalic);
			m_chkUnderline = new JCheckBox("Underline");
			m_chkUnderline.setFont(defaultfont);
			effectsPanel.add(m_chkUnderline);
			m_chkStrikethrough = new JCheckBox("Strikeout");
			m_chkStrikethrough.setFont(defaultfont);
			effectsPanel.add(m_chkStrikethrough);
			m_chkSubscript = new JCheckBox("Subscript");
			m_chkSubscript.setFont(defaultfont);
			effectsPanel.add(m_chkSubscript);
			m_chkSuperscript = new JCheckBox("Superscript");
			m_chkSuperscript.setFont(defaultfont);
			effectsPanel.add(m_chkSuperscript);
			getContentPane().add(effectsPanel);

			getContentPane().add(Box.createVerticalStrut(5));

			JPanel p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
			p.add(Box.createHorizontalStrut(10));
			m_colorLabel = new JLabel();
			m_colorLabel.setFont(defaultfont);
			p.add(m_colorLabel);
			p.add(Box.createHorizontalStrut(20));
			m_btColor = new JButton(" ");
		    p.add(m_btColor);
			p.add(Box.createHorizontalStrut(10));
			getContentPane().add(p);

			previewPanel = new JPanel(new BorderLayout());
			previewPanel.setBorder(new TitledBorder(new EtchedBorder()));//, "Preview"));
			//m_preview = new JLabel("Preview Font", JLabel.CENTER);
			m_preview = new JTextPane();
			m_preview.setBackground(Color.white);
			m_preview.setForeground(Color.black);
			m_preview.setOpaque(true);
			m_preview.setBorder(new LineBorder(Color.black));
			m_preview.setPreferredSize(new Dimension(120, 40));
			previewPanel.add(m_preview, BorderLayout.CENTER);
			getContentPane().add(previewPanel);

			p = new JPanel(new FlowLayout());
			JPanel p1 = new JPanel(new GridLayout(1, 2, 10, 2));

			ActionListener OKlst = new ActionListener()
				{
					  public void actionPerformed(ActionEvent e)
					  {
						m_option = JOptionPane.OK_OPTION;
						System.out.println(" 		Error status at OK button:"+m_lstFontSize.errorOccured);

						try
						{
							int tempFontSize = Integer.parseInt(m_lstFontSize.m_text.getText());
						}
						catch (NumberFormatException ex)
						{
							m_lstFontSize.errorOccured = true;
							Utils.warning(m_lstFontSize,"InvalidNumber");
							m_lstFontSize.m_text.setText("");
						}
						System.out.println(" 		Error status at OK button:"+m_lstFontSize.errorOccured);


						if(m_lstFontSize.errorOccured)
							setVisible(true);
						else
						{
							setVisible(false);
							dispose();
						}

					 }
				};
			btOK.addActionListener(OKlst);
			btOK.setFont(defaultfont);
			p1.add(btOK);

			btCancel = new JButton("Cancel");
			btCancel.setFont(defaultfont);
			ActionListener Cancellst = new ActionListener()
				{
				  	public void actionPerformed(ActionEvent e)
				  	{
						m_option = JOptionPane.CANCEL_OPTION;
						setVisible(false);
						dispose();
					}
				};
			btCancel.addActionListener(Cancellst);
			p1.add(btCancel);
			p.add(p1);
			getContentPane().add(p);

			System.out.println(" UP In side the constrctor");
			ListSelectionListener lsel = new ListSelectionListener()
				{
					  public void valueChanged(ListSelectionEvent e)
					  {
						System.out.println(" UP 1");
						updatePreview();
					  }
				};
			m_lstFontName.addListSelectionListener(lsel);
			m_lstFontSize.addListSelectionListener(lsel);

			//listener for check boxes
			ActionListener chkBoxlst = new ActionListener()
				{
				  public void actionPerformed(ActionEvent e)
				  {
					  System.out.println(" UP 2");
						updatePreview();
				  	}
				};

			//listener for "foreground color" button
			ActionListener btColorlst = new ActionListener()
				{
					//open up a color chooser dialog to choose text color
					public void actionPerformed(ActionEvent e)
					{
						Color newFgColor = JColorChooser.showDialog(
							FontDialog.this,"Choose Color",m_btColor.getBackground());
						if(newFgColor!=null)
						{
							m_btColor.setBackground(newFgColor);
							System.out.println(" UP 3");
							updatePreview();
						}
					}
				};

			m_chkBold.addActionListener(chkBoxlst);
			m_chkItalic.addActionListener(chkBoxlst);
			m_chkUnderline.addActionListener(chkBoxlst);
			m_chkSubscript.addActionListener(chkBoxlst);
			m_chkSuperscript.addActionListener(chkBoxlst);
			m_btColor.addActionListener(btColorlst);

			pack();
			setResizable(false);
			setLocale();
			/*Dimension d1 = getSize();
			Dimension d2 = parentword.getSize();
			int x = Math.max((d2.width-d1.width)/2, 0);
			int y = Math.max((d2.height-d1.height)/2, 0);
			setBounds(x, y, d1.width, d1.height);
			*/
			setLocation(Utils.getMiddle(getSize()));

			addKeyListener(new EscKeyListener((Component)this,true));

	  }

	  public void setAttributes(AttributeSet a)
	  {
			m_attributes = new SimpleAttributeSet(a);
			String name = StyleConstants.getFontFamily(a);
			m_lstFontName.setSelected(name);
			int size = StyleConstants.getFontSize(a);
			m_lstFontSize.setSelectedInt(size);
			m_chkBold.setSelected(StyleConstants.isBold(a));
			m_chkItalic.setSelected(StyleConstants.isItalic(a));
			m_chkUnderline.setSelected(StyleConstants.isUnderline(a));
			m_chkStrikethrough.setSelected(StyleConstants.isStrikeThrough(a));
			m_chkSubscript.setSelected(StyleConstants.isSubscript(a));
			m_chkSuperscript.setSelected(StyleConstants.isSuperscript(a));
			m_btColor.setBackground(StyleConstants.getForeground(a));
			System.out.println(" UP 4");
			updatePreview();
	  }

	  public AttributeSet getAttributes()
	  {
		if (m_attributes == null)
		  return null;
		StyleConstants.setFontFamily(m_attributes,m_lstFontName.getSelected());

		System.out.println(" at 1 getAttributes");
		int fontSize = m_lstFontSize.getSelectedInt();

		if(fontSize > 1200 )
			fontSize = 1200;
		StyleConstants.setFontSize(m_attributes,fontSize);
		StyleConstants.setBold(m_attributes, m_chkBold.isSelected());
		StyleConstants.setItalic(m_attributes, m_chkItalic.isSelected());
		StyleConstants.setUnderline(m_attributes, m_chkUnderline.isSelected());
		StyleConstants.setStrikeThrough(m_attributes, m_chkStrikethrough.isSelected());
		StyleConstants.setSubscript(m_attributes, m_chkSubscript.isSelected());
		StyleConstants.setSuperscript(m_attributes, m_chkSuperscript.isSelected());
		StyleConstants.setForeground(m_attributes, m_btColor.getBackground());
		return m_attributes;
	  }

	  public int getOption()
	  {
		  return m_option;
	  }

	  protected void updatePreview()
	  {
			MutableAttributeSet a=new SimpleAttributeSet();
			StyleConstants.setFontFamily(a,m_lstFontName.getSelected());

			int fontSize = m_lstFontSize.getSelectedInt();

			//can't generate preview for large text size
			if(fontSize > 1200)
				fontSize = 1200;

			StyleConstants.setFontSize(a,fontSize);
			StyleConstants.setBold(a,m_chkBold.isSelected());
			StyleConstants.setItalic(a,m_chkItalic.isSelected());
			StyleConstants.setUnderline(a,m_chkUnderline.isSelected());
			StyleConstants.setSubscript(a,m_chkSubscript.isSelected());
			StyleConstants.setStrikeThrough(a,m_chkStrikethrough.isSelected());
			StyleConstants.setSuperscript(a,m_chkSuperscript.isSelected());
			StyleConstants.setForeground(a,m_btColor.getBackground());
			StyleConstants.setAlignment(a,StyleConstants.ALIGN_CENTER);
			m_preview.setText(StyleConstants.getFontFamily(a));
			m_preview.setSelectionColor(Color.white);
			m_preview.selectAll();
			m_preview.setCharacterAttributes(a,false);
			m_preview.repaint();
	  }

	  /**
	  	 * Sets the locale for components depends on the current Locale.
	  	 */
	  public void setLocale()
	  {
	  		ResourceBundle wordBundle = parentword.wordBundle;

	  		setTitle(wordBundle.getString("fontE"));
	  	 	m_lstFontName.setText(wordBundle,"Name");
	  	 	m_lstFontSize.setText(wordBundle,"Size");

	  		m_colorLabel.setText(wordBundle.getString("Color"));
	  		m_chkBold.setText(wordBundle.getString("Bold"));
	  		m_chkItalic.setText(wordBundle.getString("Italic"));
	  		m_chkUnderline.setText(wordBundle.getString("Underline"));
	  		m_chkSubscript.setText(wordBundle.getString("Subscript"));
	  		m_chkSuperscript.setText(wordBundle.getString("Superscript"));
	  		m_chkStrikethrough.setText(wordBundle.getString("StrikeThrough"));

	  		((TitledBorder)fontPanel.getBorder()).setTitle(wordBundle.getString("Font"));
	  		((TitledBorder)effectsPanel.getBorder()).setTitle(wordBundle.getString("Style"));
	  		((TitledBorder)previewPanel.getBorder()).setTitle(wordBundle.getString("Preview"));

	  		btOK.setText(wordBundle.getString("Ok"));
	  		btCancel.setText(wordBundle.getString("Cancel"));
	  		pack();
	  		setSize(getPreferredSize());
	 }

}


class OpenList extends JPanel implements ListSelectionListener, ActionListener
{
	  protected JLabel m_title;
	  protected JTextField m_text;
	  protected JList m_list;
	  protected JScrollPane m_scroll;
	  protected boolean contentType; // this is to specify whether the content of this openList is text or number. True for text and false for number.

	  boolean errorOccured = false;

	  public OpenList(String[] data, String title, boolean contentType)
	  {
			setLayout(null);
			m_title = new JLabel(title, JLabel.LEFT);

			add(m_title);
			m_text = new JTextField(data[0]);
			m_text.addActionListener(this);
			add(m_text);
			m_list = new JList(data);
			m_list.setVisibleRowCount(4);
			m_list.addListSelectionListener(this);
			m_scroll = new JScrollPane(m_list);
			add(m_scroll);
	  }

	  public void setSelected(String sel)
	  {
			m_list.setSelectedValue(sel, true);
			if(contentType == false)
			{   System.out.println("         Seting value at setSelected:"+sel);
				m_text.setText(sel);
			}
	  }

	  public String getSelected()
	  {
		  return m_text.getText();
	  }

	  public void setSelectedInt(int value)
	  {
			setSelected(Integer.toString(value));
	  }

	  public int getSelectedInt()
	  {
		try
		{
			int tempInt = Integer.parseInt(getSelected());
			errorOccured = false;
			System.out.println("         error status changing to false:"+errorOccured);
		  	return tempInt;
		}
		catch (NumberFormatException ex)
		{
			if(contentType == false) // if the content type of this openLIst is Number.
			{
				System.out.println(" 		Warning at getSelectedInt()");
				errorOccured = true;
				System.out.println("         error status changing to True:"+errorOccured);

				Utils.warning(this,"InvalidNumber");
				m_text.setText("");

			}
			return -1;
		}


	  }

	  public void valueChanged(ListSelectionEvent e)
	  {
		Object obj = m_list.getSelectedValue();
		if (obj != null)
			if(contentType == false)
			{
				System.out.println("         Seting value at valueChange:"+obj.toString());
				m_text.setText(obj.toString());
			}
	  }

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

	  public void addListSelectionListener(ListSelectionListener lst)
	  {
		m_list.addListSelectionListener(lst);
	  }

	  public Dimension getPreferredSize()
	  {
			Insets ins = getInsets();
			Dimension d1 = m_title.getPreferredSize();
			Dimension d2 = m_text.getPreferredSize();
			Dimension d3 = m_scroll.getPreferredSize();
			int w = Math.max(Math.max(d1.width, d2.width), d3.width);
			int h = d1.height + d2.height + d3.height;
			return new Dimension(w+ins.left+ins.right,
			  h+ins.top+ins.bottom);
	  }

	  public Dimension getMaximumSize()
	  {
			Insets ins = getInsets();
			Dimension d1 = m_title.getMaximumSize();
			Dimension d2 = m_text.getMaximumSize();
			Dimension d3 = m_scroll.getMaximumSize();
			int w = Math.max(Math.max(d1.width, d2.width), d3.width);
			int h = d1.height + d2.height + d3.height;
			return new Dimension(w+ins.left+ins.right,
			  h+ins.top+ins.bottom);
	  }

	  public Dimension getMinimumSize()
	  {
			Insets ins = getInsets();
			Dimension d1 = m_title.getMinimumSize();
			Dimension d2 = m_text.getMinimumSize();
			Dimension d3 = m_scroll.getMinimumSize();
			int w = Math.max(Math.max(d1.width, d2.width), d3.width);
			int h = d1.height + d2.height + d3.height;
			return new Dimension(w+ins.left+ins.right,
			  h+ins.top+ins.bottom);
	  }

	  public void doLayout()
	  {
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

class ColorComboRenderer extends JPanel implements ListCellRenderer
{
	  protected Color m_color = Color.black;
	  protected Color m_focusColor =
		(Color) UIManager.get("List.selectionBackground");
	  protected Color m_nonFocusColor = Color.white;

	  public Component getListCellRendererComponent(JList list,
	   Object obj, int row, boolean sel, boolean hasFocus)
	  {
			if (hasFocus || sel)
			  setBorder(new CompoundBorder(
				new MatteBorder(2, 10, 2, 10, m_focusColor),
				new LineBorder(Color.black)));
			else
			  setBorder(new CompoundBorder(
				new MatteBorder(2, 10, 2, 10, m_nonFocusColor),
				new LineBorder(Color.black)));

			if (obj instanceof Color)
			  m_color = (Color) obj;
			return this;
	  }

	  public void paintComponent(Graphics g)
	  {
		setBackground(m_color);
		super.paintComponent(g);
	  }
}

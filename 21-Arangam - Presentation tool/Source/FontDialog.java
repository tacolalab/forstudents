import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;

/**
 * Custom dialog window that let the user change font format options.
 * The format options are:
 * <OL><LI>font name
 * <LI>color
 * <LI>font size
 * <LI>font style
 */

public class FontDialog extends JDialog
{
	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;
	/*** Bilingual(Tamil and English) font used */
	private Font bilingualFont;
	/*** Main class reference */
	private Arangam arangam;

	/*** Reference to the focused component */
	private ConnectComponent focused;

	/*** Text Attributes  */
	protected MutableAttributeSet m_attributes;

	/*** Closed option in JOptionPane */
	protected int m_option = JOptionPane.CLOSED_OPTION;

	/*** Font name list */
	protected OpenList m_lstFontName;

	/*** Font size list */
	protected OpenList m_lstFontSize;

	/*** Check box for "bold" attribute */
	protected JCheckBox m_chkBold;

	/*** Check box for "italic" attribute */
	protected JCheckBox m_chkItalic;

	/*** Check box for "underline" attribute */
	protected JCheckBox m_chkUnderline;

	/*** Check box for "subscript" attribute */
	protected JCheckBox m_chkSubscript;

	/*** Check box for "superscript" attribute */
	protected JCheckBox m_chkSuperscript;

	/*** Label indicating "Color:" */
	protected JLabel m_colorLabel;

	/*** Button indicating background color */
	protected JButton m_btColor;

	/*** Preview pane */
	protected JTextPane m_preview;

	/*** Button for applying attribute to text */
	JButton btOk = new JButton();

	/*** Button for closing this dialog */
	JButton btCancel = new JButton();

	/** Panel showing font name, size */
	JPanel fontPanel;
	/** Panel showing font styles like bold, italic */
	JPanel effectsPanel;
	/** Panel for previewing the attributes */
	JPanel previewPanel;

	/**
	 * Creates a custom dialog with specified Frame as its owner.
	 * @param arangam the Arangam Frame from which the dialog is run
	 * @param focused the ConnectComponent for which the dialog is displayed
	 * @see OpenList
	 */
	public FontDialog(Arangam arangam,ConnectComponent focused)
	{
		super(arangam,true);
		this.arangam = arangam;
		this.focused = focused;

		bilingualFont = arangam.getBilingualFont();

		//set box layout
		getContentPane().setLayout(new BoxLayout(getContentPane(),
				BoxLayout.Y_AXIS));

		//font panel
		fontPanel = new JPanel(new GridLayout(1, 2, 10, 2));
		fontPanel.setBorder(new TitledBorder(new EtchedBorder()));


		//initialise font name and size
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().
								getAvailableFontFamilyNames();
		String[] fontSizes= {"8","9","10","12","14","16","18","20","24","28",
							"32","36","40","44","48","54","60","66","72","80",
							"88","96" };

		//construct font name list
		m_lstFontName = new OpenList(fontNames, null);
		fontPanel.add(m_lstFontName);

		//construct font size list
		m_lstFontSize = new OpenList(fontSizes, null);
		fontPanel.add(m_lstFontSize);

		getContentPane().add(fontPanel);

		effectsPanel = new JPanel(new GridLayout(2, 3, 2, 2));
		effectsPanel.setBorder(new TitledBorder(new EtchedBorder()));

		//construct components for other styles and add them
		m_chkBold = new JCheckBox();
		effectsPanel.add(m_chkBold);
		m_chkItalic = new JCheckBox();
		effectsPanel.add(m_chkItalic);
		m_chkUnderline = new JCheckBox();
		effectsPanel.add(m_chkUnderline);
		m_chkSubscript = new JCheckBox();
		effectsPanel.add(m_chkSubscript);
		m_chkSuperscript = new JCheckBox();
		effectsPanel.add(m_chkSuperscript);
		getContentPane().add(effectsPanel);

		getContentPane().add(Box.createVerticalStrut(5));

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.add(Box.createHorizontalStrut(10));
		m_colorLabel = new JLabel();
		p.add(m_colorLabel);
		p.add(Box.createHorizontalStrut(20));
		m_btColor = new JButton(" ");
		p.add(m_btColor);
		p.add(Box.createHorizontalStrut(10));
		getContentPane().add(p);

		previewPanel = new JPanel(new BorderLayout());
		previewPanel.setBorder(new TitledBorder(new EtchedBorder()));
		m_preview = new JTextPane();
		m_preview.setBackground(Color.white);
		m_preview.setForeground(Color.black);
		m_preview.setBorder(new LineBorder(Color.black));
		m_preview.setPreferredSize(new Dimension(120, 40));
		previewPanel.add(m_preview, BorderLayout.CENTER);
		getContentPane().add(previewPanel);

		p = new JPanel(new FlowLayout());
		JPanel p1 = new JPanel(new GridLayout(1, 2, 10, 2));

		p1.add(btOk);
		p1.add(btCancel);
		p.add(p1);
		getContentPane().add(p);

		setLocale();
		setResizable(false);
		setLocation(Utils.getMiddle(getSize()));

		//listen list for value changes
		ListSelectionListener lsel = new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				updatePreview();
			}
		};

		//listener for ok button
		ActionListener btOKlst = new ActionListener()
		{
			//Apply the Font Dialog attribute to text
			//and close the dialog
			public void actionPerformed(ActionEvent e)
			{
				String focusedName = FontDialog.this.focused.getClass().getName();
				m_option = JOptionPane.OK_OPTION;
				if(focusedName.equals("AText"))
				{
					((ConnectText)FontDialog.this.focused).
						setAttributeSet(getAttributes(),false);
				}
				else if(focusedName.equals("AShape"))
				{
					((ConnectShape)FontDialog.this.focused).
						setFontAttributes(getAttributes());
				}
				setVisible(false);
				dispose();
			}
		};

		//listener for cancel button
		ActionListener btCancellst = new ActionListener()
		{
			//close the dialog
			public void actionPerformed(ActionEvent e)
			{
				m_option = JOptionPane.CANCEL_OPTION;
				setVisible(false);
				dispose();
			}
		};

		//listener for check boxes
		ActionListener chkBoxlst = new ActionListener()
		{
			//update the preview pane
			public void actionPerformed(ActionEvent e)
			{
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
					updatePreview();
				}
			}
		};

		//register listeners
		m_lstFontName.addListSelectionListener(lsel);
		m_lstFontSize.addListSelectionListener(lsel);
		btOk.addActionListener(btOKlst);
		btCancel.addActionListener(btCancellst);
		m_chkBold.addActionListener(chkBoxlst);
		m_chkItalic.addActionListener(chkBoxlst);
		m_chkUnderline.addActionListener(chkBoxlst);
		m_chkSubscript.addActionListener(chkBoxlst);
		m_chkSuperscript.addActionListener(chkBoxlst);
		m_btColor.addActionListener(btColorlst);

		addKeyListener(new EscKeyListener((Component)this,true));
	}

	/**
	 * Updates the preview panel with new attributes
	 */
	protected void updatePreview()
	{
		MutableAttributeSet a=new SimpleAttributeSet();
		StyleConstants.setFontFamily(a,m_lstFontName.getSelected());
		int fontSize = m_lstFontSize.getSelectedInt();

		//can't generate preview for large text size
		if(fontSize > 25)
			fontSize = 25;

		StyleConstants.setFontSize(a,fontSize);
		StyleConstants.setBold(a,m_chkBold.isSelected());
		StyleConstants.setItalic(a,m_chkItalic.isSelected());
		StyleConstants.setUnderline(a,m_chkUnderline.isSelected());
		StyleConstants.setSubscript(a,m_chkSubscript.isSelected());
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
	 * Returns the attributes to be applied to text component.
	 * @return the attributes to be applied to component.
	 */
	public AttributeSet getAttributes()
	{
		if (m_attributes == null)
			return null;
		StyleConstants.setFontFamily(m_attributes,m_lstFontName.getSelected());
		StyleConstants.setFontSize(m_attributes,m_lstFontSize.getSelectedInt());
		StyleConstants.setBold(m_attributes,m_chkBold.isSelected());
		StyleConstants.setItalic(m_attributes,m_chkItalic.isSelected());
		StyleConstants.setUnderline(m_attributes,m_chkUnderline.isSelected());
		StyleConstants.setSubscript(m_attributes,m_chkSubscript.isSelected());
		StyleConstants.setSuperscript(m_attributes,m_chkSuperscript.isSelected());
		StyleConstants.setForeground(m_attributes,m_btColor.getBackground());
		return m_attributes;
	}

	/**
	 * Sets the attributes for FontDialog. Called on construction
	 * @param the attributes of the current component.
	 */
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
		m_chkSubscript.setSelected(StyleConstants.isSubscript(a));
		m_chkSuperscript.setSelected(StyleConstants.isSuperscript(a));
		m_btColor.setBackground(StyleConstants.getForeground(a));
		updatePreview();
	}

	/**
	 * Sets the locale for components depends on the current Locale.
	 */
	public void setLocale()
	{
		wordBundle = arangam.getWordBundle();
		setTitle(wordBundle.getString("fontE"));
	 	m_lstFontName.setText(wordBundle,"name");
	 	m_lstFontSize.setText(wordBundle,"size");

		m_colorLabel.setText(wordBundle.getString("color"));
		m_chkBold.setText(wordBundle.getString("bold"));
		m_chkItalic.setText(wordBundle.getString("italic"));
		m_chkUnderline.setText(wordBundle.getString("underline"));
		m_chkSubscript.setText(wordBundle.getString("subscript"));
		m_chkSuperscript.setText(wordBundle.getString("superscript"));

		((TitledBorder)fontPanel.getBorder()).setTitle(wordBundle.getString("font"));
		((TitledBorder)effectsPanel.getBorder()).setTitle(wordBundle.getString("style"));
		((TitledBorder)previewPanel.getBorder()).setTitle(wordBundle.getString("preview"));

		btOk.setText(wordBundle.getString("ok"));
		btCancel.setText(wordBundle.getString("cancel"));
		pack();
		setSize(getPreferredSize());
	}

}

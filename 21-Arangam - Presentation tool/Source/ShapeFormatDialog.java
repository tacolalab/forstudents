import java.awt.*;
import java.util.ResourceBundle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;

/**
 * Custom dialog window that let the user change shape format options.
 * The format options are:
 * <OL><LI>line color
 * <LI>line style - solid or dashing styles
 * <LI>line width
 * <LI>fill color
 */
public class ShapeFormatDialog extends JDialog implements ActionListener
{
	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** ActionsImpl class reference */
 	private transient ConnectActions actionsConnection;

	/*** Main class reference */
	private Arangam arangam;

	/*** Shape component reference */
	private AShape aShape;

	/*** Button for applying the attributes */
	private JButton btOk;

	/*** Button for closing window */
	private JButton btCancel;

	/*** Button for selecting background color */
	private JButton m_btBgColor;

	/*** Button for selecting outline color */
	private JButton m_btOutColor;

	/*** Combo box for selecting line width */
	private JComboBox m_comboLineWidth;

	/*** Combo box for selecting line width */
	private JComboBox m_comboLineStyle;

	/*** Store Shape attribute */
	private AttributeSet m_attributes;

	/*** Label indicating "Fill color" */
	protected JLabel fillColorLabel = new JLabel();

	/*** Label indicating "Outline color" */
	protected JLabel outlineColorLabel = new JLabel();

	/*** Label indicating "Line width" */
	protected JLabel outlineWidthLabel = new JLabel();

	/*** Label indicating "Line style" */
	protected JLabel outlineStyleLabel = new JLabel();

	/**
	 * Creates a custom dialog with specified Frame as its owner.
	 * @param arangam the Arangam Frame from which the dialog is run
	 * @param focused the ConnectComponent for which the dialog is displayed
	 */
	public ShapeFormatDialog(Arangam arangam,AShape aShape)
	{
		super(arangam,true);
		this.aShape = aShape;
		this.arangam = arangam;

		setTitle("Shape Format");

		//panel for showing text attributes
		JPanel textboxPane = new JPanel();
		textboxPane.setLayout(new BoxLayout(textboxPane, BoxLayout.Y_AXIS ) );

		//panel for showing line color and line
		JPanel colorsAndLinesPane = new JPanel();

		JPanel colorLabelPane = new JPanel(new GridLayout(3, 1));
		JPanel colorPane = new JPanel(new GridLayout(3, 1));
		JPanel lineLabelPane = new JPanel(new GridLayout(3, 1));
		JPanel linePane = new JPanel(new GridLayout(2, 1));

		colorLabelPane.add(fillColorLabel);
		colorLabelPane.add(outlineColorLabel);

		//construct component and add them to dialog
		m_btBgColor = new JButton(" ");
		m_btBgColor.setFont(Arangam.englishFont);

		colorPane.add(m_btBgColor);
		m_btOutColor = new JButton(" ");
		colorPane.add(m_btOutColor);

		lineLabelPane.add(outlineWidthLabel);
		lineLabelPane.add(outlineStyleLabel);

		m_comboLineWidth = new JComboBox();
		m_comboLineWidth.addItem("1");
		m_comboLineWidth.addItem("2");
		m_comboLineWidth.addItem("3");
		m_comboLineWidth.addItem("4");
		m_comboLineWidth.addItem("5");
		m_comboLineWidth.addItem("6");
		m_comboLineWidth.addItem("7");
		m_comboLineWidth.addItem("8");
		m_comboLineWidth.addItem("9");
		m_comboLineWidth.addItem("10");
		linePane.add(m_comboLineWidth);

		m_comboLineStyle = new JComboBox();
		m_comboLineStyle.addItem("1");
		m_comboLineStyle.addItem("2");
		m_comboLineStyle.addItem("3");
		m_comboLineStyle.addItem("4");
		m_comboLineStyle.addItem("5");
		m_comboLineStyle.addItem("6");
		m_comboLineStyle.addItem("7");
		m_comboLineStyle.addItem("8");
		linePane.add(m_comboLineStyle);

		colorsAndLinesPane.add(colorLabelPane);
		colorsAndLinesPane.add(colorPane);
		colorsAndLinesPane.add(lineLabelPane);
		colorsAndLinesPane.add(linePane);

		textboxPane.add(colorsAndLinesPane);

		btOk=new JButton();
		btCancel=new JButton();
		JPanel buttonPane=new JPanel();
		buttonPane.add(btOk);
		buttonPane.add(btCancel);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(textboxPane, BorderLayout.CENTER );
		getContentPane().add(buttonPane, BorderLayout.SOUTH );

		btOk.addActionListener(this);
		btCancel.addActionListener(this);

		m_btBgColor.addActionListener(this);
		m_btOutColor.addActionListener(this);
		m_comboLineWidth.addActionListener(this);
		m_comboLineStyle.addActionListener(this);
		addKeyListener(new EscKeyListener((Component)this,true));

		//set locale
		setLocale();

		//make it not resizable
		setResizable(false);

		//set location
		setLocation(Utils.getMiddle(getSize()));
	}

	/**
	 * Connects the class to the <code>ConnectActions</code> interface.
	 * @param actionsConnection interface to access <code>ActionsImpl</code> methods
	 */
	public void getInterface(ConnectActions actionsConnection)
	{
		this.actionsConnection = actionsConnection;
	}

	/**
	 * Sets the attributes for ShapeFormatDialog.
	 * @param the attributes of the current component.
	 */
	public void setAttributes()
	{
		//get attributes from shape and set them to dialog
		m_btBgColor.setBackground(aShape.getBackground());
		m_btOutColor.setBackground(aShape.label.outlineColor);
		m_comboLineWidth.setSelectedIndex(aShape.getLineWidth()-1);
		m_comboLineStyle.setSelectedIndex(aShape.getLineStyle());
	}

	/**
	 * Sets the color attributes for ShapeFormatDialog.
	 * @param the color attributes of the current component.
	 */
	public void setColorAttributes()
	{
		aShape.label.bgColor = m_btBgColor.getBackground();
		aShape.textPane.setBackground(aShape.label.bgColor);
		aShape.label.outlineColor = m_btOutColor.getBackground();
	}

	/**
	 * Sets the locale for components depends on the current Locale.
	 */
	public void setLocale()
	{
		wordBundle = arangam.getWordBundle();

		btOk.setText(wordBundle.getString("ok"));
		btCancel.setText(wordBundle.getString("cancel"));

		outlineColorLabel.setText(wordBundle.getString("outlineColor"));
		fillColorLabel.setText(wordBundle.getString("fillColor"));
		outlineWidthLabel.setText(wordBundle.getString("outlineWidth"));
		outlineStyleLabel.setText(wordBundle.getString("outlineStyle"));
		pack();
		setSize(getPreferredSize());
	}

	public void actionPerformed(ActionEvent ae)
	{
		//apply the attributes to shape
		if(ae.getSource()==btOk)
		{
			setColorAttributes();
			int width = m_comboLineWidth.getSelectedIndex();
			aShape.setLineWidth(width+1);

			int style = m_comboLineStyle.getSelectedIndex();
			aShape.setLineStyle(style);

			dispose();
		}
		//dispose the dialog
		else if(ae.getSource()==btCancel)
		{
			dispose();
		}
		//show color chooser dialog to choose background color
		else if(ae.getSource()==m_btBgColor)
		{
			Color newBgColor = JColorChooser.showDialog(
				ShapeFormatDialog.this,"Choose Color",m_btBgColor.getBackground());
			if(newBgColor!=null)
			{
				m_btBgColor.setBackground(newBgColor);
			}
		}
		//show color chooser dialog to choose outline color
		else if(ae.getSource()==m_btOutColor)
		{
			Color newOutColor = JColorChooser.showDialog(
			ShapeFormatDialog.this,"Choose Color",m_btOutColor.getBackground());
			if(newOutColor!=null)
			{
				m_btOutColor.setBackground(newOutColor);
			}
		}
	}
}

import java.awt.*;
import java.util.ResourceBundle;

import javax.swing.*;

/**
 * Custom dialog window that let the user to change Slide background texture.
 *
 * The background texture can be a solid color or a shading from one color
 * to another (horizontal, vertical, diagonal up, diagonal down).
 */
public class BackgroundDialog extends JDialog
{
	/*** Main class reference */
	private Arangam arangam;

	/*** parent Slide reference */
	private Slide parentSlide;

	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** Label showing "fill effect" */
	javax.swing.JLabel JFillEffectLabel = new javax.swing.JLabel();

	/*** Combo box showing "fill effects" */
	javax.swing.JComboBox JFillEffectComboBox = new javax.swing.JComboBox();

	/*** Label showing "from color" */
	javax.swing.JLabel JFromColorLabel = new javax.swing.JLabel();

	/*** Button showing the "from color" */
	javax.swing.JButton fromColorButton = new javax.swing.JButton();

	/*** Label showing "to color" */
	javax.swing.JLabel JToColorLabel = new javax.swing.JLabel();

	/*** Button showing the "to color" */
	javax.swing.JButton toColorButton = new javax.swing.JButton();

	/*** Button for accepting all attributes and set it to the current Slide */
	javax.swing.JButton JOKButton = new javax.swing.JButton();

	/*** Button for cancel */
	javax.swing.JButton JCancelButton = new javax.swing.JButton();

	/*** Button for accepting all attributes and set it to all slides */
	javax.swing.JButton applyToAllButton = new javax.swing.JButton();

	/**
	 * Creates a custom dialog with specified Frame as its owner.
	 * @param parent the <code>Arangam</code> Frame from which the dialog is run
	 * @param aSlide the </code>Slide</code> for which the dialog is displayed
	 */
	public BackgroundDialog(JFrame parent, Slide aSlide)
	{
		super(parent, true);
		this.parentSlide = aSlide;
		this.arangam = (Arangam)parent;

		//set border layout
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		//set bounds
		setSize(370, 140);
		setLocation(Utils.getMiddle(getSize()));
		setResizable(false);

		JPanel gradPane = new JPanel();
		JPanel colorPane = new JPanel();
		JPanel buttonPane = new JPanel();

		//fill effect
		gradPane.add(JFillEffectLabel);
		JFillEffectComboBox.addItem("Solid");
		JFillEffectComboBox.addItem("Gradient Horizontal");
		JFillEffectComboBox.addItem("Gradient Vertical");
		JFillEffectComboBox.addItem("Gradient Diagonal Up");
		JFillEffectComboBox.addItem("Gradient Diagonal Down");
		JFillEffectComboBox.setSelectedIndex(parentSlide.fillEffect);
		gradPane.add(JFillEffectComboBox);

		//from color
		colorPane.add(JFromColorLabel);
		fromColorButton = new JButton();
		colorPane.add(fromColorButton);
		fromColorButton.setBackground(parentSlide.fromColor);

		//to color
		colorPane.add(JToColorLabel);
		toColorButton = new JButton();
		colorPane.add(toColorButton);
		toColorButton.setBackground(parentSlide.toColor);

		//ok and cancel button
		buttonPane.add(JOKButton);
		buttonPane.add(JCancelButton);
		buttonPane.add(applyToAllButton);
		cp.add(gradPane,BorderLayout.NORTH);
		cp.add(colorPane,BorderLayout.CENTER);
		cp.add(buttonPane,BorderLayout.SOUTH);

		//register listeners
		SymAction lSymAction = new SymAction();
		JOKButton.addActionListener(lSymAction);
		JCancelButton.addActionListener(lSymAction);
		applyToAllButton.addActionListener(lSymAction);
		fromColorButton.addActionListener(lSymAction);
		toColorButton.addActionListener(lSymAction);
		addKeyListener(new EscKeyListener((Component)this,true));

		//set current locale
		setLocale();
	}

	/**
	 * Sets the Background for all Slides.
	 * @param effect one of four gradient effects
	 * @param fromColor From color of Gradient paint
	 * @param toColor To color of Gradient paint
	 */
	public void setAllSlideBackground(int effect,Color fromColor,Color toColor)
	{
		//remember current Slide
		int c = arangam.slideshow1.getCurrentSlideNum();
		//show last Slide
		arangam.slideshow1.showSlide(arangam.slideshow1.getSlidesCount());

		//apply the background to all slides
		Slide tmpSlide = new Slide(arangam);
		int i = -1;
		do
		{
			if(i == -1)
				tmpSlide = (Slide)arangam.slideshow1.getComponent(0);
			else
				tmpSlide = arangam.slideshow1.getSlide(i);
			tmpSlide.setFillEffect(effect);
			tmpSlide.setFromColor(fromColor);
			tmpSlide.setToColor(toColor);
			i++;
		}while (i < (arangam.slideshow1.getSlidesCount()-1));
		//show the originally shown Slide
		arangam.slideshow1.showSlide(c);
	}

	/**
	 * Sets the locale for components depends on the current <code>Locale</code>.
	 */
	public void setLocale()
	{
		//get words
		wordBundle = Arangam.wordBundle;

		//set text for the components from it
		setTitle(wordBundle.getString("backgroundE"));
		JFillEffectLabel.setText(wordBundle.getString("fillEffect"));
		JFromColorLabel.setText(wordBundle.getString("fromColor"));
		JToColorLabel.setText(wordBundle.getString("toColor"));
		JOKButton.setText(wordBundle.getString("ok"));
		JCancelButton.setText(wordBundle.getString("cancel"));
		applyToAllButton.setText(wordBundle.getString("applyToAll"));

		//set values in FillEffect ComboBox
		int i = JFillEffectComboBox.getSelectedIndex();
		JFillEffectComboBox.removeAllItems();
		JFillEffectComboBox.addItem(wordBundle.getString("solid"));
		JFillEffectComboBox.addItem(wordBundle.getString("gradientHorizontal"));
		JFillEffectComboBox.addItem(wordBundle.getString("gradientVertical"));
		JFillEffectComboBox.addItem(wordBundle.getString("gradientDiagonalUp"));
		JFillEffectComboBox.addItem(wordBundle.getString("gradientDiagonalDown"));
		if(i != -1)
			JFillEffectComboBox.setSelectedIndex(i);
		else
			JFillEffectComboBox.setSelectedIndex(0);

		//pack and resize
		pack();
		setSize(getPreferredSize());
	}

	/**
	 * Listen to clicks on buttons and update values if needed
	 */
	class SymAction implements java.awt.event.ActionListener
	{
		/**
		 * Invoked when an action occurs. When a button is clicked.
		 */
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();

			//dispose
			if(object == JCancelButton)
				dispose();

			//show color chooser to choose "from color"
			else if(object == fromColorButton)
			{
				Color fromColor = JColorChooser.showDialog(
				BackgroundDialog.this,"Choose Color",
				fromColorButton.getBackground());
				if(fromColor!=null)
				{
					fromColorButton.setBackground(fromColor);
				}
			}

			//show color chooser to choose "to color"
			else if(object == toColorButton)
			{
				Color toColor = JColorChooser.showDialog(
				BackgroundDialog.this,"Choose Color",
				toColorButton.getBackground());
				if(toColor!=null)
				{
					toColorButton.setBackground(toColor);
				}
			}

			//set background to the Slide
			else if(object == JOKButton)
			{
				parentSlide.setFillEffect(JFillEffectComboBox.getSelectedIndex());
				parentSlide.setFromColor(fromColorButton.getBackground());
				parentSlide.setToColor(toColorButton.getBackground());
				arangam.isBGApplyToAll = false;
				dispose();
			}

			//set background to all slides
			else if(object == applyToAllButton)
			{
				arangam.isBGApplyToAll = true;
				setAllSlideBackground(JFillEffectComboBox.getSelectedIndex(),
					fromColorButton.getBackground(),
					toColorButton.getBackground());
				dispose();
			}
		}
	}

}

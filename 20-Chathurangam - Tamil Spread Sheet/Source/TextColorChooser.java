/**
* @(#)TextPreviewLabel.java 1.02 09/12/2002
*
* Copyright (c) 2000-2003 RCILTS-Tamil,SCSE,
* Anna University,Chennai.
* All Rights Reserved.
*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**Displays the text preview label in the
color dialog box. It is used for setting the color
to the selected text from th table(sheet). it is
extended from the JLabel for text display.*/
class TextPreviewLabel extends JLabel
{
  /**To display the sample text color in the color dialog box itself/*/
  private String sampleText="<html><body font face=TABKural color=black>ñ£î¤ó¤ â¿î¢¶ õí¢íñ¢ </body></html>";
  /**Checks for fore color and back color of the text.*/
  boolean isForgroundSelection;

  /**Constructor to invoke the text preview label
  in the color chooser dialog box. It displays the
  sample text for the color change.*/
  public TextPreviewLabel()
  {
    this(Color.black, Color.white, true);
  }

  /**Constructor with forecolor,back color and checks fore or back color
  as parameters*/
  public TextPreviewLabel(Color fore, Color back, boolean isForgroundSelection)
  {
    setOpaque(true);
    setForeground(fore);
    setBackground(back);
    this.isForgroundSelection = isForgroundSelection;
    setText(sampleText);
  }

  /**Sets the fore color for the selected data from the sheet.
  @param col the color object.*/
  public void setForeground(Color col)
  {
    if (isForgroundSelection)
    {
      super.setForeground(col);
    }
    else
    {
      super.setBackground(col);
    }
  }

}

/** To display the color chooser dialog box for setting
the color for selected text from the table cell. It sets both
back ground and fore color for the text.It is extended from
JDialog component.
 * @version 1.0 11/22/98
 */
class ColorChooserDialog extends JDialog
{
	/**Color object to set for initial color*/
    private Color initialColor;
    /**Color object to set for retrived color*/
    private Color retColor;
    /**Color chooser object used to display the color palatte.*/
    private JColorChooser chooserPane;
	/**Gets the component for which the color to be set
	@param c the component object
	@param title the string object used for title.
	@param chooserpane the JColorChooser object.*/
    public ColorChooserDialog(Component c, String title,
                          final    JColorChooser chooserPane)
                          {
        super(JOptionPane.getFrameForComponent(c), title, true);
        setResizable(false);

        this.chooserPane = chooserPane;
		/**sets the font for the ok button in Color chooser*/
		String okString = UIManager.getString("ColorChooser.okText"	);
		/**sets the font for the cancel button in Color chooser*/
		String cancelString = UIManager.getString("ColorChooser.cancelText");
		/**sets the font for the reset button in Color chooser*/
		String resetString = UIManager.getString("ColorChooser.resetText");
		/**Container object used to place the component in the window*/
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(chooserPane, BorderLayout.CENTER);
		/**Panel to set buttons*/
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        /**Button used for ok the selected color*/
        JButton okButton = new JButton(okString);
		getRootPane().setDefaultButton(okButton);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                retColor = chooserPane.getColor();
                setVisible(false);
            }
        });
        buttonPane.add(okButton);
		/**Button used for cancel the selected color*/
        JButton cancelButton = new JButton(cancelString);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                retColor = null;
                setVisible(false);
            }
        });
        buttonPane.add(cancelButton);
		/**Button used for reset the selected color*/
        JButton resetButton = new JButton(resetString);
        resetButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e)
           {
               chooserPane.setColor(initialColor);
           }
        });
        buttonPane.add(resetButton);
        contentPane.add(buttonPane, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(c);
        addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            setVisible(false);
          }
        });
    }

 /**Gets the color for retriving the selected color
 @return color object .*/
  public Color getColor()
  {
    return retColor;
  }

}

/**Displays the color chooser through this class
This class is extended from JColorChooser.*/
public class TextColorChooser extends JColorChooser
{
  /**Constructor to invoke the color chooser
  @param target the color object to be set
  @param reference the color object
  @param isForgroundSelection of type boolean
  to check the fore and back color.*/
  public TextColorChooser(Color target, Color reference, boolean isForgroundSelection)
  {
    super(target);
    if (isForgroundSelection)
    {
      setPreviewPanel(new TextPreviewLabel(target, reference,isForgroundSelection));
    } else
    {
      setPreviewPanel(new TextPreviewLabel(reference,target, isForgroundSelection));
    }
    updateUI();
  }

  /**Displays the dialog box with all colors.
  @param component the dialog box component
  @param title the string object in the dialog.*/
  public Color showDialog(Component component, String title)
  {
    ColorChooserDialog dialog = new ColorChooserDialog(component, title, this);
    dialog.show();
    Color col = dialog.getColor();
    dialog.dispose();
    return col;
  }

}


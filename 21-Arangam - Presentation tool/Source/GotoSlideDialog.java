import java.awt.*;
import java.util.ResourceBundle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import javax.swing.*;
import javax.swing.text.*;

/**
 * Custom dialog window that let the user to go to other slides
 */
public class GotoSlideDialog extends JDialog
{
	/*** Main class reference */
	private Arangam arangam;

	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

    /*** Text field for entering Slide number to go to */
    private javax.swing.JTextField gotoSlideTextField = new javax.swing.JTextField(5);

    /*** Button to goto the particular Slide */
    private javax.swing.JButton goButton = new javax.swing.JButton();

	/*** Button to close the dialog */
	private javax.swing.JButton closeButton = new javax.swing.JButton();

	/*** Label indicating the dialog message */
	private javax.swing.JLabel messageLabel = new javax.swing.JLabel();

	/**
	 * Creates a non-modal custom dialog with specified Frame as its owner.
	 * @param arangam the Arangam Frame from which the dialog is run
	 */
	public GotoSlideDialog(Arangam arangam)
	{
		super(arangam,true);
		this.arangam = arangam;

		//create panels
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();

		//topPanel
		topPanel.add(messageLabel);
		topPanel.add(gotoSlideTextField);
		gotoSlideTextField.requestFocus();

		//bottomPanel
		bottomPanel.add(goButton);
		bottomPanel.add(closeButton);

		//set border layout
		getContentPane().setLayout( new BorderLayout() );

		getContentPane().add( topPanel, BorderLayout.CENTER );
		getContentPane().add( bottomPanel, BorderLayout.SOUTH);

		//dispose on close
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent event)
			{
				Object object = event.getSource();
				if(object == GotoSlideDialog.this)
					dispose();
			}
		});

	   //register listeners
		SymAction lSymAction = new SymAction();
		goButton.addActionListener(lSymAction);
		closeButton.addActionListener(lSymAction);

		addKeyListener(new EscKeyListener((Component)this,true));

		//set current locale
		setLocale();
		setResizable(false);

		//set display location
		setLocation(Utils.getMiddle(getSize()));

	}//GotoSlideDialog

	/**
	 * Sets the locale for components depends on the current Locale.
	 */
	public void setLocale()
	{
		wordBundle = Arangam.wordBundle;
		setTitle(wordBundle.getString("gotoSlideE"));
		goButton.setText(wordBundle.getString("go"));
		closeButton.setText(wordBundle.getString("close"));
		messageLabel.setText(wordBundle.getString("gotoSlideMsg"));
		pack();
		setSize(getPreferredSize());
	}

	/**
	 * Listen to clicks on buttons and update values if needed
	 */
  	private class SymAction implements java.awt.event.ActionListener
  	{
	    Color fromColor = null;
		/**
		 * Invoked when an action occurs - when a button is clicked.
		 */
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			//dispose on close
			if (object == closeButton)
			{
				dispose();
			}
			//@see ActionsImpl#GotoSlide
			else if(object == goButton)
			{
			 	arangam.actionsImpl1.GotoSlide(gotoSlideTextField.getText());
			}
		}
	}
}

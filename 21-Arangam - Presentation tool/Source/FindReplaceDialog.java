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
 * Custom dialog window that let the user to find/replace text.
 */
public class FindReplaceDialog extends JDialog
{
	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/*** ActionsImpl class reference */
	private transient ConnectActions actionsConnection;

	/*** Main class reference */
	private Arangam arangam;

	/*** Arraylist to store all text components */
	private TextArray arangamTextArray;

	/*** Default selection */
	private boolean caseSensitive = false,
			wholeWord = false,
			downDir = true;

	/*** Refer current text component  */
	private int currentTextCompIndex = -2;

	/*** Number of text component in the file */
	private int textCompCount = 0;

    /*** Text field to enter search text */
    javax.swing.JTextField searchTextField = new javax.swing.JTextField(12);

    /*** Text field to enter "replace text" */
    javax.swing.JTextField replaceTextField = new javax.swing.JTextField(12);

	/*** Button to "find" action */
	javax.swing.JButton findButton = new javax.swing.JButton();

	/*** Button to "replace" action */
	javax.swing.JButton replaceButton = new javax.swing.JButton();

	/*** Button for canceling "find/replace" action */
	javax.swing.JButton closeButton = new javax.swing.JButton();

	/*** Search for whole words? */
	javax.swing.JCheckBox wholeWordChkbox = new javax.swing.JCheckBox();

	/*** Match case only for English */
	javax.swing.JCheckBox caseChkbox = new javax.swing.JCheckBox();

	/*** Groups "direction" radio buttons */
	javax.swing.ButtonGroup dir = new javax.swing.ButtonGroup();

	/*** Search upwards? */
	javax.swing.JRadioButton dirUp = new javax.swing.JRadioButton();

	/*** Search downwards? */
	javax.swing.JRadioButton dirDown = new javax.swing.JRadioButton();

    /*** Label indicating "Find What:" */
    javax.swing.JLabel findWhatLabel = new javax.swing.JLabel();

    /*** Label indicating "Replace With:" */
    javax.swing.JLabel replaceWithLabel = new javax.swing.JLabel();

    /*** Label indicating "Search:" (up/down) */
    javax.swing.JLabel searchLabel = new javax.swing.JLabel();

	/**
	 * Creates a custom dialog with specified Frame as its owner.
	 * @param arangam the Arangam Frame from which the dialog is run
	 */
	public FindReplaceDialog(Arangam arangam)
	{
		super(arangam,false);
		this.arangam = arangam;
		setTitle("Find/Replace");

		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();

		//construct panels
		JPanel replacePanel = new JPanel();
		JPanel findPanel = new JPanel();
		JPanel wordCasePanel = new JPanel();
		JPanel directionPanel = new JPanel();

		//set layout for them
		leftPanel.setLayout(new BoxLayout( leftPanel, BoxLayout.Y_AXIS ) );
		rightPanel.setLayout(new BoxLayout( rightPanel, BoxLayout.Y_AXIS ) );

		//findPanel
		findPanel.add(findWhatLabel);
		findPanel.add(searchTextField);
		searchTextField.requestFocus();
		searchTextField.setSelectionColor(Color.black);
		searchTextField.setSelectedTextColor(Color.white);

		//replacePanel
		replacePanel.add(replaceWithLabel);
		replacePanel.add(replaceTextField);
		replaceTextField.setSelectionColor(Color.black);
		replaceTextField.setSelectedTextColor(Color.white);

		//wordCasePanel
		wordCasePanel.add(wholeWordChkbox);
		wholeWordChkbox.setSelected(wholeWord);
		wordCasePanel.add(caseChkbox);
		caseChkbox.setSelected(wholeWord);
		directionPanel.add(searchLabel);

		//directionPanel
		dirDown.setSelected(downDir);
		dirUp.setSelected(!downDir);
		dir.add(dirUp);
		dir.add(dirDown);
		directionPanel.add(dirUp);
		directionPanel.add(dirDown);

		//rightPanel
		rightPanel.add(findButton);
		rightPanel.add(replaceButton);
		rightPanel.add(closeButton);

		//leftPanel
		leftPanel.add(findPanel);
		leftPanel.add(replacePanel);
		leftPanel.add(wordCasePanel);
		leftPanel.add(directionPanel);

		//set layout
		getContentPane().setLayout( new BorderLayout() );
		getContentPane().add( leftPanel, BorderLayout.WEST );
		getContentPane().add( rightPanel, BorderLayout.EAST );

		//register window listeners to make invisible on close
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent event)
			{
				Object object = event.getSource();
				if(object == FindReplaceDialog.this)
					setVisible(false);
			}
		});

		//to repaint
		searchTextField.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				FindReplaceDialog.this.repaint();
			}
		});

		//to select all text in search text field when focused
		searchTextField.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent fe)
			{
				searchTextField.selectAll();
			}
		});

		//to select all text in replace text field when focused
		replaceTextField.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent fe)
			{
				replaceTextField.selectAll();
			}
		});

		//to listen to "Escape" and "Enter" key
		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if(ke.getKeyCode() == 27)//Escape
				{
					setVisible(false);
				}
				if(ke.getKeyCode() == 10 && findButton.isEnabled())//Enter
				{
					find();
				}
			}
		});

		//register action listeners
		SymAction lSymAction = new SymAction();
		findButton.addActionListener(lSymAction);
		replaceButton.addActionListener(lSymAction);
		closeButton.addActionListener(lSymAction);
		caseChkbox.addActionListener(lSymAction);
		wholeWordChkbox.addActionListener(lSymAction);
		dirUp.addActionListener(lSymAction);
		dirDown.addActionListener(lSymAction);

		//set current locale
		setLocale();

		setResizable(false);
		//set display location
		setLocation(Utils.getMiddle(getSize()));
		setVisible(false);

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
	 * Paints the dialog. Used to activate Find Button.
	 * @param graphics the <code>Graphics</code> context in which to paint.
	 */
	public void paint(Graphics g)
	{
		super.paint(g);

		//enable the find button if there is anything to search
		if(searchTextField.getText() == null || searchTextField.getText().equals(""))
		{
			findButton.setEnabled(false);
		}
		else
		{
			findButton.setEnabled(true);
		}
	}

	/**
	 * Replace the found text with the given text.
	 * @param replaceText replacement text.
	 */
	private void replace(String replaceText)
	{
		//replace the current one and call find
		if(currentTextCompIndex >= 0 && currentTextCompIndex < textCompCount)
		{
			if(arangamTextArray.getArangamText(currentTextCompIndex).getSelectedText() != null)
			{
				arangamTextArray.getArangamText(currentTextCompIndex).replaceSelection(replaceText);
			}
		}
		find();
	}

	/**
	 * Find the occurrence of the given text in text components
	 */
	public void find()
	{
		//initialise
		textCompCount = 0;
		Slide tmpSlide = arangam.slideshow1.getCurrentSlide();
		//initialise text component array
		setArray(tmpSlide);

		//if there is any text component in current Slide
		if(textCompCount > 0)
		{
			//if nothing is focused, focus first/last component depends
			//on search direction
			if(tmpSlide.focused == null)
			{
				currentTextCompIndex=downDir?0:textCompCount-1;
				int caretPosition = downDir?0:arangamTextArray.getArangamText(currentTextCompIndex).getText().length();

				arangam.slideshow1.getCurrentSlide().focusComponent(
					arangamTextArray.getArangamText(currentTextCompIndex),caretPosition);
			}

			//determine the focused component
			for(int i=0; i < textCompCount; i++)
			{
				if(arangamTextArray.getArangamText(i)==(AText)(tmpSlide.focused))
				{
					currentTextCompIndex=i;
					break;
				}
			}

			//find the text in the current text component
			if(currentTextCompIndex<textCompCount && currentTextCompIndex>=0)
			{
				currentTextCompIndex=findInTextComp(currentTextCompIndex);
			}
		}
		//else navigate to next or previous Slide depend on search direction
		else
		{
			boolean over = false;
			//search downwards
			if(downDir)
			{
				over = !actionsConnection.NextSlideView();
				//if no next Slide
				if(over)
				{
					//finished searching
					showFinishedDialog();
				}
				//else find again
				else
				{
					arangam.slideshow1.getCurrentSlide().focusComponent(null);
					find();
				}
			}
			//search upwards
			else
			{
				over = !actionsConnection.PrevSlideView();
				//if no previous Slide
				if(over)
				{
					//finished searching
					showFinishedDialog();
				}
				//else find again
				else
				{
					arangam.slideshow1.getCurrentSlide().focusComponent(null);
					find();
				}
			}
		}

	}

	/**
	 * Find the given text in text components.
	 * @param textCompIndex the index of text component.
	 */
	private int findInTextComp(int textCompIndex)
	{
		//start with zero-th position
		int caretPosition = 0;

		//initialise
		textCompCount = 0;
		//update text component array
		Slide tmpSlide = arangam.slideshow1.getCurrentSlide();
		setArray(tmpSlide);

		boolean finished = arangamTextArray.getArangamText(textCompIndex).find(
					searchTextField.getText(),
					caseSensitive,downDir,wholeWord);

		//not found, navigate to next/previous text component depend
		//on search direction
		if(finished)
		{
			if(downDir)	{			textCompIndex = textCompIndex+1;			}
			else		{			textCompIndex = textCompIndex-1;			}

			//call recursively till finished
			if(textCompIndex >= 0 && textCompIndex<textCompCount)
			{
				if(!downDir)
					caretPosition = arangamTextArray.getArangamText(textCompIndex).getText().length();
				arangam.slideshow1.getCurrentSlide().focusComponent(arangamTextArray.getArangamText(textCompIndex),caretPosition);
				findInTextComp(textCompIndex);
			}
			else
				arangam.slideshow1.getCurrentSlide().focusComponent(null);
		}

		//navigate to next or previous Slide depend
		//on search direction
		if(textCompIndex < 0 || textCompIndex >= textCompCount)
		{
			boolean over = false;
		    if(downDir)
		    	over = arangam.slideshow1.getCurrentSlideNum() ==
		         		arangam.slideshow1.getSlidesCount();
		    else
		    	over = arangam.slideshow1.getCurrentSlideNum() == 1;

			//no next/previous Slide
			if(over)
		    {
				showFinishedDialog();
			}
			else
			{
				if(downDir)
				{
					actionsConnection.NextSlideView();
					arangam.slideshow1.getCurrentSlide().focusComponent(null);
					find();
					return currentTextCompIndex;
				}
				else if(!downDir)
				{
					actionsConnection.PrevSlideView();
					arangam.slideshow1.getCurrentSlide().focusComponent(null);
					find();
					return currentTextCompIndex;
				}

			}
		}
		return textCompIndex;
	}

	/**
	 * Initialise the text component array - "arangamTextArray"
	 * with the text components in a Slide
	 * @param Slide the Slide from which text components are taken
	 */
	private void setArray(Slide Slide)
	{
		//initialise text array
		arangamTextArray = new TextArray();

		//references for components
		ConnectComponent component;
		ConnectComponent focusedComp = Slide.focused;
		Slide.focusComponent(null);

		//get all text components in the Slide to an array
		//and calculate text component count in that Slide
		for(int i=0;i<Slide.getComponentCount();i++)
		{
			component=(ConnectComponent)Slide.getComponent(i);
			if(component.getClass().getName().equals("AText"))
			{
				arangamTextArray.add((AText)component);
				textCompCount++;
			}
		}
		Slide.focusComponent(focusedComp);
	}

	/**
	 * Sets the locale for components depends on the current Locale.
	 */
	public void setLocale()
	{
		wordBundle = arangam.getWordBundle();
		setTitle(wordBundle.getString("findReplaceE"));
		findButton.setText(wordBundle.getString("find"));
		replaceButton.setText(wordBundle.getString("replace"));
		closeButton.setText(wordBundle.getString("close"));
		wholeWordChkbox.setText(wordBundle.getString("wholeWord"));
		caseChkbox.setText(wordBundle.getString("case"));

		dirUp.setText(wordBundle.getString("up"));
		dirDown.setText(wordBundle.getString("down"));

		findWhatLabel.setText(wordBundle.getString("findWhatLabel"));
		replaceWithLabel.setText(wordBundle.getString("replaceWithLabel"));
		searchLabel.setText(wordBundle.getString("find"));

		pack();
		setSize(getPreferredSize());
	}

	/**
	 * Display a dialog indicating search is finished.
	 * @see Utils#showDialog
	 */
	private void showFinishedDialog()
	{
		wordBundle = arangam.getWordBundle();
		Object[] options = {wordBundle.getString("ok")};

		Utils.showDialog(this,"finishedSearchInfo",JOptionPane.OK_OPTION,
			JOptionPane.INFORMATION_MESSAGE, Arangam.infoIcon, options, 0);
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
			//make the window invisible
			if (object == closeButton)
			{
			  	setVisible(false);
		    }

			//find
			else if(object == findButton)
			{
				find();
			}

			//replace
			else if(object == replaceButton)
			{
				replaceTextField.requestFocus();
				replace(replaceTextField.getText());
			}

			//update the boolean variable caseSensitive
			else if(object == caseChkbox)
			{
				caseSensitive = caseChkbox.isSelected();
			}

			//update the boolean variable wholeWord
			else if(object == wholeWordChkbox)
			{
				wholeWord = wholeWordChkbox.isSelected();
			}

			//toggles down direction

			else if(object == dirUp)
			{
				downDir = !(dirUp.isSelected());
			}
			else if(object == dirDown)
			{
				downDir = dirDown.isSelected();
			}
		}
	}

}

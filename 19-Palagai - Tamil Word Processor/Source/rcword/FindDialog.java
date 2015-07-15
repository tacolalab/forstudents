package rcword;

//import DL.*;
//import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;

import java.util.*;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.event.*;

import javax.swing.undo.*;
import javax.swing.table.*;
import javax.swing.border.*;

import java.awt.print.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.text.html.*;

/**
 *	Used to get the user options and find the given text in the document.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class FindDialog extends JDialog
{
	  /**
	   * a reference to the main <code> Word </code> object
		*/
	  protected Word m_owner;
	  /** tabbed pane used to separate find pane and replace pane	*/
	  protected JTabbedPane m_tb;
	    /** textfiled to get the text to be searched in find pane	*/
	  protected JTextField m_txtFind1;
	    /** textfiled to get the text to be searched in replace pane	*/
	  protected JTextField m_txtFind2;
	    /**	document where the find operation has to be done.*/
	  protected Document m_docFind;
	    /**	document where the replace operation has to be done.*/
	  protected Document m_docReplace;
	    /**	for whole word*/
	  protected ButtonModel m_modelWord;
	    /**	for case sensitive*/
	  protected ButtonModel m_modelCase;
	    /**	for upward direction*/
	  protected ButtonModel m_modelUp;
	    /**	for downward direction*/
	  protected ButtonModel m_modelDown;
	    /**	position where the search has to be started.*/
	  protected int m_searchIndex = -1;
	    /**	indicates whether the search is upwards or downwards.*/
	  protected boolean m_searchUp = false;
	    /**	whole text where the find or replace operation has to be done.*/
	  protected String  m_searchData;
	    /**	label to show the "find what " text in the find pane*/
	  protected JLabel jl_findwhat1;
	    /**	label to show the "find what " text in the replace pane.*/
	  protected JLabel jl_findwhat2;
	    /**	label to show the "replace with"in the replace pane.*/
	 protected JLabel jl_replaceWith ;
	    /**	to check the whole word option in the find pane*/
	  JCheckBox chkWord1;
	    /**	to check the whole word option in the replace pane.*/
	  JCheckBox chkWord2;
	    /**	to check the case sensitive option in the find pane*/
	  JCheckBox chkCase1 ;
	    /**	to check the case sensitive in the replace pane.*/
	  JCheckBox chkCase2;
	    /**	to select the upward direction option in the find pane*/
	  JRadioButton rdUp1;
	    /**	to select the upward direction option in the replace pane.*/
	  JRadioButton rdUp2;
	    /**	to select the downward direction option in the find pane*/
	  JRadioButton rdDown1;
	    /**	to select the downward direction option in the replace pane.*/
	  JRadioButton rdDown2;
	    /**	button to give the find command in the find pane*/
	  JButton btFind1;
	    /**	button to give the find command in the replace pane.*/
	  JButton btFind2;
	    /**	button to give the close command in the find pane*/
	  JButton btClose1;
	    /**	button to give the close command in the replace pane.*/
	  JButton btClose2;
	    /**	button to give the replace command in the replace pane.*/
	  JButton btReplace;
	    /**	button to give the replace allcommand in the replace pane.*/
	  JButton btReplaceAll ;
	    /**	used to show the title for the options in the find pane*/
	  TitledBorder tb_optionsTitledBorder1;
	    /**	used to show the title for the options in the replace pane.*/
	  TitledBorder tb_optionsTitledBorder2;
	    /**	panel which contains the command buttons. */
	  JPanel jp_command;
	    /**	point where the search has to be started.*/
	  int point_tostart = -1;
	    /**	used to count the number of times the text replacement action happened. */
	  int counter = 0;

      /**
	* Constructs the find or replace dialog with the following parameters.
	*
	* @ param owner 	a reference to the main <code> Word </code> object
	* @ param index 	to specify find or replace pane in the find dialog.
	* 			<p>	1 for find pane and 2 for replace pane. </p>
	*/

	  public FindDialog(Word owner, int index)
	  {
			super(owner, "Find and Replace", false);
			m_owner = owner;

			m_tb = new JTabbedPane();
			m_tb.setFont(m_owner.defaultfont);

			// "Find" panel
			JPanel p1 = new JPanel(new BorderLayout());

			JPanel pc1 = new JPanel(new BorderLayout());

			JPanel pf = new JPanel();
			pf.setLayout(new DialogLayout(20, 5));
			pf.setBorder(new EmptyBorder(8, 5, 8, 0));

			jl_findwhat1 = new JLabel("Find what:");
			jl_findwhat1.setFont(m_owner.defaultfont);
			pf.add(jl_findwhat1);

			m_txtFind1 = new JTextField();//"3333");
			m_txtFind1.setFont(m_owner.defaultfont);
			m_docFind = m_txtFind1.getDocument();
			pf.add(m_txtFind1);
			pc1.add(pf, BorderLayout.CENTER);

			JPanel po = new JPanel(new GridLayout(2, 2, 8, 2));

			//eb_optionsEtchedBorder = new EtchedBorder();
			tb_optionsTitledBorder1 = new TitledBorder( new EtchedBorder(),"OPtions");

			po.setBorder(tb_optionsTitledBorder1);//new TitledBorder(new EtchedBorder(),"Options"));

			chkWord1 = new JCheckBox("Whole words only");
			chkWord1.setFont(m_owner.defaultfont);
			chkWord1.setMnemonic('w');
			m_modelWord = chkWord1.getModel();
			po.add(chkWord1);

			ButtonGroup bg = new ButtonGroup();
			rdUp1 = new JRadioButton("Search up");
			rdUp1.setFont(m_owner.defaultfont);
			rdUp1.setMnemonic('u');
			m_modelUp = rdUp1.getModel();
			bg.add(rdUp1);
			po.add(rdUp1);

			chkCase1 = new JCheckBox("Match case");
			chkCase1.setFont(m_owner.defaultfont);
			chkCase1.setMnemonic('c');
			m_modelCase = chkCase1.getModel();
			po.add(chkCase1);

			rdDown1 = new JRadioButton("Search down", true);
			rdDown1.setFont(m_owner.defaultfont);
			rdDown1.setMnemonic('d');
			m_modelDown = rdDown1.getModel();
			bg.add(rdDown1);
			po.add(rdDown1);
			pc1.add(po, BorderLayout.SOUTH);

			p1.add(pc1, BorderLayout.CENTER);

			JPanel p01 = new JPanel(new FlowLayout());
			jp_command = new JPanel(new GridLayout(2, 1, 2, 8));

			ActionListener findAction = new ActionListener()
			{
				  public void actionPerformed(ActionEvent e)
				  {
					  //	System.out.println("         Befor :"+m_searchIndex);
						/* calls the find method with the following indications.
						 don't do replacement, show messages, consider the 							  direction
						*/
					  	findNext(false, true, true);
						//System.out.println("         After :"+m_searchIndex);
				  }
			};
			btFind1 = new JButton("Find Next");
			btFind1.setFont(m_owner.defaultfont);
			btFind1.addActionListener(findAction);
			btFind1.setMnemonic('f');
			jp_command.add(btFind1);

			ActionListener closeAction = new ActionListener()
			{
				  public void actionPerformed(ActionEvent e)
				  {
						setVisible(false);
				  }
			};
			btClose1 = new JButton("Close");
			btClose1.setFont(m_owner.defaultfont);
			btClose1.addActionListener(closeAction);
			btClose1.setDefaultCapable(true);
			jp_command.add(btClose1);
			p01.add(jp_command);
			p1.add(p01, BorderLayout.EAST);

			m_tb.addTab("Find", p1);

			// "Replace" panel
			JPanel p2 = new JPanel(new BorderLayout());

			JPanel pc2 = new JPanel(new BorderLayout());

			JPanel pc = new JPanel();
			pc.setLayout(new DialogLayout(20, 5));
			pc.setBorder(new EmptyBorder(8, 5, 8, 0));

			jl_findwhat2 = new JLabel();
			pc.add(jl_findwhat2);
			m_txtFind2 = new JTextField();
			m_txtFind2.setFont(m_owner.defaultfont);
			m_txtFind2.setDocument(m_docFind);
			//m_txtFind2.setText("11");
			pc.add(m_txtFind2);

			jl_replaceWith = new JLabel("ReplaceWith");
			jl_replaceWith.setFont(m_owner.defaultfont);
			pc.add(jl_replaceWith);

			JTextField txtReplace = new JTextField();
			txtReplace.setFont(m_owner.defaultfont);
			m_docReplace = txtReplace.getDocument();
			pc.add(txtReplace);
			pc2.add(pc, BorderLayout.CENTER);

			po = new JPanel(new GridLayout(2, 2, 8, 2));

			tb_optionsTitledBorder2 = new TitledBorder( new EtchedBorder(),"OPtions");

			po.setBorder(tb_optionsTitledBorder2);

			//po.setBorder(new TitledBorder(new EtchedBorder(),"Options"));

			chkWord2 = new JCheckBox("Whole words only");
			chkWord2.setFont(m_owner.defaultfont);
			chkWord2.setMnemonic('w');
			chkWord2.setModel(m_modelWord);
			po.add(chkWord2);

			bg = new ButtonGroup();
			rdUp2 = new JRadioButton("Search up");
			rdUp2.setFont(m_owner.defaultfont);
			rdUp2.setMnemonic('u');
			rdUp2.setModel(m_modelUp);
			bg.add(rdUp2);
			po.add(rdUp2);

			chkCase2 = new JCheckBox("Match case");
			chkCase2.setFont(m_owner.defaultfont);
			chkCase2.setMnemonic('c');
			chkCase2.setModel(m_modelCase);
			po.add(chkCase2);

			rdDown2 = new JRadioButton("Search down", true);
			rdDown2.setFont(m_owner.defaultfont);
			rdDown2.setMnemonic('d');
			rdDown2.setModel(m_modelDown);
			bg.add(rdDown2);
			po.add(rdDown2);
			pc2.add(po, BorderLayout.SOUTH);

			p2.add(pc2, BorderLayout.CENTER);

			JPanel p02 = new JPanel(new FlowLayout());
			jp_command = new JPanel(new GridLayout(4, 1, 2, 8));

			ActionListener find2Action = new ActionListener()
				{
					  public void actionPerformed(ActionEvent e)
					  {	// sets the cursor's current position as starting position to find.
						point_tostart = m_owner.workspace.getCaretPosition();
						/* calls the find method with the following indications.
						 don't do replacement, show messages, consider the 							  direction
						*/
						findNext(false, true, true);
					  }
				};


			btFind2 = new JButton("Find Next");
			btFind2.setFont(m_owner.defaultfont);
			btFind2.addActionListener(find2Action);
			btFind2.setMnemonic('f');
			jp_command.add(btFind2);

			ActionListener replaceAction = new ActionListener()
				{
				  	public void actionPerformed(ActionEvent e)
				  	{
						if(point_tostart!=-1)
							m_owner.workspace.setCaretPosition(point_tostart);
						/* calls the find method with the following indications.
						 do the replacement, show messages, consider the 							  direction
						*/
						findNext(true, true,true);
						point_tostart = m_owner.workspace.getCaretPosition();
						/* calls the find method with the following indications.
						  don't do replacement, show messages, consider the 							  direction
						*/
						findNext(false,true,true);
						/*int pos = m_owner.workspace.getCaretPosition();
						findNext(false,true);
						m_owner.workspace.setCaretPosition(pos);
						*/
				  	}
				};
			btReplace = new JButton("Replace");
			btReplace.setFont(m_owner.defaultfont);
			btReplace.addActionListener(replaceAction);
			btReplace.setMnemonic('r');
			jp_command.add(btReplace);

			ActionListener replaceAllAction = new ActionListener()
				{
					  public void actionPerformed(ActionEvent e)
					  {
						    m_owner.workspace.setCaretPosition(0);
							counter = 0;
							while (true)
							{
								/* calls the find method with the following indications.
								 do the replacement,  don't show messages, don't consider the direction
								*/
								  int result = findNext(true, false,false);
								  if (result < 0)    // error
									return;
								  else if (result == 0)    // no more
									break;
								  counter++;
							}
							//JOptionPane.showMessageDialog(m_owner,
							//  counter+" replacement(s) have been done", "Info",
							//  JOptionPane.INFORMATION_MESSAGE);
							warning(counter+" RHBD");
					  }
				};
			btReplaceAll = new JButton("Replace All");
			btReplaceAll.setFont(m_owner.defaultfont);
			btReplaceAll.addActionListener(replaceAllAction);
			btReplaceAll.setMnemonic('a');
			jp_command.add(btReplaceAll);

			btClose2 = new JButton("Close");
			btClose2.setFont(m_owner.defaultfont);
			btClose2.addActionListener(closeAction);
			btClose2.setDefaultCapable(true);
			jp_command.add(btClose2);
			p02.add(jp_command);
			p2.add(p02, BorderLayout.EAST);

			// Make button columns the same size
			p01.setPreferredSize(p02.getPreferredSize());

			m_tb.addTab("Replace", p2);

			m_tb.setSelectedIndex(index);

			getContentPane().add(m_tb, BorderLayout.CENTER);

			WindowListener flst = new WindowAdapter()
				{
				  public void windowActivated(WindowEvent e)
				  {
						m_searchIndex = -1;
						if (m_tb.getSelectedIndex()==0)
						  m_txtFind1.grabFocus();
						else
						  m_txtFind2.grabFocus();
				  }

				  public void windowDeactivated(WindowEvent e)
				  {
						m_searchData = null;
				  }
				};
			addWindowListener(flst);

			addKeyListener(new KeyAdapter()
				{
					public void keyPressed(KeyEvent ke)
					{
						if(ke.getKeyCode() == 27)//Esc
						{
							setVisible(false);
						}
					}
				});

             setLocale();
			 pack();
			 setLocation(Utils.getMiddle(getSize()));
			 setResizable(false);
	  }

	  /**
	* Used to specify the find dialog to find or replace action with the parameter.
	*
	* @ param index 	integer to specify find or replace pane
	*			1 for find and 2 for replace.
	*/
	  public void setSelectedIndex(int index)
	  {
			m_tb.setSelectedIndex(index);
			setVisible(true);
			m_searchIndex = -1;	// sets the starting index as -1 to search
	  }


	/**
	* Finds the given word in the document and replaces according to the parameters.
	*
	* @ param	doReplace 	states whether replacement also has to be done or not.
	* @ param	showWarnings	states whether any messages like warning has to be shown to the user.
	* @param	no_direction	states whether the direction of search has to be considered or not.
	*/
	 public int findNext(boolean doReplace, boolean showWarnings,boolean no_direction)
	  {
		  try
		  {
			JTextPane monitor = m_owner.workspace;
			int pos = monitor.getCaretPosition();
			//System.out.println(" caret Pos in find :"+pos);

			if(no_direction)	//checks the direction has to considered or not
				m_searchUp= false;
			if (m_modelUp.isSelected() != m_searchUp)
			{
			  m_searchUp = m_modelUp.isSelected();
			  m_searchIndex = -1;
			}

			if (m_searchIndex == -1)
			{	// if searches starts from the middle of the document.
				  try
				  {		// get the document according to the direction of flow.
						Document doc = monitor.getDocument();
						if (m_searchUp)
						  m_searchData = doc.getText(0, pos);
						else
						  m_searchData = doc.getText(pos, doc.getLength()-pos);
						m_searchIndex = pos;
				  }
				  catch (BadLocationException ex)
				  {
					  System.out.println(ex+"--->bad location ex :finddialog");
						//warning(ex.toString());
						return -1;
				  }
			}

			String key = "";
			try { key = m_docFind.getText(0, m_docFind.getLength()); }
			catch (BadLocationException ex) {}
			if (key.length()==0)
			{	// show the warning "Please enter the target to search".
				warning("PETTS");
				return -1;
			}
			if (!m_modelCase.isSelected())
			{	// check the case sensitive
			 	 m_searchData = m_searchData.toLowerCase();
			 	 key = key.toLowerCase();
			}
			if (m_modelWord.isSelected())
			{
				//System.out.println(" comes here");
				  for (int k=0; k<Utils.WORD_SEPARATORS.length; k++)
				  {
						if (key.indexOf(Utils.WORD_SEPARATORS[k]) >= 0)
						{
						 	/*warning("The text target contains an illegal "+
								"character \'"+Utils.WORD_SEPARATORS[k]+"\'");
								*/
							// show the warning "The text target contains an illegal character".
							warning("TTTCIC");
						 	return -1;	// return -1 for find result finds nothing.
						}
				  }
				 //System.out.println(" finishes here");
			}

			String replacement = "";
			if (doReplace)
			{	// get the replace text
				  try
				  {
						replacement = m_docReplace.getText(0,
						  m_docReplace.getLength());
				  }
				  catch (BadLocationException ex)
				  {
					  System.out.println(ex+"----> badlocation exception; FindDialog");
				  }
			}

			int xStart = -1;
			int xFinish = -1;
			while (true)
			{
				  if (m_searchUp)	// check the direction
					xStart = m_searchData.lastIndexOf(key, pos-1);
				  else
					xStart = m_searchData.indexOf(key, pos-m_searchIndex);
				  if (xStart < 0)
				  {
						if (showWarnings)
						  warning("TextNotFound");
						return 0;
				  }

				  xFinish = xStart+key.length();

				  if (m_modelWord.isSelected())
				  {
					    //System.out.println(" comes here also");
				 		//Thread.sleep(1000);
						boolean s1 = xStart>0;
						boolean b1 = s1 && !Utils.isSeparator(m_searchData.charAt(
						  xStart-1));
						boolean s2 = xFinish<m_searchData.length();
						boolean b2 = s2 && !Utils.isSeparator(m_searchData.charAt(
						  xFinish));

						if (b1 || b2)    // Not a whole word
						{
							  if (m_searchUp && s1)    // Can continue up
							  {
								pos = xStart;
								continue;
							  }
							  if (!m_searchUp && s2)    // Can continue down
							  {
								pos = xFinish;
								continue;
							  }
							  // Found, but not a whole word, and we cannot continue
							  if (showWarnings)
								warning("TextNotFound");
							  return 0;
						}
				  }
				  break;
			}

			if (!m_searchUp)
			{	// for downwards direction.
			 	 xStart += m_searchIndex;
			 	 xFinish += m_searchIndex;
			}
			if (doReplace)
			{	// to do the replace
			 	 setSelection(xStart, xFinish, m_searchUp);
			 	 monitor.replaceSelection(replacement);
			 	 setSelection(xStart, xStart+replacement.length(),m_searchUp);
			 	 m_searchIndex = -1;
			}
			else
			 	 setSelection(xStart, xFinish, m_searchUp);
		  }
		  catch(Exception e)
		  {
				System.out.println(e+"----> e at finddialog");
				//e.printStackTrace();
		  }
			return 1;
	  }

	/**
	* Used to show the warning messages.
	*
	* @ param	message 	message to be shown to the user
	*/
	  protected void warning(String message)
	  {
		  //  Contains the word bundle for the current local.
		  ResourceBundle wordbundle = m_owner.wordBundle;
		  Object[] options = {wordbundle.getString("Ok")};
	   	  Utils.showDialog(m_owner,message,JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null , options, 0);
	  }

	/**
	*  Used to set the selection in the required portion in the document.
	*
	* @ param	xStart	selection starting position
	*  @ param	xFinish	selection end position
	*  @ param	moveUp	upwards direction if true, otherwise downwards.
	*/
	public void setSelection(int xStart, int xFinish, boolean moveUp)
	{
		  //System.out.println(" start :"+xStart);
		  //System.out.println("   end :"+xFinish);
		  //System.out.println(" Move--:"+moveUp);
		  if (moveUp)
		  {
				m_owner.workspace.setCaretPosition(xFinish);
				m_owner.workspace.moveCaretPosition(xStart);
		  }
		  else
				m_owner.workspace.select(xStart, xFinish);
		  m_owner.caretstart = m_owner.workspace.getSelectionStart();
		  m_owner.caretend = m_owner.workspace.getSelectionEnd();
	}

	/**
	 * Sets the current locale to the user interface.
	 */
	public void setLocale()
	{
		//	Contains the word bundle for the current local.
		ResourceBundle wordbundle = m_owner.wordBundle;

		//System.out.println("Current lang:"+m_owner.current_language+"  "+wordbundle.getString("Replace"));
		tb_optionsTitledBorder1.setTitle(wordbundle.getString("Options"));
		tb_optionsTitledBorder2.setTitle(wordbundle.getString("Options"));
		jl_findwhat1.setText(wordbundle.getString("FindWhatLabel")+":");
		jl_findwhat2.setText(wordbundle.getString("FindWhatLabel")+":");
		jl_replaceWith.setText(wordbundle.getString("ReplaceWithLabel")+":");

		chkWord1.setText(wordbundle.getString("WholeWord"));
		chkWord2.setText(wordbundle.getString("WholeWord"));
		chkCase1.setText(wordbundle.getString("Case"));
		chkCase2.setText(wordbundle.getString("Case"));
		rdUp1.setText(wordbundle.getString("Up"));
		rdUp2.setText(wordbundle.getString("Up"));
		rdDown1.setText(wordbundle.getString("Down"));
		rdDown2.setText(wordbundle.getString("Down"));
		btFind1.setText(wordbundle.getString("FindNext"));
		btFind2.setText(wordbundle.getString("FindNext"));
		btClose1.setText(wordbundle.getString("Cancel"));
		btClose2.setText(wordbundle.getString("Cancel"));
		btReplace.setText(wordbundle.getString("Replace"));
		btReplaceAll.setText(wordbundle.getString("ReplaceAll"));

		m_tb.setTitleAt(0,wordbundle.getString("Find"));
		m_tb.setTitleAt(1,wordbundle.getString("Replace"));
		pack();
	}
}


/**
 *	Used to set the layout for the find dialog.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class DialogLayout
	implements LayoutManager
{
	/**	*/
	protected int m_divider = -1;
	/** horizontal gap*/
	protected int m_hGap = 10;
	/** vertical gap*/
	protected int m_vGap = 5;

	/**
	* Constructs an object of <code> DialogLayout </code>
	*/
	public DialogLayout() {}

	/**
	* Constructs an object of <code> DialogLayout </code> with given horizontal and vertical gap
	*
	* @ param 	hGap 	horizontal gap
	* @ param	vGap	vertical gap.
	*/
	public DialogLayout(int hGap, int vGap)
	{
		m_hGap = hGap;
		m_vGap = vGap;
	}

	/**
	* Adds the given component with the given text
	*
	* @ param name	text to be displayed on the component
	* @param	comp	component to be added.
	*/
	public void addLayoutComponent(String name, Component comp) {}

	/**
	* Removes the sepecified component form the layout.
	*
	* @ param comp component to be removed.
	*/
	public void removeLayoutComponent(Component comp) {}

	/**
	* Used to get the preferred layout size of a component.
	*
	* @ param parent container for which the layout size has to be found.
	* @return	Dimension	dimension of the component with the preferred layout size.
	*/
	public Dimension preferredLayoutSize(Container parent)
	{
		int divider = getDivider(parent);

		int w = 0;
		int h = 0;
		for (int k=1 ; k<parent.getComponentCount(); k+=2)
		{
			Component comp = parent.getComponent(k);
			Dimension d = comp.getPreferredSize();
			w = Math.max(w, d.width);
			h += d.height + m_vGap;
		}
		h -= m_vGap;

		Insets insets = parent.getInsets();
		return new Dimension(divider+w+insets.left+insets.right,
			h+insets.top+insets.bottom);
	}

	/**
	* Used to get the minimum layout size of a container.
	*
	* @ param	parent	container for which the minimum layout size has to be found.
	* @return	Dimension	minimum dimension size of the container
	*/
	public Dimension minimumLayoutSize(Container parent)
	{
		return preferredLayoutSize(parent);
	}

	/**
	* Used to fix the layout of  a container
	*
	* @ param 	parent	container for which the layout has to be fixed.
	*/
	public void layoutContainer(Container parent)
	{
		int divider = getDivider(parent);

		Insets insets = parent.getInsets();
		int w = parent.getWidth()-insets.left-insets.right-divider;
		int x = insets.left;
		int y = insets.top;

		for (int k=1 ; k<parent.getComponentCount(); k+=2)
		{
			Component comp1 = parent.getComponent(k-1);
			Component comp2 = parent.getComponent(k);
			Dimension d = comp2.getPreferredSize();

			comp1.setBounds(x, y, divider, d.height);
			comp2.setBounds(x+divider, y, w, d.height);
			y += d.height + m_vGap;
		}
	}

	/**
	* Returns the horizontal gap.
	* @return int the horizontal gap.
	*/
	public int getHGap()
	{
		return m_hGap;
	}

	/**
	* Returns the vertical gap.
	* @return	int	the vertical gap.
	*/
	public int getVGap()
	{
		return m_vGap;
	}

	/**
	*  Sets the divider for the layout.
	* @param divider integer to set the divider.
	*/
	public void setDivider(int divider)
	{
		if (divider > 0)
			m_divider = divider;
	}

	/**
	* Returns the divider of the layout.
	* @ return 	int 	integer value of the divider.
	*/
	public int getDivider()
	{
		return m_divider;
	}

	/**
	* Returns the divider of a container.
	* @ param	parent	container of which the divider has to be passed
	* @ return	int	integer value of the divider.
	*/
	protected int getDivider(Container parent)
	{
		if (m_divider > 0)
			return m_divider;

		int divider = 0;
		for (int k=0 ; k<parent.getComponentCount(); k+=2)
		{
			Component comp = parent.getComponent(k);
			Dimension d = comp.getPreferredSize();
			divider = Math.max(divider, d.width);
		}
		divider += m_hGap;
		return divider;
	}

	/**
	* Returns the class name with the horizontal and vertical gaps and the divider.
	* @ return String class name with the horizontal and vertical gaps and the divider.
	*/
	public String toString()
	{
		return getClass().getName() + "[hgap=" + m_hGap + ",vgap="
			+ m_vGap + ",divider=" + m_divider + "]";
	}
}


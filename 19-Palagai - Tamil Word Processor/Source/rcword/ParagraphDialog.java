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

/**
 *	Used to get the paragraph attributes and fix it at the selected portion or at the
 *	cursor's position.
 *	@ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class ParagraphDialog extends JDialog
{
	  /** a reference to the main <code> Word </code> object */
	 Word parentword;
	/** integer number for closed option*/
	  protected int m_option = JOptionPane.CLOSED_OPTION;
	/** attributes set to fix the document*/
	  protected MutableAttributeSet m_attributes;
	/** used to get the line spacing attribute*/
	  protected JTextField m_lineSpacing;
	/** used to get the space above attribute*/
	  protected JTextField m_spaceAbove;
	/** used to get the space bellow attribute*/
	  protected JTextField m_spaceBelow;
	/** used to get the first indent attribute.*/
	  protected JTextField m_firstIndent;
	/** used to get the left indent*/
	  protected JTextField m_leftIndent;
	/** used to get the right indent*/
	  protected JTextField m_rightIndent;
	/** button for the left align*/
	  protected SmallToggleButton m_btLeft;
	/** button for the center align*/
	  protected SmallToggleButton m_btCenter;
	/** button for the right align*/
	  protected SmallToggleButton m_btRight;
	/** button of align justify*/
	  protected SmallToggleButton m_btJustified;
	/** used to show the preview*/
	  protected ParagraphPreview m_preview;
	  /**	 Appropriate font for the current local for entire GUI.  */
	  static Font defaultfont ;
	/** used to display the space border*/
	  TitledBorder spaceBorder = new TitledBorder(new EtchedBorder(),"Þ¬ìªõ÷¤");
	/** used to display the indent border*/
	  TitledBorder indentBorder =new  TitledBorder(new EtchedBorder(),"õ¤÷¤ñ¢¹ ªõì¢´");
	/** used to display the preview border*/
	  TitledBorder previewBorder =new  TitledBorder(new EtchedBorder(),"ºù¢ ð£ó¢¬õ");
	/** label to show "line space"*/
	  JLabel jl_linespace = new JLabel("õó¤");
	/** label to show "space above"*/
      JLabel jl_abovespace = new JLabel("«ñô¢");
	/** label to show "space bellow"*/
      JLabel jl_belowspace = new JLabel("è¦ö¢");
	/** label to show "first indent"*/
      JLabel jl_firstIndent = new JLabel("ºîô¢ õó¤");
	/** label to show "left indent"*/
      JLabel jl_leftIndent = new JLabel("Þì¶");
	/** label to show "right indent"*/
      JLabel jl_rightIndent = new JLabel("õô¶");
	/** label to show "alignment"*/
      JLabel jl_alignment = new JLabel(" å¿é¢è¬ñ¾");
	/** button to give the cancel command*/
      JButton btCancel;
	/** button ot give the ok command.*/
      JButton btOK;

	/**
	*	Constructs an object of <code> ParagrapDialog </code> with a reference
	* 	to the main <code> Word </code> object
	*
	* @param word	a reference to the main <code> Word </code> object
	*/
	  public ParagraphDialog(Word word)
	  {
			super(word, "Paragraph", true);
			parentword = word;
			defaultfont = parentword.defaultfont;

			spaceBorder.setTitleFont(defaultfont);
			indentBorder.setTitleFont(defaultfont);
			previewBorder.setTitleFont(defaultfont);
			jl_linespace.setFont(defaultfont);
			jl_abovespace.setFont(defaultfont);
			jl_belowspace.setFont(defaultfont);
			jl_firstIndent.setFont(defaultfont);
			jl_leftIndent.setFont(defaultfont);
			jl_rightIndent.setFont(defaultfont);
			jl_alignment.setFont(defaultfont);

			jl_linespace.setHorizontalAlignment(SwingConstants.RIGHT);
			jl_abovespace.setHorizontalAlignment(SwingConstants.RIGHT);
			jl_belowspace.setHorizontalAlignment(SwingConstants.RIGHT);
			jl_firstIndent.setHorizontalAlignment(SwingConstants.RIGHT);
			jl_leftIndent.setHorizontalAlignment(SwingConstants.RIGHT);
			jl_rightIndent.setHorizontalAlignment(SwingConstants.RIGHT);


			//spaceBorder.setForeground(Color.black);
			//indentBorder.setForeground(Color.black);
			//previewBorder.setForeground(Color.black);
			jl_linespace.setForeground(Color.black);
			jl_abovespace.setForeground(Color.black);
			jl_belowspace.setForeground(Color.black);
			jl_firstIndent.setForeground(Color.black);
			jl_leftIndent.setForeground(Color.black);
			jl_rightIndent.setForeground(Color.black);
			jl_alignment.setForeground(Color.black);

			getContentPane().setLayout(new BoxLayout(getContentPane(),
			  BoxLayout.Y_AXIS));

			JPanel p = new JPanel(new GridLayout(1, 2, 5, 2));

			JPanel ps = new JPanel(new GridLayout(3, 2, 10, 2));

			ps.setBorder(spaceBorder);

			ps.add(jl_linespace);//new JLabel("õó¤ Line spacing:"));
			m_lineSpacing = new JTextField();
			ps.add(m_lineSpacing);
			ps.add(jl_abovespace);//new JLabel("Space above:"));
			m_spaceAbove = new JTextField();
			ps.add(m_spaceAbove);
			ps.add(jl_belowspace);//new JLabel("Space below:"));
			m_spaceBelow = new JTextField();
			ps.add(m_spaceBelow);
			p.add(ps);

			JPanel pi = new JPanel(new GridLayout(3, 2, 10, 2));
			pi.setBorder(indentBorder);//new TitledBorder(new EtchedBorder(), "Indent"));
			pi.add(jl_firstIndent);//new JLabel("First indent:"));
			m_firstIndent = new JTextField();
			pi.add(m_firstIndent);
			pi.add(jl_leftIndent);//new JLabel("Left indent:"));
			m_leftIndent = new JTextField();
			pi.add(m_leftIndent);
			pi.add(jl_rightIndent);//new JLabel("Right indent:"));
			m_rightIndent = new JTextField();
			pi.add(m_rightIndent);
			p.add(pi);
			getContentPane().add(p);

			getContentPane().add(Box.createVerticalStrut(5));
			p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
			p.add(Box.createHorizontalStrut(10));
			p.add(jl_alignment);//new JLabel("Alignment:"));
			p.add(Box.createHorizontalStrut(20));

			ButtonGroup bg = new ButtonGroup();
			ImageIcon img = new ImageIcon("rcword/Images/alignleft.gif");
			m_btLeft = new SmallToggleButton(false, img, img, "Left");
			bg.add(m_btLeft);
			p.add(m_btLeft);

			img = new ImageIcon("rcword/Images/aligncenter.gif");
			m_btCenter = new SmallToggleButton(false, img, img, "Center");
			bg.add(m_btCenter);
			p.add(m_btCenter);
			img = new ImageIcon("rcword/Images/alignright.gif");
			m_btRight = new SmallToggleButton(false, img, img, "Right");
			bg.add(m_btRight);
			p.add(m_btRight);
			img = new ImageIcon("rcword/Images/alignjustify.gif");
			m_btJustified = new SmallToggleButton(false, img, img,
			  "Justify");
			bg.add(m_btJustified);
			//p.add(m_btJustified);
			getContentPane().add(p);

			p = new JPanel(new BorderLayout());
			p.setBorder(previewBorder);//new TitledBorder(new EtchedBorder(), "Preview"));
			m_preview = new ParagraphPreview();
			p.add(m_preview, BorderLayout.CENTER);
			getContentPane().add(p);

			p = new JPanel(new FlowLayout());
			JPanel p1 = new JPanel(new GridLayout(1, 2, 10, 2));
			btOK = new JButton("êó¤");
			btOK.setForeground(Color.black);
			btOK.setFont(defaultfont);
			ActionListener lst = new ActionListener()
				{
				   public void actionPerformed(ActionEvent e)
				   {
						m_option = JOptionPane.OK_OPTION;
						setVisible(false);
					}
				};
			btOK.addActionListener(lst);
			p1.add(btOK);

			btCancel = new JButton("ï¦è¢°");
			  btCancel.setFont(defaultfont);
			  btCancel.setForeground(Color.black);

			lst = new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						m_option = JOptionPane.CANCEL_OPTION;
						setVisible(false);
					}
				};
			btCancel.addActionListener(lst);
			p1.add(btCancel);
			p.add(p1);
			getContentPane().add(p);

			// focus listener to update the preview.
			FocusListener flst = new FocusListener()
				{
					  public void focusGained(FocusEvent e) {}
					  public void focusLost(FocusEvent e) { updatePreview(); }
				};
			m_lineSpacing.addFocusListener(flst);
			m_spaceAbove.addFocusListener(flst);
			m_spaceBelow.addFocusListener(flst);
			m_firstIndent.addFocusListener(flst);
			m_leftIndent.addFocusListener(flst);
			m_rightIndent.addFocusListener(flst);

			lst = new ActionListener()
				{
				  public void actionPerformed(ActionEvent e)
				  {
						 updatePreview();
					}
				};
			m_btLeft.addActionListener(lst);
			m_btCenter.addActionListener(lst);
			m_btRight.addActionListener(lst);
			m_btJustified.addActionListener(lst);
			setLocale();
			pack();
			setResizable(false);
			setLocation(Utils.getMiddle(getSize()));
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


	  }

	/**
	* Used to set the paragraph attributes of the selected portion or the cursor's
	* position in the paragraph dialog.
	* @param a attribute set to be fixed in the paragraph dialog
	*/
	  public void setAttributes(AttributeSet a)
	  {
			m_attributes = new SimpleAttributeSet(a);
			m_lineSpacing.setText(Float.toString(
			  StyleConstants.getLineSpacing(a)));
			m_spaceAbove.setText(Float.toString(
			  StyleConstants.getSpaceAbove(a)));
			m_spaceBelow.setText(Float.toString(
			  StyleConstants.getSpaceBelow(a)));
			m_firstIndent.setText(Float.toString(
			  StyleConstants.getFirstLineIndent(a)));
			m_leftIndent.setText(Float.toString(
			  StyleConstants.getLeftIndent(a)));
			m_rightIndent.setText(Float.toString(
			  StyleConstants.getRightIndent(a)));

			int alignment = StyleConstants.getAlignment(a);
			if (alignment == StyleConstants.ALIGN_LEFT)
			  m_btLeft.setSelected(true);
			else if (alignment == StyleConstants.ALIGN_CENTER)
			  m_btCenter.setSelected(true);
			else if (alignment == StyleConstants.ALIGN_RIGHT)
			  m_btRight.setSelected(true);
			else if (alignment == StyleConstants.ALIGN_JUSTIFIED)
			  m_btJustified.setSelected(true);

			updatePreview();
	  }

	/**
	* Used to get the attributes given in the paragraph dialog.
	* @return AttributeSet attribute set given in the paragraph dialog.
	*/
	  public AttributeSet getAttributes()
	  {
			if (m_attributes == null)
			  return null;
			float value;
			try
			{
			  value = Float.parseFloat(m_lineSpacing.getText());
			  StyleConstants.setLineSpacing(m_attributes, value);
			} catch (NumberFormatException ex) {}
			try
			{
			  value = Float.parseFloat(m_spaceAbove.getText());
			  StyleConstants.setSpaceAbove(m_attributes, value);
			} catch (NumberFormatException ex) {}
			try
			{
			  value = Float.parseFloat(m_spaceBelow.getText());
			  StyleConstants.setSpaceBelow(m_attributes, value);
			} catch (NumberFormatException ex) {}
			try
			{
			  value = Float.parseFloat(m_firstIndent.getText());
			  StyleConstants.setFirstLineIndent(m_attributes, value);
			} catch (NumberFormatException ex) {}
			try
			{
			  value = Float.parseFloat(m_leftIndent.getText());
			  StyleConstants.setLeftIndent(m_attributes, value);
			} catch (NumberFormatException ex) {}
			try
			{
			  value = Float.parseFloat(m_rightIndent.getText());
			  StyleConstants.setRightIndent(m_attributes, value);
			} catch (NumberFormatException ex) {}

			StyleConstants.setAlignment(m_attributes, getAlignment());

			return m_attributes;
	  }

	/**
	* Used to get the user option.
	*
	* @return int the option given by the user
	*/
	  public int getOption()
	  {
			return m_option;
	  }

	/**
	* 	Used to update the preview in the paragraph dialog.
	*/
	  protected void updatePreview()
	  {
			m_preview.repaint();
	  }

	/**
	* Used to get the alignment option given in the paragraph dialog.
	*/
	  protected int getAlignment()
	  {
			if (m_btLeft.isSelected())
			{
				//System.out.println("  align LEFT");
			  	return StyleConstants.ALIGN_LEFT;
		  	}
			if (m_btCenter.isSelected())
			{
				//System.out.println("  align CEMTER");
			  	return StyleConstants.ALIGN_CENTER;
		  	}
			else if (m_btRight.isSelected())
			{
				//System.out.println("  align RIGHT");
			  	return StyleConstants.ALIGN_RIGHT;
		  	}
			else if(m_btJustified.isSelected())
			{
				//System.out.println("  align JUSTIFIED");
			  	return StyleConstants.ALIGN_JUSTIFIED;
		  	}
			else
			{
				//System.out.println("  align ELSE ");
				return StyleConstants.ALIGN_LEFT;
			}
				// DEFAULT
	  }


/**
 *	Used to show the preview in the paragraph dialog.
 *
 *	@ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
	  class ParagraphPreview extends JPanel
	  {
		    /** Appropriate font for the current local for this preview.	*/
			protected Font m_fn = Word.defaultfont;
			/** dummy string to be displayed in the preview.*/
			protected String m_dummy ="ÜÝÞßèêîðñòøù"; // "abcdefghjklm";
			/** width of the preview text*/
			protected float  m_scaleX = 0.25f;
			/** length of the preview text.*/
			protected float  m_scaleY = 0.25f;
			/** used to get the sub string from the dummy string of random length. */
			protected Random m_random = new Random();

			/**
			*	constructs an object of <code> ParagraphPreview </code>.
			*/
			public ParagraphPreview()
			{
				  setFont(defaultfont);
				  setBackground(Color.white);
				  setForeground(Color.black);
				  setOpaque(true);
				  setBorder(new LineBorder(Color.black));
				  setPreferredSize(new Dimension(120, 56));
			}

			/**
			* Used to paint the preview component in the paragraph dialog.
			* @param g graphics with which the component has to be painted.
			*/
			public void paintComponent(Graphics g)
			{
				  super.paintComponent(g);
				  float lineSpacing = 0;
				  float spaceAbove  = 0;
				  float spaceBelow  = 0;
				  float firstIndent = 0;
				  float leftIndent  = 0;
				  float rightIndent = 0;

				  try
				  {
					lineSpacing = Float.parseFloat(m_lineSpacing.getText());
				  } catch (NumberFormatException ex) {}
				  try
				  {
						spaceAbove = Float.parseFloat(m_spaceAbove.getText());
				  } catch (NumberFormatException ex) {}
				  try
				  {
						spaceBelow = Float.parseFloat(m_spaceBelow.getText());
				  } catch (NumberFormatException ex) {}
				  try
				  {
						firstIndent = Float.parseFloat(m_firstIndent.getText());
				  } catch (NumberFormatException ex) {}
				  try
				  {
						leftIndent = Float.parseFloat(m_leftIndent.getText());
				  } catch (NumberFormatException ex) {}
				  try
				  {
						rightIndent = Float.parseFloat(m_rightIndent.getText());
				  } catch (NumberFormatException ex) {}

				  m_random.setSeed(1959);    // Use same seed every time

				  g.setFont(m_fn);
				  FontMetrics fm = g.getFontMetrics();
				  int h = fm.getAscent();
				  int s  = Math.max((int)(lineSpacing*m_scaleY), 1);
				  int s1 = Math.max((int)(spaceAbove*m_scaleY), 0) + s;
				  int s2 = Math.max((int)(spaceBelow*m_scaleY), 0) + s;
				  int y = 5+h;

				  int xMarg = 20;
				  int x0 = Math.max((int)(firstIndent*m_scaleX)+xMarg, 3);
				  int x1 = Math.max((int)(leftIndent*m_scaleX)+xMarg, 3);
				  int x2 = Math.max((int)(rightIndent*m_scaleX)+xMarg, 3);
				  int xm0 = getWidth()-xMarg;
				  int xm1 = getWidth()-x2;

				  int n = (int)((getHeight()-(2*h+s1+s2-s+10))/(h+s));
				  n = Math.max(n, 1);

				  g.setColor(Color.lightGray);
				  int x = xMarg;
				  drawLine(g, x, y, xm0, xm0, fm, StyleConstants.ALIGN_LEFT);
				  y += h+s1;

				  g.setColor(Color.gray);
				  int alignment = getAlignment();
				  for (int k=0; k<n; k++)
				  {
						x = (k==0 ? x0 : x1);
						int xLen = (k==n-1 ? xm1/2 : xm1);
						if (k==n-1 && alignment==StyleConstants.ALIGN_JUSTIFIED)
						  alignment = StyleConstants.ALIGN_LEFT;
						drawLine(g, x, y, xm1, xLen, fm, alignment);
						y += h+s;
				  }

				  y += s2-s;
				  x = xMarg;
				  g.setColor(Color.lightGray);
				  drawLine(g, x, y, xm0, xm0, fm, StyleConstants.ALIGN_LEFT);
			}


			protected void drawLine(Graphics g, int x, int y, int xMax,
			 int xLen, FontMetrics fm, int alignment)
			{
				  if (y > getHeight()-3)
					return;
				  StringBuffer s = new StringBuffer();
				  String str1;
				  int xx = x;
				  while (true)
				  {
						int m = m_random.nextInt(10)+1;
						str1 = m_dummy.substring(0, m)+" ";
						int len = fm.stringWidth(str1);
						if (xx+len >= xLen)
						  break;
						xx += len;
						s.append(str1);
				  }
				  String str = s.toString();

				  switch (alignment)
				  {
						case StyleConstants.ALIGN_LEFT:
						  		g.drawString(str, x, y);
						  		break;
						case StyleConstants.ALIGN_CENTER:
						  		xx = (xMax+x-fm.stringWidth(str))/2;
						  		g.drawString(str, xx, y);
						  		break;
						case StyleConstants.ALIGN_RIGHT:
						  		xx = xMax-fm.stringWidth(str);
						  		g.drawString(str, xx, y);
						  		break;
						case StyleConstants.ALIGN_JUSTIFIED:
						  		while (x+fm.stringWidth(str) < xMax)
									str += "a";
						  		g.drawString(str, x, y);
						  		break;
				  }// end of switch
			}// end of method
	  } //end of innerclass

	/**
	 * sets the current locale to the user interface.
	 */
	  public void setLocale()
	  {
		    // 	Contains the word bundle for the current local.
			ResourceBundle wordbundle = parentword.wordBundle;
			spaceBorder.setTitle(wordbundle.getString("Space"));
			indentBorder.setTitle(wordbundle.getString("Indent"));
			previewBorder.setTitle(wordbundle.getString("Preview"));
			jl_linespace.setText(wordbundle.getString("LineSpacing")+":");
			jl_abovespace.setText(wordbundle.getString("SpaceAbove")+":");
			jl_belowspace.setText(wordbundle.getString("SpaceBelow")+":");
			jl_firstIndent.setText(wordbundle.getString("FirstIndent")+":");
			jl_leftIndent.setText(wordbundle.getString("LeftIndent")+":");
			jl_rightIndent.setText(wordbundle.getString("RightIndent")+":");
			jl_alignment.setText(wordbundle.getString("Alignment"));
			btOK.setText(wordbundle.getString("Ok"));
			btCancel.setText(wordbundle.getString("Cancel"));
			pack();
  	}

} //end of paragraphdialog



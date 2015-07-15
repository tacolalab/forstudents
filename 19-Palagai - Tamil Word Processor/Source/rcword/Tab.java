package rcword;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;


import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 *	Used to get the tab options from the user and set the selected
 *  attributes to the selected portion of the document or the cursor's
 *	current position.
 *
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class Tab extends JDialog
{
	/** a reference to the main <code> Word </code> object */
	Word parentword;
	/** top panel of the Tab dialog contains the labels "Tab stop","Default tab stop","tab stop
	 * range" and the text field to get the tab offset.
	 */
	JPanel jp_top = new JPanel();
	/** center panel of the Tab dialog contains the offsets panel and the style panel*/
	JPanel jp_center = new JPanel();
	/** bottom panel of the Tab dialog contains the command buttons.*/
	JPanel jp_bottom = new JPanel();

	/** panel contains the tab offsets scrollpane.*/
	JPanel jp_offsets = new JPanel();
	/** panel contains the align panel and lead panel*/
	JPanel jp_style = new JPanel();
	/** panel contains the alignment radio buttons for tab stop.*/
	JPanel jp_align = new JPanel();
	/** panel contains the various leads for tab stop.*/
    JPanel jp_lead = new JPanel();

	/** panel contains the three command buttons for set,clear clearall.*/
    JPanel jp_3buttons = new JPanel();
    /** panel contains the two command buttons for ok and cancel.*/
    JPanel jp_2buttons = new JPanel();

	/** button to give the ok command.*/
	JButton jb_ok = new JButton("êó¤");
	/** button to give the cancel command.*/
	JButton jb_cancel = new JButton("ï¦è¢°");
	/** button to clear a tab stop offset form the list.*/
	JButton jb_clear = new JButton("Üö¤");
	/** button to clear all the tab stop offsets in the list.*/
	JButton jb_clearall = new JButton("Ü¬ùî¢¬î»ñ¢ Üö¤");
	/** button to add the given tab stop offset in the list of offsets*/
	JButton jb_set = new JButton("ªð£¼î¢¶");

	/** button for the left alignment of the tab stop.*/
	JRadioButton jrb_left = new JRadioButton("Þì¶");
	/** button for the right alignment of the tab stop.*/
	JRadioButton jrb_right = new JRadioButton("õô¶");
	/** button for the decimal alignment of the tab stop.*/
	JRadioButton jrb_decimal = new JRadioButton("¹÷¢¢÷¤");
	/** button for the center alignment of the tab stop.*/
	JRadioButton jrb_center = new JRadioButton("ï´");
	/** button for the bar alignment of the tab stop.*/
	JRadioButton jrb_bar = new JRadioButton("âô¢¬ô");

	/** button for tab stop with simple lead (none).*/
	JRadioButton jrb_none_lead = new JRadioButton("1.ªõø¢Á");
	/** button for tab stop with dotted lead*/
	JRadioButton jrb_dotted_lead = new JRadioButton("2.....");
	/** button for tab stop with dashed lead */
	JRadioButton jrb_dashed_lead= new JRadioButton("3----");
	/**button for tab stop with line lead */
	JRadioButton jrb_line_lead = new JRadioButton("4____");

	/** label to display the text "tab stop".*/
	JLabel jl_positions = new JLabel("ï¤Áî¢î ï¤¬ô :");
	/** label to display the text "default tab stop*/
	JLabel jl_default = new JLabel("ï¤Áî¢î ï¤¬ô âô¢¬ô:");
	/** label to display the tab stop range*/
    JLabel jl_offset_range = new JLabel("     0.01\" - 22\"");

	/** text filed to get the tab offset.*/
	JTextField jtf_offset = new JTextField(6);
	/** used to display the list of tab stop offsets.*/
	JList jlt_offsets;
	/** used to display the offset range of tab stop.*/
	JList jlt_offset_range;

	/** used to display the list of tab stop offsets.*/
	JScrollPane jsp_offset;
	/** used to display the title "alignment" for the alignment buttons.*/
 	TitledBorder tb_align = new TitledBorder("å¿é¢è¬ñ¾ :");
 	/** used to display the title "leader" for the leads of tabStop.*/
 	TitledBorder tb_lead = new TitledBorder("ºù¢ù¤ìñ¢ :");
	/** contains the array of tab stops.*/
	TabSet tabs;
    /** contains the set of tab stops. */
	ArrayList al_tablist = new ArrayList();

	String[] s_offset_range = {"0.01"};//,"0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9","1","2","22"};
	String[] datad = {"1","2","3","4","5","6"};


	/**
	* Creates an object of <code> Tab </code> with
	* a reference to the main <code> Word </code> object
	*
	* @ param word a reference to the main <code> Word </code> object
	*/
	public Tab(Word word)
	{
		super(word,"Tabs",true);
		parentword = word;

        jl_positions.setFont(parentword.defaultfont);
        jl_default.setFont(parentword.defaultfont);
		jb_cancel.setFont(parentword.defaultfont);
		jb_ok.setFont(parentword.defaultfont);
		jb_set.setFont(parentword.defaultfont);
		jb_clear.setFont(parentword.defaultfont);
		jb_clearall.setFont(parentword.defaultfont);
		jrb_left.setFont(parentword.defaultfont);
		jrb_right.setFont(parentword.defaultfont);
		jrb_center.setFont(parentword.defaultfont);
		jrb_decimal.setFont(parentword.defaultfont);
		jrb_bar.setFont(parentword.defaultfont);
		jrb_none_lead.setFont(parentword.defaultfont);
		tb_align.setTitleFont(parentword.defaultfont);
		tb_lead.setTitleFont(parentword.defaultfont);

		//jl_positions.setForeground(Color.black);

		jp_top.setLayout(new GridLayout(3,2));
		jp_top.add(new JLabel(""));
		jp_top.add(new JLabel(""));
		jp_top.add(jl_positions);
		jp_top.add(jl_default);
		jp_top.add(jtf_offset);

		jlt_offset_range = new JList(s_offset_range);
		jp_top.add(jl_offset_range);

        jp_center.setLayout(new BorderLayout());


        //showList();
        jlt_offsets = new JList(datad);

        jsp_offset = new JScrollPane(jlt_offsets,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsp_offset.setSize(100,200);
        jp_offsets.add(jsp_offset);

        jp_align.add(jrb_left);
        jp_align.add(jrb_decimal );
		jp_align.add(jrb_center );
		jp_align.add(jrb_bar );
		jp_align.add(jrb_right );
		jp_align.setBorder(tb_align);

		ButtonGroup bg_align = new ButtonGroup();

        bg_align.add(jrb_left);
		bg_align.add(jrb_decimal);
		bg_align.add(jrb_center);
		bg_align.add(jrb_bar);
		bg_align.add(jrb_right);

        jp_style.setLayout(new GridLayout(2,1));
        jp_style.add(jp_align);

        jp_lead.add(jrb_none_lead);
        jp_lead.add(jrb_dotted_lead);
        jp_lead.add(jrb_dashed_lead);
        jp_lead.add(jrb_line_lead);
        jp_lead.setBorder(tb_lead);

		ButtonGroup bg_lead = new ButtonGroup();
        bg_lead.add(jrb_none_lead);
        bg_lead.add(jrb_dotted_lead);
        bg_lead.add(jrb_dashed_lead);
        bg_lead.add(jrb_line_lead);

        jp_style.add(jp_lead);

        jp_center.add(jp_offsets, BorderLayout.WEST);
        jp_center.add(jp_style, BorderLayout.EAST);

		jp_3buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jp_3buttons.add(jb_set);
        jp_3buttons.add(jb_clear);
        jp_3buttons.add(jb_clearall);

        jp_bottom.setLayout(new GridLayout(3,1));
        jp_bottom.add(jp_3buttons);
        jp_bottom.add(new JLabel("  ______________________________________________________"));

        jp_2buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));

        ActionListener okAction = new ActionListener()
		        	{
						public void actionPerformed(ActionEvent ev)
						{
							//System.out.println(" ok");
							try
							{
								StyledDocument doc = parentword.workspace.getStyledDocument();
								SimpleAttributeSet a = new SimpleAttributeSet();
								int offset=0 ;
								try
								{
									offset= Integer.parseInt(jtf_offset.getText());
								}
								catch (NumberFormatException ex)
								{
									Utils.warning(parentword,"InvalidNumber");
									jtf_offset.setText("");
									return;
								}

								int align;
								int leads;
								if(jrb_left.isSelected())
								       align= TabStop.ALIGN_LEFT;
							    else if(jrb_right.isSelected())
							    	 align = TabStop.ALIGN_RIGHT;
							    else if(jrb_center.isSelected())
							    	 align = TabStop.ALIGN_CENTER;
							    else if(jrb_decimal.isSelected())
							    	 align = TabStop.ALIGN_DECIMAL;
							    else
							         align = TabStop.ALIGN_BAR;

							    if(jrb_none_lead.isSelected())
							    	 leads = TabStop.LEAD_NONE;
							    else if(jrb_dotted_lead.isSelected())
							    	 leads = TabStop.LEAD_DOTS;
							    else if(jrb_dashed_lead.isSelected())
							    	 leads = TabStop.LEAD_THICKLINE;
							    else// if(jrb_line_lead.isSelected())
							    	 leads = TabStop.LEAD_UNDERLINE;

								TabStop tabitem = new TabStop(offset, align , leads);

								al_tablist.add(tabitem);
								//System.out.println("position :"+al_tablist.indexOf(tabitem));

							    /*
							    TabSet t = StyleConstants.getTabSet(a);
							    if(t!=null)
							    	//System.out.println("tabsss. "+t.getTabCount());
							    else
							        //System.out.println("sd   null");
							     */

                                int no_tabs = al_tablist.size();
                                TabStop tabarray[] =new TabStop[no_tabs];
                                for(int i=0;i<no_tabs;i++)
                                {
									tabarray[i] = (TabStop)al_tablist.get(i);
								}
								tabs= new TabSet( tabarray);

							    StyleConstants.setTabSet(a, tabs);
                                doc.setParagraphAttributes(doc.getLength(),1,a,true);
                                show(  false  );
							}
							catch(Exception e)
							{
								System.out.println(e+"\n ---> ex at hererrrr");
								//e.printStackTrace();
							}
						 }
					 };
		jb_ok.addActionListener(okAction);
        jp_2buttons.add(jb_ok,"Right");

        ActionListener cancelAction = new ActionListener()
				        	{
								public void actionPerformed(ActionEvent ev)
								{
		                            show(false);
								 }
							 };
		jb_cancel.addActionListener(cancelAction);
        jp_2buttons.add(jb_cancel,"Right");
        jp_bottom.add(jp_2buttons);

        getContentPane().add(jp_top,"North");
        getContentPane().add(jp_center,"Center");
        getContentPane().add(jp_bottom,"South");

		setSize(410,350);
 		setResizable(false);
 		setLocation(Utils.getMiddle(getSize()));
 		setLocale();
 		pack();

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
    * Used to show the list of tabs.
    */
    public void showList()
    {
		if(al_tablist.size() !=0)
		 {
		 	Double da[] = { new Double(9.0),new Double(8.9)};
		 	Float datas[] = new Float[al_tablist.size()];
			for(int i=0; i<al_tablist.size(); i++)
			{
				try
				{
					TabStop f =(TabStop) al_tablist.get(i);
					////System.out.println(""+i+" align   "+f.getAlignment());
					////System.out.println(""+i+" lead    "+f.getLeader());
					////System.out.println(""+i+" offset  "+f.getPosition());
					datas[i] = new Float(f.getPosition());//.toString();
			    }
			    catch(Exception e)
			    {
					System.out.println(e+"\n --->   ex at here : tab");
					//e.printStackTrace();
				}
		    }
		    if(al_tablist.size()==1)
		        {
					String dd[] = { "d","l"};
					jlt_offsets = new JList(datad);
					jlt_offsets.repaint();
					return;
				}
		    jlt_offsets = new JList( da );
		    jlt_offsets.repaint();
		}

	}// end of showLIst

	/**
	 * Sets the current locale to the user interface.
	 */
	public void setLocale()
	{

		    jb_ok.setText(parentword.wordBundle.getString("Ok"));
	        jb_cancel.setText(parentword.wordBundle.getString("Cancel"));
	        jb_clear.setText(parentword.wordBundle.getString("Clear"));
	        jb_clearall.setText(parentword.wordBundle.getString("ClearAll"));
	        jb_set.setText(parentword.wordBundle.getString("Set"));
	        jrb_left.setText(parentword.wordBundle.getString("Left"));
	        jrb_right.setText(parentword.wordBundle.getString("Right"));
	        jrb_decimal.setText(parentword.wordBundle.getString("Decimal"));
	        jrb_center.setText(parentword.wordBundle.getString("Center"));
	        jrb_bar.setText(parentword.wordBundle.getString("Bar"));

	        jrb_none_lead.setText(parentword.wordBundle.getString("None"));
	        jl_positions.setText(parentword.wordBundle.getString("TabStop"));
	        jl_default.setText(parentword.wordBundle.getString("DefaultTabStop"));

 			tb_align.setTitle(parentword.wordBundle.getString("Alignment"));
 			tb_lead.setTitle(parentword.wordBundle.getString("Leader"));
	}



}
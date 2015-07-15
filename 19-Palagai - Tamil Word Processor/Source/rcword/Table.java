package rcword;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;

import java.util.*;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 *  Used to get the number of rows and columns from the user and creates
 *  a table with that and  to do the other table operations.
 *  <p>   In order to do the table operations the main <code> Word </code>
 * 	class contains two arraylist. Finding the currently focused table's object among
 *	multiple tables is done by as follows.The arraylist al_tablelist contains the objects
 * 	of tables created so for. The other arraylist al_tableStatus contains the
 * 	status of the tables by either ON or OFF. It contains only one ON at any time
 * 	for the reason that focus can be at most one table atmost at a time. So the
 * 	last focus gained table index only will contain the ON state. When a table
 * 	gets the focus it's focus state will be ON and the previously focused table's
 * 	index state will be set OFF. </p>
 *	<p>   If we want to insert a row or column or if we want to delete a row or
 *	column or a table we need the table object for which that action to be taken among
 *	the other tables. The table object for which the action to be taken is one whose
 *	status in ON in the al_tableStatus arraylist. </p>
 *
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class Table extends JDialog implements ActionListener
{
		/** a reference to the main <code> Word </code> object	 */
	   Word parentword;
	   /** text field to get number of rows*/
	   JTextField jtf_noofrows;
	   /** text field to get number of columns*/
	   JTextField jtf_noofcolumns;
	   /** button to give the ok command*/
	   JButton jb_ok;
	   /** button to give the cancel command*/
	   JButton jb_cancel;
	   /** number of rows to be created*/
	   int rowcount=0;
	   /** number of columns to be created*/
       int columncount = 0;
		/** used to set the focus status of a table.*/
       Boolean OFF = new Boolean( false );
       /** used to set the focus status of a table */
       Boolean ON = new Boolean( true );

	   /** label to display the text "no. of rows"*/
       JLabel jl_no_rows = new JLabel("âî¢î¬ù ï¤óô¢è÷¢: ");
       /** label to display the text "no. of columns"*/
       JLabel jl_no_collumns = new JLabel("âî¢î¬ù ï¤¬óè÷¢ :");

       /** states whether the column selection is allowed or not*/
	   private boolean ALLOW_COLUMN_SELECTION =true;
		/** states whether the row selection is allowed or not*/
       private boolean ALLOW_ROW_SELECTION = true;
       /** contains the column number currently focused*/
       int CURENT_COLUMN = -1;
       /** contains the row number currently focused*/
       int CURENT_ROW = -1;

	/**
	* Creates the table dialog to create a table.
	* @param	parent a reference to the main <code> Word </code> object
	*/

	   public Table(Word parent)
	   {
	   	   super(parent,"Insert Table",true);
	   	   parentword = parent;

	   	   getContentPane().setLayout(new BorderLayout());
           JPanel jp_top = new JPanel(),
           jp_end = new JPanel();

         jp_top.setLayout(new GridLayout(2,2));
         jp_top.add(jl_no_collumns);

         jtf_noofcolumns = new JTextField();
         jp_top.add(jtf_noofcolumns);
         jtf_noofcolumns.addActionListener(this);

         jp_top.add(jl_no_rows);
         jtf_noofrows = new JTextField();
         jtf_noofrows.addActionListener(this);
         jp_top.add(jtf_noofrows);

         getContentPane().add(jp_top,BorderLayout.NORTH);

         jb_ok = new JButton("êó¤");
         jb_cancel = new JButton("ï¦è¢°");

         jl_no_rows.setFont(parentword.defaultfont);
         jl_no_collumns.setFont(parentword.defaultfont);
         jb_ok.setFont(parentword.defaultfont);
         jb_cancel.setFont(parentword.defaultfont);

         jb_ok.addActionListener(this);
         jb_cancel.addActionListener(this);

         jp_end.add(jb_ok);
         jp_end.add(jb_cancel);

         getContentPane().add(jp_end,BorderLayout.SOUTH);

         jtf_noofrows.setText("4");
         jtf_noofcolumns.setText("5");

         setSize(350,250);
       	 setResizable(false);
		 setLocation(Utils.getMiddle(getSize()));
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
	   * Action to create a table.
	   * @param event an object of ActionEvent which causes the event.
	   */
	   public void actionPerformed(ActionEvent event)
	   {
	   	     if(event.getSource() == jb_cancel)
	   	  	           setVisible(false);

	   	     else if(event.getSource() == jb_ok)
	   	     {

                int noofrows=1,noofcolumns = 1;
	   	     	    try
	   	     	    {
	   	             noofrows = Integer.parseInt( jtf_noofrows.getText() );
	   	       	     noofcolumns = Integer.parseInt( jtf_noofcolumns.getText() );
	   	       	     rowcount = noofrows;
	   	       	     columncount = noofcolumns;
	   	     	    }
	   	     	    catch(NumberFormatException n)
	   	     	    {
	   	     	    	  JOptionPane.showMessageDialog(this,"Enter an Integer number ","Warning",JOptionPane.WARNING_MESSAGE);
	   	     	    	  return;
	   	     	    }

	   	     	    if( noofrows>50 || noofcolumns > 50)
	   	     	    {
	   	     	    	  JOptionPane.showMessageDialog(this,"Enter no. < 50. ","Warning",JOptionPane.WARNING_MESSAGE);
	   	     	    	  return;
                    }

                 // creates a table with the given no. of rows and columns.
                 JTable temptable = new JTable( new DefaultTableModel(noofrows,noofcolumns));

                 temptable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				 if (ALLOW_ROW_SELECTION)
				 {   // true by default to allow row selection
				 			  // used to identify the row currently focused.
				             ListSelectionModel rowSM = temptable.getSelectionModel();
				             rowSM.addListSelectionListener(new ListSelectionListener()
				                {
				                    public void valueChanged(ListSelectionEvent e)
				                    {
				 						//Ignore extra messages.
				 						if (e.getValueIsAdjusting()) return;

				 						ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				 						if (lsm.isSelectionEmpty())
				 						{	//"No rows are selected."
				 							//System.out.println("No rows are selected.");
				 						} else
				 						{
				 							int selectedRow = lsm.getMinSelectionIndex();
				 							CURENT_ROW = selectedRow;
				 						}
				                    }
				               });
				         }
				 else
				 {  // row selection is not allowed.
					 temptable.setRowSelectionAllowed(false);
				 }

				 if (ALLOW_COLUMN_SELECTION)
				 { // false by default for column selection
					 if (ALLOW_ROW_SELECTION)
					 {
						 /*allows both row and column selection, which
						 implies that really wants to allow individual
						 cell selection. */
						 temptable.setCellSelectionEnabled(true);
					 }
					 temptable.setColumnSelectionAllowed(true);
					 ListSelectionModel colSM =
						 temptable.getColumnModel().getSelectionModel();
					 colSM.addListSelectionListener(new ListSelectionListener()
					 {	// used to identify the column number currently focused.
						 public void valueChanged(ListSelectionEvent e)
						 {
							 //Ignore extra messages.
							 if (e.getValueIsAdjusting()) return;

							 ListSelectionModel lsm = (ListSelectionModel)e.getSource();
							 if (lsm.isSelectionEmpty())
							 {	//"No columns are selected."
								// System.out.println("No columns are selected.");
							 } else
							 {
								 int selectedCol = lsm.getMinSelectionIndex();
								 CURENT_COLUMN = selectedCol;
							 }
						 }
					 });
				 }
				 // adds this newly created table in the list of tables created so for
                 parentword.al_tablelist.add(temptable);
                 // sets the state of this newly created table's state as OFF and add
                 // this in the list of table's state which contains either on or off.
                 parentword.al_tableStatus.add(OFF);

				 /* focus listener to listen identify the currently focused table among
				 	multiple tables. When a table gains the focus it sets the currently
				 	focus gained table's status as ON and sets the previously focus gained
				 	table's status as OFF */
				 FocusListener table_listener = new FocusListener()
					 {
						   public void focusGained(FocusEvent e)
							   {
									JTable t =(JTable) e.getSource();
									int table_no = parentword.al_tablelist.indexOf(t);

									int previousTableIndex = parentword.al_tableStatus.indexOf(new Boolean(true));
									if(previousTableIndex != -1)
									{
										 parentword.al_tableStatus.remove(previousTableIndex);
										 parentword.al_tableStatus.add(previousTableIndex,OFF);
									 }
									parentword.al_tableStatus.remove(table_no);
									parentword.al_tableStatus.add(table_no,ON);
								}
							public void focusLost(FocusEvent e)
							 {
							 }
					};

				  temptable.addFocusListener(table_listener);
				  parentword.workspace.insertComponent(temptable); // inserts the new table in the workspace.
   	              parentword.workspace.repaint();
	   	          setVisible(false);

	   	     }

	} // end of actionperformed
 }






























































	  /* public void addaColumn( );
	   {
	   	   jt_table.addColumn(new TableColumn());
	   	   return;
	   }*/


/*
    class MyTableModel extends AbstractTableModel
	    {
	        public int getColumnCount()
	        {
	            return columncount;//Names.length;
	        }

	        public int getRowCount()
	        {
	            return rowcount;//data.length;
	        }
	        public Object getValueAt(int a, int b)
	        {
				return " ";

			}
       }
*/
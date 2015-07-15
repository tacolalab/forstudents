/* (swing1.1beta3) */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @version 1.0 11/09/98
 */
public class ButtonEditor extends DefaultCellEditor
{
  protected JButton button;
  private String    label;
  private boolean   isPushed;
  Chathurangam main;
  String value;
  public ButtonEditor(Chathurangam main, JCheckBox checkBox)
  {
    super(checkBox);
    this.main=main;
    button = new JButton();
   // button.setFont(new Font("ArialBlack",0,12));
    button.setOpaque(false);
   /*  MouseListener mlistener = new MouseAdapter()
	   {

		   public void mousePressed(MouseEvent e) { checkPopup(e); }
		   public void mouseEntered(MouseEvent me)
		   {
						button.setBorderPainted(true);
					//	System.out.println("mouse entered event ");


		   	checkPopup(me);

		   }
		   public void mouseClicked(MouseEvent e)
			{
			   checkPopup(e);
			   	System.out.println("mouse clicked event ");

			}
		   public void mouseReleased(MouseEvent e)  { checkPopup(e); }
		   private void checkPopup(MouseEvent e)
		   {
		   }
	};
	button.addMouseListener(mlistener);*/

    button.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        fireEditingStopped();
      }
    });
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
                   boolean isSelected, int row, int column)
 {
    if (isSelected)
    {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else
    	{
    	  button.setForeground(table.getForeground());
    	  button.setBackground(table.getBackground());
    	}
    label = (value ==null) ? "" : value.toString();
   // button.setText("ZZZ");
	//value=value.toString();
    isPushed = true;
    return button;
  }

  public Object getCellEditorValue()
  {
    if (isPushed)
    {
		main.cur_tableHeader.setRowSelectionAllowed(true);
       	//System.out.println("sle row "+main.headerColumn.getRowSelectionAllowed()+"kkk    "+main.headerColumn.getSelectedRow());
 		int ccnt=main.cur_table.getColumnCount();
 		//System.out.println("YYYY "+ccnt);
 		main.cur_table.setColumnSelectionInterval(0,ccnt-1);
 		/*int selRowhd=main.temptable.getSelectedRow();
 		System.out.println("YYYY "+selRowhd);
    	//main.temptable.setRowHeight(100);
    	main.headerColumn.setRowHeight(selRowhd, 100);*/
    }
    isPushed = false;
    return new String( label ) ;
  }

  public boolean stopCellEditing()
  {
    isPushed = false;
    return super.stopCellEditing();
  }

  protected void fireEditingStopped()
  {
    super.fireEditingStopped();
  }
}


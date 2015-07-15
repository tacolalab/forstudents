import javax.swing.JToggleButton;
import javax.swing.Icon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import javax.swing.ButtonModel;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Toggle button in toolbar with different border on mouse over
 */
public class RolloverToggleButton extends JToggleButton
{
	/**
	 * Set icon, border for button
	 */
	public RolloverToggleButton(Icon icon)
	{
		//set icon
		super(icon);
		//set border
		setBorder(new BevelBorder(BevelBorder.LOWERED,Color.gray, Color.white));
		//default state
		setBorderPainted(false);

		//register mouse listener
		addMouseListener(new MouseAdapter()
		{
			//on mouse enter
			public void mouseEntered(MouseEvent me)
			{
				//paint border if enabled
				if(isEnabled())
				{
					setBorderPainted(true);
				}
			}
			//on mouse exit
			public void mouseExited(MouseEvent me)
			{
				//paint border if not selected
				if(!isSelected())
					setBorderPainted(false);
			}
		});
	}

	/**
	 * Sets the state of the button
	 * @param select <code>true</code> if the button is selected, otherwise <code>false</code>
	 */
	public void setSelected(boolean select)
	{
		super.setSelected(select);
		//paint border if selection is true
		if(select)
			setBorderPainted(true);
		else
			setBorderPainted(false);
	}
}

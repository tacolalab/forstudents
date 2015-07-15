import javax.swing.JButton;
import javax.swing.Icon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import java.awt.Color;

/**
 * Toolbar button with different border on mouse over
 */
public class RolloverButton extends JButton
{
	/**
	 * Create button with icon and border
	 */
	public RolloverButton(Icon icon)
	{
		//set icon
		super(icon);

		//set border
		setBorder(new BevelBorder(BevelBorder.LOWERED,Color.gray,
			Color.white));

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
					repaint();
				}
			}

			//on mouse exit
			public void mouseExited(MouseEvent me)
			{
				//don't paint border
				setBorderPainted(false);
				repaint();
			}
		});
	}
}

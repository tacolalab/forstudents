import java.awt.event.*;
import javax.swing.*;

public class EscWinEvent extends KeyAdapter
{
	java.awt.Component c;
		EscWinEvent(java.awt.Component c)
		{
			this.c=c;
		}
		public void keyPressed(KeyEvent ke)
		{
			System.out.println("keyTyped "+ke.getKeyCode());
			if(ke.getKeyCode() == ke.VK_ESCAPE)
			{
				System.out.println("keyTyped "+ke.getKeyCode());
				c.setVisible(false);
			}
		}


}
/*
 * 11:37:13 06/06/00
 *
 * ASeparator.java - A new separator for toolbar
 * Copyright (C) 2000 Romain Guy
 * romain.guy@jext.org
 * www.jext.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

import javax.swing.JToolBar;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.SwingConstants;

public class ASeparator extends JToolBar.Separator
{
	Color shadow = UIManager.getColor("controlDkShadow");
	Color highlight = UIManager.getColor("controlLtHighlight");

	public void paint(Graphics g)
	{
		Dimension s = getSize();
		int sWidth = s.width/2;

		g.setColor(shadow);
		g.drawLine(sWidth, 0, sWidth, s.height);

		g.setColor(highlight);
		g.drawLine(sWidth + 1, 0, sWidth + 1, s.height);
	}
}
// End of ASeparator.java

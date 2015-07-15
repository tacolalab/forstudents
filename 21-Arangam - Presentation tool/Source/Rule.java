import javax.swing.border.BevelBorder;
import java.awt.*;
import javax.swing.*;
import javax.accessibility.*;
import java.util.Locale;


/* Rule.java is used by AccessibleScrollDemo.java. */

public class Rule extends JComponent implements Accessible
{
	public static final int INCH = Toolkit.getDefaultToolkit().getScreenResolution();

	/*** Marker y position */
	private int currentY = 0;

	/*** Marker x position */
	private int currentX = 0;

	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	public static final int SIZE = 15;

	public int orientation;
	public boolean isMetric;
	private int increment;
	private int units;

	public Rule(int o, boolean m)
	{
		orientation = o;
		isMetric = m;
		setIncrementAndUnits();
		setBorder(new BevelBorder(BevelBorder.LOWERED, Color.gray, Color.white));
	}

	public void setIsMetric(boolean isMetric)
	{
		if (accessibleContext != null && this.isMetric != isMetric)
		{
			if (isMetric)
			{
				accessibleContext.firePropertyChange(
				  AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
				  AccessibleRulerState.INCHES, AccessibleRulerState.CENTIMETERS);
			} else
			{
				accessibleContext.firePropertyChange(
				  AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
				  AccessibleRulerState.CENTIMETERS, AccessibleRulerState.INCHES);
			}
		}
		this.isMetric = isMetric;
		setIncrementAndUnits();
		repaint();
	}

	private void setIncrementAndUnits()
	{
		if (isMetric)
		{
			units = (int) ((double) INCH / (double) 2.54); // dots per centimeter
			increment = units;
		} else
		{
			units = INCH;
			increment = units / 2;
		}
	}

	public boolean isMetric()
	{
		return this.isMetric;
	}

	public int getIncrement()
	{
		return increment;
	}

	public void setPreferredHeight(int ph)
	{
		setPreferredSize(new Dimension(SIZE, ph));
	}

	public void setPreferredWidth(int pw)
	{
		setPreferredSize(new Dimension(pw, SIZE));
	}

	protected void paintComponent(Graphics g)
	{
		Rectangle drawHere = g.getClipBounds();

		// Fill clipping area with dirty brown/orange.
		g.setColor(Color.white);
		g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);

		// Do the ruler labels in a small font that's black.
		Font currentFont = g.getFont();

		// Font - anu
		g.setFont(new Font(currentFont.getName(), currentFont.getStyle(), 9));
		g.setColor(Color.black);

		// Some vars we need.
		int end = 0;
		int start = 0;
		int tickLength = 0;
		String text = null;

		// Use clipping bounds to calculate first and last tick locations.
		if (orientation == HORIZONTAL)
		{
			start = (drawHere.x / increment) * increment;
			end = (((drawHere.x + drawHere.width) / increment) + 1) * increment;
		} else
		{
			start = (drawHere.y / increment) * increment;
			end = (((drawHere.y + drawHere.height) / increment) + 1) * increment;
		}

		// Make a special case of 0 to display the number
		// within the rule and draw a units label.
		Dimension d = getSize();
		if (start == 0)
		{
			text = Integer.toString(0) + (isMetric ? " cm" : " in");
			tickLength = 10;
			if (orientation == HORIZONTAL)
			{
				g.drawLine(0, SIZE - 1, 0, SIZE - tickLength - 1);
				g.drawString(text, 2, 21);
				g.drawLine(currentX, 1, currentX, d.height-1);
			} else
			{
				g.drawLine(SIZE - 1, 0, SIZE - tickLength - 1, 0);
				g.drawString(text, 9, 10);
				g.drawLine(1, currentY, d.width, currentY);
			}
			text = null;
			start = increment;
		}

		// ticks and labels
		for (int i = start; i < end; i += increment)
		{
			if (i % units == 0)
			{
				tickLength = 10;
				text = Integer.toString(i / units);
			} else
			{
				tickLength = 7;
				text = null;
			}

			// Draw - anu
			if (tickLength != 0)
			{
				if (orientation == HORIZONTAL)
				{
					g.drawLine(i, SIZE - 1, i, SIZE - tickLength - 1);
					if (text != null)
					{
						g.drawString(text, i + 3, SIZE / 2 + 3);
					}
				} else
				{
					g.drawLine(SIZE - 1, i, SIZE - tickLength - 1, i);
					if (text != null)
					{
						g.drawString(text, 3, i + 10);
					}
				}
			}
		}
	}

	public AccessibleContext getAccessibleContext()
	{
		if (accessibleContext == null)
		{
			accessibleContext = new AccessibleRuler();
		}
		return accessibleContext;
	}

	protected class AccessibleRuler extends AccessibleJComponent
	{
		public AccessibleRole getAccessibleRole()
		{
			return AccessibleRuleRole.RULER;
		}

		public AccessibleStateSet getAccessibleStateSet()
		{
			AccessibleStateSet states = super.getAccessibleStateSet();

			if (orientation == VERTICAL)
			{
				states.add(AccessibleState.VERTICAL);
			} else
			{
				states.add(AccessibleState.HORIZONTAL);
			}
			if (isMetric)
			{
				states.add(AccessibleRulerState.CENTIMETERS);
			} else
			{
				states.add(AccessibleRulerState.INCHES);
			}
			return states;
		}
	}

	public void setY(int y)
	{
		currentY = y;
		repaint();
	}

	public void setX(int x)
	{
		currentX = x;
		repaint();
	}

}


class AccessibleRuleRole extends AccessibleRole
{
	public static final AccessibleRuleRole RULER = new AccessibleRuleRole(
	  "ruler");

	protected AccessibleRuleRole(String key)
	{
		super(key);
	}

	// Should really provide localizable versions of these names.
	public String toDisplayString(String resourceBundleName,
	  Locale locale)
	{
		return key;
	}
}


class AccessibleRulerState extends AccessibleState
{
	public static final AccessibleRulerState INCHES = new AccessibleRulerState(
	  "inches");
	public static final AccessibleRulerState CENTIMETERS = new AccessibleRulerState(
	  "centimeters");

	protected AccessibleRulerState(String key)
	{
		super(key);
	}

	// Should really provide localizable versions of these names.
	public String toDisplayString(String resourceBundleName,
	  Locale locale)
	{
		return key;
	}
}


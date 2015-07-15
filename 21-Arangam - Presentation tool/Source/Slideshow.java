import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.print.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * Slideshow class that enables editing of multiple slides acts like a container for the slides
 * @see Slide
 */
public class Slideshow extends javax.swing.JPanel implements Printable
{
	/**
	 * The Arangam Frame from which the Slideshow is run.
	 */
	private transient Arangam arangam;

	/**
	 * Current Slide in view.
	 */
	private int currentSlide = 0;

	/**
	 * Number of slides in Slideshow.
	 */
	private int slidesNum = 0;

	/**
	 * All slides in Slideshow
	 */
	private Vector slides = new Vector();

	/**
	 * Printed page format of the Slideshow
	 */
	private PageFormat pageFormat;

	/*** Gets the words in current language. Tamil, English or Tamil-English */
	private ResourceBundle wordBundle;

	/**
	 * Constructs a new SlideShow and connects it main the application class
	 * @param arangam main class reference Arangam.
 	 * @see Arangam
	 */
	public Slideshow(Arangam arangam)
	{
		this.arangam = arangam;

		//set layout
		setLayout(new GridLayout(1,1));//1,1 is ok now

		//set background
		//setBackground(arangam.presentaionPane.getBackground());

		//set bounds
		//setBounds(Arangam.SLIDESHOW_X, Arangam.SLIDESHOW_Y,Arangam.SLIDESHOW_WIDTH, Arangam.SLIDESHOW_HEIGHT);
	}

	/**
	 * Connect the class to the main class.
	 * @param arangam main class reference.
	 */
	public void getInterface(Arangam arangam)
	{
		this.arangam = arangam;
	}

	/**
	 * Checks if the Slideshow is empty.
	 * @return <code>true</code> - sildeshow empty, false - more than 0 slides.
	 */
	public boolean isEmpty()
	{
		if (slidesNum == 0)
			return true;
		else
			return false;
	}

	/**
	 * Retrieves the current number of slides in the Slideshow.
	 * @return the current number of slides in the Slideshow.
	 */
	public int getSlidesCount()
	{
		return slidesNum;
	}

	/**
	 * Retrieves the current Slide number that is shown.
	 * @return the current Slide number that is shown.
	 */
	public int getCurrentSlideNum()
	{
		return currentSlide;
	}

	/**
	 * Sets the Slide to be shown.
	 * @param slideNum the Slide number to be shown.
	 */
	public void setCurrentSlideNum(int slideNum)
	{
		currentSlide = slideNum;
	}

	/**
	 * Returns the current shown Slide.
	 * @return the current shown Slide.
	 * @see Slide
	 */
	public Slide getCurrentSlide()
	{
		return (Slide)getComponent(0);
	}

	/**
	 * Returns a Slide.
	 * @param slideNum the Slide number to retrieve.
	 * @return a Slide.
	 * @see Slide
	 */
	public Slide getSlide(int slideNum)
	{
		if(slideNum == currentSlide)
			return (Slide)getComponent(0);
		else
			return (Slide)slides.elementAt(slideNum);
	}

	/**
	 * Sets the entire Slide show to edit and non edit mode.
	 * @param editable if <code>true</code>, can be editable; otherwise cannot be editable
	 */
	public void setArangamEditable(boolean editable)
	{
		//apply to all slides
		for (int i = 0; i < slidesNum; i++)
		{
			Slide tmpSlide = (Slide)(slides.elementAt(i));
			tmpSlide.setArangamEditable(editable);
		}
		repaint();
	}

	/**
	 *Adds a Slide to the Slideshow.
	 */
	public void addSlide()
	{
		Slide newSlide = new Slide(arangam);

		//apply same background
		if(arangam.isBGApplyToAll)
		{
			Slide tmpSlide = new Slide(arangam);
			int count = arangam.slideshow1.getComponentCount();
			if(count != 0)
			{
				tmpSlide = (Slide)arangam.slideshow1.getComponent(0);

				int effect = tmpSlide.fillEffect;
				Color fromColor = tmpSlide.fromColor;
				Color toColor = tmpSlide.toColor;

				newSlide.setFillEffect(effect);
				newSlide.setFromColor(fromColor);
				newSlide.setToColor(toColor);
			}
		}

		slides.insertElementAt(newSlide, currentSlide);
		add(newSlide);

		//if first Slide
		if(slidesNum == 0)
		{
			currentSlide = 1;
			setBackground(Color.white);
		}
		slidesNum++;
	}

	/**
	 * Removes the current shown Slide from the Slideshow.
	 */
	public void removeSlide()
	{
		removeSlide(currentSlide);
	}

	/**
	 * Removes a Slide from the Slideshow.
	 * @param slideToRemove the Slide number to be removed
	 */
	public void removeSlide(int slideToRemove)
	{
		//if only one Slide, should close Slide and not use this method
		if(slidesNum <= 1)
			return;
		if((slideToRemove > slidesNum) || (slideToRemove <= 0))
			return;
		slides.removeElementAt(slideToRemove-1);
		if(slideToRemove == currentSlide)
		{
			removeAll();
			if(currentSlide == slidesNum)
				currentSlide--;
			showSlide();
		}
		slidesNum--;
	}

	/**
	 * Shows the current Slide.
	 */
	public void showSlide()
	{
		showSlide(currentSlide);
	}

	/**
	 * Shows a Slide.
	 * @param slideToShow the Slide number to be shown.
	 */
	public void showSlide(int slideToShow)
	{
		//not a proper Slide number
		if((slideToShow > slidesNum) || (slideToShow < 1))
		{
			return;
		}

		//remove everything
		if(getComponentCount() != 0)
		{
			ConnectComponent focusedComp = getCurrentSlide().focused;
			if(focusedComp != null)
				getCurrentSlide().focusComponent(null);
			removeAll();
		}

		//add this one
		add((Slide)slides.elementAt(slideToShow - 1));
		//update current Slide
		currentSlide = slideToShow;
		repaint();
	}

	/**
	 * Sets the size of the Slide Show.
	 * @param size the dimension specifying the new size of this Slide.
	 */
	public void setSlideshowSize(Dimension size)
	{
		//apply to all slides
		for(int i = 0; i < slidesNum; i++)
		{
			Slide tmpSlide = (Slide)(slides.elementAt(i));
			tmpSlide.setSize(size);
		}
	}

	/**
	 * Sets the size of the Slide show back to the editable size.
	 * @see Slide#setSizeBack
	 */
	public void setSlideshowSizeBack()
	{
		/*
		//set normal bounds
		setBounds(Arangam.SLIDESHOW_X, Arangam.SLIDESHOW_Y,
				Arangam.SLIDESHOW_WIDTH, Arangam.SLIDESHOW_HEIGHT);
		//apply to all slides
		for(int i = 0; i < slidesNum; i++)
		{
			Slide tmpSlide = (Slide)(slides.elementAt(i));
			tmpSlide.setSizeBack();
		}
		*/
	}

	/**
	 * Shows the Slide sorter according to the myApp.zoom (zoom + 1)*(zoom +1) slides and the segment of the slides to be shown Example zoom = 2 segment = 2 -> slides 7-11.
	 * @param segment the segment of the slides to be shown.
	 */
	public void showSlideSorter(int segment)
	{
		double zoom = arangam.slideSorterDimensions;

		//not a proper setting
		if((segment < 1) || (zoom < 1))
			return;

		setBackground(Color.lightGray);

		int smallX = (int) (Arangam.SLIDESHOW_WIDTH / (1.2*zoom));
		int smallY = (int) (Arangam.SLIDESHOW_HEIGHT /(1.2*zoom));

		//horizontal space between two slides
		int spaceX = (int) (
			( (double) (Arangam.SLIDESHOW_WIDTH - zoom*smallX))/(zoom+1));
		//vertical space between two slides
		int spaceY = (int) (
			( (double) (Arangam.SLIDESHOW_HEIGHT - zoom*smallY))/(zoom+1));

		double pX = (double) smallX / Arangam.SLIDESHOW_WIDTH;
		double pY = (double) smallY / Arangam.SLIDESHOW_HEIGHT;

		removeAll();
		int x = 1;
		int y = 1;

		//update Slideshow size
		setSlideshowSize(new Dimension(Arangam.SLIDESHOW_WIDTH/4,
			Arangam.SLIDESHOW_HEIGHT/4));

		for(int i=0; i < slidesNum; i++)
		{
			Slide tmpSlide = (Slide)slides.elementAt(i);

			//update Slide size
			if((i >= (segment-1)*zoom*zoom) && (i < segment*zoom*zoom))
			{
				tmpSlide.setLocation((spaceX+smallX)*(x-1)+spaceX,
					(spaceY+smallY)*(y-1)+spaceY);
				add(tmpSlide);
			}
			if(((x % zoom) == 0) && ((y % zoom) == 0))
			{
				x = 1;
				y = 1;
			}
			else if((x % zoom) == 0)
			{
				x = 1;
				y++;
			}
			else
				x++;
		}
		repaint();
	}

	/**
	* Displays a dialog that allows modification of the page format of
	* the to-be-printed slides.
	*
	* @see #printSlideshow
	* @see #print
	* @see java.awt.print.PrinterJob#pageDialog
	*/
	public void pageSetup()
	{
		PrinterJob printJob = PrinterJob.getPrinterJob();
		//if the user has already changed the page format, show the last
		//selected options
		if(pageFormat == null)
		{
			pageFormat = printJob.pageDialog(printJob.defaultPage());
		}
		else
		{
			pageFormat = printJob.pageDialog(printJob.defaultPage(pageFormat));
		}
	}

	/**
	 * Prints the Slideshow.
	 */
	public void printSlideshow()
	{
		//create a new print job and set it to the current Slideshow
		PrinterJob printJob = PrinterJob.getPrinterJob();

		printJob.setPrintable(this);
		//show print dialog
		if(printJob.printDialog())
		try
		{
			//print set of slides
			printJob.print();
			int lastPrinted = currentSlide;
			if (currentSlide != lastPrinted)
				showSlide(currentSlide);
		}
		catch (PrinterException error)
		{
			wordBundle = arangam.getWordBundle();
			Object[] options = {wordBundle.getString("ok")};

			Utils.showDialog(arangam,"printErr",JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE, Arangam.warIcon, options, 0);

		}
	}

	/**
	 * Print the page.
	 */
	public int print(Graphics g, PageFormat pageFormat, int pageIndex)
	throws PrinterException
	{
		if((pageIndex < 0) || (pageIndex >= slidesNum))
			return Printable.NO_SUCH_PAGE;
		int lastViewMode = arangam.viewMode;
		if(lastViewMode != Arangam.SORTER_VIEW)
			showSlide(pageIndex+1);
		else
			return Printable.PAGE_EXISTS;;
		//translate screen coordinate system to printing coordinate system
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		double pX = pageFormat.getImageableWidth()/this.getWidth();
		double pY = pageFormat.getImageableHeight()/this.getHeight();
		double pPrint = Math.min(pX, pY);
		g2.scale(pPrint,pPrint);
		g2.clip(new Rectangle(0, 0,
		getCurrentSlide().getWidth(), getCurrentSlide().getHeight()));
		arangam.viewMode = Arangam.PRINT_VIEW;
		getCurrentSlide().print(g2);
		arangam.viewMode = lastViewMode;
		return Printable.PAGE_EXISTS;
	}

}

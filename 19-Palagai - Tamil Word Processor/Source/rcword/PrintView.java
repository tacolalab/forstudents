   package rcword;

   import java.awt.*;
   import java.awt.event.*;
   import java.io.*;
   import java.util.*;
   import java.sql.*;

   import java.awt.print.*;
   import javax.swing.plaf.basic.*;

   import javax.swing.*;
   import javax.swing.text.*;
   import javax.swing.event.*;
   import javax.swing.border.*;
   import javax.swing.text.rtf.*;
   import javax.swing.undo.*;

/**
 * Used in the printing process.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */

   class PrintView extends BoxView
	 {
	     protected int m_firstOnPage = 0;
	     protected int m_lastOnPage = 0;
	     protected int m_pageIndex = 0;

	     /**
		* a reference to the main <code> Word </code> object
		 */
	     Word parentword;

		/**
		* Constructs an object of <code> PrintView </code> with the following parameters.
		*
		* @param elem 	default root element of the document to be printed
		* @param root root view of the workspace
		* @param w width of the page to be printed
		* @param h height of the pane go be printed.
		* @param word a reference to the main <code> Word </code> object
		*/
	     public PrintView(Element elem, View root, int w, int h,Word word)
	     {

	       super(elem, Y_AXIS);
	       parentword = word;
	       setParent(root);
	       setSize(w, h);
	       layout(w, h);
	     }

		/**
		*	Used to retrieve a particular page while printing the current document.
		*/
	     public boolean paintPage(Graphics g, int hPage,int pageIndex)
	     {
	 		  if (pageIndex > m_pageIndex)
	 		  {
	 				m_firstOnPage = m_lastOnPage + 1;
	 				if (m_firstOnPage >= getViewCount())
	 				    return false;
	 				m_pageIndex = pageIndex;
	 		  }
	 		  int yMin = getOffset(Y_AXIS, m_firstOnPage);
	 		  int yMax = yMin + hPage;
	 		  Rectangle rc = new Rectangle();

	 		  for (int k = m_firstOnPage; k < getViewCount(); k++)
	 		  {
	 				rc.x = getOffset(X_AXIS, k);
	 				rc.y = getOffset(Y_AXIS, k);
	 				rc.width = getSpan(X_AXIS, k);
	 				rc.height = getSpan(Y_AXIS, k);
	 				if (rc.y+rc.height > yMax)
	 				  break;
	 				m_lastOnPage = k;
	 				rc.y -= yMin;
	 				paintChild(g, rc, k);
	 		  }
	 		  return true;
	     }// end of method paintpage

  } // end of class printview
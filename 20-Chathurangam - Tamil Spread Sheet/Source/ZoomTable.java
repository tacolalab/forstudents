import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

public class ZoomTable extends JTable {

private double zoomValue = 1.0;

ZoomTable() {
super();
}

ZoomTable(int numRows, int numColumns) {
super(numRows, numColumns);
}

ZoomTable(Object[][] rowData, Object[] columnNames) {
super(rowData, columnNames);
}

ZoomTable(TableModel dm) {
super(dm);
}

ZoomTable(TableModel dm, TableColumnModel cm) {
super(dm, cm);
}

ZoomTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm)
{
super(dm, cm, sm);
}

ZoomTable(Vector rowData, Vector columnNames) {
super(rowData, columnNames);
}

public double getZoomValue() {
return zoomValue;
}

public void setZoomValue(double zoomValue) {
if (this.zoomValue != zoomValue) {
this.zoomValue = zoomValue;
resizeAndRepaint();
}
}

public void paint(Graphics g) {

AffineTransform at = new AffineTransform(zoomValue, 0.0, 0.0,
zoomValue, 0.0f, 0.0f);
Graphics2D g2 = (Graphics2D)g;
g2.setTransform(at);
super.paint(g2);

}
}


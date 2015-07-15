package lyricanalyser;

import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Dimension;

/**
 * <p>Title: Tamil Lyric Analyser</p>
 *
 * <p>Description: Analyses Tamil Lyrics on Various Aspects</p>
 *
 * <p>Copyright: Copyright (c) 2009 Mellinam Education</p>
 *
 * <p>Company: Mellinam Education</p>
 *
 * @author Madhan Karky
 * @version 1.0
 */
public class LyricAnalyser {
    boolean packFrame = false;

    /**
     * Construct and show the application.
     */
    public LyricAnalyser() {
        LyricFrame frame = new LyricFrame();
        // Validate frames that have preset sizes
        // Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }

        // Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Application entry point.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.
                                             getSystemLookAndFeelClassName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                new LyricAnalyser();
            }
        });
    }

    private void jbInit() throws Exception {
    }
}

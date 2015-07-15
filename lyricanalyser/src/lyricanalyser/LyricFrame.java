package lyricanalyser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.lang.Object;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;

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
public class LyricFrame extends JFrame {
    JPanel contentPane;
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    ImageIcon image1 = new ImageIcon(lyricanalyser.LyricFrame.class.getResource(
            "openFile.png"));
    ImageIcon image2 = new ImageIcon(lyricanalyser.LyricFrame.class.getResource(
            "closeFile.png"));
    ImageIcon image3 = new ImageIcon(lyricanalyser.LyricFrame.class.getResource(
            "help.png"));
    FlowLayout flowlayout = new FlowLayout();
    JTextArea jTextArea1 = new JTextArea();


    JMenuItem jMenuItem1 = new JMenuItem();
    JMenu jMenu1 = new JMenu();
    JMenuItem jMenuItem2 = new JMenuItem();
    JMenuItem jMenuItem3 = new JMenuItem();
    JMenu jMenu2 = new JMenu();
    JMenuItem jMenuItem4 = new JMenuItem();
    JMenuItem jMenuItem5 = new JMenuItem();
    JMenuItem jMenuItem6 = new JMenuItem();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JList jList1 = new JList();
    JButton jButton1 = new JButton();
    JScrollPane jScrollPane1;
    JMenu jMenu3 = new JMenu();
    JMenuItem jMenuItem7 = new JMenuItem();

    Lyric currentLyric;
    Characters chars;


    JFileChooser fileChoose = new JFileChooser();

    String fileName;
    JMenuItem jMenuItem8 = new JMenuItem();

    public LyricFrame() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {

        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(flowlayout);
        setSize(new Dimension(500, 600));
        setTitle("Tamil Lyric Analyser");
        jMenuFile.setText("File");
        jMenuFileExit.setText("Exit");
        jMenuFileExit.addActionListener(new
                                        LyricFrame_jMenuFileExit_ActionAdapter(this));
        contentPane.setMinimumSize(new Dimension(800, 1000));
        contentPane.setPreferredSize(new Dimension(800, 1000));
        jTextArea1.setFont(new java.awt.Font("Arial", Font.PLAIN, 14));
        jTextArea1.setText("");
        jTextArea1.setColumns(40);
        jTextArea1.setRows(30);
        jScrollPane1 = new JScrollPane(jTextArea1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new LyricFrame_jMenuItem1_actionAdapter(this));
        jMenuItem2.setText("Flow Score");
        jMenuItem2.addActionListener(new LyricFrame_jMenuItem2_actionAdapter(this));
        jMenuItem3.setText("Close");
        jMenuItem3.addActionListener(new LyricFrame_jMenuItem3_actionAdapter(this));
        jMenu2.setText("Search");
        jMenuItem4.setText("Find Meter Match");
        jMenuItem5.setText("Search Lyric");
        jMenuItem6.setText("Pattern");
        jLabel1.setFont(new java.awt.Font("Lucida Grande", Font.PLAIN, 18));
        jLabel1.setText("Score");
        jLabel2.setFont(new java.awt.Font("Lucida Grande", Font.BOLD, 20));
        jLabel2.setText("0");
        jButton1.setText("FlowScore");
        jButton1.addActionListener(new LyricFrame_jButton1_actionAdapter(this));
        jMenu3.setText("Tools");
        jMenuItem7.setText("Transliterate");
        jMenuItem7.addActionListener(new LyricFrame_jMenuItem7_actionAdapter(this));
        jMenu1.setText("Analyse");
        jMenuItem8.setEnabled(false);
        jMenuItem8.setText("Show Stats");
        jMenuItem8.addActionListener(new LyricFrame_jMenuItem8_actionAdapter(this));
        jMenuBar1.add(jMenuFile);
        jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu2);
        jMenuBar1.add(jMenu3);
        jMenuFile.add(jMenuItem1);
        jMenuFile.add(jMenuItem3);
        jMenuFile.add(jMenuFileExit);
        //contentPane.add(jTextArea1);
        contentPane.add(jScrollPane1);
        contentPane.add(jButton1);
        contentPane.add(jList1);
        contentPane.add(jLabel1);
        contentPane.add(jLabel2);
        jMenu1.add(jMenuItem2);
        jMenu1.add(jMenuItem6);
        jMenu2.add(jMenuItem4);
        jMenu2.add(jMenuItem5);
        jMenu3.add(jMenuItem7);
        jMenu3.add(jMenuItem8);
        setJMenuBar(jMenuBar1);

        chars = new Characters();

        fileChoose.setDialogTitle("Open Tamil Song File");
        fileChoose.setCurrentDirectory(new File("./db"));

        fileName = new String("");



    }

    /**
     * File | Exit action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }


    /**
     * File open
     * @param actionEvent ActionEvent
     */
    public void jMenuItem1_actionPerformed(ActionEvent actionEvent) {
        int returnVal = fileChoose.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            fileName = fileChoose.getSelectedFile().getName();
        }

        try{
         // Open the file that is the first
         // command line parameter
         BufferedReader br = new BufferedReader(new InputStreamReader
                              (new FileInputStream("./db/"+fileName)));
          String strLine = new String("");
          //Read File Line By Line
          jTextArea1.setText("");
          while ((strLine = br.readLine()) != null)   {
              if(strLine.trim().equals("<\u0B9A\u0BB0\u0BA3\u0BAE\u0BCD>"))//saranam
                  strLine = "#\n";
              else if(strLine.charAt(0)=='<')
                  strLine = "";
              else
                  strLine += "\n";
              jTextArea1.append(strLine);
            }



            currentLyric = new Lyric(jTextArea1.getText(),chars);
            jLabel2.setText("0");



      }catch (Exception e){//Catch exception if any
          System.err.println("Error0: " + e.getMessage());
      }


    }
    /**
     * Performs operations for file close
     * @param actionEvent ActionEvent
     */
    public void jMenuItem3_actionPerformed(ActionEvent actionEvent) {
        jTextArea1.setText("");
        jLabel2.setText("0");
        currentLyric = null;
        fileName = "";
    }

    /**
     * Computes score for the lyric
     * @param actionEvent ActionEvent
     * @throws IOException
     */
    public void jButton1_actionPerformed(ActionEvent actionEvent) throws IOException{
        if(!jTextArea1.getText().trim().equalsIgnoreCase("")){
            jLabel2.setText("0");
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.####");

            if(currentLyric != null) {
                //currentLyric = new Lyric(jTextArea1.getText(),chars);
                jLabel2.setText("" + df.format(currentLyric.computeMScore()));
            }
        }


    }
    /**
     * Transliterates the Tamil Text
     * @param actionEvent ActionEvent
     * @throws IOException
     */
    public void jMenuItem7_actionPerformed(ActionEvent actionEvent) throws IOException {
        if(!jTextArea1.getText().trim().equalsIgnoreCase("")){
            if(currentLyric == null)
                currentLyric = new Lyric(jTextArea1.getText().trim(),chars);
           jTextArea1.setText(currentLyric.toEnglish());
       }

    }

    /**
     * Computes Flow Score for the Lyric
     * @param actionEvent ActionEvent
     * @throws IOException
     */
    public void jMenuItem2_actionPerformed(ActionEvent actionEvent) throws IOException{
        if(!jTextArea1.getText().trim().equalsIgnoreCase("")){
            jLabel2.setText("0");
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.####");

            if(currentLyric != null) {

                jLabel2.setText("" + df.format(currentLyric.computeMScore()));
            }
        }

    }
    /**
     * This method shows Statistics about a Lyric
     * @param actionEvent ActionEvent
     */
    public void jMenuItem8_actionPerformed(ActionEvent actionEvent) {

    }
}


class LyricFrame_jMenuItem8_actionAdapter implements ActionListener {
    private LyricFrame adaptee;
    LyricFrame_jMenuItem8_actionAdapter(LyricFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuItem8_actionPerformed(actionEvent);
    }
}


class LyricFrame_jMenuItem2_actionAdapter implements ActionListener {
    private LyricFrame adaptee;
    LyricFrame_jMenuItem2_actionAdapter(LyricFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        try{
            adaptee.jMenuItem2_actionPerformed(actionEvent);
        }catch (Exception e){//Catch exception if any
          System.err.println("Error1: " + e.getMessage());
      }

    }
}


class LyricFrame_jMenuItem7_actionAdapter implements ActionListener {
    private LyricFrame adaptee;
    LyricFrame_jMenuItem7_actionAdapter(LyricFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {

        try{
             adaptee.jMenuItem7_actionPerformed(actionEvent);
      }catch (Exception e){//Catch exception if any
          System.err.println("Error2: " + e.getMessage());
      }


    }
}


class LyricFrame_jButton1_actionAdapter implements ActionListener {
    private LyricFrame adaptee;
    LyricFrame_jButton1_actionAdapter(LyricFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        try{
            adaptee.jButton1_actionPerformed(actionEvent);
      }catch (Exception e){//Catch exception if any
          System.err.println("Error3: " + e.getMessage());
      }
    }
}


class LyricFrame_jMenuItem3_actionAdapter implements ActionListener {
    private LyricFrame adaptee;
    LyricFrame_jMenuItem3_actionAdapter(LyricFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuItem3_actionPerformed(actionEvent);
    }
}


class LyricFrame_jMenuItem1_actionAdapter implements ActionListener {
    private LyricFrame adaptee;
    LyricFrame_jMenuItem1_actionAdapter(LyricFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuItem1_actionPerformed(actionEvent);
    }
}


class LyricFrame_jMenuFileExit_ActionAdapter implements ActionListener {
    LyricFrame adaptee;

    LyricFrame_jMenuFileExit_ActionAdapter(LyricFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuFileExit_actionPerformed(actionEvent);
    }
}

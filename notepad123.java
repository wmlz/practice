import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

public class notepad123 extends JFrame {
    //create popupMenu
    JPopupMenu popupMenu;
    //create toolBar
    JToolBar toolbar;
    //create menubar
    JMenuBar menubar;
    //create mune
    JMenu menu1, menu2, menu3, menu4;
    //create menuitem, radiobtn, checkbox and button
    JMenuItem menuitem1_1, menuitem1_2, menuitem1_3, menuitem1_4, menuitem1_5, popitem1, popitem2,menuitem4_1,menuitem4_2,menuitem4_3;
    JRadioButtonMenuItem radiobtn1, radiobtn2, radiobtn3;
    JCheckBoxMenuItem checkbox1, checkbox2;
    JButton btn1, btn2, btn3, btn4, btn5;
    //create textarea
    JTextArea txa;
    //create scroll
    JScrollPane scroll;
    //create file path
    String filename = null;
    // create fontlist
    String[] fontsizelist = {"10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30"};
    JList fontlist;
    // boolean variable record whether the file has been saved
    boolean isSaved = false;
    //String variable to save the saved content
    String savedContent = null;


    public static void main(String[] args) throws Exception {
        notepad123 notepad = new notepad123();
    }

    //constructor
    public notepad123() {

        //new menubar
        menubar = new JMenuBar();
        //new toolbar
        toolbar = new JToolBar();
        toolbar.setFloatable(false);

        //set menu name
        menu1 = new JMenu("File");
        menu2 = new JMenu("Text");
        menu3 = new JMenu("Font Size");
        menu4 = new JMenu("Font Color");
        popupMenu = new JPopupMenu();

        //set menuitem name
        menuitem1_1 = new JMenuItem("New");
        menuitem1_2 = new JMenuItem("Open");
        menuitem1_3 = new JMenuItem("Save");
        menuitem1_4 = new JMenuItem("Save as");
        menuitem1_5 = new JMenuItem("Exit");
        menuitem4_1 = new JMenuItem("Red");
        menuitem4_2 = new JMenuItem("Black");
        menuitem4_3 = new JMenuItem("Blue");
        popitem1 = new JMenuItem("Copy");
        popitem2 = new JMenuItem("Paste");
        radiobtn1 = new JRadioButtonMenuItem("Monospaced");
        radiobtn2 = new JRadioButtonMenuItem("Serif");
        radiobtn3 = new JRadioButtonMenuItem("SansSerif");
        checkbox1 = new JCheckBoxMenuItem("Italic");
        checkbox2 = new JCheckBoxMenuItem("Bold");
        btn1 = new JButton("Copy");
        btn2 = new JButton("Paste");
        btn3 = new JButton("Red");
        btn4 = new JButton("Black");
        btn5 = new JButton("Blue");
        fontlist = new JList(fontsizelist);
        fontlist.setFixedCellWidth(30);
        fontlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        //add listener
        menuitem1_1.addActionListener(new ButtonListener());
        menuitem1_2.addActionListener(new ButtonListener());
        menuitem1_3.addActionListener(new ButtonListener());
        menuitem1_4.addActionListener(new ButtonListener());
        menuitem1_5.addActionListener(new ButtonListener());
        menuitem4_1.addActionListener(new ButtonListener());
        menuitem4_2.addActionListener(new ButtonListener());
        menuitem4_3.addActionListener(new ButtonListener());
        radiobtn1.addActionListener(new ButtonListener());
        radiobtn2.addActionListener(new ButtonListener());
        radiobtn3.addActionListener(new ButtonListener());
        checkbox1.addActionListener(new ButtonListener());
        checkbox2.addActionListener(new ButtonListener());
        fontlist.addListSelectionListener(new ListListener());
        popitem1.addActionListener(new ButtonListener());
        popitem2.addActionListener(new ButtonListener());
        btn1.addActionListener(new ButtonListener());
        btn2.addActionListener(new ButtonListener());
        btn3.addActionListener(new ButtonListener());
        btn4.addActionListener(new ButtonListener());
        btn5.addActionListener(new ButtonListener());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (savedContent.equals(txa.getText())) {   //check whether the current file has been saved
                    System.exit(0);
                } else {
                    int n = JOptionPane.showConfirmDialog(null, "The file has NOT been saved, are you sure to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            }

            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                filename = null;
                txa.setText("");
                savedContent = txa.getText();
            }
        });


        //new texearea
        txa = new JTextArea();
        txa.setLineWrap(true);        //auto wrap
        txa.setWrapStyleWord(true);            // not break word when wrap

        //add mouselistener
        txa.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
                txa.requestFocus();
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
                txa.requestFocus();
            }
        });
        //new scroll
        scroll = new JScrollPane(txa);

        //new buttongroup
        ButtonGroup btngroup1 = new ButtonGroup();
        btngroup1.add(radiobtn1);
        btngroup1.add(radiobtn2);
        btngroup1.add(radiobtn3);

        menu1.add(menuitem1_1);
        menu1.add(menuitem1_2);
        menu1.addSeparator();    //add separator
        menu1.add(menuitem1_3);
        menu1.add(menuitem1_4);
        menu1.addSeparator();    //add separator
        menu1.add(menuitem1_5);
        menu2.add(radiobtn1);
        menu2.add(radiobtn2);
        menu2.add(radiobtn3);
        menu2.addSeparator();    //add separator
        menu2.add(checkbox1);
        menu2.add(checkbox2);
        menu3.add(fontlist);
        menu4.add(menuitem4_1);
        menu4.add(menuitem4_2);
        menu4.add(menuitem4_3);
        popupMenu.add(popitem1);
        popupMenu.add(popitem2);

        menubar.add(menu1);
        menubar.add(menu2);
        menubar.add(menu3);
        menubar.add(menu4);
        toolbar.add(btn1);
        toolbar.add(btn2);
        toolbar.addSeparator();    //add separator
        toolbar.add(btn3);
        toolbar.add(btn4);
        toolbar.add(btn5);


        this.setJMenuBar(menubar);
        this.add(toolbar, BorderLayout.NORTH);
        this.add(scroll);


        //set title
        this.setTitle("Notepad");
        //set size
        this.setSize(500, 350);
        //set location
        this.setLocation(100, 100);
        //set default close operation
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //set visible
        this.setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // new file
            if (e.getSource() == menuitem1_1) {
                if (savedContent.equals(txa.getText())) {   //check whether the current file has been saved
                    filename = null;
                    txa.setText("");
                    savedContent = txa.getText();
                } else {
                    int n = JOptionPane.showConfirmDialog(null, "The file has NOT been saved, are you sure to create a new file?", "New File Confirmation", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        filename = null;
                        txa.setText("");
                        savedContent = txa.getText();
                    }
                }
            }

            // open file
            if (e.getSource() == menuitem1_2) {
                if (savedContent.equals(txa.getText())) {       //check whether the current file has been saved
                    filename = getFilename();
                    openFile(filename);
                    savedContent = txa.getText();
                } else {
                    int n = JOptionPane.showConfirmDialog(null, "The file has NOT been saved, are you sure to open a new file?", "Open File Confirmation", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        filename = getFilename();
                        openFile(filename);
                        savedContent = txa.getText();
                    }
                }
            }

            // save file
            if (e.getSource() == menuitem1_3) {
                if (filename == null)
                    filename = setFilename();
                saveToFile(filename);
                isSaved = true;
                savedContent = txa.getText();
            }

            //save as file
            if (e.getSource() == menuitem1_4) {
                filename = setFilename();
                saveToFile(filename);
                isSaved = true;
                savedContent = txa.getText();
            }
            // exit
            if (e.getSource() == menuitem1_5) {
                if (savedContent.equals(txa.getText())) {   //check whether the current file has been saved
                    System.exit(0);
                } else {
                    int n = JOptionPane.showConfirmDialog(null, "The file has NOT been saved, are you sure to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            }
            // set Monosapced font
            if (e.getSource() == radiobtn1) {
                Font currentFont = txa.getFont();
                txa.setFont(new Font("Monospaced", currentFont.getStyle(), currentFont.getSize()));
            }
            //set Serif font
            if (e.getSource() == radiobtn2) {
                Font currentFont = txa.getFont();
                txa.setFont(new Font("Serif", currentFont.getStyle(), currentFont.getSize()));
            }
            //set SansSerif font
            if (e.getSource() == radiobtn3) {
                Font currentFont = txa.getFont();
                txa.setFont(new Font("SansSerif", currentFont.getStyle(), currentFont.getSize()));
            }
            // set ITALIC and BOLD
            if (e.getSource() == checkbox1 || e.getSource() == checkbox2) {
                Font currentFont = txa.getFont();
                if (!checkbox1.isSelected() && checkbox2.isSelected())
                    currentFont = currentFont.deriveFont(Font.BOLD);
                else if (!checkbox1.isSelected() && !checkbox2.isSelected())
                    currentFont = currentFont.deriveFont(Font.PLAIN);
                else if (checkbox1.isSelected() && checkbox2.isSelected())
                    currentFont = currentFont.deriveFont(Font.BOLD + Font.ITALIC);
                else if (checkbox1.isSelected() && !checkbox2.isSelected())
                    currentFont = currentFont.deriveFont(Font.ITALIC);
                txa.setFont(currentFont);
            }
            //copy
            if (e.getSource() == popitem1 || e.getSource() == btn1) {
                String selectedtext = txa.getSelectedText();
                if (!(selectedtext == null)) {
                    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection selection = new StringSelection(selectedtext);
                    clip.setContents(selection, null);
                }
            }
            //paste
            if (e.getSource() == popitem2 || e.getSource() == btn2) {
                txa.requestFocus();
                Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable contents = clip.getContents(this);
                if (contents == null) return;
                String text = "";
                try {
                    text = (String) contents.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception exception) {
                }
                txa.replaceRange(text, txa.getSelectionStart(), txa.getSelectionEnd());
            }

            // set selection color to red
            if (e.getSource() == btn3) {
                txa.setSelectionColor(Color.red);
            }
            // set selection color to black
            if (e.getSource() == btn4) {
                txa.setSelectionColor(Color.black);
            }
            // set selection color to blue
            if (e.getSource() == btn5) {
                txa.setSelectionColor(Color.blue);
            }
            //set font color to red
            if(e.getSource() == menuitem4_1) {
                txa.setForeground(Color.red);
            }
            // set font color to black
            if(e.getSource() == menuitem4_2) {
                txa.setForeground(Color.black);
            }
            // set font color to blue
            if(e.getSource() == menuitem4_3) {
                txa.setForeground(Color.blue);
            }
        }
    }

    // List listener
    private class ListListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            Font currentFont = txa.getFont();
            txa.setFont(currentFont.deriveFont(Float.parseFloat(fontlist.getSelectedValue().toString())));
        }
    }

    // function save to file
    public void saveToFile(String filename) {
        try {
            File txtfile = new File(filename);
            FileWriter fileWriter = new FileWriter(txtfile);
            fileWriter.write(this.txa.getText());
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    //function get file name
    public String getFilename() {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("Open");
        filechooser.showOpenDialog(null);
        filechooser.setVisible(true);
        return filechooser.getSelectedFile().getAbsolutePath();
    }

    //function set file name
    public String setFilename() {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("Save");
        filechooser.showSaveDialog(null);
        filechooser.setVisible(true);
        return filechooser.getSelectedFile().getAbsolutePath();
    }

    public void openFile(String filepath) {
        FileReader filereader = null;
        BufferedReader bufferedreader = null;

        try {
            filereader = new FileReader(filepath);
            bufferedreader = new BufferedReader(filereader);
            String s = bufferedreader.readLine();
            String content = "";

            while (s != null) {
                content += (s + "\n");
                s = bufferedreader.readLine();
            }
            txa.setText(content);

        } catch (Exception ee) {

        } finally {
            try {
                filereader.close();
                bufferedreader.close();
            } catch (Exception ee) {

            }
        }
    }
}

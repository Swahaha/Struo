import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths; 
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.*;
import java.util.Locale;  

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.Desktop;  

// Libs required by MigLayout
import net.miginfocom.swing.MigLayout;
import net.miginfocom.layout.Grid;

// Libs required to manage events
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;

public class GUII {

    JFrame main;

    JPanel pnlLEFT;
    JPanel pnMID;
    JPanel pnlRIGHT;
    JPanel pnlPAGE2;     
    JSplitPane splSPLIT;
    JTabbedPane tabPANE;

    JLabel labONE;
    JLabel labREM;

    JTextField txtTWO;
    JTextField txtTHREE;
    JTextField new1;
    JTextField new2;
    JTextArea txtFILE;

    JButton butTHREE;
    JButton butREAD;
    JButton butSAVE;
    JButton butADD;
    JButton butFILE;
    JButton butCREATE;
    JButton butSORT;

    DefaultListModel model1;    
    DefaultListModel model2;
    DefaultListModel model3;

    JList lstFOUR;
    JList lstREM;

    JMenuBar menuBar;

    JButton butOpen;
    JButton butSave;
    JButton butSearch;
    JButton butPreferences;
    JButton butDelete;
    JButton butSplit;
    JButton butMerge;

    JPopupMenu menuRight;
    JPopupMenu menuRem;

    JScrollPane scrollFOUR;
    JScrollPane scrollFILE;
    JScrollPane scrollREM;

    JComboBox combo1;
    JComboBox combo2;
    DefaultComboBoxModel combom1;
    DefaultComboBoxModel combom2;

    String FILEPATH;    

    RealOverallinital sm = new RealOverallinital();
    Boolean Popup = false;

    File initial = new File("non-volatile.txt");

    String FILEPATHoverall;

    /**
     * Constructor for objects of GUI
     */
    public GUII(){
        main = new JFrame("My Text File Organiser");

        final JDesktopPane Desk = new JDesktopPane();

        main.setPreferredSize(new Dimension(1000,600));  
        main.setContentPane(Desk);
        main.getContentPane().setLayout(new MigLayout());
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.getContentPane().setBackground(new Color(74, 83, 107));

        pnlLEFT = new JPanel();
        pnlLEFT.setLayout(new MigLayout("width 100%", "left", "top"));
        pnlLEFT.setBackground(new Color(255, 154, 141));        

        pnlRIGHT = new JPanel();
        pnlRIGHT.setBackground(new Color(174, 214, 220));        
        pnlRIGHT.setLayout(new MigLayout("width 100%", "left", "top"));

        pnlPAGE2 = new JPanel();
        pnlPAGE2.setBackground(new Color(255, 154, 141));        
        pnlPAGE2.setLayout(new MigLayout("width 100%", "left", "top"));

        splSPLIT = new JSplitPane(SwingConstants.VERTICAL, pnlLEFT, pnlRIGHT); 
        splSPLIT.setOrientation(SwingConstants.VERTICAL); 
        splSPLIT.setResizeWeight(0.5);
        splSPLIT.setBackground(new Color(74, 83, 107));
        splSPLIT.setDividerLocation(340);

        tabPANE = new JTabbedPane();
        tabPANE.setFont(new Font("sansserif",0,16));
        tabPANE.setBackground(new Color(255, 154, 141));
        tabPANE.setForeground(Color.black);

        labONE = new JLabel();
        labONE.setFont(new Font("Georgia",Font.BOLD,18));
        labONE.setText("File"); 

        txtFILE = new JTextArea("Empty");
        txtFILE.setFont(new Font("Helvetica", 0, 14));
        txtFILE.setLineWrap(true);
        txtFILE.setWrapStyleWord(true);
        txtFILE.setMargin(new Insets (3,3,3,3));

        scrollFILE = new JScrollPane(txtFILE);

        // Set up Save Button
        butFILE = new JButton();
        butFILE.setFont(new Font("sansserif",0,14));
        butFILE.setText("Save");
        butFILE.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(labONE.getText().equals("in-progress.txt")){
                        JDialog dialogie = new JDialog(main, "JDialog Box");
                        dialogie.getContentPane().setLayout(new MigLayout());

                        JLabel namepls = new JLabel("Name new file: ");

                        JTextField name = new JTextField(20);
                        name.setFont(new Font("sansserif",0,14));

                        JButton aight = new JButton();
                        aight.setFont(new Font("sansserif",0,14));
                        aight.setText("\u2713");
                        aight.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {

                                    if(name.getText() != ""){
                                        labONE.setText(name.getText());
                                        try {                   
                                            Save();
                                            sm.main();
                                            addItemToList();
                                            model1.set(0,labONE.getText());
                                        } catch (IOException i) {
                                        }
                                        dialogie.dispose();
                                    }
                                }
                            });
                        dialogie.add(namepls, "span 2, wrap 10");
                        dialogie.add(name, "width 150, height 30, wrap 10");
                        name.setText("");
                        dialogie.add(aight, "width 30, height 30");
                        dialogie.setSize(400, 200);
                        dialogie.setLocation(400,250);
                        dialogie.setVisible(true);
                    }else{

                        try {                   
                            Save();
                            sm.main();
                            addItemToList();
                            model1.set(0,labONE.getText());
                        } catch (IOException i) {
                        }
                    }
                }
            });       

        // Set up Delete Button
        butDelete = new JButton();
        butDelete.setFont(new Font("sansserif",0,14));
        butDelete.setText("Delete");
        butDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog dialog = new JDialog(main, "JDialog Box");
                    dialog.getContentPane().setLayout(new MigLayout());

                    JLabel lab = new JLabel("Are you sure you want to delete " + labONE.getText() + "?");

                    JButton nokay = new JButton();
                    nokay.setFont(new Font("sansserif",0,14));
                    nokay.setText("No");
                    nokay.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                            }
                        });

                    JButton kay = new JButton();
                    kay.setFont(new Font("sansserif",0,14));
                    kay.setText("Yes");
                    kay.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {

                                try {                   
                                    Removes();
                                    sm.main();
                                    addItemToList();
                                } catch (IOException i) {
                                }

                                dialog.dispose();

                            }
                        });
                    dialog.add(lab, "span 2, wrap 10");
                    dialog.add(nokay, "width 50, height 30");
                    dialog.add(kay, "width 50, height 30");
                    dialog.setSize(400, 150);
                    dialog.setLocation(400,250);
                    dialog.setVisible(true);
                }
            });       

        // Set up Split Button
        butSplit = new JButton();
        butSplit.setFont(new Font("sansserif",0,14));
        butSplit.setText("Split");
        butSplit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog dialog = new JDialog(main, "Split Dialogue");
                    dialog.getContentPane().setLayout(new MigLayout());

                    JLabel lab = new JLabel("Name the files you would like this data to be split into");
                    new1 = new JTextField(20);
                    new1.setFont(new Font("sansserif",0,14));
                    new2 = new JTextField(20);
                    new2.setFont(new Font("sansserif",0,14));
                    JButton ok = new JButton();
                    ok.setFont(new Font("sansserif",0,14));
                    ok.setText("\u2713");
                    ok.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                try {                   
                                    Split();
                                    sm.main(); //Backend method
                                    addItemToList(); //Reset the file list
                                } catch (IOException i) {
                                }
                                dialog.dispose();
                            }
                        });
                    dialog.add(lab, "span 2, wrap 10");
                    dialog.add(new1, "width 150, height 30");

                    new1.setText(labONE.getText());
                    String notxt = labONE.getText();
                    StringBuffer sb = new StringBuffer(notxt);
                    sb.delete(sb.length()-4, sb.length()); 
                    // The ".txt" is removed so that the default Split names can be given
                    dialog.add(new2, "width 150, height 30, wrap 15");
                    new2.setText(sb + " 2.txt");
                    dialog.add(ok, "width 30, height 30");
                    dialog.setSize(400, 200);
                    dialog.setLocation(400,250);
                    dialog.setVisible(true);
                }
            });

        // Set up Popup Menu Item: Open File
        JPopupMenu menuRight = new JPopupMenu();
        JMenuItem jmiFILE = new JMenuItem("Open file");
        jmiFILE.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    Open();

                }
            });   

        // Set up Popup Menu Item: Rename
        JMenuItem jmiRename = new JMenuItem("Rename");
        jmiRename.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent z) {
                    JDialog dialogu = new JDialog(main, "Rename " + (String)lstFOUR.getSelectedValue());
                    dialogu.getContentPane().setLayout(new MigLayout());

                    JLabel nameplease = new JLabel("Rename to: ");

                    JTextField namen = new JTextField(20);
                    namen.setFont(new Font("sansserif",0,14));

                    JButton okie = new JButton();
                    okie.setFont(new Font("sansserif",0,14));
                    okie.setText("\u2713");
                    okie.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent q) {
                                if(namen.getText() != ""){

                                    File file = new File(FILEPATHoverall + "/" + lstFOUR.getSelectedValue().toString());
                                    File file2 = new File(FILEPATHoverall + "/" + namen.getText());

                                    if (file2.exists()){
                                        JOptionPane.showMessageDialog(null, "The new file name already exists");
                                    } else {
                                        file.renameTo(file2);
                                        model1.set((int)lstFOUR.getSelectedIndex(),namen.getText());
                                        dialogu.dispose();
                                    }
                                }
                            }
                        });
                    dialogu.add(nameplease, "span 2, wrap 10");
                    dialogu.add(namen, "width 150, height 30, wrap 10");
                    namen.setText("");
                    dialogu.add(okie, "width 30, height 30");

                    dialogu.setSize(400, 200);
                    dialogu.setLocation(400,250);
                    dialogu.setVisible(true);

                }
            });   

        // Set up Popup Menu Item: Context
        JMenuItem jmiContext = new JMenuItem("Context");
        jmiContext.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    Conextualize();

                }
            });   

        // Set up Stop Words Popup Menu Item: Delete
        JPopupMenu menuRem = new JPopupMenu();
        JMenuItem jmiDelete = new JMenuItem("Delete");
        jmiDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    DefaultListModel model = (DefaultListModel) lstREM.getModel();
                    int selectedIndex = lstREM.getSelectedIndex();

                    String correctREM = (String)lstREM.getSelectedValue();
                    StringBuilder sb = new StringBuilder(correctREM);
                    sb.deleteCharAt(correctREM.length() - 1);
                    sb.deleteCharAt(0);

                    REM_words app = new REM_words();
                    app.Delete(sb.toString(), FILEPATHoverall);
                    if (selectedIndex != -1) {
                        model.remove(selectedIndex);
                    }
                }
            });   
        menuRem.add(jmiDelete);

        // Set up Search textbox
        txtTWO = new JTextField(20);
        txtTWO.setFont(new Font("sansserif",0,14));
        txtTWO.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    butTHREE.doClick();                

                }
            });   

        // Set up Stop Words input box
        txtTHREE = new JTextField(20);
        txtTHREE.setFont(new Font("sansserif",0,14));
        txtTHREE.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    butADD.doClick();                

                }
            });   

        // Set up Search Button
        butTHREE = new JButton();
        butTHREE.setFont(new Font("sansserif",0,14));
        butTHREE.setText("Search");
        butTHREE.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    Searches();
                    Popup = true;
                }
            });      

        // Set up New File Button
        butCREATE = new JButton();
        butCREATE.setFont(new Font("sansserif",0,14));
        butCREATE.setText("New file");
        butCREATE.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    try {                   
                        Create();
                    } catch (IOException i) {
                    }

                }
            });    

        // Set up dynamic Sorting Button
        butSORT = new JButton();
        butSORT.setFont(new Font("sansserif",0,14));
        butSORT.setText("Name ↑");
        butSORT.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {

                    if(butSORT.getText().equals("Date ↓")){
                        butSORT.setText("Date ↑");
                        SortDate();
                    } else if(butSORT.getText().equals("Date ↑")){
                        butSORT.setText("Name ↓");
                        ReversesortName();
                    } else if(butSORT.getText().equals("Name ↓")){
                        butSORT.setText("Name ↑");
                        SortName();
                    } else if(butSORT.getText().equals("Name ↑")){
                        butSORT.setText("Date ↓");
                        ReversesortDate();
                    }
                }
            });    

        // Set up Refresh Button
        butREAD = new JButton();
        butREAD.setFont(new Font("sansserif", 0,18));
        butREAD.setText("\u21ba");
        butREAD.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    addItemToList();
                    txtTWO.setText("");
                    Popup = false;
                }
            });  

        // Set up Add String button for Stop Words
        butADD = new JButton();
        butADD.setFont(new Font("sansserif",0,14));
        butADD.setText("Add String");
        butADD.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    addRemToList();

                }
            });  

        // Set up file-name list with its list model to hold data
        model1 = new DefaultListModel();
        lstFOUR = new JList(model1);
        lstFOUR.setFont(new Font("sansserif",0,14));
        lstFOUR.addMouseListener(new MouseAdapter(){
                public void mousePressed(MouseEvent e) {
                    maybeShowPopup(e);
                }

                public void mouseReleased(MouseEvent e) {
                    maybeShowPopup(e);
                }

                private void maybeShowPopup(MouseEvent e) {
                    if (e.isPopupTrigger()) {

                        if (Popup == false){
                            menuRight.add(jmiFILE);
                            menuRight.add(jmiRename);
                            menuRight.remove(jmiContext);
                            menuRight.show(e.getComponent(), e.getX(), e.getY());
                        } else if (Popup == true){
                            menuRight.add(jmiFILE);
                            menuRight.add(jmiContext);
                            menuRight.add(jmiRename);
                            menuRight.show(e.getComponent(),e.getX(), e.getY());
                        }

                    }
                }

                public void mouseClicked(MouseEvent e)  {
                    if (e.getClickCount() == 2) {
                        try {                   
                            txtFILE.setText("");
                            labONE.setText((String)lstFOUR.getSelectedValue());
                            transferfile((String)lstFOUR.getSelectedValue());
                        } catch (IOException i) {
                        }
                    }
                }
            });

        // Set up scroll bar for file name list
        scrollFOUR = new JScrollPane(lstFOUR);

        // Set up 'Stop' words list with its list model to hold data
        model3 = new DefaultListModel();
        lstREM = new JList(model3);
        lstREM.setFont(new Font("sansserif",0,14));      
        lstREM.addMouseListener(new MouseAdapter(){

                public void mousePressed(MouseEvent e) {
                    maybeShowPopup(e);
                }

                public void mouseReleased(MouseEvent e) {
                    maybeShowPopup(e);
                }

                private void maybeShowPopup(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        menuRem.show(e.getComponent(),e.getX(), e.getY());
                    }
                }
            });

        // Set up Merge Button
        butMerge = new JButton();
        butMerge.setFont(new Font("sansserif",0,14));
        butMerge.setText("Merge");
        butMerge.addActionListener(new ActionListener() { 
                @Override public void actionPerformed(ActionEvent e) {
                    JDialog dialog = new JDialog(main, "Merge");
                    dialog.getContentPane().setLayout(new MigLayout());
                    JLabel Filesname = new JLabel("Choose the two files you want to merge together:");
                    String[] lstArray = new String[model1.getSize()];
                    model1.copyInto(lstArray); //Making the dropdown menus use items from the File list
                    combom1 = new DefaultComboBoxModel(lstArray);
                    combo1 = new JComboBox();
                    combo1.setModel(combom1);
                    combom2 = new DefaultComboBoxModel(lstArray);
                    combo2 = new JComboBox();
                    combo2.setModel(combom2);
                    JLabel Mergename = new JLabel("Name the new merged file :");
                    JTextField merged = new JTextField(20);
                    merged.setFont(new Font("sansserif",0,14));
                    JButton okay = new JButton();
                    okay.setFont(new Font("sansserif",0,14));
                    okay.setText("\u2713");
                    okay.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                try {                   
                                    txtFILE.setText("");
                                    labONE.setText(merged.getText());
                                    transferfile((String)combo1.getSelectedItem()); //Append content to Textarea
                                    transferfile((String)combo2.getSelectedItem()); //Append content to Textarea
                                } catch (IOException i) {}
                                dialog.dispose();
                            }
                        });
                    dialog.add(Filesname, "span 2, wrap 10");
                    dialog.add(combo1, "width 150, height 30");
                    dialog.add(combo2, "width 150, height 30, wrap 15");
                    dialog.add(Mergename, "span 2, wrap 10");
                    dialog.add(merged, "width 150, height 30, wrap 15");
                    dialog.add(okay, "width 30, height 30");
                    dialog.setSize(560, 230);
                    dialog.setLocation(400,250);
                    dialog.setVisible(true);
                }
            });

        // Set up 'Stop' words label
        labREM = new JLabel();
        labREM.setFont(new Font("Georgia",Font.BOLD,18));
        labREM.setText("Stop Words"); 

        // Set up scroll bar for 'stop' words list
        scrollREM = new JScrollPane(lstREM);

        // Add elements to respective JPanels and the main frame
        generateMenu();
        main.setJMenuBar(menuBar);

        pnlLEFT.add(txtTWO,"width 150, height 30");  
        pnlLEFT.add(butTHREE,"width 90:90:90, height 30");
        pnlLEFT.add(butREAD,"width 50:50:50, height 30, wrap 10");
        pnlLEFT.add(scrollFOUR, "span 3, grow, height 80%, wrap 10");
        pnlLEFT.add(butCREATE,"width 90:90:90, height 30");
        pnlLEFT.add(butSORT,"align right, width 90:90:90, height 30");

        pnlRIGHT.add(labONE,"height 20, align left, wrap 20, span 5");
        pnlRIGHT.add(scrollFILE, "width 95%, height 80%, span 5, wrap 20");
        pnlRIGHT.add(butSplit);
        pnlRIGHT.add(butMerge);
        pnlRIGHT.add(butDelete, "align right");
        pnlRIGHT.add(butFILE,"align right");

        pnlPAGE2.add(labREM,"height 20, align left, wrap 10");
        pnlPAGE2.add(txtTHREE,"width 150, height 30");  
        pnlPAGE2.add(butADD,"width 110:110:110, height 30, wrap 10");
        pnlPAGE2.add(scrollREM, "span 2, grow, height 80%, wrap 10");

        tabPANE.addTab(" Main ", splSPLIT);
        tabPANE.addTab(" Options ", pnlPAGE2);
        main.add(tabPANE,"width 100%, height 100%");

        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setResizable(true);
        main.pack();
        main.setVisible(true);

        // Fill up file-name list on start-up
        try{
            if (initial.exists() == false){
                FileWriter fWriter = new FileWriter(initial);
                fWriter.close();
                selectFile();
            }
            Path currentfile = Path.of("non-volatile.txt");
            FILEPATHoverall = Files.readString(currentfile);
            sm.main();
        }catch(IOException e){}
        REMToList();
        addItemToList();
    }  

    /**
     * Sort file name list based on Ascending order
     */
    private void SortName() {
        Object Temp;
        int Templocation;
        int L = model1.getSize();
        int Smallest;
        String reference;
        String compare;

        for (int n = 0; n < L-1; n++){
            Smallest = n;
            for (int m = n+1; m < L; m++){
                reference = model1.get(Smallest).toString().toLowerCase();
                compare = model1.get(m).toString().toLowerCase();
                if ((reference).compareTo(compare) > 0){
                    Smallest = m;
                }
            } 

            Temp = model1.get(Smallest);
            Templocation = model1.indexOf(Temp, 0);
            model1.set(Templocation, model1.get(n));
            model1.set(n, Temp);
        }
    }

    /**
     * Sort file name list based on Descending order
     */
    private void ReversesortName()  {
        Object Temp;
        int Templocation;
        int L = model1.getSize();
        int Largest;
        String reference;
        String compare;

        for (int n = 0; n < L-1; n++){
            Largest = n;
            for (int m = n+1; m < L; m++){
                reference = model1.get(Largest).toString().toLowerCase();
                compare = model1.get(m).toString().toLowerCase();
                if ((reference).compareTo(compare) < 0){
                    Largest = m;
                }
            }

            Temp = model1.get(Largest);
            Templocation = model1.indexOf(Temp, 0);
            model1.set(Templocation, model1.get(n));
            model1.set(n, Temp);
        }
    }

    /**
     * Sort file name list based on Ascending last-modified time
     */
    private void SortDate() {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        // Format used to store last modified date as a String in the database
        String SELECTD = "SELECT tFILE.FILE_date FROM tFILE WHERE File_name = ?";
        try (Connection conn = this.connect();
        PreparedStatement pstmt  = conn.prepareStatement(SELECTD)){
            //The following is a Selection sort with the added complexity of using database queries
            Object Temp;
            int L = model1.getSize();
            int NewestIndex;
            String reference;
            String compare;
            LocalDateTime ReferenceDate; 
            LocalDateTime CompareDate;
            for (int n = 0; n < L-1; n++){
                NewestIndex = n;
                for (int m = n+1; m < L; m++){ // keeps reducing the loop size to be efficient
                    pstmt.setString(1,model1.get(NewestIndex).toString());
                    ResultSet rs1  = pstmt.executeQuery();
                    reference = rs1.getString("FILE_date");
                    ReferenceDate = LocalDateTime.parse(reference, DATE_FORMATTER); 
                    // Gets the DATE format last modified date of the current file
                    pstmt.setString(1,model1.get(m).toString());
                    ResultSet rs2  = pstmt.executeQuery();
                    compare = rs2.getString("FILE_date");
                    CompareDate = LocalDateTime.parse(compare, DATE_FORMATTER);

                    if ((ReferenceDate).compareTo(CompareDate) > 0){ // ReferenceDate is after CompareDate
                        NewestIndex = m;
                    }
                } 

                Temp = model1.get(NewestIndex);
                model1.set(NewestIndex, model1.get(n)); 
                model1.set(n, Temp); //Swaps the files such that the newest one comes to the front 
            }
        } catch (SQLException e) {}
    }

    /**
     * Sort file name list based on Descending last-modified time
     */
    private void ReversesortDate() {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        String SELECTD = "SELECT tFILE.FILE_date "
            + "FROM tFILE WHERE File_name = ?";

        try (Connection conn = this.connect();
        PreparedStatement pstmt  = conn.prepareStatement(SELECTD)){
            Object Temp;
            int L = model1.getSize();
            int Largest;
            String reference;
            String compare;
            LocalDateTime RefDate;
            LocalDateTime CompDate;

            for (int n = 0; n < L-1; n++){
                Largest = n;
                for (int m = n+1; m < L; m++){
                    pstmt.setString(1,model1.get(Largest).toString());
                    ResultSet rs1  = pstmt.executeQuery();
                    reference = rs1.getString("FILE_date");
                    RefDate = LocalDateTime.parse(reference, DATE_FORMATTER);

                    pstmt.setString(1,model1.get(m).toString());
                    ResultSet rs2  = pstmt.executeQuery();
                    compare = rs2.getString("FILE_date");
                    CompDate = LocalDateTime.parse(compare, DATE_FORMATTER);

                    if ((RefDate).compareTo(CompDate) < 0){
                        Largest = m;
                    }
                } 

                Temp = model1.get(Largest);
                model1.set(Largest, model1.get(n));
                model1.set(n, Temp);
            }
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
    }

    /**
     * Create "in-progress.txt" on UI
     */
    private void Create() throws IOException {
        if(model1.contains("in-progress.txt") == false){
            model1.add(0,"in-progress.txt");
        }
        int i = model1.indexOf("in-progress.txt");
        lstFOUR.setSelectedIndex(i);
        txtFILE.setText("");
        labONE.setText((String)lstFOUR.getSelectedValue());
        transferfile((String)lstFOUR.getSelectedValue());
    }

    /**
     * Delete file and reset Textarea/UI
     */
    private void Removes() throws IOException {
        File f = new File(FILEPATHoverall + "/" + labONE.getText());
        f.delete();            
        txtFILE.setText("Empty");
        labONE.setText("File");
    }

    /**
     * Append selected file contents to Textarea
     */
    private void transferfile(String filename) throws IOException {
        File file = new File(FILEPATHoverall + "/" + filename);   
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String s; 
        while((s=br.readLine())!=null)   //Reading Content from the file
        {
            txtFILE.append(s + "\n"); //Adding the content onto the Textarea
        }
    }

    /**
     * Saves Textarea contents under the selected file-name
     */
    private void Save() throws IOException {
        String change = txtFILE.getText();
        FileWriter fWriter = new FileWriter(
                FILEPATHoverall + "/" + labONE.getText());
        fWriter.write(change); //Inserts Textarea content into file
        fWriter.close();
        JOptionPane.showMessageDialog(null, labONE.getText() + " had been updated");

    }

    /**
     * Splits Textarea contents into two files
     */
    private void Split() throws IOException{
        int caret = txtFILE.getCaretPosition();
        txtFILE.setSelectionStart(0);
        txtFILE.setSelectionEnd(caret);
        String change = txtFILE.getSelectedText();
        //Select all the content until caret from Textarea
        FileWriter fWriter1 = new FileWriter(
                FILEPATHoverall + "/" + new1.getText());
        fWriter1.write(change);
        fWriter1.close();
        //Insert that text into the first chosen file
        txtFILE.setSelectionStart(caret);
        int end = txtFILE.getText().length();
        txtFILE.setSelectionEnd(end);
        change = txtFILE.getSelectedText();
        //Select all the content after the caret in the Textarea
        FileWriter fWriter2 = new FileWriter(
                FILEPATHoverall + "/" + new2.getText());
        fWriter2.write(change);
        fWriter2.close();
        //Insert that text into the second chosen file
        txtFILE.setCaretPosition(caret);

        JOptionPane.showMessageDialog(null, labONE.getText() + " has been split");

    }

    /**
     * Selects the folder domain
     */
    private void Open(){
        File file = new File(FILEPATHoverall + "/" + (String)lstFOUR.getSelectedValue());
        if(!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not  
        {  
            //System.out.println("not supported");  
            return;  
        }  
        Desktop desktop = Desktop.getDesktop();  
        if(file.exists()){         //checks file exists or not  
            try{
                desktop.open(file); //opens the specified file
            }catch (Exception a){
            }
        }
    }

    /**
     * Establish connection with my.db using filepath from non-volatile.txt
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + FILEPATHoverall + "/my.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Adds user-inputted 'Stop' word to 'stop' word list
     */
    private void addRemToList(){
        if (txtTHREE.getText().compareTo("")!=0){
            model3.add(0, "\"" + txtTHREE.getText() + "\"");
            REM_words app = new REM_words();
            app.insert(txtTHREE.getText().toLowerCase(), FILEPATHoverall);
            txtTHREE.setText("");
        }    
    }

    /**
     * Adds 'stop' words from my.db to the 'stop' words list in UI
     */
    private void REMToList(){
        String sql = "SELECT REM_words FROM tREM ORDER BY REM_id ASC";

        if(model3.capacity() != model3.getSize()){

            try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
                while (rs.next()) {
                    model3.addElement("\"" + rs.getString("REM_words") + "\"");
                }
            } catch (SQLException e) {
                //System.out.(e.getMessage());
            }
            model3.trimToSize();
        }
    }

    /**
     * Adds file-names from my.db to the file-name list in UI
     */
    private void addItemToList(){
        model1.clear();
        try{
            sm.main();
        }catch(IOException e){
        }

        String sql = "SELECT FILE_name FROM tFILE ORDER BY FILE_date ASC";

        if(model1.capacity() != model1.getSize()){

            try (Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)){
                while (rs.next()) {
                    model1.addElement(rs.getString("FILE_name"));
                }
            } catch (SQLException e) {
                //System.out.(e.getMessage());
            }
            model1.trimToSize();
        }

        butSORT.setText("Date ↓");
        ReversesortDate();
    }

    /**
     * Searches a term and modulates file-name list to files that contain said term
     */
    private void Searches(){
        if (txtTWO.getText().compareTo("")!=0){
            model1.removeAllElements(); // empty file list
            Search app = new Search();
            String names[] = app.manage(txtTWO.getText().toLowerCase(), FILEPATHoverall);
            for (int i = 0; i<names.length; i++){
                if (names[i] != null) {
                    model1.addElement(names[i]); // add the relevant files into file list
                }else{ break; }
            }
        }
    }

    /**
     * Displays the context snippets given a searched term and a file
     */
    int counter;
    private void Conextualize(){
        if (txtTWO.getText().compareTo("")!=0){
            counter = 0;
            Context app = new Context(); // instantiate the context class so I can directly call on the output array

            JDialog dialog = new JDialog(main, "Context for " + "\"" + txtTWO.getText() + "\"" );
            dialog.getContentPane().setLayout(new MigLayout());

            try{ // Executes the above method for relevant search term, file name, and folder path 
                app.contextsearch(txtTWO.getText().toLowerCase(), (String)lstFOUR.getSelectedValue(), FILEPATHoverall);
            }catch (Exception e){}

            JLabel labnow = new JLabel(app.output[0]);

            JButton Next = new JButton();
            Next.setFont(new Font("sansserif",0,14));
            Next.setText("Next"); 
            // I am not showing here, but this label is only added up when app.output[1] != null
            Next.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        counter = counter + 1;
                        if(app.output[counter] != null){
                            labnow.setText(app.output[counter]); // Switches between context Strings on click
                        }
                        if(app.output[counter+1] == null){
                            Next.hide(); // Hides button when no further Strings are available
                        }
                    }
                });

            JLabel intro = new JLabel("Context for the word:");
            intro.setFont(new Font("Georgia",0,14));

            JButton okay = new JButton();
            okay.setFont(new Font("sansserif",Font.BOLD,14));
            okay.setText("Ok");
            okay.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });
            dialog.add(intro, "span 2, wrap 10");
            dialog.add(labnow, "align center, span 2, wrap 30");
            if(app.output[1] != null){
                dialog.add(Next, "width 40, height 30");
            }
            dialog.add(okay, "align right, width 40, height 30");
            dialog.setLocation(400,250);
            dialog.setSize(600, 150);
            dialog.setVisible(true);
            okay.requestFocus();
        }
    }    

    /**
     * Sets up filechooser
     */
    private void selectFile() throws IOException{
        int result;

        JFileChooser fileChooser = new JFileChooser();

        // FileNameExtensionFilter filter = new FileNameExtensionFilter("File folder");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 

        result = fileChooser.showOpenDialog(null); 

        if (result == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "You selected the file:\n"
                + "\"" + fileChooser.getSelectedFile().getAbsolutePath() + "\""); 

            FILEPATH = fileChooser.getSelectedFile().getAbsolutePath();
            FILEPATHoverall = FILEPATH;

            FileWriter fWriter3 = new FileWriter(initial);
            fWriter3.write(FILEPATH);
            fWriter3.close();

            File checkDB = new File(FILEPATHoverall + "/my.db");

            if(checkDB.exists() == false){
                String url = "jdbc:sqlite:" + FILEPATHoverall + "/my.db";
                try (Connection conn = DriverManager.getConnection(url)) {
                } catch (SQLException e) {}

                String table1 = "CREATE TABLE IF NOT EXISTS tFILE (\n"
                    + "    FILE_id INTEGER PRIMARY KEY,\n"
                    + "    FILE_name TEXT NOT NULL,\n"
                    + "    FILE_date TEXT NOT NULL\n"
                    + ");";
                try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
                    stmt.execute(table1);
                } catch (SQLException e) {}

                String table2 = "CREATE TABLE IF NOT EXISTS tIMP (\n"
                    + "    IMP_id INTEGER PRIMARY KEY,\n"
                    + "    IMP_words TEXT NOT NULL\n"
                    + ");";
                try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
                    stmt.execute(table2);
                } catch (SQLException e) {}

                String table3 = "CREATE TABLE IF NOT EXISTS tINDEX (\n"
                    + "    I_id INTEGER NOT NULL,\n"
                    + "    F_id INTEGER NOT NULL,\n"
                    + "    CONSTRAINT tINDEX_FK FOREIGN KEY (I_id) REFERENCES tIMP(IMP_id) ON DELETE CASCADE,\n"
                    + "    CONSTRAINT tINDEX_FK_1 FOREIGN KEY (F_id) REFERENCES tFILE(FILE_id) ON DELETE CASCADE\n"
                    + ");";
                try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
                    stmt.execute(table3);
                } catch (SQLException e) {}

                String table4 = "CREATE TABLE IF NOT EXISTS tREM (\n"
                    + "    REM_id INTEGER PRIMARY KEY,\n"
                    + "    REM_words TEXT NOT NULL\n"
                    + ");";
                try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
                    stmt.execute(table4);
                } catch (SQLException e) {}

                String table5 = "CREATE TABLE IF NOT EXISTS tTEMP (\n"
                    + "    TEMP_words TEXT,\n"
                    + "    TEMP_fileid INTEGER NOT NULL, \n"
                    + "    CONSTRAINT tTEMP_FK FOREIGN KEY (TEMP_fileid) REFERENCES tFILE(FILE_id) ON DELETE CASCADE\n"
                    + ");";
                try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
                    stmt.execute(table5);
                } catch (SQLException e) {}
            }
            try{
                sm.main();
                addItemToList();
            }
            catch(IOException e){}           
        }else{
            JOptionPane.showMessageDialog(null, "Operation cancelled by user"); 
        }
    } 

    /**
     * Second part of the folder domain choice algorithm
     */
    private void readFile(){
        try (BufferedReader MYFILE = new BufferedReader(new FileReader(FILEPATH))){
            String LINE = null;
            model1.clear();
            while ((LINE = MYFILE.readLine()) != null){
                model1.addElement(LINE);
            }
            MYFILE.close();
        }catch(Exception e){
            //System.out.println(e);
            return;
        }                
    }

    /**  
     * Sets up confirmation dialog box
     */
    public int askConfirmation(String theTitle, String theMessage) {
        int result = JOptionPane.showConfirmDialog((Component) null, theMessage, theTitle, JOptionPane.OK_CANCEL_OPTION);
        return result;
    }

    /** 
     * Sets up the About message
     */
    private void displayAboutMessage(){
        JOptionPane.showMessageDialog(main,"000050-0095 Text File Organizing Tool\nVersion 1.16","About...",JOptionPane.INFORMATION_MESSAGE);
    } 

    /**  
     * Allows user to exit program
     */    
    private void exit(String theTitle, String theMessage){
        int i = askConfirmation(theTitle, theMessage);
        if (i == 0){ 
            main.dispose();
        }
    }    

    /** 
     * Generates the menu that is set up earlier
     */
    private void generateMenu(){

        menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenu menuData = new JMenu("Data");
        JMenu menuPrefs = new JMenu("Preferences");        
        JMenu menuHelp = new JMenu("Help");

        JMenuItem comOpen = new JMenuItem("Open");
        JMenuItem comEmpty = new JMenuItem("Empty data");
        JMenuItem comExit = new JMenuItem("Exit");
        JMenuItem comRefresh = new JMenuItem("Refresh");
        JMenuItem comGeneral = new JMenuItem("General");
        JMenuItem comSearch = new JMenuItem("Search...");
        JMenuItem comRem = new JMenuItem("Stop words...");   
        JMenuItem comAbout = new JMenuItem("About...");

        // Sets up Menu Item: Open Button to choose folder domain
        comOpen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try{
                        selectFile();
                    }
                    catch (IOException ioe){}
                    readFile();
                }
            });

        // Sets up Menu Items for preferences: General, Stop words
        comRem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tabPANE.setSelectedIndex(1);
                }
            });
        comGeneral.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tabPANE.setSelectedIndex(1);
                }
            });

        // Sets up Menu ItemL: Empty all data in my.db
        comEmpty.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    DeleteApp del = new DeleteApp();
                    del.delete(FILEPATHoverall);

                    model1.clear();
                }
            });  

        // Sets up Menu Item: refresh
        comRefresh.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try{
                        sm.main(); 
                    }catch(IOException e){}
                }
            });   

        // Sets up Menu Item: Exit
        comExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    exit("Confirm","Do you really want to quit?");
                }
            });

        // Sets up Menu Item: About
        comAbout.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    displayAboutMessage();
                }
            });    

        // Adds all Menu Items to the menu
        menuFile.add(comOpen);
        menuFile.addSeparator();         
        menuFile.add(comExit);

        menuData.add(comEmpty);
        menuData.addSeparator();
        menuData.add(comRefresh);

        menuPrefs.add(comGeneral);
        menuPrefs.addSeparator();
        menuPrefs.add(comRem);

        menuHelp.addSeparator();                
        menuHelp.add(comAbout);

        menuBar.add(menuFile);
        menuBar.add(menuData);
        menuBar.add(menuPrefs);      
        menuBar.add(menuHelp);
    }
    
    public static void main(String args[]){
        new GUII();
    } 
}    


package com.mycompany.notepadapp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Main Graphical User Interface for the Notepad Application.
 * <p>
 * This class extends {@link JFrame} and implements {@link ActionListener} to provide
 * a functional desktop text editor. It incorporates a customizable text area, 
 * menu bar operations (File, Edit, Format, Help), and a real-time status bar.
 * </p>
 * 
 * @author Thapelo Mathebola
 * @version 1.0
 */
public class NotepadGUI extends JFrame implements ActionListener {

    // ==========================================
    // Core GUI Components & State Variables
    // ==========================================
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JLabel statusLabel;
    
    // File tracking for Open/Save operations
    private JFileChooser fileChooser;
    private File currentFile;

    // Menu Bar Components
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, formatMenu, helpMenu;
    private JMenuItem itemNew, itemOpen, itemSave, itemSaveAs, itemPrint, itemExit;
    private JMenuItem itemCut, itemCopy, itemPaste, itemSelectAll;
    private JMenuItem itemWordWrap, itemFont;

    /**
     * Default constructor to initialize and render the Notepad window frame.
     */
    public NotepadGUI() {
        // Window frame configuration
        setTitle("Notepad Application - Untitled");
        setSize(800, 600);
        setLocationRelativeTo(null); // Centers window on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize and layout components
        initComponents();
        
        // Render main application window
        setVisible(true);
    }

    /**
     * Instantiates GUI components, configures layout management, 
     * registers event listeners, and assembles the menu system.
     */
    private void initComponents() {
        // ------------------------------------------
        // 1. Text Area & Scroll Pane Setup
        // ------------------------------------------
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Standard monospace font for editors
        
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // ------------------------------------------
        // 2. Status Bar Setup & Document Tracking
        // ------------------------------------------
        statusLabel = new JLabel(" Line: 1 | Col: 1 | Words: 0 | Chars: 0 ");
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        add(statusLabel, BorderLayout.SOUTH);

        // Document Listener: Triggers status updates when text is inserted, removed, or modified
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateStatusBar(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateStatusBar(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateStatusBar(); }
        });

        // Caret Listener: Triggers status updates when typing or clicking across lines/columns
        textArea.addCaretListener(e -> updateStatusBar());

        // ------------------------------------------
        // 3. File Chooser & Menu Assembly
        // ------------------------------------------
        fileChooser = new JFileChooser();
        menuBar = new JMenuBar();

        // --- File Menu ---
        fileMenu = new JMenu("File");
        itemNew = new JMenuItem("New");
        itemOpen = new JMenuItem("Open...");
        itemSave = new JMenuItem("Save");
        itemSaveAs = new JMenuItem("Save As...");
        itemPrint = new JMenuItem("Print...");
        itemExit = new JMenuItem("Exit");

        // Register ActionListeners
        itemNew.addActionListener(this);
        itemOpen.addActionListener(this);
        itemSave.addActionListener(this);
        itemSaveAs.addActionListener(this);
        itemPrint.addActionListener(this);
        itemExit.addActionListener(this);

        fileMenu.add(itemNew);
        fileMenu.add(itemOpen);
        fileMenu.add(itemSave);
        fileMenu.add(itemSaveAs);
        fileMenu.addSeparator();
        fileMenu.add(itemPrint);
        fileMenu.addSeparator();
        fileMenu.add(itemExit);

        // --- Edit Menu ---
        editMenu = new JMenu("Edit");
        itemCut = new JMenuItem("Cut");
        itemCopy = new JMenuItem("Copy");
        itemPaste = new JMenuItem("Paste");
        itemSelectAll = new JMenuItem("Select All");

        itemCut.addActionListener(this);
        itemCopy.addActionListener(this);
        itemPaste.addActionListener(this);
        itemSelectAll.addActionListener(this);

        editMenu.add(itemCut);
        editMenu.add(itemCopy);
        editMenu.add(itemPaste);
        editMenu.addSeparator();
        editMenu.add(itemSelectAll);

        // --- Format Menu ---
        formatMenu = new JMenu("Format");
        itemWordWrap = new JMenuItem("Toggle Word Wrap");
        itemFont = new JMenuItem("Change Font Size...");

        itemWordWrap.addActionListener(this);
        itemFont.addActionListener(this);

        formatMenu.add(itemWordWrap);
        formatMenu.add(itemFont);

        // --- Help Menu ---
        helpMenu = new JMenu("Help");
        JMenuItem itemAbout = new JMenuItem("About Notepad");
        itemAbout.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Project 9: Notepad Application\nBuilt with Java Swing & Maven",
                "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(itemAbout);

        // Attach menus to main MenuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
        
        // Find where editMenu components are initialized and add shortcut key bindings:
        itemCut.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemSelectAll.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));

        // Add a "Clear All" option to editMenu
        JMenuItem itemClearAll = new JMenuItem("Clear All");
        itemClearAll.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to clear all text?", 
                    "Clear Text", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                textArea.setText("");
            }
        });
        editMenu.addSeparator();
        editMenu.add(itemClearAll);

        }

    /**
     * Calculates current caret line/column numbers alongside total word and character counts,
     * reflecting changes dynamically in the status bar label.
     */
        private void updateStatusBar() {
            try {
                int caretPos = textArea.getCaretPosition();
                int line = textArea.getLineOfOffset(caretPos) + 1;
                int col = caretPos - textArea.getLineStartOffset(line - 1) + 1;

                String text = textArea.getText();
                int totalChars = text.length();
                int charsNoSpaces = text.replaceAll("\\s+", "").length();
                int words = text.trim().isEmpty() ? 0 : text.trim().split("\\s+").length;

                statusLabel.setText(String.format(" Line: %d | Col: %d | Words: %d | Chars: %d (No Spaces: %d) ",
                    line, col, words, totalChars, charsNoSpaces));
        }   catch (Exception ex) {
            // Silence transient offset calculations during rapid edits
        }
        }

    // Add View Menu inside initComponents() of NotepadGUI.java:
        JMenu viewMenu = new JMenu("View");
        JCheckBoxMenuItem toggleStatus = new JCheckBoxMenuItem("Status Bar", true);
        toggleStatus.addActionListener(e -> statusLabel.setVisible(toggleStatus.isSelected()));

        viewMenu.add(toggleStatus);
    menuBar.add(viewMenu); // Place between formatMenu and helpMenu
    

    /**
     * Centralized event handling mechanism for menu action events.
     *
     * @param e The ActionEvent fired by interacting with menu items.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == itemNew) {
            newFile();
        } else if (source == itemOpen) {
            openFile();
        } else if (source == itemSave) {
            saveFile();
        } else if (source == itemSaveAs) {
            saveFileAs();
        } else if (source == itemPrint) {
            try {
                textArea.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Print Error: " + ex.getMessage());
            }
        } else if (source == itemExit) {
            System.exit(0);
        } else if (source == itemCut) {
            textArea.cut();
        } else if (source == itemCopy) {
            textArea.copy();
        } else if (source == itemPaste) {
            textArea.paste();
        } else if (source == itemSelectAll) {
            textArea.selectAll();
        } else if (source == itemWordWrap) {
            textArea.setLineWrap(!textArea.getLineWrap());
            textArea.setWrapStyleWord(true);
        } else if (source == itemFont) {
            changeFontSize();
        }
    }

    // ==========================================
    // File Management Logic
    // ==========================================

    /** Clears the text editor canvas and resets active file tracking. */
    /*
    private void newFile() {
        textArea.setText("");
        currentFile = null;
        setTitle("Notepad Application - Untitled");
    }*/
    private void newFile(){//begin method newFile
        //prompt user to save unsaved text before clearing
        if(!textArea.getText().trim().isEmpty()){                                   //begin if
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Do you want to save changes before creating a new document?",
                    "Unsaved Changes",JOptionPane.YES_NO_CANCEL_OPTION);
            
            if(confirm == JOptionPane.YES_OPTION){                                  //begin else if
                saveFile();                
            }else if(confirm == JOptionPane.CANCEL_OPTION){
                return;
            }//end else if
        }//end if
        textArea.setText("");
        currentFile = null;
        setTitle("Notepad Application - Untitled");
    }//end method newFile
    
    /** Opens a file dialog chooser and reads text content into the main editor. */

    private void openFile(){//begin end openFile method
        
        // Restrict file chooser to show only .txt files
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Documents (*.txt)", "txt"));
        
        int option = fileChooser.showOpenDialog(this);
        if (option ==JFileChooser.APPROVE_OPTION){                                              //begin if
            currentFile = fileChooser.getSelectedFile();
            try(BufferedReader reader = new BufferedReader(new FileReader(currentFile))){
                textArea.read(reader, null);
                setTitle("NotePad Application - "+ currentFile.getName());
            }catch(IOException ex){                                                             //begin error handling
                JOptionPane.showMessageDialog(this, "Error opening file"+ex.getMessage(),"File Error",JOptionPane.ERROR_MESSAGE);
            }//end error handling
           }//end if     
    }//end openFile() method

    /** Saves current text area content back to active file or delegates to Save As. */
    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                textArea.write(writer);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        }
    }

    /** Opens file dialog chooser to save document under a specified path/filename. */
    private void saveFileAs(){                                                      //begin method saveFileAs
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Documents (*.txt)","txt"));
        int option = fileChooser.showSaveDialog(this);
        if(option == JFileChooser.APPROVE_OPTION){                                 //begin if
            currentFile = fileChooser.getSelectedFile();
            
            //Append .txt extension if missing
            if(!currentFile.getName().toLowerCase().endsWith(".txt")){      //begin if   
                currentFile = new File(currentFile.getAbsolutePath()+ ".txt");
            }//end if
            saveFile();
            if(currentFile != null){                                               //begin if
               setTitle("Notepad Application - "+ currentFile.getName());
            }//end if
        }//end if        
    }//end method saveFileAs
    
    /** Prompts user with input dialog to adjust text area font point size. */
    private void changeFontSize() {
        String input = JOptionPane.showInputDialog(this, "Enter Font Size:", "Font Size", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.trim().isEmpty()) {
            try {
         float newSize = Float.parseFloat(input);
         textArea.setFont(textArea.getFont().deriveFont(newSize));
       } catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(this, "Invalid number entered.");
       }
        }
    }

    /**
     * Application entry point for standalone thread execution.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NotepadGUI());
    }
}
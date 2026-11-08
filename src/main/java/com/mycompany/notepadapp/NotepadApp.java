package com.mycompany.notepadapp;

import javax.swing.SwingUtilities;

/**
 * Main entry point for launching the Notepad Application.
 * 
 * @author Thapelo Mathebola
 */
public class NotepadApp {

    public static void main(String[] args) {
        // Ensures the Swing GUI components are constructed safely on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new NotepadGUI();
        });
    }
}
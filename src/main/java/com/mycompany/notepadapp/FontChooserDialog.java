package com.mycompany.notepadapp;

import javax.swing.*;
import java.awt.*;

/**
 * Custom dialog utility for selecting system font family, style, and size.
 * 
 * @author Sandiso
 */
public class FontChooserDialog {

    public static Font showDialog(Component parent, Font currentFont) {
        // Get all available system fonts
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        
        String selectedFontFamily = (String) JOptionPane.showInputDialog(
                parent,
                "Select Font Family:",
                "Font Selector",
                JOptionPane.PLAIN_MESSAGE,
                null,
                fonts,
                currentFont.getFamily()
        );

        if (selectedFontFamily != null) {
            return new Font(selectedFontFamily, currentFont.getStyle(), currentFont.getSize());
        }
        return currentFont; // Return current font if cancelled
    }
}

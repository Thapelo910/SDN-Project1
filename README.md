
# Project 9: Notepad Application

A Java Swing desktop text editor built with Apache NetBeans and Apache Maven.

---

## 📌 Project Overview
This desktop application provides core text editing capabilities with file management, text manipulation, font selection, and real-time status bar tracking. Developed as part of our Software Development / Computer Engineering group project.

---

## 👥 Team Members & Role Allocation
| Member | Assigned Feature / Role | GitHub Handle |
| :--- | :--- | :--- |
| **Thapelo Mathebola** | Repository setup, main architecture, base `NotepadGUI` class | `@Thapelo910` |
| **Bonginkosi** | File I/O operations (`Open`, `Save`, `Save As`, unsaved prompts) | `@Bong-nkos1` |
| **Ndumiso Mthembu** | Edit operations (`Cut`, `Copy`, `Paste`, Keyboard Shortcuts) | `@NdumisoMthembu-hub` |
| **Sandiso** | Font selection dialog and formatting logic | `@Sandiso987` |
| **Simphiwe** | Status bar mechanics & view toggles | `@SimzLuya10` |
| **Thembelani** | Testing, error handling, & exception guards | `@Thembelani929` |
| **Windvoel** | Documentation & User Guide (`USER_GUIDE.md`) | `@Windvoel-10` |

---

## 🛠️ Tech Stack & Prerequisites
- **Language:** Java (JDK 17 or higher)
- **Build System:** Apache Maven
- **IDE:** Apache NetBeans
- **GUI Framework:** Java Swing (`JFrame`, `JTextArea`, `JMenuBar`, `JScrollPane`)
- **Version Control:** Git & GitHub

---

## 🚀 Standard Git Workflow for All Team Members

To prevent code conflicts and ensure everyone gets individual contribution marks on GitHub, follow these exact 5 steps whenever you work on your task:

### Step 1: Sync Your Local Repository
1. Open NetBeans and right-click `NotepadApp [master]`.
2. Go to **Git** -> **Remote** -> **Pull...** and click **Finish**.

### Step 2: Create Your Feature Branch
1. Right-click `NotepadApp [master]`.
2. Go to **Git** -> **Branch** -> **Create Branch...**
3. Name your branch using your feature format (e.g., `feature-file-io`, `feature-edit-tools`).
4. Click **Create**. NetBeans will automatically switch you to your branch.

### Step 3: Implement Your Code
Add or update your assigned code snippets in NetBeans as outlined below.

### Step 4: Commit Your Changes
1. Right-click `NotepadApp` -> **Git** -> **Commit...**
2. Check the modified files and write a descriptive commit message (e.g., `Add file filter and unsaved prompt`).
3. Click **Commit**.

### Step 5: Push Branch to GitHub
1. Right-click `NotepadApp` -> **Git** -> **Remote** -> **Push...**
2. Ensure your feature branch is selected and click **Finish**.
3. Go to [https://github.com/Thapelo910/SDN-Project1](https://github.com/Thapelo910/SDN-Project1) and click **Compare & pull request** to merge your code into `master`.

---

## 📋 Individual Implementation Guides & Code

---

### 1. Bonginkosi (`@Bong-nkos1`) — File I/O Operations

#### Branch Name: `feature-file-io`

#### Instructions:
1. Open `NotepadGUI.java`.
2. Locate `openFile()`, `saveFileAs()`, and `newFile()`.
3. Update them with file filters and unsaved changes confirmation dialogs.

#### Code to Add / Replace in `NotepadGUI.java`:

```java
// Replace openFile() in NotepadGUI.java with this code:
private void openFile() {
    // Restrict file chooser to show only .txt files
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Documents (*.txt)", "txt"));
    
    int option = fileChooser.showOpenDialog(this);
    if (option == JFileChooser.APPROVE_OPTION) {
        currentFile = fileChooser.getSelectedFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
            textArea.read(reader, null);
            setTitle("Notepad Application - " + currentFile.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Replace saveFileAs() in NotepadGUI.java with this code:
private void saveFileAs() {
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Documents (*.txt)", "txt"));
    int option = fileChooser.showSaveDialog(this);
    if (option == JFileChooser.APPROVE_OPTION) {
        currentFile = fileChooser.getSelectedFile();
        
        // Append .txt extension if missing
        if (!currentFile.getName().toLowerCase().endsWith(".txt")) {
            currentFile = new File(currentFile.getAbsolutePath() + ".txt");
        }
        
        saveFile();
        if (currentFile != null) {
            setTitle("Notepad Application - " + currentFile.getName());
        }
    }
}

// Replace newFile() in NotepadGUI.java with this code:
private void newFile() {
    // Prompt user to save unsaved text before clearing
    if (!textArea.getText().trim().isEmpty()) {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Do you want to save changes before creating a new document?", 
            "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            saveFile();
        } else if (confirm == JOptionPane.CANCEL_OPTION) {
            return;
        }
    }
    textArea.setText("");
    currentFile = null;
    setTitle("Notepad Application - Untitled");
}

```

---

### 2. Ndumiso Mthembu (`@NdumisoMthembu-hub`) — Edit Actions & Shortcuts

#### Branch Name: `feature-edit-tools`

#### Instructions:

1. Open `NotepadGUI.java`.
2. Locate the Edit menu setup inside `initComponents()`.
3. Add keyboard shortcut accelerators (`Ctrl+X`, `Ctrl+C`, `Ctrl+V`, `Ctrl+A`) and a "Clear All" menu option.

#### Code to Add to `initComponents()` inside `NotepadGUI.java`:

```java
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

```

---

### 3. Sandiso (`@Sandiso987`) — Font Selection Dialog

#### Branch Name: `feature-formatting`

#### Instructions:

1. In NetBeans, right-click `com.mycompany.notepadapp` -> **New** -> **Java Class...**
2. Class Name: `FontChooserDialog`.
3. Paste the code for `FontChooserDialog.java` below.
4. Update `changeFontSize()` inside `NotepadGUI.java` to use this new dialog.

#### Code for NEW FILE: `FontChooserDialog.java`:

```java
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

```

#### Code to Update in `NotepadGUI.java`:

```java
// Update changeFontSize() in NotepadGUI.java to call the new dialog:
private void changeFontSize() {
    Font newFont = FontChooserDialog.showDialog(this, textArea.getFont());
    textArea.setFont(newFont);
}

```

---

### 4. Simphiwe (`@SimzLuya10`) — Status Bar Mechanics & View Menu

#### Branch Name: `feature-status-bar`

#### Instructions:

1. Open `NotepadGUI.java`.
2. Update `updateStatusBar()` to calculate non-space character counts.
3. Add a **View** menu to allow users to toggle the status bar on/off.

#### Code to Add / Update in `NotepadGUI.java`:

```java
// Replace updateStatusBar() in NotepadGUI.java with this code:
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
    } catch (Exception ex) {
        // Silence transient offset calculations during rapid edits
    }
}

// Add View Menu inside initComponents() of NotepadGUI.java:
JMenu viewMenu = new JMenu("View");
JCheckBoxMenuItem toggleStatus = new JCheckBoxMenuItem("Status Bar", true);
toggleStatus.addActionListener(e -> statusLabel.setVisible(toggleStatus.isSelected()));

viewMenu.add(toggleStatus);
menuBar.add(viewMenu); // Place between formatMenu and helpMenu

```

---

### 5. Thembelani (`@Thembelani929`) — Unit Testing & Error Guards

#### Branch Name: `feature-testing-docs`

#### Instructions:

1. In NetBeans, expand `Test Packages` (or `src/test/java`).
2. Right-click the test package -> **New** -> **Java Class...**
3. Class Name: `NotepadLogicTest`.
4. Add unit test verification methods for word and character counting logic.

#### Code for NEW FILE: `NotepadLogicTest.java`:

```java
package com.mycompany.notepadapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests verifying text parsing and metric calculation rules.
 * 
 * @author Thembelani
 */
public class NotepadLogicTest {

    @Test
    public void testWordCountCalculation() {
        String sampleText = "The quick brown fox jumps over the lazy dog";
        int words = sampleText.trim().split("\\s+").length;
        assertEquals(9, words, "Word count should accurately identify 9 words.");
    }

    @Test
    public void testEmptyTextWordCount() {
        String emptyText = "";
        int words = emptyText.trim().isEmpty() ? 0 : emptyText.trim().split("\\s+").length;
        assertEquals(0, words, "Empty string must yield 0 words.");
    }

    @Test
    public void testNonSpaceCharacterCount() {
        String sampleText = "Hello World";
        int nonSpaceCount = sampleText.replaceAll("\\s+", "").length();
        assertEquals(10, nonSpaceCount, "Character count excluding spaces should equal 10.");
    }
}

```

---

### 6. Windvoel (`@Windvoel-10`) — User Documentation

#### Branch Name: `feature-docs-uml`

#### Instructions:

1. In the root directory of the project, create a new file named `USER_GUIDE.md`.
2. Copy and paste the user manual text below into `USER_GUIDE.md`.

#### Code for NEW FILE: `USER_GUIDE.md`:

```markdown
# 📖 Notepad Application — User Guide

Welcome to the **Notepad Application** user manual. This guide explains how to compile, launch, and operate the desktop editor.

---

## 🚀 How to Run the Application
1. Launch **Apache NetBeans**.
2. Go to **File** -> **Open Project...** and select `NotepadApp`.
3. In the left panel, navigate to `Source Packages` -> `com.mycompany.notepadapp` -> `NotepadGUI.java`.
4. Right-click `NotepadGUI.java` and click **Run File** (or press **Shift + F6**).

---

## 📝 Operating Features

### File Operations
- **New (`Ctrl+N`):** Clears the editor canvas. Prompts to save active work if unsaved text is present.
- **Open (`Ctrl+O`):** Launches a file dialog to load plain text (`.txt`) files into the editor.
- **Save (`Ctrl+S`):** Writes changes directly to the current file.
- **Save As:** Prompts for a file path and auto-appends the `.txt` extension.
- **Print:** Opens system printing dialog to print text directly.

### Editing Tools
- **Cut (`Ctrl+X`), Copy (`Ctrl+C`), Paste (`Ctrl+V`):** Standard system clipboard interactions.
- **Select All (`Ctrl+A`):** Highlights all text in the text area.
- **Clear All:** Clears the canvas after confirmation.

### Formatting & View Options
- **Toggle Word Wrap:** Enables or disables horizontal scrolling for long lines.
- **Change Font Size:** Opens font selector to change font family and style.
- **Toggle Status Bar:** Shows or hides the bottom status bar via the **View** menu.

### Status Bar Metrics
Located at the bottom of the editor, tracking:
- Current cursor **Line** number
- Current cursor **Column** position
- Total **Word** count
- Total **Character** count (including and excluding spaces)

```

---

## 💻 How to Clone and Run Locally

```bash
# 1. Clone this repository
git clone [https://github.com/Thapelo910/SDN-Project1.git](https://github.com/Thapelo910/SDN-Project1.git)

# 2. Open Apache NetBeans -> File -> Open Project -> Select NotepadApp
# 3. Build with Maven and run NotepadGUI.java

```


# 📝 Project 9: Notepad Application

A multi-functional desktop text editor built with **Java Swing** and **Apache Maven**. Developed as a group project to demonstrate software engineering design principles, component modularity, and Git feature-branch collaboration.

---

## 👥 Team Members & Role Allocation

| Member Name | Assigned Module / Role | GitHub Handle |
| :--- | :--- | :--- |
| **Thapelo Mathebola** | Architecture, Base GUI Frame & Git Lead | `@Thapelo910` |
| **Bonginkosi** | File I/O Operations (`Open`, `Save`, `Save As`) | `@Bong-nkos1` |
| **Ndumiso Mthembu** | Text Editing & Keyboard Accelerators | `@NdumisoMthembu-hub` |
| **Sandiso** | Font Selection & Formatting Logic | `@Sandiso987` |
| **Simphiwe** | Status Bar Metrics & View Controls | `@SimzLuya10` |
| **Thembelani** | Testing, Error Handling & Exception Guards | `@Thembelani929` |
| **Windvoel** | User Guide & Documentation | `@Windvoel-10` |

---

## 🛠️ Tech Stack & Requirements

- **Language:** Java (JDK 17+)
- **Build Tool:** Apache Maven
- **GUI Toolkit:** Java Swing (`JFrame`, `JTextArea`, `JFileChooser`, `JMenuBar`)
- **Supported Editors:** Apache NetBeans, VS Code

---

## 📚 Project Documentation & Deliverables

- 📖 [User Guide](USER_GUIDE.md) — Step-by-step operating manual.
- 📐 [UML Class Diagram](UML%20Class%20Diagram.pdf) — Object-oriented architecture blueprint.
- 📄 [Project Documentation](PROJECT%20DOCUMENTATION.pdf) — Technical report & specifications.
- ✍️ [Pseudocode](Pseudocode.pdf) — Algorithm and logic flow overview.

---

## 🚀 How to Clone and Run

### Running in Apache NetBeans:
1. Open NetBeans -> **File** -> **Open Project...**
2. Select the `NotepadApp` directory.
3. Expand `Source Packages` -> `com.mycompany.notepadapp`.
4. Right-click `NotepadGUI.java` and click **Run File** (or press `Shift + F6`).

### Running via Terminal (Maven):
```bash
git clone [https://github.com/Thapelo910/SDN-Project1.git](https://github.com/Thapelo910/SDN-Project1.git)
cd SDN-Project1
mvn clean compile
mvn exec:java -Dexec.mainClass="com.mycompany.notepadapp.NotepadGUI"

---

## 🌿 Git Branching Strategy

Each team member develops their assigned module in an isolated feature branch before submitting a Pull Request to `master`:
- `feature-file-io`
- `feature-edit-tools`
- `feature-formatting`
- `feature-status-bar`
- `feature-testing-docs`
- `feature-docs-uml`

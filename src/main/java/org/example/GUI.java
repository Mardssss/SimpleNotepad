package org.example;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;

public class GUI implements ActionListener {
    // Text area
    JFrame window, prefWindow;
    JTextPane textPane;
    // Top menu bar
    JScrollPane scrollPane;
    JMenuBar menuBar;
    // File menu
    JMenu fileMenu, editMenu, formatMenu, styleMenu;
    JMenuItem newItem, openItem, saveItem, saveAsItem, preferencesItem, exitItem;
    // Format menu
    JMenu fontFamilyItem, fontSizeItem;
    String appName = "Notepad";

    JMenuItem boldItem, italicItem;
    Functions file = new Functions(this);

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        createWindow();
        createTextPane();
        createMenuBar();
        createFileMenu();
        createStyleMenu(); // Create a style menu
        window.setVisible(true);
    }

    public void createWindow() {
        window = new JFrame("New Document - " + appName);
        window.setSize(600, 800);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createPrefWindow() {
        prefWindow = new JFrame("Preferences");
        prefWindow.setSize(350, 500);
        prefWindow.setLocationRelativeTo(null);
        prefWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Create a JPanel to hold the preferences components
        JPanel preferencesPanel = new JPanel();
        preferencesPanel.setLayout(new GridLayout(4, 2)); // Example: 4 rows and 2 columns

        // Add preferences components to the panel
        JLabel themeLabel = new JLabel("Theme:");
        JComboBox<String> themeComboBox = new JComboBox<>(new String[]{"Light", "Dark"});
        themeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTheme = (String) themeComboBox.getSelectedItem();
                if ("Light".equals(selectedTheme)) {
                    setLightTheme();
                } else if ("Dark".equals(selectedTheme)) {
                    setDarkTheme();
                }
            }
        });

        JLabel fontFamilyLabel = new JLabel("Font Family:");
        JComboBox<String> fontFamilyComboBox = new JComboBox<>(getAvailableFontFamilyNames());
        fontFamilyComboBox.addActionListener(e -> {
            String selectedFontFamily = (String) fontFamilyComboBox.getSelectedItem();
            Font currentFont = textPane.getFont();
            textPane.setFont(new Font(selectedFontFamily, currentFont.getStyle(), currentFont.getSize()));
        });

        JLabel fontSizeLabel = new JLabel("Font Size:");
        String[] fontSizeOptions = {"8", "10", "12", "14", "16", "18", "20", "24", "28", "32"};
        JComboBox<String> fontSizeComboBox = new JComboBox<>(fontSizeOptions);
        fontSizeComboBox.addActionListener(e -> {
            String selectedFontSize = (String) fontSizeComboBox.getSelectedItem();
            int fontSize = Integer.parseInt(selectedFontSize);
            Font currentFont = textPane.getFont();
            textPane.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), fontSize));
        });

        JCheckBox spellCheckCheckBox = new JCheckBox("Enable Spell Check");

        // Add components to the panel
        preferencesPanel.add(themeLabel);
        preferencesPanel.add(themeComboBox);

        preferencesPanel.add(fontFamilyLabel);
        preferencesPanel.add(fontFamilyComboBox);

        preferencesPanel.add(fontSizeLabel);
        preferencesPanel.add(fontSizeComboBox);

        preferencesPanel.add(spellCheckCheckBox);

        // Add the preferences panel to the JFrame
        prefWindow.add(preferencesPanel);

        // Center the JFrame on the monitor
        prefWindow.setLocationRelativeTo(null);

        // Make the JFrame visible
        prefWindow.setVisible(true);
    }

    public void createTextPane() {
        textPane = new JTextPane();
        scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        window.add(scrollPane);
    }

    public void createMenuBar() {
        menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        styleMenu = new JMenu("Style"); // Style menu
        menuBar.add(styleMenu); // Add the style menu to the menu bar
    }

    public void createFileMenu() {
        newItem = new JMenuItem("New");
        newItem.addActionListener(this);
        newItem.setActionCommand("new");
        fileMenu.add(newItem);

        openItem = new JMenuItem("Open");
        openItem.addActionListener(this);
        openItem.setActionCommand("open");
        fileMenu.add(openItem);

        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this);
        saveItem.setActionCommand("save");
        fileMenu.add(saveItem);

        saveAsItem = new JMenuItem("Save As");
        saveAsItem.addActionListener(this);
        saveAsItem.setActionCommand("save as");
        fileMenu.add(saveAsItem);

        preferencesItem = new JMenuItem("Preferences");
        preferencesItem.addActionListener(this);
        preferencesItem.setActionCommand("pref");
        fileMenu.add(preferencesItem);

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);
        exitItem.setActionCommand("exit");
        fileMenu.add(exitItem);
    }

    public void createFormatMenu() {
        fontFamilyItem = new JMenu("Font Family");
        formatMenu.add(fontFamilyItem);
        // Define a list of font family names (you can customize this list)
        String[] fontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (String fontFamily : fontFamilyNames) {
            JMenuItem fontFamilyMenuItem = new JMenuItem(fontFamily);
            fontFamilyMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Get the selected font family
                    String selectedFontFamily = fontFamilyMenuItem.getText();

                    // Set the selected font family in the text area
                    Font currentFont = textPane.getFont();
                    textPane.setFont(new Font(selectedFontFamily, currentFont.getStyle(), currentFont.getSize()));
                }
            });
            fontFamilyItem.add(fontFamilyMenuItem);
        }
    }
    private String[] getAvailableFontFamilyNames() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }
    public void createStyleMenu() {
        boldItem = new JMenuItem("Bold");
        boldItem.addActionListener(this);
        boldItem.setActionCommand("bold");
        styleMenu.add(boldItem);

        JMenuItem italicItem = new JMenuItem("Italic");
        italicItem.addActionListener(this);
        italicItem.setActionCommand("italic");
        styleMenu.add(italicItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "new":
                file.newFile();
                break;
            case "open":
                file.openFile();
                break;
            case "save":
                file.save();
                break;
            case "save as":
                file.saveAs();
                break;
            case "exit":
                file.exit();
                break;
            case "pref":
                createPrefWindow();
            case "bold":
                applyStyleToSelectedText(TextAttribute.WEIGHT_BOLD);
                break;
            case "italic":
                applyStyleToSelectedText(TextAttribute.POSTURE_OBLIQUE);
                break;
        }
    }
    private void applyStyleToSelectedText(Object style) {
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start != end) {
            StyledDocument doc = textPane.getStyledDocument();
            MutableAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setBold(attr, style == TextAttribute.WEIGHT_BOLD);
            StyleConstants.setItalic(attr, style == TextAttribute.POSTURE_OBLIQUE);
            doc.setCharacterAttributes(start, end - start, attr, false);
        }
    }
    private void setLightTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(window);
            SwingUtilities.updateComponentTreeUI(prefWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDarkTheme() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();

            // Set the background color to a dark color (e.g., black)
            defaults.put("nimbusBase", new Color(0, 0, 0));

            // Set the text color to a light color (e.g., white)
            defaults.put("nimbusText", new Color(255, 255, 255));

            SwingUtilities.updateComponentTreeUI(window);
            SwingUtilities.updateComponentTreeUI(prefWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
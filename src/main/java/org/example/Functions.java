package org.example;


import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Functions {
    String fileName;
    String fileAddress;
    GUI gui;

    public Functions(GUI gui) {
        this.gui = gui;
    }

    public void newFile() {
        gui.textPane.setText("");
        gui.window.setTitle("New Document - "+gui.appName);
        fileName = null;
        fileAddress = null;
    }
    public void openFile(){
        FileDialog fd = new FileDialog(gui.window,"Open",FileDialog.LOAD);
        fd.setVisible(true);
        if (fd.getFile()!=null){
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            gui.window.setTitle(fileName+" - "+gui.appName);
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName));
            gui.textPane.setText("");
            String line = null;
            while ((line = br.readLine())!=null){
                StyledDocument doc = gui.textPane.getStyledDocument();
                doc.insertString(doc.getLength(), line + "\n", null);
            }
            br.close();
        } catch (Exception e){
            System.out.println("File Not Opened");
        }
    }
    public void save(){
        if (fileName==null){
            saveAs();
        } else {
            try {
                FileWriter fw = new FileWriter(fileAddress + fileName);
                fw.write(gui.textPane.getText());
                gui.window.setTitle(fileName + " - " + gui.appName);
                fw.close();
            }catch (Exception e){
                System.out.println("something went wrong");
            }
        }
    }
    public void saveAs(){
        FileDialog fd = new FileDialog(gui.window,"Save us",FileDialog.SAVE);
        fd.setVisible(true);
        if (fd.getFile()!=null){
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            gui.window.setTitle(fileName+" - "+gui.appName);
        }
        try {
            FileWriter fw = new FileWriter(fileAddress + fileName);
            fw.write(gui.textPane.getText());
            fw.close();
        }catch (Exception e){
            System.out.println("Could not save the file");
        }
    }
    public void exit(){
        System.exit(0);
    }
}
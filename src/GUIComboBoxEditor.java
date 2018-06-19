/**
 * GUIComboBoxEditor.java
 * Assignment: Final Project - Studiam
 * Purpose:Studiam would be a tool used to help memorize specific vocabulary for classes and
 * practice for tests, particularly for learning complex languages like Latin. Users would
 * be able to type up or import vocabulary lists for classes into the software and practice
 * with the material. The software would randomly generate mock-up practice tests with all
 * of the content, optionally timed, and we give immediate feedback. It would also have the
 * tools to make the test as “smart” as possible, i.e. accepting multiple potential answers,
 * common spelling mistakes, punctuation and capitalization accepted. The focus would also
 * be on user-friendliness, and much of the development would be focused on making an intuitive,
 * aesthetically pleasing user interface that could be piloted well even without much experience.
 *
 * @version 06/19/18
 */

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUIComboBoxEditor implements ComboBoxEditor {

    JTextArea textArea;

    public GUIComboBoxEditor() {
        textArea = new JTextArea();
        textArea.setSelectionColor(Main.LESS_PURPLE);
        textArea.setBorder(BorderFactory.createLoweredBevelBorder());
    }


    @Override //method to return the editor component
    public Component getEditorComponent() {
        return textArea;
    }


    @Override //method to set the displayed item of the combobox
    public void setItem(Object anObject) {
        if (anObject instanceof String) {
            textArea.setText((String) anObject);
        }

    }


    @Override //method to return the item being displayed by the combobox
    public Object getItem() {
        return textArea.getText();
    }


    @Override //method to select all text
    public void selectAll() {
        textArea.selectAll();
    }


    @Override //lol nope
    public void addActionListener(ActionListener l) {
    }


    @Override //lol nope
    public void removeActionListener(ActionListener l) {
    }
}

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

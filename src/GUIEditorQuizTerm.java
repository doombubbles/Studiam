import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class GUIEditorQuizTerm extends JComboBox {

    private final String NEW = "[New]";

    private int index;
    private DefaultComboBoxModel model;


    public GUIEditorQuizTerm(QuizTerm term) {
        setAlignmentX(LEFT_ALIGNMENT);
        List<String> terms = term.getAlternates();
        terms.add(0, term.toString());
        terms.add(NEW);
        model = new DefaultComboBoxModel<>(terms.toArray());
        setModel(model);
        setEditable(true);
        setPreferredSize(new Dimension(75, 20));
        setMaximumSize(new Dimension(150, 20));
        setEditor(new GUIComboBoxEditor());
        JTextArea textArea = (JTextArea)getEditor().getEditorComponent();


        addActionListener(e -> {
            if (e.getActionCommand().equals("comboBoxChanged") && model.getSelectedItem().equals(NEW)) {
                model.insertElementAt("[term]", model.getSize() - 1);
                setSelectedIndex(model.getSize() - 2);
            }
            if (e.getActionCommand().equals("comboBoxEdited")) {
                String newTerm = (String) model.getSelectedItem();
                model.removeElementAt(index);
                if (!newTerm.isEmpty()) {
                    model.insertElementAt(model.getSelectedItem(), index);
                }
                setSelectedIndex(Math.min(index, model.getSize() - 2));
            }

            index = getSelectedIndex() > -1 ? getSelectedIndex() : index;
        });


    }

    public QuizTerm getQuizTerm() {
        QuizTerm quizTerm = new QuizTerm((String) model.getSelectedItem());
        for (int i = 0; i < model.getSize() - 1; i++) {
            if (i != getSelectedIndex()) {
                quizTerm.addAlternate((String) model.getElementAt(i));
            }
        }
        return quizTerm;
    }
}

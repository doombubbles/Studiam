import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GUIEditorQuizTerm extends JComboBox {

    private final String NEW = "[New]";

    private int index;
    private DefaultComboBoxModel model;
    private boolean deleted = false;


    public GUIEditorQuizTerm(QuizTerm term) {
        setAlignmentX(LEFT_ALIGNMENT);
        List<String> terms = term.getAlternates();
        terms.add(0, term.toString());
        terms.add(NEW);
        model = new DefaultComboBoxModel<>(terms.toArray());
        setModel(model);
        setMaximumSize(new Dimension(300, 20));
        setEditable(true);
        setEditor(new GUIComboBoxEditor());
        JTextArea textArea = (JTextArea)getEditor().getEditorComponent();
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (model.getSize() == 2 && textArea.getText().isEmpty()) {
                        delete();
                    }
                    e.consume();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        addActionListener(e -> {
            if (e.getActionCommand().equals("comboBoxChanged") && model.getSelectedItem().equals(NEW)) {
                model.insertElementAt("[term]", model.getSize() - 1);
                setSelectedIndex(model.getSize() - 2);
            }
            if (e.getActionCommand().equals("comboBoxEdited")) {
                updateTerm();
            }

            index = getSelectedIndex() > -1 ? getSelectedIndex() : index;
        });
    }

    public void updateTerm() {
        String newTerm = (String) model.getSelectedItem();
        model.removeElementAt(index);
        if (!newTerm.isEmpty()) {
            model.insertElementAt(newTerm, index);
        } else if (model.getSize() < 2 && !deleted) {
            delete();
            return;
        }
        setSelectedIndex(Math.min(index, model.getSize() - 2));
        revalidate();
        repaint();
    }

    public void delete() {
        deleted = true;
        Container parent = getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
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

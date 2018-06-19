/**
 * GUIEdtiroQuizTerm.java
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
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GUIEditorQuizTerm extends JComboBox {

    private final String NEW = "[New]";
    private final String DELETE = "[Delete]";

    private int index;
    private DefaultComboBoxModel model;
    private boolean deleted = false;


    public GUIEditorQuizTerm(QuizTerm term) {
        setAlignmentX(LEFT_ALIGNMENT);
        setForeground(Color.BLACK);
        addKeyListener(Main.mainKeyListener());
        List<String> terms = term.getAlternates();
        terms.add(0, term.toString());
        terms.add(NEW);
        terms.add(DELETE);
        model = new DefaultComboBoxModel<>(terms.toArray());
        setModel(model);
        setMaximumSize(new Dimension(300, 20));
        setEditable(true);
        setEditor(new GUIComboBoxEditor());
        JTextArea textArea = (JTextArea)getEditor().getEditorComponent();
        textArea.setForeground(Color.BLACK);
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                //delete this term if the last potential alternative is deleted
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (model.getSize() == 3 && textArea.getText().isEmpty()) {
                        delete();
                    } else {
                        Main.getMainFrame().requestFocus();
                    }
                    updateParentElement(getParent());
                    updateParentElement(getParent());

                    e.consume();
                }
                //unfocus
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Main.getMainFrame().requestFocus();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        addActionListener(e -> {
            if (e.getActionCommand().equals("comboBoxChanged")
                    && model.getSelectedItem().equals(NEW)) {
                model.insertElementAt("[term]", model.getSize() - 2);
                setSelectedIndex(model.getSize() - 3);
            }
            if (e.getActionCommand().equals("comboBoxChanged")
                    && model.getSelectedItem().equals(DELETE)) {
                delete();
            }
            if (e.getActionCommand().equals("comboBoxEdited")) {
                updateTerm();
                updateParentElement(getParent());
            }

            index = getSelectedIndex() > -1 ? getSelectedIndex() : index;

            updateParentElement(getParent());
        });
    }

    //update the entry in the combobox to reflect what's typed in the text area
    public void updateTerm() {
        String newTerm = (String) model.getSelectedItem();
        model.removeElementAt(index);
        if (!newTerm.isEmpty()) {
            model.insertElementAt(newTerm, index);
        } else if (model.getSize() < 3 && !deleted) {
            delete();
            return;
        }
        setSelectedIndex(Math.min(index, model.getSize() - 3));
        revalidate();
        repaint();
    }

    //method to update the visuals of the GUIEditorQuizElement this term is housed in
    public void updateParentElement(Container parent) {
        if (parent instanceof GUIEditorQuizElement) {
            GUIEditorQuizElement element = (GUIEditorQuizElement) parent;
            element.updateVisuals();
        }
    }

    //bye bye
    public void delete() {
        deleted = true;
        Container parent = getParent();
        parent.remove(this);
        updateParentElement(parent);
    }

    //method to get the baseline QuizTerm from this GUIEditorQuizTerm
    public QuizTerm getQuizTerm() {
        QuizTerm quizTerm = new QuizTerm((String) model.getSelectedItem());
        for (int i = 0; i < model.getSize() - 2; i++) {
            if (i != getSelectedIndex()) {
                quizTerm.addAlternate((String) model.getElementAt(i));
            }
        }
        return quizTerm;
    }
}

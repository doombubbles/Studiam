/**
 * GUIEdtiroQuizSection.java
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GUIEditorQuizSection extends JPanel {

    private JTextField sectionNameArea;

    public GUIEditorQuizSection(QuizSection section) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(200, 200, 200, 100));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 0, 0, 1000), //outside
                BorderFactory.createLineBorder(Color.BLACK)),
                BorderFactory.createEmptyBorder(-13,5,5,5))); //inside


        JPanel sectionNamePanel = StudiamFactory.newTransparentPanel(new BorderLayout());
        sectionNameArea = StudiamFactory.newStudiamTextField(section.getName(), 20,
                BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), //outside
                        BorderFactory.createEmptyBorder(-2, 0,-2, 0))); //inside
        sectionNameArea.addKeyListener(Main.textFieldKeyListener());
        sectionNamePanel.add(sectionNameArea, BorderLayout.WEST);


        sectionNamePanel.add(deleteButton(), BorderLayout.EAST);


        add(sectionNamePanel);
        setAlignmentX(LEFT_ALIGNMENT);
        for (QuizElement quizElement : section) {
            add(new GUIEditorQuizElement(quizElement));
        }

        add(newButton());
    }

    //method to return the name of this section
    public String getSectionName() {
        return sectionNameArea.getText();
    }

    //method for the button to add a new term to this element
    private JButton newButton() {
        JButton newButton = new JButton();
        newButton.setText("New");
        newButton.setForeground(Color.BLACK);
        newButton.setPreferredSize(new Dimension(100, 25));
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizElement element1 = new QuizElement("[term]");
                GUIEditorQuizElement newElement = new GUIEditorQuizElement(element1);
                add(newElement, getComponentCount() - 1);
                updateVisuals();
            }
        });
        return newButton;
    }

    //method for the button to delete this quiz element
    private JButton deleteButton() {
        JButton deleteButton = new JButton();
        deleteButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        deleteButton.setText("X");
        deleteButton.setMargin(new Insets(0, 0, 0, 0));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 20));
        deleteButton.setPreferredSize(new Dimension(25, 25));
        deleteButton.setMaximumSize(new Dimension(25, 25));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFocusable(false);
        return deleteButton;
    }

    //self-explanatory
    public void updateVisuals() {
        revalidate();
        repaint();
    }

    //method to delete this quiz section and keep things looking right
    public void delete() {
        Container parent = getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

    //method to get all the specific GUIEditorQuizElement components from this element
    public List<GUIEditorQuizElement> getGUIQuizElements() {
        List<GUIEditorQuizElement> list = new ArrayList<>();
        for (Component c : getComponents()) {
            if (c instanceof GUIEditorQuizElement) {
                list.add((GUIEditorQuizElement) c);
            }
        }
        return list;
    }

    //method to get the base QuizSection from this GUIEditorQuizSection
    public QuizSection getQuizSectiom() {
        QuizSection section = new QuizSection(sectionNameArea.getText());
        for (GUIEditorQuizElement quizElement : getGUIQuizElements()) {
            section.add(quizElement.getQuizElement());
        }
        return section;
    }


    @Override //stupid method to avoid transparent panel painting artifacts
    protected void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}

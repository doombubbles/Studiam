/**
 * GUIQuizElement.java
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

public class GUIQuizElement extends JPanel {

    public GUIQuizElement(QuizElement element, int percent, int maxRemoved, QuizScreen screen) {
        addKeyListener(Main.mainKeyListener());
        setOpaque(false);

        setBackground(new Color(200, 200, 200, 200));
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 0, 5, 0),
                BorderFactory.createLineBorder(Main.PURPLE)));
        int totalWidthCalculation = 30;
        int removed = 0;
        for (QuizTerm term : element) {
            if ((Math.random() * 100) < percent && removed < maxRemoved) {
                JTextField field = StudiamFactory.newStudiamTextField("", 15,
                        BorderFactory.createLineBorder(Color.BLACK));
                field.addKeyListener(Main.mainKeyListener());
                field.setPreferredSize(new Dimension(term.toString().length() * 15, 20));
                add(field);
                screen.getQuizzedTerms().put(field, term);
                screen.totalTermsPlusPlus();
                removed++;
            } else {
                JLabel label = StudiamFactory.newStudiamLabel(term.toString(), 15,
                        BorderFactory.createLineBorder(Color.BLACK));
                label.addKeyListener(Main.mainKeyListener());
                add(label);
            }
            totalWidthCalculation += term.toString().length() * 15;
        }
        setPreferredSize(new Dimension(totalWidthCalculation, 40));
        setMaximumSize(new Dimension(totalWidthCalculation, 40));
        setMinimumSize(new Dimension(totalWidthCalculation, 40));

    }


    @Override //stupid method to avoid transparent panel painting artifacts
    protected void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}

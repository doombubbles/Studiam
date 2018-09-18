/**
 * GUIQuizSection.java
 *
 * The graphical component of a {@link QuizSection} within the {@link QuizScreen}
 *
 * @version 06/19/18
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUIQuizSection extends JPanel {

    public GUIQuizSection(QuizSection section, int percent, int maxRemoved, QuizScreen screen) {
        addKeyListener(Main.mainKeyListener());
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(200, 200, 200, 100));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                section.getName(), 0, 0, new Font("Times New Roman", Font.BOLD, 20)));
        /*
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 0, 0, 1000), //outside
                BorderFactory.createTitledBorder(section.getName())),
                BorderFactory.createEmptyBorder(-13,5,5,5))); //inside
        */


        setAlignmentX(LEFT_ALIGNMENT);
        for (QuizElement quizElement : section) {
            add(new GUIQuizElement(quizElement, percent, maxRemoved, screen));
        }
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

    @Override //stupid method to avoid transparent panel painting artifacts
    protected void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}

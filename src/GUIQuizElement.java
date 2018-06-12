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
                JTextField field = StudiamFactory.newStudiamTextField("", 15, BorderFactory.createLineBorder(Color.BLACK));
                field.addKeyListener(Main.mainKeyListener());
                field.setPreferredSize(new Dimension(term.toString().length() * 15, 20));
                add(field);
                screen.getQuizzedTerms().put(field, term);
                screen.totalTermsPlusPlus();
                removed++;
            } else {
                JLabel label = StudiamFactory.newStudiamLabel(term.toString(), 15, BorderFactory.createLineBorder(Color.BLACK));
                label.addKeyListener(Main.mainKeyListener());
                add(label);
            }
            totalWidthCalculation += term.toString().length() * 15;
        }
        setPreferredSize(new Dimension(totalWidthCalculation, 40));
        setMaximumSize(new Dimension(totalWidthCalculation, 40));
        setMinimumSize(new Dimension(totalWidthCalculation, 40));

    }


    @Override //method to stop dumb visual glitch
    protected void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}

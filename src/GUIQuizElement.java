import javax.swing.*;
import java.awt.*;

public class GUIQuizElement extends JComponent {

    public GUIQuizElement(QuizElement element) {
        addKeyListener(Main.mainKeyListener());
        setOpaque(false);

        setBackground(new Color(200, 200, 200, 200));
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 0, 5, 0),
                BorderFactory.createLineBorder(Main.PURPLE)));

        setPreferredSize(new Dimension(100, 40));

        for (QuizTerm term : element) {

        }


    }




    @Override //method to stop dumb visual glitch
    protected void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}

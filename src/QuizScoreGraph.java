import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuizScoreGraph extends JPanel {

    public static final int SIZE = 150;
    public static final int MARGIN = 25;

    List<QuizScore> scores;

    public QuizScoreGraph(List<QuizScore> scores) {
        setPreferredSize(new Dimension(150, 150));
        setMaximumSize(new Dimension(150, 150));
        setSize(new Dimension(150, 150));

        setAlignmentX(LEFT_ALIGNMENT);

        this.scores = scores;
        repaint();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,SIZE,SIZE);
        g.setColor(Color.BLACK);
        g.drawLine(MARGIN, 0, MARGIN, SIZE - MARGIN);
        g.drawLine(25, SIZE - MARGIN, SIZE, SIZE - MARGIN);

        int width = (SIZE - MARGIN) / scores.size();
        int xPoint = MARGIN;
        int lastX = -1;
        int lastY = -1;

        for (int i = 0; i < scores.size(); i++) {
            QuizScore score = scores.get(i);
            double scorePercent = score.getCorrect() / (1.0 * score.getTotal());
            Color color = Color.RED;

            int yPoint = (int) (SIZE - (scorePercent * SIZE));
            if (scorePercent >= .9) {
                color = Color.GREEN;
            } else if (scorePercent > .8) {
                color = Color.BLUE;
            } else if (scorePercent > .7) {
                color = color.YELLOW;
            } else if (scorePercent > .6) {
                color = Color.ORANGE;
            }
            g.setColor(color);

            g.fillOval(xPoint - 5, yPoint - 5, 10, 10);
            if (lastX != -1 && lastY != -1) {
                g.setColor(Color.BLACK);
                g.drawLine(lastX, lastY, xPoint, yPoint);
            }
            lastX = xPoint;
            lastY = yPoint;
            xPoint += width;
        }

    }
}

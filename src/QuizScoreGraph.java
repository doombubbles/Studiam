/**
 * QuizScoreGraph.java
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
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class QuizScoreGraph extends JPanel {

    public static final int SIZE = 150;
    public static final int MARGIN = 25;
    public static final int SCALING = 50;

    List<QuizScore> scores;

    public QuizScoreGraph(List<QuizScore> scores) {
        if (scores.isEmpty()) {
            throw new NullPointerException("Wait, how did this happen? We're smarter than this!");
        }
        setPreferredSize(new Dimension((scores.size()) * SCALING, SIZE));
        setMaximumSize(new Dimension((scores.size()) * SCALING, SIZE));
        setSize(new Dimension((scores.size()) * SCALING, SIZE));

        setAlignmentX(LEFT_ALIGNMENT);

        this.scores = scores;
        repaint();
    }

    public void setScores(List<QuizScore> scores) {
        this.scores = scores;
    }

    public void update() {
        setPreferredSize(new Dimension((scores.size()) * SCALING, SIZE));
        setMaximumSize(new Dimension((scores.size()) * SCALING, SIZE));
        setSize(new Dimension((scores.size()) * SCALING, SIZE));
        revalidate();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,(scores.size()) * SCALING,SIZE);
        g.setColor(Color.BLACK);
        g.drawLine(MARGIN, 0, MARGIN, SIZE - MARGIN);
        g.drawLine(MARGIN, SIZE - MARGIN, (scores.size()) * SCALING, SIZE - MARGIN);
        int width = MARGIN * 2;
        int xPoint = MARGIN;
        int lastX = -1;
        int lastY = -1;

        for (int i = 0; i < scores.size(); i++) {
            QuizScore score = scores.get(i);
            double scorePercent = score.getCorrect() / (1.0 * score.getTotal());
            Color color = Color.RED;

            int yPoint = (int) (SIZE - MARGIN - (scorePercent * (SIZE * .8)));
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
            g.setColor(Color.BLACK);
            String s = (scorePercent * 100 + "");
            g.drawString(s.substring(0, s.indexOf(".")) + "%", xPoint - 5, yPoint + MARGIN / 2);
            if (lastX != -1 && lastY != -1) {
                g.drawLine(lastX, lastY, xPoint, yPoint);
            }
            lastX = xPoint;
            lastY = yPoint;
            xPoint += width;
        }

    }
}

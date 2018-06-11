import javax.swing.*;
import java.awt.*;

public class QuizScreen extends Screen {

    public QuizScreen(Quiz quiz) {
        screenId = "Quiz_" + quiz.name;
        setLayout(new BorderLayout());


        JPanel topPanel = StudiamFactory.newTransparentPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 200, 200, 100));
        add(topPanel, BorderLayout.NORTH);

        JPanel middlePanel = StudiamFactory.newTransparentPanel(new BorderLayout());
        add(middlePanel, BorderLayout.CENTER);

        JLabel title = StudiamFactory.newStudiamLabel(quiz.name, 30); //inside
        topPanel.add(title, BorderLayout.WEST);

        JLabel desc = StudiamFactory.newStudiamLabel(quiz.description, 15);
        topPanel.add(desc, BorderLayout.SOUTH);


        JViewport viewport = new JViewport() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        viewport.setOpaque(false);
        viewport.setBackground(Main.CLEAR);


        JPanel viewPanel = StudiamFactory.newTransparentPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.add(new JLabel("hi"));
        viewport.add(viewPanel);

        middlePanel.add(scrollPane(viewport));



    }

    private JScrollPane scrollPane(JViewport viewPort) {
        JScrollPane scrollPane = new JScrollPane(viewPort) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBackground(Main.CLEAR);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
}

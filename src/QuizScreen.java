import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class QuizScreen extends Screen {

    public QuizScreen(Quiz quiz) {
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

        topPanel.add(turnInButton(), BorderLayout.EAST);


        topPanel.add(timer());


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

        for (IQuizEntry element : quiz) {
            if (element instanceof QuizSection) {
                QuizSection section = (QuizSection) element;
                JPanel sectionPanel = new GUIQuizSection(section, quiz.percent, quiz.maxRemoved);
                viewPanel.add(sectionPanel);

            } else if (element instanceof QuizElement) {
                QuizElement quizElement = (QuizElement) element;
                viewPanel.add(new GUIQuizElement(quizElement, quiz.percent, quiz.maxRemoved));
            }

        }


        viewport.add(viewPanel);

        middlePanel.add(scrollPane(viewport));



    }

    private JLabel timer() {
        JLabel timer = StudiamFactory.newStudiamLabel("0:00", 30);
        timer.setHorizontalAlignment(SwingConstants.CENTER);
        Timer realTimer = new Timer();
        TimerTask update = new TimerTask() {
            @Override
            public void run() {
                String[] strings = timer.getText().split(":");
                int[] numbers = new int[2];
                numbers[0] = Integer.parseInt(strings[0]);
                numbers[1] = Integer.parseInt(strings[1]);
                if (numbers[1] == 59) {
                    numbers[1] = 0;
                    numbers[0]++;
                } else numbers[1]++;
                String text = ":";
                if (numbers[1] < 10) {
                    text += "0";
                }
                timer.setText(numbers[0] + text + numbers[1]);
            }
        };
        realTimer.schedule(update, 1000, 1000);
        return timer;
    }

    private JButton turnInButton() {
        JButton startQuizButton = new JButton();
        startQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        startQuizButton.setBackground(Main.LESS_PURPLE);
        startQuizButton.setBorder(BorderFactory.createRaisedBevelBorder());
        startQuizButton.setText("Turn In");
        startQuizButton.setForeground(Color.BLACK);
        startQuizButton.setFocusable(false);
        startQuizButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        return startQuizButton;
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

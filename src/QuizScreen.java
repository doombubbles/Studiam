import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class QuizScreen extends Screen {


    private JPanel viewPanel;
    private JLabel timer;
    private Timer internalTimer;
    private int totalTerms;
    private JButton turnInButton;
    private Map<JTextField, QuizTerm> quizzedTerms;
    private boolean turnedIn = false;

    public QuizScreen(Quiz quiz) {
        setLayout(new BorderLayout());
        quizzedTerms = new HashMap<>();

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


        viewPanel = StudiamFactory.newTransparentPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));

        for (IQuizEntry element : quiz) {
            if (element instanceof QuizSection) {
                QuizSection section = (QuizSection) element;
                JPanel sectionPanel = new GUIQuizSection(section, quiz.percent, quiz.maxRemoved, this);
                viewPanel.add(sectionPanel);

            } else if (element instanceof QuizElement) {
                QuizElement quizElement = (QuizElement) element;
                viewPanel.add(new GUIQuizElement(quizElement, quiz.percent, quiz.maxRemoved, this));
            }

        }


        viewport.add(viewPanel);

        middlePanel.add(scrollPane(viewport));

    }

    public Map<JTextField, QuizTerm> getQuizzedTerms() {
        return quizzedTerms;
    }

    public void totalTermsPlusPlus() {
        totalTerms++;
    }

    public boolean isTurnedIn() {
        return turnedIn;
    }

    public java.util.List<GUIQuizSection> getGUIQuizSections() {
        List<GUIQuizSection> list = new ArrayList<>();
        for (Component c : viewPanel.getComponents()) {
            if (c instanceof JPanel) {
                JPanel panel = (JPanel) c;
                if (panel instanceof GUIQuizSection) {
                    list.add((GUIQuizSection) panel);
                }
            }
        }
        return list;
    }

    private JLabel timer() {
        timer = StudiamFactory.newStudiamLabel("0:00", 30);
        timer.setHorizontalAlignment(SwingConstants.CENTER);
        internalTimer = new Timer();
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
        internalTimer.schedule(update, 1000, 1000);
        return timer;
    }

    private JButton turnInButton() {
        turnInButton = new JButton();
        turnInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnInQuiz();
            }
        });
        turnInButton.setBackground(Main.LESS_PURPLE);
        turnInButton.setBorder(BorderFactory.createRaisedBevelBorder());
        turnInButton.setText("Turn In");
        turnInButton.setForeground(Color.BLACK);
        turnInButton.setFocusable(false);
        turnInButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        return turnInButton;
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

    //Switch to seeing the results of the quiz
    public void turnInQuiz() {
        int right = totalTerms;
        Main.getMainFrame().requestFocus();
        for (JTextField field : getQuizzedTerms().keySet()) {
            field.setEditable(false);
            QuizTerm term = getQuizzedTerms().get(field);
            if (term.matches(field.getText())) {
                field.setBackground(Color.GREEN);
            } else {
                field.setBackground(Color.RED);
                field.setText(field.getText() + "*");
                field.setToolTipText("Correct Answer: " + term.toString());
                right--;
            }
        }

        viewPanel.remove(turnInButton);
        JLabel scoreLabel = StudiamFactory.newStudiamLabel("Score: " + right + "/" + totalTerms +
                        " - " + (Math.round(10000 * right / totalTerms) / 100.0), 30,
                BorderFactory.createLineBorder(Color.BLACK));
        viewPanel.add(scoreLabel, BorderLayout.EAST);
        internalTimer.cancel();

    }
}

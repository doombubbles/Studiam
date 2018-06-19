/**
 * QuizScreen.java
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
import java.util.*;
import java.util.List;
import java.util.Timer;

public class QuizScreen extends Screen {

    private QuizFile quizFile;
    private JPanel viewPanel;
    private JPanel topPanel;
    private JLabel timer;
    private Timer internalTimer;
    private int totalTerms;
    private JButton turnInButton;
    private Map<JTextField, QuizTerm> quizzedTerms;
    private boolean turnedIn = false;
    private QuizScore score;
    private JButton saveScoreButton;

    public QuizScreen(Quiz quiz, QuizFile quizFile) {
        this.quizFile = quizFile;
        setLayout(new BorderLayout());
        quizzedTerms = new HashMap<>();

        topPanel = StudiamFactory.newTransparentPanel(new BorderLayout());
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
                JPanel sectionPanel =
                        new GUIQuizSection(section, quiz.percent, quiz.maxRemoved, this);
                viewPanel.add(sectionPanel);

            } else if (element instanceof QuizElement) {
                QuizElement quizElement = (QuizElement) element;
                viewPanel.add(
                        new GUIQuizElement(quizElement, quiz.percent, quiz.maxRemoved, this));
            }

        }


        viewport.add(viewPanel);

        middlePanel.add(scrollPane(viewport));

    }

    //method to get a full list of terms quizzed by this quiz
    public Map<JTextField, QuizTerm> getQuizzedTerms() {
        return quizzedTerms;
    }

    //method to help count the total number of terms
    public void totalTermsPlusPlus() {
        totalTerms++;
    }

    //method to see if the quiz is turned
    public boolean isTurnedIn() {
        return turnedIn;
    }

    //method to get the time that this quiz was taken in
    private int getTime() {
        String[] text = timer.getText().split(":");
        return Integer.parseInt(text[0]) * 60 + Integer.parseInt(text[1]);
    }


    private JButton saveScoreButton() {
        saveScoreButton = new JButton();
        saveScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quizFile.saveScore(score);
                topPanel.remove(saveScoreButton);
                topPanel.revalidate();
                topPanel.repaint();
                topPanel.add(StudiamFactory.newStudiamLabel("Saved!", 30), BorderLayout.EAST);
            }
        });
        saveScoreButton.setBackground(Main.LESS_PURPLE);
        saveScoreButton.setBorder(BorderFactory.createRaisedBevelBorder());
        saveScoreButton.setText("Save Score");
        saveScoreButton.setForeground(Color.BLACK);
        saveScoreButton.setFocusable(false);
        saveScoreButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        return saveScoreButton;
    }

    //method for the timer that times the time it takes to take this timed quiz
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

    //method for the button to turn in this quiz
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

    //method to create the scroll pane to house all the terms in
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

    //method to witch to seeing the results of the quiz
    public void turnInQuiz() {
        List<String> wrong = new ArrayList<>();
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
                wrong.add(term.toString());
                right--;
            }
        }
        internalTimer.cancel();
        score = new QuizScore(right, totalTerms, getTime(), wrong);
        topPanel.remove(turnInButton);
        JLabel scoreLabel = StudiamFactory.newStudiamLabel("QuizScore: " + score.toNiceString(),
                30, BorderFactory.createLineBorder(Color.BLACK));
        viewPanel.add(scoreLabel, BorderLayout.EAST);

        topPanel.revalidate();
        topPanel.repaint();
        topPanel.add(saveScoreButton(), BorderLayout.EAST);

    }
}

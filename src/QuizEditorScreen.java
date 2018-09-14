/**
 * QuizEditorScreen.java
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
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.List;

public class QuizEditorScreen extends Screen {

    private QuizFile quizFile;
    private Quiz baseQuiz;
    private JTextField title;
    private JTextField desc;
    private JTextField percent;
    private JTextField maxBye;
    private JPanel viewPanel;
    private QuizScoreGraph quizScoreGraph;

    public boolean saved = true;

    public QuizEditorScreen(QuizFile file, Quiz quiz) {
        this.quizFile = file;

        if (quiz == null) {
            baseQuiz = file.createQuiz();
        } else baseQuiz = quiz;

        screenId = "EditQuiz_" + baseQuiz.name;
        setLayout(new BorderLayout());


        JPanel topPanel = StudiamFactory.newTransparentPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 200, 200, 100));
        add(topPanel, BorderLayout.NORTH);
        JPanel middlePanel = StudiamFactory.newTransparentPanel(new BorderLayout());
        add(middlePanel, BorderLayout.CENTER);

        title = StudiamFactory.newStudiamTextField(baseQuiz.name, 30,
                BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), //outside
                BorderFactory.createEmptyBorder(0, 2, 0, 5))); //inside
        title.addKeyListener(Main.textFieldKeyListener());
        topPanel.add(title, BorderLayout.WEST);

        desc = StudiamFactory.newStudiamTextField(baseQuiz.description, 15,
                BorderFactory.createEmptyBorder(0,5,0,0));
        desc.setBackground(new Color(200, 200, 200));
        topPanel.add(desc, BorderLayout.SOUTH);

        topPanel.add(settingsPanel());
        topPanel.add(startQuizButton(), BorderLayout.EAST);


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
        middlePanel.add(scrollPane(viewport));

        viewPanel = StudiamFactory.newTransparentPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));

        for (IQuizEntry element : baseQuiz) {
            if (element instanceof QuizSection) {
                QuizSection section = (QuizSection) element;
                JPanel sectionPanel = new GUIEditorQuizSection(section);
                viewPanel.add(sectionPanel);

            } else if (element instanceof QuizElement) {
                QuizElement quizElement = (QuizElement) element;
                viewPanel.add(new GUIEditorQuizElement(quizElement));
            }

        }



        viewPanel.add(newButton());
        viewPanel.add(Box.createVerticalStrut(50));


        if (!baseQuiz.getScores().isEmpty()) {
            viewPanel.add(StudiamFactory.newStudiamLabel("Score History:", 42));
            for (QuizScore score : baseQuiz.getScores()) {
                JPanel scorePanel = StudiamFactory.newTransparentPanel();
                scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
                scorePanel.setAlignmentX(LEFT_ALIGNMENT);
                scorePanel.setMaximumSize(new Dimension(350, 1000));
                JLabel scoreLabel = StudiamFactory.newStudiamLabel(score.toNiceString(), 20);
                scorePanel.add(scoreLabel);
                scorePanel.add(Box.createHorizontalStrut(5));
                scorePanel.add(deleteScoreButton(scorePanel, score));
                viewPanel.add(scorePanel);
            }
            quizScoreGraph = new QuizScoreGraph(baseQuiz.getScores());
            viewPanel.add(quizScoreGraph);
        } else quizScoreGraph = null;

        viewPanel.add(Box.createVerticalStrut(500));
        viewport.add(viewPanel);

        baseQuiz = getQuiz();
    }

    //method for the button to add new Sections
    private JButton newButton() {
        JButton newButton = new JButton();
        newButton.setText("New Section");
        newButton.setPreferredSize(new Dimension(100, 25));
        newButton.setFocusable(false);
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizElement element1 = new QuizElement("[term]");

                QuizSection section = new QuizSection("name", Arrays.asList(element1));
                JPanel quizSectionPanel = new GUIEditorQuizSection(section);
                viewPanel.add(quizSectionPanel, viewPanel.getComponentCount()
                        - 4 - baseQuiz.getScores().size());
                saved = false;
                revalidate();
                repaint();

                //Main.addToScreenHistory();
            }
        });
        return newButton;
    }

    //method for the button to delete a quiz score
    private JButton deleteScoreButton(JPanel scorePanel, QuizScore score) {
        JButton deleteScoreButton = new JButton();
        deleteScoreButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPanel.remove(scorePanel);
                baseQuiz.getScores().remove(score);
                revalidate();
                repaint();
                quizScoreGraph.setScores(baseQuiz.getScores());
                quizScoreGraph.update();
            }
        });
        deleteScoreButton.setText("X");
        deleteScoreButton.setMargin(new Insets(0, 0, 0, 0));
        deleteScoreButton.setFont(new Font("Arial", Font.BOLD, 15));
        deleteScoreButton.setPreferredSize(new Dimension(20, 20));
        deleteScoreButton.setMaximumSize(new Dimension(20, 20));
        deleteScoreButton.setForeground(Color.BLACK);
        deleteScoreButton.setFocusable(false);
        return deleteScoreButton;
    }

    //method to query the user to see if they'll save their changes first
    public boolean saveChangesFirst() {
        int hm = JOptionPane.showConfirmDialog(null,
                "Save your changes first?", "Warning", JOptionPane.OK_CANCEL_OPTION);
        if (hm == JOptionPane.OK_OPTION) {
            return true;
        } else return false;
    }

    //method for the button to start the quiz
    private JButton startQuizButton() {
        JButton startQuizButton = new JButton();
        startQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!baseQuiz.equals(getQuiz())) {
                    if (saveChangesFirst()) {
                        saveFile();
                    } else return;
                }
                Main.switchScreen(new QuizScreen(getQuiz(), quizFile));
            }
        });
        startQuizButton.setBackground(Main.LESS_PURPLE);
        startQuizButton.setBorder(BorderFactory.createRaisedBevelBorder());
        startQuizButton.setText("Start the Quiz!");
        startQuizButton.setForeground(Color.BLACK);
        startQuizButton.setFocusable(false);
        startQuizButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        return startQuizButton;
    }

    //method for creating the scrollpane window where the quiz stuff will be housed
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

    //method to make the panel of settings for the quiz
    private JPanel settingsPanel() {
        JPanel settings = StudiamFactory.newTransparentPanel();
        JLabel percentLabel = StudiamFactory.newStudiamLabel("Quiz Percent", 15);
        percent = StudiamFactory.newStudiamTextField(baseQuiz.percent + "%", 15);
        percent.setPreferredSize(new Dimension(45, 20));
        percent.addActionListener(a -> {
            if (percent.getText().indexOf("%") != percent.getText().length() - 1) {
                percent.setText(percent.getText().replace("%", "") + "%");
            } else if (percent.getText().isEmpty() || percent.getText().equals("%")) {
                percent.setText("0%");
            } else if (percent.getText().length() > 3) {
                percent.setText("100%");
            }
        });
        percent.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (percent.getText().indexOf("%") != percent.getText().length() - 1) {
                    percent.setText(percent.getText().replace("%", "") + "%");
                } else if (percent.getText().isEmpty() || percent.getText().equals("%")) {
                    percent.setText("0%");
                } else if (percent.getText().length() > 3) {
                    percent.setText("100%");
                }
            }
        });
        settings.add(percentLabel);
        settings.add(percent);


        JLabel maxByeLabel = StudiamFactory.newStudiamLabel("Max Removed", 15);
        maxBye = StudiamFactory.newStudiamTextField(baseQuiz.maxRemoved + "", 15);
        maxBye.setPreferredSize(new Dimension(20, 20));
        maxBye.addActionListener(a -> {
            try {
                int test = Integer.parseInt(maxBye.getText());
                if (test == 0) {
                    maxBye.setText("1");
                }
            } catch (NumberFormatException e) {
                maxBye.setText("1");
                return;
            }
        });
        settings.add(maxByeLabel);
        settings.add(maxBye);

        return settings;
    }

    //method in-case you specifically want to see the base quiz
    public Quiz getBaseQuiz() {
        return baseQuiz;
    }

    //method to get all of the quiz sections in the editor as GUIEditorQuizSection objects
    public List<GUIEditorQuizSection> getGUIEditorQuizSections() {
        List<GUIEditorQuizSection> list = new ArrayList<>();
        for (Component c : viewPanel.getComponents()) {
            if (c instanceof JPanel) {
                JPanel panel = (JPanel) c;
                if (panel instanceof GUIEditorQuizSection) {
                    list.add((GUIEditorQuizSection) panel);
                }
            }
        }
        return list;
    }

    //method to update the internal quiz file with what's currently displayed and return that
    public Quiz getQuiz() {
        Quiz quiz = new Quiz();
        quiz.name = title.getText();
        quiz.description = desc.getText();
        quiz.percent = Integer.parseInt(percent.getText().replace("%", ""));
        quiz.maxRemoved = Integer.parseInt(maxBye.getText());
        for (GUIEditorQuizSection guiSection : getGUIEditorQuizSections()) {
            quiz.add(guiSection.getQuizSectiom());
        }
        baseQuiz.getScores().forEach(quiz::addScore);
        return quiz;
    }

    //method to find the location to save the quiz file to and save it
    public boolean saveFileAs() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Studiam Quiz Files", "studiam"));
        int result = jFileChooser.showSaveDialog(Main.getMainFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            QuizFile quizFile = new QuizFile(file);
            saveFile(quizFile);
            Main.addRecentFile(quizFile);
        }

        return true;
    }

    //dummy method for next method
    public boolean saveFile() {
        return saveFile(quizFile);
    }

    //method to save the file being edited
    public boolean saveFile(QuizFile quizFile) {
        PrintStream output;
        try {
            output = new PrintStream(new FileOutputStream(quizFile, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        Quiz quiz = getQuiz();
        baseQuiz = quiz;
        this.quizFile = quizFile;

        output.println("quizName = " + quiz.name);
        output.println("quizDesc = " + quiz.description);
        output.println("quizPercent = " + quiz.percent);
        output.println("quizMaxRemoved = " + quiz.maxRemoved);
        for (IQuizEntry quizEntry : quiz) {
            if (quizEntry instanceof QuizSection) {
                QuizSection section = (QuizSection) quizEntry;
                output.println("quizSection = " + section.getName());
                for (QuizElement element : section) {
                    output.println("quizElement = " + element.convertBack());
                }
            } else {
                QuizElement element = (QuizElement) quizEntry;
                output.println("quizElement = " + element.convertBack());
            }
        }
        for (QuizScore s : quiz.getScores()) {
            output.println("quizScore = " + s.toString());
        }

        return true;
    }


}

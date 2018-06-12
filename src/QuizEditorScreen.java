import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    public boolean saved = true;

    public QuizEditorScreen(QuizFile file) {
        this.quizFile = file;

        baseQuiz = file.createQuiz();

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
        viewPanel.add(Box.createVerticalStrut(1000));
        viewport.add(viewPanel);

        baseQuiz = getQuiz();
    }

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

                viewPanel.add(quizSectionPanel, viewPanel.getComponentCount() - 2);
                saved = false;
                revalidate();
                repaint();
            }
        });
        return newButton;
    }

    private JButton startQuizButton() {
        JButton startQuizButton = new JButton();
        startQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!baseQuiz.equals(getQuiz())) {
                    int hm = JOptionPane.showConfirmDialog(null,
                            "Save your changes first?", "Warning", JOptionPane.OK_CANCEL_OPTION);
                    if (hm == JOptionPane.OK_OPTION) {
                        saveFile();
                    } else return;
                }
                Main.switchScreen(new QuizScreen(getQuiz()));
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
        maxBye = StudiamFactory.newStudiamTextField("1", 15);
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

    public Quiz getBaseQuiz() {
        return baseQuiz;
    }

    public Quiz getQuiz() {
        Quiz quiz = new Quiz();
        quiz.name = title.getText();
        quiz.description = desc.getText();
        quiz.percent = Integer.parseInt(percent.getText().replace("%", ""));
        quiz.maxRemoved = Integer.parseInt(maxBye.getText());
        for (Component c : viewPanel.getComponents()) {
            if (c instanceof JPanel) {
                JPanel panel = (JPanel) c;
                if (panel instanceof GUIEditorQuizSection) {
                    GUIEditorQuizSection section = (GUIEditorQuizSection) panel;
                    quiz.add(section.getQuizSectiom());
                } else if (c instanceof GUIEditorQuizElement) {
                    quiz.add(((GUIEditorQuizElement) c).getQuizElement());
                }
            }
        }
        return quiz;
    }



    public boolean saveFile() {
        PrintStream output;
        try {
            output = new PrintStream(new FileOutputStream(quizFile, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        Quiz quiz = getQuiz();
        baseQuiz = quiz;

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

        return true;
    }


}

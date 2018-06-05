import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class EditQuizScreen extends Screen {

    private QuizFile quizFile;
    private Quiz quiz;
    private JTextArea title;
    private JTextArea desc;
    private JPanel viewPanel;

    public EditQuizScreen(QuizFile file) {
        this.quizFile = file;
        setBackground(Main.CLEAR);

        quiz = quizFile.initialize();

        screenId = "EditQuiz_" + quiz.name;
        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 200, 200, 100));
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(Main.CLEAR);


        title = new JTextArea(quiz.name);
        title.setFont(new Font("Times New Roman", Font.BOLD, 30));
        title.setSelectionColor(Main.LESS_PURPLE);
        title.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(0, 2, 0, 5)));
        title.setForeground(Color.BLACK);

        desc = new JTextArea(quiz.description);
        desc.setFont(new Font("Times New Roman", Font.BOLD, 15));
        desc.setSelectionColor(Main.LESS_PURPLE);
        desc.setBackground(new Color(200, 200, 200));
        desc.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
        desc.setForeground(Color.BLACK);

        JButton startQuizButton = new JButton();
        startQuizButton.setBackground(Main.LESS_PURPLE);
        startQuizButton.setBorder(BorderFactory.createRaisedBevelBorder());
        startQuizButton.setText("Start the quiz!");
        startQuizButton.setForeground(Color.BLACK);
        startQuizButton.setFont(new Font("Times New Roman", Font.BOLD, 25));

        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(desc, BorderLayout.SOUTH);
        topPanel.add(startQuizButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);


        JViewport viewport = new JViewport();
        JScrollPane scrollPane = new JScrollPane(viewport);
        scrollPane.setBackground(Main.CLEAR);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        viewport.setLayout(new BorderLayout());
        viewport.setBackground(Main.CLEAR);

        viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.setBackground(Main.CLEAR);

        for (IQuizEntry element : quiz) {
            if (element instanceof QuizSection) {
                QuizSection section = (QuizSection) element;
                JPanel sectionPanel = quizSectionPanel(section);
                viewPanel.add(sectionPanel);


            } else if (element instanceof QuizElement) {
                QuizElement quizElement = (QuizElement) element;
                viewPanel.add(new GUIEditorQuizElement(quizElement));
            }

        }

        JButton newButton2 = new JButton();
        newButton2.setText("New Section");
        newButton2.setPreferredSize(new Dimension(100, 25));
        newButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizElement element1 = new QuizElement("[term]");
                GUIEditorQuizElement newElement = new GUIEditorQuizElement(element1);

                QuizSection section = new QuizSection("name", Arrays.asList(element1));
                JPanel quizSectionPanel = quizSectionPanel(section);

                viewPanel.add(quizSectionPanel, viewPanel.getComponentCount() - 1);
                revalidate();
                repaint();
            }
        });
        viewPanel.add(newButton2);

        viewport.add(viewPanel);

        middlePanel.add(scrollPane);
    }

    public JPanel quizSectionPanel(QuizSection section) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setName(section.getName());
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(new Color(200, 200, 200, 100));
        sectionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), section.getName(),
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Times New Roman", Font.BOLD, 25), Color.BLACK));


        sectionPanel.setAlignmentX(LEFT_ALIGNMENT);
        for (QuizElement quizElement : section) {
            sectionPanel.add(new GUIEditorQuizElement(quizElement));
        }
        JButton newButton = new JButton();
        newButton.setText("New");
        newButton.setPreferredSize(new Dimension(100, 25));
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizElement element1 = new QuizElement("[term]");
                GUIEditorQuizElement newElement = new GUIEditorQuizElement(element1);
                sectionPanel.add(newElement, sectionPanel.getComponentCount() - 1);
                revalidate();
                repaint();
            }
        });

        sectionPanel.add(newButton);

        return sectionPanel;
    }



    public boolean saveFile() {
        PrintStream output;
        try {
            output = new PrintStream(new FileOutputStream(quizFile, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        output.println("quizName = " + title.getText());
        output.println("quizDesc = " + desc.getText());
        for (Component c : viewPanel.getComponents()) {
            if (c instanceof JPanel) {
                JPanel panel = (JPanel) c;
                if (panel.getName() != null) {
                    output.println("quizSection = " + panel.getName());
                    for (Component c2 : panel.getComponents()) {
                        if (c2 instanceof GUIEditorQuizElement) {
                            QuizElement element = ((GUIEditorQuizElement) c2).getQuizElement();
                            output.println("quizElement = " + element.convertBack());
                        }
                    }
                } else if (c instanceof GUIEditorQuizElement) {
                    QuizElement element = ((GUIEditorQuizElement) c).getQuizElement();
                    output.println("quizElement = " + element.convertBack());
                }
            }
        }



        return true;
    }


}

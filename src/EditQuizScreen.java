import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class EditQuizScreen extends Screen {

    private QuizFile quizFile;
    private Quiz quiz;
    private final String TEMP_FILE = "";

    public EditQuizScreen(QuizFile file) {
        this.quizFile = file;

        quiz = quizFile.initialize();

        screenId = "EditQuiz_" + quiz.name;
        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        JPanel middlePanel = new JPanel(new BorderLayout());
        JLabel studiamLabel = new JLabel(quiz.name);
        studiamLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        topPanel.add(studiamLabel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);




        JViewport viewport = new JViewport();
        JScrollPane scrollPane = new JScrollPane(viewport);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        viewport.setLayout(new BorderLayout());

        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));


        for (IQuizEntry element : quiz) {
            if (element instanceof QuizSection) {
                QuizSection section = (QuizSection) element;
                JPanel sectionPanel = new JPanel();
                sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
                sectionPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.BLACK), section.getName(),
                        TitledBorder.LEFT, TitledBorder.TOP, new Font("Times New Roman", Font.BOLD, 25)));
                sectionPanel.setAlignmentX(LEFT_ALIGNMENT);
                viewPanel.add(sectionPanel);
                for (QuizElement quizElement : section) {
                    sectionPanel.add(new GUIEditorQuizElement(quizElement));
                }

            } else if (element instanceof QuizElement) {
                QuizElement quizElement = (QuizElement) element;
                viewPanel.add(new GUIEditorQuizElement(quizElement));
            }

        }




        viewport.add(viewPanel);

        middlePanel.add(scrollPane);


    }
}

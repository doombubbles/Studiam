import javax.swing.*;
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
        middlePanel.add(scrollPane);


        viewport.setLayout(new FlowLayout());
        viewport.add(new JLabel(new ImageIcon("icon.png")));
        viewport.add(new JLabel(new ImageIcon("icon.png")));
        viewport.add(new JLabel(new ImageIcon("icon.png")));

        for (IQuizEntry element : quiz) {
            if (element instanceof QuizSection) {
                QuizSection section = (QuizSection) element;
                for (QuizElement quizElement : section) {
                    for (QuizTerm term : quizElement) {
                        viewport.add(new GUIEditorQuizTerm(term));
                    }
                }

            } else if (element instanceof QuizElement) {
                QuizElement quizElement = (QuizElement) element;
                for (QuizTerm term : quizElement) {
                    viewport.add(new GUIEditorQuizTerm(term));
                }

            }

        }






    }
}

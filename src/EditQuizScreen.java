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
        setBackground(Color.MAGENTA);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        JLabel studiamLabel = new JLabel(quiz.name);
        studiamLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        topPanel.add(studiamLabel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        for (QuizElement element : quiz.getAllElements()) {
            System.out.println("hmm" + element);
        }


    }
}

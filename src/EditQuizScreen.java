import javax.swing.*;
import java.awt.*;

public class EditQuizScreen extends Screen {

    private QuizFile quizFile;
    private final String TEMP_FINAL_NAME = "";

    public EditQuizScreen(QuizFile file) {
        this.quizFile = file;
        screenId = "EditQuiz_" + quizFile.getQuizName();
        setLayout(new BorderLayout());
        setBackground(Color.MAGENTA);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        JLabel studiamLabel = new JLabel(quizFile.getQuizName());
        studiamLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        topPanel.add(studiamLabel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

    }
}

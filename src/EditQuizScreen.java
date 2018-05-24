import javax.swing.*;
import java.awt.*;
import java.io.File;

public class EditQuizScreen extends Screen {

    private File file;
    private final String TEMP_FINAL_NAME = "";

    public EditQuizScreen(File file) {
        this.file = file;
        screenId = "EditQuiz_" + file.getName();
        setLayout(new BorderLayout());
        setBackground(Color.MAGENTA);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        JLabel studiamLabel = new JLabel("Studiam");
        studiamLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        topPanel.add(studiamLabel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

    }
}

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUIEditorQuizElement extends JPanel {

    public GUIEditorQuizElement(QuizElement element) {
        setPreferredSize(new Dimension(100, 40));
        setMaximumSize(new Dimension(element.size() * 100, 40));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 10, 0),
                BorderFactory.createLineBorder(new Color(150, 0, 200))));

        for (QuizTerm term : element) {
            add(new GUIEditorQuizTerm(term));
        }
    }
}

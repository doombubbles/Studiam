import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUIEditorQuizElement extends JPanel {

    public GUIEditorQuizElement(QuizElement element) {
        setPreferredSize(new Dimension(100, 40));
        setMaximumSize(new Dimension(element.size() * 100, 40));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 10, 0),
                BorderFactory.createLineBorder(Main.PURPLE)));

        for (QuizTerm term : element) {
            add(new GUIEditorQuizTerm(term));
        }
    }

    public QuizElement getQuizElement() {
        QuizElement element = new QuizElement();
        for (Component c : getComponents()) {
            if (c instanceof GUIEditorQuizTerm) {
                QuizTerm quizTerm = ((GUIEditorQuizTerm) c).getQuizTerm();
                element.add(quizTerm);
            }
        }
        return element;
    }
}

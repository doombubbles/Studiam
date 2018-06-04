import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class GUIEditorQuizElement extends JPanel {

    public GUIEditorQuizElement(QuizElement element) {
        setPreferredSize(new Dimension(100, 40));
        setMaximumSize(new Dimension(element.size() * 100 + 100, 40));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 10, 0),
                BorderFactory.createLineBorder(Main.PURPLE)));

        for (QuizTerm term : element) {
            add(new GUIEditorQuizTerm(term));
        }

        JButton addButton = new JButton();
        addButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add(new GUIEditorQuizTerm(new QuizTerm("[term]")), getComponentCount() - 4);
                setMaximumSize(new Dimension(element.size() * 100 + 100, 40));
                revalidate();
                repaint();
            }
        });
        addButton.setText("+");
        addButton.setMargin(new Insets(0, 0, 0, 0));
        addButton.setFont(new Font("Arial", Font.BOLD, 15));
        addButton.setPreferredSize(new Dimension(20, 20));
        addButton.setMaximumSize(new Dimension(20, 20));
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(addButton);

        JButton deleteButton = new JButton();
        deleteButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        deleteButton.setText("X");
        deleteButton.setMargin(new Insets(0, 0, 0, 0));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 15));
        deleteButton.setPreferredSize(new Dimension(20, 20));
        deleteButton.setMaximumSize(new Dimension(20, 20));
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(deleteButton);
    }

    public void delete() {
        Container parent = getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
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

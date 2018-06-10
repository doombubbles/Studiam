import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GUIEditorQuizElement extends JPanel {

    public GUIEditorQuizElement(QuizElement element) {
        addKeyListener(Main.mainKeyListener());
        setOpaque(false);
        setPreferredSize(new Dimension(100, 40));
        setBackground(new Color(200, 200, 200, 200));
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 0, 5, 0),
                BorderFactory.createLineBorder(Main.PURPLE)));

        for (QuizTerm term : element) {
            add(new GUIEditorQuizTerm(term));
        }


        add(Box.createRigidArea(new Dimension(10, 0)));
        add(addButton());
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(deleteButton());


        updateVisuals();
    }

    //button to add a new term to this quiz element
    private JButton addButton() {
        JButton addButton = new JButton();
        addButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add(new GUIEditorQuizTerm(new QuizTerm("[term]")), getComponentCount() - 4);
                updateVisuals();
            }
        });
        addButton.setText("+");
        addButton.setMargin(new Insets(0, 0, 0, 0));
        addButton.setFont(new Font("Arial", Font.BOLD, 15));
        addButton.setPreferredSize(new Dimension(20, 20));
        addButton.setMaximumSize(new Dimension(20, 20));
        return addButton;
    }

    //button to delete this quiz element
    private JButton deleteButton() {
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
        return deleteButton;
    }

    //method to delete this quiz element and keep things looking right
    public void delete() {
        Container parent = getParent();
        parent.remove(this);
        if (parent instanceof GUIEditorQuizSection) {
            GUIEditorQuizSection quizSection = (GUIEditorQuizSection) parent;
            quizSection.updateVisuals();
        }
    }

    //self-explanatory
    public void updateVisuals() {
        revalidate();
        repaint();

        /*
        int total = 100;
        for (GUIEditorQuizTerm term : getGUIQuizTerms()) {
            total += Math.max(75, term.getSize().width);
        }
        setMaximumSize(new Dimension(total, 40));
        */

    }

    //method to get all the specific GUIEditorQuizTerm components from this element
    public List<GUIEditorQuizTerm> getGUIQuizTerms() {
        List<GUIEditorQuizTerm> list = new ArrayList<>();
        for (Component c : getComponents()) {
            if (c instanceof GUIEditorQuizTerm) {
                list.add((GUIEditorQuizTerm) c);
            }
        }
        return list;
    }

    //method to get the base QuizElement from this GUIEditorQuizElement
    public QuizElement getQuizElement() {
        QuizElement element = new QuizElement();
        for (GUIEditorQuizTerm quizTerm : getGUIQuizTerms()) {
            element.add(quizTerm.getQuizTerm());
        }
        return element;
    }

    @Override //method to stop dumb visual glitch
    protected void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}

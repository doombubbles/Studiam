import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUIEditorQuizSection extends JPanel {

    private JTextField sectionNameArea;

    public GUIEditorQuizSection(QuizSection section) {
        addKeyListener(Main.mainKeyListener());
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(200, 200, 200, 100));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 0, 0, 1000), //outside
                BorderFactory.createLineBorder(Color.BLACK)),
                BorderFactory.createEmptyBorder(-13,5,5,5))); //inside


        JPanel sectionNamePanel = StudiamFactory.newTransparentPanel(new BorderLayout());
        sectionNameArea = StudiamFactory.newStudiamTextField(section.getName(), 20,
                BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), //outside
                        BorderFactory.createEmptyBorder(-2, 0,-2, 0))); //inside
        sectionNamePanel.add(sectionNameArea, BorderLayout.WEST);


        sectionNamePanel.add(deleteButton(), BorderLayout.EAST);


        add(sectionNamePanel);
        setAlignmentX(LEFT_ALIGNMENT);
        for (QuizElement quizElement : section) {
            add(new GUIEditorQuizElement(quizElement));
        }

        add(newButton());
    }

    public String getSectionName() {
        return sectionNameArea.getText();
    }

    private JButton newButton() {
        JButton newButton = new JButton();
        newButton.setText("New");
        newButton.setForeground(Color.BLACK);
        newButton.setPreferredSize(new Dimension(100, 25));
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizElement element1 = new QuizElement("[term]");
                GUIEditorQuizElement newElement = new GUIEditorQuizElement(element1);
                add(newElement, getComponentCount() - 1);
                updateVisuals();
            }
        });
        return newButton;
    }

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
        deleteButton.setFont(new Font("Arial", Font.BOLD, 20));
        deleteButton.setPreferredSize(new Dimension(25, 25));
        deleteButton.setMaximumSize(new Dimension(25, 25));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFocusable(false);
        return deleteButton;
    }

    public void updateVisuals() {
        revalidate();
        repaint();
    }

    //method to delete this quiz section and keep things looking right
    public void delete() {
        Container parent = getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

    //method to get all the specific GUIEditorQuizElement components from this element
    public List<GUIEditorQuizElement> getGUIQuizElements() {
        List<GUIEditorQuizElement> list = new ArrayList<>();
        for (Component c : getComponents()) {
            if (c instanceof GUIEditorQuizElement) {
                list.add((GUIEditorQuizElement) c);
            }
        }
        return list;
    }

    //method to get the base QuizSection from this GUIEditorQuizSection
    public QuizSection getQuizSectiom() {
        QuizSection section = new QuizSection(sectionNameArea.getText());
        for (GUIEditorQuizElement quizElement : getGUIQuizElements()) {
            section.add(quizElement.getQuizElement());
        }
        return section;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}

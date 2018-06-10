import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUIEditorQuizSection extends JPanel {

    JTextArea sectionNameArea;

    public GUIEditorQuizSection(QuizSection section) {
        addKeyListener(Main.mainKeyListener());
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(200, 200, 200, 100));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 0, 0, 0), //outside
                BorderFactory.createLineBorder(Color.BLACK)),
                BorderFactory.createEmptyBorder(-11,5,15,5))); //inside


        JPanel sectionNamePanel = StudiamFactory.newTransparentPanel(new BorderLayout());
        sectionNameArea = StudiamFactory.newStudiamTextArea(section.getName(), 20,
                BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), //outside
                        BorderFactory.createEmptyBorder(-5, 0,0, 0))); //inside
        sectionNamePanel.add(sectionNameArea, BorderLayout.WEST);
        add(sectionNamePanel);

        setAlignmentX(LEFT_ALIGNMENT);
        for (QuizElement quizElement : section) {
            add(new GUIEditorQuizElement(quizElement));
        }

        JButton newButton = new JButton();
        newButton.setText("New");
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
        add(newButton);
        updateVisuals();
    }

    public void updateVisuals() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 0, 0, 0), //outside
                BorderFactory.createLineBorder(Color.BLACK)),
                BorderFactory.createEmptyBorder(-15,5,8 + 6 * getComponentCount(),5)));
        revalidate();
        repaint();
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

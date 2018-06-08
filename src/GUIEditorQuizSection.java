import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIEditorQuizSection extends JPanel {

    public GUIEditorQuizSection(QuizSection section) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(200, 200, 200, 100));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 0, 0, 0), //outside
                BorderFactory.createLineBorder(Color.BLACK)),
                BorderFactory.createEmptyBorder(-15,5,25,5))); //inside


        JPanel sectionNamePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        sectionNamePanel.setOpaque(false);
        sectionNamePanel.setMinimumSize(new Dimension(100, 20));
        sectionNamePanel.setPreferredSize(new Dimension(0, 20));
        sectionNamePanel.setMaximumSize(new Dimension(2000, 20));
        sectionNamePanel.setBackground(Main.CLEAR);
        JTextArea sectionNameArea = new JTextArea(section.getName());
        sectionNameArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        sectionNameArea.setSelectionColor(Main.LESS_PURPLE);
        sectionNameArea.setBorder(BorderFactory.createLoweredBevelBorder());
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
                updateBorder();
                revalidate();
                repaint();

            }
        });
        revalidate();
        repaint();
        add(newButton);
    }

    public void updateBorder() {
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 0, 0, 0), //outside
                BorderFactory.createLineBorder(Color.BLACK)),
                BorderFactory.createEmptyBorder(-15,5,8 + 6 * getComponentCount(),5)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}

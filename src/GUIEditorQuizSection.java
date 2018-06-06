import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIEditorQuizSection extends JPanel {

    public GUIEditorQuizSection(QuizSection section) {
        setOpaque(false);
        setName(section.getName());
       setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       setBackground(new Color(200, 200, 200, 100));
       TitledBorder titledBorder = new TitledBorder(BorderFactory.createLineBorder(Color.BLACK),
               section.getName(), TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 25), Color.BLACK) {
       };
       setBorder(titledBorder);

       setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
               section.getName(), TitledBorder.LEFT, TitledBorder.TOP,
               new Font("Times New Roman", Font.BOLD, 25), Color.BLACK));


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
                revalidate();
                repaint();
            }
        });

        add(newButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}

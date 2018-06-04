import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class EditQuizScreen extends Screen {

    private QuizFile quizFile;
    private Quiz quiz;
    private JTextArea title;
    private JTextArea desc;
    private JPanel viewPanel;

    public EditQuizScreen(QuizFile file) {
        this.quizFile = file;

        quiz = quizFile.initialize();

        screenId = "EditQuiz_" + quiz.name;
        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        JPanel middlePanel = new JPanel(new BorderLayout());


        title = new JTextArea(quiz.name);
        title.setFont(new Font("Times New Roman", Font.BOLD, 30));
        title.setSelectionColor(Main.LESS_PURPLE);
        title.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(0, 2, 0, 5)));

        desc = new JTextArea(quiz.description);
        desc.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        desc.setSelectionColor(Main.LESS_PURPLE);
        desc.setBackground(Color.LIGHT_GRAY);

        JButton startQuizButton = new JButton();
        startQuizButton.setText("Start the quiz!");
        startQuizButton.setFont(new Font("Times New Roman", Font.BOLD, 25));

        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(desc, BorderLayout.SOUTH);
        topPanel.add(startQuizButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);


        JViewport viewport = new JViewport();
        JScrollPane scrollPane = new JScrollPane(viewport);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        viewport.setLayout(new BorderLayout());

        viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));


        for (IQuizEntry element : quiz) {
            if (element instanceof QuizSection) {
                QuizSection section = (QuizSection) element;
                JPanel sectionPanel = new JPanel();
                sectionPanel.setName(section.getName());
                sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
                sectionPanel.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.BLACK), section.getName(),
                        TitledBorder.LEFT, TitledBorder.TOP, new Font("Times New Roman", Font.BOLD, 25)));
                sectionPanel.setAlignmentX(LEFT_ALIGNMENT);
                viewPanel.add(sectionPanel);
                for (QuizElement quizElement : section) {
                    sectionPanel.add(new GUIEditorQuizElement(quizElement));
                }

                JButton newButton = new JButton();
                newButton.setText("New");
                newButton.setPreferredSize(new Dimension(100, 25));
                newButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        QuizElement element1 = new QuizElement("[term]");
                        GUIEditorQuizElement newElement = new GUIEditorQuizElement(element1);
                        sectionPanel.add(newElement, sectionPanel.getComponentCount() - 1);
                        revalidate();
                        repaint();
                    }
                });

                sectionPanel.add(newButton);

            } else if (element instanceof QuizElement) {
                QuizElement quizElement = (QuizElement) element;
                viewPanel.add(new GUIEditorQuizElement(quizElement));
            }

        }

        viewport.add(viewPanel);

        middlePanel.add(scrollPane);

    }

    public boolean saveFile() {
        PrintStream output;
        try {
            output = new PrintStream(new FileOutputStream(quizFile, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        output.println("quizName = " + title.getText());
        output.println("quizDesc = " + desc.getText());
        for (Component c : viewPanel.getComponents()) {
            if (c instanceof JPanel) {
                JPanel panel = (JPanel) c;
                if (panel.getName() != null) {
                    output.println("quizSection = " + panel.getName());
                    for (Component c2 : panel.getComponents()) {
                        if (c2 instanceof GUIEditorQuizElement) {
                            QuizElement element = ((GUIEditorQuizElement) c2).getQuizElement();
                            output.println("quizElement = " + element.convertBack());
                        }
                    }
                } else if (c instanceof GUIEditorQuizElement) {
                    QuizElement element = ((GUIEditorQuizElement) c).getQuizElement();
                    output.println("quizElement = " + element.convertBack());
                }
            }
        }



        return true;
    }


}

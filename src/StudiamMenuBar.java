import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class StudiamMenuBar extends JMenuBar{

    public static final int CTRL = Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask();

    private static JFrame parent;

    public StudiamMenuBar(JFrame parent) {
        JMenu fileMenu = new JMenu("File");
        this.parent = parent;

        fileMenu.add(open());
        fileMenu.add(save());

        add(fileMenu);
    }

    private static JMenuItem open() {
        JMenuItem open = new JMenuItem();
        open.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new FileNameExtensionFilter("Studiam Quiz Files", "studiam"));
                int result = jFileChooser.showOpenDialog(parent);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = jFileChooser.getSelectedFile();
                    QuizFile quizFile = new QuizFile(file);
                    Main.openFile(quizFile);
                }

            }
        });
        open.setAccelerator(KeyStroke.getKeyStroke('O', CTRL));
        open.setText("Open");
        return open;
    }

    private static JMenuItem save() {
        JMenuItem save = new JMenuItem("Save");
        save.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Saved!");
            }
        });
        save.setAccelerator(KeyStroke.getKeyStroke('S', CTRL));
        save.setText("Save");
        return save;
    }
}

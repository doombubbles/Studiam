import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StudiamMenuBar extends JMenuBar{

    public static final int CTRL = Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask();

    private static JFrame parent;

    public StudiamMenuBar(JFrame parent, Screen screen) {
        JMenu fileMenu = new JMenu("File");
        this.parent = parent;

        fileMenu.add(open());
        if (screen instanceof QuizEditorScreen) {
            fileMenu.add(save());
        }

        add(fileMenu);
    }

    private static JMenuItem open() {
        JMenuItem open = new JMenuItem();
        open.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.chooseOpenFile();
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
                if (Main.saveFile()) {
                    JOptionPane.showMessageDialog(null, "Saved!");
                }

            }
        });
        save.setAccelerator(KeyStroke.getKeyStroke('S', CTRL));
        save.setText("Save");
        return save;
    }
}

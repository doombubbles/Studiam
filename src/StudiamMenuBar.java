import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class StudiamMenuBar extends JMenuBar{

    public static final int CTRL = Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask();

    public StudiamMenuBar(JFrame parent) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem();
        open.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new FileNameExtensionFilter("Studiam Quiz Files", "studiam"));
                int result = jFileChooser.showOpenDialog(parent);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = jFileChooser.getSelectedFile();
                    Main.openFile(file);
                }

            }
        });
        open.setAccelerator(KeyStroke.getKeyStroke('O', CTRL));
        open.setText("Open");


        JMenuItem importt = new JMenuItem("Import");
        importt.setText("Import");


        JMenuItem save = new JMenuItem("Save");
        save.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Saved!");
            }
        });
        save.setAccelerator(KeyStroke.getKeyStroke('S', CTRL));
        save.setText("Save");

        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(importt);
        add(fileMenu);
    }
}

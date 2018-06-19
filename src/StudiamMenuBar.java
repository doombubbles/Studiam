/**
 * StudiamMenuBar.java
 * Assignment: Final Project - Studiam
 * Purpose:Studiam would be a tool used to help memorize specific vocabulary for classes and
 * practice for tests, particularly for learning complex languages like Latin. Users would
 * be able to type up or import vocabulary lists for classes into the software and practice
 * with the material. The software would randomly generate mock-up practice tests with all
 * of the content, optionally timed, and we give immediate feedback. It would also have the
 * tools to make the test as “smart” as possible, i.e. accepting multiple potential answers,
 * common spelling mistakes, punctuation and capitalization accepted. The focus would also
 * be on user-friendliness, and much of the development would be focused on making an intuitive,
 * aesthetically pleasing user interface that could be piloted well even without much experience.
 *
 * @version 06/19/18
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

public class StudiamMenuBar extends JMenuBar{

    public static final int CTRL = Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask();

    private static JFrame parent;

    public StudiamMenuBar(JFrame parent, Screen screen) {
        JMenu fileMenu = new JMenu("File");
        this.parent = parent;

        fileMenu.add(open());
        fileMenu.add(nnew());
        if (screen instanceof QuizEditorScreen) {
            fileMenu.add(save());
            fileMenu.add(saveAs());
        }

        add(fileMenu);
    }

    //method for the menu item to create a new file
    private static JMenuItem nnew() {
        JMenuItem open = new JMenuItem();
        open.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.newFile();
            }
        });
        open.setAccelerator(KeyStroke.getKeyStroke('N', CTRL));
        open.setText("New");
        return open;
    }

    //method for the menu item to open a file
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

    //method for the menu item to save a file
    private static JMenuItem save() {
        JMenuItem save = new JMenuItem();
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

    //method for the menu item to save a file as
    private static JMenuItem saveAs() {
        JMenuItem save = new JMenuItem();
        save.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Main.saveFileAs()) {
                    JOptionPane.showMessageDialog(null, "Saved!");
                }

            }
        });
        save.setAccelerator(KeyStroke.getKeyStroke('S', CTRL | InputEvent.SHIFT_MASK));
        save.setText("Save As...");
        return save;
    }
}

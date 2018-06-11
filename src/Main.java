import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String RECENT = "recent";
    public static final Color PURPLE = new Color(150, 0, 200);
    public static final Color LESS_PURPLE = new Color(174, 99, 250);
    public static final Color CLEAR = new Color(0, 0, 0, 0);
    public static final List<Integer> KEY_CODES = Arrays.asList(KeyEvent.VK_0, KeyEvent.VK_1,
            KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6,
            KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);

    private static MainFrame mainFrame;

    public static void main(String[] args){
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public static KeyListener mainKeyListener() {
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (Main.getMainFrame().getCurrentScreen() instanceof QuizEditorScreen) {
                        int hmm = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to go back to the main menu?", "Confirm", JOptionPane.YES_NO_OPTION);
                        if (hmm == JOptionPane.OK_OPTION) {
                            Main.switchScreen(new MainMenuScreen());
                        }
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ALT) {
                    mainFrame.revalidate();
                    mainFrame.pack();
                    mainFrame.repaint();
                    System.out.println("packed");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        return keyListener;
    }

    public static void chooseOpenFile() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Studiam Quiz Files", "studiam"));
        int result = jFileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            openFile(file);
        }
    }


    public static void openFile(File file) {
        QuizFile quizFile = new QuizFile(file);
        QuizEditorScreen quizEditorScreen = new QuizEditorScreen(quizFile);
        switchScreen(quizEditorScreen);

        try {
            File recentFile = new File(RECENT);
            Scanner scanner = new Scanner(recentFile);
            List<String> list = new ArrayList<>();
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
            list.removeIf(s -> s.equals(file.getPath()));
            PrintStream printStream = new PrintStream(new FileOutputStream(recentFile, false));
            printStream.println(quizFile.getPath());
            list.forEach(printStream::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void switchScreen(Screen newScreen) {
        mainFrame.remove(mainFrame.getCurrentScreen());
        mainFrame.setCurrentScreen(newScreen);
        mainFrame.add(newScreen);
        mainFrame.updateMenubar();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public static boolean saveFile() {
        if (!(mainFrame.getCurrentScreen() instanceof QuizEditorScreen)) {
            return false;
        }

        QuizEditorScreen quizEditorScreen = (QuizEditorScreen) mainFrame.getCurrentScreen();
        return quizEditorScreen.saveFile();
    }


    public static boolean saveFileAs() {
        return true;
    }
}

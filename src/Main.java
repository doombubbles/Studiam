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

    public static Color backgroundColor;
    private static MainFrame mainFrame;

    public static void main(String[] args){
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);

        loadSettings();
    }

    public static void loadSettings() {
        try {
            Scanner settingsScanner = new Scanner(new File("settings"));
            while (settingsScanner.hasNextLine()) {
                String line = settingsScanner.nextLine();
                String[] data = line.split(" = ");
                switch (data[0]) {
                    case "backgroundColor":
                        String[] values = data[1].split(", ");
                        backgroundColor = new Color(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]));
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            backgroundColor = CLEAR;
            e.printStackTrace();
        }
    }

    public static KeyListener mainKeyListener() {
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (!(Main.getMainFrame().getCurrentScreen() instanceof MainMenuScreen)) {
                        int hmm = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to go back to the main menu?", "Confirm", JOptionPane.YES_NO_OPTION);
                        if (hmm == JOptionPane.OK_OPTION) {
                            Main.switchScreen(new MainMenuScreen());
                        }
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    mainFrame.requestFocus();
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        return keyListener;
    }

    public static void newFile() {
        Quiz newQuiz = new Quiz("untitled", "What's this quiz about? How should I know, I'm just a line of code in the newFile method");
        newQuiz.add(new QuizSection("default section", new QuizElement("[term]")));

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("New");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Studiam Quiz Files", "studiam"));
        int result = jFileChooser.showSaveDialog(Main.getMainFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            QuizFile quizFile = new QuizFile(file);
            QuizEditorScreen quizEditorScreen = new QuizEditorScreen(quizFile, newQuiz);
            if (mainFrame.getCurrentScreen() instanceof QuizEditorScreen) {
                QuizEditorScreen editorScreen = (QuizEditorScreen) mainFrame.getCurrentScreen();
                if (!editorScreen.saveChangesFirst()) {
                    return;
                }
            }
            switchScreen(quizEditorScreen);
            addRecentFile(quizFile);

        }
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

    public static boolean addRecentFile(QuizFile quizFile) {
        try {
            File recentFile = new File(RECENT);
            Scanner scanner = new Scanner(recentFile);
            List<String> list = new ArrayList<>();
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
            list.removeIf(s -> s.equals(quizFile.getPath()));
            PrintStream printStream = new PrintStream(new FileOutputStream(recentFile, false));
            printStream.println(quizFile.getPath());
            list.forEach(printStream::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static void openFile(File file) {
        QuizFile quizFile = new QuizFile(file);
        QuizEditorScreen quizEditorScreen = new QuizEditorScreen(quizFile, null);
        switchScreen(quizEditorScreen);
        addRecentFile(quizFile);
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
        if (!(mainFrame.getCurrentScreen() instanceof QuizEditorScreen)) {
            return false;
        }

        QuizEditorScreen quizEditorScreen = (QuizEditorScreen) mainFrame.getCurrentScreen();
        return quizEditorScreen.saveFileAs();
    }
}

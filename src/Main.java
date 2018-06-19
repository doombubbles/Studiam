/**
 * Main.java
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

    //method to access the saved settings and load up the results
    public static void loadSettings() {
        try {
            Scanner settingsScanner = new Scanner(new File("settings"));
            while (settingsScanner.hasNextLine()) {
                String line = settingsScanner.nextLine();
                String[] data = line.split(" = ");
                switch (data[0]) {
                    case "backgroundColor":
                        String[] values = data[1].split(", ");
                        backgroundColor = new Color(Integer.parseInt(values[0]),
                                Integer.parseInt(values[1]),
                                Integer.parseInt(values[2]),
                                Integer.parseInt(values[3]));
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

    //method for the main key listener used across super many components throughout the project
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
                                "Are you sure you want to go back to the main menu?",
                                "Confirm", JOptionPane.YES_NO_OPTION);
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

    //method for the key listener specifically used by a lot of textfields throughout the project
    public static KeyListener textFieldKeyListener() {
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    mainFrame.requestFocus();
                    mainFrame.revalidate();
                    mainFrame.repaint();
                    e.consume();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        return keyListener;
    }

    //method to create a new quiz file
    public static void newFile() {
        Quiz newQuiz = new Quiz("untitled", "What's this quiz about? How should I know, " +
                "I'm just a line of code in the newFile method");
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

    //method to choose a quiz file to open
    public static void chooseOpenFile() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Studiam Quiz Files", "studiam"));
        int result = jFileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            openFile(file);
        }
    }

    //method to add a file to the recent file list
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

    //method to open a specific file
    public static void openFile(File file) {
        QuizFile quizFile = new QuizFile(file);
        QuizEditorScreen quizEditorScreen = new QuizEditorScreen(quizFile, null);
        switchScreen(quizEditorScreen);
        addRecentFile(quizFile);
    }

    /*
    public static void undo() {
        if (mainFrame.getScreenHistoryIndex() < mainFrame.getScreenHistory().size()) {
            switchScreen(mainFrame.getScreenHistory().get(mainFrame.getScreenHistoryIndex() - 1), false);
        }

    }

    public static void redo() {
        if (mainFrame.getScreenHistoryIndex() < mainFrame.getScreenHistory().size() - 1) {
            switchScreen(mainFrame.getScreenHistory().get(mainFrame.getScreenHistoryIndex() + 1), false);
        }
    }

    public static void addToScreenHistory(Screen screen) {
        mainFrame.getScreenHistory().add(screen);
        mainFrame.setScreenHistoryIndex(mainFrame.getScreenHistoryIndex() + 1);
    }
    */

    //method to get the main frame of the program
    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    //method to switch to a different screen of the program
    public static void switchScreen(Screen newScreen, boolean log) {
        mainFrame.remove(mainFrame.getCurrentScreen());
        mainFrame.setCurrentScreen(newScreen);
        /*
        if (log) {
            if (mainFrame.getScreenHistoryIndex() < mainFrame.getScreenHistory().size() - 1) {
                mainFrame.getScreenHistory().removeIf(s -> mainFrame.getScreenHistory().indexOf(s) > mainFrame.getScreenHistoryIndex());
            }
            mainFrame.getScreenHistory().add((Screen) newScreen.clone());
        }
        */
        mainFrame.add(newScreen);
        mainFrame.updateMenubar();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    //dummy method for above
    public static void switchScreen(Screen newScreen) {
        switchScreen(newScreen, true);
    }

    //method to save the file currently being edited
    public static boolean saveFile() {
        if (!(mainFrame.getCurrentScreen() instanceof QuizEditorScreen)) {
            return false;
        }

        QuizEditorScreen quizEditorScreen = (QuizEditorScreen) mainFrame.getCurrentScreen();
        return quizEditorScreen.saveFile();
    }


    //method to save the file currently being edited to a different location
    public static boolean saveFileAs() {
        if (!(mainFrame.getCurrentScreen() instanceof QuizEditorScreen)) {
            return false;
        }

        QuizEditorScreen quizEditorScreen = (QuizEditorScreen) mainFrame.getCurrentScreen();
        return quizEditorScreen.saveFileAs();
    }
}

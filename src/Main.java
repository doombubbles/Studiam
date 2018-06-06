import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String RECENT = "recent";
    public static final Color PURPLE = new Color(150, 0, 200);
    public static final Color LESS_PURPLE = new Color(174, 99, 250);
    public static final Color CLEAR = new Color(0, 0, 0, 0);

    private static MainFrame mainFrame;

    public static void main(String[] args){
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }


    public static void openFile() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Studiam Quiz Files", "studiam"));
        int result = jFileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            QuizFile quizFile = new QuizFile(file);
            EditQuizScreen editQuizScreen = new EditQuizScreen(quizFile);
            switchScreen(editQuizScreen);

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
        if (!(mainFrame.getCurrentScreen() instanceof EditQuizScreen)) {
            return false;
        }

        EditQuizScreen editQuizScreen = (EditQuizScreen) mainFrame.getCurrentScreen();
        return editQuizScreen.saveFile();
    }


    public static boolean saveFileAs() {
        return true;
    }
}

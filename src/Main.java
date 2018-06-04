import java.awt.*;

public class Main {

    public static final Color PURPLE = new Color(150, 0, 200);
    public static final Color LESS_PURPLE = new Color(174, 99, 250);

    private static MainFrame mainFrame;

    public static void main(String[] args){
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }


    public static void openFile(QuizFile quizFile) {
        EditQuizScreen editQuizScreen = new EditQuizScreen(quizFile);
        switchScreen(editQuizScreen);
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void switchScreen(Screen newScreen) {
        if (!mainFrame.getScreens().contains(newScreen)) {
            mainFrame.addScreen(newScreen);
        }
        mainFrame.getCurrentScreen().setVisible(false);
        mainFrame.setCurrentScreen(newScreen);
        mainFrame.updateMenubar();
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

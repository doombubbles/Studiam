import java.io.File;

public class Main {

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
    }
}

import java.io.File;

public class Main {

    private static MainFrame mainFrame;

    public static void main(String[] args){
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }


    public static void openFile(File file) {
        EditQuizScreen editQuizScreen = new EditQuizScreen(file);
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
        System.out.println(newScreen.isVisible());
    }
}

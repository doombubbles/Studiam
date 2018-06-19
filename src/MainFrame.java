/**
 * MainFrame.java
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

import sun.awt.image.FileImageSource;
import sun.awt.image.ToolkitImage;

import javax.swing.*;
import java.util.List;

public class MainFrame extends JFrame {

    private Screen currentScreen;

    private List<Screen> screenHistory;
    private int screenHistoryIndex;

    public MainFrame() {
        setTitle("Studiam");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setIconImage(new ToolkitImage(new FileImageSource("img/s3.png")));
        currentScreen = new MainMenuScreen();
        add(currentScreen);
        setJMenuBar(new StudiamMenuBar(this, currentScreen));
        setFocusable(true);
        addKeyListener(Main.mainKeyListener());
    }

    //method to return the current screen of the frame
    public Screen getCurrentScreen() {
        return currentScreen;
    }
    /*
    public void setScreenHistoryIndex(int screenHistoryIndex) {
        this.screenHistoryIndex = screenHistoryIndex;
    }

    public int getScreenHistoryIndex() {
        return screenHistoryIndex;
    }

    public List<Screen> getScreenHistory() {
        return screenHistory;
    }
    */

    //method to set the current screen of the frame
    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

    //method to update the menubar of the frame
    public void updateMenubar() {
        setJMenuBar(new StudiamMenuBar(this, currentScreen));
    }
}

import sun.awt.image.FileImageSource;
import sun.awt.image.ToolkitImage;

import javax.swing.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private ArrayList<Screen> screens;

    private Screen currentScreen;

    public MainFrame() {
        screens = new ArrayList<>();
        init();
    }

    public ArrayList<Screen> getScreens() {
        return screens;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void addScreen(Screen newScreen) {
        add(newScreen);
        screens.add(newScreen);
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

    public void updateMenubar() {
        setJMenuBar(new StudiamMenuBar(this, currentScreen));
    }

    private void init() {
        setTitle("Studiam");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setIconImage(new ToolkitImage(new FileImageSource("s3.png")));
        currentScreen = new MainMenuScreen();
        addScreen(currentScreen);
        setJMenuBar(new StudiamMenuBar(this, currentScreen));
    }
}

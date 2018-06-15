import sun.awt.image.FileImageSource;
import sun.awt.image.ToolkitImage;

import javax.swing.*;
import java.util.List;

public class MainFrame extends JFrame {

    private Screen currentScreen;

    private List<Screen> screenHistory;
    private int screenHistoryIndex;

    public MainFrame() {
        init();
        setFocusable(true);
        addKeyListener(Main.mainKeyListener());
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setScreenHistoryIndex(int screenHistoryIndex) {
        this.screenHistoryIndex = screenHistoryIndex;
    }

    public int getScreenHistoryIndex() {
        return screenHistoryIndex;
    }

    public List<Screen> getScreenHistory() {
        return screenHistory;
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
        setIconImage(new ToolkitImage(new FileImageSource("img/s3.png")));
        currentScreen = new MainMenuScreen();
        add(currentScreen);
        setJMenuBar(new StudiamMenuBar(this, currentScreen));
    }
}

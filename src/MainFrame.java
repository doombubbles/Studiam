import sun.awt.image.FileImageSource;
import sun.awt.image.ToolkitImage;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private Screen currentScreen;

    public MainFrame() {
        init();
        setFocusable(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (Main.getMainFrame().getCurrentScreen() instanceof EditQuizScreen) {
                        int hmm = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to go back to the main menu?", "Warning", JOptionPane.YES_NO_OPTION);
                        if (hmm == JOptionPane.OK_OPTION) {
                            Main.switchScreen(new MainMenuScreen());
                        }
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public Screen getCurrentScreen() {
        return currentScreen;
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
        add(currentScreen);
        setJMenuBar(new StudiamMenuBar(this, currentScreen));
    }
}

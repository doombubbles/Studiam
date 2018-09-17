/**
 * Screen.java
 *
 * A JPanel window that serves as a certain section of the Studiam program
 * Managed by the {@link MainFrame}
 *
 * @version 09/17/18
 */

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {

    protected String screenId = "";

    public Screen() {
        setPreferredSize(new Dimension(800, 600));
    }

    //method to get the id of the screen
    public String getScreenId() {
        return screenId;
    }

    @Override //method to see if this screen equals another object (screen)
    public boolean equals(Object obj) {
        if (obj instanceof Screen) {
            return screenId.equals(((Screen) obj).getScreenId());
        } else return super.equals(obj);
    }

    @Override //stupid method to avoid transparent panel painting artifacts
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = getRootPane().getSize();
        g.drawImage(new ImageIcon("img/background1.png").getImage(),0,0, size.width, size.height,
                this);
        g.setColor(Main.backgroundColor);
        g.fillRect(0, 0, size.width, size.height);
    }
}

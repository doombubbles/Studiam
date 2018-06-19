/**
 * Screen.java
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

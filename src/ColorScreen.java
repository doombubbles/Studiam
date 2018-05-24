import javax.swing.*;
import java.awt.*;

public class ColorScreen extends Screen {

    public ColorScreen(Color color, boolean button) {
        super();
        screenId = color.toString() + "Screen";
        setBackground(color);
        if (button) {
            JButton button1 = new JButton();
            button1.addActionListener(action -> Main.switchScreen(new ColorScreen(Color.BLACK)));
            button1.setText("Woah");
            button1.setPreferredSize(new Dimension(100, 50));
            add(button1);
        }
    }

    public ColorScreen(Color color) {
        this(color, false);
    }
}

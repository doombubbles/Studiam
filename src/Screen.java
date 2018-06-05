import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {

    protected String screenId = "";

    public Screen() {
        setPreferredSize(new Dimension(800, 600));
    }

    public String getScreenId() {
        return screenId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Screen) {
            return screenId.equals(((Screen) obj).getScreenId());
        } else return super.equals(obj);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = getRootPane().getSize();
        g.drawImage(new ImageIcon("background1.png").getImage(),0,0, size.width, size.height, this);

    }


}

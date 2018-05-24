import javax.swing.*;
import java.awt.*;

public class MainMenuScreen extends Screen {

    public MainMenuScreen() {
        setLayout(new BorderLayout());
        screenId = "MainMenu";

        add(new JLabel(new ImageIcon("icon.png")), BorderLayout.SOUTH);
        add(new JLabel(new ImageIcon("icon.png")), BorderLayout.NORTH, 1);
        JPanel centerPanel = new JPanel(new FlowLayout());
        add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(new JLabel(new ImageIcon("icon.png")), BorderLayout.NORTH);



    }


}

import javax.swing.*;
import java.awt.*;

public class OpenFrame extends JFrame {

    public OpenFrame() {
        setTitle("Open...");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.BLUE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public JFileChooser getFileChooser() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showOpenDialog(this);
        return jFileChooser;
    }
}

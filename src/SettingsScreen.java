import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class SettingsScreen extends Screen {

    File settings;

    public SettingsScreen() {
        screenId = "Settings";
        setLayout(new BorderLayout());

        JPanel mainPanel = StudiamFactory.newTransparentPanel();
        add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(backButton());
        JLabel label = new JLabel(new ImageIcon("img/settings.png"));
        label.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(label);

        mainPanel.add(colorButton());

        settings = new File("settings");

    }

    //method to get an outputstream for the settings
    public PrintStream outputStream() {
        try {
            PrintStream outputStream = new PrintStream(new FileOutputStream(settings, false));
            return outputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //method for the button to go back to the main menu
    private JButton backButton() {
        JButton backButton = StudiamFactory.newStudiamButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.switchScreen(new MainMenuScreen());
            }
        });
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setText("Back");
        return backButton;
    }

    //method for the button to change the background color
    private JButton colorButton() {
        JButton colorButton = StudiamFactory.newStudiamButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color chosenColor = JColorChooser.showDialog(Main.getMainFrame(), "Studiam",
                        Main.CLEAR);
                Main.backgroundColor = chosenColor;
                outputStream().println("backgroundColor = " + chosenColor.getRed() + ", "
                        + chosenColor.getGreen()  + ", " + chosenColor.getBlue() + ", " +
                        chosenColor.getAlpha());
                Main.getMainFrame().repaint();
            }
        });
        colorButton.setAlignmentX(CENTER_ALIGNMENT);
        colorButton.setText("Set Background Color");

        return colorButton;
    }

}

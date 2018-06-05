import com.sun.java.swing.plaf.motif.MotifInternalFrameTitlePane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainMenuScreen extends Screen {

    public MainMenuScreen() {
        screenId = "MainMenu";
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0, 0,0, 0));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(new JLabel(new ImageIcon("screenshot.png")), BorderLayout.NORTH);

        mainPanel.add(newButton());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(openButton());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(importButton());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 45)));

        add(mainPanel, BorderLayout.CENTER);

        JPanel recentFiles = recentFiles();
        if (recentFiles != null) {
            add(recentFiles, BorderLayout.SOUTH);
        }
    }

    public void setButtonDefaults(JButton jButton) {
        jButton.setBackground(Main.LESS_PURPLE);
        jButton.setPreferredSize(new Dimension(200, 35));
        jButton.setFont(new Font("Times New Roman", Font.BOLD, 35));
        jButton.setForeground(Color.BLACK);
        jButton.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton.setAlignmentX(CENTER_ALIGNMENT);
    }


    public JButton newButton() {
        JButton newButton = new JButton();
        setButtonDefaults(newButton);
        newButton.setText("New...");
        newButton.setToolTipText("Create a new quiz file from scratch");
        return newButton;
    }

    public JButton openButton() {
        JButton openButton = new JButton();
        openButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.openFile();
            }
        });
        setButtonDefaults(openButton);
        openButton.setText("Open...");
        openButton.setToolTipText("Open a .studiam quiz file");
        return openButton;
    }

    public JButton importButton() {
        JButton importButton = new JButton();
        setButtonDefaults(importButton);
        importButton.setText("Import...");
        importButton.setToolTipText("Import from quizlet or some shit idk");
        return importButton;
    }

    public JPanel recentFiles() {
        JPanel recentFiles = new JPanel();
        recentFiles.setBackground(new Color(0,0,0,0));
        recentFiles.setLayout(new BorderLayout());
        recentFiles.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), "Recent Files", TitledBorder.TOP, TitledBorder.CENTER,
                new Font("Times New Roman", Font.BOLD, 25), Color.BLACK));

        Scanner input;
        try {
            input = new Scanner(new File("recent"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        int i = 0;
        while (input.hasNextLine() && i < 5) {
            String line = input.nextLine();
            JButton fileButton = new JButton();
            fileButton.setBackground(Main.LESS_PURPLE);
            fileButton.setPreferredSize(new Dimension(200, 20));
            fileButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
            fileButton.setForeground(Color.BLACK);
            fileButton.setBorder(BorderFactory.createRaisedBevelBorder());
            fileButton.setText(line);
            recentFiles.add(fileButton, BorderLayout.CENTER);
            i++;
        }

        return recentFiles;
    }




}

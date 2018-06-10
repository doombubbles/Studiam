import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StudiamFactory {

    public static JPanel newTransparentPanel(LayoutManager layoutManager) {
        JPanel jPanel = new JPanel() {
            protected void paintComponent(Graphics g)
            {
                g.setColor( getBackground() );
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        jPanel.setOpaque(false);
        if (layoutManager != null) {
            jPanel.setLayout(layoutManager);
        }
        jPanel.setBackground(Main.CLEAR);
        jPanel.addKeyListener(Main.mainKeyListener());
        return jPanel;
    }

    public static JPanel newTransparentPanel() {
        return newTransparentPanel(null);
    }

    public static JTextArea newStudiamTextArea(String s) {
        return newStudiamTextArea(s,15, null);
    }

    public static JTextArea newStudiamTextArea(String s, int fontSize) {
        return newStudiamTextArea(s, fontSize, null);
    }

    public static JTextArea newStudiamTextArea(String s, int fontSize, Border border) {
        JTextArea jTextArea = new JTextArea(s);
        jTextArea.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
        jTextArea.setSelectionColor(Main.LESS_PURPLE);
        if (border != null) {
            jTextArea.setBorder(border);
        }
        jTextArea.setForeground(Color.BLACK);
        jTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Main.getMainFrame().requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        return jTextArea;
    }

    public static JButton newStudiamButton(AbstractAction action) {
        JButton jButton = new JButton();
        if (action != null) {
            jButton.addActionListener(action);
        }
        jButton.setBackground(Main.LESS_PURPLE);
        jButton.setPreferredSize(new Dimension(200, 35));
        jButton.setFont(new Font("Times New Roman", Font.BOLD, 35));
        jButton.setForeground(Color.BLACK);
        jButton.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return jButton;
    }


}

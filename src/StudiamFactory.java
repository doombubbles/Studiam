import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

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

    public static JTextField newStudiamTextField(String s, int fontSize) {
        return newStudiamTextField(s, fontSize, null);
    }

    public static JTextField newStudiamTextField(String s, int fontSize, Border border) {
        return newStudiamTextField(s, fontSize, border, null);
    }

    public static JTextField newStudiamTextField(String s, int fontSize, Border border, NumberFormat format) {
        JTextField jTextField;
        if (format != null) {
            jTextField = new JFormattedTextField(format);
            jTextField.setText(s);
        } else jTextField = new JTextField(s);

        jTextField.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
        jTextField.setSelectionColor(Main.LESS_PURPLE);
        if (border != null) {
            jTextField.setBorder(border);
        }
        jTextField.setForeground(Color.BLACK);
        jTextField.addKeyListener(new KeyListener() {
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
        return jTextField;
    }

    public static JLabel newStudiamLabel(String s, int fontSize) {
        return newStudiamLabel(s, fontSize, null);
    }

    public static JLabel newStudiamLabel(String s, int fontSize, Border border) {
        JLabel label = new JLabel();
        label.setText(s);
        label.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
        if (border != null) {
            label.setBorder(border);
        }
        label.setForeground(Color.BLACK);
        label.setVisible(true);
        return label;
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

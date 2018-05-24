import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {

    protected String screenId = "";

    public Screen() {
        setPreferredSize(new Dimension(400, 400));
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
}
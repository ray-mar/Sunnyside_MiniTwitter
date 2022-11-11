package Sunnyside_UI;

import javax.swing.*;
import java.awt.*;


public abstract class controlPanel extends JPanel {

    private static final Insets insets = new Insets(0, 0, 0, 0);

    public controlPanel() {
        super(new GridBagLayout());
    }

    public static void addComponent(Container container, Component component, int gridx, int gridy,
            int gridwidth, int gridheight, int anchor, int fill)
    {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
            anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }

}

package Sunnyside_UI;

import Sunnyside_Main.Observer;
import Sunnyside_Main.User;
import Sunnyside_Main.UserGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;


public class Admin extends controlPanel {

    private static Admin INSTANCE;

    private static JFrame frame;
    private JPanel treePanel;
    private JPanel addUserPanel;
    private JPanel openUserViewPanel;
    private JPanel showInfoPanel;

    public static Admin getInstance() {
        if (INSTANCE == null) {
            synchronized (Main.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Admin();
                }
            }
        }
        return INSTANCE;
    }


    private Admin() {
        super();

        init();
        addComponents();
    }

    private void addComponents() {
        addComponent(frame, treePanel, 0, 0, 1, 6, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, addUserPanel, 1, 0, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, openUserViewPanel, 1, 2, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, showInfoPanel, 1, 4, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void init() {
        frame = new JFrame("Sunnyside");
        formatFrame();

        Map<String, Observer> allUsers = new HashMap<>();
        User root = new UserGroup("Root");
        allUsers.put(root.getID(), root);

        treePanel = new TreePanel(root);
        addUserPanel = new addUser(treePanel, allUsers);
        openUserViewPanel = new OpenUserViewPanel(treePanel, allUsers);
        showInfoPanel = new adminStats(treePanel);

        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        InputMap im = (InputMap) UIManager.get("Button.focusInputMap");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "none");
    }

    private void formatFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(800, 400);
        frame.setVisible(true);
    }

}

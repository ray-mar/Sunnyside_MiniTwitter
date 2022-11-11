package Sunnyside_UI;
import Sunnyside_Main.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Map;


public class addUser extends controlPanel {

    private final JPanel treePanel;
    private final Map<String, Observer> allUsers;

    private JButton addUserButton;
    private JButton addGroupButton;
    private JTextField userId;
    private JTextField groupId;


    public addUser(JPanel treePanel, Map<String, Observer> allUsers) {
        super();
        this.treePanel = treePanel;
        this.allUsers = allUsers;

        init();
        addComponents();
    }


    private void addComponents() {
        addComponent(this, userId, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupId, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addGroupButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void init() {
        userId = new JTextField("User ID: ");
        groupId = new JTextField("Group ID: ");
        fieldListener();

        addUserButton = new JButton("Add User");
        initAddButtonListener();

        addGroupButton = new JButton("Add Group");
        initAddGroup();
    }

    private void initAddButtonListener() {
        addUserButton.addActionListener(arg0 -> {
            if (allUsers.containsKey(userId.getText())) {
                errorMessage errorMessage = new errorMessage("Error!", "This girl exists already.\nPlease choose a different user name.",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                User child = new UserLeaf(userId.getText());

                allUsers.put(child.getID(), child);
                ((TreePanel) treePanel).addUserLeaf(child);
            }
        });
    }

    private void initAddGroup() {
        addGroupButton.addActionListener(e -> {
            if (allUsers.containsKey(groupId.getText())) {
                errorMessage errorMessage = new errorMessage("Error!", "This girl exists already.\nPlease choose a different user name.",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                User child = new UserGroup(groupId.getText());

                allUsers.put(child.getID(), child);
                ((TreePanel) treePanel).addUserGroup(child);
            }
        }
        );
    }

    private void fieldListener() {
        userId.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                userId.setText("");
            }


            public void focusLost(FocusEvent e) {
            }

        });

        groupId.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                groupId.setText("");
            }


            public void focusLost(FocusEvent e) {
            }

        });
    }
}



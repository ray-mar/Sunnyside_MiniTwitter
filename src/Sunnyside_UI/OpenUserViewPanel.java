package Sunnyside_UI;

import Sunnyside_Main.Observer;
import Sunnyside_Main.User;
import Sunnyside_Main.UserGroup;
import Sunnyside_Main.UserLeaf;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class OpenUserViewPanel extends controlPanel {

    private JButton openUserViewButton;
    private JPanel spacerPanel;

    private final JPanel treePanel;
    private final Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;

    public OpenUserViewPanel(JPanel treePanel, Map<String, Observer> allUsers) {
        super();

        this.treePanel = treePanel;
        this.allUsers = allUsers;
        init();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, openUserViewButton, 1, 2, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, spacerPanel, 1, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void init() {
        openPanels = new HashMap<>();

        openUserViewButton = new JButton("Open User View");
        initializeOpenUserViewActionListener();

        spacerPanel = new JPanel();
    }

    private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = ((TreePanel) treePanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) Objects.requireNonNull(tree.getSelectionPath()).getLastPathComponent();

        if (!((TreePanel) treePanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }

        return selectedNode;
    }

    private void initializeOpenUserViewActionListener() {
        openUserViewButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = getSelectedNode();

            if (!allUsers.containsKey(((User) selectedNode).getID())) {
                errorMessage errorMsg = new errorMessage("Error!", "Who is that? I don't know her.",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if (selectedNode.getClass() == UserGroup.class) {
                errorMessage errorMsg = new errorMessage("Error!", "This isn't actually Twitter. I can't do this.",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if (openPanels.containsKey(((User) selectedNode).getID())) {
                errorMessage errorMsg = new errorMessage("Error!", "Use your eyes. " + ((User) selectedNode).getID() + "'s window is up.",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if (selectedNode.getClass() == UserLeaf.class) {
                UserView userView = new UserView(allUsers, openPanels, selectedNode);
                openPanels.put(((User) selectedNode).getID(), userView);
            }
        });
    }

}

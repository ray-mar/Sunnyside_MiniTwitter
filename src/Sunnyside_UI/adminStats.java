package Sunnyside_UI;

import Sunnyside_Main.*;
import Sunnyside_Visitors.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;


public class adminStats extends controlPanel {

    private JButton userTotalButton;
    private JButton groupTotalButton;
    private JButton messagesTotalButton;
    private JButton positivePercentageButton;

    private final JPanel treePanel;

    public adminStats(JPanel treePanel) {
        super();

        this.treePanel = treePanel;
        init();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, userTotalButton, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupTotalButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, messagesTotalButton, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, positivePercentageButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void init() {
        userTotalButton = new JButton("Show User Total");
        initUserTotalButton();

        groupTotalButton = new JButton("Show Group Total");
        initGroupButton();

        messagesTotalButton = new JButton("Show Messages Total");
        initMessagesButton();

        positivePercentageButton = new JButton("Show Positive Percentage");
        initPositivity();
    }

    private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = ((TreePanel) treePanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) Objects.requireNonNull(tree.getSelectionPath()).getLastPathComponent();

        if (!((TreePanel) treePanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }

        return selectedNode;
    }

    private void initPositivity() {
        positivePercentageButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = getSelectedNode();

            PositiveMessagesVisitor positiveCountVisitor = new PositiveMessagesVisitor();
            ((User) selectedNode).accept(positiveCountVisitor);
            int positiveCount = positiveCountVisitor.visitUser(((User) selectedNode));

            MessagesVisitor messageCountVisitor = new MessagesVisitor();
            ((User) selectedNode).accept(messageCountVisitor);
            int messageCount = messageCountVisitor.visitUser(((User) selectedNode));

            double percentage = 0;
            if (messageCount > 0) {
                percentage = ((double) positiveCount / messageCount) * 100;
            }

            String percentageString = String.format("%.2f", percentage);
            String message = "What is the POSITIVITY! % of "
                    + ((User) selectedNode).getID() + "'s tweets? "
                    + percentageString + "%";

            errorMessage errorMsg = new errorMessage(((User) selectedNode).getID() + " information",
                    message, JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void initUserTotalButton() {
        userTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                UserTotalVisitor visitor = new UserTotalVisitor();
                ((User) selectedNode).accept(visitor);
                String message = "Here are the amount of people here in "
                        + ((User) selectedNode).getID() + ": "
                        + visitor.visitUser(((User) selectedNode));

                errorMessage errorMsg = new errorMessage(((User) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void initGroupButton() {
        groupTotalButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = getSelectedNode();

            GroupVisitor visitor = new GroupVisitor();
            ((User) selectedNode).accept(visitor);
            String message = "How many groups is "
                    + ((User) selectedNode).getID() + " in? "
                    + visitor.visitUser(((User) selectedNode));

            errorMessage errorMsg = new errorMessage(((User) selectedNode).getID() + " information",
                    message, JOptionPane.INFORMATION_MESSAGE);

        });
    }

    private void initMessagesButton() {
        messagesTotalButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = getSelectedNode();

            MessagesVisitor visitor = new MessagesVisitor();
            ((User) selectedNode).accept(visitor);
            String message = "How much tea did "
                    + ((User) selectedNode).getID() + " spill? "
                    + visitor.visitUser(((User) selectedNode));

            errorMessage errorMsg = new errorMessage(((User) selectedNode).getID() + " information",
                    message, JOptionPane.INFORMATION_MESSAGE);
        });
    }

}

package Sunnyside_UI;

import Sunnyside_Main.*;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;


public class TreePanel extends JPanel {

    private final DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private JTree tree;
    private JScrollPane scrollPane;

    public TreePanel(DefaultMutableTreeNode root) {
        super(new GridLayout(1,0));

        rootNode = root;
        init();
        addComponents();
    }

    private void init() {
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new treeListener());

        tree = new JTree(treeModel);
        formatTree();

        scrollPane = new JScrollPane(tree);
    }

    private void addComponents() {
        add(scrollPane);
    }

    public JTree getTree() {
        return this.tree;
    }

    public DefaultMutableTreeNode getRoot() {
        return this.rootNode;
    }

    private void addUser(DefaultMutableTreeNode parent, DefaultMutableTreeNode child) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = rootNode;
        }

        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        tree.scrollPathToVisible(new TreePath(childNode.getPath()));

        if (parent.getClass() != UserGroup.class) {
            parent = (DefaultMutableTreeNode) parent.getUserObject();
        }
        ((UserGroup) parent).addUserInGroup((User) child);
    }

    public void addUserGroup(DefaultMutableTreeNode child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        }
        else {
            parentNode = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
        }

        if (parentNode.getUserObject().getClass() == UserLeaf.class) {
            parentNode = (DefaultMutableTreeNode) parentNode.getParent();
        }
        addUser(parentNode, child);
    }

    public void addUserLeaf(DefaultMutableTreeNode child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        }
        else {
            parentNode = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
        }

        if (parentNode.getUserObject().getClass() == UserLeaf.class) {
            parentNode = (DefaultMutableTreeNode) parentNode.getParent();
        }
        addUser(parentNode, child);
    }

    private void formatTree() {
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.setSelectionRow(0);
    }

    private static class treeListener implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {}

        public void treeNodesInserted(TreeModelEvent e) {}

        public void treeNodesRemoved(TreeModelEvent e) {}

        public void treeStructureChanged(TreeModelEvent e) {}
    }

}
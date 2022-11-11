package Sunnyside_UI;

import Sunnyside_Main.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;


public class UserView extends controlPanel {

    private static JFrame frame;
    private GridBagConstraints constraints;

    private JTextField followTextField;

    private JTextArea tweetMessage;
    private JTextArea currentlyFollowing;
    private JTextArea newsFeed;

    private JScrollPane tweetMessagePane;
    private JScrollPane followingPane;
    private JScrollPane newsFeedPane;

    private JButton followButton;
    private JButton postTweetButton;

    private Subject user;
    private Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;

    public UserView(Map<String, Observer> allUsers, Map<String, JPanel> allPanels, DefaultMutableTreeNode user) {
        super();

        this.user = (Subject) user;
        this.allUsers = allUsers;
        this.openPanels = allPanels;
        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(frame, followTextField, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, followButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, currentlyFollowing, 0, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, tweetMessagePane, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, postTweetButton, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, newsFeedPane, 0, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        frame = new JFrame("User View");
        formatFrame();

        constraints = new GridBagConstraints();
        constraints.ipady = 100;

        followTextField = new JTextField("User ID: ");
        followButton = new JButton("Follow User");
        initFollowButton();

        currentlyFollowing = new JTextArea("Currently Following: ");
        formatTextArea(currentlyFollowing);
        followingPane = new JScrollPane(currentlyFollowing);
        followingPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        tweetMessage = new JTextArea("Tweet Message: ");
        tweetMessagePane = new JScrollPane(tweetMessage);
        tweetMessagePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        postTweetButton = new JButton("Post Tweet: ");
        initTweetButton();

        newsFeed = new JTextArea("News Feed: ");
        formatTextArea(newsFeed);
        newsFeedPane = new JScrollPane(newsFeed);
        newsFeedPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        updateFollowing();
        updateNewsFeedTextArea();
    }

    private void formatTextArea(JTextArea textArea) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(8);
        textArea.setEditable(false);
    }

    private void formatFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.setTitle(((User) user).getID());

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                openPanels.remove(((User) user).getID());
            }
        });
    }

    private void updateNewsFeedTextArea() {
        StringBuilder list = new StringBuilder("News Feed: \n");

        for (String news : ((UserLeaf) user).getNewsFeed()) {
            list.append(" - ").append(news).append("\n");
        }
        newsFeed.setText(list.toString());
        newsFeed.setCaretPosition(0);
    }

    private void updateFollowing() {
        StringBuilder list = new StringBuilder("Following List \n");
        for (String following : ((UserLeaf) user).getFollowing().keySet()) {
            list.append(" - ").append(following).append("\n");
        }
        currentlyFollowing.setText(list.toString());
        currentlyFollowing.setCaretPosition(0);
    }

    private void initTweetButton() {
        postTweetButton.addActionListener(e -> {
            String newText = tweetMessage.getText().replace("Tweet Message:", "");
            ((UserLeaf) user).sendMessage(newText);

            for (JPanel panel : openPanels.values()) {
                ((UserView) panel).updateNewsFeedTextArea();
            }
        });
    }

    private void initFollowButton() {
        followButton.addActionListener(e -> {
            String newText = followTextField.getText().replace("User ID: ", "");
            User toFollow = (User) allUsers.get(newText);

            if (allUsers.containsKey(newText)) {
                ((Subject) toFollow).attach((Observer) user);
            }
            else if (!allUsers.containsKey(newText)) {
                errorMessage errorMessage = new errorMessage("Error!", "User doesn't exist, sis!",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if (toFollow.getClass() == UserGroup.class) {
                errorMessage errorMessage = new errorMessage("Error!", "Can't follow a group, weenie.",
                        JOptionPane.ERROR_MESSAGE);
            }

            updateFollowing();
        });
    }

}

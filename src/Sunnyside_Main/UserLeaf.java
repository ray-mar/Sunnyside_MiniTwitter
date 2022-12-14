package Sunnyside_Main;

import Sunnyside_Visitors.Visitor;
import java.util.*;

public class UserLeaf extends User implements Subject
{
    private static final List<String> positiveWords = Arrays.asList("good", "wonderful", "lovely", "nice", "great",
            "delightful", "amazing", "mesmerizing", "sexy", "sunny");


    private String latestMessage;
    private int positiveMessageCount;
    private String latestUpdated;

    private final Map<String, Observer> followers;
    private final Map<String, Subject> following;
    private final List<String> newsFeed;
    private final List<String> lastUpdated;


    public UserLeaf(String id) {
        super(id);
        followers = new HashMap<>();
        followers.put(this.getID(), this);
        following = new HashMap<>();
        newsFeed = new ArrayList<>();
        lastUpdated = new ArrayList<>();
    }


    public Map<String, Observer> getFollowers() {
        return followers;
    }

    public Map<String, Subject> getFollowing() {
        return following;
    }

    public List<String> getNewsFeed() {
        return newsFeed;
    }

    public String getLatestMessage() {
        return this.latestMessage;
    }

    public int getPositiveMessageCount() {
        return positiveMessageCount;
    }

    public List<String> getLastUpdated(){return lastUpdated;}

    public String getLatestUpdated(){return latestUpdated;}

    public Long getLastUpdatedTime(){return this.getLastUpdateTime();}

    @Override
    public int getUserGroupCount() {
        return 0;
    }

    @Override
    public int getUserLeafCount() {
        return 1;
    }

    public void sendMessage(String message) {
        this.latestMessage = message;
        this.setMessageCount(this.getMessageCount() + 1);
        this.setLastUpdateTime(System.currentTimeMillis());
        System.out.println(this.getID() + " just sent a message! Update time: " + this.getLastUpdateTime());
        latestUpdated = this.getID();
        if (isPositiveMessage(message)) {
            ++positiveMessageCount;
        }

        notifyObservers();
    }


    @Override
    public boolean contains(String id) {
        return this.getID().equals(id);
    }

    @Override
    public void update(Subject subject) {
        newsFeed.add(0, (((UserLeaf) subject).getID() + ": " + ((UserLeaf) subject).getLatestMessage()));
        lastUpdated.add(0, (((UserLeaf) subject).getID() + ": " + ((UserLeaf) subject).getLastUpdatedTime()));
    }

    @Override
    public void attach(Observer observer) {
        addFollower(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : followers.values()) {
            obs.update(this);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitUserLeaf(this);
    }

    private void addFollower(Observer user) {
        this.getFollowers().put(((User) user).getID(), user);
        ((UserLeaf) user).addUserToFollow(this);
    }

    private void addUserToFollow(Subject toFollow){
        if (toFollow.getClass() == UserLeaf.class) {
            getFollowing().put(((User) toFollow).getID(), toFollow);
        }
    }

    private boolean isPositiveMessage(String message) {
        boolean positive = false;
        message = message.toLowerCase();
        for (String word : positiveWords) {
            if (message.contains(word)) {
                positive = true;
                break;
            }
        }
        return positive;
    }

}

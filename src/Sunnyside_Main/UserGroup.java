package Sunnyside_Main;

import Sunnyside_Visitors.Visitor;

import java.util.HashMap;
import java.util.Map;

public class UserGroup extends User
{

    private final Map<String,User> UserGroups;

    public UserGroup(String id) {
        super(id);
        UserGroups = new HashMap<>();
    }

    public Map<String,User> getUserGroups() {
        return UserGroups;
    }

    public void addUserInGroup(User user) {
        if (!this.contains(user.getID())) {
            this.UserGroups.put(user.getID(), user);
        }
    }

    @Override
    public boolean contains(String id) {
        boolean contains = false;
        for (User user : UserGroups.values()) {
            if (user.contains(id)) {
                contains = true;
            }
        }
        return contains;
    }

    @Override
    public int getUserLeafCount() {
        int count = 0;
        for (User user : this.UserGroups.values()) {
            count += user.getUserLeafCount();
        }
        return count;
    }

    @Override
    public int getUserGroupCount() {
        int count = 0;
        for (User user : this.UserGroups.values()) {
            if (user.getClass() == UserGroup.class) {
                ++count;
                count += user.getUserGroupCount();
            }
        }
        return count;
    }

    @Override
    public int getMessageCount() {
        int msgCount = 0;
        for (User user : this.UserGroups.values()) {
            msgCount += user.getMessageCount();
        }
        return msgCount;
    }

    @Override
    public void update(Subject subject) {
        for (User user : UserGroups.values()) {
            user.update(subject);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        for (User user : UserGroups.values()) {
            user.accept(visitor);
        }
        visitor.visitUserGroup(this);
    }

}

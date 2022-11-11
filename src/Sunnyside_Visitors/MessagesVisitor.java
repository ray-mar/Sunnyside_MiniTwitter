package Sunnyside_Visitors;
import Sunnyside_Main.User;
import Sunnyside_Main.UserLeaf;
import Sunnyside_Main.UserGroup;

public class MessagesVisitor implements Visitor
{
    @Override
    public int visitUser(User user) {
        int count = 0;

        if (user.getClass() == UserLeaf.class) {
            count += visitUserLeaf(user);
        }

        else if (user.getClass() == UserGroup.class) {
            count += visitUserGroup(user);
        }

        return count;
    }

    @Override
    public int visitUserLeaf(User user) {
        return user.getMessageCount();
    }

    @Override
    public int visitUserGroup(User user) {
        int count = 0;

        for (User u : ((UserGroup) user).getUserGroups().values()) {
            count += visitUser(u);
        }

        return count;
    }

}

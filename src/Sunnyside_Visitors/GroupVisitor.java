package Sunnyside_Visitors;
import Sunnyside_Main.User;
import Sunnyside_Main.UserLeaf;
import Sunnyside_Main.UserGroup;

public class GroupVisitor implements Visitor
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
        return 0;
    }

    @Override
    public int visitUserGroup(User user) {
        int count = 0;

        for (User u : ((UserGroup) user).getUserGroups().values()) {
            if (u.getClass() == UserLeaf.class) {
                ++count;
            }
            count += visitUser(u);
        }

        return count;
    }

}

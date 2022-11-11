package Sunnyside_Visitors;
import Sunnyside_Main.User;

public interface Visitor {

    public int visitUser(User user);
    public int visitUserLeaf(User user);
    public int visitUserGroup(User user);

}

package Sunnyside_Main;
import Sunnyside_Visitors.Visitor;
import javax.swing.tree.DefaultMutableTreeNode;

public abstract class User extends DefaultMutableTreeNode implements Observer
{
    private final String id;
    private int messageCount;

    public abstract boolean contains(String id);
    public abstract int getUserLeafCount();
    public abstract int getUserGroupCount();

    public User(String id) {
        super(id);
        this.id = id;
        this.setMessageCount(0);
    }

    public String getID()
    {
        return id;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public abstract void accept(Visitor visitor);

}

package Sunnyside_Main;
import Sunnyside_Visitors.Visitor;
import javax.swing.tree.DefaultMutableTreeNode;

public abstract class User extends DefaultMutableTreeNode implements Observer
{
    private final String id;
    private int messageCount;
    private long creationTime;
    private long lastUpdateTime = 0;

    public abstract boolean contains(String id);
    public abstract int getUserLeafCount();
    public abstract int getUserGroupCount();


    public User(String id) {
        super(id);
        this.id = id;
        this.setMessageCount(0);
        this.creationTime = System.currentTimeMillis();
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

    public long getCreationTime(){ return creationTime;}

    public long getLastUpdateTime(){ return lastUpdateTime;}

    public void setLastUpdateTime(long newTime){ lastUpdateTime = newTime;}

}

package groupme.teamspoiler.android.myapplication.DataElements;

/**
 * Created by hirats on 4/3/2016.
 */
public class Friend implements IDable {
    private int id;
    private String name;
    private String username;

    public Friend(int id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }
    public Friend(String name, String username) {
        this(0, name, username);
    }

    //ID can only be set once
    public int getID() {return id;}
    public void setID(int id) {
        this.id = id;
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
}

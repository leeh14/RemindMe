package teamspoiler.renameme.DataElements;

/**
 * Created by hirats on 4/3/2016.
 */
public class Friend implements IDable {
    private int id;
    private String name;
    private String username;

    //constructor
    public Friend(int id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }
    public Friend(String name, String username) {
        this(0, name, username);
    }

    //get and set ID
    public int getID() {return id;}
    public void setID(int id) {
        this.id = id;
    }
    //get and set name
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    //get and set username
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String toString() {return name;}
}

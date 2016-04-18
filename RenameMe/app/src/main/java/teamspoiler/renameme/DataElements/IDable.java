package teamspoiler.renameme.DataElements;

/**
 * Created by hirats on 4/3/2016.
 */

//Any object that is IDable has an unique ID number, useful for database access
public interface IDable {
    public int getID();
    public void setID(int id);
}

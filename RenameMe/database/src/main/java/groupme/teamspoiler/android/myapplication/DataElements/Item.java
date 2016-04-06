package groupme.teamspoiler.android.myapplication.DataElements;

import org.joda.time.LocalDateTime;

/**
 * Created by hirats on 4/3/2016.
 */
public class Item implements IDable {
    private int id;
    private String name;
    private LocalDateTime date;
    private String note;
    private int categoryID;

    public Item(int id, String name, int categoryID) {
        this.id = id;
        this.name = name;
        this.categoryID = categoryID;
    }
    public Item(String name, int categoryID) {
        this(0, name, categoryID);
    }

    //ID can only be set once
    public int getID() {return id;}
    public void setID(int id){
        this.id = id;
    }
    public int getCategoryID() {return categoryID;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public LocalDateTime getDate() {return date;}
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getNote() {return note;}
    public void setNote(String note) {this.note = note;}

    public int compareTo(Object o) {
        Item comparedTo = (Item)o;
        return (this.getID() < comparedTo.getID() ? -1 : (this.getID() == comparedTo.getID() ? 0 : 1));
    }
}

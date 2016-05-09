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
    //categoryID is the ID of the category the item belongs to
    private int categoryID;

    //constructor
    public Item(int id, String name, int categoryID) {
        this.id = id;
        this.name = name;
        this.categoryID = categoryID;
    }
    public Item(String name, int categoryID) {
        this(0, name, categoryID);
    }

    //get and set id
    public int getID() {return id;}
    public void setID(int id){
        this.id = id;
    }
    //get categoryID
    public int getCategoryID() {return categoryID;}
    //get and set name
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    //get and set date
    public LocalDateTime getDate() {return date;}
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    //get and set note
    public String getNote() {return note;}
    public void setNote(String note) {this.note = note;}

    public String toString() {return name;}
}

package teamspoiler.renameme.DataElements;

import java.util.Iterator;

/**
 * Created by hirats on 4/3/2016.
 */
public class Category implements IDable, Iterable<Item> {
    private int id;
    private String name;
    //items stores all the items in the category
    private IterableMap<Item> items;

    //constructor
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        items = new IterableMap<Item>();
    }
    public Category(String name) {
        this(0, name);
    }

    //get and set id
    public int getID() {return id;}
    public void setID(int id){
        this.id = id;
    }
    //get and set name
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    //access the items that belong to the category
    public int itemCount() {return items.size();}
    public boolean addItem(Item item) {return items.add(item);}
    public Item getItem(int id) {return items.get(id);}
    public Item deleteItem(int id) {return items.delete(id);}

    public Iterator<Item> iterator() {
        return items.iterator();
    }

    public String toString() {return name;}
}

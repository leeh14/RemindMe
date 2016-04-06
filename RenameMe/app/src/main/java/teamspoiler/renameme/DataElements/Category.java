package teamspoiler.renameme.DataElements;

import java.util.Iterator;

/**
 * Created by hirats on 4/3/2016.
 */
public class Category implements IDable, Iterable<Item> {
    private int id;
    private String name;
    private IterableMap<Item> items;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        items = new IterableMap<Item>();
    }
    public Category(String name) {
        this(0, name);
    }

    //ID can only be set once
    public int getID() {return id;}
    public void setID(int id){
        this.id = id;
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int itemCount() {return items.size();}
    public boolean addItem(Item item) {return items.add(item);}
    public Item getItem(int id) {return items.get(id);}
    public Item deleteItem(int id) {return items.delete(id);}

    public Iterator<Item> iterator() {
        return items.iterator();
    }
    public String toString() {return name;}
}

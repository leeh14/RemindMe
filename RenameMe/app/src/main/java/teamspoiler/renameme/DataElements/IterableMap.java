package teamspoiler.renameme.DataElements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by hirats on 4/3/2016.
 */
/*IterableMap stores a group of categories, friend, or items
It is useful because accessing these IDable items is done by
passing an unique id number.
*/
public class IterableMap<T extends IDable> implements Iterable<T> {
    //The underlying data structure is a map
    private HashMap<Integer, T> map;

    //constructor
    public IterableMap() {
        map = new HashMap<Integer, T>();
    }

    //access the contents of the map
    public int size() {return map.size();}
    public boolean add(T obj) {return map.put(obj.getID(), obj) != obj;}
    public T get(int id) {return map.get(id);}
    public T delete(int id) {return map.remove(id);}

    //allows iteration through the map
    public Iterator<T> iterator() {
        return new MapIterator();
    }
    //Iterator object
    public class MapIterator implements Iterator<T> {
        private Iterator<Map.Entry<Integer, T> > it;
        public MapIterator() {
            it = map.entrySet().iterator();
        }
        public boolean hasNext() {
            return it.hasNext();
        }
        public T next() {
            return it.next().getValue();
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    //Returns the contents as a list of IDable objects
    public List<T> toList() {
        List<T> list = new ArrayList<T>();
        for (T obj : this)
            list.add(obj);
        return list;
    }
}

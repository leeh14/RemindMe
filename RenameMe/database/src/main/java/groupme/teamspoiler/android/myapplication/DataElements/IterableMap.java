package groupme.teamspoiler.android.myapplication.DataElements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hirats on 4/3/2016.
 */
public class IterableMap<T extends IDable> implements Iterable<T> {
    private HashMap<Integer, T> map;

    public IterableMap() {
        map = new HashMap<Integer, T>();
    }

    public boolean add(T obj) {return map.put(obj.getID(), obj) != obj;}
    public T get(int id) {return map.get(id);}
    public T delete(int id) {return map.remove(id);}

    public Iterator<T> iterator() {
        return new MapIterator();
    }
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
}

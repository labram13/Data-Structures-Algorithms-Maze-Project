package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;

    // You may add extra fields or helper methods though!
    private int numEntries;
    private double lambda;

    /**
     * Constructs a new ArrayMap with default initial capacity.
     */
    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
        numEntries = 0;
        lambda = .75;


    }

    /**
     * Constructs a new ArrayMap with the given initial capacity (i.e., the initial
     * size of the internal array).
     *
     * @param initialCapacity the initial capacity of the ArrayMap. Must be > 0.
     */
    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override

    public V get(Object key) {
        // check if there are entries. if none, return null
        if (numEntries == 0) {
            return null;
        }
        //loop through all entries
        for (int i = 0; i < numEntries; i++) {
            //check if each entry key matches parameter key
            //if so, return value of that key
            if (entries[i].getKey().equals(key)) {
                return entries[i].getValue();
            }
        }
        //return null if coming out of for loop without finding matches
        return null;
    }



    @Override
    public V put(K key, V value) {
        //check for 0 entries. if none, put at beginning index and return null
        if (numEntries == 0) {
           entries[0] = new SimpleEntry<>(key, value);
           numEntries++;
           return null;
        } else {
            //other case is numelems is 1 or more
            //first check if need to resize (bigger than or equal to lambda)
            if ((double) numEntries / entries.length >= lambda) {
                resizeHelper();
            }
            //loop through entries. if key matches key at index, replace value
            // and return old value.
            for (int i = 0; i < numEntries; i++) {
                if (entries[i].getKey().equals(key)) {
                    return entries[i].setValue(value);
                }
            }

            //if no key found, add entry to end of entry and increment size
            entries[numEntries] = new SimpleEntry<>(key, value);
            numEntries++;
            return null;

        }
    }

    @Override
    public V remove(Object key) {
        //return null if no entries exist
        if (numEntries == 0) {
            return null;
        }
        //record last entry index
        int lastEntryIndex = numEntries - 1;

        //loop through entries to find key
        for (int i = 0; i < numEntries; i++) {
            //check if key matches and is before last key in entry.
            //if so, decrement size, replace key and value with last key and value
            //delete last entry
            if (entries[i].getKey().equals(key)) {
                if (i < lastEntryIndex) {
                    V lastVal = entries[i].getValue();
                    entries[i] = entries[lastEntryIndex];
                    entries[lastEntryIndex] = null;
                    numEntries--;
                    return lastVal;
                } else {
                    //if it's the last entry, return old value and null the index.
                    V lastVal = entries[i].getValue();
                    entries[i] = null;
                    numEntries--;
                    return lastVal;
                }
            }
        }
        return null;
    }

    @Override
    public void clear() {
        //record current capacity and recreate. reset numEntries to 0.
        int currCapacity = entries.length;
        entries = createArrayOfEntries(currCapacity);
        numEntries = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        //if no entries, just return false
        if (numEntries == 0) {
            return false;
        }

        //otherwise, loop through all to find matching key. return true if found
        for (int i = 0; i < numEntries; i++) {
            if (entries[i].getKey().equals(key)) {
                return true;
            }
        }

        //if no match found, return false.
        return false;
    }

    @Override
    public int size() {
         return numEntries;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: You may or may not need to change this method, depending on whether you
        // add any parameters to the ArrayMapIterator constructor.
        return new ArrayMapIterator<>(this.entries);
    }

    // Doing so will give you a better string representation for assertion errors the debugger.
    @Override
    public String toString() {
        return super.toString();
    }

    private void resizeHelper() {
        //resize with added 10 to current capacity
        SimpleEntry<K, V>[] temp = createArrayOfEntries(entries.length + 10);
        for (int i = 0; i < entries.length; i++) {
            temp[i] = entries[i];
        }
        entries = temp;
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        // You may add more fields and constructor parameters
        private int index;



        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
            this.index = 0;

        }
        @Override
        public boolean hasNext() {
            return index < entries.length && entries[index] != null;
        }



        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int prevIndex = index;
            index++;
            return entries[prevIndex];
        }
    }
}

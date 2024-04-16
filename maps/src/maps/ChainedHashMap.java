package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = .75;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 10;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 5;




    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;
    // You're encouraged to add extra fields (and helper methods) though!
    private double loadFactor; //number of  elements in a chain
    private int chainMapCapacity; // number of buckets of chainmap
    private int arrayMapCapacity; //size of arraymap

    private int numElems;
    private int numBuckets;


    /**
     * Constructs a new ChainedHashMap with default resizing load factor threshold,
     * default initial chain count, and default initial chain capacity.
     */
    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);

    }

    /**
     * Constructs a new ChainedHashMap with the given parameters.
     *
     * @param resizingLoadFactorThreshold the load factor threshold for resizing. When the load factor
     *                                    exceeds this value, the hash table resizes. Must be > 0.
     * @param initialChainCount the initial number of chains for your hash table. Must be > 0.
     * @param chainInitialCapacity the initial capacity of each ArrayMap chain created by the map.
     *                             Must be > 0.
     */
    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
       loadFactor = resizingLoadFactorThreshold;
       chainMapCapacity = initialChainCount;
       arrayMapCapacity = chainInitialCapacity;
       chains = createArrayOfChains(chainMapCapacity);
       numElems = 0;
    }


    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    @Override
    public V get(Object key) {
        int hashIndex = Math.abs(key.hashCode() % chains.length);
        if (chains[hashIndex] == null) {
            return null;
        }
        return chains[hashIndex].get(key);
    }

    @Override
    public V put(K key, V value) {
        //hash the key
        //check index if null or contains elements
        // int hashIndex = Math.abs(key.hashCode() % chains.length);
        //if null, create new array map and put new (key, value)

        if (size() / chains.length >= loadFactor) {
            resizeHelper();
            // hashIndex = Math.abs(key.hashCode() % chains.length);
            // loadFactor *= 2;
        }
        int hashIndex = Math.abs(key.hashCode() % chains.length);

        if (chains[hashIndex] == null) {
            chains[hashIndex]= new ArrayMap<>(arrayMapCapacity);
            chains[hashIndex].put(key, value);
            numElems++;
            numBuckets++;
            return null;
        }

        else {
            if (chains[hashIndex].containsKey(key)) {
                return chains[hashIndex].put(key, value);
            } else {
                numElems++;
                return chains[hashIndex].put(key, value);
            }
        }
    }

    @Override
    public V remove(Object key) {
        int hashIndex = Math.abs(key.hashCode() % chains.length);
            if (chains[hashIndex]==null) {
                return null;
            } else {
                numElems--;
                return chains[hashIndex].remove(key);
            }

    }

    @Override
    public void clear() {
        numElems = 0;
        chains = createArrayOfChains(chains.length);
    }

    @Override
    public boolean containsKey(Object key) {
        int hashIndex = Math.abs(key.hashCode() % chains.length);
        if (chains[hashIndex]==null) {
            return false;
        }
        return chains[hashIndex].containsKey(key);
    }

    @Override
    public int size() {
        return numElems;
    }


    private void resizeHelper() {
        AbstractIterableMap<K, V>[] newChains = createArrayOfChains(chains.length * 2);

        for (int i = 0; i < chains.length; i++) {
            //if index not null, loop through all elements within arraymaps
            //recalculate each key's hashcode
            //assign it to new hash index of newChains[];
            if (chains[i] != null) {
                Iterator<Map.Entry<K, V>> iterator = chains[i].iterator();
                while (iterator.hasNext()) {
                    Entry<K, V> currElem = iterator.next();
                    int newHashCode = Math.abs(currElem.getKey().hashCode() % newChains.length);
                    if (newChains[newHashCode] != null) {
                        newChains[newHashCode].put(currElem.getKey(), currElem.getValue());
                    } else {
                        newChains[newHashCode] = new ArrayMap<>(arrayMapCapacity);
                        newChains[newHashCode].put(currElem.getKey(), currElem.getValue());
                    }

                }
            }
        }
        chains = newChains;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }

    // Doing so will give you a better string representation for assertion errors the debugger.
    @Override
    public String toString() {
        return super.toString();
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        // You may add more fields and constructor parameters
        int index;
        private Iterator<Map.Entry<K, V>> currIterator;

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            this.index = 0;
            currIterator = null;
        }

        @Override
        public boolean hasNext() {
            while (index < chains.length) {
                if (chains[index] != null) {
                    currIterator = chains[index].iterator();
                    if (currIterator.hasNext()) {
                        return true;
                    }
                }
                index++;
            }
            return false;
        }


        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Entry<K, V> elem = currIterator.next();

            if (!currIterator.hasNext()) {
                // currIterator = null;
                index++;
            }

            return elem;

        }

        }
}


//index to keep track of where we are in the hashtable - whether we looped thru all
//hasNext returns index!=chains.length


//
//hasNext()
//  checks if indices in hashtable is null or not
    //iterator that starts from 0 and checks

//next():
    //if hasNext() true --> using second iterator to iterate through the index's arrayMaap by accessing index
    //if false, index ++


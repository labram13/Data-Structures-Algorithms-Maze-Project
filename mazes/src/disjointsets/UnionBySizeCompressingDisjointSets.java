package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A quick-union-by-size data structure with path compression.
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;
    HashMap<T, Integer> itemIndexes;
    int size;

    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        pointers = new ArrayList<>();
        itemIndexes = new HashMap<>();
        size = 0;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void makeSet(T item) {
        if (itemIndexes.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        itemIndexes.put(item, size);
        pointers.add(-1);
        size++;
    }

    @Override
    public int findSet(T item) {

        if (!itemIndexes.containsKey(item)) {
            throw new IllegalArgumentException();
        }

        int index = itemIndexes.get(item);
        while (pointers.get(index) >= 0) {
            index  = pointers.get(index);
        }

        //path compression (loop through nodes again and reassign index value?)
        int currIndex = itemIndexes.get(item);
        while (pointers.get(currIndex) > 0) {
            int nextIndex = pointers.get(currIndex); //store parent index
            pointers.set(currIndex, index); //change index value to new parent (overall root)
            currIndex = nextIndex; // change to next parent index
        }

        return index; //return overall root index found in first while loop

    }

    @Override
    public boolean union(T item1, T item2) {
        //check if both items are in the same set, if so, no union needed
        // if not, find index location of each item's overall root
        // compare sizes of the overall root
        //smaller set's overall root points to the larger set's overall root
        int indexOne = findSet(item1);
        int indexTwo = findSet(item2);
        if (indexOne != indexTwo) {
            int itemWeightOne = pointers.get(indexOne);
            int itemWeightTwo = pointers.get(indexTwo);
            int newWeight = itemWeightOne + itemWeightTwo;
            if (itemWeightOne > itemWeightTwo) {
                pointers.set(indexOne, indexTwo);
                pointers.set(indexTwo, newWeight);
            } else {
                pointers.set(indexTwo, indexOne);
                pointers.set(indexOne, newWeight);

            }
            return true;


        }
        return false;
    }

}

package priorityqueues;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashMap;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 0;
    List<PriorityNode<T>> items;
    private int numItems;
    private int nextOpenIndex;
    HashMap<T, Integer> itemIndices = new HashMap<>();

    //[0, 1, 2, 3]
    //3's index = 3
    //i
    HashSet<T> itemSet;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        numItems = 0;
        itemSet = new HashSet<>();

    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode<T> child = items.get(a); //store currIndex node
        PriorityNode<T> parent = items.get(b);
        // items.set(index, item);
        items.set(a, parent);
        items.set(b, child);
        itemIndices.put(child.getItem(), b);
        itemIndices.put(parent.getItem(), a);

    }

    @Override
    public void add(T item, double priority) {
        // if (itemSet.contains(item)) {
        //     throw new IllegalArgumentException();
        // }
        if (itemIndices.containsKey(item)) {
            throw new IllegalArgumentException();
        }


        if (size() == 0) {
            items.add(new PriorityNode<>(item, priority));
            // itemSet.add(item);
            itemIndices.put(item, 0);
            numItems++;
        }
        //add node right off the bat
        //executes only if there are 2 lodes or less
        else if (numItems < 3) {
            items.add(new PriorityNode<>(item, priority));
            numItems++;
            itemIndices.put(item, numItems - 1);
            // itemSet.add(item);
            //compare priorities
            double parentPriority = items.get(0).getPriority();
            if (parentPriority > priority) {
                swap(numItems - 1, 0);

            }
        } else {
            //[1, 2, 3, 4, 5, 6] 2 is 4's parent
            items.add(new PriorityNode<>(item, priority));
            // itemSet.add(item);
            numItems++;
            itemIndices.put(item, numItems - 1);
            // int currIndex = numItems - 1;
            // int parentIndex = (currIndex - 1) / 2;
            percolateUp(numItems - 1);
        }

    }

    private void percolateUp(int currIndex) {
        int parentIndex = (currIndex - 1) / 2;
        while (items.get(parentIndex).getPriority() > items.get(currIndex).getPriority()) {
            swap(currIndex, parentIndex);
            currIndex = parentIndex;
            parentIndex = (currIndex - 1) / 2;
        }
    }

    @Override
    public boolean contains(T item) {
        return itemIndices.containsKey(item);
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public T peekMin() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return items.get(0).getItem();
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public T removeMin() {
        //
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T temp = items.get(0).getItem();
        swap(0, size() - 1);
        items.remove(size() - 1);
        // itemSet.remove(temp);
        itemIndices.remove(temp);
        numItems--;
        percolateDown(0);
        return temp;

    }

    private void percolateDown(int currIndex) {
        // int currIndex = 1;
        int leftIndex = (2 * currIndex) + 1;
        int rightIndex = (2 * currIndex) + 2;

        while ((currIndex <= numItems - 1 && leftIndex <= numItems - 1 && rightIndex <= numItems - 1) &&
            (items.get(currIndex).getPriority() > items.get(leftIndex).getPriority() ||
            items.get(currIndex).getPriority() > items.get(rightIndex).getPriority())) {
            if (items.get(leftIndex).getPriority() < items.get(rightIndex).getPriority()) {
                swap(currIndex, leftIndex);
                currIndex = leftIndex;
                leftIndex = (2 * currIndex) + 1;
                rightIndex = (2 * currIndex) + 2;
            } else {
                swap(currIndex, rightIndex);
                currIndex = rightIndex;
                leftIndex = (2 * currIndex) + 1;
                rightIndex = (2 * currIndex) + 2;
            }
        }

        while (currIndex <= numItems - 1 && leftIndex <= numItems - 1) {
            if (items.get(currIndex).getPriority() > items.get(leftIndex).getPriority()) {
                swap(currIndex, leftIndex);
                currIndex = leftIndex;
                leftIndex = (2 * currIndex) + 1;
            } else {
                break;
            }
        }
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!itemIndices.containsKey(item)) {
            throw new NoSuchElementException();
        }


        // change priority of item.
        int currIndex = itemIndices.get(item);
        items.get(currIndex).setPriority(priority);
        percolateDown(currIndex);
        percolateUp(currIndex);


        //case 1: percolate down //check if children exist//check if greater than children

        //case 2: percolate up // check if parent exists// check if smaller than parent

        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int size() {
        return numItems;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

}

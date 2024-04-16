package deques;

/**
 * @see Deque
 */
public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    // Feel free to add any additional fields you may need, though.




    public LinkedDeque() {
        size = 0;
        front = new Node<T>(null, null, null);
        back = new Node<T>(null, null, null);

        front.next= back;
        back.prev=front;
    }

    public void addFirst(T item) {
        size += 1;
        if (front.next == null) {
            front.next = new Node<T>(item, front, back);
            back.prev = front.next;
        } else {
            Node<T> x = new Node<T>(item, front, front.next);
            front.next = x;
            front.next.prev = front;
            x.next.prev = x;
        }
    }

    public void addLast(T item) {
        size += 1;
        if (front.next==null) {
            back.prev = new Node<T>(item, front, back);
            back.prev.next = back;
        } else {
            Node<T> sentBack = new Node<T>(item, back.prev, back);
            back.prev.next = sentBack;
            back.prev = sentBack;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else if (size <= 1) {
            size -= 1;
            T val = front.next.value;
            front.next=back;
            back.prev=front;
            return val;
        } else {
            size -= 1;
            T val = front.next.value;
            front.next = front.next.next;
            front.next.prev = front;
            return val;
        }

    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        // size -= 1;
        //T val = back.prev.value;
        else if (size <= 1) {
            size -= 1;
            T val = back.prev.value;
            back.prev=front;
            front.next = back;

            return val;

        } else {
            T val = back.prev.value;
            back.prev = back.prev.prev;
            back.prev.next=back;
            size -= 1;
            return val;

        }
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }


        if (size <= 0) {
            return null;

        } else {

            if (index <= size / 2) {
                int counter = 0;
                Node<T> curr = front.next;
                while (curr != null) {
                    if (counter == index) {
                        return curr.value;
                    } else {
                        counter++;
                        curr = curr.next;
                    }
                }
            } else if (index > size / 2) {
                int counter = size-1;
                Node<T> curr = back.prev;
                while (curr != null) {
                    if (counter == index) {
                        return curr.value;
                    } else {
                        counter--;
                        curr = curr.prev;
                    }
                }
            }
        }
        return null;
    }

    public int size() {
        return size;
    }
}



package deques;

import org.junit.jupiter.api.Test;

public class LinkedDequeTests extends BaseDequeTests {
    public static <T> LinkedDequeAssert<T> assertThat(LinkedDeque<T> deque) {
        return new LinkedDequeAssert<>(deque);
    }

    @Override
    protected <T> Deque<T> createDeque() {
        return new LinkedDeque<>();
    }

    @Override
    protected <T> void checkInvariants(Deque<T> deque) {
        // cast so we can use the LinkedDeque-specific version of assertThat defined above
        assertThat((LinkedDeque<T>) deque).isValid();
    }

    @Test
    void testConstructor() {
        Deque<Integer> deque = createDeque();
        // LinkedDeque<T> x =

    }
    @Test
    void testAddVeryFirstItem() {
        Deque<Integer> deque = createDeque();
        deque.addFirst(1);


    }
    @Test void testAddFirstItems() {
        Deque<Integer> deque = createDeque();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
    }

    @Test
    void testAddLastItem() {
        Deque<Integer> deque = createDeque();
        deque.addLast(1);
    }

    @Test
    void testAddLastItems() {
        Deque<Integer> deque = createDeque();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
    }

    @Test
    void testAddFirstLastItem() {
        Deque<Integer> deque = createDeque();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(3);
        deque.addLast(4);

    }
    @Test
    void testAddLastFirstItem() {
        Deque<Integer> deque = createDeque();
        deque.addLast(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.addFirst(4);

    }
    @Test
    void testRemoveFirstItem() {
        Deque<Integer> deque = createDeque();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.addLast(5);
        assertThat(deque.removeFirst()).isEqualTo(3);
        assertThat(deque.removeFirst()).isEqualTo(2);
        assertThat(deque.removeFirst()).isEqualTo(1);
        assertThat(deque.removeFirst()).isEqualTo(4);
        assertThat(deque.removeFirst()).isEqualTo(5);

    }
    @Test
    void testRemoveLastItem() {
        Deque<Integer> deque = createDeque();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertThat(deque.removeLast()).isEqualTo(3);
        assertThat(deque.removeLast()).isEqualTo(2);
        assertThat(deque.removeLast()).isEqualTo(1);
    }

    @Test
    void testAddRemove() {
        Deque<Integer> deque = createDeque();
        deque.addLast(1);
        assertThat(deque.size()).isEqualTo(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        assertThat(deque.size()).isEqualTo(6);

        assertThat(deque.removeFirst()).isEqualTo(6);
        assertThat(deque.removeFirst()).isEqualTo(5);
        assertThat(deque.removeFirst()).isEqualTo(4);
        assertThat(deque.removeLast()).isEqualTo(3);
        assertThat(deque.removeLast()).isEqualTo(2);
        assertThat(deque.removeLast()).isEqualTo(1);
        assertThat(deque.size()).isEqualTo(0);

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
    }
    @Test
    void testGet() {
        Deque<Integer> deque = createDeque();
        deque.addLast(2);
        deque.addLast(3);
        // deque.get(1);
        // deque.get(2);

        assertThat(deque.get(0)).isEqualTo(2);
        assertThat(deque.get(1)).isEqualTo(3);
        assertThat(deque.get(-1)).isEqualTo(null);
        assertThat(deque.get(100)).isEqualTo(null);

        deque.removeLast();
        deque.removeLast();
        assertThat(deque.get(0)).isEqualTo(null);
    }

    @Test
    void removeTestNull() {
        Deque<Integer> deque = createDeque();

       deque.addFirst(1);
       deque.addLast(2);
        deque.addFirst(1);
       deque.removeFirst();
       deque.removeLast();

    }

    @Test
    void getEachTest() {
        Deque<Integer> deque = createDeque();
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addFirst(0);

        assertThat(deque.get(5)).isEqualTo(5);
        assertThat(deque.get(0)).isEqualTo(0);

        for (int i = 0; i < deque.size(); i++) {
            System.out.println(deque.get(i));
        }
    }
    @Test
    void removeAfterRemove() {
            Deque<Integer> deque = createDeque();
            deque.addFirst(1);
            deque.addLast(2);

            // deque.addFirst(3);
            // deque.addLast(4);
            deque.removeFirst();
            deque.removeLast();
            // assertThat(deque.removeFirst()).isEqualTo(1);
            // assertThat(deque.removeLast()).isEqualTo(2); //4

        }
    }
    // You can write additional tests here if you only want them to run for LinkedDequeTests and not ArrayDequeTests


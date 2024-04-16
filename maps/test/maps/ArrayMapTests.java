package maps;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Map;

public class ArrayMapTests extends BaseMapTests {
    @Override
    protected <K, V> Map<K, V> createMap() {
        return new ArrayMap<>();
    }

    protected <K, V> Map<K, V> createMap(int capacity) {
        return new ArrayMap<>(capacity);
    }

    @Test
    void iterator_hasNext_afterExhausted_whenArrayIsFull_returnsFalse() {
        int capacity = 373;
        Map<Integer, Integer> map = createMap(capacity);

        // fill array to capacity - 1
        for (int i = 0; i < capacity - 1; i++) {
            map.put(i, i);
        }

        // make sure iterator works during capacity - 1
        Iterator<Map.Entry<Integer, Integer>> iterator1 = map.entrySet().iterator();
        exhaust(iterator1);
        assertThat(iterator1).as("size == capacity - 1").isExhausted();

        // makes sure iterator works during full capacity
        map.put(-1, -1);
        Iterator<Map.Entry<Integer, Integer>> iterator2 = map.entrySet().iterator();
        exhaust(iterator2);
        assertThat(iterator2).as("size == capacity").isExhausted();
    }

    @Test
    void constructorTest() {
        Map<Integer, Integer> map = createMap(6);
        assertThat(map.size()).isEqualTo(0);
    }

    @Test
    void add1Test() {
        Map<Integer, Integer> map = createMap();
        assertThat(map.put(1, 2)).isEqualTo(null);
        assertThat(map.put(2, 3)).isEqualTo(null);
        assertThat(map.put(2, 2)).isEqualTo(3);
        assertThat(map.put(3, 3)).isEqualTo(null);
        assertThat(map.put(3, 4)).isEqualTo(3);

    }

    @Test
    void getTest() {
        Map<Integer, Integer> map = createMap();
        map.put(1, 2);
        // assertThat(map.put(2, 2)).isEqualTo(null);
        assertThat(map.get(1)).isEqualTo(2);
    }

    @Test
    void hashCodeTest() {
        String a = "a";
        String f = "f";

        System.out.println(a.hashCode() % 5);
        System.out.println(f.hashCode() % 5);
    }


}


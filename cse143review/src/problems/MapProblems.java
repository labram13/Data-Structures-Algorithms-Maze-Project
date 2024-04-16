package problems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    public static boolean contains3(List<String> list) {
        Map<String, Integer> result = new HashMap<>();
        for (String word : list) {
            if (!result.containsKey(word)) {
                result.put(word, 0);
            }
            result.put(word, result.get(word) + 1);
        }

        for (String key : result.keySet()) {
            if (result.get(key) >= 3) {
                return true;
            }
        }

        return false;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output iff the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> result = new HashMap<>();
        for (String key : m1.keySet()) {
            if (m2.containsKey(key)) {
                if (m1.get(key) == m2.get(key)) {
                    result.put(key, m1.get(key));
                }
            }
        }


        return result;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }
}

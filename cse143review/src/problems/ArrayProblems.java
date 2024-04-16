package problems;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {
        String result = "[";
        for (int i = 0; i < array.length; i++) {
            if (i < array.length - 1) {
                result += array[i] + ", ";
            } else {
                result += array[i];
            }
        }
        result += "]";
        return result;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {
        int[] result = new int[array.length];
        int index = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            result[i] = array[index];
            index++;
        }
        // throw new UnsupportedOperationException("Not implemented yet.");
        return result;
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
       int[] temp = new int[array.length];
       if (!(array.length < 2)) {
           int lastElem = array[array.length - 1];
           for (int i = 1; i < array.length; i++) {
               temp[i] = array[i - 1];
           }
           temp[0] = lastElem;
           for (int i = 0; i < array.length; i++) {
               array[i] = temp[i];
           }
       }
    }
}

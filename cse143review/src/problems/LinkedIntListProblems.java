package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        ListNode temp = null;
        ListNode curr1 = list.front;

        while (curr1 != null) {
            ListNode curr2 = curr1.next;
            curr1.next = temp;
            temp = curr1;
            curr1 = curr2;
        }
        list.front = temp;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        ListNode curr = list.front;
        if (curr != null) {
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = list.front;
            curr = list.front.next;
            list.front.next = null;
            list.front = curr;
        }
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        // Hint: you'll need to use the 'new' keyword to construct new objects.
        LinkedIntList result = new LinkedIntList();
        if (a.front != null && b.front == null) {
            return a;
        } else if (a.front == null && b.front != null) {
            return b;
        } else if (a.front == null && b.front == null) {
            return result;
        } else {
            result.front = new ListNode(a.front.data);
            ListNode curr = a.front.next;
            ListNode currResult = result.front;
            while (curr != null) {
                currResult.next = new ListNode(curr.data);
                curr = curr.next;
                currResult = currResult.next;
            }
            curr = b.front;
            while (curr != null) {
                currResult.next = new ListNode(curr.data);
                curr = curr.next;
                currResult = currResult.next;
            }
            return result;
        }


        // throw new UnsupportedOperationException("Not implemented yet.");
    }
}

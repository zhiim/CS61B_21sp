package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    /**
     * Construct MaxArrayDeque with a given Comparator "c", "c" will be
     * used to compare elements of type "T" in ArrayDeque.
     * Comparator "c" will specific what attribute is used to compare elements
     * in ArrayDeque
     * @param c Comparator used to compare elements in ArrayDeque
     */
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    /**
     * Get the max elements in MaxArrayDeque, using the compare method defined
     * by Comparator "comparator"
     * @return the max element. If MaxArrayDeque is empty, it will return "null"
     */
    public T max() {
        // if MaxArrayDeque is empty
        if (this.isEmpty()) {
            return null;
        }

        T maxItem = get(0);
        for (int i = 0; i < size(); i++) {
            if (comparator.compare(get(i), maxItem) >= 0) {
                maxItem = get(i);
            }
        }
        return maxItem;
    }

    /**
     * Get the max elements in MaxArrayDeque, using the compare method defined
     * by Comparator "c"
     * @param c comparator c used to compare elements in Deque
     * @return the max element. If MaxArrayDeque is empty, it will return "null"
     */
    public T max(Comparator<T> c) {
        // if MaxArrayDeque is empty
        if (this.isEmpty()) {
            return null;
        }

        T maxItem = get(0);
        for (int i = 0; i < size(); i++) {
            // use the method defined in c to compare elements in Deque
            if (c.compare(get(i), maxItem) >= 0) {
                maxItem = get(i);
            }
        }
        return maxItem;
    }
}

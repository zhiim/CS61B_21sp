package deque;

public interface Deque<T> {
    // Add item from the start of list
    void addFirst(T item);
    // Add item from the end of list
    void addLast(T item);

    /**
     * This function check whether this list is empty
     * @return true if this list is empty, otherwise false
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    // Get number of items in this list
    int size();
    // Print out items in this list
    void printDeque();
    // Remove item from the start of the list
    T removeFirst();
    // Remove item from the end of the list
    T removeLast();
    // Get an item based on index
    T get(int index);
}

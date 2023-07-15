package deque;

public interface Deque<T> {
    // Add item from the start of list
    public void addFirst(T item);
    // Add item from the end of list
    public void addLast(T item);
    // Whether this list is empty or not
    public boolean isEmpty();
    // Get number of items in this list
    public int size();
    // Print out items in this list
    public void printDeque();
    // Remove item from the start of the list
    public T removeFirst();
    // Remove item from the end of the list
    public T removeLast();
    // Get an item based on index
    public T get(int index);
}

package deque;

import java.util.Iterator;

/**
 * A double linked list implemented with the circular sentinel topology
 */
public class LinkedListDeque<T> implements Deque<T>,Iterable<T>{
    private int size;  // size of this list
    private ObNode sentinel;

    // define the Node class to contain objects and point
    private class ObNode {
        public T item;  // item contained in this node
        public ObNode previous;
        public ObNode next;

        public ObNode() {
            item = null;
            // previous and next all point to self
            previous = this;
            next = this;
        }

        public ObNode(T item, ObNode previous, ObNode next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }

    public LinkedListDeque() {
        sentinel = new ObNode();
        size = 0;
    }

    public LinkedListDeque(T x) {
        sentinel = new ObNode();
        sentinel.next = new ObNode(x, sentinel, sentinel);
        sentinel.previous = sentinel.next;
        size = 0;
    }

    /**
     * This function add a node before the first node of list
     * @param item the item contained in the new node
     */
    @Override
    public void addFirst(T item) {
        // add a new node right before the first node
        sentinel.next.previous = new ObNode(item, sentinel, sentinel.next);
        // set sentinel's next link to this new node
        sentinel.next = sentinel.next.previous;
        size++;
    }

    /**
     * This function add a node to the end of list
     * @param item item contained in the new node
     */
    @Override
    public void addLast(T item) {
        // add a new node behind the formerly last node
        sentinel.previous.next = new ObNode(item, sentinel.previous, sentinel);
        // set sentinel's previous link to this new node
        sentinel.previous = sentinel.previous.next;
        size++;
    }

    /**
     * This function check whether this list is empty
     * @return true if this list is empty, otherwise false
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * This function return the size of this list
     * @return number of nodes in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * This function print the item of each node one by one
     */
    @Override
    public void printDeque() {
        ObNode p = sentinel;
        do {
            p = p.next;
            // if p is the last node print a new line after this print
            if (p.next == sentinel) {
                System.out.println(p.item);
            } else {
                System.out.print(p.item + " ");
            }
        } while (p.next != sentinel);
    }

    /**
     * This function remove the First node from this list
     * @return the item contained by the first node. if is a empty node,
     * just return null
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T item;
        item = sentinel.next.item;
        // remove the first node from the list, just remove all link to
        // this node
        sentinel.next.next.previous = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return item;
    }

    /**
     * This function remove the last node from this list
     * @return return the item contained by the last node.
     * return null if this list is empty
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item;
        item = sentinel.previous.item;
        // remove the last node
        sentinel.previous.previous.next = sentinel;
        sentinel.previous = sentinel.previous.previous;
        size--;
        return item;
    }

    /**
     * This function return item contained in the indexed node
     * @param index the index used to reach node
     * @return item contained in the indexed node. Return null if
     * the index bigger than list size
     */
    @Override
    public T get(int index) {
        if (index + 1> size) {
            return null;
        }
        ObNode p = sentinel;
        for (int i = 0; i <= index; i++) {
            p = p.next;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index + 1> size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(ObNode p, int counter) {
        // base case: if counter = 0, we get to the wanted node
        if (counter == 0) {
            return p.item;
        }
        // recursive case
        return getRecursiveHelper(p.next, counter - 1);
    }

    private class LLDequeIterator implements Iterator<T> {
        private int wizPos;

        public LLDequeIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = get(wizPos);
            wizPos++;
            return returnItem;
        }
    }

    public Iterator<T> iterator() {
        // return an Iterator<T> type, which has the hasNext()
        // and next() method
        return new LLDequeIterator();
    }

    /**
     * This function is an overwrite of default equals() function
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }

        LinkedListDeque<T> comparedList = (LinkedListDeque<T>) o;
        if (size != comparedList.size) {
            return false;
        }
        // whether items contained in all nodes are same
        return itemsEqual(comparedList);
    }

    /**
     * This helper function is used to determine whether two list have
     * the same content and order
     * @param o the list used to compare with this list
     * @return true if two list have same content at same order,
     * false otherwise
     */
    private boolean itemsEqual(LinkedListDeque<T> o) {
        ObNode p = sentinel;
        ObNode po = o.sentinel;
        for (int i = 0; i < size; i++) {
            p = p.next; po = po.next;
            if (p.item != po.item) {
                return false;
            }
        }
        return true;
    }
}
package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int nextFirst;  // Where to insert items using addFirst()
    private int nextLast;  // Where to insert items using addLast()
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[8]; // Create a generic array
        // Elements add by addFirst should be added to the end part of "items",
        // cause we think this array "item" is a circular array
        nextFirst = 7;
        nextLast = 0;
        size = 0;
    }

    /**
     * This function is used to create a longer array to replace
     * the older array "items"
     * @param newSize the length of the new array
     */
    private void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        // Copy elements inserted by addFast() to the beginning of array
        System.arraycopy(items, nextFirst + 1,
                newArray, 0, (items.length - 1) - nextFirst);
        // Copy elements inserted by addLast() following addFirst part
        System.arraycopy(items, 0, newArray,
                (items.length - 1) - nextFirst, nextLast);
        // The old array will be release after "items" point to "newArray"
        items = newArray;
        // addFirst() will add item to the end of array
        nextFirst = newSize - 1;
        // addLast() will add item to the end of current last item
        nextLast = size;
    }

    /**
     * This function add an item before the first element of "items"
     * @param item item to add to the array "items"
     */
    @Override
    public void addFirst(T item) {
        // If the array is already full, create a longer array
        if (size == items.length) {
            resize((int) (size * 1.25));
        }
        items[nextFirst] = item;
        nextFirst--;
        size++;
    }

    /**
     * This function add an item after the last element of "items"
     * @param item item to add to the array "items"
     */
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize((int) (size * 1.25));
        }
        items[nextLast] = item;
        nextLast++;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * This function print out the elements of array "items" one by one
     */
    @Override
    public void printDeque() {
        // First, print out items add by addFirst()
        for (int i = nextFirst + 1; i < items.length; i++) {
            System.out.print(items[i] + " ");
        }
        // Second, print out items for the beginning of array "items"
        for (int i = 0; i <= nextLast - 1; i++) {
            if (i == nextLast - 1) {
                System.out.println(items[i]);
            } else {
                System.out.print(items[i] + " ");
            }
        }
    }

    /**
     * This function is used to remove the first element from "items"
     * @return the removed element
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        T returnValue;
        // if there have not been elements add to the end part of array "items",
        // then remove element from the start part of array "items"
        if (nextFirst == items.length - 1) {
            returnValue = items[0];
            removeFirstElementOfItems();
            nextLast--;
        } else {
            returnValue = items[nextFirst + 1];
            // else if there are elements added by addFirst()
            nextFirst++;
        }
        size--;

        // if usage factor less than 25%, reduce the length of "item"
        if (items.length >= 16
                && size  < items.length * 0.25) {
            resize(items.length / 2);
        }
        return returnValue;
    }

    /**
     * This function is used to remove the first element from the array "items",
     * when there are only elements exist in the start part ot "items"
     */
    private void removeFirstElementOfItems() {
        // create a new array with the same size
        T[] newArray = (T[]) new Object[items.length];
        // copy elements start from index 1 to the new array
        System.arraycopy(items, 1, newArray, 0, size - 1);
        items = newArray;
    }

    /**
     * This function is used to remove the last element from "items"
     * @return the removed element
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        T returnValue;
        // if no element added by addLast(), then remove element added by addFirst()
        if (nextLast == 0) {
            returnValue = items[items.length - 1];
            removeLastElementOfItems();
            nextFirst++;
        } else {
            // else if there are elements added by addFirst()
            returnValue = items[nextLast - 1];
            nextLast--;
        }
        size--;

        // if usage factor less than 25%, reduce the length of "item"
        if (items.length >= 16
                && size < items.length * 0.25) {
            resize(items.length / 2);
        }
        return returnValue;
    }

    /**
     * This function is used to remove the last element from the array "items",
     * when there are only elements exist in the end part ot "items"
     */
    private void removeLastElementOfItems() {
        // create a new array with the same size
        T[] newArray = (T[]) new Object[items.length];
        // copy elements expect the last item to the new array
        System.arraycopy(items, nextFirst + 1, newArray, nextFirst + 2, size - 1);
        items = newArray;
    }

    /**
     * This function return an elements of array "items" by index
     * @param index index number used to find an element
     * @return the element found by index number
     */
    @Override
    public T get(int index) {
        // If index number less than number of elements added by addFirst(),
        // than it should be found in the end of the array "items"
        int numAddByAddFirst = (items.length - 1) - (nextFirst + 1) + 1;
        if (index < numAddByAddFirst) {
            return items[(nextFirst + 1) + index ];
        } else {
            return items[index - numAddByAddFirst];
        }
    }

    public Iterator<T> iterator() {
        return new ADIterator();
    }

    private class ADIterator implements Iterator<T> {
        private int wizPos;
        ADIterator() {
            wizPos = 0;
        }
        public boolean hasNext() {
            return wizPos < size;
        }
        public T next() {
            wizPos++;
            return get(wizPos - 1);
        }
    }

    /**
     * This function is used to compare whether an object is equal to
     * this ArrayDeque
     * @param o the object is going to be compared with this ArrayDeque
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }

        if (o instanceof Deque compareList) {
            if (compareList.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (compareList.get(i) != get(i)) {
                    return false;
                }
            }
        }
        return true;
    }
}

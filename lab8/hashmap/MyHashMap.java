package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Q Xu
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int tableSize = 16;
    private double loadFactor = 0.75;
    private int size;  // number of nodes in map
    private Set<K> hashKeys;  // a set contains all keys

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(tableSize);
        size = 0;
        hashKeys = new HashSet<>();
    }

    public MyHashMap(int initialSize) {
        tableSize = initialSize;
        buckets = createTable(tableSize);
        size = 0;
        hashKeys = new HashSet<>();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        tableSize = initialSize;
        loadFactor = maxLoad;
        buckets = createTable(tableSize);
        size = 0;
        hashKeys = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * Override this method to use different data structures as
     * the underlying bucket type
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    /** Remove all mapping nodes from map */
    @Override
    public void clear() {
        // set backing array size to default size
        tableSize = 16;
        size = 0;
        hashKeys = new HashSet<>();
        // point buckets to new table
        buckets = createTable(tableSize);
    }

    /**
     * Find whether a key exists in map
     * @param key the key we want to find
     * @return true if we find this key in map, otherwise false
     */
    @Override
    public boolean containsKey(K key) {
        if (size == 0) {
            return false;
        }
        // corresponding bucket order of key in hashing table
        int order = Math.floorMod(key.hashCode(), tableSize);
        // if find a node whose key equals to parameter key, return true
        for (Node node: buckets[order]) {
            if (key.equals(node.key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (size == 0) {
            return null;
        }
        // corresponding bucket order of key in hashing table
        int order = Math.floorMod(key.hashCode(), tableSize);
        // if find a node whose key equals to parameter key, return true
        for (Node node: buckets[order]) {
            if (key.equals(node.key)) {
                return node.value;
            }
        }
        return null;
    }

    /**
     * @return the number of nodes in map
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Add new key-value pair to map
     * @param key key to be added
     * @param value value to be added
     */
    @Override
    public void put(K key, V value) {
        // if N/M > load factor, increase table size
        if (((double) (size + 1)) / tableSize > loadFactor) {
            resizeTable(tableSize * 2);
            tableSize *= 2;
        }

        int order = Math.floorMod(key.hashCode(), tableSize);
        // if no bucket created for this table order, create an new bucket
        if (buckets[order] == null) {
            buckets[order] = createBucket();
        }
        // if the same key already exist, just update the value
        for (Node node: buckets[order]) {
            if (key.equals(node.key)) {
                node.value = value;
                return;
            }
        }
        // insert this key to correspond bucket in hashing table
        buckets[order].add(createNode(key, value));
        size++;
        hashKeys.add(key);
    }

    /**
     * Increase the size of backing array
     * @param tableSize new size of backing array
     */
    private void resizeTable(int tableSize) {
        Collection<Node>[] newBuckets = createTable(tableSize);
        // move buckets from old to new
        for (Collection<Node> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (Node node : bucket) {
                int newOrder = Math.floorMod(node.key.hashCode(), tableSize);
                // if no bucket in new table, create it first
                if (newBuckets[newOrder] == null) {
                    newBuckets[newOrder] = createBucket();
                }
                newBuckets[newOrder].add(node);
            }
        }
        // point buckets to new
        buckets = newBuckets;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return hashKeys;
    }

    @Override
    public V remove(K key) {
        if (size == 0) {
            return null;
        }
        int order = Math.floorMod(key.hashCode(), tableSize);
        if (buckets[order] == null) {
            return null;
        }else {
            for (int i = 0; i < buckets[order].size();) {
                if (key.equals(((LinkedList<Node>) buckets[order]).get(i).key)) {
                    size--;
                    hashKeys.remove(key);
                    return ((LinkedList<Node>) buckets[order]).remove(i).value;
                }
                i++;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if (size == 0) {
            return null;
        }
        int order = Math.floorMod(key.hashCode(), tableSize);
        if (buckets[order] == null) {
            return null;
        }else {
            for (int i = 0; i < buckets[order].size();) {
                if (key.equals(((LinkedList<Node>) buckets[order]).get(i).key)
                && value.equals(((LinkedList<Node>) buckets[order]).get(i).value)) {
                    size--;
                    hashKeys.remove(key);
                    return ((LinkedList<Node>) buckets[order]).remove(i).value;
                }
                i++;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return hashKeys.iterator();
    }
}

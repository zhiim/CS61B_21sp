package bstmap;

import org.w3c.dom.Node;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/**
 * Ordered map implemented with binary search tree, and tree structure is
 * represented using linked node
 * @param <K> Key of map
 * @param <V> Value paired with key
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    int size;
    BSTNode root;

    /**
     * Node is used to store Key-Value pair of each tree node,
     * and point to sub nodes
     */
    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        BSTNode(K key, V value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public BSTMap() {
        size = 0;
        root = null;
    }

    /**
     * Clear off all node in this map
     */
    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Check whether a node with specific key exists in this map
     * @param key the key to check in map
     * @return true if this key exits in this map, otherwise false
     */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Find the node has the same key as given
     * @param key the given key used to search in map
     * @return if find corresponding node, return value of that node,
     * else return null
     */
    @Override
    public V get(K key) {
        if (size == 0) {
            return null;
        }
        return searchNode(key, this.root);
    }

    /**
     * Search a key in all nodes of tree
     * @param key the key used to search
     * @param root the root node of the tree
     * @return the value of corresponding node, or null if can not find
     */
    private V searchNode(K key, BSTNode root) {
        // base case: if find the node with same key, or reach out to leaves
        // if root == null should be checked before compareTo() function
        if (root == null) {
            return null;
        }
        if (key.compareTo(root.key) == 0) {
            return root.value;
        }

        // recursive case: if key < root.key, search in root's sub-left tree,
        // else search in root's sub-right tree
        if (key.compareTo(root.key) < 0) {
            return searchNode(key, root.left);
        }else if (key.compareTo(root.key) > 0) {
            return searchNode(key, root.right);
        }

        return null;
    }

    /**
     * Get the number of node in map
     * @return the number of node in map
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (size == 0) {
            this.root = new BSTNode(key, value, null, null);
            this.size++;
            return;
        }
        insertNode(key, value, this.root);
    }

    /**
     * Insert a key-value pair into map
     * @param key key in key-value pair
     * @param value value in key-value pair
     * @param root the root node of the BST to be inserted with a node
     */
    private void insertNode (K key, V value, BSTNode root) {
        // base case
        if (key.compareTo(root.key) == 0) {
            return;
        }
        // reach leaves
        if (key.compareTo(root.key) < 0 && root.left == null) {
            root.left = new BSTNode(key, value, null, null);
            this.size++;
            return;
        }
        if (key.compareTo(root.key) > 0 && root.right == null) {
            root.right = new BSTNode(key, value, null, null);
            this.size++;
            return;
        }

        // recursive case
        if (key.compareTo(root.key) < 0) {
            insertNode(key, value, root.left);
        }else if (key.compareTo(root.key) > 0) {
            insertNode(key, value, root.right);
        }
    }

    public void printInOrder() {
        if (this.size == 0) {
            return;
        }
        printTree(this.root);
    }

    private void printTree(BSTNode root) {
        // base case: print from leaves
        if (root.left == null && root.right == null) {
            System.out.println(root.key + ": " + root.value);
            return;
        }

        // recursive case
        if (root.left != null) {
            printTree(root.left);
        }
        System.out.println(root.key + ": " + root.value);
        if (root.right != null) {
            printTree(root.right);
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}

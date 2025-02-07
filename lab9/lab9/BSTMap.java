package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
            left = right = null;
        }

        private Node(K k, V v, Node l, Node r) {
            key = k;
            value = v;
            left = l;
            right = r;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p){
        if (p == null)
            return null;
        int cmp = key.compareTo(p.key);
        if (cmp > 0)
            return getHelper(key, p.right);
        else if (cmp < 0)
            return getHelper(key, p.left);
        else
            return p.value;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p){
        if (p == null)
            return new Node(key, value);
        int cmp = key.compareTo(p.key);
        if (cmp > 0)
            p.right = putHelper(key, value, p.right);
        else if (cmp < 0)
            p.left = putHelper(key, value, p.left);
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        size++;
        this.root = putHelper(key, value, this.root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    private void sethlper(Node p, Set<K> set){
        if (p == null)
            return;
        sethlper(p.left, set);
        set.add(p.key);
        sethlper(p.right, set);
    }
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        sethlper(root, set);
        return set;
    }
    private Node findmin(Node p){
        if (p.left == null)
            return p;
        return findmin(p.left);
    }
    private Node remove_helper(Node p, K key){
        if (p == null)
            return null;
        int cmp = key.compareTo(p.key);
        if (cmp < 0)
            p.left = remove_helper(p.left, key);
        else if (cmp > 0)
            p.right = remove_helper(p.right, key);
        else{
            if (p.right == null)
                return p.left;
            else if (p.left == null)
                return p.right;
            Node tmp = findmin(p.right);
            p.right = remove_helper(p.right, tmp.key);
            p.key = tmp.key;
            p.value = tmp.value;
        }
        return p;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (root == null)
            return null;
        V res = get(key);
        if (res != null)
            size--;
        root = remove_helper(this.root, key);
        return res;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (value.equals(get(key))){
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}

/*
Author: Matilda Qvick 001105-0606
Generated: 21/9 - 2020
Last updated: 28/9- 2020
Solves: Creates a binary search symbol table where key-value
        paris are put and accessed when needed. The class also
        includes an iterator which one can use to iterate through
        all the keys in the table.
How to use: This class is only used in FrequencyCounter
 */

import java.util.NoSuchElementException;

public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] values;
    private static int size = 0;
    private static final int CAPACITY = 2;

    /**
     * Constructor
     */
    public BinarySearchST(){
        this(CAPACITY);
    }

    /**
     * Constructor
     * @param capacity takes in max capacity to create finite arrays
     */
    public BinarySearchST(int capacity){
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    /**
     *
     * @return true if the symbol table is empty
     */
    public boolean isEmpty(){
        return size==0;
    }

    public static int size(){return size;}

    /**
     * Takes in a key and value pair in the right place.
     * If the key is null an exception is thrown.
     * If the value is null the key is deleted.
     * We get the rank of the key and compare the key at
     * given rank with the key we have. If they match we
     * assign the value at that rank to the parameter value.
     * The array of keys is resized if necessary.
     * Every element in the key array is shifted one place
     * due to the new key element.
     * Th size of the symbol table is increased.
     * @param key   the object to put into the symbol table
     * @param value the value that pairs the key
     */
    public void put(Key key, Value value){
        if(key == null){
            throw new IllegalArgumentException();
        }

        if(value == null){
            delete(key);
            return;
        }

        int i = rank(key);
        if((i < size) && (keys[i].compareTo(key)==0)){
            values[i] = value;
            return;
        }

        if(size == keys.length){
            resize(2* keys.length);
        }

        for(int j = size; j > i; j--){
            keys[j] = keys[j-1];
            values[j] = values[j-1];
        }
        keys[i] = key;
        values[i] = value;
        size++;
        assert check();
    }

    /**
     * Gets the rank of given key to delete.
     * If the rank is outside of the limits of the table,
     * if the key given doesn't match given rank or if the
     * table is empty, return is called.
     * Else, every key after given key is shifted one place
     * to cover the place of the deleted key.
     * Lastly, the size is decreased and last key and value is
     * assigned null.
     * @param key is the given object to delete
     */
    public void delete(Key key){
        if(key == null){
            throw new IllegalArgumentException();
        }
        int i = rank(key);

        if(i == size || keys[i].compareTo(key)!=0 || isEmpty()){
            return;
        }

        for(int j = i; j < size-1; j++){
            keys[j] = keys[j+1];
            values[j] = values[j+1];
        }

        size--;
        keys[size] = null;
        values[size] = null;

        if(size > 0 && size == keys.length/4){
            resize(keys.length/2);
        }
    }

    /**
     * The search table is divided and the middle-key is
     * compared to the key. If the middle-key is bigger
     * than the given key we assign a new highest value
     * below the previous middle. If the middle-key is
     * smaller than given key we assign a new lowest value
     * above the middle. This is done until the keys either match
     * or the lowest value is assigned to the highest and which
     * in both cases means we have found the value to return.
     * @param key object we want to find the rank of
     * @return    the rank of the given key
     */
    public int rank(Key key){
        if(key == null){
            throw new IllegalArgumentException();
        }

        int lo = 0;
        int hi = size-1;
        while (lo <= hi){
            int mid = lo + (hi-lo)/2;
            int compare = key.compareTo(keys[mid]);
            if(compare < 0){
                hi = mid-1;
            }
            else if(compare > 0){
                lo = mid+1;
            }
            else return mid;
        }
        return lo;
    }

    /**
     * Resizes the symbol table
     * @param n is the wanted new size
     */
    private void resize(int n){
        Key[] tempk = (Key[]) new Comparable[n];
        Value[] tempv = (Value[]) new Object[n];

        for(int i = 0; i < size; i++){
            tempk[i] = keys[i];
            tempv[i] = values[i];
        }
        keys = tempk;
        values = tempv;
    }

    /**
     *
     * @param key is wanted object
     * @return true if the key exists in the symbol table
     */
    public boolean contains(Key key){
        if(key == null){
            throw new IllegalArgumentException();
        }
        return get(key) != null;
    }

    /**
     * If key or value exists, the rank of wanted item
     * is fetched. If this is within the limits of the
     * table and if it matches the key in the symbol
     * table, the value paired with the key is returned.
     * @param key is wanted object
     * @return value that pairs with key
     */
    public Value get(Key key){
        if(key == null){
            throw new IllegalArgumentException();
        }
        if(isEmpty()){
            return null;
        }
        int i = rank(key);
        if(i < size && keys[i].compareTo(key)==0){
            return values[i];
        }
        return null;
    }

    /**
     * Constructor for iterable
     * @return a sorted queue of keys
     */
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    /**
     * Creates a queue of the key-value pairs.
     * The queue is sorted and all key value pairs are put in place
     * @param lo is lowest index
     * @param hi is highest index
     * @return all keys from low to high index in an iterable queue
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        SortQueue<Key> queue = new SortQueue<Key>();
        if (lo.compareTo(hi) > 0) return queue;
        for (int i = rank(lo); i < rank(hi); i++)
            queue.enqueue(keys[i]);
        if (contains(hi)) queue.enqueue(keys[rank(hi)]);
        return queue;
    }

    /**
     * Checks if the array is sorted and
     * if rank works correctly
     * @return true if the array is sorted and rank correct
     */
    private boolean check() {
        return isSorted() && rankCheck();
    }

    /**
     * Checks if the array is sorted in ascending order
     * @return true if it is sorted
     */
    private boolean isSorted() {
        for (int i = 1; i < size; i++)
            if (keys[i].compareTo(keys[i-1]) < 0) return false;
        return true;
    }

    /**
     * Checks if rank of selected item corresponds
     * to index.
     * Checks if the key at index i is corresponding
     * to the item tied to that rank
     * @return true if both of the above are true
     */
    private boolean rankCheck() {
        for (int i = 0; i < size; i++)
            if (i != rank(select(i))) return false;
        for (int i = 0; i < size; i++)
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) return false;
        return true;
    }

    /**
     * Getter of an item of specific index
     * @param k is given index
     * @return key at index k in array
     */
    public Key select(int k) {
        if (k < 0 || k >= size) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        return keys[k];
    }

    /**
     *
     * @return the last key in array
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        return keys[size-1];
    }

    /**
     *
     * @return the first key in array
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        return keys[0];
    }
}

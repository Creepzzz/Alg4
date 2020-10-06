/**
 * Generic implementation of bag with an iterator
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int size;

    /**
     * Node class with attributes of item an next node
     * @param <Item> instance of object within node
     */
    private static class Node<Item>{
        private Item item;
        private Node<Item> next;
    }

    /**
     * Constructor for bag, instancing an empty bag
     */
    public Bag(){
        first = null;
        size = 0;
    }

    /**
     *
     * @return true if the bag is empty
     */
    private boolean isEmpty(){
        return first == null;
    }

    /**
     *
     * @return the size of the bag
     */
    public int size(){
        return size;
    }

    /**
     * Adds element in bag
     * Items are added from the front
     * @param item to add to the bag
     */
    public void add(Item item){
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    /**
     * Constructor for iterator
     * @return a linked list iterator
     */
    public Iterator<Item> iterator(){
        return new LinkedIterator(first);
    }

    /**
     * Class of linked list iterator
     * Includes constructor, check and getter for next item
     */
    private class LinkedIterator implements Iterator<Item>{
        private Node<Item> current;

        public LinkedIterator(Node<Item> first){
            current = first;
        }
        public boolean hasNext(){return current != null;}

        public Item next(){
            if(!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}

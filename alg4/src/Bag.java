import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int size;

    private static class Node<Item>{
        private Item item;
        private Node<Item> next;
    }

    public Bag(){
        first = null;
        size = 0;
    }

    private boolean isEmpty(){
        return first == null;
    }

    public int size(){
        return size;
    }

    public void add(Item item){
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    public Iterator<Item> iterator(){
        return new LinkedIterator(first);
    }

    private class LinkedIterator implements Iterator<Item>{
        private Node<Item> current;

        public LinkedIterator(Node<Item> first){
            current = first;
        }
        public boolean hasNext(){return current.next != null;}

        public Item next(){
            if(!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}

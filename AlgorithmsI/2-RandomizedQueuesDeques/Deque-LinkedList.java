import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private int N;
    private Node<Item> first;
    private Node<Item> last;
    
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }
    
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return N;
    }
    
    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> newnode = new Node<Item>();
        newnode.item = item;
        newnode.next = first;
        first = newnode;
        ++N;
        if (N == 1) last = first; 
    }
    
    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> newnode = new Node<Item>();
        newnode.item = item;
        newnode.next = null;
        if (last != null) {
            last.next = newnode;
        }
        last = newnode;
        ++N;
        if (N == 1) first = last;
    }
    
    // delete and return the item at the front
    public Item removeFirst() {
        if (N == 0) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        --N;
        if (N == 0) last = null;
        return item;
    }
    
    // delete and return the item at the end
    public Item removeLast() {
        if (N == 0) throw new java.util.NoSuchElementException();
        Node<Item> previous = null;
        Node<Item> current = first;
        while (current.next != null) {
            previous = current;
            current = current.next;
        }
        Item item = current.item;
        if (previous != null) {
            last = previous;
            last.next = null;
        } else {
            first = null;
            last = null;
        }
        --N;
        return item;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }
    
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        
        public ListIterator(Node<Item> first) {
            current = first;
        }
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    // unit testing
    public static void main(String[] args) {
        Deque<String> deck = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("+f")) {
                item = StdIn.readString();
                deck.addFirst(item);
                StdOut.printf("added first: %s\n", item);
            } else if (item.equals("+l")) {
                item = StdIn.readString();
                deck.addLast(item);
                StdOut.printf("added last: %s\n", item);
            } else if (item.equals("-f")) {
                if (!deck.isEmpty()) {
                    StdOut.printf("removed first: %s\n", deck.removeFirst());
                } else {
                    StdOut.printf("empty deque\n");
                }
            } else if (item.equals("-l")) {
                if (!deck.isEmpty()) {
                    StdOut.printf("removed last: %s\n", deck.removeLast());
                } else {
                    StdOut.printf("empty deque\n");
                }
            }                 
        }
        StdOut.println("(" + deck.size() + " left on deque)\n");
    }
}
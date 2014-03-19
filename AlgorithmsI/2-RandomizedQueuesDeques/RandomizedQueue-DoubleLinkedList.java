import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private int N;
    private Node<Item> first;
    private Node<Item> last;
       
    private static class Node<Item> {
        private Item item;
        private Node<Item> previous;
        private Node<Item> next;
    }
     
    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
        N = 0;
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return N;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> newnode = new Node<Item>();
        newnode.item = item;
        newnode.previous = last;
        newnode.next = null;
        if (last != null) {
            last.next = newnode;
        }
        last = newnode;
        ++N;
        if (N == 1) first = last;
    }
    
    // delete and return a random item
    public Item dequeue() {
        if (N == 0) throw new java.util.NoSuchElementException();
        int randpos = StdRandom.uniform(N);
        Node<Item> current = first;
        for (int i = 0; i < randpos; ++i) {
            current = current.next;
        }
        Item item = current.item;
        if (current.previous != null) {
            current.previous.next = current.next;
        } else {
            first = current.next;
        }
        if (current.next != null) {
            current.next.previous = current.previous;
        } else {
            last = current.previous;
        }
        --N;
        return item;
    }
    
    // return (but do not delete) a random item
    public Item sample() {
        if (N == 0) throw new java.util.NoSuchElementException();
        int randpos = StdRandom.uniform(N);
        Node<Item> current = first;
        for (int i = 0; i < randpos; ++i) {
            current = current.next;
        }
        Item item = current.item;
        return item;
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(N, first);
    }
    
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        private Node<Item> first;
        private int randpos;
        private int[] order;
        private int count;
        private int size;
        
        public ListIterator(int N, Node<Item> firstarg) {
            if (N != 0) {
                count = 0;
                size = N;
                first = firstarg;
                order  = new int[N];
                for (int i = 0; i < N; ++i) {
                    order[i] = i;
                }
                for (int i = 0; i < N; ++i) {
                    randpos = StdRandom.uniform(i, N);
                    int temp = order[i];
                    order[i] = order[randpos];
                    order[randpos] = temp;
                }
                randpos = order[0];
                current = first;
                for (int i = 0; i < randpos; ++i) {
                    current = current.next;
                }
            } else {
                current = null;
            }
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
            ++count;
            if (count < size) {
                randpos = order[count];
                current = first;
                for (int i = 0; i < randpos; ++i) {
                    current = current.next;
                }
            } else {
                current = null;
            }
            return item;
        }
    }
    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> randq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("e")) {
                item = StdIn.readString();
                randq.enqueue(item);
                StdOut.printf("enqueued: %s\n", item);
            } else if (item.equals("d")) {
                if (!randq.isEmpty()) {
                    StdOut.printf("dequeued: %s\n", randq.dequeue());
                } else {
                    StdOut.printf("empty randomized queue\n");
                }
            } else if (item.equals("s")) {
                if (!randq.isEmpty()) {
                    StdOut.printf("sample: %s\n", randq.sample());
                } else {
                    StdOut.printf("empty randomized queue\n");
                }
            } else if (item.equals("p")) {
                Iterator it = randq.iterator();
                StdOut.printf("Iterator: ");
                while (it.hasNext()) {
                    StdOut.printf("%s ", it.next());
                }
                StdOut.printf("\n");
            }                 
        }
        StdOut.println("(" + randq.size() + " left on randomized queue)\n");
    }
}

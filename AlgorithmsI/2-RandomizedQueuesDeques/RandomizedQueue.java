import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] randq;
    private int N;
    private int first;
    private int last; 
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        N = 0;
        first = 0;
        last  = 0; 
        randq = (Item[]) new Object[100];
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return N;
    }
    
    // resize the array
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = randq[i];
        }
        randq = temp;
    }

    
    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (N == randq.length) resize(2*randq.length);
        randq[last++] = item;
        ++N;
    }
    
    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int randpos = StdRandom.uniform(N);
        Item item = randq[randpos];
        randq[randpos] = randq[--last];
        randq[last] = null;
        --N;
        if (N > 0 && N == randq.length/4) resize(randq.length/2); 
        return item;
    }
    
    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int randpos = StdRandom.uniform(N);
        Item item = randq[randpos];
        return item;
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(N);
    }
    
    private class ListIterator<Item> implements Iterator<Item> {
        private int current;
        private int randpos;
        private int[] order;
        private int count;
        private int size;
        
        public ListIterator(int N) {
            if (N > 0) {
                count = 0;
                size = N;
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
                current = order[0];
            } else {
                current = -1;
            }
        }
        
        public boolean hasNext() {
            return current != -1;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = (Item) randq[current];
            ++count;
            if (count < size) {
                current = order[count];
            } else {
                current = -1;
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

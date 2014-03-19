public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            randq.enqueue(item);
        }
        if (k >= 0 && k <= randq.size()) {
            for (int i = 0; i < k; ++i) {
                if (!randq.isEmpty()) {
                    StdOut.printf("%s\n", randq.dequeue());
                } else {
                    StdOut.printf("empty randomized queue\n");
                }
            }
        }
    }
}
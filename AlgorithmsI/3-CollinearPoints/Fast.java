import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        In in = new In(args[0]);
        int numpoints = in.readInt();
        Point[] origpoints = new Point[numpoints];
        Point[] points;
        for (int i = 0; i < numpoints; ++i) {
            origpoints[i] = new Point(in.readInt(), in.readInt());
            origpoints[i].draw();
        }
        Arrays.sort(origpoints);
        for (int p = 0; p < numpoints; ++p) {
            points = Arrays.copyOf(origpoints, origpoints.length);
            double[] slopes = new double[numpoints];
            for (int i = 0; i < numpoints; ++i) {
                slopes[i] = points[p].slopeTo(points[i]);
            }
            Arrays.sort(slopes);
            Arrays.sort(points, points[p].SLOPE_ORDER);
            int counter = 0;
            for (int i = 1; i < numpoints; ++i) {
                if (Double.compare(slopes[i-1], slopes[i]) == 0) {
                    ++counter;
                } else {
                    if (counter >= 2) {
                        if (points[0].compareTo(points[i - counter - 1]) < 0) {
                            StdOut.printf("%s", points[0].toString());
                            for (int j = i - counter - 1; j < i; ++j) {
                                StdOut.printf(" -> %s", points[j].toString());
                            }
                            StdOut.printf("\n");
                            points[0].drawTo(points[i-1]);
                        }
                    }
                    counter = 0;
                }
             }
            if (counter >= 2) {
                if (points[0].compareTo(points[numpoints - counter - 1]) < 0) {
                    StdOut.printf("%s", points[0].toString());
                    for (int j = numpoints - counter - 1; j < numpoints; ++j) {
                        StdOut.printf(" -> %s", points[j].toString());
                    }
                    StdOut.printf("\n");
                    points[0].drawTo(points[numpoints -1]);
                }
            }
        }
    }       
}
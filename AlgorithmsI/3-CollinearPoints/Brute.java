import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        In in = new In(args[0]);
        int numpoints = in.readInt();
        Point[] points = new Point[numpoints];
        for (int i = 0; i < numpoints; ++i) {
            points[i] = new Point(in.readInt(), in.readInt());
            points[i].draw();
        }            
        Arrays.sort(points);
        for (int p = 0; p < numpoints-3; ++p) {
            double[] slopes = new double[numpoints];
            for (int i = 0; i < numpoints; ++i) {
                slopes[i] = points[p].slopeTo(points[i]);
            }
            for (int p1 = p + 1; p1 < numpoints - 2; ++p1) {
                for (int p2 = p1 + 1; p2 < numpoints - 1; ++p2) {
                    if (Double.compare(slopes[p1], slopes[p2]) == 0) {
                        for (int p3 = p2 + 1; p3 < numpoints; ++p3) {
                            if (Double.compare(slopes[p2], slopes[p3]) == 0) {
                                StdOut.printf("%s -> %s -> %s -> %s\n",
                                              points[p].toString(),
                                              points[p1].toString(),
                                              points[p2].toString(),
                                              points[p3].toString());
                                points[p].drawTo(points[p3]);
                            }
                        }
                    }
                }
            }
        }
    }       
}
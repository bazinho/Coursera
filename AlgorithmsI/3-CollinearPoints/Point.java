/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;
import java.util.Arrays;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        if (this.x == that.x)
            return Double.POSITIVE_INFINITY;
        if (this.y == that.y)
            return 0;
        return (double) (that.y - this.y) / (double) (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.x == that.x && this.y == that.y)
            return 0;
        if (this.y < that.y || (this.y == that.y && this.x < that.x))
            return -1;
        return +1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slopetop1 = slopeTo(p1);
            double slopetop2 = slopeTo(p2);
            if (slopetop1 < slopetop2)
                return -1;
            if (slopetop1 > slopetop2)
                return +1;
            return 0;
        }
    }
        
    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 2);
        Point p3 = new Point(2, 1);
        Point p4 = new Point(2, 2);
        Point p5 = new Point(3, 2);

        StdOut.printf("p1 = %s, p2 = %s, p3 = %s, p4 = %s, p5 = %s\n",
                      p1.toString(),
                      p2.toString(),
                      p3.toString(),
                      p4.toString(),
                      p5.toString());    
        StdOut.printf("Comparing p2 and p1 = %d (expected: 1)\n",
                      p2.compareTo(p1));        
        StdOut.printf("Comparing p1 and p2 = %d (expected: -1)\n",
                      p1.compareTo(p2));        
        StdOut.printf("Comparing p1 and p3 = %d (expected: -1)\n",
                      p1.compareTo(p3));        
        StdOut.printf("Comparing p1 and p4 = %d (expected: -1)\n",
                      p1.compareTo(p4));        

        StdOut.printf("Slope between p1 and itself = %f(expected: -Infinity)\n",
                      p1.slopeTo(p1));        
        StdOut.printf("Slope between p1 and p2 (vert) = %f (expected: Infinity)\n",
                      p1.slopeTo(p2));
        StdOut.printf("Slope between p1 and p3 (horiz) = %f (expected: 0.0)\n",
                      p1.slopeTo(p3));
        StdOut.printf("Slope between p1 and p4 = %f (expected value = 1.0)\n",
                      p1.slopeTo(p4));
        StdOut.printf("Slope between p1 and p5 = %f (expected value = 0.5)\n",
                      p1.slopeTo(p5));

        Point[] points = new Point[5];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        points[3] = p4;
        points[4] = p5;

        StdOut.printf("points array before sort by slope from point[0]: ");
        for (int i = 0; i < 5; ++i) {
            StdOut.printf("%s ", points[i].toString());
        }
        StdOut.printf("\n");

        Arrays.sort(points, points[0].SLOPE_ORDER);

        StdOut.printf("points array after sort by slope from point[0]: ");
        for (int i = 0; i < 5; ++i) {
            StdOut.printf("%s ", points[i].toString());
        }
        StdOut.printf("\n");
        
        p1.draw();
        p2.draw();
        p3.draw();
        p4.draw();
        p5.draw();
        p1.drawTo(p2);
        p1.drawTo(p3);
        p1.drawTo(p4);
        p1.drawTo(p5);
    }
}

public class KdTree {
    
    private static final double MINCOORD = 0.0;
    private static final double MAXCOORD = 1.0;
    private Node root;
    private int N = 0;
    
    private static class Node {
        private Point2D p;
        private Node left, right;
       
        public Node(Point2D p) {
            this.p = p;
        }
    }
    
    // construct an empty set of points
    public KdTree() {
        root = null;
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }
    
    // number of points in the set
    public int size() {
        return this.N;
    }
      
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insert(root, p, 0);
    }

    private Node insert(Node x, Point2D p, int level) {
        if (x == null) {
            this.N++;
            return new Node(p);
        }
        int cmp;
        if (level % 2 == 0) { 
            cmp = Point2D.X_ORDER.compare(p, x.p);
        } else {
            cmp = Point2D.Y_ORDER.compare(p, x.p);
        }
        if (cmp < 0)
            x.left  = insert(x.left, p, level + 1);
        else if (cmp > 0 || p.compareTo(x.p) != 0)
            x.right = insert(x.right, p, level + 1);
        return x;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return search(p) != null;
    }
    
    private Point2D search(Point2D p) {
        return search(root, p, 0);
    }

    private Point2D search(Node x, Point2D p, int level) {
        if (x == null) return null;
        int cmp;
        if (level % 2 == 0) cmp = Point2D.X_ORDER.compare(p, x.p);
        else                cmp = Point2D.Y_ORDER.compare(p, x.p);
        if (cmp < 0)
            return search(x.left,  p, level + 1);
        else if (cmp > 0 || (p.compareTo(x.p) != 0)) 
            return search(x.right, p, level + 1);
        else return x.p;
    }
    
    // draw all of the points to standard draw
    public void draw() {
        draw(root, 0, MINCOORD, MINCOORD, MAXCOORD, MAXCOORD);
    }

    private void draw(Node x, 
                      int level, 
                      double xmin, 
                      double ymin, 
                      double xmax, 
                      double ymax) {
        if (x == null) return;
        StdDraw.setPenRadius(.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        x.p.draw();
        Point2D p0, p1; 
        double current = 0.0;
        if (level % 2 == 0) {
            current = x.p.x();
            p0 = new Point2D(current, ymin); 
            p1 = new Point2D(current, ymax);
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            p0.drawTo(p1);
            draw(x.left, level + 1, xmin, ymin, current, ymax);
            draw(x.right, level + 1, current, ymin, xmax, ymax);
        }
        else {
            current = x.p.y();
            p0 = new Point2D(xmin, current);
            p1 = new Point2D(xmax, current);
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            p0.drawTo(p1);
            draw(x.left, level + 1, xmin, ymin, xmax, current);
            draw(x.right, level + 1, xmin, current, xmax, ymax);
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        intersects(root, 0, q, rect);
        return q;
    }

    private void intersects(Node x, int level, Queue<Point2D> q, RectHV rect) {
        if (x == null) return;
        if (rect.contains(x.p)) q.enqueue(x.p);
        double current = 0.0, rectmin = 0.0, rectmax = 0.0;
        if (level % 2 == 0) {
            current = x.p.x();
            rectmin = rect.xmin();
            rectmax = rect.xmax();
        } else {
            current = x.p.y();
            rectmin = rect.ymin();
            rectmax = rect.ymax();
        }
        if (rectmax < current) { 
            intersects(x.left,  level + 1, q, rect);
        } else if (rectmin > current) { 
            intersects(x.right, level + 1, q, rect);
        } else {
            intersects(x.left,  level + 1, q, rect);
            intersects(x.right, level + 1, q, rect);
        }            
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (root == null) return null;
        return findnearest(root, 0, root.p, p, 
                           new RectHV(MINCOORD, MINCOORD, MAXCOORD, MAXCOORD));
    }

    private Point2D findnearest(Node x, int level, Point2D closer, Point2D p, 
                                RectHV rect) {
        if (x == null) return closer;
        Point2D closest = closer;
        if (Double.compare(p.distanceSquaredTo(x.p), 
                           p.distanceSquaredTo(closest)) < 0)
            closest = x.p;
        if (level % 2 == 0) {
            RectHV rectleft = new RectHV(rect.xmin(), rect.ymin(), 
                                         x.p.x(), rect.ymax());
            RectHV rectright = new RectHV(x.p.x(), rect.ymin(), 
                                          rect.xmax(), rect.ymax());
            double distleft = rectleft.distanceSquaredTo(p);
            double distright = rectright.distanceSquaredTo(p);
            if (distleft <= distright) { 
                if (Double.compare(distleft, p.distanceSquaredTo(closest)) < 0)
                    closest = findnearest(x.left, level + 1, closest, p, 
                                          rectleft);
                if (Double.compare(distright, p.distanceSquaredTo(closest)) < 0)
                    closest = findnearest(x.right, level + 1, closest, p, 
                                          rectright);
            } else {
                if (Double.compare(distright, p.distanceSquaredTo(closest)) < 0)
                    closest = findnearest(x.right, level + 1, closest, p, 
                                          rectright);
                if (Double.compare(distleft, p.distanceSquaredTo(closest)) < 0)
                    closest = findnearest(x.left, level + 1, closest, p, 
                                          rectleft);
            }
        } else {
            RectHV rectdown = new RectHV(rect.xmin(), rect.ymin(), 
                                         rect.xmax(), x.p.y());
            RectHV rectup = new RectHV(rect.xmin(), x.p.y(), 
                                       rect.xmax(), rect.ymax());
            double distdown = rectdown.distanceSquaredTo(p);
            double distup = rectup.distanceSquaredTo(p);
            if (distdown <= distup) { 
                if (Double.compare(distdown, p.distanceSquaredTo(closest)) < 0)
                    closest = findnearest(x.left, level + 1, closest, p, 
                                          rectdown);
                if (Double.compare(distup, p.distanceSquaredTo(closest)) < 0)
                    closest = findnearest(x.right, level + 1, closest, p, 
                                          rectup);
            } else {
                if (Double.compare(distup, p.distanceSquaredTo(closest)) < 0)
                    closest = findnearest(x.right, level + 1, closest, p, 
                                          rectup);
                if (Double.compare(distdown, p.distanceSquaredTo(closest)) < 0)
                    closest = findnearest(x.left, level + 1, closest, p, 
                                          rectdown);
            }
        }
        return closest;
    }
}
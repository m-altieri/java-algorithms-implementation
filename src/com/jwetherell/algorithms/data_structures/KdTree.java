package com.jwetherell.algorithms.data_structures;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A k-d tree (short for k-dimensional tree) is a space-partitioning data
 * structure for organizing points in a k-dimensional space. k-d trees are a
 * useful data structure for several applications, such as searches involving a
 * multidimensional search key (e.g. range searches and nearest neighbor
 * searches). k-d trees are a special case of binary space partitioning trees.
 * <p>
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @param <T> the generic type
 * @see <a href="https://en.wikipedia.org/wiki/K-d_tree">K-D Tree (Wikipedia)</a>
 * <br>
 */
public class KdTree<T extends KdTree.XYZPoint> implements Iterable<T> {

    private int k = 3;
    private KdNode root = null;

    private static final Comparator<XYZPoint> X_COMPARATOR = new Comparator<XYZPoint>() {
        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(XYZPoint p1, XYZPoint p2) {
            if (p1.x < p2.x)
                return -1;
            if (p1.x > p2.x)
                return 1;
            return 0;
        }
    };

    private static final Comparator<XYZPoint> Y_COMPARATOR = new Comparator<XYZPoint>() {
        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(XYZPoint p1, XYZPoint p2) {
            if (p1.y < p2.y)
                return -1;
            if (p1.y > p2.y)
                return 1;
            return 0;
        }
    };

    private static final Comparator<XYZPoint> Z_COMPARATOR = new Comparator<XYZPoint>() {
        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(XYZPoint p1, XYZPoint p2) {
            if (p1.z < p2.z)
                return -1;
            if (p1.z > p2.z)
                return 1;
            return 0;
        }
    };

    protected static final int X_AXIS = 0;
    protected static final int Y_AXIS = 1;
    protected static final int Z_AXIS = 2;

    /**
     * Default constructor.
     */
    public KdTree() { }

    /**
     * Constructor for creating a more balanced tree. It uses the
     * "median of points" algorithm.
     *
     * @param list
     *            of XYZPoints.
     */
    public KdTree(List<XYZPoint> list) {
        super();
        root = createNode(list, k, 0);
    }

    /**
     * Constructor for creating a more balanced tree. It uses the
     * "median of points" algorithm.
     *
     * @param list
     *            of XYZPoints.
     * @param k
     *            of the tree.
     */
    public KdTree(List<XYZPoint> list, int k) {
        super();
        root = createNode(list, k, 0);
    }
    
    private static void conditionalSort(int axis, List<XYZPoint> list, 
    		Comparator<XYZPoint> compX, Comparator<XYZPoint> compY, Comparator<XYZPoint> compZ) {
    	
    	if (axis == X_AXIS) {
    		Collections.sort(list, compX);
    	} else if (axis == Y_AXIS) {
    		Collections.sort(list, compY);
    	} else {
    		Collections.sort(list, compZ);
    	}
    }

    /**
     * Creates node from list of XYZPoints.
     *
     * @param list
     *            of XYZPoints.
     * @param k
     *            of the tree.
     * @param depth
     *            depth of the node.
     * @return node created.
     */
    private static KdNode createNode(List<XYZPoint> list, int theK, int depth) {
        if (list == null || list.size() == 0)
            return null;

        int axis = depth % theK;
        conditionalSort(axis, list, X_COMPARATOR, Y_COMPARATOR, Z_COMPARATOR);

        KdNode node = null;
        List<XYZPoint> less = new ArrayList<XYZPoint>(list.size());
        List<XYZPoint> more = new ArrayList<XYZPoint>(list.size());
        if (list.size() > 0) {
            int medianIndex = list.size() / 2;
            node = new KdNode(list.get(medianIndex), theK, depth);
            // Process list to see where each non-median point lies
            int listSize = list.size();
            for (int i = 0; i < listSize; i++) {
                if (i == medianIndex)
                    continue;
                XYZPoint p = list.get(i);
                // Cannot assume points before the median are less since they could be equal
                if (KdNode.compareTo(depth, theK, p, node.id) <= 0) {
                    less.add(p);
                } else {
                    more.add(p);
                }
            }

            node.lesser = getLesser(medianIndex, less, node, theK, depth);
            node.greater = getGreater(medianIndex, list, more, node, theK, depth);            
        }

        return node;
    }
    
    private static KdNode getGreater(int medianIndex, List<XYZPoint> list, List<XYZPoint> more, KdNode node,
    		int theK, int depth) {
    
    	if ((medianIndex <= list.size()-1) && more.size() > 0) {
            node.greater = createNode(more, theK, depth + 1);
            node.greater.parent = node;
        }
    	return node.greater;
    }
    
    private static KdNode getLesser(int medianIndex, List<XYZPoint> less, KdNode node,
    		int theK, int depth) {
    	
    	if ((medianIndex - 1 >= 0) && less.size() > 0) {
    		node.lesser = createNode(less, theK, depth + 1);
    		node.lesser.parent = node;
    	}
    	return node.lesser;
    }

    /**
     * Adds value to the tree. Tree can contain multiple equal values.
     *
     * @param value
     *            T to add to the tree.
     * @return True if successfully added to tree.
     */
    public boolean add(T value) {
        if (value == null)
            return false;

        if (root == null) {
            root = new KdNode(value);
            return true;
        }

        KdNode node = root;
        while (true) {
            if (KdNode.compareTo(node.depth, node.k, value, node.id) <= 0) {
                // Lesser
                if (node.lesser == null) {
                    KdNode newNode = new KdNode(value, k, node.depth + 1);
                    newNode.parent = node;
                    node.lesser = newNode;
                    break;
                }
                node = node.lesser;
            } else {
                // Greater
                if (node.greater == null) {
                    KdNode newNode = new KdNode(value, k, node.depth + 1);
                    newNode.parent = node;
                    node.greater = newNode;
                    break;
                }
                node = node.greater;
            }
        }

        return true;
    }

    /**
     * Does the tree contain the value.
     *
     * @param value
     *            T to locate in the tree.
     * @return True if tree contains value.
     */
    public boolean contains(T value) {
        if (value == null || root == null)
            return false;

        KdNode node = getNode(this, value);
        return (node != null);
    }

    /**
     * Locates T in the tree.
     *
     * @param tree
     *            to search.
     * @param value
     *            to search for.
     * @return KdNode or NULL if not found
     */
    private static final <T extends KdTree.XYZPoint> KdNode getNode(KdTree<T> tree, T value) {
        if (tree == null || tree.root == null || value == null)
            return null;

        KdNode node = tree.root;
        while (true) {
            if (node.id.equals(value)) {
                return node;
            } else if (KdNode.compareTo(node.depth, node.k, value, node.id) <= 0) {
                // Lesser
                if (node.lesser == null) {
                    return null;
                }
                node = node.lesser;
            } else {
                // Greater
                if (node.greater == null) {
                    return null;
                }
                node = node.greater;
            }
        }
    }

    private boolean nullCheck(T value) {
    	return value == null || root == null || getNode(this, value) == null;
    }
    
    private boolean removeUtility(KdNode parent, KdNode node) {
    	return parent.lesser != null && node.equals(parent.lesser);
    }
    
    /**
     * Removes first occurrence of value in the tree.
     *
     * @param value
     *            T to remove from the tree.
     * @return True if value was removed from the tree.
     */
    public boolean remove(T value) {

        KdNode node = getNode(this, value);
    	if (nullCheck(value)) {
    		return false;
    	}
    	
        KdNode parent = node.parent;
        if (parent != null) {
            if (removeUtility(parent, node)) {
                List<XYZPoint> nodes = getTree(node);
                if (nodes.size() > 0) {
                    parent.lesser = createNode(nodes, node.k, node.depth);
                    if (parent.lesser != null) {
                        parent.lesser.parent = parent;
                    }
                } else {
                    parent.lesser = null;
                }
            } else {
                List<XYZPoint> nodes = getTree(node);
                if (nodes.size() > 0) {
                    parent.greater = createNode(nodes, node.k, node.depth);
                    if (parent.greater != null) {
                        parent.greater.parent = parent;
                    }
                } else {
                    parent.greater = null;
                }
            }
        } else {
            // root
            List<XYZPoint> nodes = getTree(node);
            putRoot(nodes, node);
        }

        return true;
    }
    
    private void putRoot(List<XYZPoint> nodes, KdNode node) {
    	if (nodes.size() > 0)
            root = createNode(nodes, node.k, node.depth);
        else
            root = null;
    }

    /**
     * Gets the (sub) tree rooted at root.
     *
     * @param root
     *            of tree to get nodes for.
     * @return points in (sub) tree, not including root.
     */
    private static final List<XYZPoint> getTree(KdNode theRoot) {
        List<XYZPoint> list = new ArrayList<XYZPoint>();
        if (theRoot == null)
            return list;

        if (theRoot.lesser != null) {
            list.add(theRoot.lesser.id);
            list.addAll(getTree(theRoot.lesser));
        }
        if (theRoot.greater != null) {
            list.add(theRoot.greater.id);
            list.addAll(getTree(theRoot.greater));
        }

        return list;
    }

    /** 
     * Searches the K nearest neighbor.
     *
     * @param K
     *            Number of neighbors to retrieve. Can return more than K, if
     *            last nodes are equal distances.
     * @param value
     *            to find neighbors of.
     * @return Collection of T neighbors.
     */
    @SuppressWarnings("unchecked")
    public Collection<T> nearestNeighbourSearch(int K, T value) {
        if (value == null || root == null)
            return Collections.EMPTY_LIST;

        // Map used for results
        TreeSet<KdNode> results = new TreeSet<KdNode>(new EuclideanComparator(value));

        // Find the closest leaf node
        KdNode prev = null;
        KdNode node = root;
        while (node != null) {
            if (KdNode.compareTo(node.depth, node.k, value, node.id) <= 0) {
                // Lesser
                prev = node;
                node = node.lesser;
            } else {
                // Greater
                prev = node;
                node = node.greater;
            }
        }
        KdNode leaf = prev;

        if (leaf != null) {
            // Used to not re-examine nodes
            Set<KdNode> examined = new HashSet<KdNode>();

            // Go up the tree, looking for better solutions
            node = leaf;
            while (node != null) {
                // Search node
                searchNode(value, node, K, results, examined);
                node = node.parent;
            }
        }

        // Load up the collection of the results
        Collection<T> collection = new ArrayList<T>(K);
        for (KdNode kdNode : results)
            collection.add((T) kdNode.id);
        return collection;
    }
    
    private static TreeSet<KdNode> resultsSelectiveAdd(Double nodeDistance, TreeSet<KdNode> results, KdNode lastNode, Double lastDistance, KdNode node, int K) {
    	if (nodeDistance.compareTo(lastDistance) < 0) {
            if (results.size() == K && lastNode != null)
                results.remove(lastNode);
            results.add(node);
        } else if (nodeDistance.equals(lastDistance)) {
            results.add(node);
        } else if (results.size() < K) {
            results.add(node);
        }
    	return results;
    }
    
    private static boolean lesserCheck(KdNode lesser, Set<KdNode> examined) {
    	return lesser != null && !examined.contains(lesser);
    }
    
    private static boolean greaterCheck(KdNode greater, Set<KdNode> examined) {
    	return greater != null && !examined.contains(greater);
    }

    private static final <T extends KdTree.XYZPoint> void searchNode(T value, KdNode node, int K, TreeSet<KdNode> results, Set<KdNode> examined) {
        examined.add(node);

        // Search node
        KdNode lastNode = null;
        Double lastDistance = Double.MAX_VALUE;
        if (results.size() > 0) {
            lastNode = results.last();
            lastDistance = lastNode.id.euclideanDistance(value);
        }
        Double nodeDistance = node.id.euclideanDistance(value);
        results = resultsSelectiveAdd(nodeDistance, results, lastNode, lastDistance, node, K);
        
        lastNode = results.last();
        lastDistance = lastNode.id.euclideanDistance(value);

        int axis = node.depth % node.k;
        KdNode lesser = node.lesser;
        KdNode greater = node.greater;

        // Search children branches, if axis aligned distance is less than
        // current distance
        if (lesserCheck(lesser, examined)) {
            examined.add(lesser);

            double nodePoint = Double.MIN_VALUE;
            double valuePlusDistance = Double.MIN_VALUE;
            
            nodePoint = getNodePoint(axis, nodePoint, node);
            valuePlusDistance = getValuePlusDistance(axis, valuePlusDistance, value, lastDistance);
            
            boolean lineIntersectsCube = ((valuePlusDistance <= nodePoint) ? true : false);

            // Continue down lesser branch
            if (lineIntersectsCube)
                searchNode(value, lesser, K, results, examined);
        }
        if (greaterCheck(greater, examined)) {
            examined.add(greater);

            double nodePoint = Double.MIN_VALUE;
            double valuePlusDistance = Double.MIN_VALUE;
            
            nodePoint = getNodePoint(axis, nodePoint, node);
            valuePlusDistance = getValuePlusDistance2(axis, valuePlusDistance, value, lastDistance);
            
            boolean lineIntersectsCube = ((valuePlusDistance >= nodePoint) ? true : false);

            // Continue down greater branch
            if (lineIntersectsCube)
                searchNode(value, greater, K, results, examined);
        }
    }
    
    
    
    private static double getNodePoint(int axis, double nodePoint, KdNode node) {

    	if (axis == X_AXIS) {
            nodePoint = node.id.x;
        } else if (axis == Y_AXIS) {
            nodePoint = node.id.y;
        } else {
            nodePoint = node.id.z;
        }
    	return nodePoint;
    }
    
    private static <T extends KdTree.XYZPoint> double getValuePlusDistance2(int axis, double valuePlusDistance, T value, Double lastDistance) {

    	if (axis == X_AXIS) {
            valuePlusDistance = value.x + lastDistance;
        } else if (axis == Y_AXIS) {
            valuePlusDistance = value.y + lastDistance;
        } else {
            valuePlusDistance = value.z + lastDistance;
        }
    	return valuePlusDistance;
    }
    
    private static <T extends KdTree.XYZPoint> double getValuePlusDistance(int axis, double valuePlusDistance, T value, Double lastDistance) {
    	if (axis == X_AXIS) {
            valuePlusDistance = value.x - lastDistance;
        } else if (axis == Y_AXIS) {
            valuePlusDistance = value.y - lastDistance;
        } else {
            valuePlusDistance = value.z - lastDistance;
        }
    	return valuePlusDistance;
    }

    /** 
     * Adds, in a specified queue, a given node and its related nodes (lesser, greater).
     * 
     * @param node 
     *              Node to check. May be null.
     * 
     * @param results 
     *              Queue containing all found entries. Must not be null.
     */
    @SuppressWarnings("unchecked")
    private static <T extends XYZPoint> void search(final KdNode node, final Deque<T> results) {
        if (node != null) {
            results.add((T) node.id);
            search(node.greater, results);
            search(node.lesser, results);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }

    protected static class EuclideanComparator implements Comparator<KdNode> {

        private final XYZPoint point;

        public EuclideanComparator(XYZPoint point) {
            this.point = point;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(KdNode o1, KdNode o2) {
            Double d1 = point.euclideanDistance(o1.id);
            Double d2 = point.euclideanDistance(o2.id);
            if (d1.compareTo(d2) < 0)
                return -1;
            else if (d2.compareTo(d1) < 0)
                return 1;
            return o1.id.compareTo(o2.id);
        }
    }

    /** 
     * Searches all entries from the first to the last entry.
     * 
     * @return Iterator 
     *                  allowing to iterate through a collection containing all found entries.
     */
    public Iterator<T> iterator() {
        final Deque<T> results = new ArrayDeque<T>();
        search(root, results);
        return results.iterator();
    }

    /** 
     * Searches all entries from the last to the first entry.
     * 
     * @return Iterator 
     *                  allowing to iterate through a collection containing all found entries.
     */
    public Iterator<T> reverse_iterator() {
        final Deque<T> results = new ArrayDeque<T>();
        search(root, results);
        return results.descendingIterator();
    }

    /**
     * The Class KdNode.
     */
    public static class KdNode implements Comparable<KdNode> {

        private final XYZPoint id;
        private final int k;
        private final int depth;

        private KdNode parent = null;
        private KdNode lesser = null;
        private KdNode greater = null;

        /**
         * Instantiates a new kd node.
         *
         * @param id the id
         */
        public KdNode(XYZPoint id) {
            this.id = id;
            this.k = 3;
            this.depth = 0;
        }

        /**
         * Instantiates a new kd node.
         *
         * @param id the id
         * @param k the k
         * @param depth the depth
         */
        public KdNode(XYZPoint id, int k, int depth) {
            this.id = id;
            this.k = k;
            this.depth = depth;
        }

        /**
         * Compare to.
         *
         * @param theDepth the the depth
         * @param theK the the K
         * @param o1 the o 1
         * @param o2 the o 2
         * @return the int
         */
        public static int compareTo(int theDepth, int theK, XYZPoint o1, XYZPoint o2) {
            int axis = theDepth % theK;
            if (axis == X_AXIS)
                return X_COMPARATOR.compare(o1, o2);
            if (axis == Y_AXIS)
                return Y_COMPARATOR.compare(o1, o2);
            return Z_COMPARATOR.compare(o1, o2);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return 31 * (this.k + this.depth + this.id.hashCode());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass()) {
            	return false;
            }
            if (!(obj instanceof KdNode))
                return false;

            KdNode kdNode = (KdNode) obj;
            if (this.compareTo(kdNode) == 0)
                return true;
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(KdNode o) {
            return compareTo(depth, k, this.id, o.id);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("k=").append(k);
            builder.append(" depth=").append(depth);
            builder.append(" id=").append(id.toString());
            return builder.toString();
        }
    }

    /**
     * The Class XYZPoint.
     */
    public static class XYZPoint implements Comparable<XYZPoint> {

        protected final double x;
        protected final double y;
        protected final double z;

        /**
         * z is defaulted to zero.
         *
         * @param x the x
         * @param y the y
         */
        public XYZPoint(double x, double y) {
            this.x = x;
            this.y = y;
            this.z = 0;
        }

        /**
         * Default constructor.
         *
         * @param x the x
         * @param y the y
         * @param z the z
         */
        public XYZPoint(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * Does not use R to calculate x, y, and z. Where R is the approximate radius of earth (e.g. 6371KM).
         *
         * @param latitude the latitude
         * @param longitude the longitude
         */
        public XYZPoint(Double latitude, Double longitude) {
            x = cos(Math.toRadians(latitude)) * cos(Math.toRadians(longitude));
            y = cos(Math.toRadians(latitude)) * sin(Math.toRadians(longitude));
            z = sin(Math.toRadians(latitude));
        }

        /**
         * Gets the x.
         *
         * @return the x
         */
        public double getX() {
            return x;
        }
        
        /**
         * Gets the y.
         *
         * @return the y
         */
        public double getY() {
            return y;
        }
        
        /**
         * Gets the z.
         *
         * @return the z
         */
        public double getZ() {
            return z;
        }

        /**
         * Computes the Euclidean distance from this point to the other.
         *
         * @param o1
         *            other point.
         * @return euclidean distance.
         */
        public double euclideanDistance(XYZPoint o1) {
            return euclideanDistance(o1, this);
        }

        /**
         * Computes the Euclidean distance from one point to the other.
         *
         * @param o1
         *            first point.
         * @param o2
         *            second point.
         * @return euclidean distance.
         */
        private static final double euclideanDistance(XYZPoint o1, XYZPoint o2) {
            return Math.sqrt(Math.pow((o1.x - o2.x), 2) + Math.pow((o1.y - o2.y), 2) + Math.pow((o1.z - o2.z), 2));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return 31 * (int)(this.x + this.y + this.z);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass()) {
            	return false;
            }
            if (!(obj instanceof XYZPoint))
                return false;

            XYZPoint xyzPoint = (XYZPoint) obj;
            if (Double.compare(this.x, xyzPoint.x)!=0)
                return false;
            if (Double.compare(this.y, xyzPoint.y)!=0)
                return false;
            if (Double.compare(this.z, xyzPoint.z)!=0)
                return false;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(XYZPoint o) {
            int xComp = X_COMPARATOR.compare(this, o);
            if (xComp != 0)
                return xComp;
            int yComp = Y_COMPARATOR.compare(this, o);
            if (yComp != 0)
                return yComp;
            int zComp = Z_COMPARATOR.compare(this, o);
            return zComp;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            builder.append(x).append(", ");
            builder.append(y).append(", ");
            builder.append(z);
            builder.append(")");
            return builder.toString();
        }
    }

    protected static class TreePrinter {

        public static <T extends XYZPoint> String getString(KdTree<T> tree) {
            if (tree.root == null)
                return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static String getString(KdNode node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            if (node.parent != null) {
                String side = "left";
                if (node.parent.greater != null && node.id.equals(node.parent.greater.id))
                    side = "right";
                builder.append(prefix + getStringTailUtility(isTail) + "[" + side + "] " + "depth=" + node.depth + " id="
                        + node.id + System.getProperty("line.separator"));
            } else {
                builder.append(prefix + getStringTailUtility(isTail) + "depth=" + node.depth + " id=" + node.id + System.getProperty("line.separator"));
            }
            List<KdNode> children = null;

            children = initialize(node, children);
            if (children != null) {
            	int size = children.size();
                for (int i = 0; i < size - 1; i++) {
                    builder.append(getString(children.get(i), prefix + getStringTailUtility2(isTail), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString(children.get(children.size() - 1), prefix + getStringTailUtility2(isTail),
                            true));
                }
            }
            
           

            return builder.toString();
        }
        
        private static List<KdNode> initialize(KdNode node, List<KdNode> children) {
        	if (node.lesser != null || node.greater != null) {
        		children = new ArrayList<KdNode>(2);
        		if (node.lesser != null) {
        			children.add(node.lesser);
        		}
        		if (node.greater != null) {
        			children.add(node.greater);
        		}
        	}
        	return children;
        }
        
        private static String getStringTailUtility(boolean isTail) {
        	return isTail ? "└── " : "├── ";
        }
        
        private static String getStringTailUtility2(boolean isTail) {
        	return isTail ? "    " : "│   ";
        }
    }
}

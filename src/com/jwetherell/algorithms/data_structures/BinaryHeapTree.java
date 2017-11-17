package com.jwetherell.algorithms.data_structures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class BinaryHeapTree.
 *
 * @param <T> the generic type
 */
public class BinaryHeapTree<T extends Comparable<T>> implements BinaryHeap<T> {


    private Type type = Type.MIN;
    private int size = 0;
    private Node<T> root = null;

    /**
     * Constructor for heap, defaults to a min-heap.
     */
    public BinaryHeapTree() {
        root = null;
        size = 0;
    }

    /**
     * Constructor for heap.
     * 
     * @param type
     *            Heap type.
     */
    public BinaryHeapTree(Type type) {
        this();
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Get the navigation directions through the tree to the index.
     * 
     * @param index
     *            of the Node to get directions for.
     * @return Integer array representing the directions to the index.
     */
    private static int[] getDirections(int idx) {
        int index = idx;
        int directionsSize = (int) (Math.log10(index + 1) / Math.log10(2)) - 1;
        int[] directions = null;
        if (directionsSize > 0) {
            directions = new int[directionsSize];
            int i = directionsSize - 1;
            while (i >= 0) {
                index = (index - 1) / 2;
                directions[i--] = (index > 0 && index % 2 == 0) ? 1 : 0; // 0=left, 1=right
            }
        }
        return directions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T value) {
        return add(new Node<T>(null, value));
    }

    private boolean add(Node<T> newNode) {
        if (root == null) {
            root = newNode;
            size++;
            return true;
        }

        Node<T> node = root;
        int[] directions = getDirections(size); // size == index of new node
        if (directions != null && directions.length > 0) {
            for (int d : directions) {
                if (d == 0) {
                    // Go left
                    node = node.left;
                } else {
                    // Go right
                    node = node.right;
                }
            }
        }
        if (node.left == null) {
            node.left = newNode;
        } else {
            node.right = newNode;
        }
        newNode.parent = node;

        size++;

        heapUp(newNode);

        return true;
    }

    /**
     * Remove the root node.
     */
    private void removeRoot() {
        replaceNode(root);
    }

    private Node<T> getLastNode() {
        // Find the last node
        int[] directions = getDirections(size-1); // Directions to the last node
        Node<T> lastNode = root;
        if (directions != null && directions.length > 0) {
            for (int d : directions) {
                if (d == 0) {
                    // Go left
                    lastNode = lastNode.left;
                } else {
                    // Go right
                    lastNode = lastNode.right;
                }
            }
        }
        if (lastNode.right != null) {
            lastNode = lastNode.right;
        } else if (lastNode.left != null) {
            lastNode = lastNode.left;
        }
        return lastNode;
    }

    /**
     * Replace the node with the last node and heap down.
     * 
     * @param node to replace.
     */
    private void replaceNode(Node<T> node) {
        Node<T> lastNode = getLastNode();

        // Remove lastNode from tree
        Node<T> lastNodeParent = lastNode.parent;
        if (lastNodeParent!=null) {
            if (lastNodeParent.right != null) {
                lastNodeParent.right = null;
            } else {
                lastNodeParent.left = null;
            }
            lastNode.parent = null;
        }

        if (node.parent!=null) {
            if (node.parent.left.equals(node)) {
                node.parent.left = lastNode;
            } else {
                node.parent.right = lastNode;
            }
        }
        lastNode.parent = node.parent;

        lastNode.left = node.left;
        if (node.left!=null) node.left.parent = lastNode;

        lastNode.right = node.right;
        if (node.right!=null) node.right.parent = lastNode;

        if (node.equals(root)) {
            if (!lastNode.equals(root)) root = lastNode;
            else root = null;
        }

        size--;

        // Last node is the node to remove
        if (lastNode.equals(node)) return;

        if (lastNode.equals(root)) {
            heapDown(lastNode);
        } else {
            heapDown(lastNode);
            heapUp(lastNode);
        }
    }

    /**
     * Get the node in the startingNode sub-tree which has the value.
     * 
     * @param startingNode
     *            node rooted sub-tree to search in.
     * @param value
     *            to search for.
     * @return Node<T> which equals value in sub-tree or NULL if not found.
     */
    private Node<T> getNode(Node<T> startingNode, T value) {
        Node<T> result = null;
        if (startingNode != null && startingNode.value.equals(value)) {
            result = startingNode;
        } else if (startingNode != null && !startingNode.value.equals(value)) {
            Node<T> left = startingNode.left;
            Node<T> right = startingNode.right;
            if (left != null 
                && ((type==Type.MIN && left.value.compareTo(value)<=0)||(type==Type.MAX && left.value.compareTo(value)>=0))
            ) {
                result = getNode(left, value);
                if (result != null) return result;
            }
            if (right != null 
                && ((type==Type.MIN && right.value.compareTo(value)<=0)||(type==Type.MAX && right.value.compareTo(value)>=0))
            ) {
                result = getNode(right, value);
                if (result != null) return result;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        if (root == null) return false;
        Node<T> node = getNode(root,value);
        return (node != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T remove(T value) {
        if (root == null) return null;
        Node<T> node = getNode(root,value);
        if (node!=null) {
            T t = node.value;
            replaceNode(node);
            return t;
        }
        return null;
    }

    /**
     * Heap up the heap from this node.
     * 
     * @param nodeToHeapUp
     *            to heap up.
     */
    protected void heapUp(Node<T> nodeToHeapUp) {
        Node<T> node = nodeToHeapUp;
        while (node != null) {
            Node<T> heapNode = node;
            Node<T> parent = heapNode.parent;

            if ((parent != null) && 
                ((type == Type.MIN && node.value.compareTo(parent.value) < 0) || (type == Type.MAX && node.value.compareTo(parent.value) > 0))
            ) {
                // Node is less than parent, switch node with parent
                Node<T> grandParent = parent.parent;
                Node<T> parentLeft = parent.left;
                Node<T> parentRight = parent.right;

                parent.left = heapNode.left;
                if (parent.left != null) parent.left.parent = parent;
                parent.right = heapNode.right;
                if (parent.right != null) parent.right.parent = parent;

                if (parentLeft != null && parentLeft.equals(node)) {
                    heapNode.left = parent;
                    heapNode.right = parentRight;
                    if (parentRight != null) parentRight.parent = heapNode;
                } else {
                    heapNode.right = parent;
                    heapNode.left = parentLeft;
                    if (parentLeft != null) parentLeft.parent = heapNode;
                }
                parent.parent = heapNode;

                if (grandParent == null) {
                    // New root.
                    heapNode.parent = null;
                    root = heapNode;
                } else {
                    Node<T> grandLeft = grandParent.left;
                    if (grandLeft != null && grandLeft.equals(parent)) {
                        grandParent.left = heapNode;
                    } else {
                        grandParent.right = heapNode;
                    }
                    heapNode.parent = grandParent;
                }
            } else {
                node = heapNode.parent;
            }
        }
    }

    /**
     * Heap down the heap from this node.
     * 
     * @param nodeToHeapDown
     *            to heap down.
     */
    protected void heapDown(Node<T> nodeToHeapDown) {
        if (nodeToHeapDown==null) return;

        Node<T> node = nodeToHeapDown;
        Node<T> heapNode = node;
        Node<T> left = heapNode.left;
        Node<T> right = heapNode.right;

        if (left == null && right == null) {
            // Nothing to do here
            return;
        }

        Node<T> nodeToMove = null;

        if ((left != null && right != null ) &&
            ((type == Type.MIN && node.value.compareTo(left.value) > 0 && node.value.compareTo(right.value) > 0)
            || (type == Type.MAX && node.value.compareTo(left.value) < 0 && node.value.compareTo(right.value) < 0))
         ) {
            // Both children are greater/lesser than node
            if ((type == Type.MIN && right.value.compareTo(left.value) < 0) || (type == Type.MAX && right.value.compareTo(left.value) > 0)) {
                // Right is greater/lesser than left
                nodeToMove = right;
            } else if ((type == Type.MIN && left.value.compareTo(right.value) < 0) || (type == Type.MAX && left.value.compareTo(right.value) > 0)) {
                // Left is greater/lesser than right
                nodeToMove = left;
            } else {
                // Both children are equal, use right
                nodeToMove = right;
            }
        } else if ((type == Type.MIN && right != null && node.value.compareTo(right.value) > 0)
                   || (type == Type.MAX && right != null && node.value.compareTo(right.value) < 0)) {
            // Right is greater than node
            nodeToMove = right;
        } else if ((type == Type.MIN && left != null && node.value.compareTo(left.value) > 0)
                   || (type == Type.MAX && left != null && node.value.compareTo(left.value) < 0)) {
            // Left is greater than node
            nodeToMove = left;
        }
        // No node to move, stop recursion
        if (nodeToMove == null) return;

        // Re-factor heap sub-tree
        Node<T> nodeParent = heapNode.parent;
        if (nodeParent == null) {
            // heap down the root
            root = nodeToMove;
            root.parent = null;
        } else {
            if (nodeParent.left!=null && nodeParent.left.equals(node)) {
                // heap down a left
                nodeParent.left = nodeToMove;
                nodeToMove.parent = nodeParent;
            } else {
                // heap down a right
                nodeParent.right = nodeToMove;
                nodeToMove.parent = nodeParent;
            }
        }

        Node<T> nodeLeft = heapNode.left;
        Node<T> nodeRight = heapNode.right;
        Node<T> nodeToMoveLeft = nodeToMove.left;
        Node<T> nodeToMoveRight = nodeToMove.right;
        if (nodeLeft!=null && nodeLeft.equals(nodeToMove)) {
            nodeToMove.right = nodeRight;
            if (nodeRight != null) nodeRight.parent = nodeToMove;

            nodeToMove.left = heapNode;
        } else {
            nodeToMove.left = nodeLeft;
            if (nodeLeft != null) nodeLeft.parent = nodeToMove;

            nodeToMove.right = heapNode;
        }
        heapNode.parent = nodeToMove;

        heapNode.left = nodeToMoveLeft;
        if (nodeToMoveLeft != null) nodeToMoveLeft.parent = heapNode;

        heapNode.right = nodeToMoveRight;
        if (nodeToMoveRight != null) nodeToMoveRight.parent = heapNode;

        heapDown(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        if (root == null) return true;
        return validateNode(root);
    }

    /**
     * Validate node for heap invariants.
     * 
     * @param node
     *            to validate for.
     * @return True if node is valid.
     */
    private boolean validateNode(Node<T> node) {
        Node<T> left = node.left;
        Node<T> right = node.right;

        // We shouldn't ever have a right node without a left in a heap
        if (right != null && left == null)
            return false;

        if (left != null) {
            if ((type == Type.MIN && node.value.compareTo(left.value) < 0)
                    || (type == Type.MAX && node.value.compareTo(left.value) > 0)) {
                return validateNode(left);
            }
            return false;
        }
        if (right != null) {
            if ((type == Type.MIN && node.value.compareTo(right.value) < 0)
                    || (type == Type.MAX && node.value.compareTo(right.value) > 0)) {
                return validateNode(right);
            }
            return false;
        }

        return true;
    }

    /**
     * Populate the node in the array at the index.
     * 
     * @param node
     *            to populate.
     * @param idx
     *            of node in array.
     * @param array
     *            where the node lives.
     */
    private void getNodeValue(Node<T> node, int idx, T[] array) {
        int index = idx;
        array[index] = node.value;
        index = (index * 2) + 1;

        Node<T> left = node.left;
        if (left != null)
            getNodeValue(left, index, array);
        Node<T> right = node.right;
        if (right != null)
            getNodeValue(right, index + 1, array);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T[] getHeap() {
        T[] nodes = (T[]) new Comparable[size];
        if (root != null)
            getNodeValue(root, 0, nodes);
        return nodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getHeadValue() {
        T result = null;
        if (root != null)
            result = root.value;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T removeHead() {
        T result = null;
        if (root != null) {
            result = root.value;
            removeRoot();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Collection<T> toCollection() {
        return (new JavaCompatibleBinaryHeapTree<T>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return HeapPrinter.getString(this);
    }

    protected static class HeapPrinter {

        public static <T extends Comparable<T>> void print(BinaryHeapTree<T> tree) {
            System.out.println(getString(tree.root, "", true));
        }

        public static <T extends Comparable<T>> String getString(BinaryHeapTree<T> tree) {
            if (tree.root == null)
                return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <T extends Comparable<T>> String getString(Node<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + (isTail ? "└── " : "├── ") + node.value + "\n");
            List<Node<T>> children = null;
            if (node.left != null || node.right != null) {
                children = new ArrayList<Node<T>>(2);
                if (node.left != null)
                    children.add(node.left);
                if (node.right != null)
                    children.add(node.right);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString(children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString(children.get(children.size() - 1),
                            prefix + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }

    private static class Node<T extends Comparable<T>> {
    	 private T value = null;
         private Node<T> parent = null;
         private Node<T> left = null;
         private Node<T> right = null;

         private Node(Node<T> parent, T value) {
             this.value = value;
             this.parent = parent;
         }

         /**
          * {@inheritDoc}
          */
         @Override
         public String toString() {
             return "value=" + value + " parent=" + ((parent != null) ? parent.value : "NULL") + " left="
                     + ((left != null) ? left.value : "NULL") + " right=" + ((right != null) ? right.value : "NULL");
         }
    }
    

    /**
     * The Class JavaCompatibleBinaryHeapTree.
     *
     * @param <T> the generic type
     */
    public static class JavaCompatibleBinaryHeapTree<T extends Comparable<T>> extends java.util.AbstractCollection<T> {

        private BinaryHeapTree<T> heap = null;

        /**
         * Instantiates a new java compatible binary heap tree.
         */
        public JavaCompatibleBinaryHeapTree() {
            heap = new BinaryHeapTree<T>();
        }

        /**
         * Instantiates a new java compatible binary heap tree.
         *
         * @param heap the heap
         */
        public JavaCompatibleBinaryHeapTree(BinaryHeapTree<T> heap) {
            this.heap = heap;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return heap.add(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return (heap.remove((T)value)!=null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return heap.contains((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return heap.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Iterator<T> iterator() {
            return (new BinaryHeapTreeIterator<T>(this.heap));
        }

        private static class BinaryHeapTreeIterator<C extends Comparable<C>> implements java.util.Iterator<C> {

            private BinaryHeapTree<C> heap = null;
            private BinaryHeapTree.Node<C> last = null;
            private Deque<BinaryHeapTree.Node<C>> toVisit = new ArrayDeque<BinaryHeapTree.Node<C>>();

            protected BinaryHeapTreeIterator(BinaryHeapTree<C> heap) {
                this.heap = heap;
                if (heap.root!=null) toVisit.add(heap.root);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                if (toVisit.size()>0) return true; 
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public C next() {
                while (toVisit.size()>0) {
                    // Go thru the current nodes
                    BinaryHeapTree.Node<C> n = toVisit.pop();

                    // Add non-null children
                    if (n.left!=null) toVisit.add(n.left);
                    if (n.right!=null) toVisit.add(n.right);

                    // Update last node (used in remove method)
                    last = n;
                    return n.value;
                }
                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                heap.replaceNode(last);
            }
        }
    }
}

package com.jwetherell.algorithms.data_structures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/**
 * A red–black tree is a type of self-balancing binary search tree, a data
 * structure used in computer science, typically to implement associative
 * arrays. A red–black tree is a binary search tree that inserts and deletes in
 * such a way that the tree is always reasonably balanced. Red-black trees are
 * often compared with AVL trees. AVL trees are more rigidly balanced, they are
 * faster than red-black trees for lookup intensive applications. However,
 * red-black trees are faster for insertion and removal.
 * <p>
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @param <T> the generic type
 * @see <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree">Red-Black Tree (Wikipedia)</a>
 * <br>
 */
@SuppressWarnings("unchecked")
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    protected static final boolean BLACK = false;
    protected static final boolean RED = true;

    /**
     * Default constructor.
     */
    public RedBlackTree() {
        this.creator = new BinarySearchTree.INodeCreator<T>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public BinarySearchTree.Node<T> createNewNode(BinarySearchTree.Node<T> parent, T id) {
                return (new RedBlackNode<T>(parent, id, BLACK));
            }
        };
    }

    /**
     * Constructor with external Node creator.
     *
     * @param creator the creator
     */
    public RedBlackTree(INodeCreator<T> creator) {
        super(creator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Node<T> addValue(T id) {
        if (root == null) {
            // Case 1 - The current node is at the root of the tree.

            // Defaulted to black in our creator
            root = this.creator.createNewNode(null, id);
            root.lesser = this.creator.createNewNode(root, null);
            root.greater = this.creator.createNewNode(root, null);

            size++;
            return root;
        }

        RedBlackNode<T> nodeAdded = null;
        // Insert node like a BST would
        Node<T> node = root;
        while (node != null) {
            if (node.id == null) {
                node.id = id;
                ((RedBlackNode<T>) node).color = RED;

                // Defaulted to black in our creator
                node.lesser = this.creator.createNewNode(node, null);
                node.greater = this.creator.createNewNode(node, null);

                nodeAdded = (RedBlackNode<T>) node;
                break;
            } else if (id.compareTo(node.id) <= 0) {
                node = node.lesser;
            } else {
                node = node.greater;
            }
        }

        if (nodeAdded != null)
            balanceAfterInsert(nodeAdded);

        size++;
        return nodeAdded;
    }

    private boolean colorCheck(RedBlackNode<T> parent, RedBlackNode<T> uncle, boolean color1, boolean color2) {
    	return parent.color == color1 && uncle.color == color2;
    }
    
    private boolean greaterLesserCheck(RedBlackNode<T> node, RedBlackNode<T> parent, RedBlackNode<T> grandParent) {
    	return node == parent.greater && parent == grandParent.lesser;
    }
    
    private boolean lesserGreaterCheck(RedBlackNode<T> node, RedBlackNode<T> parent, RedBlackNode<T> grandParent) {
    	return node == parent.lesser && parent == grandParent.greater;
    }
    
    private boolean lesserLesserCheck(RedBlackNode<T> node, RedBlackNode<T> parent, RedBlackNode<T> grandParent) {
    	return node == parent.lesser && parent == grandParent.lesser;
    }
    
    private boolean greaterGreaterCheck(RedBlackNode<T> node, RedBlackNode<T> parent, RedBlackNode<T> grandParent) {
    	return node == parent.greater && parent == grandParent.greater;
    }
    
    private void setGrandParent(RedBlackNode<T> grandParent) {
    	if (grandParent != null) {
            grandParent.color = RED;
            balanceAfterInsert(grandParent);
        }
    }
    
    /**
     * Post insertion balancing algorithm.
     * 
     * @param begin
     *            to begin balancing at.
     * @return True if balanced.
     */
    private void balanceAfterInsert(RedBlackNode<T> begin) {
        RedBlackNode<T> node = begin;
        RedBlackNode<T> parent = (RedBlackNode<T>) node.parent;

        if (parent == null) {
            // Case 1 - The current node is at the root of the tree.
            node.color = BLACK;
            return;
        }

        if (parent.color == BLACK) {
            // Case 2 - The current node's parent is black, so property 4 (both
            // children of every red node are black) is not invalidated.
            return;
        }

        RedBlackNode<T> grandParent = node.getGrandParent();
        RedBlackNode<T> uncle = node.getUncle(grandParent);
        if (colorCheck(parent, uncle, RED, RED)) {
            // Case 3 - If both the parent and the uncle are red, then both of
            // them can be repainted black and the grandparent becomes
            // red (to maintain property 5 (all paths from any given node to its
            // leaf nodes contain the same number of black nodes)).
            parent.color = BLACK;
            uncle.color = BLACK;
            setGrandParent(grandParent);
            
            return;
        }

        if (colorCheck(parent, uncle, RED, BLACK)) {
            // Case 4 - The parent is red but the uncle is black; also, the
            // current node is the right child of parent, and parent in turn
            // is the left child of its parent grandparent.
            if (greaterLesserCheck(node, parent, grandParent)) {
                // right-left
                rotateLeft(parent);
 
                node = (RedBlackNode<T>) node.lesser;
                parent = (RedBlackNode<T>) node.parent;
                grandParent = node.getGrandParent();
                uncle = node.getUncle(grandParent);
            } else if (lesserGreaterCheck(node, parent, grandParent)) {
                // left-right
                rotateRight(parent);

                node = (RedBlackNode<T>) node.greater;
                parent = (RedBlackNode<T>) node.parent;
                grandParent = node.getGrandParent();
                uncle = node.getUncle(grandParent);
            }
        }
        
        redBlackBalance(node, uncle, parent, grandParent);
    }
    
    private void redBlackBalance(RedBlackNode<T> node, RedBlackNode<T> uncle, RedBlackNode<T> parent, RedBlackNode<T> grandParent) {
    	if (colorCheck(parent, uncle, RED, BLACK)) {
            // Case 5 - The parent is red but the uncle is black, the
            // current node is the left child of parent, and parent is the
            // left child of its parent G.
            parent.color = BLACK;
            grandParent.color = RED;
            rotateRightDirection(node, parent, grandParent);
        }
    }
    
    private void rotateRightDirection(RedBlackNode<T> node, RedBlackNode<T> parent, RedBlackNode<T> grandParent) {

        if (lesserLesserCheck(node, parent, grandParent)) {
            // left-left
            rotateRight(grandParent);
        } else if (greaterGreaterCheck(node, parent, grandParent)) {
            // right-right
            rotateLeft(grandParent);
        }
    }
    
    private RedBlackNode<T> getNodeToRemoved(RedBlackNode<T> nodeToRemoved) {
    	if (nodeToRemoved == root) {
    		root = null;
    	} else {
    		nodeToRemoved.id = null;
    		nodeToRemoved.color = BLACK;
    		nodeToRemoved.lesser = null;
    		nodeToRemoved.greater = null;
    	}
    	return nodeToRemoved;
    }
    
    private boolean nullCheck(RedBlackNode<T> lesser, RedBlackNode<T> greater) {
    	return lesser.id != null && greater.id != null;
    }
    
    private RedBlackNode<T> getGreatestInLesser(RedBlackNode<T> greatestInLesser, RedBlackNode<T> lesser) {
    	if (greatestInLesser == null || greatestInLesser.id == null) 
            greatestInLesser = lesser;
    	return greatestInLesser;
    }
    
    private RedBlackNode<T> getChild(RedBlackNode<T> lesser, RedBlackNode<T> greater) {
    	return (RedBlackNode<T>) ((lesser.id != null) ? lesser : greater);
    }
    
    private RedBlackNode<T> getNodeToRemovedColor(RedBlackNode<T> nodeToRemoved, RedBlackNode<T> child) {
    	if (child.color == BLACK) 
            nodeToRemoved.color = RED;
    	return nodeToRemoved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Node<T> removeNode(Node<T> node) {
    	
        if (node == null) {
        	return node;
        }

        RedBlackNode<T> nodeToRemoved = (RedBlackNode<T>) node;

        if (nodeToRemoved.isLeaf()) {
            // No children
            nodeToRemoved.id = null;
            
            nodeToRemoved = getNodeToRemoved(nodeToRemoved);

            size--;
            return nodeToRemoved;
        }

        // At least one child

        // Keep the id and assign it to the replacement node
        T id = nodeToRemoved.id;
        RedBlackNode<T> lesser = (RedBlackNode<T>) nodeToRemoved.lesser;
        RedBlackNode<T> greater = (RedBlackNode<T>) nodeToRemoved.greater;
        if (nullCheck(lesser, greater)) {
            // Two children
            RedBlackNode<T> greatestInLesser = (RedBlackNode<T>) this.getGreatest(lesser);
            greatestInLesser = getGreatestInLesser(greatestInLesser, lesser);

            // Replace node with greatest in his lesser tree, which leaves us with only one child
            replaceValueOnly(nodeToRemoved, greatestInLesser);
            nodeToRemoved = greatestInLesser;
            lesser = (RedBlackNode<T>) nodeToRemoved.lesser;
            greater = (RedBlackNode<T>) nodeToRemoved.greater;
        }

        // Handle one child
        RedBlackNode<T> child = getChild(lesser, greater);
        if (nodeToRemoved.color == BLACK) {
        	nodeToRemoved = getNodeToRemovedColor(nodeToRemoved, child);
            
            boolean result = balanceAfterDelete(nodeToRemoved);
            if (!result) 
                return nodeToRemoved;
        }

        // Replacing node with child
        replaceWithChild(nodeToRemoved, child);
        // Add the id to the child because it represents the node that was removed.
        child.id = id;
        if (root == nodeToRemoved) {
            root.parent = null;
            ((RedBlackNode<T>) root).color = BLACK;
            // If we replaced the root with a leaf, just null out root
            
            isLeafCheck(nodeToRemoved);
        }
        nodeToRemoved = child;

        size--;
        return nodeToRemoved;
    }
    
    private void isLeafCheck(RedBlackNode<T> nodeToRemoved) {
    	if (nodeToRemoved.isLeaf()) 
            root = null;
    }

    /**
     * Replace value of nodeToReplaceWith with nodeToReplace.
     * 
     * @param nodeToReplace
     *            will get value of nodeToReplaceWith.
     * @param nodeToReplaceWith
     *            will get value NULLed.
     */
    private void replaceValueOnly(RedBlackNode<T> nodeToReplace, RedBlackNode<T> nodeToReplaceWith) {
        nodeToReplace.id = nodeToReplaceWith.id;
        nodeToReplaceWith.id = null;
    }

    /**
     * Replace entire contents of nodeToReplace with nodeToReplaceWith.
     * 
     * @param nodeToReplace
     *            will get it's contents replace with nodeToReplaceWith
     *            contents.
     * @param nodeToReplaceWith
     *            will not be changed.
     */
    private void replaceWithChild(RedBlackNode<T> nodeToReplace, RedBlackNode<T> nodeToReplaceWith) {
        nodeToReplace.id = nodeToReplaceWith.id;
        nodeToReplace.color = nodeToReplaceWith.color;

        nodeToReplace.lesser = nodeToReplaceWith.lesser;
        if (nodeToReplace.lesser!=null) 
            nodeToReplace.lesser.parent = nodeToReplace;

        nodeToReplace.greater = nodeToReplaceWith.greater;
        if (nodeToReplace.greater!=null) 
            nodeToReplace.greater.parent = nodeToReplace;
    }
    
    private boolean colorCheck1(RedBlackNode<T> parent, RedBlackNode<T> sibling) {
    	return parent.color == BLACK 
                && sibling.color == BLACK 
                && ((RedBlackNode<T>) sibling.lesser).color == BLACK
                && ((RedBlackNode<T>) sibling.greater).color == BLACK;            
    }
    
    private boolean colorCheck2(RedBlackNode<T> parent, RedBlackNode<T> sibling) {
    	return parent.color == RED 
                && sibling.color == BLACK 
                && ((RedBlackNode<T>) sibling.lesser).color == BLACK
                && ((RedBlackNode<T>) sibling.greater).color == BLACK;            
    }
    
    private boolean colorCheck3(RedBlackNode<T> node, RedBlackNode<T> parent, RedBlackNode<T> sibling) {
    	return node == parent.lesser 
                && ((RedBlackNode<T>) sibling.lesser).color == RED
                && ((RedBlackNode<T>) sibling.greater).color == BLACK;            
    }
    
    private boolean colorCheck4(RedBlackNode<T> node, RedBlackNode<T> parent, RedBlackNode<T> sibling) {
    	return node == parent.greater 
                && ((RedBlackNode<T>) sibling.lesser).color == BLACK
                && ((RedBlackNode<T>) sibling.greater).color == RED;
    }
    
    private void balanceDeleteUtility(RedBlackNode<T> node, RedBlackNode<T> parent) {
    	if (node == parent.lesser) {
    		rotateLeft(parent);
    	} else if (node == parent.greater) {
    		rotateRight(parent);
    	} else {
    		throw new UnrelatedParentNodeException(node.toString());
    	}
    }
    
    private RedBlackNode<T> getParentUtility(RedBlackNode<T> node, RedBlackNode<T> parent) {
    	if (node == parent.lesser) {
            parent = (RedBlackNode<T>) node.parent;
        } else if (node == parent.greater) {
            parent = (RedBlackNode<T>) node.parent;
        }
    	return parent;
    }
    
    private RedBlackNode<T> getSiblingUtility(RedBlackNode<T> node, RedBlackNode<T> parent, RedBlackNode<T> sibling) {
    	if (node == parent.lesser) {
    		sibling = node.getSibling();
    	} else if (node == parent.greater) {
    		sibling = node.getSibling();
    	}
    	return sibling;
    }

    /**
     * Post delete balancing algorithm.
     * 
     * @param node
     *            to begin balancing at.
     * @return True if balanced or false if error.
     */
    private boolean balanceAfterDelete(RedBlackNode<T> node) {
        if (node.parent == null) {
            // Case 1 - node is the new root.
            return true;
        }

        RedBlackNode<T> parent = (RedBlackNode<T>) node.parent;
        RedBlackNode<T> sibling = node.getSibling();
        if (sibling.color == RED) {
            // Case 2 - sibling is red.
            parent.color = RED;
            sibling.color = BLACK;
            
            balanceDeleteUtility(node, parent);
            parent = getParentUtility(node, parent);
            sibling = getSiblingUtility(node, parent, sibling);
            
        }

        if (colorCheck1(parent, sibling)) {
            // Case 3 - parent, sibling, and sibling's children are black.
            sibling.color = RED;
            return balanceAfterDelete(parent);
        } 

        if (colorCheck2(parent, sibling)) {
            // Case 4 - sibling and sibling's children are black, but parent is red.
            sibling.color = RED;
            parent.color = BLACK;
            return true;
        }

        if (sibling.color == BLACK) {
            // Case 5 - sibling is black, sibling's left child is red,
            // sibling's right child is black, and node is the left child of
            // its parent.
            if (colorCheck3(node, parent, sibling)) {
                sibling.color = RED;
                ((RedBlackNode<T>) sibling.lesser).color = RED;

                rotateRight(sibling);

                // Rotation, need to update parent/sibling
                parent = (RedBlackNode<T>) node.parent;
                sibling = node.getSibling();
            } else if (colorCheck4(node, parent, sibling)) {
                sibling.color = RED;
                ((RedBlackNode<T>) sibling.greater).color = RED;

                rotateLeft(sibling);

                // Rotation, need to update parent/sibling
                parent = (RedBlackNode<T>) node.parent;
                sibling = node.getSibling();
            }
        }

        // Case 6 - sibling is black, sibling's right child is red, and node
        // is the left child of its parent.
        sibling.color = parent.color;
        parent.color = BLACK;
        rotateRightDirection2(node, parent, sibling);

        return true;
    }
    
    private void rotateRightDirection2(RedBlackNode<T> node, RedBlackNode<T> parent, RedBlackNode<T> sibling) {
    	if (node == parent.lesser) {
            ((RedBlackNode<T>) sibling.greater).color = BLACK;
            rotateLeft(node.parent);
        } else if (node == parent.greater) {
            ((RedBlackNode<T>) sibling.lesser).color = BLACK;
            rotateRight(node.parent);
        } else {
            throw new UnrelatedParentNodeException(node.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        if (root == null)
            return true;

        if (((RedBlackNode<T>) root).color == RED) {
            // Root node should be black
            return false;
        }

        return this.validateNode(root);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean validateNode(Node<T> node) {
    	
    	boolean res = true;
    	
        RedBlackNode<T> rbNode = (RedBlackNode<T>) node;
        RedBlackNode<T> lesser = (RedBlackNode<T>) rbNode.lesser;
        RedBlackNode<T> greater = (RedBlackNode<T>) rbNode.greater;

        // Leafs should not be red
        res &= !rbNode.isLeaf() || !rbNode.color == RED;

        res &= notTwoRedInARow(rbNode, lesser, greater);
        
        res &= lesserChecks(rbNode, lesser);

        if (!greater.isLeaf()) {
            // Check BST property
            res &= greater.id.compareTo(rbNode.id) > 0;
            // Check red-black property
            res &= this.validateNode(greater);
        }

        return res;
    }
    
    private boolean lesserChecks(RedBlackNode<T> rbNode, RedBlackNode<T> lesser) {
    	boolean res = true;
    	if (!lesser.isLeaf()) {
            // Check BST property
            res &= lesser.id.compareTo(rbNode.id) <= 0;
            // Check red-black property
            res &= this.validateNode(lesser);
        }
    	return res;
    }
    
    private boolean notTwoRedInARow(RedBlackNode<T> node, RedBlackNode<T> lesser, RedBlackNode<T> greater) {
    	boolean res = true;
    	if (node.color == RED) {
            // You should not have two red nodes in a row
            if (lesser.color == RED) res = false;
            if (greater.color == RED) res = false;
        }
    	return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Collection<T> toCollection() {
        return (new JavaCompatibleRedBlackTree<T>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return RedBlackTreePrinter.getString(this);
    }

    protected static class RedBlackNode<T extends Comparable<T>> extends Node<T> {

        protected boolean color = BLACK;

        protected RedBlackNode(Node<T> parent, T id, boolean color) {
            super(parent, id);
            this.color = color;
        }

        protected RedBlackNode<T> getGrandParent() {
            if (parent == null || parent.parent == null) return null;
            return (RedBlackNode<T>) parent.parent;
        }

        protected RedBlackNode<T> getUncle(RedBlackNode<T> grandParent) {
            if (grandParent == null) return null;
            if (grandParent.lesser != null && grandParent.lesser == parent) {
                return (RedBlackNode<T>) grandParent.greater;
            } else if (grandParent.greater != null && grandParent.greater == parent) {
                return (RedBlackNode<T>) grandParent.lesser;
            }
            return null;
        }

        protected RedBlackNode<T> getUncle() {
            RedBlackNode<T> grandParent = getGrandParent();
            return getUncle(grandParent);
        }

        protected RedBlackNode<T> getSibling() {
            if (parent == null) 
                return null;
            if (parent.lesser == this) {
                return (RedBlackNode<T>) parent.greater;
            } else if (parent.greater == this) {
                return (RedBlackNode<T>) parent.lesser;
            } else {
                throw new UnrelatedParentNodeException(this.toString());
            }
        }

        protected boolean isLeaf() {
            if (lesser != null) 
                return false;
            if (greater != null) 
                return false;
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "id=" + id + " color=" + ((color == RED) ? "RED" : "BLACK") + " isLeaf=" + isLeaf() + " parent="
                    + ((parent != null) ? parent.id : "NULL") + " lesser=" + ((lesser != null) ? lesser.id : "NULL")
                    + " greater=" + ((greater != null) ? greater.id : "NULL");
        }
    }

    protected static class RedBlackTreePrinter {

        public static <T extends Comparable<T>> String getString(RedBlackTree<T> tree) {
            if (tree.root == null)
                return "Tree has no nodes.";
            return getString((RedBlackNode<T>) tree.root, "", true);
        }

        public static <T extends Comparable<T>> String getString(RedBlackNode<T> node) {
            if (node == null)
                return "Sub-tree has no nodes.";
            return getString(node, "", true);
        }

        private static <T extends Comparable<T>> String getString(RedBlackNode<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + getStringTailUtility(isTail) + "(" + getStringColorUtility(node) + ") " + node.id
                           + " [parent=" + getStringParentUtility(node) 
                           + " grand-parent=" + getStringGrandparentUtility(node)
                           + "]\n"
            );
            List<Node<T>> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<Node<T>>(2);
                if (node.lesser != null)
                    children.add(node.lesser);
                if (node.greater != null)
                    children.add(node.greater);
            }
            if (children != null) {
            	int size = children.size();
                for (int i = 0; i < size - 1; i++) {
                    builder.append(getString((RedBlackNode<T>) children.get(i), prefix + getStringTail2Utility(isTail), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString((RedBlackNode<T>) children.get(children.size() - 1), prefix + getStringTail2Utility(isTail), true));
                }
            }

            return builder.toString();
        }
        
        private static <T extends Comparable<T>> String getStringGrandparentUtility(RedBlackNode<T> node) {
        	return (String) ((node.parent != null && node.parent.parent != null) ? node.parent.parent.id : "NULL");
        }
        
        private static <T extends Comparable<T>> String getStringParentUtility(RedBlackNode<T> node) {
        	return (String) (node.parent != null ? node.parent.id : "NULL");
        }
        
        private static <T extends Comparable<T>> String getStringColorUtility(RedBlackNode<T> node) {
        	return (node.color == RED) ? "RED" : "BLACK";
        }
        
        private static String getStringTailUtility(boolean isTail) {
        	return isTail ? "└── " : "├── ";
        }
        
        private static String getStringTail2Utility(boolean isTail) {
        	return isTail ? "    " : "│   ";
        }
    }

    /**
     * The Class JavaCompatibleRedBlackTree.
     *
     * @param <T> the generic type
     */
    public static class JavaCompatibleRedBlackTree<T extends Comparable<T>> extends java.util.AbstractCollection<T> {

        private RedBlackTree<T> tree = null;

        /**
         * Instantiates a new java compatible red black tree.
         */
        public JavaCompatibleRedBlackTree() {
            this.tree = new RedBlackTree<T> ();
        }

        /**
         * Instantiates a new java compatible red black tree.
         *
         * @param tree the tree
         */
        public JavaCompatibleRedBlackTree(RedBlackTree<T> tree) {
            this.tree = tree;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(T value) {
            return tree.add(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return (tree.remove((T)value)!=null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object value) {
            return tree.contains((T)value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return tree.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<T> iterator() {
            return (new RedBlackTreeIterator<T>(this.tree));
        }

        private static class RedBlackTreeIterator<C extends Comparable<C>> implements Iterator<C> {

            private RedBlackTree<C> tree = null;
            private RedBlackTree.Node<C> last = null;
            private Deque<RedBlackTree.Node<C>> toVisit = new ArrayDeque<RedBlackTree.Node<C>>();

            protected RedBlackTreeIterator(RedBlackTree<C> tree) {
                this.tree = tree;
                if (tree.root!=null) {
                    toVisit.add(tree.root);
                }
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
            	int size = toVisit.size();
                while (size > 0) {
                    // Go thru the current nodes
                    RedBlackTree.Node<C> n = toVisit.pop();

                    // Add non-null children
                    if (n.lesser!=null && n.lesser.id!=null) {
                        toVisit.add(n.lesser);
                    }
                    if (n.greater!=null && n.greater.id!=null) {
                        toVisit.add(n.greater);
                    }

                    last = n;
                    return n.id;
                }
                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                tree.removeNode(last);
            }
        }
    }
}

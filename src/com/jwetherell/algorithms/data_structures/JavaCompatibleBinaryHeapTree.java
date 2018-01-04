package com.jwetherell.algorithms.data_structures;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The Class JavaCompatibleBinaryHeapTree.
 *
 * @param <T> the generic type
 */
public class JavaCompatibleBinaryHeapTree<T extends Comparable<T>> extends java.util.AbstractCollection<T> {

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
            if (heap.getRoot()!=null) toVisit.add(heap.getRoot());
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
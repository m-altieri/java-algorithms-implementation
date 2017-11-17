package com.jwetherell.algorithms.data_structures;

import com.jwetherell.algorithms.data_structures.interfaces.IHeap;

// TODO: Auto-generated Javadoc
/**
 * A binary heap is a heap data structure created using a binary tree. It can be
 * seen as a binary tree with two additional constraints: 1) The shape property:
 * the tree is a complete binary tree; that is, all levels of the tree, except
 * possibly the last one (deepest) are fully filled, and, if the last level of
 * the tree is not complete, the nodes of that level are filled from left to
 * right. 2) The heap property: each node is right than or equal to each of its
 * children according to a comparison predicate defined for the data structure.
 * <p>
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @param <T> the generic type
 * @see <a href="https://en.wikipedia.org/wiki/Binary_heap">Binary Heap (Wikipedia)</a>
 * <br>
 */
public interface BinaryHeap<T extends Comparable<T>> extends IHeap<T> {

    /**
     * The Enum HeapType.
     */
    public enum HeapType {
        Tree, Array
    }

    /**
     * The Enum Type.
     */
    public enum Type {
        MIN, MAX
    }

    /**
     * Get the heap in array form.
     * 
     * @return array representing the heap.
     */
    public T[] getHeap();

}

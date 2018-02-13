package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A binary heap using an array to hold the nodes.
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @param <T> the generic type
 */
public class BinaryHeapArray<T extends Comparable<T>> implements BinaryHeap<T> {

	private static final int MINIMUM_SIZE = 1024;

	private Type type = Type.MIN;
	private int size = 0;
	@SuppressWarnings("unchecked")
	private T[] array = (T[]) new Comparable[MINIMUM_SIZE];
	
	int getSize() {
		return size;
	}
	
	T[] getArray() {
		return array;
	}

	/**
	 * Get the parent index of this index, will return Integer.MIN_VALUE if
	 * no parent is possible.
	 * 
	 * @param index
	 *            of the node to find a parent for.
	 * @return index of parent node or Integer.MIN_VALUE if no parent.
	 */
	private static final int getParentIndex(int index) {
		if (index > 0)
			return (int) Math.floor((index - 1) / 2);
		return Integer.MIN_VALUE;
	}

	/**
	 * Get the left child index of this index.
	 * 
	 * @param index
	 *            of the node to find a left child for.
	 * @return index of left child node.
	 */
	private static final int getLeftIndex(int index) {
		return 2 * index + 1;
	}

	/**
	 * Get the right child index of this index.
	 * 
	 * @param index
	 *            of the node to find a right child for.
	 * @return index of right child node.
	 */
	private static final int getRightIndex(int index) {
		return 2 * index + 2;
	}

	/**
	 * Constructor for heap, defaults to a min-heap.
	 */
	public BinaryHeapArray() {
		size = 0;
	}

	/**
	 * Constructor for heap.
	 * 
	 * @param type
	 *            Heap type.
	 */
	public BinaryHeapArray(Type type) {
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
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(T value) {
		if (size >= array.length)
			grow();
		array[size] = value;

		heapUp(size++);

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T remove(T value) {
		if (array.length == 0) return null;
		for (int i = 0; i < size; i++) {
			T node = array[i];
			if (node.equals(value)) return remove(i);
		}
		return null;
	}

	T remove(int index) {
		if (index<0 || index>=size) return null;

		T t = array[index];
		array[index] = array[--size];
		array[size] = null;

		heapDown(index);

		int shrinkSize = array.length>>1;
		if (shrinkSize >= MINIMUM_SIZE && size < shrinkSize)
			shrink();

		return t;
	}

	protected void heapUp(int idx) {
		int nodeIndex = idx;
		T value = this.array[nodeIndex];
		if (value==null)
			return;

		while (nodeIndex >= 0) {
			int parentIndex = getParentIndex(nodeIndex);
			if (parentIndex < 0)
				return;

			T parent = this.array[parentIndex];

			if ((type == Type.MIN && value.compareTo(parent) < 0)
					|| (type == Type.MAX && value.compareTo(parent) > 0)
					) {
				// Node is greater/lesser than parent, switch node with parent
				this.array[parentIndex] = value;
				this.array[nodeIndex] = parent;
			} else {
				return;
			}
			nodeIndex = parentIndex;
		}
	}

	private boolean heapDownCheck1(T left, T right, T value) {
		return (type == Type.MIN && left != null && right != null && value.compareTo(left) > 0 && value.compareTo(right) > 0)
		|| (type == Type.MAX && left != null && right != null && value.compareTo(left) < 0 && value.compareTo(right) < 0);
	}
	
	private boolean heapDownCheck2(T left, T right, T value) {
		return (type == Type.MIN && right != null && value.compareTo(right) > 0)
				|| (type == Type.MAX && right != null && value.compareTo(right) < 0);
	}
	
	private boolean rightGreaterThanLeft(T left, T right) {
		return ((right != null) && type == Type.MIN && (right.compareTo(left) < 0)) || ((type == Type.MAX && right.compareTo(left) > 0));
	}
	
	private boolean leftGreaterThanRight(T left, T right) {
		return (left!=null) && 
				((type == Type.MIN && left.compareTo(right) < 0) || (type == Type.MAX && left.compareTo(right) > 0));
	}
	
	private T getLeft(int leftIndex) {
		return leftIndex != Integer.MIN_VALUE && leftIndex < this.size ? this.array[leftIndex] : null;
	}
	
	private T getRight(int rightIndex) {
		return rightIndex != Integer.MIN_VALUE && rightIndex < this.size ? this.array[rightIndex] : null;
	}
	
	private boolean leftGreaterThanNode(T left, T value) {
		return (type == Type.MIN && left != null && value.compareTo(left) > 0)
		|| (type == Type.MAX && left != null && value.compareTo(left) < 0);	
	}
	
	private boolean nullCheck(T left, T right, T value) {
		return value == null || (left == null && right == null);
	}
	protected void heapDown(int index) {
		
		T value = this.array[index];

		int leftIndex = getLeftIndex(index);
		int rightIndex = getRightIndex(index);
		T left = getLeft(leftIndex);
		T right = getRight(rightIndex);

		if (nullCheck(left, right, value)) {
			return;
		}

		T nodeToMove = null;
		int nodeToMoveIndex = -1;
		if (heapDownCheck1(left, right, value)) {
			// Both children are greater/lesser than node
			if (rightGreaterThanLeft(left, right)) {
				// Right is greater/lesser than left
				nodeToMove = right;
				nodeToMoveIndex = rightIndex;
			} else if (leftGreaterThanRight(left, right)) {
				// Left is greater/lesser than right
				nodeToMove = left;
				nodeToMoveIndex = leftIndex;
			} else {
				// Both children are equal, use right
				nodeToMove = right;
				nodeToMoveIndex = rightIndex;
			}
		} else if (heapDownCheck2(left, right, value)) {
			// Right is greater/lesser than node
			nodeToMove = right;
			nodeToMoveIndex = rightIndex;
		} else if (leftGreaterThanNode(left, value)) {
			// Left is greater/lesser than node
			nodeToMove = left;
			nodeToMoveIndex = leftIndex;
		}
		// No node to move, stop recursion
		if (nodeToMove == null)
			return;

		// Re-factor heap sub-tree
		this.array[nodeToMoveIndex] = value;
		this.array[index] = nodeToMove;

		heapDown(nodeToMoveIndex);
	}

	// Grow the array by 50%
	private void grow() {
		int growSize = size + (size<<1);
		array = Arrays.copyOf(array, growSize);
	}

	// Shrink the array by 50%
	private void shrink() {
		int shrinkSize = array.length>>1;
		array = Arrays.copyOf(array, shrinkSize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		size = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(T value) {
		if (array.length == 0) return false;
		for (int i = 0; i < size; i++) {
			T t = array[i];
			if (t.equals(value)) return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validate() {
		if (array.length == 0) return true;
		return validateNode(0);
	}

	/**
	 * Validate the node for the heap invariants.
	 * 
	 * @param node
	 *            to validate for.
	 * @return True if node is valid.
	 */
	private boolean validateNode(int index) {
		T value = this.array[index];
		int leftIndex = getLeftIndex(index);
		int rightIndex = getRightIndex(index);

		// We shouldn't ever have a right node without a left in a heap
		if (rightIndex != Integer.MIN_VALUE && leftIndex == Integer.MIN_VALUE) return false;

		if (minValueLeftCheck(leftIndex)) {
			T left = this.array[leftIndex];
			if (typeLeftCheck(value, left)) {
				return validateNode(leftIndex);
			}
			return false;
		}
		if (minValueRightCheck(rightIndex)) {
			T right = this.array[rightIndex];
			if (typeRightCheck(value, right)) {
				return validateNode(rightIndex);
			}
			return false;
		}

		return true;
	}
	
	private boolean typeRightCheck(T value, T right) {
		return (type == Type.MIN && value.compareTo(right) < 0)
				|| (type == Type.MAX && value.compareTo(right) > 0);
	}
	
	private boolean typeLeftCheck(T value, T left) {
		return (type == Type.MIN && value.compareTo(left) < 0) 
				|| (type == Type.MAX && value.compareTo(left) > 0);
	}
	
	private boolean minValueRightCheck(int rightIndex) {
		return rightIndex != Integer.MIN_VALUE && rightIndex < size;
	}
	
	private boolean minValueLeftCheck(int leftIndex) {
		return leftIndex != Integer.MIN_VALUE && leftIndex < size;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T[] getHeap() {
		T[] nodes = (T[]) new Comparable[size];
		if (array.length == 0) return nodes;

		for (int i = 0; i < size; i++) {
			T node = this.array[i];
			nodes[i] = node;
		}
		return nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getHeadValue() {
		if (array.length == 0) return null;
		return array[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T removeHead() {
		return remove(getHeadValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.Collection<T> toCollection() {
		return (new JavaCompatibleBinaryHeapArray<T>(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return HeapPrinter.getString(this);
	}

	protected static class HeapPrinter {

		public static <T extends Comparable<T>> String getString(BinaryHeapArray<T> tree) {
			if (tree.array.length == 0)
				return "Tree has no nodes.";

			T root = tree.array[0];
			if (root == null)
				return "Tree has no nodes.";
			return getString(tree, 0, "", true);
		}

		private static <T extends Comparable<T>> String getString(BinaryHeapArray<T> tree, int index, String prefix, boolean isTail) {
			StringBuilder builder = new StringBuilder();

			T value = tree.array[index];
			builder.append(prefix + getStringTailUtility(isTail) + value + System.getProperty("line.separator"));
			List<Integer> children = null;
			
			children = initialize(tree, children, index);
			
			if (children != null) {
				int size = children.size();
				for (int i = 0; i < size - 1; i++) {
					builder.append(getString(tree, children.get(i), prefix + getStringTailUtility2(isTail), false));
				}
				if (children.size() >= 1) {
					builder.append(getString(tree, children.get(children.size() - 1), prefix
							+ getStringTailUtility2(isTail), true));
				}
			}

			return builder.toString();
		}
		
		private static <T extends Comparable<T>> List<Integer> initialize(BinaryHeapArray<T> tree, List<Integer> children, int index) {

			int leftIndex = getLeftIndex(index);
			int rightIndex = getRightIndex(index);
			
			if (leftIndex != Integer.MIN_VALUE || rightIndex != Integer.MIN_VALUE) {
				children = new ArrayList<Integer>(2);
				if (leftIndex != Integer.MIN_VALUE && leftIndex < tree.size) {
					children.add(leftIndex);
				}
				if (rightIndex != Integer.MIN_VALUE && rightIndex < tree.size) {
					children.add(rightIndex);
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

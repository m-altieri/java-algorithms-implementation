package com.jwetherell.algorithms.data_structures;

/**
 * The Class JavaCompatibleBinaryHeapArray.
 *
 * @param <T> the generic type
 */
public class JavaCompatibleBinaryHeapArray<T extends Comparable<T>> extends java.util.AbstractCollection<T> {

	private BinaryHeapArray<T> heap = null;

	/**
	 * Instantiates a new java compatible binary heap array.
	 */
	public JavaCompatibleBinaryHeapArray() {
		heap = new BinaryHeapArray<T>();
	}

	/**
	 * Instantiates a new java compatible binary heap array.
	 *
	 * @param heap the heap
	 */
	public JavaCompatibleBinaryHeapArray(BinaryHeapArray<T> heap) {
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
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object value) {
		return (heap.remove((T)value)!=null);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
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
		return (new BinaryHeapArrayIterator<T>(this.heap));
	}

	private static class BinaryHeapArrayIterator<T extends Comparable<T>> implements java.util.Iterator<T> {

		private BinaryHeapArray<T> heap = null;
		private int last = -1;
		private int index = -1;

		protected BinaryHeapArrayIterator(BinaryHeapArray<T> heap) {
			this.heap = heap;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			if (index+1>=heap.getSize()) return false; 
			return (heap.getArray()[index+1]!=null);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public T next() {
			if (++index>=heap.getSize()) return null;
			last = index;
			return heap.getArray()[index];
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			heap.remove(last);
		}
	}
}
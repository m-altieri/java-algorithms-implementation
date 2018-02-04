package com.jwetherell.algorithms.data_structures;

import java.util.Arrays;

import com.jwetherell.algorithms.data_structures.interfaces.IList;

/**
 * A dynamic array, growable array, resizable array, dynamic table, or array
 * list is a random access, variable-size list data structure that allows
 * elements to be added or removed.
 * <p>
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @param <T> the generic type
 * @see <a href="https://en.wikipedia.org/wiki/Dynamic_array">Dynamic Array (Wikipedia)</a>
 * <br>
 */
public class ArrayList<T> implements IList<T> {

	private static final int MINIMUM_SIZE = 1024;

	private int size = 0;
	@SuppressWarnings("unchecked")
	private T[] array = (T[]) new Object[MINIMUM_SIZE];
	
	int getSize() {
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(T value) {
		return add(size,value);
	}

	/**
	 * Add value to list at index.
	 *
	 * @param index to add value.
	 * @param value to add to list.
	 * @return true, if successful
	 */
	public boolean add(int index, T value) {
		if (size >= array.length)
			grow();
		if (index==size) {
			array[size] = value;
		} else {
			// Shift the array down one spot
			System.arraycopy(array, index, array, index+1, size - index);
			array[index] = value;
		}
		size++;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(T value) {
		for (int i = 0; i < size; i++) {
			T obj = array[i];
			if (obj.equals(value)) {
				if (remove(i)!=null) return true;
				return false;
			}
		}
		return false;
	}

	/**
	 * Remove value at index from list.
	 * 
	 * @param index of value to remove.
	 * @return value at index.
	 */
	public T remove(int index) {
		if (index<0 || index>=size) return null;

		T t = array[index];
		if (index != --size) {
			// Shift the array down one spot
			System.arraycopy(array, index + 1, array, index, size - index);
		}
		array[size] = null;

		int shrinkSize = array.length>>1;
		if (shrinkSize >= MINIMUM_SIZE && size < shrinkSize)
			shrink();

		return t;
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
	 * Set value at index.
	 * 
	 * @param index of value to set.
	 * @param value to set.
	 * @return value previously at index.
	 */
	public T set(int index, T value) {
		if (index<0 || index>=size) return null;
		T t = array[index];
		array[index] = value;
		return t;
	}

	/**
	 * Get value at index.
	 * 
	 * @param index of value to get.
	 * @return value at index.
	 */
	public T get(int index) {
		if (index<0 || index>=size) return null;
		return array[index];
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
		for (int i = 0; i < size; i++) {
			T obj = array[i];
			if (obj.equals(value)) return true;
		}
		return false;
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
	public boolean validate() {
		int localSize = 0;
		for (int i=0; i<array.length; i++) {
			T t = array[i];
			if (i<size) {
				if (t==null) return false;
				localSize++;
			} else {
				if (t!=null) return false;
			}
		}
		return (localSize==size);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<T> toList() {
		return (new JavaCompatibleArrayList<T>(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.Collection<T> toCollection() {
		return (new JavaCompatibleArrayList<T>(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			builder.append(array[i]).append(", ");
		}
		return builder.toString();
	}

	/**
	 * Instantiates a new array list.
	 */
	public ArrayList() {
		super();
	}



}

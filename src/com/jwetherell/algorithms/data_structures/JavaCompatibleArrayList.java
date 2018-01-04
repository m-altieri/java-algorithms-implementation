package com.jwetherell.algorithms.data_structures;

/**
 * The Class JavaCompatibleArrayList.
 *
 * @param <T> the generic type
 */

public class JavaCompatibleArrayList<T> extends java.util.AbstractList<T> implements java.util.RandomAccess {

	private com.jwetherell.algorithms.data_structures.ArrayList<T> list = null;

	/**
	 * Instantiates a new java compatible array list.
	 *
	 * @param list the list
	 */
	public JavaCompatibleArrayList(com.jwetherell.algorithms.data_structures.ArrayList<T> list) {
		this.list = list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(T value) {
		return list.add(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object value) {
		return list.remove((T)value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object value) {
		return list.contains((T)value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return list.getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(int index, T value) {
		list.add(index, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T remove(int index) {
		return list.remove(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get(int index) {
		T t = list.get(index);
		if (t!=null) 
			return t;
		throw new IndexOutOfBoundsException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T set(int index, T value) {
		return list.set(index, value);
	}
}
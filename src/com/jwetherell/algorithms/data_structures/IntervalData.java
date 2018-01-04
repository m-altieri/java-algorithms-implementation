package com.jwetherell.algorithms.data_structures;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Data structure representing an interval.
 *
 * @param <O> the generic type
 */
public final class IntervalData<O extends Object> extends Data {

	private Set<O> set = new TreeSet<O>(); // Sorted

	/**
	 * Interval data using O as it's unique identifier.
	 *
	 * @param index the index
	 * @param object            Object which defines the interval data
	 */
	public IntervalData(long index, O object) {
		super(index);

		this.set.add(object);
	}

	/**
	 * Interval data using O as it's unique identifier.
	 *
	 * @param start the start
	 * @param end the end
	 * @param object            Object which defines the interval data
	 */
	public IntervalData(long start, long end, O object) {
		super(start, end);

		this.set.add(object);
	}

	/**
	 * Interval data list which should all be unique.
	 *
	 * @param start the start
	 * @param end the end
	 * @param set the set
	 */
	public IntervalData(long start, long end, Set<O> set) {
		super(start, end);

		this.set = set;
	}

	/**
	 * Get the data set in this interval.
	 *
	 * @return Unmodifiable collection of data objects
	 */
	public Collection<O> getData() {
		return Collections.unmodifiableCollection(this.set);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		super.clear();

		this.set.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Data copy() {
		final Set<O> listCopy = new TreeSet<O>();
		listCopy.addAll(set);
		return new IntervalData<O>(start, end, listCopy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Data query(long startOfQuery, long endOfQuery) {
		if (endOfQuery < this.start || startOfQuery > this.end)
			return null;

		return copy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Data combined(Data data) {
		IntervalData<O> q = null;
		if (data instanceof IntervalData) {
			q = (IntervalData<O>) data;
			this.combined(q);
		}
		return this;
	}

	/**
	 * Combined for interval specific data.
	 * 
	 * @param data
	 *            resulted from combination.
	 */
	private void combined(IntervalData<O> data) {
		if (data.start < this.start)
			this.start = data.start;
		if (data.end > this.end)
			this.end = data.end;
		this.set.addAll(data.set);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return 31 * (int)(this.start + this.end + this.set.size());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IntervalData))
			return false;

		final IntervalData<O> data = (IntervalData<O>) obj;
		if (this.start == data.start && this.end == data.end) {
			if (this.set.size() != data.set.size())
				return false;
			for (O o : set) {
				if (!data.set.contains(o))
					return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" ");
		builder.append("set=").append(set);
		return builder.toString();
	}
}
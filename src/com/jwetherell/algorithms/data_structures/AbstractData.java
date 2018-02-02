package com.jwetherell.algorithms.data_structures;

/**
 * The Class Data.
 */
public abstract class AbstractData<T> extends Data {

	protected long start = Long.MIN_VALUE;
	protected long end = Long.MAX_VALUE;
	
	/**
	 * 
	 */
	public AbstractData() {
		super(Long.MIN_VALUE, Long.MAX_VALUE);
	}

	/**
	 * Constructor for data at index.
	 *
	 * @param index            of data.
	 */
	public AbstractData(long index) {
		super(index);
	}

	/**
	 * Constructor for data at range (inclusive).
	 *
	 * @param start            start of range for data.
	 * @param end            end of range for data.
	 */
	public AbstractData(long start, long end) {
		super(start, end);
	}

	/**
	 * Clear the indices.
	 */
	public void clear() {
		start = Long.MIN_VALUE;
		end = Long.MAX_VALUE;
	}

	/**
	 * Combined this data with the Data parameter.
	 *
	 * @param data            Data to combined
	 * @return Data which represents the combination.
	 */
	public abstract AbstractData<T> combined(AbstractData<T> data);

	/**
	 * Deep copy of data.
	 * 
	 * @return deep copy.
	 */
	public abstract AbstractData<T> copy();

	/**
	 * Query inside this data object.
	 * 
	 * @param startOfRange
	 *            start of range (inclusive)
	 * @param endOfRange
	 *            end of range (inclusive)
	 * @return Data queried for or NULL if it doesn't match the query.
	 */
	public abstract AbstractData<T> query(long startOfRange, long endOfRange);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(start).append("->").append(end);
		return builder.toString();
	}

	/**
	 * @param d
	 * @return
	 */
	public int compareTo(AbstractData<T> d) {
		if (this.end < d.end)
			return -1;
		if (d.end < this.end)
			return 1;
		return 0;
	}
}

package com.jwetherell.algorithms.data_structures;

/**
 * The Class Data.
 */
public abstract class Data implements Comparable<Data> {

	protected long start = Long.MIN_VALUE;
	protected long end = Long.MAX_VALUE;

	/**
	 * Constructor for data at index.
	 *
	 * @param index            of data.
	 */
	public Data(long index) {
		this.start = index;
		this.end = index;
	}

	/**
	 * Constructor for data at range (inclusive).
	 *
	 * @param start            start of range for data.
	 * @param end            end of range for data.
	 */
	public Data(long start, long end) {
		this.start = start;
		this.end = end;
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
	public abstract Data combined(Data data);

	/**
	 * Deep copy of data.
	 * 
	 * @return deep copy.
	 */
	public abstract Data copy();

	/**
	 * Query inside this data object.
	 * 
	 * @param startOfRange
	 *            start of range (inclusive)
	 * @param endOfRange
	 *            end of range (inclusive)
	 * @return Data queried for or NULL if it doesn't match the query.
	 */
	public abstract Data query(long startOfRange, long endOfRange);

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
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Data d) {
		if (this.end < d.end)
			return -1;
		if (d.end < this.end)
			return 1;
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}
		if (compareTo((Data) o) != 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}

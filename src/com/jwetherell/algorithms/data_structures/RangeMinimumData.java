package com.jwetherell.algorithms.data_structures;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Data structure representing minimum in the range.
 *
 * @param <N> the number type
 */
public final class RangeMinimumData<N extends Number> extends Data {

	/**
	 * The minimum.
	 */
	public N minimum = null;

	/**
	 * Instantiates a new range minimum data.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public RangeMinimumData(long start, long end) {
		super(start, end);
	}

	/**
	 * Instantiates a new range minimum data.
	 *
	 * @param index the index
	 * @param number the number
	 */
	public RangeMinimumData(long index, N number) {
		super(index);

		this.minimum = number;
	}

	/**
	 * Instantiates a new range minimum data.
	 *
	 * @param start the start
	 * @param end the end
	 * @param number the number
	 */
	public RangeMinimumData(long start, long end, N number) {
		super(start, end);

		this.minimum = number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		super.clear();

		minimum = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Data copy() {
		return new RangeMinimumData<N>(start, end, minimum);
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
		RangeMinimumData<N> q = null;
		if (data instanceof RangeMinimumData) {
			q = (RangeMinimumData<N>) data;
			this.combined(q);
		}
		return this;
	}

	private void combined(RangeMinimumData<N> data) {
		if (this.minimum == null && data.minimum == null)
			return;
		else if (this.minimum != null && data.minimum == null)
			return;
		else if (this.minimum == null && data.minimum != null)
			this.minimum = data.minimum;
		else {
			/* TODO: This is ugly */
			if (this.minimum instanceof BigDecimal || data.minimum instanceof BigDecimal) {
				if (((BigDecimal)data.minimum).compareTo(((BigDecimal)this.minimum))==-1)
					this.minimum = data.minimum;
			} else if (this.minimum instanceof BigInteger || data.minimum instanceof BigInteger) {
				if (((BigInteger)data.minimum).compareTo(((BigInteger)this.minimum))==-1)
					this.minimum = data.minimum;
			} else if (this.minimum instanceof Long || data.minimum instanceof Long) {
				if (((Long)data.minimum).compareTo(((Long)this.minimum))==-1)
					this.minimum = data.minimum;
			} else if (this.minimum instanceof Double || data.minimum instanceof Double) {
				if (((Double)data.minimum).compareTo(((Double)this.minimum))==-1)
					this.minimum = data.minimum;
			} else if (this.minimum instanceof Float || data.minimum instanceof Float) {
				if (((Float)data.minimum).compareTo(((Float)this.minimum))==-1)
					this.minimum = data.minimum;
			} else {
				// Integer
				if (((Integer)data.minimum).compareTo(((Integer)this.minimum))==-1)
					this.minimum = data.minimum;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return 31 * (int)(this.start + this.end + this.minimum.hashCode());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass())
			return false;

		final RangeMinimumData<N> data = (RangeMinimumData<N>) obj;
		if (this.start == data.start && this.end == data.end && this.minimum.equals(data.minimum))
			return true;

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" ");
		builder.append("minimum=").append(minimum);
		return builder.toString();
	}
}
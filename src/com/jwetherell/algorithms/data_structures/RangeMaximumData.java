package com.jwetherell.algorithms.data_structures;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Data structure representing maximum in the range.
 *
 * @param <N> the number type
 */
public final class RangeMaximumData<N extends Number> extends Data {

	/**
	 * The maximum.
	 */
	public N maximum = null;

	/**
	 * Instantiates a new range maximum data.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public RangeMaximumData(long start, long end) {
		super(start, end);
	}

	/**
	 * Instantiates a new range maximum data.
	 *
	 * @param index the index
	 * @param number the number
	 */
	public RangeMaximumData(long index, N number) {
		super(index);

		this.maximum = number;
	}

	/**
	 * Instantiates a new range maximum data.
	 *
	 * @param start the start
	 * @param end the end
	 * @param number the number
	 */
	public RangeMaximumData(long start, long end, N number) {
		super(start, end);

		this.maximum = number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		super.clear();

		maximum = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Data copy() {
		return new RangeMaximumData<N>(start, end, maximum);
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
		RangeMaximumData<N> q = null;
		if (data instanceof RangeMaximumData) {
			q = (RangeMaximumData<N>) data;
			this.combined(q);
		}
		return this;
	}

	private void combined(RangeMaximumData<N> data) {
		if (this.maximum == null && data.maximum == null)
			return;
		else if (this.maximum != null && data.maximum == null)
			return;
		else if (this.maximum == null && data.maximum != null)
			this.maximum = data.maximum;
		else {
			/* TODO: This is ugly */
			if (this.maximum instanceof BigDecimal || data.maximum instanceof BigDecimal) {
				if (((BigDecimal)data.maximum).compareTo(((BigDecimal)this.maximum))==1)
					this.maximum = data.maximum;
			} else if (this.maximum instanceof BigInteger || data.maximum instanceof BigInteger) {
				if (((BigInteger)data.maximum).compareTo(((BigInteger)this.maximum))==1)
					this.maximum = data.maximum;
			} else if (this.maximum instanceof Long || data.maximum instanceof Long) {
				if (((Long)data.maximum).compareTo(((Long)this.maximum))==1)
					this.maximum = data.maximum;
			} else if (this.maximum instanceof Double || data.maximum instanceof Double) {
				if (((Double)data.maximum).compareTo(((Double)this.maximum))==1)
					this.maximum = data.maximum;
			} else if (this.maximum instanceof Float || data.maximum instanceof Float) {
				if (((Float)data.maximum).compareTo(((Float)this.maximum))==1)
					this.maximum = data.maximum;
			} else {
				// Integer
				if (((Integer)data.maximum).compareTo(((Integer)this.maximum))==1)
					this.maximum = data.maximum;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return 31 * (int)(this.start + this.end + this.maximum.hashCode());
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

		final RangeMaximumData<N> data = (RangeMaximumData<N>) obj;
		if (this.start == data.start && this.end == data.end && this.maximum.equals(data.maximum))
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
		builder.append("maximum=").append(maximum);
		return builder.toString();
	}
}
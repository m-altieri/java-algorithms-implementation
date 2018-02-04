package com.jwetherell.algorithms.data_structures;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Data structure representing sum of the range.
 *
 * @param <N> the number type
 */
public final class RangeSumData<N extends Number> extends Data {

	/**
	 * The sum.
	 */
	public N sum = null;

	/**
	 * Instantiates a new range sum data.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public RangeSumData(long start, long end) {
		super(start, end);
	}

	/**
	 * Instantiates a new range sum data.
	 *
	 * @param index the index
	 * @param number the number
	 */
	public RangeSumData(long index, N number) {
		super(index);

		this.sum = number;
	}

	/**
	 * Instantiates a new range sum data.
	 *
	 * @param start the start
	 * @param end the end
	 * @param number the number
	 */
	public RangeSumData(long start, long end, N number) {
		super(start, end);

		this.sum = number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		super.clear();

		sum = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Data copy() {
		return new RangeSumData<N>(start, end, sum);
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
		RangeSumData<N> q = null;
		if (data instanceof RangeSumData) {
			q = (RangeSumData<N>) data;
			this.combined(q);
		}
		return this;
	}

	private void combined(RangeSumData<N> data) {
		if (this.sum == null && data.sum == null)
			return;
		else if (this.sum != null && data.sum == null)
			return;
		else if (this.sum == null && data.sum != null)
			this.sum = data.sum;
		else {
			/* TODO: This is ugly and how to handle number overflow? */
			if (this.sum instanceof BigDecimal || data.sum instanceof BigDecimal) {
				BigDecimal result = ((BigDecimal)this.sum).add((BigDecimal)data.sum);
				this.sum = (N)result;
			} else if (this.sum instanceof BigInteger || data.sum instanceof BigInteger) {
				BigInteger result = ((BigInteger)this.sum).add((BigInteger)data.sum);
				this.sum = (N)result;
			} else if (this.sum instanceof Long || data.sum instanceof Long) {
				Long result = (this.sum.longValue() + data.sum.longValue());
				this.sum = (N)result;
			} else if (this.sum instanceof Double || data.sum instanceof Double) {
				Double result = (this.sum.doubleValue() + data.sum.doubleValue());
				this.sum = (N)result;
			} else if (this.sum instanceof Float || data.sum instanceof Float) {
				Float result = (this.sum.floatValue() + data.sum.floatValue());
				this.sum = (N)result;
			} else {
				// Integer
				Integer result = (this.sum.intValue() + data.sum.intValue());
				this.sum = (N)result;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return 31 * (int)(this.start + this.end + this.sum.hashCode());
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

		final RangeSumData<N> data = (RangeSumData<N>) obj;
		if (this.start == data.start && this.end == data.end && this.sum.equals(data.sum))
			return true;

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append(" ");
		builder.append("sum=").append(sum);
		return builder.toString();
	}
}
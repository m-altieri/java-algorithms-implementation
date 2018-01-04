package com.jwetherell.algorithms.data_structures;

/**
 * Data structure representing points in the x,y space and their
 * location in the quadrants.
 */
public final class QuadrantData extends Data {

	/**
	 * The quad 0.
	 */
	public long quad0 = 0;

	/**
	 * The quad 1.
	 */
	public long quad1 = 0;

	/**
	 * The quad 2.
	 */
	public long quad2 = 0;

	/**
	 * The quad 3.
	 */
	public long quad3 = 0;

	/**
	 * Instantiates a new quadrant data.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public QuadrantData(long start, long end) {
		super(start, end);
	}

	/**
	 * Instantiates a new quadrant data.
	 *
	 * @param index the index
	 * @param quad1 the quad 1
	 * @param quad2 the quad 2
	 * @param quad3 the quad 3
	 * @param quad4 the quad 4
	 */
	public QuadrantData(long index, long quad1, long quad2, long quad3, long quad4) {
		super(index);

		this.quad0 = quad1;
		this.quad1 = quad2;
		this.quad2 = quad3;
		this.quad3 = quad4;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		super.clear();

		quad0 = 0;
		quad1 = 0;
		quad2 = 0;
		quad3 = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QuadrantData copy() {
		final QuadrantData copy = new QuadrantData(start, end);
		copy.quad0 = this.quad0;
		copy.quad1 = this.quad1;
		copy.quad2 = this.quad2;
		copy.quad3 = this.quad3;
		return copy;
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
		QuadrantData q = null;
		if (data instanceof QuadrantData) {
			q = (QuadrantData) data;
			this.combined(q);
		}
		return this;
	}

	private void combined(QuadrantData data) {
		this.quad0 += data.quad0;
		this.quad1 += data.quad1;
		this.quad2 += data.quad2;
		this.quad3 += data.quad3;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return 31 * (int)(this.start + this.end + this.quad0 + this.quad1 + this.quad2 + this.quad3);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof QuadrantData))
			return false;
		QuadrantData data = (QuadrantData) obj;
		if (this.start == data.start && this.end == data.end && this.quad0 == data.quad0
				&& this.quad1 == data.quad1 && this.quad2 == data.quad2 && this.quad3 == data.quad3) 
		{
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
		builder.append(quad0).append(",");
		builder.append(quad1).append(",");
		builder.append(quad2).append(",");
		builder.append(quad3);
		return builder.toString();
	}
}
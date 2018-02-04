package com.jwetherell.algorithms.data_structures;

/**
 * The Class XYPoint.
 */
public class XYPoint implements Comparable<Object> {

    protected double x = Float.MIN_VALUE;
    protected double y = Float.MIN_VALUE;

    /**
     * Instantiates a new XY point.
     */
    public XYPoint() { }

    /**
     * Instantiates a new XY point.
     *
     * @param x the x
     * @param y the y
     */
    public XYPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the.
     *
     * @param theX the the X
     * @param theY the the Y
     */
    public void set(double theX, double theY) {
        this.x = theX;
        this.y = theY;
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public double getX() {
        return x;
    }
    
    /**
     * Gets the y.
     *
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 13 + (int)x;
        hash = hash * 19 + (int)y;
        return hash; 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof XYPoint))
            return false;

        XYPoint xyzPoint = (XYPoint) obj;
        return compareTo(xyzPoint) == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Object o) {
        if ((o instanceof XYPoint)==false)
            throw new RuntimeException("Cannot compare object.");

        XYPoint p = (XYPoint) o;
        int xComp = QuadTree.X_COMPARATOR.compare(this, p);
        if (xComp != 0) 
            return xComp;
        return QuadTree.Y_COMPARATOR.compare(this, p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(x).append(", ");
        builder.append(y);
        builder.append(")");
        return builder.toString();
    }
}

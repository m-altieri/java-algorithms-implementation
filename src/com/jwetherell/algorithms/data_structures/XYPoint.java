package com.jwetherell.algorithms.data_structures;

/**
 * 
 */
public class XYPoint implements Comparable<Object> {

    protected double x = Float.MIN_VALUE;
    protected double y = Float.MIN_VALUE;

    /**
     * 
     */
    public XYPoint() { }

    /**
     * 
     *
     * @param x 
     * @param y 
     */
    public XYPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 
     *
     * @param x 
     * @param y 
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 
     *
     * @return 
     */
    public double getX() {
        return x;
    }
    
    /**
     * 
     *
     * @return 
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

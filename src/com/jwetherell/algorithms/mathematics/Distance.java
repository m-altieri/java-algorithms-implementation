package com.jwetherell.algorithms.mathematics;

/**
 * The Class Distance.
 */
public class Distance {

    private Distance() { }

    /**
     * Chebyshev distance.
     *
     * @param point1 the point 1
     * @param point2 the point 2
     * @return the long
     */
    /*
     * Chess distance
     */
    public static final long chebyshevDistance(long[] point1, long[] point2) {
        long x1 = point1[0];
        long y1 = point1[1];
        long x2 = point2[0];
        long y2 = point2[1];
        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    /**
     * Squared distance.
     *
     * @param x1 the x 1
     * @param y1 the y 1
     * @param x2 the x 2
     * @param y2 the y 2
     * @return the double
     */
    public static final double squaredDistance(double x1, double y1, double x2, double y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        double sqr = (x * x) + (y * y);
        return sqr;
    }

    /**
     * Euclidean distance.
     *
     * @param x1 the x 1
     * @param y1 the y 1
     * @param x2 the x 2
     * @param y2 the y 2
     * @return the double
     */
    public static final double euclideanDistance(double x1, double y1, double x2, double y2) {
        double x = Math.pow((x1 - x2), 2);
        double y = Math.pow((y1 - y2), 2);
        double sqrt = Math.sqrt(x + y);
        return sqrt;
    }
}

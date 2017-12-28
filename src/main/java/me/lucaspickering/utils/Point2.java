package me.lucaspickering.utils;

import java.util.Objects;

/**
 * A class representing an immutable 2-dimensional integer point. Used mostly for points on-screen.
 */
public class Point2 implements Cloneable {

    /**
     * {@link Point2}s are immutable, so this globally-available zero point can save some time &
     * space. This can be used as a placeholder {@link Point2} without having to instantiate a new
     * object.
     */
    public static final Point2 ZERO = new Point2(0.0, 0.0);

    private final double x, y;

    /**
     * Constructs a new {@code Point2} with the given x and y.
     *
     * @param x the x-value
     * @param y the y-value
     */
    public Point2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    /**
     * Create a new point whose coordinates are the sum of this point's and the given point's.
     *
     * @param p the point to be added with this one
     * @return the new {@code Point2}
     */
    public Point2 plus(Point2 p) {
        return plus(p.x(), p.y());
    }

    /**
     * Create a new point whose coordinates are the sum of this point's and the given ones.
     *
     * @param x the x to be added
     * @param y the y to be added
     * @return the new {@code Point2}
     */
    public Point2 plus(double x, double y) {
        return new Point2(this.x + x, this.y + y);
    }

    /**
     * Create a new point whose coordinates are the difference between this point's and the given
     * point's.
     *
     * @param p the point to be subtracted from this one
     * @return the new {@code Point2}
     */
    public Point2 minus(Point2 p) {
        return minus(p.x(), p.y());
    }


    /**
     * Create a new point whose coordinates are the different between this point's and the given
     * coordinates.
     *
     * @param x the x to be subtracted
     * @param y the y to be subtracted
     * @return the new {@code Point2}
     */
    public Point2 minus(double x, double y) {
        return new Point2(this.x - x, this.y - y);
    }

    /**
     * Create a new point whose coordinates are the product of this point's coordinates and the
     * given scaling factor.
     *
     * @param scale the factor to multiple this point's coodinates by
     * @return the new {@code Point2}
     */
    public Point2 scale(double scale) {
        return new Point2(x * scale, y * scale);
    }

    /**
     * Gets the Euclidean distance between this point and another point.
     *
     * @param p the other point (non-null)
     * @return the Euclidean distance between the two points
     * @throws NullPointerException if {@code p == null}
     */
    public double distanceTo(Point2 p) {
        Objects.requireNonNull(p);
        final double xDiff = x - p.x;
        final double yDiff = y - p.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Point2)) {
            return false;
        }

        final Point2 point = (Point2) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }
}

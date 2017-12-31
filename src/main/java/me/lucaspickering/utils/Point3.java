package me.lucaspickering.utils;

import java.util.Objects;

/**
 * A class representing an immutable 3-dimensional integer point.
 */
public class Point3 implements Cloneable {

    /**
     * {@link Point3}s are immutable, so this globally-available zero point can save some time &
     * space. This can be used as a placeholder {@link Point3} without having to instantiate a new
     * object.
     */
    public static final Point3 ZERO = new Point3(0.0, 0.0, 0.0);

    private final double x, y, z;

    /**
     * Construct a new {@code Point3} with the given x, y, and z.
     *
     * @param x the x value
     * @param y the y value
     * @param z the z value
     */
    public Point3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Construct a new {@code Point3} with the x and y value from the given {@link Point2}, and the
     * given z value.
     * @param p the x and y values
     * @param z the z value
     */
    public Point3(Point2 p, double z) {
        this(p.x(), p.y(), z);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    /**
     * Create a new point whose coordinates are the sum of this point's and the given point's.
     *
     * @param p the point to be added with this one
     * @return the new {@code Point3}
     */
    public Point3 plus(Point3 p) {
        return plus(p.x(), p.y(), p.z());
    }

    /**
     * Create a new point whose coordinates are the sum of this point's and the given ones.
     *
     * @param x the x to be added
     * @param y the y to be added
     * @param z the y to be added
     * @return the new {@code Point3}
     */
    public Point3 plus(double x, double y, double z) {
        return new Point3(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Create a new point whose coordinates are the difference between this point's and the given
     * point's.
     *
     * @param p the point to be subtracted from this one
     * @return the new {@code Point3}
     */
    public Point3 minus(Point3 p) {
        return minus(p.x(), p.y(), p.z());
    }

    /**
     * Create a new point whose coordinates are the different between this point's and the given
     * coordinates.
     *
     * @param x the x to be subtracted
     * @param y the y to be subtracted
     * @param z the z to be subtracted
     * @return the new {@code Point3}
     */
    public Point3 minus(double x, double y, double z) {
        return new Point3(this.x - x, this.y - y, this.z - z);
    }

    /**
     * Create a new point whose coordinates are the product of this point's coordinates and the
     * given scaling factor.
     *
     * @param scale the factor to multiple this point's coodinates by
     * @return the new {@code Point3}
     */
    public Point3 scale(double scale) {
        return new Point3(x * scale, y * scale, z * scale);
    }

    /**
     * Gets the Euclidean distance between this point and another point.
     *
     * @param p the other point (non-null)
     * @return the Euclidean distance between the two points
     * @throws NullPointerException if {@code p == null}
     */
    public double distanceTo(Point3 p) {
        Objects.requireNonNull(p);
        final double xDiff = x - p.x();
        final double yDiff = y - p.y();
        final double zDiff = z - p.z();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Point3)) {
            return false;
        }

        final Point3 point = (Point3) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("(%f, %f, %f)", x, y, z);
    }
}

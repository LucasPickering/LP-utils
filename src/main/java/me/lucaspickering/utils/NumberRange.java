package me.lucaspickering.utils;


import org.jetbrains.annotations.NotNull;

import java.util.Objects;

abstract class NumberRange<T extends Number & Comparable<T>> implements Range<T> {

    /**
     * Represents one bound in a range, made up of a value and type.
     */
    private class Bound {

        private final T value;
        private final BoundType type;

        private Bound(@NotNull T value, @NotNull BoundType type) {
            Objects.requireNonNull(value);
            Objects.requireNonNull(type);

            this.value = value;
            this.type = type;
        }

        private boolean includesValue(@NotNull T n) {
            return type == BoundType.INCLUSIVE && value.equals(n);
        }

        private int compare(@NotNull T n) {
            return value.compareTo(n);
        }
    }

    private final Bound lowerBound;
    private final Bound upperBound;

    /**
     * Constructs a new {@code NumberRange} with the given bound values, where both bounds are
     * closed.
     *
     * @param lowerBound the value of the lower bound
     * @param upperBound the value of the upper bound
     */
    public NumberRange(@NotNull T lowerBound, @NotNull T upperBound) {
        this(lowerBound, BoundType.INCLUSIVE, upperBound, BoundType.INCLUSIVE);
    }

    /**
     * Constructs a new {@code NumberRange} with the given bound values and types. The lower
     * bound must be less than or equal to the upper bound
     *
     * @param lowerBound     the value of the lower bound
     * @param lowerBoundType the type of the lower bound
     * @param upperBound     the value of the upper bound
     * @param upperBoundType the type of the upper bound
     */
    public NumberRange(@NotNull T lowerBound, @NotNull BoundType lowerBoundType,
                       @NotNull T upperBound, @NotNull BoundType upperBoundType) {
        // Check if lower > upper
        if (lowerBound.compareTo(upperBound) > 0) {
            throw new IllegalArgumentException(String.format(
                "Lower bound cannot be greater than upper bound. Lower [%s]; Upper [%s]",
                lowerBound, upperBound));
        }
        this.lowerBound = new Bound(lowerBound, lowerBoundType);
        this.upperBound = new Bound(upperBound, upperBoundType);
    }

    @Override
    @NotNull
    public T lower() {
        return lowerBound.value;
    }

    @Override
    @NotNull
    public BoundType lowerType() {
        return lowerBound.type;
    }

    @Override
    @NotNull
    public T upper() {
        return upperBound.value;
    }

    @Override
    @NotNull
    public BoundType upperType() {
        return upperBound.type;
    }

    @Override
    public boolean contains(@NotNull T n) {
        // Is the number equal to the lower bound, or between the bounds,
        // or equal to the upper bound?
        return lowerBound.includesValue(n) ||
               (lowerBound.compare(n) < 0 && upperBound.compare(n) > 0) ||
               upperBound.includesValue(n);
    }

    @Override
    @NotNull
    public T coerce(@NotNull T n) {
        // If the number is less than the lower bound, return the lower bound
        if (lowerBound.compare(n) > 0) {
            return lowerBound.value;
        }

        // If it's greater than the upper bound, return the upper bound
        if (upperBound.compare(n) < 0) {
            return upperBound.value;
        }

        // Value is in the range, just return it
        return n;
    }
}

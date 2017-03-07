package me.lucaspickering.utils;

import com.sun.istack.internal.NotNull;

import java.util.Comparator;
import java.util.Objects;

public class NumberRange implements Range {

    /**
     * Represents one bound in a range, made up of a value and type.
     */
    private class Bound {

        private final Number value;
        private final BoundType type;

        private Bound(@NotNull Number value, @NotNull BoundType type) {
            Objects.requireNonNull(value);
            Objects.requireNonNull(type);

            this.value = value;
            this.type = type;
        }

        private boolean includesValue(@NotNull Number n) {
            return type == BoundType.CLOSED && value.equals(n);
        }

        private int compare(@NotNull Number n) {
            return comparator.compare(value, n);
        }
    }

    private final Bound lowerBound;
    private final Bound upperBound;
    private final Comparator<Number> comparator;

    public NumberRange(@NotNull Number lowerBound, @NotNull Number upperBound) {
        this(lowerBound, BoundType.CLOSED, upperBound, BoundType.CLOSED);
    }

    public NumberRange(@NotNull Number lowerBound, @NotNull BoundType lowerBoundType,
                       @NotNull Number upperBound, @NotNull BoundType upperBoundType) {
        this.lowerBound = new Bound(lowerBound, lowerBoundType);
        this.upperBound = new Bound(upperBound, upperBoundType);
        this.comparator = null; // TODO
    }

    @Override
    public Number lower() {
        return lowerBound.value;
    }

    @Override
    public BoundType lowerType() {
        return lowerBound.type;
    }

    @Override
    public Number upper() {
        return upperBound.value;
    }

    @Override
    public BoundType upperType() {
        return upperBound.type;
    }

    @Override
    public boolean contains(@NotNull Number n) {
        // Is the number equal to the lower bound, or between the bounds,
        // or equal to the upper bound?
        return lowerBound.includesValue(n) ||
               (lowerBound.compare(n) < 0 && upperBound.compare(n) > 0) ||
               upperBound.includesValue(n);
    }

    @Override
    public Number coerce(@NotNull Number n) {
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

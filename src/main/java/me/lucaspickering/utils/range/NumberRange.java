package me.lucaspickering.utils.range;


import org.jetbrains.annotations.NotNull;

import java.util.Collection;
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
    NumberRange(@NotNull T lowerBound, @NotNull T upperBound) {
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
    NumberRange(@NotNull T lowerBound, @NotNull BoundType lowerBoundType,
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

    /**
     * Constructs a new {@code NumberRange} with the lower bound and upper bound being the
     * minimum and maximum values of the given collection, respectively. Both bounds will be
     * inclusive. This range will be as small as possible while still including all values in the
     * given colletion.
     *
     * @param coll the collection of numbers for this range to bound (non-empty)
     * @throws IllegalArgumentException if the given collection is empty
     */
    NumberRange(@NotNull Collection<T> coll) {
        this(min(coll), max(coll)); // Compute min and max of the given collection
    }

    private static <T extends Number & Comparable<T>> T min(@NotNull Collection<T> coll) {
        return coll.stream()
            .min(T::compareTo)
            .orElseThrow(() -> new IllegalArgumentException("Collection cannot be empty"));
    }

    private static <T extends Number & Comparable<T>> T max(@NotNull Collection<T> coll) {
        return coll.stream()
            .max(T::compareTo)
            .orElseThrow(() -> new IllegalArgumentException("Collection cannot be empty"));
    }

    /**
     * Casts the given {@link Number} to be of this range's type.
     *
     * @param value the value to cast
     * @return the casted value
     */
    abstract T cast(Number value);

    /**
     * Adds two numbers together. This is needed so that addition can be implemented on a
     * type-by-type basis.
     *
     * @param value1 the first number
     * @param value2 the second number
     * @return {@code value1 + value2}
     */
    abstract T plus(T value1, T value2);

    /**
     * Subtracts the second number from the first. This is needed so that subtraction can be
     * implemented on a type-by-type basis.
     *
     * @param value1 the first number
     * @param value2 the second number
     * @return {@code value1 - value2}
     */
    abstract T minus(T value1, T value2);

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

    @NotNull
    @Override
    public T span() {
        return minus(upper(), lower());
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

    @Override
    public double normalize(@NotNull T value) {
        final T coerced = coerce(value); // Coerce the value so that the output is [0, 1]
        // (value - lower) / (upper - lower) -> [0, 1]
        return minus(coerced, lower()).doubleValue() / span().doubleValue();
    }

    @NotNull
    @Override
    public T denormalize(double value) {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException("Given value must be in the range [0, 1]");
        }

        // (value * (upper - lower)) + lower
        return plus(cast(value * span().doubleValue()), lower());
    }

    @NotNull
    @Override
    public <U extends Number & Comparable<U>> U mapTo(@NotNull T value,
                                                      @NotNull Range<U> targetRange) {
        double normalized = normalize(value); // Normalize the value to [0, 1]
        return targetRange.denormalize(normalized); // De-normalize the value to the new range
    }

    @Override
    public String toString() {
        return String.format("%c%s, %s%c",
                             lowerType().getLowerSymbol(), lower(),
                             upper(), upperType().getUpperSymbol());
    }
}

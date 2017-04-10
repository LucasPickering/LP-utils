package me.lucaspickering.utils.range;


import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * A {@code Range} is a set of numbers that is defined by a lower and upper bound, where each
 * bound has a value and a type (inclusive or exclusive).
 */
public interface Range<T extends Number & Comparable<T>> {

    enum BoundType {
        INCLUSIVE('[', ']'), EXCLUSIVE('(', ')');

        private final char lowerSymbol;
        private final char upperSymbol;

        BoundType(char lowerSymbol, char upperSymbol) {
            this.lowerSymbol = lowerSymbol;
            this.upperSymbol = upperSymbol;
        }

        char getLowerSymbol() {
            return lowerSymbol;
        }

        char getUpperSymbol() {
            return upperSymbol;
        }
    }

    /**
     * Gets the value of the lower bound of this range.
     *
     * @return the lower bound value
     */
    @NotNull
    T lower();

    /**
     * Gets the type of the lower bound of this range (inclusive or exclusive).
     *
     * @return the lower bound type
     */
    @NotNull
    BoundType lowerType();

    /**
     * Gets the value of the upper bound of this range.
     *
     * @return the upper bound value
     */
    @NotNull
    T upper();

    /**
     * Gets the type of the upper bound of this range (inclusive or exclusive).
     *
     * @return the upper bound type
     */
    @NotNull
    BoundType upperType();

    /**
     * Gets the span (size) of this range. The span is the value of the upper bound minus the
     * value of the lower bound. The span is not dependent upon the type of each bound.
     *
     * @return {@code upper() - lower()}
     */
    @NotNull
    T span();

    /**
     * Determines if the given value is in this range. A value is in the range if it is strictly
     * between the two bounds, or if it is equal to the lower bound and that bound is closed, or
     * it is equal to the upper bound and that bound is closed.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is in this range, {@code false} otherwise
     */
    boolean contains(@NotNull T n);

    /**
     * Coerces the given value into this range. A number is coerced by forcing it into the range.
     * If the number is less than the lower bound, the lower bound value is return. If it is
     * greater than the upper bound, the upper bound value is returned. Otherwise, the given
     * value is returned.
     *
     * Note that the returned value is not necessarily in this range, i.e.
     * {@code contains(coerce(n))} doesn't always return {@code true}. This is only the case when
     * the number is coerced to an exclusive bound. The coerced number will be the value of the
     * bound, but the range does not include that value because the bound is exclusive.
     *
     * @param n the number to be coerced
     * @return the coerced number
     */
    @NotNull
    T coerce(@NotNull T n);

    /**
     * Returns a randomly-selected value this is in this range. All values in this range have an
     * equal chance of being selected.
     *
     * @param random the {@link Random} instance to use
     * @return a randomly-selected value in this range
     */
    @NotNull
    T randomIn(@NotNull Random random);

    /**
     * Normalizes the given value to the range [0, 1]. If the given value is <= this range's min,
     * it gets mapped to 0. If it is >= this range's max, it gets mapped to 1. Otherwise, it gets
     * mapped between [0, 1] proportional to its position between this range's two bounds.
     *
     * @param value the value to be mapped
     * @return the given value, normalized to the range [0, 1]
     */
    double normalize(@NotNull T value);

    /**
     * De-normalizes the given value from the range [0, 1] to this range. If the given value is
     * 0, it gets mapped to this range's min. If it is 1, it gets mapped to this range's max.
     * Otherwise, it gets mapped to this range proportional to its position between 0 and 1. The
     * returned value can be equal to the value of an exclusive bound of this range, and as such,
     * is not necessarily guaranteed to be in this range (but in almost all cases will be).
     *
     * @param value the value to be de-normalized
     * @return the de-normalized value
     * @throws IllegalArgumentException if the given value is not in the range [0, 1]
     */
    @NotNull
    T denormalize(double value);

    /**
     * Maps the given value from this range into the given range. First, if the given value isn't in
     * this range, it is coerced into it (using {@link #coerce}). Then, the coerced value is
     * proportionally mapped to the target range.
     *
     * For example, if the value is the minimum of this range, it will become the minimum of the
     * second. If it's the max of this range, it becomes the max of the second. If it is halfway
     * between the min and max of this range, it becomes halfway between the min and max of the
     * target, etc. The type of each bound is not relevant; each bound is effectively considered to
     * be inclusive. As such, the mapped value is not guaranteed to be in the target range (it could
     * be equal to an exclusive bound of that range).
     *
     * @param value       the value to map
     * @param targetRange the range to map to
     * @param <U>         the type of number to map to
     * @return the mapped value
     */
    @NotNull <U extends Number & Comparable<U>> U mapTo(@NotNull T value,
                                                        @NotNull Range<U> targetRange);
}

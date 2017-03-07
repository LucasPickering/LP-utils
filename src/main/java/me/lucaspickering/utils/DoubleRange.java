package me.lucaspickering.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DoubleRange extends NumberRange<Double> {

    public DoubleRange(@NotNull Double lowerBound,
                       @NotNull Double upperBound) {
        super(lowerBound, upperBound);
    }

    public DoubleRange(@NotNull Double lowerBound, BoundType lowerBoundType,
                       @NotNull Double upperBound, BoundType upperBoundType) {
        super(lowerBound, lowerBoundType, upperBound, upperBoundType);
    }

    @NotNull
    @Override
    public Double randomIn(@NotNull Random random) {
        // Random's lower bound is inclusive by default, so if we want it to be exclusive, we have
        // to shift it up one ulp.
        double lower = lower();
        if (lowerType() == BoundType.EXCLUSIVE) {
            lower += Math.ulp(lower);
        }

        // Random's upper bound is exclusive by default, so if we want it to be inclusive, we
        // have to shift it up one ulp.
        double upper = upper();
        if (upperType() == BoundType.INCLUSIVE) {
            upper += Math.ulp(upper);
        }

        final double span = upper - lower;
        return lower + random.nextDouble() * span;
    }

    @Override
    @NotNull
    public Double mapTo(@NotNull Double n, @NotNull Range<Double> targetRange) {
        n = coerce(n); // Coerce n into this range first
        final double fromSpan = upper() - lower(); // Get the span of this range
        final double halfMapped = (n - lower()) / fromSpan; // Map to [0, 1]
        final double toSpan = targetRange.upper() - targetRange.lower();
        return halfMapped * toSpan + targetRange.lower(); // Now map to otherRange
    }
}

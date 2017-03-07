package me.lucaspickering.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class FloatRange extends NumberRange<Float> {

    public FloatRange(@NotNull Float lowerBound,
                      @NotNull Float upperBound) {
        super(lowerBound, upperBound);
    }

    public FloatRange(@NotNull Float lowerBound, BoundType lowerBoundType,
                      @NotNull Float upperBound, BoundType upperBoundType) {
        super(lowerBound, lowerBoundType, upperBound, upperBoundType);
    }

    @NotNull
    @Override
    public Float randomIn(@NotNull Random random) {
        // Random's lower bound is inclusive by default, so if we want it to be exclusive, we have
        // to shift it up one ulp.
        float lower = lower();
        if (lowerType() == BoundType.EXCLUSIVE) {
            lower += Math.ulp(lower);
        }

        // Random's upper bound is exclusive by default, so if we want it to be inclusive, we
        // have to shift it up one ulp.
        float upper = upper();
        if (upperType() == BoundType.INCLUSIVE) {
            upper += Math.ulp(upper);
        }

        final float span = upper - lower;
        return lower + random.nextFloat() * span;
    }

    @Override
    @NotNull
    public Float mapTo(@NotNull Float n, @NotNull Range<Float> targetRange) {
        n = coerce(n); // Coerce n into this range first
        final float fromSpan = upper() - lower(); // Get the span of this range
        final float halfMapped = (n - lower()) / fromSpan; // Map to [0, 1]
        final float toSpan = targetRange.upper() - targetRange.lower();
        return halfMapped * toSpan + targetRange.lower(); // Now map to otherRange
    }
}

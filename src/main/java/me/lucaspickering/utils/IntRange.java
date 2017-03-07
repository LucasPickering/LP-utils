package me.lucaspickering.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class IntRange extends NumberRange<Integer> {

    public IntRange(@NotNull Integer lowerBound,
                    @NotNull Integer upperBound) {
        super(lowerBound, upperBound);
    }

    public IntRange(@NotNull Integer lowerBound, BoundType lowerBoundType,
                    @NotNull Integer upperBound, BoundType upperBoundType) {
        super(lowerBound, lowerBoundType, upperBound, upperBoundType);
    }

    @NotNull
    @Override
    public Integer randomIn(@NotNull Random random) {
        // Random's lower bound is inclusive by default, so if we want it to be exclusive, we have
        // to shift it up one.
        int lower = lower();
        if (lowerType() == BoundType.EXCLUSIVE) {
            lower++;
        }

        // Random's upper bound is exclusive by default, so if we want it to be inclusive, we
        // have to shift it up one.
        int upper = upper();
        if (upperType() == BoundType.INCLUSIVE) {
            upper++;
        }

        return lower + random.nextInt(upper - lower);
    }

    @Override
    @NotNull
    public Integer mapTo(@NotNull Integer n, @NotNull Range<Integer> targetRange) {
        n = coerce(n); // Coerce n into this range first
        final int fromSpan = upper() - lower(); // Get the span of this range
        final float halfMapped = (n - lower()) / (float) fromSpan; // Map to [0, 1]
        final int toSpan = targetRange.upper() - targetRange.lower();
        return (int) (halfMapped * toSpan) + targetRange.lower(); // Now map to otherRange
    }
}

package me.lucaspickering.utils.range;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DoubleRange extends NumberRange<Double> {

    public DoubleRange(@NotNull Double lowerBound,
                       @NotNull Double upperBound) {
        super( lowerBound, upperBound);
    }

    public DoubleRange(@NotNull Double lowerBound, BoundType lowerBoundType,
                       @NotNull Double upperBound, BoundType upperBoundType) {
        super( lowerBound, lowerBoundType, upperBound, upperBoundType);
    }

    @Override
    Double cast(Number value) {
        return value.doubleValue();
    }

    @Override
    Double plus(Double value1, Double value2) {
        return value1 + value2;
    }

    @Override
    Double minus(Double value1, Double value2) {
        return value1 - value2;
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
}

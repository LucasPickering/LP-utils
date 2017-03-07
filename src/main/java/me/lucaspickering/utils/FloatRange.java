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

    /**
     * This is only supported for ranges with an inclusive lower bound and exclusive upper bound,
     * as that is the style of value that {@link Random#nextFloat} returns.
     *
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Float randomIn(@NotNull Random random) {
        // We only support inclusive,exclusive for this operation
        if (lowerType() != BoundType.INCLUSIVE || upperType() != BoundType.EXCLUSIVE) {
            throw new UnsupportedOperationException();
        }

        final float span = upper() - lower();
        return lower() + random.nextFloat() * span;
    }
}

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
        throw new UnsupportedOperationException();
    }
}

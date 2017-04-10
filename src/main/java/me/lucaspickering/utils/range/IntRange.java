package me.lucaspickering.utils.range;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
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

    public IntRange(@NotNull Collection<Integer> coll) {
        super(coll);
    }

    @Override
    Integer cast(Number value) {
        return value.intValue();
    }

    @Override
    Integer plus(Integer value1, Integer value2) {
        return value1 + value2;
    }

    @Override
    Integer minus(Integer value1, Integer value2) {
        return value1 - value2;
    }

    @NotNull
    @Override
    public Integer span() {
        return upper() - lower();
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
}

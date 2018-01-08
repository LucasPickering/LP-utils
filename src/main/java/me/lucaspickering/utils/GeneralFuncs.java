package me.lucaspickering.utils;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class GeneralFuncs {

    private GeneralFuncs() {
        // Don't allow instantiation
    }

    /**
     * Returns the first element in a {@link Collection}. Technically collections don't have
     * ordering so this is effectively the same as {@link #randomFromCollection}, but a little bit
     * faster.
     *
     * @param coll the collection to be chosen from (non-null, non-empty)
     * @param <T>  the type of the element in the collection
     * @return the first element in the collection, as determined by its iterator
     */
    @NotNull
    public static <T> T firstFromCollection(@NotNull Collection<T> coll) {
        Objects.requireNonNull(coll);

        // Exception will be thrown if the collection is empty
        return coll.stream()
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Collection is empty"));
    }

    /**
     * Randomly selects one element from the given non-empty collection. Each element has an equal
     * chance of being chosen.
     *
     * @param random the {@link Random} to generate numbers from (non-null)
     * @param coll   the collection to be chosen from (non-null, non-empty)
     * @param <T>    the type of the element in the collection
     * @return one randomly-selected, even-distributed element from the given collection
     */
    @NotNull
    public static <T> T randomFromCollection(@NotNull Random random, @NotNull Collection<T> coll) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(coll);

        // Select a random element from the collection, exception is thrown if coll is empty
        return coll.stream()
            .skip(random.nextInt(coll.size()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Collection is empty"));
    }

    /**
     * Randomly selects one element from the given non-empty collection. Each element has a chance
     * of being chosen that is proportional to that element's return value from {@code
     * weightFunction}.
     *
     * @param random         the {@link Random} to generate numbers from
     * @param coll           the collection to be chosen from (non-null, non-empty)
     * @param weightFunction the function that provides a weight for each element in the collection
     * @param <T>            the type of the element in the collection
     * @return one randomly-selected element from the given collection
     */
    @NotNull
    public static <T> T randomFromCollectionWeighted(@NotNull Random random,
                                                     @NotNull Collection<T> coll,
                                                     @NotNull Function<T, Integer> weightFunction) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(coll);
        Objects.requireNonNull(weightFunction);
        if (coll.isEmpty()) {
            throw new IllegalArgumentException("Collection cannot be empty");
        }

        // Potential optimization?
        // Create a list with x entires for each element, where x is its weight of being chosen
        final List<T> weightedList = new LinkedList<>();
        for (T value : coll) {
            final int weight = weightFunction.apply(value);
            for (int i = 0; i < weight; i++) {
                weightedList.add(value);
            }
        }

        // Select an element from the weighted list
        return randomFromCollection(random, weightedList);
    }

    /**
     * Returns a random boolean, with the given chance of being true. If the given weight is 0 or
     * less, there is a 0% chance of getting true, 1 or greater is 100%, 0.5 is 50%, etc.
     *
     * @param weight the weight towards true, with 0.5 being a 50/50 chance
     * @param random the {@link Random} instance to use
     * @return a random boolean with the given weight towards true
     */
    public static boolean weightedChance(Random random, float weight) {
        return random.nextFloat() < weight;
    }

    /**
     * Applies a random amount of slop to the given value.
     *
     * @param random  the {@link Random} to use
     * @param x       the value to be randomized
     * @param maxSlop the maximum amount of slop to apply
     * @return a random, uniformly distributed value in the range {@code [x - maxSlop, x + maxSlop]}
     */
    public static int randomSlop(Random random, int x, int maxSlop) {
        return x + random.nextInt(maxSlop * 2 + 1) - maxSlop;
    }

    /**
     * Run the given function and time how long it takes.
     *
     * @param func the function to run
     * @return the time it took to execute, in milliseconds
     */
    public static long timed(Runnable func) {
        final long startTime = System.currentTimeMillis();
        func.run();
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Run the given function and time how long it takes, returning both the execution time and the
     * return value of the function.
     *
     * @param func the function to run
     * @return the return value of the function and the time it took to execute, in milliseconds
     */
    public static <T> Pair<Long, T> timedValue(Supplier<T> func) {
        final long startTime = System.currentTimeMillis();
        final T rv = func.get();
        return new Pair<>(System.currentTimeMillis() - startTime, rv);
    }
}

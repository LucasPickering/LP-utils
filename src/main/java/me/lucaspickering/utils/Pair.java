package me.lucaspickering.utils;

import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Pair<T, U> {

    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }

    private final U second;
    private final T first;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns a {@link Collector} that will convert a {@link java.util.stream.Stream} of pairs
     * into a {@link Map} of those same pairs.
     *
     * @param <K> the type of the first value in each pair, to become the type of each key
     * @param <V> the type of the second value in each pair, to become the type of each value
     * @return a {@link Collector} that will turn pairs into a {@link Map}
     */
    public static <K, V> Collector<Pair<K, V>, ?, Map<K, V>> mapCollector() {
        return Collectors.toMap(Pair::first, Pair::second);
    }

    public static <K, V, M extends Map<K, V>>
    Collector<Pair<K, V>, ?, M> mapCollector(Supplier<M> mapSupplier) {
        return Collectors.toMap(Pair::first, Pair::second, throwingMerger(), mapSupplier);
    }

    public static <K, V, M extends Map<K, V>>
    Collector<Pair<K, V>, ?, M> mapCollector(BinaryOperator<V> mergeFunction,
                                             Supplier<M> mapSupplier) {
        return Collectors.toMap(Pair::first, Pair::second, mergeFunction, mapSupplier);
    }

    public T first() {
        return first;
    }

    public U second() {
        return second;
    }
}

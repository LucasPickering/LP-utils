package me.lucaspickering.utils.collections;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class KeyedSet<E extends Keyable> extends AbstractSet<E> {

    // Internal map
    private final Map<Object, E> map;

    public KeyedSet() {
        this(new HashMap<>());
    }

    public KeyedSet(Map<Object, E> internalMap) {
        map = internalMap;
    }

    public KeyedSet(Collection<? extends E> coll) {
        this();
        addAll(coll);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public E getByKey(Object key) {
        return map.get(key);
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Keyable) {
            final Keyable keyable = (Keyable) o;
            return containsKey(keyable.getKey());
        }
        return false;
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return map.values().iterator();
    }

    @Override
    public boolean add(E e) {
        Objects.requireNonNull(e);
        map.put(e.getKey(), e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof Keyable) {
            final Keyable keyable = (Keyable) o;
            return removeByKey(keyable.getKey());
        }
        return false;
    }

    public boolean removeByKey(Object key) {
        return map.remove(key) != null;
    }

    @Override
    public void clear() {
        map.clear();
    }
}

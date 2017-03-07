package me.lucaspickering;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import me.lucaspickering.utils.Funcs;

import static org.junit.Assert.assertEquals;

public class TestFuncs {

    @Test
    public void testFirstFromCollection() {
        // Test normal functionality of Funcs.firstFromCollection
        final int n = 100;
        final List<Integer> l = new ArrayList<>();
        l.add(n);

        int first;

        first = Funcs.firstFromCollection(l);
        assertEquals(n, first);

        for (int i = 0; i < 10; i++) {
            l.add(i);
        }

        first = Funcs.firstFromCollection(l);
        assertEquals(n, first);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFirstFromCollectionFailure() {
        // Test that firstFromCollection fails on an empty collection
        Funcs.firstFromCollection(new ArrayList<>());
    }
}
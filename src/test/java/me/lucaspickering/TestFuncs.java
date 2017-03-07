package me.lucaspickering;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import me.lucaspickering.utils.Funcs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFuncs {

    @Test
    public void testFirstFromCollection() {
        // Test normal functionality of Funcs.firstFromCollection
        final int n = 100;
        final List<Integer> l = new ArrayList<>();
        l.add(n);

        int first;

        first = Funcs.firstFromCollection(l);
        assertEquals("Should return first value in the list", n, first);

        for (int i = 0; i < 10; i++) {
            l.add(i);
        }

        first = Funcs.firstFromCollection(l);
        assertEquals("Should return first value in the list", n, first);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFirstFromCollectionFailure() {
        // Test that firstFromCollection fails on an empty collection
        Funcs.firstFromCollection(new ArrayList<>());
    }

    @Test
    public void testRandomFromCollection() {
        // Test normal functionality of Funcs.randomFromCollection
        final Random random = new Random();

        final int size = 10;
        final Collection<Integer> coll = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            coll.add(i);
        }

        final int randomInt = Funcs.randomFromCollection(random, coll);
        assertTrue("Should be in range [0, 9]", 0 <= randomInt && randomInt < size);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomFromCollectionFailure() {
        // Test that randomFromCollection fails on an empty collection
        final Random random = new Random();
        Funcs.randomFromCollection(random, new ArrayList<>());
    }

    @Test
    public void testRandomFromCollectionWeighted() {
        // Test normal functionality of Funcs.randomFromCollectionWeighted
        final Random random = new Random();

        final int size = 10;
        final Collection<Integer> coll = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            coll.add(i);
        }

        int randomInt;

        // Weight of 1 each
        randomInt = Funcs.randomFromCollectionWeighted(random, coll, (e) -> 1);
        assertTrue("Should be in range [0, 9]", 0 <= randomInt && randomInt < size);

        // Try with different weighting
        randomInt = Funcs.randomFromCollectionWeighted(random, coll, (e) -> 3);
        assertTrue("Should be in range [0, 9]", 0 <= randomInt && randomInt < size);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomFromCollectionWeightedFailure() {
        // Test that randomFromCollectionWeighted fails on an empty collection
        final Random random = new Random();
        Funcs.randomFromCollectionWeighted(random, new ArrayList<>(), (e) -> 1);
    }

    @Test
    public void testWeightedChance() {
        final Random random = new Random();

        // Test that chance of 0 always returns false
        assertFalse("Should always return false", Funcs.weightedChance(random, 0f));

        // Test that chance of 1 always returns true
        assertTrue("Should always return true", Funcs.weightedChance(random, 1f));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWeightedChanceTooLow() {
        Funcs.weightedChance(new Random(), -0.1f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWeightedChanceTooHigh() {
        Funcs.weightedChance(new Random(), 1.1f);
    }

    @Test
    public void testRandomSlop() {
        final Random random = new Random();
        final int slop = 3;
        final int iterations = 50;

        // run it a bunch of times and make sure it's always withing the slop range
        for (int i = 0; i < iterations; i++) {
            final int randInt = Funcs.randomSlop(random, i, slop);
            assertTrue("Should be within the slop value", Math.abs(randInt - i) <= slop);
        }
    }
}
package me.lucaspickering;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import me.lucaspickering.utils.GeneralFuncs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestGeneralFuncs {

    @Test
    public void testFirstFromCollection() {
        // Test normal functionality of GeneralFuncs.firstFromCollection
        final int n = 100;
        final List<Integer> l = new ArrayList<>();
        l.add(n);

        int first;

        first = GeneralFuncs.firstFromCollection(l);
        assertEquals("Should return first value in the list", n, first);

        for (int i = 0; i < 10; i++) {
            l.add(i);
        }

        first = GeneralFuncs.firstFromCollection(l);
        assertEquals("Should return first value in the list", n, first);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFirstFromCollectionFailure() {
        // Test that firstFromCollection fails on an empty collection
        GeneralFuncs.firstFromCollection(new ArrayList<>());
    }

    @Test
    public void testRandomFromCollection() {
        // Test normal functionality of GeneralFuncs.randomFromCollection
        final Random random = new Random();

        final int size = 10;
        final Collection<Integer> coll = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            coll.add(i);
        }

        final int randomInt = GeneralFuncs.randomFromCollection(random, coll);
        assertTrue("Should be in range [0, 9]", 0 <= randomInt && randomInt < size);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomFromCollectionFailure() {
        // Test that randomFromCollection fails on an empty collection
        final Random random = new Random();
        GeneralFuncs.randomFromCollection(random, new ArrayList<>());
    }

    @Test
    public void testRandomFromCollectionWeighted() {
        // Test normal functionality of GeneralFuncs.randomFromCollectionWeighted
        final Random random = new Random();

        final int size = 10;
        final Collection<Integer> coll = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            coll.add(i);
        }

        int randomInt;

        // Weight of 1 each
        randomInt = GeneralFuncs.randomFromCollectionWeighted(random, coll, (e) -> 1);
        assertTrue("Should be in range [0, 9]", 0 <= randomInt && randomInt < size);

        // Try with different weighting
        randomInt = GeneralFuncs.randomFromCollectionWeighted(random, coll, (e) -> 3);
        assertTrue("Should be in range [0, 9]", 0 <= randomInt && randomInt < size);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomFromCollectionWeightedFailure() {
        // Test that randomFromCollectionWeighted fails on an empty collection
        final Random random = new Random();
        GeneralFuncs.randomFromCollectionWeighted(random, new ArrayList<>(), (e) -> 1);
    }

    @Test
    public void testWeightedChance() {
        final Random random = new Random();

        // Test that chance of 0 always returns false
        assertFalse("Should always return false", GeneralFuncs.weightedChance(random, 0f));
        assertFalse("Should always return false", GeneralFuncs.weightedChance(random, -1f));

        // Test that chance of 1 always returns true
        assertTrue("Should always return true", GeneralFuncs.weightedChance(random, 1f));
        assertTrue("Should always return true", GeneralFuncs.weightedChance(random, 2f));
    }

    @Test
    public void testRandomSlop() {
        final Random random = new Random();
        final int slop = 3;
        final int iterations = 50;

        // run it a bunch of times and make sure it's always withing the slop range
        for (int i = 0; i < iterations; i++) {
            final int randInt = GeneralFuncs.randomSlop(random, i, slop);
            assertTrue("Should be within the slop value", Math.abs(randInt - i) <= slop);
        }
    }
}
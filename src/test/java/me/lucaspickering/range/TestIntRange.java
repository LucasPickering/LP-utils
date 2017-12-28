package me.lucaspickering.range;

import org.junit.Test;

import java.util.Random;

import me.lucaspickering.utils.range.IntRange;
import me.lucaspickering.utils.range.Range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestIntRange {

    @Test(expected = IllegalArgumentException.class)
    public void testBackwardsBoundsFailure() {
        new IntRange(10, 9); // If lower > upper, it should fail
    }

    @Test
    public void testBounds() {
        // Simple test for the getters
        Range<Integer> range = new IntRange(0, Range.BoundType.INCLUSIVE,
                                            10, Range.BoundType.EXCLUSIVE);
        assertEquals(0, range.lower().intValue());
        assertEquals(Range.BoundType.INCLUSIVE, range.lowerType());
        assertEquals(10, range.upper().intValue());
        assertEquals(Range.BoundType.EXCLUSIVE, range.upperType());

        range = new IntRange(0, 10);
        assertEquals(0, range.lower().intValue());
        assertEquals(Range.BoundType.INCLUSIVE, range.lowerType());
        assertEquals(10, range.upper().intValue());
        assertEquals(Range.BoundType.INCLUSIVE, range.upperType());
    }

    @Test
    public void testContains() {
        // Try an open,open range
        Range<Integer> range = new IntRange(10, Range.BoundType.EXCLUSIVE,
                                            15, Range.BoundType.EXCLUSIVE);
        assertFalse("Range should not contain the value", range.contains(9));
        assertFalse("Range should not contain the value", range.contains(10));
        assertTrue("Range should contain the value", range.contains(12));
        assertFalse("Range should not contain the value", range.contains(15));
        assertFalse("Range should not contain the value", range.contains(16));

        // Try a closed,closed range
        range = new IntRange(10, Range.BoundType.INCLUSIVE,
                             15, Range.BoundType.INCLUSIVE);
        assertFalse("Range should not contain the value", range.contains(9));
        assertTrue("Range should contain the value", range.contains(10));
        assertTrue("Range should contain the value", range.contains(12));
        assertTrue("Range should contain the value", range.contains(15));
        assertFalse("Range should not contain the value", range.contains(16));
    }

    @Test
    public void testCoerce() {
        // Try an open,open range
        Range<Integer> range = new IntRange(10, Range.BoundType.EXCLUSIVE,
                                            15, Range.BoundType.EXCLUSIVE);
        assertEquals("Should return lower bound value", 10, range.coerce(9).intValue());
        assertEquals("Should return same value", 10, range.coerce(10).intValue());
        assertEquals("Should return same value", 12, range.coerce(12).intValue());
        assertEquals("Should return same value", 15, range.coerce(15).intValue());
        assertEquals("Should return upper bound value", 15, range.coerce(16).intValue());

        // Try a closed,closed range (should be the same)
        range = new IntRange(10, Range.BoundType.INCLUSIVE,
                             15, Range.BoundType.INCLUSIVE);
        assertEquals("Should return lower bound value", 10, range.coerce(9).intValue());
        assertEquals("Should return same value", 10, range.coerce(10).intValue());
        assertEquals("Should return same value", 12, range.coerce(12).intValue());
        assertEquals("Should return same value", 15, range.coerce(15).intValue());
        assertEquals("Should return upper bound value", 15, range.coerce(16).intValue());
    }

    @Test
    public void testRandomIn() {
        Range<Integer> range;
        final Random random = new Random();
        final int iterations = 50;

        range = new IntRange(10, Range.BoundType.EXCLUSIVE,
                             15, Range.BoundType.EXCLUSIVE);
        for (int i = 0; i < iterations; i++) {
            final int r = range.randomIn(random);
            assertTrue("Random value should be in the range", range.contains(r));
        }

        range = new IntRange(10, Range.BoundType.INCLUSIVE,
                             15, Range.BoundType.EXCLUSIVE);
        for (int i = 0; i < iterations; i++) {
            final int r = range.randomIn(random);
            assertTrue("Random value should be in the range", range.contains(r));
        }

        range = new IntRange(10, Range.BoundType.EXCLUSIVE,
                             15, Range.BoundType.INCLUSIVE);
        for (int i = 0; i < iterations; i++) {
            final int r = range.randomIn(random);
            assertTrue("Random value should be in the range", range.contains(r));
        }

        range = new IntRange(10, Range.BoundType.INCLUSIVE,
                             15, Range.BoundType.INCLUSIVE);
        for (int i = 0; i < iterations; i++) {
            final int r = range.randomIn(random);
            assertTrue("Random value should be in the range", range.contains(r));
        }
    }

    @Test
    public void testMapTo() {
        Range<Integer> fromRange;
        Range<Integer> toRange;
        fromRange = new IntRange(10, 20);
        toRange = new IntRange(100, 200);

        assertEquals("Should map to the minimum", fromRange.mapTo(9, toRange).intValue(), 100);
        assertEquals("Should map to the minimum", fromRange.mapTo(10, toRange).intValue(), 100);
        assertEquals("Should map to a middle value", fromRange.mapTo(13, toRange).intValue(), 130);
        assertEquals("Should map to the maximum", fromRange.mapTo(20, toRange).intValue(), 200);
        assertEquals("Should map to the maximum", fromRange.mapTo(21, toRange).intValue(), 200);
    }
}

package me.lucaspickering;

import org.junit.Test;

import java.util.Random;

import me.lucaspickering.utils.range.DoubleRange;
import me.lucaspickering.utils.range.Range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestDoubleRange {

    @Test(expected = IllegalArgumentException.class)
    public void testBackwardsBoundsFailure() {
        new DoubleRange(10.0, 9.0); // If lower > upper, it should fail
    }

    @Test
    public void testBounds() {
        // Simple test for the getters
        Range<Double> range = new DoubleRange(-5.3, Range.BoundType.INCLUSIVE,
                                              10.1, Range.BoundType.EXCLUSIVE);
        assertEquals(-5.3, range.lower(), 0.0);
        assertEquals(Range.BoundType.INCLUSIVE, range.lowerType());
        assertEquals(10.1, range.upper(), 0.0);
        assertEquals(Range.BoundType.EXCLUSIVE, range.upperType());

        range = new DoubleRange(-5.3, 10.1);
        assertEquals(-5.3, range.lower(), 0.0);
        assertEquals(Range.BoundType.INCLUSIVE, range.lowerType());
        assertEquals(10.1, range.upper(), 0.0);
        assertEquals(Range.BoundType.INCLUSIVE, range.upperType());
    }

    @Test
    public void testContains() {
        // Try an open,open range
        Range<Double> range = new DoubleRange(3.5, Range.BoundType.EXCLUSIVE,
                                              7.6, Range.BoundType.EXCLUSIVE);
        assertFalse("Range should not contain the value", range.contains(3.4999));
        assertFalse("Range should not contain the value", range.contains(3.5));
        assertTrue("Range should contain the value", range.contains(5.0));
        assertFalse("Range should not contain the value", range.contains(7.6));
        assertFalse("Range should not contain the value", range.contains(7.600001));

        // Try a closed,closed range
        range = new DoubleRange(3.5, Range.BoundType.INCLUSIVE,
                                7.6, Range.BoundType.INCLUSIVE);
        assertFalse("Range should not contain the value", range.contains(3.4999));
        assertTrue("Range should contain the value", range.contains(3.5));
        assertTrue("Range should contain the value", range.contains(5.0));
        assertTrue("Range should contain the value", range.contains(7.6));
        assertFalse("Range should not contain the value", range.contains(7.600001));
    }

    @Test
    public void testCoerce() {
        // Try an open,open range
        Range<Double> range = new DoubleRange(3.6, Range.BoundType.EXCLUSIVE,
                                              10.4, Range.BoundType.EXCLUSIVE);
        assertEquals("Should return lower bound value", 3.6, range.coerce(3.5), 0.0);
        assertEquals("Should return same value", 3.6, range.coerce(3.6), 0.0);
        assertEquals("Should return same value", 5.0, range.coerce(5.0), 0.0);
        assertEquals("Should return same value", 10.4, range.coerce(10.4), 0.0);
        assertEquals("Should return upper bound value", 10.4, range.coerce(10.5), 0.0);

        // Try a closed,closed range (should be the same)
        range = new DoubleRange(3.6, Range.BoundType.INCLUSIVE,
                                10.4, Range.BoundType.INCLUSIVE);
        assertEquals("Should return lower bound value", 3.6, range.coerce(3.5), 0.0);
        assertEquals("Should return same value", 3.6, range.coerce(3.6), 0.0);
        assertEquals("Should return same value", 5.0, range.coerce(5.0), 0.0);
        assertEquals("Should return same value", 10.4, range.coerce(10.4), 0.0);
        assertEquals("Should return upper bound value", 10.4, range.coerce(10.5), 0.0);
    }

    @Test
    public void testRandomIn() {
        Range<Double> range;
        final Random random = new Random();
        final int iterations = 50;

        range = new DoubleRange(10.0, Range.BoundType.EXCLUSIVE,
                                15.0, Range.BoundType.EXCLUSIVE);
        for (int i = 0; i < iterations; i++) {
            final double r = range.randomIn(random);
            assertTrue("Random value should be in the range", range.contains(r));
        }

        range = new DoubleRange(10.0, Range.BoundType.INCLUSIVE,
                                15.0, Range.BoundType.EXCLUSIVE);
        for (int i = 0; i < iterations; i++) {
            final double r = range.randomIn(random);
            assertTrue("Random value should be in the range", range.contains(r));
        }

        range = new DoubleRange(10.0, Range.BoundType.EXCLUSIVE,
                                15.0, Range.BoundType.INCLUSIVE);
        for (int i = 0; i < iterations; i++) {
            final double r = range.randomIn(random);
            assertTrue("Random value should be in the range", range.contains(r));
        }

        range = new DoubleRange(10.0, Range.BoundType.INCLUSIVE,
                                15.0, Range.BoundType.INCLUSIVE);
        for (int i = 0; i < iterations; i++) {
            final double r = range.randomIn(random);
            assertTrue("Random value should be in the range", range.contains(r));
        }
    }

    @Test
    public void testMapTo() {
        Range<Double> fromRange;
        Range<Double> toRange;
        fromRange = new DoubleRange(10.0, 20.0);
        toRange = new DoubleRange(100.0, 200.0);

        assertEquals("Should map to the minimum", fromRange.mapTo(9.0, toRange), 100.0, 0.0);
        assertEquals("Should map to the minimum", fromRange.mapTo(10.0, toRange), 100.0, 0.0);
        assertEquals("Should map to a middle value", fromRange.mapTo(13.0, toRange), 130.0, 0.0);
        assertEquals("Should map to the maximum", fromRange.mapTo(20.0, toRange), 200.0, 0.0);
        assertEquals("Should map to the maximum", fromRange.mapTo(21.0, toRange), 200.0, 0.0);
    }
}

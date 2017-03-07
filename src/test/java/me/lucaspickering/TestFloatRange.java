package me.lucaspickering;

import org.junit.Test;

import java.util.Random;

import me.lucaspickering.utils.FloatRange;
import me.lucaspickering.utils.Range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFloatRange {

    @Test(expected = IllegalArgumentException.class)
    public void testBackwardsBoundsFailure() {
        new FloatRange(10f, 9f); // If lower > upper, it should fail
    }

    @Test
    public void testBounds() {
        // Simple test for the getters
        Range<Float> range = new FloatRange(-5.3f, Range.BoundType.INCLUSIVE,
                                            10.1f, Range.BoundType.EXCLUSIVE);
        assertEquals(-5.3f, range.lower(), 0f);
        assertEquals(Range.BoundType.INCLUSIVE, range.lowerType());
        assertEquals(10.1f, range.upper(), 0f);
        assertEquals(Range.BoundType.EXCLUSIVE, range.upperType());

        range = new FloatRange(-5.3f, 10.1f);
        assertEquals(-5.3f, range.lower(), 0f);
        assertEquals(Range.BoundType.INCLUSIVE, range.lowerType());
        assertEquals(10.1f, range.upper(), 0f);
        assertEquals(Range.BoundType.INCLUSIVE, range.upperType());
    }

    @Test
    public void testContains() {
        // Try an open,open range
        Range<Float> range = new FloatRange(3.5f, Range.BoundType.EXCLUSIVE,
                                            7.6f, Range.BoundType.EXCLUSIVE);
        assertFalse("Range should not contain the value", range.contains(3.4999f));
        assertFalse("Range should not contain the value", range.contains(3.5f));
        assertTrue("Range should contain the value", range.contains(5f));
        assertFalse("Range should not contain the value", range.contains(7.6f));
        assertFalse("Range should not contain the value", range.contains(7.600001f));

        // Try a closed,closed range
        range = new FloatRange(3.5f, Range.BoundType.INCLUSIVE,
                               7.6f, Range.BoundType.INCLUSIVE);
        assertFalse("Range should not contain the value", range.contains(3.4999f));
        assertTrue("Range should contain the value", range.contains(3.5f));
        assertTrue("Range should contain the value", range.contains(5f));
        assertTrue("Range should contain the value", range.contains(7.6f));
        assertFalse("Range should not contain the value", range.contains(7.600001f));
    }

    @Test
    public void testCoerce() {
        // Try an open,open range
        Range<Float> range = new FloatRange(3.6f, Range.BoundType.EXCLUSIVE,
                                            10.4f, Range.BoundType.EXCLUSIVE);
        assertEquals("Should return lower bound value", 3.6f, range.coerce(3.5f), 0f);
        assertEquals("Should return same value", 3.6f, range.coerce(3.6f), 0f);
        assertEquals("Should return same value", 5f, range.coerce(5f), 0f);
        assertEquals("Should return same value", 10.4f, range.coerce(10.4f), 0f);
        assertEquals("Should return upper bound value", 10.4f, range.coerce(10.5f), 0f);

        // Try a closed,closed range (should be the same)
        range = new FloatRange(3.6f, Range.BoundType.INCLUSIVE,
                               10.4f, Range.BoundType.INCLUSIVE);
        assertEquals("Should return lower bound value", 3.6f, range.coerce(3.5f), 0f);
        assertEquals("Should return same value", 3.6f, range.coerce(3.6f), 0f);
        assertEquals("Should return same value", 5f, range.coerce(5f), 0f);
        assertEquals("Should return same value", 10.4f, range.coerce(10.4f), 0f);
        assertEquals("Should return upper bound value", 10.4f, range.coerce(10.5f), 0f);
    }

    @Test
    public void testRandomIn() {
        Range<Float> range;
        final Random random = new Random();
        final int iterations = 50;

        range = new FloatRange(10f, Range.BoundType.INCLUSIVE,
                               15f, Range.BoundType.EXCLUSIVE);
        for (int i = 0; i < iterations; i++) {
            final float r = range.randomIn(random);
            assertTrue("Random value should be in the range", range.contains(r));
        }
    }
}

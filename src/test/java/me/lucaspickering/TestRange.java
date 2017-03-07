package me.lucaspickering;

import org.junit.Test;

import me.lucaspickering.utils.IntRange;
import me.lucaspickering.utils.Range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRange {

    @Test(expected = IllegalArgumentException.class)
    public void testBackwardsBoundsFailure() {
        new IntRange(10, 9); // If lower > upper, it should fail
    }

    @Test
    public void testBoundsInt() {
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
    public void testBoundsFloat() {
        // Simple test for the getters
        Range<Float> range = new NumberRange<>(-5.3f, Range.BoundType.INCLUSIVE,
                                               10.1f, Range.BoundType.EXCLUSIVE);
        assertEquals(-5.3f, range.lower(), 0f);
        assertEquals(Range.BoundType.INCLUSIVE, range.lowerType());
        assertEquals(10.1f, range.upper(), 0f);
        assertEquals(Range.BoundType.EXCLUSIVE, range.upperType());

        range = new NumberRange<>(-5.3f, 10.1f);
        assertEquals(-5.3f, range.lower(), 0f);
        assertEquals(Range.BoundType.INCLUSIVE, range.lowerType());
        assertEquals(10.1f, range.upper(), 0f);
        assertEquals(Range.BoundType.INCLUSIVE, range.upperType());
    }

    @Test
    public void testContainsInt() {
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
    public void testContainsFloat() {
        // Try an open,open range
        Range<Float> range = new NumberRange<>(3.5f, Range.BoundType.EXCLUSIVE,
                                               7.6f, Range.BoundType.EXCLUSIVE);
        assertFalse("Range should not contain the value", range.contains(3.4999f));
        assertFalse("Range should not contain the value", range.contains(3.5f));
        assertTrue("Range should contain the value", range.contains(5f));
        assertFalse("Range should not contain the value", range.contains(7.6f));
        assertFalse("Range should not contain the value", range.contains(7.600001f));

        // Try a closed,closed range
        range = new NumberRange<>(3.5f, Range.BoundType.INCLUSIVE,
                                  7.6f, Range.BoundType.INCLUSIVE);
        assertFalse("Range should not contain the value", range.contains(3.4999f));
        assertTrue("Range should contain the value", range.contains(3.5f));
        assertTrue("Range should contain the value", range.contains(5f));
        assertTrue("Range should contain the value", range.contains(7.6f));
        assertFalse("Range should not contain the value", range.contains(7.600001f));
    }

    @Test
    public void testCoerceInt() {
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
    public void testCoerceFloat() {
        // Try an open,open range
        Range<Float> range = new NumberRange<>(3.6f, Range.BoundType.EXCLUSIVE,
                                               10.4f, Range.BoundType.EXCLUSIVE);
        assertEquals("Should return lower bound value", 3.6f, range.coerce(3.5f), 0f);
        assertEquals("Should return same value", 3.6f, range.coerce(3.6f), 0f);
        assertEquals("Should return same value", 5f, range.coerce(5f), 0f);
        assertEquals("Should return same value", 10.4f, range.coerce(10.4f), 0f);
        assertEquals("Should return upper bound value", 10.4f, range.coerce(10.5f), 0f);

        // Try a closed,closed range (should be the same)
        range = new NumberRange<>(3.6f, Range.BoundType.INCLUSIVE,
                                  3.6f, Range.BoundType.INCLUSIVE);
        assertEquals("Should return lower bound value", 3.6f, range.coerce(3.5f), 0f);
        assertEquals("Should return same value", 3.6f, range.coerce(3.6f), 0f);
        assertEquals("Should return same value", 5f, range.coerce(5f), 0f);
        assertEquals("Should return same value", 10.4f, range.coerce(10.4f), 0f);
        assertEquals("Should return upper bound value", 10.4f, range.coerce(10.5f), 0f);
    }
}

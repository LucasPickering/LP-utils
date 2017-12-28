package me.lucaspickering;

import org.junit.Test;

import me.lucaspickering.utils.Point2;
import static org.junit.Assert.assertEquals;

public class TestPoint2 {

    @Test
    public void testGetters() {
        final Point2 p = new Point2(0.0, 1.0);
        assertEquals(0.0, p.x(), 0.0);
        assertEquals(1.0, p.y(), 0.0);
    }

    @Test
    public void testZero() {
        assertEquals(new Point2(0.0, 0.0), Point2.ZERO);
    }

    @Test
    public void testPlus() {
        final Point2 p = new Point2(1.0, 1.0);
        assertEquals(new Point2(0.5, 1.5), p.plus(new Point2(-0.5, 0.5)));
        assertEquals(new Point2(0.5, 1.5), p.plus(-0.5, 0.5));
    }

    @Test
    public void testMinus() {
        final Point2 p = new Point2(1.0, 1.0);
        assertEquals(new Point2(1.5, 0.5), p.minus(new Point2(-0.5, 0.5)));
        assertEquals(new Point2(1.5, 0.5), p.minus(-0.5, 0.5));
    }

    @Test
    public void testScale() {
        final Point2 p = new Point2(1.0, 1.5);
        assertEquals(new Point2(2.0, 3.0), p.scale(2.0));
    }

    @Test
    public void testDistanceTo() {
        final Point2 p1 = new Point2(1.0, 1.0);
        final Point2 p2 = new Point2(1.0, 0.0);
        final Point2 p3 = new Point2(0.0, 1.0);

        assertEquals(1.0, p1.distanceTo(p2), 0.0000001);
        assertEquals(1.0, p1.distanceTo(p3), 0.0000001);
        assertEquals(Math.sqrt(2.0), p2.distanceTo(p3), 0.0000001);
    }
}

package me.lucaspickering;

import org.junit.Test;

import me.lucaspickering.utils.Point3;
import static org.junit.Assert.assertEquals;

public class TestPoint3 {

    @Test
    public void testGetters() {
        final Point3 p = new Point3(0.0, 1.0, 2.0);
        assertEquals(0.0, p.x(), 0.0);
        assertEquals(1.0, p.y(), 0.0);
        assertEquals(2.0, p.z(), 0.0);
    }

    @Test
    public void testZero() {
        assertEquals(new Point3(0.0, 0.0, 0.0), Point3.ZERO);
    }

    @Test
    public void testPlus() {
        final Point3 p = new Point3(1.0, 1.0, 1.0);
        assertEquals(new Point3(0.5, 1.5, 2.0), p.plus(new Point3(-0.5, 0.5, 1.0)));
        assertEquals(new Point3(0.5, 1.5, 2.0), p.plus(-0.5, 0.5, 1.0));
    }

    @Test
    public void testMinus() {
        final Point3 p = new Point3(1.0, 1.0, 1.0);
        assertEquals(new Point3(1.5, 0.5, 0.0), p.minus(new Point3(-0.5, 0.5, 1.0)));
        assertEquals(new Point3(1.5, 0.5, 0.0), p.minus(-0.5, 0.5, 1.0));
    }

    @Test
    public void testScale() {
        final Point3 p = new Point3(1.0, 1.5, 2.0);
        assertEquals(new Point3(2.0, 3.0, 4.0), p.scale(2.0));
    }

    @Test
    public void testDistanceTo() {
        final Point3 p1 = new Point3(1.0, 1.0, 1.0);
        final Point3 p2 = new Point3(1.0, 0.0, 1.0);
        final Point3 p3 = new Point3(0.0, 1.0, 1.0);
        final Point3 p4 = new Point3(0.0, 0.0, 1.0);
        final Point3 p5 = new Point3(1.0, 1.0, 0.0);

        assertEquals(1.0, p1.distanceTo(p2), 0.0000001);
        assertEquals(1.0, p1.distanceTo(p3), 0.0000001);
        assertEquals(Math.sqrt(2.0), p2.distanceTo(p3), 0.0000001);
        assertEquals(Math.sqrt(2.0), p1.distanceTo(p4), 0.0000001);
        assertEquals(Math.sqrt(3.0), p4.distanceTo(p5), 0.0000001);
    }
}

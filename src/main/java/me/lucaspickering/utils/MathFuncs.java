package me.lucaspickering.utils;

public class MathFuncs {

    private MathFuncs() {
        // Don't allow instantiation
    }

    /**
     * Varargs version of {@link Math#min(int, int)}.
     *
     * @param values the values to find the minimum of (non-empty)
     * @return minimum of all given values
     * @throws IllegalArgumentException if no values are given
     */
    public static int min(int... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Given array is empty");
        }

        int min = values[0]; // Start with the first value (we know it's not empty)
        for (int i = 1; i < values.length; i++) {
            final int val = values[i];
            if (val < min) {
                min = val;
            }
        }
        return min;
    }

    /**
     * Varargs version of {@link Math#min(long, long)}.
     *
     * @param values the values to find the minimum of (non-empty)
     * @return minimum of all given values
     * @throws IllegalArgumentException if no values are given
     */
    public static long min(long... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Given array is empty");
        }

        long min = values[0]; // Start with the first value (we know it's not empty)
        for (int i = 1; i < values.length; i++) {
            final long val = values[i];
            if (val < min) {
                min = val;
            }
        }
        return min;
    }

    /**
     * Varargs version of {@link Math#min(float, float)}.
     *
     * @param values the values to find the minimum of (non-empty)
     * @return minimum of all given values
     * @throws IllegalArgumentException if no values are given
     */
    public static float min(float... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Given array is empty");
        }

        float min = values[0]; // Start with the first value (we know it's not empty)
        for (int i = 1; i < values.length; i++) {
            final float val = values[i];
            if (val < min) {
                min = val;
            }
        }
        return min;
    }

    /**
     * Varargs version of {@link Math#min(double, double)}.
     *
     * @param values the values to find the minimum of (non-empty)
     * @return minimum of all given values
     * @throws IllegalArgumentException if no values are given
     */
    public static double min(double... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Given array is empty");
        }

        double min = values[0]; // Start with the first value (we know it's not empty)
        for (int i = 1; i < values.length; i++) {
            final double val = values[i];
            if (val < min) {
                min = val;
            }
        }
        return min;
    }

    /**
     * Varargs version of {@link Math#max(int, int)}.
     *
     * @param values the values to find the maximum of (non-empty)
     * @return maximum of all given values
     * @throws IllegalArgumentException if no values are given
     */
    public static int max(int... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Given array is empty");
        }

        int max = values[0]; // Start with the first value (we know it's not empty)
        for (int i = 1; i < values.length; i++) {
            final int val = values[i];
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    /**
     * Varargs version of {@link Math#max(long, long)}.
     *
     * @param values the values to find the maximum of (non-empty)
     * @return maximum of all given values
     * @throws IllegalArgumentException if no values are given
     */
    public static long max(long... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Given array is empty");
        }

        long max = values[0]; // Start with the first value (we know it's not empty)
        for (int i = 1; i < values.length; i++) {
            final long val = values[i];
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    /**
     * Varargs version of {@link Math#max(float, float)}.
     *
     * @param values the values to find the maximum of (non-empty)
     * @return maximum of all given values
     * @throws IllegalArgumentException if no values are given
     */
    public static float max(float... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Given array is empty");
        }

        float max = values[0]; // Start with the first value (we know it's not empty)
        for (int i = 1; i < values.length; i++) {
            final float val = values[i];
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    /**
     * Varargs version of {@link Math#max(double, double)}.
     *
     * @param values the values to find the maximum of (non-empty)
     * @return maximum of all given values
     * @throws IllegalArgumentException if no values are given
     */
    public static double max(double... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Given array is empty");
        }

        double max = values[0]; // Start with the first value (we know it's not empty)
        for (int i = 1; i < values.length; i++) {
            final double val = values[i];
            if (val > max) {
                max = val;
            }
        }
        return max;
    }
}

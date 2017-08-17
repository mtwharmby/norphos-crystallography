package uk.co.norphos.crystallography.adaptor.apachemaths;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestUtils {

    public static void assert2dArrayEquals(double [][] expected, double[][] found, double delta) {
        assert2dArrayEquals(null, expected, found, delta);
    }

    public static void assert2dArrayEquals(String message, double [][] expected, double[][] found, double delta) {
        for (int i=0; i < found.length; i++) {
            assertArrayEquals(message, expected[i], found[i], delta);
        }
    }
}

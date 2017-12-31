package uk.co.norphos.crystallography.tk;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;
import org.mockito.internal.progress.HandyReturnValues;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;

public class TestUtils {

    public static void assertTwoDArrayEquals(String message, double[][] expecteds, double[][] actuals, double delta) {
        if (expecteds.length != actuals.length) {
            fail(message);
        }

        for (int i = 0; i < expecteds.length; i++) {
            assertArrayEquals(message+" (i="+i+")", expecteds[i], actuals[i], delta);
        }
    }

    public static void assertTwoDArrayEquals(double[][] expecteds, double[][] actuals, double delta) {
        assertTwoDArrayEquals(null, expecteds, actuals, delta);
    }

    public static boolean almostEquals(double first, double second, double delta) {
        return Math.abs(first - second) < delta;
    }

    protected static class twoDArrayAlmostEquals extends ArgumentMatcher<double[][]> {

        private double[][] expected;

        public twoDArrayAlmostEquals(double[][] expected) {
            this.expected = expected;
        }

        @Override
        public boolean matches(Object o) {
            if (o == null || double[][].class != o.getClass()) return false;
            double[][] obj = (double[][]) o;

            if (expected.length != expected.length) return false;
            for (int i = 0; i < obj.length; i++) {
                if (expected[i].length != obj[i].length) return false;
                for (int j = 0; j < expected[i].length; j++) {
                    double diff = expected[i][j] - obj[i][j];
                    double absDiff = Math.abs(diff);
                    if (Math.abs(expected[i][j] - obj[i][j]) > 1e-10) return false;
                }
            }
            return true;
        }
    }

    protected static BaseMatcher<double[][]> arrayAllElementsNonZero = new BaseMatcher<double[][]>() {
        @Override
        public boolean matches(Object o) {
            if (o == null || double[][].class != o.getClass()) return false;
            double[][] obj = (double[][]) o;

            for (int i = 0; i < obj.length; i++) {
                for (int j = 0; j < obj[i].length; j++) {
                    if (almostEquals(0, obj[i][j], 1e-10)) return false;
                }
            }
            return true;
        }

        @Override
        public void describeTo(Description description) {

        }
    };

    protected static BaseMatcher<double[][]> arrayLowerLeftZero = new BaseMatcher<double[][]>() {
        @Override
        public boolean matches(Object o) {
            if (o == null || double[][].class != o.getClass()) return false;
            double[][] obj = (double[][]) o;
            return almostEquals(0, obj[1][0], 1e-10) &&
                    almostEquals(0, obj[2][0], 1e-10) &&
                    almostEquals(0, obj[2][1], 1e-10);
        }

        @Override
        public void describeTo(Description description) {

        }
    };
}

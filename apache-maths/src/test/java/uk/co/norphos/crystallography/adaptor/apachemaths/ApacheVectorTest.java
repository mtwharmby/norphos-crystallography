package uk.co.norphos.crystallography.adaptor.apachemaths;

import org.junit.Test;
import uk.co.norphos.crystallography.api.maths.Vector;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ApacheVectorTest {

    @Test
    public void testBasicFunctions() {
        Vector vec = new ApacheVector(new double[]{10.,11.,12.});

        assertEquals("Wrong size", 3, vec.getSize());
        assertArrayEquals("Wrong shape", new int[]{3}, vec.getShape());
        assertEquals("Wrong value at (2)", 12d, vec.get(2), 0);
    }

    @Test
    public void testToArray() {
        double[] vecVals = new double[]{10.,11.,12.};

        Vector vec = new ApacheVector(vecVals);
        assertArrayEquals("Return array differs from the one vector created from", vecVals, vec.toArray(), 0);
    }

    @Test
    public void testSumSubtract() {
        Vector vecA = new ApacheVector(new double[]{10.,11.,12.});
        Vector vecB = new ApacheVector(new double[]{5., 3., 67.});

        assertArrayEquals("Sum of vectors is wrong", new double[]{15., 14., 79.}, vecA.add(vecB).toArray(), 0);

        assertArrayEquals("Subtracting vector from itself gave non-zero result", new double[]{0,0,0}, vecA.subtract(vecA).toArray(), 0);
        assertArrayEquals("Subtracting vectors A-B gave wrong answer", new double[]{5.,8.,-55.}, vecA.subtract(vecB).toArray(), 0);
        assertArrayEquals("Subtracting vectos B-A gave wrong answer", new double[]{-5.,-8.,55.}, vecB.subtract(vecA).toArray(), 0);
    }
}

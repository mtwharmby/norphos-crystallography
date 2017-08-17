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
    public void testSubtract() {

    }
}

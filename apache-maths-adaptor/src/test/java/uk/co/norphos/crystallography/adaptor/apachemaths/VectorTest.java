package uk.co.norphos.crystallography.adaptor.apachemaths;

import org.junit.Test;
import uk.co.norphos.crystallography.api.maths.IVector;

import static org.junit.Assert.assertEquals;

public class VectorTest {

    @Test
    public void testToArray() {
        double[] vecVals = new double[]{10.,11.,12.};

        IVector vec = new Vector(vecVals);
        assertEquals("Return array differs from the one vector created from", vecVals, vec.toArray());
    }
}

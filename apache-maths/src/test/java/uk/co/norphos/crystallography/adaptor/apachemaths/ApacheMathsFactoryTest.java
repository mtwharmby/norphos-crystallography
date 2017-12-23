package uk.co.norphos.crystallography.adaptor.apachemaths;

import org.junit.Before;
import org.junit.Test;
import uk.co.norphos.crystallography.api.maths.MathsFactory;
import uk.co.norphos.crystallography.api.maths.Matrix;
import uk.co.norphos.crystallography.api.maths.Vector;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static uk.co.norphos.crystallography.adaptor.apachemaths.TestUtils.assert2dArrayEquals;

public class ApacheMathsFactoryTest {

    private MathsFactory mathsFactory;

    @Before
    public void setUp() {
        mathsFactory = new ApacheMathsFactory();
    }

    @Test
    public void testMatrixBuild() {
        //Test building Matrix from double arrays
        double[][] inputArr = new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix fromArray = mathsFactory.buildMatrix(inputArr);
        assert2dArrayEquals("Matrix built from array not as expected", inputArr, fromArray.toArray(), 0d);

        //Test building Matrix from another Matrix
        ApacheMatrix apMat = new ApacheMatrix(inputArr);
        Matrix fromMat = mathsFactory.buildMatrix(apMat);
        assertEquals("Matrix built from matrix not as expected", apMat, fromMat);
    }

    @Test
    public void testVectorBuild() {
        //Test building Vector from double array
        double[] inputArr = new double[]{8, 6, 3};
        Vector fromArray = mathsFactory.buildVector(inputArr);
        assertArrayEquals("Vector built from array not as expected", inputArr, fromArray.toArray(), 0);

        //Test building Vector from another Vector
        ApacheVector apVec = new ApacheVector(inputArr);
        Vector fromVec = mathsFactory.buildVector(apVec);
        assertEquals("Vector built from another Vector not as expected", apVec, fromVec);
    }



//        Vector buildVector(double[] vector);
//
//        Vector buildVector(Vector vector);
//
//        Matrix buildMatrix(double[][] matrix);
//
//        Matrix buildMatrix(Matrix matr
}

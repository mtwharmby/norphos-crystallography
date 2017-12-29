package uk.co.norphos.crystallography.adaptor.apachemaths;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;
import uk.co.norphos.crystallography.api.maths.Matrix;
import uk.co.norphos.crystallography.api.maths.Vector;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static uk.co.norphos.crystallography.adaptor.apachemaths.TestUtils.assert2dArrayEquals;

public class ApacheMatrixTest {

    private Matrix mat;
    private double[][] matVals = new double[][]{{5.,4.,6.},{3.,1.,2.},{9.,8.,7.}};

    @Test
    public void testBasicFunctions() {
        double[][] matValsExtra = new double[][]{{5.,4.,6.},{3.,1.,2.},{9.,8.,7.}};
        mat = new ApacheMatrix(matValsExtra);

        assertEquals("Wrong size", 9, mat.getSize());
        assertArrayEquals("Wrong shape", new int[]{matValsExtra.length, matValsExtra[0].length}, mat.getShape());
        assertEquals("Wrong value at (2,1)", 8d, mat.get(2,1), 0);
        assertEquals("Wrong value at (1,2)", 2d, mat.get(1,2), 0);
    }

    @Test
    public void testToArray() {
        Matrix mat = new ApacheMatrix(matVals);
        assert2dArrayEquals("Output array different from that used in constructor", matVals, mat.toArray(), 0);
    }

    @Test
    public void testMultiplyVector() {
        double[] vecVals = new double[]{10.,11.,12.};

        Matrix mat = new ApacheMatrix(matVals);
        Vector vec = new ApacheVector(vecVals);

        RealMatrix apacheMat = MatrixUtils.createRealMatrix(matVals);
        RealVector apacheVec = new ArrayRealVector(vecVals);
        double[] apacheArrExpect = apacheMat.operate(vecVals);
        double[] apacheVecExpect = apacheMat.operate(apacheVec).toArray();

        //Test arrays, then test vectors
        assertArrayEquals("Array multiplication values differ", apacheArrExpect, mat.multiply(vecVals), 0);
        double[] vecProd = mat.multiply(vec).toArray();
        assertArrayEquals("ApacheVector multiplication values differ", apacheVecExpect, vecProd, 0);
    }

    @Test
    public void testGetInverse() {
        double[][] matVals = new double[][]{{1, 2, 3}, {0, 1, 4}, {5, 6, 0}};
        Matrix mat = new ApacheMatrix(matVals);

        double[][] invMatVals = new double[][]{{-24d, 18d, 5d}, {20d, -15d, -4d}, {-5d, 4d, 1d}};
        Matrix invMat = new ApacheMatrix(invMatVals);

        assert2dArrayEquals("Wrong inverse matrix calculated", invMatVals, mat.getInverse().toArray(), 1e-10);
    }

    @Test
    public void testGetDeterminant() {
        //2x2 case
        double[][] matVals = new double[][]{{3, 8}, {4, 6}};
        Matrix mat = new ApacheMatrix(matVals);
        assertEquals("Wrong value for determinant of 2x2 matrix", -14d, mat.getDeterminant(), 1e-10);

        //3x3 case
        matVals = new double[][]{{6, 1, 1}, {4, -2, 5}, {2, 8, 7}};
        mat = new ApacheMatrix((matVals));
        assertEquals("Wrong value for determinant of 3x3 matrix", -306d, mat.getDeterminant(), 1e-10);
    }

}

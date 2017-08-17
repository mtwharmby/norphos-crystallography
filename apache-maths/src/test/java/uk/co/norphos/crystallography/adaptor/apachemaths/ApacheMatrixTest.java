package uk.co.norphos.crystallography.adaptor.apachemaths;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;
import uk.co.norphos.crystallography.api.maths.Matrix;
import uk.co.norphos.crystallography.api.maths.Vector;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static uk.co.norphos.crystallography.adaptor.apachemaths.TestUtils.assert2dArrayEquals;

public class ApacheMatrixTest {

    private Matrix mat;
    private double[][] matVals = new double[][]{{5.,4.,6.},{3.,1.,2.},{9.,8.,7.}};

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
        assertArrayEquals("Array multiplication values differ", apacheArrExpect, mat.multiplyVector(vecVals), 0);
        double[] vecProd = mat.multiplyVector(vec).toArray();
        assertArrayEquals("ApacheVector multiplication values differ", apacheVecExpect, vecProd, 0);
    }
}

package uk.co.norphos.crystallography.adaptor.apachemaths;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;
import uk.co.norphos.crystallography.adaptor.apachemaths.Matrix;
import uk.co.norphos.crystallography.adaptor.apachemaths.Vector;
import uk.co.norphos.crystallography.api.maths.IMatrix;
import uk.co.norphos.crystallography.api.maths.IVector;

import static org.junit.Assert.assertEquals;

public class MatrixTest {

    @Test
    public void testMultiplyVector() {
        double[][] matVals = new double[][]{{5.,4.,6.},{3.,1.,2.},{9.,8.,7.}};
        double[] vecVals = new double[]{10.,11.,12.};

        IMatrix mat = new Matrix(matVals);
        IVector vec = new Vector(vecVals);

        RealMatrix apacheMat = MatrixUtils.createRealMatrix(matVals);
        RealVector apacheVec = new ArrayRealVector(vecVals);
        double[] apacheArrExpect = apacheMat.operate(vecVals);
        double[] apacheVecExpect = apacheMat.operate(apacheVec).toArray();

        //Test arrays, then test vectors
        assertEquals("Array multiplication values differ", apacheArrExpect, mat.multiplyVector(vecVals));
        double[] vecProd = mat.multiplyVector(vec).toArray();
        assertEquals("Vector multiplication values differ", apacheVecExpect, vecProd);
    }
}

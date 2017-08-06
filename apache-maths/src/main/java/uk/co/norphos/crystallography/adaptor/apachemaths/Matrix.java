package uk.co.norphos.crystallography.adaptor.apachemaths;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import uk.co.norphos.crystallography.api.maths.IMatrix;
import uk.co.norphos.crystallography.api.maths.IVector;

public class Matrix implements IMatrix {

    private RealMatrix matrix;

    public Matrix(double[][] values) {
        matrix = MatrixUtils.createRealMatrix(values);
    }

    @Override
    public double[] multiplyVector(double[] vector) {
        return matrix.operate(vector);
    }

    @Override
    public IVector multiplyVector(IVector vector) {
        return new Vector(multiplyVector(vector.toArray()));
    }
}

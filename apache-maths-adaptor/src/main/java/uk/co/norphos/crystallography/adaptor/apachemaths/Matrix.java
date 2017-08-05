package uk.co.norphos.crystallography.adaptor.apachemaths;

import uk.co.norphos.crystallography.api.maths.IMatrix;
import uk.co.norphos.crystallography.api.maths.IVector;

public class Matrix implements IMatrix {

    public Matrix(double[][] values) {

    }

    @Override
    public double[] multiplyVector(double[] vector) {
        return new double[0];
    }

    @Override
    public IVector multiplyVector(IVector vector) {
        return null;
    }
}

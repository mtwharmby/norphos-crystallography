package uk.co.norphos.crystallography.adaptor.apachemaths;

import uk.co.norphos.crystallography.api.maths.MathsFactory;
import uk.co.norphos.crystallography.api.maths.Matrix;
import uk.co.norphos.crystallography.api.maths.Vector;

public class ApacheMathsFactory implements MathsFactory {


    @Override
    public Vector buildVector(double[] vector) {
        return new ApacheVector(vector);
    }

    @Override
    public Vector buildVector(Vector vector) {
        return new ApacheVector(vector.toArray());
    }

    @Override
    public Matrix buildMatrix(double[][] matrix) {
        return new ApacheMatrix(matrix);
    }

    @Override
    public Matrix buildMatrix(Matrix matrix) {
        return new ApacheMatrix(matrix.toArray());
    }
}
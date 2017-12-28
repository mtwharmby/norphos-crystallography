package uk.co.norphos.crystallography.adaptor.apachemaths;

import uk.co.norphos.crystallography.api.maths.MathsFactory;
import uk.co.norphos.crystallography.api.maths.Matrix;
import uk.co.norphos.crystallography.api.maths.Vector;

public class ApacheMathsFactory implements MathsFactory {


    @Override
    public Vector createVector(double[] vector) {
        return new ApacheVector(vector);
    }

    @Override
    public Vector createVector(Vector vector) {
        return new ApacheVector(vector.toArray());
    }

    @Override
    public Matrix createMatrix(double[][] matrix) {
        return new ApacheMatrix(matrix);
    }

    @Override
    public Matrix createMatrix(Matrix matrix) {
        return new ApacheMatrix(matrix.toArray());
    }
}

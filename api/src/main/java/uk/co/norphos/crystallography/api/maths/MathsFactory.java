package uk.co.norphos.crystallography.api.maths;

public interface MathsFactory {

    Vector buildVector(double[] vector);

    Vector buildVector(Vector vector);

    Matrix buildMatrix(double[][] matrix);

    Matrix buildMatrix(Matrix matrix);
}

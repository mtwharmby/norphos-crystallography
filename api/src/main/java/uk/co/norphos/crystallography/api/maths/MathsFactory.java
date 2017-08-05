package uk.co.norphos.crystallography.api.maths;

public interface MathsFactory {

    IVector buildVector(double[] vector);

    IVector buildVector(IVector vector);

    IMatrix buildMatrix(double[][] matrix);

    IMatrix buildMatrix(IMatrix matrix);
}
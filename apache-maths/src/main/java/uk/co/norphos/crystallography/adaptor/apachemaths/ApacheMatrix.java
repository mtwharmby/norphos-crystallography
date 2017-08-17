package uk.co.norphos.crystallography.adaptor.apachemaths;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import uk.co.norphos.crystallography.api.maths.Matrix;
import uk.co.norphos.crystallography.api.maths.Vector;

public class ApacheMatrix implements Matrix {

    private RealMatrix matrix;

    public ApacheMatrix(double[][] values) {
        matrix = MatrixUtils.createRealMatrix(values);
    }

    @Override
    public int[] getShape() {
        return new int[]{matrix.getRowDimension(), matrix.getColumnDimension()};
    }

    @Override
    public int getSize() {
        return matrix.getColumnDimension() * matrix.getRowDimension();
    }

    @Override
    public double get(int i, int j) {
        return matrix.getEntry(i, j);
    }

    @Override
    public double[] multiply(double[] vector) {
        return matrix.operate(vector);
    }

    @Override
    public Vector multiply(Vector vector) {
        return new ApacheVector(multiply(vector.toArray()));
    }

    @Override
    public double[][] toArray() {
        return matrix.getData();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApacheMatrix that = (ApacheMatrix) o;

        return matrix.equals(that.matrix);
    }

    @Override
    public int hashCode() {
        return matrix.hashCode();
    }

}

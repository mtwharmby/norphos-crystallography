package uk.co.norphos.crystallography.adaptor.apachemaths;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import uk.co.norphos.crystallography.api.maths.Vector;

public class ApacheVector implements Vector {

    private RealVector vector;

    public ApacheVector(double[] values) {
        vector = new ArrayRealVector(values);
    }

    @Override
    public int[] getShape() {
        return new int[]{vector.getDimension()};
    }

    @Override
    public int getSize() {
        return vector.getDimension();
    }

    @Override
    public double get(int i) {
        return get(i, 0);
    }

    @Override
    public double get(int i, int j) {
        if (j != 0) throw new IllegalArgumentException("Vector only has one dimension");
        return vector.getEntry(i);
    }

    @Override
    public Vector add(double[] vector) {
        return add(new ApacheVector(vector));
    }

    @Override
    public Vector add(Vector vector) {
        RealVector summed = this.vector.add(((ApacheVector)vector).getRealVector());
        return new ApacheVector(summed.toArray());
    }

    @Override
    public Vector subtract(double[] vector) {
        return subtract(new ApacheVector(vector));
    }

    @Override
    public Vector subtract(Vector vector) {
        RealVector subtracted = this.vector.subtract(((ApacheVector)vector).getRealVector());
        return new ApacheVector(subtracted.toArray());
    }

    @Override
    public double[] toArray() {
        return vector.toArray();
    }

    public RealVector getRealVector() {
        return vector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApacheVector that = (ApacheVector) o;

        return vector.equals(that.vector);
    }

    @Override
    public int hashCode() {
        return vector.hashCode();
    }

}

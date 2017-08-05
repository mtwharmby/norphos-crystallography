package uk.co.norphos.crystallography.api.maths;

public interface IVector {

    IVector subtract(IVector vector);

    double[] toArray();
}

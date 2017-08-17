package uk.co.norphos.crystallography.api.maths;

public interface Vector extends MatrixLike {

    double get(int i);

    Vector subtract(double[] vector);

    Vector subtract(Vector vector);

    double[] toArray();
}

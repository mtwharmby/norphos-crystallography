package uk.co.norphos.crystallography.api.maths;

public interface Vector extends MatrixLike {

    double get(int i);

    Vector add(double[] vector);

    Vector add(Vector vector);

    Vector subtract(double[] vector);

    Vector subtract(Vector vector);

    double[] toArray();
}

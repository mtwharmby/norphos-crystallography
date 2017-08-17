package uk.co.norphos.crystallography.api.maths;

public interface Matrix extends MatrixLike {

    double[] multiply(double[] vector);

    Vector multiply(Vector vector);

    double[][] toArray();
}

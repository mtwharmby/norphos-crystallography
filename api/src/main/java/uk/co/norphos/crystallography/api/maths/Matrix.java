package uk.co.norphos.crystallography.api.maths;

public interface Matrix {

    double[] multiply(double[] vector);

    Vector multiply(Vector vector);
}

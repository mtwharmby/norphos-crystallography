package uk.co.norphos.crystallography.api.maths;

public interface Matrix {

    double[] multiplyVector(double[] vector);

    Vector multiplyVector(Vector vector);
}

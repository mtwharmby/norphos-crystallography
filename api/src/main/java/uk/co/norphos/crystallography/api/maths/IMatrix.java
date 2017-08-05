package uk.co.norphos.crystallography.api.maths;

public interface IMatrix {

    double[] multiplyVector(double[] vector);

    IVector multiplyVector(IVector vector);
}

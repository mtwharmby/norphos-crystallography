package uk.co.norphos.crystallography.api.maths;

public interface MatrixLike {

    int[] getShape();

    int getSize();

    double get(int i, int j);

}

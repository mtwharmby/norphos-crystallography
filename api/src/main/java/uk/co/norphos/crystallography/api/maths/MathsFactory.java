package uk.co.norphos.crystallography.api.maths;

/**
 *
 *
 * @author Michael Wharmby
 */
public interface MathsFactory {

    /**
     * Create new vector from a 1d double array.
     * @param vector 1d double array
     * @return {@link Vector} form of array
     */
    Vector createVector(double[] vector);

    /**
     * Create a new vector from an existing vector.
     * @param vector {@link Vector}
     * @return {@link Vector} copy of vector
     */
    Vector createVector(Vector vector);

    /**
     * Create a new matrix from a 2d double array.
     * @param matrix 2d double array
     * @return {@link Matrix} form of array
     */
    Matrix createMatrix(double[][] matrix);

    /**
     * Create a new matrix from an existing matrix.
     * @param matrix {@link Matrix}
     * @return {@link Matrix} copy of matrix
     */
    Matrix createMatrix(Matrix matrix);

}
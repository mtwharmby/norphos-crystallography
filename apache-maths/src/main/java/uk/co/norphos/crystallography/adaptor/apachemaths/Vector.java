package uk.co.norphos.crystallography.adaptor.apachemaths;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import uk.co.norphos.crystallography.api.maths.IVector;

public class Vector implements IVector {

    private RealVector vector;

    public Vector(double[] values) {
        vector = new ArrayRealVector(values);
    }

    @Override
    public IVector subtract(IVector vector) {
        return null;
    }

    @Override
    public double[] toArray() {
        return vector.toArray();
    }
}

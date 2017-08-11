package uk.co.norphos.crystallography.adaptor.apachemaths;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import uk.co.norphos.crystallography.api.maths.Vector;

public class ApacheVector implements Vector {

    private RealVector vector;

    public ApacheVector(double[] values) {
        vector = new ArrayRealVector(values);
    }

    @Override
    public Vector subtract(Vector vector) {
        return null;
    }

    @Override
    public double[] toArray() {
        return vector.toArray();
    }
}

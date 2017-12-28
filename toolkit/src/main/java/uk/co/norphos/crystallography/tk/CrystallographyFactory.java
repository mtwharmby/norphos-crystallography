package uk.co.norphos.crystallography.tk;

import uk.co.norphos.crystallography.api.CrystalSystem;
import uk.co.norphos.crystallography.api.Lattice;
import uk.co.norphos.crystallography.api.maths.Matrix;

public class CrystallographyFactory {

    public static CrystalSystem getCrystalSystem(Double[] lengths, Double[] angles) {
        return null;
    }

    public static Lattice createLattice(double a, Double b, Double c, Double alpha, Double beta, Double gamma) {
        return null;
    }

    public static Lattice createLattice(double[] lengths, double[] angles, Double volume) {
        return null;
    }

    public static Lattice createLattice(Matrix metricTensor) {
        return null;
    }

}

package uk.co.norphos.crystallography.tk;

import uk.co.norphos.crystallography.api.Lattice;
import uk.co.norphos.crystallography.api.MillerPlane;
import uk.co.norphos.crystallography.api.UnitCell;
import uk.co.norphos.crystallography.api.maths.MathsFactory;
import uk.co.norphos.crystallography.api.maths.Matrix;
import uk.co.norphos.crystallography.api.maths.Vector;

public class UnitCellImpl implements UnitCell {

    private final MathsFactory mathsFactory;

    private Lattice lattice;
    private Matrix metricTensor, orthogonalisationMatrix;
    private UnitCell reciprocalUnitCell;

    public UnitCellImpl(MathsFactory mathsFactory, Lattice lattice) {
        this.lattice = lattice;
        this.mathsFactory = mathsFactory;

        this.metricTensor = determineMetricTensor(lattice);
        reciprocalUnitCell = new UnitCellImpl(mathsFactory, metricTensor, true);

        this.orthogonalisationMatrix = determineOrthogonalisationMatrix();
    }

    public UnitCellImpl(MathsFactory mathsFactory, Matrix metricTensor) {
        this(mathsFactory, metricTensor, false);
    }

    public UnitCellImpl(MathsFactory mathsFactory, Matrix metricTensor, boolean reciprocal) {
        this.mathsFactory = mathsFactory;
        if (reciprocal) {
            this.metricTensor = metricTensor.getInverse();
        } else {
            this.metricTensor = metricTensor;
            reciprocalUnitCell = new UnitCellImpl(mathsFactory, metricTensor, true);
            this.orthogonalisationMatrix = determineOrthogonalisationMatrix();
        }
        this.lattice = CrystallographyFactory.createLattice(this.metricTensor);
}

    private Matrix determineMetricTensor(Lattice lattice) {
        double[] lengths = lattice.getLengths();
        double[] angles = lattice.getAnglesRadians();

        double[][] tensor = new double[3][3];
        int i, j, k;
        //Set diagonal values
        for (i = 0; i < 3; i++) {
            tensor[i][i] = Math.pow(lengths[i], 2);
        }
        //Set off diagonal values
        for (i = 0; i < 3; i++) {
            j = (i + 1) % 3;
            k = (j + 1) % 3;
            double val = lengths[i] * lengths[j] * Math.cos(angles[k]);
            if (Math.abs(val) < 1e-10) val = 0;
            tensor[i][j] = val;
            tensor[j][i] = val;
        }
        return mathsFactory.createMatrix(tensor);
    }
    private Matrix determineOrthogonalisationMatrix() {
        double[][] matrix = new double[][]{{lattice.getA(), lattice.getB() * Math.cos(lattice.getGaR()), lattice.getC() * Math.cos(lattice.getBeR())},
                {0, lattice.getB() * Math.sin(lattice.getGaR()), -lattice.getC() * Math.cos(getReciprocalLattice().getAlR())},
                {0, 0, 1 / getReciprocalLattice().getC()}};
        return mathsFactory.createMatrix(matrix);
    }

    @Override
    public Lattice getLattice() {
        return lattice;
    }

    @Override
    public Matrix getMetricTensor() {
        return metricTensor;
    }

    @Override
    public UnitCell getReciprocal() {
        return this.reciprocalUnitCell;
    }

    @Override
    public Matrix getFractionalizationMatrix() {
        return null;
    }

    @Override
    public Matrix getOrthogonalizationMatrix() {
        return null;
    }

    @Override
    public double calculateLength(Vector fracVec) {
        return 0;
    }

    @Override
    public double calculateAngle(Vector fracVec1, Vector fracVec2) {
        return 0;
    }

    @Override
    public double calculateDihedralAngle(Vector site1, Vector site2, Vector site3, Vector site4) {
        return 0;
    }

    @Override
    public MillerPlane getMaxMillerIndex(double dSpacing) {
        return null;
    }

    @Override
    public double calculateDSpacing(MillerPlane hkl) {
        return 0;
    }

    @Override
    public int compareTo(UnitCell o) {
        return 0;
    }
}

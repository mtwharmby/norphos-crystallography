package uk.co.norphos.crystallography.tk;

import uk.co.norphos.crystallography.api.Crystal;
import uk.co.norphos.crystallography.api.CrystalSystem;
import uk.co.norphos.crystallography.api.Lattice;
import uk.co.norphos.crystallography.api.PrincipleAxis;
import uk.co.norphos.crystallography.api.maths.Matrix;

import javax.xml.bind.PrintConversionEvent;
import java.util.*;

public class CrystallographyFactory {

    public static CrystalSystem getCrystalSystem(Double[] lengths, Double[] angles) {
        /*
         * Cubic - a = b = c; al = be = ga = 90
         * Rhombohedral - a = b = c; al = be = ga != 90
         * Hexagonal/Trigonal - a = b != c; al = be = 90 ga = 120
         * Tetragonal - a = b != c; al = be = ga = 90
         * Orthorhombic - a != b != c; al = be = ga = 90
         * Monoclinic - a != b != c; al = ga = 90 != be
         * Triclinic - a != b != c; al != be != ga
         */

        cleanupLengths(lengths);
        int nrEqualLengths = Collections.frequency(Arrays.asList(lengths), lengths[0]);
        if (hasAllSameAngles(angles, true)) {
            if (nrEqualLengths == 3) {
                return CrystalSystem.CUBIC;
            } else if (nrEqualLengths == 2) {
                return CrystalSystem.TETRAGONAL;
            } else {
                return CrystalSystem.ORTHORHOMBIC;
            }
        } else if (hasAllSameAngles(angles, false)) {
            return CrystalSystem.RHOMBOHEDRAL;
        } else if (Arrays.asList(angles).contains(120d) && nrEqualLengths == 2) {
            return CrystalSystem.HEXAGONAL;
        } else if (Collections.frequency(Arrays.asList(angles), 90d) == 2 && nrEqualLengths == 1) {
            return CrystalSystem.MONOCLINIC;
        } else if (Collections.frequency(Arrays.asList(angles), 90d) <= 1 && (
                !lengths[0].equals(lengths[1]) && !lengths[0].equals(lengths[2]) && !lengths[1].equals(lengths[2])
            )) {
            return CrystalSystem.TRICLINIC;
        }
        return CrystalSystem.UNKNOWN; //TODO Throw an error here instead? It's not really valid input.
    }

    private static void cleanupLengths(Double[] lengths) {
        //Cleanup the input lengths to remove nulls
        if (lengths[1] == null) lengths[1] = lengths[0];
        if (lengths[2] == null) lengths[2] = lengths[0];
    }

    private static boolean hasAllSameAngles(Double[] angles, boolean ortho) {
        boolean result = angles[0].equals(90d) == ortho;
        return angles[0].equals(angles[1]) && angles[0].equals(angles[2]) && result;
    }

    public static PrincipleAxis getPrincipalAxis(Double[] lengths, Double[] angles, CrystalSystem cSystem) {
        if (cSystem.equals(CrystalSystem.HEXAGONAL) || cSystem.equals(CrystalSystem.TETRAGONAL)) {
            return PrincipleAxis.C;
        } if (cSystem.equals(CrystalSystem.MONOCLINIC)) {
            List<Boolean> anglesCompared = new ArrayList<>(3); //[al-be, al-ga, be-ga]
            anglesCompared.add(angles[0].equals(angles[1]));
            anglesCompared.add(angles[0].equals(angles[2]));
            anglesCompared.add(angles[1].equals(angles[2]));

            if (anglesCompared.get(0)) {
                //ga different
                return PrincipleAxis.C;
            } else if (anglesCompared.get(1)) {
                //be different
                return PrincipleAxis.B;
            } else {
                //al different
                return PrincipleAxis.A;
            }
        }
        return PrincipleAxis.NONE;
    }

    public static Lattice createLattice(double a, Double b, Double c, Double alpha, Double beta, Double gamma) {
        return createLattice(new Double[]{a, b, c}, new Double[]{alpha, beta, gamma}, null);
    }

    public static Lattice createLattice(Double[] lengths, Double[] angles, Double volume) {
        CrystalSystem cSystem = getCrystalSystem(lengths, angles);
        PrincipleAxis pAxis = getPrincipalAxis(lengths, angles, cSystem);

        return new Lattice(lengths, angles, volume, cSystem, pAxis);
    }

    public static Lattice createLattice(Matrix metricTensor) {
        double[][] tensor = metricTensor.toArray();

        Double[] lengths = new Double[3], angles = new Double[3];
        double volume;

        int i,j,k;

        //Recover lengths...
        for (i = 0; i < 3; i++) {
            lengths[i] = Math.sqrt(tensor[i][i]);
        }
        //... and angles...
        for (i = 0; i <3; i++) {
            j = (i + 1) % 3;
            k = (j + 1) % 3;

            angles[i] = Math.toDegrees(Math.acos(tensor[j][k] / (lengths[j] * lengths[k])));
        }

        //... and volume
        volume = Math.sqrt(metricTensor.getDeterminant());

        return createLattice(lengths, angles, volume);
    }

}

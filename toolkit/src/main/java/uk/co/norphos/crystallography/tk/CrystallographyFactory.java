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
        //Any null lengths should be equal to the a parameter
        Double[] noNullLengths = replaceNulls(lengths, lengths[0]);
//         noNullAngles = replaceNulls(angles, angles[0]);
        if (hasAllSameAngles(angles, false)) {
            return CrystalSystem.RHOMBOHEDRAL;
        }

        //We know all remaining null angles should be 90deg.
        Double[] noNullAngles = replaceNulls(angles, 90d);
        int nrEqualLengths = Collections.frequency(Arrays.asList(noNullLengths), noNullLengths[0]);
        if (hasAllSameAngles(noNullAngles, true)) {
            if (nrEqualLengths == 3) {
                return CrystalSystem.CUBIC;
            } else if (nrEqualLengths == 2) {
                return CrystalSystem.TETRAGONAL;
            } else {
                return CrystalSystem.ORTHORHOMBIC;
            }
        } else if (Arrays.asList(noNullAngles).contains(120d) && nrEqualLengths == 2) {
            return CrystalSystem.HEXAGONAL;
        } else if (Collections.frequency(Arrays.asList(noNullAngles), 90d) == 2 && nrEqualLengths == 1) {
            return CrystalSystem.MONOCLINIC;
        } else if (Collections.frequency(Arrays.asList(noNullAngles), 90d) <= 1 && (
                !noNullLengths[0].equals(noNullLengths[1]) && !noNullLengths[0].equals(noNullLengths[2]) && !noNullLengths[1].equals(noNullLengths[2])
            )) {
            return CrystalSystem.TRICLINIC;
        }
        return CrystalSystem.UNKNOWN; //TODO Throw an error here instead? It's not really valid input.
    }

    private static boolean hasAllSameAngles(Double[] angles, boolean ortho) {
        try {
            boolean result = (angles[0] == null || angles[0] == 90d) == ortho;
            return angles[0].equals(angles[1]) && angles[0].equals(angles[2]) && result;
        } catch (NullPointerException npe) {
            return false;
        }
    }

    private static Double[] replaceNulls(Double[] angles, double replacement) {
        return Arrays.stream(angles).map(ang -> ang == null ? replacement : ang).toArray(Double[]::new);
    }

    public static PrincipleAxis getPrincipalAxis(Double[] angles, CrystalSystem cSystem) {
        if (cSystem.equals(CrystalSystem.HEXAGONAL) || cSystem.equals(CrystalSystem.TETRAGONAL)) {
            return PrincipleAxis.C;
        } if (cSystem.equals(CrystalSystem.MONOCLINIC)) {
            //We know all null angles will be 90deg.
            Double[] noNullAngles = replaceNulls(angles, 90d);
            List<Boolean> anglesCompared = new ArrayList<>(3); //[al-be, al-ga, be-ga]
            anglesCompared.add(noNullAngles[0].equals(noNullAngles[1]));
            anglesCompared.add(noNullAngles[0].equals(noNullAngles[2]));
            anglesCompared.add(noNullAngles[1].equals(noNullAngles[2]));

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
        PrincipleAxis pAxis = getPrincipalAxis(angles, cSystem);

        //Cleanup the input to remove nulls:
        Double[] cleanedLengths = replaceNulls(lengths, lengths[0]);
        Double[] cleanedAngles = cSystem.equals(CrystalSystem.RHOMBOHEDRAL) ? replaceNulls(angles, angles[0]) : replaceNulls(angles, 90d);
        return new Lattice(cleanedLengths, cleanedAngles, volume, cSystem, pAxis);
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

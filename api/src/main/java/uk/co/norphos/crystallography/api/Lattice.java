package uk.co.norphos.crystallography.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * A bean-like object which holds all of the parameters necessary to specify a 
 * periodic lattice.
 * 
 * @author Michael Wharmby
 *
 */
public class Lattice implements Serializable, Comparable<Lattice> {

    private static final long serialVersionUID = 5083826131364460534L;
    
    private final double[] lengths, angles, anglesRadians;
    private final Double volume;
    private final PrincipleAxis principleAxis;
    private final CrystalSystem crystalSystem;
    
    /**
     * Construct {@link Lattice} object from distances a, b, c and angles 
     * alpha, beta, gamma. Volume is set to null, crystal system defaults to 
     * TRICLINIC and Principle axis defaults to NONE.
     *  
     * @param a double in Angstrom
     * @param b double in Angstrom
     * @param c double in Angstrom
     * @param al double in degrees
     * @param be double in degrees
     * @param ga double in degrees
     */
    public Lattice(double a, double b, double c, double al, double be, double ga) {
        this(a, b, c, al, be, ga, null, CrystalSystem.UNKNOWN, PrincipleAxis.NONE);
    }
    
    /**
     * Construct {@link Lattice} object from distances a, b, c and angles 
     * alpha, beta, gamma. Crystal system indicates the metric symmetry of the 
     * lattice. Principle axis indicates highest symmetry axis of the lattice.
     *  
     * @param a double in Angstrom
     * @param b double in Angstrom
     * @param c double in Angstrom
     * @param al double in degrees
     * @param be double in degrees
     * @param ga double in degrees
     * @param volume Double in Angstrom<sup>3</sup>
     * @param crystalSystem {@link CrystalSystem}
     * @param pAxis {@link PrincipleAxis}
     */
    public Lattice(double a, double b, double c, double al, double be, double ga, Double volume, CrystalSystem crystalSystem, PrincipleAxis pAxis) {
        lengths = new double[]{a,b,c};
        angles = new double[]{al,be,ga};
        anglesRadians = DoubleStream.of(angles).map(Math::toRadians).toArray();
        this.volume = volume;
        this.principleAxis = pAxis;
        this.crystalSystem = crystalSystem;
    }
    
    /**
     * Construct {@link Lattice} object from distances a, b, c and angles 
     * alpha, beta, gamma. Crystal system indicates the metric symmetry of the 
     * lattice. Principle axis indicates highest symmetry axis of the lattice.
     * @param lengths Double[] lattice length parameters in Angstrom
     * @param angles Double[] lattice angle parameters in degrees
     * @param volume Double in Angstrom<sup>3</sup>
     * @param crystalSystem {@link CrystalSystem}
     * @param pAxis {@link PrincipleAxis}
     */
    public Lattice(Double[] lengths, Double angles[], Double volume, CrystalSystem crystalSystem, PrincipleAxis pAxis) {
        this.lengths = Stream.of(lengths).mapToDouble(Double::doubleValue).toArray();
        this.angles = Stream.of(angles).mapToDouble(Double::doubleValue).toArray();
        this.anglesRadians = Stream.of(angles).mapToDouble(Double::doubleValue).map(Math::toRadians).toArray();
        this.volume = volume;
        this.crystalSystem = crystalSystem;
        this.principleAxis = pAxis;
    }
    
    /**
     * Returns all three lattice length parameters.
     * @return double[] in Angstrom
     */
    public double[] getLengths() {
        return lengths;
    }
    
    /**
     * Returns all three lattice angle parameters.
     * @return double[] in degrees
     */
    public double[] getAngles() {
        return angles;
    }

    /**
     * Returns all three lattice angle parameters.
     * @return double[] in radians
     */
    public double[] getAnglesRadians() {
        return anglesRadians;
    }

    /**
     * Return lattice a parameter
     * @return double in Angstrom
     */
    public double getA() {
        return lengths[0];
    }
    /**
     * Return lattice b parameter
     * @return double in Angstrom
     */
    public double getB() {
        return lengths[1];
    }

    /**
     * Return lattice c parameter
     * @return double in Angstrom
     */
    public double getC() {
        return lengths[2];
    }

    /**
     * Return lattice alpha parameter
     * @return double in degrees
     */
    public double getAl() {
        return angles[0];
    }

    /**
     * Return lattice beta parameter
     * @return double in degrees
     */
    public double getBe() {
        return angles[1];
    }

    /**
     * Return lattice gamma parameter
     * @return double in degrees
     */
    public double getGa() {
        return angles[2];
    }

    /**
     * Return lattice alpha parameter
     * @return double in radians
     */
    public double getAlR() {
        return anglesRadians[0];
    }

    /**
     * Return lattice beta parameter
     * @return double in radians
     */
    public double getBeR() {
        return anglesRadians[1];
    }

    /**
     * Return lattice gamma parameter
     * @return double in radians
     */
    public double getGaR() {
        return anglesRadians[2];
    }

    /**
     * Return the volume of the unit cell defined by the lattice.
     * @return double in Angstrom<sup>3</sup>
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * Return the crystal system of this lattice.
     * @return {@link CrystalSystem}
     */
    public CrystalSystem getCrystalSystem() {
        return crystalSystem;
    }

    /**
     * Return the {@link PrincipleAxis} of this lattice. Useful for example 
     * with monoclinic unit cells, where the principle axis is that 
     * perpendicular to plane containing the two 90degree lattice angles.
     * @return {@link PrincipleAxis}
     */
    public PrincipleAxis getPrincipleAxis() {
        return principleAxis;
    }
    
    @Override
    public int compareTo(Lattice o) {
        // TODO Auto-generated method stub
        //compare volume
        return 0;
    }

    @Override
    public String toString() {
        return "Lattice [a=" + lengths[0] + ", b=" + lengths[1] + ", c=" + lengths[2] + ", al=" 
                + angles[0] + ", be=" + angles[1] + ", ga=" + angles[2] + ", volume=" + volume 
                +", crystalSystem=" + crystalSystem    + ", principleAxis=" + principleAxis + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lattice lattice = (Lattice) o;
        return arraysAlmostEqual(lengths, lattice.lengths, 1e-10) &&
                arraysAlmostEqual(angles, lattice.angles, 1e-10) &&
                arraysAlmostEqual(anglesRadians, lattice.anglesRadians, 1e-10) &&
                Objects.equals(volume, lattice.volume) &&
                principleAxis == lattice.principleAxis &&
                crystalSystem == lattice.crystalSystem;
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(volume, principleAxis, crystalSystem);
        result = 31 * result + Arrays.hashCode(lengths);
        result = 31 * result + Arrays.hashCode(angles);
        result = 31 * result + Arrays.hashCode(anglesRadians);
        return result;
    }

    private boolean arraysAlmostEqual(double[] one, double[] two, double delta) {
        if (one.length != two.length) return false;
        for(int i = 0; i < one.length; i++) {
            if (Math.abs(one[i] - two[i]) < delta) {
                return true;
            }
        }
        return false;
    }
}
